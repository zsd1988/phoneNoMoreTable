package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.FunctionPermissions;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import test.testTable.entity.TestTable;

import java.util.List;

public class FunctionPermissionsCondition extends QPCondition {
	Boolean bDel = false;
	List<String> functionPermissionsList;
	List<String> noFunctionPermissionsList;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(FunctionPermissions.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}

		if(functionPermissionsList!=null&&functionPermissionsList.size()>0){
			this.criteria.add( Restrictions.in("id", functionPermissionsList) );
		}


		super.initCriteria(sessionFactory);
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setFunctionPermissionsList(List<String> functionPermissionsList) {
		this.functionPermissionsList = functionPermissionsList;
	}
}
