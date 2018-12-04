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
import com.qingpu.phone.user.condition.JiangHuPaiRankCondition;
import com.qingpu.phone.user.condition.JiangHuRankCondition;
import com.qingpu.phone.user.entity.JiangHuPai;
import com.qingpu.phone.user.entity.JiangHuPaiRank;
import com.qingpu.phone.user.service.JiangHuPaiRankService;
import com.qingpu.phone.user.service.JiangHuPaiService;
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
@RequestMapping("/service/platform/login/user/jiangHuPaiRank")
public class JiangHuPaiRankController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(JiangHuPaiRankController.class);

	@Resource
	JiangHuPaiRankService jiangHuPaiRankService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request

	@Resource
	JiangHuPaiService jiangHuPaiService;


	/**
	 * 创建
	 * @param jiangHuPaiRank
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(JiangHuPaiRank jiangHuPaiRank, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = jiangHuPaiRankService.createByParams(jiangHuPaiRank);
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
	 * @param paramsJiangHuPaiRank
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(JiangHuPaiRank paramsJiangHuPaiRank, HttpServletResponse response) throws IOException {
		try{
			jiangHuPaiRankService.updateByParams(paramsJiangHuPaiRank);
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
			jiangHuPaiRankService.delById(id, request);
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
			String startTimeStr = tableParams.getStartTimeStr();
			if(StringUtils.isBlank(startTimeStr)){
				throw new Exception("请传入时间");
			}
			JiangHuPaiRankCondition jiangHuPaiRankCondition = new JiangHuPaiRankCondition();
			Date createTime = DateUtil.strDateToDate(startTimeStr, 0);
			jiangHuPaiRankCondition.setGeCreateTime(DateUtil.getThisWeekMonday(createTime));
			jiangHuPaiRankCondition.setLtCreateTime(DateUtil.getNextWeekMonday(createTime));
			PublicEnum.RoleType roleType = PublicEnum.RoleType.CS;
			if(StringUtils.isNotBlank(tableParams.getRoleType())){
				if(tableParams.getRoleType().equals("interview")){
					roleType = PublicEnum.RoleType.Interview;
				}
			}
			jiangHuPaiRankCondition.setRoleType(roleType);
			PaginationSupport<JiangHuPaiRank> testTablePaginationSupport = jiangHuPaiRankService.list(jiangHuPaiRankCondition, range, new Sorter().desc("gold"));
			List<JiangHuPaiRank> jiangHuPaiRankList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(JiangHuPaiRank jiangHuPaiRank : jiangHuPaiRankList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(jiangHuPaiRank);
				JiangHuPai jiangHuPai = jiangHuPaiService.get(jiangHuPaiRank.getPaiId());
				if(jiangHuPai != null){
					map.put("name", jiangHuPai.getNickName());
				}
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
