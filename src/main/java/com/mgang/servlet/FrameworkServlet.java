package com.mgang.servlet;

/**
 * @author 梅刚 2014-11-3 21:34
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class FrameworkServlet extends HttpServlet{
	/**
	 * 信息反馈
	 */
	public static String resString = "";
	public static String REQUEST_URL;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//编码过滤
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		REQUEST_URL = request.getRequestURL().toString() +"?"+ request.getQueryString();
		//System.out.println(new Date().toLocaleString() +":"+ url);
		//LogUtil.logger.info(url);
		runMethod(request, response);
	}
	/**
	 * 1.分析url得到要执行的方法名
	 * @param r
	 */
	public String getActionNameFromUrl(HttpServletRequest r){
		return r.getParameter("action");
	}
	/**
	 * 根据方法名，使用反射得到该方法对象
	 * @param r
	 * @return
	 */
	public Method getMethodByActionName(HttpServletRequest r){
		Method m = null;
		try {
			m = this.getClass().getMethod(getActionNameFromUrl(r), new Class[]{HttpServletRequest.class,HttpServletResponse.class});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	/**
	 * 使用invoke来运行要执行的Method对象
	 * @param r
	 */
	public void runMethod(HttpServletRequest r,HttpServletResponse resp){
		Method m = getMethodByActionName(r);
		if(m==null){
			//如果请求的方法不存在
			try {
				PrintWriter out = resp.getWriter();
				String errorHtml = "<head><meta charset='utf-8'/><title>404</title></head><h2><center>404</center></h2>"
						+ "<hr/><center>您输入的请求有误，无法访问到资源，请重新输入!!</center>";
				out.print(errorHtml);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String res;
			try {
				res = (String) m.invoke(this, r,resp);
				if(res!=null){
					r.getRequestDispatcher(res).forward(r, resp);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
