package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.DiaryType;

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
	
	

}
