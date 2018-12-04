package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.RoleFunctionCondition;
import com.qingpu.phone.user.condition.UserClientGroupCondition;
import com.qingpu.phone.user.entity.RoleFunction;
import com.qingpu.phone.user.entity.Roles;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserClientGroup;
import com.qingpu.phone.user.service.UserClientGroupService;
import com.qingpu.phone.user.service.UserService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/service/platform/noLogin/user/userClientGroup")
public class UserClientGroupController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(UserClientGroupController.class);

	@Resource
	UserClientGroupService userClientGroupService;


	@Resource
	UserService userService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param testTable
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(UserClientGroup testTable, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = userClientGroupService.createByParams(testTable);
			JsonRetInfo.returnSuccess(response, "获取成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "num")){
				error = "编号已存在，不能重复创建";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}


	/**
	 * 修改
	 * @param paramsTestTable
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(UserClientGroup paramsTestTable, HttpServletResponse response) throws IOException {
		try{
			userClientGroupService.updateByParams(paramsTestTable);
			JsonRetInfo.returnSuccess(response, "操作成功");
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
	public Object list(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try {
			PaginationParams paginationParams = tableParams.getPaginationParams();
			Range range = paginationParams.getRange();
			UserClientGroupCondition testTableCondition = new UserClientGroupCondition();
			PaginationSupport<UserClientGroup> testTablePaginationSupport = userClientGroupService.list(testTableCondition, range, new Sorter().desc("create_time"));
			List<UserClientGroup> testTableList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(UserClientGroup testTable : testTableList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(testTable);
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", testTablePaginationSupport.getTotalCount());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}


	/**
	 * 创建用户分组
	 * @param userId
	 * @param userClientGroups
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/saveUserClientGroup")
	public void saveUserClientGroup(String userId,String userClientGroups , HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isNotBlank(userId)){
				User user=this.userService.get(Integer.parseInt(userId));
				if(user==null){
					JsonRetInfo.returnFail(response, "操作失败：用户不存在");
					return;
				}

				//删除之前关联的权限
				UserClientGroupCondition userClientGroupCondition = new UserClientGroupCondition();
				userClientGroupCondition.setbDel(false);
				userClientGroupCondition.setUserId(user.getId());
				List<UserClientGroup> userClientGroupList = this.userClientGroupService.list(userClientGroupCondition);
				if(userClientGroupList!=null&&userClientGroupList.size()>0){
					for(UserClientGroup userClientGroup:userClientGroupList){
						this.userClientGroupService.delete(userClientGroup);
					}
				}

				//添加新的权限
				if(StringUtils.isNotBlank(userClientGroups)){
					String [] clientGroups=userClientGroups.split(";");
					if(clientGroups!=null&&clientGroups.length>0){
						for(String clientGroup:clientGroups){
							UserClientGroup userClientGroup=new UserClientGroup();
							userClientGroup.setUserId(user.getId());
							userClientGroup.setClientGroupId(clientGroup.split(",")[0]);
							userClientGroup.setProjectId(clientGroup.split(",")[1]);
							this.userClientGroupService.create(userClientGroup);
						}
					}
				}
				JsonRetInfo.returnSuccess(response, "保存成功", null);
				return;
			}
			JsonRetInfo.returnFail(response, "操作失败：用户Id不能为空");
			return;
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}
}
