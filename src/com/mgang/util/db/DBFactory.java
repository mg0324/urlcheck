package com.mgang.util.db;

import java.sql.Connection;

import com.mgang.mgds4j.core.MgDataSource;
import com.mgang.mgds4j.core.MgDataSourceFactory;
import com.mgang.util.LogUtil;

public class DBFactory {
	private static MgDataSource mgds;
	static{
		MgDataSourceFactory.build();
		mgds = MgDataSourceFactory.getMgDataSource("ds");
	}
	
	public static Connection getConnection(){
		Connection conn = mgds.getConnection();
		LogUtil.log.debug("从连接池中获得数据库连接");
		return conn;
	}
	
	public static void backConnection(Connection conn){
		mgds.close(conn);
		LogUtil.log.debug("将数据库连接还回连接池");
	}
}
