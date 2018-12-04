package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="user_info")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    String name;        // 用户名称

    Integer user_id;    // 用户id

    Date birth_day;     // 生日

    Boolean is_golden_egg = false;   // 是否已砸金蛋

    Date employee_day = new Date();  // 入职日期

    String head_image;

    Integer day_intention_count = 0;	// 当天意向数量

    Integer day_draw_count = 0;		// 当天抽奖次数

    String day_finish_draw = "0,0,0";	// 当天是否已达到5,8,11次意向

    Boolean is_first_day_hint = false;

    Boolean is_week_day_hint = false;

    Boolean is_month_hint = false;

    Boolean is_first_year_hint = false;

    Boolean is_second_year_hint = false;

    Boolean is_third_year_hint = false;

    Boolean is_fourth_year_hint = false;

    Boolean is_fifth_year_hint = false;

    Boolean is_sixth_year_hint = false;

    Boolean is_seventh_year_hint = false;

    Boolean is_eighth_year_hint = false;

    Boolean is_ninth_year_hint = false;

    Boolean is_tenth_year_hint = false;

    Boolean is_birthday_hint = false;

    @Transient
    String birthDayStr;

    @Transient
    String employeeDayStr;

    public Boolean getIsBirthdayHint() {
        return is_birthday_hint;
    }

    public void setIsBirthdayHint(Boolean is_birthday_hint) {
        this.is_birthday_hint = is_birthday_hint;
    }

    public Boolean getIsFirstDayHint() {
        return is_first_day_hint;
    }

    public void setIsFirstDayHint(Boolean is_first_day_hint) {
        this.is_first_day_hint = is_first_day_hint;
    }

    public Boolean getIsWeekDayHint() {
        return is_week_day_hint;
    }

    public void setIsWeekDayHint(Boolean is_week_day_hint) {
        this.is_week_day_hint = is_week_day_hint;
    }

    public Boolean getIsMonthHint() {
        return is_month_hint;
    }

    public void setIsMonthHint(Boolean is_month_hint) {
        this.is_month_hint = is_month_hint;
    }

    public Boolean getIsFirstYearHint() {
        return is_first_year_hint;
    }

    public void setIsFirstYearHint(Boolean is_first_year_hint) {
        this.is_first_year_hint = is_first_year_hint;
    }

    public Boolean getIsSecondYearHint() {
        return is_second_year_hint;
    }

    public void setIsSecondYearHint(Boolean is_second_year_hint) {
        this.is_second_year_hint = is_second_year_hint;
    }

    public Boolean getIsThirdYearHint() {
        return is_third_year_hint;
    }

    public void setIsThirdYearHint(Boolean is_third_year_hint) {
        this.is_third_year_hint = is_third_year_hint;
    }

    public Boolean getIsFourthYearHint() {
        return is_fourth_year_hint;
    }

    public void setIsFourthYearHint(Boolean is_fourth_year_hint) {
        this.is_fourth_year_hint = is_fourth_year_hint;
    }

    public Boolean getIsFifthYearHint() {
        return is_fifth_year_hint;
    }

    public void setIsFifthYearHint(Boolean is_fifth_year_hint) {
        this.is_fifth_year_hint = is_fifth_year_hint;
    }

    public Boolean getIsSixthYearHint() {
        return is_sixth_year_hint;
    }

    public void setIsSixthYearHint(Boolean is_sixth_year_hint) {
        this.is_sixth_year_hint = is_sixth_year_hint;
    }

    public Boolean getIsSeventhYearHint() {
        return is_seventh_year_hint;
    }

    public void setIsSeventhYearHint(Boolean is_seventh_year_hint) {
        this.is_seventh_year_hint = is_seventh_year_hint;
    }

    public Boolean getIsEighthYearHint() {
        return is_eighth_year_hint;
    }

    public void setIsEighthYearHint(Boolean is_eighth_year_hint) {
        this.is_eighth_year_hint = is_eighth_year_hint;
    }

    public Boolean getIsNinthYearHint() {
        return is_ninth_year_hint;
    }

    public void setIsNinthYearHint(Boolean is_ninth_year_hint) {
        this.is_ninth_year_hint = is_ninth_year_hint;
    }

    public Boolean getIsTenthYearHint() {
        return is_tenth_year_hint;
    }

    public void setIsTenthYearHint(Boolean is_tenth_year_hint) {
        this.is_tenth_year_hint = is_tenth_year_hint;
    }

    public Integer getDayIntentionCount() {
        return day_intention_count;
    }

    public void setDayIntentionCount(Integer day_intention_count) {
        this.day_intention_count = day_intention_count;
    }

    public Integer getDayDrawCount() {
        return day_draw_count;
    }

    public void setDayDrawCount(Integer day_draw_count) {
        this.day_draw_count = day_draw_count;
    }

    public String getDayFinishDraw() {
        return day_finish_draw;
    }

    public void setDayFinishDraw(String day_finish_draw) {
        this.day_finish_draw = day_finish_draw;
    }

    public String getHeadImage() {
        return head_image;
    }

    public void setHeadImage(String head_image) {
        this.head_image = head_image;
    }

    public String getBirthDayStr() {
        return birthDayStr;
    }

    public void setBirthDayStr(String birthDayStr) {
        this.birthDayStr = birthDayStr;
    }

    public String getEmployeeDayStr() {
        return employeeDayStr;
    }

    public void setEmployeeDayStr(String employeeDayStr) {
        this.employeeDayStr = employeeDayStr;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birth_day;
    }

    public void setBirthDay(Date birth_day) {
        this.birth_day = birth_day;
    }

    public Boolean getIsGoldenEgg() {
        return is_golden_egg;
    }

    public void setIsGoldenEgg(Boolean is_golden_egg) {
        this.is_golden_egg = is_golden_egg;
    }

    public Date getEmployeeDay() {
        return employee_day;
    }

    public void setEmployeeDay(Date employee_day) {
        this.employee_day = employee_day;
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
