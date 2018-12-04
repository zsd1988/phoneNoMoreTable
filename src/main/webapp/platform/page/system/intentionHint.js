//# sourceURL=intentionHint.js
function init_system_intentionHint() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")
    var _todayStr = DateUtil.getNowFormatDate();
    var _table=document.getElementById("recordTable");

    init = function () {
        GlobalData.initPageJs();

        if(GlobalData.isManager){
            $("#intentionHintManager").show()
            $("#getAll").show()
        }else{
            $("#selectMine").show()
            $("#isMine").selectpicker({})
            $("#isMine").selectpicker("val", '')
        }

        $("#isReward").selectpicker()
        $("#isRewardSearch").selectpicker()
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
        var params = {params:"IntentionHintType"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            var intentionHintType = JSON.parse(dataJson.IntentionHintType )
            SelectPickerFunc.loadLocalData('recordHintType', intentionHintType, true)
            SelectPickerFunc.loadLocalData('type', intentionHintType, true)
            SelectPickerFunc.loadLocalData('hintType', intentionHintType, true)
        })

        DateTimePickerFunc.init({
            id:"startTime",
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
                field: 'content',
                title: '词条内容',
                align: 'center',
            },
            {
                field: 'userName',
                title: '用户名称',
                align: 'center',
            },
            {
                field: 'typeStr',
                title: '活动类型',
                align: 'center',
            },
            {
                field: 'createTimeStr',
                title: '时间',
                align: 'center',
            }
        ]
        if(GlobalData.isManager){
            colums.push({
                field: 'isGet',
                title: '是否领取',
                valign: 'middle',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value) {
                        var a = '<span   >已领取</span> ';
                        return a;
                    }else{
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"  onclick="getIntentionHint(' + index + ')">领取</button> ';
                        return a;
                    }
                }
            })
        }else{
            colums.push({
                field: 'isGet',
                title: '是否领取',
                valign: 'middle',
                align: 'center',
                formatter: function (value, row, index) {
                    if (value) {
                        var a = '<span   >已领取</span> ';
                        return a;
                    }else{
                        var addColor = '';
                        if(GlobalData.userName == row.userName){
                            addColor = 'style="color: red"'
                        }
                        var a = '<span ' + addColor + '  >未领取</span> ';
                        return a;
                    }
                }
            })
        }
        TableFunc.showTable({
            url: "/service/platform/login/systemManager/intentionHintRewardRecord/list",
            queryParams: queryParamsRecord,
            pagination: true,
            pageSize:100,
            pageList:[100,200,500,1000],
            elm:"recordTable",
            columns: colums
        });
        TableFunc.makeSortable(_table,0,false);
        TableFunc.makeSortable(_table,1,false);
        TableFunc.makeSortable(_table,2,false);
        TableFunc.makeSortable(_table,3,false);

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/intentionHint/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:100,
            pageList:[100,200,500],
            columns: [
                {
                    field: 'typeStr',
                    title: '活动类型',
                    align: 'center',
                },
                {
                    field: 'content',
                    title: '内容',
                    align: 'center',
                },
                {
                    field: 'count',
                    title: '库存',
                    align: 'center',
                },
                {
                    field: 'rateStr',
                    title: '概率',
                    align: 'center',
                },
                {
                    field: 'todayCount',
                    title: '当天已出次数',
                    align: 'center',
                },
                {
                    field: 'dayCount',
                    title: '每日发放',
                    align: 'center',
                },
                {
                    field: 'isReward',
                    title: '词条奖品',
                    align: 'center',
                    formatter: function (value, row, index) {
                        return TableFunc.formatter.bool(value, row, index)
                    }
                },
                {
                    field: 'isDel',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if (value) {
                            var a = '<button type="button" class="btn btn-info waves-effect waves-light"  onclick="ableIntentionHint(' + index + ',1)">启用</button> ';
                            return a;
                        }else{
                            var a = '<button type="button" class="btn btn-danger waves-effect waves-light"  onclick="ableIntentionHint(' + index + ',0)">禁用</button> ';
                            return a;
                        }
                    }
                },
                {
                    field: '',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"  onclick="editIntentionHint(' + index + ')">修改</button> '
                        return a;
                    }
                }
            ]
        });
    }

    getAll = function () {
        var selectArr = $("#recordTable").bootstrapTable('getSelections');
        if(selectArr.length == 0){
            boldFunc.showErrorMes("请选择要领取的奖品")
            return
        }
        boldFunc.showConfirm("确定批量领取", function () {
            var ids = '';
            for(var i = 0 ; i < selectArr.length; i++){
                ids += selectArr[i].id + ",";
            }
            ids = StringUtil.subLastChar(ids);
            var params = {};
            params.ids = ids;
            getDataByURL("/service/platform/login/systemManager/intentionHintRewardRecord/getAll", params, function (data) {
                boldFunc.notification("领取成功")
                $("#recordTable").bootstrapTable('refresh')
            })
        })
    }

    ableIntentionHint = function (index,value) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var params = {
            id:_editData.id
        }
        if (value) {
            var url = "/service/platform/login/systemManager/intentionHint/enable";
        }else{
            var url = "/service/platform/login/systemManager/intentionHint/delete";
        }
        var str = _editData.isDel ? "确定启用" : "确定禁用"
        boldFunc.showConfirm(str, function () {
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    getIntentionHint = function (index) {
        _editData = $("#recordTable").bootstrapTable('getData')[index];
        boldFunc.showConfirm("确定领取奖品", function () {
            getDataByURL("/service/platform/login/systemManager/intentionHintRewardRecord/get", {id:_editData.id}, function () {
                boldFunc.notification("操作成功");
                $("#recordTable").bootstrapTable('refresh');
            })
        })
    }

    editIntentionHint = function (index) {
        _isEdit=true;
        _editData = $("#table").bootstrapTable('getData')[index];
        $("#title").text("修改")
        FormFunc.loadObject(_editData, "#modal")
        _modal.modal('show')
    }

    addIntentionHint = function () {
        _isEdit=false;
        FormFunc.clearForm("#modal")
    }

    resetRecord = function () {
        $("#userName").val('')
        $("#recordHintType").selectpicker('val', '')
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
        queryRecord()
    }

    queryRecord = function () {
        $("#recordTable").bootstrapTable('refresh')
        TableFunc.makeSortable(_table,0,false);
        TableFunc.makeSortable(_table,1,false);
        TableFunc.makeSortable(_table,2,false);
        TableFunc.makeSortable(_table,3,false);
    }

    saveIntentionHint = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/systemManager/intentionHint/create";
            if(_isEdit){
                url = "/service/platform/login/systemManager/intentionHint/update";
            }
            if(StringUtil.isEmpty(params.type)){
                boldFunc.showErrorMes("请选择活动类型")
                return
            }
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                _modal.modal('hide')
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    reset = function () {
        $("#hintType").selectpicker('val', '')
        $("#isRewardSearch").selectpicker('val', '')
        query()
    }

    query = function () {
        $("#table").bootstrapTable('refresh')
    }

    queryParams = function (params) {
        var param = {
            roleType:$("#hintType").selectpicker('val'),
            isInclude:$("#isRewardSearch").selectpicker('val'),
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
        }
        return param;
    }

    queryParamsRecord = function (params) {
        var param = {
            name:$("#userName").val(),
            roleType:$("#recordHintType").selectpicker('val'),
            startTimeStr:$("#startTime").val(),
            endTimeStr:$("#endTime").val(),
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
        }
        if( ! GlobalData.isManager){
            param.isInclude = $("#isMine").selectpicker('val')
        }
        return param;
    }

    init();

}





