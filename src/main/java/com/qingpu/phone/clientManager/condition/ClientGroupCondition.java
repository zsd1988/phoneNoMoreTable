package com.qingpu.phone.clientManager.condition;

import com.qingpu.phone.clientManager.entity.ClientGroup;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientGroupCondition extends QPCondition {
	Boolean bDel;
	String clientGroupName;
	String noGroupId;
	List<String> groupIdList;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(ClientGroup.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}

		if(StringUtils.isNotBlank(clientGroupName)){
			this.criteria.add( Restrictions.eq("client_group_name", clientGroupName) );
		}

		if(StringUtils.isNotBlank(noGroupId)){
			this.criteria.add( Restrictions.ne("id", noGroupId) );
		}

		if(groupIdList!=null&&groupIdList.size()>0){
			this.criteria.add( Restrictions.in("id", groupIdList) );
		}

		super.initCriteria(sessionFactory);
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setClientGroupName(String clientGroupName) {
		this.clientGroupName = clientGroupName;
	}

	public void setNoGroupId(String noGroupId) {
		this.noGroupId = noGroupId;
	}

	public void setGroupIdList(List<String> groupIdList) {
		this.groupIdList = groupIdList;
	}
}
