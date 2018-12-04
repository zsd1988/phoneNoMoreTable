package com.qingpu.phone.systemSetting.condition;

import com.qingpu.phone.systemSetting.entity.Parameter;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class ParameterCondition extends QPCondition {

	private String code;


	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Parameter.class);
		eq("code", code);
		super.initCriteria(sessionFactory);
	}

	public void setCode(String code) {
		this.code = code;
	}
}
