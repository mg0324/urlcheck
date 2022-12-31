<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mg" uri="http://room.mgang.com/checkFunction" %>
<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/3td/cus-icons/cus-icons.css">
<link href="${pageContext.request.contextPath}/3td/subNav/css/base.css" type="text/css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js" type="text/javascript"></script>
<style type="text/css">
.subNavBox {
	width: 190px;
	border: solid 1px #bdbdbd;
	margin: 0px auto;
	left:5px;
	position: relative;
}

.subNav {
	border-bottom: solid 1px #bdbdbd;
	cursor: pointer;
	font-weight: bold;
	font-size: 14px;
	color: #999;
	line-height: 28px;
	padding-left: 5px;
	background:
		url(${pageContext.request.contextPath}/3td/subNav/images/jiantou1.jpg)
		no-repeat;
	background-position: 95% 50%
}

.subNav:hover {
	color: #277fc2;
}

.currentDd {
	color: #277fc2
}

.currentDt {
	background-image:
		url(${pageContext.request.contextPath}/3td/subNav/images/jiantou.jpg);
}

.navContent {
	display: none;
	border-bottom: solid 1px #bdbdbd;
}

.navContent li a {
	display: block;
	width: 100%;
	heighr: 28px;
	text-align: center;
	font-size: 14px;
	line-height: 28px;
	color: #333
}

.navContent li a:hover{
	color: #fff;
	background-color: #15DE46
}
#slidebtn{
	margin-left: 5px;
	left:5px;
}
#slide{
	background-color:white;
	color:red;
	font-size: 15px;
	font-weight: bold;
}
</style>
<script type="text/javascript">
$(function(){
	$(".subNav").click(function(){
		$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
		$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
		$(this).next(".navContent").slideToggle(300).siblings(".navContent").slideUp(500);
	});
	$(".navContent li a").click(function(){
		$(".navContent li").css('backgroundColor','white');
		$(".navContent li a").css('color','black');
		$(this).parent().css('backgroundColor','#277fc2');
		$(this).css('color','white');
	});
})
</script>
<body onresize="refershMainRight();">
	
	<div class="subNavBox" style="height: 100%;float: left;z-index: 100;">
	    <div class="subNav currentDd currentDt"><i class="cus-user_edit"></i>用户设置</div>
	    <ul class="navContent " style="display:block">
			<c:if test="${mg:check(loginUser,'bclient/user/updatePwd.jsp') }">
				<li><a href="${pageContext.request.contextPath}/bclient/user/updatePwd.jsp" target="main"><i class="cus-key"></i>修改密码　　　</a></li>
			</c:if>
			<c:if test="${mg:check(loginUser,'bclient/user/addAdmin.jsp') }">
				<li><a href="${pageContext.request.contextPath}/bclient/user/addAdmin.jsp" target="main"><i class="cus-user_add"></i>新增管理员　　</a></li>
			</c:if>
			<c:if test="${mg:check(loginUser,'admin.do?listUser&user') }">
				<li><a href="${pageContext.request.contextPath}/admin.do?action=listUser&type=user" target="main"><i class="cus-group"></i>查看会员列表　</a></li>
			</c:if>
			<c:if test="${mg:check(loginUser,'admin.do?listUser&admin') }">
				<li><a href="${pageContext.request.contextPath}/admin.do?action=listUser&type=admin" target="main"><i class="cus-group"></i>查看管理员列表</a></li>
			</c:if>
		</ul>
		<div class="subNav currentDd currentDt"><i class="cus-group"></i>角色设置</div>
		<ul class="navContent">
			<c:if test="${mg:check(loginUser,'bclient/user/addRole.jsp') }">
				<li><a href="${pageContext.request.contextPath}/bclient/user/addRole.jsp" target="main"><i class="cus-group_add"></i>添加角色　　　</a></li>
			</c:if>
			<c:if test="${mg:check(loginUser,'role.do?listRole') }">
				<li><a href="${pageContext.request.contextPath}/role.do?action=listRole" target="main"><i class="cus-group_gear"></i>查看角色列表　</a></li>
			</c:if>
		</ul>
		<div class="subNav currentDd currentDt"><i class="cus-lock"></i>权限设置</div>
		<ul class="navContent">
			<c:if test="${mg:check(loginUser,'bclient/user/addFun.jsp') }">
				<li><a href="${pageContext.request.contextPath}/bclient/user/addFun.jsp" target="main"><i class="cus-lock_add"></i>添加权限　　　</a></li>
			</c:if>
			<c:if test="${mg:check(loginUser,'function.do?listFunction') }">
				<li><a href="${pageContext.request.contextPath}/function.do?action=listFunction" target="main"><i class="cus-group_key"></i>查看权限列表　</a></li>
			</c:if>
		</ul>
	</div>
	<div id="slidebtn" style="background-color: #15DE46;height: 100%;margin: 0px;padding: 0px;
			width: 10px;float: left;position:relative;z-index: 200;left: 6px;">
		<a id="slide" href="javascript:void(0);" flag="1"><</a>
		
	</div>
	<div id="mainFrame" style="float: left;position:relative; padding-top:5px;padding-left: 10px;z-index: 100;">
		<iframe id="main" name="main" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes"
		style="width:100%;height: 100%;padding: 0px;margin: 0px;" src="welcome.jsp"></iframe>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		var slide_height = $("#slide").parent().css("height");
		$("#slide").parent().css("line-height",slide_height);
		$("#mainFrame").css("width",(screen.availWidth-210)*0.97);
		var slide_height = $("#slide").parent().css("height");
		$("#mainFrame").css("height",slide_height-10);
		
		//执行一次
		refershMainRight();
		$("#slide").click(function(){
			var slide_flag = $("#slide").attr("flag");
			var subNav_width = $(".subNavBox").css("width");
			var slide_left = 6;
			var value = parseInt(subNav_width)+parseInt(slide_left);
			
			if(slide_flag == 1){
				$(".subNavBox,#slidebtn").animate({ 
				    left: "-="+value,
				    
				  },500 );
				$("#mainFrame").animate({
					width: "+="+value,
					left: 15
				},1000).css("position","absolute");
				$("#slidebtn").css("backgroundColor","#0B9FBF");
				$("#slide").attr("flag",0).text(">");
			
			}else if(slide_flag == 0){
				$(".subNavBox,#slidebtn").animate({ 
				    left: "+="+value,
				  },500 );
				$("#mainFrame").animate({
					width: "-="+value,
					left: 0
				},1000).css("position","relative");
				$("#slidebtn").css("backgroundColor","#15DE46");
				$("#slide").attr("flag",1).text("<");
				
			}
		});
	});
	//2015-04-26 晚 修复后台主页缩放后main中的内容无法正常显示问题
	function refershMainRight(){
		$("#mainFrame").css("width",(document.body.offsetWidth -210)*0.97);//.css("position","absolute");
	}
</script>

	
