<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　角色设置&gt;添加角色
	</div>
	<div class="funContent">
		<form id="addRoleForm" style="width:80%;"
		    action="${pageContext.request.contextPath}/role.do?action=addRole" method="post" class="funForm">
		    <table>
		    	<tr>
		    		<td align="left">角色名称：</td>
		    		<td><input type="text" name="roleName"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="left">角色描述：</td>
		    		<td><textarea rows="6" cols="30" name="note"></textarea></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td><input type="hidden" id="msg" value="${resString }"/></td>
		    		<td><input type="submit" class="btn btn-primary btn-sm" value=" 添加角色  "/></td>
		    		<td></td>
		    	</tr>
		    </table>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$("#addRoleForm").validate({
			errorClass: "inputError",
			success: "inputSuccess",
			rules:{
				roleName:{
					required:true,
					maxlength:16
				},
				note:{
					required:true,
					minlength:6,
					maxlength:50
				}
			},
			messages:{
				roleName:{
					required:"权限名称不能为空",
					maxlength:"最多16个字符"
				},
				note:{
					required:"权限描述不能为空",
					minlength:"最少6个字符",
					maxlength:"最多50个字符"
				}
			}
		});
		//弹出反馈信息
		alertMsg("msg",12);
		
	});
</script>    
