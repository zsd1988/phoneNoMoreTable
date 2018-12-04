//# sourceURL=utils.js
//使用JSON-RPC2.0协议调用后台Service的服务
//url:请求的url
//params：一个数组，调用的函数参数
//success, failed分别是异步调用后通知结果的回调函数（一个成功，一个失败）
//success原型：function(result)，result为调用返回值
//failed原型：failed(message)，message为错误信息
function jsonrpc(url, params, success, failed) {
    var timestamp = new Date().getTime();
    if(params === null){
        params = {}
    }
    if (params && (typeof params == "object")) {
        params["timestamp"] = timestamp;
    }
    var async = (success == undefined) ? false : true;//如果定义了成功回调方法则是同步的，否则是异步的
    var returnValue;
    $.ajax({
        url :  url,
        async : async,
        type : "POST",
        dataType : "json",
        data : JSON.stringify(params),
        contentType : "application/json",
        success : function(data) {
            if (async) {
                if (data.code != 0){
                    failed(data.message);
                }
                else{
                    success(data.data);
                }
            } else {
                if (data.code != 0)
                    throw new Error(data.message);
                else
                    returnValue = data.data;
            }
        },
        error : function(req, err) {
            if (err == "timeout") {
                boldFunc.showErrorMes("网络超时，请检查网络");
            } else if (err == "error") {
                boldFunc.showErrorMes("网络错误，请检查网络");
            } else if (err == "abort") {
                boldFunc.showErrorMes("连接被取消，请联系服务提供商");
            } else if (err == "parsererror") {
                boldFunc.showErrorMes("无法提交数据，请联系服务提供商");
            }else{
                boldFunc.showErrorMes("未知错误，请联系服务提供商");
            }
        }
    });
    return returnValue;
}

