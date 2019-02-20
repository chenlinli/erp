package com.cl.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cl.erp.dao.IDepDao;
import com.cl.erp.entity.Dep;

public class DepDao extends BaseDao<Dep> implements IDepDao {
	
	public DetachedCriteria buildDetachCriteria(Dep dep1){
		DetachedCriteria dc = DetachedCriteria.forClass(Dep.class);
		if(null!=dep1){
			//是否输入部门名称
			if(null!=dep1.getName()&&dep1.getName().trim().length()>0){
				//添加条件，MachMode ANYWHERE :% %
								//END：%name
				                //START name%
				dc.add(Restrictions.like("name", dep1.getName(),MatchMode.ANYWHERE));
			}
			if(null!=dep1.getTele()&&dep1.getTele().trim().length()>0){
				dc.add(Restrictions.like("tele", dep1.getTele(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

}
