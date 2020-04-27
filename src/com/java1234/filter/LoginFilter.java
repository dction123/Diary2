package com.java1234.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter  extends HttpFilter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		Object obj = session.getAttribute("currentUser");
		String path = request.getServletPath();
		//System.out.println(path);
		if(obj == null && path.indexOf("login")<0 && path.indexOf("bootstrap")<0 && path.indexOf("images")<0){
			response.sendRedirect("login.jsp");
			
		}else{
			request.setCharacterEncoding("utf-8");
	        response.setCharacterEncoding("utf-8");
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

}
