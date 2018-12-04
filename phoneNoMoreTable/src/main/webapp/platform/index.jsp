<%@ page import="com.qingpu.phone.user.entity.Permissions" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.qingpu.phone.user.entity.User" %>
<%@ page import="com.qingpu.phone.constants.QingpuConstants" %>
<%@ page import="com.qingpu.phone.common.utils.CommonUtils" %>
<%@ page import="com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    String imagePath =  request.getScheme()+"://" + QingpuConstants.File_Server_Ip +":"+QingpuConstants.File_Server_Port;
    String ipAndHostPath = request.getServerName()+":"+request.getServerPort() + path;
    User user = (User)session.getAttribute("_user");
    if (null == user) {
        response.sendRedirect(basePath+"/platform/login.jsp");
        return;
    }
    String permissionsTags = CommonUtils.getEnumKeyValueString("permissionsTag");
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>/platform/">
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" href="assets/images/favicon_1.ico">

    <title>擎谱智能呼叫中心</title>
    <script src="js/globalData.js"></script>

    <%--上传文件--%>
    <link href="js/webuploader/css/webuploader.css" rel="stylesheet" type="text/css">

    <%--选择日期--%>
    <link href="assets/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet">
    <link href="assets/plugins/mjolnic-bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css" rel="stylesheet">
    <link href="assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="assets/plugins/datetimepicke/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="assets/plugins/clockpicker/dist/jquery-clockpicker.min.css" rel="stylesheet">
    <link href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <%--选择日期--%>

    <link href="assets/plugins/jquery-editable-select-master/dist/jquery-editable-select.min.css" rel="stylesheet">
    <link href="assets/plugins/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" rel="stylesheet" />
    <link href="assets/plugins/multiselect/css/multi-select.css"  rel="stylesheet" type="text/css" />
    <link href="assets/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" />
    <link href="assets/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="assets/plugins/vis/vis-network.min.css"/>
    <link rel="stylesheet" type="text/css" href="assets/plugins/vis/vis.min.css"/>
    <link href="assets/plugins/sweetalert/dist/sweetalert.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="assets/plugins/morris/morris.css">


    <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/core-qunee.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/components.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/icons.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/pages.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/responsive.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/base.css" />
    <link rel="stylesheet" href="css/menu.css" />
    <script src="assets/js/modernizr.min.js"></script>
</head>

