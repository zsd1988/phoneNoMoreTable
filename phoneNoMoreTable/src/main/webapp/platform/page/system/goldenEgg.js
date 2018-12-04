//# sourceURL=goldenEgg.js
function init_system_goldenEgg() {
    var _dataJson = null;
    var _$selectElm = null
    var _interval = null

    init = function () {
        var $image = $("#image")
        var imageHeight = $image.height();
        getDataByURL("/service/platform/login/systemManager/dayHint/getGoldenEggHint", null, function (data) {
            var dataJson = data.data
            if(StringUtil.isNotEmpty(dataJson.content)){
                $("#goldenHint").text(dataJson.content)
                $("#goldenHint").css("top", -imageHeight + imageHeight * 0.365)
            }
        })


        var isEggSaveBtnClick = false;
        $(".danbox li").mouseenter(function () {
            if(! isEggSaveBtnClick){
                $(".p2-chui").hide();
                $(".p2-ps").hide();
                $(".p2-egg").removeClass("p2-egg-hover");
                $(this).find(".p2-egg").addClass("p2-egg-hover");
                $(this).find(".p2-chui").show();
                $(this).find(".p2-ps").show();
                _$selectElm = $($(this).find(".p2-chui").find("img")[0])
            }
        });

        $(".danbox li").click(function () {
            isEggSaveBtnClick = true;
            setTimeout(function() {
                isEggSaveBtnClick = false;
            }, 1000);
            $(this).find(".p2-egg").remove();
            $(this).find(".p2-chui").hide();
            $(this).find(".p2-ps").remove();
            $(this).find(".p2-hit").show();
            $(this).find(".p2-finish").show();
            var $this = $(this)
            setTimeout(function () {
                $this.find(".p2-hit").remove();
                $this.find(".p2-chui").show();
            }, 200)
            getDataByURL("/service/platform/login/systemManager/intentionHintRewardRecord/createForGoldenType", null, function (data) {
                var dataJson = data.data
                _dataJson = dataJson
                var color = "#505683"
                var color2 = "#6d84b3"
                if( dataJson.isWin){
                    color = "#ff2562"
                    color2 = "#fd7a99"
                    $("#bg").css("background-image", 'url("img/beijingkuang.png")')
                    $("#fix2").css("top", "425px")
                    $("#fix").css("top", "375px")
                    $("#dayHint").css("top", "470px")
                    $("#winDai").show()
                    $("#kuang").attr("src", "img/egg/day/biankuang1.png")
                }else{
                    $("#bg").css("background-image", 'url("img/beijingdi.png")')
                    $("#kuang").attr("src", "img/egg/day/biankuang2.png")
                }
                $("#answer").css("color", color)
                $("#fix").css("color", color2)
                $("#fix2").css("color", color2)
                $("#dayHint").css("color", color2)
                $("#dayCount").css("color", color2)
                var ipIndex = Math.floor(Math.random()*2);
                $("#dayCount").text("     " + dataJson.dayCount + "     ")
                if(ipIndex == 0){
                    $("#ip").css("top", "445px")
                    $("#ip").attr("src", "img/IP.png")
                }else{
                    $("#ip").css("top", "507px")
                    $("#ip").attr("src", "img/IPWin.png")
                }
                $("#name").text(dataJson.name)
                $("#nickName").text(dataJson.nickName)
                if(StringUtil.isNotEmpty(dataJson.headImage)){
                    $("#headImage").attr("src", GlobalData.fileUrl + dataJson.headImage)
                }
                if(StringUtil.isNotEmpty(dataJson.result)){
                    $("#answer").text(dataJson.result)
                }
                var dayImage = Math.floor(Math.random()*20) + 1;
                if(dayImage < 10){
                    dayImage = "0" + dayImage
                }
                $("#image").attr("src", "img/egg/day/0" + dayImage + ".png")
                $("#dayHint").text(dataJson.dayHint)
                $("#modal").modal('show')
            })
        });


    }


    closeEgg = function () {
        $("#modal").modal("hide")
        if(StringUtil.isNotEmpty(_dataJson.stageHint)){
            if(StringUtil.isNotEmpty(_dataJson.headImage)){
                $("#headImageStage").attr("src", GlobalData.fileUrl + _dataJson.headImage)
            }
            $("#nickNameStage").text(_dataJson.nickName)
            $("#nameStage").text(_dataJson.name)
            $("#stage").text(_dataJson.stageHint)
            $("#dayHintStage").text(_dataJson.stageDayHint)
            $("#stageModal").modal('show')
        }else if(StringUtil.isNotEmpty(_dataJson.birthdayHint)){
            showBirthDay()
        }else{
            $("#contentDiv").hide()
        }
    }

    closeStage = function () {
        $("#stageModal").modal("hide")
        if(StringUtil.isNotEmpty(_dataJson.birthdayHint)){
            showBirthDay()
        }else{
            $("#contentDiv").hide()
        }
    }

    closeBirthday = function () {
        $("#birthDayModal").modal("hide")
        $("#contentDiv").hide()
    }

    showBirthDay = function () {
        if(StringUtil.isNotEmpty(_dataJson.headImage)){
            $("#headImageBirthday").attr("src", GlobalData.fileUrl + _dataJson.headImage)
        }
        $("#nickNameBirthday").text(_dataJson.nickName)
        $("#nameBirthday").text(_dataJson.name)
        $("#dayHintBirthday").text(_dataJson.birthdayHint)
        $("#birthDayModal").modal('show')
    }

    init();

}



