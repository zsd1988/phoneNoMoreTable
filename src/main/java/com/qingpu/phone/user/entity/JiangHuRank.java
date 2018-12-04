package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="jiang_hu_rank")
public class JiangHuRank implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Enumerated(value = EnumType.STRING)
    PublicEnum.RoleType roleType = PublicEnum.RoleType.CS;

    Integer gold = 0;

    Integer user_id;

    String user_name;

    Integer intention = 0;

    Integer finish = 0;     // 认筹

    Integer last_index;     // 倒数第几

    Integer pai_id; // 组

    public Integer getLastIndex() {
        return last_index;
    }

    public void setLastIndex(Integer last_index) {
        this.last_index = last_index;
    }

    public Integer getPaiId() {
        return pai_id;
    }

    public void setPaiId(Integer pai_id) {
        this.pai_id = pai_id;
    }

    public PublicEnum.RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(PublicEnum.RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public Integer getIntention() {
        return intention;
    }

    public void setIntention(Integer intention) {
        this.intention = intention;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
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
}
