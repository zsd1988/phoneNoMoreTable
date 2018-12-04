//# sourceURL=conversionRate.js
function init_statistics_conversionRate() {
        var _editData = null;
        var _todayStr = DateUtil.getNowFormatDate();
        var _monthDay = DateUtil.getMonthFirstDayFormatDate();
        var _userList = null;
        var _roleUserIdMap= {};
        var _table=document.getElementById("table");

        init = function () {
            GlobalData.initPageJs();

            $("#startTime").val(_monthDay)
            $("#endTime").val(_todayStr)
            //获取下拉框数据
            var params = {params:"CSGroup"}
            getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
                var dataJson = data.data
                SelectPickerFunc.loadMap('groupId',dataJson.CSGroup, true)
                _userList = dataJson.CSGroupUser;
            })
            DateTimePickerFunc.init({
                id:"startTime",
                selectDay:true
            })

            DateTimePickerFunc.init({
                id:"endTime",
                selectDay:true
            })


            // 获取客服用户后再查询通话记录，不然选择的客服角色没生效
            getDataByURL("/service/platform/login/user/user/getCSAndInterview", null, function (data) {
                var dataJson = data.data
                _roleUserIdMap.CS = dataJson.CS;
                TableFunc.showTable({
                    url: "/service/platform/login/clientManager/client/getConversionRate",
                    queryParams: queryParams,
                    pagination: false,
                    columns: [
                        {
                            title: '序号',
                            align : 'center',
                            formatter: function (value, row, index) {
                                return index+1;
                            }
                        },
                        {
                            field: 'name',
                            title: '姓名',
                            align: 'center',
                        },
                        {
                            field: 'reviewCount',
                            title: '质检数量',
                            align: 'center',
                        },
                        {
                            field: 'count',
                            title: '自定数量',
                            align: 'center',
                        },
                        {
                            field: 'rate',
                            title: '转化率',
                            align: 'center',
                        }
                    ]
                })
            })
        }


        query = function () {
            var startTimeStr = $("#startTime").val()
            var endTimeStr = $("#endTime").val()
            if(startTimeStr != "" && endTimeStr != "" ){
                if(DateUtil.strToDate(startTimeStr) > DateUtil.strToDate(endTimeStr)){
                    boldFunc.showErrorMes("开始时间大于结束时间")
                    return
                }
            }
            $("#table").bootstrapTable('refresh');
        }

        queryParams = function (params) {
            var param = {
                paginationParams:{
                    pageIndex : this.pageNumber,
                    pageSize : this.pageSize,
                },
                startTimeStr:$("#startTime").val(),
                endTimeStr:$("#endTime").val(),
                workNumber:$("#workNumber").val(),
            }
            var groupId = $("#groupId").selectpicker('val')
            if(StringUtil.isNotEmpty(groupId)){
                var userIds = _userList[groupId]
                if(userIds.length > 0){
                    param.userIds = ArrayFunc.toCommaStr(userIds);
                }
            }
            if(StringUtil.isEmpty(groupId) && StringUtil.isEmpty(param.workNumber)){
                param.userIds = _roleUserIdMap.CS
            }
            return param;
        }

        reset = function () {
            $("#startTime").val(_monthDay)
            $("#endTime").val(_todayStr)
            $("#workNumber").val('')
            $("#groupId").selectpicker('val','')
            $("#table").bootstrapTable('refresh')
        }

        init();
    }










