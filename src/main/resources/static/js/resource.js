$(function(){
	formValidate();
})

function menuHandler(item){
	if(item.name=='addResource'){
		clearData();
		$('#addOrUpdate').dialog({
			 title: '添加资源',    
			    width: 400,    
			    height: 200,    
			    closed: false,    
			    href: '',    
			    modal: true   

		});
		$('#resourceForm').form('load',{
			id:resourceRow.id,
			opeartype:'ADD_RESOURCE'
		});
	}
	else if(item.name=='updateResource'){
		$('#addOrUpdate').dialog({
			 title: '修改资源',    
			    width: 400,    
			    height: 200,    
			    closed: false,    
			    href: '',    
			    modal: true   

		});

		$('#resourceForm').form('load',{
			id:resourceRow.id,
			opeartype:'UPDATE_RESOURCE',
			resourceName:resourceRow.text,
			resourceUrl:resourceRow.resourceUrl,
			resourceIcon:resourceRow.resourceIcon,
			resourceType:resourceRow.resourceType
		});
		
	}
	//删除菜单
	else if(item.name=='delResource'){
		var id = resourceRow.id;
		var data = "id="+id;
		$.ajax({
			type:"POST",
			url:"../wxadmin/existResource.json",
			data:data,
			success:function(data){
				if(data == 'EXIST'){
					$.messager.alert('提示','该区域存在子菜单，请先删除子菜单');
				}else{
					$.messager.confirm('友情提示', '您确定要删除该菜单?', function(boo){   
		                if (boo){  
							var data = "id="+resourceRow.id;
							$.ajax({
								type:"POST",
								url:"../wxadmin/delResource.json",
								data:data,
								success:function(data){
									if(data =='DEL_SUCCESS'){
										 $('#resourceData').treegrid('remove',resourceRow.id);
									}
								},
								error:function (XMLHttpRequest, textStatus, errorThrown){
									$.messager.alert('系统异常','系统异常,请稍后再试');
								}
							});

		                }  
		            });  
				}
			},
			error:function (XMLHttpRequest, textStatus, errorThrown){
				$.messager.alert('系统异常','系统异常,请稍后再试');
			}
		});
	}
}





$(function(){ 
	$('#resourceForm').form({ 
		 onSubmit: function(){//表单提交前验证    
			 var check = formValidate();
		       if(check){
		    	   return true;
		       }else{
		    	   return false;
		       }
		    },    

		success:function(data){ 
			if(data == 'ADD_SUCCESS'){
				$('#resourceData').treegrid('append',{
					parent: resourceRow.id,
					row: {
						text: resourceRow.text,
						resourceUrl: resourceRow.resourceUrl,
						resourceIcon:resourceRow.resourceIcon,
						resourceType:resourceRow.resourceType
					}
				});
				$('#resourceData').treegrid("reload",resourceRow.id);
				$('#resourceData').treegrid('expand',resourceRow.id);//展开父节点
				$.messager.alert('系统提示','保存成功');
				return false;
			}else if(data == 'UPDATE_SUCCESS'){
				$('#resourceData').treegrid('update',{
					id: resourceRow.id,
					row: {
						text: $("#resourceName").val(),
						resourceUrl:$("#resourceUrl").val(),
						resourceIcon:$("#resourceIcon").val(),
						resourceType:$("#resourceType").val()
					}
				});
				$('#resourceData').treegrid("reload",resourceRow.id);
				$('#resourceData').treegrid('expand',resourceRow.id);//展开父节点
				$.messager.alert('系统提示','修改成功');
				return false;
			}else{
				$.messager.alert('系统异常','系统异常,请稍后再试');
			}
			
		} 
	}); 
});  


function clearData(){
	$("#id").val("");
	$("#resourceName").val("");
	$("#resourceUrl").val("");
	$("#resourceIcon").val("");
	$("#resourceType").val("");
}

var flag=false;
function formValidate(){
	var that = this;
	$('#resourceForm').validator({
		theme: 'yellow_right',//主题
		timely:1,//0 || false: 关闭实时验证，将只在提交表单的时候进行验证1 || true: 启用实时验证，在字段失去焦点后验证该字段 2: 启用实时验证，在输入的同时验证该字段
		fields:{//那些字段要加入到规则中
			"resourceName":{
				rule:"required;",
				tip: "资源名不能为空"
			},
		},
		valid: function(form){ //验证成功
			flag=true;
			$('#addOrUpdate').dialog('close');
		},
		invalid: function(form){//验证失败
			flag=false;
		}
		
	});
	return flag;
}