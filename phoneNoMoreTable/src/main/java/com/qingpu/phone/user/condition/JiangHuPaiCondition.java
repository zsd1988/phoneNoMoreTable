package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.JiangHuPai;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class JiangHuPaiCondition extends QPCondition {
	Boolean bDel;
	PublicEnum.RoleType roleType;
	Integer neId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(JiangHuPai.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		ne("id", neId);
		eq("role_type", roleType);
		super.initCriteria(sessionFactory);
	}

	public void setNeId(Integer neId) {
		this.neId = neId;
	}

	public void setRoleType(PublicEnum.RoleType roleType) {
		this.roleType = roleType;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
