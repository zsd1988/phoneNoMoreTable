package com.qingpu.phone.systemManager.phoneSocket;

import com.qingpu.phone.common.func.webSocket.WebSocketService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * 播放录音文件
 */
public class TempSocket extends Socket {
    private static Logger logger = Logger.getLogger(TempSocket.class);
    private  OutputStream outputStream;
    private TempThread tempThread;
    private WebSocketService webSocketService;
    private Integer userId;
    private String extNum;

    public TempSocket(WebSocketService webSocketService) throws IOException{
        super(CallPhoneListener._socketHost, CallPhoneListener._socketPort);
        this.outputStream = this.getOutputStream();
        tempThread = new TempThread(this);
        tempThread.start();
        this.webSocketService = webSocketService;
    }

    /**
     * 指定分机号播放录音
     * @param extNum
     * @param recordPath
     */
    public void playRecord(String extNum, String recordPath, Integer userId){
        this.userId = userId;
        this.extNum = extNum;
        CallPhoneListener._extNumTempSocketMap.put(extNum, this);
        String message = "{\"agent_num\":\"" + extNum + "\",\"record_path\":\"" + recordPath + "\"}";
        this.sendMessage(message);
    }

    /**
     * 收到消息
     * @param message
     * @throws Exception
     */
    public void receiveMessage(String message) throws Exception{
        logger.info( "临时socket收到freeSwitch数据：" + message);
        JSONObject jsonObject= JSONObject.fromObject(message);
        if(jsonObject.has("callstatus")){
            handleCallStatus(jsonObject);
        }else{
            logger.error("临时socke收到未知群呼信息：" + message);
        }
    }


    /**
     * 处理通话结果
     * @param jsonObject
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handleCallStatus(JSONObject jsonObject) throws Exception{
        String status = jsonObject.getString("callstatus");
        if(StringUtils.isNotBlank(status)){
            switch (status){
                case"CALL-NOCOMPLETE-PLAYBACK":
                    if(userId != null){
                        webSocketService.sendHangUp(userId + "");
                    }
                    break;
                case "CALL-COMPLETE-PLAYBACK":
                    break;
            }
        }
        this.closeSocket();
    }


    public void sendMessage(String message){
        try {
            if(this.outputStream != null){
                logger.info("播放录音：" + message);
                this.outputStream.write(message.getBytes());
                this.outputStream.flush();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }


    public void hangupByMyself(){
        logger.info(extNum + " 客服操作手动挂断录音");
        String message = "{\"stopChannel\":\"true\"}";
        this.sendMessage(message);
    }

    public void closeSocket(){
        try {
            this.getInputStream().close();
            tempThread.isRun = false;
            if(CallPhoneListener._extNumTempSocketMap.containsKey(extNum)) {
                CallPhoneListener._extNumTempSocketMap.remove(extNum);
            }
            tempThread.stop();
            tempThread.inputStream = null;
            tempThread = null;
            outputStream.close();
            this.close();
        }catch (Exception e){
            logger.info("播放录音socket出错：" + e.getMessage());
        }
    }
}
