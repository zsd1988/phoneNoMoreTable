package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.condition.*;
import com.qingpu.phone.user.entity.*;
import com.qingpu.phone.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/service/platform/login/user/userGroup")
public class UserGroupController extends HandlerInterceptorAdapter {
	@Resource
	UserGroupService userGroupService;

	@Resource
	UserService userService;

	@Resource
	JiangHuPaiService jiangHuPaiService;

	@Autowired  
	HttpServletRequest request; //获取到当前请求的request

	/**
	 * 创建
	 * @param userGroup
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(UserGroup userGroup,  HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = userGroupService.createByParams(userGroup);
			JsonRetInfo.returnSuccess(response, "获取成功", map);
			return;
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response,  error);
		}
	}

	/**
	 * 获取所有用户组
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getUserGroupList")
	public void getUserGroupList(String fid,HttpServletResponse response) throws IOException{
		try{
			/**
			 * 获取所有用户组
			 * @param isDel 为空则为获取全部的
			 * @return
			 */
			List<Map<String, Object>> mapUserGroupList = new ArrayList<>();

			UserGroupCondition userGroupCondition = new UserGroupCondition();
			userGroupCondition.setbDel(false);
			//userGroupCondition.setFid(Integer.parseInt(userGroupId));
			List<UserGroup> userGroupList = this.userGroupService.list(userGroupCondition);
			if(userGroupList!=null&&userGroupList.size()>0){
				List<Integer> fidList=new ArrayList<Integer>();
				if(StringUtils.isNotBlank(fid)){
					getAllFid(Integer.parseInt(fid),fidList);
				}
				for(UserGroup userGroupInfo:userGroupList){
					Map<String, Object> map = new HashMap<>();
					map.put("name", userGroupInfo.getGroupName());
					Integer id = userGroupInfo.getId();
					map.put("id", id);
					map.put("pId", userGroupInfo.getFid());
					map.put("open",false);
					if(userGroupInfo.getId() == QingpuConstants.User_Group_System_Id){
						map.put("open",true);
					}
					if(fidList!=null&&fidList.size()>0&&fidList.contains(userGroupInfo.getId())){
						map.put("open",true);
					}
					Integer fidItem = userGroupInfo.getFid();
					if(fidItem.intValue() == QingpuConstants.User_Group_Customer_Id
							|| fidItem.intValue() == QingpuConstants.User_Group_Interview_Id){
						JiangHuPai jiangHuPai = jiangHuPaiService.get(id);
						if(jiangHuPai != null){
							map.put("nickName", jiangHuPai.getNickName());
							map.put("count", jiangHuPai.getCount());
							map.put("gongfu", jiangHuPai.getGongfu());
						}
					}
					//查询
					mapUserGroupList.add(map);
				}

				//查询所用用户
				UserCondition userCondition=new UserCondition();
//				userCondition.setDel(false);
				userCondition.setNotNullGroupId(0);
				List<User> userList=this.userService.list(userCondition, new Sorter().asc("work_number"));
				if(userList!=null&&userList.size()>0){
					for(User user:userList){
						Map<String, Object> map = new HashMap<>();
						map.put("name", user.getWorkNumber());
						map.put("id", "user_"+user.getId());
						map.put("type", "user");
						map.put("pId", user.getGroupId());
						map.put("open",false);
						mapUserGroupList.add(map);
					}
				}
			}
			Map<String, Object> result = new HashMap<>();
			result.put("mapUserGroupList", mapUserGroupList);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}


	public void  getAllFid(Integer id,List<Integer> fidList){
		UserGroup userGroup=this.userGroupService.get(id);
		if(userGroup!=null&&userGroup.getId()!=5){
			fidList.add(userGroup.getId());
			getAllFid(userGroup.getFid(),fidList);
		}
	}



	/**
	 * 获取所有基础组
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/userGroupOption")
	public void userGroupOption(HttpServletResponse response) throws IOException{
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			UserGroupCondition userGroupCondition = new UserGroupCondition();
			userGroupCondition.setbDel(false);
			userGroupCondition.setFid(QingpuConstants.User_Group_System_Id.intValue());
			List<UserGroup> userGroupList = this.userGroupService.list(userGroupCondition);
			if(userGroupList!=null&&userGroupList.size()>0){
				for(UserGroup userGroup:userGroupList){
					map.put(userGroup.getId()+"",userGroup.getGroupName());
				}
			}

			Map<String, Object> result = new HashMap<>();
			result.put("userGroupOption", map);
			JsonRetInfo.returnSuccess(response, "操作成功", result);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}






}
