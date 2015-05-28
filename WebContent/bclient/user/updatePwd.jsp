<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　用户设置&gt;修改密码
	</div>
	<div class="funContent">
		<form id="updatePwdForm"
		    action="${pageContext.request.contextPath}/admin.do?action=updatePwd" method="post" class="funForm">
		    <table>
		    	<tr>
		    		<td>当前用户：</td>
		    		<td>${loginUser.userName }</td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td>原　密码：</td>
		    		<td><input type="password" name="password"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td>新　密码：</td>
		    		<td><input type="password" id="pwd1" name="newPassword1"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td>确认密码：</td>
		    		<td><input type="password" name="newPassword2"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td><input type="hidden" id="updateMsg" value="${resString }"/></td>
		    		<td><input type="submit" class="btn btn-primary btn-sm" value=" 修改密码 "/></td>
		    		<td></td>
		    	</tr>
		    </table>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$("#updatePwdForm").validate({
			errorClass: "inputError",
			success: "inputSuccess",
			rules:{
				password:{
					required:true,
					minlength:6,
					maxlength:16
				},
				newPassword1:{
					required:true,
					minlength:6,
					maxlength:16
				},
				newPassword2:{
					required:true,
					minlength:6,
					maxlength:16,
					equalTo:"#pwd1"
				}
				
			},
			messages:{
				password:{
					required:"您的原密码不能为空",
					minlength:"您的原密码不能少于6个字符",
					maxlength:"您的原密码不能多余16个字符"
				},
				newPassword1:{
					required:"您的新密码不能为空",
					minlength:"您的新密码不能少于6个字符",
					maxlength:"您的新密码不能多余16个字符"
				},
				newPassword2:{
					required:"您的确认新密码不能为空",
					minlength:"您的确认新密码不能少于6个字符",
					maxlength:"您的确认新密码不能多余16个字符",
					equalTo:"您的两次新密码输入不一致"
				}
			}
		});
		//弹出反馈信息
		alertMsg("updateMsg",12);
		
	});
</script>    
