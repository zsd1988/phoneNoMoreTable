//# sourceURL=folder.js
$(function(){
    /**
     *  取消
     */
    $("#ext-gen532").click(function () {
        $("#ext-comp-1201").css("display","none");
        $("#ext-gen485").css("display","none");
    })
    /**
     *  取消
     */
    $("#ext-gen524").click(function () {
        $("#ext-comp-1201").css("display","none");
        $("#ext-gen485").css("display","none");
    })

    /**
     *  取消
     */
    $("#x-tool-close").click(function () {
        $("#ext-comp-1201").css("display","none");
        $("#ext-gen485").css("display","none");
    })


    /**
     *  创建、修改确定
     */
    $("#save_folder").click(function () {
        var permissionName=$("#permissionName").val();
        var formData = new FormData(document.getElementById("folderCFM"));
        if(StringUtil.isEmpty(permissionName)){
            $("#permissionName").addClass("x-form-invalid");
            $("#showMessage").html("文件名不能为空！");
            $("#showMessageIcon").css("visibility","visible");
            return;
        }
        if( GlobalData.thisFolderType==1){
            $.ajax({
                url: "../service/platform/login/user/permission/create",
                type: "POST",
                dataType: "json",
                data: formData,
                async: false,
                cache: false,
                processData: false,
                contentType: false,
                success: function (response) {
                    var data=response;
                    if(data.code==0){
                        if(data.data.fidExistCount==1){
                            var addFolderHtml="<ul style=\"display: block;\">" +
                                "                <li class=\"has_sub \">" +
                                "                        <a href=\"javascript:void(0)\" class=\"waves-effect  folders_class\" candel=\""+data.data.canDelete+"\" permissionid=\""+data.data.id+"\" permissionname=\""+permissionName+"\" onclick=\"gotoPage('"+data.data.permissionUrl+"','','"+data.data.folderType+"')\"><i class=\"folder\"></i><span>"+permissionName+"</span> </a>" +
                                "                </li>" +
                                "    </ul>";
                            $(GlobalData.thisFolder).parent().append(addFolderHtml);
                        }else if(data.data.fidExistCount>1){
                            var addFolderHtml= " <li class=\"has_sub \">" +
                                "                        <a href=\"javascript:void(0)\" class=\"waves-effect  folders_class\" candel=\""+data.data.canDelete+"\" permissionid=\""+data.data.id+"\" permissionname=\""+permissionName+"\" onclick=\"gotoPage('"+data.data.permissionUrl+"','','"+data.data.folderType+"')\"><i class=\"folder\"></i><span>"+permissionName+"</span> </a>" +
                                "                </li>" ;
                            $(GlobalData.thisFolder).next().append(addFolderHtml);
                        }
                        $("#permissionName").val("");
                        $("#ext-comp-1201").css("display","none");
                        $("#ext-gen485").css("display","none");
                        FolderFuc.change();
                    }else{
                        $("#permissionName").addClass("x-form-invalid");
                        $("#showMessage").html(data.message);
                        $("#showMessageIcon").css("visibility","visible");
                    }
                    return;
                }
            });
        }else{
            $.ajax({
                url: "../service/platform/login/user/permission/update",
                type: "POST",
                dataType: "json",
                data: formData,
                async: false,
                cache: false,
                processData: false,
                contentType: false,
                success: function (response) {
                    var data=response;
                    if(data.code==0){
                        $(GlobalData.thisFolder).attr("permissionname",data.data.permissionName);
                        $(GlobalData.thisFolder).children("span").html(data.data.permissionName);
                        $("#permissionName").val("");
                        $("#ext-comp-1201").css("display","none");
                        $("#ext-gen485").css("display","none");
                    }else{
                        $("#permissionName").addClass("x-form-invalid");
                        $("#showMessage").html(data.message);
                        $("#showMessageIcon").css("visibility","visible");
                    }
                    return;
                }
            });
        }
    })

    /**
     *  删除确定
     */
    $("#folder_del").click(function () {
        var permissionsId= $("#folder_del_id").val();
        $.ajax({
            url: "../service/platform/login/user/permission/delete",
            dataType: "json",
            data: {
                "permissionsId":    permissionsId
            },
            success: function (response) {
                var data=response;
                if(data.code==0){
                    if(data.data.fidExistCount==0){
                        $(GlobalData.thisFolder).parent().parent().remove();
                    }else{
                        $(GlobalData.thisFolder).parent().remove();
                    }
                    $("#ext-comp-1203").css("display","none");
                    $("#ext-gen485").css("display","none");
                }
                return;
            }
        });
    })

    /**
     *  取消
     */
    $("#ext-gen522").click(function () {
        $("#ext-comp-1203").css("display","none");
        $("#ext-gen485").css("display","none");
    })

    /**
     *  取消
     */
    $("#ext-gen510").click(function () {
        $("#ext-comp-1203").css("display","none");
        $("#ext-gen485").css("display","none");
    })


    $("#showMessageIcon").mouseover(function (){
        $("#messageShow").css("display","block");
    }).mouseout(function (){
        $("#messageShow").css("display","none");
    });

});




var folder={
    create:function(permissionsId){
        GlobalData.thisFolderType=1;
        $("#permissionsId").val(permissionsId);
        $("#ext-comp-1201").css("display","block");
        $("#ext-gen485").css("display","block");
    },

    del:function (permissionId) {
        $("#folder_del_id").val(permissionId);
        $("#ext-comp-1203").css("display","block");
        $("#ext-gen485").css("display","block");
    },

    update:function (permissionsId,permissionName) {
        GlobalData.thisFolderType=2;
        $("#permissionsId").val(permissionsId);
        $("#permissionName").val(permissionName);
        $("#ext-comp-1201").css("display","block");
        $("#ext-gen485").css("display","block");
    },

    changeValue:function(obj){
        var thisVal=$(obj).val();
        if(StringUtil.isEmpty(thisVal)){
            $("#permissionName").addClass("x-form-invalid");
            $("#showMessage").html("文件名不能为空！");
            $("#showMessageIcon").css("visibility","visible");
            return;
        }else{
            $("#permissionName").removeClass("x-form-invalid");
            $("#showMessageIcon").css("visibility","hidden");
        }
    }
};




