
//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
//确认删除
function confirmDelete() {
	if (confirm("您真的要删除吗?")) {
		return true;
	} else {
		return false;
	}
}
//弹出提示(input的id的value值)
function alertMsg(id,icontype){
	var _msg = document.getElementById(id).value;
	if(_msg.length > 0){
		//alert(_msg);
		layer.alert(_msg,{icon:icontype});
	}
}
//2015-4-27 layer询问层
//href="${pageContext.request.contextPath}/gf?action=logout" 跳转
function layerConfirm(content,url){
	layer.confirm(
			content,
			{icon: 4, title:'提示'},
			function(index){
				layer.close(index);
				window.location.href = url;
			}
	); 
}