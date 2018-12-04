package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="call_record")
public class CallRecord implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    @Index(name = "I_createTime")
    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Column(length = 32)
    String group_call_detail_id;	//群呼id

    @Column(length = 32)
    String client_id;		//客户id

    @Column(length = 32)
    @Index(name = "I_userId")
    String user_id;	//客服id

    @Column(length = 32)
    @Index(name = "I_projectId")
    String project_id;	//项目id

    @Index(name = "I_userName")
    String user_name;	//客服姓名

    @Index(name = "I_phone")
    String phone;   // 手机号

    private Integer ext_num;		//分机号

    @Index(name = "I_workNumber")
    private String  work_number;		//员工工号

    @Enumerated(value = EnumType.STRING)
    PublicEnum.WorkType work_type = PublicEnum.WorkType.FullTime;      // 默认全职

    @Column(length = 32)
    String review_id;	//质检id

    String review_work_number;	//质检工号

    String review_name;	//质检姓名

    @Index(name = "I_startTime")
    Date start_time;    //通话开始时间

    Date end_time;		//结束时间

    Integer time = 0;	//通话时长

    String record_path;	//录音地址

    String client_record_path;  //客户录音地址

    Boolean is_lock = false;    //是否锁定，根据第一次听录音文件来锁定

    Boolean is_set_new_intention = false;    // 设置为了新意向

    @Enumerated(value = EnumType.STRING)
    PublicEnum.VoiceType voice_type = PublicEnum.VoiceType.CS;      // 语音类型

    @Transient
    String clientName;

    @Transient
    String clientPhone;

    @Transient
    String clientReviewStatus;

    @Transient
    String clientConfirmStatus;

    @Transient
    String clientStatus;

    @Transient
    String clientDes;

    @Transient
    String groupId;

    @Transient
    String groupType;

    @Transient
    Boolean isAppeal;

    //统计报表
    @Transient
    BigInteger sumTotal;     //通话数量
    @Transient
    BigDecimal sumYx;     //意向数量
    @Transient
    BigDecimal sumSs;     //实时数量
    @Transient
    BigDecimal sumA;     //A类意向数量
    @Transient
    BigDecimal sumB;     //B类意向数量
    @Transient
    BigDecimal sumC;     //C类意向数量
    @Transient
    BigDecimal sumWaiting;     //待申诉数量
    @Transient
    BigDecimal sumBlur;     //模糊意向数量
    @Transient
    BigDecimal sumMerchants;     //招商数量
    @Transient
    BigDecimal sumReferrals;     //转介数量
    @Transient
    BigDecimal sumShop;     //低总价商铺数量
    @Transient
    BigDecimal sumHouse;     //住宅数量
    @Transient
    BigDecimal sumOffice;     //写字楼数量
    @Transient
    BigDecimal sumFlats;     //公寓数量
    @Transient
    BigDecimal sumNo;     //没意向数量
    @Transient
    String efficiency;  //效率
    @Transient
    BigDecimal salary;     //提成
    @Transient
    BigDecimal Yx2Yx;     //意向转意向数量
    @Transient
    BigDecimal Mh2Yx;     //模糊转意向数量
    @Transient
    BigDecimal Mb2Yx;     //陌拜转意向数量

    //--统计报表字段--end

    public Boolean getIsAppeal() {
        return isAppeal;
    }

    public void setIsAppeal(Boolean appeal) {
        isAppeal = appeal;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getClientConfirmStatus() {
        return clientConfirmStatus;
    }

    public void setClientConfirmStatus(String clientConfirmStatus) {
        this.clientConfirmStatus = clientConfirmStatus;
    }

    public Boolean getIsSetNewIntention() {
        return is_set_new_intention;
    }

    public void setIsSetNewIntention(Boolean is_set_new_intention) {
        this.is_set_new_intention = is_set_new_intention;
    }

    public BigDecimal getYx2Yx() { return Yx2Yx; }

    public void setYx2Yx(BigDecimal yx2Yx) { Yx2Yx = yx2Yx; }

    public BigDecimal getMh2Yx() { return Mh2Yx; }

    public void setMh2Yx(BigDecimal mh2Yx) { Mh2Yx = mh2Yx; }

    public BigDecimal getMb2Yx() { return Mb2Yx; }

    public void setMb2Yx(BigDecimal mb2Yx) { Mb2Yx = mb2Yx; }

    public BigDecimal getSalary() { return salary; }

    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public PublicEnum.VoiceType getVoiceType() {
        return voice_type;
    }

    public void setVoiceType(PublicEnum.VoiceType voice_type) {
        this.voice_type = voice_type;
    }

    public BigDecimal getSumWaiting() {
        return sumWaiting;
    }

    public void setSumWaiting(BigDecimal sumWaiting) {
        this.sumWaiting = sumWaiting;
    }

    public String getClientRecordPath() {
        return client_record_path;
    }

    public void setClientRecordPath(String client_record_path) {
        this.client_record_path = client_record_path;
    }

    public Integer getExtNum() {
        return ext_num;
    }

    public void setExtNum(Integer ext_num) {
        this.ext_num = ext_num;
    }

    public BigInteger getSumTotal() { return sumTotal; }

    public void setSumTotal(BigInteger sumTotal) { this.sumTotal = sumTotal; }

    public BigDecimal getSumYx() { return sumYx; }

    public void setSumYx(BigDecimal sumYx) { this.sumYx = sumYx; }

    public BigDecimal getSumSs() { return sumSs; }

    public void setSumSs(BigDecimal sumSs) { this.sumSs = sumSs; }

    public BigDecimal getSumA() { return sumA; }

    public void setSumA(BigDecimal sumA) { this.sumA = sumA; }

    public BigDecimal getSumB() { return sumB; }

    public void setSumB(BigDecimal sumB) { this.sumB = sumB; }

    public BigDecimal getSumC() { return sumC; }

    public void setSumC(BigDecimal sumC) { this.sumC = sumC; }

    public BigDecimal getSumBlur() { return sumBlur; }

    public void setSumBlur(BigDecimal sumBlur) { this.sumBlur = sumBlur; }

    public BigDecimal getSumMerchants() { return sumMerchants; }

    public void setSumMerchants(BigDecimal sumMerchants) { this.sumMerchants = sumMerchants; }

    public BigDecimal getSumReferrals() { return sumReferrals; }

    public void setSumReferrals(BigDecimal sumReferrals) { this.sumReferrals = sumReferrals; }

    public BigDecimal getSumShop() { return sumShop; }

    public void setSumShop(BigDecimal sumShop) { this.sumShop = sumShop; }

    public BigDecimal getSumHouse() { return sumHouse; }

    public void setSumHouse(BigDecimal sumHouse) { this.sumHouse = sumHouse; }

    public BigDecimal getSumOffice() { return sumOffice; }

    public void setSumOffice(BigDecimal sumOffice) { this.sumOffice = sumOffice; }

    public BigDecimal getSumFlats() { return sumFlats; }

    public void setSumFlats(BigDecimal sumFlats) { this.sumFlats = sumFlats; }

    public BigDecimal getSumNo() { return sumNo; }

    public void setSumNo(BigDecimal sumNo) { this.sumNo = sumNo; }

    public String getEfficiency() { return efficiency; }

    public void setEfficiency(String efficiency) { this.efficiency = efficiency; }

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
    }

    public String getPhone() {
        return phone;
    }

    public String getReviewWorkNumber() {
        return review_work_number;
    }

    public void setReviewWorkNumber(String review_work_number) {
        this.review_work_number = review_work_number;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartTime() {
        return start_time;
    }

    public String getWorkNumber() {
        return work_number;
    }

    public void setWorkNumber(String work_number) {
        this.work_number = work_number;
    }

    public void setStartTime(Date start_time) {
        this.start_time = start_time;
    }

    public String getGroupId() {
        return groupId;
    }

    public PublicEnum.WorkType getWorkType() {
        return work_type;
    }

    public void setWorkType(PublicEnum.WorkType work_type) {
        this.work_type = work_type;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Boolean getIsLock() {

        return is_lock;
    }

    public void setIsLock(Boolean is_lock) {
        this.is_lock = is_lock;
    }

    public Integer getOperatorId() {
        return operator_id;
    }

    public void setOperatorId(Integer operator_id) {
        this.operator_id = operator_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public Boolean getIsDel() {
        return is_del;
    }

    public void setIsDel(Boolean is_del) {
        this.is_del = is_del;
    }

    public String getGroupCallDetailId() {
        return group_call_detail_id;
    }

    public void setGroupCallDetailId(String group_call_detail_id) {
        this.group_call_detail_id = group_call_detail_id;
    }

    public String getClientId() {
        return client_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getReviewId() {
        return review_id;
    }

    public String getProjectId() {
        return project_id;
    }

    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientReviewStatus() {
        return clientReviewStatus;
    }

    public void setClientReviewStatus(String clientReviewStatus) {
        this.clientReviewStatus = clientReviewStatus;
    }

    public String getClientDes() {
        return clientDes;
    }

    public void setClientDes(String clientDes) {
        this.clientDes = clientDes;
    }

    public void setReviewId(String review_id) {
        this.review_id = review_id;
    }

    public String getReviewName() {
        return review_name;
    }

    public void setReviewName(String review_name) {
        this.review_name = review_name;
    }

    public Date getEndTime() {
        return end_time;
    }

    public void setEndTime(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getRecordPath() {
        return record_path;
    }

    public void setRecordPath(String record_path) {
        this.record_path = record_path;
    }
}
