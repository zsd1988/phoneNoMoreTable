package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.DayHintCondition;
import com.qingpu.phone.systemManager.entity.DayHint;
import com.qingpu.phone.systemManager.service.DayHintService;
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
@RequestMapping("/service/platform/login/systemManager/dayHint")
public class DayHintController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(DayHintController.class);

	@Resource
	DayHintService dayHintService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param dayHint
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(DayHint dayHint, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = dayHintService.createByParams(dayHint);
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
	 * @param paramsDayHint
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(DayHint paramsDayHint, HttpServletResponse response) throws IOException {
		try{
			dayHintService.updateByParams(paramsDayHint);
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
			dayHintService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "删除成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}

	/**
	 * 启用
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/enable")
	public void enable(Long id, HttpServletResponse response) throws IOException{
		try{
			DayHint luckDraw = dayHintService.get(id);
			if(luckDraw == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return ;
			}
			luckDraw.setIsDel(false);
			dayHintService.update(luckDraw);
			JsonRetInfo.returnSuccess(response, "启用成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "启用失败：" + e.getMessage());
		}
	}


	/**
	 * 获取砸金蛋问候语
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getGoldenEggHint")
	public void getGoldenEggHint(HttpServletResponse response) throws IOException{
		try{
			DayHint dayHint = dayHintService.getRateDayHintByType(PublicEnum.DayHintType.Day);
			Map<String, Object> map = new HashMap<>();
			if(dayHint != null){
				map = AutoEvaluationUtil.domainToMap(dayHint);
			}
			JsonRetInfo.returnSuccess(response, "启用成功", map);
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
			DayHintCondition dayHintCondition = new DayHintCondition();
			String roleType = tableParams.getRoleType();
			if(StringUtils.isNotBlank(roleType)){
				dayHintCondition.setType(PublicEnum.DayHintType.valueOf(roleType));
			}
			PaginationSupport<DayHint> testTablePaginationSupport = dayHintService.list(dayHintCondition, range, new Sorter().asc("type").desc("create_time"));
			List<DayHint> dayHintList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			Map<PublicEnum.DayHintType, Integer> rateMap = new HashMap<>();
			for(DayHint dayHint : dayHintList){
				if( ! dayHint.getIsDel()){
					PublicEnum.DayHintType typeItem = dayHint.getType();
					Integer totalRate = rateMap.get(typeItem);
					if(totalRate == null){
						totalRate = 0;
					}
					totalRate += dayHint.getRate();
					rateMap.put(typeItem, totalRate);
				}
			}
			for(DayHint dayHint : dayHintList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(dayHint);
				PublicEnum.DayHintType typeItem = dayHint.getType();
				Integer totalRate = rateMap.get(typeItem);
				Integer rate = dayHint.getRate();
				String rateStr = rate + "";
				if( ! dayHint.getIsDel() && totalRate > 0 ){
					rateStr += "（"  + ArithUtil.getRoundTwo(rate*100.0/totalRate) + "%）";
				}
				map.put("rateStr", rateStr );
				map.put("typeStr", typeItem.getName());
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
