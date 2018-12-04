package com.qingpu.phone.systemSetting.entity;

import com.qingpu.phone.common.entity.PublicEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="port")
public class Port implements Serializable {

    @Id
    @Column(length = 32)
    private String id; // 主键Id

    private Date create_time = new Date();

    @NotNull(message = "不能为空")
    private Boolean is_del = false;     //是否启用

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @NotNull(message = "不能为空")
    @Enumerated(value = EnumType.STRING)
    PublicEnum.PortStatus status = PublicEnum.PortStatus.Idle;

    Boolean is_caller = false;  //是否主叫

    Date last_used_time;    // 最后一次使用时间

    String called;      // 被叫号码

    String caller;      // 主叫号码

    @NotNull(message = "不能为空")
    Boolean is_third = false;   // 是否第三方

    @NotNull(message = "不能为空")
    Boolean is_call_out = false;    // 是否能拨打外地号码


    @NotNull(message = "不能为空")
    Boolean is_first = false;   // 是否优先拨打

    public Boolean getIsCallOut() {
        return is_call_out;
    }

    public void setIsCallOut(Boolean is_call_out) {
        this.is_call_out = is_call_out;
    }

    public Boolean getIsFirst() {
        return is_first;
    }

    public void setIsFirst(Boolean is_first) {
        this.is_first = is_first;
    }

    public Boolean getIsThird() {
        return is_third;
    }

    public void setIsThird(Boolean is_third) {
        this.is_third = is_third;
    }

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

    public Boolean getIsCaller() {
        return is_caller;
    }

    public Date getLastUsedTime() {
        return last_used_time;
    }

    public void setLastUsedTime(Date last_used_time) {
        this.last_used_time = last_used_time;
    }

    public void setIsCaller(Boolean is_caller) {
        this.is_caller = is_caller;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public Boolean getIsDel() {
        return is_del;
    }

    public PublicEnum.PortStatus getStatus() {
        return status;
    }

    public void setStatus(PublicEnum.PortStatus status) {
        this.status = status;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public void setIsDel(Boolean is_del) {
        this.is_del = is_del;
    }
}
