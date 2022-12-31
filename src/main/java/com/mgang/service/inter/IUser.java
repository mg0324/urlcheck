package com.mgang.service.inter;

import com.mgang.util.Pager;
import com.mgang.vo.User;

/**
 * 
 * @author meigang 2014-11-4 8:33
 * 用户service接口
 */
public interface IUser {
	/**
	 * 用户登陆
	 * @param u 带有用户名和密码的User对象
	 * @return 查询得到的所有信息的User对象
	 */
	User loginUser(User u);
	/**
	 * 修改密码
	 * @param u  保存u对象
	 * @return 如果成功返回true,反之返回false
	 */
	boolean updatePwd(User u);
	/**
	 * 得到User对应的表中的总行数
	 * @return
	 */
	int countUser();
	/**
	 * 得到带有数据的pege对象
	 * @param page
	 * @param orderBy 排序字段
	 * @param flag admin表示是列出管理员，user表示是列出会员
	 * @return 返回带有user集合的page
	 */
	Pager findUserPage(Pager page, String orderBy,String flag);
	/**
	 * 屏蔽或者禁用用户
	 * @param u 要禁用的用户
	 * @return 屏蔽或者禁用成功，返回true.反之返回false
	 */
	boolean stopUser(User u);
	/**
	 * 解锁用户
	 * @param u
	 * @return 成功返回true,反之返回false
	 */
	boolean activeUser(User u);
	/**
	 * 通过userId得到User对象
	 * @param userId
	 * @return 返回查询得到的user对象
	 */
	User getUserById(int userId);
	/**
	 * 变更用户
	 * @param u 用户对象
	 * @param _new 用户u中，变更后拥有的角色编号数组
	 * @param _old 用户u中，原本就有的角色编号数组
	 * @return 变更成功返回true,反之返回false
	 */
	boolean changeUser(User u, String[] _new, String[] _old);
	/**
	 * 添加管理员
	 * @param u
	 * @return 添加成功，返回true。失败，返回false.
	 */
	boolean addAdmin(User u);

}
