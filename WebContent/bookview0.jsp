<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.dlut.cx.service.*,
			java.util.Map,
			java.util.Set,
			java.util.ArrayList,
			java.util.Iterator,
			org.apache.struts2.ServletActionContext"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./dist/css/bootstrap.min.css" rel="stylesheet">
<link href="./dist/css/bootstrap-theme.css" rel="stylesheet">
<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./dist/js/bootstrap.min.js"></script>

<style type="text/css">
	.sub-content-left {
		width: 968px;
		background: #fff;
		margin-left: auto;
		margin-right: auto;
		border: 1px solid #ddd;
	}
	
	.position-bar {
		height: 30px;
		line-height: 30px;
		padding-left: 15px;
		background: url(./images/title-bg3.png) repeat-x;
		border-bottom: 1px solid #ccc;
	}
	
	#resourceTable {
		border-collapse: collapse;
		border-style: solid;
		border-width: 3px;
		vertical-align: middle;
		font-size: 12px;
		Table-layout: fixed;
	}
	
	#resourceTable th {
		text-align: center;
		padding: 0 0 0 0;
		border-style: solid;
		border-width: 2px;
		line-height: 20px;
		word-wrap: break-word;
		width: 6%;
	}
	
	#resourceTable td {
		text-align: center;
		padding: 0 0 0 0;
		border-style: solid;
		border-width: 2px;
		line-height: 20px;
		word-wrap: break-word;
	}
		
	tbody {
		display: table-row-group;
		vertical-align: middle;
		border-color: inherit;
	}
	
	.alert {
		padding: 8px 35px 8px 14px;
		margin-bottom: 20px;
		text-shadow: 0 1px 0 rgba(255,255,255,0.5);
		background-color: #fcf8e3;
		border: 1px solid #fbeed5;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
		border-radius: 4px;
	}
</style>

