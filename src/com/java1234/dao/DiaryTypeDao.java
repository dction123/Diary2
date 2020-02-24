package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Diary;
import com.java1234.model.DiaryType;
import com.java1234.util.DbUtil;

/**
 * 日志类别Dao
 * @author Administrator
 *
 */
public class DiaryTypeDao {
	public List<DiaryType> diaryTypeCountList(Connection con ) throws SQLException{
		List<DiaryType> diaryTypeCountList = new ArrayList<DiaryType>();
		String sql="SELECT diaryTypeId,typeName,COUNT(diaryId) as diaryTypeCount FROM t_diary RIGHT JOIN t_diaryType ON t_diary.typeId=t_diaryType.diaryTypeId GROUP BY typeName;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
			diaryType.setDiaryCount(rs.getInt("diaryTypeCount"));
			diaryTypeCountList.add(diaryType);
			
		}
		return diaryTypeCountList;
		
	}
	
	/*
	 * 查询全部日志类别信息
	 */
	public  List<DiaryType> diaryTypeList(Connection con) throws Exception{
		List<DiaryType> diaryTypeList = new ArrayList<DiaryType>();
		
		String sql = "select * from t_diaryType";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
			diaryTypeList.add(diaryType);
		}
		
		return diaryTypeList;
	}

	/**
	 * 删除日志
	 * @param con
	 * @param diaryTypeId
	 * @return
	 * @throws Exception
	 */
	public  int deleteDiaryType(Connection con,String diaryTypeId)throws Exception{
		String sql = "delete from t_diaryType where diaryTypeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diaryTypeId);
		return pstmt.executeUpdate();
	}
	/**
	 * 添加类别
	 * @param con
	 * @param diaryTypeId
	 * @return
	 * @throws Exception
	 */
	public  int addDiaryType(Connection con,DiaryType diaryType)throws Exception{
		String sql = "insert into t_diaryType values(null,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diaryType.getTypeName());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 修改
	 * @param con
	 * @param diaryTypeId
	 * @return
	 * @throws Exception
	 */
	public  int updateDiaryType(Connection con,DiaryType diaryType)throws Exception{
		String sql = "update t_diaryType set typeName=? where diaryTypeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diaryType.getTypeName());
		pstmt.setInt(2, diaryType.getDiaryTypeId());
		return pstmt.executeUpdate();
	}
	
	
	/**
	 * 查询一条记录
	 * @param con
	 * @param diaryTypeId
	 * @return
	 * @throws Exception
	 */
	public DiaryType selectdiaryType(Connection con,String diaryTypeId) throws Exception{
		
		String sql = "select * from t_diaryType where diaryTypeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diaryTypeId);
		ResultSet rs = pstmt.executeQuery();
		DiaryType diaryType = null;
		if(rs.next()){
			diaryType = new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
		}
		
		return diaryType;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	/*
	public static void main(String[] args) throws Exception {
		DbUtil dbUtil = new DbUtil();
		
		for (DiaryType diaryType : selectdiaryType(dbUtil.getCon(),"1")) {
			System.out.println(diaryType.getDiaryTypeId());
			System.out.println(diaryType.getTypeName());
		}
		
	}*/
}
