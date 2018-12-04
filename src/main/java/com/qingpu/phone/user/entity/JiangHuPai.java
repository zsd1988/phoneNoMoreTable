package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="jiang_hu_pai")
public class JiangHuPai implements Serializable {

    @Id
    private int id;

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.RoleType role_type = PublicEnum.RoleType.CS;

    String nick_name;   // 昵称

    Integer count = 0;  // 任务

    Integer finish_count = 0;   // 已完成任务

    Integer gold = 0;   // 纹银

    String gongfu;      // 功夫

    String gold_pai_ids;   // 扣金币序列

    Integer sub_count = 0;   // 组中已扣多少个人的金币

    Integer init_gold = 0;  // 周初始纹银，由每周个人纹银乘以组人数

    Integer ren_count = 0;  // 帮派人数

    Double double_gold = 100.0;       // 每周纹银数

    public Double getDoubleGold() {
        return double_gold;
    }

    public void setDoubleGold(Double double_gold) {
        this.double_gold = double_gold;
    }

    public Integer getSubCount() {
        return sub_count;
    }

    public void setSubCount(Integer sub_count) {
        this.sub_count = sub_count;
    }

    public Integer getRenCount() {
        return ren_count;
    }

    public void setRenCount(Integer ren_count) {
        this.ren_count = ren_count;
    }

    public Integer getInitGold() {
        return init_gold;
    }

    public void setInitGold(Integer init_gold) {
        this.init_gold = init_gold;
    }

    public String getGoldPaiIds() {
        return gold_pai_ids;
    }

    public void setGoldPaiIds(String gold_pai_ids) {
        this.gold_pai_ids = gold_pai_ids;
    }

    public String getGongfu() {
        return gongfu;
    }

    public void setGongfu(String gongfu) {
        this.gongfu = gongfu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PublicEnum.RoleType getRoleType() {
        return role_type;
    }

    public void setRoleType(PublicEnum.RoleType role_type) {
        this.role_type = role_type;
    }

    public String getNickName() {
        return nick_name;
    }

    public void setNickName(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFinishCount() {
        return finish_count;
    }

    public void setFinishCount(Integer finish_count) {
        this.finish_count = finish_count;
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