<body class="fixed-left">
<!-- Begin page -->
<div id="wrapper">

    <!-- Top Bar Start -->
    <div class="topbar">

        <!-- LOGO -->
        <div class="topbar-left">
            <div class="text-center">
                <a href="/service/platform/login/user/user/welcome" class="logo"><i class="icon-magnet icon-c-logo"></i>
                    <img src="assets/images/LOGO.png" style="width: 46px; margin-right: 15px;">
                    <span>擎谱智能呼叫中心</span>
                </a>
            </div>
        </div>


        <!-- Button mobile view to collapse sidebar menu -->
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="a" id="indexImportIntention"  onclick="gotoPage('common/importIntention')" style="display: none">
                            <a >录入意向</a>
                        </li>
                        <li class="a" id="indexLuckDraw"  onclick="indexDraw()" style="display: none">
                            <a >抽奖</a>
                        </li>
                        <li class="a">
                            <a><%= ((user.getExtNum()+"").equals("null"))?"":"<i class='ti-agenda m-r-5'></i>" + "分机号：" + user.getExtNum() %></a>
                        </li>
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><i class="ti-user m-r-5"></i> <%= user.getName()%><span class="fa fa-caret-down toggle-content" style="margin-left: 10px;"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href=""  data-toggle="modal" data-target="#con-password-modal"><i class="ti-lock m-r-5"></i> 修改密码</a></li>
                                <li onclick="loginOut(false)"><a href="javascript:void(0)"><i class="ti-power-off m-r-5"></i> 退出 </a></li>
                            </ul>
                        </li>
                        <li class="return">
                            <a  class="dropdown-toggle" onclick="goBack()">  <img src="assets/images/return.png" style="width: 20px; margin-right: 10px;">返回</a>
                        </li>
                    </ul>
                </div>
                <div class="tabs seat-switch" id="changeStatus" style="display: none">
                    <ul>
                        <li class="active"  onclick="tabSelect(0, true)" ><a href="javascript:void(0)"  ><img id="im1" src="assets/images/Ready.png"><span>就绪</span></a>
                        </li>
                        <li  onclick="tabSelect(1, true)" ><a href="javascript:void(0)" ><img id="im2" src="assets/images/rest.png"><span>休息</span></a>
                        </li>
                        <li ><a  href="javascript:void(0)"  onclick="indexGetClientInfo()" ><img id="im3" src="assets/images/incoming .png"><span>来电弹屏</span></a>
                        </li>
                    </ul>
                </div>
                <!--/.nav-collapse -->
            </div>
        </div>
    </div>
    <!-- Top Bar End -->
    <form id="con-password-modal" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-parsley-validate   style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearModalData()">×</button>
                    <h4 class="modal-title">修改密码</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label  class="control-label col-md-3">原始密码：</label>
                                <div class="col-md-9">
                                    <input type="password" name="pwBefore" required  id="indexPasswordBefore" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label  class="control-label col-md-3">新密码：</label>
                                <div class="col-md-9">
                                    <input type="password"  required   data-parsley-trigger="focusout"   class="form-control"  id="indexPassword" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label  class="control-label col-md-3">确认密码：</label>
                                <div class="col-md-9">
                                    <input type="password"   required
                                           data-parsley-equalto="#indexPassword" data-parsley-trigger="focusout"   class="form-control"  id="indexConfirmPassword"  >
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default waves-effect" data-dismiss="modal" onclick="clearModalData()">取消</button>
                    <button type="button" class="btn btn-success waves-effect waves-light" onclick="changPw()">提交</button>
                </div>
            </div>
        </div>
    </form>


    <!-- ========== 来电弹屏 ========== -->
    <form id="callin-tp-modal" class="modal fade" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel"  data-parsley-validate  >
        <div class="modal-dialog"  style="width:900px">
            <div class="modal-content" style="background:#C7EECE;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"  onclick="closeIndexClient()">×</button>
                    <h4 class="modal-title" id="indexCallingTitle" style="width: 90px;text-align: center;font-size: 18px;">来电弹屏</h4>
                </div>
                <div class="modal-body">
                    <input type="text"   id="id"   name="id" style="display: none">
                    <div class="row rowCount">
                        <div class="col-sm-12">
                            <h4 class="modal-title"><b>客户信息</b> </h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12  call-screen">
                            <div class="card-box" style="background:#C7EECE;">
                                <div class="col-md-12">
                                    <div class="input-group-sm col-md-4 ">
                                        <label class="control-label col-md-4">客户姓名：</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" id="indexName" name="name">
                                        </div>
                                    </div>
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">性别：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker"  id="indexSex" name="sex">
                                                <option value ="1" >男</option>
                                                <option value ="0" >女</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">手机号码：</label>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" readonly id="indexPhone" name="phone" >
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">年龄段：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker" id="indexYearTag" name="yearTag">
                                            </select>
                                        </div>
                                    </div>
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">区域：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker" placeholder="全部" id="indexArea" name="area">
                                            </select>
                                        </div>
                                    </div>
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">实时状态：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker" id="indexOnceStatus" name="onceStatus">
                                                <option value = "true">实时</option>
                                                <option value = "false">非实时</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">客户状态：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker" onChange="indexSetReviewStatus(this);" id="indexStatus" name="status">
                                            </select>
                                        </div>
                                    </div>
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">审核状态：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker"  id="indexReviewStatus" name="reviewStatus">
                                            </select>
                                        </div>
                                    </div>
                                    <div class="input-group-sm col-md-4">
                                        <label class="control-label col-md-4">约访状态：</label>
                                        <div class="col-md-8">
                                            <select class="form-control selectpicker" onChange="indexSetConfirmStatus(this);" id="indexConfirmStatus" name="confirmStatus">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="col-md-6">
                                        <h4 class="m-t-0"><b onclick="commonSpeech()">备注（常用短语）</b> </h4>
                                        <textarea class="form-control" rows="5" id="indexDes" name="des"></textarea>
                                    </div>
                                    <div class="col-md-6">
                                        <h4 class="m-t-0"><b>智能语音问答</b> </h4>
                                        <textarea class="form-control" rows="5" id="indexVoiceContent" name="voiceContent"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-warning waves-effect waves-light" id="indexHangUp" onclick="indexHangup()">主动挂断</button>
                        <button type="button" class="btn btn-success waves-effect waves-light" id="indexClientSaveBtn" onclick="saveTpClient()">保存</button>
                    </div>
                    <div class="row rowCount">
                        <div class="col-sm-12">
                            <h4 class="modal-title">通话记录 <a href="javascript:void(0)" onclick="indexRefresh()"  style='text-decoration:underline;'>[刷新]</a></h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12">
                                <div class="inquire pull-right">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="card-box" style="background:#C7EECE;">
                                <video id="indexMyMp3" controls="controls" style="width: 100%; height: 30px;display: none"   preload="auto" ></video>
                                <table id="indexCallRecordTable">
                                </table>
                                <br>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </form>



    <form id="indexMessage" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false"
          aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;" data-parsley-validate>
        <div class="modal-dialog " style="top: 30%;">
            <div style="background: url('img/message.png') no-repeat center top;width: 495px;height: 370px;">
                <div style="       width: 453px;    position: absolute;    height: 207px;    top: 139px;    left: 17px;">
                    <p id="indexMessageContent"
                         style="font-size: 20px;top: 50%;width: 100%;position: absolute;text-align: center;line-height: 43px;word-wrap:break-word;color: #e55465;transform: translate(0,-50%);"></p>
                </div>
                <div onclick="closeIndexMessage()" style="z-index: 10000">
                    <image style="position: absolute;top:68px;left:454px;z-index: 10000;" src="img/guanbi.png"></image>
                </div>
            </div>
        </div>
    </form>

    <%--通话记录modal--%>
    <div id="indexModalList">
    </div>

    <%--最小化--%>
    <div class="pop-box row" >
        <div class="col-sm-12"  id="indexMinList">

        </div>
    </div>
    <!-- ========== Left Sidebar Start ========== -->
    <div class="left side-menu">
        <div class="sidebar-inner slimscrollleft">
            <!--- Divider -->
            <div id="sidebar-menu">
                <ul>
                    <c:forEach var="permissionInfo" items="${permissionList}" varStatus="status">
                        <li class="text-muted menu-title">${permissionInfo.permissionName}</li>
                        <c:forEach var="permission1" items="${permissionInfo.permissionsList}" varStatus="status1">
                            <c:if test="${permission1.permissionName==selectName}">
                                <li class="has_sub">
                                    <a href="javascript:void(0)" onclick="gotoPage('${permission1.permissionUrl}','','${permission1.tag}')" class="waves-effect subdrop"><i class="${permission1.className}"></i><span>${permission1.permissionName}</span></a>
                                    <c:if test="${not empty permission1.permissionsList}">
                                        <c:set var="permissionBase"  value="${permission1}"     scope="request" />
                                        <c:import url="permission.jsp"/>
                                    </c:if>
                                </li>
                            </c:if>
                            <c:if test="${permission1.permissionName!=selectName}">
                                <li class="has_sub">
                                    <a href="javascript:void(0)" onclick="gotoPage('${permission1.permissionUrl}','','${permission1.tag}')" class="waves-effect"><i class="${permission1.className}"></i><span>${permission1.permissionName}</span></a>
                                    <c:if test="${not empty permission1.permissionsList}">
                                        <c:set var="permissionBase"  value="${permission1}"     scope="request" />
                                        <c:import url="permission.jsp"/>
                                    </c:if>
                                </li>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start right Content here -->
    <!-- ============================================================== -->
    <div class="content-page" id="pageDiv">
        <!-- Start content -->
        <div class="content">
            <div class="container" id="pageContent">
            </div>
        </div> <!-- container -->
        <!-- content -->
    </div>

    <footer class="footer">
        Copyright &copy; 2017-2018 擎谱集团
    </footer>
