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
					<label for="search_ydsName">水闸名称</label>
					<input id="search_ydsName" name="ydsName" class="easyui-textbox" style="width:120px;"/>
				</td>
				<td width="18%" align="center" style="min-width:150px">
					<label for="search_ydsCode">水闸编码</label>
					<input id="search_ydsCode" name="ydsCode" class="easyui-textbox" style="width:120px;"/>
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
    url:"${ctx}/waterCensus/getYDSGCPage",
    method:'get',
    pagination:true,
    columns:[[
              {field:'id',title:'序号',width:'5%'},
              {field:'cell1',title:'行政区划',width:'5%'},
              {field:'cell2',title:'行政区划代码',width:'5%'},
              {field:'ydsName',title:'引调水工程名称',width:'5%'},
              {field:'ydsCode',title:'引调水工程编码',width:'5%'},
              {field:'cell5',title:'取水水源类型',width:'5%'},
              {field:'cell6',title:'取水水源名称',width:'5%'},
              {field:'cell7',title:'取水水源编码',width:'5%'},
              {field:'cell8',title:'取水水源取水口数量',width:'5%'},
              {field:'cell9',title:'调出流域名称',width:'5%'},
              {field:'cell10',title:'调入流域名称',width:'5%'},
              {field:'cell11',title:'调出水资源三级区名称',width:'5%'},
              {field:'cell12',title:'调入水资源三级区名称',width:'5%'},
              {field:'cell13',title:'工程范围输水线路区',width:'5%'},
              {field:'cell14',title:'工程范围受水区',width:'5%'},
              {field:'cell15',title:'引调水方式',width:'5%'},
              {field:'cell16',title:'工程建设情况',width:'5%'},
              {field:'cell17',title:'建成时间（年）',width:'5%'},
              {field:'cell18',title:'建成时间（月）',width:'5%'},
              {field:'cell19',title:'开工时间（年）',width:'5%'},
              {field:'cell20',title:'开工时间（月）',width:'5%'},
              {field:'cell21',title:'工程等别',width:'5%'},
              {field:'cell22',title:'设计引水流量（立方米/s）',width:'5%'},
              {field:'cell23',title:'设计年引水量(万立方米)',width:'5%'},
              {field:'cell24',title:'设计灌溉面积（万亩)',width:'5%'},
              {field:'cell25',title:'输水干线总长度(km)',width:'5%'},
              {field:'cell26',title:'输水干线上的建筑物数量水闸数量(个)',width:'5%'},
              {field:'cell27',title:'输水干线上的建筑物数量泵站数量(处)',width:'5%'},
              {field:'cell28',title:'输水干线上的建筑物数量渡槽数量(个)',width:'5%'},
              {field:'cell29',title:'输水干线上的建筑物数量倒虹吸数量(个)',width:'5%'},
              {field:'cell30',title:'工程任务',width:'5%'},
              {field:'cell31',title:'2011年引水量(万立方米)',width:'5%'},
              {field:'cell32',title:'数据来源',width:'5%'},             
              {field:'cell33',title:'引调水工程管理单位名称',width:'5%'},
              {field:'cell34',title:'引调水工程管理单位代码',width:'5%'},
              {field:'cell35',title:'引调水工程归口管理部门',width:'5%'},
              {field:'cell36',title:'是否完成划界',width:'5%'},
              {field:'cell37',title:'是否完成确权',width:'5%'},
              {field:'cell38',title:'填表人员',width:'5%'},
              {field:'cell39',title:'填表联系人电话',width:'5%'},
              {field:'cell40',title:'复核人员',width:'5%'},
              {field:'cell41',title:'复核人联系电话',width:'5%'},
              {field:'cell42',title:'审查人员',width:'5%'},
              {field:'cell43',title:'审核标志',width:'5%'},
              {field:'cell44',title:'地区审核',width:'5%'},
              {field:'cell45',title:'省级审核',width:'5%'},
              {field:'cell46',title:'中央审核',width:'5%'},
              
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