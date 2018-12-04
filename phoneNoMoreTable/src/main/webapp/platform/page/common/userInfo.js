//# sourceURL=userInfo.js
function init_common_userInfo() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")
    var _headImageFunc;

    init = function () {
        GlobalData.initPageJs();
        _modal.css("display", "block");
        _modal.addClass("webuploader-element-invisible");
        _modal.on('show.bs.modal', function () {
            _modal.removeClass("webuploader-element-invisible")
        })
        _headImageFunc = UploadFunc.uploadInit({
            id:"headImage",
            width:60,
            height:60,
            callback:function (data) {
                $("#headImageSourcePath").val(data.sourceImg);
            }
        });

        DateTimePickerFunc.init({
            id:"employeeDay",
            selectDay:true
        })

        DateTimePickerFunc.init({
            id:"birthDay",
            selectDay:true
        })

        TableFunc.showTable({
            url: "/service/platform/login/user/userInfo/list",
            queryParams: queryParams,
            pagination: true,
            pageSize:50,
            pageList:[50,100,200,500],
            columns: [
                {
                    field: 'headImage',
                    title: '头像',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = "";
                        if(StringUtil.isNotEmpty(value)){
                            a = '<img  style="height: 60px;padding: 5px"  src="' + GlobalData.fileUrl + value +'" />';
                        }
                        return a;
                    }
                },
                {
                    field: 'name',
                    title: '姓名',
                    align: 'center',
                },
                {
                    field: 'employeeDayStr',
                    title: '入职日期',
                    align: 'center',
                },
                {
                    field: 'birthDayStr',
                    title: '生日',
                    align: 'center',
                },
                {
                    field: '',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a = "";
                        a += '<button type="button" class="btn btn-success waves-effect waves-light"   data-toggle="modal" data-target="#modal" onclick="edit(' + index + ')">修改</button>';
                        return a;
                    }
                }]
        });

    }

    edit = function (index) {
        _isEdit=true;
        _editData = $("#table").bootstrapTable('getData')[index];
        $("#title").text("修改")
        if(StringUtil.isNotEmpty(_editData.headImage)){
            var uploadData = {sourceImg:_editData.headImage}
            _headImageFunc.load(uploadData, 'image')
        }else{
            _headImageFunc.loadDefaultPng("headImageSourcePath")
        }
        FormFunc.loadObject(_editData, "#modal")
    }

    addExtAndIP = function () {
        _isEdit=false;
        FormFunc.clearForm("#modal")
    }

    query = function () {
        $("#table").bootstrapTable('refresh')
    }

    save = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/user/userInfo/update";
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





