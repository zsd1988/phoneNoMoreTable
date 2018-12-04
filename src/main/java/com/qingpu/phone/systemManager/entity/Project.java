package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="project")
public class Project implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    @Index(name = "I_isDel")
    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    private String name;    //名称

    private String introduce_voice_url; //开场白语音url

    private String retrieve_voice_url;  //挽回语音url

    //统计端口接通率
    @Transient
    BigDecimal sumA;     //A类未呼数量

    @Transient
    BigDecimal sumB;     //B类未呼数量

    @Transient
    BigDecimal sumC;     //C类未呼数量

    @Transient
    BigDecimal sumD;     //D类未呼数量

    @Transient
    BigDecimal sumE;     //E类未呼数量

    @Transient
    BigDecimal sumDistribute;     //已呼导入群呼或已分配数量

    @Transient
    private String prologueJsonArray; //开场白列表

    public String getPrologueJsonArray() {
        return prologueJsonArray;
    }

    public void setPrologueJsonArray(String prologueJsonArray) {
        this.prologueJsonArray = prologueJsonArray;
    }

    public BigDecimal getSumA() { return sumA; }

    public void setSumA(BigDecimal sumA) { this.sumA = sumA; }

    public BigDecimal getSumB() { return sumB; }

    public void setSumB(BigDecimal sumB) { this.sumB = sumB; }

    public BigDecimal getSumC() { return sumC; }

    public void setSumC(BigDecimal sumC) { this.sumC = sumC; }

    public BigDecimal getSumD() { return sumD; }

    public void setSumD(BigDecimal sumD) { this.sumD = sumD; }

    public BigDecimal getSumE() { return sumE; }

    public void setSumE(BigDecimal sumE) { this.sumE = sumE; }

    public BigDecimal getSumDistribute() { return sumDistribute; }

    public void setSumDistribute(BigDecimal sumDistribute) { this.sumDistribute = sumDistribute; }

    public String getIntroduceVoiceUrl() { return introduce_voice_url; }

    public void setIntroduceVoiceUrl(String introduce_voice_url) { this.introduce_voice_url = introduce_voice_url; }

    public String getRetrieveVoiceUrl() { return retrieve_voice_url; }

    public void setRetrieveVoiceUrl(String retrieve_voice_url) { this.retrieve_voice_url = retrieve_voice_url; }

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
