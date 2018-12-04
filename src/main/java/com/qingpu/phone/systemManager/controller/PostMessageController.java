package com.qingpu.phone.systemManager.controller;

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
import com.qingpu.phone.systemManager.condition.PostMessageCondition;
import com.qingpu.phone.systemManager.entity.PostMessage;
import com.qingpu.phone.systemManager.service.PostMessageService;
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
@RequestMapping("/service/platform/login/systemManager/postMessage")
public class PostMessageController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(PostMessageController.class);

	@Resource
	PostMessageService postMessageService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param postMessage
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(PostMessage postMessage, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = postMessageService.createByParams(postMessage, request);
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
	 * @param paramsPostMessage
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(PostMessage paramsPostMessage, HttpServletResponse response) throws IOException {
		try{
			postMessageService.updateByParams(paramsPostMessage, request);
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
	public void delete(Long id, HttpServletResponse response) throws IOException{
		try{
			postMessageService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}


	@RequestMapping("/enable")
	public void enable(Long id, HttpServletResponse response) throws IOException{
		try{
			PostMessage postMessage = postMessageService.get(id);
			if(postMessage == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return ;
			}
			postMessage.setIsDel(false);
			postMessageService.update(postMessage);
			JsonRetInfo.returnSuccess(response, "启用成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "启用失败：" + e.getMessage());
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
			PostMessageCondition postMessageCondition = new PostMessageCondition();
			String roleTypeStr = tableParams.getRoleType();
			if(StringUtils.isNotBlank(roleTypeStr)){
				postMessageCondition.setType(PublicEnum.PostMessageType.valueOf(roleTypeStr));
			}
			PaginationSupport<PostMessage> testTablePaginationSupport = postMessageService.list(postMessageCondition, range, new Sorter().desc("create_time"));
			List<PostMessage> postMessageList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(PostMessage postMessage : postMessageList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(postMessage);
				String roleTypes = postMessage.getReceiveRoleTypes();
				List<String> roleTypeList = StringUtil.splitByComma(roleTypes);
				String roleTypeName = "";
				for(String roleType : roleTypeList){
					roleTypeName += PublicEnum.RoleType.valueOf(roleType).getName() + ",";
				}
				map.put("roleTypeName", StringUtil.subLastChar(roleTypeName));
				map.put("typeStr", postMessage.getType().getName());
				map.put("pushTimeStr", DateUtil.dateToString(postMessage.getPushTime(), 2));
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
