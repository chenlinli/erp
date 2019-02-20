package com.cl.erp.dao;

import java.util.List;

public interface IBaseDao<T> {

	List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults);
	long getCount(T t1,T t2,Object param);
	void add(T t);
	public void delete(Long uuid);
	T get(Long uuid);
	void update(T t);
}
