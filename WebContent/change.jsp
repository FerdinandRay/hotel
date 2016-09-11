<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码修改</title>
<style type="text/css">
body{
	color : #000;
	font-size : 14px;
	margin : 20px auto;
}
</style>

</head>

<body>
	<form action="userServlet?method=3&uid=<%=session.getAttribute("user_id") %>" method="post" name="passwdForm">
		<table border="1" cellspacing="0" cellpadding="5" bordercolor="silver" align="center">
			<tr>
				<td colspan="2" align="center" bgcolor="#E8E8E8">密码修改</td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="user_account" value="<%=session.getAttribute("user_account") %>" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>旧密码：</td>
				<td><input type="password" name="oldPassWord" /></td>
			</tr>
			<tr>
				<td>新密码：</td>
				<td><input type="password" name="newPassWord" /></td>
			</tr>
			<tr>
				<td>确认新密码：</td>
				<td><input type="password" name="confirmNewPassWord" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<font color="red">${error }</font>
					<input type="submit" name="submit" value="修改" /> 
					<input type="reset" name="reset" value="重置" />
				</td>
			</tr>
		</table>

	</form>
</body>
</html>