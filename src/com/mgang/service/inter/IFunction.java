package com.mgang.service.inter;

import java.util.List;

import com.mgang.util.Pager;
import com.mgang.vo.Function;

/**
 * 
 * @author meigang 2014-11-4 9:20
 *
 */
public interface IFunction {
	/**
	 * 添加权限
	 * @param f 要添加的权限对象
	 * @return 添加成功返回true，失败返回false
	 */
	boolean addFunction(Function f);
	/**
	 * 得到Function对象表中的总行数
	 * @return 返回总行数
	 */
	int countFunction();
	/**
	 * 得到分页的带分页数据的page对象
	 * @param page 分页对象
	 * @param orderBy 按orderBy排序
	 * @return 返回带分页数据的page对象
	 */
	Pager findFunPage(Pager page, String orderBy);
	/**
	 * 删除权限
	 * @param f
	 * @return 删除成功，返回true;反之，返回false.
	 */
	boolean deleteFunction(Function f);
	/**
	 * 修改权限
	 * @param f
	 * @return 修改成功，返回true;反之，返回false
	 */
	boolean updateFunction(Function f);
	/**
	 * 通过funId来得到Function对象
	 * @param funId 
	 * @return 返回查询得到Function对象
	 */
	Function getFunctionById(int funId);
	/**
	 * 得到所有的权限集合
	 * @return
	 */
	List<Function> getAllFunctions();

}
