<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
#page {
	margin:0px;
	padding:0px;
	display:inline-block;
	height: 20px;
	list-style-type: none;
	text-align: center;
	color:gray;
}
#page li{
	float:left;
	margin-right:5px;
	width:30px;
	height: 20px;
	line-height: 20px;
	text-align: center;
	border: 1px solid #bdbdbd;
	border-radius:5px;
}
#page li a{
	display: block;
	width: 30px;
	height: 20px;
	text-align: center;
	text-decoration: none;
	line-height: 20px;
}
#page li:hover{
	background-color: #5FA7DB;
}
.noBg{
	background-color: #5FA7DB;
	color:black;
}
.bg{
	background-color:white;
}

</style>
<ul id="page">
	<c:if test="${page.totalPage>1}">
		
		<c:if test="${!page.isFirst }">
			<li><a href="${param.path }&currentPage=1"><span style="font-size: 12px;">首页</span></a></li>
			<li><a  href="${param.path }&currentPage=${page.prePage }">&lt;&lt;</a></li>
		</c:if>
		<c:if test="${page.totalPage<=9 }">
			<c:forEach begin="1" end="${page.totalPage }" step="1" var="index">
				
				<c:if test="${index==page.currentPage }">
					<li  class="noBg"><a disabled="disabled">${index }</a></li>
				</c:if>
				<c:if test="${index!=page.currentPage }">
					<li class="bg"><a href="${param.path }&currentPage=${index}">${index }</a></li>
				</c:if>
				
			</c:forEach>
		</c:if>
		<c:if test="${page.totalPage>9 }">
			<c:if test="${page.currentPage<5 }">
				<c:forEach begin="1" end="9" step="1" var="index">
					<c:if test="${index==page.currentPage }">
						<li  class="noBg"><a disabled="disabled">${index }</a></li>
					</c:if>
					<c:if test="${index!=page.currentPage }">
						<li class="bg"><a href="${param.path }&currentPage=${index}">${index }</a></li>
					</c:if>
					
				</c:forEach>
			</c:if>
			<c:if
				test="${(page.totalPage>=page.currentPage+4)&&(page.currentPage>=5)}">
				<c:forEach begin="${page.currentPage-4 }"
					end="${page.currentPage+4 }" step="1" var="index">
					<c:if test="${index==page.currentPage }">
					<li  class="noBg"><a disabled="disabled">${index }</a></li>
					</c:if>
					<c:if test="${index!=page.currentPage }">
						<li class="bg"><a href="${param.path }&currentPage=${index}">${index }</a></li>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if
				test="${page.totalPage<page.currentPage+4&&page.currentPage>=5 }">
				<c:forEach begin="${page.totalPage-8 }" end="${page.totalPage }"
					step="1" var="index">
					<c:if test="${index==page.currentPage }">
						<li  class="noBg"><a disabled="disabled">${index }</a></li>
					</c:if>
					<c:if test="${index!=page.currentPage }">
						<li class="bg"><a href="${param.path }&currentPage=${index}">${index }</a></li>
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
		<c:if test="${!page.isLast }">
	    	<li><a  href="${param.path }&currentPage=${page.nextPage }">&gt;&gt;</a></li>
	    	<li><a
			href="${param.path }&currentPage=${page.lastPage }"><span style="font-size: 12px;">尾页</a></span></li>
	    </c:if>
	</c:if>
</ul>
<div style="color: gray;margin: 0 atuo;width: 100%;margin-top: 5px;font-size: 14px;">
<span style="display: inline-block;text-align: center;">
共
<font color="#FF8000">${page.totalCount }</font>
条数据，当前第
<font color="#FF8000">${page.currentPage }</font>
页，共有
<font color="#FF8000">${page.totalPage }</font>
页。
</span>
</div>

