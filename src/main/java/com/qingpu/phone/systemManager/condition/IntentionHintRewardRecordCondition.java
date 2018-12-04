package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.IntentionHintRewardRecord;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

public class IntentionHintRewardRecordCondition extends QPCondition {
	Boolean bDel;
	Date gtCreateTime;
	Date ltCreateTime;
	String hintId;
	PublicEnum.IntentionHintType type;
	String userName;
	String likeUserName;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(IntentionHintRewardRecord.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		like("user_name", likeUserName, MatchMode.ANYWHERE);
		eq("user_name", userName);
		eq("type", type);
		if(gtCreateTime != null){
			this.criteria.add( Restrictions.gt("create_time", gtCreateTime) );
		}
		if(ltCreateTime != null){
			this.criteria.add( Restrictions.lt("create_time", ltCreateTime) );
		}
		eq("hint_id", hintId);
		super.initCriteria(sessionFactory);
	}

	public void setLikeUserName(String likeUserName) {
		this.likeUserName = likeUserName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setType(PublicEnum.IntentionHintType type) {
		this.type = type;
	}

	public void setHintId(String hintId) {
		this.hintId = hintId;
	}

	public void setGtCreateTime(Date gtCreateTime) {
		this.gtCreateTime = gtCreateTime;
	}

	public void setLtCreateTime(Date ltCreateTime) {
		this.ltCreateTime = ltCreateTime;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
