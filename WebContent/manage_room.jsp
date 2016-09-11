<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客房管理</title>
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

	function delRoom() {
		var selectedRows = $("#dg").datagrid('getSelected');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var ids = selectedRows.room_id;
		var roomtypeids = selectedRows.room_type_id;
		$.messager.confirm("系统提示", "您确认要删掉这条数据吗？", function(r) {
			if (r) {
				$.post("roomServlet?method=4", {
					delIds : ids,
					delTypeIds : roomtypeids
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
	
	// 打开添加窗口
	function addRoom() {
		$('#dlg').dialog("open").dialog("setTitle", "添加客房信息");
		url="roomServlet?method=2";
		initCombobox();
	}
	
	function initCombobox(){
		$('#room_type_id').combobox({
            url: 'roomServlet?method=5',
            valueField: 'roomtype_id',
            textField: 'roomtype_name',
            editable: false,
        });
	}
	
	// 保存按钮，真实执行操作
	function saveRoom(){
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
	function modifyRoom(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑客房类型信息");
		$("#fm").form("load",row);
		url="roomServlet?method=3&room_id="+row.room_id;
	}
	
	// 关闭按钮
	function closeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	// 重置文本
	function resetValue(){
		$("#room_number").val("");
		$("#room_type_id").val("");
		$("#room_status").val("");
	}
</script>
</head>
<body>

	<table id="dg" title="客房管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" singleSelect="true" url="roomServlet?method=1" toolbar="#tb">
		<thead>
			<tr>
				<th field="room_id" width="25%" align="center">客房ID</th>
				<th field="room_number" width="25%" align="center">客房号</th>
				<th field="roomtype_name" width="25%" align="center">客房类型</th>
				<th field="room_type_id" hidden=true>客房类型ID</th>
				<th field="room_status" width="25%" align="center">客房状态</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:addRoom()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:modifyRoom()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:delRoom()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 350px;height: 280px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>客房号：</td>
					<td><input type="text" name="room_number" id="room_number" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>客房类型：</td>
					<td><input type="text" name="room_type_id" id="room_type_id" class="easyui-combobox" required="true"/></td>
				</tr>
				<tr>
					<td>客房状态：</td>
					<td>空:<input type="radio" checked="checked" name="room_status" value="空"/>&nbsp;非空:<input type="radio" name="room_status" value="非空"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveRoom()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

</body>
</html>