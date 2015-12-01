<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<script type="text/javascript" charset="utf-8" language="javascript" src="./js/SHA256.js"></script>
    <title>用户登录</title>

    <!-- Bootstrap core CSS -->
    <link href="./dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="./css/login.css" rel="stylesheet">
    
 <script type="text/javascript" class="init">
 function MD5()
{
	 var password;
	 var nowtime;
	 var signinform=document.getElementById("siginForm");
	 var newElement = document.createElement("input");
	 var today=new Date();
	 var year=today.getFullYear();
	 var month=today.getMonth()+1;
	 var day=today.getDate();
	 var hour=today.getHours();
	 var minute=today.getMinutes();
	 var second=today.getSeconds();
	 
	// window.alert(year+""+month+day+hour+minute+second);
	 var nowtime=year+""+month+day+hour+minute+second;
	 var password = document.getElementById("passwordInput").value;
//	 window.alert("password "+password);
	 var passwordMd5=sha256_digest(password);
	// window.alert("passwordMD5 "+passwordMd5);
	 
	 //加密
	 document.getElementById("passwordInput").value=sha256_digest(passwordMd5+nowtime);
	 
	 //添加时间
	 newElement.setAttribute("name","nowtime");
	 newElement.setAttribute("value",nowtime);
	 newElement.style.visibility="hidden";
	 signinform.appendChild(newElement);
	// window.alert(document.getElementById("password").value);
	 return true;
}
</script>
    
  </head>
  <body>
	<div id="fullBg">
    <div class="container input-group-lg">
      <form class="form-signin" role="form" action="login.action" method="post" id="siginForm">
        <h2 class="form-signin-heading">请登录</h2>
        <input type="number" name="userid" class="form-control" placeholder="Email address" id="usernameInput">
        <input type="password" name="password" class="form-control" placeholder="Password" id="passwordInput">
        <div class="checkbox">
          <label>
            <input type="checkbox" name="cookie" value="remember-me"> 记住我
          </label>
          <label>
          	<input type="checkbox" name="identity" value="manager" checked> 管理员
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" onClick="MD5()">登录</button>
      </form>
    </div> <!-- /container -->
    </div>
  </body>
 
</html>
