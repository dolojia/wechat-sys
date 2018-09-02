/**
 * 用于消息管理页面的js
 */

function addMsg() {
	$("#operType").val("");
	window.location='to_text_message.xhtml?tid';
	$("#title").val('');
	$("#desc").val('');
	$("#content").val('');
	$("#primaryKey").val('');
	$("#operType").val('');
	
}
function editMsg() {
	var rows = $('#tt').datagrid('getChecked');
	var size = rows.length;
	if (size < 1) {
		$.messager.alert('提示',"请先选择一条记录",'info');
		return;
	} else if (size > 1) {
		$.messager.alert('提示',"一次只能编辑一条记录",'info');
		$('#tt').datagrid('clearChecked');
		return;
	}

	window.location='to_text_message.xhtml?tid='+rows[0].id;
}

function delMsg() {
	var rows = $('#tt').datagrid('getChecked');
	var size = rows.length;
	if (size < 1) {
		$.messager.alert('提示',"请至少先选择一条记录",'info');
		return;
	}
	var ids = new Array();
	for (i = 0; i < size; i++) {
		ids[i] = rows[i].articleMsgId;
	}
	var idsStr = ids.toString();
	$.ajax({
		url : 'del_msg_manage.json',
		type : 'POST',
		data : {
			ids : idsStr
		},
		success : function(backData) {
			$('#tt').datagrid('clearChecked');
			$.messager.alert('提示',backData,'info');

			$('#tt').datagrid('load');
		}
	});

}