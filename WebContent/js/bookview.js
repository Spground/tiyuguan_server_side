$(function(){
	var today = new Date();
	$("input[name = btn-my]").click(function() {
		location.href = "myrecord.jsp";
	});
	$(".ckRadio2:first").addClass("btn-info");
	$(".ckRadio2").each(function(i){
		var newdate = new Date();
		newdate.setDate(today.getDate() + i)
		var day = newdate.format("yyyy-MM-dd");
		$(this).attr("date",day);
		$(this).html(day);
	});
	$(".ckRadio2").click(function(){
		$(".ckRadio2").removeClass("btn-info");
		$(this).addClass("btn-info");
		getVenues();
	});
	
	$.post("sport/getSportInfo.action", {"dataType":"json"}, function(data, status){
		$("#sports ul").empty();
		$("#sports ul").append("<li><a sportId=0 class='ckRadio1'>不限</a></li>");
		var sportJson = data.data.Sport;
		$(sportJson).each(function(i) {
			$("#sports ul").append("<li><a sportId="+sportJson[i].sportId+" class='ckRadio1'>"+sportJson[i].sportName+"</a></li>");
		});
		$(".ckRadio1:first").addClass("btn-info");
		$(".ckRadio1").click(function(){
			$(".ckRadio1").removeClass("btn-info");
			$(this).addClass("btn-info");
			var sportId=$(this).attr("sportId");
			$(".venuesItem").parent().hide().end()
				.removeClass("btn-info")
				.filter(sportId==0?"[sportId!=0]":"[sportId="+sportId+"]").parent().show().end()
				.eq(0).addClass("btn-info");
			getVenues();
		});
		getVenues();
	});
	
	$("[name='btn-reserve']").click(getReserve);
});	

function getReserve(){
	$(".selected").each(function(){
		var time=$(this).parent().find("th").text();
		var location=$(this).prevAll().length;
		var hours=time.split("-")[0].split(":")[0];
		var datetime=new Date($('.ckRadio2.btn-info').attr("date"));
		datetime.setHours(parseInt(hours));
		var startTime=datetime.format("yyyy-MM-dd hh:mm:ss");
		datetime.setHours(parseInt(hours)+1);
		var endTime=datetime.format("yyyy-MM-dd hh:mm:ss");
		var params={
			"dataType":"json",
			"venuesId":$(".venuesItem.btn-info").attr("venuesId"),
			"location":location,
			"startTime":startTime,
			"endTime":endTime
		};
		
		var $td=$(this);
		$.post("reserve/makeReserve.action", params, function(data, status){
			$td.removeClass().addClass(data.message.result=="success"?"occupied":"useable");
			var mesg = data.message.result == "success" ? "预约成功" : "预约失败";
			$(".reserveResult").append("<p>" + mesg + "：" + $(".venuesItem.btn-info").text() + "，场地"+ params.location 
				+"，时间：" + params.startTime +" 到 " + params.endTime + "</p>");
		});
	});
}

function submitQuery(){
	var date=$(".ckRadio2").filter(".btn-info").attr("date");
	
	console.log("Order must be same(attribute class):"+$("[class='ckRadio2 btn-info']").attr("date"));
	console.log($(".ckRadio2.btn-info").attr("date"));
	
	var sportId=$(".ckRadio1.btn-info").attr("sportId");
	var venuesId=$(".venuesItem.btn-info").attr("venuesId");
	
	console.log("venuesId:"+venuesId+",date:"+date);
	
	$.post("venues/getVenuesRecordList.action", {"dataType":"json", "venuesId":venuesId, "queryDay":date} ,function(data, status){
		
		var venues=data.data.Venues;
		
		var locationNum=parseInt(venues.locationNum);
		var venuesName=venues.venuesName;
		var openTime=parseInt(venues.openTime.split(":")[0]);
		var closeTime=parseInt(venues.closeTime.split(":")[0]);
		
		var record=venues.Record;
		
		$("#resourceTable").empty();
		
		var tbRow="<tr><th></th>";
		for(var i=1;i<=locationNum;i++)
			tbRow+="<th>"+venuesName+i+"</th>";
		tbRow+="</tr>";
		$("#resourceTable").append(tbRow);
		
		for(var time = openTime; time < closeTime; time ++){
			var show_time = time + ":00-" + (time+1) + ":00";
			tbRow = "<tr><th>" + show_time + "</th>";
			for(var i = 1;i <= locationNum;i ++)
				tbRow += "<td></td>";
			tbRow += "</tr>";
			$("#resourceTable").append(tbRow);
		}

		var first_row = openTime;
		var today=new Date();
		var end_row = today.getDate()<(new Date(date)).getDate()?first_row-1:parseInt((new Date()).getHours());
		$("#resourceTable tr").each(function(i){
				$(this).find("td").addClass(i+first_row <= end_row?"outtime":"useable");
		});
		
		$(record).each(function(i){
			var row = parseInt(record[i].startTime.split(":")[0]) - openTime + 1;
			var col = parseInt(record[i].location) - 1;
			console.log("row:"+row+",col:"+col);
			$("#resourceTable tr").eq(row).find("td").eq(col).removeClass().addClass("occupied");
		});
		
		$("tr .useable").click(function(){
			$(this).toggleClass("selected");
		});

	});
}

function getVenues(){
	if($(".search").find(".venuesItem")[0]){
		submitQuery();
		console.log($(".search").find(".venuesItem")[0]+"already initialized");
		return;
	}
	$.post("sport/getSportVenues.action", {"dataType":"json"} ,function(data, status){
		var $ul=$("<ul></ul>");
		var sport = data.data.Sport;
		$(sport).each(function(i){
			var sportId=sport[i].sportId;
			var venues=sport[i].Venues;
			$(venues).each(function(k){
				var $li=$("<li><a>"+venues[k].venuesName+"</a></li>")
					.find("a").addClass("venuesItem").attr("sportId",sportId).attr("venuesId",venues[k].venuesId)
					.end();
				$ul.append($li);
			});
		});
		$(".search").addClass("choose").append($ul);
		
		// $(".venues_item:first").addClass("btn-info");
		var sportId=$(".ckRadio1:first").attr("sportId");
		$(".venuesItem").parent().hide().end()
			.removeClass("btn-info")
			.filter(sportId==0?"[sportId!=0]":"[sportId="+sportId+"]").parent().show().end()
			.eq(0).addClass("btn-info")
			.end().end()
			.click(function(){
				$(".venuesItem").removeClass("btn-info");
				$(this).addClass("btn-info");
				submitQuery();
			});
		submitQuery();
	});
}
Date.prototype.format = function (fmt) { 
	var o = {
		"M+": this.getMonth()+1, 
		"d+": this.getDate(), 
		"h+": this.getHours(), 
		"m+": this.getMinutes(), 
		"s+": this.getSeconds(), 
		"q+": Math.floor((this.getMonth() + 3) / 3),  
		"S": this.getMilliseconds() 
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

