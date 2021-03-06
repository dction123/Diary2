package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.DiaryDao;
import com.java1234.dao.DiaryTypeDao;
import com.java1234.model.Diary;
import com.java1234.model.DiaryType;
import com.java1234.util.DbUtil;
import com.java1234.util.StringUtil;

public class DiaryTypeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();	
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	DiaryDao diaryDao = new DiaryDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("list".equals(action)){
			diaryTypeList(request, response);
		}else if("delete".equals(action)){
			diaryTypeDelete(request, response);
		}else if("preSave".equals(action)){
			diaryTypePreSave(request, response);
		}else if("save".equals(action)){
			diaryTypeSave(request, response);
		}

}
	
	private void diaryTypeSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String diaryTypeId = request.getParameter("diaryTypeId");
		String diaryTypeName = request.getParameter("diaryTypeName");
		System.out.println("diaryTypeName :"+diaryTypeName);
		DiaryType diaryType = null;
		if (StringUtil.isNotEmpty(diaryTypeId)) {
			 diaryType = new DiaryType(Integer.parseInt(diaryTypeId),diaryTypeName);
		}else{
			 diaryType = new DiaryType(diaryTypeName);

		}
		
			Connection con = null;
			try {
				int resultNum = 0;
				con = dbUtil.getCon();
				if(StringUtil.isNotEmpty(diaryTypeId)){
					resultNum = diaryTypeDao.updateDiaryType(con, diaryType);
				}else{
					resultNum = diaryTypeDao.addDiaryType(con, diaryType);
				}
				
				if(resultNum<0){
					request.setAttribute("error", "添加或保存失败");
					request.setAttribute("mainPage", "diaryTypePreSave");
					request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}finally {
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		request.getRequestDispatcher("diaryType?action=list").forward(request, response);
		
	}
	
	
	
	/**
	 * 预处理
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void diaryTypePreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		if(StringUtil.isNotEmpty(diaryTypeId)){
			Connection con = null;
			try {
				con = dbUtil.getCon();
				DiaryType selectdiaryType = diaryTypeDao.selectdiaryType(con, diaryTypeId);
				request.setAttribute("selectdiaryType", selectdiaryType);
			} catch (Exception e) {
				e.printStackTrace();

			}finally {
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
		request.setAttribute("mainPage", "diaryType/diaryTypePreSave.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);;
		
	}

	/**
	 * 删除日志
	 * @param request
	 * @param response
	 */
	private void diaryTypeDelete(HttpServletRequest request, HttpServletResponse response) {
		Connection con = null;
		String diaryTypeId = request.getParameter("diaryTypeId");
		
		try {
			con = dbUtil.getCon();
			//根据diaryTypeId去diary表中查找是否有数据 当该类型下没有数据的时候才能删除 
			
			if(!diaryDao.existDiaryFromDiaryTypeId(con, diaryTypeId)){
				int n= diaryTypeDao.deleteDiaryType(con, diaryTypeId);
				
				if (n>0) {
					request.getRequestDispatcher("diaryType?action=list").forward(request, response);
				}
			}else{
				request.setAttribute("error", "日志类别下有日志，不能删除该类别！");
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);

			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 显示全部diaryType
	 * @param request
	 * @param response
	 */
	private void diaryTypeList(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		try {
			con = dbUtil.getCon();
			List<DiaryType> diaryTypeList = diaryTypeDao.diaryTypeList(con);
			request.setAttribute("diaryTypeList", diaryTypeList);
			
			request.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
}