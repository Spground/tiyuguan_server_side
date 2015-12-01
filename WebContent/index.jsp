<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"
	import="com.dlut.cx.service.SportService,
			java.util.Map,
			java.util.Set,
			java.util.Iterator"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="./dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./dist/css/bootstrap-theme.css" rel="stylesheet">
<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/json.js"></script>

<style type="text/css">
#wrapper {
	width: 960px;
}

.title-d {
	background: url(./images/title-bg2.png) no-repeat left center;
	color: #fff;
	padding-left: 24px;
	font-size: 16px;
	font-family: "微软雅黑", Arial, Helvetica, sans-serif;
	padding-right: 12px;
	height: 34px;
	line-height: 34px;
}

.more {
	float: right;
	background: url(./images/more.gif) no-repeat;
	display: block;
	width: 33px;
	height: 5px;
	text-indent: -9999em;
	position: relative;
	top: 12px; . f-inner { padding : 12px;
	background: #fff;
	border: 1px solid #ddd;
	border-top: none;
}

.f-inner {
	padding: 12px;
	background: #fff;
	border: 1px solid #ddd;
	border-top: none;
}

.clear {
	zoom: 1;
}

}
</style>

<script type="text/javascript">
	
	function getInfoData(){
		
		var sport_id = $("#sport_select option:selected").val();
	//	alert(sport_id);
		
		$.post("indexinfo.action", {"dataType":"json", "sport_id":sport_id}, function(data, status){
			var infojson = JSON.parse(data);
			var venuesinfos = eval(infojson);
			var queryday = venuesinfos["date"];
			
			$("#infotable").find("tr:not(:first)").remove();
			
			var info = venuesinfos["venues_info"];
			$(info).each(function(i){
				var name = info[i]["venues_name"];
				var sum = info[i]["sum"];
				var order_count = info[i]["order_count"];
				var times = info[i]["times"];
				//alert(times.constructor);
				$(eval(times)).each(function(i){
					var rest = parseInt(sum) - parseInt(order_count[i]);
					//var time = times[i].split(":");
					//var hour = time[0];
					//var min = time[1];
					var showtime = times[i]+":00-"+(parseInt(times[i])+1).toString()+":00";
					$("#infotable").append("<tr><td>"+name+"</td><td>"+showtime+"</td><td>"+sum+"剩"+rest+"<a href="+"\""+"#"+"\""+">预约</a></td></tr>");
					//$("#infotable").append("<tr><td>篮球馆</td><td>14:00-15:00</td><td>10余6 </td></tr>");
				});
				
			});
			
		});
	}
	
	$(document).ready(function() {
		/*$("#query-btn").click(function() {
			$.ajax({
				url : "indexinfo.action",
				type : "POST",
				//data : "ajaxField=" + userName,
				dataType:"json",
				success : function(data, textStatus) {
					alert(data.constructor);
					//var info = eval(data);
					//$(info).each(function(i){
					//	alert(info[i]["name"]);
					//});
				}
			});
		});*/
		getInfoData();
		
		$("#query-btn").click(function() {
			getInfoData();
		});
	});
</script>
</head>
<body>
	<div class="container">
		<jsp:include page="navtop.jsp" />
		<div class="wrapper">
			<div>
				<h1 class="title-d">
					<a href="#" title="更多" class="more">更多</a>场馆通知&nbsp;
				</h1>
			</div>
			<div class="f-inner clear">
				<style>
					.ullist {
						overflow: auto;
					}
					
					.ullist li {
						width: 350px;
						float: left;
						margin-left: 22px;
					}
					
					.ullist lm {
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
						height: 26px;
						line-height: 26px;
						overflow: hidden;
						font-size: 12px;
						border-bottom: 1px dashed #ced8df;
						width: 120px;
						float: left;
					}
					
					.ullist_tz {
						overflow: auto;
					}
					
					.ullist_tz li a {
						font-size: 18px;
					}
					
					div.cg-box a:link {
						text-decoration: underline;
					}
					.list-content li {
						line-height: 29px;
						line-height: 29px;
						background: url(./images/dotted.png) no-repeat 5px center;
						border-bottom: 1px dashed #ddd;
						padding: 0 5px 0 20px;
					}
					.list-content li span.time {
						color: #0E7ECA;
						margin-left: 75px;
					}
					.list-content li span {
						float: right;
					}
				</style>
				<div class="list-content">
					<ul class="ullist-tz">
						<li>
							<span class="time">2014-9-27</span>
							<a href="#">假期开放安排</a>
						</li>
						<li>
							<span class="time">2014-10-30</span>
							<a href="#">羽毛球馆停用通知</a>
						</li>
						<li>
							<span class="time">2014-11-2</span>
							<a href="#">篮球馆恢复使用通知</a>
						</li>
					</ul>
				</div>
			</div>
						
			<div>
				<h1 class="title-d">场地情况</h1>
				<div style="position:relative;width:69%;float:left" class="clear">
					&nbsp; <select name="sport_select" id="sport_select">
						<option value="-1">请选择</option>
						
						<s:iterator value="sports" id="Sport">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator>

					</select>
					&nbsp; <input type="hidden" name="time_date"
						id="time_date" value="2014-11-21" size="10" /> &nbsp;
						
					<button type="button" class="btn btn-default" id="query-btn">查询</button>

				</div>

				<div style="padding-top: 20px">
					<div style="overflow-y: scroll; margin-top: 30px; height: 200px;">
						<style>
							
							#infotable{
								width: 80%;
								border-collapse:collapse;
								border: 1px solid #ddd;
							}
															
							#infotable td{
								padding: 10px 2px; 
								border: 1px solid #ddd;
								font-size: 12px;
							}
							#infotable tr{
								display: table-row;
								vertical-align: inherit;
								border-color: inherit;
							}
							
							#infotable th{
								background: #D4D6BA;
								height: 24px;
								line-height: 24px;
								color: #222;
								text-align:center;
							}
						</style>
						<table id="infotable" class="infotable" align="center">
							<thead align="center">
								<tr>
									<th>2014-11-21</th>
									<th>开放时间</th>
									<th>详情</th>
								</tr>
							</thead>
							<tbody align="center">
								
							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>