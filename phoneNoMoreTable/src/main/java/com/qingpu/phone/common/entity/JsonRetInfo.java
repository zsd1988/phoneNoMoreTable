package com.qingpu.phone.common.entity;

import com.qingpu.phone.common.utils.JsonUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonRetInfo extends JSONObject{
    private static final String SUCCESSStr = "成功";
    private static final String ERRORStr = "失败";

    private int code;//错误编号，正常为0，异常为-1、-2、-3等其他负值
    private String message;//错误或者正确信息描述
    private Object data;//需要发送的业务数据对象，可以是JsonObject，可以是普通POJO，最后都会被装换成json字符串

    private String dataStr;

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    public JsonRetInfo() {
    }

    /**
     * 调用成功，只传递业务数据对象，errorno和message使用默认值
     * @param data
     */
    public JsonRetInfo(Object data) {
        this.code = 0;
        this.message = SUCCESSStr;
        this.data = data;
    }

    /**
     * 调用失败，构造错误号和错误信息
     * @param code
     * @param message
     */
    public JsonRetInfo(int code, String message) {
        this.code = code;
        this.message = this.handleMessage(message);
    }

    private String handleMessage(String message){
        if(message == null){
            message = "";
        }
        if(message.contains("java.lang.") || message.length() > 80 || message.contains("For input string") || message.contains("JSONObject")){
            message = "获取数据失败";
        }
        return  message;
    }

    /**
     * 返回获取失败
     * @param response
     * @param message
     * @throws IOException
     */
    static public void returnFail(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write( new JsonRetInfo(JsonRetInfo.ERROR, message).toString());
    }

    /**
     * 返回获取成功
     * @param response
     * @param message
     * @param data
     * @throws IOException
     */
    static public void returnSuccess(HttpServletResponse response, String message, Object data) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write( new JsonRetInfo(JsonRetInfo.SUCCESS, message, JsonUtil.getJsonStrFromEntity(data)).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回获取成功
     * @param response
     * @param message
     * @param data
     * @throws IOException
     */
    static public void returnDataSuccess(HttpServletResponse response, String message, Object data) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write( new JsonRetInfo(JsonRetInfo.SUCCESS, message, data).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回获取成功
     * @param response
     * @param message
     * @throws IOException
     */
    static public void returnSuccess(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write( new JsonRetInfo(JsonRetInfo.SUCCESS, message).toString());
    }

    /**
     * 调用失败，构造错误号和错误信息
     * @param code
     */
    public JsonRetInfo(int code) {
        this.code = code;
        this.message = ERRORStr;
    }

    /**
     * 设置错误号、详细信息、业务对象
     * @param code
     * @param message
     * @param data
     */
    public JsonRetInfo(int code, String message, Object data) {
        this.code = code;
        this.message = this.handleMessage(message);
        this.data = data;
    }


    public JsonRetInfo(String data ,int code, String message) {
        this.code = code;
        this.message = this.handleMessage(message);
        this.dataStr = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = this.handleMessage(message);
    }

    public Object getData() {
        return data;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return new JSONObject(this).toString();
    }
    public static  void responseJson(HttpServletResponse response,JsonRetInfo jsonRetInfo){
        try{
            response.getWriter().write(jsonRetInfo.toString());
        }catch (Exception e){
            e.printStackTrace();;
        }
    }
}
