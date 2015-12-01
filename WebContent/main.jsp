<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" charset="UTF-8"/>
	<title>首页</title>
	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/main.js"></script>
</head>
<body>
	<div>
	<jsp:include page="nav.jsp" />
	<!-- <div class="welcome">游客，您好&nbsp;|&nbsp;<a href='login.html'>登录</a></div> -->
	<span>场馆通知</span>
	<div id="notice">
		<div>
		<ul>
			<li><a href="news/1024.html">假期开放安排</a><span>2014-09-27</span></li>
			<li><a href="#">羽毛球馆停用通知</a><span>2014-10-30</span></li>
			<li><a href="#">篮球馆恢复使用通知</a><span>2014-11-02</span></li>
			<li><a href="#">乒乓球馆开放时间调整通知</a><span>2014-12-30</span></li>
			<li><a href="#">游泳池关闭通知</a><span>2014-12-30</span></li>
		</ul>
		<a href="#">MORE</a>
		</div>
		<img src="img/gym.jpg"/>
	</div>
	<hr>
	<div id="service">
		<img src="img/service.gif"/>
		<div>
		<ul>
			<li><a href="build.html"><img src="img/build.gif"/></a></li>
			<li><a href="#"><img src="img/map.gif"/></a></li>
			<li><a href="#"><img src="img/time.gif"/></a></li>
		</ul>
		<ul>
			<li>楼层布局</li>
			<li>校园地图</li>
			<li>工作时间</li>
		</ul>
		</div>
	</div>
	<hr>
	<span>场馆情况</span>
	<div id="situation">
		<!--<s:action name="indexsport" executeResult="true"></s:action>-->
		<select name="sport_select" id="sport_select">
			<option value="0">请选择</option>
			<!--<s:iterator value="sports" id="Sport">
				<option value="<s:property value="id"/>"><s:property value="name"/></option>
			</s:iterator>-->
		</select>
		<input type="hidden" name="time_date" id="time_date" value="2014-11-21" size="10" />
		<input type="button" id="query-btn" value="查&nbsp;&nbsp;&nbsp;询"/>
		<hr>
		<table>
			<thead>
				<tr>
					<th>场馆</th>
					<th>预约时段</th>
					<th>场地总数</th>
					<th>可预约数</th>
					<!-- <th>日期</th>-->
				</tr>
			</thead>
		</table>
		<div id="tablearea">
		<table id="infotable">
			<tbody>
				<tr>
					<td>羽毛球馆</td>
					<td>8：00-9：00</td>
					<td>32</td>
					<td>32</td>
				</tr>
				<tr>
					<td>羽毛球馆</td>
					<td>8：00-9：00</td>
					<td>32</td>
					<td>32</td>
				</tr>
				<tr>
					<td>羽毛球馆</td>
					<td>8：00-9：00</td>
					<td>32</td>
					<td>32</td>
				</tr>
				<tr>
					<td>羽毛球馆</td>
					<td>8：00-9：00</td>
					<td>32</td>
					<td>32</td>
				</tr>
			</tbody>
		</table>
		</div>
	</div>
	</div>
	<div id="bottom">
		Copyright © 2015 - 2016 DutGym.All Rights Reserved.<br>
		DUT体育馆创新项目团队 版权所有
	</div>
</body>
</html>