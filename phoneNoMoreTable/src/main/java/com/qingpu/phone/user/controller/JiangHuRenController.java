package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.JiangHuRenCondition;
import com.qingpu.phone.user.entity.JiangHuRen;
import com.qingpu.phone.user.service.JiangHuRenService;
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
@RequestMapping("/service/platform/login/user/jiangHuRen")
public class JiangHuRenController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(JiangHuRenController.class);

	@Resource
	JiangHuRenService jiangHuRenService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param jiangHuRen
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(JiangHuRen jiangHuRen, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = jiangHuRenService.createByParams(jiangHuRen);
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
	 * @param paramsJiangHuRen
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(JiangHuRen paramsJiangHuRen, HttpServletResponse response) throws IOException {
		try{
			jiangHuRenService.updateByParams(paramsJiangHuRen);
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
			jiangHuRenService.delById(id, request);
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
			JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
			PaginationSupport<JiangHuRen> testTablePaginationSupport = jiangHuRenService.list(jiangHuRenCondition, range, new Sorter().desc("create_time"));
			List<JiangHuRen> jiangHuRenList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(JiangHuRen jiangHuRen : jiangHuRenList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(jiangHuRen);
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
	 * 导入意向
	 * @param userIds
	 * @param intentions
	 * @param finishes
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importIntention")
	public void importIntention(String userIds, String intentions, String finishes, HttpServletResponse response) throws IOException{
		try{
			jiangHuRenService.importIntention(userIds, intentions, finishes);
			JsonRetInfo.returnSuccess(response, "成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}
}
