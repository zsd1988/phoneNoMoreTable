//@ sourceURL=projectEdit.js
function init_system_projectEdit(){
    var currentJson = GlobalData.currentJson;
    var currentJsonData = null;
    var isEdit = false;
    if(currentJson != null && currentJson.isEdit){
        isEdit = true;
        currentJsonData = currentJson.data;
    }
    var isDel = false;
    var prologueCount = 0;
    var prologueData = [];
    var $prologueList = $("#prologueList")


    init = function () {
        GlobalData.initPageJs();

        var title = "新增项目";
        if(isEdit){
            title = "修改项目";
            $("#projectName").val(currentJsonData.name);
            $("#managerName").val(currentJsonData.managerName)

            //获取项目开场白
            getDataByURL("/service/platform/login/systemManager/prologue/listByProjectId", {projectId:currentJsonData.id}, function (data) {
                var dataJson = data.data;
                var length = dataJson.length;
                if( length == 0){
                    addNewPrologueData();
                    initUpload(prologueData);
                }else{
                    for(var i = 0;i < length; i++){
                        var item = dataJson[i]
                        item.index = i + 1;
                    }
                    prologueCount = length;
                    initUpload(dataJson)
                }
            })
        }else{
            addPrologue();
        }
        $("#title").text(title);
    }

    addNewPrologueData = function () {
        var index = ++prologueCount;
        var prologueItem = {name:'',path1:'',path2:"",path3:"",rate:0, index:index}
        prologueData.push(prologueItem)
    }

    /**
     * 初始化上传组件
     * @param paramsData
     */
    initUpload = function (paramsData) {
        $prologueList.empty();
        var json={
            data:paramsData
        }
        $prologueList.setTemplateURL("tpl/prologueItem.html");
        $prologueList.processTemplate(json);
        // 注册文件上传控件
        for(var i = 1; i < prologueCount + 1; i++){
            for(var j = 1; j < 4; j++){
                var prologueItemDivFunc = UploadFunc.uploadInit({
                    id:"prologueDiv" + j +  i,
                    width:500,
                    height:25,
                    supportImage:false,
                    supportMp3:true,
                    addParams:j + "" + i,
                    callback:function (data) {
                        $("#prologuePath" + data.addParams).val(data.sourceImg)
                    }
                });
                var data = paramsData[i-1]
                var imagePath = null
                switch (j){
                    case 1:
                        imagePath = data.path1;
                        break;
                    case 2:
                        imagePath = data.path2;
                        break;
                    case 3:
                        imagePath = data.path3;
                        break
                }
                if(StringUtil.isNotEmpty(imagePath)){
                    data.sourceImg = imagePath;
                    prologueItemDivFunc.load(data, 'mp3')
                }
            }
        }
    }

    addPrologue = function () {
        try{
            var tempPrologueData = [];
            for(var i = 1; i < prologueCount + 1; i++){
                var name = $("#prologueName" + i).val()
                if(StringUtil.isEmpty(name)){
                    throw "请输入名称"
                }
                var rate = $("#prologueRate" + i).val()
                if(StringUtil.isEmpty(rate)){
                    throw "请输入概率"
                }
                var path1 = $("#prologuePath1" + i).val()
                if(StringUtil.isEmpty(path1)){
                    throw "请上传开场白"
                }
                var path2 = $("#prologuePath2" + i).val()
                if(StringUtil.isEmpty(path2)){
                    throw "请上传挽回录音"
                }
                var path3 = $("#prologuePath3" + i).val()
                if(StringUtil.isEmpty(path3)){
                    throw "请上传挂断录音"
                }
                var id = $("#prologueId" + i).val()
                var prologueItem = {name:name,rate:rate, path1:path1,path2:path2,path3:path3, index:i,id:id}
                tempPrologueData.push(prologueItem)
            }
            ArrayFunc.clear(prologueData);
            prologueData = tempPrologueData
            addNewPrologueData();
            initUpload(prologueData)
        }catch (err){
            boldFunc.showErrorMes(err)
        }
    }

    saveProject = function() {
        ParsleyFunc.checkForm($("#form"), function () {
            var params = $("#form").serializeHumpObject();
            var url = "/service/platform/login/systemManager/Project/create";
            if(isEdit){
                url = "/service/platform/login/systemManager/Project/update";
            }
            if(StringUtil.isEmpty(params.name)){
                boldFunc.showErrorMes("请输入项目名称");
                return;
            }
            var prologueList = [];
            for(var i = 1; i < prologueCount + 1; i++){
                var name = $("#prologueName" + i).val()
                var path1 = $("#prologuePath1" + i).val()
                var path2 = $("#prologuePath2" + i).val()
                var path3 = $("#prologuePath3" + i).val()
                if(StringUtil.isNotEmpty(name) && StringUtil.isNotEmpty(path1) && StringUtil.isNotEmpty(path2) && StringUtil.isNotEmpty(path3)){
                    var prologueItem = {
                        name:name,
                        id:$("#prologueId" + i).val(),
                        rate:$("#prologueRate" + i).val(),
                        path1:path1,
                        path2:path2,
                        path3:path3,
                    };
                    prologueList.push(prologueItem)
                }
            }
            params.prologueJsonArray = JSON.stringify(prologueList);
            getDataByURL(url, params, function () {
                boldFunc.notification("操作成功");
                goBack();
            })
        })
    }

    init();

}








