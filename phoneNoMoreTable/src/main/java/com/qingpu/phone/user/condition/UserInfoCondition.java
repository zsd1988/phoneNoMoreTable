package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.UserInfo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserInfoCondition extends QPCondition {
	Boolean bDel;
	String name;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(UserInfo.class);
		eq("name", name);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
