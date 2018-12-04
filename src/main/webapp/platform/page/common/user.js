//# sourceURL=user.js
function init_common_user() {

    var _this;
    var _roleId;
    var addFid;
    var _userId;
    var id;
    var _createModal = $("#con-user-modal")
    var _headImageCreate = UploadFunc.uploadInit({
        id:"headImageCreate",
        width:60,
        height:60,
        callback:function (data) {
            $("#headImageCreateSourcePath").val(data.sourceImg);
        }
    });


    zTreeOnClick=function(event, treeId, treeNode) {
      /*  $("#addGroupId").val(treeNode.id);
        $("#addGroupIdStr").val(treeNode.name);
*/
        if(StringUtil.isNotEmpty(treeNode.type)&&treeNode.type=='user'){
            var url="/service/platform/login/user/user/getUserInfo";
            //获取用户的菜单权限
            getDataByURL(url, {id:treeNode.id.substring(5)}, function (data) {
                var userInfo= data.data.userInfo;
                $("#userId").val(userInfo.id);
                $("#workNumber").val(userInfo.workNumber);
                $("#userName").val(userInfo.name);
                if(userInfo.isTeamLeader){
                    $("#isTeamLeader").selectpicker('val', 'true')
                }else{
                    $("#isTeamLeader").selectpicker('val', 'false')
                }
                if(userInfo.isDel){
                    $("#isUserDel").selectpicker('val', 'true')
                }else{
                    $("#isUserDel").selectpicker('val', 'false')
                }
                $("#updateUserGroupList").selectpicker('val',userInfo.groupId);
                $("#updateRoleListOption").selectpicker('val',userInfo.roleId);
                $("#workType").selectpicker("val", userInfo.workType)
                $("#extNum").val(userInfo.extNum);
                $("#password").val("000000");
                $("#headImageSourcePath").val(null)
                $("#headImage").empty();
                var headImage = UploadFunc.uploadInit({
                    id:"headImage",
                    width:60,
                    height:60,
                    callback:function (data) {
                        $("#headImageSourcePath").val(data.sourceImg)
                    }
                });
                var imagePath = userInfo.headImage
                if(StringUtil.isNotEmpty(imagePath)){
                    $("#headImageSourcePath").val(imagePath)
                    var dataImage = {sourceImg:imagePath}
                    headImage.load(dataImage, 'image')
                }
            })
            _userId=treeNode.id.substring(5);
            $("#con-user-update-modal").css("display","block");
            getGroupListData(treeNode.id.substring(5));
        }else{
            $("#userId").val("");
            $("#workNumber").val("");
            $("#userName").val("");
            $("#updateUserGroupList").selectpicker('val',"");
            $("#updateRoleListOption").selectpicker('val',"");
            $("#isTeamLeader").selectpicker('val', 'false')
            $("#isUserDel").selectpicker('val', 'false')
            $("#workType").selectpicker("val", "")
            $("#extNum").val("");
            $("#password").val("");
            $("#con-user-update-modal").css("display","none");
            $.fn.zTree.init($("#clientGroupMapList"), clientGroupSetting,[]);
        }
    };

    zTreeOnDbClick=function(event, treeId, treeNode) {
        if(StringUtil.isEmpty(treeNode.type)&&treeNode.pId!=5){
            $("#groupId").val(treeNode.id);
            $("#groupName").val(treeNode.name);
            $("#nickName").val(treeNode.nickName);
            $("#gongfu").val(treeNode.gongfu);
            $("#count").val(treeNode.count);
            $("#userGroupOption").selectpicker('val',treeNode.pId);
            $("#addUserGroupButton").click();
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

    var clientGroupSetting = {
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


    clearModalData = function(){
        $("#groupId").val("0");
        FormFunc.clearForm("#con-groups-modal");
    }

    clearUserData=function () {
        FormFunc.clearForm("#con-user-modal");
    }

    init = function () {
        $("#isTeamLeader").selectpicker('val', 'false')
        $("#isTeamLeaderCreate").selectpicker('val', 'false')
        $("#isUserDel").selectpicker('val', 'false')
        _createModal.css("display", "block");
        _createModal.addClass("webuploader-element-invisible");
        _createModal.on('show.bs.modal', function () {
            _createModal.removeClass("webuploader-element-invisible")
        })
        $("#workType").selectpicker()

        var url="/service/platform/login/user/userGroup/getUserGroupList";
        //获取用户的菜单权限
        var param=getParam();
        getDataByURL(url, param, function (data) {
            var mapUserGroupList= data.data.mapUserGroupList;
            $.fn.zTree.init($("#mapUserGroupList"), setting,mapUserGroupList);
            if(id!=0){
                var mapUserGroupTree=$.fn.zTree.getZTreeObj("mapUserGroupList");
                var node = mapUserGroupTree.getNodeByParam("id", id, null);
                mapUserGroupTree.selectNode(node);
            }
        })

        if(GlobalData.isSupportIP){
            $("#extNum").attr("readOnly", true)
        }
    }


    getUserGroupOptions = function () {
        var url = "/service/platform/login/user/userGroup/userGroupOption";
        getFormDataByURL(url, null, function (data) {
            SelectPickerFunc.loadLocalData('userGroupOption', data.data.userGroupOption, false);
        })
    };


    roleListOption = function () {
        var url = "/service/platform/login/user/user/roleListOption";
        getFormDataByURL(url, null, function (data) {
            SelectPickerFunc.loadLocalData('addRoleListOption', data.data.roleListOption, false);
            SelectPickerFunc.loadLocalData('updateRoleListOption', data.data.roleListOption, false);
        })
    };

    userGroupList = function () {
        var url = "/service/platform/login/user/user/userGroupList";
        getFormDataByURL(url, null, function (data) {
            SelectPickerFunc.load('addUserGroupList', data.data, false);
            SelectPickerFunc.load('updateUserGroupList', data.data, false);
        })
    };


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



    saveUserGroup=function () {
        ParsleyFunc.checkForm($("#con-groups-modal"), function () {
            var params = new FormData(document.getElementById("con-groups-modal"));
            var url = "/service/platform/login/user/userGroup/create";
            getFormDataByURL(url, params, function (data) {
                addFid=data.data.fid;
                init();
                userGroupList();
                $("#closeUserGroup").click();
            })
        })
    }



    saveUser=function () {
        ParsleyFunc.checkForm($("#con-user-modal"), function () {
            var params = $("#con-user-modal").serializeHumpObject();
            var url = "/service/platform/login/user/user/create";
            getDataByURL(url, params, function (data) {
                id=data.data.id;
                addFid=data.data.groupId;
                init();
                $("#closeUser").click();
            })
        })
    }

    updateUser=function () {
        ParsleyFunc.checkForm($("#con-user-update-modal"), function () {
            var params = $("#con-user-update-modal").serializeHumpObject();
            var url = "/service/platform/login/user/user/create";
            getDataByURL(url, params, function (data) {
                id=data.data.id;
                boldFunc.notification("操作成功");
                addFid=data.data.groupId;
                init();
            })
        })
    }


    getGroupListData=function(userId){
        var url="/service/platform/noLogin/clientManager/clientGroupController/getClientGroupList";
        //获取用户的菜单权限
        var params={
            userId: userId,
        }
        getDataByURL(url, params, function (data) {
            var clientGroupMapList= data.data.clientGroupMapList;
            $.fn.zTree.init($("#clientGroupMapList"), clientGroupSetting,clientGroupMapList);
        })
    }


    saveUserClientGroup=function () {
        var selectClientGroups=$.fn.zTree.getZTreeObj("clientGroupMapList");
        var userClientGroups=checkUserClientGroup(selectClientGroups);
        var url="/service/platform/noLogin/user/userClientGroup/saveUserClientGroup";
        //获取用户的菜单权限
        var params={
            userClientGroups: userClientGroups,
            userId:_userId,
        }
        getDataByURL(url, params, function (data) {
            boldFunc.notification("操作成功")
        })
    }

    checkUserClientGroup=function(treeObj){
        var nodes=treeObj.getCheckedNodes(true);
        var v="";
        for(var i=0;i<nodes.length;i++){
            if(nodes[i].pId!=0&&nodes[i].id!=0){
                v+=nodes[i].pId + ","+nodes[i].id+";";
            }
        }
        if(v!=""){
            v = v.substring(0,v.length-1);
        }
        return v;
    }



    getParam=function () {
        var param = {
            fid:addFid
        }
        return param;
    }

    
    
    getUserGroupOptions();
    roleListOption();
    userGroupList();
    init();
}







