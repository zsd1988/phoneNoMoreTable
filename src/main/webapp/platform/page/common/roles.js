//# sourceURL=roles.js
function init_common_roles() {

    var _this;
    var _roleId;

    var setting = {
        check: {
            enable: true
        },
        view: {
            showIcon: false,
        },
        async: {
            enable: true,
            autoParam: ["id"]
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        }
    };

    init = function () {
        var listElm = $("#rolesList");
        listElm.empty();
        listElm.setTemplateURL("/platform/tpl/common/customer/roles.html");
        var url = "/service/platform/login/user/roles/getRolesMap";
        getDataByURL(url,null,function(object) {
            var data = object.data;
            var json = {
                data: data,
            };
            listElm.processTemplate(json);
        });
        getPermissionData("root");
    }


    editRoleType=function (obj,roleId,roleType) {
        _roleId=roleId;
        _this=$(obj);
        $("#id").val(roleId);
        $("#roleType").val(roleType);
        $("#addRoleButton").click();

    }

    clearModalData = function(){
        FormFunc.clearForm("#con-role-modal");
    }


    showPermission=function(obj,roleId){
        _roleId=roleId;
        $("#rolesList li").css("color","#0b0b0b");
        $(obj).css("color","#5fbeaa");
        getPermissionData(roleId);
    }


    
    
    getPermissionData=function(roleId){
        var url="/service/platform/login/user/roles/getRolesPermission";
        //获取用户的菜单权限
        var params={
            roleId: roleId,
        }
        getDataByURL(url, params, function (data) {
            var mapPermissionsData= data.data.mapPermissionsList;
            var mapFunctionsData= data.data.mapFunctionList;
            $.fn.zTree.init($("#mapPermission"), setting,mapPermissionsData);
            $.fn.zTree.init($("#mapFunctions"), setting,mapFunctionsData);
        })
    }




    saveDepartment=function () {
        ParsleyFunc.checkForm($("#con-role-modal"), function () {
            var params = new FormData(document.getElementById("con-role-modal"));
            var url = "/service/platform/login/user/roles/create";
            getFormDataByURL(url, params, function (data) {
                var roleData=data.data;
                if(roleData.isExsit==0){
                    $("#rolesList").append("  <li onclick=\"showPermission(this,\""+roleData.id+"\")\"  ondblclick=\"editRoleType(this,\""+roleData.id+"\",\""+roleData.roleType+"\")\">"+roleData.roleType+"</li>");
                }else{
                    _this.attr("ondblclick","editRoleType(this,\""+roleData.id+"\",\""+roleData.roleType+"\")");
                    _this.text(roleData.roleType);
                }
                $("#roleType").val("");
                $("#closeButton").click();
            })
        })

    }

    savePermission=function () {
        var selectPermissions=$.fn.zTree.getZTreeObj("mapPermission");
        var permissionsList=checkPermissions(selectPermissions);
        var url="/service/platform/login/user/roles/createPermissions";
        //获取用户的菜单权限
        var params={
            permissionsList: permissionsList,
            roleId:_roleId,
        }
        getDataByURL(url, params, function (data) {
            boldFunc.notification("操作成功")
        })
    }


    saveFunctions=function () {
        var selectFunctions=$.fn.zTree.getZTreeObj("mapFunctions");
        var functionList=checkPermissions(selectFunctions);
        var url="/service/platform/login/user/roles/createFunctions";
        //获取用户的菜单权限
        var params={
            functionList: functionList,
            roleId:_roleId,
        }
        getDataByURL(url, params, function (data) {
            boldFunc.notification("操作成功")
        })
    }

    checkPermissions=function(treeObj){
        var nodes=treeObj.getCheckedNodes(true);
        var v="";
        for(var i=0;i<nodes.length;i++){
            v+=nodes[i].id + ",";
        }
        if(v!=""){
            v = v.substring(0,v.length-1);
        }
        return v;
    }



    init();
}





