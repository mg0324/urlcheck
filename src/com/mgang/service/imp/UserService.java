package com.mgang.service.imp;

import java.util.ArrayList;
import java.util.List;

import com.mgang.dao.UserDao;
import com.mgang.service.inter.IUser;
import com.mgang.util.Pager;
import com.mgang.vo.User;
/**
 * 
 * @author meigang 2014-11-4 8:33
 * 用户service
 */
public class UserService implements IUser{
	private static UserDao userDao;
	
	public UserService(){
		userDao = new UserDao();
	}
	
	public User loginUser(User u) {
		// TODO Auto-generated method stub
		return userDao.checkUser(u);
	}

	public boolean updatePwd(User u) {
		// TODO Auto-generated method stub
		boolean b = false;
		if(userDao.updateUserPwd(u) > 0){
			b = true;
		};
		return b;
	}

	public int countUser() {
		// TODO Auto-generated method stub
		return userDao.getCount();
	}

	public Pager findUserPage(Pager page, String orderBy,String flag) {
		// TODO Auto-generated method stub
		List<Integer> ut = new ArrayList<Integer>();
		if(flag.equals("admin")){
			ut.add(1);//超级管理员
			ut.add(2);//普通管理员
		}else if(flag.equals("user")){
			ut.add(3);//个人会员
			ut.add(4);//企业会员
		}
		return userDao.findUserPage(page,orderBy,ut);
	}

	public boolean stopUser(User u) {
		// TODO Auto-generated method stub
		if(userDao.stopUser(u) > 0)
			return true;
		else
			return false;
	}

	public boolean activeUser(User u) {
		// TODO Auto-generated method stub
		if(userDao.activeUser(u) > 0)
			return true;
		else
			return false;
	}

	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return userDao.getUserById(userId);
	}

	public boolean changeUser(User u, String[] _new, String[] _old) {
		// TODO Auto-generated method stub
		//都去掉第一个元素，因为是0，用来做逗号间隔的
		List<Integer> both = new ArrayList<Integer>();
		List<Integer> update10 = new ArrayList<Integer>();
		List<Integer> insertUpdate01 = new ArrayList<Integer>();
		
		doUpdate10AndInsertUpdate01(_new, _old, both, update10, insertUpdate01);
		
		return userDao.changeUser(u,update10,insertUpdate01);
	}
	/**
	 * 计算得到要update have 1->0的加入到update10中，
	 * 要插入或者是update have 0->1的加入到insertUpdate01中
	 * @param _new 变更后的roleId列表
	 * @param _old 原来have为1的roleId列表
	 * @param both 存放变更后和变更前共同的roleId列表
	 * @param update10 存放update have 1->0的roleId列表
	 * @param insertUpdate01 存放要insert或者是update have 0->1的roleId列表
	 */
	private void doUpdate10AndInsertUpdate01(String[] _new, String[] _old,
			List<Integer> both, List<Integer> update10,
			List<Integer> insertUpdate01) {
		
		for(int i=1;i<_new.length;i++){
			int newRoleId = Integer.parseInt(_new[i]);
			for(int j=1;j<_old.length;j++){
				int oldRoleId = Integer.parseInt(_old[j]);
				if(newRoleId == oldRoleId){
					//原来和变更后都有
					both.add(newRoleId);
					break;
				}
			}
		}
		//在_old中找不是both中的,就是需要update t_user_Role中的have从1到0的
		for(int m=1;m<_old.length;m++){
			int oldRoleId = Integer.parseInt(_old[m]);
			boolean isHave = false;
			for(Integer b : both){
				if(oldRoleId == b){
					isHave = true;
					break;
				}
			}
			//找到both中不是_old中的
			if(!isHave){
				update10.add(oldRoleId);
			}
		}
		//在_new中找到不是both中的,就是需要insert t_user_Role中的
		for(int n=1;n<_new.length;n++){
			int newRoleId = Integer.parseInt(_new[n]);
			boolean isHave = false;
			for(Integer b : both){
				if(newRoleId == b){
					isHave = true;
					break;
				}
			}
			//找到both中不是_new中的,有就0->1,没有就insert
			if(!isHave){
				insertUpdate01.add(newRoleId);
			}
		}
		/*System.out.println("_new:"+_new.length);
		System.out.println("_old:"+_old.length);
		System.out.println("both:"+both.toString());
		System.out.println("update10:"+update10.toString());
		System.out.println("insertUpdate01:"+insertUpdate01.toString());*/
	}

	public boolean addAdmin(User u) {
		// TODO Auto-generated method stub
		try {
			if(userDao.addAdmin(u) > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
