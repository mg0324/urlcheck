<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　角色设置&gt;变更角色
	</div>
	<div class="funContent">
		<form id="changeRoleForm" style="width:85%;" onsubmit="handleFunList();"
		    action="${pageContext.request.contextPath}/role.do?action=changeRole" method="post" class="funForm">
		    <table>
		    	<tr>
		    		<td style="width: 100px;" align="right">角色编号：</td>
		    		<td>${role.roleId }<input type="hidden" value="${role.roleId }" name="roleId"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">角色名称：</td>
		    		<td><input type="text" name="roleName" value="${role.roleName }"/></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">角色描述：</td>
		    		<td><textarea rows="5" cols="50" name="note">${role.note }</textarea></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">权限列表：</td>
		    		<td>
		    			<% int i = 1; %>
		    			<c:forEach items="${funs }" var="fun">
		    				<span style="width: 200px;display: inline-block;"><input type="checkbox" class="allFun" id="fun_${fun.funId }" value="${fun.funId }"/>${fun.funName }</span>
		    			<% 
		    				if(i%4==0){
		    				%>
		    					<br/>
		    				<% 
		    				}
		    				i++; 
		    			%>
		    			</c:forEach>
					</td>
					<td>
		    			<c:forEach items="${role.funList }" var="f">
		    				<input type="hidden" class="haveFun" value="${f.funId }"/>
		    			</c:forEach>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td>　</td>
		    		<td>　</td>
		    		<td>　</td>
		    	</tr>
		    	<tr>
		    		<td><input type="hidden" id="msg" value="${resString }"/>
		    			<input type="hidden" id="newFunParams" name="newFunParams" value=""/>
		    			<input type="hidden" id="oldFunParams" name="oldFunParams" value=""/>
		    			</td>
		    		<td><input type="submit" class="btn btn-primary btn-sm" value=" 变更角色  "/> 
		    			<a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/role.do?action=listRole"> 返回角色列表 </a>
		    		</td>
		    		<td>
		    		</td>
		    	</tr>
		    </table>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	var _selectFunId = "0";
	var _oldFunId = "0";
	$(function(){
		//选中role中已经有的权限
		$(".haveFun").each(function(index,dom){
			var haveFunId = $(this).val();
			$("#fun_"+haveFunId).attr("checked","checked");
		});
		$("#changeRoleForm").validate({
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
					required:"角色名称不能为空",
					maxlength:"最多16个字符"
				},
				note:{
					required:"角色描述不能为空",
					minlength:"最少6个字符",
					maxlength:"最多50个字符"
				}
			}
		});
		//弹出反馈信息
		alertMsg("msg",12);
		
	});
	function handleFunList(){
		$(".allFun").each(function(index,dom){
			if(this.checked)
				_selectFunId += "," + $(this).val();
		});
		$(".haveFun").each(function(index,dom){
			_oldFunId += "," + $(this).val();
		});
		$("#newFunParams").val(_selectFunId);
		$("#oldFunParams").val(_oldFunId);
	}
</script>    
