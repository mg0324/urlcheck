package com.mgang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mgang.service.imp.FunctionService;
import com.mgang.service.inter.IFunction;
import com.mgang.util.LogUtil;
import com.mgang.util.Pager;
import com.mgang.util.prop.PropAppConfig;
import com.mgang.vo.Function;
/**
 * 
 * @author meigang 2014-11-5 18:35
 * 权限servlet
 */
@WebServlet("/function.do")
public class FunctionServlet extends FrameworkServlet{
	private static Pager page;
	/**
	 * 返回信息变量
	 */
	private static String resString = "";
	private static IFunction iFun;
	public FunctionServlet(){
		iFun = new FunctionService();
		page = new Pager();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 添加权限
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addFunction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Function f = doForm(request);
		if(iFun.addFunction(f)){
			//添加权限成功
			resString = "权限添加成功";
			LogUtil.log.info("权限添加成功");
		}else{
			resString = "权限添加失败";
			LogUtil.log.info("权限添加失败");
		}
		request.setAttribute("resString", resString);
		return listFunction(request, response);
	}
	/**
	 * 查看权限列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String listFunction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//封装权限集合到request中
		int currentPage=1;
		if(request.getParameter("currentPage")!=null){
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		}
		//得到Funciton的总行数
		int totalCount=iFun.countFunction();
		page.paging(currentPage, Integer.parseInt(PropAppConfig.get("FUNCTION_PAGE_SIZE").toString().trim()), totalCount);
		page =iFun.findFunPage(page,"type");
		request.setAttribute("page",page);
		LogUtil.log.info("查看权限列表");
		return "bclient/user/funList.jsp";
	}
	/**
	 * 删除权限，通过funId
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteFunction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Function f = doForm(request);
		if(iFun.deleteFunction(f)){
			//删除权限成功
			resString = "删除权限成功";
			LogUtil.log.info("删除权限成功");
		}else{
			//删除权限失败
			resString = "删除权限失败";
			LogUtil.log.info("删除权限成功");
		}
		request.setAttribute("resString", resString);
		return listFunction(request, response);
	}
	/**
	 * 跳转到修改权限页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toUpdateFunctionUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Function f = doForm(request);
		f = iFun.getFunctionById(f.getFunId());
		request.setAttribute("fun", f);
		return "bclient/user/updateFun.jsp";
	}
	/**
	 * 修改权限
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateFunction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Function f = doForm(request);
		if(iFun.updateFunction(f)){
			//修改权限成功
			resString = "修改权限成功";
			LogUtil.log.info("修改权限成功");
		}else{
			//修改权限失败
			resString = "修改权限失败";
			LogUtil.log.info("修改权限失败");
		}
		request.setAttribute("resString", resString);
		return listFunction(request, response);
	}
	/**
	 * 封装表单
	 * @param r
	 * @return
	 */
	private Function doForm(HttpServletRequest r){
		Function f = new Function();
		if(null != r.getParameter("funId"))
			f.setFunId(Integer.parseInt(r.getParameter("funId").trim()));
		if(null != r.getParameter("funName"))
			f.setFunName(r.getParameter("funName"));
		if(null != r.getParameter("note"))
			f.setNote(r.getParameter("note"));
		if(null != r.getParameter("resource"))
			f.setResource(r.getParameter("resource"));
		if(null != r.getParameter("stuffix"))
			f.setStuffix(r.getParameter("stuffix"));
		if(null != r.getParameter("params"))
			f.setParams(r.getParameter("params"));
		if(null != r.getParameter("type"))
			f.setType(Integer.parseInt(r.getParameter("type")));
		return f;
	}
}
