<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　用户设置&gt;新增管理员
	</div>
	<div class="funContent">
		<form id="addAdminForm" style="width:80%;"
		    action="${pageContext.request.contextPath}/admin.do?action=addAdmin" method="post" class="funForm">
		    <table>
		    	<tr>
		    		<td>管理员用户名：</td>
		    		<td><input type="text" name="userName"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td>　管理员密码：</td>
		    		<td><input type="password" id="password" name="password"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td>　　确认密码：</td>
		    		<td><input type="password" name="password2"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td><input type="hidden" id="msg" value="${resString }"/></td>
		    		<td><input type="submit" class="btn btn-primary btn-sm" value=" 添加管理员  "/></td>
		    		<td></td>
		    	</tr>
		    </table>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$("#addAdminForm").validate({
			errorClass: "inputError",
			success: "inputSuccess",
			rules:{
				userName:{
					required:true,
					minlength:6,
					maxlength:16
				},
				password:{
					required:true,
					minlength:6,
					maxlength:16
				},
				password2:{
					required:true,
					minlength:6,
					maxlength:16,
					equalTo:"#password"
				}
			},
			messages:{
				userName:{
					required:"用户名不能为空",
					minlength:"最少6个字符",
					maxlength:"最多16个字符"
				},
				password:{
					required:"密码不能为空",
					minlength:"最少6个字符",
					maxlength:"最多16个字符"
				},
				password2:{
					required:"确认密码不能为空",
					minlength:"最少6个字符",
					maxlength:"最多16个字符",
					equalTo:"两次密码不一致"
				}
			}
		});
		//弹出反馈信息
		alertMsg("msg",12);
		
	});
</script>    
