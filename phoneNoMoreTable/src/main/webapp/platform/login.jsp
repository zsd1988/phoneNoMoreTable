<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.qingpu.phone.user.entity.User" %>
<%@ page import="com.qingpu.phone.common.utils.ArithUtil" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
Integer logoPngIndex = ArithUtil.randomDigit(0, 9);
User user = (User)session.getAttribute("_user");
if (null != user) {
	response.sendRedirect(basePath+"/service/platform/login/user/user/welcome");
	return;
}
%>

<!DOCTYPE html>
<html>
  <head>       
	<base href="<%=basePath%>/platform/">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" href="assets/images/favicon_1.ico">
	<title>登录-擎谱智能呼叫中心</title>
    <script src="js/globalData.js"></script>

	<link href="assets/plugins/bootstrapvalidator/src/css/bootstrapValidator.css" rel="stylesheet" type="text/css" />

	<link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="assets/css/core.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/components.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/icons.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/pages.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/responsive.css" rel="stylesheet" type="text/css" />
	<link href="assets/css/waves.min.css" rel="stylesheet" type="text/css" />

      <link href="assets/plugins/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" rel="stylesheet" />
      <link href="assets/plugins/multiselect/css/multi-select.css"  rel="stylesheet" type="text/css" />
      <link href="assets/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" />
      <link href="assets/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" type="text/css">
      <link rel="stylesheet" type="text/css" href="assets/plugins/vis/vis-network.min.css"/>
      <link rel="stylesheet" type="text/css" href="assets/plugins/vis/vis.min.css"/>
      <link href="assets/plugins/sweetalert/dist/sweetalert.css" rel="stylesheet" type="text/css">
      <link rel="stylesheet" href="assets/plugins/morris/morris.css">

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="assets/js/modernizr.min.js"></script>
  </head>
  
	<body>
		<div class="account-pages" style="background: url('assets/images/loginBg.jpg') no-repeat center top;background-size:100% 100% !important; "></div>
		<div class="clearfix"></div>
		<div class="wrapper-page" style="width:600px">
            <div class="raw_logo" style="text-align: center;  margin-bottom: 70px;"><img src="assets/images/raw_logo.png" style="width: 240px;" ></div>
			<div class="card-box" style="padding: 0; overflow: hidden; background-color: #d9daff;">
				<div class="col-md-6" style="padding-left: 0">
                    <img  src="assets/images/raw_<%= logoPngIndex%>.jpg" >
				</div>
				<div class="login-interface  col-md-6" >
					<form class="form-horizontal" data-parsley-validate id="form0">
						<div class="panel-heading">
							<h3 class="text-center">
								<!--	<img src="assets/images/LOGO.png" style="width: 46px; margin-right: 10px;">-->
                                   <strong class="text-custom" style="color: #55b6eb"> —     LOGIN     —</strong></h3>
                           </div>
                           <div class="form-group m-t-20">
                               <div class="col-sm-12">
                                   <label class="control-label col-md-3">工号：</label>
                                   <div class="col-md-9">
                                       <input type="text" required data-parsley-trigger="focusout" class="form-control" id="inputEmail" name="inputEmail">
                                       <p style="color:green; display:none;" id="info2" ></p>
                                   </div>
                               </div>
                           </div>
                           <div class="form-group">
                               <div class="col-sm-12">
                                   <label class="control-label col-md-3">姓名：</label>
                                   <div class="col-md-9">
                                       <input type="text"  required data-parsley-trigger="focusout" class="form-control" id="name">
                                   </div>
                               </div>
                           </div>
                           <div class="form-group">
                               <div class="col-sm-12">
                                   <label class="control-label col-md-3">类型：</label>
                                   <div class="types-of col-md-9">
                                       <select class="form-control col-md-12" id="workType" >
                                           <option value="FullTime">全职</option>
                                           <option value="PartTime">兼职</option>
                                       </select>
                                   </div>
                               </div>
                           </div>
                           <div class="form-group">
                               <div class="col-sm-12">
                                   <label class="control-label col-md-3">密码：</label>
                                   <div class="col-md-9">
                                       <input id="hori-pass1" name="hori-pass1" type="password" required class="form-control"  data-parsley-trigger="focusout">
                                       <p style="color:green; display:none;" id="info3" ></p>
                                   </div>
                               </div>
                           </div>
                           <div class="form-group text-center m-t-40" style="margin-bottom: 0;">
                               <div class="col-xs-12">
                                   <button class="btn btn-danger btn-block text-uppercase waves-effect waves-light" id="loginBtn" type="button" style="width: 70%;
                                   background-color: #5b53cb !important; border: 1px solid #5b53cb !important;  margin-top: -20px; height:30px;">登 录</button>
                               </div>
                           </div>
                       </form>
                   </div>

               </div>
           </div>
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

        <script type="text/javascript" src="assets/plugins/parsleyjs/dist/parsley.min.js"></script>
        <script type="text/javascript" src="assets/plugins/parsleyjs/dist/i18n/zh_cn.js"></script>
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
		<script type="text/javascript">
		    saveObjectData("userinfo", null);//清空微信user信息
		    saveObjectData("selectedScreen", null);
		    
        	$(document).ready(function(){
				
				//填写邮箱之后检验是否存在此用户
				$("#inputEmail").blur(function(){
					if($("#inputEmail").parsley().isValid()){
						//如果校验通过,向服务器查询此用户是否存在
						var imeil = $(this).val().trim();
						var url = "<%=basePath%>/service/platform/noLogin/user/checkImeilUserExist";
						var params = {
							imeil: imeil
						};
						jsonrpc(url, params, checkExecSuccess, checkExecFailed);						
					}
				});
				function checkExecSuccess(data){
					$("#info2").css("display", "none");
				}
				function checkExecFailed(message){
					$("#info2").html(message);
					$("#info2").css("color", "red");
					$("#info2").css("display", "block");
				}
				
				function checkExecSuccess2(data){
					$("#info2").css("display", "none");
					
					var imeil = $("#inputEmail").val().trim();
					var password = $("#hori-pass1").val().trim();
                    var name = $("#name").val().trim();
                    var workType = $("#workType").selectpicker("val");
                    var url = "<%=basePath%>/service/platform/noLogin/user/imeilLogin";
					var params = {
						imeil: imeil,
						password: $.md5(password),
                        workType:workType,
                        name:name
					}
                    if(typeof qingpuJsObj != 'undefined'){
                        params.ip = qingpuJsObj.getIP();
                        if(StringUtil.isNotEmpty(params.ip)){
                            // boldFunc.showMessage("获取到IP地址： " + params.ip)
                        }
                    }
					jsonrpc(url, params, loginExecSuccess, loginExecFailed);
				}

				//点击登录
				$("#loginBtn").click(function(){
                    login()
					event.preventDefault();
				});
                //回车事件
                document.onkeydown = function (event) {
                    var e = event || window.event;
                    if (e && e.keyCode == 13) { 		//回车键的键值为13
                        login()
                    }
                };
                login = function () {
                    $("#inputEmail").parsley().validate();
                    $("#hori-pass1").parsley().validate();
                    $("#name").parsley().validate();
                    if($("#inputEmail").parsley().isValid() && $("#hori-pass1").parsley().isValid() && $("#name").parsley().isValid()){
                        //如果校验通过,向服务器查询此用户是否存在
                        var imeil = $("#inputEmail").val().trim();
                        var url = "<%=basePath%>/service/platform/noLogin/user/checkImeilUserExist";
                        var params = {
                            imeil: imeil
                        };
                        jsonrpc(url, params, checkExecSuccess2, checkExecFailed);
                    }
                }
				function loginExecSuccess(data){
					$("#info3").css("display", "none");
					saveObjectData("userinfo", data);
					window.location.href = "<%=basePath%>/service/platform/login/user/user/welcome";
				}
				function loginExecFailed(message){
					$("#info3").html(message);
					$("#info3").css("color", "red");
					$("#info3").css("display", "block");
				}

				$("#testException").click(function(){
                    var params = {
                        imeil: "imeil"
                    };
				    jsonrpc("<%=basePath%>/service/platform/login/user/user/testException", params, execOK, execFailed);
				});
                function execOK(e){
                    alert("请求成功" + e);
                }
                function execFailed(e){
                    alert("失败" + e);
                }


                init = function () {
                    GlobalData.basePath = "<%= basePath%>";

                    $('#workType').selectpicker({ });
                }


                init();
			});

		</script>
	</body>
</html>
