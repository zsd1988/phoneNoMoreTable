//# sourceURL=app.js
function init_systemSetting_app() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")
    var _uploadZipFunc

    init = function () {
        GlobalData.initPageJs();

        _modal.css("display", "block");
        _modal.addClass("webuploader-element-invisible");
        _modal.on('show.bs.modal', function () {
            _modal.removeClass("webuploader-element-invisible")
        })

        _uploadZipFunc = UploadFunc.uploadInit({
            id:"uploadZip",
            width:365,
            height:38,
            supportZip:true,
            callback:function (data) {
                $("#downloadUrl").val(data.sourceImg);
            }
        });

        //获取下拉框数据
        var params = {params:"OsType"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            SelectPickerFunc.loadLocalData('osType', JSON.parse(dataJson.OsType ), true)
        })

        TableFunc.showTable({
            url: "/service/platform/login/systemSetting/app/list",
            queryParams: queryParams,
            pagination: true,
            columns: [
                {
                    field: 'osTypeStr',
                    title: '客户端类型',
                    align: 'center',
                },
                {
                    field: 'leastVersion',
                    title: '最低版本',
                    align: 'center',
                },
                {
                    field: 'downloadUrl',
                    title: '下载地址',
                    align: 'center',
                    formatter: function (value, row, index) {
                        return GlobalData.fileUrl + value;
                    }
                },
                {
                    field: '',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a ='<button type="button" class="btn btn-success waves-effect waves-light"   data-toggle="modal" data-target="#modal" onclick="editApp(' + index + ')">修改</button>';
                        return a;
                    }
                }]
        });

    }

    editApp = function (index) {
        _isEdit=true;
        _editData = $("#table").bootstrapTable('getData')[index];
        $("#title").text("修改")
        FormFunc.loadObject(_editData, "#modal")
        var dataJson = {sourceImg:_editData.downloadUrl}
        _uploadZipFunc.load(dataJson, "zip")
    }

    addApp = function () {
        _isEdit=false;
        FormFunc.clearForm("#modal")
    }

    query = function () {
        $("#table").bootstrapTable('refresh')
    }

    saveApp = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/systemSetting/app/create";
            if(_isEdit){
                url = "/service/platform/login/systemSetting/app/update";
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





