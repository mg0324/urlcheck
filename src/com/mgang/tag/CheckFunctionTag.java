package com.mgang.tag;

import java.io.UnsupportedEncodingException;

import com.mgang.util.LogUtil;
import com.mgang.util.prop.PropAppConfig;
import com.mgang.vo.Function;
import com.mgang.vo.Role;
import com.mgang.vo.User;

/**
 * 
 * @author meigang 2014-11-8 17:02
 * 自定义匹配权限的function标签
 */
public class CheckFunctionTag {
	
	/**
	 * 检查用户权限
	 * @param u 用户对象
	 * @param url 请求的url
	 * @return 如果该用户有该权限，就返回true.
	 */
	public static boolean checkFunctionByUser(User u,String url){
		boolean haveFun = false;
		if(null != u){
			labe:for(Role role : u.getRoleList()){
				info("角色匹配："+role.getRoleName());
				for(Function f : role.getFunList()){
					info("权限匹配："+f.getFunName() +" url:"+url);
					haveFun = matchFunctionByReqUrl(f, url);
					//匹配成功跳出
					if(haveFun)
						break labe;
				}
			}
		}
	
		return haveFun;
	}
	/**
	 * 根据日志开关写日志
	 * @param msg
	 */
	private static void info(Object msg){
		try {
			String flag = PropAppConfig.get("log4j_urlcheck_btn");
			if(flag != null && flag.equals("true")){
				LogUtil.log.info(msg);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 通过ReqUrl来匹配Function权限
	 * @param fun 权限对象
	 * @param reqUrl 资源请求路径
	 * @return 如果配置成功，返回true;
	 * 			反之，则返回false;
	 */
	public static boolean matchFunctionByReqUrl(Function fun,String reqUrl){
		boolean b = true;
		int count = 1;
		//先匹配resource
		if(reqUrl.contains(fun.getResource())){
			//resource匹配成功
			count ++;
			//匹配后缀
			if(reqUrl.contains(fun.getStuffix())){
				//匹配后缀成功
				count ++;
				//匹配参数
				//System.out.println("reqUrl:"+reqUrl+"-fun:"+fun.getParams());
				//function.do?action=toUpdateFunctionUI&funId&xx&yy
				if(reqUrl.contains("=")){
					int urlParamLen = (reqUrl.substring(reqUrl.indexOf('='),reqUrl.length()-1).split("&")).length;
					//判断参数个数是否相同
					if(fun.getParams().split(",").length == urlParamLen){
						for(String p : fun.getParams().split(",")){
							if(!reqUrl.contains(p)){
								//有参数不匹配
								b = false;
							}
						}
					}else{
						b = false;
					}
				}
			}
		}
		if(count == 3 && b){
			return true;
		}else{
			return false;
		}
	}
}
