function addZhuMenu(){
	clearData();
	var roorList = $("#menuData").treegrid('getRoots');
	var data = "opearType="+"ADD_ZHUMENU";
	$.ajax({
		type:"POST",
		url:"../wxadmin/checkMenuCount.json",
		data:data,
		dataType:"json",
		success:function(result){
			if(result.data =='ROOTS_OUT'){
				 $.messager.alert('系统提示','主菜单个数最多为三个');
			}else{
				$('#addOrUpdate').dialog({
					 title: '添加主菜单',    
					    width: 420,    
					    height: 300,    
					    closed: false,    
					    href: '',    
					    modal: true   
				});
				$('#menuForm').form('load',{
					opearType:'ADD_ZHUMENU'
				});
				$("#radio3").attr("checked","checked");
				document.getElementById("menuKey").style.display='';
				document.getElementById("view").style.display='none';
				document.getElementById("event").style.display='none';
				 $("#eventkey").combobox('setValue','下拉选择');
				document.getElementById("urlKey").value="http://www.xxx.com";
			}
		},
		error:function (XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert('系统异常','系统异常,请稍后再试');
		}
	});
}

function menuHandler(item){
	if(item.name=='verifyMenu'){
		var status = menuRow.status;
		if("未提交"==status){
			$.messager.alert('系统提示','请先提交后再进行审核操作');
		}else{
			$.messager.confirm('友情提示', '是否审核该菜单?', function(boo){   
	            if (boo){  
					var data = "id="+menuRow.id+"&opearType="+"verifyMenu";
					$.ajax({
						type:"POST",
						url:"../wxadmin/verifyMenu.json",
						data:data,
						dataType:"json",
						success:function(result){
							if(result.data =='VERIFY_SUCCESS'){
								 $('#menuData').treegrid('update',{
									 id:menuRow.id,
										row: {
											status:'审核通过',
										}
									});
								 $.messager.alert('系统提示','审核成功');
							}
						},
						error:function (XMLHttpRequest, textStatus, errorThrown){
							$.messager.alert('系统异常','系统异常,请稍后再试');
						}
					});
	            }  
	        }); 
		}
		
	}
	if(item.name=='submitMenu'){
		$.messager.confirm('友情提示', '是否提交该菜单?', function(boo){   
            if (boo){  
				var data = "id="+menuRow.id+"&opearType="+"submitMenu";
				$.ajax({
					type:"POST",
					url:"../wxadmin/verifyMenu.json",
					data:data,
					dataType:"json",
					success:function(result){
						if(result.data =='SUBMIT_SUCCESS'){
							 $('#menuData').treegrid('update',{
								 id:menuRow.id,
									row: {
										status:'待审核',
									}
								});
							 $.messager.alert('系统提示','提交成功');
						}
					},
					error:function (XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert('系统异常','系统异常,请稍后再试');
					}
				});
            }  
        }); 
	}
	if(item.name=='noVerifyMenu'){
		var status = menuRow.status;
		if("未提交"==status){
			$.messager.alert('系统提示','请先提交后再进行不通过审核操作');
		}else{
			$.messager.confirm('友情提示', '是否不通过该菜单审核?', function(boo){   
	            if (boo){  
					var data = "id="+menuRow.id+"&opearType="+"noVerifyMenu";
					$.ajax({
						type:"POST",
						url:"../wxadmin/verifyMenu.json",
						data:data,
						dataType:"json",
						success:function(result){
							if(result.data =='VERIFY_FALSE'){
								 $('#menuData').treegrid('update',{
									 id:menuRow.id,
										row: {
											status:'审核不通过',
										}
									});
								 $.messager.alert('系统提示','操作成功');
							}
						},
						error:function (XMLHttpRequest, textStatus, errorThrown){
							$.messager.alert('系统异常','系统异常,请稍后再试');
						}
					});
	            }  
	        }); 
		}	
		
	}
	if(item.name=='addMenu'){
		clearData();
		var parentId = menuRow.parentId;
		var menuId = menuRow.menuId;
		if(parentId == 0){
			var data = "opearType="+"ADD_MENU"+"&menuId="+menuId;
			$.ajax({
				type:"POST",
				url:"../wxadmin/checkMenuCount.json",
				data:data,
				dataType:"json",
				success:function(result){
					if(result.data =='CHILDS_OUT'){
						 $.messager.alert('系统提示','二级菜单个数最多为五个');
					}else{
						$('#addOrUpdate').dialog({
							 title: '添加菜单',    
							    width: 420,    
							    height: 300,    
							    closed: false,    
							    href: '',    
							    modal: true   
						});
						$('#menuForm').form('load',{
							menuId:menuRow.menuId,
							eventType:menuRow.menuType,
							opearType:'ADD_MENU'
						});
						$("#radio1").attr("checked","checked");
						document.getElementById("view").style.display='';
						document.getElementById("event").style.display='none';	
						 $("#eventkey").combobox('setValue','下拉选择');
						document.getElementById("menuKey").style.display='none';
						document.getElementById("caidanKey").value='menuName';
					}
				},
				error:function (XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('系统异常','系统异常,请稍后再试');
				}
			});
		}else{
			$.messager.alert('系统提示','二级菜单下不能添加子菜单');
		}
	}
	else if(item.name=='updateMenu'){
		clearData();
		$('#addOrUpdate').dialog({
			 title: '修改菜单',    
			    width: 420,    
			    height: 300,    
			    closed: false,    
			    href: '',    
			    modal: true   
		});
		$('#menuForm').form('load',{
			id:menuRow.id,
			menuId:menuRow.menuId,
			opearType:'UPDATE_MENU',
			menuName:menuRow.name,
			eventType:menuRow.menuType,
			urlKey:menuRow.properties,
			eventkey:menuRow.properties,
			caidanKey:menuRow.properties,
			menuOrderNo:menuRow.menuOrder,
		});
		if(menuRow.menuType=='链接事件'){
			$("#radio1").attr("checked","checked");
			document.getElementById("view").style.display='';
			document.getElementById("event").style.display='none';
		     $("#eventkey").combobox('setValue','下拉选择');
		    document.getElementById("menuKey").style.display='none';
			document.getElementById("caidanKey").value='menuName';
		}else if(menuRow.menuType=='点击事件'){
			$("#radio2").attr("checked","checked");
			document.getElementById("view").style.display='none';
			document.getElementById("event").style.display='';
			document.getElementById("urlKey").value="http://www.xxx.com";
			document.getElementById("menuKey").style.display='none';
		    document.getElementById("caidanKey").value='menuName';
		}else{
			$("#radio3").attr("checked","checked");
			document.getElementById("event").style.display='none';
			  $("#eventkey").combobox('setValue','下拉选择');
			 document.getElementById("view").style.display='none';
			document.getElementById("urlKey").value="http://www.xxx.com";
		    document.getElementById("menuKey").style.display='';
		}
	}
	//删除菜单
	else if(item.name=='delMenu'){
		var id = menuRow.id;
		var data = "id="+id;
		$.ajax({
			type:"POST",
			url:"../wxadmin/existChildMenu.json",
			data:data,
			dataType:"json",
			success:function(result){
				if(result.data == 'EXIST'){
					$.messager.alert('提示','该区域存在子菜单，请先删除子菜单');
				}else{
					$.messager.confirm('友情提示', '您确定要删除该菜单?', function(boo){   
		                if (boo){  
							var data = "id="+menuRow.id;
							$.ajax({
								type:"POST",
								url:"../wxadmin/deleteMenu.json",
								data:data,
								dataType:"json",
								success:function(result){
									if(result.data =='DEL_SUCCESS'){
										 $('#menuData').treegrid('remove',menuRow.id);
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

var flag = false;
function formValidate(){
	var that = this;
	$('#menuForm').validator({
		theme: 'yellow_right',
		timely:1,
		rules:{
			unique:function(element, param, field){ //自定义方法规则
				var data = "menuName="+$("#menuName").val()+"&opearType="+$("#opearType").val();
				return $.ajax({
					type:"POST",
					url:"../wxadmin/existMenu.json",
					data:data,
					success:function(data){
						return true;
					},
					error:function (XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert('系统异常','验证请等待');
						return false;
					}
				});
			}
		},
		fields:{
			"menuOrderNo":{
				rule:"required;digits;range[~10]",
				tip:"纯数字编号"
			},
			"urlKey":{
				rule:"required;url",
				tip:"（http://www.xxx.com）"
			},
//			"eventkey":{
//				rule:"required;letters",
//				tip:"纯字母"
//			},
			"eventkey":{
				rule:"required",
				tip:"必输项"
			},
			"menuName":{
				rule:"required",
				tip:"必输项"
			},
			"caidanKey":{
				rule:"required",
				tip:"必输项"
			}
			
		},
		valid: function(form){
			flag = true;
		},
		invalid: function(form){
			flag = false;
		}
	});
	return flag;
}

function clearData(){
	$("#id").val("");
	$("#menuName").val("");
	$("#urlKey").val("");
	$("#eventkey").val("");
	$("#menuOrderNo").val("");
	$("#caidanKey").val("");
}

