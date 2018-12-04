package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.GroupCallCondition;
import com.qingpu.phone.systemManager.entity.GroupCall;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.service.ProjectService;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.entity.User;
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
import java.util.*;

@Controller
@RequestMapping("/service/platform/login/systemManager/groupCall")
public class GroupCallController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(GroupCallController.class);

	@Resource
	GroupCallService groupCallService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request

	@Resource
	ProjectService projectService;

	@Resource
	UserService userService;


	/**
	 * 创建
	 * @param groupCall
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(GroupCall groupCall, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = groupCallService.createByParams(groupCall);
			JsonRetInfo.returnSuccess(response, "获取成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "name")){
				error = "名称已存在，不能重复创建";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}


	/**
	 * 修改
	 * @param paramsGroupCall
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(GroupCall paramsGroupCall, HttpServletResponse response) throws IOException {
		try{
			groupCallService.updateByParams(paramsGroupCall);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "name")){
				error = "名称已存在，不能重复创建";
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
	public void setIsEnable(String id, Boolean isEnable,  HttpServletResponse response) throws IOException{
		try{
			groupCallService.setIsEnable(id, isEnable);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 刷新并发数
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/refreshRateNum")
	public void refreshRateNum(HttpServletResponse response) throws IOException{
		try{
			Map<String, Map<String, Integer>> map = new HashMap<>();
			for(String key : CallPhoneListener._groupCallConNumMap.keySet()){
				Map<String, Integer> countMap = new HashMap<>();
				countMap.put("conCount", CallPhoneListener._groupCallConNumMap.get(key));
				List<String> phoneList = CallPhoneListener._groupCallCallingPhoneMap.get(key);	// 通话中
				Integer phoneCount = 0;
				if(phoneList != null){
					phoneCount = phoneList.size();
				}
				Integer callCount = 0;
				List<String> portIdList = CallPhoneListener._groupCallPortIdMap.get(key);
				if(portIdList != null){
					callCount = portIdList.size() - phoneCount;
				}
				GroupCall groupCall = groupCallService.get(key);
				if(groupCall != null){
					Integer loginUserCount = 0;
					List<String> extNumList = StringUtil.splitByComma(groupCall.getExtNumList());
					for(String extNum : extNumList){
						if(CallPhoneListener._onlineUserWorkNumList.contains(extNum)){
							loginUserCount++;
						}
					}
					countMap.put("Waiting", loginUserCount);
				}
				countMap.put("Calling", callCount);
				countMap.put("Success", phoneCount);
				map.put(key, countMap);
			}
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 显示详情
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getDetail")
	public void getDetail(String id, HttpServletResponse response) throws IOException{
		try{
			Map<String, Integer> groupNumMap = groupCallService.getTableMap();
			JsonRetInfo.returnSuccess(response, "操作成功", groupNumMap);
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
			GroupCallCondition groupCallCondition = new GroupCallCondition();
			PaginationSupport<GroupCall> groupCallPaginationSupport = groupCallService.list(groupCallCondition, range, new Sorter().desc("create_time"));
			List<GroupCall> groupCallList = groupCallPaginationSupport.getItems();

			List<Map<String, Object>> mapList = new ArrayList<>();
			Map<String, String> projectNameMap = new HashMap<>();
			Map<String, Integer> groupNumMap = groupCallService.getTableMap();
			for(GroupCall groupCall : groupCallList){
				String groupCallId = groupCall.getId();
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(groupCall);
				String projectId = groupCall.getProjectId();
				String projectName = "";
				if( ! projectNameMap.containsKey(projectId)){
					projectName = projectService.getName(projectId);
				}else{
					projectName = projectNameMap.get(projectId);
				}
				map.put("projectName", projectName);
				if( groupCall.getIsEnable() && CallPhoneListener._groupCallConNumMap.containsKey(groupCallId)){
					map.put("rateNum", CallPhoneListener._groupCallConNumMap.get(groupCallId));
				}
				List<String> phoneList = CallPhoneListener._groupCallCallingPhoneMap.get(groupCallId);	// 通话中
				Integer phoneCount = 0;
				if(phoneList != null){
					phoneCount = phoneList.size();
				}
				Integer callCount = 0;
				List<String> portIdList = CallPhoneListener._groupCallPortIdMap.get(groupCallId);
				if(portIdList != null){
					callCount = portIdList.size() - phoneCount;
				}
				map.put("Calling", callCount);
				map.put("Success", phoneCount);
				Integer total  =  groupNumMap.get(groupCallId);
				if(total == null){
					total = 0;
				}
				map.put("total", total );
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", groupCallPaginationSupport.getTotalCount());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}


	/**
	 * 重启服务器
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/restartServer")
	public void restartServer(HttpServletResponse response) throws IOException {
		try{
			String os = System.getProperty("os.name");
			if( os.toLowerCase().startsWith("win")){
				throw new Exception("windows系统不支持此功能");
			}
			GroupCallCondition groupCallCondition = new GroupCallCondition();
			groupCallCondition.setEnable(true);
			List<GroupCall> groupCallList = groupCallService.list(groupCallCondition);
			if( ! groupCallList.isEmpty()){
				throw new Exception("群呼任务启动中，不能重启");
			}
			User user = (User)request.getSession().getAttribute("_user");
			List<String> commandList = new ArrayList<>();
			logger.info("----------------------------------------手动操作重启： " + user.getId() + " " + user.getName());
			String command="/mnt/apache/bin/shutdown.sh";
			commandList.add(command);

			command="pkill -9 java";
			commandList.add(command);

			command="/mnt/apache/bin/startup.sh";
			commandList.add(command);
			LinuxUtil.executeNewFlow(commandList);
			JsonRetInfo.returnSuccess(response, "获取成功");
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "name")){
				error = "名称已存在，不能重复创建";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}
}
