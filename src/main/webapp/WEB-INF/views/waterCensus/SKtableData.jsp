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
					<label for="search_skName">水库名称</label>
					<input id="search_skName" name="skName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_skCode">水库编码</label>
					<input id="search_skCode" name="skCode" class="easyui-textbox" style="width:120px;"/>
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
    url:"${ctx}/waterCensus/getSKPage",
    method:'get',
    pagination:true,
    columns:[[
              {field:'id',title:'序号',width:'5%'},
              {field:'cell1',title:'省',width:'5%'},
              {field:'cell2',title:'地区',width:'5%'},
              {field:'cell3',title:'县',width:'5%'},
              {field:'cell4',title:'乡（镇）',width:'5%'},
              {field:'cell5',title:'行政区划代码',width:'5%'},
              {field:'skName',title:'水库名称',width:'5%'},
              {field:'skCode',title:'水库编码',width:'5%'},
              {field:'cell8',title:'地理坐标(度)（东经）',width:'5%'},
              {field:'cell9',title:'地理坐标(分)（东经）',width:'5%'},
              {field:'cell10',title:'地理坐标(秒)（东经）',width:'5%'},
              {field:'cell11',title:'地理坐标(度)（北纬）',width:'5%'},
              {field:'cell12',title:'地理坐标(分)（北纬）',width:'5%'},
              {field:'cell13',title:'地理坐标(秒)（北纬）',width:'5%'},
              {field:'cell14',title:'所在水资源三级区名称及编码',width:'5%'},
              {field:'cell15',title:'所在河流（湖泊）名称',width:'5%'},
              {field:'cell16',title:'所在河流（湖泊）编码',width:'5%'},
              {field:'cell17',title:'水库类型',width:'5%'},
              {field:'cell18',title:'类型',width:'5%'},
              {field:'cell19',title:'挡水主坝类型按材料分',width:'5%'},
              {field:'cell20',title:'挡水主坝类型按结构分',width:'5%'},
              {field:'cell21',title:'主要泄洪建筑物型式',width:'5%'},
              {field:'cell22',title:'坝址控制流域面积（平方公里）',width:'5%'},
              {field:'cell23',title:'坝址多年平均径流量（万立方米）',width:'5%'},
              {field:'cell24',title:'生产安置人口（万人）',width:'5%'},
              {field:'cell25',title:'搬迁安置人口（万人）',width:'5%'},
              {field:'cell26',title:'工程建设情况',width:'5%'},
              {field:'cell27',title:'建成时间（年）',width:'5%'},
              {field:'cell28',title:'建成时间（月）',width:'5%'},
              {field:'cell29',title:'开工时间（年）',width:'5%'},
              {field:'cell30',title:'开工时间（月）',width:'5%'},
              {field:'cell31',title:'水库调节性能',width:'5%'},
              {field:'cell32',title:'工程等别',width:'5%'},
              {field:'cell33',title:'主坝级别',width:'5%'},
              {field:'cell34',title:'主坝尺寸坝高（m）',width:'5%'},
              {field:'cell35',title:'主坝尺寸坝长（m）',width:'5%'},
              {field:'cell36',title:'最大泄洪流量（立方米/S）',width:'5%'},
              {field:'cell37',title:'高程系统',width:'5%'},
              {field:'cell38',title:'坝顶高程(m)',width:'5%'},
              {field:'cell39',title:'设计洪水标准［重现期］（年）',width:'5%'},
              {field:'cell40',title:'校核洪水标准［重现期］（年）',width:'5%'},
              {field:'cell41',title:'校核洪水位(m)',width:'5%'},
              {field:'cell42',title:'设计洪水位(m)',width:'5%'},
              {field:'cell43',title:'防洪高水位(m)',width:'5%'},
              {field:'cell44',title:'正常蓄水位(m)',width:'5%'},
              {field:'cell45',title:'防洪限制水位(m)',width:'5%'},
              {field:'cell46',title:'死水位(m)',width:'5%'},
              {field:'cell47',title:'总库容（万立方米）',width:'5%'},
              {field:'cell48',title:'调洪库容（万立方米）',width:'5%'},
              {field:'cell49',title:'防洪库容（万立方米）',width:'5%'},
              {field:'cell50',title:'兴利库容（万立方米）',width:'5%'},
              {field:'cell51',title:'死库容(万立方米)',width:'5%'},
              {field:'cell52',title:'正常蓄水位相应水面面积（平方公里）',width:'5%'},
              {field:'cell53',title:'防洪',width:'5%'},
              {field:'cell54',title:'发电',width:'5%'},
              {field:'cell55',title:'供水',width:'5%'},
              {field:'cell56',title:'灌溉',width:'5%'},
              {field:'cell57',title:'航运',width:'5%'},
              {field:'cell58',title:'养殖',width:'5%'},
              {field:'cell59',title:'其它',width:'5%'},
              {field:'cell60',title:'重要保护对象',width:'5%'},
              {field:'cell61',title:'设计年供水量',width:'5%'},
              {field:'cell62',title:'2011年供水量(万立方米)',width:'5%'},
              {field:'cell63',title:'2011年供水量数据来源',width:'5%'},
              {field:'cell64',title:'取水口数量（个）',width:'5%'},
              {field:'cell65',title:'供水对象',width:'5%'},
              {field:'cell66',title:'设计灌溉面积（万亩）',width:'5%'},
              {field:'cell67',title:'灌溉对象：灌区名称',width:'5%'},
              {field:'cell68',title:'水库管理单位名称',width:'5%'},
              {field:'cell69',title:'水库管理单位代码',width:'5%'},
              {field:'cell70',title:'水库归口管理部门',width:'5%'},
              {field:'cell71',title:'是否完成划界',width:'5%'},
              {field:'cell72',title:'是否完成确权',width:'5%'},
              {field:'cell73',title:'填表人员',width:'5%'},
              {field:'cell74',title:'填表联系人电话',width:'5%'},
              {field:'cell75',title:'复核人员',width:'5%'},
              {field:'cell76',title:'复核人联系电话',width:'5%'},
              {field:'cell77',title:'审查人员',width:'5%'},
              {field:'cell78',title:'审核标志',width:'5%'},
              {field:'cell79',title:'地区审核',width:'5%'},
              {field:'cell80',title:'省级审核',width:'5%'},
              {field:'cell81',title:'中央审核',width:'5%'},
              
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