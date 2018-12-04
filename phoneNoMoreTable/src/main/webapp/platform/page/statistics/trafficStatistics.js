//# sourceURL=trafficStatistics.js
function init_statistics_trafficStatistics() {
        var _editData = null;
        var _todayStr = DateUtil.getNowFormatDate();
        var _userList = null;
        var _roleUserIdMap= {};
        var _table=document.getElementById("table");

        init = function () {
            GlobalData.initPageJs();

            $("#startTime").val(_todayStr)
            $("#endTime").val(_todayStr)
            //获取下拉框数据
            var params = {params:"Project,VoiceType,CSGroup"}
            getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
                var dataJson = data.data
                SelectPickerFunc.loadLocalData('voiceType', JSON.parse(dataJson.VoiceType ), true)
                SelectPickerFunc.loadMap('projectId',dataJson.Project, true)
                SelectPickerFunc.loadMap('groupId',dataJson.CSGroup, true)
                _userList = dataJson.CSGroupUser;
            })
            //客服和约访只能查看本角色
            if(GlobalData.isCS){
                $("#roleType").selectpicker('val','cs')
                $("#roleType").attr("disabled",'disabled');
            }else if(GlobalData.isInterview){
                $("#roleType").selectpicker('val','interview')
                $("#roleType").attr("disabled",'disabled');
            }else if(GlobalData.isManager){
                $("#clear").show()
            }
            var startDate = null;
            if( ! GlobalData.isManager ){
                startDate = DateUtil.getLastMonthFirstDayFormatDate();
            }

            DateTimePickerFunc.init({
                id:"startTime",
                startDate:startDate,
                selectDay:true
            })

            DateTimePickerFunc.init({
                id:"endTime",
                selectDay:true
            })
            getDataByURL("/service/platform/login/user/user/getCSAndInterview", null, function (data) {
                var dataJson = data.data;
                _roleUserIdMap.CS = dataJson.CS;
                _roleUserIdMap.interview = dataJson.interview;

                TableFunc.showTable({
                    url: "/service/platform/login/systemManager/callRecord/listStatistics",
                    queryParams: queryParams,
                    pageSize:20,
                    pageList: [20, 50, 200, 500],
                    pagination: false,
                    columns: getColums()
                })
                //点击列名排序
                TableFunc.makeSortable(_table,-1,true);
                //第1列为工号，按字符排序
                TableFunc.makeSortable(_table,0,false);
            })
        }

        getColums = function () {
            var colums = [
                {
                    title: '序号',
                    align : 'center',
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'workNumber',
                    title: '工号',
                    align: 'center',
                },
                {
                    field: 'userName',
                    title: '姓名',
                    align: 'center',
                },
                {
                    field: 'sumTotal',
                    title: '电话量',
                    align: 'center',
                },
                {
                    field: 'sumYx',
                    title: '意向量',
                    align: 'center',
                }
            ]
            if($("#roleType").selectpicker('val')=='cs') {
                colums.push({
                    field: 'sumSs',
                    title: '实时数量',
                    align: 'center',
                })
            }
            colums.push(
                {
                    field: 'sumA',
                    title: 'A类意向',
                    align: 'center',
                },
                {
                    field: 'sumB',
                    title: 'B类意向',
                    align: 'center',
                },
                {
                    field: 'sumC',
                    title: 'C类意向',
                    align: 'center',
                }
            )
            if($("#roleType").selectpicker('val')=='cs') {
                colums.push({
                    field: 'sumWaiting',
                    title: '待申诉',
                    align: 'center',
                })
            }else if($("#roleType").selectpicker('val')=='interview') {
                colums.push(
                    {
                        field: 'Yx2Yx',
                        title: '意向转意向',
                        align: 'center',
                    },
                    {
                        field: 'Mh2Yx',
                        title: '模糊转意向',
                        align: 'center',
                    },
                    {
                        field: 'Mb2Yx',
                        title: '陌拜转意向',
                        align: 'center',
                    }
                )
            }
            colums.push({
                    field: 'sumBlur',
                    title: '模糊意向',
                    align: 'center',
                },
                {
                    field: 'sumMerchants',
                    title: '招商',
                    align: 'center',
                },
                {
                    field: 'sumReferrals',
                    title: '转介',
                    align: 'center',
                },
                {
                    field: 'sumShop',
                    title: '低总价商铺',
                    align: 'center',
                },
                {
                    field: 'sumHouse',
                    title: '住宅',
                    align: 'center',
                },
                {
                    field: 'sumOffice',
                    title: '写字楼',
                    align: 'center',
                },
                {
                    field: 'sumFlats',
                    title: '公寓',
                    align: 'center',
                },
                {
                    field: 'sumNo',
                    title: '没意向',
                    align: 'center',
                },
                {
                    field: 'efficiency',
                    title: '效率',
                    align: 'center',
                },
                {
                    field: 'salary',
                    title: '提成',
                    align: 'center',
                }
            )
            return colums;
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
            $("#table").bootstrapTable('destroy');
            TableFunc.showTable({
                url: "/service/platform/login/systemManager/callRecord/listStatistics",
                queryParams: queryParams,
                pageSize:20,
                pageList: [20, 50, 200, 500],
                pagination: false,
                columns: getColums()
            })
            //点击列名排序
            TableFunc.makeSortable(_table,-1,true);
            //第1列为工号，按字符排序
            TableFunc.makeSortable(_table,0,false);
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
                projectId:$("#projectId").selectpicker('val'),
                groupType:$("#voiceType").selectpicker('val'),
            }
            var groupId = $("#groupId").selectpicker('val')
            if(StringUtil.isNotEmpty(groupId)){
                var userIds = _userList[groupId]
                if(userIds.length > 0){
                    param.userIds = ArrayFunc.toCommaStr(userIds);
                }
            }
            var roleType = $("#roleType").selectpicker('val')
            var isSetRoleType = false;
            if(StringUtil.isEmpty(groupId) && StringUtil.isEmpty(param.workNumber)){
                isSetRoleType = true;
                switch (roleType){
                    case "cs":
                        param.userIds = _roleUserIdMap.CS
                        break;
                    case "interview":
                        param.userIds = _roleUserIdMap.interview;
                        break
                }
            }else{
                if(StringUtil.isNotEmpty(groupId)){
                    isSetRoleType = true;
                }
            }
            if(isSetRoleType){
                param.roleType = roleType;
            }
            return param;
        }


    /**
     * 清空缓存
     */
    clearStatistics = function () {
            getDataByURL("/service/platform/login/systemManager/callRecord/clearStatistics", null, function (data) {
                boldFunc.notification("清空成功")
            })
        }

        reset = function () {
            $("#startTime").val(_todayStr)
            $("#endTime").val(_todayStr)
            $("#workNumber").val('')
            $("#groupId").selectpicker('val','')
            $("#projectId").selectpicker('val', '')
            $("#voiceType").selectpicker('val', '')
            $("#roleType").selectpicker('val', 'cs')
            $("#table").bootstrapTable('refresh')
        }

        init();
    }










