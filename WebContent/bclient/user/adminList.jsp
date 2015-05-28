<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mg" uri="http://room.mgang.com/checkFunction" %>
<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/3td/cus-icons/cus-icons.css">

<link href="${pageContext.request.contextPath}/bclient/css/user/admin.css" type="text/css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/bclient/css/user/bt_table.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../public/importValidate.jsp"></jsp:include>
<div class="fun">
	<div class="funTitle">
		　用户设置&gt;查看管理员列表
	</div>
	<div class="funContent">
		    <table class="table table-condensed table-bordered table-striped table-hover table12" cellspacing="0" cellpadding="0">
		    	<tr>
		    		<th>操作</th>
		    		<th>编号</th>
		    		<th>用户名</th>
		    		<th>类型</th>
		    		<th>积分</th>
		    		<th>已发布广告数</th>
		    		<th>手机</th>
		    		<th>邮箱</th>
		    		<th>注册时间</th>
		    		<th>最近登陆</th>
		    		<th>登陆次数</th>
		    		<th>状态</th>
		    	</tr>
		    	<c:forEach items="${page.list }" var="u">
		    		<tr>
		    			<td>
		    				<c:if test="${mg:check(loginUser,'admin.do?action=toChangeUserUI&userId&admin') }">
				    			<a title="变更用户角色" href="${pageContext.request.contextPath}/admin.do?action=toChangeUserUI&userId=${u.userId}&type=admin" onclick="return confirm('您真的要变更该管理员角色吗？')"><i class="cus-group_edit"></i></a>
				    		</c:if>
			    			<c:if test="${u.userType != 1 }">
			    				<c:if test="${u.status eq 1 }">
			    					<c:if test="${mg:check(loginUser,'admin.do?action=stopAdmin&userId') }">
			    						<a title="禁用管理员" href="${pageContext.request.contextPath}/admin.do?action=stopAdmin&userId=${u.userId}" onclick="return confirm('您真的要禁用该管理员吗？');"><i class="cus-user_delete"></i></a>
			    					</c:if>
			    				</c:if>
			    				<c:if test="${u.status eq 0 }">
			    					<c:if test="${mg:check(loginUser,'admin.do?action=activeAdmin&userId') }">
			    						<a title="解锁管理员" href="${pageContext.request.contextPath}/admin.do?action=activeAdmin&userId=${u.userId}" onclick="return confirm('您真的要解锁该管理员吗？');"><i class="cus-user_go"></i></a>
			    					</c:if>
			    				</c:if>
			    			</c:if>
			    		</td>
			    		<td>${u.userId }</td>
			    		<td>${u.userName }</td>
			    		<td>
			    			<c:if test="${u.userType eq 1 }">超级管理员</c:if>
			    			<c:if test="${u.userType eq 2 }">普通管理员</c:if>
			    		</td>
			    		<td>${u.score }</td>
			    		<td>${u.publishedAdCount }</td>
			    		<td>
			    		<c:if test="${empty u.phone }"> -- </c:if>
			    		${u.phone }</td>
			    		<td>
			    		<c:if test="${empty u.email }"> -- </c:if>
			    		${u.email }</td>
			    		<td><fmt:formatDate value="${u.regTime }" type="both"/></td>
			    		<td><c:if test="${empty u.lastLoginTime }"> -- </c:if>
			    		<fmt:formatDate value="${u.lastLoginTime }" type="both"/></td>
			    		<td>${u.loginCount }</td>
			    		<td>
			    			<c:if test="${u.status eq 1 }">可用</c:if>
			    			<c:if test="${u.status eq 0 }"><span style="color:red;">不可用</span></c:if>
			    		</td>
			    	</tr>
		    	</c:forEach>
		    	
		    </table>
		    <input type="hidden" value="${resString }" id="msg"/>
	</div>
	<div align="center">
		<jsp:include page="../public/pager.jsp">
			<jsp:param
				value="${pageContext.request.contextPath}/admin.do?action=listUser&type=admin"
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
