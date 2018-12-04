package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.condition.UserRoleCondition;
import com.qingpu.phone.user.entity.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userRoleService")
@Transactional
public class UserRoleService {

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param userRole
	 * @return
	 */
	public UserRole create(UserRole userRole) {
		if(userRole != null && StringUtils.isBlank(userRole.getId())){
			userRole.setId(UUIDGenerator.getUUID());
		}
		return (UserRole) baseDao.save(userRole);
	}


	/**
	 * 更新
	 * @param userRole
	 */
	public void update(UserRole userRole) {
		baseDao.update(userRole);
	}


	/**
	 * 删除
	 * @param userRole
	 */
	public void delete(UserRole userRole) {
		userRole.setIsDel(true);
		userRole.setDelTime(new Date());
		baseDao.update(userRole);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public UserRole get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (UserRole)baseDao.get(UserRole.class, id);
	}


	/**
	 * 按条件查找
	 * @param userRoleCondition
	 * @return
	 */
	public List<UserRole> list(UserRoleCondition userRoleCondition){
		return  userRoleCondition.list(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param userRoleCondition
	 * @return
	 */
	public List<Object[]> listCustom(UserRoleCondition userRoleCondition){
		return  userRoleCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param userRoleCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(UserRoleCondition userRoleCondition, int num){
		return  userRoleCondition.list(sessionFactory, num);
	}

	/**
	 * 按条件排序
	 * @param userRoleCondition
	 * @param sorter
	 * @return
	 */
	public List<UserRole> list(UserRoleCondition userRoleCondition, Sorter sorter){
		return  userRoleCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param userRoleCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<UserRole> list(UserRoleCondition userRoleCondition, Range range, Sorter sorter){
		PaginationSupport<UserRole> userRolePaginationSupport = (PaginationSupport<UserRole>)userRoleCondition.pageList(sessionFactory, sorter, range);
		return userRolePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param userRoleCondition
	 * @return
	 */
	public Long countByCondition(UserRoleCondition userRoleCondition){
		return   userRoleCondition.countByCondition(sessionFactory);
	}

    /**
     * 初始化
	 */
	public void init(String roleId, Integer userId){
		UserRoleCondition userRoleCondition = new UserRoleCondition();
		userRoleCondition.setRoleId(roleId);
		userRoleCondition.setUserId(userId);
		List<UserRole> userRoleList = userRoleCondition.list(sessionFactory);
		if(userRoleList.isEmpty()){
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			this.create(userRole);
		}
	}

	/**
	 * 获取未删除的用户角色
	 * @param userId
	 * @return
	 */
	public List<UserRole> getByUserId(Integer userId){
		UserRoleCondition userRoleCondition = new UserRoleCondition();
		userRoleCondition.setUserId(userId);
		return this.list(userRoleCondition);
	}

	/**
	 * 为后台登录用户获取角色id
	 * @param userId
	 * @return
	 */
	public String getRoleIdForPlatform(Integer userId){
		List<UserRole> userRoleList = this.getByUserId(userId);
		List<String> roleIdList = new ArrayList<>();
		for(UserRole userRole : userRoleList){
			roleIdList.add(userRole.getRoleId());
		}
		if(roleIdList.size() > 0){
			if(roleIdList.contains(QingpuConstants.Roles_Super_id)){
				return QingpuConstants.Roles_Super_id;
			}else if(roleIdList.contains(QingpuConstants.Roles_Manager_id)){
				return QingpuConstants.Roles_Manager_id;
			}else if(roleIdList.contains(QingpuConstants.Roles_Employee_id)){
				return QingpuConstants.Roles_Employee_id;
			}else{
				return roleIdList.get(0);
			}
		}
		return null;
	}

	/**
	 * 获取用户角色ids，用逗号分隔
	 * @param userId
	 * @return
	 */
	public String getRoleIdsByUserId(Integer userId){
		List<UserRole> userRoleList = this.getByUserId(userId);
		String roleIds = "";
		for(UserRole userRole : userRoleList){
			roleIds += userRole.getRoleId() + ",";
		}
		return StringUtil.subLastChar(roleIds);
	}

	/**
	 * 给用户设置权限
	 * @param roleIds
	 * @param userId
	 * @param operatorId
	 */
	public void set(String roleIds, Integer userId, Integer operatorId){
		if(QingpuConstants.User_Admin_Id.equals(userId) ){
			return;
		}
		List<UserRole> userRoleList = this.getByUserId(userId);
		List<String> roleIdList = StringUtil.splitByComma(roleIds);
		for(UserRole userRole : userRoleList){
			String roleId = userRole.getRoleId();
			if( roleIdList.contains(roleId)){
				roleIdList.remove(roleId);
			}else{
				userRole.setDelUserId(operatorId);
				this.delete(userRole);
			}
		}
		for(String roleId : roleIdList){
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRole.setDelUserId(operatorId);
			this.create(userRole);
		}
	}
}
