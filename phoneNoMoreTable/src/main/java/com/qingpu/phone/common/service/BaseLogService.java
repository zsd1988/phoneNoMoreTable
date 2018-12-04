package com.qingpu.phone.common.service;

import java.util.List;

import com.qingpu.phone.common.dao.BaseLogDao;

public interface BaseLogService<T> extends BaseLogDao<T> {

	/**
	 * @Desc 保存日志
	 * */
	@Override
	public void saveLog(T t);

	/**
	 * @Desc 使用hql语句查询日志
	 * */
	public List<T> findLogList(String type, String desc);

	/**
	 * @Desc 使用sql语句查询相关日志列表
	 * */
	public List<T> findLogListBySql(String type, String desc, final Object[] params, final Class objectClass);

	/**
	 * @Desc 保存或者更新日志
	 * */
	@Override
	Object saveOrUpdate(Object entity);

}
