package com.mgang.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mgang.service.imp.FunctionService;
import com.mgang.service.imp.RoleService;
import com.mgang.service.inter.IFunction;
import com.mgang.service.inter.IRole;
import com.mgang.util.LogUtil;
import com.mgang.util.Pager;
import com.mgang.util.prop.PropAppConfig;
import com.mgang.vo.Function;
import com.mgang.vo.Role;

/**
 * 
 * @author meigang 2014-11-6 19:44
 *
 */
@WebServlet("/role.do")
public class RoleServlet extends FrameworkServlet{
	private static Pager page;
	private static IRole iRole;
	private static IFunction iFun;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RoleServlet(){
		iRole = new RoleService();
		page = new Pager();
		iFun = new FunctionService();
	}
	
	/**
	 * 添加角色
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addRole(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Role r = doForm(request);
		if(iRole.addRole(r)){
			//添加角色成功
			resString = "角色添加成功";
			LogUtil.log.info("角色添加成功");
		}else{
			resString = "角色添加失败";
			LogUtil.log.info("角色添加失败");
		}
		request.setAttribute("resString", resString);
		return "bclient/user/addRole.jsp";
	}
	/**
	 * 查看角色列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String listRole(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//封装角色集合到request中
		int currentPage=1;
		if(request.getParameter("currentPage")!=null){
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		}
		//得到Role的总行数
		int totalCount=iRole.countRole();
		page.paging(currentPage, Integer.parseInt(PropAppConfig.get("ROLE_PAGE_SIZE").toString().trim()), totalCount);
		page =iRole.findRolePage(page,"roleId");
		request.setAttribute("page",page);
		LogUtil.log.info("查看角色列表");
		return "bclient/user/roleList.jsp";
	}
	/**
	 * 删除角色，通过roleId
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteRole(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Role r = doForm(request);
		if(iRole.deleteRole(r)){
			//删除角色成功
			resString = "删除角色成功";
			LogUtil.log.info("删除角色成功");
		}else{
			//删除角色失败
			resString = "删除角色失败";
			LogUtil.log.info("删除角色失败");
		}
		request.setAttribute("resString", resString);
		return listRole(request, response);
	}
	/**
	 * 跳转到修改角色页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toChangeRoleUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Role r = doForm(request);
		r = iRole.getRoleById(r.getRoleId());
		
		//所有的权限集合
		List<Function> funs = iFun.getAllFunctions();
		request.setAttribute("funs", funs);
		request.setAttribute("role", r);
		return "bclient/user/changeRole.jsp";
	}
	/**
	 * 变更角色
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changeRole(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Role r = doForm(request);
		//两个数组都不包含第一个元素，因为都是0，是用来做逗号间隔的
		//_new变更后角色r拥有的funId数组
		String[] _new = request.getParameter("newFunParams").split(",");
		//_old之前角色r就拥有的funId数组
		String[] _old = request.getParameter("oldFunParams").split(",");
		if(iRole.changeRole(r,_new,_old)){
			//变更角色成功
			resString = "变更角色成功";
			LogUtil.log.info("变更角色成功");
		}else{
			//变更角色成功
			resString = "变更角色失败";
			LogUtil.log.info("变更角色失败");
		}
		request.setAttribute("resString", resString);
		return listRole(request, response);
	}
	/**
	 * 封装表单
	 * @param r
	 * @return
	 */
	private Role doForm(HttpServletRequest r){
		Role role = new Role();
		if(null != r.getParameter("roleId"))
			role.setRoleId(Integer.parseInt(r.getParameter("roleId")));
		if(null != r.getParameter("roleName"))
			role.setRoleName(r.getParameter("roleName"));
		if(null != r.getParameter("note"))
			role.setNote(r.getParameter("note"));
		return role;
	}

}
