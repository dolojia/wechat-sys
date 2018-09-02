
function before(){
	var flag = true;
	if($("#msgName").val()==''){
		$.messager.alert('提示',"必须填写消息名称",'info');
		 flag =false;
	}else
	if($("#content").val()==''){
		$.messager.alert('提示',"必须填写消息内容",'info');
		flag =false;
	}
	return flag;
	
}

function control_message(type,status){
	var id =$("#subId").val();
	if(type=='submit'){
		if('未提交'!=status){
			alert("只能提交未提交状态的消息");
			return false;
		}
		$.ajax({
			  type: 'POST',
			  url: "update_message_status.json",
			  data: {id:id,type:"submit"},
			  success: function(data){
				  if (!data.errorCode) {
						window.location.href="jump_res_message_list.xhtml";
				  }else{
					  alert(data.errorMsg);
					  return;
				  }
			  },
			  dataType: 'json'
			}); 
		
}else if(type=='verify_yes'){
	if('待审核'!=status){
		alert("只能审核待审核状态的消息");
		return false;
	}
	$.ajax({
		  type: 'POST',
		  url: "control_text_message.json",
		  data: {id:id,type:"verify_yes"},
		  success: function(data){
			  if (!data.errorCode) {
					window.location.href="jump_res_message_list.xhtml";
			  }else{
				  alert(data.errorMsg);
				  return;
			  }
		  },
		  dataType: 'json'
		}); 
		
}else if(type=='verify_no'){
	if('待审核'!=status){
		alert("只能审核待审核状态的消息");
		return false;
	}
	$.ajax({
		  type: 'POST',
		  url: "control_text_message.json",
		  data: {id:id,type:"verify_no"},
		  success: function(data){
			  if (!data.errorCode) {
					window.location.href="jump_res_message_list.xhtml";
			  }else{
				  alert(data.errorMsg);
				  return;
			  }
		  },
		  dataType: 'json'
		}); 
}
}
