<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@include file="/public/common.jsp"%>
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
		<input type="hidden" id="sewageSaveType" name ="saveType" value="createmonitor"></input>
		<input type="hidden" id="addOrupdate" name ="addOrupdate" value="0"></input>
		<div class="line-div">
			污水厂名称：
			<input id="sewageName" name="name"  class="easyui-textbox" style="width:150px;"/>
			采样时间：
			<input id="monitorTime" name="monitorTime" type="text" style="width:150px;"/>
		</div>
		<div class="line-div">
			进出水类别：
			<select id="type" class="easyui-combobox" name="type" style="width:150px;">
			    <option value="1">进水出水</option>
			    <option value="2">脱水污泥</option>
			</select>
			监测频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:150px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="4">年测</option>
			</select>
		</div>
	</form>
</div>

<div id="sewage_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:sewageAddData()" id = "sewageAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >录入</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:sewageEditData()" id = "sewageEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:sewageDeleData()" id = "sewageDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#sewageDatagrid').datagrid({
    url:"${ctx}/manageData/getMonitorDataNewList",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'序号',sortable:true},
        {field:'waterName',title:'污水处理厂名称'},
        {field:'waterCode',title:'污水处理厂编码',},
        {field:'itemName',title:'监测名称'},
        {field:'itemValue',title:'监测值'},
        {field:'type',title:'类型',formatter:function(value,rec){
        	if(rec.type==1)  
        		return "进水出水";
        	else  if(rec.type==2) 		  
        		return "脱水污泥";
        	else 
        		return "未知";
        }},
        {field:'monitorType',title:'频率',formatter:function(value,rec){
        	if(rec.monitorType==1)  
        		return "周测";
        	else  if(rec.monitorType==2)		  
        		return "月测";
        	else if(rec.monitorType==3)
        		return "季测";
        	else if(rec.monitorType==4)
        		return "年测";
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
    	mergeCellsByField('sewageDatagrid', 'waterName','type','monitorType');
    },
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
		$("#sewageDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#sewageDeleButton").linkbutton("disable");
		$("#sewageEditButton").linkbutton("disable");
		$("#sewageEnableButton").linkbutton("disable");
		$("#sewageDisableButton").linkbutton("disable");
	}else{
		$("#sewageEditButton").linkbutton("disable");
		$("#sewageEnableButton").linkbutton("disable");
		$("#sewageDisableButton").linkbutton("disable");
	}
}

$('#sewageSearchButton').click(function(){
	$('#sewageDatagrid').datagrid('load',$('#sewageSearchConditionForm').getFormData());
});

