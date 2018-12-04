package com.qingpu.phone.systemSetting.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemSetting.condition.ExtAndIPCondition;
import com.qingpu.phone.systemSetting.entity.ExtAndIP;
import com.qingpu.phone.systemSetting.service.ExtAndIPService;
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
@RequestMapping("/service/platform/login/systemSetting/extAndIP")
public class ExtAndIPController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(ExtAndIPController.class);

	@Resource
	ExtAndIPService extAndIPService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param extAndIP
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(ExtAndIP extAndIP, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = extAndIPService.createByParams(extAndIP);
			JsonRetInfo.returnSuccess(response, "获取成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "ip")){
				error = "IP已存在";
			}
			if(CommonUtils.checkIsNotUniqueForEntity(error, "ext_num")){
				error = "分机号已存在";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}


	/**
	 * 修改
	 * @param paramsExtAndIP
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(ExtAndIP paramsExtAndIP, HttpServletResponse response) throws IOException {
		try{
			extAndIPService.updateByParams(paramsExtAndIP);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "ip")){
				error = "IP已存在";
			}
			if(CommonUtils.checkIsNotUniqueForEntity(error, "ext_num")){
				error = "分机号已存在";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}

	/**
	 * 删除
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/setIsEnable")
	public void setIsEnable(String id, Boolean isDel, HttpServletResponse response) throws IOException{
		try{
			extAndIPService.delById(id, isDel, request);
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
			ExtAndIPCondition extAndIPCondition = new ExtAndIPCondition();
			PaginationSupport<ExtAndIP> testTablePaginationSupport = extAndIPService.list(extAndIPCondition, range, new Sorter().asc("ip"));
			List<ExtAndIP> extAndIPList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(ExtAndIP extAndIP : extAndIPList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(extAndIP);
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
