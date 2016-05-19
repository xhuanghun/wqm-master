<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "waterSourceContainer">
<div id="waterSourceSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="waterSourceSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterSourceName">水源名称</label>
					<input id="search_waterSourceName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterSourceCode">水源编码</label>
					<input id="search_waterSourceCode" name="code" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterIsLeaf">水源类型</label>
					<select id="search_waterIsLeaf" class="easyui-combobox" name="isLeaf" style="width:120px;">
						 <option value="" selected="selected">--请选择--</option>
					     <option value="false">水体</option>
			   			 <option value="true">监测站</option>
					</select>
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
				   <a class="easyui-linkbutton" href="#" id="waterSourceSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="waterSourceResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="waterSourceSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="waterSourceDatagrid" style="width:100%;"></table>
</div>
<div id="waterSourceDataDialog"  style="display:none">
	<form id="waterSourceDataForm" style="margin:10px" >
		<input type="hidden" id="waterSourceId" name="id"  ></input>
		<input type="hidden" id="waterSourceSaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			水源名称：
			<input id="waterSourceName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			水源编码：
			<input id="waterSourceCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="waterSourceIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			水体类型：
			<select id="waterIsLeaf" class="easyui-combobox" name="isLeaf" style="width:150px;">
			    <option value="false" selected="selected">水体</option>
			    <option value="true">监测站</option>
			</select>
			父水体号：
			<input id="waterParentId" name="parentCode" style="width:150px;" />
		</div>
	</form>
</div>

<form id="waterSourceMonitorItemForm" style="margin:10px" >
	<div id="waterSourceMonitorItemDialog"  style="display:none;">
		<div style="margin-top: 5px;margin-bottom:5px;">
			频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:120px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="4">年测</option>
			</select>
		</div>
		<ul id="waterSourceMonitorItemTree"></ul>
	</div>
</form>

<div id="waterSource_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:waterSourceAddData()" id = "waterSourceAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:waterSourceEditData()" id = "waterSourceEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:waterSourceDeleData()" id = "waterSourceDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:waterSourceMonitorItem()" id = "waterSourceMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#waterSourceDatagrid').datagrid({
    url:"${ctx}/waterSource/getWaterSourcePage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'水源名称',width:'10%'},
        {field:'code',title:'水源编码',width:'10%'},
        {field:'area',title:'所属区域',width:'10%',formatter:function(value,rec){
        	if(rec.area)  
        		return rec.area.name;
        	else  		  
        		return "未知";
        }},
        {field:'isLeaf',title:'是否水体',width:'5%',formatter:function(value,rec){
        	if(value)  
        		return "监测站";
        	else  		  
        		return "水体";
        }},
        {field:'user',title:'操作员',width:'8%',formatter:function(value,rec){
        	if(rec.user)  
        		return rec.user.name;
        	else  		  
        		return "未知";
        }},
        {field:'parentCode',title:'父水体',width:'5%'},
        {field:'createDate',title:'创建日期',width:'10%'},
        {field:'updateDate',title:'修改日期',width:'10%'}
    ]],
    queryParams:$('#waterSourceSearchConditionForm').getFormData(), 
    toolbar:"#waterSource_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){waterSourceSelectChange(index,row);},
	onUnselect: function(index,row){waterSourceSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	waterSourceDataDialog("水源地编辑",row);
    } 
});

function waterSourceSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#waterSourceDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#waterSourceEditButton").linkbutton("enable");
		$("#waterSourceMonitorItemButton").linkbutton("enable");
		$("#waterSourceDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#waterSourceDeleButton").linkbutton("disable");
		$("#waterSourceEditButton").linkbutton("disable");
		$("#waterSourceEnableButton").linkbutton("disable");
		$("#waterSourceDisableButton").linkbutton("disable");
		$("#waterSourceMonitorItemButton").linkbutton("disable");
	}else{
		$("#waterSourceMonitorItemButton").linkbutton("disable");
		$("#waterSourceEditButton").linkbutton("disable");
		$("#waterSourceEnableButton").linkbutton("disable");
		$("#waterSourceDisableButton").linkbutton("disable");
	}
}

