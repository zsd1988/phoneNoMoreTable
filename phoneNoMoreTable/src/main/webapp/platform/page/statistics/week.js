//# sourceURL=week.js
function init_statistics_week() {
        var _editData = null;
        var _todayStr = DateUtil.getDayBeforeOrAfter(-7);
        var _userList = null;
        var _roleUserIdMap= {};
        var _table=document.getElementById("table");

        init = function () {
            GlobalData.initPageJs();

            $("#startTime").val(_todayStr)
            $('#roleType').selectpicker({ });
            $('#roleType').selectpicker("val", "cs")
            //获取下拉框数据
            var params = {params:"UserGroup"}
            getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
                var dataJson = data.data
                SelectPickerFunc.loadMap('groupId',dataJson.UserGroup, true)
                _userList = dataJson.UserGroupUser;
            })
            DateTimePickerFunc.init({
                id:"startTime",
                showWeeks:true
            })


            // 获取客服用户后再查询通话记录，不然选择的客服角色没生效
            getDataByURL("/service/platform/login/user/user/getCSAndInterview", null, function (data) {
                var dataJson = data.data
                _roleUserIdMap.CS = dataJson.CS;
                _roleUserIdMap.interview = dataJson.interview
                TableFunc.showTable({
                    url: "/service/platform/login/user/jiangHuRank/list",
                    queryParams: queryParams,
                    pagination: false,
                    pageSize:500,
                    columns: [
                        {
                            title: '序号',
                            align : 'center',
                            formatter: function (value, row, index) {
                                return index+1;
                            }
                        },
                        {
                            field: 'userName',
                            title: '姓名',
                            align: 'center',
                        },
                        {
                            field: 'gold',
                            title: '贡献值',
                            align: 'center',
                        },
                        {
                            field: 'intention',
                            title: '意向数/来访数',
                            align: 'center',
                        },
                        {
                            field: 'finish',
                            title: '认筹数',
                            align: 'center',
                        },
                    ]
                })


                TableFunc.showTable({
                    url: "/service/platform/login/user/jiangHuPaiRank/list",
                    queryParams: queryPaiParams,
                    pagination: false,
                    elm:"paiTable",
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
                            title: '组名',
                            align: 'center',
                        },
                        {
                            field: 'gold',
                            title: '贡献值',
                            align: 'center',
                        },
                    ]
                })
            })
        }


        query = function () {
            $("#paiTable").bootstrapTable('refresh');
            $("#table").bootstrapTable('refresh');
        }

        queryParams = function (params) {
            var param = {
                paginationParams:{
                    pageIndex : this.pageNumber,
                    pageSize : this.pageSize,
                },
                startTimeStr:$("#startTime").val(),
                name:$("#userName").val(),
            }
            var groupId = $("#groupId").selectpicker('val')
            if(StringUtil.isNotEmpty(groupId)){
                var userIds = _userList[groupId]
                if(userIds.length > 0){
                    param.userIds = ArrayFunc.toCommaStr(userIds);
                }
            }
            if(StringUtil.isEmpty(groupId) && StringUtil.isEmpty(param.name)){
                var roleType = $("#roleType").selectpicker('val')
                switch (roleType){
                    case "cs":
                        param.userIds = _roleUserIdMap.CS
                        break;
                    case "interview":
                        param.userIds = _roleUserIdMap.interview;
                        break
                }
            }
            return param;
        }

        queryPaiParams = function (params) {
            var param = {
                paginationParams:{
                    pageIndex : this.pageNumber,
                    pageSize : this.pageSize,
                },
                startTimeStr:$("#startTime").val(),
                roleType : $("#roleType").selectpicker('val')
            }
            return param;
        }

        reset = function () {
            $("#startTime").val(_todayStr)
            $("#userName").val('')
            $("#groupId").selectpicker('val','')
            $('#roleType').selectpicker("val", "cs")
            $("#table").bootstrapTable('refresh')
        }

        init();
    }










