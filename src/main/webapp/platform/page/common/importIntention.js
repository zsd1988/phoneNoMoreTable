//@ sourceURL=importIntention.js
function init_common_importIntention(){
    var $prologueList = $("#prologueList")
    var _userData = null

    init = function () {
        GlobalData.initPageJs();
        var url = "/service/platform/login/user/user/getLeaderUsers";
        getDataByURL(url, {userId:GlobalData.userId}, function (data) {
            var dataJson = data.data
            _userData = data.data
            $prologueList.empty();
            var json={
                data:dataJson
            }
            $prologueList.setTemplateURL("tpl/importIntentionItem.html");
            $prologueList.processTemplate(json);
        })
    }


    saveImportIntention = function() {
        var url = "/service/platform/login/user/jiangHuRen/importIntention";
        var userIds = "";
        var intentions = "";
        var finishes = "";
        for(var i = 1; i < _userData.length + 1; i++){
            var intention = $("#intention" + i).val()
            var finish = $("#finish" + i).val()
            if(intention == ""){
                intention = 0;
            }
            if(finish == 0){
                finish = 0;
            }
            if(intention != 0 || finish != 0){
                userIds += _userData[i-1].id + ","
                intentions += intention + ","
                finishes += finish + ","
            }
        }
        getDataByURL(url, {userIds:userIds, intentions:intentions, finishes:finishes}, function (data) {
            boldFunc.notification("录入成功")
        })
    }

    init();

}








