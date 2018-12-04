package com.qingpu.phone.filter;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ajax请求拦截
 */
public class ServiceFilter implements Filter {
	
	private List<String> otherFile;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		otherFile = new ArrayList<>();
		otherFile.add("/service/platform/noLogin");
	}

	@Override
	public void doFilter(ServletRequest servletRrequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRrequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		servletRrequest.setCharacterEncoding("UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		String host = StringUtils.replace(request.getRequestURL().toString(), uri, "");
		
		User user = (User) session.getAttribute("_user");	// 网页平台

		User userWX = (User) session.getAttribute("_userWX");	// 微信客户端

		if(uri.indexOf("/service/platform/") != -1 && user != null){
			chain.doFilter(request,response);
			return;
		}else if(uri.indexOf("/app.") != -1 && userWX != null){
			chain.doFilter(request,response);
			return;
		}else{
			//如果没有登录
			for (String string : otherFile) {
				if(uri.indexOf(string) != -1){
					chain.doFilter(request, response);
					return;
				}
			}
			try {
				//设置返回数据字符编码
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				if(uri.indexOf("/service/platform/") != -1){
					response.sendRedirect(host + "/platform/login.jsp");
					return;
				}else if(uri.indexOf("/app") != -1){
					response.getWriter().write(new JsonRetInfo(JsonRetInfo.ERROR, "当前登录失败，请重新登录").toString());
					return;
				}else if(uri.startsWith("/service")){
					response.sendRedirect(host + "/platform/login.jsp");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void destroy() {

	}

}
