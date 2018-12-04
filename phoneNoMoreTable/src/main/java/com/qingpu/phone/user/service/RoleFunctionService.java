package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.FunctionPermissionsCondition;
import com.qingpu.phone.user.condition.PermissionsCondition;
import com.qingpu.phone.user.condition.RoleFunctionCondition;
import com.qingpu.phone.user.condition.RolesCondition;
import com.qingpu.phone.user.entity.FunctionPermissions;
import com.qingpu.phone.user.entity.Permissions;
import com.qingpu.phone.user.entity.RoleFunction;
import com.qingpu.phone.user.entity.Roles;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("roleFunctionService")
@Transactional
public class RoleFunctionService {
	private static Logger logger = Logger.getLogger(RoleFunctionService.class);

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
	 * @param roleFunction
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public RoleFunction create(RoleFunction roleFunction) throws Exception{
		if(roleFunction != null && StringUtils.isBlank(roleFunction.getId())){
			roleFunction.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(roleFunction);
		return (RoleFunction) baseDao.save(roleFunction);
	}


	/**
	 * 更新
	 * @param roleFunction
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(RoleFunction roleFunction)  throws Exception {
		CommonUtils.checkEntity(roleFunction);
		baseDao.update(roleFunction);
	}


	/**
	 * 删除
	 * @param roleFunction
	 */
	public void delete(RoleFunction roleFunction) {
		roleFunction.setIsDel(true);
		roleFunction.setDelTime(new Date());
		baseDao.update(roleFunction);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public RoleFunction get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (RoleFunction)baseDao.get(RoleFunction.class, id);
	}


	/**
	 * 按条件查找
	 * @param roleFunctionCondition
	 * @return
	 */
	public List<RoleFunction> list(RoleFunctionCondition roleFunctionCondition){
		return  roleFunctionCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param roleFunctionCondition
	 * @param sorter
	 * @return
	 */
	public List<RoleFunction> list(RoleFunctionCondition roleFunctionCondition, Sorter sorter){
		return  roleFunctionCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param roleFunctionCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<RoleFunction> list(RoleFunctionCondition roleFunctionCondition, Range range, Sorter sorter){
		PaginationSupport<RoleFunction> roleFunctionPaginationSupport = (PaginationSupport<RoleFunction>)roleFunctionCondition.pageList(sessionFactory, sorter, range);
		return roleFunctionPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param roleFunctionCondition
	 * @return
	 */
	public Long countByCondition(RoleFunctionCondition roleFunctionCondition){
		return   roleFunctionCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param roleFunctionCondition
	 * @return
	 */
	public List<Object[]> listCustom(RoleFunctionCondition roleFunctionCondition){
		return  roleFunctionCondition.list(sessionFactory);
	}


	public List<String> listSingle(RoleFunctionCondition roleFunctionCondition){
		return  roleFunctionCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param roleFunctionCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(RoleFunctionCondition roleFunctionCondition, int num){
		return  roleFunctionCondition.list(sessionFactory, num);
	}

	/**
	 * 创建菜单权限记录
	 * @param roleId
	 * @param permissionId
	 */
	private void init(String roleId, String permissionId){
		RoleFunction roleFunction = this.getByRoleIdAndPermissionsId(roleId, permissionId);
		if(roleFunction == null){
			roleFunction = new RoleFunction();
			roleFunction.setRoleId(roleId);
			try{
				this.create(roleFunction);
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
	public RoleFunction getByRoleIdAndPermissionsId(String roleId, String permissionId){
		RoleFunctionCondition roleFunctionCondition = new RoleFunctionCondition();
		roleFunctionCondition.setRoleId(roleId);
		roleFunctionCondition.setbDel(null);
		List<RoleFunction> roleFunctionList = roleFunctionCondition.list(sessionFactory);
		if(roleFunctionList.isEmpty()){
			return null;
		}else{
			return roleFunctionList.get(0);
		}
	}

	/**
	 * 获取角色的功能列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<String> getRoleFunctionByRoleId(String roleId) throws Exception{
		List<String> functionList = new ArrayList<>();
		if( StringUtils.isBlank(roleId)){
			return functionList;
		}
		RoleFunctionCondition roleFunctionCondition = new RoleFunctionCondition();
		roleFunctionCondition.setRoleId(roleId);
		roleFunctionCondition.setbDel(false);
		List<RoleFunction> roleFunctionList = this.list(roleFunctionCondition);
		for(RoleFunction roleFunction : roleFunctionList){
			String functionId = roleFunction.getFunctionId();
			functionList.add(functionId);
		}
		return functionList;
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
		RoleFunctionCondition roleFunctionCondition = new RoleFunctionCondition();
		roleFunctionCondition.setRoleId(roleId);
		List<String> permissionIdList = this.listSingle(roleFunctionCondition);

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