</div>



<!-- ============================================================== -->
<!-- End Right content here -->
<!-- ============================================================== -->

<script>
    var resizefunc = [];
</script>

<!-- jQuery  -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/detect.js"></script>
<script src="assets/js/fastclick.js"></script>
<script src="assets/js/jquery.slimscroll.js"></script>
<script src="assets/js/jquery.blockUI.js"></script>
<script src="assets/js/wow.min.js"></script>
<script src="assets/js/jquery.nicescroll.js"></script>
<script src="assets/js/jquery.scrollTo.min.js"></script>

<script src="assets/js/jquery.core.js"></script>
<script src="assets/js/jquery.app.js"></script>

<script src="assets/plugins/moment/moment.js"></script>

<%--input select选择框--%>
<script type="text/javascript" src="assets/plugins/multiselect/js/jquery.multi-select.js"></script>
<script src="assets/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js" type="text/javascript"></script>

<script src="assets/plugins/bootstrap-filestyle/src/bootstrap-filestyle.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
<script src="assets/plugins/clockpicker/dist/jquery-clockpicker.min.js"></script>
<script src="assets/plugins/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="assets/plugins/bootstrap-fileinput/js/locales/zh.js"></script>
<script src="assets/plugins/sweetalert/dist/sweetalert.min.js"></script>
<script src="assets/plugins/custombox/dist/custombox.min.js"></script>
<script src="assets/plugins/custombox/dist/legacy.min.js"></script>
<script src="assets/plugins/notifyjs/dist/notify.min.js"></script>
<script src="assets/plugins/notifications/notify-metro.js"></script>

<%--table表--%>
<link href="assets/plugins/bootstrap-table/src/bootstrap-table.css" rel="stylesheet" type="text/css">
<script src="assets/plugins/bootstrap-table/src/bootstrap-table.js"></script>
<script src="assets/plugins/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="assets/plugins/jquery-ui/external/jquery.tablednd.js"></script>
<link href="assets/plugins/bootstrap-table/reorder-rows/bootstrap-table-reorder-rows.css" rel="stylesheet" type="text/css">
<script src="assets/plugins/bootstrap-table/reorder-rows/bootstrap-table-reorder-rows.min.js"></script>

<%--选择日期--%>
<script src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.zh-CN.js"></script>
<script src="assets/plugins/datetimepicke/bootstrap-datetimepicker.min.js"></script>
<script src="assets/plugins/datetimepicke/bootstrap-datetimepicker.zh-CN.js"></script>

<script src="assets/plugins/timepicker/bootstrap-timepicker.min.js"></script>

<script src="assets/plugins/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>

