//@ sourceURL=signLabelDetail.js
function init_common_customer_signLabelDetail(){
    var _data = GlobalData.currentJson.data
    init=function () {
        $("#brand").text(_data.brand)
        $("#area").text(_data.area + "㎡")
        $("#floorName").text(_data.floorName);
        var floorId = _data.floorId


        TableFunc.showTable({
            url: "/service/platform/login/customer/clientDoor/list",
            queryParams: queryParams,
            pagination: false,
            columns: [
                {
                    title: '序号',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(row.id != "100"){
                            return index + 1;
                        }else{
                            return "总面积"
                        }
                    }
                },
                {
                    field: 'doorNum',
                    title: '铺位号',
                    align: 'center',
                },
                {
                    field: 'rentsArea',
                    title: '计租面积',
                    align: 'center',
                },
                {
                    field: '',
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(row.id != "100"){
                            var  a = '<button type="button" class="btn btn-primary waves-effect waves-light"  onclick="set(' + index +')">定位</button>';
                            return a;
                        }
                    }
                }]
        });

        if(StringUtil.isNotEmpty(floorId)){
            getDataByURL("/service/platform/login/systemSetting/floor/getById", {id:floorId}, function (result) {
                var dataJson = result.data
                var mapSrc = GlobalData.fileUrl + dataJson.imagePath
                MapFunc.init({
                    mapSrc:mapSrc,
                    onLoad:function () {
                        var param = {
                            floorId:floorId,
                            projectId:_data.projectId
                        }
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
                                }
                            }
                        );
                    }
                })
            })
        }
    }

    /**
     * 定位到店铺
     * @param index
     */
    set = function (index) {
        var data = $("#table").bootstrapTable('getData')[index];
        var node = MapFunc.getNodeById(data.doorId)
        if(node != null){
            MapFunc.mapZoomToXY(node.x, node.y)
            MapFunc.selectNode(node.id)
        }
    }

    queryParams = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            id:_data.id
        }
        return param;
    }

    init();
}








