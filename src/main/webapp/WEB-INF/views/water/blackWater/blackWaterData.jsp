<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="/public/common.jsp"%>
<div id= "blackWaterContainer">
<div id="blackWaterSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="blackWaterSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_blackWaterName">水体名称</label>
					<input id="search_blackWaterName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_blackWaterCode">水体编码</label>
					<input id="search_blackWaterCode" name="code" class="easyui-textbox" style="width:120px;"/>
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
		<input type="hidden" id="blackWaterSaveType" name ="saveType" value="createmonitor"></input>
		<input type="hidden" id="addOrupdate" name ="addOrupdate" value="0"></input>
		<div class="line-div">
			水体名称：
			<input id="blackWaterName" name="name"  class="easyui-textbox" style="width:150px;"/>
			采样时间：
			<input id="monitorTime" name="monitorTime" type="text" style="width:150px;"/>
		</div>
		<div class="line-div">
			监测频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:150px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="5">半年测</option>
			    <option value="4">年测</option>
			</select>
		</div>
	</form>
</div>

<div id="blackWater_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:blackWaterAddData()" id = "blackWaterAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >录入</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:blackWaterEditData()" id = "blackWaterEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:blackWaterDeleData()" id = "blackWaterDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#blackWaterDatagrid').datagrid({
    url:"${ctx}/blackWater/getMonitorDataNewList",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'序号',sortable:true},
        {field:'blackWaterName',title:'水体名称'},
        {field:'blackWaterCode',title:'水体编码',},
        {field:'itemName',title:'监测名称'},
        {field:'itemValue',title:'监测值'},
        {field:'monitorType',title:'频率',formatter:function(value,rec){
        	if(rec.monitorType==1)  
        		return "周测";
        	else  if(rec.monitorType==2)		  
        		return "月测";
        	else if(rec.monitorType==3)
        		return "季测";
        	else if(rec.monitorType==4)
        		return "年测";
        	else if(rec.monitorType==5)
        		return "半年测";
        	else
        		return "未知";
        }},
        {field:'company',title:'单位'},
        {field:'user',title:'操作员',formatter:function(value,rec){
        	if(rec.user)  
        		return rec.user.name;
        	else  		  
        		return "未知";
        }},
        {field:'monitorDate',title:'采样时间'},
        {field:'createDate',title:'创建日期'},
        {field:'updateDate',title:'修改日期'}
    ]],
    onLoadSuccess: function (rowData) {   	 
    	mergeCellsByField('blackWaterDatagrid', 'blackWaterName','monitorType');
    },
    queryParams:$('#blackWaterSearchConditionForm').getFormData(), 
    toolbar:"#blackWater_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){blackWaterSelectChange(index,row);},
	onUnselect: function(index,row){blackWaterSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	blackWaterDataDialog("污水体编辑",row);
    } 
});

function blackWaterSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#blackWaterDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#blackWaterEditButton").linkbutton("enable");
		$("#blackWaterDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#blackWaterDeleButton").linkbutton("disable");
		$("#blackWaterEditButton").linkbutton("disable");
		$("#blackWaterEnableButton").linkbutton("disable");
		$("#blackWaterDisableButton").linkbutton("disable");
	}else{
		$("#blackWaterEditButton").linkbutton("disable");
		$("#blackWaterEnableButton").linkbutton("disable");
		$("#blackWaterDisableButton").linkbutton("disable");
	}
}

$('#blackWaterSearchButton').click(function(){
	$('#blackWaterDatagrid').datagrid('load',$('#blackWaterSearchConditionForm').getFormData());
});

