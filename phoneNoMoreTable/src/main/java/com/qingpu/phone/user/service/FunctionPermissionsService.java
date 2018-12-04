package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.EncryptUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.condition.FunctionPermissionsCondition;
import com.qingpu.phone.user.entity.FunctionPermissions;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("functionPermissionsService")
@Transactional
public class FunctionPermissionsService {
	private static Logger logger = Logger.getLogger(FunctionPermissionsService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param functionPermissions
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public FunctionPermissions create(FunctionPermissions functionPermissions) throws Exception{
		if(functionPermissions != null && StringUtils.isBlank(functionPermissions.getId())){
			functionPermissions.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(functionPermissions);
		return (FunctionPermissions) baseDao.save(functionPermissions);
	}


	/**
	 * 更新
	 * @param functionPermissions
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(FunctionPermissions functionPermissions)  throws Exception {
		CommonUtils.checkEntity(functionPermissions);
		baseDao.update(functionPermissions);
	}


	/**
	 * 删除
	 * @param functionPermissions
	 */
	public void delete(FunctionPermissions functionPermissions) {
		functionPermissions.setIsDel(true);
		baseDao.update(functionPermissions);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public FunctionPermissions get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (FunctionPermissions)baseDao.get(FunctionPermissions.class, id);
	}


	/**
	 * 按条件查找
	 * @param functionPermissionsCondition
	 * @return
	 */
	public List<FunctionPermissions> list(FunctionPermissionsCondition functionPermissionsCondition){
		return  functionPermissionsCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param functionPermissionsCondition
	 * @param sorter
	 * @return
	 */
	public List<FunctionPermissions> list(FunctionPermissionsCondition functionPermissionsCondition, Sorter sorter){
		return  functionPermissionsCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param functionPermissionsCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<FunctionPermissions> list(FunctionPermissionsCondition functionPermissionsCondition, Range range, Sorter sorter){
		PaginationSupport<FunctionPermissions> functionPermissionsPaginationSupport = (PaginationSupport<FunctionPermissions>)functionPermissionsCondition.pageList(sessionFactory, sorter, range);
		return functionPermissionsPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param functionPermissionsCondition
	 * @return
	 */
	public Long countByCondition(FunctionPermissionsCondition functionPermissionsCondition){
		return   functionPermissionsCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param functionPermissionsCondition
	 * @return
	 */
	public List<Object[]> listCustom(FunctionPermissionsCondition functionPermissionsCondition){
		return  functionPermissionsCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param functionPermissionsCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(FunctionPermissionsCondition functionPermissionsCondition, int num){
		return  functionPermissionsCondition.list(sessionFactory, num);
	}

	/**
	 * 根据参数创建
	 * @param functionPermissions
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(FunctionPermissions functionPermissions) throws Exception{
		functionPermissions = this.create(functionPermissions);
		Map<String, Object> map = new HashMap<>();
		map.put("id", functionPermissions.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsFunctionPermissions
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(FunctionPermissions paramsFunctionPermissions) throws Exception{
		FunctionPermissions functionPermissions = this.get(paramsFunctionPermissions.getId());
		if(functionPermissions == null){
			throw new Exception("未找到对象");
		}
		this.update(functionPermissions);
	}


	/**
	 * 初始化表数据
	 */
	public void init() {
		/*
		超级管理员
		 */
		// 建立表数据
		init(QingpuConstants.Function_Customer_Id, "root","客户信息" );
		init(QingpuConstants.Function_Hide_Telephone_Id, QingpuConstants.Function_Customer_Id,"隐藏客户号码中间四位" );
		init(QingpuConstants.Function_Customer_Add_Id, QingpuConstants.Function_Customer_Id,"添加查询客户" );
		init(QingpuConstants.Function_Customer_Del_Id, QingpuConstants.Function_Customer_Id,"删除客户" );
		init(QingpuConstants.Function_Customer_Move_Id, QingpuConstants.Function_Customer_Id,"移动组" );
		init(QingpuConstants.Function_Customer_Resources_Id, QingpuConstants.Function_Customer_Id,"客户资源分配" );
		init(QingpuConstants.Function_Traffic_Id, "root","话务信息" );
		//init(QingpuConstants.Function_Traffic_Telephone_Id, QingpuConstants.Function_Traffic_Id,"只查看个人通话记录" );
		init(QingpuConstants.Function_Traffic_Export_Id, QingpuConstants.Function_Traffic_Id,"导出通话记录" );
		init(QingpuConstants.Function_Traffic_Select_Id, QingpuConstants.Function_Traffic_Id,"查看历史通话记录联系人" );
		init(QingpuConstants.Function_FindAllCallRecord, QingpuConstants.Function_Traffic_Id,"查看所有通话记录" );
	}

	/**
	 * 初始化表数据
	 * @param id
	 * @param fid
	 * @param permissionName
	 */
	private void init(String id, String fid, String permissionName) {
		FunctionPermissions functionPermissions = this.get(id);
		if(functionPermissions == null){
			functionPermissions = new FunctionPermissions();
			functionPermissions.setId(id);
			functionPermissions.setFid(fid);
			functionPermissions.setPermissionName(permissionName);
			try{
				this.create(functionPermissions);
			}catch (Exception e){
				e.printStackTrace();;
			}

		}
	}

}
