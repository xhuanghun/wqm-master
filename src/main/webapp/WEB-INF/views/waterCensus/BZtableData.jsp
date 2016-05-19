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
					<label for="search_waterName">泵站名称</label>
					<input id="search_waterName" name="bzName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_waterCode">泵站编码</label>
					<input id="search_waterCode" name="bzCode" class="easyui-textbox" style="width:120px;"/>
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
<script type="text/javascript">
/**
 *  datagrid 初始化 
 */
$('#waterDatagrid').datagrid({
    url:"${ctx}/waterCensus/getBZPage",
    method:'get',
    pagination:true,
    columns:[[
        {field:'id',title:'序号',width:'5%'},
        {field:'province',title:'省',width:'10%'},
        {field:'city',title:'市',width:'10%'},
        {field:'area',title:'区',width:'10%'},
        {field:'county',title:'县',width:'10%'},
        {field:'street',title:'街道/村',width:'10%'},
        {field:'areaCode',title:'行政区划代码',width:'10%'},
        {field:'bzName',title:'泵站名称',width:'10%'},
        {field:'bzCode',title:'泵站编码',width:'10%'},
        {field:'eastLngDeg',title:'(东经)（度）',width:'10%'},
        {field:'eastLngMin',title:'(东经)（分）',width:'10%'},
        {field:'eastLngSec',title:'(东经)（秒）',width:'10%'},
        {field:'northLatDeg',title:'(北纬)（度）',width:'10%'},
        {field:'northLatMin',title:'(北纬)（分）',width:'10%'},
        {field:'northLatSec',title:'(北纬)（秒）',width:'10%'},
        {field:'onWaterResourceNameCode',title:'所在水资源三级区名称及编码',width:'10%'},
        {field:'onWaterName',title:'所在河流(湖泊、水库、渠道)名称',width:'10%'},
        {field:'onWaterNameCode',title:'所在河流(湖泊、水库、渠道)编码',width:'10%'},
        {field:'onIrrigationName',title:'所在灌区(引调水工程)名称',width:'10%'},
        {field:'onIrrigationNameCode',title:'所在灌区(引调水工程)编码',width:'10%'},
        {field:'type',title:'泵站类型',width:'10%'},
        {field:'isJWork',title:'是否为闸站工程',width:'10%'},
        {field:'isQWork',title:'是否为引泉工程',width:'10%'},
        {field:'workStatus',title:'工程建设情况',width:'10%'},
        {field:'workYear',title:'建成时间（年）',width:'10%'},
        {field:'workMonth',title:'建成时间（月）',width:'10%'},
        {field:'startWorkYear',title:'开工时间（月）',width:'10%'},
        {field:'startWorkMonth',title:'开工时间（月）',width:'10%'},
        {field:'workJob',title:'工程任务',width:'10%'},
        {field:'workType',title:'工程等别',width:'10%'},
        {field:'workGrade',title:'主要建筑物级别',width:'10%'},
        {field:'capacity',title:'装机流量(立方米/秒)',width:'10%'},
        {field:'power',title:'装机功率(kW)',width:'10%'},
        {field:'planDistance',title:'设计扬程(m)',width:'10%'},
        {field:'pumpsNum',title:'水泵数量(台)',width:'10%'},
        {field:'bzManageArea',title:'泵站管理单位名称',width:'10%'},
        {field:'bzManageAreaCode',title:'泵站管理单位代码',width:'10%'},
        {field:'bzPortManageName',title:'泵站归口管理部门',width:'10%'},
        {field:'isDivide',title:'是否完成划界',width:'10%'},
        {field:'isRights',title:'是否完成确权',width:'10%'},
        {field:'fillName',title:'填表人员',width:'10%'},
        {field:'fillNameTel',title:'填表联系人电话',width:'10%'},
        {field:'reviewName',title:'复核人员',width:'10%'},
        {field:'reviewNameTel',title:'复核人联系电话',width:'10%'},
        {field:'checkName',title:'审查人员',width:'10%'},
        {field:'checkStatus',title:'审核标志',width:'10%'},
        {field:'areaCheck',title:'地区审核',width:'10%'},
        {field:'provinceCheck',title:'省级审核',width:'10%'},
        {field:'centralCheck',title:'中央审核',width:'10%'},
    ]],
    queryParams:$('#waterSearchConditionForm').getFormData(), 
    toolbar:"#water_toolbar",					//根据权限动态生成按钮
	onSelect: function(index,row){waterSelectChange(index,row);},
	onUnselect: function(index,row){waterSelectChange(index,row);}, 
});

$('#waterSearchButton').click(function(){
	$('#waterDatagrid').datagrid('load',$('#waterSearchConditionForm').getFormData());
});
/**
 * 设置分页
 */
var p = $('#waterDatagrid').datagrid('getPager'); 
$(p).pagination({ 
    pageSize: 10,			//每页显示的记录条数，默认为15 
    pageList: [10,15,20]
});
</script>
</div>