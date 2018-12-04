package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.RolePermission;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class RolePermissionCondition extends QPCondition {
	private String role_id;

	private Boolean bDel = false;

	private String permission_id;

	private Boolean isOnlyPermissionId = false;		// 是否只获取权限id

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(RolePermission.class);
		if(StringUtils.isNotBlank(role_id)){
			this.criteria.add( Restrictions.eq("role_id", role_id) );
		}
		if(StringUtils.isNotBlank(permission_id)){
			this.criteria.add( Restrictions.eq("permission_id", permission_id) );
		}
		if( bDel != null ){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		ProjectionList projectionList = Projections.projectionList();
		if(isOnlyPermissionId){
			projectionList.add(Projections.property("permission_id"));
			criteria.setProjection(projectionList);
		}
		super.initCriteria(sessionFactory);
	}

	public void setOnlyPermissionId(Boolean onlyPermissionId) {
		isOnlyPermissionId = onlyPermissionId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}

	public void setPermissionId(String permission_id) {
		this.permission_id = permission_id;
	}
}
