package com.qingpu.phone.systemManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="day_hint")
public class DayHint implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Enumerated(value = EnumType.STRING)
    PublicEnum.DayHintType type = PublicEnum.DayHintType.Day;     // 中奖活动类型

    @Column(length = 500)
    String content;

    Integer rate = 0;    //概率

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public PublicEnum.DayHintType getType() {
        return type;
    }

    public void setType(PublicEnum.DayHintType type) {
        this.type = type;
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
