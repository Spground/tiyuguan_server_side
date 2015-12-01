var courtInfoArray = new Array();
$(function() {
	$("#addCourt").click(function(){ 
		$('#editModal').modal({
	    	keyboard: true
		})
	 });
	$("#save").click(function() {
		$("form").trigger("submit");
	})
	
	$.post("sport/getSportInfo.action", {"dataType":"json"}, function(data, status){
		var sportjson = data.data.Sport;
		$(sportjson).each(function(i){
			$("#sportSelect").append("<option value="+sportjson[i].sportId+">"+sportjson[i].sportName+"</option>");
		});
	});
	
	$.post("court/courtInfo.action", {"dataType":"json"}, function(data, status){
		var infojson = data.data.Venues;
		var i=0;
		$(infojson).each(function(){
			courtInfoArray[i] = new Array();
			courtInfoArray[i].push(this.venuesName);
			courtInfoArray[i].push(this.openTime);
			courtInfoArray[i].push(this.closeTime);
			courtInfoArray[i].push(this.locationNum);
			courtInfoArray[i].push(this.venuesCharge);
			courtInfoArray[i].push(this.isOpen == "1" ? "YES" : "NO");
			i++;
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
						
			"data": courtInfoArray,
			"columns": [
				{ "title": "场馆名称" },
				{ "title": "开馆时间" },
				{ "title": "闭馆时间" },
				{ "title": "价格", "class": "center" },
				{ "title": "数目", "class": "center" },
				{ "title":"是否开放","class":"center"}
			]
		});
	});
});

