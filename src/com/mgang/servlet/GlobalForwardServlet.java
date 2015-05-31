package com.mgang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mgang.urlcheck.core.CheckFunctionTag;

import com.mgang.service.imp.UserService;
import com.mgang.service.inter.IUser;
import com.mgang.util.LogUtil;
import com.mgang.util.MD5;
import com.mgang.util.ValidateCode;
import com.mgang.util.prop.PropAppConfig;
import com.mgang.vo.User;

/**
 * 
 * @author meigang 2014-11-4 10:20
 * 全局跳转（不用权限拦截的跳转）
 */
@WebServlet("/gf")
public class GlobalForwardServlet extends FrameworkServlet{
	private static IUser iUser;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GlobalForwardServlet(){
		iUser = new UserService();
	}

	
	
	/**
	 * 跳转到后台的登陆界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute("app_back_title", PropAppConfig.get("app_back_title"));
		LogUtil.log.info("后台登陆");
		return "bclient/login.jsp";
	}
	
	/**
	 * 后台管理员登陆
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User u = doForm(request);
		//System.out.println(u.getUserName()+":"+u.getPassword()+":"+request.getParameter("inCode"));
		String checkCode = request.getParameter("inCode");
		if(ValidateCode.checkInCode(request, checkCode)){
			//验证码正确
			//将password加密MD5
			u.setPassword(MD5.getMD5(u.getPassword()));
			u = iUser.loginUser(u);
			if(null != u){
				//该用户是数据库中的存在用户
				//匹配用户有没有后台登陆权限
				String login_url = PropAppConfig.get("login_url");
				boolean haveFun = CheckFunctionTag.checkFunctionByUser(u,login_url);
				if(haveFun){
					//有后台登陆权限
					//该用户是否是管理员级别
					if(u.getUserType()>2){
						resString = "该用户不是管理员";
						LogUtil.log.info(u.getUserName() + "该用户不是管理员");
					}else{
						//是管理员，是否是可用的管理员
						if(u.getStatus()==0){
							resString = "该管理员账号被禁用";
							LogUtil.log.info(u.getUserName() + "该管理员账号被禁用");
						}else{
							//登陆成功
							request.getSession().setAttribute(PropAppConfig.get("LOGIN_USER"), u);
							LogUtil.log.info(u.getUserName() + "登陆成功");
							return "bclient/index.jsp";
						}
					}
				}else{
					resString = "该用户无操作权限";
					LogUtil.log.info(u.getUserName() + "该用户无操作权限");
				}
			}else{
				//登陆失败
				resString = "用户名或密码错误";
				LogUtil.log.info("用户名或密码错误");
			}
		}else{
			resString = "验证码错误";
			LogUtil.log.info(u.getUserName() + "验证码错误");
		}
		request.setAttribute("resString", resString);
		request.setAttribute("resUser", u);
		return toIndex(request, response);
	}
	/**
	 * 管理员注销
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute(PropAppConfig.get("LOGIN_USER"));
		if(u == null)
			return toIndex(request, response);
		//删除登陆用户的session
		request.getSession().removeAttribute(PropAppConfig.get("LOGIN_USER")	);
		//清空session
		request.getSession().invalidate();
		LogUtil.log.info(u.getUserName() + "安全退出后台系统");
		return toIndex(request, response);
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
