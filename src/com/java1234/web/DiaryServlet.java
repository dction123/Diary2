package com.java1234.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.DiaryDao;
import com.java1234.model.Diary;
import com.java1234.util.DbUtil;
import com.java1234.util.StringUtil;

public class DiaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	DiaryDao diaryDao = new DiaryDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String diaryId = request.getParameter("diaryId");
		if("show".equals(action)){
			diaryShow(request, response);
			
		}else if("preSave".equals(action)){
			diaryPreSave(request, response);
		}else if("save".equals(action)){
			if(StringUtil.isNotEmpty(diaryId)){
				diaryUpdate(request, response);
			}else{
				diarySave(request, response);
			}
			
		}else if("delete".equals(action)){
			diaryDelete(request, response);
		}
	
	}

	
	/**
	 * 修改日志
	 * @param request
	 * @param response
	 */
	private void diaryUpdate(HttpServletRequest request, HttpServletResponse response) {
		String diaryId = request.getParameter("diaryId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//System.out.println("content"+content);
		String typeId = request.getParameter("typeId");
		Diary diary = new Diary(Integer.parseInt(diaryId),title,content,Integer.parseInt(typeId));
		Connection con = null;
		try{
			
			con = dbUtil.getCon();
			int saveNum = diaryDao.updateDiary(con, diary);
			if (saveNum>0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
				
			}else{
				request.setAttribute("diary", diary);
				request.setAttribute("error", "修改失败");
				request.setAttribute("mainPage", "diary/diaryPreSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除日记
	 * @param request
	 * @param response
	 */
	
	private void diaryDelete(HttpServletRequest request, HttpServletResponse response) {
		String diaryId = request.getParameter("diaryId");
		response.setCharacterEncoding("utf-8");
		Connection con = null;
		try {
		
			con = dbUtil.getCon();
			Diary diary = new Diary();
			diary.setDiaryId(Integer.parseInt(diaryId));
			int resultNum = diaryDao.deleteDiary(con, diary);
			if(resultNum>0){
				request.getRequestDispatcher("main?action=true").forward(request, response);
			}else{
				PrintWriter out = response.getWriter();
				out.println("<script>alert('删除失败，请联系管理员')</script>");
				out.println("请求错误，请正确请求");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		
	}

	/**
	 * 保存日记
	 * @param request
	 * @param response
	 */
	private void diarySave(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//System.out.println("content"+content);
		String typeId = request.getParameter("typeId");
		Diary diary = new Diary(title,content,Integer.parseInt(typeId));
		Connection con = null;
		try{
			
			con = dbUtil.getCon();
			int saveNum = diaryDao.addDiary(con, diary);
			if (saveNum>0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
				
			}else{
				request.setAttribute("diary", diary);
				request.setAttribute("error", "保存失败");
				request.setAttribute("mainPage", "diary/diaryPreSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 写日志
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void diaryPreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		if(StringUtil.isNotEmpty(diaryId)){
			//执行修改操作
			Connection con = null;
			try {
				con = dbUtil.getCon();
				Diary diary = diaryDao.ShowDiary(con, diaryId);
				request.setAttribute("diary",diary);

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		request.setAttribute("mainPage", "diary/diaryPreSave.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		
		
	}

	/**
	 * 显示具体Diary
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void diaryShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		
		Connection con = null;
		try {
			con = dbUtil.getCon();
			Diary diary = diaryDao.ShowDiary(con, diaryId);
			request.setAttribute("diary",diary);
			request.setAttribute("mainPage", "diary/diaryShow.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
