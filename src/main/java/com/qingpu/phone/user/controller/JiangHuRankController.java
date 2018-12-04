package com.qingpu.phone.user.controller;

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
import com.qingpu.phone.user.condition.JiangHuRankCondition;
import com.qingpu.phone.user.condition.JiangHuRenCondition;
import com.qingpu.phone.user.entity.JiangHuRank;
import com.qingpu.phone.user.entity.JiangHuRen;
import com.qingpu.phone.user.service.JiangHuRankService;
import com.qingpu.phone.user.service.JiangHuRenService;
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
@RequestMapping("/service/platform/login/user/jiangHuRank")
public class JiangHuRankController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(JiangHuRankController.class);

	@Resource
	JiangHuRankService jiangHuRankService;

	@Resource
	JiangHuRenService jiangHuRenService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param jiangHuRank
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(JiangHuRank jiangHuRank, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = jiangHuRankService.createByParams(jiangHuRank);
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
	 * @param paramsJiangHuRank
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(JiangHuRank paramsJiangHuRank, HttpServletResponse response) throws IOException {
		try{
			jiangHuRankService.updateByParams(paramsJiangHuRank);
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
			jiangHuRankService.delById(id, request);
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
			String userIds = tableParams.getUserIds();
			String startTimeStr = tableParams.getStartTimeStr();
			if(StringUtils.isBlank(startTimeStr)){
				throw new Exception("请传入时间");
			}
			JiangHuRankCondition jiangHuRankCondition = new JiangHuRankCondition();
			String userName = tableParams.getName();
			if(StringUtils.isNotBlank(userName)){
				jiangHuRankCondition.setLikeUserName(userName);
			}else{
				if(StringUtils.isNotBlank(userIds)){
					List<String> userIdList = StringUtil.splitByComma(userIds);
					List<Integer> userIdIntLIst = new ArrayList<>();
					for(String userId : userIdList){
						userIdIntLIst.add(Integer.parseInt(userId));
					}
					jiangHuRankCondition.setUserIdList(userIdIntLIst);
				}
			}
			Date createTime = DateUtil.strDateToDate(startTimeStr, 0);
			jiangHuRankCondition.setGeCreateTime(DateUtil.getThisWeekMonday(createTime));
			jiangHuRankCondition.setLtCreateTime(DateUtil.getNextWeekMonday(createTime));
			PaginationSupport<JiangHuRank> testTablePaginationSupport = jiangHuRankService.list(jiangHuRankCondition, range, new Sorter().desc("gold"));
			List<JiangHuRank> jiangHuRankList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(JiangHuRank jiangHuRank : jiangHuRankList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(jiangHuRank);
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
