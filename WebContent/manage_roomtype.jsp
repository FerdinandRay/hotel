<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客房类型管理</title>
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

	function delRoomType() {
		var selectedRows = $("#dg").datagrid('getSeleted');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var ids = selectedRows.roomtype_id;
		$.messager.confirm("系统提示", "您确认要删掉这条数据吗？", function(r) {
			if (r) {
				$.post("roomTypeServlet?method=4", {
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
	
	// 打开添加窗口
	function addRoomType() {
		$('#dlg').dialog("open").dialog("setTitle", "添加客房类型信息");
		url="roomTypeServlet?method=2";
	}
	// 保存按钮，真实执行操作
	function saveRoomType(){
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
	function modifyRoomType(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑客房类型信息");
		$("#fm").form("load",row);
		url="roomTypeServlet?method=3&roomtype_id="+row.roomtype_id;
	}
	
	// 关闭按钮
	function closeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	// 重置文本
	function resetValue(){
		$("#roomtype_name").val("");
		$("#roomtype_cost").val("");
		$("#roomtype_deposit").val("");
		$("#roomtype_total").val("");
		$("#roomtype_surplus").val("");
	}
</script>
</head>
<body>

	<table id="dg" title="客房类型管理" class="easyui-datagrid" fitColumns="true" pagination="true" singleSelect="true" rownumbers="true" url="roomTypeServlet?method=1" toolbar="#tb">
		<thead>
			<tr>
				<th field="roomtype_id" width="10%" align="center">客房类型ID</th>
				<th field="roomtype_name" width="20%" align="center">客房类型名称</th>
				<th field="roomtype_cost" width="10%" align="center">客房类型花费</th>
				<th field="roomtype_deposit" width="10%" align="center">客房类型押金</th>
				<th field="roomtype_total" width="10%" align="left">客房类型总量</th>
				<th field="roomtype_surplus" width="10%" align="center">客房类型剩余量</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:addRoomType()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:modifyRoomType()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:delRoomType()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 400px;height: 250px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>客房类型名称：</td>
					<td><input type="text" name="roomtype_name" id="roomtype_name" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>客房类型花费：</td>
					<td><input name="roomtype_cost" id="roomtype_cost" class="easyui-numberspinner" required="true" data-options="min:100,max:9999,editable:true,increment:50,prefix:'$'"/></td>
				</tr>
				<tr>
					<td>客房类型押金：</td>
					<td><input name="roomtype_deposit" id="roomtype_deposit" class="easyui-numberspinner" required="true" data-options="min:100,max:9999,editable:true,increment:50,prefix:'$'"/></td>
				</tr>
				<tr>
					<td>客房类型总量：</td>
					<td><input name="roomtype_total" id="roomtype_total" class="easyui-numberspinner" required="true" data-options="min:0,max:100,editable:true"/></td>
				</tr>
				<tr>
					<td>客房类型剩余量：</td>
					<td><input name="roomtype_surplus" id="roomtype_surplus" class="easyui-numberspinner" required="true" data-options="min:0,max:100,editable:true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveRoomType()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

</body>
</html>