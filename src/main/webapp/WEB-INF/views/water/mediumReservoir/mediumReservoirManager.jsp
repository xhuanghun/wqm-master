<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "mediumReservoirContainer">
<div id="mediumReservoirSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="mediumReservoirSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_mediumReservoirName">水库名称</label>
					<input id="search_mediumReservoirName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_mediumReservoirCode">水库编码</label>
					<input id="search_mediumReservoirCode" name="code" class="easyui-textbox" style="width:120px;"/>
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
				   <a class="easyui-linkbutton" href="#" id="mediumReservoirSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="mediumReservoirResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="mediumReservoirSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="mediumReservoirDatagrid" style="width:100%;"></table>
</div>
<div id="mediumReservoirDataDialog"  style="display:none">
	<form id="mediumReservoirDataForm" style="margin:10px" >
		<input type="hidden" id="mediumReservoirId" name="id"  ></input>
		<input type="hidden" id="mediumReservoirSaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			水库名称：
			<input id="mediumReservoirName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			水库编码：
			<input id="mediumReservoirCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="mediumReservoirIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			采样点位：
			<input id="monitorPoit" name="monitorPoit" class="easyui-textbox" style="width:150px;"/> 
			水库功能：
			<input id="content" name="content"  class="easyui-textbox" style="width:150px;"/>
		</div>
	</form>
</div>

<form id="mediumReservoirMonitorItemForm" style="margin:10px" >
	<div id="mediumReservoirMonitorItemDialog"  style="display:none;">
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
		<ul id="mediumReservoirMonitorItemTree"></ul>
	</div>
</form>

<div id="mediumReservoir_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:mediumReservoirAddData()" id = "mediumReservoirAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:mediumReservoirEditData()" id = "mediumReservoirEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:mediumReservoirDeleData()" id = "mediumReservoirDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:mediumReservoirMonitorItem()" id = "mediumReservoirMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#mediumReservoirDatagrid').datagrid({
    url:"${ctx}/mediumReservoir/getMediumReservoirPage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'水库名称',width:'10%'},
        {field:'code',title:'水库编码',width:'10%'},
        {field:'monitorPoit',title:'采样监测点位',width:'10%'},
        {field:'content',title:'水库功能',width:'40%'},
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
    queryParams:$('#mediumReservoirSearchConditionForm').getFormData(), 
    toolbar:"#mediumReservoir_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){mediumReservoirSelectChange(index,row);},
	onUnselect: function(index,row){mediumReservoirSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	mediumReservoirDataDialog("提升泵站编辑",row);
    } 
});

function mediumReservoirSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#mediumReservoirDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#mediumReservoirEditButton").linkbutton("enable");
		$("#mediumReservoirMonitorItemButton").linkbutton("enable");
		$("#mediumReservoirDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#mediumReservoirDeleButton").linkbutton("disable");
		$("#mediumReservoirEditButton").linkbutton("disable");
		$("#mediumReservoirEnableButton").linkbutton("disable");
		$("#mediumReservoirDisableButton").linkbutton("disable");
		$("#mediumReservoirMonitorItemButton").linkbutton("disable");
	}else{
		$("#mediumReservoirMonitorItemButton").linkbutton("disable");
		$("#mediumReservoirEditButton").linkbutton("disable");
		$("#mediumReservoirEnableButton").linkbutton("disable");
		$("#mediumReservoirDisableButton").linkbutton("disable");
	}
}

$('#mediumReservoirSearchButton').click(function(){
	$('#mediumReservoirDatagrid').datagrid('load',$('#mediumReservoirSearchConditionForm').getFormData());
});

function mediumReservoirAddData(){
	mediumReservoirDataDialog("提升泵站新增",null);		 
}
function mediumReservoirEditData(){
	var selected = $('#mediumReservoirDatagrid').datagrid('getSelected');
	mediumReservoirDataDialog("提升泵站编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  mediumReservoir对象由DataGrid 传送 
}
function mediumReservoirMonitorItem(){
	var selected = $('#mediumReservoirDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	mediumReservoirMonitorItemDialog("设置监测项",selected);
}

function mediumReservoirDeleData(){
	var selections = $('#mediumReservoirDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/mediumReservoir/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#mediumReservoirDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function mediumReservoirSave(){
	var saveType =$("#mediumReservoirSaveType").val();
	if(checkNotNull('mediumReservoirName',"提升泵站名称")&&checkNotNull('mediumReservoirCode',"提升泵站编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/mediumReservoir/"+saveType,
			data:$('#mediumReservoirDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#mediumReservoirDataDialog").dialog("close");
		   		$("#mediumReservoirDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#mediumReservoirDataDialog").dialog("close");
			    $("#mediumReservoirDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#mediumReservoirMonitorItemTree').tree('getChecked');//获取:checked的结点.
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
		url:"${ctx}/mediumReservoir/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#mediumReservoirMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#mediumReservoirMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function mediumReservoirDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#mediumReservoirDataDialog").show(); //先显示，再弹出
    $("#mediumReservoirDataDialog").dialog({
        title: title,
        width: 600,
        height: 230,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){mediumReservoirSave();}
		},{
			text:'取消',
			handler:function(){$("#mediumReservoirDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	$.ajax({
        url:"${ctx}/mediumReservoir/getMediumReservoirMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#mediumReservoirMonitorItemTree').tree('getRoot');  
			$("#mediumReservoirMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#mediumReservoirMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#mediumReservoirMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function mediumReservoirMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#mediumReservoirMonitorItemDialog").show(); //先显示，再弹出
    $("#mediumReservoirMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#mediumReservoirMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#mediumReservoirMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#mediumReservoirName").textbox('setValue',selected.name);
	 $("#mediumReservoirCode").textbox('setValue',selected.code);
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#mediumReservoirIconCls").textbox('setValue',selected.iconCls);
	 $("#mediumReservoirId").val(selected.id);
	 $("#mediumReservoirSaveType").val("update");	 
	 $("#monitorPoit").textbox('setValue',selected.monitorPoit);
	 $("#content").textbox('setValue',selected.monitorPoit);
}
function clearWaterForm(){
	 $("#mediumReservoirName").textbox('setValue',"");
	 $("#mediumReservoirCode").textbox('setValue',"");
 	 $('#belong_area').combotree('clear'); 
	 $("#mediumReservoirIconCls").textbox('setValue',"");
	 $("#mediumReservoirId").val("");
	 $("#mediumReservoirSaveType").val("create"); 
	 $("#monitorPoit").textbox('setValue',"");
	 $("#content").textbox('setValue',"");
	 
	 
}

/**
 * 设置分页
 */
var p = $('#mediumReservoirDatagrid').datagrid('getPager'); 
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
		 		var selected = $('#mediumReservoirDatagrid').datagrid('getSelected');
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

$("#mediumReservoirEnableButton").click(function(){
	 mediumReservoirEnableDisable("1");
});
$("#mediumReservoirDisableButton").click(function(){
	 mediumReservoirEnableDisable("0");
});
function mediumReservoirEnableDisable(status){
	var selected = $('#mediumReservoirDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/mediumReservoir/updateStatus",
		data:{"id":selected.id,"mediumReservoirStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#mediumReservoirDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#mediumReservoirDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>