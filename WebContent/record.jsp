<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>场馆预约管理</title>
<link rel="stylesheet" type="text/css" href="./dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/jquery.dataTables.css">

<link rel="stylesheet" type="text/css"
	href="./css/dataTables.editor.min.css">

<style type="text/css">
table.dataTable tr td:name {
	text-align: center;
}

table.dataTable tr td:name:before {
	content: "\f096"; /* fa-square-o */
	font-family: FontAwesome;
}

table.dataTable tr.selected td:name:before {
	content: "\f046"; /* fa-check-square-o */
}
</style>
	<script src="./js/jquery-1.11.1.min.js"></script>
    <script src="./dist/js/bootstrap.min.js"></script>
	<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
	<script src="js/record.js"></script>
</head>
<body>
<div>
	<jsp:include page="nav.jsp"/>

	<ul class="breadcrumb">
		<li><a href="courtinfo.jsp">首页</a></li>
		<li class="active">场馆预约管理</li>
	</ul>
	<div class="container" style="margin-top: 10px" align="center">
		<table
			class="table table-striped table-bordered table-hover dispaly"
			id="example">
			<thead>
				<tr>
					<th>场馆名称</th>
					<th>订单号</th>
					<th>用户</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>预约日期</th>
					<th>操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="display:none;" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">删除订单</h4>
				</div>
				<form class="bs-example bs-example-form" action="court/adminCancelReserve.action" method="post" role="form" id="deleteform">
					<div class="input-group">
						<span class="input-group-addon">订单号</span> 
						<input type="text" class="form-control" value="" id="recordId" name="recordId">
					</div>
					<div class="input-group">
						<span class="input-group-addon">用户号</span> 
						<input type="text" class="form-control" value="" id="userId" name="userId">
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" onclick="mysubmit()">删除</button>
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