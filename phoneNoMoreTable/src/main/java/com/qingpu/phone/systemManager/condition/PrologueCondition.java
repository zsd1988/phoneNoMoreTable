package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.Prologue;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class PrologueCondition extends QPCondition {
	Boolean bDel;
	String projectId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Prologue.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("project_id", projectId);
		super.initCriteria(sessionFactory);
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
