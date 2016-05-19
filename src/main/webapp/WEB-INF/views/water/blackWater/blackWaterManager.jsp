<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "blackWaterContainer">
<div id="blackWaterSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="blackWaterSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_blackWaterName">黑臭水体名称</label>
					<input id="search_blackWaterName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_blackWaterCode">黑臭水体编码</label>
					<input id="search_blackWaterCode" name="code" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterIsLeaf">黑臭水体类型</label>
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
				   <a class="easyui-linkbutton" href="#" id="blackWaterSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="blackWaterResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="blackWaterSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="blackWaterDatagrid" style="width:100%;"></table>
</div>
<div id="blackWaterDataDialog"  style="display:none">
	<form id="blackWaterDataForm" style="margin:10px" >
		<input type="hidden" id="blackWaterId" name="id"  ></input>
		<input type="hidden" id="blackWaterSaveType" name ="saveType" value="create"></input>
		<div class="line-div">
			水体名称：
			<input id="blackWaterName" name="name"  class="easyui-textbox" style="width:150px;"/>
			所属区域：
			<input id="belong_area" name="areaCode" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			水体编码：
			<input id="blackWaterCode" name="code" class="easyui-textbox" style="width:150px;"/> 
			显示图标：
			<input id="blackWaterIconCls" name="iconCls" value="1" class="easyui-textbox" style="width:150px;"/>
		</div>
		<div class="line-div">
			断面类型：
			<select id="waterIsLeaf" class="easyui-combobox" name="isLeaf" style="width:150px;">
			    <option value="false" selected="selected">水体</option>
			    <option value="true">监测站</option>
			</select>
			父水体号：
			<input id="waterParentId" name="parentCode" style="width:150px;" />
		</div>
		<div class="line-div">
			水体类型：
			<input id="waterType" name="waterType" style="width:150px;" class="easyui-textbox"/>
			水质管理：
			<input id="waterManager" name="waterManager" style="width:150px;" class="easyui-textbox"/>
		</div>
		<div class="line-div">
			上月类别：
			<input id="lastMonthType" name="lastMonthType" style="width:150px;" class="easyui-textbox" />
			当月类别：
			<input id="monthType" name="monthType" style="width:150px;" class="easyui-textbox"/>
		</div>
		<div class="line-div">
			水质评价：
			<input id="evaluate" name="parentCode" style="width:150px;" class="easyui-textbox"/>
		</div>
	</form>
</div>

<form id="blackWaterMonitorItemForm" style="margin:10px" >
	<div id="blackWaterMonitorItemDialog"  style="display:none;">
		<div style="margin-top: 5px;margin-bottom:5px;">
			频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:120px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="4">年测</option>
			</select>
		</div>
		<ul id="blackWaterMonitorItemTree"></ul>
	</div>
</form>

<div id="blackWater_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:blackWaterAddData()" id = "blackWaterAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >新增</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:blackWaterEditData()" id = "blackWaterEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:blackWaterDeleData()" id = "blackWaterDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:blackWaterMonitorItem()" id = "blackWaterMonitorItemButton" class="easyui-linkbutton" data-options="iconCls:'pic_18',plain:true,disabled:true," >设置监测项</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#blackWaterDatagrid').datagrid({
    url:"${ctx}/blackWater/getBlackWaterPage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'编号',width:'5%',sortable:true},
        {field:'name',title:'黑臭水体名称',width:'10%'},
        {field:'code',title:'黑臭水体编码',width:'10%'},
        {field:'waterType',title:'水体类型',width:'10%'},
        {field:'waterManager',title:'水质管理目标',width:'10%'},
        {field:'lastMonthType',title:'上月类别',width:'10%'},
        {field:'monthType',title:'当月类别',width:'10%'},
        {field:'evaluate',title:'水质评价',width:'10%'},
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
    queryParams:$('#blackWaterSearchConditionForm').getFormData(), 
    toolbar:"#blackWater_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){blackWaterSelectChange(index,row);},
	onUnselect: function(index,row){blackWaterSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	blackWaterDataDialog("黑臭水体编辑",row);
    } 
});

function blackWaterSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#blackWaterDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#blackWaterEditButton").linkbutton("enable");
		$("#blackWaterMonitorItemButton").linkbutton("enable");
		$("#blackWaterDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#blackWaterDeleButton").linkbutton("disable");
		$("#blackWaterEditButton").linkbutton("disable");
		$("#blackWaterEnableButton").linkbutton("disable");
		$("#blackWaterDisableButton").linkbutton("disable");
		$("#blackWaterMonitorItemButton").linkbutton("disable");
	}else{
		$("#blackWaterMonitorItemButton").linkbutton("disable");
		$("#blackWaterEditButton").linkbutton("disable");
		$("#blackWaterEnableButton").linkbutton("disable");
		$("#blackWaterDisableButton").linkbutton("disable");
	}
}

$('#blackWaterSearchButton').click(function(){
	$('#blackWaterDatagrid').datagrid('load',$('#blackWaterSearchConditionForm').getFormData());
});

function blackWaterAddData(){
	blackWaterDataDialog("黑臭水体新增",null);		 
}
function blackWaterEditData(){
	var selected = $('#blackWaterDatagrid').datagrid('getSelected');
	blackWaterDataDialog("黑臭水体编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  blackWater对象由DataGrid 传送 
}
function blackWaterMonitorItem(){
	var selected = $('#blackWaterDatagrid').datagrid('getSelected');
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	blackWaterMonitorItemDialog("设置监测项",selected);
}

function blackWaterDeleData(){
	var selections = $('#blackWaterDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var ids = "";
	    	for(sele in selections){
	    		ids += selections[sele].id;
	    		if(sele<(num-1)) ids += ",";
	    	}
	    	$.ajax({
	    		url:"${ctx}/blackWater/delete",
	    		type:'GET',
	    		data: { 'ids': ids },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#blackWaterDatagrid").datagrid('reload');
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function blackWaterSave(){
	var saveType =$("#blackWaterSaveType").val();
	if(checkNotNull('blackWaterName',"黑臭水体名称")&&checkNotNull('blackWaterCode',"黑臭水体编码")){
		$.ajax({
			type: "POST",
			url:"${ctx}/blackWater/"+saveType,
			data:$('#blackWaterDataForm').serialize(), //将Form 里的值序列化
			asyn:false,
		    error: function(jqXHR, textStatus, errorMsg) {
		    	$.messager.alert('操作结果',""+jqXHR.responseText);
		   	 	$("#blackWaterDataDialog").dialog("close");
		   		$("#blackWaterDatagrid").datagrid('reload');
		    },
		    success: function(data) {
		    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    $("#blackWaterDataDialog").dialog("close");
			    $("#blackWaterDatagrid").datagrid('reload');
		    }
		}); 	
	}
}

function monitorItemSave(id){
	var nodes = $('#blackWaterMonitorItemTree').tree('getChecked');//获取:checked的结点.
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
		url:"${ctx}/blackWater/saveMonitorItem",
		data:{'itemList':s,'id':id,'MonitorType':MonitorType},  
		asyn:false,
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   	 	$("#blackWaterMonitorItemDialog").dialog("close");
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#blackWaterMonitorItemDialog").dialog("close");
	    }
	});  
}

/**
 * 本页面内DIV Dialog
 */
function blackWaterDataDialog(title,selected){
	clearWaterForm();
	if(selected!=null)
		setWaterFormValue(selected);
	$("#blackWaterDataDialog").show(); //先显示，再弹出
    $("#blackWaterDataDialog").dialog({
        title: title,
        width: 600,
        height: 430,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){blackWaterSave();}
		},{
			text:'取消',
			handler:function(){$("#blackWaterDataDialog").dialog("close");}
		}]
    });
}
function monitorselect(selected){
	var MonitorType=$('#MonitorType').combobox('getValue');
	$.ajax({
        url:"${ctx}/blackWater/getBlackWaterMonitorItemList",
        dataType:"json",
        data:{'id':selected.id,'monitorType':MonitorType},
        async:true,
        cascadeCheck:false,
        success:function(data){
        	var root = $('#blackWaterMonitorItemTree').tree('getRoot');  
			$("#blackWaterMonitorItemTree").tree('uncheck',root.target); 
            $(data).each(function(i, obj){
                var n = $("#blackWaterMonitorItemTree").tree('find',obj.code);
                if(n){
                    $("#blackWaterMonitorItemTree").tree('check',n.target);
                } 
            });
        },
        error:function(){alert("发送请求失败");}
    });
}
/**
 * 监测项编辑
 */
