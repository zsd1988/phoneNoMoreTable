
var ProjectFunc={
    /**
     * 获取是否签约
     * @param status
     * @returns {boolean}
     */
    getIsSignByStatus : function (status) {
        var isSign = false     // 是否签约
        switch (status){
            case "Protocol":case "Contract":
            isSign = true;
            break;
            default:
                break;
        }
        return isSign;
    },
    /**
     * 设置video音量
     * @param id
     */
    setVolume :function (id) {
        var Media = document.getElementById(id);
        var eventTester = function(e){
            Media.addEventListener(e,function(){
                GlobalData.volume = Media.volume
            },false);
        }
        eventTester("volumechange");
        Media.volume = GlobalData.volume;
    },
    getIsWaitingByStatus : function (status) {
        var isSign = false     // 是否签约
        switch (status){
            case "Protocol":case "Contract":
            isSign = true;
            break;
            default:
                break;
        }
        return isSign;
    },
    getMap:function (dataJson) {
        var title = '';
        var color = "black";
        var strokeColor = null;
        var uploadColor = "#5d9cec"
        var waitingColor = "#ffbd4a"
        var passColor = "#81c868"
        var whiteColor = "white"
        var isMyself = dataJson.isMyself
        var strokeWidth = 0;
        if(isMyself){
            strokeWidth = 2;
            color = whiteColor;
        }
        switch (dataJson.type){
            case "Uploaded":
                if(isMyself){
                    strokeColor = uploadColor
                }else{
                    color = uploadColor;
                }
                break;
            case "Waiting":
                if(isMyself){
                    strokeColor = waitingColor
                }else{
                    color = waitingColor;
                }
                break;
            case "Pass":
                if(isMyself){
                    strokeColor = passColor
                }else{
                    color = passColor;
                }
                var projectClient = dataJson.projectClient
                title += '<div>商家信息</div><div>' + projectClient.brand + '</div>' +
                '<div>联系人:' + projectClient.name + '</div>' +
                '<div>联系电话:' + projectClient.phone + '</div>' +
                '<div>签约面积:' + projectClient.area + 'm²</div><div>铺位信息</div>'
                break;
            default:
                color = "black"
                break;
        }
        var des = "";
        if(StringUtil.isNotEmpty(dataJson.des)){
            des = '<div>铺位备注:' + dataJson.des + '</div>'
        }
        var map = {
            id: dataJson.id,
            x: dataJson.posX,
            y: dataJson.posY,
            data:dataJson,
            label:dataJson.num,
            font:{color:color,
                strokeWidth:strokeWidth,
                mod :"bold"
            },
            shape:"text",
            title: title + '<div>计租面积:' + dataJson.rentsArea + '</div>' + des
        };
        map.font.strokeColor = strokeColor;
        return map;
    },
    getChangShaMap:function (projectClient, posX, posY) {
        var isSign = false     // 是否签约
        switch (projectClient.signType){
            case "SignPass":
                isSign = true;
                break;
            default:
                break;
        }
        var isWaiting = false;
        if(!isSign){
            switch (projectClient.status){
                case "Protocol":case "Contract":
                isWaiting = true;
                break;
                default:
                    break;
            }
        }
        var image = "img/status_new.png"
        var map = {
            id: projectClient.id,
            x: posX,
            y: posY,
            image: image,
            title: '<div>' + projectClient.brand + '</div>' +
            '<div>联系人:' + projectClient.name + '</div>' +
            '<div>联系电话:' + projectClient.phone + '</div>'
        };
        return map;
    }
}

/**
 * 通用模板使用规则
 *  1、 加入 <input type="text" class="form-control" style="width: 100% !important;" id="selectProjectName">输入框，点击输入框即可弹出模板
 *  2、<div id="selectProjectDiv"></div>，在page页面最底部加入div用来动态写入模板载体
 *  3、初始化
        SelectProjectModal.selectProjectModalInit({
            callback:function (data) {
                $("#selectProjectName").val(data.name)
            }
        })
 */

var SelectProjectModal = {
    queryProjectModalTableParams : function () {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            name:$("#selectProjectModalName").val()
        }
        return param;
    },
    querySelectProjectModal : function () {
        $("#selectProjectModalTable").bootstrapTable("refresh");
    },
    resetSelectProjectModal : function () {
        $("#selectProjectModalName").val('')
    },
    selectProjectModalInit : function (arg) {
        var Defaults = {
            divId:"selectProjectDiv",
            inputId:"selectProjectName",
            json:{},
            callback:function (data) {

            }
        };
        var opt = $.extend({}, this._defaults, Defaults, arg);

        // 页面底部先加入selectProjectDiv的div
        var selectProjectDiv = $("#" + opt.divId)
        selectProjectDiv.setTemplateURL("tpl/selectProject.html");
        selectProjectDiv.processTemplate(opt.json);

        var $modal = $("#selectProjectModal")
        $modal.on('show.bs.modal', function () {
            $table.bootstrapTable("refresh");
        })
        $("#" + opt.inputId).click(function () {
            $modal.modal("show")
        })

        var $table = $("#selectProjectModalTable")
        TableFunc.showTable({
            url:"/service/platform/login/systemSetting/project/list",
            elm:"selectProjectModalTable",
            queryParams:SelectProjectModal.queryProjectModalTableParams,
            onClickRow: function (row, $element, field) {
                $modal.modal("hide");
                opt.callback(row)
            },
            columns: [
                {
                    radio:true,
                    align : 'center',
                },
                {
                    field: 'name',
                    title: '项目名',
                    align: 'center',
                }]
        });

    }
}