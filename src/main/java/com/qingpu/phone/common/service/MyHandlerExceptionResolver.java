package com.qingpu.phone.common.service;

import com.qingpu.phone.common.utils.HttpUtils;
import com.qingpu.phone.common.utils.RemoteCallException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.testng.log4testng.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    private static Logger log = Logger.getLogger(MyHandlerExceptionResolver.class);  
    
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		JSONObject ret = new JSONObject();
		if (ex instanceof DataAccessException) {
			// 数据库操作异常			
			ret.put("code", -1);
			ret.put("message", "操作数据库失败");
			//记录日志
			log.error("--操作数据库失败:" + ex.getMessage(), ex);
		} else if (ex instanceof RemoteCallException) {
			// 自定义异常
			ret.put("code", -1);
			ret.put("message", ex.getMessage());
			log.error("--产生自定义异常:" + ex.getMessage(), ex);
		} else if (ex instanceof RuntimeException) {
			// 系统运行时异常
			ret.put("code", -1);
			ret.put("message", ex.getMessage());
			log.error("--产生系统运行异常:" + ex.getMessage(), ex);
		}
		//将异常发送到客户端
		HttpUtils.sendJsonStr(response, ret.toString());

		return mv;
	}
}
