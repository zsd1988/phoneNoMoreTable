//# sourceURL=portCompletionRate.js
function init_statistics_portCompletionRate() {
    var _todayStr = DateUtil.getNowFormatDate();

    init = function () {
        GlobalData.initPageJs();
        $("#startTime").val(_todayStr + " 00:00:00")
        $("#endTime").val(_todayStr + " 23:59:59")

        DateTimePickerFunc.init({
            id:"startTime",
            selectMinutes:true
        })
        DateTimePickerFunc.init({
            id:"endTime",
            selectMinutes:true
        })


        var params = {params:"PortDev"}
        getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
            var dataJson = data.data
            SelectPickerFunc.loadMap('devName',dataJson.PortDev, true)
        })

        TableFunc.showTable({
            url: "/service/platform/login/systemManager/GroupCallDetail/portDetailRate",
            queryParams: queryParams,
            pagination: false,
            columns: [
                {
                    field: 'portId',
                    title: '端口号',
                    align: 'center',
                },
                {
                    field: 'sumTotal',
                    title: '总数量',
                    align: 'center',
                },
                {
                    field: 'sumSuccess',
                    title: '成功数',
                    align: 'center',
                },
                {
                    field: 'sumFail',
                    title: '失败数',
                    align: 'center',
                },
                {
                    field: 'portRate',
                    title: '成功率',
                    align: 'center',
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
            startTimeStr:$("#startTime").val(),
            endTimeStr:$("#endTime").val(),
            name:$("#devName").selectpicker('val'),
        }
        return param;
    }

    query = function(){
        $("#table").bootstrapTable('refresh');
    };

    init();

}