//# sourceURL=parameter.js
function init_systemSetting_parameter() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")

    init = function () {
        GlobalData.initPageJs();

        TableFunc.showTable({
            url: "/service/platform/login/systemSetting/parameter/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:50,
            pageList:[50,100,200,500],
            columns: [
                {
                    field: 'name',
                    title: '名称',
                    align: 'center',
                },
                {
                    field: 'content',
                    title: '内容',
                    align: 'center',
                },
                {
                    field: 'dataTypeStr',
                    title: '数据类型',
                    align: 'center',
                },
                {
                    field: '',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a = "";
                        a += '<button type="button" class="btn btn-success waves-effect waves-light"   data-toggle="modal" data-target="#modal" onclick="editExtAndIP(' + index + ')">修改</button>';
                        return a;
                    }
                }]
        });

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
            var url = "/service/platform/login/systemSetting/parameter/update";
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





