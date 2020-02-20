package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.DiaryDao;
import com.java1234.dao.DiaryTypeDao;
import com.java1234.model.Diary;
import com.java1234.model.PageBean;
import com.java1234.util.DbUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.StringUtil;

public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	DiaryDao diaryDao = new DiaryDao();
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String page = request.getParameter("page");
		String s_typeId = request.getParameter("s_typeId");
		String s_releaseDatestr = request.getParameter("s_releaseDatestr");
		String all = request.getParameter("all");
		String s_title = request.getParameter("s_title");
		
		Diary diary = new Diary();
		
		if("true".equals(all)){
			if(StringUtil.isNotEmpty(s_title)){
				diary.setTitle(s_title);
			}
			session.removeAttribute("s_releaseDatestr");
			session.removeAttribute("s_typeId");
			session.setAttribute("s_title", s_title);
		}else{
			if(StringUtil.isNotEmpty(s_typeId)){
				diary.setTypeId(Integer.parseInt(s_typeId));
				session.setAttribute("s_typeId", s_typeId);
				session.removeAttribute("s_releaseDatestr");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isNotEmpty(s_releaseDatestr)){
				 //s_releaseDatestr = new String(s_releaseDatestr.getBytes("ISO-8859-1"),"UTF-8");
				diary.setReleaseDatestr(s_releaseDatestr);
				session.setAttribute("s_releaseDatestr", s_releaseDatestr);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isEmpty(s_typeId)){
				Object o = session.getAttribute("s_typeId");
				if(o != null){
					diary.setTypeId(Integer.parseInt((String)o));
				}
			}
			
			if(StringUtil.isEmpty(s_releaseDatestr)){
				Object o = session.getAttribute("s_releaseDatestr");
				if(o != null){
					diary.setReleaseDatestr((String)o);
				}
			}
			
			if(StringUtil.isEmpty(s_title)){
				Object o = session.getAttribute("s_title");
				if(o != null){
					diary.setTitle((String)o);
				}
			}
		}
		
		
		
		
		if(StringUtil.isEmpty(page)){
			page = "1";
		}
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		
		
		try {
			con = dbUtil.getCon();
			List<Diary> diaryList = diaryDao.diaryList(con,pageBean,diary);
			//获取总的记录数
			int total = diaryDao.diaryCount(con,diary);
			//调用生成分页方法
			String pageCode = this.genPageAction(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			
			request.setAttribute("diaryList", diaryList);
			request.setAttribute("pageCode", pageCode);
			session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(con));
			session.setAttribute("diaryCountList", diaryDao.diaryCountList(con));

			request.setAttribute("mainPage", "diary/diaryList.jsp");
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

	/**
	 * 生成分页栏
	 * @param totalNum总记录数
	 * @param currentPage当前页
	 * @param pageSize每页记录数
	 * @return返回分页代码
	 */
	public String genPageAction(int totalNum,int currentPage,int pageSize){
		int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>首页</a></li>");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a hred='#'>上一页</a></li>");

		}else{
			pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");

		}
		
				
		for(int i=currentPage-2;i<=currentPage+2;i++){
			if(i<1||i>totalPage){
				continue;
			}
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
			}
		}
		
		
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a hred='#'>下一页</a></li>");

		}else{
			pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");

		}
		
		
		pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");

		return pageCode.toString();
		
	}
	
}
