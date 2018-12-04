package com.qingpu.phone.systemManager.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.IntentionHintCondition;
import com.qingpu.phone.systemManager.condition.IntentionHintRewardRecordCondition;
import com.qingpu.phone.systemManager.entity.IntentionHint;
import com.qingpu.phone.systemManager.service.IntentionHintRewardRecordService;
import com.qingpu.phone.systemManager.service.IntentionHintService;
import com.qingpu.phone.user.entity.User;
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
@RequestMapping("/service/platform/login/systemManager/intentionHint")
public class IntentionHintController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(IntentionHintController.class);

	@Resource
	IntentionHintService intentionHintService;

	@Resource
	IntentionHintRewardRecordService intentionHintRewardRecordService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param intentionHint
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(IntentionHint intentionHint, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = intentionHintService.createByParams(intentionHint);
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
	 * @param paramsIntentionHint
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(IntentionHint paramsIntentionHint, HttpServletResponse response) throws IOException {
		try{
			intentionHintService.updateByParams(paramsIntentionHint);
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
			intentionHintService.delById(id, request);
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
			IntentionHint luckDraw = intentionHintService.get(id);
			if(luckDraw == null){
				JsonRetInfo.returnFail(response, "未找到数据");
				return ;
			}
			luckDraw.setIsDel(false);
			intentionHintService.update(luckDraw);
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

			IntentionHintCondition intentionHintCondition = new IntentionHintCondition();
			IntentionHintCondition intentionHintCondition2 = new IntentionHintCondition();
			String typeStr = tableParams.getRoleType();
			if(StringUtils.isNotBlank(typeStr)){
				PublicEnum.IntentionHintType type = PublicEnum.IntentionHintType.valueOf(typeStr);
				intentionHintCondition.setType(type);
				intentionHintCondition2.setType(type);
			}
			Map<PublicEnum.IntentionHintType, Integer> rateMap = new HashMap<>();
			List<IntentionHint> totalList = intentionHintService.list(intentionHintCondition);
			// 查询所有奖品概率
			for(IntentionHint intentionHint : totalList){
				if( ! intentionHint.getIsDel() && intentionHint.getCount() > 0){
					PublicEnum.IntentionHintType typeItem = intentionHint.getType();
					Integer totalRate = rateMap.get(typeItem);
					if(totalRate == null){
						totalRate = 0;
					}
					totalRate += intentionHint.getRate();
					rateMap.put(typeItem, totalRate);
				}
			}


			// 查询返回数据
			intentionHintCondition2.setReward(tableParams.getInclude());
			PaginationSupport<IntentionHint> testTablePaginationSupport = intentionHintService.list(intentionHintCondition2, range, new Sorter().desc("create_time"));
			List<IntentionHint> intentionHintList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(IntentionHint intentionHint : intentionHintList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(intentionHint);
				Integer rate = intentionHint.getRate();
				PublicEnum.IntentionHintType typeItem = intentionHint.getType();
				Integer totalRate = rateMap.get(typeItem);
				String rateStr = rate + "";
				if( ! intentionHint.getIsDel() && intentionHint.getCount() > 0 && totalRate > 0 ){
					rateStr += "（"  + ArithUtil.getRoundTwo(rate*100.0/totalRate) + "%）";
				}
				map.put("rateStr", rateStr );
				map.put("typeStr", intentionHint.getType().getName());
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