<script type="text/javascript">

	Date.prototype.format = function (fmt) { //author: meizz 
		var o = {
			"M+": this.getMonth()+1, //月份 
			"d+": this.getDate(), //日 
			"h+": this.getHours(), //小时 
			"m+": this.getMinutes(), //分 
			"s+": this.getSeconds(), //秒 
			"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
			"S": this.getMilliseconds() //毫秒 
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}

	//var sport_id = $("[name='ckRadio1']")[0].attr("sportsId");
	//var date = $("[name='ckRadio2']")[0].attr("date");
	
	function findSports(clkobj){
		$(".venues_item").show();
		$("[name='ckRadio1']").removeClass("btn-info");
		$(clkobj).addClass("btn-info");
		
		$(".venues_item").each(function(){
			var sport_id = $(this).attr("sport_id");
			if(sport_id != $(clkobj).attr("sportsId")){
				$(this).hide();
			}
		});
		
		submitQuery();
	}
	
	function findDate(clkobj){
		$("[name='ckRadio2']").removeClass("btn-info");
		$(clkobj).addClass("btn-info");
		
		submitQuery();
	}
	
	function findVenues(clkobj, sport_id){
		$(".venues_item").each(function(){
			if($(this).attr("sport_id") == sport_id){
				$(this).removeClass("btn-info");
			}
		});
		
		$(clkobj).addClass("btn-info");
		
		submitQuery();
	}
	
	function submitQuery(){
		var date;
		$("[name='ckRadio2']").each(function(){
			if($(this).hasClass("btn-info")){
				date = $(this).attr("date");
			}
		});
		var sport_id;
		$("[name='ckRadio1']").each(function(){
			if($(this).hasClass("btn-info")){
				sport_id = $(this).attr("sportsId");
			}
		});
		var venues_id;
		$(".venues_item").each(function(){
			if($(this).hasClass("btn-info") && $(this).attr("sport_id")==sport_id){
				venues_id = $(this).attr("venues_id");
			}
		});
		console.log("venues_id:"+venues_id);
		
		$("#resourceTable").html("");
		
//		alert(date +" "+ venues_id);
		$.post("orderdetail.action", {"dataType":"json", "venues_id":1, "queryday":"2014-12-26"} ,function(data, status){
//alert(data);
			var orderdetail_json = JSON.parse(data);
			var orderdetail = eval(orderdetail_json);
			var venues_num = orderdetail["num"];
			var v_name = orderdetail["v_name"];
			var order_times = orderdetail["order_times"];
			var all_times = orderdetail["all_times"];
			var order_locations = orderdetail["order_locations"];
			
			var num = parseInt(venues_num);
			
			var template = " onmouseover="+"\""+"this.style.cssText='cursor:pointer;background-color:#00FFFF'"+"\"" +" onmouseout="+"\""+"this.style.cssText='cursor:pointer'"+"\""+" onmousedown="+"\""+"this.style.cssText='cursor:progress;background-color:blue'"+"\""+" style="+"\""+"cursor: pointer;"+"\"";
			var tablerow = "<tr><th"+template+"></th>";
			for(var i=1;i<=num;i++){
				tablerow += "<th onmouseup="+"\""+"this.style.cssText='cursor:pointer;background-color:#00FFFF'"+"\" " + template +">" + v_name + i + "</th>";
			}
			tablerow += "</tr>";
			$("#resourceTable").append(tablerow);
			
			tablerow = "";
			$(all_times).each(function(i){
				var time1 = parseInt(all_times[i]);
				var time2 = parseInt(time1+1);
				var show_time = time1 + ":00-" + time2+":00";
				tablerow += "<tr><th class="+"\""+"datetime" +"\""+
					"onmouseup="+"\""+"this.style.cssText='cursor:pointer;background-color:#00FFFF'" +"\""+
					"onmouseover="+"\""+"this.style.cssText='cursor:pointer;background-color:#00FFFF'" +"\""+
					"onmouseout="+"\""+"this.style.cssText='cursor:pointer'" +"\""+
					"onmousedown="+"\""+"this.style.cssText='cursor:pointer;background-color:blue'" +"\""+
					"style="+"\""+"cursor: pointer;"+"\""+">"+show_time+"</th>";
				
				for(var i=1;i<=num;i++){
					tablerow += "<td class="+"\""+"resourceTd"+"\""+" id="+"\""+"resourceTd_5307422"+"\""+" style="+"\""+"background-color:white"+"\""+">&nbsp;"+							
							"</td>";
				}
				tablerow += "</tr>";
				$("#resourceTable").append(tablerow);
				tablerow = "";
			});
			
			
			//for(var i=parseInt(all_times[0]);i<parseInt((new Date()).getHours());i++){
			//	$("#resourceTable").find("tr").eq(i).css("background-color", "yellow");
			//	alert(i);
			//}
			var first_row = parseInt(all_times[0]);
			var end_row = parseInt((new Date()).getHours());
			var tr = $("#resourceTable tr");
			tr.each(function(i){
				if(i+first_row <= end_row){
					$(this).find("td").css("background-color", "gray");
				}
			});
			
			$(order_times).each(function(i){
				var row = parseInt(order_times[i]) - parseInt(all_times[0]) + 1;
				var col = parseInt(order_locations) - 1;
//alert(row+" "+col);
				console.log("row:"+row+",col:"+col);
				$("#resourceTable tr").eq(row).find("td").eq(col).css("background-color", "yellow");
			});			
			
		});
	}
	
	function init(){
		$("[name='ckRadio1']:first").addClass("btn-info");
		//根据运动项目选择初始化搜索结果
		var init_sport_is;
		$("[name='ckRadio1']").each(function(){
			if($(this).hasClass("btn-info")){
				init_sport_is = $(this).attr("sportsId");
			}
		});
		$(".venues_item").each(function(){
			if($(this).attr("sport_id")!=init_sport_is){
				$(this).hide();
			}
		});
		console.log("init_sport_is:"+init_sport_is);
		
		//初始化日期
		var today = new Date();
		$("[name='ckRadio2']").each(function(i){
			var newdate = new Date();
			newdate.setDate(today.getDate() + i)
			var day = newdate.format("yyyy-MM-dd");
			$(this).attr({"date":day});
			$(this).html(day);
		});
	}

	$(function(){
		init();
		console.log("init execute");
		submitQuery();
	});
</script>

</head>
<body>
	<div class="container">
		<jsp:include page="navtop.jsp"></jsp:include>
		<div class="sub-content-left clear" style="margin-top:20px;">
			<div class="position-bar">当前位置：场地预约</div>
			<div class="wz_banner">
				<table style="margin-top:20px;">
					<tr>
						<th><b>运动项目选择 </b></th>
						<td>
							<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;"name="ckRadio1" sportsId="0" onclick="findSports(this);">不限</a>&nbsp;&nbsp;
							<a class="btn-info"	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findSports(this)" name="ckRadio1" sportsId="1">篮球</a>&nbsp;&nbsp;
							<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findSports(this)" name="ckRadio1" sportsId="2">羽毛球</a>&nbsp;&nbsp;
							<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findSports(this)" name="ckRadio1" sportsId="3">乒乓球</a>&nbsp;&nbsp;
							
							<!--<s:iterator value="sports" id="Sport">
								<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findSports(this)" name="ckRadio1" sportsId="<s:property value="id" />"><s:property value="name" /></a>&nbsp;&nbsp;
							</s:iterator>-->
						</td>
					</tr>
					<tr>
						<th><b>日期选择 </b></th>
						<td>
							<a class="btn-info"	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findDate(this)" name="ckRadio2" date="2014-11-17">2014-11-17</a>&nbsp;&nbsp;
							<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findDate(this)" name="ckRadio2" date="2014-11-18">2014-11-18</a>&nbsp;&nbsp;
							<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findDate(this)" name="ckRadio2" date="2014-11-19">2014-11-19</a>&nbsp;&nbsp;
							<a class=""	style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findDate(this)" name="ckRadio2" date="2014-11-20">2014-11-20</a>&nbsp;&nbsp;
						</td>
						<td><font color="red">注：只显示当日及后三日场地情况。</font></td>
					</tr>
				</table>
				
				<div style="height:3px;background-color:#87118B;margin-top:4px;margin-botton:20px"></div>
				
				<div class="alert search" style="margin-top:4px">
					<strong>搜索结果：</strong>
					<s:iterator value="v_map" id="column">
						<s:iterator value="#column.value" status="venues">
							<s:if test="#venues.first">
								<a style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findVenues(this ,<s:property value="#column.key"/>)" class="venues_item btn-info" venues_id="<s:property value="id"/>" sport_id="<s:property value="#column.key"/>"><s:property value="v_name"/>&nbsp;</a>
								<!--span><s:property value="id"/></span-->
							</s:if>
							<s:else>
								<a style="cursor: pointer; margin: 0 auto; text-align: center;" onclick="findVenues(this ,<s:property value="#column.key"/>)" class="venues_item" venues_id="<s:property value="id"/>" sport_id="<s:property value="#column.key"/>"><s:property value="v_name"/>&nbsp;</a>
							</s:else>
						</s:iterator>
					</s:iterator>
				</div>

				<div id="bookInfo">
					<div style="margin-top:10px;margin-bottom:20px">
						注：<span style="border: thin solid black; background: yellow">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;为已被占用场地&nbsp;&nbsp;
						<span style="border: thin solid black; background: grey">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;为已过期场地&nbsp;&nbsp;
						<span style="border: thin solid black; background: white">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;为可预约场地
					</div>
					<div id="bookTableDiv">
						<table id="resourceTable" style="font-size: 10px; text-align: center" class="table">
							<tbody>
								
							</tbody>
						</table>
					</div>
					<div
						style="text-align: center; margin-left: auto; margin-right: auto; margin-top: 10px;"
						class="yuyueButton">
						<a class="btn btn-large btn-success" href="javascript:viewBook()">
							预约场地</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>