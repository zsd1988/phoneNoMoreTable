//# sourceURL=postMessage.js
function init_system_postMessage() {
    var _isEdit = false;
    var _editData = null;
    var _modal = $("#modal")
    var _table=document.getElementById("recordTable");

    init = function () {
        GlobalData.initPageJs();

        DateTimePickerFunc.init({
            id:"pushTime",
            selectMinutes:true
        })

        var params = {params:"PostMessageType,RoleType"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            SelectPickerFunc.loadLocalData('receiveRoleTypes', JSON.parse(dataJson.RoleType ), true)
        })

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/postMessage/list",
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
                    field: 'pushTimeStr',
                    title: '推送时间',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = ''
                        if(StringUtil.isNotEmpty(value)){
                            a = value
                        }else{
                            a = "立即推送"
                        }
                        return a;
                    }
                },
                {
                    field: 'roleTypeName',
                    title: '接收角色',
                    align: 'center',
                },
                {
                    field: 'isDel',
                    title: '操作',
                    valign: 'middle',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var a = ''
                        if( ! row.isPush){
                            if (value) {
                                a = '<button type="button" class="btn btn-info waves-effect waves-light"  onclick="able(' + index + ',1)">启用</button> ';
                            }else{
                                a = '<button type="button" class="btn btn-danger waves-effect waves-light"  onclick="able(' + index + ',0)">删除</button> ';
                            }
                            if(row.type == "Admin"){
                                a += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"  onclick="edit(' + index + ')">修改</button> '
                            }
                        }else{
                            a = '已推送'
                        }
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
            var url = "/service/platform/login/systemManager/postMessage/enable";
        }else{
            var url = "/service/platform/login/systemManager/postMessage/delete";
        }
        var str = _editData.isDel ? "确定启用" : "确定删除"
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
        var roleTypeArr = _editData.receiveRoleTypes.split(',')
        $("#receiveRoleTypes").selectpicker('val', roleTypeArr)
        _modal.modal('show')
    }

    addIntentionHint = function () {
        _isEdit=false;
        FormFunc.clearForm("#modal")
    }

    save = function () {
        ParsleyFunc.checkForm(_modal, function () {
            var params = _modal.serializeHumpObject();
            var url = "/service/platform/login/systemManager/postMessage/create";
            if(_isEdit){
                url = "/service/platform/login/systemManager/postMessage/update";
            }
            var roleTypeArr = $("#receiveRoleTypes").selectpicker('val')
            if(roleTypeArr  == null){
                boldFunc.showErrorMes("请选择接收角色")
                return
            }
            var roleTypes = ''
            for(var i = 0; i < roleTypeArr.length; i++){
                roleTypes += roleTypeArr[i] + ","
            }
            params.receiveRoleTypes = StringUtil.subLastChar(roleTypes)
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                _modal.modal('hide')
                $("#table").bootstrapTable('refresh');
            })
        })
    }

    reset = function () {
        query()
    }

    query = function () {
        $("#table").bootstrapTable('refresh')
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