<%--统计表显示--%>
<script src="assets/plugins/flot-chart/jquery.flot.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.time.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.tooltip.min.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.resize.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.pie.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.selection.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.stack.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.orderBars.min.js"></script>
<script src="assets/plugins/flot-chart/jquery.flot.crosshair.js"></script>

<script src="assets/plugins/jquery-editable-select-master/dist/jquery-editable-select.min.js"></script>
<script src="assets/plugins/morris/morris.min.js"></script>
<script src="https://cdn.bootcss.com/echarts/3.8.5/echarts-en.common.min.js"></script>
<script src="assets/plugins/raphael/raphael-min.js"></script>

<script src="assets/plugins/jquery-sparkline/jquery.sparkline.min.js"></script>

<%--地图显示--%>
<script type="text/javascript" src="assets/plugins/vis/vis.js"></script>
<script type="text/javascript" src="assets/plugins/vis/vis-network.min.js"></script>
<script src="assets/js/waves.js"></script>  <%--一定要放在vis.js的后面，不然Hammer变量冲突--%>

<script src="js/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/jquery.sort.js"></script>
<script type="text/javascript" src="js/jquery-jtemplates_uncompressed.js"></script>
<script src="js/wendu.ajaxhook.min.js"></script>
<script src="js/My97DatePicker/WdatePicker.js"></script>

<%--md5--%>
<script src="js/md5.min.js"></script>

<%--树形复选框--%>
<link href="js/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<script src="js/zTree/jquery.ztree.all.min.js"></script>

<%--树形--%>
<link href="css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<script src="js/jquery.ztree.core-3.5.js"></script>
<script src="js/jquery.ztree.excheck-3.5.js"></script>
<script src="js/jquery.ztree.exedit-3.5.js"></script>



<%--文件上传--%>
<script src="js/webuploader/webuploader.js"></script>

<script src="js/common.js"></script>
<script src="js/utils.js"></script>
<script src="js/folder.js"></script>
<script src="js/project.js"></script>


