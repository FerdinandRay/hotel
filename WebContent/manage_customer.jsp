<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户管理</title>
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
			cus_name : $('#search_name').val()
		});
	}
	
	function delCustomer() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].cus_id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示", "您确认要删掉这<font color=red>"
				+ selectedRows.length + "</font>条数据吗？", function(r) {
			if (r) {
				$.post("customerServlet?method=4", {
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
	function addCustomer() {
		$('#dlg').dialog("open").dialog("setTitle", "添加客房类型信息");
		url="customerServlet?method=2";
		initCombobox();
	}
	
	function initCombobox(){
		$('#cus_level_id').combobox({
            url: 'customerServlet?method=5',
            valueField: 'level_id',
            textField: 'level_name',
            editable: false,
        });
	}
	// 保存按钮，真实执行操作
	function saveCustomer(){
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
	function modifyCustomer(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑客房类型信息");
		$("#fm").form("load",row);
		url="customerServlet?method=3&cus_id="+row.cus_id;
	}
	
	// 关闭按钮
	function closeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	// 重置文本
	function resetValue(){
		$("#cus_name").val("");
		$("#cus_sex").val("");
		$("#cus_identity").val("");
		$("#cus_phone").val("");
		$("#cus_points").val("");
		$("#cus_level_id").val("");
	}
</script>
</head>
<body>

	<table id="dg" title="客户管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="customerServlet?method=1" toolbar="#tb">
		<thead>
			<tr>
				<th field="cus_id" width="10%" align="center">客户ID</th>
				<th field="cus_name" width="10%" align="center">客户姓名</th>
				<th field="cus_sex" width="5%" align="center">客户性别</th>
				<th field="cus_identity" width="40%" align="center">客户身份证</th>
				<th field="cus_phone" width="20%" align="center">客户手机</th>
				<th field="cus_points" width="5%" align="center">客户积分</th>
				<th field="cus_level_id" width="10%" align="center" hidden="true">客户等级ID</th>
				<th field="level_name" width="10%" align="center" >客户等级</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:addCustomer()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:modifyCustomer()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:delCustomer()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			&nbsp;姓名：&nbsp;<input type="text" name="search_name" id="search_name" /> <a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 350px;height: 280px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>客户姓名：</td>
					<td><input type="text" name="cus_name" id="cus_name" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>客户性别：</td>
					<td>男<input type="radio" checked="checked" name="cus_sex" value="男"/>&nbsp;女<input type="radio" name="cus_sex" value="女"/></td>
				</tr>
				<tr>
					<td>客户身份证：</td>
					<td><input type="text" name="cus_identity" id="cus_identity" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>客户手机：</td>
					<td><input type="text" name="cus_phone" id="cus_phone" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>客户积分：</td>
					<td><input type="text" name="cus_points" id="cus_points" class="easyui-numberspinner" required="true" data-options="min:0,editable:true,increment:100,value:100"/></td>
				</tr>
				<tr>
					<td>客户等级ID：</td>
					<td><input type="text" name="cus_level_id" id="cus_level_id" class="easyui-combobox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveCustomer()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

</body>
</html>