package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.UserRoleCondition;
import com.qingpu.phone.user.entity.Roles;
import com.qingpu.phone.user.entity.UserRole;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.RolesService;
import com.qingpu.phone.user.service.UserRoleService;
import com.qingpu.phone.user.service.UserService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/service/platform/login/user/userRole")
public class UserRoleController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(UserRoleController.class);

	@Resource
	UserRoleService userRoleService;

	@Resource
	UserService userService;

	@Resource
	RolesService rolesService;
	
	@Autowired  
	HttpServletRequest request;


	/**
	 * 创建
	 * @param userRole
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(UserRole userRole, HttpServletResponse response) throws IOException {
		try{
			userRoleService.create(userRole);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}

	/**
	 * 修改
	 * @param userRole
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(UserRole userRole, HttpServletResponse response) throws IOException  {
		try{
			userRoleService.update(userRole);
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
	public void delete(String id, Boolean isDel, HttpServletResponse response) throws IOException{
		try{
			UserRole userRole = userRoleService.get(id);
			if(userRole == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			User user = (User)request.getSession().getAttribute("_user");
			if(user != null){
				userRole.setDelUserId(user.getId());
			}
			userRole.setIsDel(isDel);
			userRoleService.update(userRole);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
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
			UserRole userRole = userRoleService.get(id);
			if(userRole == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			userRole.setIsDel(isDel);
			userRoleService.update(userRole);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
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
		UserRoleCondition userRoleCondition = new UserRoleCondition();
		PaginationSupport<UserRole> userRolePaginationSupport = userRoleService.list(userRoleCondition, range, new Sorter().desc("create_time"));
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", userRolePaginationSupport.getTotalCount());
		List<UserRole> list = userRolePaginationSupport.getItems();
		List<Map<String, Object>> mapList = new ArrayList<>();
		Map<String, String> userMap = new HashMap<>();
		Map<String, String> roleMap = new HashMap<>();
		for(UserRole userRole : list){
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(userRole);
			String userName = "";
			Integer userId = userRole.getUserId();
			if(userMap.containsKey(userId)){
				userName = userMap.get(userId);
			}else{
				User user = userService.get(userId);
				if(user != null){
					userName = user.getName();
				}
			}
			map.put("userName", userName);
			String roleName = "";
			String roleId = userRole.getRoleId();
			if(roleMap.containsKey(roleId)){
				roleName = roleMap.get(roleId);
			}else {
				Roles role = rolesService.get(roleId);
				if(role != null){
					roleName = role.getRoleType();
				}
			}
			map.put("roleName", roleName);
			mapList.add(map);
		}
		result.put("result", mapList);
		return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
	}
}
