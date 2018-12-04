package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.JiangHuPaiRank;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

public class JiangHuPaiRankCondition extends QPCondition {
	Boolean bDel;
	Date gtCreateTime;
	Date ltCreateTime;
	Date geCreateTime;
	PublicEnum.RoleType roleType;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(JiangHuPaiRank.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("role_type", roleType);
		lt("create_time", ltCreateTime);
		gt("create_time", gtCreateTime);
		ge("create_time", geCreateTime);
		super.initCriteria(sessionFactory);
	}

	public void setRoleType(PublicEnum.RoleType roleType) {
		this.roleType = roleType;
	}

	public void setGtCreateTime(Date gtCreateTime) {
		this.gtCreateTime = gtCreateTime;
	}

	public void setLtCreateTime(Date ltCreateTime) {
		this.ltCreateTime = ltCreateTime;
	}

	public void setGeCreateTime(Date geCreateTime) {
		this.geCreateTime = geCreateTime;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
