package com.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	private String dbUrl="jdbc:mysql://10.144.244.155:3306/Hotel?useUnicode=true&characterEncoding=utf8";
	private String dbUserName="root";
	private String dbPassword="root";
	private String jdbcName="com.mysql.jdbc.Driver";
	
	/**
	 * 连接数据库
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
	
	/**
	 * 关闭数据库
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection conn) throws Exception{
		if(conn!=null){
			conn.close();
		}
	}
	
}
