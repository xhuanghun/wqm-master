<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "waterSupplyContainer">
<div id="waterSupplySearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="waterSupplySearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterSupplyName">供水名称</label>
					<input id="search_waterSupplyName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterSupplyCode">供水编码</label>
					<input id="search_waterSupplyCode" name="code" class="easyui-textbox" style="width:120px;"/>
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
				   <a class="easyui-linkbutton" href="#" id="waterSupplySearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="waterSupplyResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="waterSupplySearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="waterSupplyDatagrid" style="width:100%;"></table>
</div>
<div id="waterSupplyDataDialog"  style="display:none">
	<form id="waterSupplyDataForm" style="margin:10px" >
		<input type="hidden" id="waterSupplyId" name="id"  ></input>
		<input type="hidden" id="waterSupplySaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			供水名称：
			<input id="waterSupplyName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			供水编码：
			<input id="waterSupplyCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="waterSupplyIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			采样地址：
			<input id="monitorPoit" name="monitorPoit" class="easyui-textbox" style="width:150px;"/> 
			供水备注：
			<input id="content" name="content"  class="easyui-textbox" style="width:150px;"/>
		</div>
	</form>
</div>

<form id="waterSupplyMonitorItemForm" style="margin:10px" >
	<div id="waterSupplyMonitorItemDialog"  style="display:none;">
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
		<ul id="waterSupplyMonitorItemTree"></ul>
	</div>
</form>

<div id="waterSupply_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:waterSupplyAddData()" id = "waterSupplyAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:waterSupplyEditData()" id = "waterSupplyEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:waterSupplyDeleData()" id = "waterSupplyDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:waterSupplyMonitorItem()" id = "waterSupplyMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#waterSupplyDatagrid').datagrid({
    url:"${ctx}/waterSupply/getWaterSupplyPage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'供水名称',width:'10%'},
        {field:'code',title:'供水编码',width:'10%'},
        {field:'monitorPoit',title:'采样地址',width:'10%'},
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
    queryParams:$('#waterSupplySearchConditionForm').getFormData(), 
    toolbar:"#waterSupply_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){waterSupplySelectChange(index,row);},
	onUnselect: function(index,row){waterSupplySelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	waterSupplyDataDialog("提升泵站编辑",row);
    } 
});

function waterSupplySelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#waterSupplyDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#waterSupplyEditButton").linkbutton("enable");
		$("#waterSupplyMonitorItemButton").linkbutton("enable");
		$("#waterSupplyDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#waterSupplyDeleButton").linkbutton("disable");
		$("#waterSupplyEditButton").linkbutton("disable");
		$("#waterSupplyEnableButton").linkbutton("disable");
		$("#waterSupplyDisableButton").linkbutton("disable");
		$("#waterSupplyMonitorItemButton").linkbutton("disable");
	}else{
		$("#waterSupplyMonitorItemButton").linkbutton("disable");
		$("#waterSupplyEditButton").linkbutton("disable");
		$("#waterSupplyEnableButton").linkbutton("disable");
		$("#waterSupplyDisableButton").linkbutton("disable");
	}
}

$('#waterSupplySearchButton').click(function(){
	$('#waterSupplyDatagrid').datagrid('load',$('#waterSupplySearchConditionForm').getFormData());
});

function waterSupplyAddData(){
	waterSupplyDataDialog("提升泵站新增",null);		 
}
function waterSupplyEditData(){
	var selected = $('#waterSupplyDatagrid').datagrid('getSelected');
	waterSupplyDataDialog("提升泵站编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  waterSupply对象由DataGrid 传送 
}
function waterSupplyMonitorItem(){
	var selected = $('#waterSupplyDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	waterSupplyMonitorItemDialog("设置监测项",selected);
}

function waterSupplyDeleData(){
	var selections = $('#waterSupplyDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/waterSupply/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#waterSupplyDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function waterSupplySave(){
	var saveType =$("#waterSupplySaveType").val();
	if(checkNotNull('waterSupplyName',"提升泵站名称")&&checkNotNull('waterSupplyCode',"提升泵站编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/waterSupply/"+saveType,
			data:$('#waterSupplyDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#waterSupplyDataDialog").dialog("close");
		   		$("#waterSupplyDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#waterSupplyDataDialog").dialog("close");
			    $("#waterSupplyDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#waterSupplyMonitorItemTree').tree('getChecked');//获取:checked的结点.
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
		url:"${ctx}/waterSupply/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#waterSupplyMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#waterSupplyMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function waterSupplyDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#waterSupplyDataDialog").show(); //先显示，再弹出
    $("#waterSupplyDataDialog").dialog({
        title: title,
        width: 600,
        height: 230,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){waterSupplySave();}
		},{
			text:'取消',
			handler:function(){$("#waterSupplyDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	$.ajax({
        url:"${ctx}/waterSupply/getWaterSupplyMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#waterSupplyMonitorItemTree').tree('getRoot');  
			$("#waterSupplyMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#waterSupplyMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#waterSupplyMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function waterSupplyMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#waterSupplyMonitorItemDialog").show(); //先显示，再弹出
    $("#waterSupplyMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#waterSupplyMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#waterSupplyMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#waterSupplyName").textbox('setValue',selected.name);
	 $("#waterSupplyCode").textbox('setValue',selected.code);
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#waterSupplyIconCls").textbox('setValue',selected.iconCls);
	 $("#waterSupplyId").val(selected.id);
	 $("#waterSupplySaveType").val("update");	 
	 $("#monitorPoit").textbox('setValue',selected.monitorPoit);
	 $("#content").textbox('setValue',selected.monitorPoit);
}
function clearWaterForm(){
	 $("#waterSupplyName").textbox('setValue',"");
	 $("#waterSupplyCode").textbox('setValue',"");
 	 $('#belong_area').combotree('clear'); 
	 $("#waterSupplyIconCls").textbox('setValue',"");
	 $("#waterSupplyId").val("");
	 $("#waterSupplySaveType").val("create"); 
	 $("#monitorPoit").textbox('setValue',"");
	 $("#content").textbox('setValue',"");
	 
	 
}

/**
 * 设置分页
 */
var p = $('#waterSupplyDatagrid').datagrid('getPager'); 
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
		 		var selected = $('#waterSupplyDatagrid').datagrid('getSelected');
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

$("#waterSupplyEnableButton").click(function(){
	 waterSupplyEnableDisable("1");
});
$("#waterSupplyDisableButton").click(function(){
	 waterSupplyEnableDisable("0");
});
function waterSupplyEnableDisable(status){
	var selected = $('#waterSupplyDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/waterSupply/updateStatus",
		data:{"id":selected.id,"waterSupplyStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#waterSupplyDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#waterSupplyDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>