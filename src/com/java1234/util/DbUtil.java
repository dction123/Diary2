package com.java1234.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	public Connection getCon()throws Exception{
		
		Class.forName(PropertiesUtil.getValue("jdbcName"));
		Connection conn = DriverManager.getConnection(PropertiesUtil.getValue("dbUrl"), PropertiesUtil.getValue("dbUserName"),  PropertiesUtil.getValue("dbPassword"));
		return conn;
	
	}
	
	public void closeCon(Connection conn)throws Exception{
		conn.close();
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			Connection con = dbUtil.getCon();
			System.out.println(con);
			dbUtil.closeCon(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
