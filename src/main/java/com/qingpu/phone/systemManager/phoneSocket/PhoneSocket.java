package com.qingpu.phone.systemManager.phoneSocket;

import com.qingpu.phone.common.func.webSocket.WebSocketData;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.user.entity.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.aspectj.weaver.ast.Call;
import org.springframework.transaction.annotation.Transactional;
import server.info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PhoneSocket extends Socket {
    private static Logger logger = Logger.getLogger(PhoneSocket.class);
    private static final Logger login = Logger.getLogger("login");

    private GroupCallDetail groupCallDetail;
    public String groupCallId;
    private String clientId;
    public String phone;

    private OutputStream outputStream;
    private PhoneGetMessageThread phoneGetMessageThread;
    private CallPhoneThread callPhoneThread;

    private CallRecord callRecord;

    private Port port;
    private String portId;
    private Boolean isAgain = false;
    private String toCSText = null;
    PublicEnum.VoiceType voiceType = null;
    String similarityStr;
    String similarityFloat;
    String resolveStr = "";
    Boolean isWaiting = true;   // 是否等待转人工

    // 开场白相关字段
    String path1;
    String path2;
    String path3;
    String prologueName;
    Integer refuseCount = 0;

    Date startTime; // 开始通话时间
    Date endTime;   // 结束通话时间
    Timer shutDownTimer = new Timer();  // 三十分钟后，超时自动关闭socket

    WaitingThread waitingThread = null; // 等待转人工线程
    final Object waitingThreadSynObject = new Object(); // 同步锁对象，防止socket关闭之后还会转人工的现象

    // 转接的客服
    private User user = null;

    public PhoneSocket(String host, int portNum, GroupCallDetail groupCallDetail, CallPhoneThread callPhoneThread, Port port)
            throws Exception, IOException{
        super(host, portNum);
        this.callPhoneThread = callPhoneThread;

        this.groupCallDetail = groupCallDetail;
        if(groupCallDetail != null){
            groupCallId = groupCallDetail.getGroupCallId();
            clientId = groupCallDetail.getClientId();
            CallPhoneListener._clientIdPhoneMap.put(clientId, this);
            phone = groupCallDetail.getPhone();
        }

        this.port = port;
        portId = port.getId();
        CallPhoneListener._idlePortMap.remove(portId);
        // 加入群呼占用端口map
        List<String> portList = CallPhoneListener._groupCallPortIdMap.get(groupCallId);
        if(portList == null){
            portList = new ArrayList<>();
        }
        if( ! portList.contains(portId)){
            portList.add(portId);
            if(portList.contains(null)){
                portList.remove(null);
            }
            CallPhoneListener._groupCallPortIdMap.put(groupCallId, portList);
        }
        if( ! CallPhoneListener._incallingPort.containsKey(portId)){
            CallPhoneListener._incallingPort.put(portId, port);
        }

        this.outputStream = this.getOutputStream();
        phoneGetMessageThread = new PhoneGetMessageThread(this);
        logger.info(phone + " 。。。。。。。。。。。线程名字：" + phoneGetMessageThread.getName());
        phoneGetMessageThread.start();

        final PhoneSocket phoneSocketFinal = this;
        shutDownTimer.schedule(new TimerTask(){
            public void run(){
                logger.info(phoneSocketFinal.phone + " 超时关闭socket");
                phoneSocketFinal.groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.ResetWaiting);
                phoneSocketFinal.closeSocket(false);
            }
        },1800000);
        this.startCall();
        //拨打电话数量加1
        if(CallPhoneListener._callPhoneNum.containsKey(groupCallId)){
            CallPhoneListener._callPhoneNum.put(groupCallId, CallPhoneListener._callPhoneNum.get(groupCallId) + 1);
        }
    }


    /**
     * 呼叫号码
     */
    @Transactional(rollbackFor = Exception.class)
    public void startCall() throws Exception{
        this.voiceType = CallPhoneListener._callVoiceType.get(groupCallId);
        if(voiceType == null){
            CallPhoneListener._callPhoneNum.remove(groupCallId);
            throw new Exception(" " + phone + " 群呼任务已停止 " + groupCallId);
        }
        CallPhoneListener._currentPhoneList.add(phone);
        groupCallDetail.setPortId(portId);
        groupCallDetail.setStartTime(new Date());
        groupCallDetail.setVoiceType(voiceType);
        groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Calling);


        // 获取开场白
        String prologueId = null;
        Map<String, Double> prologueRateMap = CallPhoneListener._groupCallPrologueMap.get(groupCallId);
        Integer totalCount = 0;
        for(String key : prologueRateMap.keySet()){
            if(prologueId == null){
                prologueId = key;
            }
            List<Object> objectList = CallPhoneListener._prologueMap.get(key);
            Integer count = (Integer)objectList.get(4);
            totalCount += count;
        }
        if(totalCount > 0){
            for(String key : prologueRateMap.keySet()){
                List<Object> objectList = CallPhoneListener._prologueMap.get(key);
                Integer count = (Integer)objectList.get(4);
                Double rate = prologueRateMap.get(key);
                if((count + 0.0)/totalCount < rate){
                    prologueId = key;
                    break;
                }
            }
        }
        List<Object> objectList = CallPhoneListener._prologueMap.get(prologueId);
        if(objectList != null){
            path1 = QingpuConstants.uploadUrl + objectList.get(0).toString();
            path2 = QingpuConstants.uploadUrl + objectList.get(1).toString();
            path3 = QingpuConstants.uploadUrl + objectList.get(2).toString();
            prologueName = objectList.get(3).toString();
//            groupCallDetail.setPrologueName(prologueName);
            Integer count = (Integer)objectList.get(4);
            count++;
            objectList.remove(4);
            objectList.add(4, count);
        }
        callPhoneThread.updateGroupDetail(groupCallDetail);

        port.setCalled(phone);
        String message = "{\"callnum\":\"";
        message = CallPhoneListener.handlePort(message, portId, phone, port.getIsThird());
        String project = "g98";
        if(groupCallDetail.getProjectId().equals("e8513e30c49340fbb1f9a8dcc3000e0c")){
            project = "g51";
        }
        message += "\",\"voicetype\":\"" + voiceType.getType() + "\",\"project\":\""+ project +"\"}";
