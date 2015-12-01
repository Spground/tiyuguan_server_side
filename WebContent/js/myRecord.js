var recordInfoArray = new Array();
$(function() {
	$("#cancel").click(function() {
		$("#deleteForm").trigger("submit");
	});
	//获取当前点击按钮所在行的内容
	$('#example').on( 'click', 'tbody td:not(:first-child)', function (e) {
		if($(this).text() == "删除") {
			var e = e || window.event; 
			var target = e.target || e.srcElement; 
			if (target.parentNode.tagName.toLowerCase() == "td") { 
				//给模态框赋初始值
				$("#recordId").val($(this).parent().find("td").eq(1).text());
				$("#userId").val($(this).parent().find("td").eq(2).text());
				//弹出模态框
				$("#myModal").modal({ keyboard:true});
			}
		}
	});

	$("#example tbody tr").dblclick(function(e){
		var index = $(this).context._DT_RowIndex; //行号
	});
	
	getMyRecord();

});
function getMyRecord() {
	$.post("reserve/getUseableReserve.action", {"dataType":"json"}, function(data, status) {
		
		console.table(data.data.Record);
		var record = data.data.Record;
		
		$(record).each(function(i) {
			recordInfoArray[i] = new Array();
			recordInfoArray[i].push(this.venuesName);
			recordInfoArray[i].push(this.recordId);
			recordInfoArray[i].push(this.startTime);
			recordInfoArray[i].push(this.endTime);
			recordInfoArray[i].push(this.recordDate);
		});
		$('#example').dataTable({
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
			"data": recordInfoArray,
			"columns": [
			            { "title": "场馆名称" },
				        { "title": "订单号" },
				        { "title": "开始时间", "class": "center" },
				        { "title": "结束时间", "class": "center" },
				        {"title":"预约日期","class":"center" },
				        {"title":"操作","class":"center",defaultContent:"<button type='button' class='btn btn-info'>删除</button>", orderable: true}
			],
			order: [ 1, 'asc' ],
		});
	});
}
