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
					<label for="search_gsName">农村供水工程名称</label>
					<input id="search_gsName" name="gsName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_gsCode">农村供水工程编码</label>
					<input id="search_gsCode" name="gsCode" class="easyui-textbox" style="width:120px;"/>
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
    url:"${ctx}/waterCensus/getNCGSGCPage",
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
        
        {field:'getWaterType',title:'取水水源类型',width:'10%'},
        {field:'isReservoir',title:'是否为水库水',width:'10%'},
        {field:'type',title:'工程类型',width:'10%'},
        {field:'beneficiaryNum',title:'受益行政村数量（个）',width:'10%'},
        {field:'gsWay',title:'供水方式',width:'10%'},
        {field:'isWaterIntakingPermit',title:'是否有取水许可证',width:'10%'},
        {field:'waterIntakingPermitCode',title:'取水许可证编号',width:'10%'},
        {field:'isSanitaryPermit',title:'是否取得卫生许可',width:'10%'},
        {field:'sanitaryPermitCode',title:'卫生许可证编号',width:'10%'},
        {field:'beforeLenght',title:'入户前的管网长度（km）',width:'10%'},
        {field:'power',title:'配套功率（kw）',width:'10%'},
        {field:'workStatus',title:'工程建设情况',width:'10%'},
        {field:'workYear',title:'建成时间（年）',width:'10%'},
        {field:'startProvince',title:'建成时间（月）',width:'10%'},
        {field:'workMonth',title:'起点位置 地区',width:'10%'},
        {field:'startWorkYear',title:'开工时间（年）',width:'10%'},
        {field:'startWorkMonth',title:'开工时间（月）',width:'10%'},
        {field:'planScale',title:'设计供水规模（立方米/d）',width:'10%'},
        {field:'gsPopulationNum',title:'设计供水人口(人)',width:'10%'},
        {field:'yearTotal',title:'年实际供水总量（万立方米）',width:'10%'},
        {field:'yearLiveTotal',title:'年实际生活供水量（万立方米）',width:'10%'},
        {field:'yearProductionTotal',title:'年实际生产供水量（万立方米）',width:'10%'},
        {field:'yearPopulationTotal',title:'年实际供水人口 （人）',width:'10%'},
        {field:'excessiveWaterQuality',title:'水质超标项目',width:'10%'},
        {field:'waterPurification',title:'净水处理',width:'10%'},
        {field:'monitorEquipment',title:'水质检测设备',width:'10%'},
        {field:'waterMonitorType',title:'水质检测方式',width:'10%'},
        {field:'gsManageArea',title:'管理单位名称',width:'10%'},
        {field:'startProvince',title:'管理单位代码',width:'10%'},
        {field:'gsPortManageName',title:'管理主体',width:'10%'},
        {field:'feeType',title:'收费形式',width:'10%'},
        {field:'feePrice',title:'计量收费执行居民生活水价（元/立方米）',width:'10%'},
        {field:'feeYearPrice',title:'计量收费年实收水费（万元）',width:'10%'},
        {field:'fixedPrice',title:'固定收费执行居民生活水价（元/户·月）',width:'10%'},
        {field:'fixedYearPrice',title:'固定收费年实收水费（万元）',width:'10%'},
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