function sewageAddData(){
	$('#addOrupdate').val("0");
	$("#sewageSaveType").val("createmonitor");
	sewageDataDialog("污水厂新增",null);		 
}
function sewageEditData(){
	$('#addOrupdate').val("1");
	$("#sewageSaveType").val("updatemonitor");
	var selected = $('#sewageDatagrid').datagrid('getSelected');
	sewageDataDialog("污水厂编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  sewage对象由DataGrid 传送 
}


function sewageDeleData(){
	var selections = $('#sewageDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var code = "";
	    	var type = "";
	    	var monitorType="";
	    	for(sele in selections){
	    		code += selections[sele].waterCode;
	    		type += selections[sele].type;
	    		if(type=='进水出水'){
	    			type='1';
	    		}else if(type=='脱水污泥'){
	    			type='2';
	    		}
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
	    			type += ",";
	    			monitorType += ",";
	    		}
	    	}
	    	$.ajax({
	    		url:"${ctx}/sewage/deletemonitor",
	    		type:'GET',
	    		data: { 'code': code,'type':type,'monitorType':monitorType },  
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
	if(checkNotNull('sewageName',"污水厂名称")){
		var monitorContent='';
		var mVal='';
		var code=$('#sewageName').combobox('getValue');
		var monitorType=$('#MonitorType').combobox('getValue');
 		var type=$('#type').combobox('getValue');
		var sewageName=$('#sewageName').combobox('getText');
		var monitorTime=$('#monitorTime').combobox('getText');
		$('#sewageDataForm div:gt(1)>input').each(function(){
			mVal=mVal+$(this).val();
		  });
		if(mVal==''){
			$.messager.alert("出错！","请至少填写一项内容",'error',function(r){
				refId.textbox().next('span').find('input').focus();
			});
			return false;
		}
		$('#sewageDataForm div:gt(1)>input').each(function(){
			monitorContent=monitorContent+'##'+$(this).attr('name')+'@@'+$(this).val();
		 });
		$.ajax({
	   		url:"${ctx}/sewage/"+saveType,
	   		type:'GET',
	   		data: { 'monitorContent': monitorContent,'sewageCode':code,'sewageName':sewageName,'type':type,'monitorType':monitorType,'monitorTime':monitorTime},  
	   		success:function(data){
	   			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	   			$("#sewageDatagrid").datagrid('reload');
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
function sewageDataDialog(title,selected){
	//clearWaterForm();
	$("#MonitorType").combobox('setText',"周测");
	$("#type").combobox('setText',"进水出水");
	$("#sewageDataDialog").show(); //先显示，再弹出
    $("#sewageDataDialog").dialog({
        title: title,
        width: 650,
        height: 530,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){sewageSave();}
		},{
			text:'取消',
			handler:function(){$("#sewageDataDialog").dialog("close");}
		}]
    });
	if(selected!=null){
		//setWaterFormValue(selected);
		$(".combo-panel>div:contains('"+selected.waterName+"')").trigger("click");
		//$("#type option[value='("+selected.type+")']").trigger("click");
		$("#MonitorType").combobox('select',selected.monitorType);
		$("#type").combobox('select',selected.type);
	}
}

/* 
function setWaterFormValue(selected){
	 $("#sewageName").textbox('setValue',selected.name);
	 $("#sewageId").val(selected.id);
	 $("#sewageSaveType").val("updatemonitor");
}
function clearWaterForm(){
	 $("#sewageName").textbox('setValue',"");
	 $("#sewageId").val("");
	 $("#sewageSaveType").val("createmonitor"); 
} */

/**
 * 设置分页
 */
var p = $('#sewageDatagrid').datagrid('getPager'); 
$(p).pagination({ 
    pageSize: 10,			//每页显示的记录条数，默认为15 
    pageList: [10,15,20]
});


$("#sewageName").combobox({
    url:'${ctx}/sewage/sewageList',
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
			code=$('#sewageName').combobox('getValue');
		}else{
			code=value.code;
		}
		var addOrupdate=$('#addOrupdate').val();
		var monitorType=$('#MonitorType').combobox('getValue');
 		var type=$('#type').combobox('getValue');
 		$.ajax({
    		url:"${ctx}/manageData/getMonitorSewage",
    		type:'GET',
    		data: { 'code': code,'monitorType':monitorType,'type':type,'addOrupdate':addOrupdate },  
    		success:function(data){
    			$('#sewageDataForm div:gt(1)').remove();
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
    	            		$('#sewageDataForm').append('<div class="line-div">'+inputContent+'</div>');
    	            		num=0;
    	            		inputContent='';
    	            	}
    	            }
    	            $('#sewageDataForm').append('<div class="line-div">'+inputContent+'</div>');
    			}else{
    				if(addOrupdate=='1'){
        				$('#sewageDataForm').append('<div class="line-div">该选项还未录入数据</div>');
            		}else{
        				$('#sewageDataForm').append('<div class="line-div">该选项还未录入监测项</div>');
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
 $('#MonitorType,#type').combobox({ 
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
 function mergeCellsByField(tableID, colList,type,monitor) {
     var ColArray = colList.split(",");
     var TypeArray= type.split(",");
     var MonitorArray=monitor.split(",");
     var tTable = $("#" + tableID);
     var TableRowCnts = tTable.datagrid("getRows").length;
     var tmpA;
     var tmpB;
     var PerTxt = "";
     var CurTxt = "";
     var PerTxtType = "";
     var CurTxtType = "";
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
                 CurTxtType = "";
                 CurTxtMonitor = "";                 
             }
             else {
                 CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                 CurTxtType = tTable.datagrid("getRows")[i][TypeArray[j]];
                 CurTxtMonitor= tTable.datagrid("getRows")[i][MonitorArray[j]];
             }
             if (PerTxt == CurTxt&&PerTxtType == CurTxtType&&PerTxtMonitor == CurTxtMonitor) {
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
             PerTxtType = CurTxtType;
             PerTxtMonitor = CurTxtMonitor;
             
         }
     }
 }
</script>
</div>