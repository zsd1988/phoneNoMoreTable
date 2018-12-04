package com.qingpu.phone.user.controller;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.EncryptUtils;
import com.qingpu.phone.common.utils.HttpRequestToHtml;
import com.qingpu.phone.common.utils.RemoteCallException;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemSetting.service.ExtAndIPService;
import com.qingpu.phone.user.entity.*;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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
@RequestMapping("/service/platform/noLogin/user")
public class UserControllerNoLogin extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(UserControllerNoLogin.class);


	@Resource
	UserService userService;

	@Resource
	ExtAndIPService extAndIPService;
	
	@Autowired  
	HttpServletRequest request; //获取到当前请求的request
	
	//客户端请求返回到登录界面
	@RequestMapping("/login")
	public String adminLogin(HttpServletResponse response) throws Exception{		
		return "/platform/login";
	}
	
	//客户端显示注册页面
	@RequestMapping("/register")
	public String adminRegister(HttpServletResponse response) throws Exception{
		return "/platform/register";
	}
	
	//邮箱用户注册
	@RequestMapping(value="/imeilRegister")
	@ResponseBody
	public String pcRegister(@RequestBody String body, HttpServletResponse response){
		JSONObject jsonObj = new JSONObject(body);
		String imeil = jsonObj.getString("imeil");
		String nickname = jsonObj.getString("nickname");
		String password = jsonObj.getString("password");
		User user = new User();
		user.setPassword(EncryptUtils.encryptByMD5(password));//将密码转换为32位MD5存储
		/*Farm farm = new Farm();
		farm.setUser(user);//农场绑定用户*/
		userService.saveUser(user);//产生异常将由异常处理类进行响应客户端
		
		JSONObject ret = new JSONObject();
		ret.put("code", 0);
		ret.put("message", "操作数据库成功");
	
		return ret.toString();
	}


	//检查登录邮箱用户是否存在
	@RequestMapping("/checkImeilUserExist")
	@ResponseBody
	public String checkUserExist(@RequestBody String body){
		JSONObject jsonObj = new JSONObject(body);
		JsonRetInfo userinfo = new JsonRetInfo();
		try {
			String imeil = jsonObj.getString("imeil");
			User user = userService.getByWorkNumber(imeil);
			if(user == null){
				userinfo.setCode(-1);
				userinfo.setMessage("*该工号不存在");
			}
		}catch (Exception e){
			userinfo.setCode(-1);
			userinfo.setMessage("*请输入正确的工号");
		}
		return new JSONObject(userinfo).toString();
	}


	//pc后台用户使用邮箱登录
	@RequestMapping("/imeilLogin")
	@ResponseBody
	public String pcLoginByEmail(@RequestBody String body){
		JSONObject jsonObject = new JSONObject(body);
		JsonRetInfo userinfo = new JsonRetInfo();
		if(jsonObject.has("openid")){
			//说明是从微信端扫码登录的用户
			System.out.println("--微信扫码登录");
			String openid = jsonObject.getString("openid");
			User user = userService.getUserByOpenid(openid);
			if(user != null){
				//在session中缓存userinfo
				request.getSession().setAttribute("UserInfo", userinfo);
				request.getSession().setAttribute("_user", user);
			}
		}else{
			//使用邮箱密码方式登录
			String workNumber = jsonObject.getString("imeil");
			String password = jsonObject.getString("password");
			String name = jsonObject.getString("name");
			String workTypeStr = jsonObject.getString("workType");
			PublicEnum.WorkType workType = PublicEnum.WorkType.valueOf(workTypeStr);
			User user = userService.getByWorkNumber(workNumber);
			if(user != null){
				if(!password.equals(user.getPassword())){
					//如果密码不正确
					userinfo.setCode(-1);
					userinfo.setMessage("*登录失败，密码错误");
					return  new JSONObject(userinfo).toString();
				}
				if(user.getIsDel()){
					userinfo.setCode(-1);
					userinfo.setMessage("账号已禁用");
					return  new JSONObject(userinfo).toString();
				}
				if( user.getWorkType() != workType){
					userinfo.setCode(-1);
					userinfo.setMessage("*登录失败，所选类型不匹配");
					return  new JSONObject(userinfo).toString();
				}
				if(workType == PublicEnum.WorkType.FullTime){
					if( ! user.getName().equals(name)){
						userinfo.setCode(-1);
						userinfo.setMessage("*登录失败，姓名错误");
						return  new JSONObject(userinfo).toString();
					}
				}else{
					user.setName(name);
				}
				try {
					user.setIsLogin(true);
					if( jsonObject.has("ip") &&  QingpuConstants._isSupportIP){
						extAndIPService.loginByIP(jsonObject, user);
					}
				}catch (Exception e){
					userinfo.setCode(-1);
					userinfo.setMessage("*" + e.getMessage());
					return  new JSONObject(userinfo).toString();
				}
				userService.updateUser(user);
				Integer userId = user.getId();
				if(CallPhoneListener._onlineUserMap.containsKey(userId)){
					CallPhoneListener._onlineUserMap.put(userId, user);
				}

				//在session中缓存userinfo
				request.getSession().setAttribute("UserInfo", userinfo);
				request.getSession().setAttribute("_user", user);
			}else{
				userinfo.setCode(-1);
				userinfo.setMessage("*登录失败，密码错误");
			}
		}

		return new JSONObject(userinfo).toString();
	}

	/**
	 * 检测是否连接服务器
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/checkIsConnection")
	public void checkIsConnection(HttpServletResponse response) throws IOException{
		try{
			JsonRetInfo.returnSuccess(response, "连接成功");
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "连接失败：" + e.getMessage());
		}
	}

	@RequestMapping("/testException")
	public String testException(HttpServletRequest request, HttpServletResponse response){
		System.out.println("执行测试异常代码");
		userService.getUserByOpenid("hhhhhhhhhh");
		throw new RemoteCallException(RemoteCallException.RCE_DATABASE, "数据库操作出错");
	}

	/**
	 * 查看内存关键信息
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getIdleList")
	public void getIdleList( HttpServletResponse response) throws IOException{
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("sip", CallPhoneListener._socketHost);
			Map<String, List<String>> portIdMap = new HashMap<>();
			for(String key : CallPhoneListener._groupCallPortIdMap.keySet()){
				List<String> portIdList = CallPhoneListener._groupCallPortIdMap.get(key);
				portIdMap.put(key, portIdList);
			}
			map.put("群呼占用端口", portIdMap);
			map.put("空闲端口", CallPhoneListener._idlePortIdList);
			map.put("通话中的用户", CallPhoneListener._inCallingUser);
			String inCallingPortId = "";
			for(String key : CallPhoneListener._incallingPort.keySet()){
				inCallingPortId += key + ",";
			}
			map.put("通话中的端口", inCallingPortId);
			map.put("在线用户", CallPhoneListener._onlineUserList);
			map.put("通话中的电话", CallPhoneListener._currentPhoneList);
			portIdMap = new HashMap<>();
			for(String key : CallPhoneListener._groupCallCallingPhoneMap.keySet()){
				List<String> portIdList = CallPhoneListener._groupCallCallingPhoneMap.get(key);
				portIdMap.put(key, portIdList);
			}
			map.put("转客服成功通话中的电话", portIdMap);
			List<String> callerPhone = new ArrayList<>();
			for(CallRecord callRecord : CallPhoneListener._waitingCaller){
				callerPhone.add(callRecord.getPhone());
			}
			map.put("回拨队列中的电话",  callerPhone);
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, e.getMessage());
		}
	}
}
