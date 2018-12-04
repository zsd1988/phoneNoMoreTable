package com.qingpu.phone.systemSetting.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemSetting.condition.AppCondition;
import com.qingpu.phone.systemSetting.entity.App;
import com.qingpu.phone.systemSetting.service.AppService;
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
@RequestMapping("/service/platform/noLogin/systemSetting/app")
public class AppControllerNoLogin extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(AppControllerNoLogin.class);

	@Resource
	AppService appService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 检测更新
	 * @param app
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/checkUpdate")
	public void checkUpdate(App app, HttpServletResponse response) throws IOException {
		try{
			App exitApp = appService.getByOsType(app.getOsType());
			Map<String, Object> map = new HashMap<>();
			Boolean isUpdate = false;
			if(exitApp != null && exitApp.getLeastVersion() > app.getLeastVersion()){
				isUpdate = true;
				map.put("downUrl", QingpuConstants.uploadUrl + exitApp.getDownloadUrl());
			}
			map.put("isUpdate", isUpdate);
			JsonRetInfo.returnSuccess(response, "获取成功", map);
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, error);
		}
	}

}
