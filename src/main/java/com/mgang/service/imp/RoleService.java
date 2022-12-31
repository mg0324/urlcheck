package com.mgang.service.imp;

import java.util.ArrayList;
import java.util.List;

import com.mgang.dao.RoleDao;
import com.mgang.service.inter.IRole;
import com.mgang.util.Pager;
import com.mgang.vo.Role;
/**
 * 
 * @author meigang 2014-11-4 9:21
 * role的service实现
 */
public class RoleService implements IRole{
	private static RoleDao roleDao;
	public RoleService(){
		roleDao = new RoleDao();
	}
	public boolean addRole(Role r) {
		// TODO Auto-generated method stub
		if(roleDao.addRole(r) > 0){
			return true;
		}else{
			return false;
		}
	}
	public int countRole() {
		// TODO Auto-generated method stub
		return roleDao.getCount();
	}
	public Pager findRolePage(Pager page, String orderBy) {
		// TODO Auto-generated method stub
		return roleDao.getRolePage(page,orderBy);
	}
	public boolean deleteRole(Role r) {
		// TODO Auto-generated method stub
		if(roleDao.deleteRole(r) > 0){
			return true;
		}else{
			return false;
		}
		
	}
	public Role getRoleById(int roleId) {
		// TODO Auto-generated method stub
		return roleDao.getRoleById(roleId);
	}
	
	public boolean changeRole(Role r, String[] _new, String[] _old) {
		// TODO Auto-generated method stub
		//都去掉第一个元素，因为是0，用来做逗号间隔的
		List<Integer> both = new ArrayList<Integer>();
		List<Integer> update10 = new ArrayList<Integer>();
		List<Integer> insertUpdate01 = new ArrayList<Integer>();
		
		doUpdate10AndInsertUpdate01(_new, _old, both, update10, insertUpdate01);
		
		return roleDao.changeRole(r,update10,insertUpdate01);
	}
	/**
	 * 计算得到要update have 1->0的加入到update10中，
	 * 要插入或者是update have 0->1的加入到insertUpdate01中
	 * @param _new 变更后的funId列表
	 * @param _old 原来have为1的funId列表
	 * @param both 存放变更后和变更前共同的funId列表
	 * @param update10 存放update have 1->0的funId列表
	 * @param insertUpdate01 存放要insert或者是update have 0->1的funId列表
	 */
	private void doUpdate10AndInsertUpdate01(String[] _new, String[] _old,
			List<Integer> both, List<Integer> update10,
			List<Integer> insertUpdate01) {
		
		for(int i=1;i<_new.length;i++){
			int newFunId = Integer.parseInt(_new[i]);
			for(int j=1;j<_old.length;j++){
				int oldFunId = Integer.parseInt(_old[j]);
				if(newFunId == oldFunId){
					//原来和变更后都有
					both.add(newFunId);
					break;
				}
			}
		}
		//在_old中找不是both中的,就是需要update t_role_function中的have从1到0的
		for(int m=1;m<_old.length;m++){
			int oldFunId = Integer.parseInt(_old[m]);
			boolean isHave = false;
			for(Integer b : both){
				if(oldFunId == b){
					isHave = true;
					break;
				}
			}
			//找到both中不是_old中的
			if(!isHave){
				update10.add(oldFunId);
			}
		}
		//在_new中找到不是both中的,就是需要insert t_role_function中的
		for(int n=1;n<_new.length;n++){
			int newFunId = Integer.parseInt(_new[n]);
			boolean isHave = false;
			for(Integer b : both){
				if(newFunId == b){
					isHave = true;
					break;
				}
			}
			//找到both中不是_new中的,有就0->1,没有就insert
			if(!isHave){
				insertUpdate01.add(newFunId);
			}
		}
		/*System.out.println("_new:"+_new.length);
		System.out.println("_old:"+_old.length);
		System.out.println("both:"+both.toString());
		System.out.println("update10:"+update10.toString());
		System.out.println("insertUpdate01:"+insertUpdate01.toString());*/
	}
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return roleDao.getAllRoles();
	}

}
