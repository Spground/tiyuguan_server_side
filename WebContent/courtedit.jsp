<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>场馆信息编辑</title>
<link rel="stylesheet" type="text/css" href="./dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/jquery.dataTables.css">

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
<script type="text/javascript" class="init">
	var table;
	var courtinfoarray = new Array();
	$(function() {	
	//获取当前点击按钮所在行的内容
	$('#example').on( 'click', 'tbody td:not(:first-child)', function (e) {
		var tdobj = $(this);
		var nowTable = document.getElementById("example");
		var cellArray = new Array();
		var str1 = tdobj.text();
		if(str1=="编辑")
		{
			var e = e || window.event; 
			var target = e.target || e.srcElement; 
			if (target.parentNode.tagName.toLowerCase() == "td") { 
				
			//获取行号
			var rowIndex1 = target.parentNode.parentNode.rowIndex; 
			var column = nowTable.rows[rowIndex1].cells.length-1;
	
			for(var i=0;i<column;i++)
			{
				cellArray[i] = nowTable.rows[rowIndex1].cells[i].innerHTML;
			}
			
			//给模态框赋初始值
			$("#venuesName").val(cellArray[0]);//场馆名
			$("#openTime").attr("placeholder",cellArray[1]).val(cellArray[1]);//开馆时间
			$("#closeTime").attr("placeholder",cellArray[2]).val(cellArray[2]);//闭关时间
			$("#locationNum").attr("placeholder",cellArray[3]).val(cellArray[3]);//资源数目
			$("#venuesCharge").attr("placeholder",cellArray[4]).val(cellArray[4]);//价格
			
			//弹出模态框
			$("#myModal").modal({ keyboard:true});
			}
			
		}
	} );
	$.post("court/courtInfo.action", {"dataType":"json"}, function(data, status){
		var infojson = data.data.Venues;
		var i=0;
		$(infojson).each(function(){
			courtinfoarray[i] = new Array();
			courtinfoarray[i].push(this.venuesName);
			courtinfoarray[i].push(this.openTime);
			courtinfoarray[i].push(this.closeTime);
			courtinfoarray[i].push(this.locationNum);
			courtinfoarray[i].push(this.venuesCharge);
			courtinfoarray[i].push(this.isOpen == "1"? "YES" : "NO");
			i++;
		});	
		$('#example').dataTable( {
			"oLanguage": {
		        "sSearch": "搜索:",
		        "sLengthMenu": "每页显示 _MENU_ 条记录",
		            "sZeroRecords": "Nothing found - 没有记录",
		        "sInfo": "显示第  _START_ 条到第  _END_ 条记录,一共  _TOTAL_ 条记录",
		        "sInfoEmpty": "显示0条记录",
		        "oPaginate": {
		            "sPrevious": " 上一页 ",
		            "sNext":     " 下一页 ",
		            }
		        },
			"bStateSave": true,
			"bProcessing":true,
			 "data": courtinfoarray,
	         "columns": [
	             { "title": "场馆名称" },
	             { "title": "开馆时间" },
	             { "title": "闭馆时间" },
	             { "title": "价格", "class": "center" },
	             { "title": "数目", "class": "center" },
	             {"title":"是否开放","class":"center" },
	             {"title":"编辑","class":"center",defaultContent:"<button type=\"button\" class=\"btn btn-info\">编辑</button>", orderable: true}
	         ],
	         order: [ 1, 'asc' ]
		} );
  });
});

	function mysubmit()
	{
		if(!confirm("确定修改吗"))
			return false;
		document.getElementById("editform").submit();
	}
</script>
</head>
<body>
<div>
	<jsp:include page="nav.jsp" />
	<ul class="breadcrumb">
		<li><a href="courtinfo.jsp">首页</a></li>
		<li class="active">场馆信息编辑</li>
	</ul>
	<div class="container" style="margin-top: 10px" align="center">
		<table 
			class="table table-striped table-bordered table-hover dispaly"
			id="example">
			<thead>
				<tr>
					<th>馆名</th>
					<th>开馆时间</th>
					<th>闭馆时间</th>
					<th>资源数目</th>
					<th>价格</th>
					<th width="%18">是否开放</th>
					<th>编辑</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">编辑该馆内容</h4>
				</div>
				<form class="bs-example bs-example-form" action="court/courtEdit.action" method="post" role="form" id="editform">
					<div class="input-group">
					<span class="input-group-addon">场馆名称</span> <input 
						type="text" class="form-control" placeholder="" id="venuesName" readonly="readonly" name="venuesName">
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
							class="form-control" id="locationNum" placeholder="" name="locationNum">
					</div>
					<div class="input-group">
						<span class="input-group-addon">场馆价格</span> <input type="text"
							class="form-control" id="venuesCharge" placeholder="" name="venuesCharge">
					</div>
					<div class="input-group">
						<span class="input-group-addon">是否开放</span> 
						<select class="form-control" id="isOpen" name="isOpen">
         				<option>开放</option>
         				<option>闭馆</option>
      				</select>
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="mysubmit()">提交更改</button>
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