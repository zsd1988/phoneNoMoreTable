package com.qingpu.phone.common.func.webSocket;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.JsonUtil;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.IntentionHintCondition;
import com.qingpu.phone.systemManager.entity.IntentionHint;
import com.qingpu.phone.systemManager.entity.LuckDrawRecord;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.systemManager.service.IntentionHintService;
import com.qingpu.phone.systemManager.service.LuckDrawService;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserInfo;
import com.qingpu.phone.user.service.JiangHuRenService;
import com.qingpu.phone.user.service.UserInfoService;
import com.qingpu.phone.user.service.UserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理webSocket
 * */
@Service("webSocketService")
public class WebSocketService {
	private static Logger logger = Logger.getLogger(WebSocketService.class);

	@Resource
	UserService userService;

	@Resource
	LuckDrawService luckDrawService;

	@Resource
	IntentionHintService intentionHintService;

	@Resource
	JiangHuRenService jiangHuRenService;

	@Resource
	UserInfoService userInfoService;

	// 在线屏幕
	public static Map<String, Session> onLineClient = new HashMap<>();
	public static List<Session> cocosOnlineSession = new ArrayList<>();
    // 异地登陆旧的session
    public static List<Session> loginOutSession = new ArrayList<>();

	/**
	 * 显示客户来电弹窗
	 * @param userId
	 * @param clientId
	 * @param text
	 */
	@Transactional(rollbackFor = Exception.class)
	public void sendShowClient(String userId, String clientId, String text) throws Exception{
		Session session = WebSocketService.onLineClient.get(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("clientId", clientId);
		map.put("text", text);
		this.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.ShowClient, map));
		this.setIsOnline(false, Integer.parseInt(userId));
		this.sendOnline(false, session);
		logger.info("用户id设置离线：  " +  userId);
	}

	/**
	 * 发送通话结束
	 * @param userId
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void sendCallEnd(String userId) throws Exception{
		Session session = WebSocketService.onLineClient.get(userId);
		this.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.CallEnd));
	}


	@Transactional(rollbackFor = Exception.class)
	public void sendHangUp(String userId) throws Exception{
		Session session = WebSocketService.onLineClient.get(userId);
		this.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.HangUp));
	}

	/**
	 * 发送信息
	 * @param session
	 */
	@Transactional(rollbackFor = Exception.class)
	public void sendMessage(Session session,WebSocketData webSocketData) throws Exception{
		try {
			JSONObject json = JSONObject.fromObject(webSocketData);
			session.getBasicRemote().sendText(json.toString());
		}catch (Exception e){
//			logger.error( webSocketData.getData() + " 发送数据：" + webSocketData.getWant().getName() + " 失败，可能由于网页刷新导致旧的session失败：" + e.getMessage());
//			throw new Exception("发送信息失败，session失效");
		}
	}

	/**
	 * 通知管理员未开启sip服务器
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void sendHintFreeSwitchOff() throws Exception{
		this.sendHintToManager(WebSocketData.WebSocketWant.HintFreeSwitchOff);
	}

	@Transactional(rollbackFor = Exception.class)
	public void sendHintToManager(WebSocketData.WebSocketWant want) throws Exception{
		try {
			UserCondition userCondition = new UserCondition();
			userCondition.setDel(false);
			List<String> roleList = new ArrayList<>();
			roleList.add(QingpuConstants.Roles_Super_id);
			roleList.add(QingpuConstants.Roles_Manager_id);
			userCondition.setRoleIdList(roleList);
			userCondition.setOnline(true);
			List<User> userList = userService.list(userCondition);
			for(User user : userList){
				String userId = user.getId() + "";
				if(WebSocketService.onLineClient.containsKey(userId)){
					Session session = WebSocketService.onLineClient.get(userId);
					this.sendMessage(session, new WebSocketData(want));
				}
			}
		}catch (Exception e){
			logger.error("通知管理员‘freeswitch未开启’失败："  + e.getMessage());
		}
	}

	/**
	 * 设置用户未登录
	 * @param userId
	 */
	public void setUserNologin(Integer userId){
		User user = userService.get(userId);
		user.setIsLogin(false);
		userService.updateUser(user);
	}

	/**
	 * 设置用户是否在线
	 * @param isOnline
	 * @param userId
	 */
	public void setIsOnline(Boolean isOnline, Integer userId){
		try{
			userService.setIsOnline(isOnline, userId);
		}catch (Exception e){
			logger.error("设置用户在线状态失败：" + e.getMessage());
		}
	}


	public void getJianghuBaseInfo(Session session){
		try{
			Map<String, Object> map = jiangHuRenService.getBaseInfo();
			this.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.GetJianghuBaseInfo, map));
		}catch (Exception e){
			logger.error("发送江湖基础信息：" + e.getMessage());
		}
	}


	public void getJianghuChangeInfo(Session session){
		try{
			Map<String, Object> map = jiangHuRenService.getChangeInfo();
			this.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.GetJianghuChangeInfo, map));
		}catch (Exception e){
			logger.error("发送江湖基础信息：" + e.getMessage());
		}
	}

	/**
	 * 用户是否在线，发送给前端
	 * @param isOnline
	 * @param session
	 */
	@Transactional(rollbackFor = Exception.class)
	public void sendOnline(Boolean isOnline, Session session) throws Exception{
		WebSocketData.WebSocketWant want = WebSocketData.WebSocketWant.Offline;
		if(isOnline){
			want = WebSocketData.WebSocketWant.Online;
		}
		this.sendMessage(session, new WebSocketData(want));
	}

	/**
	 * 检测是否有抽奖次数
	 * @param userId
	 */
	public void checkUserDayDrawCount(Integer userId){
		try{
			User user = userService.get(userId);
			if(user != null ){
				String roleId = user.getRoleId();
				UserInfo userInfo = userInfoService.getByName(user.getName());
				if(userInfo != null ){
					if( ! userInfo.getIsGoldenEgg()
							&& ( ! roleId.equals(QingpuConstants.Roles_Manager_id) &&  ! roleId.equals(QingpuConstants.Roles_Super_id) )){
						Session session = WebSocketService.onLineClient.get(userId + "");
						if(session != null){
							WebSocketData.WebSocketWant want = WebSocketData.WebSocketWant.ShowGoldenEgg;
							this.sendMessage(session, new WebSocketData(want));
						}
					}
					if( userInfo.getDayDrawCount() > 0){
						this.showDraw(userId);
					}
				}
			}
		}catch (Exception e){
			logger.error("显示抽奖信息失败：" + e.getMessage());
		}
	}


	public void checkUserGoldenEgg(Integer userId){
		try{
			User user = userService.get(userId);
			if(user != null ){
				UserInfo userInfo = userInfoService.getByName(user.getName());
				if(userInfo != null && userInfo.getDayDrawCount() > 0){
					this.showDraw(userId);
				}
			}
		}catch (Exception e){
			logger.error("显示抽奖信息失败：" + e.getMessage());
		}
	}

	/**
	 * 显示抽奖按钮
	 * @param userId
	 * @throws Exception
	 */
	public void showDraw(Integer userId) {
		try{
			Boolean isHaveLuckDraw = luckDrawService.isHaveLuckDraw();
			if(isHaveLuckDraw){
				Session session = WebSocketService.onLineClient.get(userId + "");
				if(session != null){
					WebSocketData.WebSocketWant want = WebSocketData.WebSocketWant.ShowDraw;
					this.sendMessage(session, new WebSocketData(want));
				}
			}
		}catch (Exception e){
			logger.error("推送显示抽奖按钮失败：" + e.getMessage());
		}
	}



	/**
	 * 通知词条信息
	 */
	public void showHint(String content, WebSocketData.WebSocketWant want){
		try{
			UserCondition userCondition = new UserCondition();
			userCondition.setDel(false);
			List<User> userList = userService.list(userCondition);
			for(User item : userList){
				String userId = item.getId() + "";
				if(WebSocketService.onLineClient.containsKey(userId)){
					Session session = WebSocketService.onLineClient.get(userId);
					this.sendMessage(session, new WebSocketData(want, content));
				}
			}
		}catch (Exception e){
			logger.info("通知词条信息失败： "  +e.getMessage());
		}
	}


	/**
	 * 显示抽奖
	 * @throws Exception
	 */
	public void showLuckDrawHint(LuckDrawRecord luckDrawRecord){
		try{
			String content = "恭喜 " + luckDrawRecord.getUserName() + " 抽中 " + luckDrawRecord.getName();
			UserCondition userCondition = new UserCondition();
			userCondition.setDel(false);
			userCondition.setOnline(true);
			List<User> userList = userService.list(userCondition);
			WebSocketData.WebSocketWant want = WebSocketData.WebSocketWant.ShowLuckDrawHint;
			for(User item : userList){
				String userId = item.getId() + "";
				if(WebSocketService.onLineClient.containsKey(userId)){
					Session session = WebSocketService.onLineClient.get(userId);
					this.sendMessage(session, new WebSocketData(want, content));
				}
			}
		}catch (Exception e){
			logger.info("通知抽奖信息失败： "  +e.getMessage());
		}
	}
}
