package com.qingpu.phone.systemSetting.controller;

import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.phoneSocket.UserPhoneSocket;
import com.qingpu.phone.systemSetting.condition.PortCondition;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.systemSetting.service.PortService;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.UserService;
import jnr.ffi.annotations.In;
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
@RequestMapping("/service/platform/login/systemSetting/port")
public class PortController extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(PortController.class);

	@Resource
	PortService portService;

	@Resource
	UserService userService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 创建
	 * @param port
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/create")
	public void create(Port port, HttpServletResponse response) throws IOException {
		try{
			Map<String, Object> map = portService.createByParams(port);
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
	 * @param paramsPort
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(Port paramsPort, HttpServletResponse response) throws IOException {
		try{
			portService.updateByParams(paramsPort);
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
			portService.delById(id, request);
			JsonRetInfo.returnSuccess(response, "禁用成功");
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
			Port port = portService.get(id);
			if(port == null){
				JsonRetInfo.returnFail(response, "启用失败：未找到数据");
				return ;
			}
			portService.enable(port);
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
			PortCondition portCondition = new PortCondition();
			PaginationSupport<Port> testTablePaginationSupport = portService.list(portCondition, range, new Sorter().asc("id"));
			List<Port> portList = testTablePaginationSupport.getItems();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for(Port port : portList){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(port);
				if( port.getIsDel() || CallPhoneListener._idlePortIdList.contains(port.getId())){
					port.setStatus(PublicEnum.PortStatus.Idle);
				}else{
					if(CallPhoneListener._incallingPort.containsKey(port.getId())){
						port.setStatus(PublicEnum.PortStatus.InCall);
					}else{
						port.setStatus(PublicEnum.PortStatus.Calling);
					}
				}
				map.put("statusStr", port.getStatus().getName());
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
	 * 获取启动的数量
	 * @param tableParams
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEnableList")
	@ResponseBody
	public Object getEnableList(@RequestBody TableParams tableParams, HttpServletResponse response) throws Exception{
		try {
			PortCondition portCondition = new PortCondition();
			List<Port> portList = portService.list(portCondition, new Sorter().asc("id"));
			Integer sumCount = 0;
			Integer sumTotalCount = 0;
			List<Map<String, Object>> mapList = new ArrayList<>();
			Map<String, Integer> map = new HashMap<>();
			Map<String, Integer> totalMap = new HashMap<>();
			Map<String, Boolean> isThirdMap = new HashMap<>();
			Map<String, Boolean> isCallOutMap = new HashMap<>();
			Map<String, Boolean> isFirstMap = new HashMap<>();
			for(Port port : portList){
				String id = port.getId();
				String dev = id.split("-")[0];
				Integer count = 0;
				if( ! map.containsKey(dev)){
					map.put(dev, count);
				}
				if( ! isThirdMap.containsKey(dev)){
					isThirdMap.put(dev, port.getIsThird());
					isCallOutMap.put(dev, port.getIsCallOut());
					isFirstMap.put(dev, port.getIsFirst());
				}
				count = map.get(dev);
				if( ! port.getIsDel()){
					count++;
					sumCount++;
				}
				map.put(dev, count);
				Integer totalCount = 0;
				if( ! totalMap.containsKey(dev)){
					totalMap.put(dev, totalCount);
				}
				totalCount = totalMap.get(dev);
				totalCount++;
				sumTotalCount++;
				totalMap.put(dev, totalCount);
			}

            List<Map.Entry<String, Integer>> sortList =
                    new ArrayList<>(map.entrySet());
            Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            for(Map.Entry<String, Integer> map1 : sortList){
                Map<String, Object> item = new HashMap<>();
                String key = map1.getKey();
                item.put("dev", key);
                item.put("count", map.get(key));
                item.put("totalCount", totalMap.get(key));
                item.put("isFirst", isFirstMap.get(key));
				item.put("isCallOut", isCallOutMap.get(key));
				item.put("isThird", isThirdMap.get(key));
                mapList.add(item);
            }

			Map<String, Object> item = new HashMap<>();
			item.put("dev", "总计");
			item.put("count",  sumCount);
			item.put("totalCount", sumTotalCount);
			mapList.add(item);

			Map<String, Object> result = new HashMap<>();
			result.put("totalCount", mapList.size());
			result.put("result", mapList);
			return new JsonRetInfo(JsonRetInfo.SUCCESS, "操作成功", result);
		}catch (Exception e){
			return new JsonRetInfo(JsonRetInfo.ERROR, e.getMessage());
		}
	}


	/**
	 * 启动相应数量
	 * @param dev
	 * @param count
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/savePortNum")
	public void savePortNum(String dev, Integer count,Boolean isFirst, Boolean isThird, Boolean isCallOut, HttpServletResponse response) throws IOException{
		try{
			portService.savePortNum(dev, count, isFirst, isThird, isCallOut);
			JsonRetInfo.returnSuccess(response, "操作成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}


	/**
	 * 检测端口拨打
	 * @param port
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/callPort")
	public void callPort(Port port, HttpServletResponse response) throws IOException {
		try{
			Port exitPort = portService.get(port.getId());
			if( ! exitPort.getIsDel()){
				throw new Exception("请先禁用端口");
			}
			User user = (User)request.getSession().getAttribute("_user");
			user = userService.get(user.getId());
			Integer extNum = user.getExtNum();
			if(extNum == null){
				throw new Exception("用户没有分机号");
			}
			String phone = port.getCalled();
			String tempPhone = phone;
			if(tempPhone.startsWith("0")){
				tempPhone = tempPhone.substring(1, tempPhone.length());
			}
			if( ! StringUtil.checkIsPhone(tempPhone)){
				throw new Exception("拨打的手机号格式不正确");
			}
			CallRecord callRecord = new CallRecord();
			callRecord.setPhone(phone);
			callRecord.setExtNum(extNum);
			UserPhoneSocket phoneSocket = new UserPhoneSocket(CallPhoneListener._socketHost,CallPhoneListener._socketPort, callRecord, null, port);
			phoneSocket.startCall();
			JsonRetInfo.returnSuccess(response, "拨打成功");
		}catch (Exception e){
			String error = e.getMessage();
			if(CommonUtils.checkIsNotUniqueForEntity(error, "num")){
				error = "编号已存在，不能重复创建";
			}
			JsonRetInfo.returnFail(response, error);
		}
	}
}
