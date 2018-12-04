package com.qingpu.phone.common.dao;

import com.qingpu.phone.common.entity.PageInfo;

import java.util.List;

//数据库操作的基类接口定义
public interface BaseDao {
	/**
	 * 插入一条数据
	 * */
	Object save(Object entity);
	
	/**
	 * 更新一条数据
	 * */
	void update(String entityName, Object entity);
	
	void update(Object entity);
	
	/**
	 * 保存或者新增对象
	 * */
	Object saveOrUpdate(Object entity);
	
	Object saveOrUpdate(String entityName, Object entity);

	/**
	 * 批量保存数据
	 * @param list
	 * @return
	 */
	void saveOrUpdateAll(List<?> list);
	
	/**
	 * 删除对象
	 * */
	void delete(Object entity);
	
	/**
	 * 获取指定id的业务对象
	 * */
	Object get(final Class<?> clazz, Object id);
	
	/**
	 * 不带参数的hql语句查询
	 * @return List
	 * */
	List<?> findByHql(String hql);
	
	/**
	 * 带参数的hql语句查询
	 * @return List
	 * */
	List<?> findByHqlParams(String hql, Object[] params);
	
	/**
	 * 执行自定义hql语句，executeUpdate
	 * */
	Object execQueryHql(final String queryHql, final Object[] params);
	
	/**
	 * 执行自定义sql语句，executeUpdate
	 * */
	Object execQuerySql(final String querySql, final Object[] params);

	/**
	 * 分页查询
	 * @param t    					对象
	 * @param condition				条件
	 * @param pageInfo				page对象内
	 * @return
	 */
	public List<?>  queryListForPage(final Class t,final String condition,final PageInfo pageInfo) ;

	/**
	 * 查询总记录条数
	 * @param t
	 * @param condition
	 * @return
	 */
	public int  countListForPage(final Class t,final String condition) ;
}