$('#waterSourceSearchButton').click(function(){
	$('#waterSourceDatagrid').datagrid('load',$('#waterSourceSearchConditionForm').getFormData());
});

function waterSourceAddData(){
	waterSourceDataDialog("水源地新增",null);		 
}
function waterSourceEditData(){
	var selected = $('#waterSourceDatagrid').datagrid('getSelected');
	waterSourceDataDialog("水源地编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  waterSource对象由DataGrid 传送 
}
function waterSourceMonitorItem(){
	var selected = $('#waterSourceDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	waterSourceMonitorItemDialog("设置监测项",selected);
}

function waterSourceDeleData(){
	var selections = $('#waterSourceDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/waterSource/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#waterSourceDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function waterSourceSave(){
	var saveType =$("#waterSourceSaveType").val();
	if(checkNotNull('waterSourceName',"水源地名称")&&checkNotNull('waterSourceCode',"水源地编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/waterSource/"+saveType,
			data:$('#waterSourceDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#waterSourceDataDialog").dialog("close");
		   		$("#waterSourceDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#waterSourceDataDialog").dialog("close");
			    $("#waterSourceDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#waterSourceMonitorItemTree').tree('getChecked');//获取:checked的结点.
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
		url:"${ctx}/waterSource/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#waterSourceMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#waterSourceMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function waterSourceDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#waterSourceDataDialog").show(); //先显示，再弹出
    $("#waterSourceDataDialog").dialog({
        title: title,
        width: 600,
        height: 230,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){waterSourceSave();}
		},{
			text:'取消',
			handler:function(){$("#waterSourceDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	$.ajax({
        url:"${ctx}/waterSource/getWaterSourceMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#waterSourceMonitorItemTree').tree('getRoot');  
			$("#waterSourceMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#waterSourceMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#waterSourceMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function waterSourceMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#waterSourceMonitorItemDialog").show(); //先显示，再弹出
    $("#waterSourceMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#waterSourceMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#waterSourceMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#waterSourceName").textbox('setValue',selected.name);
	 $("#waterSourceCode").textbox('setValue',selected.code);
	 var url = ctx+"/water/getWaterByAreaCode?areaCode="+selected.area.code;
	 $('#waterParentId').combobox('reload',url); 
 	 $('#waterParentId').combobox('setValue', selected.parentCode );
 	 $('#waterIsLeaf').combobox('reload'); 
	 $('#waterIsLeaf').combobox('setValue',(selected.isLeaf).toString() );
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#waterSourceIconCls").textbox('setValue',selected.iconCls);
	 $("#waterSourceId").val(selected.id);
	 $("#waterSourceSaveType").val("update");	 
}
function clearWaterForm(){
	 $("#waterSourceName").textbox('setValue',"");
	 $("#waterSourceCode").textbox('setValue',"");
 	 $('#waterIsLeaf').combobox('reload'); 
 	 $('#belong_area').combotree('clear'); 
	 $("#waterSourceIconCls").textbox('setValue',"");
	 $("#waterSourceId").val("");
	 $("#waterSourceSaveType").val("create"); 
	 $('#waterParentId').combobox('reload'); 
}

/**
 * 设置分页
 */
var p = $('#waterSourceDatagrid').datagrid('getPager'); 
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
 	onSelect:function(value){
   		$('#waterParentId').combobox('clear'); 
   		var url = ctx+"/water/getWaterByAreaCode?areaCode="+value.id;
   		$('#waterParentId').combobox('reload',url); 
   	} 
});
$("#waterParentId").combobox({
    url:'${ctx}/waterSource/getWaterByAreaCode',
    valueField:'code',
    textField:'name',
    method:'GET'
});

/**
 * 选中监测频率选项
 */
 $('#MonitorType').combobox({    
		 onSelect: function(rec){    
		 		var selected = $('#waterSourceDatagrid').datagrid('getSelected');
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

$("#waterSourceEnableButton").click(function(){
	 waterSourceEnableDisable("1");
});
$("#waterSourceDisableButton").click(function(){
	 waterSourceEnableDisable("0");
});
function waterSourceEnableDisable(status){
	var selected = $('#waterSourceDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/waterSource/updateStatus",
		data:{"id":selected.id,"waterSourceStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#waterSourceDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#waterSourceDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>