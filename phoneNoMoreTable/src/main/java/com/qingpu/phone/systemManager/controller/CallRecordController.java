package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.entity.ClientGroup;
import com.qingpu.phone.clientManager.service.ClientGroupService;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.systemManager.condition.CallRecordCondition;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.entity.Project;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.phoneSocket.PhoneSocket;
import com.qingpu.phone.systemManager.phoneSocket.TempSocket;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.service.ProjectService;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserGroup;
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
@RequestMapping("/service/platform/login/systemManager/callRecord")
public class CallRecordController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(CallRecordController.class);

	@Resource
	CallRecordService callRecordService;

	@Resource
	ClientService clientService;

	@Resource
	ProjectService projectService;

	@Resource
	WebSocketService webSocketService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request

	@Resource
	ClientGroupService clientGroupService;

	@Resource
	UserService userService;


	/**
	 * 创建
	 * @param callRecord
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(CallRecord callRecord, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = callRecordService.createByParams(callRecord);
			JsonRetInfo.returnSuccess(response, "获取成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			if(StringUtils.isNotBlank(error) && error.equals("Connection refused: connect")){
				error = "sip服务器未开启";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}


	/**
	 * 修改
	 * @param paramsCallRecord
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(CallRecord paramsCallRecord, HttpServletResponse response) throws IOException {
		try{
			callRecordService.updateByParams(paramsCallRecord);
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
			callRecordService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 锁定记录
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/setLock")
	public void setLock(String id, HttpServletResponse response) throws IOException{
		try{
			callRecordService.setLock(id, request);
			JsonRetInfo.returnSuccess(response, "锁定成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 分机播放录音
	 * @param extNum
	 * @param recordPath
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/playExtRecord")
	public void playExtRecord(String extNum, String recordPath, HttpServletResponse response) throws IOException{
		try{
			if(StringUtils.isBlank(extNum)){
				throw new Exception("没有分机号，不能使用此功能");
			}
			if(StringUtils.isBlank(recordPath)){
				throw new Exception("录音文件为空");
			}
			Integer userId = null;
			User user = (User)request.getSession().getAttribute("_user");
			if(user != null){
				userId = user.getId();
			}
			TempSocket tempSocket = new TempSocket(webSocketService);
			tempSocket.playRecord(extNum, recordPath, userId);
			JsonRetInfo.returnSuccess(response, "请求成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 导出数据
	 * @param tableParams
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/export")
	public void export(TableParams tableParams, HttpServletResponse response) throws IOException{
		try{
			callRecordService.getList(tableParams, response, request);
			JsonRetInfo.returnSuccess(response, "导出成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}


	/**
	 * 清空统计缓存
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/clearStatistics")
	public void clearStatistics(HttpServletResponse response) throws IOException{
		try{
			logger.info("手动清空缓存");
			CallPhoneListener._statisticsDataMap.clear();
			JsonRetInfo.returnSuccess(response, "清空成功");
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
			Map<String, Object> result = callRecordService.getList(tableParams, response, request);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}


	/**
	 * 根据手机号获取通话记录
	 * @param tableParams
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listByPhone")
	@ResponseBody
	public Object listByPhone(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try {
			PaginationParams paginationParams = tableParams.getPaginationParams();
			CallRecordCondition callRecordCondition = new CallRecordCondition();
			callRecordCondition.setPhone(tableParams.getPhone());
			PaginationSupport<CallRecord> callRecordPaginationSupport = callRecordService.list(callRecordCondition, paginationParams.getRange(), new Sorter().desc("create_time"));
			List<CallRecord> callRecordList = callRecordPaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			Map<String, String> projectNameMap = new HashMap<>();
			for(CallRecord callRecord : callRecordList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(callRecord);
				map.put("createTimeStr", DateUtil.dateToString(callRecord.getCreateTime(), 2));
				String projectId = callRecord.getProjectId();
				String projectName = "";
				if( ! projectNameMap.containsKey(projectId)){
					projectName = projectService.getName(projectId);
				}else{
					projectName = projectNameMap.get(projectId);
				}
				map.put("projectName", projectName);
				Client client = clientService.get(callRecord.getClientId());
				String clientId = tableParams.getClientId();
				if(clientId == null){
					clientId = "";
				}
				if(client != null){
					Boolean isToReturn = false;
					if(client.getId().equals(clientId)){
						isToReturn = true;
					}else{
						PublicEnum.ClientStatus clientStatus = client.getReviewStatus();
						if(clientStatus != null && (clientStatus == PublicEnum.ClientStatus.C
								|| clientStatus == PublicEnum.ClientStatus.A
								|| clientStatus == PublicEnum.ClientStatus.B
								|| clientStatus == PublicEnum.ClientStatus.Blur
								|| clientStatus == PublicEnum.ClientStatus.Merchants)){
							isToReturn = true;
						}
					}
					if( ! isToReturn){
						continue;
					}
					map.put("clientName", client.getName());
					if(client.getReviewStatus() != null){
						map.put("clientReviewStatusStr", client.getReviewStatus().getName());
					}
					map.put("clientDes", client.getDes());
				}
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", callRecordPaginationSupport.getTotalCount());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}

	/**
	 * 获取项目各类别客户数量
	 * @return
	 * */
	@RequestMapping("/listStatistics")
	@ResponseBody
	public Object listStatistics(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try {
			Map<String, Object> result = callRecordService.listStatistics(tableParams, false);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}



	/**
	 * 呼叫号码
	 * @param phone
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/toCallPhone")
	public void toCallPhone(String phone, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = callRecordService.toCallPhone(phone, request);
			JsonRetInfo.returnSuccess(response, "拨打成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "num")){
				error = "编号已存在，不能重复创建";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}


	/**
	 * 获取通话记录详情
	 * @param id
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping("/getCallRecordTableItem")
    public void getCallRecordTableItem(String id, HttpServletResponse response) throws IOException{
        try{
            Map<String, Object> map = new HashMap<>();
            CallRecord callRecord = callRecordService.get(id);
            if(callRecord != null){
            	Client client = clientService.get(callRecord.getClientId());
            	if(client != null){
            		map.put("clientName", client.getName());
            		map.put("phone", client.getPhone());
            		map.put("clientReviewStatusStr", client.getReviewStatus() == null ? "" : client.getReviewStatus().getName());
					map.put("clientConfirmStatusStr", client.getConfirmStatus() == null ? "" : client.getConfirmStatus().getName());
					map.put("createTimeStr", DateUtil.dateToString(callRecord.getCreateTime(), 8));
					map.put("clientDes", client.getDes());
					Project project = projectService.get(client.getProjectId());
					if(project != null){
						map.put("projectName", project.getName());
					}
					map.put("workNumber", callRecord.getWorkNumber());
					map.put("userName", callRecord.getUserName());
					map.put("time", callRecord.getTime());
					map.put("reviewWorkNumber", callRecord.getReviewWorkNumber());
					map.put("isAppeal", client.getIsAppeal());
					map.put("groupType", client.getGroupType());
					String groupId = client.getGroupId();
					if(StringUtils.isNotBlank(groupId)){
						ClientGroup clientGroup = clientGroupService.get(groupId);
						if(clientGroup != null){
							User user = userService.getByWorkNumber(clientGroup.getClientGroupName());
							if(user != null){
								map.put("groupName", user.getName());
							}
						}
					}
					map.put("clientStatus", client.getStatus());
				}
			}
            JsonRetInfo.returnSuccess(response, "成功", map);
        }catch (Exception e){
            JsonRetInfo.returnFail(response, e.getMessage());
        }
    }


	/**
	 * 挂断电话
	 * @param clientId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/hangup")
	public void hangup(String clientId, HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isNotBlank(clientId)){
				PhoneSocket phoneSocket = CallPhoneListener._clientIdPhoneMap.get(clientId);
				if(phoneSocket != null){
					phoneSocket.hangupByMyself();
				}else{
					throw new Exception("电话已挂断");
				}
			}
			JsonRetInfo.returnSuccess(response, "成功");
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, error);
		}
	}

	/**
	 * 挂断播放录音
	 * @param extNum
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/hangupPlayRecord")
	public void hangupPlayRecord(String extNum, HttpServletResponse response) throws IOException {
		try{
			if(StringUtils.isNotBlank(extNum)){
				TempSocket tempSocket = CallPhoneListener._extNumTempSocketMap.get(extNum);
				if(tempSocket != null){
					tempSocket.hangupByMyself();
				}else{
					throw new Exception("已停止播放录音");
				}
			}
			JsonRetInfo.returnSuccess(response, "成功");
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, error);
		}
	}
}
