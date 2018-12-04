package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.LuckDraw;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class LuckDrawCondition extends QPCondition {
	Boolean bDel;
	Integer gtCount;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(LuckDraw.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(gtCount != null){
			this.criteria.add( Restrictions.gt("count", gtCount) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setGtCount(Integer gtCount) {
		this.gtCount = gtCount;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
