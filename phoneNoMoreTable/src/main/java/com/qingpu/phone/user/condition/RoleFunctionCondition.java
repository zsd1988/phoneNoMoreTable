package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.RoleFunction;
import com.qingpu.phone.user.entity.RolePermission;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RoleFunctionCondition extends QPCondition {
	private String role_id;

	private Boolean bDel = false;



	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(RoleFunction.class);
		if(StringUtils.isNotBlank(role_id)){
			this.criteria.add( Restrictions.eq("role_id", role_id) );
		}

		if( bDel != null ){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}

		super.initCriteria(sessionFactory);
	}

	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
