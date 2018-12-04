package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.PermissionsCondition;
import com.qingpu.phone.user.condition.RolePermissionCondition;
import com.qingpu.phone.user.entity.Permissions;
import com.qingpu.phone.user.entity.RolePermission;
import com.qingpu.phone.user.entity.Roles;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("rolePermissionService")
@Transactional
public class RolePermissionService {
	private static Logger logger = Logger.getLogger(RolePermissionService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private PermissionsService permissionsService;

	@Resource
	private RolesService rolesService;

	/**
	 * 创建
	 * @param rolePermission
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public RolePermission create(RolePermission rolePermission) throws Exception{
		if(rolePermission != null && StringUtils.isBlank(rolePermission.getId())){
			rolePermission.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(rolePermission);
		return (RolePermission) baseDao.save(rolePermission);
	}


	/**
	 * 更新
	 * @param rolePermission
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(RolePermission rolePermission)  throws Exception {
		CommonUtils.checkEntity(rolePermission);
		baseDao.update(rolePermission);
	}


	/**
	 * 删除
	 * @param rolePermission
	 */
	public void delete(RolePermission rolePermission) {
		rolePermission.setIsDel(true);
		rolePermission.setDelTime(new Date());
		baseDao.update(rolePermission);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public RolePermission get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (RolePermission)baseDao.get(RolePermission.class, id);
	}


	/**
	 * 按条件查找
	 * @param rolePermissionCondition
	 * @return
	 */
	public List<RolePermission> list(RolePermissionCondition rolePermissionCondition){
		return  rolePermissionCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param rolePermissionCondition
	 * @param sorter
	 * @return
	 */
	public List<RolePermission> list(RolePermissionCondition rolePermissionCondition, Sorter sorter){
		return  rolePermissionCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param rolePermissionCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<RolePermission> list(RolePermissionCondition rolePermissionCondition, Range range, Sorter sorter){
		PaginationSupport<RolePermission> rolePermissionPaginationSupport = (PaginationSupport<RolePermission>)rolePermissionCondition.pageList(sessionFactory, sorter, range);
		return rolePermissionPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param rolePermissionCondition
	 * @return
	 */
	public Long countByCondition(RolePermissionCondition rolePermissionCondition){
		return   rolePermissionCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param rolePermissionCondition
	 * @return
	 */
	public List<Object[]> listCustom(RolePermissionCondition rolePermissionCondition){
		return  rolePermissionCondition.list(sessionFactory);
	}


	public List<String> listSingle(RolePermissionCondition rolePermissionCondition){
		return  rolePermissionCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param rolePermissionCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(RolePermissionCondition rolePermissionCondition, int num){
		return  rolePermissionCondition.list(sessionFactory, num);
	}

	/**
	 * 创建菜单权限记录
	 * @param roleId
	 * @param permissionId
	 */
	private void init(String roleId, String permissionId){
		RolePermission rolePermission = this.getByRoleIdAndPermissionsId(roleId, permissionId);
		if(rolePermission == null){
			rolePermission = new RolePermission();
			rolePermission.setRoleId(roleId);
			rolePermission.setPermissionId(permissionId);
			try{
				this.create(rolePermission);
			}catch (Exception e){
				logger.error("初始化角色菜单失败：" + roleId);
			}
		}
	}

	/**
	 * 根据角色id初始化菜单权限
	 * @param permissionsIdList
	 * @param roleId
	 */
	public void init(List<String> permissionsIdList, String roleId){
		for(String permissionsId : permissionsIdList){
			this.init(roleId, permissionsId);
		}
	}

	/**
	 * 获取角色权限
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	public RolePermission getByRoleIdAndPermissionsId(String roleId, String permissionId){
		RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
		rolePermissionCondition.setRoleId(roleId);
		rolePermissionCondition.setbDel(null);
		rolePermissionCondition.setPermissionId(permissionId);
		List<RolePermission> rolePermissionList = rolePermissionCondition.list(sessionFactory);
		if(rolePermissionList.isEmpty()){
			return null;
		}else{
			return rolePermissionList.get(0);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> getByRoleId(String roleId) throws Exception{
		if(StringUtils.isBlank(roleId)){
			throw new Exception("请传入角色id");
		}
		Roles roles = rolesService.get(roleId);
		if(roles == null){
			throw new Exception("未找到该角色");
		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
		rolePermissionCondition.setRoleId(roleId);
		rolePermissionCondition.setOnlyPermissionId(true);
		List<String> permissionIdList = this.listSingle(rolePermissionCondition);

		PermissionsCondition permissionsCondition = new PermissionsCondition();
		List<Permissions> permissionsList = permissionsService.list(permissionsCondition, new Sorter().asc("show_index"));
		for(Permissions permissions : permissionsList){
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(permissions);
			Boolean isHave = false;
			if(permissionIdList.contains(permissions.getId())){
				isHave = true;
			}
			map.put("isHave", isHave);
			resultList.add(map);
		}
		return resultList;
	}
}
