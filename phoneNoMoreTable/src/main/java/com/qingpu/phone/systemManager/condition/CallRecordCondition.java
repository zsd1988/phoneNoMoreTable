package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
public class CallRecordCondition extends QPCondition {
	Boolean bDel = false;
	String phone;
	Date createTime;
	String clientId;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(CallRecord.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		eq("client_id", clientId);
		eq("create_time", createTime);
		if(StringUtils.isNotBlank(phone)){
			this.criteria.add( Restrictions.eq("phone", phone) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