var browser={
    os:function(){
        var u = navigator.userAgent, app = navigator.appVersion;
        return {
            ie: u.indexOf('Trident') > -1, //IE内核
            chrome: u.indexOf('Chrome') > -1, //谷歌
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            //mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/)&&u.indexOf('QIHU')&&u.indexOf('Chrome')<0, //是否为移动终端
            ios: !!u.match(/(i[^;]+;(U;)? CPU.+Mac OS X)/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
            weixin:u.indexOf('MicroMessenger') > -1, //是否微信
            webview:u.indexOf('Chrome') > -1,
            app:u.indexOf('MicroMessenger') == -1 && ((u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) || !!u.match(/(i[^;]+;(U;)? CPU.+Mac OS X)/) ),
            wxAndroid:u.indexOf('MicroMessenger') > -1 && (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1),//android微信客户端
            wxIOS:u.indexOf('MicroMessenger') > -1 && !!u.match(/(i[^;]+;(U;)? CPU.+Mac OS X)/),//IOS微信客户端
            AndroidNotWx:u.indexOf('MicroMessenger') == -1 && (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1),//android APP 非微信客户端
            IOSNotWx:u.indexOf('MicroMessenger') == -1 && !!u.match(/(i[^;]+;(U;)? CPU.+Mac OS X)/), //IOS APP 非微信客户端
            QQ:u.indexOf('QQ') > -1 && u.indexOf('MicroMessenger') == -1,//qq空间浏览器
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
};

var  os = {
    isAndroidWx:function()
    {
        return browser.os.wxAndroid;
    },
    isIOSWx:function()
    {
        return browser.os.wxIOS;
    },
    isAndroidAPP:function()
    {
        return browser.os.AndroidNotWx;
    },
    isIOSAPP:function()
    {
        return browser.os.IOSNotWx;
    }
};

$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 将字符串有下划线的转变成驼峰命名方法
 * @returns {{}}
 */
$.fn.serializeHumpObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        this.name = StringUtil.toHump(this.name);
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


String.prototype.endWith=function(endStr){
    if(this == null){
        return false;
    }
    var d=this.length-endStr.length;
    return (d>=0&&this.lastIndexOf(endStr)==d)
}

/**
 * 字符串工具
 */
var StringUtil = {
    isEmpty: function (o) {
        o += "";
        if (o == null || o == 'undefined' || o == '' || o == 'null') {
            return true;
        } else {
            return false;
        }
    },
    isNotEmpty: function (o) {
        if(this.isEmpty(o)){
            return false;
        }else{
            return true;
        }
    },
    /**
     * 隐藏手机号码
     * @param str
     */
    handlePhone:function (str) {
        if(str != null && str != ""){
            var index = str.length - 8
            var re =new RegExp("(\\d{" + index + "})\\d{4}(\\d{4})");
            str = str.replace(re, '$1****$2');
        }
        return str;
    },
    ignoreCaseEquals:function (str1, str2) {
        if(str1 == null && str2 == null){
            return true;
        }
        if(str1 != null && str2 == null){
            return false;
        }
        if(str1 == null && str2 != null){
            return false;
        }
        if(str1.length == str2.length && str1.toLowerCase()==str2.toLowerCase()) {
            return true;
        }
        return false;
    },
    getRequestParas: function (paras) {
        var url = location.href;
        var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
        var paraObj = {};
        var i, j;
        for (i = 0; j = paraString[i]; i++) {
            paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
        }
        var returnValue = paraObj[paras.toLowerCase()];
        if (typeof (returnValue) == "undefined") {
            return "";
        } else {
            return returnValue;
        }
    },
    /**
     * 从链接中获取参数
     * @param paras
     * @param url
     * @returns {*}
     */
    getRequestParasFromUrl: function (paras, url) {
        var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
        var paraObj = {};
        var i, j;
        for (i = 0; j = paraString[i]; i++) {
            paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
        }
        var returnValue = paraObj[paras.toLowerCase()];
        if (typeof (returnValue) == "undefined") {
            return "";
        } else {
            return returnValue;
        }
    },
    contains:function (containsStr, str) {
        if(StringUtil.isNotEmpty(str)){
            if(str.indexOf(containsStr) > -1){
                return true;
            }
        }
        return false;
    },
    subLastChar:function (str) {
        if(str != null && str.length > 1){
            return str.substring(0,str.length-1)
        }
        return "";
    },
    /**
     * 转变为驼峰命名方法
     * @param str
     */
    toHump:function (str) {
        var name = '';
        if(str != null){
            var strArr = str.split('_');
            for(var i = 0; i < strArr.length; i++){
                var item = strArr[i];
                if(i > 0){
                    item = item.slice(0, 1).toUpperCase() + item.slice(1);
                }
                name += item;
            }
        }
        return name;
    },
    /**
     * 字符串转数字
     * @param str
     * @returns {null}
     */
    toNum:function (str) {
        if(str != null){
            return str.replace(/[^0-9]/ig,"");
        }
        return null;
    },
    /**
     * obj转网页参数
     * @param param
     * @param key
     * @param encode
     * @returns {string}
     */
    urlEncode : function (param, key, encode) {
        if(param==null) return '';
        var paramStr = '';
        var t = typeof (param);
        if (t == 'string' || t == 'number' || t == 'boolean') {
            paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);
        } else {
            for (var i in param) {
                var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
                paramStr += StringUtil.urlEncode(param[i], k, encode);
            }
        }
        return paramStr;
    },
};


var NumFunc={
    /**
     * 处理数字为美式数字。如 138,223,341.33434
     * @param value
     * @returns {*}
     */
    adjustNum:function (value) {
        if(isNaN(value)){
            return 0;
        }
        value = value + "";
        var valueArr = value.split(".");
        var temp = "";
        var head = valueArr[0];
        var index = 0;
        for(var i = head.length - 1 ; i > -1 ; i--){
            temp += head[i];
            index++;
            if(index%3 == 0 && index != head.length){
                temp += ",";
            }
        }
        temp = temp.split("").reverse().join("")
        var end = "";
        if(valueArr.length > 1){
            end = "." + valueArr[1]
        }
        return temp + end;
    },
    /**
     * 去除美式的逗号
     * @param value
     * @returns {void|string|XML|*}
     */
    backNum:function (value) {
        value = value.replace(/,/g,'');
        value = value.replace(/，/g,'');
        return value;
    },
    /**
     * 成为一千的倍数
     * @param value
     * @returns {void|string|XML|*}
     */
    toThousand:function (value) {
        if(isNaN(value)){
            return 0;
        }
        value = value + "";
        var valueArr = value.split(".");
        var temp = "";
        var head = valueArr[0];
        if(head.length < 3){
            return 0;
        }
        for(var i = head.length - 1 ; i > -1 ; i--){
            if(i > head.length - 4){
                temp += "0";
            }else{
                temp += head[i];
            }
        }
        temp = temp.split("").reverse().join("")
        return temp;
    },
    /**
     * 保留两位小数
     * @param value
     * @returns {*}
     */
    getRoundTwo:function (value) {
        if(value != null){
            var re = /^[0-9]+.?[0-9]*$/;
            if(re.test(value)){
                return value.toFixed(2);
            }
        }
        return 0.00;
    },
    /**
     * 检测是否为数字
     * @param val
     * @returns {boolean}
     */
    isNumber:function (val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }
    },
    /**
     * 检测是否为整数
     * @param obj
     * @returns {boolean}
     */
     isInteger : function(obj) {
        if(!/^\d+$/.test(obj)){
            return false;
        }
        return true;
    }
};

(function($) {
    $.fn.blink = function(options) {
        var defaults = { delay: 500 };
        var options = $.extend(defaults, options);
        return $(this).each(function(idx, itm) {
            var handle = setInterval(function() {
                if ($(itm).css("visibility") === "visible") {
                    $(itm).css('visibility', 'hidden');
                } else {
                    $(itm).css('visibility', 'visible');
                }
            }, options.delay);

            $(itm).data('handle', handle);
        });
    }
    $.fn.unblink = function() {
        return $(this).each(function(idx, itm) {
            var handle = $(itm).data('handle');
            if (handle) {
                clearInterval(handle);
                $(itm).data('handle', null);
                $(itm).css('visibility', 'inherit');
            }
        });
    }
}(jQuery))

Array.prototype.contains = function ( needle ) {
    for (i in this) {
        if (this[i] == needle) return true;
    }
    return false;
}


var localStorageUtils ={
    getLocal:function (key) {
        var data;
        data = window.localStorage.getItem(key);
        if(data == null){
            return "";
        }else{
            return data;
        }
    },
    setLocal:function (key, value) {
        if(this.isSupportLocalStorge()) {
            window.localStorage.setItem( key, value +"");
        }
    },
    removeLocal:function(key)
    {
        if(this.isSupportLocalStorge()){
            window.localStorage.removeItem(key);
        }else{
        }
    },
    /**
     * 清除所有local
     */
    clearAllLocal:function()
    {
        window.localStorage.clear();
    },
    /**
     * 是否支持LocalStorge
     * @returns {boolean}
     */
    isSupportLocalStorge:function()
    {
        var flag = true;
        return flag;
    },
}

var JsonUtils = {
    time:null,
    functionId:null,
    getLength:function(id){
        var now = Date.parse(new Date());
        if(this.time == null){
            this.time = now;
            return false;
        }
        if(this.functionId == null){
            this.functionId = id
            return false;
        }
        if(this.functionId != id){
            this.functionId = id
            return false;
        }
        if(now - this.time > 300){
            this.time = now;
            console.log(now - this.time)
            return false;
        }
        return true;
    }
};


JSON.length = function(json){
    var jsonLength=0;
    for (var i in json) {
        jsonLength++;
    }
    return jsonLength;
};

var boldFunc = {
    /**
     * 显示弹窗
     * @param title
     * @param okCall
     * @param cancelCall
     */
    showConfirmText:function (title, text, okCall, cancelCall) {
        swal({
            title: title,
            text: text,
            showCancelButton: true,
            cancelButtonText: '取消',
            confirmButtonText: '确定',
            closeOnConfirm: true
        },function(isConfirm){
            if (isConfirm) {
                okCall();
            } else {
                if(cancelCall){
                    cancelCall();
                }
            }
        });
    },

    showConfirm:function (title, okCall, cancelCall) {
        boldFunc.showConfirmText(title,null, okCall, cancelCall)
    },

    showAlert:function (title, okCall) {
        swal({
            title: title,
            confirmButtonText: '确定',
            closeOnConfirm: true
        },function(){
            okCall();
        });
    },

    /**
     * 提示信息
     */
    showMessage:function(title ,message) {
        swal({
            title:  title,
            text:   message,
            timer:  2000,
            showConfirmButton: false
        });
        return;
    },

    /**
     * 按名称选择左侧菜单
     * @param name
     */
    selectLeftMenu:function (name) {
        $("#sidebar-menu").find('span').each(function (index, item) {
            var elm = $(item)
            var parentA = elm.parents('a');
            if(elm.text() == name){
                parentA.addClass("subdrop")
            }else{
                parentA.removeClass("subdrop")
            }
        });
    },

    /**
     * 推送提示信息
     */
    notification:function(text){
        $.Notification.notify('success','top right',text, '');
    },

    /**
     * 显示错误信息
     * @param text
     */
    showErrorMes:function(text){
        $.Notification.notify('error','top right',text, '');
    }


};

var loadApi = {

    /**
     * 可同步加载的js
     */
    loadJS: function (path){
        $.ajax({
            url:path + "?t=" + new Date().getTime(),
            dataType: "script",
            cache: true,
        }).fail(function() {
            console.log('加载失败：' + path)
        });
    },

    /**
     * 可同步加载的js
     */
    loadBackJS:function(url,callback,bCache){
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        if(bCache){
            script.src = url
        }else{
            script.src = url + "?t=" + new Date().getTime();
        }
        script.type = "text/javascript";
        head.appendChild( script);

        // script 标签，IE下有onreadystatechange事件, w3c标准有onload事件
        // IE9+也支持 W3C标准的onload
        var ua = navigator.userAgent,
            ua_version;
        // IE6/7/8
        if (/MSIE ([^;]+)/.test(ua)) {
            ua_version = parseFloat(RegExp["$1"], 10);
            if (ua_version <= 8) {
                script.onreadystatechange = function(){
                    if (this.readyState == "loaded" ){
                        callback();
                    }
                }
            } else {
                script.onload = function(){
                    callback();
                };
            }
        } else {
            script.onload = function(){
                callback();
            };
        }
    },

    /**
     * 同步加载样式文件
     * @param cssName
     */
    loadLink:function(cssName){
        $("head").append("<link>");
        var css = $("head").children(":last");
        css.attr({
            rel:  "stylesheet",
            type: "text/css",
            href: cssName
        });
    }
};

var pageInit={

    /**
     * 页面高度初始化
     */
    pageHeight:function () {
        var navbar=$(".navbar").height();
        var qingpuHeight=$(".qingpu").height();
        var contentHeight= window.screen.availHeight ;
        var rowHeight=contentHeight-(qingpuHeight+navbar+200);
        $(".rowCount").css("height",rowHeight).css("overflow-y","auto");
    }
};

/**
 * 校验
 * @type {{verifyNumber: verifyData.verifyNumber, verifyTelphone: verifyData.verifyTelphone, verifyIdentityNo: verifyData.verifyIdentityNo}}
 */
var verifyData={
    /**
     * 数字校验
     */
    verifyNumber:function(numberValue){
        var reg = /^[0-9]*$/;
        if(numberValue==''||numberValue.match(reg)==null){
            boldFunc.showMessage("提示信息！","数据格式错误！");
            return false;
        }
    },

    /**
     * 11位手机号码校验
     */
    verifyTelphone:function(telphone){
        var reg = /^1[3|4|5|7|8][0-9]{9}$/;
        if(telphone==''||telphone.match(reg)==null){
            boldFunc.showMessage("提示信息！","手机号码格式错误！");
            return false;
        }
    },

    /**
     * 身份证校验
     */
    verifyIdentityNo:function(identityNo){
        var regIdentityNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if(identityNo==''||identityNo.match(reg)==null){
            boldFunc.showMessage("提示信息！","身份证格式错误！");
            return false;
        }
    },

    /**
     * 邮箱校验
     * @param email
     */
    verifyEmail:function(email){
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(email==''||email.match(reg)==null){
            boldFunc.showMessage("提示信息！","邮箱格式错误！");
            return false;
        }
    },

    /**
     * 确认密码校验
     * @param pwd
     * @param repeatPwd
     * @returns {boolean}
     */
    verifyPwd:function (pwd,repeatPwd) {
        if(repeatPwd==''||repeatPwd!=pwd){
            return false;
        }
    },
};


/**
 * checkBox事件
 * @type {{checkAllBox: checkBoxEvent.checkAllBox, changeCheck: checkBoxEvent.changeCheck, getAllCheckValue: checkBoxEvent.getAllCheckValue}}
 */
var checkBoxEvent={
    /**
     *  全选中和全部选中事件
     */
    checkAllBox:function() {
        var clen= $(".cce").size();
        if (document.getElementById("ce").checked == true) {
            for(var i=1;i<=clen;i++){
                document.getElementById("ce"+i).checked=true;
            }
        }else{
            for(var i=1;i<=clen;i++){
                document.getElementById("ce"+i).checked=false;
            }
        }
    },

    /**
     *  单个选择事件
     */
    changeCheck:function () {
        if (document.getElementById("ce").checked == true) {
            document.getElementById("ce").checked=false;
        }
    },

    /**
     *  获取已选的值
     * @returns {Array}
     */
    getAllCheckValue:function () {
        var clen= $(".cce").size();
        var arr = new Array();

        for(var i=1;i<=clen;i++){
            if (document.getElementById("ce"+i).checked == true) {
                arr.push(document.getElementById("ce"+i).value);
            }
        }
        return  arr;
    }
};

var CheckBoxFunc = {
    init : function (arg) {
        var Defaults = {
            data:null,      // 数据，包含所有的id，和全选组件的id
            divId: null,    // 包含所有checkbox的div
            allId:null      // 全选id
        };
        var opt = $.extend({}, this._defaults, Defaults, arg);
        var allId = opt.allId;
        var data = opt.data
        $("#" + opt.divId).find('input').each(function (index, elm) {
            $(elm).click(function(div) {
                var id = $(this).attr("id")
                var userArrLength = data.length;
                if(id== allId){
                    for(var i = 0; i < userArrLength; i++){
                        var item = data[i]
                        var itemId = item.id
                        if(itemId != allId){
                            $("#" + itemId).get(0).checked = $(this).is(':checked');
                        }
                    }
                }else{
                    var checkedNum = 0;
                    for(var i = 0; i < userArrLength; i++){
                        var item = data[i]
                        var itemId = item.id
                        if(itemId != allId){
                            if($("#" + itemId).is(':checked')){
                                checkedNum++
                            }
                        }
                    }
                    var isAllChecked = false
                    if(checkedNum == userArrLength - 1){
                        isAllChecked = true;
                    }
                    $("#" + allId).get(0).checked = isAllChecked;
                }
            })
        });
    },
    getSelectId:function (elmId) {
        var ids = '';
        $("#" + elmId).find('input').each(function (index, elm) {
            var $elm = $(elm)
            var id = $elm.attr("id")
            if(id != 'all' && $(elm).get(0).checked){
                ids += id + ','
            }
        });
        ids = StringUtil.subLastChar(ids);
        return ids;
    }
}

/**
 * 弹出modal函数方法
 * @type {{clearForm: ModalFunc.clearForm}}
 */
var ModalFunc = {
    /**
     * 注册：隐藏modal同时，清除数据
     * @param modalName
     */
    regHiddenModal:function (modalName) {
        $("#" + modalName).on("hidden.bs.modal", function() {
            FormFunc.clearForm($(this));
        });
    },
    /**
     * 显示modal
     * @param modalName
     * @param tableName
     * @param index
     */
    show:function (modalName, tableName, index) {
        if(StringUtil.isNotEmpty(tableName)){
            var data = $("#" + tableName).bootstrapTable('getData')[index];
            FormFunc.loadObject(data, $("#" + modalName))
        }
        $("#" + modalName).modal("show");
    },
    /**
     * 显示名称为table的Modal
     * @param modalName
     * @param tableName
     * @param index
     */
    showTable:function (modalName, index) {
        ModalFunc.show(modalName, "table", index)
    }
}

/**
 * 输入校验方法
 * @type {{}}
 */
var ParsleyFunc = {
    checkForm: function (form, success) {
        var isAllValid = true;
        $(':input', form).each(function () {
            var type = this.type;
            var tag = this.tagName.toLowerCase(); // normalize case
            if (type == 'text' || type == 'password' || tag == 'textarea' || type == 'number'){
                $(this).parsley().validate();
                var isValid =$(this).parsley().isValid()
                if( ! isValid){
                    isAllValid = false;
                }
            }
        });
        if(isAllValid){
            success();
        }
    },
}

/**
 * 对象函数
 * @type {{toJsonStr: ObjectFunc.toJsonStr}}
 */
var ObjectFunc = {
    toJsonStr:function (obj) {
        return JSON.stringify(obj);
    },
    getLength:function (obj) {
        var arr = Object.keys(obj);
        var length = 0;
        if(arr != null){
            length = arr.length
        }
        return length;
    }
}

/**
 * json函数
 * @type {{toObject: JsonFunc.toObject}}
 */
var JsonFunc = {
    toObject:function (jsonStr) {
        return JSON.parse(jsonStr);
    }
}

/**
 * 表单函数
 * @type {{loadObject: FormFunc.loadObject, loadJsonStr: FormFunc.loadJsonStr}}
 */
var FormFunc = {
    /**
     * 加载对象
     * @param obj
     */
    loadObject:function (obj, form) {
        try{
            var key,value,tagName,type,arr;
            for(var x in obj){
                key = x;
                value = obj[x];
                $( "[name='"+key+"'],[name='"+key+"[]']", form).each(function(){
                    tagName = $(this)[0].tagName;
                    type = $(this).attr('type');
                    if(tagName=='INPUT'){
                        if(type=='radio'){
                            if(value){
                                if($(this).val() == "true"){
                                    $(this).prop('checked',true );
                                }else{
                                    $(this).removeAttr('checked');
                                }
                            }else{
                                if($(this).val() == "false"){
                                    $(this).prop('checked',true);
                                }else{
                                    $(this).removeAttr('checked');
                                }
                            }
                        }else if(type=='checkbox'){
                            arr = value.split(',');
                            for(var i =0;i<arr.length;i++){
                                if($(this).val()==arr[i]){
                                    $(this).attr('checked',true);
                                    break;
                                }
                            }
                        }else{
                            $(this).val(value);
                        }
                    }else if(tagName=='TEXTAREA'){
                        $(this).val(value);
                    }
                    else if(tagName=='SELECT'){
                        if(typeof(value)=="boolean"){
                            if(value){
                                value="true";
                            }
                            else{
                                value="false";
                            }
                        }
                        $(this).selectpicker('val', value);
                    }
                });
            }
        }catch(e){
            console.log("form加载数据失败：" + ObjectFunc.toJsonStr(obj))
        }
    },
    /**
     * 加载json字符串数据
     * @param jsonStr
     */
    loadJsonStr : function (jsonStr, form){
        var obj = JsonFunc.toObject(jsonStr);
        FormFunc.loadObject(obj, form)
    },
    /**
     * 清空form里面的内容
     * @param form
     */
    clearForm : function (form) {
        // input清空
        $(':input', form).each(function () {
            var type = this.type;
            var tag = this.tagName.toLowerCase(); // normalize case
            if (type == 'text' || type == 'password' || tag == 'textarea' || type == 'number'){
                var dd = $(this).val();
                $(this).val('')
            }else if(tag=='select' || tag=='textarea'){
                $(this).selectpicker('val', '');
            }
        });
    },
}

var PageFunc = {
    gotoUrl:function (page, data) {
        window.open(window.location.href);
        LocalStorageFunc.saveData(GlobalData.localGotoPage, page)
        LocalStorageFunc.saveData(GlobalData.localGotoData, data)
    }
}

var LocalStorageFunc = {
    saveData: function(key, data){
        localStorage.setItem(key, data);
    },
    loadData: function(key){
        return localStorage.getItem(key);
    },
    saveObjectData: function(key, data){
        localStorage.setItem(key, JSON.stringify(data));
    },
    loadObjectData: function(key){
        var str = localStorage.getItem(key);
        if(!str){
            return null;
        }
        return JSON.parse(str);
    },
    removeItem: function(key){
        localStorage.removeItem(key);
    }
}

Array.prototype.remove = function(s) {
    for (var i = 0; i < this.length; i++) {
        if (s == this[i])
            this.splice(i, 1);
    }
}

var TableFunc = {
    _defaults: {
        type : "post",
        cache: false,
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        queryParamsType : "undefined",
        pagination:true,                    //分页
        queryParams : null,
        showColumns:false,
        pageSize:10,
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageList: [10, 20, 50, 100, 150],        //可供选择的每页的行数（*）
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showExport: true,
        exportDataType: 'all',
        clickToSelect:false,                //是否启用点击选中行
        elm:"table",
        method:'post',
        dataType:'json',
    },
    showTable:function (arg) {
        var Defaults = {
            obj: null,
            content:'',
            responseHandler:this.responseHandler
        };
        var opt = $.extend({}, this._defaults, Defaults, arg);
        opt.url = GlobalData.basePath + opt.url;
        $("#" + opt.elm).bootstrapTable(opt);
    },
    gotoPage:function (elm) {
        var pageNum = $(elm).val();
        if (pageNum) {
            var $table = $(elm).parent().parent().prev().prev().find("table")
            $table.bootstrapTable('selectPage', parseInt(pageNum));
        }
    },
    responseHandler:function (res) {
        if (res.code == 0) {
            if(res.data == null){
                return {
                    "rows" : [],
                    "total" : 0
                };
            }else{
                return {
                    "rows" : res.data.result,
                    "total" : res.data.totalCount
                }
            };
        } else {
            if(res.message != null){
                boldFunc.showErrorMes(res.message)
            }
            return {
                "rows" : [],
                "total" : 0
            };
        }
    },
    formatter:{
        /**
         * 获取枚举类型的值
         * @param value
         * @param enumName
         * @returns {*}
         */
        _enumType:function (value, enumName) {
            var data = null;
            switch (enumName){
                case "screenType":
                    data = GlobalData.screenTypes;
                    break;
                case "deviceType":
                    data = GlobalData.deviceTypes;
                    break;
            }
            for(var key in data){
                if(value == key){
                    return data[key];
                }
            }
        },
        bool:function (value, row, index) {
            if(value){
                return "<span style='color: #d70206'>是</span>"
            }else{
                return "否"
            }
        },
        /**
         * 取相反的值
         * @param value
         * @param row
         * @param index
         * @returns {*}
         */
        inverseBool:function (value, row, index) {
            if( ! value){
                return "<span style='color: #d70206'>是</span>"
            }else{
                return "否"
            }
        },
        /**
         * 是否启用
         * @param value
         * @param row
         * @param index
         * @returns {*}
         */
        boolOnOff:function (value, row, index) {
            if( value){
                return "<span style='color: #d70206'>禁用</span>"
            }else{
                return "启用"
            }
        },
        screenType:function (value, row, index) {
            return TableFunc.formatter._enumType(value, "screenType")
        },
        deviceType:function (value, row, index) {
            return TableFunc.formatter._enumType(value, "deviceType")
        },
        setRed:function (value, redValue) {
            if(StringUtil.contains(value, redValue)){
                return "<span style='color: #d70206'>" + value + "</span>"
            }else{
                return value;
            }
        }
    },
    /**
     * table点击列名排序，默认按逐个字符的charCode排序
     * @param table:id
     * @param sortIndex:需要排序的列，-1表示全部都需要排序
     * @param isIndex:是否转换为整型再排序（同一table同时需要字符整型排序，则需要前后调用此函数多次）
     * @returns {*}
     */
    makeSortable:function(table,sortIndex,isInt) {
        var headers=table.getElementsByTagName("th");
        for(var i=0;i<headers.length;i++){
            (function(n){
                var flag=false;
                if(sortIndex != -1){
                    var n=sortIndex;
                }
                headers[n].onclick=function(){
                    var tbody=table.tBodies[0];//第一个<tbody>
                    var rows=tbody.getElementsByTagName("tr");//tbody中的所有行
                    rows=Array.prototype.slice.call(rows,0);//真实数组中的快照

                    //基于第n个<td>元素的值对行排序
                    rows.sort(function(row1,row2){
                        var cell1=row1.getElementsByTagName("td")[n];//获得第n个单元格
                        var cell2=row2.getElementsByTagName("td")[n];
                        var val1=cell1.textContent||cell1.innerText;//获得文本内容
                        var val2=cell2.textContent||cell2.innerText;
                        if(!isInt){
                            if(val1<val2){
                                return -1;
                            }else if(val1>val2){
                                return 1;
                            }else{
                                return 0;
                            }
                        }
                        else{
                            var val1Int=parseInt(val1);
                            var val2Int=parseInt(val2);
                            if(val1Int<val2Int){
                                return -1;
                            }else if(val1Int>val2Int){
                                return 1;
                            }else{
                                return 0;
                            }
                        }

                    });
                    if(flag){
                        rows.reverse();
                    }
                    //在tbody中按它们的顺序把行添加到最后
                    //这将自动把它们从当前位置移走，故没必要预先删除它们
                    //如果<tbody>还包含了除了<tr>的任何其他元素，这些节点将会悬浮到顶部位置
                    for(var i=0;i<rows.length;i++){
                        tbody.appendChild(rows[i]);
                    }
                    flag=!flag;
                }
            }(i));
        }
    },
}


var SelectPickerFunc = {
    /**
     * 加载远程url
     * @param id
     * @param url
     * @param isUnSelect 不选择默认值
     */
    loadUrlData:function (id, url, isUnSelect, val) {
        getDataByURL(url, null, function (data) {
            var json = data.data
            SelectPickerFunc.load(id, json, isUnSelect, val)
        })
    },
    /**
     * 适合批量加载
     * @param id
     * @param json
     * @param isUnSelect
     * @param val
     */
    load:function (id, json, isUnSelect, val) {
        $("#" + id).empty();
        for(var i = 0; i < json.length; i++){
            var item = json[i]
            if(val == null ){
                val = item.key;
            }
            $("#" + id).append(" <option value=\"" + item.key + "\">" + item.value + "</option>");
        }
        $("#" + id).selectpicker('refresh');
        if( isUnSelect){
            $("#" + id).selectpicker('val', '');
        }else{
            $("#" + id).selectpicker('val', val);
        }
    },

    /**
     * 加载map里面的数据
     * @param id
     * @param json
     * @param isUnSelect
     * @param val
     */
    loadMap:function (id, json, isUnSelect, val) {
        $("#" + id).empty();
        for(var i = 0; i < json.length; i++){
            var obj = json[i]
            for(var key in obj){
                if(val == null ){
                    val = key;
                }
                $("#" + id).append(" <option value=\"" + key + "\">" + obj[key] + "</option>");
            }
        }
        $("#" + id).selectpicker('refresh');
        if( isUnSelect){
            $("#" + id).selectpicker('val', '');
        }else{
            $("#" + id).selectpicker('val', val);
        }
    },
    /**
     * 加载本地数据
     * @param id
     * @param data
     * @param isUnSelect 不选择默认值
     */
    loadLocalData:function (id, data, isUnSelect) {
        $("#" + id).empty();
        var val = null;
        for(var key in data){
            if(val == null){
                val = key;
            }
            $("#" + id).append(" <option value=\"" + key + "\">" + data[key] + "</option>");
        }
        $("#" + id).selectpicker('refresh');
        if( isUnSelect){
            $("#" + id).selectpicker('val', '');
        }else{
            $("#" + id).selectpicker('val', val);
        }
    },
}


Date.prototype.format = function(format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

var DateFunc = {
    /**
     * 获取当前时间：“yyyy-MM-dd HH:MM:SS”
     * @returns {string}
     */
    getNowFormatDate : function () {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    },
    getDateArray : function (endDate, splitTime, count) {
        if(!endDate) {
            endDate = new Date();
        }
        if(!splitTime) {
            splitTime = 5 * 60 * 1000;
        }
        if(!count) {
            count= 12;
        }
        var endTime = endDate.getTime();
        var mod = endTime % splitTime;
        if(mod > 0) {
            endTime -= mod;
        }
        var dateArray = [];
        while(count-- > 0) {
            var d = new Date();
            d.setTime(endTime - count * splitTime);
            dateArray.push(d);
        }
        return dateArray;
    },
    dateToString : function (date, index) {
        if(date instanceof Date){
            if(index == null){
                index = 0;
            }
            switch (index){
                case 1:
                    return date.format("yyyy-MM-dd HH:mm");
                case 2:
                    return date.format("yyyy-MM-dd HH:mm:ss");
                case 3:
                    return date.format("yyyyMMdd");
                case 4:
                    return date.format("yyyyMMddHHmmss");
                case 5:
                    return date.format("HH:mm");
                case 6:
                    return date.format("MM-dd");
                case 7:
                    return date.format("yyyyMM");
                case 8:
                    return date.format("MM-dd HH:mm");
                default:
                    return date.format("yyyy-MM-dd");
            }
        }
    }
}

var ArrayFunc = {
    /**
     * arr转成以逗号分隔的字符串
     * @param arr
     * @returns {string}
     */
    toCommaStr:function (arr) {
        var str = '';
        if(arr != null){
            for(var i = 0; i < arr.length; i++){
                str += arr[i] + ','
            }
            if(str.length > 1){
                str = StringUtil.subLastChar(str)
            }
        }
        return str;
    },
    clear:function (arr) {
        arr.splice(0,arr.length);
    }
}


var CheckDataUtil={
    /**
     * 不是空值判断
     * @param obj
     * @returns {boolean}
     */
    isNotNull:function (obj) {
        if(obj==null||obj==''||obj=='underfind'){
            return false;
        }
        return true;
    },

    /**
     * 空值判断
     * @param obj
     * @returns {boolean}
     */
    isNull:function (obj) {
        if(obj==null||obj==''||obj=='underfind'){
            return true;
        }
        return false;
    }
}

/**
 * 字符串转json
 * @type {{}}
 */
var JsonUtil={
    strTurnJson:function(jsonName,jsonValue){
        var str ='{'+jsonName+':'+jsonValue+'}';
        var json = eval('(' + str + ')');
        return json;
    },

    strFolderAndSource:function(folderId,sourceType){
        var str ='{folderId:"'+folderId+'",type:'+sourceType+'}';
        var json = eval('(' + str + ')');
        return json;
    }
}

{
    /**
     * web-upload 工具类
     *
     * 约定：
     this.uploaderId = modalId + "Uploader";
     this.dndAreaId = modalId + "DndArea";
     this.filePickerId = modalId + "FilePicker";
     this.continueFilePickerId = modalId + "ContinueFilePicker";
     *
     */
    var $WebModalUpload = function(arg) {
        var _defaults = {
            fileType:"image",   // video,
            isMore:true,        // 是否多个文件上传
            fileNumLimit:1,
            modalId:"uploadImage",
            fileSizeLimit: 200 * 1024 * 1024,    // 200 M
            fileSingleSizeLimit: 10 * 1024 * 1024,    // 50 M
            formData:{},
            uploadSuccess:null,     // 上传成功回调
        };
        var opt = $.extend(true, {},_defaults, arg);
        this.opt = opt;
        var modalId = opt.modalId;
        this.modalId = modalId;
        this.uploaderId = modalId + "Uploader";
        this.dndAreaId = modalId + "DndArea";
        this.filePickerId = modalId + "FilePicker";
        this.continueFilePickerId = modalId + "ContinueFilePicker";

        this.$wrap = $('#' + this.uploaderId);
        // 图片容器
        this.$queue = $( '<ul class="filelist"></ul>' )
            .appendTo( this.$wrap.find( '.queueList' ) );
        this.$parent = this.$wrap
        if(StringUtil.isNotEmpty(this.modalId)){
            this.$parent = $("#" + this.modalId);
        }
        // 状态栏，包括进度和控制按钮
        this.$statusBar = this.$parent.find( '.uploaderStatusBar' );
        // 文件总体选择信息。
        this.$info = this.$statusBar.find( '.info' );
        // 上传按钮
        this.$upload = this.$parent.find( '.uploadBtn' );
        // 没选择文件之前的内容。
        this.$placeHolder = this.$wrap.find( '.placeholder' );
        this.$progress = this.$statusBar.find( '.progress' ).hide();
        // 添加的文件数量
        this.fileCount = 0;
        // 添加的文件总大小
        this.fileSize = 0;
        // 优化retina, 在retina下这个值是2
        this.ratio = window.devicePixelRatio || 1;
        // 缩略图大小
        this.thumbnailWidth = 110 * this.ratio;
        this.thumbnailHeight = 110 * this.ratio;
        // 可能有pedding, ready, uploading, confirm, done.
        this.state = 'pedding';
        // 所有文件的进度信息，key为file id
        this.percentages = {};
        this.uploader;
        this.checkMD5 = false;
        if(opt.isMore){
            switch (opt.fileType){
                case 'video':
                    opt.fileNumLimit = 30
                    this.checkMD5 = true;
                    break;
                default:
                    opt.fileNumLimit = 200
                    break
            }
        }
        this.uploader = this._create();
        this._bindEvent(this.uploader);
        return this.uploader;
    };

    $WebModalUpload.prototype = {
        /**
         * 创建webuploader对象
         */
        _create : function() {
            var server = 'uploadImage';
            if(this.opt.fileType == 'video'){
                server = "uploadVideo"
                this.opt.fileSizeLimit = 10 * 1024 * 1024 * 1024;
                this.opt.fileSingleSizeLimit = 1024 * 1024 * 1024;
            }
            var webUploader = WebUploader.create({
                auto:!this.opt.isMore,   // 自动上传
                pick: {
                    id: '#' + this.filePickerId,
                    multiple : this.opt.isMore
                },
                formData: this.opt.formData,
                dnd: '#' + this.dndAreaId,
                paste: '#' + this.uploaderId,
                chunked: false,
                chunkSize: 512 * 1024,
                server: '../' + server,
                // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                disableGlobalDnd: true,
                fileNumLimit: this.opt.fileNumLimit,
                fileSizeLimit: this.opt.fileSizeLimit,
                fileSingleSizeLimit: this.opt.fileSingleSizeLimit
            });
            return webUploader;
        },

        /**
         * 绑定事件
         */
        _bindEvent : function(uploader) {
            var me =  this;

            // 添加“添加文件”的按钮，
            uploader.addButton({
                id: '#' + me.continueFilePickerId,
                label: '继续添加'
            });


            uploader.on( 'dndAccept', function( items ) {
                var denied = false,
                    len = items.length,
                    i = 0;
                for ( ; i < len; i++ ) {
                    // 如果在列表里面
                    if ( items[ i ].type.indexOf( me.opt.fileType + '/' ) ) {
                        denied = true;
                        break;
                    }
                }
                return !denied;
            });

            // 文件上传过程中创建进度条实时显示。
            uploader.on( 'uploadAccept', function( file, object ) {
                if(object.code!=0){
                    boldFunc.showErrorMes(object.message)
                    uploader.removeFile( file.file );
                    return false;
                }else{
                    if(me.opt.uploadSuccess){
                        me.opt.uploadSuccess();
                    }
                }
            });

            uploader.onUploadProgress = function( file, percentage ) {
                var $li = $('#'+file.id),
                    $percent = $li.find('.progress span');

                $percent.css( 'width', percentage * 100 + '%' );
                me.percentages[ file.id ][ 1 ] = percentage;
                me._updateTotalProgress();
            };

            uploader.onFileQueued = function( file ) {
                var folderId = GlobalData.clickFolderId;
                if(StringUtil.isEmpty(folderId)){
                    boldFunc.showErrorMes("请选择文件夹")
                    uploader.removeFile(file)
                    return
                }
                uploader.options.formData.folderId = folderId;
                if( ! StringUtil.contains(me.opt.fileType + "/", file.type)){
                    boldFunc.showErrorMes("不支持的文件格式")
                    uploader.removeFile(file)
                    return
                }
                if(me.checkMD5){
                    me.$upload.addClass("disabled")
                    uploader.md5File( file ).then(function(val) {
                        uploader.on( 'uploadBeforeSend', function( block, data ) {
                            data.md5 = val;
                        });
                        var params = {
                            md5:val,
                            sourceName:file.name,
                            folderId:uploader.options.formData.folderId
                        }
                        getDataByURL("/service/platform/func/source/checkMD5", params, function (data) {
                            var json = data.data;
                            if(json){
                                file.status = "success";
                                uploader.skipFile( file );
                                if(me.opt.uploadSuccess){
                                    me.opt.uploadSuccess();
                                }
                            }
                            me.$upload.removeClass("disabled")
                        })
                    });
                }

                me.fileCount++;
                me.fileSize += file.size;

                if ( me.fileCount === 1 ) {
                    me.$placeHolder.addClass( 'element-invisible' );
                    me.$statusBar.show();
                }

                me._addFile( file );
                me._setState( 'ready' );
                me._updateTotalProgress();
            };

            uploader.onFileDequeued = function( file ) {
                me.fileCount--;
                me.fileSize -= file.size;

                if ( !me.fileCount ) {
                    me._setState( 'pedding' );
                }
                me._removeFile( file );
                me._updateTotalProgress();
            };

            uploader.on( 'all', function( type ) {
                switch( type ) {
                    case 'uploadFinished':
                        me._setState( 'confirm' );
                        break;

                    case 'startUpload':
                        me._setState( 'uploading' );
                        break;

                    case 'stopUpload':
                        me._setState( 'paused' );
                        break;

                }
            });

            uploader.onError = function( code ) {
                switch (code){
                    case "F_EXCEED_SIZE":
                        var byteHint = '';
                        switch (me.opt.fileType){
                            case 'video':
                                byteHint = me.opt.fileSingleSizeLimit/(1024 * 1024) + "M"
                                break;
                            default:
                                byteHint = me.opt.fileSingleSizeLimit/(1024) + "KB"
                                break;
                        }
                        boldFunc.showErrorMes("文件大小超出：" + byteHint);
                        break;
                    case "Q_EXCEED_NUM_LIMIT":
                        boldFunc.showErrorMes("上传文件数量超出限制：" + me.fileNumLimit);
                        break;
                    case "F_DUPLICATE":
                        boldFunc.showErrorMes("文件已存在");
                        break;
                    default:
                        boldFunc.showErrorMes("上传出错：" + code);
                        break;
                }
            };

            me.$upload.on('click', function() {
                if ( $(this).hasClass( 'disabled' ) ) {
                    return false;
                }
                if ( me.state === 'ready' ) {
                    me.uploader.upload();
                } else if ( me.state === 'paused' ) {
                    me.uploader.upload();
                } else if ( me.state === 'uploading' ) {
                    me.uploader.stop();
                }
            });
            me.$upload.addClass( 'state-' + me.state );
        },

        // 当有文件添加进来时执行，负责view的创建
        _addFile : function ( file ) {
            var me = this;
            var $li = $( '<li id="' + file.id + '">' +
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>'+
                '<p class="progress"><span></span></p>' +
                '</li>' ),

                $inBtn = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '</div>').appendTo( $li ),
                $singleProgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>'),
                text,
                showError = function( code ) {
                    switch( code ) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text( text ).appendTo( $li );
                };

            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                $wrap.text( '预览中' );
                me.uploader.makeThumb( file, function( error, src ) {
                    var img;
                    if ( error ) {
                        $wrap.text( '' );
                        return;
                    }
                    if( GlobalData.isSupportBase64 ) {
                        img = $('<img src="'+src+'">');
                        $wrap.empty().append( img );
                    } else {
                        // $.ajax('../../server/preview.php', {
                        //     method: 'POST',
                        //     data: src,
                        //     dataType:'json'
                        // }).done(function( response ) {
                        //     if (response.result) {
                        //         img = $('<img src="'+response.result+'">');
                        //         $wrap.empty().append( img );
                        //     } else {
                        //         $wrap.text("预览出错");
                        //     }
                        // });
                    }
                }, me.thumbnailWidth, me.thumbnailHeight );
                me.percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;
            }

            file.on('statuschange', function( cur, prev ) {
                if ( prev === 'progress' ) {
                    $singleProgress.hide().width(0);
                } else if ( prev === 'queued' ) {
                    $li.off( 'mouseenter mouseleave' );
                    $inBtn.remove();
                }

                // 成功
                if ( cur === 'error' || cur === 'invalid' ) {
                    showError( file.statusText );
                    me.percentages[ file.id ][ 1 ] = 1;
                } else if ( cur === 'interrupt' ) {
                    showError( 'interrupt' );
                } else if ( cur === 'queued' ) {
                    $info.remove();
                    $singleProgress.css('display', 'block');
                    me.percentages[ file.id ][ 1 ] = 0;
                } else if ( cur === 'progress' ) {
                    $info.remove();
                    $singleProgress.css('display', 'block');
                } else if ( cur === 'complete' ) {
                    $singleProgress.parent().hide();
                    me.percentages[ file.id ][ 1 ] = 1;
                    $li.append( '<span class="success"></span>' );
                }

                $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            });

            $li.on( 'mouseenter', function() {
                $inBtn.stop().animate({height: 30});
            });

            $li.on( 'mouseleave', function() {
                $inBtn.stop().animate({height: 0});
            });

            $inBtn.on( 'click', 'span', function() {
                var index = $(this).index();
                switch ( index ) {
                    case 0:
                        me.uploader.removeFile( file );
                        return;
                }
            });

            $info.on( 'click', '.retry', function() {
                me.uploader.retry();
            } );

            $info.on( 'click', '.ignore', function() {
//            alert( 'todo' );
            } );

            $li.appendTo( me.$queue );
        },


        // 负责view的销毁
        _removeFile : function ( file ) {
            var $li = $('#'+file.id);
            delete this.percentages[ file.id ];
            this._updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        },

        _updateTotalProgress : function () {
            var loaded = 0,
                total = 0,
                spans = this.$progress.children(),
                percent;
            $.each( this.percentages, function( k, v ) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            } );
            percent = total ? loaded / total : 0;
            var progress = Math.round( percent * 100 )
            var totalHint = progress  + '%';
            if(progress == 100){
                totalHint = '服务器解码中'
            }
            spans.eq( 0 ).text( totalHint );
            spans.eq( 1 ).css( 'width', progress + '%' );
            this._updateStatus();
        },

        _updateStatus : function () {
            var me = this;
            var text = '', stats;
            if ( me.state === 'ready' ) {
                text = '选中' + me.fileCount + '个文件，共' +
                    WebUploader.formatSize( me.fileSize ) + '。';
            } else if ( me.state === 'confirm' ) {
                stats = me.uploader.getStats();
                if ( stats.uploadFailNum ) {
                    text = '已成功上传' + stats.successNum+ '个文件，'+
                        stats.uploadFailNum + '个文件上传失败，<a class="retry" href="#">重新上传</a>失败文件或<a class="ignore" href="#">忽略</a>'
                }
            } else {
                stats = me.uploader.getStats();
                text = '共' + me.fileCount + '张（' +
                    WebUploader.formatSize( me.fileSize )  +
                    '），已上传' + stats.successNum + '张';
                if ( stats.uploadFailNum ) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }
            me.$info.html( text );
        },

        _setState : function ( val ) {
            var file, stats;
            if ( val === this.state ) {
                return;
            }
            this.$upload.removeClass( 'state-' + this.state );
            this.$upload.addClass( 'state-' + val );
            this.state = val;
            switch ( this.state ) {
                case 'pedding':
                    this.$placeHolder.removeClass( 'element-invisible' );
                    this.$queue.hide();
                    this.$statusBar.addClass( 'element-invisible' );
                    this.uploader.refresh();
                    break;

                case 'ready':
                    this.$placeHolder.addClass( 'element-invisible' );
                    $( '#' + this.continueFilePickerId ).removeClass( 'element-invisible');
                    this.$queue.show();
                    this.$statusBar.removeClass('element-invisible');
                    this.uploader.refresh();
                    break;

                case 'uploading':
                    $( '#' + this.continueFilePickerId ).addClass( 'element-invisible' );
                    this.$progress.show();
                    this.$upload.text( '暂停上传' );
                    break;

                case 'paused':
                    this.$progress.show();
                    this.$upload.text( '继续上传' );
                    break;

                case 'confirm':
                    this.$progress.hide();
                    $( '#' + this.continueFilePickerId ).removeClass( 'element-invisible' );
                    this.$upload.text( '开始上传' );

                    stats = this.uploader.getStats();
                    if ( stats.successNum && !stats.uploadFailNum ) {
                        this._setState( 'finish' );
                        return;
                    }
                    break;
                case 'finish':
                    stats = this.uploader.getStats();
                    if ( stats.successNum ) {
                        //                        alert( '上传成功' );
                    } else {
                        // 没有成功的图片，重设
                        this.state = 'done';
                        //                        location.reload();
                    }
                    break;
            }
            this._updateStatus();
        },
    };
    window.$WebModalUpload = $WebModalUpload;
}