function blackWaterAddData(){
	$('#addOrupdate').val("0");
	$("#blackWaterSaveType").val("createmonitor");
	blackWaterDataDialog("污水体新增",null);		 
}
function blackWaterEditData(){
	$('#addOrupdate').val("1");
	$("#blackWaterSaveType").val("updatemonitor");
	var selected = $('#blackWaterDatagrid').datagrid('getSelected');
	blackWaterDataDialog("污水体编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  blackWater对象由DataGrid 传送 
}


function blackWaterDeleData(){
	var selections = $('#blackWaterDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var code = "";
	    	var monitorType="";
	    	for(sele in selections){
	    		code += selections[sele].blackWaterCode;
	    		monitorType += selections[sele].monitorType;
	    		if(monitorType=='周测'){
	    			monitorType='1';
	    		}else if(monitorType=='月测'){
	    			monitorType='2';
	    		}else if(monitorType=='季测'){
	    			monitorType='3';
	    		}else if(monitorType=='年测'){
	    			monitorType='4';
	    		}
	    		if(sele<(num-1)){
	    			code += ",";
	    			monitorType += ",";
	    		}
	    	}
	    	$.ajax({
	    		url:"${ctx}/blackWater/deletemonitor",
	    		type:'GET',
	    		data: { 'code': code,'monitorType':monitorType },  
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
	if(checkNotNull('blackWaterName',"水体名称")){
		var monitorContent='';
		var mVal='';
		var code=$('#blackWaterName').combobox('getValue');
		var monitorType=$('#MonitorType').combobox('getValue');
		var blackWaterName=$('#blackWaterName').combobox('getText');
		var monitorTime=$('#monitorTime').combobox('getText');
		$('#blackWaterDataForm div:gt(1)>input').each(function(){
			mVal=mVal+$(this).val();
		  });
		if(mVal==''){
			$.messager.alert("出错！","请至少填写一项内容",'error',function(r){
				refId.textbox().next('span').find('input').focus();
			});
			return false;
		}
		$('#blackWaterDataForm div:gt(1)>input').each(function(){
			monitorContent=monitorContent+'##'+$(this).attr('name')+'@@'+$(this).val();
		 });
		$.ajax({
	   		url:"${ctx}/blackWater/"+saveType,
	   		type:'GET',
	   		data: { 'monitorContent': monitorContent,'blackWaterCode':code,'blackWaterName':blackWaterName,'monitorType':monitorType,'monitorTime':monitorTime},  
	   		success:function(data){
	   			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	   			$("#blackWaterDatagrid").datagrid('reload');
	   		},
	   		error:function(XMLHttpRequest, textStatus, errorThrown){
	   			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	   		}
	   	});  	
	}
}



/**
 * 本页面内DIV Dialog
 */
function blackWaterDataDialog(title,selected){
	//clearWaterForm();
	$("#MonitorType").combobox('setText',"周测");
	$("#MonitorType").combobox('select',1);
	$("#blackWaterDataDialog").show(); //先显示，再弹出
    $("#blackWaterDataDialog").dialog({
        title: title,
        width: 650,
        height: 530,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){blackWaterSave();}
		},{
			text:'取消',
			handler:function(){$("#blackWaterDataDialog").dialog("close");}
		}]
    });
	if(selected!=null){
		//setWaterFormValue(selected);
		$(".combo-panel>div:contains('"+selected.blackWaterName+"')").trigger("click");
		$("#MonitorType").combobox('select',selected.monitorType);
	}
}

/**
 * 设置分页
 */
var p = $('#blackWaterDatagrid').datagrid('getPager'); 
$(p).pagination({ 
    pageSize: 10,			//每页显示的记录条数，默认为15 
    pageList: [10,15,20]
});


$("#blackWaterName").combobox({
    url:'${ctx}/blackWater/blackWaterList',
    valueField:'code',
    textField:'name',
    method:'GET',
 	onSelect:function(value){
			monitorselect(value);
   	} 
});
/*
 * 获取监测项方法
 */
 function monitorselect(value){
		var code='';
		if(value==''){
			code=$('#blackWaterName').combobox('getValue');
		}else{
			code=value.code;
		}
		var addOrupdate=$('#addOrupdate').val();
		var monitorType=$('#MonitorType').combobox('getValue');
 		$.ajax({
    		url:"${ctx}/blackWater/getMonitorBlackWater",
    		type:'GET',
    		data: { 'code': code,'monitorType':monitorType,'addOrupdate':addOrupdate },  
    		success:function(data){
    			$('#blackWaterDataForm div:gt(1)').remove();
    			if(data.length>0){
    				var num=0;
    				var inputContent='';
    	            for(var index in data) {
    	            	var row = data[index];
    	            	if(row.ename==''||row.name==''){	    	            		
    	            	}else{
    	            		if(addOrupdate=='1'){
    	    	            	inputContent=inputContent+'<span style="width:120px;display:inline-block;display:-moz-inline-box;text-align:right">'+row.name+'</span>：<input value="'+row.value+'" name="'+row.id+'"  class="easyui-textbox" style="width:150px;"/>';
    	            		}else{
    	    	            	inputContent=inputContent+'<span style="width:120px;display:inline-block;display:-moz-inline-box;text-align:right">'+row.name+'</span>：<input id="'+row.ename+'" name="'+row.name+'"  class="easyui-textbox" style="width:150px;"/>';
    	            		}
	    	            	num++;
    	            	}
    	            	if(num==2){
    	            		$('#blackWaterDataForm').append('<div class="line-div">'+inputContent+'</div>');
    	            		num=0;
    	            		inputContent='';
    	            	}
    	            }
    	            $('#blackWaterDataForm').append('<div class="line-div">'+inputContent+'</div>');
    			}else{
                	if(addOrupdate=='1'){
        				$('#blackWaterDataForm').append('<div class="line-div">该选项还未录入数据</div>');
            		}else{
        				$('#blackWaterDataForm').append('<div class="line-div">该选项还未录入监测项</div>');
            		}
    			}
    		},
    		error:function(XMLHttpRequest, textStatus, errorThrown){
    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
    		}
    	});
}
 /*
  * 时间
  */
 $('#monitorTime').datetimebox({    
 	    required: true,    
 	    showSeconds: false   
 }); 
/**
 * 选中监测频率选项
 */
 $('#MonitorType').combobox({ 
		 onSelect: function(rec){  
		   		monitorselect('');   
	     } 
	}); 

 var checkNotNull=function(ID,idName){
 	var refId=$('#'+ID).combobox('getText');
 	if(refId==""){
 		$.messager.alert("出错！","请填写"+idName,'error',function(r){
 			refId.textbox().next('span').find('input').focus();
 		});
 		return false;
 	}else{
 		return true;
 	}
 };
 /**
 * EasyUI DataGrid根据字段动态合并单元格
 * 参数 tableID 要合并table的id
 * 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
 */
 function mergeCellsByField(tableID, colList,monitor) {
     var ColArray = colList.split(",");
     var MonitorArray=monitor.split(",");
     var tTable = $("#" + tableID);
     var TableRowCnts = tTable.datagrid("getRows").length;
     var tmpA;
     var tmpB;
     var PerTxt = "";
     var CurTxt = "";
     var PerTxtMonitor = "";
     var CurTxtMonitor = "";
     var alertStr = "";
     for(j = ColArray.length - 1; j >= 0; j--) {
         PerTxt = "";
         tmpA = 1;
         tmpB = 0;

         for (i = 0; i <= TableRowCnts; i++) {
             if (i == TableRowCnts) {
                 CurTxt = "";
                 CurTxtMonitor = "";                 
             }
             else {
                 CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                 CurTxtMonitor= tTable.datagrid("getRows")[i][MonitorArray[j]];
             }
             if (PerTxt == CurTxt&&PerTxtMonitor == CurTxtMonitor) {
                 tmpA += 1;
             }
             else {
                 tmpB += tmpA;
                 
                 tTable.datagrid("mergeCells", {
                     index: i - tmpA,
                     field: ColArray[j],//合并字段
                     rowspan: tmpA,
                     colspan: null
                 });
                 tTable.datagrid("mergeCells", { //根据ColArray[j]进行合并
                     index: i - tmpA,
                     field: "Ideparture",
                     rowspan: tmpA,
                     colspan: null
                 });
                
                 tmpA = 1;
             }
             PerTxt = CurTxt;
             PerTxtMonitor = CurTxtMonitor;
             
         }
     }
 }
</script>
</div>