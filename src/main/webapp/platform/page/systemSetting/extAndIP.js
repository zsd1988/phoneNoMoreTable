//# sourceURL=extAndIP.js
function init_systemSetting_extAndIP() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")

    init = function () {
        GlobalData.initPageJs();

        TableFunc.showTable({
            url: "/service/platform/login/systemSetting/extAndIP/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:500,
            pageList:[500,1000,2000,5000],
            columns: [
                {
                    field: 'ip',
                    title: 'IP',
                    align: 'center',
                },
                {
                    field: 'extNum',
                    title: '分机号',
                    align: 'center',
                },
                {
                    field: '',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a = "";
                        if(row.isDel){
                            a += '<button type="button" class="btn  btn-success waves-effect waves-light" onclick="enableExtAndIP(' + index + ')">启用</button>';
                        }else{
                            a += '<button type="button" class="btn btn-danger waves-effect waves-light" onclick="enableExtAndIP(' + index + ')">禁用</button>';
                        }
                        a += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"   data-toggle="modal" data-target="#modal" onclick="editExtAndIP(' + index + ')">修改</button>';
                        return a;
                    }
                }]
        });

    }


    enableExtAndIP = function (index) {
        _editData = $("#table").bootstrapTable('getData')[index];
        var str = _editData.isDel ? "启用" : "禁用"
        var title = str + "  " + _editData.id;
        boldFunc.showConfirm(title, function () {
            getDataByURL("/service/platform/login/systemSetting/extAndIP/setIsEnable", {id:_editData.id, isDel:!_editData.isDel}, function () {
                boldFunc.notification("操作成功");
                _modal.modal('hide')
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    editExtAndIP = function (index) {
        _isEdit=true;
        _editData = $("#table").bootstrapTable('getData')[index];
        $("#title").text("修改")
        FormFunc.loadObject(_editData, "#modal")
    }

    addExtAndIP = function () {
        _isEdit=false;
        FormFunc.clearForm("#modal")
    }

    query = function () {
        $("#table").bootstrapTable('refresh')
    }

    saveExtAndIP = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/systemSetting/extAndIP/create";
            if(_isEdit){
                url = "/service/platform/login/systemSetting/extAndIP/update";
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
            },
        }
        return param;
    }

    init();
}





