package com.qingpu.phone.common.entity;

import com.qingpu.phone.common.utils.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class PublicEnum {


    /**
     * 获取枚举值
     * @return
     */
    public static String getEnumKeyValueString(String type){
        Map<String, String> map = new LinkedHashMap<>();
        switch (type){
            case "ImportTag":
                for(PublicEnum.ImportTag importTag : PublicEnum.ImportTag.values()){
                    map.put(importTag.getType(), importTag.getName());
                }
                break;
            case "RoleType":
                for(PublicEnum.RoleType importTag : PublicEnum.RoleType.values()){
                    map.put(importTag.getType(), importTag.getName());
                }
                break;
            case "PostMessageType":
                for(PublicEnum.PostMessageType importTag : PublicEnum.PostMessageType.values()){
                    map.put(importTag.getType(), importTag.getName());
                }
                break;
            case "VoiceType":
                for(PublicEnum.VoiceType voiceType : PublicEnum.VoiceType.values()){
                    map.put(voiceType.getType(), voiceType.getName());
                }
                break;
            case "DayHintType":
                for(PublicEnum.DayHintType voiceType : PublicEnum.DayHintType.values()){
                    map.put(voiceType.getType(), voiceType.getName());
                }
                break;
            case "IntentionHintType":
                for(PublicEnum.IntentionHintType workType : PublicEnum.IntentionHintType.values()){
                    map.put(workType.getType(), workType.getName());
                }
                break;
            case "WorkType":
                for(PublicEnum.WorkType workType : PublicEnum.WorkType.values()){
                    map.put(workType.getType(), workType.getName());
                }
                break;
            case "SourceType":
                for(PublicEnum.SourceType sourceType : PublicEnum.SourceType.values()){
                    map.put(sourceType.getType(), sourceType.getName());
                }
                break;
            case "YearTag":
                for(PublicEnum.YearTag yearTag : PublicEnum.YearTag.values()){
                    map.put(yearTag.getType(), yearTag.getName());
                }
                break;
            case "Area":
                for(PublicEnum.Area area : PublicEnum.Area.values()){
                    map.put(area.getType(), area.getName());
                }
                break;
            case "ClientStatus":
                for(PublicEnum.ClientStatus clientStatus : PublicEnum.ClientStatus.values()){
                    map.put(clientStatus.getType(), clientStatus.getName());
                }
                break;
            case "ClientGroupType":
                for(PublicEnum.ClientGroupType clientGroupType : PublicEnum.ClientGroupType.values()){
                    map.put(clientGroupType.getType(), clientGroupType.getName());
                }
                break;
            case "OsType":
                for(PublicEnum.OsType osType : PublicEnum.OsType.values()){
                    map.put(osType.getType(), osType.getName());
                }
                break;
        }
        return JsonUtil.getJsonStrFromEntity(map);
    }

    public enum SourceType {
        Image("图片"),
        Zip("压缩包"),
        Office("办公文档"), //word，excel
        Video("视频");
        private String name;
        SourceType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    /**
     * 客户端类型
     */
    public enum OsType {
        PC("电脑"),
        Android("安卓"),
        IOS("苹果");
        private String name;
        OsType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }


    public enum PortStatus {
        Idle("空闲"),
        InCall("呼叫中"),
        Calling("通话中");
        private String name;
        PortStatus(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }


    public enum VoiceType {
        CS("客服"),
        Middle("机器转客服"),
        Robot("机器人");
        private String name;
        VoiceType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum GroupCallDetailStatus {
        ResetWaiting("重置为待呼"),
        Waiting("待呼"),
        Calling("呼叫中"),
        CallUserBusy("占线"),
        CallNoAnswer("未接听"),
        CallEmpty("空号"),
        CallReject("拒接"),
        Success("呼叫成功"),
        TransSuccess("转人工成功"),
        Refuse("客户拒绝"),
        TransFail("转人工失败");
        private String name;
        GroupCallDetailStatus(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum Area {
        WeiZhi("未知"),
        YuHua("雨花区"),
        TianXin("天心区"),
        FuRong("芙蓉区"),
        KaiFu("开福区"),
        YueLu("岳麓区"),
        WangCheng("望城区"),
        CSX("长沙县"),
        LiuYang("浏阳市"),
        NingXiang("宁乡县"),
        CSS("长沙市"),
        ZhuZhou("株洲市"),
        XiangTan("湘潭市"),
        HuNan("湖南省内其它市"),
        NoHuNan("湖南省外");
        private String name;
        Area(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum YearTag {
        Normal("正常"),
        Old("老年"),
        Stu("学生");
        private String name;
        YearTag(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum ImportTag {
        A("A类"),
        B("B类"),
        C("C类"),
        D("D类"),
        E("E类");
        private String name;
        ImportTag(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum ClientStatus {
        No("没意向"),
        NoReject("没意向-直接拒绝"),
        CallBack("稍后回拨"),
        Blur("模糊意向"),
        C("C类意向"),
        B("B类意向"),
        A("A类意向"),
        Waiting("待申诉"),
        Referrals("转介"),
        Merchants("招商"),
        House("住宅"),
        Office("写字楼"),
        Shop("低总价商铺"),
        Flats("公寓"),
        NoOne("无人接听"),
        Refuse("拒接"),
        Downtime("停机"),
        Shutdown("关机"),
        Calling("通话中"),
        UnConnect("无法接通"),
        Paused("暂停服务"),
        Null("空号"),
        Visit("已来访"),
        Finish("已成交");
        private String name;
        ClientStatus(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }
	
	public enum ClientGroupType {
        Visiting("陌拜资源"),
        Vague("模糊资源"),
        Customer("准客户资源"),
        Follow("跟进客户"),
        Attract("招商资源");
        private String name;
        ClientGroupType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum RoleType {
        CS("客服"),
        Interview("约访"),
        Quality("质检"),
        Manager("管理员"),
        All("全员");
        private String name;
        RoleType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

	public enum WorkType {
        FullTime("全职"),
        PartTime("兼职");
        private String name;
        WorkType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum PostMessageType {
        Admin("管理员"),
        Birthday("生日"),
        FirstDay("第一天"),
        WeekDay("满一周"),
        MonthDay("满一个月"),
        YearDay("满一年"),
        SecondYear("满二年"),
        ThirdYear("满三年"),
        FourthYear("满四年"),
        FifthYear("满五年"),
        SixthYear("满六年"),
        SeventhYear("满七年"),
        EighthYear("满八年"),
        NinthYear("满九年"),
        TenthYear("满十年"),
        System("系统");
        private String name;
        PostMessageType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }

    public enum IntentionHintType {
        GoldenEgg("砸金蛋"),
        Intention("意向");
        private String name;
        IntentionHintType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }


    public enum DayHintType {
        Day("每天"),
        Stage("阶段全员"),
        MyStage("阶段自己"),
        BirthDay("生日全员"),
        MyBirthDay("生日自己");
        private String name;
        DayHintType(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public String getType(){
            return this.toString();
        }
    }
}
