package test.testTable.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import test.testTable.entity.TestTable;

public class TestTableCondition extends QPCondition {
	Boolean bDel;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(TestTable.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
