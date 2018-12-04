package com.qingpu.phone.clientManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.clientManager.condition.ClientCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.systemManager.service.ProjectService;
import com.qingpu.phone.user.condition.UserClientGroupCondition;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserClientGroup;
import com.qingpu.phone.user.service.UserClientGroupService;
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
import java.util.*;

@Controller
@RequestMapping("/service/platform/login/clientManager/client")
public class ClientController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(ClientController.class);

	@Resource
	ClientService clientService;

	@Resource
	UserClientGroupService userClientGroupService;

	@Resource
	ProjectService projectService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param client
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(Client client, HttpServletResponse response) throws IOException {
		try{
			/*
			//手机号去重判断
			ClientCondition clientCondition=new ClientCondition();
			clientCondition.setPhone(client.getPhone());
			long count=this.clientService.countByCondition(clientCondition);
			if(count>0){
				JsonRetInfo.returnFail(response,  "手机号已存在");
				return;
			}
			*/
			Map<String, Object> map = clientService.createByParams(client);
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
	 * @param paramsClient
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(Client paramsClient,Boolean calling, HttpServletResponse response) throws IOException {
		try{
			User user = (User)request.getSession().getAttribute("_user");
			clientService.updateByParams(paramsClient, calling, user);
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
			clientService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 转移客户
	 * @param paramsClient
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/transClient")
	public void transClient(Client paramsClient, HttpServletResponse response) throws IOException{
		try{
			clientService.transClient(paramsClient);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}


	/**
	 * 取消转移
	 * @param paramsClient
	 * @throws Exception
	 */
	@RequestMapping("/cancelTransClient")
	public void cancelTransClient(Client paramsClient, HttpServletResponse response) throws IOException{
		try{
			clientService.cancelTransClient(paramsClient);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}


	/**
	 * 批量转移
	 * @param paramsClient
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/transAllClient")
	public void transAllClient(Client paramsClient, HttpServletResponse response) throws IOException{
		try{
			clientService.transAllClient(paramsClient);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}


	/**
	 * 申诉
	 * @param paramsClient
	 * @throws Exception
	 */
	@RequestMapping("/setAppeal")
	public void setAppeal(Client paramsClient, HttpServletResponse response) throws IOException{
		try{
			Client client = clientService.get(paramsClient.getId());
			if(client == null){
				throw new Exception("未找到该客户");
			}
			client.setIsAppeal(paramsClient.getIsAppeal());
			clientService.update(client);
			JsonRetInfo.returnSuccess(response, "成功");
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
			ClientCondition clientCondition = new ClientCondition();
			clientCondition.setbDel(false);

			if(tableParams.getDistribute()!=null){	//&&!tableParams.getDistribute()
				clientCondition.setbDistribute(tableParams.getDistribute());
			}else{
				//clientCondition.setbDistribute(true);
			}
			String phone = tableParams.getPhone();
			// 是否主动点击查询
			Boolean isClick = tableParams.getInclude();
			if(isClick != null && isClick){
				if(StringUtils.isBlank(phone)){
					phone = "abc";
				}
			}

			if(StringUtils.isNotBlank(phone)){
				clientCondition.setPhoneLike(phone);
			}
            String groupId = tableParams.getGroupId();
			if(StringUtils.isNotBlank(groupId)){
				switch (groupId){
					case "public":
						groupId = "0";
						break;
					case "publicInterview":
						groupId = "1";
						clientCondition.setLtLastUpdateTime(DateUtil.add(new Date(), DateUtil.ADDMONTH, -2));
						break;
					default:
						break;
				}
				clientCondition.setGroupId(groupId);
			}
			if(StringUtils.isNotBlank(tableParams.getProjectId())){
				clientCondition.setProjectId(tableParams.getProjectId());
			}
			if(StringUtils.isNotBlank(tableParams.getGroupType())){
				clientCondition.setClientGroupType(Enum.valueOf(PublicEnum.ClientGroupType.class,tableParams.getGroupType()));
			}
			if(tableParams.getClientStatus()!=null){
				clientCondition.setClientStatus(tableParams.getClientStatus());
			}

			if(tableParams.getImportTag()!=null){
				clientCondition.setImportTag(tableParams.getImportTag());
			}
            Sorter sorter = new Sorter();
			if(StringUtils.isNotBlank(groupId) && "1".equals(groupId)){
                sorter.asc("last_update_time");
            }else{
                sorter.desc("update_time");
            }
			PaginationSupport<Client> testTablePaginationSupport = clientService.list(clientCondition, range,sorter);
			List<Client> clientList = testTablePaginationSupport.getItems();
			Map<String, String> projectNameMap = new HashMap<>();
			List<Map<String, Object>> mapList = new ArrayList<>();
			if(clientList!=null&&clientList.size()>0){
				for(Client client : clientList){
					if(client.getStatus()!=null){
						client.setStatusStr(client.getStatus().getName());
					}
					if(client.getReviewStatus()!=null){
						client.setReviewStatusStr(client.getReviewStatus().getName());
					}
					if(client.getConfirmStatus()!=null){
						client.setConfirmStatusStr(client.getConfirmStatus().getName());
					}
					if(client.getImportTag()!=null){
						client.setImportTagStr(client.getImportTag().getName());
					}
					client.setCreateTimeStr(DateUtil.dateToString(client.getCreateTime(),2));
					client.setUpdateTimeStr(DateUtil.dateToString(client.getUpdateTime(),2));
					if(StringUtil.isNotNull(client.getProjectId())){
						client.setProjectName(projectService.getNameByMap(client.getProjectId(), projectNameMap));
					}

					if(client.getGroupType()!=null){
						client.setGroupTypeStr(client.getGroupType().getName());
					}

					Map<String, Object> map = AutoEvaluationUtil.domainToMap(client);
					map.put("lastUpdateTimeStr", DateUtil.dateToString(client.getLastUpdateTime(),2));
					if(client.getSex()!=null){
						if(client.getSex()==1){
							map.put("sexStr",'男');
						}
						else{
							map.put("sexStr",'女');
						}
					}
					if(client.getYearTag() != null){
						map.put("yearTagStr",client.getYearTag().getName());
					}
					if(client.getArea() != null){
						map.put("areaStr",client.getArea().getName());
					}
					mapList.add(map);
				}
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
	 * 通过id获取客户
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getClient")
	public void getClient(String id, HttpServletResponse response) throws IOException{
		try {
			Client client = clientService.get(id);
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(client);
			JsonRetInfo.returnSuccess(response, "获取客户成功",map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "获取客户失败：" + e.getMessage());
		}
	}



	/**
	 * 客户移组
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/moveClientGroupType")
	public void moveClientGroupType(Client client, HttpServletResponse response) throws IOException {
		try{
			Client clientInfo=this.clientService.get(client.getId());
			if(clientInfo!=null){
				if("1".equals(clientInfo.getGroupId())){
					User user = (User)request.getSession().getAttribute("_user");
					UserClientGroupCondition userClientGroupCondition = new UserClientGroupCondition();
					userClientGroupCondition.setUserId(user.getId());
					userClientGroupCondition.setbDel(false);
					userClientGroupCondition.setProjectId(client.getProjectId());
					List<UserClientGroup> userClientGroupList = userClientGroupService.list(userClientGroupCondition);
					if( ! userClientGroupList.isEmpty()){
						String groupId = userClientGroupList.get(0).getClientGroupId();
						clientInfo.setGroupId(groupId);
						clientInfo.setUpdateTime(new Date());
					}
				}
				clientInfo.setProjectId(client.getProjectId());
				clientInfo.setGroupType(client.getGroupType());
				this.clientService.update(clientInfo);
				JsonRetInfo.returnSuccess(response, "操作成功");
				return;
			}
			JsonRetInfo.returnFail(response, "获取客户失败：客户不存在！" );
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 导入客户资源
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveProjectClient")
	public void saveProjectClient(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			String os = System.getProperty("os.name");
			if( ! os.toLowerCase().startsWith("win")){
				clientService.saveExportProjectClient2(request,response);
			}else{
				clientService.saveExportProjectClient(request,response);
			}
		}catch(Exception e){
			e.printStackTrace();
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, "操作失败：" + error);
		}
	}

	/**
	 * 获取客服意向转化率
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getConversionRate")
	@ResponseBody
	public Object getConversionRate(@RequestBody TableParams tableParams,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			Map<String, Object> result = clientService.getConversionRate(tableParams);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch(Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}
}
