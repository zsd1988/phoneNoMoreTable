package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.Roles;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class RolesCondition extends QPCondition {
	Boolean bDel;
	String neId;
	String roleType;	//角色名称
	String roleId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Roles.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(StringUtils.isNotBlank(roleId)){
			this.criteria.add( Restrictions.eq("id", roleId) );
		}

		if(StringUtils.isNotBlank(neId)){
			this.criteria.add( Restrictions.ne("id", neId) );
		}
		if(roleType != null){
			this.criteria.add( Restrictions.eq("role_type", roleType) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setNeId(String neId) {
		this.neId = neId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setRoleType(String roleType) {this.roleType = roleType;	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
