<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/public/common.jsp"%>
<html>
<head>
<title>海口市智慧水务管理平台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link type="text/css" href="${ctx }/static/styles/front/header_footer.css" rel="Stylesheet" />
<link rel="stylesheet" href="${ctx }/static/arcgisApi/3.16compact/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="${ctx }/static/arcgisApi/3.16compact/esri/css/esri.css">
<link type="text/css" href="${ctx }/static/styles/front/map.css" rel="Stylesheet" />
</head>
<body class='claro'>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center',iconCls:'icon-ok'" style="padding:5px;">
        	<div id="map">
        		<div id="navToolbar" data-dojo-type="dijit/Toolbar">
    			 <div data-dojo-type="dijit/form/Button" id="zoomin" data-dojo-props="iconClass:'zoominIcon'">缩小</div>
   				 <div data-dojo-type="dijit/form/Button" id="zoomout" data-dojo-props="iconClass:'zoomoutIcon'">放大</div>
    			 <div data-dojo-type="dijit/form/Button" id="zoomfullext" data-dojo-props="iconClass:'zoomfullextIcon'">全图范围</div>
   				 <div data-dojo-type="dijit/form/Button" id="zoomprev" data-dojo-props="iconClass:'zoomprevIcon'">上一范围</div>
    			 <div data-dojo-type="dijit/form/Button" id="zoomnext" data-dojo-props="iconClass:'zoomnextIcon'">下一范围</div>
                 <div data-dojo-type="dijit/form/Button" id="pan" data-dojo-props="iconClass:'panIcon'">平移</div>
     			 <div data-dojo-type="dijit/form/Button" id="deactivate" data-dojo-props="iconClass:'deactivateIcon'">停用</div>
				</div>
        		<!-- <div id="homeButton"></div> -->
			</div>
			<div id="info">
				<div id="legend"></div>
			</div>
        </div>
    </div>
    <script src="${ctx }/static/arcgisApi/3.16compact/init.js"></script>
	<script src="${ctx }/static/js/map/monitoring_points.js"></script>
</body>
</html>
