package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.UserClientGroup;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserClientGroupCondition extends QPCondition {
	Boolean bDel;
	Integer userId;
	String projectId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(UserClientGroup.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(StringUtils.isNotBlank(projectId)){
			this.criteria.add( Restrictions.eq("project_id", projectId) );
		}

		if(userId!=null&&userId>0){
			this.criteria.add( Restrictions.eq("user_id", userId) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
