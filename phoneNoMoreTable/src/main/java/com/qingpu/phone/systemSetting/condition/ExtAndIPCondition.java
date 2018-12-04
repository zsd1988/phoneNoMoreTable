package com.qingpu.phone.systemSetting.condition;

import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemSetting.entity.ExtAndIP;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class ExtAndIPCondition extends QPCondition {
	Boolean bDel;
	String ip;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(ExtAndIP.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(StringUtils.isNotBlank(ip)){
			this.criteria.add( Restrictions.eq("ip", ip) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
