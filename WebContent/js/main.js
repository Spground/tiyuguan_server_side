function getInfoData() {
		
	var sportId = $("#sport_select option:selected").val();
	
	$.post("record/getVenuesRestInfo.action", {"dataType":"json", "sportId":sportId}, function(data, status) {
		
		$("#infotable").empty();
		$("#infotable").find("tr:not(:first)").remove();
		
		var venues = data.data.Venues;
		$(venues).each(function(i) {
			var openTime = parseInt(venues[i].openTime.split(":")[0]);
			var closeTime = parseInt(venues[i].closeTime.split(":")[0]);
			var venuesName = venues[i].venuesName;
			var locationNum = venues[i].locationNum;
			var record = venues[i].Record;
			var j = 0;
			for(var time = openTime; time < closeTime; time ++) {
				var count = 0;
				while(j < record.length) {
					var date = new Date(record[j].startTime);
					if(time < date.getHours())
						break;
					
					else if(time == date.getHours()) {
						count ++;
						j ++;
					}
				}
				var rest = locationNum - count;
				
				var showtime = time+":00-"+(time+1)+":00";
				$("#infotable").append("<tr><td>"+venuesName+"</td><td>"+showtime+"</td><td>"+locationNum+"</td><td>"+rest+"&nbsp;<a href='bookview.jsp'"+">预约</a></td></tr>");
			}
		});
				
	});
	
}
	
$(function() {
	getInfoData();
	
	$.post("sport/getSportInfo.action", {"dataType":"json"}, function(data, status){
		var sportjson = data.data.Sport;
		$(sportjson).each(function(i){
			$("#sport_select").append("<option value="+sportjson[i].sportId+">"+sportjson[i].sportName+"</option>");
		});
	});
	
	$("#query-btn").click(function() {
		getInfoData();
	});
});