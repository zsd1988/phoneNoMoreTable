//# sourceURL=dayHint.js
function init_system_dayHint() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")
    var _table=document.getElementById("recordTable");

    init = function () {
        GlobalData.initPageJs();
        var params = {params:"DayHintType"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            var dayHintType = JSON.parse(dataJson.DayHintType )
            SelectPickerFunc.loadLocalData('dayHintTypeSearch', dayHintType, true)
            SelectPickerFunc.loadLocalData('type', dayHintType, true)
        })

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/dayHint/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:100,
            pageList:[100,200,500],
            columns: [
                {
                    field: 'typeStr',
                    title: '类型',
                    align: 'center',
                },
                {
                    field: 'content',
                    title: '内容',
                    align: 'center',
                },
                {
                    field: 'rateStr',
                    title: '概率',
                    align: 'center',
                },
                {
                    field: 'isDel',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if (value) {
                            var a = '<button type="button" class="btn btn-info waves-effect waves-light"  onclick="able(' + index + ',1)">启用</button> ';
                            return a;
                        }else{
                            var a = '<button type="button" class="btn btn-danger waves-effect waves-light"  onclick="able(' + index + ',0)">禁用</button> ';
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
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"  onclick="edit(' + index + ')">修改</button> '
                        return a;
                    }
                }
            ]
        });
    }

    able = function (index,value) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var params = {
            id:_editData.id
        }
        if (value) {
            var url = "/service/platform/login/systemManager/dayHint/enable";
        }else{
            var url = "/service/platform/login/systemManager/dayHint/delete";
        }
        var str = _editData.isDel ? "确定启用" : "确定禁用"
        boldFunc.showConfirm(str, function () {
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    edit = function (index) {
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

    save = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/systemManager/dayHint/create";
            if(_isEdit){
                url = "/service/platform/login/systemManager/dayHint/update";
            }
            if(StringUtil.isEmpty(params.type)){
                boldFunc.showErrorMes("请选择类型")
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
        $("#dayHintTypeSearch").selectpicker('val', '')
        query()
    }

    query = function () {
        $("#table").bootstrapTable('refresh')
    }

    queryParams = function (params) {
        var param = {
            roleType:$("#dayHintTypeSearch").selectpicker('val'),
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
        }
        return param;
    }

    init();

}





