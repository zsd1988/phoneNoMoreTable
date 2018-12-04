//# sourceURL=groupCallDetail.js
function init_statistics_groupCallDetail() {
    var _editData = null;
    var _todayStr = DateUtil.getNowFormatDate();
    var _userList = null;

    init = function () {
        GlobalData.initPageJs();

        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
        $('#haveFirst').selectpicker({ });
        $('#haveFirst').selectpicker("val", "")

        //获取下拉框数据
        var params = {params:"Project,ClientStatus,ClientGroupType,ClientGroupNameYf,CSGroup,YearTag,Area"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            var clientReviewStatus = JSON.parse(dataJson.ClientStatus )
            SelectPickerFunc.loadLocalData('reviewStatus', clientReviewStatus, true)
            SelectPickerFunc.loadLocalData('clientReviewStatus', clientReviewStatus, true)
            SelectPickerFunc.loadLocalData('clientGroupType', JSON.parse(dataJson.ClientGroupType ), true)
            SelectPickerFunc.loadLocalData('clientGroupTypeTransferAll',JSON.parse(dataJson.ClientGroupType ), true)
            SelectPickerFunc.loadMap('projectId',dataJson.Project, true)
            SelectPickerFunc.loadMap('modalProjectId',dataJson.Project, true)
            SelectPickerFunc.loadMap('clientGroupName',dataJson.ClientGroupNameYf, true)
            SelectPickerFunc.loadMap('clientGroupNameTransferAll',dataJson.ClientGroupNameYf, true)
            SelectPickerFunc.loadMap('groupId',dataJson.CSGroup, true)
            _userList = dataJson.CSGroupUser;
        })

        // 限制通话记录的查看日期
        var startDate = null;
        if( ! GlobalData.functionIdArr.contains("d7905fea91774c83810b21d25746f531")){
            startDate = DateUtil.getDayBeforeOrAfter(-7);
        }

        DateTimePickerFunc.init({
            id:"startTime",
            startDate:startDate,
            selectDay:true
        })

        DateTimePickerFunc.init({
            id:"endTime",
            selectDay:true
        })

        var colums = [
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
                field: 'projectName',
                title: '项目名',
                align: 'center',
            },
            {
                field: 'statusStr',
                title: '状态',
                align: 'center',
            },
            {
                field: 'startTimeStr',
                title: '呼叫时间',
                align: 'center',
            },
            {
                field: 'time',
                title: '通话时长',
                align: 'center',
            },
            {
                field: 'resolveStr',
                title: '匹配结果',
                align: 'center',
            },
            {
                field: 'firstText',
                title: '识别结果',
                align: 'center',
            },
            {
                field: 'similarityStr',
                title: '解析结果',
                align: 'center',
            },
            {
                field: 'similarityFloat',
                title: '解析概率',
                align: 'center',
            },
            {
                title: '录音',
                align: 'center',
                width:"130px",
                valign: 'middle',
                formatter: function (value, row, index) {
                    if(row.refuseRecordPath != null){
                        var a = "";
                        a += '&nbsp;&nbsp;<button type="button" class="playing" onclick="playRecord(' + index + ', this)"><img src="assets/images/playing.png"></button>';
                        a += '&nbsp;&nbsp;<a href="' + row.refuseRecordPath + '" download="w3logo"><img src="assets/images/download.png"></a>'
                        return a;
                    }
                }
            },
            {
                field:'clientGroupId',
                title: '转移',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    var a;
                    value ? a = '<button type="button" class="btn btn-sm waves-effect waves-light" >已转移</button>'
                        :  a = '<button type="button" class="btn btn-success btn-sm waves-effect waves-light" onclick="transClient(' + index + ')">转移</button>';
                    return a;
                }
            }
        ]

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/GroupCallDetail/list",
            queryParams: queryParams,
            pageSize:200,
            pageList: [200, 500, 1000, 2000],
            pagination: true,
            columns: colums
        })
    }

    /**
     * 转移
     * @param index
     */
    transClient = function (index) {
        boldFunc.showConfirm("确定转移", function () {
            _editData = $("#table").bootstrapTable('getData')[index];
            var params = {
                id:_editData.clientId,
                groupId:"0"
            }
            getDataByURL("/service/platform/login/clientManager/client/transClient", params, function (data) {
                boldFunc.notification("转移成功")
                $("#table").bootstrapTable('refresh')
            })
        })
    }

    reset = function () {
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
    }


    query = function () {
        var startTimeStr = $("#startTime").val()
        var endTimeStr = $("#endTime").val()
        if(startTimeStr != "" && endTimeStr != "" ){
            if(DateUtil.strToDate(startTimeStr) > DateUtil.strToDate(endTimeStr)){
                boldFunc.showErrorMes("开始时间大于结束时间")
                return
            }
        }
        $("#table").bootstrapTable('refresh')
    }

    reset = function () {
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
        $("#projectId").selectpicker('val', '')
        $("#haveFirst").selectpicker('val', '')
        $("#table").bootstrapTable('refresh')
    }

    /**
     * 显示客户信息
     * @param index
     */
    showCallRecordDetail = function (index, isAutoPlayRecord) {
        _editData = $("#table").bootstrapTable('getData')[index];
        if(_editData.refuseRecordPath != null){
            var $myMp3 = $("#myMp3")
            $myMp3.show()
            if(isAutoPlayRecord){
                $myMp3.attr("autoplay", "autoplay")
            }else {
                $myMp3.removeAttr("autoplay")
            }
            ProjectFunc.setVolume("myMp3");
            $myMp3.attr("src", _editData.refuseRecordPath)
            $("#detailModal").modal("show")
        }
    }

    /**
     * 播放录音
     * @param index
     */
    playRecord = function (index, elm) {
        _editData = $("#table").bootstrapTable('getData')[index];
        showCallRecordDetail(index, true)
    }

    /**
     * 关闭客户弹窗
     * @param index
     */
    closeClient = function (id) {
        var myVideo = document.getElementById('myMp3');
        myVideo.pause();
        $(myVideo).attr("src", "")
    }
    
    queryParams = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            isInclude:true,
            isGetEmployee:$('#haveFirst').selectpicker("val"),
            projectId:$("#projectId").selectpicker('val'),
            startTimeStr:$("#startTime").val(),
            endTimeStr:$("#endTime").val(),
        }
        return param;
    }

    init();
}





