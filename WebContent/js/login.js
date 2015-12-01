$(function(){
	$("input[type=submit]").click(MD5);
})
function MD5(){
	var password;
	var nowtime;
	var newElement = $("<input/>");
	var today=new Date();
	var year=today.getFullYear();
	var month=today.getMonth()+1;
	var day=today.getDate();
	var hour=today.getHours();
	var minute=today.getMinutes();
	var second=today.getSeconds();
	 
	var nowTime=year+""+month+day+hour+minute+second;
	var password = $("#password").val();
	var passwordMd5=sha256_digest(password);
	 
	 //Encode
	$("#password").val(sha256_digest(passwordMd5+nowTime));
	 
	 //Add Time
	newElement.attr("name","nowTime");
	newElement.attr("value",nowTime);
	newElement.attr("type","hidden");
	$("#form").append(newElement);

	return true;
}