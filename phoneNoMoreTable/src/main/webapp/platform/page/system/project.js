//# sourceURL=project.js
function init_system_project() {
    var currentJson = GlobalData.currentJson;
    var isEdit = false;
    if(currentJson.isEdit){
        isEdit = true;
    }
    var isDel = false;
    var editData = null;
    var $project_modal=$("#con-project-modal");
    var introduceFileNameCallBack = "";
    var introduceFilePathCallBack = "";
    var uploadIntroduceFileDivFunc;
    var retrieveFileNameCallBack = "";
    var retrieveFilePathCallBack = "";
    var uploadRetrieveFileDivFunc;

    init = function () {
        GlobalData.initPageJs();

        $project_modal.css("display", "block");
        $project_modal.addClass("webuploader-element-invisible");
        $project_modal.on('show.bs.modal', function () {
            //FormFunc.clearForm("#con-project-modal")
            $project_modal.removeClass("webuploader-element-invisible")
        })

        //为上传组件注册事件
        uploadIntroduceFileDivFunc = UploadFunc.uploadInit({
            id:"uploadIntroduceVoice",
            width:1400,
            height:38,
            //supportAudio:true,
            supportZip:true,
            supportImage:true,
            supportVideo:true,
            supportOffice:true,
            callback:function (data) {
                $("#introduceVoiceSourceId").val(data.id);
                introduceFileNameCallBack = data.name;
                introduceFilePathCallBack = data.sourcePathUrl;
            }
        });

        uploadRetrieveFileDivFunc = UploadFunc.uploadInit({
            id:"uploadRetrieveVoice",
            width:1400,
            height:38,
            supportZip:true,
            supportImage:true,
            supportVideo:true,
            supportOffice:true,
            callback:function (data) {
                $("#introduceVoiceSourceId").val(data.id);
                retrieveFileNameCallBack = data.name;
                retrieveFilePathCallBack = data.sourcePathUrl;
            }
        });

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/Project/listClientNum",
            queryParams: queryParams,
            pagination: false,
            columns: [
                {
                    field: 'name',
                    title: '项目名称',
                    align: 'center',
                },
                {
                    field: 'sumA',
                    title: 'A类数量',
                    align: 'center',
                },
                {
                    field: 'sumB',
                    title: 'B类数量',
                    align: 'center',
                },
                {
                    field: 'sumC',
                    title: 'C类数量',
                    align: 'center',
                },
                {
                    field: 'sumD',
                    title: 'D类数量',
                    align: 'center',
                },
                {
                    field: 'sumE',
                    title: 'E类数量',
                    align: 'center',
                },
                {
                    field: 'sumDistribute',
                    title: '已导入群呼/分配',
                    align: 'center',
                },
                {
                    field: '',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = '<button type="button" class="btn btn-success waves-effect waves-light"  onclick="edit(' + index + ')">修改</button> '+
                            '<button type="button" class="btn btn-success waves-effect waves-light" data-toggle="modal" data-target="#con-import-modal" onclick="importClient(' + index + ')">导入</button> ';
                        return a;
                    }
                }
            ]
        });
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

    clearModalData = function () {
        $("#title").text("添加项目");
        FormFunc.clearForm("#con-project-modal");
        FormFunc.clearForm("#con-import-modal");
        isEdit = false;
    }

    edit = function (index) {
        editData = $("#table").bootstrapTable('getData')[index];
        gotoPage('system/projectEdit', {isEdit: true, data: editData})
    }

    importClient = function(index){
        editData = $("#table").bootstrapTable('getData')[index];
        FormFunc.loadObject(editData, "#con-import-modal")
        $("#importTag").selectpicker('val', 'A');
    }

    //获取下拉框数据
    var params = {params:"ImportTag"}
    getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
        var dataJson = data.data
        _ImportTag = JSON.parse(dataJson.ImportTag );
        SelectPickerFunc.loadLocalData('importTag', _ImportTag, true)
        //SelectPickerFunc.loadMap('project',dataJson.Project, true)
    })

    saveProjectClientXls = function () {
        var params = new FormData(document.getElementById("con-import-modal"));
        var url = "/service/platform/login/clientManager/client/saveProjectClient";
        getFormDataByURL(url, params, function (data) {
            boldFunc.notification(data.message);
            $("#con-import-modal").modal('hide');
            $("#table").bootstrapTable('refresh')
        })
    };

    init();

}