<script>
    var checkConnectInterval = null;
    var _indexIsForceLoginOut = false;

    closePhoneClient = function () {
        // 退出软电话
        if(StringUtil.isNotEmpty(GlobalData.extNum)){
            if(typeof qingpuJsObj != 'undefined'){
                qingpuJsObj.loginOut();
            }
        }
    }


    loginOut = function () {
        jsonrpc( "<%=basePath%>/service/platform/login/user/user/loginOut", {isForce:_indexIsForceLoginOut}, function () {
            closePhoneClient()
            window.location.href = "<%=basePath%>/platform/login.jsp";
        }, function () {
        })
    }
    initWebSocket = function () {
        //判断当前浏览器是否支持WebSocket
        if('WebSocket' in window){
            websocket = new WebSocket("ws://" + "<%= ipAndHostPath%>" + "/websocket");
        }
        else{
//            alert('当前浏览器不支持WebSocket通信，请更换浏览器重试');
            return;
        }
        //连接发生错误的回调方法
        websocket.onerror = function() {
            console.log("onerror");
        };
        //连接成功建立的回调方法
        websocket.onopen = function(event) {
            console.log("onopen");
            if(checkConnectInterval != null){
                clearInterval(checkConnectInterval);
            }
        }
        //接收到消息的回调方法
        websocket.onmessage = function(event) {
            //js解析json格式字符串
            var jsonObject = $.parseJSON(event.data);
            var want = jsonObject.want;
            switch (want){
                case "RegisterReturn":
                    websocketSend("SendUserId", {userId:GlobalData.userId})
                    break;
                case "SendOffline":
                    _indexIsForceLoginOut = true;
                    boldFunc.showAlert("您的账号在异地登录", function () {
                        loginOut(_indexIsForceLoginOut);
                    })
                    break;
                case "StartPhoneClient":
                    indexStartClientPhone();
                    break;
                case "HintFreeSwitchOff":
                    boldFunc.showErrorMes("sip服务器未开启")
                    break;
                case "UNALLOCATED_NUMBER":
                case "NO_USER_RESPONSE":
                    boldFunc.showErrorMes("第三方网关出错 " + want)
                    break;
                case "Phone_UNALLOCATED_NUMBER":
                case "Phone_NO_USER_RESPONSE":
                    boldFunc.showErrorMes("手机第三方网关出错 " + want)
                    break;
                case "DESTINATION_OUT_OF_ORDER":
                    boldFunc.showErrorMes("本地网关出错 " + want)
                    break;
                case "ShowDraw":
                    $("#indexLuckDraw").show();
                    $("#indexLuckDraw").find("a").blink({
                        delay: 500
                    });
                    break;
                case "ShowGoldenEgg":
                    // 显示砸金蛋界面
                    gotoPage("system/goldenEgg")
                    break;
                case "ShowPostMessage":
                    var data = jsonObject.data
                    var postMessage = $.parseJSON(data);
                    var type = postMessage.type
                    if(type == "Admin" ){
                        $("#indexMessageContent").text(postMessage.content)
                        $("#indexMessage").modal('show')
                    }else if(type == "Birthday" || type == "FirstDay" || type == "WeekDay" || type == "MonthDay" || type == "YearDay" || type == "SecondYear"
                        || type == "ThirdYear"|| type == "FourthYear"|| type == "FifthYear"|| type == "SixthYear"|| type == "SeventhYear"|| type == "EighthYear"
                        || type == "NinthYear"|| type == "TenthYear"){
                        $.Notification.autoHideNotify('warning', 'top right', postMessage.content,null)
                    }
                    break
                case "ShowIntentionHint":
                    var content = jsonObject.data
                    $.Notification.autoHideNotify('info', 'top right', content,null)
                    break;
                case "ShowGoldenHint":
                    var content = jsonObject.data
                    $.Notification.autoHideNotify('info', 'top right', content,null)
                    break;
                case "ShowLuckDrawHint":
                    var content = jsonObject.data
                    $.Notification.autoHideNotify('warning', 'top right', content,null)
                    break;
                case "ShowClient":
                    // $("#callin-tp-modal").modal("hide")
                    isInCalling = true;
                    var data = JsonFunc.toObject(jsonObject.data)
                    showClientId = data.clientId;
                    lastCallClientId = showClientId;
                    $("#indexVoiceContent").val("")
                    $("#indexVoiceContent").val(data.text)
                    tabSelect(1, false)
                    $("#indexCallingTitle").text("来电弹屏")
                    $("#indexCallingTitle").css("background", "inherit")
                    indexGetClientInfo()
                    break;
                case "Offline":
                    // 电话转接进来，收到离线
                    tabSelect(1, false)
                    break;
                case "Online":
                    // 接完电话后，点击保存，收到在线
                    tabSelect(0, false)
                    break;
                case "CallEnd":
                    // 通话结束
                    $("#indexCallingTitle").text("通话结束")
                    $("#indexCallingTitle").css("background", "red")
                    break;
                case "HangUp":
                    boldFunc.showErrorMes("请先挂断分机");
                    break
            }
        }
        //连接关闭的回调方法
        websocket.onclose = function() {
            console.log("onclose");
            if( ! _indexIsForceLoginOut){
                // 判断网络是否连接
                checkConnectInterval = setInterval(function () {
                    $.ajax({
                        type: 'post',
                        cache:true,
                        url: GlobalData.basePath + "/service/platform/noLogin/user/checkIsConnection",
                        data: null,
                        error: function (err) {
                            boldFunc.showErrorMes("网络连接断开，请检查网络")
                        },
                        success:function (data) {
                            // closePhoneClient()
                            closePhoneClient()
                            window.location.href = GlobalData.basePath + "/platform/login.jsp";
                        }
                    });
                }, 5000)
            }
        }
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function() {
            websocket.close();
            console.log("onbeforeunload");
        }
    }


    sendClosePhoneClient = function(){
        alert(0)
    }

    websocketSend = function (want, dataStr) {
        dataStr = ObjectFunc.toJsonStr(dataStr)
        var data = {
            want:want,
            data:dataStr
        }
        websocket.send(ObjectFunc.toJsonStr(data));
    }

    GlobalData.basePath = "<%= basePath%>";
    GlobalData.fileUrl = "<%= QingpuConstants.uploadUrl%>";
    GlobalData.sipIP = "<%= CallPhoneListener._socketHost %>";
    GlobalData.imageUrl = "<%= imagePath%>" + "/upload/img/";
    GlobalData.videoUrl = "<%= imagePath%>" + "/upload/video/";
    GlobalData.userId = "<%= user.getId()%>";
    GlobalData.workNumber = "<%= user.getWorkNumber()%>";
    GlobalData.userName = "<%= user.getName()%>";
    GlobalData.extNum = "<%= user.getExtNum()%>";
    GlobalData.roleId = "<%= user.getRoleId()%>";
    GlobalData.isSupportIP = <%= QingpuConstants._isSupportIP%>;
    GlobalData.permissionsTags = JSON.parse('<%= permissionsTags%>');
    GlobalData.roleSuperId = "<%= QingpuConstants.Roles_Super_id%>"
    GlobalData.roleManager = "<%= QingpuConstants.Roles_Manager_id%>"
    GlobalData.roleCS = "<%= QingpuConstants.Roles_Employee_id%>"
    GlobalData.roleReview = "<%= QingpuConstants.Roles_Quality_id%>"
    GlobalData.roleInterview = "<%= QingpuConstants.Roles_Interview_id%>"
    GlobalData.isTeamLeader = "<%= user.getIsTeamLeader() %>"
    GlobalData.uploadDefaultPng = GlobalData.basePath + "/platform/img/plus.png"
    switch (GlobalData.roleId){
        case GlobalData.roleCS:
            GlobalData.isCS = true;
            break;
        case GlobalData.roleInterview:
            GlobalData.isInterview = true;
            break
        case GlobalData.roleReview:
            GlobalData.isReviewer = true;
            GlobalData.isAdminOrReviewer = true;
            break
        case GlobalData.roleManager:
        case GlobalData.roleSuperId:
            GlobalData.isAdminOrReviewer = true;
            GlobalData.isManager = true;
            break
        default:
            break
    }
    var functionStr = "${functionIds}"
    if(functionStr.length > 2){
        functionStr = functionStr.substring(1, functionStr.length - 1).replace(/\s|\xA0/g,"");
        GlobalData.functionIdArr = functionStr.split(",");
    }

    // 检测是否登录超时
    hookAjax({
        onreadystatechange:function(xhr){
        },
        open:function(arg,xhr){
        },
        onload:function(xhr){
            if(xhr.xhr != null){
                var url = xhr.xhr.responseURL
                // 登录超时跳转到登录界面
                if(StringUtil.contains("/platform/login.jsp", url)){
                    closePhoneClient()
                    window.location.href = GlobalData.basePath + "/platform/login.jsp";
                }
            }
        }
    })



    changPw = function() {
        ParsleyFunc.checkForm($("#con-password-modal"), function () {
            var indexPasswordBefore = $("#indexPasswordBefore").val();
            var indexPassword = $("#indexPassword").val();
            //增加判断原密码是否正确
            var params = {
                id: GlobalData.userId,
                passwordBefore: $.md5(indexPasswordBefore),
                password: $.md5(indexPassword)
            }
            getDataByURL("/service/platform/login/user/user/changPw", params, function (data) {
                boldFunc.notification("操作成功")
                $("#con-password-modal").modal('hide');
                clearModalData();
                loginOut();
            })
        })
    }

    clearModalData = function(){
        FormFunc.clearForm("#con-password-modal");
    }


    var clientPhone = "";
    var clientIdIndex = "";
    var showClientId = "";
    var isInCalling = false;
    var lastCallClientId = "";

    queryParamsIndexCallRecord = function (params) {
        var param = {
            paginationParams:{
                pageIndex : this.pageNumber,
                pageSize : this.pageSize,
            },
            clientId:clientIdIndex,
            phone:clientPhone,
        }
        return param;
    }

    //来电弹屏----//
    indexGetClientInfo = function(){
        if(StringUtil.isNotEmpty(showClientId)){
            var params = {
                id: showClientId,
            }
            getDataByURL("/service/platform/login/clientManager/client/getClient", params, function (data) {
                var dataJson = data.data;
                clientPhone = dataJson.phone
                clientIdIndex = dataJson.id
                dataJson = handlePhone(dataJson)
                FormFunc.loadObject(dataJson,"#callin-tp-modal");
                // 获取通话记录
                var colums = [
                    {
                        field: 'clientName',
                        title: '客户名称',
                        align: 'center',
                    },
                    {
                        field: 'phone',
                        title: '被叫号码',
                        align: 'center',
                        formatter: function (value, row, index) {
                            row = handlePhone(row)
                            var str = row.phone;
                            return str;
                        }
                    },
                    {
                        field: 'clientReviewStatusStr',
                        title: '审核状态',
                        align: 'center',
                    },
                    {
                        field: 'projectName',
                        title: '项目名',
                        align: 'center',
                    },
                    {
                        field: 'clientDes',
                        title: '备注',
                        align: 'center',
                    },
                    {
                        field: 'time',
                        title: '通话时长',
                        align: 'center',
                    },
                    {
                        title: '录音',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            if( StringUtil.isNotEmpty(row.recordPath) ){
                                var a = '<button type="button" class="playing" onclick="indexPlayRecord(' + index + ')"><img src="assets/images/playing.png"></button>';
                                return a;
                            }
                        }
                    },
                ]
                if(GlobalData.isCS || GlobalData.isInterview){
                    colums.push({
                        title: '话机播放',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            if(StringUtil.isNotEmpty(row.recordPath)){
                                var a = '<button type="button" class="playing" onclick="indexFirstExtPlayRecord(' + index + ')"><img src="assets/images/playing.png"></button>';
                                a += '&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success waves-effect waves-light"  onclick="indexHangupPlayRecord()">挂断</button>'
                                return a;
                            }
                        }
                    })
                }
                colums.push({
                        field: 'createTimeStr',
                        title: '时间',
                        align: 'center',
                })
                if( ! GlobalData.isCS){
                    colums.push({
                        field: 'userName',
                        title: '跟进人',
                        align: 'center'
                    })
                }
                TableFunc.showTable({
                    url: "/service/platform/login/systemManager/callRecord/listByPhone",
                    queryParams: queryParamsIndexCallRecord,
                    pagination: true,
                    elm:"indexCallRecordTable",
                    columns: colums
                })
                $("#callin-tp-modal").modal("show")
                $("#indexCallRecordTable").bootstrapTable('refresh')
            });
        }else{
            boldFunc.notification("暂时没有来电")
        }
    }

    /**
     * 隐藏手机四位
     * @param data
     * @returns {*}
     */
    handlePhone = function(data){
        if(GlobalData.functionIdArr.contains("1bd24c8491134d988639ec6c3e7496c6")){
            data.phone = StringUtil.handlePhone(data.phone)
        }
        return data
    }

    /**
     * 播放录音
     */
    indexPlayRecord = function(index){
        var editData = $("#indexCallRecordTable").bootstrapTable('getData')[index];
        if(editData.recordPath != null){
            var $indexMyMp3 = $("#indexMyMp3")
            $indexMyMp3.show()
            $indexMyMp3.attr("autoplay", "autoplay")
            $indexMyMp3.attr("src", editData.recordPath)
        }
    }

    indexFirstExtPlayRecord = function (index) {
        var editData = $("#indexCallRecordTable").bootstrapTable('getData')[index];
        indexExtPlayRecord(editData.recordPath)
    }

    /**
     * 分机播放录音
     */
    indexExtPlayRecord = function(recordPath){
        if($("#changeStatus li ").eq(0).hasClass("active")){
            boldFunc.showErrorMes("请先设置为休息状态");
            return
        }
        var params = {
            extNum: GlobalData.extNum,
            recordPath:recordPath
        }
        getDataByURL("/service/platform/login/systemManager/callRecord/playExtRecord", params, function (data) {

        });
    }

    /**
     * 关闭来电弹屏
     */
    closeIndexClient = function () {
        var myVideo = document.getElementById('indexMyMp3');
        myVideo.pause();
        $(myVideo).attr("src", "")
        $(myVideo).hide()
    }


    $("#callin-tp-modal").on("hide.bs.modal", function () {
        $.each($(".modal-backdrop.fade.in"), function (i, item) {
            $(item).remove();
        });
    })


    $("#callin-tp-modal").on("show.bs.modal", function () {
        $("#callin-tp-modal").show()
    })

    // $("#callin-tp-modal").modal('show')

    var isIndexClientSaveBtnClick = true;
    saveTpClient = function () {
        if(isIndexClientSaveBtnClick) {
            isIndexClientSaveBtnClick = false;
            setTimeout(function() {
                isIndexClientSaveBtnClick = true;
            }, 1000);
            var params = $("#callin-tp-modal").serializeHumpObject();
            params.status = $("#indexStatus").selectpicker('val');
            if(StringUtil.isEmpty(params.status) && GlobalData.isCS){
                boldFunc.showErrorMes("请选择客户状态");
                return;
            }
            params.reviewStatus = $("#indexReviewStatus").selectpicker('val');
            params.confirmStatus = $("#indexConfirmStatus").selectpicker('val');
            params.onceStatus = $("#indexOnceStatus").selectpicker('val');
            var rStatus = params.reviewStatus;
            var oStatus = params.onceStatus;
            var status = params.status;
            //判断当审核状态为意向时必须选择实时状态
            if((rStatus == "A" || rStatus == "B" || rStatus == "C") && (oStatus != "true" && oStatus != "false") && GlobalData.isCS){
                boldFunc.showErrorMes("请选择实时状态");
                return;
            }
            if(GlobalData.isCS){
                if(oStatus == "true"){
                    if(status != "A" && status != "B"){
                        boldFunc.showErrorMes("非AB类意向，不能为实时状态");
                        return;
                    }
                }
            }
            if(oStatus == "true"){params.onceStatus=true;}
            if(oStatus == "false"){params.onceStatus=false;}
            if(lastCallClientId == params.id){
                params.calling = true;
            }
            getDataByURL("/service/platform/login/clientManager/client/update", params, function () {
                boldFunc.notification("操作成功");
                isInCalling = false
                // $("#callin-tp-modal").modal('hide');
                $("#indexCallRecordTable").bootstrapTable('refresh')
                $("#indexCallingTitle").text("来电弹屏")
                $("#indexCallingTitle").css("background", "inherit")
            })
        }
    }

    indexRefresh = function () {
        $("#indexCallRecordTable").bootstrapTable('refresh')
    }

    indexSetReviewStatus = function(){
        $("#indexReviewStatus").selectpicker('val',$("#indexStatus").selectpicker('val'));
    }

    indexSetConfirmStatus = function(){
        if(isInCalling){
            $("#indexReviewStatus").selectpicker('val',$("#indexConfirmStatus").selectpicker('val'));
        }
    }

    commonSpeech = function(){
        $("#indexDes").val($("#indexDes").val()+GlobalData.hintDes);
    }

    //contact_log_table数据绑定

    //获取下拉框数据
    var params = {params:"YearTag,Area,ClientStatus"}
    getDataByURL("/service/platform/noLogin/common/publicEnum/getMap", params, function (data) {
        var dataJson = data.data;
        SelectPickerFunc.loadLocalData('indexYearTag', JSON.parse(dataJson.YearTag ), true)
        SelectPickerFunc.loadLocalData('indexArea', JSON.parse(dataJson.Area ), true)
        SelectPickerFunc.loadLocalData('indexStatus',  JSON.parse(dataJson.ClientStatus ), true)
        SelectPickerFunc.loadLocalData('indexReviewStatus',  JSON.parse(dataJson.ClientStatus ), true)
        SelectPickerFunc.loadLocalData('indexConfirmStatus',  JSON.parse(dataJson.ClientStatus ), true)
        //SelectPickerFunc.loadMap('project',dataJson.Project, true)
    })

    if(GlobalData.isCS || GlobalData.isInterview){
        $("#changeStatus").show()
    }

    /**
     *切换休息和在线
     */
    tabSelect = function (index, isUserClick) {
        if(isUserClick && isInCalling){
            boldFunc.showErrorMes("在通话过程中不能手动切换状态，请保存来电弹屏后操作")
            return
        }
        var as = $("#changeStatus li");
        var obj = null;
        $.each(as, function (i, item) {
            if(i == index){
                obj = item
            }else{
                $(item).removeClass('active');
            }
        });
        if(isUserClick){
            var want = "UserSendOffLine"
            if(index == 0){
                want = "UserSendOnLine"
            }
            websocketSend(want, {userId:GlobalData.userId})
        }
        $(obj).addClass('active');
    }

    /**
     * index页面
     */
    init = function () {
        GlobalData.initPageJs()

        $('#indexSex').selectpicker({ });
        $('#indexSex').selectpicker("val", "");
        $('#indexOnceStatus').selectpicker({ });
        $('#indexOnceStatus').selectpicker("val", "");

        $("#pageDiv").css("min-height", $(window).height() - 61)
        initWebSocket();
        var gotoPageUrl = LocalStorageFunc.loadData(GlobalData.localGotoPage);
        if(StringUtil.isNotEmpty(gotoPageUrl)){
            LocalStorageFunc.removeItem(GlobalData.localGotoPage)
            gotoPage(gotoPageUrl, LocalStorageFunc.loadData(GlobalData.localGotoData))
        }

        if(GlobalData.isCS){
            $('#indexReviewStatus').attr("disabled",'disabled');
            $("#indexConfirmStatus").attr("disabled",'disabled');
        }else if(GlobalData.isInterview){
            $('#indexReviewStatus').attr("disabled",'disabled');
            $('#indexStatus').attr("disabled",'disabled');
            $('#indexOnceStatus').attr("disabled",'disabled');
            if(GlobalData.isTeamLeader){
                $("#indexImportIntention").show()
            }
        }else if(GlobalData.isReviewer){
            $('#indexConfirmStatus').attr("disabled",'disabled');
            // $('#status').attr("disabled",'disabled');
        }

        indexStartClientPhone();
    }

    //注册软电话
    indexStartClientPhone = function(){
        if(StringUtil.isNotEmpty(GlobalData.extNum) && ! GlobalData.isSupportIP ){
            if(typeof qingpuJsObj != 'undefined'){
                qingpuJsObj.regExtNum(GlobalData.extNum, GlobalData.sipIP);
            }
        }
    }

    /**
     * 抽奖
     */
    indexDraw = function () {
        getDataByURL("/service/platform/login/systemManager/LuckDrawRecord/create", null, function (data) {
            var dataJson = data.data
            if(dataJson != null ){
                if(dataJson.userDrawCount == 0){
                    $("#indexLuckDraw").hide()
                    $("#indexLuckDraw").find("a").unblink();
                }
                if(StringUtil.isNotEmpty(dataJson.name)){
                    boldFunc.showAlert("恭喜抽中：" + dataJson.name,function () {
                    })
                }
            }
        })
    }

    /**
     * 主动挂断电话
     */
    var isIndexClientHangup = true;
    indexHangup = function () {
        if(isIndexClientHangup) {
            isIndexClientHangup = false;
            setTimeout(function() {
                isIndexClientHangup = true;
            }, 1000);
            boldFunc.showConfirm("确定挂断电话", function () {
                    if(StringUtil.isNotEmpty(showClientId)){
                        getDataByURL("/service/platform/login/systemManager/callRecord/hangup", {clientId:showClientId}, function (data) {
                            boldFunc.notification("操作成功")
                        })
                }
            })
        }
    }

    /**
     * 手动挂断播放录音
     */
    indexHangupPlayRecord = function(){
        if(isIndexClientHangup) {
            isIndexClientHangup = false;
            setTimeout(function() {
                isIndexClientHangup = true;
            }, 1000);
            if(StringUtil.isNotEmpty(GlobalData.extNum)){
                getDataByURL("/service/platform/login/systemManager/callRecord/hangupPlayRecord", {extNum:GlobalData.extNum}, function (data) {
                    boldFunc.notification("操作成功")
                })
            }
        }
    }

    closeIndexMessage = function () {
        $("#indexMessage").modal('hide')
    }


    // isInCalling = true;
    // showClientId = "1d8ae250a176458dafc2fe361a3f6ca8";
    // lastCallClientId = showClientId;
    // $("#indexVoiceContent").val("")
    // $("#indexVoiceContent").val("")
    // tabSelect(1, false)
    // $("#indexCallingTitle").text("来电弹屏")
    // $("#indexCallingTitle").css("background", "inherit")
    // indexGetClientInfo()

    init();
</script>

</body>
</html>