package com.mgang.service.imp;

import java.util.List;

import com.mgang.dao.FunctionDao;
import com.mgang.service.inter.IFunction;
import com.mgang.util.Pager;
import com.mgang.vo.Function;
/**
 * 
 * @author meigang 2014-11-4 9:22
 * FunctionService的实现
 */
public class FunctionService implements IFunction{
	private static FunctionDao funDao;
	public FunctionService(){
		funDao = new FunctionDao();
	}
	
	public boolean addFunction(Function f) {
		// TODO Auto-generated method stub
		if(funDao.addFunction(f) > 0){
			return true;
		}else{
			return false;
		}
		
	}

	public int countFunction() {
		// TODO Auto-generated method stub
		return funDao.getCount();
	}

	public Pager findFunPage(Pager page, String orderBy) {
		// TODO Auto-generated method stub
		return funDao.getFunPage(page,orderBy);
	}

	public boolean deleteFunction(Function f) {
		// TODO Auto-generated method stub
		if(funDao.deleteFunction(f) > 0){
			return true;
		}else{
			return false;
		}
	}

	public boolean updateFunction(Function f) {
		// TODO Auto-generated method stub
		if(funDao.updateFunction(f) > 0){
			return true;
		}else{
			return false;
		}
	}

	public Function getFunctionById(int funId) {
		// TODO Auto-generated method stub
		return funDao.getFunctionById(funId);
	}

	public List<Function> getAllFunctions() {
		// TODO Auto-generated method stub
		return funDao.getAllFunctions();
	}

}
