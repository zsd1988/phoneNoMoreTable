package com.qingpu.phone.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//错误处理controller
@Controller
@RequestMapping("/error")
public class ErrorController {
	
	@RequestMapping("/error_400")
	public void error_400(HttpServletRequest request, HttpServletResponse response){
		try {
			response.sendRedirect(request.getContextPath() + "/platform/page-400.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/error_403")
	public void error_403(HttpServletRequest request, HttpServletResponse response){
		try {
			response.sendRedirect(request.getContextPath() + "/platform/page-403.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/error_404")
	public void error_404(HttpServletRequest request, HttpServletResponse response){
		try {
			response.sendRedirect(request.getContextPath() + "/platform/page-404.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/error_500")
	public void error_500(HttpServletRequest request, HttpServletResponse response){
		try {
			response.sendRedirect(request.getContextPath() + "/platform/page-500.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
