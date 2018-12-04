//# sourceURL=rolesEdit.js

function init_common_rolesEdit() {
    var $permissionsTags = $("#permissionsTags")

    init = function () {
        GlobalData.initPageJs();

        SelectPickerFunc.loadLocalData('permissionsTags', GlobalData.permissionsTags, true)

        var title = "新增角色"
        if(isEdit){
            $permissionsTags.selectpicker('val', currentJsonData.tag);
            title = "修改角色"
        }
        $("#title").text(title)
        showMenu();
    }

    save = function() {
        ParsleyFunc.checkForm($("#floorForm"), function () {
            var params = $("#floorForm").serializeHumpObject()
            var url = "/service/platform/login/user/roles/create"
            params.tag = $permissionsTags.selectpicker('val');
            if(isEdit){
                url = "/service/platform/login/user/roles/update";
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                var selectPermissionNodes = treeObj.getCheckedNodes(true);
                var permissionIds = '';
                for(var i = 0 ; i < selectPermissionNodes.length; i++){
                    permissionIds += selectPermissionNodes[i].id + ","
                }
                params.permissionIds = StringUtil.subLastChar(permissionIds);
            }else{
                if(params.tag == null){
                    boldFunc.notification("请选择菜单类型")
                    return;
                }
            }
            getDataByURL(url, params, function (data) {
                boldFunc.notification("操作成功")
                if( isEdit){
                    goBack();
                }else{
                    isEdit = true;
                    $("#id").val(data.data.id)
                    showMenu();
                }
            })
        })
    }

    del = function () {
        var hint = "禁用"
        if(isDel){
            hint = "启用"
        }
        boldFunc.showConfirmText("确定" + hint + "角色", $("#roleType").val(), function () {
            var params = {
                id:$("#id").val(),
                isDel:!isDel
            }
            getDataByURL("/service/platform/login/user/roles/onOff",params, function (data) {
                boldFunc.notification("操作成功")
                goBack();
            })
        })
    }

    /**
     * 显示菜单权限列表
     */
    showMenu = function () {
        if(isEdit){
            $("#setMenuDiv").show();
            getDataByURL("/service/platform/login/user/rolePermission/getByRoleId", {roleId:$("#id").val()}, function (data) {
                var dataJson = data.data;
                var nodes = new Array();
                for(var i = 0; i < dataJson.length; i++){
                    var item = dataJson[i];
                    var node = {
                        id:item.id,
                        pId:item.fid,
                        name:item.permissionName,
                        open:true,
                        checked:item.isHave,
                    }
                    nodes.push(node);
                }
                $.fn.zTree.init($("#tree"), setting, nodes);
            })

            var $delBtn = $("#delBtn")
            $delBtn.show();
            if(currentJson.data != null && currentJson.data.isDel){
                isDel = true;
                $delBtn.text("启用")
                $delBtn.removeClass("btn-warning")
                $delBtn.addClass("btn-info")
            }else{
                $delBtn.addClass("btn-warning")
                $delBtn.removeClass("btn-info")
                $delBtn.text("禁用")
            }
        }else{
            $("#setMenuDiv").hide();
        }
    }

    var currentJson = GlobalData.currentJson;
    var currentJsonData = null;
    var isEdit = false;
    if(currentJson.isEdit){
        isEdit = true;
        currentJsonData = currentJson.data;
    }
    var isDel = false;
    var setting = {
        check: {
            enable: true
        },
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        }
    };
    init();
}



