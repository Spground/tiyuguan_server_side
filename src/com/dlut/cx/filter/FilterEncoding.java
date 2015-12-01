package com.dlut.cx.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class FilterEncoding
 */
@WebFilter("/FilterEncoding")
public class FilterEncoding implements Filter {
	protected String encoding=""; 
	protected FilterConfig filterConfig; 
	public void init(FilterConfig filterConfig) throws ServletException {
	encoding = filterConfig.getInitParameter("encoding");
	System.out.println("FilterEncoding");
	}
	// doFilter
	public void doFilter(ServletRequest request, ServletResponse response,
	FilterChain chain) throws IOException, ServletException {
	if(!encoding.equals("utf-8"))
	{
	encoding="utf-8";
	}
	request.setCharacterEncoding(encoding);
	chain.doFilter(request, response);
	}
	public void destroy() {
	}

}
