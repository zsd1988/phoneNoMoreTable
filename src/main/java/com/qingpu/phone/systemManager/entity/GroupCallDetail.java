package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.PublicEnum.GroupCallDetailStatus;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="group_call_detail")
public class GroupCallDetail implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;     // 解析freeswitch数据异常

    private Date del_time;

    @Column(length = 32)
    @Index(name = "I_projectId")
    String project_id;  //项目id

    @Index(name = "I_groupCallId")
    @Column(length = 32)
    String group_call_id;	//群呼id

    @Column(length = 32)
    String client_id;	//客户id

    String phone;	//手机号

    @Index(name = "I_status")
    @Enumerated(value = EnumType.STRING)
    GroupCallDetailStatus status = GroupCallDetailStatus.Waiting;

    @Index(name = "I_startTime")
    Date start_time;	//开始时间

    Date end_time;		//结束时间

    Integer time;		//时长

    Integer ext_num;		//分机号

    String port_id;	//呼出端口

    @Index(name = "I_firstText")
    String first_text;  // 第一次识别的文字

    @Index(name = "I_refuseRecordPath")
    String refuse_record_path;     //拒绝录音地址

    String similarity_str;      // 解析结果

    String similarity_float;    //解析概率

    String resolve_str;    //解析配对文本

    String part_record1;

    String part_record2;

    String prologue_name;

    String tmp_record_name;     // 临时录音文件名

    Integer reset_waiting_count = 0;    // 重置再次拨打次数

    @Index(name = "I_voiceType")
    @Enumerated(value = EnumType.STRING)
    PublicEnum.VoiceType voice_type = PublicEnum.VoiceType.CS;      // 语音类型

    @Transient
    PublicEnum.ImportTag import_tag; //导入标签

    //统计端口接通率
    @Transient
    BigDecimal sumTotal;     //呼叫总数量

    @Transient
    BigDecimal sumSuccess;     //呼叫成功数量

    @Transient
    BigDecimal sumFail;     //呼叫失败数量

    @Transient
    String portRate;     //接通率

    @Transient
    Integer countNum;     //

    public String getPrologueName() {
        return prologue_name;
    }

    public void setPrologueName(String prologue_name) {
        this.prologue_name = prologue_name;
    }

    public String getResolveStr() {
        return resolve_str;
    }

    public Integer getResetWaitingCount() {
        return reset_waiting_count;
    }

    public void setResetWaitingCount(Integer reset_waiting_count) {
        this.reset_waiting_count = reset_waiting_count;
    }

    public void setResolveStr(String resolve_str) {
        this.resolve_str = resolve_str;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public String getPartRecord1() {
        return part_record1;
    }

    public void setPartRecord1(String part_record1) {
        this.part_record1 = part_record1;
    }

    public String getPartRecord2() {
        return part_record2;
    }

    public void setPartRecord2(String part_record2) {
        this.part_record2 = part_record2;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public PublicEnum.ImportTag getImportTag() {
        return import_tag;
    }

    public void setImportTag(PublicEnum.ImportTag import_tag) {
        this.import_tag = import_tag;
    }

    public String getId() {
        return id;
    }

    public PublicEnum.VoiceType getVoiceType() {
        return voice_type;
    }

    public void setVoiceType(PublicEnum.VoiceType voice_type) {
        this.voice_type = voice_type;
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

    public String getTmpRecordName() {
        return tmp_record_name;
    }

    public void setTmpRecordName(String tmp_record_name) {
        this.tmp_record_name = tmp_record_name;
    }

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
    }

    public String getSimilarityStr() {
        return similarity_str;
    }

    public void setSimilarityStr(String similarity_str) {
        this.similarity_str = similarity_str;
    }

    public String getSimilarityFloat() {
        return similarity_float;
    }

    public void setSimilarityFloat(String similarity_float) {
        this.similarity_float = similarity_float;
    }

    public String getFirstText() {
        return first_text;

    }

    public void setFirstText(String first_text) {
        this.first_text = first_text;
    }

    public String getRefuseRecordPath() {
        return refuse_record_path;
    }

    public void setRefuseRecordPath(String refuse_record_path) {
        this.refuse_record_path = refuse_record_path;
    }

    public String getProjectId() {
        return project_id;
    }

    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }

    public String getGroupCallId() {
        return group_call_id;
    }

    public void setGroupCallId(String group_call_id) {
        this.group_call_id = group_call_id;
    }

    public String getClientId() {
        return client_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GroupCallDetailStatus getStatus() {
        return status;
    }

    public void setStatus(GroupCallDetailStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return start_time;
    }

    public void setStartTime(Date start_time) {
        this.start_time = start_time;
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

    public Integer getExtNum() {
        return ext_num;
    }

    public void setExtNum(Integer ext_num) {
        this.ext_num = ext_num;
    }

    public String getPortId() {
        return port_id;
    }

    public void setPortId(String port_id) {
        this.port_id = port_id;
    }

    public BigDecimal getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(BigDecimal sumTotal) {
        this.sumTotal = sumTotal;
    }

    public BigDecimal getSumSuccess() {
        return sumSuccess;
    }

    public void setSumSuccess(BigDecimal sumSuccess) {
        this.sumSuccess = sumSuccess;
    }

    public BigDecimal getSumFail() {
        return sumFail;
    }

    public void setSumFail(BigDecimal sumFail) {
        this.sumFail = sumFail;
    }

    public String getPortRate() {
        return portRate;
    }

    public void setPortRate(String portRate) {
        this.portRate = portRate;
    }
}
