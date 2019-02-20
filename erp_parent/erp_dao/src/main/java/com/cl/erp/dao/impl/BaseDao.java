package com.cl.erp.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cl.erp.dao.IBaseDao;

import oracle.net.aso.a;


public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	private Class<T> entityClass;
	
	public BaseDao() {
		//通过子类获取父类
		Type baseDaoClass = getClass().getGenericSuperclass();
		//转成参数化类型
		ParameterizedType pType = (ParameterizedType) baseDaoClass;
		//参数类型数组<T,V,A>
		Type[] actualTypeArguments = pType.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	/**
	 * 条件查询
	 */
	@Override
	public List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults) {
		DetachedCriteria dc = buildDetachCriteria(t1);
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc,firstResult,maxResults);
	}

	/**
	 * 计算条件查询总记录数
	 */
	@Override
	public long getCount(T t1,T t2,Object param) {
		DetachedCriteria dc = buildDetachCriteria(t1);
		dc.setProjection(Projections.rowCount());
		return (long) getHibernateTemplate().findByCriteria(dc).get(0);
	}

	@Override
	public void add(T t1) {
		getHibernateTemplate().save(t1);
	}

	@Override
	public void delete(Long uuid) {
		
		T t1 = getHibernateTemplate().get(entityClass, uuid);
		this.getHibernateTemplate().delete(t1);
 	}
	/**
	 * 子类重写
	 * @param 
	 * @return
	 */
	public DetachedCriteria buildDetachCriteria(T t1){
		return null;
	}

	@Override
	public T get(Long uuid) {
		return getHibernateTemplate().get(entityClass, uuid);
	}

	@Override
	public void update(T t1) {
		getHibernateTemplate().update(t1);
	}

}
