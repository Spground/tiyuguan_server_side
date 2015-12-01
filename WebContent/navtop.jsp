<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./dist/css/bootstrap-theme.css" rel="stylesheet">
<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./dist/js/bootstrap.min.js"></script>

<style type="text/css">

#nav-main {
	border-bottom: 3px solid #87118B;
}

ul {
	list-style: none;
}

#nav-main .nav-inner-box {
	width: 960px;
	margin: 0 auto;
	height: 30px;
	line-height: 30px;
}

#nav-main .nav-inner-box li {
	height: 30px;
	line-height: 30px;
	float: left;
	font-size: 16px;
	color: #666;
}

#nav-main .nav-inner-box li a {
	display: block;
	height: 30px;
	line-height: 30px;
	float: left;
	width: 87px;
	text-align: center;
}

#nav-main .nav-inner-box li a.cur {
	background: url(./images/nav-hover.png) no-repeat center bottom;
	color: #fff;
	}
</style>
</head>
<body>
	<div class="container">
		<div id="logo">
			<a href="###"><img alt="dlut" src="./images/dlut.jpg" width="434"
				height="63"></a>
		</div>
		<!--div id="nav-main">
			<nav class="navbar nav-default" role="navigation">
			<div class="container-fluid">
				<ul class="nav navbar-nav">
					<li><a href="index.jsp" class="cur">首页</a></li>
					<li><a href="bookview.jsp">预约</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right ">
					<li><a href="Login.jsp">登录</a></li>
					<li><a href="#">联系我们</a></li>
				</ul>
			</div>
			</nav>
		</div-->
		<div id="nav-main">
			<ul class="nav-inner-box" style="position:relative;width:60%;float:left">
				<li><a href="index.jsp" class="cur">首页</a></li>
				<li><a href="bookview.jsp">预约</a></li>
				<li><a href="courtinfo.jsp">场馆管理</a></li>
			</ul>
			
			<ul class="nav-inner-box" style="position:relative;width:40%;float:right">
					<li><a href="Login.jsp">登录</a></li>
					<li><a href="#">联系我们</a></li>
			</ul>
		</div>
	</div>
</body>
</html>