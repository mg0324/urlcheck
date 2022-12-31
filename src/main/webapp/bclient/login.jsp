<%@page import="com.mgang.util.prop.PropAppConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>基于url权限认证系统</title>
<meta charset="utf-8" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/3td/layer/layer.js"></script>
<jsp:include page="../public/import-bootstrap3.jsp"></jsp:include>
<style type="text/css">
#btn_login:HOVER {
	border: 1px solid blue;
}
lab{
	color:gray;
}
</style>
</head>
<%
	request.setAttribute("app_sys_version", PropAppConfig.get("app_sys_version"));
%>
<body style="background: white; color: black; margin: 0px auto;">
	<div style="width: 100%; margin: 0px auto;">
		<div style="width: 500px; margin: 0px auto;">
		<form id="form_login" action="${pageContext.request.contextPath}/gf?action=login"
			method="post" style="width:500px; margin: 0 auto;display:block;text-align: center;">
			<div style="margin-bottom: 10px;">
				<span style="font-family: '楷体'; font-size: 35px;">${app_back_title }</span><span style="font-size: 13px;color:gray;">${app_sys_version }</span>
			</div>
			<div style="float: left;width: 100px; margin-right: 5px;margin-left: 45px;">
				<img src="${pageContext.request.contextPath}/bclient/image/urlcheck.gif">
			</div>
			<div title="登陆表单左边" style="float: left;">
				<div>
					<lab>用户名</lab> <input type="text" style="width:150px;height: 25px;"  name="userName" id="userName"required="required" placeholder="用户名">
				</div>
				<div style="margin-top: 10px;">
				<lab>密　码</lab> <input type="password" style="width:150px;height: 25px;" name="password" id="password" required="required" placeholder="密码">
				</div>
				<div style="margin-top: 10px;">
				<lab>验证码</lab> <input type="text" style="width: 55px;height: 25px;" name="inCode" id="inCode" required="required" placeholder="验证码">
				<img style="border: 1px solid #bdbdbd; margin-left: 8px;"
					id="imgVerifyCode" onclick="refresh(this)" 
					src="${pageContext.request.contextPath}/validateCode?action=getCode"
					alt="验证码" title="验证码不区分大小写，点击刷新" />
				</div>
			</div>
			<div>
			<button type="submit" id="btn_login" style="float: left; width:100px;height: 100px;
				margin-left: 5px; border-radius:3px;border: 1px thick gray;">登　陆</button>
			</div>
			<input type="hidden" id="msg" value="${resString }"/>
		</form></div>
		<div style="clear: left;width:40%; margin: 0 auto;padding-top:5px;
				border-bottom: 1px solid #bdbdbd;">
		</div>
		<div style="width:30%; margin: 0 auto;padding-top:5px;
				border-top: 1px solid gray;">
		</div>
		<div style="width:35%; margin: 0 auto;text-align: center;">
			<p>
				Copyright © 2014 - 2015 meigang. All Rights Reserved. <br/>
				<a href="#">梦来梦往工作室</a> 版权所有
			</p>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			//加载扩展模块
			layer.config({
			    extend: getRootPath() + '/3td/layer/extend/layer.ext.js'
			});
			//form_login垂直位置设置
			$("#form_login").css("margin-top",screen.availHeight*0.2);
			//弹出提示信息
			alertMsg("msg",3);
		});
		
		function refresh(obj) {
			obj.src = getRootPath() + "/validateCode?action=getCode&rand="
					+ Math.random();
		}
	</script>
</body>
</html>
