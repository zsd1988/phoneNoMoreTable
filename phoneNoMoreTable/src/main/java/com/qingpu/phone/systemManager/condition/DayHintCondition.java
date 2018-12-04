package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.DayHint;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class DayHintCondition extends QPCondition {
	Boolean bDel;
	PublicEnum.DayHintType type;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(DayHint.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("type", type);
		super.initCriteria(sessionFactory);
	}

	public void setType(PublicEnum.DayHintType type) {
		this.type = type;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
