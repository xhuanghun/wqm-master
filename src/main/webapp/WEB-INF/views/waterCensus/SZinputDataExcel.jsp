<!DOCTYPE html>
<%@ page language="java"  language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/public/common.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>数据录入</title>
</head>
<body>
<h2>上传Excel文件功能</h2>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="上传" style="width:400px;">
		<div style="padding:10px 60px 20px 60px">
	    <form id="ff" method="post"  enctype="multipart/form-data">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>Excel:</td>
	    			<td><input  class="easyui-filebox" id="excelfile" type="text" name="file"  data-options="prompt:'请选择文件'"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	    </div>
	    </div>
	</div>
	<script>
		function submitForm(){
			$('#ff').form('submit', {    
			    url:'${ctx}/file/waterCensusSZ',    
			    onSubmit: function(){  
			        if ( $("input[type='file']").val() == "" ) {
		    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>请选择一个Excel</div>");
			            return false;
			        } else {
			            file = $("input[type='file']").val();
			            index = file.length;
			            start = index - 4;
			            extension = file.substring(start, index);
			            extension = extension.toLowerCase();
			            if (extension != ".xls" && extension != "xlsx") {
			    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>您选择的不是Excel文件，请重新选择</div>");
			                return false;
			            }
			        }
			    },    
			    success:function(data){   
			    	var data = eval('(' + data + ')');
	    			$.messager.alert('操作结果',"<div style='text-align:center;width:100%;'>"+data.message+"</div>");
			    }    
			});  

		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>

