var ECSINFO = {};
ECSINFO.add ="添加";
ECSINFO.update="修改";
ECSINFO.del="删除";
ECSINFO.view ="查看";

ECSINFO.alert_system_error = function(){
	$.messager.alert("提示","系统异常，请稍后再试");
};
ECSINFO.alert_update_unique = function(){
	 $.messager.alert("提示","只能选择单条记录");
};
ECSINFO.alert_delete_requried = function(){
	 $.messager.alert("提示","至少选择一条记录");
};
ECSINFO.confirm_delete = function(fun){
	$.messager.confirm("友情提示","删除请点击【确定】 否则点击【取消】",function(state){
		if(state){
			fun();
		}
	});
};

/**
 * 显示添加成功
 */
ECSINFO.showAddSuccess = function(){
	
};