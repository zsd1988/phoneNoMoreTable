package com.qingpu.phone.clientManager.controller;

import com.qingpu.phone.clientManager.condition.ClientCondition;
import com.qingpu.phone.clientManager.condition.ClientGroupCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.entity.ClientGroup;
import com.qingpu.phone.clientManager.service.ClientGroupService;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.ProjectCondition;
import com.qingpu.phone.systemManager.entity.Project;
import com.qingpu.phone.systemManager.service.ProjectService;
import com.qingpu.phone.user.condition.*;
import com.qingpu.phone.user.entity.*;
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
@RequestMapping("/service/platform/noLogin/clientManager/clientGroupController")
public class ClientGroupController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(ClientGroupController.class);

	@Resource
	ClientGroupService clientGroupService;

	@Resource
	ClientService clientService;

	@Resource
	ProjectService projectService;


	@Resource
	UserClientGroupService userClientGroupService;


	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(ClientGroup clientGroup, HttpServletResponse response) throws IOException {
		try{
			ClientGroupCondition clientGroupCondition=new ClientGroupCondition();
			clientGroupCondition.setbDel(false);
			clientGroupCondition.setClientGroupName(clientGroup.getClientGroupName());
			long count=this.clientGroupService.countByCondition(clientGroupCondition);
			if(count>0){
				JsonRetInfo.returnFail(response,  "组别名称已存在");
				return;
			}
			Map<String, Object> map = clientGroupService.createByParams(clientGroup);
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
	 * @param clientGroup
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(ClientGroup clientGroup, HttpServletResponse response) throws IOException {
		try{
			//检测是否已存在
			ClientGroupCondition clientGroupCondition=new ClientGroupCondition();
			clientGroupCondition.setbDel(false);
			clientGroupCondition.setClientGroupName(clientGroup.getClientGroupName());
			clientGroupCondition.setNoGroupId(clientGroup.getId());
			long count=this.clientGroupService.countByCondition(clientGroupCondition);
			if(count>0){
				JsonRetInfo.returnFail(response,  "组别名称已存在");
				return;
			}
			clientGroupService.updateByParams(clientGroup);
			Map<String, Object> map = new HashMap<>();
			map.put("id", clientGroup.getId());
			JsonRetInfo.returnSuccess(response, "操作成功",map);
		}catch (Exception e){
			e.printStackTrace();;
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
			clientGroupService.delById(id, request);
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
			ClientGroupCondition testTableCondition = new ClientGroupCondition();
			PaginationSupport<ClientGroup> testTablePaginationSupport = clientGroupService.list(testTableCondition, range, new Sorter().desc("create_time"));
			List<ClientGroup> testTableList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(ClientGroup testTable : testTableList){
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
	 * 获取所有基础组
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/clientGroupNameOption")
	public void clientGroupNameOption(String groupId,HttpServletResponse response) throws IOException{
		try{
			User user = (User) request.getSession().getAttribute("_user");	// 网页平台
			List<String>  groupIdList=new ArrayList<>();
			List<Map<String, Object>> clientGroupMapList = new ArrayList<>();
			Map<String, Object> publicMap = new HashMap<>();
			publicMap.put("name", "所有客户组");
			publicMap.put("id", "root");
			publicMap.put("pId","0");
			publicMap.put("open",true);
			publicMap.put("type","allGroup");
			clientGroupMapList.add(publicMap);

			String roleId = user.getRoleId();
			Boolean isShowInterviewPublic = false;
			Boolean isShowPublic = true;
			switch (roleId){
				case QingpuConstants.Roles_Interview_id:
					isShowInterviewPublic = true;
					isShowPublic = false;
					break;
				case QingpuConstants.Roles_Manager_id:
				case QingpuConstants.Roles_Super_id:
					isShowInterviewPublic = true;
					break;
			}
			if(isShowPublic){
				publicMap = new HashMap<>();
				publicMap.put("name", "公共客户资源");
				publicMap.put("id", "public");
				publicMap.put("pId","root");
				publicMap.put("open",true);
				publicMap.put("type","public");
				clientGroupMapList.add(publicMap);
			}
			if(isShowInterviewPublic){
				publicMap = new HashMap<>();
				publicMap.put("name", "公海资源");
				publicMap.put("id", "publicInterview");
				publicMap.put("pId","root");
				publicMap.put("open",true);
				publicMap.put("type","publicInterview");
				clientGroupMapList.add(publicMap);
			}

			List<UserClientGroup> userClientGroupList=new ArrayList<UserClientGroup>();
			if(!(user.getRoleId().equals(QingpuConstants.Roles_Super_id)||user.getRoleId().equals(QingpuConstants.Roles_Manager_id))){
				//查询用户的分组权限
				UserClientGroupCondition userClientGroupCondition = new UserClientGroupCondition();
				userClientGroupCondition.setbDel(false);
				userClientGroupCondition.setUserId(user.getId());
				userClientGroupList= this.userClientGroupService.list(userClientGroupCondition);

				if(userClientGroupList!=null&&userClientGroupList.size()>0){
					for(UserClientGroup userClientGroup:userClientGroupList){
						groupIdList.add(userClientGroup.getClientGroupId());
					}
				}
			}


			ClientGroupCondition clientGroupCondition = new ClientGroupCondition();
			clientGroupCondition.setbDel(false);
			if(groupIdList!=null&&groupIdList.size()>0){
				clientGroupCondition.setGroupIdList(groupIdList);
			}
			List<ClientGroup> clientGroupList = this.clientGroupService.list(clientGroupCondition,new Sorter().asc("create_time"));
			//查询项目
			ProjectCondition projectCondition=new ProjectCondition();
			projectCondition.setbDel(false);
			List<Project> projectList=this.projectService.list(projectCondition);

			if(clientGroupList!=null&&clientGroupList.size()>0){
				for(ClientGroup clientGroup:clientGroupList){
					boolean isExist=false;
					Map<String, Object> map = new HashMap<>();
					//查询
					if(projectList!=null&&projectList.size()>0){
						for(Project project:projectList){
							if(user.getRoleId().equals(QingpuConstants.Roles_Super_id)||user.getRoleId().equals(QingpuConstants.Roles_Manager_id)){
								isExist = true;
								map = new HashMap<>();
								map.put("name", project.getName());
								map.put("id", clientGroup.getId() + "_" + project.getId());
								map.put("pId", clientGroup.getId());
								if (StringUtil.isNotNull(groupId) && groupId.equals(clientGroup.getId())) {
									map.put("open", true);
								} else {
									map.put("open", false);
								}
								map.put("type", "project");
								map.put("projectId", project.getId());
								map.put("groupId", clientGroup.getId());
								//查询
								clientGroupMapList.add(map);

								if (StringUtil.isNotNull(groupId) && groupId.equals(clientGroup.getId())) {
									for (PublicEnum.ClientGroupType clientGroupType : PublicEnum.ClientGroupType.values()) {
										map = new HashMap<>();
										map.put("name", clientGroupType.getName());
										map.put("id", clientGroupType.getType());
										map.put("pId", clientGroup.getId() + "_" + project.getId());
										map.put("groupId", clientGroup.getId());
										map.put("open", true);
										map.put("type", "groupType");
										map.put("projectId", project.getId());
										clientGroupMapList.add(map);
									}
								} else {
									for (PublicEnum.ClientGroupType clientGroupType : PublicEnum.ClientGroupType.values()) {
										map = new HashMap<>();
										map.put("name", clientGroupType.getName());
										map.put("id", clientGroupType.getType());
										map.put("pId", clientGroup.getId() + "_" + project.getId());
										map.put("groupId", clientGroup.getId());
										map.put("open", false);
										map.put("type", "groupType");
										map.put("projectId", project.getId());
										clientGroupMapList.add(map);
									}
								}
							}else {
								if (userClientGroupList != null && userClientGroupList.size() > 0) {
									for (UserClientGroup userClientGroup : userClientGroupList) {
										if (userClientGroup.getClientGroupId().equals(clientGroup.getId()) && userClientGroup.getProjectId().equals(project.getId())) {
											isExist = true;
											map = new HashMap<>();
											map.put("name", project.getName());
											map.put("id", clientGroup.getId() + "_" + project.getId());
											map.put("pId", clientGroup.getId());
											if (StringUtil.isNotNull(groupId) && groupId.equals(clientGroup.getId())) {
												map.put("open", true);
											} else {
												map.put("open", false);
											}
											map.put("type", "project");
											map.put("projectId", project.getId());
											map.put("groupId", clientGroup.getId());
											//查询
											clientGroupMapList.add(map);

											if (StringUtil.isNotNull(groupId) && groupId.equals(clientGroup.getId())) {
												for (PublicEnum.ClientGroupType clientGroupType : PublicEnum.ClientGroupType.values()) {
													map = new HashMap<>();
													map.put("name", clientGroupType.getName());
													map.put("id", clientGroupType.getType());
													map.put("pId", clientGroup.getId() + "_" + project.getId());
													map.put("groupId", clientGroup.getId());
													map.put("open", true);
													map.put("type", "groupType");
													map.put("projectId", project.getId());
													clientGroupMapList.add(map);
												}
											} else {
												for (PublicEnum.ClientGroupType clientGroupType : PublicEnum.ClientGroupType.values()) {
													map = new HashMap<>();
													map.put("name", clientGroupType.getName());
													map.put("id", clientGroupType.getType());
													map.put("pId", clientGroup.getId() + "_" + project.getId());
													map.put("groupId", clientGroup.getId());
													map.put("open", false);
													map.put("type", "groupType");
													map.put("projectId", project.getId());
													clientGroupMapList.add(map);
												}
											}
										}
									}
								}
							}
						}
					}
					if(isExist){
						map = new HashMap<>();
						map.put("name", clientGroup.getClientGroupName());
						map.put("id", clientGroup.getId());
						map.put("pId", "root");
						map.put("groupName", clientGroup.getClientGroupName());
						if(StringUtil.isNotNull(groupId)&&groupId.equals(clientGroup.getId())){
							map.put("open",true);
						}else{
							map.put("open",false);
						}
						map.put("groupId",clientGroup.getId());
						map.put("type","group");
						clientGroupMapList.add(map);
					}

				}
			}
			Map<String, Object> result = new HashMap<>();
			result.put("clientGroupMapList", clientGroupMapList);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}



	public void mapList(String projectId,String groupId,List<Map<String, Object>> clientGroupMapList,Boolean open){
		Map<String, Object> map = new HashMap<>();
		map.put("name",PublicEnum.ClientGroupType.Visiting.getName() );
		map.put("id", PublicEnum.ClientGroupType.Visiting.getType());
		map.put("pId", groupId+"_"+projectId);
		map.put("groupId", groupId);
		map.put("open",open);
		map.put("type","groupType");
		map.put("projectId",projectId);
		//查询
		clientGroupMapList.add(map);

		map = new HashMap<>();
		map.put("name",PublicEnum.ClientGroupType.Vague.getName() );
		map.put("id", PublicEnum.ClientGroupType.Vague.getType());
		map.put("pId", groupId+"_"+projectId);
		map.put("projectId",projectId);
		map.put("groupId", groupId);
		map.put("open",open);
		map.put("type","groupType");
		clientGroupMapList.add(map);

		map = new HashMap<>();
		map.put("name",PublicEnum.ClientGroupType.Customer.getName() );
		map.put("id", PublicEnum.ClientGroupType.Customer.getType());
		map.put("pId", groupId+"_"+projectId);
		map.put("projectId",projectId);
		map.put("groupId", groupId);
		map.put("open",open);
		map.put("type","groupType");
		clientGroupMapList.add(map);

		map = new HashMap<>();
		map.put("name",PublicEnum.ClientGroupType.Follow.getName() );
		map.put("id", PublicEnum.ClientGroupType.Follow.getType());
		map.put("pId", groupId+"_"+projectId);
		map.put("projectId",projectId);
		map.put("groupId", groupId);
		map.put("open",open);
		map.put("type","groupType");
		clientGroupMapList.add(map);

		map = new HashMap<>();
		map.put("name",PublicEnum.ClientGroupType.Attract.getName() );
		map.put("id", PublicEnum.ClientGroupType.Attract.getType());
		map.put("pId", groupId+"_"+projectId);
		map.put("projectId",projectId);
		map.put("groupId", groupId);
		map.put("open",open);
		map.put("type","groupType");
		clientGroupMapList.add(map);
	}



	/**
	 * 获取角色的权限
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getClientGroupList")
	public void getClientGroupList(String userId ,HttpServletResponse response) throws IOException{
		try{
			List<Map<String, Object>> clientGroupMapList = new ArrayList<>();
			UserClientGroupCondition userClientGroupCondition = new UserClientGroupCondition();
			userClientGroupCondition.setbDel(false);
			userClientGroupCondition.setUserId(Integer.parseInt(userId));
			List<UserClientGroup> userClientGroupList = this.userClientGroupService.list(userClientGroupCondition);

			ProjectCondition projectCondition=new ProjectCondition();
			projectCondition.setbDel(false);
			List<Project> projectList=this.projectService.list(projectCondition);

			Map<String, Object> mapClientGroupInfo = new HashMap<>();
			mapClientGroupInfo.put("name", "全选");
			mapClientGroupInfo.put("id", "0");
			mapClientGroupInfo.put("pId", "root");
			mapClientGroupInfo.put("open",true);


			ClientGroupCondition clientGroupCondition=new ClientGroupCondition();
			clientGroupCondition.setbDel(false);
			List<ClientGroup> clientGroupList=this.clientGroupService.list(clientGroupCondition,new Sorter().asc("client_group_name"));
			if (clientGroupList!=null&&clientGroupList.size()>0) {
				for(ClientGroup clientGroup:clientGroupList){
					Map<String, Object> mapClientGroup = new HashMap<>();
					mapClientGroup.put("name", clientGroup.getClientGroupName());
					mapClientGroup.put("id", clientGroup.getId());
					mapClientGroup.put("pId", "0");
					mapClientGroup.put("open",false);
					if(projectList!=null&&projectList.size()>0){
						for(Project project:projectList){
							Map<String, Object> map = new HashMap<>();
							map.put("name", project.getName());
							map.put("id", project.getId());
							map.put("pId", clientGroup.getId());
							map.put("open",false);
							if(userClientGroupList!=null&&userClientGroupList.size()>0){
								for(UserClientGroup userClientGroup:userClientGroupList){
									if(userClientGroup.getClientGroupId().equals(clientGroup.getId())&&userClientGroup.getProjectId().equals(project.getId())){
										map.put("checked", true);
										mapClientGroup.put("checked", true);
										mapClientGroupInfo.put("checked", true);
										break;
									}
								}
							}
							clientGroupMapList.add(map);
						}
					}
					clientGroupMapList.add(mapClientGroup);

				}
			}
			clientGroupMapList.add(mapClientGroupInfo);
			Map<String, Object> result = new HashMap<>();
			result.put("clientGroupMapList", clientGroupMapList);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}



	/**
	 * 获取待分配的项目组
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/allocatedClientGroupOption")
	public void allocatedClientGroupOption(HttpServletResponse response) throws IOException{
		try{
			List<Map<String, Object>> clientGroupMapList = new ArrayList<>();
			Map<String, Object> map = null;
			//查询项目
			ProjectCondition projectCondition=new ProjectCondition();
			projectCondition.setbDel(false);
			List<Project> projectList=this.projectService.list(projectCondition);

			map = new HashMap<>();
			map.put("name","待分配资源");
			map.put("id", 0);
			map.put("pId", "root");
			map.put("open", true);
			map.put("type","allocated");
			clientGroupMapList.add(map);

			if(projectList!=null&&projectList.size()>0){
				for(Project project:projectList){
					map = new HashMap<>();
					map.put("name",project.getName() );
					map.put("id", project.getId());
					map.put("pId", "0");
					map.put("open", true);
					map.put("type","project");
					clientGroupMapList.add(map);
					for (PublicEnum.ImportTag  importTag : PublicEnum.ImportTag.values()) {
						map = new HashMap<>();
						map.put("name",importTag.getName() );
						map.put("id", importTag.getType());
						map.put("pId", project.getId());
						map.put("type","importTag");
						map.put("open", true);
						clientGroupMapList.add(map);
					}
				}
			}
			Map<String, Object> result = new HashMap<>();
			result.put("allocatedClientGroup", clientGroupMapList);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}

	/**
	 * 客户分配
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/distributionClient")
	public void distributionClient(TableParams tableParams, HttpServletResponse response) throws Exception{
		try{
			//查询符合条件的客户资源
			ClientCondition clientCondition=new ClientCondition();
			clientCondition.setProjectId(tableParams.getProjectId());
			clientCondition.setImportTag(tableParams.getImportTag());
			clientCondition.setbDistribute(false);
			Range range = new Range(0,tableParams.getLimit());
			PaginationSupport<Client> clientPaginationSupport = clientService.list(clientCondition, range, new Sorter().asc("create_time"));
			List<Client> clientList = clientPaginationSupport.getItems();
			if(clientList!=null&&clientList.size()>0){
				Date now = new Date();
				for(Client clientInfo:clientList){
					clientInfo.setIsDistribute(true);
					clientInfo.setGroupType(tableParams.getClientGroupType());
					clientInfo.setGroupId(tableParams.getGroupId());
					clientInfo.setUpdateTime(now);
					this.clientService.update(clientInfo);
				}
				JsonRetInfo.returnSuccess(response, "操作成功");
				return;
			}
			JsonRetInfo.returnFail(response, "操作失败：没有符合条件的数据");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}

/*
	*//**
	 * 客户分配
	 * @param response
	 * @throws IOException
	 *//*
	@RequestMapping("/distributionClient")
	@ResponseBody
	public Object distributionClient(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try{
			//查询符合条件的客户资源
			ClientCondition clientCondition=new ClientCondition();
			clientCondition.setProjectId(tableParams.getProjectId());
			clientCondition.setImportTag(tableParams.getImportTag());
			Range range = new Range(1,tableParams.getLimit());
			PaginationSupport<Client> clientPaginationSupport = clientService.list(clientCondition, range, new Sorter().asc("create_time"));
			List<Client> clientList = clientPaginationSupport.getItems();
			if(clientList!=null&&clientList.size()>0){
				for(Client clientInfo:clientList){
					clientInfo.setIsDistribute(true);
					clientInfo.setGroupType(tableParams.getClientGroupType());
					clientInfo.setGroupId(tableParams.getGroupId());
					this.clientService.update(clientInfo);
				}
				return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", null);
			}
			return new JsonRetInfo(JsonRetInfo.ERROR, "操作失败：没有符合条件的数据");
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}*/


}
