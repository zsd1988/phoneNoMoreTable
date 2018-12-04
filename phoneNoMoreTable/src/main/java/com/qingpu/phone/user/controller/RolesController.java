package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.condition.*;
import com.qingpu.phone.user.entity.*;
import com.qingpu.phone.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Permission;
import java.util.*;

@Controller
@RequestMapping("/service/platform/login/user/roles")
public class RolesController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(RolesController.class);

	@Resource
	RolesService rolesService;

	@Resource
	RoleFunctionService roleFunctionService;

	@Resource
	RolePermissionService rolePermissionService;

	@Resource
	FunctionPermissionsService functionPermissionsService;

	@Resource
	PermissionsService permissionsService;


	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param roles
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(Roles roles, HttpServletResponse response) throws IOException {
		try{
			Map<String, String> map = new HashMap<>();
			if(StringUtils.isNotBlank(roles.getId())){
				//检测名称是否重复
				RolesCondition rolesCondition=new RolesCondition();
				rolesCondition.setbDel(false);
				rolesCondition.setRoleType(roles.getRoleType());
				rolesCondition.setNeId(roles.getId());
				long count=this.rolesService.countByCondition(rolesCondition);
				if(count>0){
					JsonRetInfo.returnFail(response, "操作失败：角色名称已存在");
					return;
				}
				rolesService.update(roles);
				map.put("isExist", "0");
			}else{
				//检测名称是否重复
				RolesCondition rolesCondition=new RolesCondition();
				rolesCondition.setbDel(false);
				rolesCondition.setRoleType(roles.getRoleType());
				long count=this.rolesService.countByCondition(rolesCondition);
				if(count>0){
					JsonRetInfo.returnFail(response, "：操作失败角色名称已存在");
					return;
				}
				rolesService.create(roles);
				map.put("isExist", "1");
			}

			map.put("id", roles.getId());
			map.put("roleType", roles.getRoleType());
			JsonRetInfo.returnSuccess(response, "获取成功", map);
			return;
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}

	/**
	 * 修改
	 * @param id
	 * @param roleType
	 * @param permissionIds
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(String id, String roleType, Permissions.Tag tag, String permissionIds, HttpServletResponse response) throws IOException  {
		try{
			Integer operatorId = null;
			User user = (User)request.getSession().getAttribute("_user");
			if(user != null){
				operatorId = user.getId();
			}
			rolesService.update(id, roleType, permissionIds, operatorId, tag);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, error);
		}
	}

	/**
	 * 删除
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(String id, Boolean isDel, HttpServletResponse response) throws IOException{
		try{
			Roles roles = rolesService.get(id);
			if(roles == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			User user = (User)request.getSession().getAttribute("_user");
			if(user != null){
				roles.setDelUserId(user.getId());
			}
			roles.setIsDel(isDel);
			rolesService.update(roles);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
		}
	}


	/**
	 * 获取下拉选项框
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getSelect")
	public void getSelect(HttpServletResponse response) throws IOException  {
		try{
			List<Roles> rolesList = rolesService.getListNeAdmin();
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
			JsonRetInfo.returnSuccess(response, "获取成功", infoIds);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "获取失败：" + e.getMessage());
		}
	}

	/**
	 * 启用禁用
	 * @param id
	 * @param isDel
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/onOff")
	public void onOff(String id, Boolean isDel, HttpServletResponse response) throws IOException{
		try{
			Roles roles = rolesService.get(id);
			if(roles == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			roles.setIsDel(isDel);
			rolesService.update(roles);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
		}
	}

	/**
	 * 获取角色key value
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getMap")
	public void getMap(HttpServletResponse response) throws IOException{
		try{
			List<Map<String, String>> mapList = rolesService.getMap(false);
			JsonRetInfo.returnSuccess(response, "操作成功", mapList);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}


	/**
	 * 获取角色数组
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getRolesMap")
	public void getRolesMap(HttpServletResponse response) throws IOException{
		try{
			List<Map<String, String>> mapList = rolesService.getRolesMap(false);
			JsonRetInfo.returnSuccess(response, "操作成功", mapList);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}


	/**
	 * 获取角色的权限
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getRolesPermission")
	public void getRolesPermission(String roleId,HttpServletResponse response) throws IOException{
		try{
			/**
			 * 获取所有角色
			 * @param isDel 为空则为获取全部的
			 * @return
			 */
			List<Map<String, Object>> mapFunctionList = new ArrayList<>();
			RoleFunctionCondition roleFunctionCondition = new RoleFunctionCondition();
			roleFunctionCondition.setbDel(false);
			roleFunctionCondition.setRoleId(roleId);
			List<RoleFunction> roleFunctionList = this.roleFunctionService.list(roleFunctionCondition);
			List<String> functionList=new ArrayList<String>();


			FunctionPermissionsCondition functionPermissionsCondition=new FunctionPermissionsCondition();
			functionPermissionsCondition.setbDel(false);
			List<FunctionPermissions> functionPermissionsAllList=this.functionPermissionsService.list(functionPermissionsCondition);
			if(functionPermissionsAllList!=null&&functionPermissionsAllList.size()>0){
				List<FunctionPermissions> functionPermissionsList=null;
				if(roleFunctionList!=null&&roleFunctionList.size()>0) {
					for (RoleFunction roleFunction : roleFunctionList) {
						functionList.add(roleFunction.getFunctionId());
					}
					functionPermissionsCondition.setFunctionPermissionsList(functionList);
					functionPermissionsList=this.functionPermissionsService.list(functionPermissionsCondition);
				}
				for(FunctionPermissions functionPermissions:functionPermissionsAllList){
					Map<String, Object> map = new HashMap<>();
					map.put("name", functionPermissions.getPermissionName());
					map.put("id", functionPermissions.getId());
					map.put("pId", functionPermissions.getFid());
					map.put("open",true);
					if(functionPermissionsList!=null&&functionPermissionsList.size()>0){
						for(FunctionPermissions  functionPermissionsInfo:functionPermissionsList){
							if(functionPermissions.getId().equals(functionPermissionsInfo.getId())){
								map.put("checked", true);
								break;
							}
						}
					}
					mapFunctionList.add(map);
				}
			}


			List<Map<String, Object>> mapPermissionsList = new ArrayList<>();
			RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
			rolePermissionCondition.setbDel(false);
			rolePermissionCondition.setRoleId(roleId);
			List<RolePermission> rolePermissionList = this.rolePermissionService.list(rolePermissionCondition);
			List<String> permissionList=new ArrayList<String>();

			PermissionsCondition permissionsCondition=new PermissionsCondition();
			permissionsCondition.setDel(false);
			List<Permissions> permissionsAllList=this.permissionsService.list(permissionsCondition);
			if(permissionsAllList!=null&&permissionsAllList.size()>0){
				List<Permissions> permissionsList=null;
				if(rolePermissionList!=null&&rolePermissionList.size()>0) {
					for (RolePermission rolePermission : rolePermissionList) {
						permissionList.add(rolePermission.getPermissionId());
					}
					permissionsCondition.setPermissionList(permissionList);
					permissionsList=this.permissionsService.list(permissionsCondition);
				}
				for(Permissions permissions:permissionsAllList) {
					Map<String, Object> map = new HashMap<>();
					map.put("name", permissions.getPermissionName());
					map.put("id", permissions.getId());
					map.put("pId", permissions.getFid());
					map.put("open", true);
					if (permissionsList != null && permissionsList.size() > 0) {
						for (Permissions permissionsInfo : permissionsList) {
							if (permissions.getId().equals(permissionsInfo.getId())) {
								map.put("checked", true);
								break;
							}
						}
					}
					mapPermissionsList.add(map);
				}
			}

			Map<String, Object> result = new HashMap<>();
			result.put("mapFunctionList", mapFunctionList);
			result.put("mapPermissionsList", mapPermissionsList);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}



	/**
	 * 创建菜单权限
	 * @param roleId
	 * @param permissionsList
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/createPermissions")
	public void createPermissions(String roleId,String permissionsList , HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isNotBlank(roleId)){
				Roles roles=this.rolesService.get(roleId);
				if(roles==null){
					JsonRetInfo.returnFail(response, "操作失败：角色不存在");
					return;
				}

				//删除之前关联的权限
				RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
				rolePermissionCondition.setbDel(false);
				rolePermissionCondition.setRoleId(roleId);
				List<RolePermission> rolePermissionList = this.rolePermissionService.list(rolePermissionCondition);
				if(rolePermissionList!=null&&rolePermissionList.size()>0){
					for(RolePermission rolePermission:rolePermissionList){
						this.rolePermissionService.delete(rolePermission);
					}
				}

				//添加新的权限
				if(StringUtils.isNotBlank(permissionsList)){
					String [] permissions=permissionsList.split(",");
					if(permissions!=null&&permissions.length>0){
						for(String permissionsInfo:permissions){
							RolePermission rolePermission=new RolePermission();
							rolePermission.setRoleId(roleId);
							rolePermission.setPermissionId(permissionsInfo);
							this.rolePermissionService.create(rolePermission);
						}
					}
				}
				JsonRetInfo.returnSuccess(response, "保存成功", null);
				return;
			}
			JsonRetInfo.returnFail(response, "操作失败：角色Id不能为空");
			return;
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}


	/**
	 * 创建功能权限
	 * @param roleId
	 * @param functionList
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/createFunctions")
	public void createFunctions(String roleId,String functionList , HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isNotBlank(roleId)){
				Roles roles=this.rolesService.get(roleId);
				if(roles==null){
					JsonRetInfo.returnFail(response, "操作失败：角色不存在");
					return;
				}

				//删除之前关联的权限
				RoleFunctionCondition roleFunctionCondition = new RoleFunctionCondition();
				roleFunctionCondition.setbDel(false);
				roleFunctionCondition.setRoleId(roleId);
				List<RoleFunction> roleFunctionList = this.roleFunctionService.list(roleFunctionCondition);
				if(roleFunctionList!=null&&roleFunctionList.size()>0){
					for(RoleFunction roleFunction:roleFunctionList){
						this.roleFunctionService.delete(roleFunction);
					}
				}

				//添加新的权限
				if(StringUtils.isNotBlank(functionList)){
					String [] functions=functionList.split(",");
					if(functions!=null&&functions.length>0){
						for(String functionInfo:functions){
							RoleFunction roleFunction=new RoleFunction();
							roleFunction.setRoleId(roleId);
							roleFunction.setFunctionId(functionInfo);
							this.roleFunctionService.create(roleFunction);
						}
					}
				}
				JsonRetInfo.returnSuccess(response, "保存成功", null);
				return;
			}
			JsonRetInfo.returnFail(response, "操作失败：角色Id不能为空");
			return;
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}


	/**
	 * 获取列表
	 * @param tableParams
	 * @param response
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestBody TableParams tableParams, HttpServletResponse response) {
		PaginationParams paginationParams = tableParams.getPaginationParams();
		Range range = paginationParams.getRange();
		RolesCondition rolesCondition = new RolesCondition();
		if(tableParams.getPermissionsTag() != Permissions.Tag.Admin){
			rolesCondition.setNeId(QingpuConstants.Roles_Super_id);
		}
		PaginationSupport<Roles> rolesPaginationSupport = rolesService.list(rolesCondition, range, new Sorter().desc("create_time"));
		List<Roles> rolesList = rolesPaginationSupport.getItems();
		List<Map<String, Object>> mapList = new ArrayList<>();
		for(Roles roles : rolesList){
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(roles);
			mapList.add(map);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", rolesPaginationSupport.getTotalCount());
		result.put("result", mapList);
		return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
	}


}
