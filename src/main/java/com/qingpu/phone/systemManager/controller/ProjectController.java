package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.ProjectCondition;
import com.qingpu.phone.systemManager.entity.Project;
import com.qingpu.phone.systemManager.service.ProjectService;
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
import java.util.*;

@Controller
@RequestMapping("/service/platform/login/systemManager/Project")
public class ProjectController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(ProjectController.class);

	@Resource
	ProjectService projectService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param project
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(Project project, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = projectService.createByParams(project);
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
	 * @param paramsProject
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(Project paramsProject, HttpServletResponse response) throws IOException {
		try{
			projectService.updateByParams(paramsProject);
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
			projectService.delById(id, request);
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
			ProjectCondition projectCondition = new ProjectCondition();
			PaginationSupport<Project> testTablePaginationSupport = projectService.list(projectCondition, range, new Sorter().desc("create_time"));
			List<Project> projectList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(Project project : projectList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(project);
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", testTablePaginationSupport.getTotalCount());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			e.printStackTrace();
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}

	/**
	 * 获取列表
	 * @param tableParams
	 * @param response
	 * @return
	 */
	@RequestMapping("/listClientNum")
	@ResponseBody
	public Object listClientNum(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try {
			ProjectCondition projectCondition = new ProjectCondition();
			projectCondition.setbDel(false);
			List<Project> projectList = projectService.list(projectCondition);
			List<Project> projectList2 = projectService.projectClientNum();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(Project project1 : projectList){
				if(project1.getIsDel()){
					continue;
				}
				for(Project project2 : projectList2){
					if(project1.getId().equals(project2.getId())){
						project2.setName(project1.getName());
						project1 = project2;
						break;
					}
				}
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(project1);
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}
}
