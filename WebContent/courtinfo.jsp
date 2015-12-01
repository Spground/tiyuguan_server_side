<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
   <title>场馆信息管理</title>
	<link rel="stylesheet" type="text/css" href="./dist/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="./css/dataTables.tableTools.css">
	<script src="./js/jquery-1.11.1.min.js"></script>
    <script	src="./dist/js/bootstrap.min.js"></script>
	<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>

	<script src="js/courtInfo.js" class="init"></script>
</head>
<body>
<div>
	<jsp:include page="nav.jsp" />
		<ul class="nav nav-tabs">
		<li class="active"><a href="#">Home</a></li>
		<li><a href="courtedit.jsp">修改场馆信息</a></li>
		<li><a href="#" id="addCourt">添加场馆</a></li>
		<li><a href="courtdelete.jsp">删除场馆</a></li>
		<li><a href="record.jsp">场馆预约管理</a></li>
		</ul>
		<div class="container" style="margin-top: 10px">	
	<table  class="table table-striped table-bordered" id="example">
	
</table>
			
		</div>
		<!-- 模态框（Modal） -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">编辑该馆内容</h4>
				</div>
				<form class="bs-example bs-example-form" action="court/courtAdd.action" method="post" role="form">
					<div class="input-group">
						<span class="input-group-addon">运动项目</span> 
						<select class="form-control" id="sportSelect" name="sportId">
	      				</select>
					</div>
					<div class="input-group">
					<span class="input-group-addon">场馆名称</span> <input 
						type="text" class="form-control" placeholder="" id="venuesName" name="venuesName">
					</div>
					<div class="input-group">
						<span class="input-group-addon">开馆时间</span> <input type="text"
							class="form-control" placeholder="" id="openTime" name="openTime">
					</div>
					<div class="input-group">
						<span class="input-group-addon">闭馆时间</span> <input type="text"
							class="form-control" placeholder="" id="closeTime" name="closeTime">
					</div>
					<div class="input-group">
						<span class="input-group-addon">资源数量</span> <input type="text"
							class="form-control" id="locationNum" name="locationNum" placeholder="">
					</div>
					<div class="input-group">
						<span class="input-group-addon">场馆价格</span> <input type="text"
							class="form-control" id="venuesCharge" name="venuesCharge" placeholder="">
					</div>
					<div class="input-group">
						<span class="input-group-addon">是否开放</span> 
						<select class="form-control" id="isOpen" name="isOpen" >
         				<option>开放</option>
         				<option>闭馆</option>
      				</select>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="save" >保存</button>
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
		</div>
		</div>
		<div id="bottom">
			Copyright © 2015 - 2016 DutGym.All Rights Reserved.<br>
			DUT体育馆创新项目团队 版权所有
		</div>
</body>
</html>