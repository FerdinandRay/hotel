<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退房信息查看</title>
<%
	// 权限验证
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;

	function search() {
		$('#dg').datagrid('load', {
			checkout_datetime : $('#search_datetime').val()
		});
	}
	
	function delCheckout() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].checkout_id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示", "您确认要删掉这<font color=red>"
				+ selectedRows.length + "</font>条数据吗？", function(r) {
			if (r) {
				$.post("checkoutServlet?method=2", {
					delIds : ids
				}, function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "您已成功删除<font color=red>"
								+ result.delNums + "</font>条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}
	
</script>
</head>
<body>
	<table id="dg" title="客户管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="checkoutServlet?method=1" toolbar="#tb">
		<thead>
			<tr>
				<th field="checkout_id" width="10%" align="center">退房信息ID</th>
				<th field="user_name" width="10%" align="center">管理员姓名</th>
				<th field="cus_name" width="10%" align="center">退房客户姓名</th>
				<th field="room_number" width="10%" align="center">退房房间号</th>
				<th field="checkout_datetime" width="20%" align="center">退房时间</th>
				<th field="checkout_pay" width="20%" align="center">应付金额(RMB)</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:delCheckout()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			&nbsp;退房时间：&nbsp;<input type="text" name="search_datetime" id="search_datetime" /> <a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

</body>
</html>