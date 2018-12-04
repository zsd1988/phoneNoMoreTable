package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.RolePermissionCondition;
import com.qingpu.phone.user.entity.RolePermission;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.RolePermissionService;
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

@Controller
@RequestMapping("/service/platform/login/user/rolePermission")
public class RolePermissionController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(RolePermissionController.class);

	@Resource
	RolePermissionService rolePermissionService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param rolePermission
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(RolePermission rolePermission, HttpServletResponse response) throws IOException {
		try{
			rolePermissionService.create(rolePermission);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "num")){
				error = "编号已存在，不能重复创建";
			}
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}

	/**
	 * 修改
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(String id,  HttpServletResponse response) throws IOException  {
		try{
			RolePermission rolePermission = rolePermissionService.get(id);
			if(rolePermission == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return;
			}
			rolePermissionService.update(rolePermission);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "num")){
				error = "编号已存在，不能修改为此编号";
			}
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
	public void delete(String id, HttpServletRequest request,  HttpServletResponse response) throws IOException{
		try{
			RolePermission rolePermission = rolePermissionService.get(id);
			if(rolePermission == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			User user = (User)request.getSession().getAttribute("_user");
			if(user != null){
				rolePermission.setDelUserId(user.getId());
			}
			rolePermissionService.delete(rolePermission);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
		}
	}

	/**
	 * 获取角色分配的菜单，包括所有菜单
	 * @param roleId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getByRoleId")
	public void getByRoleId(String roleId, HttpServletResponse response) throws IOException{
		try{
			JsonRetInfo.returnSuccess(response, "操作成功", rolePermissionService.getByRoleId(roleId));
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
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
		Sorter sorter = paginationParams.getSorter();
		RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
		PaginationSupport<RolePermission> rolePermissionPaginationSupport = rolePermissionService.list(rolePermissionCondition, range, sorter);
		return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", CommonUtils.getPageMap(rolePermissionPaginationSupport));
	}
}
