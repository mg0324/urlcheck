<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta charset="utf-8"/>
<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/3td/cus-icons/cus-icons.css">
<style type="text/css">

/* clock */
.clock{width:300px;margin:0 auto;position: absolute;}
.clock #Date{font-family:'BebasNeueRegular', Arial, Helvetica, sans-serif;font-size:12px;text-align:center;}
.clock ul{width:300px;margin:0 auto;padding:0px;list-style:none;text-align:right;float: left;display: inline-block;}
.clock ul li{display:inline;font-size:12px;text-align:center;font-family:'BebasNeueRegular', Arial, Helvetica, sans-serif;}
#point{position:relative;-moz-animation:mymove 1s ease infinite;-webkit-animation:mymove 1s ease infinite;}
@-webkit-keyframes mymove{
	0%{opacity:1.0;text-shadow:0 0 20px #00c6ff;}
	50%{opacity:0;text-shadow:none;}
	100%{opacity:1.0;text-shadow:0 0 20px #00c6ff;}	
}


@-moz-keyframes mymove{
	0%{opacity:1.0;text-shadow:0 0 20px #00c6ff;}
	50%{opacity:0;text-shadow:none;}
	100%{opacity:1.0;text-shadow:0 0 20px #00c6ff;}	
}
.clock li{
	color:gray;
}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	// 创建两个变量，一个数组中的月和日的名称
	var monthNames = [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ]; 
	var dayNames= ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]

	// 创建一个对象newDate（）
	var newDate = new Date();
	// 提取当前的日期从日期对象
	newDate.setDate(newDate.getDate());
	//输出的日子，日期，月和年
	$('#Date').html(newDate.getFullYear() + "-" + monthNames[newDate.getMonth()] + '-' + newDate.getDate() + ' ' + dayNames[newDate.getDay()]);

	setInterval( function() {
		// 创建一个对象，并提取newDate（）在访问者的当前时间的秒
		var seconds = new Date().getSeconds();
		//添加前导零秒值
		$("#sec").html(( seconds < 10 ? "0" : "" ) + seconds);
	},1000);
	
	setInterval( function() {
		// 创建一个对象，并提取newDate（）在访问者的当前时间的分钟
		var minutes = new Date().getMinutes();
		// 添加前导零的分钟值
		$("#min").html(( minutes < 10 ? "0" : "" ) + minutes);
    },1000);
	
	setInterval( function() {
		// 创建一个对象，并提取newDate（）在访问者的当前时间的小时
		var hours = new Date().getHours();
		// 添加前导零的小时值
		$("#hours").html(( hours < 10 ? "0" : "" ) + hours);
    }, 1000);
	
}); 
</script>
<div class="clock">
	<ul>
		<li style="color:black;"><i class="cus-clock"></i></li>
		<li id="Date"></li>
		<li id="hours"></li>
		<li id="point">:</li>
		<li id="min"></li>
		<li id="point">:</li>
		<li id="sec"></li>
	</ul>
</div>