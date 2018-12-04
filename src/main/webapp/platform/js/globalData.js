var GlobalData = {
    imageUrl: null,
    fileUrl:null,
    beforePageUrl:null,     // 带参数的页面地址
    videoUrl: null,
    roleId: null,       // 角色id
    isSupportIP:true,   //是否支持通过ip设置分机号
    currentView:null, // 当前页面
    beforeView:null, // 当前页面
    currentJson:null,
    basePath:null,      // 网站域名及端口
    userId:null,    // 当前登录用户id
    workNumber:null,    //工号
    extNum:null,    //分机号
    userName:null,
    permissionsTags:null,   //菜单权限类型
    functionIdArr:[],
    screenTabIndex:1,
    floorAllTabIndex:0,
    localGotoPage:"QPBusinessLocalGotoPage",
    localGotoData:"QPBusinessLocalGotoData",
    isInLogPage:false,
    volume:0.5,
    thisFolder:null,                //菜单元素
    clickFolderId:null,            //菜单选择的ID
    permissionsTag:null,        // 菜单类型
    thisSource:null,
    callRecord:{
        showIndex:0,
        clientMap:null,
        minMap:null,
        enterTimes:0,   // 进入通话记录页面的次数
    },
    thisFolderType:1,       //1：创建文件夹  2：修改文件夹
    returnMap:{},           //return 数据
    returnId:0,             //renrunmap的KEY
    isBack:false,           //是否是返回按钮
    hintDes:"先生，女士，是否了解项目位置，关注面积，价格，位置，考虑 平的商铺，资金预算 ，投资目的，是否看过商铺，同意有空过来看看，同意置业顾问再次联系。",
    projectClientOptions:null,     //业态
    roleSuperId:"2b9c6fa88e7b495e81deef4146f130b1",
    roleManager:"f8029f72a77a5713b03cc820d0a0a8a3",
    roleCS:"0fc02e24b67949dbb14843fe1916cb01",
    roleReview:"cd90132af26d44c3adace2f9270ee247",
    roleInterview:"",
    isTeamLeader:false, // 组长
    isCS:false, // 客服
    isReviewer:false,   // 质检
    isAdminOrReviewer:false,    //质检或管理员
    isInterview:false,  // 约访
    isManager:false,    // 管理员

/*    //设置map里面的值
    setReturnMap:( function (id,newsObj) {
        //如果key也是动态的，则如下处理
        var key=id;
        returnMap[key]=newsObj;
    })(),*/


    // 判断浏览器是否支持图片的base64
    isSupportBase64 : ( function() {
        var data = new Image();
        var support = true;
        data.onload = data.onerror = function() {
            if( this.width != 1 || this.height != 1 ) {
                support = false;
            }
        }
        data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
        return support;
    } )(),
    supportTransition : (function(){
        var s = document.createElement('p').style,
            r = 'transition' in s ||
                'WebkitTransition' in s ||
                'MozTransition' in s ||
                'msTransition' in s ||
                'OTransition' in s;
        s = null;
        return r;
    })(),    
    initPageJs:function () {
        loadApi.loadBackJS('assets/plugins/parsleyjs/dist/parsley.min.js',function(){
            loadApi.loadBackJS('assets/plugins/parsleyjs/dist/i18n/zh_cn.js',function(){}, true);
            loadApi.loadBackJS('assets/plugins/parsleyjs/extra/validator/comparison.js',function(){}, true);
        }, true);
        loadApi.loadBackJS('assets/plugins/dropzone/dist/dropzone.js',function(){}, true);
    },
}