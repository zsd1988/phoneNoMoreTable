package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.IntentionHint;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class IntentionHintCondition extends QPCondition {
	Boolean bDel;
	Integer gtCount;
	PublicEnum.IntentionHintType type;
	String likeUserName;
	Boolean isReward;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(IntentionHint.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("is_reward", isReward);
		like("user_name", likeUserName, MatchMode.ANYWHERE);
		eq("type", type);
		if(gtCount != null){
			this.criteria.add( Restrictions.gt("count", gtCount) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setReward(Boolean reward) {
		isReward = reward;
	}

	public void setLikeUserName(String likeUserName) {
		this.likeUserName = likeUserName;
	}

	public void setType(PublicEnum.IntentionHintType type) {
		this.type = type;
	}

	public void setGtCount(Integer gtCount) {
		this.gtCount = gtCount;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
