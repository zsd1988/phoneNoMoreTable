package com.qingpu.phone.systemSetting.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemSetting.entity.Port;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class PortCondition extends QPCondition {
	Boolean bDel;//= false
	PublicEnum.PortStatus status = null;
	String likeId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Port.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(status != null){
			this.criteria.add( Restrictions.eq("status", status) );
		}
		like("id", likeId, MatchMode.START);
		super.initCriteria(sessionFactory);
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public void setStatus(PublicEnum.PortStatus status) {
		this.status = status;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
