//# sourceURL=callManagement.js
var _callManagementRefreshRateNum;
function init_system_callManagement() {
    var _$checkboxDivArr = $("#checkboxDivArr")
    var _isEdit = false;
    var _editData = null;
    var _extNumListData = null;
    var _voiceTypeData = null;
    var _taskModal = $("#modal")
    var _importModal = $("#importModal")
    var _transFailModal = $("#transFailModal")
    var _refuseModal = $("#refuseModal")
    var _detailModal = $("#detailModal")


    init = function () {
        GlobalData.initPageJs();
        _$checkboxDivArr.empty();
        _callManagementRefreshRateNum = setInterval(refreshRateNum, 3000);

        $('#countNum').selectpicker({ });
        $('#rate').editableSelect({
            effects: 'slide',
        });

        for(var i = 1; i < 5; i++){
            initSelectDate("time" + i + "1", true)
            initSelectDate("time" + i + "2", false)
        }

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/groupCall/list",
            queryParams: queryParams,
            pagination: true,
            columns: [
                {
                    field: 'name',
                    title: '任务名称',
                    align: 'center',
                },
                {
                    field: 'projectName',
                    title: '对应项目',
                    align: 'center',
                },
                {
                    field: 'rate',
                    title: '并发倍率',
                    align: 'center',
                },
                {
                    field: 'rateNum',
                    title: '并发数',
                    align: 'center',
                },
                {
                    field: 'Calling',
                    title: '呼叫中',
                    align: 'center',
                },
                {
                    field: 'Success',
                    title: '通话中',
                    align: 'center',
                },
                {
                    field: 'Waiting',
                    title: '空闲客服',
                    align: 'center',
                },
                {
                    title: '指定客服数',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = 0
                        if(StringUtil.isNotEmpty(row.extNumList)){
                            a = row.extNumList.split(",").length
                        }
                        return a;
                    }
                },
                {
                    field: 'total',
                    title: '未呼数',
                    align: 'center',
                },
                {
                    field: '',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a = "";
                        if(row.isEnable){
                            a += '<button type="button" class="btn btn-danger waves-effect waves-light" onclick="enableGroupCall(' + index + ')">停止</button>';
                        }else{
                            a += '<button type="button" class="btn btn-success waves-effect waves-light" onclick="enableGroupCall(' + index + ')">启动</button>';
                        }
                        a += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default waves-effect waves-light"   data-toggle="modal" data-target="#modal" onclick="editGroupCall(' + index + ')">修改</button>';
                        // a += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"   onclick="showGroupCallDetail(' + index + ')">详情</button>';
                        a += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-info waves-effect waves-light"   data-toggle="modal" data-target="#importModal" onclick="imputGroupCall(' + index + ')">导入</button>';
                        return a;
                    }
                }]
        });

        //获取下拉框数据
        var params = {params:"VoiceType,Project,ImportTag"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            _voiceTypeData = JSON.parse(dataJson.VoiceType );
            SelectPickerFunc.loadLocalData('voiceType', _voiceTypeData, true)
            SelectPickerFunc.loadLocalData('importTag', JSON.parse(dataJson.ImportTag ), true)
            SelectPickerFunc.loadMap('project',dataJson.Project, true)
            SelectPickerFunc.loadMap('importProject',dataJson.Project, true)
        })


        _taskModal.on('show.bs.modal', function () {
            // 获取客服分机号列表
            getDataByURL("/service/platform/login/user/user/getCSExtNumList", null, function (data) {
                _extNumListData = data.data
                _$checkboxDivArr.empty();
                var extNumArr = [];
                if(_isEdit){
                    var extNumList = _editData.extNumList
                    if(StringUtil.isNotEmpty(extNumList)){
                        extNumArr = extNumList.split(",")
                    }
                }
                var dataArr = [];
                var haveCount = 0;
                for(var i = 0;  i < _extNumListData.length; i++){
                    var itemData = _extNumListData[i]
                    var item = itemData.key
                    var params = {}
                    params.name = item;
                    params.id = item;
                    params.isOnline = itemData.value
                    if(_isEdit && extNumArr.contains(item)){
                        params.isHave = true;
                        haveCount++;
                    }else{
                        params.isHave = false;
                    }
                    dataArr.push(params);
                }
                var isAllHave = false;
                if(haveCount == _extNumListData.length){
                    isAllHave = true
                }
                var itemJson = {
                    name:"全选",
                    id : "all",
                    isOnline:true,
                    isHave:isAllHave
                };
                dataArr.unshift(itemJson);
                var json={
                    index:1,
                    data:dataArr
                }
                _$checkboxDivArr.setTemplateURL("tpl/assignProjectItem.html");
                _$checkboxDivArr.processTemplate(json);
                CheckBoxFunc.init({
                    divId: "checkboxDivArr",    // 包含所有checkbox的div
                    data:dataArr,      // 数据，包含所有的id，和全选组件的id
                    allId:"all"      // 全选id
                })
            })
        })


        _importModal.on('show.bs.modal', function () {
            $("#importTag").selectpicker('val', '');
        })

    }

    initSelectDate = function (id, isHour) {
        var count = 60;
        if(isHour){
            count = 24;
        }
        for(var i = 0; i < count; i++){
            var str = i
            if(i < 10){
                str = "0" + i;
            }
            $("#" + id).append(" <option value=\"" + str + "\">" + str + "</option>");
        }
        $("#" + id).selectpicker('refresh');
        $("#" + id).selectpicker('val', '00');
    }

    enableGroupCall = function (index) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var str = _editData.isEnable ? "停止" : "启动"
        var title = str + "  " + _editData.name;
        boldFunc.showConfirm(title, function () {
            getDataByURL("/service/platform/login/systemManager/groupCall/setIsEnable", {id:_editData.id, isEnable:!_editData.isEnable}, function () {
                boldFunc.notification("操作成功");
                _taskModal.modal('hide')
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    restartServer = function () {
        boldFunc.showConfirm("确定重启服务器", function () {
            getDataByURL("/service/platform/login/systemManager/groupCall/restartServer", {}, function () {
                boldFunc.notification("操作成功");
            })
        })
    }

    /**
     * 显示群呼详情
     */
    showGroupCallDetail = function (index) {
        _editData = $("#table").bootstrapTable('getData')[index];
        boldFunc.notification("正在请求请稍后")
        getDataByURL("/service/platform/login/systemManager/groupCall/getDetail", {id:_editData.id}, function (data) {
            FormFunc.clearForm("#detailModal")
            FormFunc.loadObject(data.data, "#detailModal")
            $("#detailModal").modal('show')
        })
    }

    imputGroupCall = function (index) {
        _editData = $("#table").bootstrapTable('getData')[index];
        FormFunc.loadObject(_editData, "#importModal")
    }

    importGroupCallDetail = function () {
        $("#btnImportNo").attr('disabled', true);
        var params = _importModal.serializeHumpObject();
        params.groupCallId = _editData.id;
        params.projectId = _editData.projectId;
        getDataByURL("/service/platform/login/systemManager/GroupCallDetail/importGroupCallDetail", params, function (data) {
            boldFunc.notification(data.data.count + " 条已导入");
            _importModal.modal('hide')
            $("#table").bootstrapTable('refresh');
            $("#btnImportNo").attr('disabled', false);
        })
    }

    editGroupCall = function (index) {
        _isEdit=true;
        _editData = $("#table").bootstrapTable('getData')[index];
        $("#title").text("修改任务")
        //加载停止时间
        for(var i = 1; i < 5; i++){
            var timeStr = null;
            switch (i){
                case 1:
                    timeStr = _editData.endTime1;
                    break;
                case 2:
                    timeStr = _editData.endTime2;
                    break;
                case 3:
                    timeStr = _editData.endTime3;
                    break;
                case 4:
                    timeStr = _editData.endTime4;
                    break;
            }
            if(timeStr != null){
                var strArr = timeStr.split(":")
                var first = strArr[0]
                $("#time" + i + "1").selectpicker('val', first);
                var second = strArr[1]
                $("#time" + i + "2").selectpicker('val', second);
            }else{
                $("#time" + i + "1").selectpicker('val', "00");
                $("#time" + i + "2").selectpicker('val', "00")
            }
        }
        FormFunc.loadObject(_editData, "#modal")
    }

    addGroupCall = function () {
        _isEdit=false;
        _$checkboxDivArr.empty();
        FormFunc.clearForm("#modal")
        $('#rate').val( 2.5);
        for(var i = 1; i < 5; i++){
            $("#time" + i + "1").selectpicker('val', "00");
            $("#time" + i + "2").selectpicker('val', "00")
        }
        SelectPickerFunc.loadLocalData('voiceType', _voiceTypeData, false)
    }

    allToWaiting = function () {
        var params = {groupCallId:_editData.id};
        getDataByURL("/service/platform/login/systemManager/GroupCallDetail/allToWaiting", params, function (data) {
            _transFailModal.modal('hide')
            $("#table").bootstrapTable('refresh');
        })
    }


    query = function () {
        $("#table").bootstrapTable('refresh')
    }

    /**
     * 显示转人工失败
     * @param index
     */
    showTransFailModal = function (index) {
        _detailModal.modal('hide')
        _transFailModal.modal('show')

        TableFunc.showTable({
            elm:"transFailTable",
            url: "/service/platform/login/systemManager/GroupCallDetail/list",
            queryParams: queryTransFailParams,
            pagination: true,
            columns: [
                {
                    field: 'phone',
                    title: '号码',
                    align: 'center',
                },
                {
                    field: 'startTimeStr',
                    title: '呼叫时间',
                    align: 'center',
                },
                {
                    field: 'statusStr',
                    title: '呼叫结果',
                    align: 'center',
                },
                {
                    field: 'portId',
                    title: '呼叫通道',
                    align: 'center',
                },
            ]
        })

        $("#transFailTable").bootstrapTable('refresh');
    }

    /**
     * 显示客服拒绝
     * @param index
     */
    showRefuseModal = function (elm) {
        var count = $(elm).val()
        if(count == 0){
            return
        }
        _refuseModal.modal('show')
        _detailModal.modal('hide')
        TableFunc.showTable({
            elm:"refuseTable",
            url: "/service/platform/login/systemManager/GroupCallDetail/list",
            queryParams: queryRefuseParams,
            pagination: true,
            columns: [
                {
                    field: 'phone',
                    title: '号码',
                    align: 'center',
                },
                {
                    field: 'firstText',
                    title: '识别文字',
                    align: 'center',
                },
                {
                    field: 'similarityStr',
                    title: '解析结果',
                    align: 'center',
                },
                {
                    field: 'similarityFloat',
                    title: '结果概率',
                    align: 'center',
                },
                {
                    field: 'startTimeStr',
                    title: '呼叫时间',
                    align: 'center',
                },
                {
                    title: '录音',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        if(row.refuseRecordPath != null){
                            var a = '<button type="button" class="playing" onclick="playRecordInDetail(' + index + ')"><img src="assets/images/playing.png"></button>';
                            return a;
                        }
                    }
                },
            ]
        })

        $("#refuseTable").bootstrapTable('refresh');
    }

    saveGroupCall = function () {
        ParsleyFunc.checkForm(_taskModal, function () {
            var params = _taskModal.serializeHumpObject();
            var url = "/service/platform/login/systemManager/groupCall/create";
            if(_isEdit){
                url = "/service/platform/login/systemManager/groupCall/update";
            }
            if( ! NumFunc.isNumber(params.rate)){
                boldFunc.showErrorMes("请输入正确并发倍率")
                return
            }
            var ids = CheckBoxFunc.getSelectId("checkboxDivArr")
            params.extNumList = ids;
            for(var i = 1; i < 5; i++){
                var time11 = $("#time" + i + "1").selectpicker('val');
                var time12 = $("#time" + i + "2").selectpicker('val')
                if(time11 != null && time12 != null){
                    var time = time11 + ":" + time12
                    switch (i){
                        case 1:
                            params.endTime1 = time
                            break;
                        case 2:
                            params.endTime2 = time
                            break;
                        case 3:
                            params.endTime3 = time
                            break;
                        case 4:
                            params.endTime4 = time
                            break;
                    }
                }
            }
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                _taskModal.modal('hide')
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    /**
     * 定时刷新并发数
     */
    refreshRateNum = function () {
        getDataByURL("/service/platform/login/systemManager/groupCall/refreshRateNum", null, function (data) {
            $("#table").find("tr").each(function () {
                var $this = $(this)
                var id = $this.data("uniqueid")
                if(id != null){
                    var dataJson = data.data
                    for(var key in dataJson ){
                        if(StringUtil.ignoreCaseEquals(id, key)){
                            var item = dataJson[key]
                            var val = $(this).find("td:eq(3)");
                            val.html(item.conCount);
                            val = $(this).find("td:eq(4)");
                            val.html(item.Calling);
                            val = $(this).find("td:eq(5)");
                            val.html(item.Success);
                            val = $(this).find("td:eq(6)");
                            val.html(item.Waiting);
                        }
                    }
                }
            })
        })
    }

    /**
     * 点击modal里面的播放录音
     * @param index
     */
    playRecordInDetail = function(index){
        var editData = $("#refuseTable").bootstrapTable('getData')[index];
        if(editData.refuseRecordPath != null){
            var $indexMyMp3 = $("#myMp3")
            $indexMyMp3.show()
            ProjectFunc.setVolume("myMp3");
            $indexMyMp3.attr("autoplay", "autoplay")
            $indexMyMp3.attr("src", editData.refuseRecordPath)
        }
    }

    /**
     * 停止播放录音
     */
    stopPlayRecord = function () {
        var myVideo = document.getElementById('myMp3');
        myVideo.pause();
    }

    queryTransFailParams = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            status:"TransFail",
            id:_editData.id
        }
        return param;
    }

    queryRefuseParams = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            status:"Refuse",
            id:_editData.id
        }
        return param;
    }

    queryParams = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
        }
        return param;
    }

    init();
}

function quit_system_callManagement() {
    clearInterval(_callManagementRefreshRateNum);
}





