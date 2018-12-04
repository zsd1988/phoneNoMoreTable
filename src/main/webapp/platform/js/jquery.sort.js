/**
 * jquery.sort.js
**/
/*定义三级分类数据*/
//一级分类
$(document).ready(function(){
	saveObjectData("selectedScreen", null);
	
	$("#selectedScreens").on("itemRemoved", function(event){
		console.log(event.item);
		var code = selectedMap.get(event.item);		
		//console.log("before = " + selectedArr.join(";"));
		selectedArr.remove(code);		
		//console.log("after = " + selectedArr.join(";"));
		saveObjectData("selectedScreen", selectedArr);
	});
})
var province = ["三层", "二层", "一层", "-1层"];
//二级分类
var city = [
	["主题超市", "主题餐厅", "伍捌市集"],
	["母婴用品", "孕婴护理", "美容养生", "特色餐饮"],
	["零售", "生活配套", "西式快餐"],
	["亲子零售", "休闲餐厅", "主题餐饮", "特色小吃"]
];
//三级分类
var district = new Array();
var district2 = new Array();
var expressP, expressC, expressD, expressArea, areaCont;
var arrow = " <font>&gt;</font> ";

/*初始化一级目录*/
function intProvince() {
	areaCont = "";
	for (var i=0; i<province.length; i++) {
		areaCont += '<li onClick="selectP(' + i + ');"><a href="javascript:void(0)">' + province[i] + '</a></li>';
	}
	$("#sort1").html(areaCont);
	//初始化全局多维数组
	for(var i = 0; i < 10; i++){
		district[i] = new Array();
		district2[i] = new Array();
		for(var j = 0; j < 10; j++){
			district[i][j] = new Array();
			district2[i][j] = new Array();
			for(var k = 0; k < 20; k++){
				district[i][j][k] = '';
				district2[i][j][k] = '';
			}
		}
	}
}
intProvince();

/*选择一级目录*/
function selectP(p) {
	areaCont = "";
	for (var j=0; j<city[p].length; j++) {
		areaCont += '<li onClick="selectC(' + p + ',' + j + ');"><a href="javascript:void(0)">' + city[p][j] + '</a></li>';
	}
	$("#sort2").html(areaCont).show();
	$("#sort3").hide();
	$("#sort1 li").eq(p).addClass("active").siblings("li").removeClass("active");
	expressP = province[p];
	$("#selectedSort").html(expressP);
	$("#releaseBtn").removeAttr("disabled");
}

/*选择二级目录*/
function selectC(p,c) {
	areaCont = "";
	expressC = "";
	$.ajax({
		type: "POST",
		url: "/screendevice/getAllScreenDevice.do",
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		data: {},
		success: function(data) {
			var length = data.length;
			for(var i = 0; i < length; i++) {
				var deviceType, online;
				if(data[i].deviceType == 1) {
					deviceType = "触摸屏";
				} else if(data[i].deviceType == 0) {
					deviceType = "大显示屏";
				} else if(data[i].deviceType == 2) {
					deviceType = "装饰屏";
				}
				if(data[i].online == true) {
					online = i + "号屏幕：在线";
				} else {
					online = i + "号屏幕：离线";
				}
				district[p][c][i] = online;
				district2[p][c][i] = online+";"+deviceType+";"+"东南角"+";"+data[i].anthenticationCode;
			}
			for (var k=0; k<district[p][c].length; k++) {
				var tt = district2[p][c][k].split(";")[3];
				if(tt != undefined)
					areaCont += '<li onClick="selectD(' + p + ',' + c + ',' + k + ',' + '\'' + tt + '\'' + ');"><a href="javascript:void(0)">' + district[p][c][k] + '</a></li>';
			}
			$("#sort3").html(areaCont).show();
			$("#sort2 li").eq(c).addClass("active").siblings("li").removeClass("active");
			expressC = expressP + arrow + city[p][c];
			$("#selectedSort").html(expressC);
		},
		error: function(XMLHttpResponse, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

/*选择三级目录*/
var selectedArr = new Array();
var selectedMap = new Map();
function selectD(p,c,d,tt) {
	//填充第四级内容
	var split =  district2[p][c][d].split(";");
	var details = "编号：" + split[0].split("：")[0] + "<br/>"+
					"状态：" + split[0].split("：")[1] + "<br/>"+
					"类型：" + split[1] + "<br/>"+
					"位置：" + split[2];	
	$("#sort4").html(details).show();
	//taginput显示选中的屏幕
	var tagValue = $("#selectedScreens").val();
	if(tagValue == "" || tagValue==null){
		//显示taginput插件
		$("#taginputId").show();
		$("#selectedScreens").tagsinput("add", split[0].split("：")[0]);
	}else{
		//在原有内容的基础上进行累加
		$("#selectedScreens").tagsinput("add", split[0].split("：")[0]);
	}
	
	selectedArr.push(tt);
	selectedArr = selectedArr.delRepeat();
	saveObjectData("selectedScreen", selectedArr);//去掉重复的值再存储
	selectedMap.put(split[0].split("：")[0], tt);//主要用于从框中删除屏幕值的改变
	
	$("#sort3 li").eq(d).addClass("active").siblings("li").removeClass("active");
	expressD = expressC + arrow + district[p][c][d].split("：")[0];
	$("#selectedSort").html(expressD);
}

Array.prototype.delRepeat=function(){ 
	var newArray=[]; 
	var provisionalTable = {}; 
	for (var i = 0, item; (item= this[i]) != null; i++) { 
		if (!provisionalTable[item]) { 
			newArray.push(item); 
			provisionalTable[item] = true; 
		} 
	} 
	return newArray; 
}
