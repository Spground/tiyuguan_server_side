package com.dlut.cx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserSql;

/*
 * 该文件主要实现用户登录判断，若未登录则不能访问该网站其他界面
 */

public class Authentictionfilter implements Filter{

	private final String useridCookie="SESSION_LOGIN_USERNAME";
	private final String adminrole="1";
	//private final  String userrole="0";
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		HttpSession session = request.getSession();
		String sessionID = session.getId();
		String userid = (String) session.getAttribute(sessionID);
		//如果userid为空判断是否有cookie,cookie是否正确
		if(userid==null)
		{
			Cookie[] cookies = request.getCookies();
			if(cookies!=null)
			{
				UserSql usersql = new UserSql();
				String cookiestr=null;
				for(Cookie cookie:cookies)
				{
					if(cookie.getName().equalsIgnoreCase(useridCookie))
					{
						cookiestr=cookie.getValue();
					}
				}
				if(cookiestr!=null&&cookiestr!="")
				{
					userid = usersql.getUseridbyCookie(cookiestr);
					String role = usersql.getUseridbyCookie(cookiestr);
					if(userid!=null&&userid!="")
					{
						session.setAttribute(sessionID, userid);
						session.setAttribute(userid, role);
					}
				}
			}
			
		}
		
		//判断发过来url是否需要过滤，过滤时有cookie正确不过滤，有用户名不过滤
		if((request.getRequestURI().indexOf("index.jsp")<0)&&(request.getRequestURI().indexOf("Login.jsp")<0)
			&&(request.getRequestURI().indexOf(".jsp")>=0||request.getRequestURI().indexOf(".action")>=0)
			&&(request.getRequestURI().indexOf("main.jsp")<0)&&(request.getRequestURI().indexOf("login.html")<0))
		{
			if(userid==null||userid.equalsIgnoreCase(""))
			{
				String contextPath=request.getContextPath();
				//response.sendRedirect(contextPath+"/Login.jsp");
				response.sendRedirect(contextPath+"/login.html");
				System.out.println("Afillter 75 contextpath"+contextPath);
				return;
			}
			else
			{
				String role = (String)session.getAttribute(userid);
				if(!role.equalsIgnoreCase(adminrole))
				{
					if(request.getRequestURI().indexOf("court")>=0)
					{
						String contextPath=request.getContextPath();
						//response.sendRedirect(contextPath+"/index.jsp");
						response.sendRedirect(contextPath+"/main.jsp");
						return;
					}
				}
				
			}
		}
		filterChain.doFilter(arg0, arg1);
		return;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
