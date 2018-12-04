package com.qingpu.phone.systemManager.phoneSocket;

import com.qingpu.phone.common.func.webSocket.WebSocketData;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemManager.service.GroupCallDetailService;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.systemSetting.service.PortService;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;

public class CallPhoneThread extends Thread {
    private static Logger logger = Logger.getLogger(CallPhoneThread.class);
    private PortService portService;
    private GroupCallDetailService groupCallDetailService;
    private CallRecordService callRecordService;
    private WebSocketService webSocketService;
    private UserService userService;
    public boolean isRun = true;
    private GroupCallDetail lastGroupCallDetail;     // 最后获取群呼明细


    public CallPhoneThread(PortService portService, GroupCallDetailService groupCallDetailService, CallRecordService callRecordService,
                           WebSocketService webSocketService, UserService userService){
        this.portService = portService;
        this.groupCallDetailService = groupCallDetailService;
        this.callRecordService = callRecordService;
        this.webSocketService = webSocketService;
        this.userService = userService;
    }

    @Override
    public void run(){
        while ( isRun ){
            if(CallPhoneListener._doCount >= 10000){
                CallPhoneListener._doCount = 0;
//                logger.info("执行");
            }
            CallPhoneListener._doCount++;
            String portId = null;
            try {
                // 判断是否有回拨的电话，优先回拨电话
                if(CallPhoneListener._waitingCaller.size() > 0){
                    CallRecord callRecord = CallPhoneListener._waitingCaller.get(0);
                    String getPortId = portService.getIdlePortId(true, callRecord.getPhone());
                    if(getPortId != null){
                        try{
                            callRecordService.startCall(callRecord, getPortId);
                        }catch (Exception e){
                            logger.error(callRecord.getPhone() + " " + e.getMessage());
                        }
                    }
                }

                GroupCallDetail groupCallDetail;
                if(lastGroupCallDetail != null){
                    groupCallDetail = lastGroupCallDetail;
                }else{
                    groupCallDetail = groupCallDetailService.getCallPhone();
                    lastGroupCallDetail = groupCallDetail;
                }
                if(groupCallDetail != null){
                    portId = portService.getIdlePortId(false, groupCallDetail.getPhone());
                    if(portId != null) {
                        Port port = CallPhoneListener._idlePortMap.get(portId);
                        if(port != null){
                            new PhoneSocket(CallPhoneListener._socketHost, CallPhoneListener._socketPort, groupCallDetail, this, port);
                            lastGroupCallDetail = null;
                        }
                    }
                }
            }catch (Exception e){
                if(portId != null){
                    CallPhoneListener._idlePortIdList.add(portId);
                }
                String message = e.getMessage();
                if(StringUtils.isNotBlank(message)){
                    if(message.contains("Connection refused") || message.contains("Connection timed out")){
                        if(CallPhoneListener._doCount == 1){
                            System.out.println("freeswitch服务未开启" + message);
                            try {
                                webSocketService.sendHintFreeSwitchOff();
                            }catch (Exception ex){
                                logger.error("通知管理员sip服务器未开启失败");
                            }
                        }
                    }else{
                        lastGroupCallDetail = null;
                        logger.error("调用端口拨打电话失败：" + message);
                    }
                }
            }
        }
    }

    /**
     * 获取群呼设置空闲客服
     * @param groupCallId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public  User getIdleUserId(String groupCallId) throws Exception{
        return userService.getIdleUserId(groupCallId);
    }

    /**
     * 发送消息给管理员
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public  void sendHintToManager(WebSocketData.WebSocketWant want) throws Exception{
        webSocketService.sendHintToManager(want);
    }

    /**
     * 设置端口空闲
     * @param portId
     */
    public void portSetFinish(String portId, String phone){
        portService.setFinish(portId, phone);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendShowClient(String userId, String clientId, String text) throws Exception{
        webSocketService.sendShowClient(userId, clientId, text);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendCallEnd(String userId) throws Exception{
        webSocketService.sendCallEnd(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateGroupDetail(GroupCallDetail groupCallDetail) throws Exception{
        groupCallDetailService.update(groupCallDetail);
    }

    public void createCallRecord(CallRecord callRecord) throws Exception{
        callRecordService.create(callRecord);
    }

    public void startPhoneClient(String userId) throws Exception{
        Session session = WebSocketService.onLineClient.get(userId);
        webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.StartPhoneClient));
    }
}
