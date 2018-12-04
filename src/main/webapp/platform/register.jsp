<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>platform/">
		<link rel="shortcut icon" href="assets/images/favicon_1.ico">

		<title>注册-擎谱数字</title>
		
		<link href="assets/plugins/bootstrapvalidator/src/css/bootstrapValidator.css" rel="stylesheet" type="text/css" />
		
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/core.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/components.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/icons.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/pages.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/responsive.css" rel="stylesheet" type="text/css" />

        <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->

        <script src="assets/js/modernizr.min.js"></script>

	</head>
	<body>
		<div class="account-pages"></div>
		<div class="clearfix"></div>
		<div class="wrapper-page">
			<div class=" card-box">
				<form class="form-horizontal" id="form0" data-parsley-validate>
					<div class="panel-heading">
						<h3 class="text-center">注册-<strong class="text-custom">擎谱数字平台</strong></h3>
					</div>
					<div class="form-group m-t-20">
						<div class="col-sm-12">
							<input type="email" required data-parsley-type="email" data-parsley-trigger="focusout" class="form-control" id="inputEmail" placeholder="邮  箱">
							<p style="color:green" class="hidden" id="info1" ></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<input id="nickname" type="text" placeholder="昵  称（最大长度为8个中文或者英文字符）" required class="form-control" data-parsley-maxlength="8" data-parsley-trigger="focusout">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<input id="hori-pass1" type="password" placeholder="密  码" required class="form-control" data-parsley-minlength="6" data-parsley-trigger="focusout">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<input data-parsley-equalto="#hori-pass1" data-parsley-trigger="focusout" data-parsley-minlength="6" type="password" required placeholder="重复密码" class="form-control" id="hori-pass2">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="checkbox checkbox-primary">
								<input id="checkbox-signup" type="checkbox" checked="checked" required>
								<label for="checkbox-signup">我接受 <a data-toggle="modal" data-target="#panel-modal">平台条款和相关协议</a></label>
							</div>
						</div>
					</div>
					<div class="form-group text-center m-t-40">
						<div class="col-xs-12">
							<button class="btn btn-danger btn-block text-uppercase waves-effect waves-light" id="registerBtn">注 册</button>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 text-center">
							<p>
								已 有 账 号？<a href="<%=basePath%>qingpudig/login" class="text-primary m-l-5"><b>登 录</b></a>
							</p>
						</div>
					</div>
				</form>
			<div id="panel-modal" class="modal fade" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				style="display: none;">
				<div class="modal-dialog">
					<div class="modal-content p-0 b-0">
						<div class="panel panel-color panel-primary">
							<div class="panel-heading">
								<button type="button" class="close m-t-5" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h3 class="panel-title">擎谱数字平台用户使用协议</h3>
							</div>
							<div class="panel-body">
								<p>
									1、特别提示
									本协议由财付通支付科技有限公司（为便于理解，下称“本公司”）为规范您（包括个人、个体工商户和企事业单位）使用微信支付商户平台（下称“本平台”）的行为而制定，具有合同法律效力。您应认真阅读并遵守本协议、《微信支付违规商户处理规则》和《微信公众平台运营规范》。请您务必审慎阅读、充分理解各条款内容，特别是免除或者限制责任的条款、争议解决和法律适用条款。免除或者限制责任的条款可能以加粗字体显示，您应重点阅读。除非您已阅读并接受本协议和《微信公众平台运营规范》所有条款，否则您无权使用本平台。您使用本平台即视为您已阅读并同意本协议和《微信公众平台运营规范》的约束。如您有任何疑问，应向本公司客服咨询。
									任何您之前与本公司签订的《微信支付服务协议》若与本协议有不一致之处，则不一致之处以本协议约定为准。
									2、定义
									如无特别说明，下列术语在本协议中的定义为：
									2.1 微信公众帐号：指您在微信公众平台注册的，用于登录微信公众平台的帐号（以下或称“公众帐号”）。
									2.2 微信支付服务：指本公司依托微信及微信公众平台为收付款人之间提供的货币资金转移服务。
									2.3 微信支付商户号：指本公司为您配置的用来记载您的身份信息、交易信息、资金余额，您凭以发起交易指令的电子簿记。微信支付商户号将与您提供的合法银行账户绑定，您的银行账户将根据您的交易指令和微信支付商户号的交易情况做相应的资金扣划和归集。
									2.4 微信支付商户平台：指本公司提供和维护的商户管理软件系统，您应通过该系统提交相应资料和信息，进而开通微信支付。成功开通后，您可以通过该系统使用微信支付商户号管理和营销推广等相关功能，具体功能以该系统实际提供的为准，且该系统将根据本公司的需要进行调整和增减。
									2.5 商户平台登录账号：指您凭以登录本平台，操作微信支付商户号的账号，您成功申请微信支付商户号后，本公司将向您发送电子邮件，告知您的登录账号及初始密码，您可在本平台中修改该初始密码，您亦可在本平台的“员工管理”模块中为该登录账号设置多个附属登录账号及对应的登录密码，并为附属登录账号配置相应的操作权限。（商户平台登录账号及其附属登录账号，以下统称“登录账号”）登录账号将与微信支付商户号关联，任何主体在成功登录本平台后，均可操作本平台功能和与其关联的微信支付商户号内的资金。
									3、您的权利和义务									
								</p>
							</div>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
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
        <script src="assets/js/waves.js"></script>
        <script src="assets/js/wow.min.js"></script>
        <script src="assets/js/jquery.nicescroll.js"></script>
        <script src="assets/js/jquery.scrollTo.min.js"></script>


        <script src="assets/js/jquery.core.js"></script>
        <script src="assets/js/jquery.app.js"></script>

		<script type="text/javascript" src="assets/plugins/parsleyjs/dist/parsley.min.js"></script>
        <script type="text/javascript" src="assets/plugins/parsleyjs/dist/i18n/zh_cn.js"></script>
        
        <script src="assets/plugins/notifyjs/dist/notify.min.js"></script>
        <script src="assets/plugins/notifications/notify-metro.js"></script>       
        
        <script src="js/common.js"></script>
		<script type="text/javascript" src="js/utils.js"></script>
		<script type="text/javascript">
			var imeilcheck = true;
			$(document).ready(function(){
				//检测邮箱是否已经被注册
				$("#inputEmail").blur(function() {
					if($("#inputEmail").parsley().isValid()){
						//如果校验通过，进行请求查重
						var imeil = $("#inputEmail").val().trim();
						var url = "/service/platform/login/user/user/checkRepeatImeilUser";
						var params = {
							imeil:imeil
						};
						jsonrpc(url, params, blurExecSuccess, blurExecFailed);							
					}
				});				
				function blurExecSuccess(data){
					imeilcheck = true;
					$("#info1").html(data.message);
					$("#info1").css("color", "green");
					$("#info1").removeClass("hidden");
				}				
				function blurExecFailed(message){
					imeilcheck = false;//说明邮箱当前不可用
					$("#info1").html(message);
					$("#info1").css("color", "red");
					$("#info1").removeClass("hidden");
				}
				
				//点击注册按钮				
				$("#registerBtn").click(function(){
					var checkImeil = $("#inputEmail").parsley();	
					var nickname = $("#nickname").parsley();
					var checkHoriPass1 = $("#hori-pass1").parsley();
					var checkHoriPass2 = $("#hori-pass2").parsley();
					checkImeil.validate();//根据判断结果显示校验UI
					nickname.validate();
					checkHoriPass1.validate();
					checkHoriPass2.validate();
					if(checkImeil.isValid() && nickname.isValid() && checkHoriPass1.isValid() && checkHoriPass2.isValid()){
						//如果全部校验成功，则执行ajax请求注册
						var imeil = $("#inputEmail").val().trim();
						var nickname = $("#nickname").val().trim();
						var password = $("#hori-pass1").val().trim();						
						var params = {
							imeil: imeil,
							nickname:nickname,
							password: password
						};
						var url = "/service/platform/login/user/user/imeilRegister";
						jsonrpc(url, params, execSuccess, execFailed);						
					}					
					event.preventDefault();//取消提交表单默认动作
				});
				function execSuccess(data){
					$.Notification.notify('success','top left','恭喜您，注册成功', '请使用注册用户名（邮箱）进行登录，2秒后自动跳转');
					setTimeout(function(){
						window.location.href = "<%=basePath%>qingpudig/login";								
					}, 2000);
				}
				function execFailed(message){
					alert(message);
				}
			});
		</script>
		
	</body>
</html>