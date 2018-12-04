//# sourceURL=luckDraw.js
function init_system_luckDraw() {
    var _isEdit = false;
    var _todayStr = DateUtil.getNowFormatDate();
    var _editData = null;
    var _modal = $("#modal")
    var _table=document.getElementById("recordTable");

    init = function () {
        GlobalData.initPageJs();
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)

        if(GlobalData.isManager){
            $("#luckDrawManager").show()
            $("#getAll").show()
        }else{
            $("#selectMine").show()
            $("#isMine").selectpicker({})
            $("#isMine").selectpicker("val", '')
        }

        DateTimePickerFunc.init({
            id:"startTime",
            selectDay:true
        })

        DateTimePickerFunc.init({
            id:"endTime",
            selectDay:true
        })

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/luckDraw/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:20,
            pageList:[20,50,100],
            columns: [
                {
                    field: 'name',
                    title: '名称',
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
                    field: 'recordCount',
                    title: '当天中奖',
                    align: 'center',
                },
                {
                    field: 'dayCount',
                    title: '每日发放',
                    align: 'center',
                },
                {
                    field: 'isDel',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if (value) {
                            var a = '<button type="button" class="btn btn-info waves-effect waves-light"  onclick="ableLuckDraw(' + index + ',1)">启用</button> ';
                            return a;
                        }else{
                            var a = '<button type="button" class="btn btn-danger waves-effect waves-light"  onclick="ableLuckDraw(' + index + ',0)">禁用</button> ';
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
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"  onclick="editLuckDraw(' + index + ')">修改</button> '
                        return a;
                    }
                }
            ]
        });

        var colums = [
            {
                checkbox:true,
                align : 'center',
            },
            {
                field: 'name',
                title: '奖品名称',
                align: 'center',
            },
            {
                field: 'userName',
                title: '用户名称',
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
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"  onclick="getLuckDraw(' + index + ')">领取</button> ';
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
            url: "/service/platform/login/systemManager/LuckDrawRecord/list",
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
            getDataByURL("/service/platform/login/systemManager/LuckDrawRecord/getAll", params, function (data) {
                boldFunc.notification("领取成功")
                $("#recordTable").bootstrapTable('refresh')
            })
        })
    }

    getLuckDraw = function (index) {
        _editData = $("#recordTable").bootstrapTable('getData')[index];
        boldFunc.showConfirm("确定领取奖品", function () {
            getDataByURL("/service/platform/login/systemManager/LuckDrawRecord/get", {id:_editData.id}, function () {
                boldFunc.notification("操作成功");
                $("#recordTable").bootstrapTable('refresh');
            })
        })
    }

    //任务启用disable=1/禁用disable=0
    ableLuckDraw = function (index,value) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var params = {
            id:_editData.id
        }
        if (value) {
            var url = "/service/platform/login/systemManager/luckDraw/enable";
        }else{
            var url = "/service/platform/login/systemManager/luckDraw/delete";
        }
        var str = _editData.value ? "禁用" : "启用"
        var title = str + "  " + _editData.name;
        boldFunc.showConfirm(title, function () {
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    editLuckDraw = function (index) {
        _isEdit=true;
        _editData = $("#table").bootstrapTable('getData')[index];
        $("#title").text("修改")
        FormFunc.loadObject(_editData, "#modal")
        _modal.modal('show')
    }

    addLuckDraw = function () {
        _isEdit=false;
        FormFunc.clearForm("#modal")
    }

    reset = function () {
        $("#userName").val('')
        $("#startTime").val(_todayStr)
        $("#endTime").val(_todayStr)
        query()
    }

    query = function () {
        $("#recordTable").bootstrapTable('refresh')
        TableFunc.makeSortable(_table,0,false);
        TableFunc.makeSortable(_table,1,false);
        TableFunc.makeSortable(_table,2,false);
        TableFunc.makeSortable(_table,3,false);
    }

    saveLuckDraw = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/systemManager/luckDraw/create";
            if(_isEdit){
                url = "/service/platform/login/systemManager/luckDraw/update";
            }
            if( !NumFunc.isInteger(params.rate)){
                boldFunc.showErrorMes("请设置概率为整数")
                return
            }
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                _modal.modal('hide')
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    queryParams = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            }
        }
        return param;
    }

    queryParamsRecord = function (params) {
        var param = {
            name:$("#userName").val(),
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





