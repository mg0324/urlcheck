package com.mgang.urlcheck.core;


import com.mgang.urlcheck.proputil.UrlCheckJarConfig;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.List;



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
	public static boolean checkFunctionByUser(Object u,String url){
		boolean haveFun = false;
		Class userClass;
		try {
			userClass = Class.forName(UrlCheckJarConfig.get("user_class"));
		
			Class roleClass = Class.forName(UrlCheckJarConfig.get("role_class"));
			Method getRoleListMethod = userClass.getMethod("getRoleList", null);
			Method getFunListMethod = roleClass.getMethod("getFunList", null);
			
			if(null != u){
				List<Object> lo1 = (List<Object>) getRoleListMethod.invoke(u, null);
				labe:for(Object role : lo1){
					List<Object> lo2 = (List<Object>) getFunListMethod.invoke(role, null);
					for(Object f : lo2){
						haveFun = matchFunctionByReqUrl(f, url);
						//匹配成功跳出
						if(haveFun)
							break labe;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			haveFun = false;
		}
	
		return haveFun;
	}
	
	/**
	 * 通过ReqUrl来匹配Function权限
	 * @param fun 权限对象
	 * @param reqUrl 资源请求路径
	 * @return 如果配置成功，返回true;
	 * 			反之，则返回false;
	 * @throws UnsupportedEncodingException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static boolean matchFunctionByReqUrl(Object fun,String reqUrl) throws Exception{
		Class functionClass = Class.forName(UrlCheckJarConfig.get("function_class"));
		Method getResourceMethod = functionClass.getMethod(UrlCheckJarConfig.get("function_method_getResource"), null);
		Method getStuffixMethod = functionClass.getMethod(UrlCheckJarConfig.get("function_method_getStuffix"), null);
		Method getParamsMethod = functionClass.getMethod(UrlCheckJarConfig.get("function_method_getParams"), null);
		boolean b = true;
		int count = 1;
		//先匹配resource
		String resource = (String)getResourceMethod.invoke(fun, null);
		//System.out.println("fun resource:"+resource);
		//System.out.println("req resource:"+getResourceFromReqUrl(reqUrl));
		if(getResourceFromReqUrl(reqUrl).equals(resource)){
			//resource匹配成功
			count ++;
			//System.out.println(resource + "资源匹配成功");
		}
		//匹配后缀
		if(count == 2){
			String stuffix = (String)getStuffixMethod.invoke(fun, null);
			//System.out.println("fun stuffix:"+stuffix);
			if(getStuffixFromReqUrl(reqUrl) == null){
				count ++;
				//System.out.println(reqUrl+"无后缀");
			}else{
				if(getStuffixFromReqUrl(reqUrl).equals(stuffix)){
					//匹配后缀成功
					count ++;
					//System.out.println(stuffix + "后缀匹配成功");
				}
			}
		}
		//匹配参数
		if(count == 3){
			//System.out.println("reqUrl:"+reqUrl+"-fun:"+fun.getParams());
			//function.do?toUpdateFunctionUI&funId&xx&yy没有问号，就说明无参数
			if(reqUrl.contains("?")){
				String pReqUrl = reqUrl.substring(reqUrl.indexOf('?')+1,reqUrl.length());
				String[] p_req_url = pReqUrl.split("&");
				int urlParamLen = p_req_url.length;
				//判断参数个数是否相同
				String params_str = (String) getParamsMethod.invoke(fun, null);
				String[] p_fun = params_str.split(",");
				//System.out.println("fun中的参数："+params_str);
				//System.out.println("reqUrl中的参数："+pReqUrl);
				//System.out.println("urlParamLen:"+urlParamLen);
				if(p_fun.length == urlParamLen){
					//得到reqUrl中的参数 和 fun中的匹配，精确匹配
					int pcount = 0;
					for(String p : p_fun){
						pcount = findParamInStringArray(p, p_req_url,pcount);
					}
					//System.out.println("pcount:"+pcount);
					if(pcount == urlParamLen){
						b = true;
					}else{
						b = false;
					}
				}else{
					b = false;
				}
			}else{
				b = true;
			}
		}
		
		//System.out.println("count:"+count+"---b:"+b);
		if(count == 3 && b){
			return true;
		}else{
			return false;
		}
	}
	
	private static int findParamInStringArray(String p,String[] arr,int count){
		for(String ap : arr){
			if(ap.equals(p)){
				count ++;
			}
		}
		return count;
	}
	/**
	 * 从reqUrl中得到资源
	 * @param reqUrl
	 * @return
	 */
	private static String getResourceFromReqUrl(String reqUrl){
		//加强严谨性
		if(reqUrl.contains("?")){
			if(reqUrl.contains(".")){
				//有后缀
				String allResource = reqUrl.substring(0,reqUrl.indexOf("?"));
				return allResource.substring(0,allResource.indexOf("."));
			}else{
				return reqUrl.substring(0,reqUrl.indexOf("?"));
			}
		}else{
			if(reqUrl.contains(".")){
				//有后缀
				return reqUrl.substring(0,reqUrl.indexOf("."));
			}else{
				return reqUrl;
			}
		}
		
		
	}
	/**
	 * 从reqUrl中得到后缀
	 * @param reqUrl
	 * @return
	 */
	private static String getStuffixFromReqUrl(String reqUrl){
		if(reqUrl.contains("?")){
			String allResource = reqUrl.substring(0,reqUrl.indexOf("?"));
			if(reqUrl.contains(".")){
				//有后缀
				return allResource.substring(reqUrl.indexOf("."),allResource.length());
			}else{
				return null;
			}
		}else{
			if(reqUrl.contains(".")){
				//有后缀
				return reqUrl.substring(reqUrl.indexOf("."),reqUrl.length());
			}else{
				return null;
			}
		}
		
		
	}
}
