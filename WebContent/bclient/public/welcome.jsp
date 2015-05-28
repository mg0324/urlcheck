<%@page import="com.mgang.util.prop.PropAppConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String webservice_weather_url = PropAppConfig.get("webservice_weather_url");
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
<jsp:include page="../../public/import-bootstrap3.jsp"></jsp:include>
<div id="weatherWarning" style="color:gray;font-size: 10px;width: 400px;">温馨提示：如果当前无法连接到网络，天气可能无法正常显示。</div>
<div style="width:400px;float: left;">
	<!-- 天气 -->
	<iframe width="225" scrolling="no" height="90" frameborder="0" allowtransparency="true" src="<%=webservice_weather_url%>"></iframe>
	<jsp:include page="calendar.jsp"></jsp:include>
</div>
<div id="sys_info" style="float: left;margin-left: 5px;">
	<div class="panel panel-default" >
	  <div class="panel-heading" style="padding: 0px;margin: 0px;">
	    <h3 class="panel-title" style="height: 30px;line-height: 30px;padding-left: 10px;
	    background: #1AB4D6;color: white;font-weight: bold;">系统介绍</h3>
	  </div>
	  <div class="panel-body">
	  <pre style="padding: 0px;margin: 0px;border: none;background: none;font-size: 14px;color:gray;">
1.本系统名为url权限控制系统，致力于打造为一个精简化的url权限控制模块。
2.使用到的技术有：
---FrameworkServlet做servlet请求点对点跳转。
---mgds4j-v2.2自定义数据源与连接池工具来管理数据库连接。
---基于apache dbutils工具来完成数据库CRUD。
---使用apache log4j工具来管理系统日志。
---使用properties文件来管理系统级别配置。
3.使用到第三方组件：
---bootstrap3中的一些基本样式。
---cus-icons常用的小图标库。
---subNav实现的手风琴样式菜单。
---layer贤心弹出层插件。
<font style="color:red;">4.注意：对某用户做了角色或者权限的变更后，只有当该用户下次登录系统方生效。</font>
		</pre>
	  </div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#sys_info").css("width",(screen.availWidth - 210 -400)*0.8);
	});
</script>