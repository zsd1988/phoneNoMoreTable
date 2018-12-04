package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="jiang_hu_pai_rank")
public class JiangHuPaiRank implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    Integer gold = 0;   // 赚的金币

    @Enumerated(value = EnumType.STRING)
    PublicEnum.RoleType role_type = PublicEnum.RoleType.CS;

    Integer pai_id;

    public Integer getPaiId() {
        return pai_id;
    }

    public void setPaiId(Integer pai_id) {
        this.pai_id = pai_id;
    }

    public PublicEnum.RoleType getRoleType() {
        return role_type;
    }

    public void setRoleType(PublicEnum.RoleType role_type) {
        this.role_type = role_type;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
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
