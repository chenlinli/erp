package com.cl.erp.biz.impl;

import java.util.List;

import com.cl.erp.biz.IBaseBiz;
import com.cl.erp.dao.IBaseDao;

public class BaseBiz<T> implements IBaseBiz<T> {
	/**
	 * 数据访问层调用
	 */
	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 条件查询
	 */
	@Override
	public List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults) {
		return baseDao.getList(t1,t2,param,firstResult,maxResults);
	}

	@Override
	public long getCount(T t1,T t2,Object param) {
		return baseDao.getCount(t1,t2,param);
	}
	@Override
	public void add(T t) {
		baseDao.add(t);
	}

	@Override
	public void delete(Long uuid) {
		baseDao.delete(uuid);
	}

	@Override
	public T get(Long uuid) {
		return (T) baseDao.get(uuid);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

}
