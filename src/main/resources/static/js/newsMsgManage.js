/**
 * 用于消息管理页面的js
 */
function addMsg(){
	window.location='to_news_message.xhtml';
}
function editMsg(){
	var rows = $('#tt').datagrid('getChecked');
	var size = rows.length;
	if(size<1){
		alert("请先选择一条记录");
		return;
	}else if(size>1){
		alert("一次只能编辑一条记录");
		$('#tt').datagrid('clearChecked');
		return;
	}
	
		var title = rows[0].id;
		alert(title);
		alert(rows.toString());
		//创建页面进行编辑操作
	
}

function delMsg(){
	var rows = $('#tt').datagrid('getChecked');
	var size = rows.length;
	if(size<1){
		alert("请至少先选择一条记录");
		return;
	}
	var ids =new Array();
	for(i=0;i<size;i++){
		ids[i]=rows[i].articleMsgId;
	}
	var idsStr=ids.toString();
	$.ajax({
		url:'del_msg_manage.json',
		type:'POST',
		data:{ids:idsStr},
		success:function(backData){
			$('#tt').datagrid('clearChecked');
			alert(backData);
			$('#tt').datagrid('load');

		}
		
	});
	
	
}