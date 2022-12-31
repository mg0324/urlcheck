<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　权限设置&gt;添加权限
	</div>
	<div class="funContent">
		<form id="addFunForm" style="width:80%;"
		    action="${pageContext.request.contextPath}/function.do?action=addFunction" method="post" class="funForm">
		    <table>
		    	<tr>
		    		<td align="right">权限名称：</td>
		    		<td><input type="text" name="funName"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">权限描述：</td>
		    		<td><textarea rows="5" cols="40" name="note"></textarea></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">请求资源：</td>
		    		<td><input type="text" id="pwd1" name="resource"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">请求后缀：</td>
		    		<td><input type="text" name="stuffix"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">类型：</td>
		    		<td><input type="number" name="type"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">
		    			请求参数：<br/>
		    			<span class="note">( 请以英文<br/>逗号分隔 )</span>
		    		</td>
		    		<td><input type="text" name="params"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td><input type="hidden" id="addFunMsg" value="${resString }"/></td>
		    		<td><input type="submit" class="btn btn-primary btn-sm" value=" 添加权限  "/></td>
		    		<td></td>
		    	</tr>
		    </table>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$("#addFunForm").validate({
			errorClass: "inputError",
			success: "inputSuccess",
			rules:{
				funName:{
					required:true,
					maxlength:16
				},
				note:{
					required:true,
					minlength:6,
					maxlength:50
				},
				resource:{
					required:true,
					maxlength:32
				},
				params:{
					maxlength:32
				},
				type:{
					required:true
				}
				
			},
			messages:{
				funName:{
					required:"权限名称不能为空",
					maxlength:"最多16个字符"
				},
				note:{
					required:"权限描述不能为空",
					minlength:"最少6个字符",
					maxlength:"最多50个字符"
				},
				resource:{
					required:"请求资源不能为空",
					maxlength:"最多32个字符"
				},
				params:{
					maxlength:"最后32个字符"
				},
				type:{
					required:"类型不能够为空"
				}
			}
		});
		//弹出反馈信息
		alertMsg("addFunMsg",12);
		
	});
</script>    
