package com.qingpu.phone.common.entity;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class U3dReturn extends JSONObject{
    private static final String SUCCESSStr = "成功";
    private static final String ERRORStr = "失败";

    private int code;//错误编号，正常为0，异常为-1、-2、-3等其他负值
    private String message;//错误或者正确信息描述

    private String dataStr;

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    /**
     * 调用失败，构造错误号和错误信息
     * @param code
     * @param message
     */
    public U3dReturn(int code, String message) {
        this.code = code;
        this.message = this.handleMessage(message);
    }

    private String handleMessage(String message){
        if(message == null){
            message = "";
        }
        if(message.contains("java.lang.") || message.length() > 100 || message.contains("For input string") || message.contains("JSONObject")){
            message = "获取数据失败";
        }
        return  message;
    }

    /**
     * 返回获取成功
     * @param response
     * @param message
     * @throws IOException
     */
    static public void returnSuccess(HttpServletResponse response, String message) throws IOException {
        response.getWriter().write( new U3dReturn(U3dReturn.SUCCESS, message).toString());
    }

    /**
     * 调用失败，构造错误号和错误信息
     * @param code
     */
    public U3dReturn(int code) {
        this.code = code;
        this.message = ERRORStr;
    }


    public U3dReturn(int code, String message, String data) {
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

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    @Override
    public String toString(){
        return new JSONObject(this).toString();
    }
    public static  void responseJson(HttpServletResponse response,U3dReturn jsonRetInfo){
        try{
            response.getWriter().write(jsonRetInfo.toString());
        }catch (Exception e){
            e.printStackTrace();;
        }
    }
}
