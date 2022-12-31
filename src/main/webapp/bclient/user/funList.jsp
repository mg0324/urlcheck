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
		　权限设置&gt;查看权限列表
	</div>
	<div class="funContent">
		    <table class="table table-condensed table-bordered table-striped table-hover table12" cellspacing="0" cellpadding="0">
		    	<tr>
		    		<th>操作</th>
		    		<th style="width: 40px;">编号</th>
		    		<th style="width: 40px;">类型</th>
		    		<th style="width:120px;">权限名称</th>
		    		<th>权限描述</th>
		    		<th>请求资源</th>
		    		<th style="width: 40px;">后缀</th>
		    		<th>请求参数</th>
		    	</tr>
		    	<c:forEach items="${page.list }" var="fun">
		    		<tr>
		    			<td>
				    		<c:if test="${mg:check(loginUser,'function.do?toUpdateFunctionUI&funId') }">
				    			<a title="修改权限" href="${pageContext.request.contextPath}/function.do?action=toUpdateFunctionUI&funId=${fun.funId}"
				    			 onclick="return confirm('您真的要修改该权限吗？')"><i class="cus-lock_edit"></i></a>
				    		</c:if>
				    		<c:if test="${mg:check(loginUser,'function.do?deleteFunction&funId') }">
				    			<a title="删除权限" href="${pageContext.request.contextPath}/function.do?action=deleteFunction&funId=${fun.funId}" onclick="return confirm('您真的要删除该权限吗？');"><i class="cus-lock_delete"></i></a>
				    		</c:if>
			    		</td>
			    		<td>${fun.funId }</td>
			    		<td>${fun.type }</td>
			    		<td>${fun.funName }</td>
			    		<td>${fun.note }</td>
			    		<td>${fun.resource }</td>
			    		<td>${fun.stuffix }</td>
			    		<td>${fun.params }</td>
			    		
			    	</tr>
		    	</c:forEach>
		    	
		    </table>
		    <input type="hidden" value="${resString }" id="msg"/>
			
	</div>
	<div align="center">
		<jsp:include page="../public/pager.jsp">
			<jsp:param
				value="${pageContext.request.contextPath}/function.do?action=listFunction"
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
