package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="intention_hint_reward_record")
public class IntentionHintRewardRecord implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Column(length = 32)
    private String hint_id;

    @Column(length = 32)
    String user_id; //用户id

    String user_name;   //用户名称

    Boolean is_get = false;     // 是否领取

    String content;


    Date get_date;  // 领取时间

    @Enumerated(value = EnumType.STRING)
    PublicEnum.IntentionHintType type = PublicEnum.IntentionHintType.Intention;

    public PublicEnum.IntentionHintType getType() {
        return type;
    }

    public Date getGetDate() {
        return get_date;
    }

    public void setGetDate(Date get_date) {
        this.get_date = get_date;
    }

    public void setType(PublicEnum.IntentionHintType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public Boolean getIsDel() {
        return is_del;
    }

    public void setIsDel(Boolean is_del) {
        this.is_del = is_del;
    }

    public String getHintId() {
        return hint_id;
    }

    public void setHintId(String hint_id) {
        this.hint_id = hint_id;
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

    public Boolean getIsGet() {
        return is_get;
    }

    public void setIsGet(Boolean is_get) {
        this.is_get = is_get;
    }
}
