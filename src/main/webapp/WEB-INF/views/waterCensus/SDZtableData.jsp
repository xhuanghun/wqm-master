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
					<label for="search_sdzName">水电站名称</label>
					<input id="search_sdzName" name="sdzName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_sdzCode">水电站编码</label>
					<input id="search_sdzCode" name="sdzCode" class="easyui-textbox" style="width:120px;"/>
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
    url:"${ctx}/waterCensus/getSDZPage",
    method:'get',
    pagination:true,
    columns:[[
              {field:'id',title:'序号',width:'5%'},
              {field:'province',title:'省',width:'10%'},
              {field:'city',title:'市',width:'10%'},
              {field:'area',title:'区',width:'10%'},
              {field:'county',title:'县',width:'10%'},
              {field:'areaCode',title:'行政区划代码',width:'10%'},
              {field:'sdzName',title:'水电站名称',width:'10%'},
              {field:'sdzCode',title:'水电站编码',width:'10%'},
              {field:'eastLngDeg',title:'(东经)（度）',width:'10%'},
              {field:'eastLngMin',title:'(东经)（分）',width:'10%'},
              {field:'eastLngSec',title:'(东经)（秒）',width:'10%'},
              {field:'northLatDeg',title:'(北纬)（度）',width:'10%'},
              {field:'northLatMin',title:'(北纬)（分）',width:'10%'},
              {field:'northLatSec',title:'(北纬)（秒）',width:'10%'},
              {field:'onWaterResourceNameCode',title:'所在水资源三级区名称及编码',width:'10%'},
              {field:'onWaterName',title:'所在河流（湖泊）名称',width:'10%'},
              {field:'onWaterNameCode',title:'所在河流（湖泊）编码',width:'10%'},
              {field:'islectricity',title:'是否利用水库发电',width:'10%'},
              {field:'reservoirName',title:'水库名称',width:'10%'},
              {field:'reservoirCode',title:'水库编码',width:'10%'},
              {field:'type',title:'水电站类型',width:'10%'},
              {field:'productionPeople',title:'生产安置人口（万人）',width:'10%'},
              {field:'movePeople',title:'搬迁安置人口（万人）',width:'10%'},
              {field:'workStatus',title:'工程建设情况',width:'10%'},
              {field:'workYear',title:'建成时间（年）',width:'10%'},
              {field:'workMonth',title:'建成时间（月）',width:'10%'},
              {field:'startWorkYear',title:'开工时间（月）',width:'10%'},
              {field:'startWorkMonth',title:'开工时间（月）',width:'10%'},
              {field:'workType',title:'工程等别',width:'10%'},
              {field:'workGrade',title:'主要建筑物级别',width:'10%'},
              {field:'capacity',title:'装机容量（kW）',width:'10%'},             
              {field:'output',title:'保证出力（kW）',width:'10%'},
              {field:'ratedHead',title:'额定水头(m)',width:'10%'},
              {field:'pumpsNum',title:'机组台数(台)',width:'10%'},                        
              {field:'averageElectricity',title:'多年平均发电量(万kW·h)',width:'10%'},   
              {field:'yearElectricity',title:'2011年发电量(万kW·h)',width:'10%'},
              {field:'sdzManageArea',title:'水电站管理单位名称',width:'10%'},
              {field:'sdzManageAreaCode',title:'水电站管理单位代码',width:'10%'},             
              {field:'institution',title:'独立核算单位性质',width:'10%'},
              {field:'sdzPortManageName',title:'水电站归口管理部门',width:'10%'},
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