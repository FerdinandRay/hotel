<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入住管理</title>
<%
	// 权限验证
	if (session.getAttribute("currentUser") == null) {
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
			checkin_datetime : $('#search_datetime').val()
		});
	}
	
	function delCheckin() {
		var selectedRows = $("#dg").datagrid('getSelected');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要退房的行！");
			return;
		}
		var ids = selectedRows.checkin_id;
		var room_id = selectedRows.checkin_room_id;
		var cus_id = selectedRows.checkin_cus_id;
		var datetime = selectedRows.checkin_datetime;
		var roomtype_cost = selectedRows.roomtype_cost;
		$.messager.confirm("系统提示", "您确认要<font color=red>退房</font>吗？", 
				function(r) {
					if (r) {
						$.post("checkinServlet?method=4", { 
							delIds : ids,
							checkin_room_id : room_id,
							checkin_cus_id : cus_id,
							checkin_datetime : datetime,
							roomtype_cost : roomtype_cost
						}, 
						function(result) {
							if (result.success) {
								$.messager.alert("系统提示", "您已成功<font color=red>退房</font>");
								$("#dg").datagrid("reload");
							} else {
								$.messager.alert('系统提示', result.errorMsg);
							}
						}, "json");
					}
				});
	}
	
	// 打开添加窗口
	function addCheckin() {
		$('#dlg').dialog("open").dialog("setTitle", "添加客房类型信息");
		url="checkinServlet?method=2";
		initCombox();
	}
	
	function initCombox(){
		bindComboboxCus();
		bindComboboxRoom();
	}
	
	function bindComboboxCus(){
		$('#checkin_cus_id').combobox({
            url: 'checkinServlet?method=5',
            valueField: 'cus_id',
            textField: 'cus_name',
            editable: false,
        });
	}
	
	function bindComboboxRoom(){
		$('#checkin_room_id').combobox({
            url: 'checkinServlet?method=6',
            valueField: 'room_id',
            textField: 'room_number',
            editable: false,
        });
	}
	
	// 保存按钮，真实执行操作
	function saveCheckin(){
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
	function modifyCheckin(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑客房类型信息");
		$("#fm").form("load",row);
		url="checkinServlet?method=3&checkin_id="+row.checkin_id;
	}
	
	// 关闭按钮
	function closeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	// 重置文本
	function resetValue(){
		$("#checkin_cus_id").val("");
		$("#checkin_room_id").val("");
		$("#checkin_datetime").datebox('setValue', '');
		$("#checkin_day").val("");
	}
</script>
</head>
<body>
	<table id="dg" title="客户管理" class="easyui-datagrid" fitColumns="true"
		pagination="true" rownumbers="true" singleSelect="true" url="checkinServlet?method=1"
		toolbar="#tb">
		<thead>
			<tr>
				<th field="checkin_id" width="50" align="center">入住信息ID</th>
				<th field="user_name" width="60" align="center">管理员姓名</th>
				<th field="cus_name" width="50" align="center">入住客户姓名</th>
				<th field="checkin_cus_id" width="50" align="center" hidden="true">入住客户ID</th>
				<th field="room_number" width="100" align="center">入住房间号</th>
				<th field="roomtype_cost" width="50" align="center" >房间花费</th>
				<th field="checkin_room_id" width="100" align="center" hidden="true">入住房间ID</th>
				<th field="checkin_datetime" width="100" align="center">入住时间</th>
				<th field="checkin_day" width="50" align="center">入住天数</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:addCheckin()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:modifyCheckin()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:delCheckin()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">退房</a> 
			&nbsp;入住时间：&nbsp;<input type="text" name="search_datetime" id="search_datetime" /> 
			<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 350px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>管理员ID：</td>
					<td>
						<input type="text" name="checkin_user_id" id="checkin_user_id" class="easyui-validatebox" value="<%=request.getSession().getAttribute("user_id")%>" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>入住客户：</td>
					<td>
						<input name="checkin_cus_id" id="checkin_cus_id" class="easyui-combobox" required="true">
					</td>
				</tr>
				<tr>
					<td>入住房间：</td>
					<td>
						<input name="checkin_room_id" id="checkin_room_id" class="easyui-combobox" required="true">
					</td>
				</tr>
				<tr>
					<td>入住时间：</td>
					<td>
						<input type="text" name="checkin_datetime" id="checkin_datetime" class="easyui-datebox" required="true">
					</td>
				</tr>
				<tr>
					<td>入住天数：</td>
					<td>
						<input name="checkin_day" id="checkin_day" class="easyui-numberspinner" required="true" data-options="min:0,max:100,editable:true">
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:saveCheckin()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>