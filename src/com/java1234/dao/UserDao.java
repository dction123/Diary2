package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.User;
import com.java1234.util.MD5Util;
import com.java1234.util.PropertiesUtil;

public class UserDao {
	
	public User login(Connection con,User user)throws Exception{
		User resultUser = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, MD5Util.EncoderPwdByMd5(user.getPassword()));
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			resultUser = new User();
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setMood(rs.getString("mood"));
			resultUser.setNickName(rs.getString("nickName"));
			resultUser.setImageName(PropertiesUtil.getValue("imageFile")+rs.getString("imageName"));
			
		}
		
		return resultUser;
		
		
	}
}
