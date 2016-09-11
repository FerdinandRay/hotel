<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户登录</title>
<style type="text/css">
body{
	color : #000;
	font-size : 14px;
	margin : 20px auto;
}
</style>

<script type="text/javascript">
	function check(form){
		if(document.forms.loginForm.user_account.value==""){
			alert("请输入用户名：");
			document.forms.loginForm.user_account.focus();
			return false;
		}
		if(document.forms.loginForm.user_password.value==""){
			alert("请输入密码：");
			document.forms.loginForm.user_password.focus();
			return false;
		}
	}
</script>
</head>
<body>

	<form action="userServlet?method=1" method="post" name="loginForm">
		<table border="1" cellspacing="0" cellpadding="5" bordercolor="silver" align="center">
			<tr>
				<td colspan="2" align="center" bgcolor="#E8E8E8">用户登陆</td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="user_account" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" name="user_password" /></td>
			</tr>
			<tr>
				<td align="center"><a href="register.jsp">注册</a></td>
				<td colspan="2" align="center">
					<font color="red">${error }</font>
					<input type="submit" name="submit" value="登陆" onclick="return check(this);"/>
					<input type="reset" name="reset" value="重置" />
				</td>
			</tr>
		</table>
	</form>


</body>
</html>