package com.qingpu.phone.systemSetting.controller;

import com.qingpu.phone.systemSetting.entity.Parameter;
import com.qingpu.phone.systemSetting.condition.ParameterCondition;
import com.qingpu.phone.systemSetting.service.ParameterService;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
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
@RequestMapping("/service/platform/login/systemSetting/parameter")
public class ParameterController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(ParameterController.class);

	@Resource
	ParameterService parameterService;
	
	@Autowired
    HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param parameter
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(Parameter parameter, HttpServletResponse response) throws IOException {
		try{
			parameterService.create(parameter);
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
	 * @param parameter
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(Parameter parameter, HttpServletResponse response) throws IOException  {
		try{
			parameterService.updateByParams(parameter);
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
			ParameterCondition parameterCondition = new ParameterCondition();
			PaginationSupport<Parameter> testTablePaginationSupport = parameterService.list(parameterCondition, range, new Sorter().desc("name"));
			List<Parameter> appList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(Parameter app : appList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(app);
				map.put("dataTypeStr", app.getDataType().getName());
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
