package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.PostMessage;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class PostMessageCondition extends QPCondition {
	Boolean bDel;
	PublicEnum.PostMessageType type;
	Boolean isPush;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(PostMessage.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("is_push", isPush);
		eq("type", type);
		super.initCriteria(sessionFactory);
	}

    public void setPush(Boolean push) {
        isPush = push;
    }

    public void setType(PublicEnum.PostMessageType type) {
		this.type = type;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
