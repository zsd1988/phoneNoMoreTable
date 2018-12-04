package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.systemManager.entity.GroupCall;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class GroupCallCondition extends QPCondition {
	Boolean isEnable;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(GroupCall.class);
		if(isEnable != null){
			this.criteria.add( Restrictions.eq("is_enable", isEnable) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setEnable(Boolean enable) {
		isEnable = enable;
	}
}
