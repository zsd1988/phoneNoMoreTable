//# sourceURL=customerGrouping.js
function init_customerManagement_customerGrouping() {

    var _customerGroupId;
    var projectId;
    var groupType;
    var _loadGroupId;
    var _editData;
    var _clientPhone;
    var _clientId;
    var _isClick = false;   // 是否点击查询

    zTreeOnClick=function(event, treeId, treeNode) {
        if(StringUtil.isNotEmpty(treeNode.type)&&treeNode.type!='allGroup'){
            $("#clientStatusOption").selectpicker('val',"");
            $("#selectPhone").val("");
            projectId="";
            groupType="";
            _loadGroupId = null;
            if(treeNode.type=='public'){//公共资源
                _customerGroupId="public";
                if(GlobalData.isManager){
                    _loadGroupId = "public";
                }
            }if(treeNode.type=='publicInterview'){//公共资源
                _customerGroupId="publicInterview";
                if(GlobalData.isManager){
                    _loadGroupId = "publicInterview";
                }
            }else if(treeNode.type=='group'){//分组
                _loadGroupId = treeNode.groupId;
                _customerGroupId=treeNode.groupId;
            }else if(treeNode.type=='project'){//项目
                _loadGroupId = treeNode.groupId;
                _customerGroupId=treeNode.groupId;
                projectId=treeNode.projectId;
            }else if(treeNode.type=='groupType'){//类型
                _loadGroupId = treeNode.groupId;
                _customerGroupId=treeNode.groupId;
                projectId=treeNode.projectId;
                groupType=treeNode.id;
            }
            refreshTable();
        }
    };

    zTreeOnDbClick=function(event, treeId, treeNode) {
        if(treeNode != null && StringUtil.isNotEmpty(treeNode.groupName)){
            $("#clientGroupId").val(treeNode.id);
            $("#clientGroupName").val(treeNode.groupName);
            $("#addClientGroupButton").click();
        }
    };

    var setting;
    if(GlobalData.isManager){
        setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                }
            },

            view: {
                showIcon: false,
            },

            callback: {
                onClick:  this.zTreeOnClick,
                onDblClick: this.zTreeOnDbClick,
            }

        }
    }else{
        setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                }
            },

            view: {
                showIcon: false,
            },

            callback: {
                onClick:  this.zTreeOnClick
            }

        }
    }

    
    init = function () {
        //判断菜单权限
        if( GlobalData.functionIdArr.contains("34ecdf7adb1344608bcb305466b7bc95")){
            $("#addClientGroupButton").css("display","inline-block");
            $("#addOrSelectClient").css("display","block");
        }
        if(GlobalData.isManager || GlobalData.isInterview){
            $("#callPhone").show()
        }
        _customerGroupId="public";
        if(GlobalData.isInterview){
            _customerGroupId = "publicInterview"
            $("#detailTransClient").show()
        }
        $("#detailModal").on("show.bs.modal", function () {
            $("#detailModal").css("overflow-y", "auto")
        })
        $("#con-move-modal").on("hide.bs.modal", function () {
            $("#detailModal").css("overflow-y", "auto")
        })

        //获取下拉框数据
        var params = {params:"Project,ClientStatus,YearTag,Area"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            var clientReviewStatus = JSON.parse(dataJson.ClientStatus )
            SelectPickerFunc.loadLocalData('status', clientReviewStatus, true)
            SelectPickerFunc.loadLocalData('reviewStatus', clientReviewStatus, true)
            SelectPickerFunc.loadLocalData('confirmStatus', clientReviewStatus, true)
            SelectPickerFunc.loadLocalData('yearTag', JSON.parse(dataJson.YearTag ), true)
            SelectPickerFunc.loadMap('projectId',dataJson.Project, true)
            SelectPickerFunc.loadLocalData('area', JSON.parse(dataJson.Area ), true)
        })
        var url="/service/platform/noLogin/clientManager/clientGroupController/clientGroupNameOption";
        //获取用户的菜单权限
        var param = {
            groupId:_customerGroupId
        }
        getDataByURL(url, param, function (data) {
            var clientGroupMapList= data.data.clientGroupMapList;
            $.fn.zTree.init($("#clientGroupList"), setting,clientGroupMapList);
            var clientGroupTree=$.fn.zTree.getZTreeObj("clientGroupList");
            var node = clientGroupTree.getNodeByParam("type", "public", null);
            clientGroupTree.selectNode(node);
        })
        TableFunc.showTable({
            url: "/service/platform/login/clientManager/client/list",
            queryParams: clientGroupingParam,
            pageSize:50,
            pageList: [50,100,200],
            pagination: true,
            columns: getColums()
        });


        $("#detailModal").on("show.bs.modal", function () {
            if(GlobalData.isInterview){
                $('#status').attr("disabled",'disabled');
                $('#reviewStatus').attr("disabled",'disabled');
                $('#onceStatus').attr("disabled",'disabled');
            }
        })
    }

    toCallPhone = function () {
        var callPhone = $("#selectPhone").val();
        if(StringUtil.isEmpty(callPhone)){
            boldFunc.showErrorMes("请输入手机号")
            return
        }
        boldFunc.showConfirm("确定拨打电话： " + callPhone, function () {
            var params = {
                phone:callPhone
            };
            getDataByURL("/service/platform/login/systemManager/callRecord/toCallPhone", params, function (data) {
                var dataJson = data.data
                if(dataJson.message != null){
                    boldFunc.notification(dataJson.message)
                }else{
                    boldFunc.notification("正在呼叫请稍等")
                }
            })
        })
    }


    getColums = function () {
        var colums = [
            {
                field: 'name',
                title: '客户姓名',
                align: 'center',
            },
            {
                field: 'phone',
                title: '被叫号码',
                align: 'center',
                formatter: function (value, row, index) {
                    if(GlobalData.functionIdArr.contains("1bd24c8491134d988639ec6c3e7496c6")){
                        var a=value;
                        if(value.length==11){
                            a= value.substr(0, 3) + '****' + value.substr(7);
                        }else if(value.length==12){
                            a = value.substr(0, 4) + '****' + value.substr(8);
                        }
                        return a;
                    }else{
                        return value;
                    }
                }
            },
            {
                field: 'statusStr',
                title: '客户状态',
                align: 'center',
            },
            {
                field: 'reviewStatusStr',
                title: '审核状态',
                align: 'center',
            },
            {
                field: 'confirmStatusStr',
                title: '约访状态',
                align: 'center',
            },
            {
                field: 'projectName',
                title: '项目名称',
                align: 'center',
            },
            {
                field: 'des',
                title: '备注',
                align: 'center',
            }
        ]
        if(_loadGroupId != null && _loadGroupId == "publicInterview"){
            colums.push({
                field: 'lastUpdateTimeStr',
                title: '最后更新时间',
                align: 'center',
            })
        }else{
            colums.push({
                field: 'updateTimeStr',
                title: '分配转移时间',
                align: 'center',
            })
        }
        if(GlobalData.functionIdArr.contains("93fb73ad52a441bb8aef960681ba985b")){
            colums.push(
                {
                    field: 'groupId',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light" data-toggle="modal" data-target="#con-project-modal" onclick="move(' + index + ')">移组</button> '
                        return a;
                    }
                }
            )
        }
        colums.push(
            {
                field: 'id',
                title: '详情',
                valign: 'middle',
                align: 'center',
                formatter: function (value, row, index) {
                    var a = '<button type="button" class="btn btn-success waves-effect waves-light" data-toggle="modal" data-target="#detailModal" onclick="getClientInfo('+index+')">详情</button> '
                    return a;
                }
            }
        )
        return colums;
    }

    getClientInfo=function (index) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var params = {
            id: _editData.id,
        }
        getDataByURL("/service/platform/login/clientManager/client/getClient", params, function (data) {
            var dataJson = data.data;
            _clientPhone = dataJson.phone
            _clientId = dataJson.id;
            dataJson = handlePhone(dataJson)
            FormFunc.loadObject(dataJson,"#detailModal");
            // 获取通话记录
            var colums = [
                {
                    field: 'clientName',
                    title: '客户名称',
                    align: 'center',
                },
                {
                    field: 'phone',
                    title: '被叫号码',
                    align: 'center',
                    formatter: function (value, row, index) {
                        row = handlePhone(row)
                        var str = row.phone;
                        return str;
                    }
                },
                {
                    field: 'clientReviewStatusStr',
                    title: '审核状态',
                    align: 'center',
                },
                {
                    field: 'projectName',
                    title: '项目名',
                    align: 'center',
                },
                {
                    field: 'clientDes',
                    title: '备注',
                    align: 'center',
                },
                {
                    field: 'time',
                    title: '通话时长',
                    align: 'center',
                },
                {
                    title: '录音',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        if(StringUtil.isNotEmpty(row.recordPath)){
                            var a = '<button type="button" class="playing" onclick="playRecordInDetail(' + index + ')"><img src="assets/images/playing.png"></button>';
                            return a;
                        }
                    }
                },
            ]
            if(GlobalData.isCS || GlobalData.isInterview){
                colums.push({
                    title: '话机播放',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        if(StringUtil.isNotEmpty(row.recordPath)){
                            var a = '<button type="button" class="playing" onclick="extPlayRecord(' + index + ')"><img src="assets/images/playing.png"></button>';
                            return a;
                        }
                    }
                })
            }
            colums.push(
                {
                    field: 'createTimeStr',
                    title: '时间',
                    align: 'center',
                })
            if( ! GlobalData.isCS){
                colums.push({
                    field: 'userName',
                    title: '跟进人',
                    align: 'center'
                })
            }
            TableFunc.showTable({
                url: "/service/platform/login/systemManager/callRecord/listByPhone",
                queryParams: queryParamsCallRecord,
                pagination: true,
                elm:"callRecordTable",
                columns: colums
            })
            $("#detailModal").modal("show")
            $("#callRecordTable").bootstrapTable('refresh')
        });
    }

    /**
     * 点击modal里面的播放录音
     * @param index
     */
    playRecordInDetail = function(index){
        var editData = $("#callRecordTable").bootstrapTable('getData')[index];
        if(editData.recordPath != null){
            var $indexMyMp3 = $("#myMp3")
            $indexMyMp3.show()
            ProjectFunc.setVolume("myMp3");
            $indexMyMp3.attr("autoplay", "autoplay")
            $indexMyMp3.attr("src", editData.recordPath)
        }
    }

    extPlayRecord = function (index) {
        var editData = $("#callRecordTable").bootstrapTable('getData')[index];
        indexExtPlayRecord(editData.recordPath)
    }

    callRecordRefresh = function () {
        $("#callRecordTable").bootstrapTable('refresh')
    }

    queryParamsCallRecord = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            clientId:_clientId,
            phone:_clientPhone,
        }
        return param;
    }


    closeClient = function () {
        var myVideo = document.getElementById('myMp3');
        myVideo.pause();
        $(myVideo).attr("src", "")
        $(myVideo).hide()
    }


    // 拨打电话
    callPhone = function () {
        if(StringUtil.isNotEmpty(GlobalData.extNum)){
            boldFunc.showConfirm("确定拨打：" + _clientPhone, function () {
                var params = {
                    clientId:_editData.id,
                    userId:GlobalData.userId
                }
                getDataByURL("/service/platform/login/systemManager/callRecord/create", params, function (data) {
                    var dataJson = data.data
                    if(dataJson.message != null){
                        boldFunc.notification(dataJson.message)
                    }else{
                        boldFunc.notification("正在呼叫请稍等")
                    }
                })
            })
        }
    }

    detailToTransClient = function () {
        $("#con-move-modal").modal("show")
        FormFunc.loadObject(_editData, "#con-move-modal")
    }

    refreshTable = function () {
        $("#table").bootstrapTable('destroy');
        TableFunc.showTable({
            url: "/service/platform/login/clientManager/client/list",
            queryParams: clientGroupingParam,
            pageSize:50,
            pageList: [50,100,200],
            pagination: true,
            columns: getColums()
        })
    }

    setReviewStatus = function(){
        $("#reviewStatus").selectpicker('val',$("#status").selectpicker('val'));
    }

    setConfirmStatus = function(){
    }

    getCommonSpeech = function(){
        $("#des").val($("#des").val()+GlobalData.hintDes);
    }

    saveClientModal = function () {
        var params = $("#detailModal").serializeHumpObject();
        params.status = $("#status").selectpicker('val');
        params.reviewStatus = $("#reviewStatus").selectpicker('val');
        params.confirmStatus = $("#confirmStatus").selectpicker('val');
        params.onceStatus = $("#onceStatus").selectpicker('val');
        var rStatus = params.reviewStatus;
        var oStatus = params.onceStatus;
        //判断当审核状态为意向时必须选择实时状态
        if((rStatus == "A" ||rStatus == "B" ||rStatus == "C") && (oStatus != "true" && oStatus != "false")){
            boldFunc.showErrorMes("请选择实时状态");
            return;
        }
        if(oStatus == "true"){params.onceStatus=true;}
        if(oStatus == "false"){params.onceStatus=false;}
        getDataByURL("/service/platform/login/clientManager/client/update", params, function () {
            boldFunc.notification("操作成功");
            $("#detailModal").modal('hide');
            $("#table").bootstrapTable('refresh')
            $("#callRecordTable").bootstrapTable('refresh')
        })
    }
    
    //保存客户分组
    saveClientGroup=function () {
        ParsleyFunc.checkForm($("#con-groups-modal"), function () {
            var params = new FormData(document.getElementById("con-client-group-modal"));
            var clientGroupId=$("#clientGroupId").val();
            var url = "/service/platform/noLogin/clientManager/clientGroupController/create";
            if(StringUtil.isNotEmpty(clientGroupId)){
                url = "/service/platform/noLogin/clientManager/clientGroupController/update";
            }
            getFormDataByURL(url, params, function (data) {
                _customerGroupId=data.data.id;
                init();
                $("#closeClientGroup").click();
                $("#clientGroupName").val("");
            })
        })
    }

    //保存客户
    saveClient=function () {
        ParsleyFunc.checkForm($("#con-client-modal"), function () {
            if(StringUtil.isEmpty($("#projectOption").val())){
                boldFunc.showErrorMes("请选择项目");
                return;
            }
            var params = new FormData(document.getElementById("con-client-modal"));
            var url = "/service/platform/login/clientManager/client/create";
            getFormDataByURL(url, params, function (data) {
                _customerGroupId="public";
                projectId="";
                groupType="";
                $("#clientStatusOption").val("");
                $("#selectPhone").val("");
                refreshTable();
                $("#closeClient").click();
            })
        })
    }

    move = function (index) {
        var moveData = $("#table").bootstrapTable('getData')[index];
        $("#moveGroupType").click();
        FormFunc.loadObject(moveData, "#con-move-modal")
    }


    moveClientGroupType=function () {
        ParsleyFunc.checkForm($("#con-move-modal"), function () {
            if(StringUtil.isEmpty($("#clientGroupTypeOption").val())){
                boldFunc.showErrorMes("请选择移动的组");
                return;
            }
            if(_customerGroupId != null && _customerGroupId != "public"){
                $("#groupId").val(_customerGroupId)
            }
            var params = $("#con-move-modal").serializeObject();
            var url = "/service/platform/login/clientManager/client/moveClientGroupType";
            if(params.projectId != _editData.projectId){
                boldFunc.showConfirm("确定更改项目", function () {
                    getDataByURL(url, params, function (data) {
                        refreshTable();
                        $("#closeMoveGroupType").click();
                    })
                })
            }else{
                getDataByURL(url, params, function (data) {
                    refreshTable();
                    $("#closeMoveGroupType").click();
                })
            }
        })
    }

    clearGroupTypeData=function () {
        FormFunc.clearForm("#con-move-modal");
    }


    //获取年龄段和地区信息
    getMapOptions=function () {
        //获取下拉框数据
        var params = {params:"YearTag,Area,ClientStatus,Project,ClientGroupType"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data;
            SelectPickerFunc.loadLocalData('YearTagOption', JSON.parse(dataJson.YearTag ), true);
            SelectPickerFunc.loadLocalData('areaOption',JSON.parse(dataJson.Area ), true);
            SelectPickerFunc.loadLocalData('clientStatusOption', JSON.parse(dataJson.ClientStatus), true);
            SelectPickerFunc.loadMap('projectOption', dataJson.Project, true);
            SelectPickerFunc.loadLocalData('clientGroupTypeOption', JSON.parse(dataJson.ClientGroupType), true);
        })
    }

    query=function () {
        _isClick = true;
        this.pageNumber=1;
        _customerGroupId="";
        projectId="";
        groupType="";

        refreshTable();
    }


    changeGroupType=function (obj) {
        refreshTable();
    }

    clientGroupingParam=function () {
        if(_loadGroupId == null){
            if(GlobalData.isInterview){
                _loadGroupId = "publicInterview"
            }else{
                _loadGroupId = "public"
            }
        }
        var page = $("#gotoPage").val()
        if(StringUtil.isNotEmpty(page)){
            this.pageNumber = page
            $("#gotoPage").val(null)
        }
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            groupId: _loadGroupId,
            projectId:  projectId,
            groupType:  groupType,
            isDistribute: true,
            clientStatus:   $("#clientStatusOption").val(),
            phone:$("#selectPhone").val(),
            isInclude:_isClick
        }
        if(StringUtil.isNotEmpty(param.phone)){
            param.groupId = "";
        }
        _isClick = false;
        return param;
    }

    clearModalData = function () {
        FormFunc.clearForm("#con-client-group-modal");
        FormFunc.clearForm("#con-client-modal");
        FormFunc.clearForm("#con-distribution-modal");
        FormFunc.clearForm("#con-move-modal");
    }

    init();
    getMapOptions();

}

