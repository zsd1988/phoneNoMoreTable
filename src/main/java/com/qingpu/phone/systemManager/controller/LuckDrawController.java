package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.LuckDrawCondition;
import com.qingpu.phone.systemManager.condition.LuckDrawRecordCondition;
import com.qingpu.phone.systemManager.entity.LuckDraw;
import com.qingpu.phone.systemManager.service.LuckDrawRecordService;
import com.qingpu.phone.systemManager.service.LuckDrawService;
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
@RequestMapping("/service/platform/login/systemManager/luckDraw")
public class LuckDrawController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(LuckDrawController.class);

	@Resource
	LuckDrawService luckDrawService;

	@Resource
	LuckDrawRecordService luckDrawRecordService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param luckDraw
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(LuckDraw luckDraw, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = luckDrawService.createByParams(luckDraw);
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
	 * @param paramsLuckDraw
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(LuckDraw paramsLuckDraw, HttpServletResponse response) throws IOException {
		try{
			luckDrawService.updateByParams(paramsLuckDraw);
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
			luckDrawService.delById(id, request);
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
	public void enable(String id, HttpServletResponse response) throws IOException{
		try{
			LuckDraw luckDraw = luckDrawService.get(id);
			if(luckDraw == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return ;
			}
			luckDraw.setIsDel(false);
			luckDrawService.update(luckDraw);
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
			LuckDrawCondition luckDrawCondition = new LuckDrawCondition();
			PaginationSupport<LuckDraw> testTablePaginationSupport = luckDrawService.list(luckDrawCondition, range, new Sorter().desc("create_time"));
			List<LuckDraw> luckDrawList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			Integer totalRate = 0;
			for(LuckDraw luckDraw : luckDrawList){
				if( ! luckDraw.getIsDel() && luckDraw.getCount() > 0){
					totalRate += luckDraw.getRate();
				}
			}
			for(LuckDraw luckDraw : luckDrawList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(luckDraw);
				String name = luckDraw.getName();
				// 查找当天中奖的次数
				LuckDrawRecordCondition luckDrawRecordCondition = new LuckDrawRecordCondition();
				luckDrawRecordCondition.setGtCreateTime(DateUtil.getDayStartTime());
				luckDrawRecordCondition.setLtCreateTime(DateUtil.getDayEndTime());
				luckDrawRecordCondition.setName(name);
				Long count = luckDrawRecordService.countByCondition(luckDrawRecordCondition);
				if(count == null){
					count = 0L;
				}
				map.put("recordCount", count);
				Integer rate = luckDraw.getRate();
				String rateStr = rate + "";
				if( ! luckDraw.getIsDel() && luckDraw.getCount() > 0 ){
					rateStr += "（"  + ArithUtil.getRoundTwo(rate*100.0/totalRate) + "%）";
				}
				map.put("rateStr", rateStr );
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
