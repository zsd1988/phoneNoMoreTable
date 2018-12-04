package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="intention_hint")
public class IntentionHint implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    String content;

    Integer count = 0;  //库存

    Integer rate = 0;    //概率

    Integer day_count = 0;  //每天发放数量

    Boolean is_reward = false;  // 是否奖品

    Integer today_count = 0;     // 当天已产生的数量

    @Enumerated(value = EnumType.STRING)
    PublicEnum.IntentionHintType type = PublicEnum.IntentionHintType.Intention;     // 中奖活动类型

    public PublicEnum.IntentionHintType getType() {
        return type;
    }

    public void setType(PublicEnum.IntentionHintType type) {
        this.type = type;
    }

    public Integer getTodayCount() {
        return today_count;
    }

    public void setTodayCount(Integer today_count) {
        this.today_count = today_count;
    }

    public Boolean getIsReward() {
        return is_reward;
    }

    public void setIsReward(Boolean is_reward) {
        this.is_reward = is_reward;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getDayCount() {
        return day_count;
    }

    public void setDayCount(Integer day_count) {
        this.day_count = day_count;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
