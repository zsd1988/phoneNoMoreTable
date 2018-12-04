package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.condition.RolePermissionCondition;
import com.qingpu.phone.user.condition.RolesCondition;
import com.qingpu.phone.user.entity.Permissions;
import com.qingpu.phone.user.entity.RolePermission;
import com.qingpu.phone.user.entity.Roles;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("rolesService")
@Transactional
public class RolesService {
	Logger logger = Logger.getLogger(RolesService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private RolePermissionService rolePermissionService;

	/**
	 * 创建
	 * @param roles
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Roles create(Roles roles) throws Exception{
		if(roles != null && StringUtils.isBlank(roles.getId())){
			roles.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(roles);
		return (Roles) baseDao.save(roles);
	}





	/**
	 * 更新
	 * @param roles
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Roles roles)  throws Exception {
		CommonUtils.checkEntity(roles);
		baseDao.update(roles);
	}


	/**
	 * 删除
	 * @param roles
	 */
	public void delete(Roles roles) {
		roles.setIsDel(true);
		roles.setDelTime(new Date());
		baseDao.update(roles);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Roles get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (Roles)baseDao.get(Roles.class, id);
	}



	/**
	 * 按条件查找
	 * @param rolesCondition
	 * @return
	 */
	public List<Roles> list(RolesCondition rolesCondition){
		return  rolesCondition.list(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param rolesCondition
	 * @return
	 */
	public List<Object[]> listCustom(RolesCondition rolesCondition){
		return  rolesCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param rolesCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(RolesCondition rolesCondition, int num){
		return  rolesCondition.list(sessionFactory, num);
	}

	/**
	 * 按条件排序
	 * @param rolesCondition
	 * @param sorter
	 * @return
	 */
	public List<Roles> list(RolesCondition rolesCondition, Sorter sorter){
		return  rolesCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param rolesCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Roles> list(RolesCondition rolesCondition, Range range, Sorter sorter){
		PaginationSupport<Roles> rolesPaginationSupport = (PaginationSupport<Roles>)rolesCondition.pageList(sessionFactory, sorter, range);
		return rolesPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param rolesCondition
	 * @return
	 */
	public Long countByCondition(RolesCondition rolesCondition){
		return   rolesCondition.countByCondition(sessionFactory);
	}

	/**
	 * 初始化
	 * @param id
	 * @param roleType
	 */
	public void init(String id, String roleType, Permissions.Tag tag){
		Roles roles = this.get(id);
		if(roles == null){
			roles = new Roles();
			roles.setId(id);
			roles.setRoleType(roleType);
			try{
				this.create(roles);
			}catch (Exception e){
				logger.error("初始化角色表失败：" + id + " 角色类型为：" + roleType);
			}
		}
	}

	/**
	 * 初始化角色表
	 */
	public void init(){
		init(QingpuConstants.Roles_Super_id, QingpuConstants.User_Admin_NickName, Permissions.Tag.Admin);
		init(QingpuConstants.Roles_Manager_id, "管理员", Permissions.Tag.Manager);
		init(QingpuConstants.Roles_Employee_id, "客服", Permissions.Tag.Employee);
		init(QingpuConstants.Roles_Interview_id, "约访", Permissions.Tag.Interview);
		init(QingpuConstants.Roles_Quality_id, "质检", Permissions.Tag.Quality);
	}

	/**
	 * 获取角色key value
	 * @param isDel 为空则为获取全部的
	 * @return
	 */
	public List<Map<String, String>> getMap(Boolean isDel){
		List<Map<String, String>> mapList = new ArrayList<>();
		RolesCondition rolesCondition = new RolesCondition();
		rolesCondition.setbDel(isDel);
		rolesCondition.setNeId(QingpuConstants.Roles_Super_id);
		List<Roles> rolesList = this.list(rolesCondition);
		for(Roles roles : rolesList){
			Map<String, String> map = new HashMap<>();
			map.put("name", roles.getRoleType());
			map.put("id", roles.getId());
			mapList.add(map);
		}
		return  mapList;
	}


	/**
	 * 获取所有角色
	 * @param isDel 为空则为获取全部的
	 * @return
	 */
	public List<Map<String, String>> getRolesMap(Boolean isDel){
		List<Map<String, String>> mapList = new ArrayList<>();
		RolesCondition rolesCondition = new RolesCondition();
		rolesCondition.setbDel(isDel);
		List<Roles> rolesList = this.list(rolesCondition);
		for(Roles roles : rolesList){
			Map<String, String> map = new HashMap<>();
			map.put("name", roles.getRoleType());
			map.put("id", roles.getId());
			map.put("fid", "0");
			mapList.add(map);
		}
		return  mapList;
	}




	/**
	 * 获取非超级管理员角色
	 * @return
	 */
	public List<Roles> getListNeAdmin(){
		RolesCondition rolesCondition = new RolesCondition();
		rolesCondition.setNeId(QingpuConstants.Roles_Super_id);
		List<Roles> rolesList = this.list(rolesCondition);
		return rolesList;
	}

	/**
	 * 更新角色信息
	 * @param id
	 * @param roleType
	 * @param permissionIds
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(String id, String roleType, String permissionIds, Integer operatorId, Permissions.Tag tag) throws Exception{
		Roles roles = this.get(id);
		if(roles == null){
			throw new Exception("未找到角色");
		}
		roles.setRoleType(roleType);
		this.update(roles);

		/*
		更新用户菜单权限
		 */
		RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
		rolePermissionCondition.setRoleId(id);
		List<RolePermission> rolePermissionList = rolePermissionService.list(rolePermissionCondition);
		List<String> permissionIdList = StringUtil.splitByComma(permissionIds);
		// 去除删除了的权限
		for(RolePermission rolePermission : rolePermissionList){
			String permissionId = rolePermission.getPermissionId();
			if(permissionIdList.contains(permissionId)){
				permissionIdList.remove(permissionId);
			}else{
				rolePermission.setDelUserId(operatorId);
				rolePermissionService.delete(rolePermission);
			}
		}
		// 添加新增的权限
		for(String permissionId : permissionIdList){
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(permissionId);
			rolePermission.setRoleId(id);
			rolePermission.setDelUserId(operatorId);
			rolePermissionService.create(rolePermission);
		}
	}

	/**
	 * 根据id获取角色名称
	 * @param id
	 * @return
	 */
	public String getNameByRoleId(String id){
		Roles roles = this.get(id);
		String name = "";
		if(roles != null){
			name = roles.getRoleType();
		}
		return name;
	}

	/**
	 * 获取表下拉选择框
	 * @return
	 */
	public List<Map.Entry<String, String>> getSelect(){
		List<Roles> rolesList = this.getListNeAdmin();
		Map<String, String> map = new HashMap<>();
		for(Roles roles : rolesList){
			map.put(roles.getId(), roles.getRoleType());
		}
		List<Map.Entry<String, String>> infoIds =
				new ArrayList<>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return infoIds;
	}
}
