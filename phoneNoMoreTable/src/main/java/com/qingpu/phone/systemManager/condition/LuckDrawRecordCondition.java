package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.LuckDrawRecord;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

public class LuckDrawRecordCondition extends QPCondition {
	Boolean bDel;
	Date gtCreateTime;
	Date ltCreateTime;
	String name;
	String userName;
	String likeUserName;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(LuckDrawRecord.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		like("user_name", likeUserName, MatchMode.ANYWHERE);
		if(gtCreateTime != null){
			this.criteria.add( Restrictions.gt("create_time", gtCreateTime) );
		}
		if(ltCreateTime != null){
			this.criteria.add( Restrictions.lt("create_time", ltCreateTime) );
		}
		if(StringUtils.isNotBlank(name)){
			this.criteria.add( Restrictions.eq("name", name) );
		}
		eq("user_name", userName);
		super.initCriteria(sessionFactory);
	}

	public void setLikeUserName(String likeUserName) {
		this.likeUserName = likeUserName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setName(String name) {
		this.name = name;
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
