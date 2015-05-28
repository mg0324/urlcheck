package com.mgang.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.mgang.mgds4j.core.MgDataSource;
import com.mgang.util.db.DBFactory;


/**
 * 
 * @author 梅刚 2014-11-3
 * 基本的dao,封装DBUtils的CRUD和一些常用的方法。
 *
 */
public abstract class BaseDao{
	protected static Connection conn = null;
	/**
	 * 统一的增删改方法
	 * update操作:insert,delete,update
	 * @param sql 带问号的sql语句
	 * @param params 按顺序构造的给问号赋值的参数列表
	 * @return 返回影响的行数
	 */
	public int update(String sql,Object[] params){
		int c = 0;
		conn = DBFactory.getConnection();
		QueryRunner query = new QueryRunner();
		try {
			c = query.update(conn, sql, params);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		return c;
	}
	
	
	/**
	 * 返回一个clazz对应的对象
	 * @param sql 查询的sql语句，可以带?号
	 * @param params 给问号赋值
	 * @param clazz 查询的表对应的java vo对象的Class
	 * @return 
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public Object findObject(String sql,Object[] params,Class clazz){
		conn = DBFactory.getConnection();
		QueryRunner query = new QueryRunner();
		Object obj = null;
		
		try {
			obj = query.query(conn, sql, params, new BeanHandler(clazz));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		return obj;
	}
	/**
	 * 从select count(*) count from t_xxx where ?? 注意要去别名为count
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getCountFromTable(String sql,Object[] params){
		conn = DBFactory.getConnection();
		QueryRunner query = new QueryRunner();
		Map<String,Object> map = null;
		int c = 0;
		try {
			map = query.query(conn, sql, params, new MapHandler());
			if(map.size()==1){
				c = new Integer(map.get("count").toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		return c;
	}
	/**
	 * 得到主键自增长的表中的最大的id
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getMaxId(String sql,Object[] params){
		conn = DBFactory.getConnection();
		QueryRunner query = new QueryRunner();
		Map<String,Object> map = null;
		int c = 0;
		try {
			map = query.query(conn, sql, params, new MapHandler());
			if(map.size()==1){
				c = new Integer(map.get("maxId").toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		return c;
	}
	/**
	 * 统一的查询，得到clazz对应的list集合
	 * @param sql 查询的sql语句，带有?
	 * @param params 给问号赋值的参数列表
	 * @param clazz 查询表对应的vo对应的Class
	 * @return 返回查询得到的list集合
	 */
	@SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
	public List find(String sql,Object[] params,Class clazz){
		conn = DBFactory.getConnection();
		QueryRunner query = new QueryRunner();
		List list = null;
		try {
			list = (List) query.query(conn, sql, params, new BeanListHandler(clazz));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBFactory.backConnection(conn);
		}
		return list;
	}
	
	
	
}
