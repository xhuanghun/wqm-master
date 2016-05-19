<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "sewageContainer">
<div id="sewageSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="sewageSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_sewageName">污水厂名称</label>
					<input id="search_sewageName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_sewageCode">污水厂编码</label>
					<input id="search_sewageCode" name="code" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_userName">操作员</label>
					<input id="search_userName" name="userName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="10%" align="center" style="min-width:150px">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="4"  width="90%" >
					&nbsp;
				</td>
				<td colspan="1" width="10%" align="left" >
				   <a class="easyui-linkbutton" href="#" id="sewageSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="sewageResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="sewageSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="sewageDatagrid" style="width:100%;"></table>
</div>
<div id="sewageDataDialog"  style="display:none">
	<form id="sewageDataForm" style="margin:10px" >
		<input type="hidden" id="sewageId" name="id"  ></input>
		<input type="hidden" id="sewageSaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			污水厂名称：
			<input id="sewageName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			污水厂编码：
			<input id="sewageCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="sewageIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
	</form>
</div>

<form id="sewageMonitorItemForm" style="margin:10px" >
		<div id="sewageMonitorItemDialog"  style="display:none;">
			<div style="margin-top: 5px;margin-bottom:5px;">
				类别：
				<select id="type" class="easyui-combobox" name="type" style="width:120px;">
				    <option value="1">进水出水</option>
				    <option value="2">脱水污泥</option>
				</select>
		   </div>
		   <div style="margin-top: 5px;margin-bottom:5px;">
				频率：
				<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:120px;">
				    <option value="1">周测</option>
				    <option value="2">月测</option>			    
				    <option value="3">季测</option>
				    <option value="4">年测</option>
				</select>
		   </div>
		   <ul id="sewageMonitorItemTree"></ul>
	</div>
</form>

<div id="sewage_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:sewageAddData()" id = "sewageAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:sewageEditData()" id = "sewageEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:sewageDeleData()" id = "sewageDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:sewageMonitorItem()" id = "sewageMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#sewageDatagrid').datagrid({
    url:"${ctx}/sewage/getSewagePage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'污水处理厂名称',width:'10%'},
        {field:'code',title:'污水处理厂编码',width:'10%'},
        {field:'area',title:'所属区域',width:'10%',formatter:function(value,rec){
        	if(rec.area)  
        		return rec.area.name;
        	else  		  
        		return "未知";
        }},
        {field:'user',title:'操作员',width:'8%',formatter:function(value,rec){
        	if(rec.user)  
        		return rec.user.name;
        	else  		  
        		return "未知";
        }},
        {field:'createDate',title:'创建日期',width:'10%'},
        {field:'updateDate',title:'修改日期',width:'10%'}
    ]],
    queryParams:$('#sewageSearchConditionForm').getFormData(), 
    toolbar:"#sewage_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){sewageSelectChange(index,row);},
	onUnselect: function(index,row){sewageSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	sewageDataDialog("污水厂编辑",row);
    } 
});

function sewageSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#sewageDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#sewageEditButton").linkbutton("enable");
		$("#sewageMonitorItemButton").linkbutton("enable");
		$("#sewageDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#sewageDeleButton").linkbutton("disable");
		$("#sewageEditButton").linkbutton("disable");
		$("#sewageEnableButton").linkbutton("disable");
		$("#sewageDisableButton").linkbutton("disable");
		$("#sewageMonitorItemButton").linkbutton("disable");
	}else{
		$("#sewageMonitorItemButton").linkbutton("disable");
		$("#sewageEditButton").linkbutton("disable");
		$("#sewageEnableButton").linkbutton("disable");
		$("#sewageDisableButton").linkbutton("disable");
	}
}

$('#sewageSearchButton').click(function(){
	$('#sewageDatagrid').datagrid('load',$('#sewageSearchConditionForm').getFormData());
});

