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
					<label for="search_dfName">泵站名称</label>
					<input id="search_dfName" name="dfName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_dfCode">泵站编码</label>
					<input id="search_dfCode" name="dfCode" class="easyui-textbox" style="width:120px;"/>
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
    url:"${ctx}/waterCensus/getDFGCPage",
    method:'get',
    pagination:true,
    columns:[[
        {field:'id',title:'序号',width:'5%'},
        {field:'dfName',title:'堤防名称',width:'10%'},
        {field:'dfCode',title:'堤防编码',width:'10%'},
        {field:'startProvince',title:'起点位置 省',width:'10%'},
        {field:'startCity',title:'起点位置 地区',width:'10%'},
        {field:'startArea',title:'起点位置 县',width:'10%'},
        {field:'startCounty',title:'起点位置 乡（镇）',width:'10%'},
        {field:'startStreet',title:'起点位置 街（村）',width:'10%'},
        {field:'startAreaCode',title:'起点 行政区划代码',width:'10%'},
        {field:'endProvince',title:'终点位置 省',width:'10%'},
        {field:'endCity',title:'终点位置 地区',width:'10%'},
        {field:'endArea',title:'终点位置 县',width:'10%'},
        {field:'endCounty',title:'终点位置 乡',width:'10%'},
        {field:'endStreet',title:'终点位置 村',width:'10%'},
        {field:'endAreaCode',title:'终点 行政区划代码',width:'10%'},
        {field:'startEastLngDeg',title:'起点地理坐标东经（度）',width:'10%'},
        {field:'startEastLngMin',title:'起点地理坐标东经（分）',width:'10%'},
        {field:'startEastLngSec',title:'起点地理坐标东经（秒）',width:'10%'},
        {field:'startNorthLatDeg',title:'起点地理坐标北纬（度）',width:'10%'},
        {field:'startNorthLatMin',title:'起点地理坐标北纬（分）',width:'10%'},
        {field:'startNorthLatSec',title:'起点地理坐标北纬（秒）',width:'10%'},
        {field:'endEastLngDeg',title:'终点地理坐标东经（度）',width:'10%'},
        {field:'endEastLngMin',title:'终点地理坐标东经（分）',width:'10%'},
        {field:'endEastLngSec',title:'终点地理坐标东经（秒）',width:'10%'},
        {field:'endNorthLatDeg',title:'终点地理坐标北纬（度）',width:'10%'},
        {field:'endNorthLatMin',title:'终点地理坐标北纬（分）',width:'10%'},
        {field:'endNorthLatSec',title:'终点地理坐标北纬（秒）',width:'10%'},
        {field:'onWaterName',title:'所在河流(湖泊、海岸)名称',width:'10%'},
        {field:'onWaterNameCode',title:'所在河流(湖泊、海岸)编码',width:'10%'},
        {field:'riverBankType',title:'河流岸别',width:'10%'},
        {field:'isTransboundary',title:'堤防跨界情况',width:'10%'},
        {field:'type',title:'堤防类型',width:'10%'},
        {field:'format',title:'堤防型式',width:'10%'},
        {field:'workStatus',title:'工程建设情况',width:'10%'},
        {field:'workYear',title:'建成时间（年）',width:'10%'},
        {field:'workMonth',title:'建成时间（月）',width:'10%'},
        {field:'startWorkYear',title:'开工时间（年）',width:'10%'},
        {field:'startWorkMonth',title:'开工时间（月）',width:'10%'},
        {field:'workJob',title:'工程任务',width:'10%'},
        {field:'workGrade',title:'堤防级别',width:'10%'},
        {field:'dfTerm',title:'规划防洪(潮)标准［重现期］（年）',width:'10%'},
        {field:'length',title:'堤防长度(m)',width:'10%'},
        {field:'okLength',title:'达到规划防洪（潮）标准的长度(m)',width:'10%'},
        {field:'heightSystem',title:'高程系统',width:'10%'},
        {field:'heightStart',title:'工程等别',width:'10%'},
        {field:'heightEnd',title:'堤顶高程（终点高程(m)）',width:'10%'},
        {field:'planHeight',title:'设计水（高潮）位(m)',width:'10%'},
        {field:'dfHeightMax',title:'堤防高度(m)（最大值）',width:'10%'},
        {field:'dfHeightMin',title:'堤防高度(m)（最小值）',width:'10%'},
        {field:'dfWidthMax',title:'堤顶宽度(m)（最大值）',width:'10%'},        
        {field:'dfWidthMin',title:'堤顶宽度(m)（最小值）',width:'10%'},
        {field:'damNum',title:'水闸数量(个)',width:'10%'},
        {field:'pipeNum',title:'管涵数量(个)',width:'10%'},
        {field:'pumpsNum',title:'泵站数量(处)',width:'10%'},
        {field:'nvertedSiphonNum',title:'倒虹吸数量(个)',width:'10%'},    
        {field:'dfManageArea',title:'提防管理单位名称',width:'10%'},
        {field:'dfManageAreaCode',title:'提防管理单位代码',width:'10%'},
        {field:'dfPortManageName',title:'提防归口管理部门',width:'10%'},
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