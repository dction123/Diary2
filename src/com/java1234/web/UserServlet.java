package com.java1234.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.java1234.dao.UserDao;
import com.java1234.model.User;
import com.java1234.util.DateUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.PropertiesUtil;

public class UserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("preSave".equals(action)){
			userShow(request,response);
		}else if ("save".equals(action)) {
			userSave(request,response);
		}
	}

	
	private void userShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
			request.setAttribute("mainPage", "user/userSave.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		
		
		
	}
	private void userSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
	//上传文件需要用到
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(new ServletRequestContext(request));
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator<FileItem> itr = items.iterator();
		boolean imageChange = false;
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("currentUser");
		while(itr.hasNext()){
			FileItem item = itr.next();
			
			if(item.isFormField()){//获取普通表单
				String fieldName = item.getFieldName();//获取字段名称
				if("nickName".equals(fieldName)){
					user.setNickName(item.getString("UTF-8"));
				}
				if("mood".equals(fieldName)){
					user.setMood(item.getString("UTF-8"));
				}
				
			}else if(!"".equals(item.getName())){//用于上传文件用的获取表单信息
					try {
						imageChange = true;
						String imageName = DateUtil.getCurrentDateStr();//获取用于生成文件名的方法
						user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
						String filePath = PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
						item.write(new File(filePath));//写出文件
					} catch (Exception e) {
						e.printStackTrace();
					}
					
			}
			
		}
		if(!imageChange){
			user.setImageName(user.getImageName());
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int resultNum = userDao.updateUser(con, user);
			if(resultNum > 0){
				session.setAttribute("currentUser", user);
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else{
				session.setAttribute("currentUser", user);
				request.setAttribute("error", "保存失败");
				request.setAttribute("mainPage", "user/userSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
			
			
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
