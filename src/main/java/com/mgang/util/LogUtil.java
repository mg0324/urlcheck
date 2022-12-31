package com.mgang.util;

import org.apache.log4j.Logger;

/**
 * log4j的日志工具类
 * @author 梅刚 2014-11-20
 *
 */
public class LogUtil {
	public static Logger log;
	static{
		log = Logger.getLogger(LogUtil.class);
	}
}
