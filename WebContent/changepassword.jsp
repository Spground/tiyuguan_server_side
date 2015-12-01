<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
   <title>Bootstrap 实例 - 输入框组的按钮插件</title>
   <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
   <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
   <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
   <script type="text/javascript">
   function Checkout()
   {
	   var password1=document.getElementById("password1").value;
	   var password2=document.getElementById("password2").value;
	   if(password1!=password2)
		   {
		   	 alert("两次输入密码不一致");
		   	document.getElementById("password1").focus();
		   	return false;
		   }
	   else
		   return true;
   }
   </script>
</head>
<body>

<div style="padding: 100px 100px 10px;">
   <form class="bs-example bs-example-form" role="form" method="post"  action="changepasswordaction.action" id="ChangepasswordForm">
      <div class="row">
         <div class="col-lg-6">
            <div class="input-group">
               <span class="input-group-btn">
                  <button class="btn btn-default" type="button">
                                                              输入密码
                  </button>
               </span>
               <input type="password" class="form-control" id="password1" name="newpassword">
            </div><!-- /input-group -->
         </div><!-- /.col-lg-6 --><br>
         <div class="col-lg-6">
            <div class="input-group">
            <span class="input-group-btn">
                  <button class="btn btn-default" type="button">
                                                                再次输入密码
                  </button>
               </span>
               <input type="password" class="form-control" id="password2">
            </div><!-- /input-group -->
         </div><!-- /.col-lg-6 -->
      <div class="col-sm-offset-2 col-sm-10">
         <button class="btn btn-lg btn-primary btn-block" type="submit" onClick="Checkout()">确定</button>
      </div>
      </div><!-- /.row -->
   </form>
</div>
</body>
</html>			