package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.Project;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class ProjectCondition extends QPCondition {
	Boolean bDel = false;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
