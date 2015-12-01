<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/bootstrap.min.css"/>
<script src="js/jquery-1.11.1.min.js"></script>
<script src="./dist/js/bootstrap.min.js"></script>
<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="./dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/jquery.dataTables.css">
<title>个人信息</title>
<style type="text/css">
	*{
		font-family:'Microsoft YaHei';
	}
	.item_title{
		font-size: 18px;
	}
	table caption {
		font-size: 20px;
		padding:10px;
	}
	.container{
		padding-top: 10px;
	}
	.div_nav{
		padding-top:20px;
		padding-left: 10px;
	}
</style>
</head>
<body>
	<div class="div_nav">
		<ul class="breadcrumb">
		<li><a href="main.jsp">首页</a></li>
		<li class="active">我的基本信息</li>
	</ul>
	</div>
	<% 
		boolean isLogin = false;
		String userName = (String)session.getAttribute(session.getId());
		isLogin |= (userName != null);
	%>
	<%
		if(isLogin){
	%>
	<div class="container" align="center">
		<div class="table-responsive">
			<table class="table table-hover">
				<caption>个人信息</caption>
				<tbody>
					<tr>
						<td class="item_title">用户ID</td>
						<td><%= userName %></td>
					</tr>
					<tr>
						<td class="item_title">邮箱</td>
						<td>958340585@qq.com</td>
					</tr>
					<tr>
						<td class="item_title">信誉等级</td>
						<td>0</td>
					</tr>
					<tr>
						<td class="item_title">预约总次数</td>
						<td>200</td>
					</tr>
					<tr>
						<td class="item_title">违约次数</td>
						<td>13</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<p><image src="img/myprofile_table.png"></image></p>
	</div>
	<% } else { %>
	<script type="text/javascript">
		alert("请登录！");
	</script>
	<% } %>
</body>
</html>