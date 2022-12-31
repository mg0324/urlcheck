package com.mgang.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mgang.util.Pager;
import com.mgang.util.db.DBFactory;
import com.mgang.util.prop.PropAppConfig;
import com.mgang.vo.User;
import com.mgang.vo.UserRole;

/**
 * 
 * @author meigang 2014-11-4 8:33
 * 用户dao
 */
public class UserDao extends BaseDao{
	private static RoleDao roleDao;
	public UserDao(){
		roleDao = new RoleDao();
	}
	/**
	 * 检查登陆用户是否存在数据库中
	 * @param u 带有userName和password的user对象
	 * @return 如果存在userName和password对应的用户，就返回查询得到的整个user对象。
	 * 			反之就返回null。
	 */
	public User checkUser(User u) {
		// TODO Auto-generated method stub
		String sql = "select * from t_user where userName=? and password=?";
		Object[] params = {u.getUserName(),u.getPassword()};
		u = (User) findObject(sql, params, User.class);
		if(u != null){
			/**
			 * 更新登陆的用户
			 */
			updateLoginUser(u);
			/**
			 * 需要加入User的Role,Role的Function
			 */
			u.setRoleList(roleDao.getRolesByUser(u));
		}
		return u;
	}
	
	/**
	 * 更新登陆成功的用户信息
	 * @param u 要更新的user对象
	 */
	private void updateLoginUser(User u){
		u.setLastLoginTime(new Date());
		String sql = "update t_user set lastLoginTime=?,loginCount=? where userId=?";
		Object[] params = {u.getLastLoginTime(),u.getLoginCount()+1,u.getUserId()};
		update(sql, params);
	}
	/**
	 * 更新user对象的密码到数据库中
	 * @param u 要保存的user对象
	 * @return 返回数据库操作影响的行数
	 */
	public int updateUserPwd(User u) {
		String sql = "update t_user set password=? where userId=?";
		Object[] params = {u.getPassword(),u.getUserId()};
				
		return update(sql, params);
	}
	/**
	 * 得到t_user表的总行数
	 * @return
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		String sql = "select count(userId) count from t_user";
		return getCountFromTable(sql, null);
	}
	/**
	 * 得到带有数据的pege对象
	 * @param page
	 * @param orderBy 排序字段
	 * @return 返回带有user集合的page
	 */
	public Pager findUserPage(Pager page, String orderBy,List<Integer> userTypes) {
		// TODO Auto-generated method stub
		
		String sql = "select * from t_user where userType=? or userType=? order by ? asc  limit ?,?";
		Object[] params = {userTypes.get(0),
				userTypes.get(1),
				orderBy,
				(page.getCurrentPage()-1)*page.getPageSize(),
				page.getPageSize()
				};
		page.setList(find(sql, params, User.class));
		return page;
	}
	/**
	 * 屏蔽或者禁用用户
	 * @param u 要禁用的用户
	 * @return 成功返回true,反之返回false.
	 */
	public int stopUser(User u) {
		// TODO Auto-generated method stub
		String sql = "update t_user set status=0 where userId=?";
		Object[] params = {u.getUserId()};
		return update(sql, params);
	}
	/**
	 * 解锁用户
	 * @param u
	 * @return 成功返回true，反之返回false
	 */
	public int activeUser(User u) {
		// TODO Auto-generated method stub
		String sql = "update t_user set status=1 where userId=?";
		Object[] params = {u.getUserId()};
		return update(sql, params);
	}
	/**
	 * 通过userId得到user对象
	 * @param userId
	 * @return 返回user对象
	 */
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		String sql = "select * from t_user where userId=?";
		Object[] params = {userId};
		User u = (User) findObject(sql, params, User.class);
		//封装roleList
		if(null != u)
			u.setRoleList(roleDao.getRolesByUser(u));
		return u;
	}
	/**
	 * 变更用户
	 * @param u 要变更的用户对象
	 * @param update10 要update have 1->0的roleId编号
	 * @param insertUpdate01 要insert或者是update 0->1的roleId编号
	 * @return 在一个事务中，同时成功返回true.否则返回false.
	 */
	public boolean changeUser(User u, List<Integer> update10,
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
			//第一步先更新user对象到t_user表中
			String sql = "update t_user set userType=? where userId=?";
			Object[] params = {u.getUserType(),u.getUserId()};
			query.update(conn, sql, params);
			
			//第二步查询得到该User在t_User_Role中的have为0的funId列表
			sql = "select * from t_user_role where userId=? and have=0";
			Object[] p1 = {u.getUserId()};
			@SuppressWarnings({"deprecation" })
			List<UserRole> urs = (List<UserRole>) query.query(conn, sql, p1,new BeanListHandler<UserRole>(UserRole.class));
			//从insertUpdate01中找到要update01的
			if(null != urs && urs.size() > 0){
				for(Integer iu : insertUpdate01){
					boolean isHave = false;
					for(UserRole ur : urs){
						int roleId = ur.getRoleId();
						if(iu == roleId){
							//在数据库t_user_role中存在要update0->1的记录
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
			//第2.2步，update01中的have 为0->1
			if(0 != update01.size()){
				for(Integer u01: update01){
					sql = "update t_user_role set have=1 where userId=? and roleId=?";
					Object[] p2 = {u.getUserId(),u01};
					query.update(conn,sql,p2);
				}
			}
			//第2.3步，insert中的加入到t_user_role中
			if(0 != insert.size()){
				for(Integer i : insert){
					sql = "insert into t_user_role(userId,roleId,have) values(?,?,1)";
					Object[] p3 = {u.getUserId(),i};
					query.update(conn,sql,p3);
				}
			}
			//第三步将update10中的在t_User_Role中have 1->0
			if(0 != update10.size()){
				for(Integer u10 : update10){
					sql = "update t_user_role set have=0 where userId=? and roleId=?";
					Object[] lp = {u.getUserId(),u10};
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
	 * 添加管理员用户
	 * @param u
	 * @return 返回影响数据库的行数
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	public int addAdmin(User u) throws Exception {
		// TODO Auto-generated method stub
		String sql = "insert into t_user(userName,password,userType,score,regTime,loginCount,status) values"
				+ "(?,?,?,?,?,?,?)";
		Object[] params = {u.getUserName(),
				u.getPassword(),
				2,	
				Integer.parseInt(PropAppConfig.get("NOMAL_MANAGER_SCORE").toString().trim()),
				new Date(),
				0,
				1};
		return update(sql, params);
	}
	

}
