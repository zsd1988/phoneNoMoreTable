//# sourceURL=customerAllocated.js
function init_customerManagement_customerAllocated() {

    var _project="";
    var _importTag="";

    zTreeOnClick=function(event, treeId, treeNode) {
        if(StringUtil.isNotEmpty(treeNode.type)){
            if(treeNode.type=='allocated'){//公共资源
                _project="";
                _importTag="";
            }else if(treeNode.type=='project'){//公共资源
                _project=treeNode.id;
                _importTag="";
            }else if(treeNode.type=='importTag'){//分组
                _project=treeNode.pId;
                _importTag=treeNode.id;
            }
            $("#projectOption").selectpicker('val',_project);
            $("#importTagOption").selectpicker('val',_importTag);
            $("#table").bootstrapTable('refresh');
        }
    };

    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
            }
        },

        view: {
            showIcon: false,
        },

        callback: {
            onClick:  this.zTreeOnClick,
            onDblClick: this.zTreeOnDbClick,
        }

    };


    init = function () {
        if( GlobalData.functionIdArr.contains("84f782f3934549a49d44da9b4692dd6b")){
            $("#distributionClient").css("display","block");
        }

        var url="/service/platform/noLogin/clientManager/clientGroupController/allocatedClientGroupOption";
        //获取用户的菜单权限
        getDataByURL(url, null, function (data) {
            var allocatedClientGroup= data.data.allocatedClientGroup;
            $.fn.zTree.init($("#allocatedClientGroup"), setting,allocatedClientGroup);
        })
        TableFunc.showTable({
            url: "/service/platform/login/clientManager/client/list",
            queryParams: clientParam,
            pagination: true,
            columns: [
                {
                    field: 'name',
                    title: '客户姓名',
                    align: 'center',
                },
                {
                    field: 'phone',
                    title: '被叫号码',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(GlobalData.functionIdArr.contains("1bd24c8491134d988639ec6c3e7496c6")){
                            var a=value;
                            if(value.length==11){
                                a= value.substr(0, 3) + '****' + value.substr(7);
                            }else if(value.length==12){
                                a = value.substr(0, 4) + '****' + value.substr(8);
                            }
                            return a;
                        }else{
                            return value;
                        }
                    }
                },
                {
                    field: 'sexStr',
                    title: '性别',
                    align: 'center',
                },
                {
                    field: 'yearTagStr',
                    title: '年龄段',
                    align: 'center',
                },
                {
                    field: 'areaStr',
                    title: '区域',
                    align: 'center',
                },
                {
                    field: 'projectName',
                    title: '项目名称',
                    align: 'center',
                },
                {
                    field: 'importTagStr',
                    title: '资源类别',
                    align: 'center',
                },
                {
                    field: 'createTimeStr',
                    title: '创建时间',
                    align: 'center',
                },
            ]
        });
    }


    showAllocatedList=function(){
        _project="";
        _importTag="";
        $("#table").bootstrapTable('refresh');
    }

    clientParam=function () {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            projectId:  _project,
            importTag:  _importTag,
            isDistribute:   false,
        }
        return param;
    }

    distributionClient=function () {
        ParsleyFunc.checkForm($("#con-distribution-modal"), function () {
            if(StringUtil.isEmpty($("#projectOption").val())){
                boldFunc.showErrorMes("请选择项目");
                return;
            }
            if(StringUtil.isEmpty($("#importTagOption").val())){
                boldFunc.showErrorMes("请选客户类别");
                return;
            }
            if(StringUtil.isEmpty($("#clientGroupOption").val())){
                boldFunc.showErrorMes("请选择客户组别 ");
                return;
            }
            if(StringUtil.isEmpty($("#clientGroupTypeOption").val())){
                boldFunc.showErrorMes("跟进类别");
                return;
            }
            //检测数量

            var params = new FormData(document.getElementById("con-distribution-modal"));
            var url = "/service/platform/noLogin/clientManager/clientGroupController/distributionClient";
            getFormDataByURL(url, params, function (data) {
                boldFunc.notification("分配成功");
                $("#closeDistribution").click();
                $("#table").bootstrapTable('refresh');
            })
        })
    }
    

    //获取年龄段和地区信息
    getMapOptions=function () {
        //获取下拉框数据
        var params = {params:"Project,ImportTag,ClientGroupNameYf,ClientGroupType"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data;
            SelectPickerFunc.loadMap('projectOption', dataJson.Project, true);
            SelectPickerFunc.loadLocalData('importTagOption', JSON.parse(dataJson.ImportTag ), true);
            SelectPickerFunc.loadMap('clientGroupOption',dataJson.ClientGroupNameYf, true);
            SelectPickerFunc.loadLocalData('clientGroupTypeOption', JSON.parse(dataJson.ClientGroupType), true);
        })
    }

    clearDistributionData=function () {
        FormFunc.clearForm("#con-distribution-modal");
    }

    init();
    getMapOptions();
}





