<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　用户角色设置&gt;变更用户
	</div>
	<div class="funContent">
		<form id="changeUserForm" style="width:80%;" onsubmit="handleRoleList();"
		    action="${pageContext.request.contextPath}/admin.do?action=changeUser" method="post" class="funForm">
		    <table>
		    	<tr>
		    		<td align="right">用户编号：</td>
		    		<td>${user.userId }<input type="hidden" value="${user.userId }" name="userId"/>
		    			<input type="hidden" value="${type }" name="type"/>
		    			<input type="hidden" value="${user.userType }" id="userType"/>
		    			
		    		</td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">用户名：</td>
		    		<td>${user.userName }</td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">用户类型：</td>
		    		<td>
		    			<c:if test="${user.userType eq 1 }">超级管理员</c:if>
		    			<c:if test="${user.userType eq 2 }">普通管理员</c:if>
		    			<c:if test="${user.userType eq 3 }">个人会员</c:if>
		    			<c:if test="${user.userType eq 4 }">企业会员</c:if>
		    		</td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">改变用户类型为：</td>
		    		<td>
		    			<select name="userType">
		    				<option id="_ut1" value="1">1-超级管理员</option>
		    				<option id="_ut2" value="2">2-普通管理员</option>
		    				<option id="_ut3" value="3">3-个人会员</option>
		    				<option id="_ut4" value="4">4-企业会员</option>
		    			</select>
		    		</td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td align="right">角色列表：</td>
		    		<td>
		    			<% int i=0; %>
		    			<c:forEach items="${roles }" var="role">
		    				<% i++; %>
		    				<span style="width:130px;display: inline-block;">
		    					<input type="checkbox" class="allRole" id="role_${role.roleId }" value="${role.roleId }"/>${role.roleName }
		    				</span>
		    				<%
		    					if(i%4==0){
		    						%>
		    							<br/>
		    						<%
		    					}
		    				%>
		    			</c:forEach>
		    		</td>
		    		<td>
		    			<c:forEach items="${user.roleList }" var="r">
		    				<input type="hidden" class="havaRole" value="${r.roleId }"/>
		    			</c:forEach>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td></td>
		    		<td><span class="warning">变更用户角色后，需要重新登录该用户才能生效。</span></td>
		    		<td></td>
		    	</tr>
		    	<tr>
		    		<td><input type="hidden" id="msg" value="${resString }"/>
		    			<input type="hidden" id="newRoleParams" name="newRoleParams" value=""/>
		    			<input type="hidden" id="oldRoleParams" name="oldRoleParams" value=""/>
		    		</td>
		    		<td><input type="submit" class="btn btn-primary btn-sm" value=" 变更用户角色  "/> 
		    			<a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/admin.do?action=listUser&type=${type}"> 返回用户列表 </a>
		    		</td>
		    		<td>
		    		</td>
		    	</tr>
		    </table>
		</form>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	var _selectRoleId = "0";
	var _oldRoleId = "0";
	$(function(){
		//选中user中已经有的角色
		$(".havaRole").each(function(index,dom){
			var havaRoleId = $(this).val();
			$("#role_"+havaRoleId).attr("checked","checked");
		});
		//选中select下的option
		var _ut = $("#userType").val();
		$("#_ut"+_ut).attr("selected",true);
		//弹出反馈信息
		alertMsg("msg",12);
		
	});
	function handleRoleList(){
		$(".allRole").each(function(index,dom){
			if(this.checked)
				_selectRoleId += "," + $(this).val();
		});
		$(".havaRole").each(function(index,dom){
			_oldRoleId += "," + $(this).val();
		});
		$("#newRoleParams").val(_selectRoleId);
		$("#oldRoleParams").val(_oldRoleId);
	}
</script>    
