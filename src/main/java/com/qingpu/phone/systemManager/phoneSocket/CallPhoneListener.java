package com.qingpu.phone.systemManager.phoneSocket;

import bsh.This;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemManager.service.GroupCallDetailService;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.systemSetting.service.PortService;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.UserService;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@WebListener
public class CallPhoneListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger(CallPhoneListener.class);

    public static CallPhoneThread callPhoneThread;

    public final static int _headByte = 0x40;
    public final static int _endByte = 0x26;

    // python服务器
    public static String _pythonHost = "192.168.0.166";
    public static Integer _pythonPort = 8000;
    public static XmlRpcClient _xmlRpcClient = null;    //调用python代码

    // freeSwitch服务器
 //    public static String _socketHost = "127.0.0.1";
    public static String _socketHost = "192.168.1.199";
    public static Integer _socketPort = 9999;
//    public static Integer _socketPort = 6666;

    /*
    启动重置
     */
    public static Map<String, List<GroupCallDetail>> _callPhoneListMap = new HashMap<>();		// 将要群呼的电话列表
    public static Map<String, Integer> _callPhoneNum = new HashMap<>();	// 群呼已拨打电话数量，计算出下次拨打哪个群呼的电话，启动任一群呼时，重置为0
    public static Map<String, Double> _callPhoneRate = new HashMap<>();	// 拨打该群呼的比率，根据分配的分机号来定
    public static Map<String, PublicEnum.VoiceType> _callVoiceType = new HashMap<>();  // 群呼任务语音类型
    public static Map<String,String> _groupCallAndProjectMap = new HashMap<>();        // 群呼对应项目
    public static Map<String, String> _groupCallWorkNumMap = new HashMap<>();        // 群呼分机号
    public static Map<String, GroupCallService.StopRunnable> _groupCallStopMap = new HashMap<>();   // 群呼停止线程

    // 群呼并行数控制
    public static Map<String, Integer> _groupCallConNumMap = new HashMap<>();	// 群呼并发数量
    public static Map<String, List<String>> _groupCallPortIdMap = new HashMap<>();        // 群呼端口数量
    public static Map<String, List<String>> _groupCallCallingPhoneMap = new HashMap<>();        // 群呼转客服成功通话中的手机号
    public static Map<String, Map<String, Double>> _groupCallPrologueMap = new HashMap<>();		// 群呼开场白

    public static LinkedList<String> cus_yes_list = new LinkedList<String>();
    public static LinkedList<String> cus_refuse_list = new LinkedList<String>();

    public static Map<String, Port> _idlePortMap = new HashMap<>();     // 端口id map
    public static Map<String, Date> _portIdEndTimeMap = new HashMap<>();     // 端口id挂断时间，第三方端口不加入延时处理
    public static List<String> _idlePortIdList = new ArrayList<>();         // 可拨打电话的空闲端口idList用于端口排序
    public static Map<String, Port> _incallingPort = new HashMap<>();
    public static List<String> _currentPhoneList = new ArrayList<>();
    public static Map<String, List<Object>> _prologueMap = new HashMap<>();		//开场白map

    public static List<Integer> _onlineUserList = new ArrayList<>();       // 在线客服id列表
    public static Map<Integer, User> _onlineUserMap = new HashMap<>();   // 在线客服user Map
    public static List<String> _onlineUserWorkNumList = new ArrayList<>();       // 在线客服工号列表

    public static List<Integer> _onlineExtNum = new ArrayList<>();      // 在线分机号
    public static List<Integer> _inCallingUser = new ArrayList<>();      // 通话中的用户id

    public static Double _transNoneRate = 0.3;  // 识别结果转成none的最大概率

    public static List<CallRecord> _waitingCaller = new ArrayList<>();		// 等待回拨的电话

    public static Map<String, Map<String, Object>> _statisticsDataMap = new HashMap<>();        // 话务统计缓存

    public static Map<String, Map<String, Object>> _conversionRateDataMap = new HashMap<>();    // 意向转化率缓存

    public  static  Boolean _isHaveMoreDayStatistics = false;   // 是否有大规模的话务统计

    public static List<String> _onSelectStatistics = new ArrayList<>();

    public static Boolean _isHaveGroupCalleyond  = false;   // 是否有群呼超出并发，如果有，变为没有的时候，清空群呼_callPhoneNum

    public static Integer _doCount = 0;

    private static ServletContextEvent sce;

    public static Map<String, PhoneSocket> _clientIdPhoneMap = new HashMap<>();

    public static Map<String, TempSocket> _extNumTempSocketMap = new HashMap<>();

    public static String _lastRateGroupCallId = "";     // 最后一次按倍率分配的群呼

    public static Integer _getDetailNullCount = 0;      // 获取群呼明细为null的连续累积次数

    public static List<String> _currentImportKey = new ArrayList<>();       // 当前正在导入群呼明细的key

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(null != callPhoneThread && !callPhoneThread.isInterrupted()){
//            callPhoneThread.closeSocketService();//关闭serversocket
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //连接python服务
//            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//            config.setServerURL(new URL("http://" + _pythonHost + ":"+ _pythonPort + "/RPC2"));
//            _xmlRpcClient = new XmlRpcClient();
//            _xmlRpcClient.setConfig(config);

            String basePath = StringUtil.getBashPath();
            String CUS_YES_FILE =  basePath+ "cus_yes.txt";
            String CUS_REFUSE_FILE = basePath+"cus_refuse.txt";

            if(cus_yes_list != null){
                cus_yes_list = readResourceFile(CUS_YES_FILE, cus_yes_list);
            }
            if(cus_refuse_list != null){
                cus_refuse_list = readResourceFile(CUS_REFUSE_FILE, cus_refuse_list);
            }

            this.sce = sce;
        }catch (MalformedURLException e){
            logger.error("连接python服务失败：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始线程
     */
    public static void start(){
        ServletContext context = sce.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        UserService userService = webApplicationContext.getBean(UserService.class);
        if(callPhoneThread == null){
            PortService portService = webApplicationContext.getBean(PortService.class);
            GroupCallDetailService groupCallDetailService = webApplicationContext.getBean(GroupCallDetailService.class);
            CallRecordService callRecordService = webApplicationContext.getBean(CallRecordService.class);
            WebSocketService webSocketService = webApplicationContext.getBean(WebSocketService.class);
            callPhoneThread = new CallPhoneThread(portService, groupCallDetailService, callRecordService, webSocketService, userService);
            callPhoneThread.start();
        }
    }

    public static void stop(){
        if(callPhoneThread != null){
            callPhoneThread.isRun = false;
//            callPhoneThread.stop();
            callPhoneThread = null;
        }
    }

    private LinkedList<String> readResourceFile(String file, LinkedList<String> list) throws IOException {
        System.out.println("file = " + file);
        InputStream stream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
        String line = null;
        while (( line = reader.readLine() ) != null ) {
            list.add(line);
        }
        return list;
    }


    /**
     * 拨打之前处理端口id及号码
     * @param message
     * @return
     */
    public static String handlePort(String message, String portId, String phone, Boolean isThird){
        if(isThird == null){
            logger.error(portId + " 端口isThird为空");
        }
        if(  isThird ){
            portId = portId.split("-")[0];
            if(phone.startsWith("0")){
                phone = phone.substring(1, phone.length());
            }
            phone = phone.replace(" ", "");
        }
        message += phone + "\",\"portname\":\"" + portId;
        return message;
    }
}
