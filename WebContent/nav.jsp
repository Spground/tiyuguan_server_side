<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/base.css"/>
<link rel="stylesheet" href="css/main.css"/>
<div>
	<img src="img/dlut.jpg" class="logo"/>
</div>
	<div id="nav">
	<ul>
		<li><a href="main.jsp">首页</a></li>
		<li><a href="bookview.jsp">预约</a></li>
		<li><a href="courtinfo.jsp">场馆管理</a></li>
		<li><a href="login.html">登录</a></li>
		<li><a href="aboutus.html">联系我们</a></li>
		<%
			String userName = (String)session.getAttribute(session.getId());
		%>
		<li id="nickname_li">
		<% if(userName != null){
		%> <a href="myprofile.jsp"><%= userName %></a>
		<% }else{ %>
		 	<a href="login.html">游客你好！</a>
			<% } %></li>
	</ul>
</div>