//        message += "\",\"voicetype\":\"" + voiceType.getType() + "\" ,\"path\":\"" + path1+ "\"}";
        this.sendMessage(message);
    }

    public void sendMessage(String message){
        try {
            if(this.outputStream != null){
                logger.info(phone + " 群呼发送信息：" + message);
                this.outputStream.write(message.getBytes());
                this.outputStream.flush();
            }
        }catch (Exception e){
            logger.error(phone + " 发送消息失败：" + e.getMessage());
        }
    }

    /**
     * 收到消息
     * @param message
     * @throws Exception
     */
    public void receiveMessage(String message) throws Exception{
        logger.info( phone + " 收到freeSwitch数据：" + message);
        JSONObject jsonObject= JSONObject.fromObject(message);
        if(jsonObject.has("asr_result")){
            handleText(jsonObject.getString("asr_result"));
        }else if(jsonObject.has("callstatus")){
            handleCallStatus(jsonObject);
        }else{
            logger.error(phone + "收到未知群呼信息：" + message);
        }
    }

    /**
     * 处理文本
     * @param source_str
     */
    @Transactional(rollbackFor = Exception.class)
    protected void  handleText(String source_str)throws XmlRpcException,Exception {
//        python服务器调用例子
//        try{
//            String answer = (String) CallPhoneListener._xmlRpcClient.execute("get_hello",params);
//            if("ok".equals(answer)){
//                this.toCS(text);
//            }else{
//                String message = "{\"playsoundfile\":\"" + "/home/sip/0003-go.wav" + "\",\"next_apps\":\"park\"}";
//                switch (answer){
//                    case "again":
//                        isAgain = true;
//                        break;
//                    case "bye":
//                        message = "{\"playsoundfile\":\"" + "/home/sip/0002-bye.wav" + "\",\"next_apps\":\"hangup\"}";
//                        break;
//                }
//                this.sendMessage(message);
//            }
//        }catch (XmlRpcException e){
//            logger.error("调用python失败：" + e.getMessage());
//        }

        if( ! isWaiting){
            // 已经转人工了，不再接受解析
            return;
        }

        String ret_str = null;
        String final_str = null;
        double max = 0.0;
        NormalizedLevenshtein ngram = new NormalizedLevenshtein();
        double ret = 0.0, refuse_max_ret = 0.0,yes_max_ret = 0.0;
        //1.遍历客户否定问答文件
        String resolveRefuse = "";
        for (String refuse : CallPhoneListener.cus_refuse_list) {
            ret = 1-ngram.distance(refuse, source_str);
            if(ret > refuse_max_ret){
                refuse_max_ret = ret;
                resolveRefuse = refuse;
            }
        }
        //3.遍历肯定的文件
        String resolveYes = "";
        for (String yes :  CallPhoneListener.cus_yes_list) {
            ret = 1-ngram.distance(yes, source_str);
            if(ret > yes_max_ret){
                yes_max_ret = ret;
                resolveYes = yes;
            }
        }
        //1.初始化--拒绝
        String resolve = "";
        max = refuse_max_ret;
        ret_str = "refuse";
        //3.客户肯定回答
        if(yes_max_ret >= max){
            max = yes_max_ret;
            resolve = resolveYes;
            ret_str = "yes";
        }else{
            resolve = resolveRefuse;
        }
        //如果最大值太小则设置为none
        if(max < CallPhoneListener._transNoneRate){
            ret_str = "none";
            resolve = "";
        }
        if(ret_str.equals("refuse")){
            refuseCount++;
        }
        String path = path2;
        if(refuseCount == 2){
            path = path3;
        }
        final_str = "{\"ret_str\":\""+ret_str+"\",\"max\":"+max+"}";
//        final_str = "{\"ret_str\":\""+ret_str+"\",\"max\":"+max+" ,\"path\":\"" + path+ "\"}";

        this.sendMessage(final_str);
        max = ArithUtil.round(max, 4);
        if(StringUtils.isNotBlank(this.toCSText)){
            this.toCSText += " / " + source_str;
            this.similarityFloat += " / " + max;
            this.similarityStr += " / " + ret_str;
            this.resolveStr += " / " + resolve;
        }else{
            this.toCSText = source_str;
            this.similarityFloat = max + "";
            this.similarityStr = ret_str;
            this.resolveStr = resolve;
        }
        groupCallDetail.setSimilarityFloat(this.similarityFloat);
        groupCallDetail.setSimilarityStr(this.similarityStr);
        groupCallDetail.setFirstText(this.toCSText);
        groupCallDetail.setResolveStr(this.resolveStr);
        groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Success);
        callPhoneThread.updateGroupDetail(groupCallDetail);
    }

    /**
     * 转客服
     */
    @Transactional(rollbackFor = Exception.class)
    protected void toCS() throws Exception{
        User user = callPhoneThread.getIdleUserId(this.groupCallId);
        if(user != null){
            isWaiting = false;
            this.sendToCSMessage(user);
        }else{
            logger.info(phone + " 等待转人工");
            waitingThread = new WaitingThread(callPhoneThread, groupCallId, this);
            waitingThread.start();
        }
    }



    /**
     * 等待转人工线程
     */
    class WaitingThread extends Thread{
        public Boolean isRun = true;
        CallPhoneThread _callPhoneThread;
        String _groupCallId;
        PhoneSocket _phoneSocket;
        Integer  waitingCount = 0;

        private WaitingThread(CallPhoneThread callPhoneThread, String groupCallId, PhoneSocket phoneSocket){
            _callPhoneThread = callPhoneThread;
            _groupCallId = groupCallId;
            _phoneSocket = phoneSocket;
        }

        private void getUser(){
            synchronized (waitingThreadSynObject){
                if(isRun){
                    try {
                        User user = _callPhoneThread.getIdleUserId(_groupCallId);
                        if(user != null){
                            isRun = false;
                            _phoneSocket.sendToCSMessage(user);
                        }else{
                            waitingCount++;
                            Thread.currentThread().sleep(200);//毫秒
                            if(waitingCount > 280){
                                isRun = false;
                            }
                            getUser();
                        }
                    }catch (Exception e){
                        logger.info("转人工出错：" + e.getMessage());
                    }
                }
            }
        }

        public void run(){
            getUser();
        }
    }

    /**
     * 发送转客服消息
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendToCSMessage(User user) throws Exception{
        Boolean isSuccess = false;
        String errorStr = null;
        try {
            this.user = user;
            String userId = "" +  user.getId();
            callRecord.setUserId(userId);
            callRecord.setWorkNumber(user.getWorkNumber());
            callRecord.setUserName(user.getName());
            Integer extNum = user.getExtNum();
            callRecord.setExtNum(extNum);
            callRecord.setWorkType(user.getWorkType());
            String message = "{\"transport\":\"" + extNum + "\" }";
            if(this.outputStream != null){
                this.outputStream.write(message.getBytes());
                this.outputStream.flush();
                groupCallDetail.setExtNum(user.getExtNum());
                // 加入通话中的列表，然后加入并发数 = 通话中 + 呼叫中
                if( ! CallPhoneListener._groupCallCallingPhoneMap.containsKey(groupCallId)){
                    CallPhoneListener._groupCallCallingPhoneMap.put(groupCallId, new ArrayList<String>());
                }
                List<String> phoneList = CallPhoneListener._groupCallCallingPhoneMap.get(groupCallId);
                if( ! phoneList.contains(phone)){
                    phoneList.add(phone);
                }
                callPhoneThread.sendShowClient(userId, clientId, this.toCSText);
                logger.info(phone + " 群呼发送信息：" + message +  "  "  +  user.getWorkNumber());
                isSuccess = true;
            }
        }catch (Exception e){
            errorStr = phone + " 转人工失败：" + e.getMessage();
            logger.error(errorStr);
        }
        if( ! isSuccess){
            groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.TransFail);
            if(errorStr != null){
                this.closeSocket(true);
//                throw new Exception(errorStr);
            }
        }
        return isSuccess;
    }

    /**
     * 处理客户接电话
     * @param jsonObject
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handleAnswer(JSONObject jsonObject) throws Exception{
        if(jsonObject.containsKey("agent_list")){
            List<Integer> extNumList = new ArrayList<>();
            String agentListStr = jsonObject.getString("agent_list");
            agentListStr = agentListStr.substring(1, agentListStr.length() -1);
            if(agentListStr.length() > 0){
                agentListStr = agentListStr.replace("\"", "");
                String[] agentArr = agentListStr.split(",");
                for(String extNum : agentArr){
                    Integer integer = Integer.parseInt(extNum);
                    extNumList.add(integer);
                }
                CallPhoneListener._onlineExtNum = extNumList;
            }
        }
        if(callRecord == null){
            callRecord  = new CallRecord();
            this.toCS();
            startTime = new Date();
            callRecord.setStartTime(startTime);
            callRecord.setPhone(groupCallDetail.getPhone());
            callRecord.setProjectId(groupCallDetail.getProjectId());
            callRecord.setClientId(groupCallDetail.getClientId());
            callRecord.setGroupCallDetailId(groupCallDetail.getId());
            if(jsonObject.containsKey("tmp_record_name")){
                groupCallDetail.setTmpRecordName(jsonObject.getString("tmp_record_name"));
            }
            groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Success);
            callPhoneThread.updateGroupDetail(groupCallDetail);
        }
    }

    /**
     * 处理通话结果
     * @param jsonObject
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handleCallStatus(JSONObject jsonObject) throws Exception{
        String status = jsonObject.getString("callstatus");
        if(jsonObject.containsKey("speaker_name")){
            groupCallDetail.setPrologueName(jsonObject.getString("speaker_name"));
        }
        if(status.equals("CALL-ANSWERED-ROBOT")){
//                    机器人拨打的时候用户应答了
            this.handleAnswer(jsonObject);
        }else{
            isWaiting = false;
            if(waitingThread != null){
                waitingThread.isRun = false;
            }
            WebSocketData.WebSocketWant webSocketWant = null;
            switch (status){
                case "CALL-USERBUSY":
//                    占线
                    groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                    break;
                case "CALL-EMPTYNUM":
//                    空号
                    groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                    break;
                case "CALL-NOANSWER":
//                    未接听
                    Date now = new Date();
                    if(DateUtil.secondBetween(groupCallDetail.getStartTime(), now) < 5){
                        if( port.getIsThird() ){
                            groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                        }else{
                            // 有可能端口占用直接返回了callNoAnswer
                            this.resetWaitingStatus();
                        }
                    }else{
                        groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                    }
                    break;
                case "CALL-REJECT":
//                    拒绝接听挂断
                    groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                    break;
                case "CALL-ANSWERED-AGENT":
//                    转接到了人工客服
                    groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.TransSuccess);
                    break;
                case "CALL-COMPLETE-ROBOT":
//                    用户跟机器人说完之后没有意向挂断了
                    PublicEnum.GroupCallDetailStatus status1 = PublicEnum.GroupCallDetailStatus.TransFail;
                    if(callRecord == null){
                        status1 = PublicEnum.GroupCallDetailStatus.Refuse;
                        startTime = new Date();
                        callRecord  = new CallRecord();
                        callRecord.setStartTime(groupCallDetail.getStartTime());
                        callRecord.setPhone(groupCallDetail.getPhone());
                        callRecord.setProjectId(groupCallDetail.getProjectId());
                        callRecord.setClientId(groupCallDetail.getClientId());
                        callRecord.setGroupCallDetailId(groupCallDetail.getId());
                        callRecord.setWorkNumber("robot");
                    }
                    groupCallDetail.setStatus(status1);
                    this.finishCall(jsonObject);
                    break;
                case "CALL-COMPLETE-REFUSE":
                    //客户拒绝
                    this.finishCall(jsonObject);
                    callRecord = null;
                    groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Refuse);
                    break;
                case "CALL-COMPLETE-AGENT":
//                    与人工客服通话完毕
                    this.finishCall(jsonObject);
                    groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.TransSuccess);
                    break;
                case "NORMAL-TEMPORARY-FAILURE":
//                    特殊的状态,再次拨打一次
                    this.resetWaitingStatus();
                    break;
                case "DESTINATION-OUT-OF-ORDER":
                    webSocketWant = WebSocketData.WebSocketWant.DESTINATION_OUT_OF_ORDER;
                case "UNALLOCATED-NUMBER":
                    if(webSocketWant == null){
                        webSocketWant = WebSocketData.WebSocketWant.UNALLOCATED_NUMBER;
                        if(portId.startsWith("sipprovider")){
                            webSocketWant = WebSocketData.WebSocketWant.Phone_UNALLOCATED_NUMBER;
                        }
                    }
                case "NO-USER-RESPONSE":
                    if(webSocketWant == null){
                        webSocketWant = WebSocketData.WebSocketWant.NO_USER_RESPONSE;
                        if(portId.startsWith("sipprovider")){
                            webSocketWant = WebSocketData.WebSocketWant.Phone_NO_USER_RESPONSE;
                        }
                    }
                    callPhoneThread.sendHintToManager(webSocketWant);
                    this.resetWaitingStatus();
                    break;
                default:
                    logger.info(phone + "收到未知通话结果：" + status);
            }
            groupCallDetail.setEndTime(new Date());
            this.closeSocket(true);
        }
    }

    /**
     * 设置为重复拨打
     */
    private void resetWaitingStatus(){
        Integer count = groupCallDetail.getResetWaitingCount();
        if(count == null){
            count = 1;
        }
        count++;
        if(count > 2){
            groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
        }else{
            groupCallDetail.setResetWaitingCount(count);
            groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Waiting);
        }
    }

    /**
     * 通话结束操作通话记录
     * @param jsonObject
     */
    private void finishCall(JSONObject jsonObject){
        endTime = new Date();
        int time = (int)DateUtil.secondBetween(startTime, endTime);
        if(jsonObject.has("file_url")){
            groupCallDetail.setRefuseRecordPath(jsonObject.getString("file_url"));
        }
        if(jsonObject.has("part_record1")){
            groupCallDetail.setPartRecord1(jsonObject.getString("part_record1"));
        }
        if(jsonObject.has("part_record2")){
            groupCallDetail.setPartRecord2(jsonObject.getString("part_record2"));
        }
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
        groupCallDetail.setTime(time);
    }

    public void hangupByMyself(){
        logger.info(phone + " 客服操作手动挂断");
        String message = "{\"stopChannel\":\"true\"}";
        this.sendMessage(message);
    }

    public void closeSocket(Boolean isNormal){
        try {
            if(waitingThread != null){
                synchronized (waitingThreadSynObject){
                    waitingThread.isRun = false;
                }
//                waitingThread.stop();
            }
            shutDownTimer.cancel();
            isWaiting = false;
            if(user != null){
                Integer userId = user.getId();
                if(CallPhoneListener._inCallingUser.contains(userId)){
                    CallPhoneListener._inCallingUser.remove(userId);
                }
                // 发送通话结束
                callPhoneThread.sendCallEnd(userId + "");
            }
            if(CallPhoneListener._currentPhoneList.contains(phone)){
                CallPhoneListener._currentPhoneList.remove(phone);
            }
            if(callRecord != null && groupCallDetail.getStatus() != PublicEnum.GroupCallDetailStatus.TransFail){
                callRecord.setVoiceType(voiceType);
                callPhoneThread.createCallRecord(callRecord);
            }
            List<String> phoneList = CallPhoneListener._groupCallCallingPhoneMap.get(groupCallId);
            if(phoneList != null && phoneList.contains(phone)){
                phoneList.remove(phone);
            }
            if(! isNormal){
                // 接收数据异常导致挂断
                groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                groupCallDetail.setIsDel(true);
            }
            if(CallPhoneListener._clientIdPhoneMap.containsKey(clientId)){
                CallPhoneListener._clientIdPhoneMap.remove(clientId);
            }
            logger.info(phone +  " 。。。。。。。。。。。更新数据 " );
            callPhoneThread.updateGroupDetail(groupCallDetail);
            logger.info(phone +  " 。。。。。。。。。。。释放端口 " );
            callPhoneThread.portSetFinish(portId, phone);
            logger.info(phone + " 。。。。。。。。。。。关闭线程： " + phoneGetMessageThread.getName() + " " + portId);
            this.getInputStream().close();
            phoneGetMessageThread.isRun = false;
            phoneGetMessageThread.stop();
            phoneGetMessageThread.inputStream = null;
            phoneGetMessageThread = null;
            outputStream.close();
            this.close();
        }catch (Exception e){
            logger.info(phone + " 关闭socket出错：" + e.getMessage());
            callPhoneThread.portSetFinish(portId, phone);
        }
    }
}