function blackWaterMonitorItemDialog(title,selected){
	if(selected!=null)
		setWaterFormValue(selected); 
	monitorselect(selected);
	$("#blackWaterMonitorItemDialog").show(); //先显示，再弹出
    $("#blackWaterMonitorItemDialog").dialog({
    	title:'监测项',
        width: 240,
        height:460,
        modal:false,
        buttons:[{
			text:'保存',
			handler:function(){monitorItemSave(selected.id);}
		},{
			text:'取消',
			handler:function(){$("#blackWaterMonitorItemDialog").dialog("close");}
		}]
    });
}

/**
 * 监测项树
 */
$('#blackWaterMonitorItemTree').tree({
	url: '${ctx}/monitorItem/getMonitorItemsTree', 
	method:"GET",
	checkbox:true 
});


function setWaterFormValue(selected){
	 $("#blackWaterName").textbox('setValue',selected.name);
	 $("#blackWaterCode").textbox('setValue',selected.code);
	 var url = ctx+"/water/getWaterByAreaCode?areaCode="+selected.area.code;
	 $('#waterParentId').combobox('reload',url); 
 	 $('#waterParentId').combobox('setValue', selected.parentCode );
 	 $('#waterIsLeaf').combobox('reload'); 
	 $('#waterIsLeaf').combobox('setValue',(selected.isLeaf).toString() );
 	 $('#belong_area').combotree('reload'); 
	 $('#belong_area').combotree('setValue', selected.area.code );
	 $("#blackWaterIconCls").textbox('setValue',selected.iconCls);
	 $("#blackWaterId").val(selected.id);
	 $("#blackWaterSaveType").val("update");
	 $("#waterType").textbox('setValue',selected.waterType);
	 $("#waterManager").textbox('setValue',selected.waterManager);
	 $("#lastMonthType").textbox('setValue',selected.lastMonthType);
	 $("#monthType").textbox('setValue',selected.monthType);
	 $("#evaluate").textbox('setValue',selected.evaluate);
}
function clearWaterForm(){
	 $("#blackWaterName").textbox('setValue',"");
	 $("#blackWaterCode").textbox('setValue',"");
 	 $('#waterIsLeaf').combobox('reload'); 
 	 $('#belong_area').combotree('clear'); 
	 $("#blackWaterIconCls").textbox('setValue',"");
	 $("#blackWaterId").val("");
	 $("#blackWaterSaveType").val("create"); 
	 $('#waterParentId').combobox('reload'); 
	 $("#waterType").textbox('setValue',"");
	 $("#waterManager").textbox('setValue',"");
	 $("#lastMonthType").textbox('setValue',"");
	 $("#monthType").textbox('setValue',"");
	 $("#evaluate").textbox('setValue',"");
}

/**
 * 设置分页
 */
var p = $('#blackWaterDatagrid').datagrid('getPager'); 
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
    url:'${ctx}/blackWater/getWaterByAreaCode',
    valueField:'code',
    textField:'name',
    method:'GET'
});

/**
 * 选中监测频率选项
 */
 $('#MonitorType').combobox({    
		 onSelect: function(rec){    
		 		var selected = $('#blackWaterDatagrid').datagrid('getSelected');
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

$("#blackWaterEnableButton").click(function(){
	 blackWaterEnableDisable("1");
});
$("#blackWaterDisableButton").click(function(){
	 blackWaterEnableDisable("0");
});
function blackWaterEnableDisable(status){
	var selected = $('#blackWaterDatagrid').datagrid('getSelected');
	$.ajax({
		type: "GET",
		url:"${ctx}/blackWater/updateStatus",
		data:{"id":selected.id,"blackWaterStatus":status},  
	    error: function(jqXHR, textStatus, errorMsg) {
	    	$.messager.alert('操作结果',""+jqXHR.responseText);
	   		$("#blackWaterDatagrid").datagrid('reload');
	    },
	    success: function(data) {
	    	$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
		    $("#blackWaterDatagrid").datagrid('reload');
	    }
	}); 	
}
</script>
</div>