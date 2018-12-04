package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="jiang_hu_ren")
public class JiangHuRen implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    Integer week_gold = 0;      // 周贡献纹银

    Integer last_week_gold = 0; // 上周贡献纹银

    Integer week_intention = 0; // 周意向

    Integer week_day_intention = 0; // 每周按天累积的意向量

    Integer week_finish = 0;    // 周认筹

    Integer last_week_intention = 0;    // 上周意向

    Integer last_week_finish = 0;   // 上周认筹

    Integer month_intention = 0;    // 月意向

    Integer month_finish = 0;       // 月认筹

    Integer day_intention = 0;  // 日意向

    Integer user_id;    // 用户id

    @Enumerated(value = EnumType.STRING)
    PublicEnum.RoleType role_type = PublicEnum.RoleType.CS;

    String user_name;   // 用户姓名

    String user_head_image; // 用户头像

    Integer pai_id; // 组id

    Date update_time = new Date();   // 更新时间

    public Integer getWeekDayIntention() {
        return week_day_intention;
    }

    public void setWeekDayIntention(Integer week_day_intention) {
        this.week_day_intention = week_day_intention;
    }

    public Integer getWeekFinish() {
        return week_finish;
    }

    public void setWeekFinish(Integer week_finish) {
        this.week_finish = week_finish;
    }

    public Integer getLastWeekFinish() {
        return last_week_finish;
    }

    public void setLastWeekFinish(Integer last_week_finish) {
        this.last_week_finish = last_week_finish;
    }

    public Integer getMonthFinish() {
        return month_finish;
    }

    public void setMonthFinish(Integer month_finish) {
        this.month_finish = month_finish;
    }

    public Integer getDayIntention() {
        return day_intention;
    }

    public void setDayIntention(Integer day_intention) {
        this.day_intention = day_intention;
    }

    public PublicEnum.RoleType getRoleType() {
        return role_type;
    }

    public void setRoleType(PublicEnum.RoleType role_type) {
        this.role_type = role_type;
    }

    public Integer getWeekGold() {
        return week_gold;
    }

    public void setWeekGold(Integer week_gold) {
        this.week_gold = week_gold;
    }

    public Integer getLastWeekGold() {
        return last_week_gold;
    }

    public void setLastWeekGold(Integer last_week_gold) {
        this.last_week_gold = last_week_gold;
    }

    public Integer getWeekIntention() {
        return week_intention;
    }

    public void setWeekIntention(Integer week_intention) {
        this.week_intention = week_intention;
    }

    public Integer getLastWeekIntention() {
        return last_week_intention;
    }

    public void setLastWeekIntention(Integer last_week_intention) {
        this.last_week_intention = last_week_intention;
    }

    public Integer getMonthIntention() {
        return month_intention;
    }

    public void setMonthIntention(Integer month_intention) {
        this.month_intention = month_intention;
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

    public String getUserHeadImage() {
        return user_head_image;
    }

    public void setUserHeadImage(String user_head_image) {
        this.user_head_image = user_head_image;
    }

    public Integer getPaiId() {
        return pai_id;
    }

    public void setPaiId(Integer pai_id) {
        this.pai_id = pai_id;
    }

    public Date getUpdateTime() {
        return update_time;
    }

    public void setUpdateTime(Date update_time) {
        this.update_time = update_time;
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
