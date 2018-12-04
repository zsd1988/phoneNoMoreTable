package com.qingpu.phone.systemSetting.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemSetting.entity.App;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class AppCondition extends QPCondition {
	Boolean bDel;
	PublicEnum.OsType osType;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(App.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(osType != null){
			this.criteria.add( Restrictions.eq("os_type", osType) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setOsType(PublicEnum.OsType osType) {
		this.osType = osType;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
