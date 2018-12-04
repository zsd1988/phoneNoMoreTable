package com.qingpu.phone.systemManager.phoneSocket;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.user.entity.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 用户主动拨打电话
 */
public class UserPhoneSocket extends Socket {
    private static Logger logger = Logger.getLogger(UserPhoneSocket.class);
    private static final Logger login = Logger.getLogger("login");

    private GroupCallDetail groupCallDetail;
    private String groupCallId;
    private String clientId;
    private String phone;

    private OutputStream outputStream;
    private UserPhoneGetMessageThread userPhoneGetMessageThread;

    private CallRecord callRecord;
    CallRecordService callRecordService;

    private Port port;
    private String portId;
    private Boolean isAgain = false;
    private String toCSText = null;

    Integer count = 0;
    Date startTime; // 开始通话时间
    Date endTime;   // 结束通话时间

    // 转接的客服
    private User user = null;

    public UserPhoneSocket(String host, int portNum, CallRecord callRecord, CallRecordService callRecordService, Port port)
            throws UnknownHostException, IOException{
        super(host, portNum);
        this.callRecord = callRecord;
        this.callRecordService = callRecordService;
        clientId = callRecord.getClientId();
        phone = callRecord.getPhone();

        this.port = port;
        portId = port.getId();

        this.outputStream = this.getOutputStream();
        userPhoneGetMessageThread = new UserPhoneGetMessageThread(this);
        userPhoneGetMessageThread.start();
    }

    /**
     * 呼叫号码
     */
    public void startCall(){
        String message = "{\"customer_num\":\"";
        message = CallPhoneListener.handlePort(message, portId, phone, port.getIsThird());
        message += "\",\"agent_num\":\"" + callRecord.getExtNum() + "\"}";
        this.sendMessage(message);
        if( ! CallPhoneListener._currentPhoneList.contains(phone)){
            CallPhoneListener._currentPhoneList.add(phone);
        }
        CallPhoneListener._idlePortMap.remove(portId);
        if(callRecord != null){
            startTime = new Date();
            callRecord.setStartTime(startTime);
        }
    }

    public void sendMessage(String message){
        try {
            if(this.outputStream != null){
                logger.info(phone + " client群呼发送信息：" + message);
                this.outputStream.write(message.getBytes());
                this.outputStream.flush();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 收到消息
     * @param message
     * @throws Exception
     */
    public void receiveMessage(String message) throws Exception{
        logger.info( phone + " client收到freeSwitch数据：" + message);
        JSONObject jsonObject= JSONObject.fromObject(message);
        if(jsonObject.has("callstatus")){
            handleCallStatus(jsonObject);
        }else{
            logger.error(phone + "client收到未知群呼信息：" + message);
        }
    }


    /**
     * 处理通话结果
     * @param jsonObject
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handleCallStatus(JSONObject jsonObject) throws Exception{
        String status = jsonObject.getString("callstatus");
        if(status.equals("CALL-ANSWERED-ROBOT")){
//                    机器人拨打的时候用户应答了
            if(callRecord != null){
                startTime = new Date();
                callRecord.setStartTime(startTime);
            }
        }else{
            switch (status){
                case "CALL-USERBUSY":
//                    占线
                    break;
                case "CALL-NOANSWER":
//                    未接听
                    break;
                case "CALL-EMPTYNUM":
//                    空号
                    break;
                case "CALL-REJECT":
//                    拒绝接听挂断
                    break;
                case "CALL-ANSWERED-AGENT":
//                    转接到了人工客服
                    break;
                case "CALL-COMPLETE-ROBOT":
//                    用户跟机器人说完之后没有意向挂断了
                    this.finishCall(jsonObject);
                    break;
                case "CALL-COMPLETE-AGENT":
//                    与人工客服通话完毕
                    this.finishCall(jsonObject);
                    break;
                default:
                    logger.info(phone + " client收到未知通话结果：" + status);
            }
            this.closeSocket();
        }
    }

    /**
     * 通话结束操作通话记录
     * @param jsonObject
     */
    private void finishCall(JSONObject jsonObject){
        endTime = new Date();
        int time = (int)DateUtil.secondBetween(startTime, endTime);
        if(callRecord != null){
            if(jsonObject.has("file_url")){
                callRecord.setRecordPath(jsonObject.getString("file_url"));
            }
            if(jsonObject.has("file_url_a_leg")){
                callRecord.setClientRecordPath(jsonObject.getString("file_url_a_leg"));
            }
            callRecord.setEndTime(endTime);
            callRecord.setTime(time);
        }
    }

    public void closeSocket(){
        try {
            if(callRecordService != null){
                if(callRecord != null && StringUtils.isNotBlank(callRecord.getUserName()) &&
                        callRecord.getTime() > 0 && StringUtils.isNotBlank(callRecord.getRecordPath())){
                    callRecordService.create(callRecord);
                }
                if( ! CallPhoneListener._idlePortIdList.contains(portId)){
                    CallPhoneListener._idlePortMap.put(portId, port);
                    if( ! port.getIsThird()){
                        CallPhoneListener._portIdEndTimeMap.put(portId, new Date());
                    }
                    CallPhoneListener._idlePortIdList.add(portId);
                }
            }
            if( CallPhoneListener._currentPhoneList.contains(phone)){
                CallPhoneListener._currentPhoneList.remove(phone);
            }
            logger.info(phone + " 。。。。。。。。。。。关闭回拨线程： " + portId);
            this.getInputStream().close();
            userPhoneGetMessageThread.isRun = false;
            userPhoneGetMessageThread.stop();
            userPhoneGetMessageThread.inputStream = null;
            userPhoneGetMessageThread = null;
            outputStream.close();
            this.close();
        }catch (Exception e){
            logger.info(phone + " client关闭socket出错：" + e.getMessage());
        }
    }
}
