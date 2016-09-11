<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>

<style type="text/css">
body{
	color : #000;
	font-size : 14px;
	margin : 20px auto;
}
</style>

<script type="text/javascript">
	function check(form){
		if(document.forms.registerForm.user_account.value==""){
			alert("请输入用户名：");
			document.forms.registerForm.user_account.focus();
			return false;
		}
		if(document.forms.registerForm.user_password.value==""){
			alert("请输入密码：");
			document.forms.registerForm.user_password.focus();
			return false;
		}
		if(document.forms.registerForm.confirmPassWord.value==""){
			alert("请再次输入密码：");
			document.forms.registerForm.confirmPassWord.focus();
			return false;
		}
		if(document.forms.registerForm.user_phone.length > 11){
			alert("手机号不能大于11位");
			document.forms.registerForm.user_phone.focus();
			return false;
		}
	}
</script>

</head>
<body>

	<form action="userServlet?method=2" method="post" name="registerForm">
		<table border="1" cellspacing="0" cellpadding="5" bordercolor="silver" align="center">
			<tr>
				<td colspan="2" align="center" bgcolor="#E8E8E8">用户注册</td>
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
				<td>确认密码：</td>
				<td><input type="password" name="confirmPassWord" /></td>
			</tr>
			<tr>
				<td>姓名</td>
				<td><input type="text" name="user_name" /></td>
			</tr>
			<tr>
				<td>性别</td>
				<td>男<input type="radio" checked="checked" name="user_sex" value="男"/>&nbsp;女<input type="radio" name="user_sex" value="女"/></td>
			</tr>
			<tr>
				<td>手机号</td>
				<td><input type="text" name="user_phone" /></td>
			</tr>
			<tr>
				<td align="center"><a href="index.jsp">返回</a></td>
				<td colspan="2" align="center">
					<font color="red">${error }</font>
					<input type="submit" name="submit" value="注册" onclick="return check(this);" /> 
					<input type="reset" name="reset" value="重置" />
				</td>
			</tr>
		</table>

	</form>


</body>
</html>