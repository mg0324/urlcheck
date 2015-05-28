package com.mgang.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mgang.util.ValidateCode;

/**
 * 
 * @author 梅刚 2014-7-21
 *
 */
@WebServlet("/validateCode")
public class ValidateCodeServlet extends FrameworkServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取验证码
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getCode(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		res.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        res.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expire", 0);
        try {
            ValidateCode.getRandcode(req, res);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 验证输入的验证码是否和后台存的一致
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void checkCode(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code = req.getParameter("inCode");
		//不区分大小写
		if(code.toUpperCase().equals(req.getSession().
				getAttribute(ValidateCode.RANDOMCODEKEY)
				.toString().toUpperCase())){
			//一样
			res.getOutputStream().write("T".getBytes());
		}else{
			res.getOutputStream().write("F".getBytes());
		}
        
	}
}
