<%@ page language="java"  language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	#baseInfoTable td.query{
		font-size:12px;
		padding:0 2px;
 		text-align:center; 
	}
	#baseInfoTable .red-line-border{
		border:1px solid red;
	}
	 
	#baseInfoTable td{border:none;height:30px;width:20%}
	#baseInfoTable table{ border:none;}
	#selfsupp_search_table span{color:blue;}
	#baseInfoTable td{border:1px solid #95B8E7;}
</style>
<div id="monitorDataDetail_container" style="width:100%;height:100%;">
		<input type="hidden" id="code" name="code" value="${waterZD.code }"  ></input>
		<div class="line-div" style="display:none">
			采样时间：
			<input id="monitorTime" name="monitorTime"  class="easyui-textbox" style="width:200px;"/>
		</div>
	<div id="monitorDataDetailTabs" class="easyui-tabs" style="width:100%;height:100%;">
		<div title="基本信息" data-options="fit:true" style="padding-bottom:10px;">
		<table id="baseInfoTable" style="width:99%;cellspacing:0;cellpadding:0;border-collapse:collapse;" align="center"  border="0" cellspacing="0" >
			<tr>
				<td width="50%">水体名称:</td><td width="50%">${water.name} </td>
			</tr>
			<tr>
				<td width="50%">监测站点名称:</td><td width="50%">${waterZD.name} </td>
			</tr>
			<tr>
				<td width="50%">所在区域:</td><td width="50%">${waterZD.area.name} </td>
			</tr>
		</table>
		</div>
		<div  title="监测信息" class="easyui-panel" style="width:100%;">
			<table id="monitorDataTable" style="width:100%;"></table>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$('#monitorDataDetailTabs').tabs({
 	    width:550,
   	   	height:550,
        plain:true,
        onSelect:function(title,index){
            if(title=='监测信息'){
            	$('.line-div').show();
            }else{
            	$('.line-div').hide();
            }
        }
    });
});
/**
 *  datagrid 初始化 
 */
$('#monitorDataTable').datagrid({
    url:"${ctx}/water/getMonitorData?code="+$('#code').val(),
    method:'get',
    pagination:false,
    columns:[[
        {field:'id',title:'序号'},
        {field:'waterName',title:'监测站名称'},
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
        	else
        		return "未知";
        }},
        {field:'company',title:'单位'},
        {field:'     ',title:'标准限值',width:30},
        {field:'     ',title:'是否达标',width:30},
        {field:'monitorDate',title:'采样时间'},
    ]],
    onLoadSuccess: function (rowData) {   	 
    	mergeCellsByField('monitorDataTable', 'waterName');
    },
});
/**
 * 设置分页
 */
/* var p = $('#monitorDataTable').datagrid('getPager'); 
$(p).pagination({ 
    pageSize: 10,			//每页显示的记录条数，默认为15 
    pageList: [10,15,20]
}); */
/*
 * 时间
 */
 $("#monitorTime").combobox({
	    url:'${ctx}/water/getMonitorTime?code='+$('#code').val(),
	    valueField:'id',
	    textField:'time',
	    method:'GET',
	 	onSelect:function(value){
	 		$('#monitorDataTable').datagrid('load',{
	 			monitorTime:value.time,
	 		});


	   	},
 		onLoadSuccess:function(){
 			$('#monitorTime').combobox('select',1);
 		}
});
/**
 * EasyUI DataGrid根据字段动态合并单元格
 * 参数 tableID 要合并table的id
 * 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
 */
 function mergeCellsByField(tableID, colList) {
     var ColArray = colList.split(",");
     var tTable = $("#" + tableID);
     var TableRowCnts = tTable.datagrid("getRows").length;
     var tmpA;
     var tmpB;
     var PerTxt = "";
     var CurTxt = "";
     var alertStr = "";
     for(j = ColArray.length - 1; j >= 0; j--) {
         PerTxt = "";
         tmpA = 1;
         tmpB = 0;

         for (i = 0; i <= TableRowCnts; i++) {
             if (i == TableRowCnts) {
                 CurTxt = "";
             }
             else {
                 CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
             }
             if (PerTxt == CurTxt) {
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
         }
     }
 }
</script>