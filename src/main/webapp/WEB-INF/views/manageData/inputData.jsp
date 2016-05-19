<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jksb" uri="http://www.jksb.com/common/tags"%>
<%@include file="/public/common.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id= "waterContainer">
<div id="waterSearchConditionPanel" title="查询条件" class="easyui-panel" style="width:100%;padding-top:10px;" data-options="collapsible:true">
	<form id="waterSearchConditionForm">
		<table style="width:99%;height:80px;margin-buttom:10px">
			<tr>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterName">断面名称</label>
					<input id="search_waterName" name="name" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterCode">水体编码</label>
					<input id="search_waterCode" name="code" class="easyui-textbox" style="width:120px;"/>
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
				   <a class="easyui-linkbutton" href="#" id="waterSearchButton">&nbsp;查&nbsp;询&nbsp;</a>
				    <a class="easyui-linkbutton" href="#" id="waterResetButton">&nbsp;重&nbsp;置&nbsp;</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  id="waterSearchResultPanel" title="查询结果" class="easyui-panel" style="width:100%;">
	<table id="waterDatagrid" style="width:100%;"></table>
</div>
<div id="waterDataDialog"  style="display:none">
	<form id="waterDataForm" style="margin:10px" >
		<input type="hidden" id=water_name name="water_name"  ></input>
		<input type="hidden" id="CreateOrUpdate"  value="create"></input>
		<input type="hidden" id="create_date"  value=""></input>
		<div class="line-div">
			断面名称：
			<input id="waterName" name="name"  class="easyui-textbox" style="width:120px;"/>
			采样时间：
			<input id="monitorTime" name="monitorTime" type="text" style="width:150px;"/>
		</div>
		<div class="line-div">
			监测频率：
			<select id="MonitorType" class="easyui-combobox" name="MonitorType" style="width:120px;">
			    <option value="1">周测</option>
			    <option value="2">月测</option>			    
			    <option value="3">季测</option>
			    <option value="4">年测</option>
			</select>
		</div>
	</form>
</div>

<div id="water_toolbar">
	<jksb:hasAutority authorityId="007001001">
		<a href="javascript:waterAddData()" id = "waterAddButton" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >录入</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001003">
		<a href="javascript:waterEditData()" id = "waterEditButton" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true" >编辑</a>
	</jksb:hasAutority>
	<jksb:hasAutority authorityId="007001002">
		<a href="javascript:waterDeleData()" id = "waterDeleButton" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true," >删除</a>
	</jksb:hasAutority>
</div>

<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#waterDatagrid').datagrid({
    url:"${ctx}/monitorData/getMonitorDatasPage",
    method:'get',
    pagination:true,
    columns:[[
        {checkbox:true,field:'',title:'' },
        {field:'id',title:'序号',sortable:true},
        {field:'waterName',title:'监测站名称'},
        {field:'water',title:'监测站编码',formatter:function(value,rec){
        	if(rec.water)  
        		return rec.water.code;
        	else  		  
        		return "";
        }},
        {field:'itemName',title:'监测名称'},
        {field:'itemValue',title:'监测值'},
        {field:'company',title:'单位'},
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
    	mergeCellsByField('waterDatagrid', 'waterName','monitorType','monitorDate');
    },
    queryParams:$('#waterSearchConditionForm').getFormData(), 
    toolbar:"#water_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){waterSelectChange(index,row);},
	onUnselect: function(index,row){waterSelectChange(index,row);},
    onDblClickRow:function (index,row){	   //双击行事件 
    	waterDataDialog("录入编辑",row);
    } 
});

function waterSelectChange(index,row){ 		// 选择行事件 通用。
	var selectedNum = $('#waterDatagrid').datagrid('getSelections').length;
	if(selectedNum==1){
		$("#waterEditButton").linkbutton("enable");
		$("#waterDeleButton").linkbutton("enable");
	}else if(selectedNum==0 ){
		$("#waterDeleButton").linkbutton("disable");
		$("#waterEditButton").linkbutton("disable");
		$("#waterDisableButton").linkbutton("disable");
	}else{
		$("#waterEditButton").linkbutton("disable");
		$("#waterDisableButton").linkbutton("disable");
	}
}

$('#waterSearchButton').click(function(){
	$('#waterDatagrid').datagrid('load',$('#waterSearchConditionForm').getFormData());
});
//录入数据
function waterAddData(){
	waterDataDialog("录入数据",null);		 
}
function waterEditData(){
	var selected = $('#waterDatagrid').datagrid('getSelected');
	$('#create_date').val(selected.create_date);
	waterDataDialog("录入编辑",selected);      //该方法 弹出圣诞框内容为页面DIV  water对象由DataGrid 传送 
}
function waterDeleData(){
	var selections = $('#waterDatagrid').datagrid('getSelections');
	var num = selections.length;
	$.messager.confirm('删除确认','确定删除这 '+num+' 项吗?',function(r){
	    if (r){
	    	var Code = "";
	    	var monitorType="";
	    	for(sele in selections){
	    		Code += (selections[sele].water).code;
    			monitorType += selections[sele].monitorType;
	    		if(sele<(num-1)){
	    			Code += ",";
	    			monitorType += ",";

	    		}
	    	}
	    	$.ajax({
	    		url:"${ctx}/manageData/delete",
	    		type:'GET',
	    		data: { 'code':Code,'monitorType':monitorType },  
	    		success:function(data){
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
	    			$("#waterDatagrid").datagrid('reload');
	    			$("#waterDataDialog").dialog("close");
	    		},
	    		error:function(XMLHttpRequest, textStatus, errorThrown){
	    			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
	    		}
	    	});
	    }
	});
}
function waterSave(){
	var monitorType=$('#MonitorType').combobox('getValue');
	var monitorContent='';
	var mVal='';
	var code=$('#waterName').combobox('getValue');
	var waterName=$('#waterName').combobox('getText');
	var CreateOrUpdate=$('#CreateOrUpdate').val();
	var create_date=$('#create_date').val();
	var monitorTime=$('#monitorTime').combobox('getText');
	$('#waterDataForm div:gt(1)>input').each(function(){
		mVal=mVal+$(this).val();
	  });
	if(mVal==''){
		$.messager.alert("出错！","请至少填写一项内容",'error',function(r){
			refId.textbox().next('span').find('input').focus();
			return false;
		});
	}
	$('#waterDataForm div:gt(1)>input').each(function(){
		monitorContent=monitorContent+'##'+$(this).attr('name')+'@@'+$(this).val();
	 });
	$.ajax({
   		url:"${ctx}/manageData/"+CreateOrUpdate,
   		type:'GET',
   		data: { 'monitorContent': monitorContent,'waterCode':code,'waterName':waterName,'create_date':create_date,'monitorType':monitorType,'monitorTime':monitorTime},  
   		success:function(data){
   			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
   			$("#waterDatagrid").datagrid('reload');
   		},
   		error:function(XMLHttpRequest, textStatus, errorThrown){
   			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
   		}
   	}); 	
}

