package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.JiangHuRen;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class JiangHuRenCondition extends QPCondition {
	Boolean bDel;
	String userName;
	Integer userId;
	Date gtUpdateTime;
	Date gtCreateTime;
	PublicEnum.RoleType roleType;
	List<Integer> inPaiId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(JiangHuRen.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("user_name", userName);
		eq("user_id", userId);
		gt("update_time", gtUpdateTime);
		gt("create_time", gtCreateTime);
		eq("role_type", roleType);
		in("pai_id", inPaiId);
		super.initCriteria(sessionFactory);
	}

	public void setRoleType(PublicEnum.RoleType roleType) {
		this.roleType = roleType;
	}

	public void setInPaiId(List<Integer> inPaiId) {
		this.inPaiId = inPaiId;
	}

	public void setGtCreateTime(Date gtCreateTime) {
		this.gtCreateTime = gtCreateTime;
	}

	public void setGtUpdateTime(Date gtUpdateTime) {
		this.gtUpdateTime = gtUpdateTime;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
