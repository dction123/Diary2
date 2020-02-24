package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.java1234.model.Diary;
import com.java1234.model.PageBean;
import com.java1234.util.DateUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.StringUtil;

public class DiaryDao {
	/**
	 * 查询所有日记
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Diary> diaryList(Connection con,PageBean pageBean,Diary s_diary )throws Exception{
		List<Diary> diaryList = new ArrayList<>();
		StringBuffer sb = new StringBuffer("select * from t_diary t1,t_diaryType t2 where t1.typeId = t2.diaryTypeId");
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
								 				
		if(StringUtil.isNotEmpty(s_diary.getReleaseDatestr())){
			sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDatestr()+"'");
		}
		sb.append( " order by t1.releaseDate desc");
		if(pageBean != null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
			
		}
		
		//System.out.println("sql: "+sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			Diary diary = new Diary();
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			
			diaryList.add(diary);
			
		}
		
		return diaryList;
	}

	/**
	 * 查询总记录数实现分页
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int diaryCount(Connection con,Diary s_diary) throws SQLException{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_diary t1,t_diaryType t2 where t1.typeId = t2.diaryTypeId");
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
								 				
		if(StringUtil.isNotEmpty(s_diary.getReleaseDatestr())){
			sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDatestr()+"'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
		
	}
	
	/**
	 * 根据日期分类Diary
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Diary> diaryCountList(Connection con) throws Exception{
		List<Diary> diaryList = new ArrayList<>();
		String sql = "SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDatestr,COUNT(*) AS diaryCount FROM  t_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月')ORDER BY releaseDate DESC;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			Diary diary = new Diary();
			diary.setReleaseDatestr(rs.getString("releaseDatestr"));
			diary.setDiaryCount(rs.getInt("diaryCount"));
			
			diaryList.add(diary);
			
		}
		return diaryList;
	}
	
	/**
	 * 显示Diary信息
	 * @param con
	 * @param diaryId
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public Diary ShowDiary(Connection con,String diaryId) throws SQLException, ParseException{
		
		String sql = "select * from t_diary t1,t_diaryType t2 where t1.typeId = t2.diaryTypeId and t1.diaryId=?"; 
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diaryId);
		ResultSet rs = pstmt.executeQuery();
		Diary diary = new Diary();
		while(rs.next()){
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setTypeId(rs.getInt("typeId"));
			diary.setTypeName(rs.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
		}																			
		return diary;
		
	}
	
	/**
	 * 添加日记
	 * @param con
	 * @param diary
	 * @return
	 * @throws Exception
	 */
	public  int addDiary(Connection con,Diary diary)throws Exception{
		String sql = "insert into t_diary values(null,?,?,?,now())";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diary.getTitle());
		pstmt.setString(2, diary.getContent());
		pstmt.setInt(3, diary.getTypeId());
		int n = pstmt.executeUpdate();
		return n;
		
	}
	/**
	 * 删除日志
	 * @param con
	 * @param diary
	 * @return
	 * @throws Exception
	 */
	public int deleteDiary(Connection con,Diary diary)throws Exception{
		String sql = "delete from t_diary where diaryId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, diary.getDiaryId());
		int n = pstmt.executeUpdate();
		
		return n;
	}
	
	/**
	 * 修改日志
	 */
	public int updateDiary(Connection con,Diary diary) throws Exception{
		String sql = "update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, diary.getTitle());
		pstmt.setString(2, diary.getContent());
		pstmt.setInt(3, diary.getTypeId());
		pstmt.setInt(4, diary.getDiaryId());
		return pstmt.executeUpdate();
		
	}
	
	
	public boolean existDiaryFromDiaryTypeId(Connection con,String diaryTypeId)throws Exception{
		String sql = "select * from t_diary where typeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(diaryTypeId));
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()){
			return true;
		}
		return false;
	}
	
}
