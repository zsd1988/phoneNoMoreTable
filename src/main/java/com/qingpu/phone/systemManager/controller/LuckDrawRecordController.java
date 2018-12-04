package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.LuckDrawRecordCondition;
import com.qingpu.phone.systemManager.entity.LuckDraw;
import com.qingpu.phone.systemManager.entity.LuckDrawRecord;
import com.qingpu.phone.systemManager.service.LuckDrawRecordService;
import com.qingpu.phone.user.entity.User;
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
import java.util.*;

@Controller
@RequestMapping("/service/platform/login/systemManager/LuckDrawRecord")
public class LuckDrawRecordController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(LuckDrawRecordController.class);

	@Resource
	LuckDrawRecordService luckDrawRecordService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request

	@Resource
	UserService userService;


	/**
	 * 创建
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(HttpServletResponse response) throws IOException {
		try{
			User user = (User)request.getSession().getAttribute("_user");
			LuckDraw luckDraw = luckDrawRecordService.create(user.getId());
			JsonRetInfo.returnSuccess(response, "获取成功", luckDraw);
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
	 * @param paramsLuckDrawRecord
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(LuckDrawRecord paramsLuckDrawRecord, HttpServletResponse response) throws IOException {
		try{
			luckDrawRecordService.updateByParams(paramsLuckDrawRecord);
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
			luckDrawRecordService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 领取奖品
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/get")
	public void get(String id, HttpServletResponse response) throws IOException{
		try{
			luckDrawRecordService.setIsGet(id);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 批量领取
	 * @param ids
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getAll")
	public void getAll(String ids, HttpServletResponse response) throws IOException{
		try{
			luckDrawRecordService.getAll(ids);
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
			if(StringUtils.isBlank(tableParams.getStartTimeStr())){
				throw new Exception("请选择开始时间");
			}
			if(StringUtils.isBlank(tableParams.getEndTimeStr())){
				throw new Exception("请选择结束时间");
			}
			Date startTime = DateUtil.getStartDateTime(tableParams.getStartTimeStr());
			Date endTime = DateUtil.getEndDateTime(tableParams.getEndTimeStr());
			LuckDrawRecordCondition luckDrawRecordCondition = new LuckDrawRecordCondition();
			Boolean isMine = tableParams.getInclude();
			if(isMine != null && isMine){
				User user = (User)request.getSession().getAttribute("_user");
				user = userService.get(user.getId());
				if(user != null){
					luckDrawRecordCondition.setUserName(user.getName());
				}
			}
			luckDrawRecordCondition.setGtCreateTime(startTime);
			luckDrawRecordCondition.setLtCreateTime(endTime);
			luckDrawRecordCondition.setLikeUserName(tableParams.getName());
			PaginationSupport<LuckDrawRecord> testTablePaginationSupport = luckDrawRecordService.list(luckDrawRecordCondition, range, new Sorter().desc("name"));
			List<LuckDrawRecord> luckDrawRecordList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(LuckDrawRecord luckDrawRecord : luckDrawRecordList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(luckDrawRecord);
				map.put("createTimeStr", DateUtil.dateToString(luckDrawRecord.getCreateTime(),2 ));
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
