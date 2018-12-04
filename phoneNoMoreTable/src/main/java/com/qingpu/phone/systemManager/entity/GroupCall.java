package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="group_call")
public class GroupCall implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    @Index(name = "I_createTime")
    private Date create_time = new Date();

    private Boolean is_enable = false;      // 是否启用

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    String end_time1;

    String end_time2;

    String end_time3;

    String end_time4;

    @Column(unique = true)
    String name;    //名称

    Integer concurrency;	//并发数

    Double rate = 0.0;    //并发倍率

    String project_id;	//项目id

    @Enumerated(value = EnumType.STRING)
    PublicEnum.VoiceType voice_type = PublicEnum.VoiceType.CS;      // 语音类型

    @Column(length = 1000)
    String ext_num_list;        //分机号列表

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
    }

    public String getEndTime1() {
        return end_time1;
    }

    public void setEndTime1(String end_time1) {
        this.end_time1 = end_time1;
    }

    public String getEndTime2() {
        return end_time2;
    }

    public void setEndTime2(String end_time2) {
        this.end_time2 = end_time2;
    }

    public String getEndTime3() {
        return end_time3;
    }

    public void setEndTime3(String end_time3) {
        this.end_time3 = end_time3;
    }

    public String getEndTime4() {
        return end_time4;
    }

    public void setEndTime4(String end_time4) {
        this.end_time4 = end_time4;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getExtNumList() {
        return ext_num_list;
    }

    public void setExtNumList(String ext_num_list) {
        this.ext_num_list = ext_num_list;
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

    public Boolean getIsEnable() {
        return is_enable;
    }

    public void setIsEnable(Boolean is_del) {
        this.is_enable = is_del;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return project_id;
    }

    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }
}
