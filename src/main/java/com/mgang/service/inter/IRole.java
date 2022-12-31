package com.mgang.service.inter;

import java.util.List;

import com.mgang.util.Pager;
import com.mgang.vo.Role;

/**
 * 
 * @author meigang 2014-11-4 9:19
 *	role service接口
 */
public interface IRole {
	/**
	 * 添加角色
	 * @param r
	 * @return 成功返回true,反之返回false
	 */
	boolean addRole(Role r);
	/**
	 * 得到Role对应的表中的总行数
	 * @return
	 */
	int countRole();
	/**
	 * 找到role的分页数据
	 * @param page
	 * @param orderBy 按什么排序
	 * @return 返回带有role集合的page对象
	 */
	Pager findRolePage(Pager page, String orderBy);
	/**
	 * 删除角色
	 * @param r
	 * @return 删除成功返回true,反之返回false
	 */
	boolean deleteRole(Role r);
	/**
	 * 通过roleId查询得到Role对象
	 * @param roleId
	 * @return 返回role对象
	 */
	Role getRoleById(int roleId);
	/**
	 * 变更角色
	 * @param r 角色对象
	 * @param _new 角色r中，变更后拥有的权限编号数组
	 * @param _old 角色r中，原本就有的权限编号数组
	 * @return 变更成功返回true,反之返回false
	 */
	boolean changeRole(Role r, String[] _new, String[] _old);
	/**
	 * 得到所有的角色集合
	 * @return 
	 */
	List<Role> getAllRoles();

}
