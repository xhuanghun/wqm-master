<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "sewagePumpContainer">
<div id="sewagePumpSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="sewagePumpSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_sewagePumpName">监测点名称</label>
					<input id="search_sewagePumpName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_sewagePumpCode">监测点编码</label>
					<input id="search_sewagePumpCode" name="code" class="easyui-textbox" style="width:120px;"/>
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
				   <a class="easyui-linkbutton" href="#" id="sewagePumpSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="sewagePumpResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="sewagePumpSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="sewagePumpDatagrid" style="width:100%;"></table>
</div>
<div id="sewagePumpDataDialog"  style="display:none">
	<form id="sewagePumpDataForm" style="margin:10px" >
		<input type="hidden" id="sewagePumpId" name="id"  ></input>
		<input type="hidden" id="sewagePumpSaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			监测点名称：
			<input id="sewagePumpName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			监测点编码：
			<input id="sewagePumpCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="sewagePumpIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			覆盖的流域：
			<input id="cover" name="cover" class="easyui-textbox" style="width:150px;"/> 
			涉及点位：
			<input id="monitorPoint" name="monitorPoint"  class="easyui-textbox" style="width:150px;"/>
		</div>
	</form>
</div>

<form id="sewagePumpMonitorItemForm" style="margin:10px" >
	<div id="sewagePumpMonitorItemDialog"  style="display:none;">
		<div style="margin-top: 5px;margin-bottom:5px;">
			频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:120px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="4">年测</option>
			</select>
		</div>
		<ul id="sewagePumpMonitorItemTree"></ul>
	</div>
</form>

<div id="sewagePump_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:sewagePumpAddData()" id = "sewagePumpAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:sewagePumpEditData()" id = "sewagePumpEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:sewagePumpDeleData()" id = "sewagePumpDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:sewagePumpMonitorItem()" id = "sewagePumpMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#sewagePumpDatagrid').datagrid({
    url:"${ctx}/sewagePump/getSewagePumpPage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'监测点名称',width:'10%'},
        {field:'code',title:'监测点编码',width:'10%'},
        {field:'cover',title:'覆盖流域',width:'10%'},
        {field:'monitorPoint',title:'涉及点位',width:'10%'},
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
    queryParams:$('#sewagePumpSearchConditionForm').getFormData(), 
    toolbar:"#sewagePump_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){sewagePumpSelectChange(index,row);},
	onUnselect: function(index,row){sewagePumpSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	sewagePumpDataDialog("提升泵站编辑",row);
    } 
});

function sewagePumpSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#sewagePumpDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#sewagePumpEditButton").linkbutton("enable");
		$("#sewagePumpMonitorItemButton").linkbutton("enable");
		$("#sewagePumpDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#sewagePumpDeleButton").linkbutton("disable");
		$("#sewagePumpEditButton").linkbutton("disable");
		$("#sewagePumpEnableButton").linkbutton("disable");
		$("#sewagePumpDisableButton").linkbutton("disable");
		$("#sewagePumpMonitorItemButton").linkbutton("disable");
	}else{
		$("#sewagePumpMonitorItemButton").linkbutton("disable");
		$("#sewagePumpEditButton").linkbutton("disable");
		$("#sewagePumpEnableButton").linkbutton("disable");
		$("#sewagePumpDisableButton").linkbutton("disable");
	}
}

$('#sewagePumpSearchButton').click(function(){
	$('#sewagePumpDatagrid').datagrid('load',$('#sewagePumpSearchConditionForm').getFormData());
});

function sewagePumpAddData(){
	sewagePumpDataDialog("提升泵站新增",null);		 
}
function sewagePumpEditData(){
	var selected = $('#sewagePumpDatagrid').datagrid('getSelected');
	sewagePumpDataDialog("提升泵站编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  sewagePump对象由DataGrid 传送 
}
function sewagePumpMonitorItem(){
	var selected = $('#sewagePumpDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	sewagePumpMonitorItemDialog("设置监测项",selected);
}

function sewagePumpDeleData(){
	var selections = $('#sewagePumpDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/sewagePump/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#sewagePumpDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function sewagePumpSave(){
	var saveType =$("#sewagePumpSaveType").val();
	if(checkNotNull('sewagePumpName',"提升泵站名称")&&checkNotNull('sewagePumpCode',"提升泵站编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/sewagePump/"+saveType,
			data:$('#sewagePumpDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#sewagePumpDataDialog").dialog("close");
		   		$("#sewagePumpDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#sewagePumpDataDialog").dialog("close");
			    $("#sewagePumpDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#sewagePumpMonitorItemTree').tree('getChecked');//获取:checked的结点.
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
		url:"${ctx}/sewagePump/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#sewagePumpMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#sewagePumpMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function sewagePumpDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#sewagePumpDataDialog").show(); //先显示，再弹出
    $("#sewagePumpDataDialog").dialog({
        title: title,
        width: 600,
        height: 230,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){sewagePumpSave();}
		},{
			text:'取消',
			handler:function(){$("#sewagePumpDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	$.ajax({
        url:"${ctx}/sewagePump/getSewagePumpMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#sewagePumpMonitorItemTree').tree('getRoot');  
			$("#sewagePumpMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#sewagePumpMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#sewagePumpMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function sewagePumpMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#sewagePumpMonitorItemDialog").show(); //先显示，再弹出
    $("#sewagePumpMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#sewagePumpMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#sewagePumpMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#sewagePumpName").textbox('setValue',selected.name);
	 $("#sewagePumpCode").textbox('setValue',selected.code);
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#sewagePumpIconCls").textbox('setValue',selected.iconCls);
	 $("#sewagePumpId").val(selected.id);
	 $("#sewagePumpSaveType").val("update");	 
	 $("#cover").textbox('setValue',selected.cover);
	 $("#monitorPoint").textbox('setValue',selected.monitorPoint); 
}
function clearWaterForm(){
	 $("#sewagePumpName").textbox('setValue',"");
	 $("#sewagePumpCode").textbox('setValue',"");
 	 $('#belong_area').combotree('clear'); 
	 $("#sewagePumpIconCls").textbox('setValue',"");
	 $("#sewagePumpId").val("");
	 $("#sewagePumpSaveType").val("create"); 
	 $("#cover").textbox('setValue',"");
	 $("#monitorPoint").textbox('setValue',"");
}

/**
 * 设置分页
 */
var p = $('#sewagePumpDatagrid').datagrid('getPager'); 
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
		 		var selected = $('#sewagePumpDatagrid').datagrid('getSelected');
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

$("#sewagePumpEnableButton").click(function(){
	 sewagePumpEnableDisable("1");
});
$("#sewagePumpDisableButton").click(function(){
	 sewagePumpEnableDisable("0");
});
function sewagePumpEnableDisable(status){
	var selected = $('#sewagePumpDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/sewagePump/updateStatus",
		data:{"id":selected.id,"sewagePumpStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#sewagePumpDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#sewagePumpDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>