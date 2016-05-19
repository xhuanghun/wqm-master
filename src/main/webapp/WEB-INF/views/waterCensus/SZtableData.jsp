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
					<label for="search_szName">水闸名称</label>
					<input id="search_szName" name="szName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_szCode">水闸编码</label>
					<input id="search_szCode" name="szCode" class="easyui-textbox" style="width:120px;"/>
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
    url:"${ctx}/waterCensus/getSZPage",
    method:'get',
    pagination:true,
    columns:[[
              {field:'id',title:'序号',width:'5%'},
              {field:'cell1',title:'省',width:'5%'},
              {field:'cell2',title:'地区',width:'5%'},
              {field:'cell3',title:'县',width:'5%'},
              {field:'cell4',title:'乡（镇）',width:'5%'},
              {field:'cell5',title:'街（村）',width:'5%'},
              {field:'cell6',title:'行政区划代码',width:'5%'},
              {field:'szName',title:'水闸名称',width:'5%'},
              {field:'szCode',title:'水闸编码',width:'5%'},
              {field:'cell9',title:'地理坐标(度)（东经）',width:'5%'},
              {field:'cell10',title:'地理坐标(分)（东经）',width:'5%'},
              {field:'cell11',title:'地理坐标(秒)（东经）',width:'5%'},
              {field:'cell12',title:'地理坐标(度)（北纬）',width:'5%'},
              {field:'cell13',title:'地理坐标(分)（北纬）',width:'5%'},
              {field:'cell14',title:'地理坐标(秒)（北纬）',width:'5%'},
              {field:'cell15',title:'所在水资源三级区名称及编码',width:'5%'},
              {field:'cell16',title:'所在河流（湖、库、渠、海堤）名称',width:'5%'},
              {field:'cell17',title:'所在河流（湖、库、渠、海堤）编码',width:'5%'},
              {field:'cell18',title:'所在灌区(引调水工程)名称',width:'5%'},
              {field:'cell19',title:'所在灌区(引调水工程)编码',width:'5%'},
              {field:'cell20',title:'是否为闸站工程',width:'5%'},
              {field:'cell21',title:'是否为套闸工程',width:'5%'},
              {field:'cell22',title:'工程建设情况',width:'5%'},
              {field:'cell23',title:'建成时间（年）',width:'5%'},
              {field:'cell24',title:'建成时间（月）',width:'5%'},
              {field:'cell25',title:'开工时间（年）',width:'5%'},
              {field:'cell26',title:'开工时间（月）',width:'5%'},
              {field:'cell27',title:'工程等别',width:'5%'},
              {field:'cell28',title:'主要建筑物级别',width:'5%'},
              {field:'cell29',title:'闸孔数量（孔）',width:'5%'},
              {field:'cell30',title:'闸孔总净宽（米）',width:'5%'},
              {field:'cell31',title:'副闸闸孔数量（孔）',width:'5%'},
              {field:'cell32',title:'副闸闸孔总净宽（米）',width:'5%'},
              {field:'cell33',title:'水闸类型',width:'5%'},
              {field:'cell34',title:'分(泄)洪闸过闸流量(立方米/秒)',width:'5%'},
              {field:'cell35',title:'分(泄)洪闸设计洪水标准［重现期］（年）',width:'5%'},
              {field:'cell36',title:'分(泄)洪闸校核洪水标准［重现期］（（年）',width:'5%'},
              {field:'cell37',title:'节制闸过闸流量(立方米/秒)',width:'5%'},
              {field:'cell38',title:'节制闸设计洪水标准［重现期］（年）',width:'5%'},
              {field:'cell39',title:'节制闸校核洪水标准［重现期］（年）',width:'5%'},
              {field:'cell40',title:'排(退)水闸过闸流量(立方米/秒)',width:'5%'},
              {field:'cell41',title:'排(退)水闸设计洪水标准［重现期］（年）',width:'5%'},
              {field:'cell42',title:'排(退)水闸校核洪水标准［重现期］（年）',width:'5%'},
              {field:'cell43',title:'排(退)水闸是否为引排双向闸',width:'5%'},
              {field:'cell44',title:'引(进)水闸过闸流量(立方米/秒)',width:'5%'},
              {field:'cell45',title:'引(进)水闸设计洪水标准［重现期］（年）',width:'5%'},
              {field:'cell46',title:'引(进)水闸校核洪水标准［重现期］（年）',width:'5%'},
              {field:'cell47',title:'引(进)水闸引水能力(万立方米)',width:'5%'},
              {field:'cell48',title:'引(进)水闸引水用途供水对象',width:'5%'},
              {field:'cell49',title:'引(进)水闸引水用途灌区名称',width:'5%'},
              {field:'cell50',title:'引(进)水闸是否为引排双向闸',width:'5%'},
              {field:'cell51',title:'挡潮闸过闸流量(立方米/秒)',width:'5%'},
              {field:'cell52',title:'挡潮闸设计潮水标准［重现期］（年）',width:'5%'},
              {field:'cell53',title:'挡潮闸校核潮水标准［重现期］（年）',width:'5%'},
              {field:'cell54',title:'橡胶坝坝高（米）',width:'5%'},
              {field:'cell55',title:'橡胶坝坝长（米）',width:'5%'},
              {field:'cell56',title:'橡胶坝高程系统',width:'5%'},
              {field:'cell57',title:'橡胶坝坝顶高程（米）',width:'5%'},
              {field:'cell58',title:'水闸管理单位名称',width:'5%'},
              {field:'cell59',title:'水闸管理单位代码',width:'5%'},
              {field:'cell60',title:'水闸归口管理部门',width:'5%'},
              {field:'cell61',title:'是否完成划界',width:'5%'},
              {field:'cell62',title:'是否完成确权',width:'5%'},
              {field:'cell63',title:'填表人员',width:'5%'},
              {field:'cell64',title:'填表联系人电话',width:'5%'},
              {field:'cell65',title:'复核人员',width:'5%'},
              {field:'cell66',title:'复核人联系电话',width:'5%'},
              {field:'cell67',title:'审查人员',width:'5%'},
              {field:'cell68',title:'审核标志',width:'5%'},
              {field:'cell69',title:'地区审核',width:'5%'},
              {field:'cell70',title:'省级审核',width:'5%'},
              {field:'cell71',title:'中央审核',width:'5%'},
              
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