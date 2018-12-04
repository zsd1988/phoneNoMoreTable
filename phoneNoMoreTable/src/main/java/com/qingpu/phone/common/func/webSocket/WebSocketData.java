package com.qingpu.phone.common.func.webSocket;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

public class WebSocketData implements Serializable {
    WebSocketWant want;	//操作类型
    private int code = WebSocketData.SUCCESS;//错误编号，正常为0，异常为-1、-2、-3等其他负值
    private String data;

    public enum WebSocketWant {
        // 服务端
        RegisterReturn("注册返回"),
        SendOffline("发送下线"),
        ShowClient("客户来电弹屏"),
        StartPhoneClient("打开软电话"),
        Online("在线"),
        HangUp("挂断电话"),
        CallEnd("通话结束"),
        Offline("离线"),
        ShowDraw("显示抽奖按钮"),
        ShowGoldenEgg("显示砸金蛋界面"),
        ShowIntentionHint("显示词条"),
        ShowGoldenHint("显示金蛋结果"),
        ShowPostMessage("显示推送通知"),
        ShowLuckDrawHint("显示抽奖提示"),
        HintFreeSwitchOff("提示freeswitch离线"),
        UNALLOCATED_NUMBER("第三方网关出错"),
        NO_USER_RESPONSE("第三方网关出错"),
        Phone_UNALLOCATED_NUMBER("第三方网关出错"),
        Phone_NO_USER_RESPONSE("第三方网关出错"),
        DESTINATION_OUT_OF_ORDER("本地网关出错"),

        // 客户端
        SendUserId("发送用户id"),
        IsCocos("声明是cocos客户端"),
        GetJianghuBaseInfo("获取江湖基础信息"),
        GetJianghuChangeInfo("获取江湖动态信息"),
        GetJianghuRefreshInfo("获取江湖刷新信息"),
        GetStatisticsInfo("获取统计信息"),
        SendJianghuUpgrade("发送江湖升级信息"),
        SendJianghuIntention("发送江湖意向信息"),
        SendStage("发送员工满阶段日期信息"),
        SendBirthday("发送员工生日信息"),
        ChangeScene("切换场景"),
        UserSendOffLine("发送离线"),
        UserSendOnLine("发送在线");
        private String name;
        WebSocketWant(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    public WebSocketData(int code, WebSocketWant want, String data) {
        this.code = code;
        this.want = want;
        this.data = data;
    }

    public WebSocketData(int code, WebSocketWant want) {
        this.code = code;
        this.want = want;
    }

    public WebSocketData(WebSocketWant want, String data) {
        this.code = WebSocketData.SUCCESS;
        this.want = want;
        this.data = data;
    }

    public WebSocketData(WebSocketWant want, Map<String, Object> map) {
        this.code = WebSocketData.SUCCESS;
        this.want = want;
        this.data = JSONObject.fromObject(map).toString();
    }

    public WebSocketData(WebSocketWant want) {
        this.code = WebSocketData.SUCCESS;
        this.want = want;
    }

    public WebSocketWant getWant() {
        return want;
    }

    public void setWant(WebSocketWant want) {
        this.want = want;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