function sewageAddData(){
	sewageDataDialog("污水厂新增",null);		 
}
function sewageEditData(){
	var selected = $('#sewageDatagrid').datagrid('getSelected');
	sewageDataDialog("污水厂编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  sewage对象由DataGrid 传送 
}
function sewageMonitorItem(){
	var selected = $('#sewageDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#type").combobox('setText',"进水出水");
	sewageMonitorItemDialog("设置监测项",selected);
}

function sewageDeleData(){
	var selections = $('#sewageDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/sewage/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#sewageDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function sewageSave(){
	var saveType =$("#sewageSaveType").val();
	if(checkNotNull('sewageName',"污水厂名称")&&checkNotNull('sewageCode',"污水厂编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/sewage/"+saveType,
			data:$('#sewageDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#sewageDataDialog").dialog("close");
		   		$("#sewageDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#sewageDataDialog").dialog("close");
			    $("#sewageDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#sewageMonitorItemTree').tree('getChecked');//获取:checked的结点.
	var MonitorType=$('#MonitorType').combobox('getValue');
	var type=$('#type').combobox('getValue');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if(nodes[i].id !=''){
			if (s != '') s += ',';
			s += nodes[i].id; 
		}
	}
 	$.ajax({
		type: "POST",
		url:"${ctx}/sewage/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType,'type':type},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#sewageMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#sewageMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function sewageDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#sewageDataDialog").show(); //先显示，再弹出
    $("#sewageDataDialog").dialog({
        title: title,
        width: 600,
        height: 230,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){sewageSave();}
		},{
			text:'取消',
			handler:function(){$("#sewageDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	var type=$('#type').combobox('getValue');
	$.ajax({
        url:"${ctx}/sewage/getSewageMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType,'type':type},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#sewageMonitorItemTree').tree('getRoot');  
			$("#sewageMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#sewageMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#sewageMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function sewageMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#sewageMonitorItemDialog").show(); //先显示，再弹出
    $("#sewageMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#sewageMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#sewageMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#sewageName").textbox('setValue',selected.name);
	 $("#sewageCode").textbox('setValue',selected.code);
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#sewageIconCls").textbox('setValue',selected.iconCls);
	 $("#sewageId").val(selected.id);
	 $("#sewageSaveType").val("update");
}
function clearWaterForm(){
	 $("#sewageName").textbox('setValue',"");
	 $("#sewageCode").textbox('setValue',"");
 	 $('#belong_area').combotree('clear'); 
	 $("#sewageIconCls").textbox('setValue',"");
	 $("#sewageId").val("");
	 $("#sewageSaveType").val("create"); 
}

/**
 * 设置分页
 */
var p = $('#sewageDatagrid').datagrid('getPager'); 
$(p).pagination({ 
    pageSize: 10,			//每页显示的记录条数，默认为15 
    pageList: [10,15,20]
});
/**
 * 父水体选项
 */
$("#belong_area").combotree({
    url:'${ctx}/area/getAreasTree',
    valueField:'id',
    textField:'name',
    method:'GET', 
});

/**
 * 选中监测频率选项
 */
 $('#MonitorType').combobox({    
		 onSelect: function(rec){    
		 		var selected = $('#sewageDatagrid').datagrid('getSelected');
		   		monitorselect(selected);   
	     } 
	}); 
 $('#type').combobox({    
	 onSelect: function(rec){    
	 		var selected = $('#sewageDatagrid').datagrid('getSelected');
	   		monitorselect(selected);   
     } 
}); 
/* $(document).ready(function () {
	$("#MonitorType").combobox({
		onChange: function () {
	 		var selected = $('#sewageDatagrid').datagrid('getSelected');
	   		monitorselect(selected);
		}
	});	
	$("#type").combobox({
		onChange: function () {
	 		var selected = $('#sewageDatagrid').datagrid('getSelected');
	   		monitorselect(selected);
		}
	});	
}); */

var checkNotNull=function(ID,idName){
	var refId=$('#'+ID);
	if(refId.val()==""){
		$.messager.alert("出错！","请填写"+idName,'error',function(r){
			refId.textbox().next('span').find('input').focus();
		});
		return false;
	}else{
		return true;
	}
};

$("#sewageEnableButton").click(function(){
	 sewageEnableDisable("1");
});
$("#sewageDisableButton").click(function(){
	 sewageEnableDisable("0");
});
function sewageEnableDisable(status){
	var selected = $('#sewageDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/sewage/updateStatus",
		data:{"id":selected.id,"sewageStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#sewageDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#sewageDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>