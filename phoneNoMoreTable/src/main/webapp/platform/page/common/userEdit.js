//# sourceURL=userEdit.js
function init_common_userEdit() {
    var $loginName = $("#loginName")
    var currentJson = GlobalData.currentJson;
    var isEdit = false;
    if(currentJson.isEdit){
        isEdit = true;
    }
    var isDel = false;
    var $roles = $("#roles")
    var $department = $("#department")
    var $position = $("#position")

    init = function () {
        GlobalData.initPageJs();
        switch (GlobalData.permissionsTag){
            case "Admin":
                $("#managerBtn").hide()
                break;
        }

        if(currentJson != null){
            var $delBtn = $("#delBtn")
            if(isEdit){
                $delBtn.show();
                $loginName.attr("disabled", true);
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
                $loginName.removeAttr("disabled");
            }
        }
        loadSelectPickerData()
        if(isEdit){
            loadRolesData()
        }else{
            SelectPickerFunc.loadUrlData('roles','/service/platform/login/user/roles/getSelect', true)
        }
    }

    loadSelectPickerData = function () {
        getDataByURL("/service/platform/login/user/user/loadSelectPickerData", null, function (data) {
            var dataJson = data.data
            var departmentId = null;
            if(isEdit){
                departmentId = currentJson.data.departmentId
            }
            if(StringUtil.isNotEmpty(departmentId)){
                SelectPickerFunc.load('department', dataJson.department, false, departmentId)
            }else{
                SelectPickerFunc.load('department',dataJson.department, true)
            }
            var positionId = null;
            if(isEdit){
                positionId = currentJson.data.positionId
            }
            if(StringUtil.isNotEmpty(positionId)){
                SelectPickerFunc.load('position', dataJson.position, false, positionId)
            }else{
                SelectPickerFunc.load('position', dataJson.position, true)
            }
        })
    }

    /**
     * 加载角色信息
     */
    loadRolesData = function () {
        // 获取用户其他关联信息
        getDataByURL("/service/platform/login/user/user/getUserInfo",{id:$("#id").val()}, function (data) {
            var dataJson = data.data
            var userInfo = dataJson.userInfo

            /*
            用户角色
             */
            var roleIds = userInfo.roleIds
            SelectPickerFunc.loadUrlData('roles','/service/platform/login/user/roles/getSelect', false, roleIds)
        })
    }

    save = function() {
        ParsleyFunc.checkForm($("#form"), function () {
            var url = "/service/platform/login/user/user/create"
            if(isEdit){
                url = "/service/platform/login/user/user/update";
            }
            var params = $("#form").serializeHumpObject()
            var password = $("#password").val();
            if(StringUtil.isNotEmpty(password)){
                params.password = $.md5(password);
            }else{
                if( ! isEdit){
                    boldFunc.showErrorMes("请输入密码")
                    return
                }
            }
            var rolesId = $roles.selectpicker('val');
            if(StringUtil.isEmpty(rolesId)){
                boldFunc.showErrorMes("请分配角色");
                return
            }
            params.roleIds = rolesId
            if(GlobalData.permissionsTag != "Admin"){
                var positionId = $position.selectpicker('val');
                if(StringUtil.isEmpty(positionId)){
                    boldFunc.showErrorMes("请选择职位");
                    return
                }
                params.positionId = positionId
                var departmentId = $department.selectpicker('val');
                if(StringUtil.isEmpty(departmentId)){
                    boldFunc.showErrorMes("请选择部门");
                    return
                }
                params.departmentId = departmentId
            }
            getDataByURL(url, params, function (data) {
                boldFunc.notification("操作成功")
                goBack();
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

    init();
}

function quit_user_userEdit() {
}




