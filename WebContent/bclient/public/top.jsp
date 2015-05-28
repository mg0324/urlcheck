<%@page import="com.mgang.util.prop.PropAppConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mg" uri="http://room.mgang.com/checkFunction" %>
<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/3td/cus-icons/cus-icons.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3td/layer/layer.js"></script>
<style type="text/css">
body{
	margin: 0px; 
	padding: 0px;
	margin-top: 5px;
	margin-left: 5px;
	margin-right: 5px;
}
a{
	text-decoration: none;
}
#menubanner{
	background: url("../image/urlcheck.gif") no-repeat;
}
</style>
<%
	request.setAttribute("app_sys_version", PropAppConfig.get("app_sys_version"));
%>
<div style="border:1px solid #bdbdbd; width: 100%;height: 30px;line-height: 30px;position: relative;">
	<div style="float: right;margin-right: 10px;">
		<font style="color:gray;">欢迎</font><i class="cus-user"></i> <font style="font-weight: bold;">${loginUser.userName }</font> <font style="color:gray;">| 
		<c:forEach items="${loginUser.roleList }" var="role">
			${role.roleName } |
		</c:forEach>
		</font>
		<a href="${pageContext.request.contextPath}/bclient/public/welcome.jsp" target="main"><i class="cus-house"></i>首页</a>
		<c:if test="${mg:check(loginUser,'bclient/user/updatePwd.jsp') }">
			<a href="${pageContext.request.contextPath}/bclient/user/updatePwd.jsp" target="main"><i class="cus-key"></i>修改密码</a>
		</c:if>
		<a target="_top" href="${pageContext.request.contextPath}/gf?action=logout"  onclick="return confirm('您真的要退出系统吗？');"><i class="cus-door_out"></i>安全退出</a>
	</div>
</div>

<div id="menubanner" style="width:100%;border: 1px solid #bdbdbd;margin-top: 5px;height: 100px;">
	<div style="padding-left: 100px;font-size: 40px;color:#0B9FBF;font-family: '楷体';line-height: 100px;
			float: left;">
		${app_back_title }
		<span style="font-size: 13px;color:gray;">${app_sys_version }</span>	
	</div>
	
	<div style="display: block;float: left;">
		<div style="height: 100px;padding-top: 80px;float: right;margin-right: 10px;width: 100%;">
			<jsp:include page="../../public/timer.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		
	});
</script>