/**
 * 本页面内DIV Dialog
 */
function waterDataDialog(title,selected){
	$("#MonitorType").combobox('setText',"周测");
	$("#waterDataDialog").show(); //先显示，再弹出
    $("#waterDataDialog").dialog({
        title: title,
        width: 600,
        height: 500,
        modal:true,
        buttons:[{
			text:'保存',
			handler:function(){waterSave();}
		},{
			text:'取消',
			handler:function(){$("#waterDataDialog").dialog("close");}
		}]
    });
	if(selected!=null){		
		$(".combo-panel>div:contains('"+selected.waterName+"')").trigger("click");
		$("#MonitorType").combobox('select',selected.monitorType);
		$('#CreateOrUpdate').val('update');
	}else{
		$('#CreateOrUpdate').val('create');
		$('#waterDataForm div:gt(1)>input').val('');
	}
}
/**
 * 设置分页
 */
var p = $('#waterDatagrid').datagrid('getPager'); 
$(p).pagination({ 
    pageSize: 10,			//每页显示的记录条数，默认为15 
    pageList: [10,15,20]
});
/**
 * 父水体选项(先注释先)
 */
/* 	水体名称：
	<input id="parentWaterName" name="parentWaterName"  class="easyui-textbox" style="width:120px;"/>
 */
/*  $('#parentWaterName').combobox({    
	    url:'${ctx}/water/getParents',
	    valueField:'code',
	    textField:'name',
	    method:'GET',
	    editable:true,//不可编辑，只能选择
	 	onSelect:function(value){
	 		waterNameSelect();
	   	} 
	});  */
/*
 * 时间
 */
$('#monitorTime').datetimebox({    
	    required: true,    
	    showSeconds: false   
}); 
/**
 * 断面选项
 */
 $('#waterName').combobox({    
	    url:'${ctx}/water/getWaterByIs_leaf?Is_leaf=true',
	    valueField:'code',
	    textField:'name',
	    method:'GET',
	 	onSelect:function(value){
	 		waterNameSelect();
	   	} 
	}); 
function waterNameSelect(){
	var addOrupdate=$('#CreateOrUpdate').val();
	var monitorType=$('#MonitorType').combobox('getValue');
	var code=$('#waterName').combobox('getValue');
	//alert(addOrupdate+" sdf "+monitorType+"sdf"+code);
	$.ajax({
		url:"${ctx}/manageData/getMonitorEName",
		type:'GET',
		data: { 'code': code,'addOrupdate': addOrupdate,'monitorType':monitorType},  
		success:function(data){
			$('#waterDataForm div:gt(1)').remove();
			if(data.length>0){
				var num=0;
				var inputContent='';
	            for(var index in data) {
	            	var row = data[index];
	            	if(row.ename==''||row.name==''){	    	            		
	            	}else{
    	            	if(addOrupdate=='update'){
	    	            	inputContent=inputContent+'<span style="width:120px;display:inline-block;display:-moz-inline-box;text-align:right">'+row.name+'</span>：<input value="'+row.value+'" name="'+row.id+'"  class="easyui-textbox" style="width:120px;"/>';
	            		}else{
	    	            	inputContent=inputContent+'<span style="width:120px;display:inline-block;display:-moz-inline-box;text-align:right">'+row.name+'</span>：<input id="'+row.ename+'" name="'+row.name+'"  class="easyui-textbox" style="width:120px;"/>';
	            		}
    	            	num++;
	            	}
	            	if(num==2){
	            		$('#waterDataForm').append('<div class="line-div">'+inputContent+'</div>');
	            		num=0;
	            		inputContent='';
	            	}
	            }
	            $('#waterDataForm').append('<div class="line-div">'+inputContent+'</div>');
			}else{
            	if(addOrupdate=='update'){
    				$('#waterDataForm').append('<div class="line-div">该选项还未录入数据</div>');
        		}else{
    				$('#waterDataForm').append('<div class="line-div">该选项还未录入监测项</div>');
        		}
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert('操作失败',"错误提示:"+XMLHttpRequest.responseText);
		}
	});
}
/**
 * 选中监测频率选项
 */
 $('#MonitorType').combobox({ 
		 onSelect: function(rec){ 
			 waterNameSelect();   
	     } 
	}); 
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