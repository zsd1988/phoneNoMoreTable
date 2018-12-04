package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserRoleCondition extends QPCondition {
	private String role_id;

	private String neRoleId;

	private Integer user_id;

	Boolean bDel = false;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class);
		if(StringUtils.isNotBlank(role_id)){
			this.criteria.add( Restrictions.eq("role_id", role_id) );
		}
		if(StringUtils.isNotBlank(neRoleId)){
			this.criteria.add( Restrictions.ne("role_id", neRoleId) );
		}
		if(user_id != null){
			this.criteria.add( Restrictions.eq("user_id", user_id) );
		}
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setNeRoleId(String neRoleId) {
		this.neRoleId = neRoleId;
	}

	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}
}
