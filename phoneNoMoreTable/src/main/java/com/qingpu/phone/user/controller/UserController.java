package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.user.condition.RolesCondition;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.condition.UserGroupCondition;
import com.qingpu.phone.user.entity.*;
import com.qingpu.phone.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/service/platform/login/user/user")
public class UserController extends HandlerInterceptorAdapter {
	@Resource
	UserService userService;

	@Resource
	UserRoleService userRoleService;

	@Resource
	RolesService rolesService;

	@Resource
	PermissionsService permissionsService;

	@Resource
	UserGroupService userGroupService;

	@Resource
	RoleFunctionService roleFunctionService;

	@Resource
	GroupCallService groupCallService;

	@Resource
    JiangHuRenService jiangHuRenService;

	@Autowired  
	HttpServletRequest request; //获取到当前请求的request

	/**
	 * 创建
	 * @param user
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(User user, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = userService.createByParamsUser(user);
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response,  error);
		}
	}

	/**
	 * 修改
	 * @param roleIds
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(String roleIds, User paramsUser, HttpServletResponse response) throws IOException  {
		try{
			userService.updateByParamsUser(paramsUser, roleIds, request);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}


	/**
	 * 获取客服和约访用户
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getCSAndInterview")
	public void getCSAndInterview(HttpServletResponse response) throws IOException  {
		try{
			Map<String, String> map = new HashMap<>();
			map.put("CS", userService.getIdsByRoleId(QingpuConstants.Roles_Employee_id));
			map.put("interview", userService.getIdsByRoleId(QingpuConstants.Roles_Interview_id));
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}

	/**
	 * 修改密码
	 * @param id
	 * @param passwordBefore
	 * @param password
	 * @throws IOException
	 */
	@RequestMapping("/changPw")
	public void changPw(Integer id,String passwordBefore, String password, HttpServletResponse response) throws IOException  {
		try{
			userService.changePassword(id, passwordBefore, password);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}

	/**
	 * 删除
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(Integer id, Boolean isDel, HttpServletResponse response) throws IOException{
		try{
			User user = userService.get(id);
			if(user == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			User currentUser = (User)request.getSession().getAttribute("_user");
			if(currentUser != null){
				user.setDelUserId(currentUser.getId());
			}
			user.setIsDel(isDel);
			userService.update(user);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
		}
	}

	/**
	 * 获取用户信息
	 * @param id
	 * @param response
	 * @throws IOException
	 */
/*	@RequestMapping("/getUserInfo")
	public void getUserInfo(Integer id, HttpServletResponse response) throws IOException{
		try{
			User user = userService.get(id);
			if(user == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return;
			}
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> userInfoMap = new HashMap<>();
			*//*
			用户角色
			 *//*
			map.put("rolesArr", rolesService.getMap(false));
			userInfoMap.put("roleIds", userRoleService.getRoleIdsByUserId(id));


			map.put("userInfo", userInfoMap);
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}*/

	@RequestMapping("/getUserInfo")
	public void getUserInfo(Integer id, HttpServletResponse response) throws IOException{
		try{
			User user = userService.get(id);
			if(user == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return;
			}
			Map<String, Object> result = new HashMap<>();
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(user);
			result.put("userInfo", map);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}


	/**
	 * 获取用户分机号列表
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getCSExtNumList")
	public void getCSExtNumList(HttpServletResponse response) throws IOException{
		try{
			//改成获取用户工号
			List<Map.Entry<String, Boolean>> extNumMap = userService.getWorkNum();
			JsonRetInfo.returnSuccess(response, "操作成功", extNumMap);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}
	/**
	 * 加载下拉框数据
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/loadSelectPickerData")
	public void loadSelectPickerData( HttpServletResponse response) throws IOException{
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("roles", rolesService.getSelect());
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}

	/**
	 * 获取员工列表
	 * @param tableParams
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getEmployee")
	@ResponseBody
	public Object getEmployee(@RequestBody TableParams tableParams, HttpServletResponse response) throws IOException{
		try {
			PaginationParams paginationParams = tableParams.getPaginationParams();
			Range range = paginationParams.getRange();
			PaginationSupport<User> userPaginationSupport = userService.getByRoleId(tableParams, QingpuConstants.Roles_Employee_id, range);
			List<User> userList = userPaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(User user : userList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(user);
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", userPaginationSupport.getTotalCount());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
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
		UserCondition userCondition = new UserCondition();
		if(tableParams.getPermissionsTag() != Permissions.Tag.Admin){
			userCondition.setNeId(QingpuConstants.User_Admin_Id);
		}
		userCondition.setLikeLoginName(tableParams.getPhone());
		userCondition.setNickName(tableParams.getName());
		Map<String, String> roleNameMap = new HashMap<>();
		Map<String, String> positionNameMap = new HashMap<>();
		Map<String, String> departmentNameMap = new HashMap<>();
		PaginationSupport<User> userPaginationSupport = userService.list(userCondition, range, new Sorter().desc("create_time"));
		List<User> userList = userPaginationSupport.getItems();
		List<Map<String, Object>> mapList = new ArrayList<>();
		for(User user : userList){
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(user);
			List<UserRole> userRoleList = userRoleService.getByUserId(user.getId());
			String roleName = "";
			for(UserRole userRole : userRoleList){
				String roleId = userRole.getRoleId();
				if(roleNameMap.containsKey(roleId)){
					roleName += roleNameMap.get(roleId) + ",";
				}else{
					String tempRoleName = rolesService.getNameByRoleId(roleId);
					roleName += tempRoleName + ",";
					roleNameMap.put(roleId, tempRoleName);
				}
			}
			map.put("roleName", StringUtil.subLastChar(roleName));
			mapList.add(map);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", userPaginationSupport.getTotalCount());
		result.put("result", mapList);
		return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
	}
	
	//客户端请求返回到登录界面
	@RequestMapping("/login")
	public String adminLogin(HttpServletResponse response) throws Exception{		
		return "/platform/login";
	}
	
	//客户端显示注册页面
	@RequestMapping("/register")
	public String adminRegister(HttpServletResponse response) throws Exception{
		return "/platform/register";
	}
	
	//邮箱用户注册
	@RequestMapping(value="/imeilRegister")
	@ResponseBody
	public String pcRegister(@RequestBody String body, HttpServletResponse response){
		JSONObject jsonObj = new JSONObject(body);
		String imeil = jsonObj.getString("imeil");
		String nickname = jsonObj.getString("nickname");
		String password = jsonObj.getString("password");
		User user = new User();
		user.setPassword(EncryptUtils.encryptByMD5(password));//将密码转换为32位MD5存储
		/*Farm farm = new Farm();
		farm.setUser(user);//农场绑定用户*/
		userService.saveUser(user);//产生异常将由异常处理类进行响应客户端
		
		JSONObject ret = new JSONObject();
		ret.put("code", 0);
		ret.put("message", "操作数据库成功");
	
		return ret.toString();
	}

	
	//客户端登录之后显示的主页面
	@RequestMapping("/welcome")
	public Object adminWelcome(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = (User)request.getSession().getAttribute("_user");
		Map<String, Object> dataMap = new HashMap<>();
		if(user != null){
			try {
				List<Permissions> list0 = permissionsService.getListByUserId(user);
				dataMap.put("permissionList", list0);
				String roleId = user.getRoleId();
				//返回操作权限
				dataMap.put("functionIds", roleFunctionService.getRoleFunctionByRoleId(roleId));
				if(QingpuConstants.Roles_Manager_id.equals(roleId)){
					roleId="manager";
				}else if(QingpuConstants.Roles_Employee_id.equals(roleId)){
					roleId="employee";
				}
				dataMap.put("roleType", roleId);
				return new ModelAndView("/platform/index", dataMap);
			}catch (RemoteCallException ex){
				dataMap.put("errorHint", ex.getMessage());
				return new ModelAndView("/platform/page-400", dataMap);
			}
		}else{
			return new ModelAndView("/platform/login");
		}
	}

	@RequestMapping("/testException")
	public String testException(HttpServletRequest request, HttpServletResponse response){
		System.out.println("执行测试异常代码");
		userService.getUserByOpenid("hhhhhhhhhh");
		throw new RemoteCallException(RemoteCallException.RCE_DATABASE, "数据库操作出错");
	}


	/**
	 * 注销登录
	 * @param body
	 * @return
	 */
	@RequestMapping("/loginOut")
	@ResponseBody
	public Object loginOut(@RequestBody String body){
        JSONObject jsonObj = new JSONObject(body);
        Boolean isForce = jsonObj.getBoolean("isForce");
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("_user");
		User userReal = userService.get(user.getId());
		if(userReal != null && !isForce){
			userReal.setIsLogin(false);
			userService.updateUser(userReal);
		}
		session.removeAttribute("_user");
		session.invalidate();
		return  new JsonRetInfo(JsonRetInfo.SUCCESS, "注销成功");
	}


	/**
	 * 获取角色信息
	 * @param response
	 * @return
	 */
	@RequestMapping("/roleListOption")
	public void roleListOption( HttpServletResponse response) throws Exception{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			RolesCondition rolesCondition = new RolesCondition();
			rolesCondition.setbDel(false);
			rolesCondition.setNeId(QingpuConstants.Roles_Super_id);
			List<Roles> rolesList = this.rolesService.list(rolesCondition);
			for (Roles roles : rolesList) {
				map.put(roles.getId() + "", roles.getRoleType());
			}
			Map<String, Object> result = new HashMap<>();
			result.put("roleListOption", map);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		} catch (Exception e) {
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}



	/**
	 * 获取所有用户组
	 * @param response
	 * @return
	 */
	@RequestMapping("/userGroupList")
	public void userGroupList( HttpServletResponse response) throws Exception{
		try {
			UserGroupCondition userGroupCondition = new UserGroupCondition();
			userGroupCondition.setbDel(false);
			userGroupCondition.setNoFid(QingpuConstants.User_Group_System_Id);
			userGroupCondition.setNoId(QingpuConstants.User_Group_System_Id);
			List<UserGroup> userGroupList = this.userGroupService.list(userGroupCondition);
			Map<String, String> map = new HashMap<>();
			for (UserGroup userGroup : userGroupList) {
				map.put(userGroup.getId() + "", userGroup.getGroupName());
			}
			List<Map.Entry<String, String>> list = CommonUtils.sortMapList(map);
			JsonRetInfo.returnSuccess(response, "操作成功", list);
		} catch (Exception e) {
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}

	/**
	 * 获取所领导的用户
	 * @param userId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getLeaderUsers")
	public void getLeaderUsers(Integer userId, HttpServletResponse response) throws IOException {
		try{
			User user = userService.get(userId);
			if(user == null){
				throw new Exception("未找到用户");
			}
			if( ! user.getIsTeamLeader()){
				throw new Exception("您不是组长");
			}
			UserCondition userCondition = new UserCondition();
			userCondition.setGroupId(user.getGroupId());
			userCondition.setDel(false);
			List<User> userList = userService.list(userCondition);
			List<Map<String, Object>> mapList = new ArrayList<>();
			Integer index = 1;
			for(User item : userList){
                JiangHuRen jiangHuRen = jiangHuRenService.getByNameAndRoleType(item.getName(), PublicEnum.RoleType.Interview);
                if(jiangHuRen != null){
                    Map<String, Object> map = AutoEvaluationUtil.domainToMap(item);
                    map.put("index", index);
                    map.put("weekIntention", jiangHuRen.getWeekIntention());
                    map.put("weekFinish", jiangHuRen.getWeekFinish());
                    mapList.add(map);
                    index++;
                }
			}
			JsonRetInfo.returnSuccess(response, "操作成功", mapList);
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response,  error);
		}
	}
}
