package com.qingpu.phone.common.func.webSocket;

import com.qingpu.phone.common.utils.JsonUtil;
import com.qingpu.phone.user.service.JiangHuRenService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用websocket与PC用户连接的功能类
 * */
@ServerEndpoint("/websocket")
public class WebSocketController {
    private static Logger logger = Logger.getLogger(WebSocketController.class);

    WebSocketService webSocketService;

    JiangHuRenService jiangHuRenService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
    private static int onlineCount = 0;

    //存储客户端连接的Map
    private static Map<String, WebSocketController> webSocketMap = new HashMap<String, WebSocketController>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private Integer uuid;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        webSocketService = (WebSocketService) ContextLoader.getCurrentWebApplicationContext().getBean("webSocketService");
        jiangHuRenService =  ContextLoader.getCurrentWebApplicationContext().getBean(JiangHuRenService.class);
        this.session = session;
        try {
            webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.RegisterReturn));
        }catch (Exception e){
            logger.error("客户连接时，发送信息失败：" + e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法,客户端页面刷新或者关闭都会调用此方法
     */
    @OnClose
    public void onClose(){
        if(  WebSocketService.loginOutSession.contains(session)){
            WebSocketService.loginOutSession.remove(session);
        }else{
            if(this.uuid != null){
                webSocketService.setUserNologin(this.uuid);
                webSocketService.setIsOnline(false, this.uuid);
            }
        }
        if(WebSocketService.cocosOnlineSession.contains(session)){
            WebSocketService.cocosOnlineSession.remove(session);
        }
    }



    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject jsonObject= JSONObject.fromObject(message);
        WebSocketData.WebSocketWant want = this.getWebSocketWantByStr(jsonObject.getString("want"));
        if(want != null){
            String data = jsonObject.getString("data");
            Map<String,String> map = JsonUtil.jsonToMap(data);
            String userId = map.get("userId");
            if( ! userId.equals("cocosPhone")){
                this.uuid = Integer.parseInt(userId);
                try{
                    switch (want){
                        case SendUserId:
                            Map<String, Session> sessionMap = WebSocketService.onLineClient;
                            if( sessionMap.containsKey(userId)){
                                Session oldSession = sessionMap.get(userId);
                                webSocketService.sendMessage(oldSession, new WebSocketData(WebSocketData.WebSocketWant.SendOffline));
                                if( ! WebSocketService.loginOutSession.contains(oldSession)){
                                    WebSocketService.loginOutSession.add(oldSession);
                                }
                            }
                            sessionMap.put(userId + "", session);
                            webSocketService.setIsOnline(true, this.uuid);
                            webSocketService.sendOnline(true, session);

                            // 检测是否有抽奖及砸金蛋的机会
                            webSocketService.checkUserDayDrawCount(this.uuid);
                            break;
                        case UserSendOffLine:
                            webSocketService.setIsOnline(false, this.uuid);
                            break;
                        case UserSendOnLine:
                            webSocketService.setIsOnline(true, this.uuid);
                            break;
                    }
                }catch (Exception e){
                    logger.info("处理webSocket信息失败：" + e.getMessage());
                }
            }else{
                try{
                    switch (want){
                        case IsCocos:
                            WebSocketService.cocosOnlineSession.add(session);
                            break;
                        case GetJianghuBaseInfo:
                            webSocketService.getJianghuBaseInfo(session);
                            break;
                        case GetJianghuChangeInfo:
                            webSocketService.getJianghuChangeInfo(session);
                            break;
                        case GetJianghuRefreshInfo:
                            jiangHuRenService.getJianghuRefreshInfo(session);
                            break;
                        case GetStatisticsInfo:
                            jiangHuRenService.getStatisticsInfo(session, false);
                            break;
                    }
                }catch (Exception e){
                    logger.info("处理webSocket信息失败：" + e.getMessage());
                }
            }
        }else{
            logger.error("webSocket收到异常数据：" + message);
        }
    }


    private WebSocketData.WebSocketWant getWebSocketWantByStr(String webSocketStr){
        if(StringUtils.isBlank(webSocketStr)){
            return null;
        }
        for(WebSocketData.WebSocketWant webSocketWant : WebSocketData.WebSocketWant.values()){
            if(webSocketWant.getType().equalsIgnoreCase(webSocketStr)){
                return webSocketWant;
            }
        }
        return null;
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

}
