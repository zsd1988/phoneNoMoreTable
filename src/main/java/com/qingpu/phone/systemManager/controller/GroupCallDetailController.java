package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.GroupCallDetailCondition;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.service.GroupCallDetailService;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.systemManager.service.ProjectService;
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
@RequestMapping("/service/platform/login/systemManager/GroupCallDetail")
public class GroupCallDetailController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(GroupCallDetailController.class);

	@Resource
	GroupCallDetailService groupCallDetailService;

	@Resource
	ProjectService projectService;

	@Resource
	ClientService clientService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param groupCallDetail
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(GroupCallDetail groupCallDetail, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = groupCallDetailService.createByParams(groupCallDetail);
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
	 * 导入数据
	 * @param paramsGroupCallDetail
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importGroupCallDetail")
	public void importGroupCallDetail(GroupCallDetail paramsGroupCallDetail, HttpServletResponse response) throws IOException {
		try{
			Map<String, Integer> map = groupCallDetailService.importGroupCallDetail(paramsGroupCallDetail);
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}


	/**
	 * 转人工失败转成未呼叫
	 * @param groupCallId
	 * @throws Exception
	 */
	@RequestMapping("/allToWaiting")
	public void allToWaiting(String groupCallId, HttpServletResponse response) throws IOException {
		try{
			groupCallDetailService.allToWaiting(groupCallId);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 获取转人工失败列表
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
			GroupCallDetailCondition groupCallDetailCondition = new GroupCallDetailCondition();
			String statusStr = tableParams.getStatus();
			PublicEnum.GroupCallDetailStatus groupCallDetailStatus = null;
			if(StringUtils.isNotBlank(statusStr)){
				groupCallDetailStatus = PublicEnum.GroupCallDetailStatus.valueOf(statusStr);
			}
			Boolean isGetClient = false;
			if(tableParams.getInclude() != null){
				// 用于语音通话显示列表
				isGetClient = true;
				groupCallDetailCondition.setNotNullRefuseRecordPath(true);
				groupCallDetailCondition.setVoiceType(PublicEnum.VoiceType.Middle);
			}
			String startTime = tableParams.getStartTimeStr();
			if(StringUtils.isNotBlank(startTime)){
				groupCallDetailCondition.setDayStartStartTime(DateUtil.strDateToDate(startTime, 0));
			}
			String endTime = tableParams.getEndTimeStr();
			if(StringUtils.isNotBlank(endTime)){
				groupCallDetailCondition.setDayEndStartTime(DateUtil.strDateToDate(endTime, 0));
			}
			String projectId1 = tableParams.getProjectId();
			if(StringUtils.isNotBlank(projectId1)){
				groupCallDetailCondition.setProjectId(projectId1);
			}
			Boolean isHaveFirst = tableParams.getGetEmployee();
			if(isHaveFirst != null ){
				groupCallDetailCondition.setNotNullFirstText(isHaveFirst);
			}
			groupCallDetailCondition.setStatus(groupCallDetailStatus);
			groupCallDetailCondition.setGroupCallId(tableParams.getId());
			PaginationSupport<GroupCallDetail> groupCallDetailPaginationSupport = groupCallDetailService.list(groupCallDetailCondition, range, new Sorter().desc("start_time"));
			List<GroupCallDetail> groupCallDetailList = groupCallDetailPaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			Map<String, String> projectNameMap = new HashMap<>();
			for(GroupCallDetail groupCallDetail : groupCallDetailList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(groupCallDetail);
				map.put("startTimeStr", DateUtil.dateToString(groupCallDetail.getStartTime(), 2));
				map.put("statusStr", groupCallDetail.getStatus().getName());
				String projectId = groupCallDetail.getProjectId();
				String projectName = "";
				if( ! projectNameMap.containsKey(projectId)){
					projectName = projectService.getName(projectId);
				}else{
					projectName = projectNameMap.get(projectId);
				}
				if(isGetClient){
					Client client = clientService.get(groupCallDetail.getClientId());
					if(client != null){
						map.put("clientGroupId", client.getGroupId());
					}
				}
				map.put("projectName", projectName);
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", groupCallDetailPaginationSupport.getTotalCount());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}

	/**
	 * 获取相应数量
	 * @return
	 * */
	@RequestMapping("/portDetailRate")
	@ResponseBody
	public Object portDetailRate(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try {
			List<GroupCallDetail> groupCallDetaillist=groupCallDetailService.portDetailRate(tableParams);
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(GroupCallDetail groupCallDetail : groupCallDetaillist){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(groupCallDetail);
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
