<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mg" uri="http://room.mgang.com/checkFunction" %>
<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/bclient/css/user/bt_table.css" type="text/css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/3td/cus-icons/cus-icons.css">
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　角色设置&gt;查看角色列表
	</div>
	<div class="funContent">
		    <table class="table table-condensed table-bordered table-striped table-hover table12" cellspacing="0" cellpadding="0">
		    	<tr>
		    		<th>操作</th>
		    		<th>角色编号</th>
		    		<th>权限名称</th>
		    		<th>角色描述</th>
		    		
		    	</tr>
		    	<c:forEach items="${page.list }" var="role">
		    		<tr>
		    			<td>
				    		<c:if test="${mg:check(loginUser,'role.do?toChangeRoleUI&roleId') }">
				    			<a title="变更角色" href="${pageContext.request.contextPath}/role.do?action=toChangeRoleUI&roleId=${role.roleId}" onclick="return confirm('您真的要修改该角色吗？')"><i class="cus-group_edit"></i></a>
				    		</c:if>
			    			<c:if test="${mg:check(loginUser,'role.do?deleteRole&roleId') }">
			    				<a title="删除角色" href="${pageContext.request.contextPath}/role.do?action=deleteRole&roleId=${role.roleId}" onclick="return confirm('您真的要删除该角色吗？');"><i class="cus-group_delete"></i></a>
			    			</c:if>
			    		</td>
			    		<td>${role.roleId}</td>
			    		<td>${role.roleName }</td>
			    		<td>${role.note }</td>
			    		
			    	</tr>
		    	</c:forEach>
		    	
		    </table>
		    <input type="hidden" value="${resString }" id="msg"/>
			
	</div>
	<div align="center">
		<jsp:include page="../public/pager.jsp">
			<jsp:param
				value="${pageContext.request.contextPath}/role.do?action=listRole"
				name="path" />
		</jsp:include>
	</div>
</div>
<script type="text/javascript" charset="utf-8">
	$(function(){
		//弹出反馈信息
		alertMsg("msg",12);
	});
</script>    
