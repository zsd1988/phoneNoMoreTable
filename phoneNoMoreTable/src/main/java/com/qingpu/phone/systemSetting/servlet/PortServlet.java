package com.qingpu.phone.systemSetting.servlet;

import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.systemSetting.service.PortService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * 文件上传组件完整使用
 */
@WebServlet(name="port", urlPatterns="/port")
public class PortServlet extends HttpServlet {
	PortService portService;

	private static Logger logger = Logger.getLogger(PortServlet.class);
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		portService = (PortService)wac.getBean("portService");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,response);
	}

//	端口摘机：http://192.168.0.29/port?action=phonePick&called=$called&caller=$caller&userid=$userid&status=$status
//	端口挂机：http://192.168.0.29/port?action=phoneHang&called=$called&caller=$caller&userid=$userid&status=$status
//	电话呼入：http://192.168.0.29/port?action=phoneIn&called=$called&caller=$caller&userid=$userid&status=$status
//	电话呼出：http://192.168.0.29/port?action=phoneOut&called=$called&caller=$caller&userid=$userid&status=$status
//	呼叫建立：http://192.168.0.29/port?action=phoneBuild&called=$called&caller=$caller&userid=$userid&status=$status
//	呼叫终止：http://192.168.0.29/port?action=phoneOver&called=$called&caller=$caller&userid=$userid&status=$status
//	心跳：http://192.168.0.29/port?action=heart&time=$time&sn=$sn&ip=$localip

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paramsMap = new HashMap<>();
		Enumeration enu=request.getParameterNames();
		while(enu.hasMoreElements()){
			String paraName= (String)enu.nextElement();
			paramsMap.put(paraName, request.getParameter(paraName));
		}
		try{
//			if(paramsMap.containsKey("action")){
//				String action = paramsMap.get("action");
//				if(action.equals("heart")){
////					logger.info("收到网关心跳：" + paramsMap.get("sn"));
//				}else{
//					String id = paramsMap.get("userid");
//					Boolean isCreate = false;
//					Boolean isUpdate = true;
//					Port port = portService.get(id);
//					if(port == null && StringUtils.isNotBlank(id)){
//						port = new Port();
//						port.setId(id);
//						isCreate = true;
//					}
//					String caller = paramsMap.get("caller");
//					String called = paramsMap.get("called");
////					for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
////						logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
////					}
//					logger.info("收到网关上报：" + paramsMap.toString());
//					switch (action){
//						case "phoneIn":
//							port.setIsCaller(true);
//							port.setCalled(called);
//							port.setCaller(caller);
//							port.setStatus(PublicEnum.PortStatus.InCall);
//							break;
//						case "phoneOut":
//							port.setIsCaller(false);
//							if(StringUtils.isNotBlank(called)){
//								if(called.startsWith("0731")){
//									//客户主动打电话进来
//									String portId = port.getId();
//									CallPhoneListener._idlePortIdList.remove(portId);
//									CallPhoneListener._idlePortMap.remove(portId);
//								}
//							}
//							port.setCalled(called);
//							port.setCaller(caller);
//							port.setStatus(PublicEnum.PortStatus.InCall);
//							break;
//						case "phonePick":
//							port.setStatus(PublicEnum.PortStatus.Calling);
//							break;
//						case "phoneBuild":
//							if(port.getStatus() != PublicEnum.PortStatus.Calling){
//								port.setStatus(PublicEnum.PortStatus.Calling);
//							}else{
//								isUpdate = false;
//							}
//							break;
//						case "phoneHang":
//							port.setStatus(PublicEnum.PortStatus.Idle);
//							if(StringUtils.isNotBlank(called)){
//								if(called.startsWith("0731")){
//									//客户主动打电话进来
//									String portId = port.getId();
//									if( ! CallPhoneListener._idlePortIdList.contains(portId)){
//										CallPhoneListener._idlePortIdList.add(portId);
//										CallPhoneListener._idlePortMap.put(portId, port);
//									}
//									break;
//								}
//							}
//							portService.setFinish(port);
//							port.setCaller(null);
//							port.setCalled(null);
//							break;
//						case "phoneOver":
//							if(port.getCalled() != null){
//								port.setStatus(PublicEnum.PortStatus.Idle);
//								if(StringUtils.isNotBlank(called)){
//									if(called.startsWith("0731")){
//										//客户主动打电话进来
//                                        String portId = port.getId();
//                                        if( ! CallPhoneListener._idlePortIdList.contains(portId)){
//                                            CallPhoneListener._idlePortIdList.add(portId);
//                                            CallPhoneListener._idlePortMap.put(portId, port);
//                                        }
//										break;
//									}
//								}
//							}else{
//								isUpdate = false;
//							}
//							portService.setFinish(port);
//							port.setCaller(null);
//							port.setCalled(null);
//							break;
//						default:
//							break;
//					}
//					if(port != null){
//						if(isCreate){
//							portService.create(port);
//						}else{
//							if(isUpdate){
//								portService.update(port);
//							}
//						}
//					}
//				}
//			}
		}catch (Exception e){
			logger.error("处理端口信息失败：" + e.getMessage());
		}
	}
}