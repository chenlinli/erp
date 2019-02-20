package com.cl.erp.biz;

import java.util.List;

public interface IBaseBiz<T> {
	List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults);
	long getCount(T t1,T t2,Object param);
	void add(T t);
	public void delete(Long uuid);
	T get(Long uuid);
	void update(T t);
}
