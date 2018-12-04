package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.PrologueCondition;
import com.qingpu.phone.systemManager.entity.Prologue;
import com.qingpu.phone.systemManager.service.PrologueService;
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
@RequestMapping("/service/platform/login/systemManager/prologue")
public class PrologueController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(PrologueController.class);

	@Resource
	PrologueService prologueService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param prologue
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(Prologue prologue, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = prologueService.createByParams(prologue);
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
	 * @param paramsPrologue
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(Prologue paramsPrologue, HttpServletResponse response) throws IOException {
		try{
			prologueService.updateByParams(paramsPrologue);
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
	public void delete(String id, HttpServletResponse response) throws IOException{
		try{
			prologueService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 创建
	 * @param projectId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/listByProjectId")
	public void listByProjectId(String projectId, HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isBlank(projectId)){
				throw new Exception("请传入项目id");
			}
			PrologueCondition prologueCondition = new PrologueCondition();
			prologueCondition.setProjectId(projectId);
			List<Prologue> floorList = prologueService.list(prologueCondition, new Sorter().asc("name"));
			JsonRetInfo.returnSuccess(response, "获取成功", floorList);
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response,  error);
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
			PrologueCondition prologueCondition = new PrologueCondition();
			PaginationSupport<Prologue> testTablePaginationSupport = prologueService.list(prologueCondition, range, new Sorter().desc("create_time"));
			List<Prologue> prologueList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(Prologue prologue : prologueList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(prologue);
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
