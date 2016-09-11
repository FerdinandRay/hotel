<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>等级管理</title>
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
	
	function delLevel() {
		var selectedRows = $("#dg").datagrid('getSelected');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		
		var ids = selectedRows.level_id;
		$.messager.confirm("系统提示", "您确认要删掉这条数据吗？", function(r) {
			if (r) {
				$.post("levelServlet?method=4", {
					delIds : ids
				}, function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "您已成功删除这条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}
	
	// 打开添加窗口
	function addLevel() {
		$('#dlg').dialog("open").dialog("setTitle", "添加等级信息");
		url="levelServlet?method=2";
	}
	// 保存按钮，真实执行操作
	function saveLevel(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	// 修改
	function modifyLevel(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑等级信息");
		$("#fm").form("load",row);
		url="levelServlet?method=3&level_id="+row.level_id;
	}
	
	// 关闭按钮
	function closeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	// 重置文本
	function resetValue(){
		$("#level_name").val("");
		$("#level_points").val("");
	}
</script>
</head>
<body>
	<table id="dg" title="等级管理" class="easyui-datagrid" fitColumns="true" pagination="true" singleSelect="true" rownumbers="true" url="levelServlet?method=1" toolbar="#tb">
		<thead>
			<tr>
				<th field="level_id" width="50%" align="center">等级ID</th>
				<th field="level_name" width="50%" align="center">等级名称</th>
				<th field="level_points" width="50%" align="center">等级最低积分</th>
				<th field="level_rate" width="50%" align="center">等级打折率</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:addLevel()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:modifyLevel()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:delLevel()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 330px;height: 200px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>等级名称：</td>
					<td><input type="text" name="level_name" id="level_name" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>等级最低积分：</td>
					<td><input type="text" name="level_points" id="level_points" class="easyui-numberspinner" required="true" data-options="min:300,editable:true,increment:50"/></td>
				</tr>
				<tr>
					<td>等级折扣：</td>
					<td><input type="text" name="level_rate" id="level_rate" class="easyui-numberspinner" required="true" data-options="min:10,max:100,editable:true,increment:5,suffix:'%'"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveLevel()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>