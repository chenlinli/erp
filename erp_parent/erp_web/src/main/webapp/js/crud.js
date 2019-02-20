//提交的方法
var method="";
$(function(){
	$('#grid').datagrid({
	    url:name+'_getList',
	    columns:columns,
	    pagination:true,
	    singleSelect:true,
		toolbar: [{
			text:'新增',	
			iconCls: 'icon-add',
			handler: function(){
				method="add";
				$('#editDlg').dialog('open');
				$('#editForm').form('clear');
			} 
		}] 
	});
	
	$('#btnSearch').bind('click',function(){
		//表单数据封装为json对象
		var formData = $('#searchForm').serializeJSON();
		//json对象转换成字符串
		$('#grid').datagrid('load',formData);
	});
	
	$('#editDlg').dialog({
	    title: '编辑',
	    width: 250,
	    height: 150,
	    closed: true,//初始时是否关闭，true:关闭
	    modal: true  //非模式窗口
	});
	//保存按钮点击事件
	$('#btnSave').bind('click',function(){
		var formData = $('#editForm').serializeJSON();
		$.ajax({
			url:name+'_'+method,
			data:formData,
			dataType:'json',
			type:'post',
			success:function(rtn){
				$.messager.alert("提示",rtn.message,'info',function(){//关闭窗口时回调函数
					//成功的话关闭窗口
					$('#editDlg').dialog('close');
					//刷新表格数据
					$('#grid').datagrid('reload');
				});
			}
		});
	});
	
});


function del(uuid){
	$.messager.confirm("确认","确认要删除吗",function(yes){
		if(yes){
			$.ajax({
				url:name+'_delete?id='+uuid,
				dataType:'json',
				type:'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){//关闭窗口时回调函数
						//刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});	
}

function edit(uuid){
	//弹出窗口
	$('#editDlg').dialog('open');
	
	//清空表单内容
	$('#editForm').form('clear');
	method="update";
	//加载数据
	$('#editForm').form('load',name+'_get?id='+uuid);
}
