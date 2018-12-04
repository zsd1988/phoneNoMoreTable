package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.PermissionsCondition;
import com.qingpu.phone.user.entity.Permissions;
import com.qingpu.phone.user.service.PermissionsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/service/platform/login/user/permission")
public class PermissionController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(PermissionController.class);

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	PermissionsService permissionService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param permissionsId
	 * @param permissionName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(String permissionsId,String permissionName, HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isNotBlank(permissionsId)&&StringUtils.isNotBlank(permissionName)){
				Permissions permissions=this.permissionService.get(permissionsId);
				if(permissions!=null){
					//查询名称是否存在
					PermissionsCondition permissionsCondition = new PermissionsCondition();
					permissionsCondition.setFid(permissions.getId());
					permissionsCondition.setPermissionName(permissionName);
					permissionsCondition.setDel(false);
					List<Permissions>  permissionsList =permissionService.list(permissionsCondition);
					if(permissionsList==null||permissionsList.size()==0){
						Permissions permission=new Permissions();
						permission.setId(UUIDGenerator.getUUID());
						permission.setClassName("folder");
						permission.setIsDel(false);
						permission.setCanDelete(true);
						permission.setPermissionUrl("func/mallTV/source/materialManagement="+permission.getId());
						permission.setCreateTime(new Date());
						permission.setPermissionName(permissionName);
						permission.setFid(permissions.getId());
						permission.setShowIndex(0);
						permission.setFolderType(permissions.getFolderType());
						this.permissionService.create(permission);
						permissionsCondition = new PermissionsCondition();
						permissionsCondition.setFid(permissions.getId());
						permissionsCondition.setDel(false);
						Long count =permissionService.countByCondition(permissionsCondition);
						if(count==null){
							count=0l;
						}
						permission.setFidExistCount(count.intValue());
						//加入role中
						JsonRetInfo.returnDataSuccess(response, "操作成功",permission);
						return;
					}
					JsonRetInfo.returnFail(response, "操作失败：文件名已存在，不能重复创建！");
					return;
				}
			}
			JsonRetInfo.returnFail(response, "操作失败：参数不正确！");
			return;
		}catch (Exception e){
			e.printStackTrace();
			JsonRetInfo.returnFail(response, "操作失败：：" + e.getMessage());
		}
	}

	/**
	 * 修改
	 * @param permissionsId
	 * @param permissionName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(String permissionsId,String permissionName,  HttpServletResponse response) throws IOException  {
		try{
			if(StringUtils.isNotBlank(permissionsId)&&StringUtils.isNotBlank(permissionName)){
				Permissions permissions=this.permissionService.get(permissionsId);
				if(permissions!=null){
					//查询名称是否存在
					PermissionsCondition permissionsCondition = new PermissionsCondition();
					permissionsCondition.setFid(permissions.getFid());
					permissionsCondition.setNoId(permissionsId);
					permissionsCondition.setPermissionName(permissionName);
					permissionsCondition.setDel(false);
					List<Permissions>  permissionsList =permissionService.list(permissionsCondition);
					if(permissionsList==null||permissionsList.size()==0){
						permissions.setPermissionName(permissionName);
						this.permissionService.update(permissions);
						JsonRetInfo.returnDataSuccess(response, "操作成功",permissions);
						return;
					}
					JsonRetInfo.returnFail(response, "操作失败：文件名重复！");
					return;
				}
			}
			JsonRetInfo.returnFail(response, "操作失败：参数不正确！");
			return;
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}

	/**
	 * 删除
	 * @param permissionsId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(String permissionsId, HttpServletResponse response) throws IOException{
		try{
			Permissions permissions = permissionService.get(permissionsId);
			if(permissions == null){
				JsonRetInfo.returnFail(response, "删除失败：未找到数据");
				return;
			}
			permissionService.delete(permissions);
			PermissionsCondition permissionsCondition = new PermissionsCondition();
			permissionsCondition.setFid(permissions.getId());
			permissionsCondition.setDel(false);
			Long count =permissionService.countByCondition(permissionsCondition);
			if(count==null){
				count=0l;
			}
			permissions.setFidExistCount(count.intValue());
			JsonRetInfo.returnDataSuccess(response, "删除成功",permissions);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "删除失败：" + e.getMessage());
		}
	}



	/**
	 * 根据资源类型获取资源文件夹列表
	 * @param tableParams
	 * @param response
	 * @return
	 */
	@RequestMapping("/listForFolder")
	@ResponseBody
	public Object listForFolder(@RequestBody TableParams tableParams, HttpServletResponse response) {
		PaginationParams paginationParams = tableParams.getPaginationParams();
		Range range = paginationParams.getRange();
		Sorter sorter = paginationParams.getSorter();
		PermissionsCondition permissionsCondition = new PermissionsCondition();
		PaginationSupport<Permissions> rolePermissionPaginationSupport = permissionService.list(permissionsCondition, range, sorter);
		return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", CommonUtils.getPageMap(rolePermissionPaginationSupport));
	}
}
