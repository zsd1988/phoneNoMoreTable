package com.qingpu.phone.common.dao;

import com.qingpu.phone.common.entity.PageInfo;
import org.hibernate.*;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 基础dao实现类
 * */
@Repository("baseDao")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{
	
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	protected Query createCachedQuery(String hql){
		return getSessionFactory().getCurrentSession().createQuery(hql);
	}
	
	/**
	 * 传递参数进行hql查询
	 * */
	@Override
	public List<?> findByHqlParams(String hql, Object[] params){
		if(params == null){
			return getSessionFactory().getCurrentSession().createQuery(hql).list();
		}
		
		Query q = getSessionFactory().getCurrentSession().createQuery(hql);
		for(int i = 0; i < params.length; i++){
			q.setParameter(i, params[i]);
		}
		
		return q.list();
	}

	@Override
	@Transactional(readOnly = false)
	public Object save(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(String entityName, Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entityName, entity);
	}

	@Override
	@Transactional(readOnly = false)
	public Object saveOrUpdate(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public Object saveOrUpdate(String entityName, Object entity){
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(entityName, entity);
		return entity;
	}

	@Override
	public void saveOrUpdateAll(List<?> list) {
		for(Object obj : list){
			saveOrUpdate(obj);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(entity);
	}

	@Override
	public Object get(final Class<?> clazz, Object id) {
		// TODO Auto-generated method stub		
		return getHibernateTemplate().get(clazz, (Serializable) id);
	}
	
	@SuppressWarnings("unchecked")
	public List<?> getAll(final Class clazz) {
		return (List<?>)getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
					String hql="from " + clazz.getName();
					Query query=session.createQuery(hql);
					return query.list();
			}
		});
	}

	@Override
	public List<?>  queryListForPage(final Class t,final String condition,final PageInfo pageInfo) {
		return (List<?>)getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="SELECT o FROM "+t.getSimpleName()+" o WHERE 1=1 "+condition;
				if (pageInfo==null) {
					return session.createQuery(hql).list();
				}
				List<?> tList = session.createQuery(hql).setFirstResult(pageInfo.getFirstRecordIndex())
						.setMaxResults(pageInfo.getPageMax())
						.list();
				return tList;
			}
		});
	}

	@Override
	public int  countListForPage(final Class t,final String condition) {
		return (int)getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="SELECT o FROM "+t.getSimpleName()+" o WHERE 1=1 "+condition;
				int count =session.createQuery(hql).list().size();
				return count;
			}
		});
	}


	@Override
	public List<?> findByHql(String hql) {
		// TODO Auto-generated method stub
		return findByHqlParams(hql, null);
	}
	
	/**
	 * 执行除了查询操作的自定义HQL语句，增、删、改等
	 * @param queryHql
	 * @return Integer
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object execQueryHql(final String queryHql, final Object[] params) {
		final String sql = queryHql;
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String querySentence = sql;
				Query query = session.createQuery(querySentence);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				return Integer.valueOf(query.executeUpdate());
			}
		});		
	}
	
	/**
	 * 执行除了查询操作的自定义SQL语句
	 * @param querySql
	 * @return Integer
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object execQuerySql(final String querySql,final Object[] params) {
		final String sql = querySql;
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String querySentence = sql;
				SQLQuery query = session.createSQLQuery(querySentence);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				//注意，此处调用的是executeUpdate()方法，执行成功返回1，否则返回0	
				return Integer.valueOf(query.executeUpdate());
			}
		});
	}
}
