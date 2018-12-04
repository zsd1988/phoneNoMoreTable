package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="post_message")
public class PostMessage implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    String title;   // 标题

    String content; // 内容

    Integer user_id;    // 创建用户id

    String user_name;

    Date push_time;     // 推送时间

    String receive_role_types;  // 接收人 该角色所有人

    Boolean is_push = false;    // 是否已推送

    @Enumerated(value = EnumType.STRING)
    PublicEnum.PostMessageType type = PublicEnum.PostMessageType.System;    // 推送消息类型

    @Transient
    String pushTimeStr;

    public String getPushTimeStr() {
        return pushTimeStr;
    }

    public void setPushTimeStr(String pushTimeStr) {
        this.pushTimeStr = pushTimeStr;
    }

    public String getReceiveRoleTypes() {
        return receive_role_types;
    }

    public void setReceiveRoleTypes(String receive_role_types) {
        this.receive_role_types = receive_role_types;
    }

    public Date getPushTime() {
        return push_time;
    }

    public void setPushTime(Date push_time) {
        this.push_time = push_time;
    }

    public PublicEnum.PostMessageType getType() {
        return type;
    }

    public void setType(PublicEnum.PostMessageType type) {
        this.type = type;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Boolean getIsPush() {
        return is_push;
    }

    public void setIsPush(Boolean is_push) {
        this.is_push = is_push;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
