
function addZhuMenu(){
		clearData();
		$('#addOrUpdate').dialog({
			title: '添加主菜单',    
			width: 450,    
			height: 360,    
			closed: false,    
			href: '',    
			modal: true   
		});
		$('#adminMenuForm').form('load',{
			opearType:'ADD_ZHUMENU'
		});
	}

function menuHandler(item){
	if(item.name=='addMenu'){
		clearData();
		$('#addOrUpdate').dialog({
			title: '添加菜单',    
			width: 450,    
			height: 360,    
			closed: false,    
			href: '',    
			modal: true   
		});
		$('#adminMenuForm').form('load',{
			parentId:menuRow.id,
			opearType:'ADD_MENU'
		});
	}
	else if(item.name=='updateMenu'){
		clearData();
	    $("#menuId").numberbox("disable");
		$('#addOrUpdate').dialog({
			 title: '修改菜单',    
			    width: 450,    
			    height: 360,    
			    closed: false,    
			    href: '',    
			    modal: true   
		});
		$('#adminMenuForm').form('load',{
			menuId:menuRow.id,
			opearType:'UPDATE_MENU',
			menuName:menuRow.name,
			href:menuRow.href,
			parentId:menuRow.parentId,
			order:menuRow.order,
			roles:menuRow.roles,
			description:menuRow.description
			
		});
	}
	//删除菜单
	else if(item.name=='delMenu'){
		var id = menuRow.id;
		var data = "menuId="+id;
		$.ajax({
			type:"POST",
			url:"../wxadmin/existChildrenMenu.json",
			data:data,
			dataType:"json",
			success:function(result){
				if(result.data == 'EXIST'){
					$.messager.alert('提示','该区域存在子菜单，请先删除子菜单');
				}else{
					$.messager.confirm('友情提示', '您确定要删除该菜单?', function(boo){   
		                if (boo){  
							var data = "menuId="+menuRow.id;
							$.ajax({
								type:"POST",
								url:"../wxadmin/deleteAdminMenu.json",
								data:data,
								dataType:"json",
								success:function(result){
									if(result.data =='DEL_SUCCESS'){
										 $('#adminMenu').treegrid('remove',menuRow.id);
										 $.messager.alert('系统提示','删除成功');
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


function clearData(){
	$("#menuId").numberbox("enable");
	$("#menuId").numberbox('setValue','');
	$("#menuName").val("");
	$("#href").val("");
	$('#order').numberbox('setValue','');
	$("#roles").combobox('setValue','');
	$("#description").val("");
}
