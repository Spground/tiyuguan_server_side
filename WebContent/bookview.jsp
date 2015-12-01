<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>预约</title>
	<link rel="stylesheet" href="css/base.css"/>
	<link rel="stylesheet" href="css/floor.css"/>
	<link rel="stylesheet" href="css/bookview.css"/>
	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/bookview.js"></script>
</head>
<body>
	<div>
		<jsp:include page="nav.jsp"/>
		<div>
			<div class="choose" id="sports">
				<div>运动项目选择</div>
				<ul>
					<li><a>篮球</a></li>
					<li><a>羽毛球</a></li>
				</ul>
			</div>
			<div class="choose">
				<div>日期选择</div>
				<ul>
					<li><a class="ckRadio2"></a></li>
					<li><a class="ckRadio2"></a></li>
					<li><a class="ckRadio2"></a></li>
					<li><a class="ckRadio2"></a></li>
				</ul>
				<font color="red">注：只显示当日及后三日场地情况。</font>
			</div>
			<div class="search">
				<strong>搜索结果：</strong>
			</div>
			<div class="notice">
				注：<div class="occupied">&nbsp;&nbsp;</div>为已被占用场地
				<div class="outtime">&nbsp;&nbsp;</div>为已过期场地
				<div class="useable">&nbsp;&nbsp;</div>为可预约场地
			</div>
			<table >
			<tbody id="resourceTable">
				<tr>
					<th></th>
					<th>羽毛球馆1</th>
					<th>羽毛球馆2</th>
					<th>羽毛球馆3</th>
					<th>羽毛球馆4</th>
					<th>羽毛球馆5</th>
					<th>羽毛球馆6</th>
					<th>羽毛球馆7</th>
					<th>羽毛球馆8</th>
					<th>羽毛球馆9</th>
				</tr>
				<tr>
					<th>9:00-10:00</th>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="occupied"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
				</tr>
				<tr>
					<th>10:00-11:00</th>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="occupied"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
					<td class="outtime"></td>
				</tr>
				<tr>
					<th>11:00-12:00</th>
					<td class="useable"></td>
					<td class="useable"></td>
					<td class="useable"></td>
					<td class="useable"></td>
					<td class="useable"></td>
					<td class="useable"></td>
					<td class="useable"></td>
					<td class="occupied"></td>
					<td class="occupied"></td>
				</tr>
			</tbody>
		</table>
		<div class="btn-area">
			<input type="button" name="btn-reserve" value="预约场地"/>
			<input type="button" name="btn-my" value="查看我的预约"/>
		</div>
		<div class="reserveResult">
		</div>
		</div>
	</div>
	<div id="bottom">
		Copyright © 2015 - 2016 DutGym.All Rights Reserved.<br>
		DUT体育馆创新项目团队 版权所有
	</div>
</body>
</html>