/*!
 * preLoad.js v1.0
 * (c) 2017 Meng Fangui
 * Released under the MIT License.
 */
(function ($) {
    function preLoad(imgs, options) {
        //传入imgs参数是图片 还是 数组
        this.imgs = (typeof imgs === 'string') ? [imgs] : imgs;
        //处理传入参数
        this.opts = $.extend({}, preLoad.DEFAULTS, options);
        //有序加载
        if(this.opts.order === 'ordered'){
            this._ordered();
        }else{
            //无序加载
            this._unordered();
        }
    }

    preLoad.DEFAULTS = {
        order:'unordered',//默认值：无顺预加载
        each: null,  // 每一张图片加载完毕后执行
        all: null,   // 所有图片加载完后执行
    }
    preLoad.prototype._ordered = function(){
        var opts = this.opts,
            imgs = this.imgs,
            len = imgs.length,
            count = 0;
        load();
        //有序预加载
        function load(){
            //实例化Image对象
            var imgObj = new Image();
            //监听load和error事件
            $(imgObj).on('load error',function(){
                //每加载一张图片触发的事件
                opts.each && opts.each(count);
                if (count >= len) {
                    //所有的图片已经加载完 触发的事件
                    opts.all && opts.all();
                } else{
                    load();
                }
                count++;
            });
            //图片路径赋值
            imgObj.src = imgs[count];
        }
    };
    preLoad.prototype._unordered = function () {
        //无序加载
        var imgs = this.imgs,
            opts = this.opts,
            count = 0,
            len = imgs.length;

        $.each(imgs, function (i, src) {
            //判断图片路径是否是字符串
            if (typeof src != 'string') {
                return;
            }
            //实例化Image对象
            var imgObj = new Image();
            //监听load和error事件
            $(imgObj).on('load error', function () {
                //每加载一张图片触发的事件
                opts.each && opts.each(count);
                if (count >= len - 1) {
                    //所有的图片已经加载完 触发的事件
                    opts.all && opts.all();
                }
                count++;
            });
            //给图片赋值路径
            imgObj.src = src;
        });
    };
    $.extend({
        preload: function (imgs, opts) {
            new preLoad(imgs, opts);
        }
    });
})(jQuery);




