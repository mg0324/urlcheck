package com.mgang.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.mgang.util.Pager;
import com.mgang.util.db.DBFactory;
import com.mgang.vo.Function;
import com.mgang.vo.Role;

/**
 * 
 * @author meigang 2014-11-4 9:31
 * Function的Dao
 */
public class FunctionDao extends BaseDao{
	
	/**
	 * 通过Role得到角色下的权限集合
	 * @param role 角色对象
	 * @return 该角色对象下的Function集合
	 */
	@SuppressWarnings("unchecked")
	public List<Function> getFunctionsByRole(Role role) {
		// TODO Auto-generated method stub
		List<Function> funs = null;
		String sql = "select * from t_function where funId in("
				+ "select funId from t_role_function where roleId=? and have=1)";
		Object[] params = {role.getRoleId()};
		funs = find(sql, params, Function.class);
		return funs;
	}
	/**
	 * 添加Function到数据库中
	 * @param f 要添加的权限对象
	 * @return  返回影响数据库的行数
	 */
	public int addFunction(Function f) {
		// TODO Auto-generated method stub
		String sql = "insert into t_function(funName,note,resource,stuffix,params,type) "
				+ "values(?,?,?,?,?,?)";
		Object[] params = {f.getFunName(),
				f.getNote(),
				f.getResource(),
				f.getStuffix(),
				f.getParams(),
				f.getType()};
		return update(sql, params);
	}
	/**
	 * 得到Function权限表中的总行数
	 * @return
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		String sql = "select count(*) count from t_function";
		return getCountFromTable(sql, null);
	}
	/**
	 * 从Function对应表查询得到当前页的page对象
	 * @param page page对象
	 * @param orderBy 按orderBy排序
	 * @return 返回带有数据的function的page对象
	 */
	public Pager getFunPage(Pager page, String orderBy) {
		// TODO Auto-generated method stub
		//mysql优化查询
		String sql = "select * from t_function order by ? asc  limit ?,?";
		Object[] params = {orderBy,
				(page.getCurrentPage()-1)*page.getPageSize(),
				page.getPageSize()
				};
		page.setList(find(sql, params, Function.class));
		return page;
	}
	/**
	 * 删除权限
	 * @param f 通过f对象中的funId
	 * @return 返回影响数据库的行数
	 */
	public int deleteFunction(Function f) {
		// TODO Auto-generated method stub
		boolean b = true;
		//开事务
		try {
			conn = DBFactory.getConnection();
			conn.setAutoCommit(false);
			QueryRunner query = new QueryRunner();
			//第一步先将有该function的role在t_role_function表中的have字段设置为0
			String sql = "update t_role_function set have=0 where funId=?";
			Object[] p = {f.getFunId()};
			query.update(conn,sql,p);
			//第二步将该function删除掉
			sql = "delete from t_function where funId=?";
			Object[] params = {f.getFunId()};
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
	 * 修改权限
	 * @param f 通过f对象中的funId
	 * @return 返回影响数据库的行数
	 */
	public int updateFunction(Function f) {
		String sql = "update t_function set funName=?,"
				+ "note=?,"
				+ "resource=?,"
				+ "stuffix=?,"
				+ "params=?,"
				+ "type=? where funId=?";
		Object[] params = {f.getFunName(),
				f.getNote(),
				f.getResource(),
				f.getStuffix(),
				f.getParams(),
				f.getType(),
				f.getFunId()};
		return update(sql, params);
	}
	/**
	 * 通过funId得到Function对象
	 * @param funId
	 * @return 返回查询得到的Function对象
	 */
	public Function getFunctionById(int funId) {
		// TODO Auto-generated method stub
		String sql = "select * from t_function where funId=?";
		Object[] params = {funId};
		return (Function) findObject(sql, params, Function.class);
	}
	/**
	 * 得到所有的权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Function> getAllFunctions() {
		// TODO Auto-generated method stub
		String sql = "select * from t_function order by type asc";
		return find(sql, null, Function.class);
	}

}
