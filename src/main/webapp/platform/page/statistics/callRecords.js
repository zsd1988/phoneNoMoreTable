//# sourceURL=callRecords.js
function init_statistics_callRecords() {
    var _editData = null;
    var _transferModal = $("#transferModal")
    var _transferAllModal = $("#transferAllModal")
    var _todayStr = DateUtil.getNowFormatDate();
    var _userList = null;
    var _roleUserIdMap= {};
    var _table=document.getElementById("table");
    var _modalList = $("#indexModalList")
    var _minList = $("#indexMinList")
    var _selectDataJson = null;
    if(GlobalData.callRecord.clientMap == null){
        GlobalData.callRecord.clientMap = new Map();
    }
    if(GlobalData.callRecord.minMap == null){
        GlobalData.callRecord.minMap = new Map();
    }
    var _showIndex = GlobalData.callRecord.showIndex = 0;
    var _isClickCopy = false;   // 是否点击复制
    var _currentCallRecordId = null;    // 当前操作的通话记录id
    var _currentTableIndex = null;  // 当前操作的table索引

    init = function () {
        GlobalData.initPageJs();

        $('#selectIsAppeal').selectpicker({ });
        $('#selectIsAppeal').selectpicker("val", "");
        $('#selectOnceStatus').selectpicker({ });
        $('#selectOnceStatus').selectpicker("val", "");
        $('#roleType').selectpicker({ });
        $('#roleType').selectpicker("val", "cs")
        $('#transYn').selectpicker({ });
        // 显示导出功能
        if(GlobalData.functionIdArr.contains("fc0f2ba695484899a7ba010d5721192d")){
            $("#export").show()
        }

        if(GlobalData.isCS || GlobalData.isInterview){
            $("#userId").val(GlobalData.workNumber)
        }

        if( GlobalData.isReviewer || GlobalData.isManager){
            $("#transSelect").show()
        }
        if(GlobalData.isReviewer){
            $("#checkQueryBtn").show()
        }


        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)

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
            _selectDataJson = dataJson;
            _userList = dataJson.CSGroupUser;
        })

        // 限制通话记录的查看日期
        var startDate = null;
        if( ! GlobalData.functionIdArr.contains("d7905fea91774c83810b21d25746f531")){
            startDate = DateUtil.getDayBeforeOrAfter(-60);
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
                checkbox:true,
                align : 'center',
            },
            {
                title: '号',
                align : 'center',
                width:"40px",
                formatter: function (value, row, index) {
                    return index+1;
                }
            },
            {
                field: 'clientName',
                title: '名称',
                width:"60px",
                align: 'center',
            },
            {
                field: 'phone',
                title: '被叫号码',
                align: 'center',
                width:"110px",
                formatter: function (value, row, index) {
                    row = handlePhone(row)
                    var str = row.phone;
                    return str;
                },
            },
            {
                field: 'clientReviewStatusStr',
                title: '审核状态',
                align: 'center',
                width:"120px",
            },
            {
                field: 'clientConfirmStatusStr',
                title: '约访状态',
                align: 'center',
                width:"120px",
            },
            {
                field: 'createTimeStr',
                title: '时间',
                align: 'center',
                width:"100px",
            },
            {
                field: 'clientDes',
                title: '备注',
                align: 'center',
                cellStyle:function (value, row, index, field) {
                    return {
                        css: {"text-overflow":"ellipsis","white-space":"nowrap","width":"300px","overflow": "hidden"}
                    };
                },
                formatter: function (value, row, index) {
                    return getDesA(value, index);
                },
            },
            {
                field: 'projectName',
                title: '项目名',
                align: 'center',
                width:"70px",
            },
            {
                field: 'workNumber',
                title: '工号',
                align: 'center',
                width:"70px",
            },
            {
                field: 'userName',
                title: '客服姓名',
                align: 'center',
                width:"90px",
            },
            {
                field: 'time',
                title: '时长',
                align: 'center',
                width:"50px",
            }
        ]
        colums.push(
            {
                title: '录音',
                align: 'center',
                width:"130px",
                valign: 'middle',
                formatter: function (value, row, index) {
                    if(StringUtil.isNotEmpty(row.recordPath)){
                        var a = "";
                        if(GlobalData.isReviewer){
                            if(row.isLock){
                                a += '<span style="color: red">已锁</span>';
                            }else{
                                a += '<span >未锁</span>';
                            }
                        }
                        a += '&nbsp;&nbsp;<button type="button" class="playing" onclick="playRecord(' + index + ', this)"><img src="assets/images/playing.png"></button>';
                        a += '&nbsp;&nbsp;<a href="' + row.recordPath + '" download="w3logo"><img src="assets/images/download.png"></a>'
                        return a;
                    }
                }
            },
            {
                field: 'reviewWorkNumber',
                title: '审核人',
                align: 'center',
                width:"70px",
            }
        )
        if(GlobalData.isCS || GlobalData.isInterview){
            colums.push({
                title: '话机',
                align: 'center',
                width:"120px",
                valign: 'middle',
                formatter: function (value, row, index) {
                    if(StringUtil.isNotEmpty(row.recordPath)){
                        var a = '<button type="button" class="playing" onclick="extPlayRecord(' + index + ')"><img src="assets/images/playing.png"></button>';
                        a += '&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"  onclick="indexHangupPlayRecord()">挂断</button>'
                        return a;
                    }
                }
            })
        }
        if(GlobalData.isCS || GlobalData.isReviewer){
            colums.push(
                {
                    field:'isAppeal',
                    title: '申诉',
                    align: 'center',
                    width:"70px",
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return getIsAppealA(value, row, index);
                    }
                })
        }

        if(GlobalData.isAdminOrReviewer){
            // 保存客户信息功能
            $("#saveClient").show()
            colums.push(
                {
                    field:'groupType',
                    title: '转移',
                    align: 'center',
                    width:"130px",
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        return getGroupTypeA(value, row, index);
                    }
                })
            colums.push(
                {
                    title: '删除',
                    align: 'center',
                    width:"70px",
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a;
                        a = '<button type="button" class="btn btn-danger btn-sm waves-effect waves-light" onclick="delRecord(' + index + ')">删除</button>';
                        return a;
                    }
                })
        }
        colums.push(
            {
                title: '详情',
                align: 'center',
                width:"50px",
                valign: 'middle',
                formatter: function (value, row, index) {
                    var a = '<a href="javascript:void(0)" style=\'text-decoration:underline;\' onclick="showCallRecordDetail(' + index + ', false)">详情</button>';
                    return a;
                }
            })

        // 获取客服用户后再查询通话记录，不然选择的客服角色没生效
        getDataByURL("/service/platform/login/user/user/getCSAndInterview", null, function (data) {
            var dataJson = data.data
            _roleUserIdMap.CS = dataJson.CS;
            _roleUserIdMap.interview = dataJson.interview
            TableFunc.showTable({
                classes: 'table',
                url: "/service/platform/login/systemManager/callRecord/list",
                queryParams: queryParams,
                pageSize:500,
                pageList: [500,  700, 1000],
                pagination: true,
                columns: colums,
                onClickRow: function (row,$element, field) {
                    if(field == "clientDes"){
                        var $div = $($element.context)
                        var value = $div.css("white-space")
                        if( value != null && value == "nowrap" && ! _isClickCopy ){
                            $div.css("white-space", "")
                        }else{
                            $div.css("white-space", "nowrap")
                        }
                        _isClickCopy = false
                    }
                    var rowId = row.id;
                    $("#table tr").each(function (index, elm) {
                        var $div = $(elm)
                        var uniqueid = $div.data("uniqueid")
                        var value = "";
                        if(uniqueid != null && uniqueid == rowId){
                            value = "lightblue";
                        }
                        $div.css("background-color",value);
                    })
                }
            })
            sortTable();
        })
    }

    copyToPaste = function(index) {
        _isClickCopy = true;
        var editData = $("#table").bootstrapTable('getData')[index];
        var oInput = document.createElement('input');
        oInput.value = editData.clientDes;
        document.body.appendChild(oInput);
        oInput.select(); // 选择对象
        document.execCommand("Copy");
        oInput.className = 'oInput';
        oInput.style.display='none';
    }

    extPlayRecord = function (index) {
        var editData = $("#table").bootstrapTable('getData')[index];
        indexExtPlayRecord(editData.recordPath)
    }

    callRecordRecordRefresh = function (id) {
        var data = GlobalData.callRecord.clientMap.get(id)
        var index = data.addStr
        $("#callRecordTable" + index).bootstrapTable('refresh')
    }

    reset = function () {
        $("#recordPhone").val("")
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
        $("#reviewStatus").selectpicker('val', '')
        $("#selectIsAppeal").selectpicker('val', '')
        $("#userId").val('')
        $("#reviewId").val('')
        $("#groupId").selectpicker('val','')
        $("#projectId").selectpicker('val', '')
        $("#selectOnceStatus").selectpicker('val', '')
        $("#roleType").selectpicker('val', 'cs')
        $("#transYn").selectpicker('val', 'all')
        $("#table").bootstrapTable('refresh')
    }

    callRecordGetCommonSpeech = function(id){
        var data = GlobalData.callRecord.clientMap.get(id)
        var index = data.addStr
        $("#des" + index).val($("#des" + index).val()+GlobalData.hintDes);
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
        sortTable();
    }

    makeSortable = function(table,sortIndex,isInt) {
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
                        var row = rows[i];
                        var cell =row.getElementsByTagName("td")[1];//获得第n个单元格
                        cell.innerText = (i + 1) + '';
                    }
                    flag=!flag;
                }
            }(i));
        }
    },

    sortTable = function () {
        //点击列名排序
        this.makeSortable(_table,2,false);
        this.makeSortable(_table,3,true);
        this.makeSortable(_table,4,false);
        this.makeSortable(_table,5,false);
        this.makeSortable(_table,6,false);
        this.makeSortable(_table,7,false);
        this.makeSortable(_table,8,true);
        this.makeSortable(_table,9,false);
        this.makeSortable(_table,10,true);
        this.makeSortable(_table,11,true);
        if(GlobalData.isReviewer){
            this.makeSortable(_table,13,false);
            this.makeSortable(_table,14,false);
            this.makeSortable(_table,15,false);
        }
    }


    /**
     * 显示客户信息
     * @param index
     */
    showCallRecordDetail = function (index, isAutoPlayRecord) {
        _editData = $("#table").bootstrapTable('getData')[index];
        GlobalData.callRecord.enterTimes++;
        var addStr = index + "" + GlobalData.callRecord.enterTimes;
        _editData.index = addStr
        _showIndex = addStr;
        var clientMapData = GlobalData.callRecord.clientMap.get(_editData.id);
        if( clientMapData != null){
            addStr = clientMapData.addStr
            var $detailModal = $("#detailModal" + addStr)
            $detailModal.on('shown.bs.modal', function () {
                $detailModal.css("padding-left", "0")
            })
            $detailModal.modal('show')
            GlobalData.callRecord.minMap.remove(_editData.id)
            $("#indexMinList" + addStr).remove()
            return
        }
        _editData.callRecordCallPhoneFunc = "callRecordCallPhone";
        _editData.callRecordCloseClientFunc = "callRecordCloseClient";
        _editData.callRecordMinClientFunc = "callRecordMinClient";
        _editData.callRecordSetReviewStatusFunc = "callRecordSetReviewStatus";
        _editData.callRecordSetConfirmStatusFunc = "callRecordSetConfirmStatus";
        _editData.callRecordGetCommonSpeechFunc = "callRecordGetCommonSpeech";
        _editData.callRecordSaveClientFunc = "callRecordSaveClient";
        _editData.callRecordTransClientFunc = "callRecordTransClient";
        _editData.callRecordRecordRefreshFunc = "callRecordRecordRefresh";
        _modalList.setTemplateURL("tpl/callRecordDetail.html");
        _modalList.processTemplate(_editData);
        var clientReviewStatus = JSON.parse(_selectDataJson.ClientStatus )
        SelectPickerFunc.loadLocalData('yearTag' + addStr, JSON.parse(_selectDataJson.YearTag ), true)
        SelectPickerFunc.loadLocalData('area' + addStr, JSON.parse(_selectDataJson.Area ), true)
        SelectPickerFunc.loadLocalData('status' + addStr, clientReviewStatus, true)
        SelectPickerFunc.loadLocalData('clientModalReviewStatus' + addStr, clientReviewStatus, true)
        SelectPickerFunc.loadLocalData('confirmStatus' + addStr, clientReviewStatus, true)
        SelectPickerFunc.loadMap('projectId' + addStr, _selectDataJson.Project, true)
        if(GlobalData.isCS){
            $('#clientModalReviewStatus' + addStr).attr("disabled",'disabled');
            $("#confirmStatus" + addStr).attr("disabled",'disabled');
            $("#projectId" + addStr).attr("disabled",'disabled');
        }else if(GlobalData.isInterview){
            $('#clientModalReviewStatus' + addStr).attr("disabled",'disabled');
            $('#status' + addStr).attr("disabled",'disabled');
            $("#projectId" + addStr).attr("disabled",'disabled');
        }else if(GlobalData.isReviewer){
            $('#confirmStatus' + addStr).attr("disabled",'disabled');
            // $('#status').attr("disabled",'disabled');
            $("#callRecordTransClient" + addStr).show()
        }
        if( GlobalData.isInterview || GlobalData.isManager){
            $('#clientModalConfirmStatusDiv' + addStr).show();
        }
        if(_editData.recordPath != null){
            var $myMp3 = $("#myMp3" + addStr)
            $myMp3.show()
            if(isAutoPlayRecord){
                $myMp3.attr("autoplay", "autoplay")
            }else {
                $myMp3.removeAttr("autoplay")
            }
            ProjectFunc.setVolume("myMp3"+ addStr);
            $myMp3.attr("src", _editData.recordPath)
        }
        var params = {
            id: _editData.clientId,
        }
        getDataByURL("/service/platform/login/clientManager/client/getClient", params, function (data) {
            var dataJson = data.data;
            dataJson.addStr = addStr
            GlobalData.callRecord.clientMap.put(_editData.id, dataJson)
            var loadPhone = dataJson.phone;
            var clientId = dataJson.id;
            dataJson.callRecordId = _editData.id;
            dataJson.showTableIndex = index;
            dataJson = handlePhone(dataJson)
            if(GlobalData.isCS){
                if(StringUtil.isEmpty(_editData.reviewId)){
                    $("#saveTpClient" + addStr).show()
                }else{
                    $("#saveTpClient" + addStr).hide()
                }
            }
            FormFunc.loadObject(dataJson,"#detailModal" + addStr);
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
                            var a = '<button type="button" class="playing" onclick="callRecordPlayRecordInDetail(' + index + ')"><img src="assets/images/playing.png"></button>';
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
                            var a = '<button type="button" class="playing" onclick="callRecordExtPlayRecord(' + index + ')"><img src="assets/images/playing.png"></button>';
                            a += '&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"  onclick="indexHangupPlayRecord()">挂断</button>'
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
                }
                )
            if( ! GlobalData.isCS){
                colums.push({
                    field: 'userName',
                    title: '跟进人',
                    align: 'center'
                },
                {
                    field: 'workNumber',
                    title: '工号',
                    align: 'center'
                })
            }
            TableFunc.showTable({
                url: "/service/platform/login/systemManager/callRecord/listByPhone",
                queryParams: queryParamsCallRecord = function () {
                    var param = {
                        paginationParams:{
                            pageIndex : this.pageNumber,
                            pageSize : this.pageSize,
                        },
                        clientId:clientId,
                        phone:loadPhone
                    }
                    return param;
                },
                pagination: true,
                elm:"callRecordTable" + addStr,
                columns: colums
            })
            var $detailModal = $("#detailModal" + addStr)
            $detailModal.on('shown.bs.modal', function () {
                $detailModal.css("padding-left", "0")
            })
            $detailModal.modal("show")
            $("#callRecordTable" + addStr).bootstrapTable('refresh')
        });
    }


    // 拨打电话
    callRecordCallPhone = function (id) {
        if(StringUtil.isNotEmpty(GlobalData.extNum)){
            var clientData = GlobalData.callRecord.clientMap.get(id)
            boldFunc.showConfirm("确定拨打：" + clientData.phone, function () {
                var params = {
                    clientId:clientData.id,
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

    /**
     * 点击modal里面的播放录音
     * @param index
     */
    callRecordPlayRecordInDetail = function(index){
        var editData = $("#callRecordTable" + _showIndex).bootstrapTable('getData')[index];
        if(editData.recordPath != null){
            var $indexMyMp3 = $("#myMp3" + _showIndex)
            $indexMyMp3.show()
            ProjectFunc.setVolume("myMp3" + _showIndex);
            $indexMyMp3.attr("autoplay", "autoplay")
            $indexMyMp3.attr("src", editData.recordPath)
        }
    }


    callRecordExtPlayRecord = function (index) {
        var editData = $("#callRecordTable" + _showIndex).bootstrapTable('getData')[index];
        indexExtPlayRecord(editData.recordPath)
    }

    /**
     * 播放录音
     * @param index
     */
    playRecord = function (index, elm) {
        _editData = $("#table").bootstrapTable('getData')[index];
        if(GlobalData.isReviewer){
            //设置记录已锁定
            var params = {id:_editData.id}
            getDataByURL("/service/platform/login/systemManager/callRecord/setLock", params, function (data) {
                var span = $(elm).prev()
                span.text("已锁")
                span.css("color", "red")
            })
        }
        showCallRecordDetail(index, true)
    }

    checkQuery = function () {
        var _checkArr = []
        _checkArr.push("A")
        _checkArr.push("B")
        _checkArr.push("C")
        _checkArr.push("Waiting")
        _checkArr.push("Merchants")
        $("#reviewStatus").selectpicker('val',_checkArr)
        query()
    }

    /**
     *最小化客户窗口
     * @param index
     */
    callRecordMinClient = function (id) {
        var data = GlobalData.callRecord.clientMap.get(id)
        var minData = GlobalData.callRecord.minMap.get(id)
        var index = data.addStr
        if(minData == null){
            data.index = index
            data.callRecordMaxClientFunc = "callRecordMaxClient"
            data.callRecordCloseMinClientFunc = "callRecordCloseMinClient"
            data = handlePhone(data)
            _minList.setTemplateURL("tpl/minList.html");
            _minList.processTemplate(data);
            GlobalData.callRecord.minMap.put(id, data)
        }
        $("#detailModal" + index).modal('hide')
    }

    /**
     * 最大化客户窗口
     * @param index
     */
    callRecordMaxClient = function (id) {
        var data = GlobalData.callRecord.clientMap.get(id)
        var index = data.addStr
        $("#detailModal" + index).modal('show')
        _showIndex = index
        GlobalData.callRecord.minMap.remove(id)
        $("#indexMinList" + index).remove()
    }

    /**
     * 关闭最小窗口
     * @param index
     */
    callRecordCloseMinClient = function (id) {
        var data = GlobalData.callRecord.clientMap.get(id)
        var index = data.addStr
        $("#indexMinList" + index).remove()
        callRecordCloseClient(id)
        GlobalData.callRecord.minMap.remove(id)
        GlobalData.callRecord.clientMap.remove(id)
    }

    /**
     * 关闭客户弹窗
     * @param index
     */
    callRecordCloseClient = function (id) {
        var data = GlobalData.callRecord.clientMap.get(id)
        var index;
        if(data == null){
            data = GlobalData.callRecord.minMap.get(id)
            index = data.index
        }else{
            index = data.addStr;
        }
        var myVideo = document.getElementById('myMp3' + index);
        myVideo.pause();
        $(myVideo).attr("src", "")
        $(myVideo).hide()
        $("#detailModal" + index).on("hide.bs.modal", function () {
            GlobalData.callRecord.clientMap.remove(id);
            $("#detailModal" + index).remove();
        })
    }

    callRecordTransClient = function (id) {
        var data = GlobalData.callRecord.clientMap.get(id)
        $("#transferClientId").val(data.id);
        _currentCallRecordId = data.callRecordId
        _currentTableIndex = data.showTableIndex
        _transferModal.modal("show")
        $("#clientGroupName").selectpicker('val', data.groupId ? data.groupId : '')
        $("#clientGroupType").selectpicker('val', data.groupType ? data.groupType : '')
        FormFunc.loadObject(data, "#transferModal")
        $("#transferModal").on("hide.bs.modal", function () {
            var index;
            if(data == null){
                data = GlobalData.callRecord.minMap.get(id)
                index = data.index
            }else{
                index = data.addStr;
            }
            $("#detailModal" + index).css("overflow-y", "auto")
        })
    }

    var isCallRecordClientSaveBtnClick = true;
    callRecordSaveClient = function (id) {
        if(isCallRecordClientSaveBtnClick) {
            isCallRecordClientSaveBtnClick = false;
            setTimeout(function () {
                isCallRecordClientSaveBtnClick = true;
            }, 1000);
            var data = GlobalData.callRecord.clientMap.get(id)
            var index = data.addStr
            var params = $("#detailModal" + index).serializeHumpObject();
            params.status = $("#status" + index).selectpicker('val');
            params.reviewStatus = $("#clientModalReviewStatus" + index).selectpicker('val');
            params.projectId = $("#projectId" + index).selectpicker('val');
            params.confirmStatus = $("#confirmStatus" + index).selectpicker('val');
            params.onceStatus = $("#onceStatus" + index).selectpicker('val');
            var rStatus = params.reviewStatus;
            var oStatus = params.onceStatus;
            //判断当审核状态为意向时必须选择实时状态
            if ((rStatus == "A" || rStatus == "B" || rStatus == "C") && (oStatus != "true" && oStatus != "false")) {
                boldFunc.showErrorMes("请选择实时状态");
                return;
            }
            if (oStatus == "true") {
                params.onceStatus = true;
            }
            if (oStatus == "false") {
                params.onceStatus = false;
            }
            if (GlobalData.isReviewer) {
                params.reviewId = GlobalData.userId
                params.reviewName = GlobalData.userName
            }
            if (data.projectId != params.projectId) {
                boldFunc.showConfirm("确定更改项目", function () {
                    callRecordToSaveClient(params, index, data)
                })
            } else {
                callRecordToSaveClient(params, index, data)
            }
        }
    }

    callRecordToSaveClient = function (params, index, editData) {
        _currentCallRecordId = editData.callRecordId
        _currentTableIndex = editData.showTableIndex
        getDataByURL("/service/platform/login/clientManager/client/update", params, function () {
            boldFunc.notification("操作成功");
            refreshTableCustom();
            $("#callRecordTable" + index).bootstrapTable('refresh')
        })
    }

    /**
     * 刷新table的单项
     * @param recordId
     * @param showTableIndex
     */
    refreshTableSingle = function () {
        getDataByURL("/service/platform/login/systemManager/callRecord/getCallRecordTableItem", {id:_currentCallRecordId}, function (data) {
            var dataJson = data.data
            $("#table tbody tr").each(function (index1, elm) {
                var $div = $(elm)
                if($div.data("uniqueid") == _currentCallRecordId){
                    $div.find("td").each(
                        function (index2, elm2) {
                            switch (index2){
                                case 2:
                                    $(elm2).text(dataJson.clientName)
                                    break
                                case 3:
                                    $(elm2).text(dataJson.phone)
                                    break
                                case 4:
                                    $(elm2).text(dataJson.clientReviewStatusStr)
                                    break
                                case 7:
                                    $(elm2).html(getDesA(dataJson.clientDes, _currentTableIndex))
                                    break
                                case 8:
                                    $(elm2).text(dataJson.projectName)
                                    break
                                case 13:
                                    $(elm2).text(dataJson.reviewWorkNumber)
                                    break
                                case 14:
                                    $(elm2).html(getIsAppealA(dataJson.isAppeal, dataJson, _currentTableIndex))
                                    break
                                case 15:
                                    $(elm2).html(getGroupTypeA(dataJson.groupType, dataJson, _currentTableIndex))
                                    break
                            }
                        }
                    )
                }
            })
        })
    }

    getDesA = function (value, index) {
        var a = '';
        if(StringUtil.isNotEmpty(value)){
            a += '<a href="javascript:void(0)" onclick="copyToPaste(\'' + index + '\')" >复制</a>&nbsp;&nbsp;'
            a += value;
        }
        return a
    }

    getGroupTypeA = function (value, row, index) {
        var a = '';
        if(StringUtil.isEmpty(value)){
            a = '<button type="button" class="btn btn-success btn-sm waves-effect waves-light" onclick="showTransClient(' + index + ')">转移</button>'
        }else{
            a += '<span >' + row.groupName + '</span>'
            a += '&nbsp;<button type="button" class="btn btn-sm waves-effect waves-light" onclick="showTransClient(' + index + ')">已转移</button>'
        }
        return a;
    }

    getIsAppealA = function (value, dataJson, index) {
        var a = '';
        if(dataJson.clientStatus != null && (dataJson.clientStatus == "A" || dataJson.clientStatus == "B" || dataJson.clientStatus == 'C')){
            if( value == null && GlobalData.isCS){
                var confirmStatus = dataJson.clientConfirmStatus
                if(confirmStatus != null && confirmStatus != "A" && confirmStatus != "B" && confirmStatus != "C" ){
                    a = '<button type="button" class="btn btn-success btn-sm waves-effect waves-light" onclick="setAppeal(' + index + ', 1)">申诉</button>'
                }
            }else{
                if(value != null){
                    if(value ){
                        if(GlobalData.isCS){
                            a = '<span >待受理</span>'
                        }else{
                            a = '<button type="button" class="btn btn-success waves-effect waves-light" onclick="setAppeal(' + index + ', 0)">受理</button>'
                        }
                    }else{
                        a = '<span >已受理</span>'
                    }
                }
            }
        }
        return a
    }

    callRecordSetReviewStatus = function(id){
        var data = GlobalData.callRecord.clientMap.get(id)
        var index = data.addStr
        $("#clientModalReviewStatus" + index).selectpicker('val',$("#status" + index).selectpicker('val'));
    }

    callRecordSetConfirmStatus = function(id){
        var data = GlobalData.callRecord.clientMap.get(id)
        var index = data.addStr
        if(isInCalling){
            $("#clientModalReviewStatus" + index).selectpicker('val',$("#confirmStatus" + index).selectpicker('val'));
        }
    }

    /**
     * 删除记录
     * @param index
     */
    delRecord = function (index) {
        boldFunc.showConfirmText("确定删除", null, function () {
            _editData = $("#table").bootstrapTable('getData')[index];
            var params = {id:_editData.id}
            getDataByURL("/service/platform/login/systemManager/callRecord/delete", params, function (data) {
                boldFunc.notification("删除成功")
                $("#table").bootstrapTable('refresh')
            })
        })
    }

    /**
     * 显示转移客户
     * @param index
     */
    showTransClient = function (index) {
        _transferModal.modal("show")
        _editData = $("#table").bootstrapTable('getData')[index];
        _currentCallRecordId = _editData.id
        _currentTableIndex = index
        $("#transferClientId").val(_editData.clientId);
        $("#clientGroupName").selectpicker('val', _editData.groupId ? _editData.groupId : '')
        $("#clientGroupType").selectpicker('val', _editData.groupType ? _editData.groupType : '')
        FormFunc.loadObject(_editData, "#transferModal")
    }

    /**
     * 转移客户
     */
    saveTransClient = function () {
        var groupId = $("#clientGroupName").selectpicker('val')
        if(groupId == null){
            boldFunc.showErrorMes("请选择工号")
            return
        }
        var groupType = $("#clientGroupType").selectpicker('val')
        if(groupType == null){
            boldFunc.showErrorMes("请选择资源组")
            return
        }
        ParsleyFunc.checkForm(_transferModal, function () {
            var params = _transferModal.serializeHumpObject();
            params.id = $("#transferClientId").val();
            getDataByURL("/service/platform/login/clientManager/client/transClient", params, function (data) {
                boldFunc.notification("转移成功")
                _transferModal.modal("hide")
                refreshTableCustom();
            })
        })
    }


    cancelTransClient = function () {
        boldFunc.showConfirm("确定取消转移", function () {
            var params = _transferModal.serializeHumpObject();
            params.id = $("#transferClientId").val();
            getDataByURL("/service/platform/login/clientManager/client/cancelTransClient", params, function (data) {
                boldFunc.notification("取消转移成功")
                _transferModal.modal("hide")
                refreshTableCustom();
            })
        })
    }


    setAppeal = function (index, isAppeal) {
        var hint = "确定受理"
        if(isAppeal){
            hint = "确定申诉"
        }
        _editData = $("#table").bootstrapTable('getData')[index];
        _currentTableIndex = index
        _currentCallRecordId = _editData.id
        boldFunc.showConfirm(hint, function () {
            var params = {};
            params.id = _editData.clientId;
            params.isAppeal = isAppeal
            getDataByURL("/service/platform/login/clientManager/client/setAppeal", params, function (data) {
                boldFunc.notification("操作成功")
                refreshTableCustom();
            })
        })
    }

    /**
     * 转移选择的客户
     */
    transSelect = function () {
        var selectArr = $("#table").bootstrapTable('getSelections');
        if(selectArr.length == 0){
            boldFunc.showErrorMes("请选择要转移的通话记录")
            return
        }
        for(var i = 0 ; i < selectArr.length; i++){
            var groupType = selectArr[i].groupType;
            if(groupType != null){
                boldFunc.showErrorMes( selectArr[i].phone + "  已转移")
                return
            }
        }
        FormFunc.clearForm("#transferAllModal")
        _transferAllModal.modal("show")
    }

    /**
     * 批量转移
     */
    saveTransAllClient = function () {
        var groupId = $("#clientGroupNameTransferAll").selectpicker('val')
        if(groupId == null){
            boldFunc.showErrorMes("请选择工号")
            return
        }
        var groupType = $("#clientGroupTypeTransferAll").selectpicker('val')
        if(groupType == null){
            boldFunc.showErrorMes("请选择资源组")
            return
        }
        ParsleyFunc.checkForm(_transferAllModal, function () {
            var selectArr = $("#table").bootstrapTable('getSelections');
            var ids = '';
            for(var i = 0 ; i < selectArr.length; i++){
                ids += selectArr[i].clientId + ",";
            }
            ids = StringUtil.subLastChar(ids);
            var params = _transferAllModal.serializeHumpObject();
            params.callRecordId = ids;
            getDataByURL("/service/platform/login/clientManager/client/transAllClient", params, function (data) {
                boldFunc.notification("转移成功")
                _transferAllModal.modal("hide")
                $("#table").bootstrapTable('refresh')
            })
        })
    }

    /**
     * 自定义刷新
     */
    refreshTableCustom = function () {
        if(GlobalData.isReviewer){
            refreshTableSingle()
        }else{
            $("#table").bootstrapTable('refresh')
        }
    }

    /**
     * 导出通话记录
     */
    exportCallRecord = function () {
        boldFunc.showConfirmText("确定导出", null, function () {
            var params = queryParams();
            params.common = true;
            window.location.href = "/service/platform/login/systemManager/callRecord/export?" + StringUtil.urlEncode(params)
        })
    }
    
    queryParams = function () {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            phone:$("#recordPhone").val(),
            startTimeStr:$("#startTime").val(),
            endTimeStr:$("#endTime").val(),
            statusArr:$("#reviewStatus").selectpicker('val'),
            workNumber:$("#userId").val(),
            id:$("#reviewId").val(),
            projectId:$("#projectId").selectpicker('val'),
            transYn:$("#transYn").selectpicker('val'),
            isDistribute:$("#selectOnceStatus").selectpicker('val'),
            isAppeal:$("#selectIsAppeal").selectpicker('val'),
        }
        var groupId = $("#groupId").selectpicker('val')
        if(StringUtil.isNotEmpty(groupId)){
            var userIds = _userList[groupId]
            if(userIds.length > 0){
                param.userIds = ArrayFunc.toCommaStr(userIds);
            }
        }
        if(StringUtil.isEmpty(groupId) && StringUtil.isEmpty(param.workNumber)){
            var roleType = $("#roleType").selectpicker('val')
            switch (roleType){
                case "cs":
                    param.userIds = _roleUserIdMap.CS
                    break;
                case "interview":
                    param.userIds = _roleUserIdMap.interview;
                    break
            }
        }
        if(GlobalData.isCS || GlobalData.isInterview){
            param.userIds = ""
            param.workNumber = GlobalData.workNumber
        }
        if(GlobalData.isManager || GlobalData.isInterview){
            // 显示约访状态
            param.isInclude = true;
        }
        return param;
    }

    init();
}





