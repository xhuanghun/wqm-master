<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "tapWaterContainer">
<div id="tapWaterSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="tapWaterSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_tapWaterName">自来水厂名称</label>
					<input id="search_tapWaterName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_tapWaterCode">自来水厂编码</label>
					<input id="search_tapWaterCode" name="code" class="easyui-textbox" style="width:120px;"/>
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
				   <a class="easyui-linkbutton" href="#" id="tapWaterSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="tapWaterResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="tapWaterSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="tapWaterDatagrid" style="width:100%;"></table>
</div>
<div id="tapWaterDataDialog"  style="display:none">
	<form id="tapWaterDataForm" style="margin:10px" >
		<input type="hidden" id="tapWaterId" name="id"  ></input>
		<input type="hidden" id="tapWaterSaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			自来水厂名称：
			<input id="tapWaterName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			自来水厂编码：
			<input id="tapWaterCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="tapWaterIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
	</form>
</div>

<form id="tapWaterMonitorItemForm" style="margin:10px" >
	<div id="tapWaterMonitorItemDialog"  style="display:none;">
		<div style="margin-top: 5px;margin-bottom:5px;">
			频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:120px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="5">半年测</option>
			    <option value="4">年测</option>
			</select>
		</div>
		<ul id="tapWaterMonitorItemTree"></ul>
	</div>
</form>

<div id="tapWater_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:tapWaterAddData()" id = "tapWaterAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:tapWaterEditData()" id = "tapWaterEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:tapWaterDeleData()" id = "tapWaterDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:tapWaterMonitorItem()" id = "tapWaterMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#tapWaterDatagrid').datagrid({
    url:"${ctx}/tapWater/getTapWaterPage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'自来水厂名称',width:'10%'},
        {field:'code',title:'自来水厂编码',width:'10%'},
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
    queryParams:$('#tapWaterSearchConditionForm').getFormData(), 
    toolbar:"#tapWater_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){tapWaterSelectChange(index,row);},
	onUnselect: function(index,row){tapWaterSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	tapWaterDataDialog("提升泵站编辑",row);
    } 
});

function tapWaterSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#tapWaterDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#tapWaterEditButton").linkbutton("enable");
		$("#tapWaterMonitorItemButton").linkbutton("enable");
		$("#tapWaterDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#tapWaterDeleButton").linkbutton("disable");
		$("#tapWaterEditButton").linkbutton("disable");
		$("#tapWaterEnableButton").linkbutton("disable");
		$("#tapWaterDisableButton").linkbutton("disable");
		$("#tapWaterMonitorItemButton").linkbutton("disable");
	}else{
		$("#tapWaterMonitorItemButton").linkbutton("disable");
		$("#tapWaterEditButton").linkbutton("disable");
		$("#tapWaterEnableButton").linkbutton("disable");
		$("#tapWaterDisableButton").linkbutton("disable");
	}
}

$('#tapWaterSearchButton').click(function(){
	$('#tapWaterDatagrid').datagrid('load',$('#tapWaterSearchConditionForm').getFormData());
});

function tapWaterAddData(){
	tapWaterDataDialog("提升泵站新增",null);		 
}
function tapWaterEditData(){
	var selected = $('#tapWaterDatagrid').datagrid('getSelected');
	tapWaterDataDialog("提升泵站编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  tapWater对象由DataGrid 传送 
}
function tapWaterMonitorItem(){
	var selected = $('#tapWaterDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	tapWaterMonitorItemDialog("设置监测项",selected);
}

function tapWaterDeleData(){
	var selections = $('#tapWaterDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/tapWater/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#tapWaterDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function tapWaterSave(){
	var saveType =$("#tapWaterSaveType").val();
	if(checkNotNull('tapWaterName',"提升泵站名称")&&checkNotNull('tapWaterCode',"提升泵站编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/tapWater/"+saveType,
			data:$('#tapWaterDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#tapWaterDataDialog").dialog("close");
		   		$("#tapWaterDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#tapWaterDataDialog").dialog("close");
			    $("#tapWaterDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#tapWaterMonitorItemTree').tree('getChecked');//获取:checked的结点.
	var MonitorType=$('#MonitorType').combobox('getValue');
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if(nodes[i].id !=''){
			if (s != '') s += ',';
			s += nodes[i].id; 
		}
	}
 	$.ajax({
		type: "POST",
		url:"${ctx}/tapWater/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#tapWaterMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#tapWaterMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function tapWaterDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#tapWaterDataDialog").show(); //先显示，再弹出
    $("#tapWaterDataDialog").dialog({
        title: title,
        width: 600,
        height: 230,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){tapWaterSave();}
		},{
			text:'取消',
			handler:function(){$("#tapWaterDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	$.ajax({
        url:"${ctx}/tapWater/getTapWaterMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#tapWaterMonitorItemTree').tree('getRoot');  
			$("#tapWaterMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#tapWaterMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#tapWaterMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function tapWaterMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#tapWaterMonitorItemDialog").show(); //先显示，再弹出
    $("#tapWaterMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#tapWaterMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#tapWaterMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#tapWaterName").textbox('setValue',selected.name);
	 $("#tapWaterCode").textbox('setValue',selected.code);
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#tapWaterIconCls").textbox('setValue',selected.iconCls);
	 $("#tapWaterId").val(selected.id);
	 $("#tapWaterSaveType").val("update");	 
}
function clearWaterForm(){
	 $("#tapWaterName").textbox('setValue',"");
	 $("#tapWaterCode").textbox('setValue',"");
 	 $('#belong_area').combotree('clear'); 
	 $("#tapWaterIconCls").textbox('setValue',"");
	 $("#tapWaterId").val("");
	 $("#tapWaterSaveType").val("create"); 
}

/**
 * 设置分页
 */
var p = $('#tapWaterDatagrid').datagrid('getPager'); 
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
		 		var selected = $('#tapWaterDatagrid').datagrid('getSelected');
		   		monitorselect(selected);   
	     } 
	}); 

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

$("#tapWaterEnableButton").click(function(){
	 tapWaterEnableDisable("1");
});
$("#tapWaterDisableButton").click(function(){
	 tapWaterEnableDisable("0");
});
function tapWaterEnableDisable(status){
	var selected = $('#tapWaterDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/tapWater/updateStatus",
		data:{"id":selected.id,"tapWaterStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#tapWaterDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#tapWaterDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>