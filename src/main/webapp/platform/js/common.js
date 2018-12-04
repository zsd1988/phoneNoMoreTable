//# sourceURL=common.js

/**
 * 写入localStorage缓存
 *
 */
function saveData(key, data){
    localStorage.setItem(key, data);
}

/**
 * 读取缓存
 *
 */
function loadData(key){
    return localStorage.getItem(key);
}

/**
 * 缓存对象
 */
function saveObjectData(key, data){
    localStorage.setItem(key, JSON.stringify(data));
}

/**
 * 读取缓存对象
 */
function loadObjectData(key){
    var str = localStorage.getItem(key);
    if(!str){
        return null;
    }
    return JSON.parse(str);
}

/**
 * 删除本地的永久缓存
 *
 */
function removeItem(key){
    localStorage.removeItem(key);
}

Array.prototype.remove = function(s) {
    for (var i = 0; i < this.length; i++) {
        if (s == this[i])
            this.splice(i, 1);
    }
}

/**
 * Simple Map
 *
 *
 * var m = new Map();
 * m.put('key','value');
 * ...
 * var s = "";
 * m.each(function(key,value,index){  
 *      s += index+":"+ key+"="+value+"/n";  
 * });
 * alert(s);
 *
 * @author dewitt
 * @date 2008-05-24
 */
function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };

    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };

    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        this.keys.remove(key);
        this.data[key] = null;
    };

    /**
     * 遍历Map,执行处理函数
     *
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };

    /**
     * 获取键值数组(类似<a href="http://lib.csdn.net/base/javase" class='replace_word' title="Java SE知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java</a>的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[i]
            };
        }
        return entrys;
    };

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };

    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };

    /**
     * 重写toString
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++,s+=','){
            var k = this.keys[i];
            s += k+"="+this.data[k];
        }
        s+="}";
        return s;
    };
}


function testMap(){
    var m = new Map();
    m.put('key1','Comtop');
    m.put('key2','南方电网');
    m.put('key3','景新花园');
    alert("init:"+m);

    m.put('key1','康拓普');
    alert("set key1:"+m);

    m.remove("key2");
    alert("remove key2: "+m);

    var s ="";
    m.each(function(key,value,index){
        s += index+":"+ key+"="+value+"/n";
    });
    alert(s);
}

var _topPageMap = new Map();
var _topCurrentContent = null;
var _topCurrentIndex = 0;
var _isGotoTopPage = false;

function gotoTopPage(index) {
    var map = _topPageMap.get(index)
    if(map != null){
        _topCurrentContent = map.get("content")
        gotoPage(map.get("pageUrl"), null, "topZ")
        _isGotoTopPage = true;
    }
}

/**
 * 去指定页面
 * @param pageUrl
 * @param json
 */
function gotoPage(pageUrl,json,folderType){
    if(StringUtil.isEmpty(GlobalData.permissionsTag)){
        GlobalData.permissionsTag = folderType;
    }
    if(GlobalData.isBack){
        var returnId=GlobalData.returnId;
        if(returnId>1){
            GlobalData.returnId=parseInt(returnId)-1;
            pageUrl=GlobalData.returnMap[GlobalData.returnId].pageUrl;
            json=GlobalData.returnMap[GlobalData.returnId].json;
            folderType=GlobalData.returnMap[GlobalData.returnId].folderType;
        }
    }else{
        saveReturnMap(pageUrl,json,folderType);
    }
    GlobalData.beforePageUrl = pageUrl;
    var urlArr = pageUrl.split("?")
    var pageUrl = urlArr[0]
    GlobalData.isBack=false;
    var elm = $('#pageContent');
    if(StringUtil.isNotEmpty(GlobalData.currentView) && ! _isGotoTopPage){
        var map = new Map();
        map.put("content", elm.html())
        map.put("pageUrl", GlobalData.currentView)
        _topPageMap.put(_topCurrentIndex + "", map);
        _topCurrentIndex++;
    }
    _isGotoTopPage = false;
    elm.empty();
    if(pageUrl != ''){
        var root = "page/" + pageUrl;
        var initJS = true;
        if(folderType != null && folderType == "topZ"){
            elm.append(_topCurrentContent)
            initJS = false;
        }else{
            elm.setTemplateURL(root + ".html?time=" + new Date().getTime());
            elm.processTemplate(json);

        }
        loadApi.loadBackJS(root + '.js?time=' + new Date().getTime(),function(){
            if(StringUtil.isNotEmpty(GlobalData.currentView)){
                var quitFuncName="quit_" + GlobalData.currentView.replace(new RegExp('/',"gm"), '_')+'()';
                setTimeout(function(){
                    try{
                        eval(quitFuncName);
                    }catch (e){
                    }
                },200)
            }
            var initFuncName="init_"+pageUrl.replace(new RegExp('/',"gm"), '_')+'()';
            setTimeout(function(){
                if(initJS){
                    eval(initFuncName);
                }
            },200)
            GlobalData.currentJson = json;
            GlobalData.beforeView = GlobalData.currentView;
            GlobalData.currentView = pageUrl;
        });
    }
}

/**
 * 页面返回
 */
function goBack() {
    if(StringUtil.isNotEmpty(GlobalData.beforeView)){
        GlobalData.isBack=true;
        gotoPage(GlobalData.beforeView)
    }else{
        window.location.href = GlobalData.basePath;
    }
}

/**
 * 请求网络地址
 * @param url
 * @param data
 * @param callback
 */
function getDataByURL(url, data, okcall) {
    var timestamp = new Date().getTime();
    if (data) {
        if(typeof data == "object"){
            data["timestamp"] = timestamp;
        }else{
            data += "&timestamp=" + timestamp;
        }
    }else{
        data = {timestamp:timestamp}
    }
    $.ajax({
        url: GlobalData.basePath + url,
        type: "POST",
        dataType: "json",
        data: data,
        success: function (object) {
            if(object.code==0){
                var objectTemp = object;
                var dataJson;
                if (object.data != null && object.data != "") {
                    dataJson = $.parseJSON(object.data);
                }
                if (dataJson)
                    objectTemp.data = dataJson;
                okcall.apply(this, [objectTemp]);
            }else{
                boldFunc.showErrorMes(object.message)
            }
        },
        error: function (err) {
            console.log("error");
        }
    });
}


/**
 * 请求网络地址
 * @param url
 * @param data
 * @param callback
 */
function getFormDataByURL(url, data, okcall) {
    $.ajax({
        url: GlobalData.basePath + url,
        type: "POST",
        dataType: "json",
        data: data,
        async: false,
        cache: false,
        processData: false,
        contentType: false,
        success: function (object) {
            if(object.code==0){
                var objectTemp = object;
                var dataJson;
                if (object.data != null && object.data != "") {
                    dataJson = $.parseJSON(object.data);
                }
                if (dataJson)
                    objectTemp.data = dataJson;
                okcall.apply(this, [objectTemp]);
            }else{
                boldFunc.showErrorMes(object.message)
            }
        },
        error: function (err) {
            console.log("error");
        }
    });
}


function saveReturnMap(pageUrl,json,folderType){
    var gotoPageData=new Object();
    gotoPageData.pageUrl=pageUrl;
    gotoPageData.json=json;
    gotoPageData.folderType=folderType;
    var returnId=GlobalData.returnId;
    GlobalData.returnId=parseInt(returnId)+1;
    GlobalData.returnMap[GlobalData.returnId]=gotoPageData;
}