var UploadFunc = {
    /**
     * 加载本地图片
     * @param obj
     */
    loadLocalImg : function (obj){
        var img = $(obj).next();//获取video元素
        var picImgUrlInfo = $(obj)[0].files[0];
        var url = URL.createObjectURL(picImgUrlInfo);
        img.attr("src", url);
        img.css("display","block");
        var form = $(obj).parents("form")
        var formData = new FormData(form[0]);
        formData.append('uploadImageFileName',$(obj).attr("id"));
        $.ajax({
            url: "../source/upLoadImg.do",
            type: "POST",
            dataType: "json",
            data: formData,
            async: false,
            cache: false,
            processData: false,
            contentType: false,
            success: function (object) {
                if(object.code==0){
                    var dataJson = $.parseJSON(object.data);
                    img.attr("src", GlobalData.fileUrl + dataJson.sourcePathUrl);
                    $(obj).prev().attr("value", dataJson.sourcePathUrl);
                }else{
                    boldFunc.showErrorMes(object.message)
                }
            }
        });
        $(obj).next().next().css("display","none");
    },
    getURL: function (file) {
        var url = null;
        if (window.createObjectURL != undefined) {
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
        return url;//     eg:     blob:http://localhost/671c3409-0047-44ec-bcba-89d63a439231
    },
    /**
     * 根据上传文件类型获取自定义的类型
     * @param fileType
     * @returns {*}
     */
    getTypeByFileType: function (fileType) {
        if(StringUtil.contains("image/", fileType)){
            return "image";
        }else if( StringUtil.contains("video/", fileType)){
            return "video";
        }else if( StringUtil.contains("application/x-zip-compressed", fileType)  || StringUtil.contains("application/octet-stream", fileType) ){
            return "zip";
        }else if( StringUtil.contains("application/vnd.openxmlformats-officedocument.", fileType) || StringUtil.contains("application/msword", fileType)
            || StringUtil.contains("application/pdf", fileType) || StringUtil.contains("application/vnd.ms-excel", fileType)
            || StringUtil.contains("application/vnd.ms-excel", fileType) || StringUtil.contains("openxmlformats-officedocument.spreadsheetml.sheete", fileType)){
            return "office";
        }
        return "";
    },
    uploadInit : function (arg) {
        var defaultValue = {
            id:"upload",
            width:200,
            height:200,
            supportImage:true,
            supportVideo:false,
            supportZip:false,
            supportOffice:false,
            supportMp3:false,
            addParams:null,     // 需要上传成功后，服务器返回的附加信息
            callback:null,
            uploadFile:null
        };
        var opt = $.extend(true, {},defaultValue, arg);
        var id = opt.id,
            width = opt.width,
            height = opt.height;
        var maxsize = 5000;
        var target_id = '#'+id;
        $(target_id).addClass('custom-upload')
        $(target_id).append('<div class="upload-div" ></div>')
        var uploader = WebUploader.create({
            multiple: false,
            server: GlobalData.basePath + "/bdfsUploadFile",
            pick: {
                id: target_id
            },
            fileSingleSizeLimit: maxsize * 1024 * 1024,
            duplicate: true,
            formData: {
                sourceType:1,   // 动态素材库
            }
        });
        var fileType = "image";
        var $uploadDiv = $(target_id).find(".upload-div");
        var func = {
            load :function (dataJson, fileType) {
                $uploadDiv.empty();
                $uploadDiv.css("background", "inherit")
                var srcUrl = GlobalData.fileUrl;
                if(StringUtil.contains("://", dataJson.sourceImg)){
                    srcUrl = "";
                }
                var src = srcUrl + dataJson.sourceImg;
                switch (fileType){
                    case "zip":case "Zip":case"office":case "Office":
                    $uploadDiv.append("<h4 ><b>" + src + "</b></h4>")
                    break;
                    default:
                        $uploadDiv.append("<img class='upload-image' src=\'" + src +
                            "\' style='max-width: " + width + "px;max-height:"+ height+ "px' />")
                        break;
                }
            },
            clearFile:function () {
                if(opt.uploadFile != null){
                    uploader.removeFile(opt.uploadFile)
                }
            },
            loadDefaultPng:function (id) {
                $("#" + id).val("")
                this.load({sourceImg: GlobalData.uploadDefaultPng}, "image")
            }
        }
        var loadSuccess = function (dataJson, file) {
            func.load(dataJson, fileType)
            if(opt.callback){
                opt.callback(dataJson)
            }
        }

        var $uploadDiv = $(target_id).find(".upload-div");
        $uploadDiv.css("width", width + "px").css("height", height + "px")

        //上传时
        uploader.onFileQueued = function( file ) {
            var isSupportFileType = false;
            var fileName = file.name;
            fileType = file.type;
            if(opt.supportImage && StringUtil.contains("image", UploadFunc.getTypeByFileType(fileType))){
                isSupportFileType = true;
                fileType = "image"
            }
            if( !isSupportFileType && opt.supportVideo && StringUtil.contains("video", UploadFunc.getTypeByFileType(fileType))){
                isSupportFileType = true;
                fileType = "video";
            }
            if( !isSupportFileType && opt.supportZip && StringUtil.contains("zip", UploadFunc.getTypeByFileType(fileType))){
                isSupportFileType = true;
                fileType = "zip";
            }
            if( ! isSupportFileType && opt.supportOffice && StringUtil.contains("office", UploadFunc.getTypeByFileType(fileType)) ){
                isSupportFileType = true;
                fileType = "office";
            }
            if( ! isSupportFileType && opt.supportMp3 &&  ( fileName.endWith(".mp3") || fileName.endWith(".wma") ) ){
                isSupportFileType = true;
                fileType = "mp3";
            }
            if( ! isSupportFileType ){
                boldFunc.showErrorMes("不支持的文件格式")
                return;
            }
            $uploadDiv.empty();
            var checkMD5 = false;
            switch (fileType){
                case "video":case "zip":
                checkMD5 = true;
                break;
                default:
                    break;
            }

            var $li = $( '<li id="' + file.id + '">' +
                '<p class="progress"><span></span></p>' +
                '</li>' );
            $uploadDiv.append( $li );

            if(checkMD5){
                uploader.md5File( file ).then(function(val) {
                    uploader.on( 'uploadBeforeSend', function( block, data ) {
                        data.md5 = val;
                    });
                    var params = {
                        md5:val,
                        fileType:fileType,
                        sourceName:file.name
                    }
                    getDataByURL("/service/platform/func/source/checkMD5", params, function (data) {
                        var json = data.data;
                        if(json){
                            file.status = "success";
                            uploader.skipFile( file );
                            loadSuccess(json, file)
                        }else{
                            uploader.upload(file.id);
                        }
                    })
                });
            }else{
                uploader.upload(file.id);
            }
        };
        //上传中
        uploader.on('uploadProgress', function(file, percentage) {
            var $li = $('#'+file.id),
                $percent = $li.find('.progress span');
            $percent.css("display", "block")
            $percent.css( 'width', percentage * 100 + '%' );
            if(percentage >= 1){
                $percent.text('服务器解码中')
            }
        });

        uploader.on('uploadBeforeSend', function(block, data) {
            if(opt.addParams != null){
                data.addParams = opt.addParams;
            }
            data.maxsize = maxsize;
            data.fileType = UploadFunc.getTypeByFileType(data.type)
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadAccept', function( file, object ) {
            $(target_id).find(".img_common").remove();
            if(object.code!=0){
                boldFunc.showErrorMes(object.message)
            }else{
                opt.uploadFile = file.file
                loadSuccess(object.data[0], file.file)
            }
        });
        return func;
    }
}

var VisFunc = {
    /**
     * 居中显示
     * @param canvas
     * @param x
     * @param y
     */
    mapZoomToXY : function (network, x, y){
        var options = {
            position: {
                x: x,
                y: y
            },
            scale: 3.0,
            offset: {
                x: 0,
                y: 0
            },
            animation: {
                duration: 10,
                easingFunction: "easeOutQuart"
            }
        };
        network.moveTo(options);
    },
    /**
     * 显示全部
     * @param canvas
     */
    mapGotoBorn : function (network){
        var options = {
            position: {
                x: 0,
                y: 0
            },
            scale: 1.0,
            offset: {
                x: 0,
                y: 0
            },
            animation: {
                duration: 100,
                easingFunction: "easeOutQuart"
            }
        };
        network.moveTo(options);
    },


    //更改节点图片选中效果
    changeChosenNode : function(values, id, selected, hovering) {
        values.shadowSize = 20;
        values.shadowColor = "#00c322";
    },

    //更改节点文字选中效果
    changeChosenNodeLabel : function (values, id, selected, hovering){
        values.mod = "bold";
        values.strokeWidth = 1;
        values.color = "#007f16"
        values.strokeColor = "#619a00";
    },
}

var MapFunc = {
    _defaults: {
        image:new Image(),
        mapSrc : null,
        network:null,
        nodes:null,
        elm:"canvas",       // 默认地图id为canvas
        resetSizeElm:"mapBase",       // 父级div的id。通过父级div的宽度再次调整地图大小
        canSelect:false,        // 是否能右键选择
        canRemove:false,
        screenWidth:0,
        options:{
            autoResize: true,
            width: '100%',
            height: '100%',
            locale: "CN",
            locales: {
                CN: {
                    edit: 'Edit',
                    del: '删除选中元素',
                    back: '返回',
                    addNode: '添加节点',
                    addEdge: '添加连线',
                    editNode: '修改节点',
                    editEdge: '修改连线',
                    addDescription: '在空白处点击放置',
                    edgeDescription: '点击一个节点到另一个节点上进行连接',
                    editEdgeDescription: 'Click on the control points and drag them to a node to connect to it.',
                    createEdgeError: 'Cannot link edges to a cluster.',
                    deleteClusterError: 'Clusters cannot be deleted.',
                    editClusterError: 'Clusters cannot be edited.'
                }
            },
            clickToUse: false,
            configure:{
                enabled: false,
                filter: 'nodes',
                showButton: true
            },
            nodes:{
                chosen: {
                    label: function (values, id, selected, hovering){
                        values.mod = "bold";
                        values.color = "#ffffff"
                        values.strokeWidth = 2;
                        values.strokeColor = "#5fbeaa"
                    }, //label文字也会变化
                    node: function(values, id, selected, hovering) {
                        // values.shadowSize = 1;
                        // values.shadowColor = "#5fbeaa";
                    },//被选中执行的效果函数
                },
                physics: false,//取消添加节点时的物理浮动，但是仍可拖动
                fixed: {//固定节点
                    x: true,
                    y: true
                },
                heightConstraint:{
                    valign:'bottom'
                },
                shape: 'image',//使用图片作为节点图标
                image: 'img/status_new.png',//定义节点使用的图片
                size: 2,//设置节点的大小
                value: 1,
            },
            interaction:{
                hover: false,
                navigationButtons: true,    // 显示地图操作按钮
                multiselect: true,//支持按住ctrl+左键多选
                zoomView: true,//控制是否可以缩放
                dragView: true,//控制canvas可被拖动
            }
        },
        selectNode:function (params) {
        },
        deselectNode:function (params) {
        },
        click:function (params) {
        },
        doubleClick:function (params) {
        },
        dragEnd:function (params) {
        },
        onLoad:function () {
        },
        /**
         * 右键选中节点回调
         * @param nodes
         */
        rightSelectNodes:function (nodes) {

        }
    },
    init:function (arg) {
        var defaultValue = this._defaults;
        var opt = $.extend(true, {},defaultValue, arg);
        if(opt.canRemove){
            delete opt.options.nodes.fixed;
        }
        var bgImage = defaultValue.image;
        bgImage.src = opt.mapSrc;
        bgImage.onload = function () {
            var edges = [];
            defaultValue.nodes = new vis.DataSet();
            var $canvas = $("#" + opt.elm);
            var mapDivWidth = $canvas.width();
            defaultValue.screenWidth = mapDivWidth;
            var bgImageWidth = bgImage.naturalWidth;
            var bgImageHeight = bgImage.naturalHeight;
            var scale = mapDivWidth * 1.0 / bgImageWidth;
            bgImageWidth = mapDivWidth;
            bgImageHeight = Math.ceil(bgImageHeight * scale);//返回一个比计算值大的最小正整数
            $canvas.css({
                "width": mapDivWidth,
                "height": bgImageHeight
            });
            setTimeout(function () {
                var width = $("#" + opt.resetSizeElm).width();
                if(width != null){
                    mapDivWidth = width;
                    bgImageWidth = bgImage.naturalWidth;
                    bgImageHeight = bgImage.naturalHeight;
                    var scale = mapDivWidth * 1.0 / bgImageWidth;
                    bgImageWidth = mapDivWidth;
                    bgImageHeight = Math.ceil(bgImageHeight * scale);//返回一个比计算值大的最小正整数
                    $canvas.css({
                        "width": mapDivWidth,
                        "height": bgImageHeight
                    });
                }
            }, 300)
            //初始化数据
            var data = {
                nodes: defaultValue.nodes,
                edges: edges
            };
            document.body.oncontextmenu = function () {
                return false;
            };//取消鼠标右键弹出菜单
            defaultValue.network = new vis.Network($canvas.get(0), data, opt.options);
            var canvas = defaultValue.network.canvas.frame.canvas;
            //绘制背景图
            defaultValue.network.on("beforeDrawing", function (ctx) {
                ctx.drawImage(bgImage, -(0.5 * bgImageWidth), -(0.5 * bgImageHeight), bgImageWidth, bgImageHeight);
            });
            defaultValue.network.on("selectNode", function (params) {
                opt.selectNode(params);
            });
            defaultValue.network.on("deselectNode", function (params) {
                opt.deselectNode(params);
            });
            defaultValue.network.on('click', function (params) {
                opt.click(params);
            });
            defaultValue.network.on('dragEnd', function (params) {
                opt.dragEnd(params);
            });
            defaultValue.network.on('doubleClick', function (params) {
                opt.doubleClick(params);
            });
            if(opt.canSelect){
                var rect = {}, drag = false;
                var ctx = canvas.getContext('2d');
                var drawingSurfaceImageData;
                //鼠标右键按下事件
                $canvas.on("mousedown", function(e) {
                    if (e.button == 2) {
                        var canvas = defaultValue.network.canvas.frame.canvas;
                        drawingSurfaceImageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
                        rect.startX = e.offsetX;
                        rect.startY = e.offsetY;
                        drag = true;
                        $canvas[0].style.cursor = "crosshair";
                    }
                });
                $canvas.on("mousemove", function(e) {
                    if (drag) {
                        ctx.putImageData(drawingSurfaceImageData, 0, 0);
                        rect.w = (e.offsetX) - rect.startX;
                        rect.h = (e.offsetY) - rect.startY ;
                        ctx.setLineDash([5]);
                        ctx.strokeStyle = "rgb(0, 102, 0)";
                        ctx.strokeRect(rect.startX, rect.startY, rect.w, rect.h);
                        ctx.setLineDash([]);
                        ctx.fillStyle = "rgba(0, 255, 0, 0.2)";
                        ctx.fillRect(rect.startX, rect.startY, rect.w, rect.h);
                    }
                });
                //右键鼠标按键弹起
                $canvas.on("mouseup", function(e) {
                    if (e.button == 2) {
                        ctx.putImageData(drawingSurfaceImageData, 0, 0);
                        drag = false;
                        $canvas[0].style.cursor = "default";
                        var nodesIdInDrawing = [];
                        var xRange = MapFunc.getStartToEnd(rect.startX, rect.w);
                        var yRange = MapFunc.getStartToEnd(rect.startY, rect.h);
                        var allNodes = defaultValue.nodes.get();
                        for (var i = 0; i < allNodes.length; i++) {
                            var curNode = allNodes[i];
                            var nodePosition = defaultValue.network.getPositions([curNode.id]);
                            var nodeXY = defaultValue.network.canvasToDOM({x: nodePosition[curNode.id].x, y: nodePosition[curNode.id].y});
                            if (xRange.start <= nodeXY.x && nodeXY.x <= xRange.end && yRange.start <= nodeXY.y && nodeXY.y <= yRange.end) {
                                nodesIdInDrawing.push(curNode.id);
                            }
                        }
                        if(nodesIdInDrawing.length > 0){
                            defaultValue.network.selectNodes(nodesIdInDrawing);
                            opt.rightSelectNodes(nodesIdInDrawing)
                        }
                    }
                });
            }

            $(".vis-zoomExtends").click(function () {
                MapFunc.mapGotoBorn()
            })
            opt.onLoad();
        }
    },
    /**
     * 居中显示
     * @param canvas
     * @param x
     * @param y
     */
    mapZoomToXY : function ( x, y){
        var options = {
            position: {
                x: x,
                y: y
            },
            scale: 3.0,
            offset: {
                x: 0,
                y: 0
            },
            animation: {
                duration: 10,
                easingFunction: "easeOutQuart"
            }
        };
        this._defaults.network.moveTo(options);
    },
    /**
     * 显示全部
     * @param canvas
     */
    mapGotoBorn : function (){
        var options = {
            position: {
                x: 0,
                y: 0
            },
            scale: 1.0,
            offset: {
                x: 0,
                y: 0
            },
            animation: {
                duration: 100,
                easingFunction: "easeOutQuart"
            }
        };
        this._defaults.network.moveTo(options);
    },
    mapZoomToNode:function (node) {
      MapFunc.mapZoomToXY(node.x, node.y)
    },
    getSelectedNodes :function () {
        return this._defaults.network.getSelectedNodes();
    },
    selectNodes :function (idArr) {
        this._defaults.network.selectNodes(idArr);
    },
    selectNode :function (id) {
        var arr = [];
        arr.push(id)
        MapFunc.selectNodes(arr)
    },
    /**
     * 加入节点
     * @param node
     */
    addNode:function (node) {
        this._defaults.nodes.add(node);
    },
    clearNodes:function () {
        this._defaults.nodes.clear();
    },
    removeNodeById:function (id) {
        var node = MapFunc.getNodeById(id)
        if(node != null){
            this._defaults.nodes.remove(node);
        }
    },
    getNodeById:function (id) {
        return this._defaults.nodes.get(id)
    },
    /**
     * 获取屏幕
     * @returns {number}
     */
    getScreenWidth:function () {
        return this._defaults.screenWidth
    },
    /**
     * 获取屏幕
     * @returns {number}
     */
    getRealPos:function (pos, screenWidth) {
        var realPos = pos
        if(screenWidth != null && screenWidth != 0){
            realPos =  MapFunc.getScreenWidth()/screenWidth* pos
        }
        return realPos;
    },
    /**
     * 设置背景图片
     * @param src
     */
    setImageSrc:function (src) {
        this._defaults.image.src = src;
    },
    getStartToEnd : function (start, theLen) {
        return theLen > 0 ? {start: start, end: start + theLen} : {start: start + theLen, end: start};
    },
}

var FolderFuc={
    change:function (){
        $(".folders_class").unbind("mousedown").bind("mousedown", function (e) {
            if (event.which == 3) {
                menu.style.left=e.clientX+'px';
                menu.style.top=e.clientY+'px';
                menu.style.width='120px';
                menu.style.height='80px';
                GlobalData.thisFolder=this;
                var $this=$(this);
                var canDel=$this.attr("canDel");
                var permissionId=$this.attr("permissionId");
                var permissionName=$this.attr("permissionName");
                if(canDel=="false"){
                    var i=1;
                    $("#menu a").each(function(){
                        var $this_1=$(this);
                        $this_1.removeClass("item-disabled");
                        $this_1.removeClass("item-activity");
                        if(i==1){
                            $this_1.addClass("item-activity");
                            $this_1.attr("onclick","folder.create(\""+permissionId+"\")");
                        }else if(i==2){
                            $this_1.addClass("item-disabled");
                        }else if(i==3){
                            $this_1.addClass("item-disabled");
                        }
                        i++;
                    });
                }else{
                    var i=1;
                    $("#menu a").each(function(){
                        var $this_1=$(this);
                        $this_1.removeClass("item-disabled");
                        $this_1.addClass("item-activity");
                        if(i==1){
                            $this_1.attr("onclick","folder.create(\""+permissionId+"\")");
                        }else if(i==2){
                            $this_1.attr("onclick","folder.update(\""+permissionId+"\",\""+permissionName+"\")");
                        }else if(i==3){
                            $this_1.attr("onclick","folder.del(\""+permissionId+"\")");
                        }
                        i++;
                    });
                }
            }
        });
    }
}

var DateTimePickerFunc = {
    init : function (arg) {
        var defaultValue = {
            id:null,
            language:"zh-CN",
            todayHighlight:true,
            todayBtn:true,
            selectDay:true,
            selectHours:false,
            selectMonth:false,
            showWeeks:false,
            selectMinutes:false,
            autoclose:1,
            zIndex:10000,
        };
        var opt = $.extend(true, {},defaultValue, arg);
        if( ! opt.showWeeks){
            if(opt.selectMonth){
                opt.format = 'yyyy-mm';
                opt.minView = 3;
                opt.startView = 3;
            }else if(opt.selectHours){
                opt.format = 'yyyy-mm-dd hh:00:00';
                opt.minView = 1;
                opt.startView = 2;
            }else if(opt.selectMinutes){
                opt.format = 'yyyy-mm-dd hh:ii:00';
                opt.minView = 0;
                opt.startView = 2;
            }else if(opt.selectDay){
                opt.format = 'yyyy-mm-dd';
                opt.minView = 2;
                opt.startView = 2;
            }
            $('#' + opt.id).datetimepicker(opt);
        }else{
            opt.format = 'yyyy-mm-dd';
            opt.calendarWeeks = true;
            $('#' + opt.id).datepicker(opt);
        }
    }
}


var DateUtil={
    isLeapYear:function (year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    },
    strToDate:function (str) {
        return new Date(str.replace(/-/g,"/"));
    },
    /**
     * 获取当前日期前后N天的方法:
     * @param AddDayCount
     * @returns {string}
     */
    getDayBeforeOrAfter :function (AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate();
        return y+"-"+m+"-"+d;
    },
    getMonthDays:function (year, month) {
        return [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month] || (DateUtil.isLeapYear(year) ? 29 : 28);
    },
    /**
     * 获取今天的日期
     * @returns {string}
     */
    getNowFormatDate :function () {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    },
    /**
     * 获取上个月第一天
     * @returns {string}
     */
    getLastMonthFirstDayFormatDate :function () {
        var nowdays = new Date();
        var year = nowdays.getFullYear();
        var month = nowdays.getMonth();
        if(month==0)
        {
            month=12;
            year=year-1;
        }
        if (month < 10) {
            month = "0" + month;
        }
        return year + "-" + month + "-" + "01";
    },
    /**
     * 获取本月第一天
     * @returns {string}
     */
    getMonthFirstDayFormatDate :function () {
        var date_ = new Date();
        var year = date_.getFullYear();
        var month = date_.getMonth() + 1;
        if(month < 10){
            month = "0" + month
        }
        return year + '-' + month + '-01';
    },
    getWeekNumber:function () {
        var now = new Date(),
            year = now.getFullYear(),
            month = now.getMonth(),
            days = now.getDate();
        //那一天是那一年中的第多少天
        for (var i = 0; i < month; i++) {
            days += DateUtil.getMonthDays(year, i);
        }

        //那一年第一天是星期几
        var yearFirstDay = new Date(year, 0, 1).getDay() || 7;

        var week = null;
        if (yearFirstDay == 1) {
            week = Math.ceil(days / yearFirstDay);
        } else {
            days -= (7 - yearFirstDay + 1);
            week = Math.ceil(days / 7) + 1;
        }
        return week;
    },
    getWeekByStr:function (dateTimeStr) {
        var now = new Date(dateTimeStr.replace(/-/,"/")),
        year = now.getFullYear(),
            month = now.getMonth(),
            days = now.getDate();
        //那一天是那一年中的第多少天
        for (var i = 0; i < month; i++) {
            days += DateUtil.getMonthDays(year, i);
        }
        //那一年第一天是星期几
        var yearFirstDay = new Date(year, 0, 1).getDay() || 7;

        var week = null;
        if (yearFirstDay == 1) {
            week = Math.ceil(days / yearFirstDay);
        } else {
            days -= (7 - yearFirstDay + 1);
            week = Math.ceil(days / 7) + 1;
        }
        return week-1;
    }
}