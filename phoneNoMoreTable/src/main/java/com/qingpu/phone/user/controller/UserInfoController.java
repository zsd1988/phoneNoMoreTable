package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.UserInfoCondition;
import com.qingpu.phone.user.entity.UserInfo;
import com.qingpu.phone.user.service.UserInfoService;
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
@RequestMapping("/service/platform/login/user/userInfo")
public class UserInfoController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(UserInfoController.class);

	@Resource
	UserInfoService userInfoService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param userInfo
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(UserInfo userInfo, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = userInfoService.createByParams(userInfo);
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
	 * @param paramsUserInfo
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(UserInfo paramsUserInfo, HttpServletResponse response) throws IOException {
		try{
			userInfoService.updateByParams(paramsUserInfo);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 删除
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(Long id, HttpServletResponse response) throws IOException{
		try{
			userInfoService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
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
			UserInfoCondition userInfoCondition = new UserInfoCondition();
			PaginationSupport<UserInfo> testTablePaginationSupport = userInfoService.list(userInfoCondition, range, new Sorter().desc("create_time"));
			List<UserInfo> userInfoList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(UserInfo userInfo : userInfoList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(userInfo);
				map.put("employeeDayStr", DateUtil.dateToString(userInfo.getEmployeeDay(), 0));
				map.put("birthDayStr", DateUtil.dateToString(userInfo.getBirthDay(), 0));
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
}
