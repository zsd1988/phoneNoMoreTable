//@ sourceURL=floorOverview.js
function init_common_floorOverview(){
    var projectId = StringUtil.getRequestParasFromUrl("projectId", GlobalData.beforePageUrl)
    var _floorDataJson, _showIndex = 0;
    var _clientIndex = 0;
    var _selectUserId;
    var _$selectUserModal = $("#selectUser")
    var _$selectUserTable = $("#selectUserTable")
    var _$table = $("#table")
    var _isSign = false;
    var _isShowChangsha = false;
    var _selectDoorId = null;
    var _isManager = false;

    init = function () {
        if(GlobalData.permissionsTag == "Manager"){
            _clientIndex = 1;
            _isManager = true;
            $('#allClient').tab('show')
            $("#myClient").hide();
        }else{
            $("#myClient").show();
            $('#myClient').tab('show')
        }

        /*
        查看员工
         */
        _$selectUserModal.on('show.bs.modal', function () {
            _$selectUserTable.bootstrapTable("refresh");
        })
        TableFunc.showTable({
            url:"/service/platform/login/user/user/getEmployee",
            elm:"selectUserTable",
            queryParams:queryUserTableParams,
            onClickRow: function (row, $element, field) {
                _$selectUserModal.modal("hide");
                $("#userName").text(row.nickname)
                _clientIndex = 2;
                _selectUserId = row.id;
                loadDoorMap(_showIndex)
            },
            columns: [
                {
                    radio:true,
                    align : 'center',
                },
                {
                    field: 'loginName',
                    title: '手机',
                    align: 'center',
                },
                {
                    field : 'nickname',
                    title : '姓名',
                    align : 'center',
                }]
        });


        /*
        加载地图
         */
        var mapSrc = ''
        getDataByURL("/service/platform/login/systemSetting/floor/listByProjectId", {projectId:projectId}, function (data) {
            _floorDataJson = data.data;
            $("#floorTab").empty();
            if(_floorDataJson.length > 0){
                tableInit();
                var selectNum = _floorDataJson[0].num
                var styleStr = ' style="width:' + 1/(_floorDataJson.length+1)*100 + '%" '
                for(var i = 0; i < _floorDataJson.length; i++){
                    var item = _floorDataJson[i]
                    var tab = "<li class=\"tab\" "
                    if(selectNum == item.num){
                        tab = "<li class=\"tab active \" "
                        mapSrc = GlobalData.fileUrl + item.imagePath
                        _showIndex = i;
                    }
                    tab +=  styleStr + ">\n"
                    tab += "<a href=\"#tab" + (i + 1) + "\" id='tab" + (i+1) + "' data-toggle=\"tab\" aria-expanded=\"false\">\n" +
                        "<span class=\"visible-xs\"><i class=\"fa fa-home\"></i></span>\n" +
                        "<span class=\"hidden-xs\">" + item.name + "</span>\n" +
                        "</a>\n" +
                        "</li>"

                    $("#floorTab").append(tab)
                }
                var home = "<li class=\"tab\" " + styleStr + ">\n"
                home += "<a href=\"#tab" + 10 + "\" data-toggle=\"tab\" aria-expanded=\"false\">\n" +
                    "<span class=\"visible-xs\"><i class=\"fa fa-home\"></i></span>\n" +
                    "<span class=\"hidden-xs\">" + '地图' + "</span>\n" +
                    "</a>\n" +
                    "</li>"
                $("#floorTab").append(home)

                $('#floorTab a').click(function (e) {
                    _selectDoorId = null;
                    var href = $(this).attr("href")
                    var index = StringUtil.toNum(href)
                    if(index == 10){
                        _showIndex = 10
                        MapFunc.setImageSrc(GlobalData.basePath + "/platform/img/changsha.png");
                    }else{
                        _showIndex = index - 1;
                        MapFunc.setImageSrc(GlobalData.fileUrl + _floorDataJson[index - 1].imagePath);
                    }
                })

                MapFunc.init({
                    mapSrc:mapSrc,
                    onLoad:function () {
                        if(_showIndex != 10){
                            loadDoorMap(_showIndex)
                        }else{
                            loadScreenMap(_showIndex)
                        }
                    },
                    click:function (params) {
                        var nodes = params.nodes
                        if(nodes.length > 0){
                            _selectDoorId = nodes[0]
                            var showNodes = MapFunc.getNodeById(_selectDoorId)
                            if(! _isShowChangsha){
                                if(showNodes.data.type == "Pass"){
                                    _isSign = true;
                                    $('#selectClient1').tab('show')
                                }else{
                                    _isSign = false;
                                    $('#selectClient0').tab('show')
                                }
                            }
                            _$table.bootstrapTable("refresh")
                        }
                    },
                })
            }else{
                boldFunc.notification("尚未添加楼层")
            }
        })
    }


    queryUserTableParams = function () {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            name:$("#selectUserName").val(),
            phone:$("#selectUserPhone").val()
        }
        return param;
    }

    queryParams = function () {
        var userId = _selectUserId
        if(_clientIndex == 0){
            userId = GlobalData.userId
        }
        var params = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            clientId:userId,
            isGetEmployee: !_isManager,
            id:_selectDoorId,
        };
        if(_isShowChangsha){
            params.clientType = "Reserve"
        }else{
            params.isInclude = _isSign;
            params.floorId = _floorDataJson[_showIndex].id
        }
        return params;
    }

    /**
     * 获取长沙地图标注
     * @param index
     */
    loadScreenMap = function (index) {
        _isShowChangsha = true;
        var param = {
            clientIndex:_clientIndex,
            isShowChangsha:_isShowChangsha,
            userId:_selectUserId
        }
        _$table.bootstrapTable('refresh')
        getDataByURL("/service/platform/login/customer/projectClient/getClientForMap", param, function (data) {
                MapFunc.clearNodes();
                var clientList = data.data
                if(clientList.length > 0){
                    for(var j = 0; j < clientList.length; j++){
                        var projectClient = clientList[j];
                        var posX = MapFunc.getRealPos(projectClient.posX, projectClient.screenWidth)
                        var posY = MapFunc.getRealPos(projectClient.posY, projectClient.screenWidth)
                        var map = ProjectFunc.getChangShaMap(projectClient, posX,posY)
                        MapFunc.addNode(map);
                    }
                }
            }
        );
    }

    loadDoorMap = function (index) {
        _isShowChangsha = false;
        var userId = _selectUserId
        if(_clientIndex == 0){
            userId = GlobalData.userId
        }
        var param = {
            clientIndex:_clientIndex,
            isShowChangsha:_isShowChangsha,
            userId:userId,
            projectId:projectId,
            floorId:_floorDataJson[index].id
        }
        _$table.bootstrapTable('refresh')
        getDataByURL("/service/platform/login/customer/clientDoor/listByFloorId", param, function (data) {
                MapFunc.clearNodes();
                var doorList = data.data
                if(doorList.length > 0){
                    for(var j = 0; j < doorList.length; j++){
                        var door = doorList[j];
                        door.posX = MapFunc.getRealPos(door.posX, door.screenWidth)
                        door.posY = MapFunc.getRealPos(door.posY, door.screenWidth)
                        var map = ProjectFunc.getMap(door)
                        MapFunc.addNode(map);
                    }
                    if(_selectDoorId != null){
                        MapFunc.mapZoomToNode(MapFunc.getNodeById(_selectDoorId))
                        MapFunc.selectNode(_selectDoorId)
                    }
                }
            }
        );
    }

    query = function () {
        var doorNum = $("#doorNum").val()
        if(StringUtil.isNotEmpty(doorNum)){
            getDataByURL("/service/platform/login/systemSetting/door/getDoorByNum", {
                num:doorNum,
                projectId:projectId
                }, function (data) {
                    var door = data.data;
                    for(var i = 0; i < _floorDataJson.length; i++){
                        if(_floorDataJson[i].id == door.floorId){
                            _selectDoorId = door.id;
                            _showIndex = i;
                            var tabIndex = i + 1;
                            $('#tab' + tabIndex).tab('show')
                            MapFunc.setImageSrc(GlobalData.fileUrl + _floorDataJson[i].imagePath);
                            $("#table").bootstrapTable('refresh')
                            break
                        }
                    }
                }
            );
        }
    }

    tableInit = function () {
        TableFunc.showTable({
            url: "/service/platform/login/customer/projectClient/list",
            queryParams: queryParams,
            pagination: true,
            columns: [
                {
                    field: 'formatStr',
                    title: '业态',
                    align: 'center',
                },
                {
                    field: 'brand',
                    title: '品牌',
                    align: 'center',
                },
                {
                    field: 'name',
                    title: '联系人',
                    align: 'center',
                },
                {
                    field: 'phone',
                    title: '电话',
                    align: 'center',
                },
                {
                    field: 'doorNums',
                    title: '铺位号',
                    align: 'center',
                },
                {
                    field: 'areaInfo',
                    title: '面积',
                    align: 'center',
                },]
        });
    }

    /**
     *  选择客户类型
     */
    selectClientType = function (index) {
        if(index == 1){
            _isSign = true;
        }else{
            _isSign = false;
        }
        _$table.bootstrapTable("refresh")
    }

    /**
     * 重置查询
     */
    resetSelectUser = function () {
        $("#selectUserName").val('')
        $("#selectUserPhone").val('')
        _$selectUserTable.bootstrapTable("refresh");
    }


    /**
     * 查询
     */
    queryUser = function () {
        _$selectUserTable.bootstrapTable("refresh");
    }

    selectClient = function (index) {
        _selectDoorId = null;
        if(index != 2){
            _selectUserId = null;
            _clientIndex = index;
            loadDoorMap(_showIndex)
        }else{
            _$selectUserModal.modal("show")
        }
    }

    init();
}









