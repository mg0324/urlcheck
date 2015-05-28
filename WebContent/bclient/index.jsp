<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>URL权限控制系统</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3td/layer/layer.js"></script>
<style>
body{
  scrollbar-base-color:#C0D586;
  scrollbar-arrow-color:#FFFFFF;
  scrollbar-shadow-color:DEEFC6;
  margin: 0px;
  padding: 0px;
  background-color: #12649C;
}

</style>
</head>
<frameset rows="145,*,50" cols="*" frameborder="no" framespacing="0" >
  <frame src="${pageContext.request.contextPath}/bclient/public/top.jsp" name="topFrame" scrolling="no">
  <frame src="${pageContext.request.contextPath}/bclient/public/midden.jsp" scrolling="no" name="mainFrame">
  <frame src="${pageContext.request.contextPath}/bclient/public/bot.jsp" name="botFrame" scrolling="no"/>
</frameset>
<script type="text/javascript">
	$(function(){
		//加载扩展模块
		layer.config({
		    extend: getRootPath() + '/3td/layer/extend/layer.ext.js'
		});
	});
</script>
</html>
