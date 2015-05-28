package com.mgang.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mgang.util.Pager;
import com.mgang.util.db.DBFactory;
import com.mgang.vo.Role;
import com.mgang.vo.RoleFunction;
import com.mgang.vo.User;

/**
 * 
 * @author meigang 2014-11-4 9:30
 * Role的Dao
 */
public class RoleDao extends BaseDao{
	private static FunctionDao funDao;
	public RoleDao(){
		funDao = new FunctionDao();
	}
	/**
	 * 通过user对象封装user对象有的Role角色对象
	 * @param u user对象
	 * @return Role对象的list集合
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getRolesByUser(User u){
		List<Role> roles = null;
		String sql = "select * from t_role where roleId in("
				+ "select roleId from t_user_role where userId=? and have=1)";
		Object[] params = {u.getUserId()};
		roles = find(sql, params, Role.class);
		/**
		 * 可以封装好Role下的Function
		 */
		makeFunctionToRole(roles);
		return roles;
	}
	/**
	 * 封装Role下的Function集合
	 * @param roles 角色集合
	 */
	public void makeFunctionToRole(List<Role> roles){
		for(Role role : roles){
			role.setFunList(funDao.getFunctionsByRole(role));
		}
	}
	/**
	 * 添加角色
	 * @param r
	 * @return 返回影响数据库的行数
	 */
	public int addRole(Role r) {
		// TODO Auto-generated method stub
		String sql = "insert into t_role(roleName,note) values(?,?)";
		Object[] params = {r.getRoleName(),
				r.getNote()};
		return update(sql, params);
	}
	/**
	 * 得到t_role的总行数
	 * @return
	 */
	public int getCount() {
		String sql = "select count(*) count from t_role";
		return getCountFromTable(sql, null);
	}
	
	/**
	 * 从Role对应表查询得到当前页的page对象
	 * @param page page对象
	 * @param orderBy 按orderBy排序
	 * @return 返回带有数据的role的page对象
	 */
	public Pager getRolePage(Pager page, String orderBy) {
		//mysql优化查询
		String sql = "select * from t_role order by ? asc  limit ?,?";
		Object[] params = {orderBy,
				(page.getCurrentPage()-1)*page.getPageSize(),
				page.getPageSize()
				};
		page.setList(find(sql, params, Role.class));
		return page;
	}
	
	/**
	 * 删除角色
	 * @param f 通过f对象中的roleId
	 * @return 返回影响数据库的行数
	 */
	public int deleteRole(Role r) {
		boolean b = true;
		//开事务
		try {
			conn = DBFactory.getConnection();
			conn.setAutoCommit(false);
			QueryRunner query = new QueryRunner();
			//第一步先将有该role的user在t_user_role表中的have字段设置为0
			String sql = "update t_user_role set have=0 where roleId=?";
			Object[] p = {r.getRoleId()};
			query.update(conn,sql,p);
			//第二步将该role删除掉
			sql = "delete from t_role where roleId=?";
			Object[] params = {r.getRoleId()};
			query.update(conn,sql,params);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				//回滚
				b = false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		if(b){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 通过roleId得到role对象
	 * @param roleId
	 * @return 返回role对象
	 */
	public Role getRoleById(int roleId) {
		// TODO Auto-generated method stub
		String sql = "select * from t_role where roleId=?";
		Object[] params = {roleId};
		Role r = (Role) findObject(sql, params, Role.class);
		//将role下的权限封装进去
		if(null != r)
			r.setFunList(funDao.getFunctionsByRole(r));
		return r;
				
	}
	/**
	 * 变更角色
	 * @param r 要变更的角色对象
	 * @param update10 要update have 1->0的funId编号
	 * @param insertUpdate01 要insert或者是update 0->1的funId编号
	 * @return 在一个事务中，同时成功返回true.否则返回false.
	 */
	public boolean changeRole(Role r, List<Integer> update10,
			List<Integer> insertUpdate01) {
		// TODO Auto-generated method stub
		List<Integer> insert = new ArrayList<Integer>();
		List<Integer> update01 = new ArrayList<Integer>();
		boolean b = true;
		//开事务
		try {
			conn = DBFactory.getConnection();
			conn.setAutoCommit(false);
			QueryRunner query = new QueryRunner();
			//第一步先更新role对象到t_role表中
			String sql = "update t_role set roleName=?,note=? where roleId=?";
			Object[] params = {r.getRoleName(),r.getNote(),r.getRoleId()};
			query.update(conn, sql, params);
			
			//第二步查询得到该role在t_role_function中的have为0的funId列表
			sql = "select * from t_role_function where roleId=? and have=0";
			Object[] p1 = {r.getRoleId()};
			@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
			List<RoleFunction> rfs = (List<RoleFunction>) query.query(conn, sql, p1,new BeanListHandler(RoleFunction.class));
			//从insertUpdate01中找到要update01的
			if(null != rfs && rfs.size() > 0){
				for(Integer iu : insertUpdate01){
					boolean isHave = false;
					for(RoleFunction rf : rfs){
						int funId = rf.getFunId();
						if(iu == funId){
							//在数据库t_role_function中存在要update0->1的记录
							isHave = true;
							update01.add(iu);
							break;
						}
					}
					//如果不存在就加入到insert中
					if(!isHave){
						insert.add(iu);
					}
				}
			}else{
				for(Integer iu : insertUpdate01){
					insert.add(iu);
				}
			}
			/*System.out.println("update01:"+update01.toString());
			System.out.println("insert:"+insert.toString());*/
			
			//第2.2步，update01中的have 为0->1
			if(0 != update01.size()){
				for(Integer u01: update01){
					sql = "update t_role_function set have=1 where roleId=? and funId=?";
					Object[] p2 = {r.getRoleId(),u01};
					query.update(conn,sql,p2);
				}
			}
			//第2.3步，insert中的加入到t_role_function中
			if(0 != insert.size()){
				for(Integer i : insert){
					sql = "insert into t_role_function(roleId,funId,have) values(?,?,1)";
					Object[] p3 = {r.getRoleId(),i};
					query.update(conn,sql,p3);
				}
			}
			//第三步将update10中的在t_role_function中have 1->0
			if(0 != update10.size()){
				for(Integer u10 : update10){
					sql = "update t_role_function set have=0 where roleId=? and funId=?";
					Object[] lp = {r.getRoleId(),u10};
					query.update(conn,sql,lp);
				}
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				//回滚
				b = false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		return b;
	}
	/**
	 * 得到所有的角色集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		String sql = "select * from t_role";
		return find(sql, null, Role.class);
	}
}
