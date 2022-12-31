package com.mgang.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mgang.service.imp.RoleService;
import com.mgang.service.imp.UserService;
import com.mgang.service.inter.IRole;
import com.mgang.service.inter.IUser;
import com.mgang.util.LogUtil;
import com.mgang.util.MD5;
import com.mgang.util.Pager;
import com.mgang.util.prop.PropAppConfig;
import com.mgang.vo.Role;
import com.mgang.vo.User;
/**
 * 
 * @author meigang 2014-11-4 10:16
 * 后台管理员的servlet
 */
@WebServlet("/admin.do")
public class AdminServlet extends FrameworkServlet{

	private static final String PASSWORD_NEW_KEY = "newPassword1";
	private static IUser iUser;
	private static Pager page;
	private static IRole iRole;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdminServlet(){
		iUser = new UserService();
		page = new Pager();
		iRole = new RoleService();
	}
	
	/**
	 * 修改管理员密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		//取出登陆的用户
		User loginUser = (User) request.getSession().getAttribute(PropAppConfig.get("LOGIN_USER"));
		//比对用户输入的原密码
		if(MD5.getMD5(u.getPassword()).equals(
				loginUser.getPassword())){
			//原密码输出正确
			//修改成新密码
			loginUser.setPassword(MD5.getMD5(request.getParameter(PASSWORD_NEW_KEY)));
			if(iUser.updatePwd(loginUser)){
				//修改成功,更新session中的user对象
				request.getSession().setAttribute(PropAppConfig.get("LOGIN_USER"), loginUser);
				resString = "密码修改成功";
				LogUtil.log.info(loginUser.getUserName() + "密码修改成功");
			}
		}else{
			//原密码输入不正确
			resString = "原密码输入不正确";
		}
		request.setAttribute("resString", resString);
		return "bclient/user/updatePwd.jsp";
	}
	/**
	 * 查看用户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String listUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//封装用户集合到request中
		String type = request.getParameter("type");
		if(null == type)
			type = "user";//默认显示user会员列表
		int currentPage=1;
		if(request.getParameter("currentPage")!=null){
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		}
		//得到User的总行数
		int totalCount=iUser.countUser();
		page.paging(currentPage, Integer.parseInt(PropAppConfig.get("USER_PAGE_SIZE").toString()), totalCount);
		page =iUser.findUserPage(page,"userId",type);
		request.setAttribute("page",page);
		if(type.equals("user")){
			return "bclient/user/userList.jsp";
		}

		//if(type.equals("admin"))
		return "bclient/user/adminList.jsp";
		
			
	}
	/**
	 * 屏蔽用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String stopUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		if(iUser.stopUser(u)){
			//屏蔽用户成功
			resString = "屏蔽成功";
			LogUtil.log.info(u.getUserName() + "屏蔽成功");
		}else{
			resString = "屏蔽失败";
			LogUtil.log.info(u.getUserName() + "屏蔽失败");
		}
		request.setAttribute("resString", resString);

		return "admin.do?action=listUser&type=user";
	}
	/**
	 * 解锁用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activeUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		if(iUser.activeUser(u)){
			//屏蔽用户成功
			resString = "解锁成功";
			LogUtil.log.info(u.getUserName() + "解锁成功");
		}else{
			resString = "解锁失败";
			LogUtil.log.info(u.getUserName() + "解锁失败");
		}
		request.setAttribute("resString", resString);

		return "admin.do?action=listUser&type=user";
	}
	/**
	 * 禁用管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String stopAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		if(iUser.stopUser(u)){
			//屏蔽管理员成功
			resString = "屏蔽成功";
			LogUtil.log.info(u.getUserName() + "屏蔽管理员成功");
		}else{
			resString = "屏蔽失败";
			LogUtil.log.info(u.getUserName() + "屏蔽管理员失败");
		}
		request.setAttribute("resString", resString);

		return "admin.do?action=listUser&type=admin";
	}
	/**
	 * 解锁管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activeAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		if(iUser.activeUser(u)){
			//解锁成功
			resString = "解锁成功";
			LogUtil.log.info(u.getUserName() + "解锁管理员成功");
		}else{
			resString = "解锁失败";
			LogUtil.log.info(u.getUserName() + "解锁管理员失败");
		}
		request.setAttribute("resString", resString);

		return "admin.do?action=listUser&type=admin";
	}
	/**
	 * 跳转到变更用户角色页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toChangeUserUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		u = iUser.getUserById(u.getUserId());
		String type = request.getParameter("type");
		//所有的角色集合
		List<Role> roles = iRole.getAllRoles();
		request.setAttribute("roles", roles);
		request.setAttribute("user", u);
		request.setAttribute("type", type);
		return "bclient/user/changeUser.jsp";
	}
	/**
	 * 变更用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changeUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		User u = doForm(request);
		//两个数组都不包含第一个元素，因为都是0，是用来做逗号间隔的
		//_new变更后用户u拥有的roleId数组
		String[] _new = request.getParameter("newRoleParams").split(",");
		//_old之前用户r就拥有的roleId数组
		String[] _old = request.getParameter("oldRoleParams").split(",");
		if(iUser.changeUser(u,_new,_old)){
			//变更用户成功
			resString = "变更用户成功";
			LogUtil.log.info(u.getUserName() + "变更用户成功");
		}else{
			//变更角色成功
			resString = "变更用户失败";
			LogUtil.log.info(u.getUserName() + "变更用户失败");
		}
		request.setAttribute("resString", resString);
		return "admin.do?action=listUser&type="+type;
	}
	/**
	 * 添加管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addAdmin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = doForm(request);
		u.setPassword(MD5.getMD5(u.getPassword()));
		if(iUser.addAdmin(u)){
			//添加管理员成功
			resString = "添加管理员成功";
			LogUtil.log.info("添加管理员" + u.getUserName() + " 成功");
		}else{
			//添加管理员成功
			resString = "添加管理员失败";
			LogUtil.log.info("添加管理员" + u.getUserName() + " 失败");
		}
		request.setAttribute("resString", resString);
		
		return "admin.do?action=listUser&type=admin";
	}
	
	/**
	 * 封装表单
	 * @param r
	 * @return
	 */
	private User doForm(HttpServletRequest r){
		User u = new User();
		if(null != r.getParameter("userId"))
			u.setUserId(Integer.parseInt(r.getParameter("userId").trim()));
		if(null != r.getParameter("userName"))
			u.setUserName(r.getParameter("userName"));
		if(null != r.getParameter("password"))
			u.setPassword(r.getParameter("password"));
		if(null != r.getParameter("userType"))
			u.setUserType(Integer.parseInt(r.getParameter("userType")));
		return u;
	}
}
