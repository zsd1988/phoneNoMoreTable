package com.qingpu.phone.common.utils;

public class ButtonStateUtil {

    /**
     * 操作按钮
     * @param styleClass                按钮样式
     * @param onclickFunction          点击操作方法
     * @param buttonName                操作按钮名称
     * @return
     */
    public static  String  buttonState(String styleClass,String onclickFunction,String buttonName) {
        String divStr = "<div class=\"label label-table "+styleClass+"\" style=\"width: 30%;height: 30px;padding: 5px 10px;font-size: 14px;text-align: center;margin: 0px 10px;\" "+
                "onclick=\""+onclickFunction+"\">"+buttonName+"</div>";
        return  divStr;
    }


    /**
     * 选择框
     * @param index
     * @param value
     * @return
     */
    public static String  checkBoxState(int index,String value){
        String divStr="<input type=\"checkbox\"    class=\"cce\"   style=\"height:20px;width: 20px;\"      id=\"ce"+index+"\"   name=\"ce"+index+"\"     value=\""+value+"\"   onchange=\" checkBoxEvent.changeCheck();\">";
        return divStr;
    }
}
