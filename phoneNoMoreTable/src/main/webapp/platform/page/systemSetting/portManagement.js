//# sourceURL=portManagement.js
function init_systemSetting_portManagement() {
    var _modal = $("#modal")
    var _editData;

    init = function () {
        $("#isFirst").selectpicker({});
        $("#isCallOut").selectpicker();
        $("#isThird").selectpicker();
        TableFunc.showTable({
            url: "/service/platform/login/systemSetting/port/getEnableList",
            queryParams: queryEnableParams,
            elm:"enableTable",
            pagination:false,
            columns: [
                {
                    field: 'dev',
                    title: '设备',
                    align: 'center',
                },
                {
                    field: 'count',
                    title: '启动数量',
                    align: 'center',
                },
                {
                    field: 'totalCount',
                    title: '总数量',
                    align: 'center',
                },
                {
                    field: 'isThird',
                    title: '第三方',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(row.dev == "总计"){
                            return '';
                        }
                        return TableFunc.formatter.bool(value, row, index)
                    }
                },
                {
                    field: 'isCallOut',
                    title: '打外地号',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(row.dev == "总计"){
                            return '';
                        }
                        return TableFunc.formatter.bool(value, row, index)
                    }
                },
                {
                    field: 'isFirst',
                    title: '优先拨打',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(row.dev == "总计"){
                            return '';
                        }
                        return TableFunc.formatter.bool(value, row, index)
                    }
                },
                {
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(row.dev == "总计"){
                            return '';
                        }
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"   data-toggle="modal" data-target="#modal" onclick="editPortNum(' + index + ')">修改</button>';
                        return a;
                    }
                }]
        });

        TableFunc.showTable({
            url: "/service/platform/login/systemSetting/port/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:500,
            pageList: [500, 1000, 2000, 5000],
            columns: [
                {
                    field: 'id',
                    title: '端口编码',
                    align: 'center',
                },
                {
                    field: 'isDel',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if (value) {
                            var a = '<button type="button" class="btn btn-info waves-effect waves-light"  onclick="ablePort(' + index + ',1)">启用</button> ';
                        }else{
                            var a = '<button type="button" class="btn btn-danger waves-effect waves-light"  onclick="ablePort(' + index + ',0)">禁用</button> ';
                        }
                        a += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"  onclick="callPort(' + index + ')">拨打</button>';
                        return a;
                    }
                },
                {
                    field: 'statusStr',
                    title: '状态',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        if (value=='空闲') {
                            var a = '<p>空闲</p>';
                            return a;
                        }
                        if (value=='呼叫中') {
                            var a = '<p>呼叫中</p>';
                            return a;
                        }
                        if (value=='通话中') {
                            var a = '<p>通话中</p>';
                            return a;
                        }
                    }
                }]
        });
    }

    enableEditCount = function () {
        var val = $("#isThird").selectpicker("val")
        if(val == "true"){
            $("#count").removeAttr("disabled");
        }else{
            $("#count").attr("disabled",'disabled');
        }
    }

    callPort = function (index) {
        var data = $("#table").bootstrapTable('getData')[index];
        var callPhone = $("#callPhone").val();
        if(StringUtil.isEmpty(callPhone)){
            boldFunc.showErrorMes("请输入手机号")
            return
        }
        boldFunc.showConfirm("确定拨打端口： " + data.id, function () {
            var params = {
                id:data.id,
                isThird:data.isThird,
                called:callPhone
            };
            getDataByURL("/service/platform/login/systemSetting/port/callPort", params, function () {
                boldFunc.notification("操作成功");
                _modal.modal('hide')
                $("#enableTable").bootstrapTable('refresh');
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    editPortNum = function (index) {
        var data = $("#enableTable").bootstrapTable('getData')[index];
        FormFunc.loadObject(data, "#modal")
        if(data.isThird){
            $("#count").removeAttr("disabled");
        }else{
            $("#count").attr("disabled",'disabled');
        }
    }

    /**
     * 保存更改
     */
    savePortNum = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            params.dev = $("#devName").val()
            params.count = $("#count").val()
            getDataByURL("/service/platform/login/systemSetting/port/savePortNum", params, function () {
                boldFunc.notification("操作成功");
                _modal.modal('hide')
                $("#enableTable").bootstrapTable('refresh');
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    //任务启用disable=1/禁用disable=0
    ablePort = function (index,value) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var params = {
            id:_editData.id
        }
        if (value) {
            var url = "/service/platform/login/systemSetting/port/enable";
        }else{
            var url = "/service/platform/login/systemSetting/port/delete";
        }
        var str = _editData.isDel ? "启用" : "禁用"
        var title = str + "  " + _editData.id;
        boldFunc.showConfirm(title, function () {
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                $("#table").bootstrapTable('refresh');
            })
        })
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

    queryEnableParams = function (params) {
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






