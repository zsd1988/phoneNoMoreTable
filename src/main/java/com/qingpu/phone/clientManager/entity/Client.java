package com.qingpu.phone.clientManager.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="client")
public class Client implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    @Index(name = "I_create_time")
    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Index(name = "I_projectId")   // ********************* projectId,isDistribute,importTag建立复合索引
    @Column(length = 32)
    String project_id;	//项目id

    @Index(name = "I_groupId")
    @Column(length = 32)
    String group_id;		//分组id，0为公共资源，1为公海资源

    String name;	//姓名

    @Index(name = "I_phone")
    String phone;	//电话

    @Column(length = 2000)
    String des;	//备注

    Boolean is_distribute=false; //是否导入/分配

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.Area area;    //区域

    Integer sex;    //性别0为女，1为男

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.YearTag year_tag;    //年龄标签

    @Index(name = "I_importTag")
    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ImportTag import_tag;    //导入标签

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ClientStatus status;		//状态

    @Index(name = "I_reviewStatus")
    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ClientStatus review_status;	//质检状态

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ClientStatus first_confirm_status;		//首次约访状态

    Integer interview_id;	//第一次跟进的约访id

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ClientStatus confirm_status;		//约访状态

    @Index(name = "I_groupType")
    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ClientGroupType group_type;	//资源类型

    private Date last_update_time;   // 用户点击更新

    private Date update_time = new Date();   // 更新时间，资源转移导致的更新

    Boolean once_status;    //实时状态true为实时，false为非实时

    Boolean is_set_new_intention = false;    // 设置为了新意向

    Boolean is_import;      // 是否已导入分库分表

    Boolean is_appeal;      // 客服是否申诉，为空表示没申诉，为true表示已申诉，为false表示质检已受理

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.ClientStatus intention_status;		//设置为意向的状态

    @Transient
    public String statusStr;            //状态值

    @Transient
    String review_id;            //质检id

    @Transient
    String reviewName;            //质检名字

    @Transient
    public String callRecordId;            //通话记录id

    @Transient
    public String reviewStatusStr;            //审核状态

    @Transient
    public String confirmStatusStr;            //审核状态

    @Transient
    public String createTimeStr;            //创建时间

    @Transient
    public String updateTimeStr;            //创建时间

    @Transient
    public String projectName;            //项目名称

    @Transient
    public String importTagStr;            //资源类别

    @Transient
    public String groupTypeStr;            //项目资源类别

    public Integer getInterviewId() {
        return interview_id;
    }

    public void setInterviewId(Integer interview_id) {
        this.interview_id = interview_id;
    }

    public PublicEnum.ClientStatus getFirstConfirmStatus() {
        return first_confirm_status;
    }

    public void setFirstConfirmStatus(PublicEnum.ClientStatus first_confirm_status) {
        this.first_confirm_status = first_confirm_status;
    }

    public PublicEnum.ClientStatus getIntentionStatus() {
        return intention_status;
    }

    public void setIntentionStatus(PublicEnum.ClientStatus intention_status) {
        this.intention_status = intention_status;
    }

    public Boolean getIsImport() {
        return is_import;
    }

    public void setIsImport(Boolean is_import) {
        this.is_import = is_import;
    }

    public Boolean getIsAppeal() {
        return is_appeal;
    }

    public void setIsAppeal(Boolean is_appeal) {
        this.is_appeal = is_appeal;
    }

    public Boolean getIsSetNewIntention() {
        return is_set_new_intention;
    }

    public void setIsSetNewIntention(Boolean is_set_new_intention) {
        this.is_set_new_intention = is_set_new_intention;
    }

    public Date getLastUpdateTime() {
        return last_update_time;
    }

    public void setLastUpdateTime(Date last_update_time) {
        this.last_update_time = last_update_time;
    }

    public Boolean getOnceStatus() { return once_status; }

    public Date getUpdateTime() {
        return update_time;
    }

    public void setUpdateTime(Date update_time) {
        this.update_time = update_time;
    }

    public PublicEnum.ClientStatus getConfirmStatus() {
        return confirm_status;
    }

    public void setConfirmStatus(PublicEnum.ClientStatus confirm_status) {
        this.confirm_status = confirm_status;
    }

    public void setOnceStatus(Boolean once_status) { this.once_status = once_status; }

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
    }

    public Integer getOperatorId() {
        return operator_id;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewId() {
        return review_id;
    }

    public void setReviewId(String review_id) {
        this.review_id = review_id;
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

    public String getProjectId() {
        return project_id;
    }

    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }

    public String getGroupId() {
        return group_id;
    }

    public void setGroupId(String group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Boolean getIsDistribute() {
        return is_distribute;
    }

    public void setIsDistribute(Boolean is_distribute) {
        this.is_distribute = is_distribute;
    }

    public PublicEnum.Area getArea() {
        return area;
    }

    public void setArea(PublicEnum.Area area) {
        this.area = area;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCallRecordId() {
        return callRecordId;
    }

    public void setCallRecordId(String callRecordId) {
        this.callRecordId = callRecordId;
    }

    public PublicEnum.YearTag getYearTag() {
        return year_tag;
    }

    public void setYearTag(PublicEnum.YearTag year_tag) {
        this.year_tag = year_tag;
    }

    public PublicEnum.ImportTag getImportTag() {
        return import_tag;
    }

    public void setImportTag(PublicEnum.ImportTag import_tag) {
        this.import_tag = import_tag;
    }

    public PublicEnum.ClientStatus getStatus() {
        return status;
    }

    public void setStatus(PublicEnum.ClientStatus status) {
        this.status = status;
    }

    public PublicEnum.ClientStatus getReviewStatus() {
        return review_status;
    }

    public void setReviewStatus(PublicEnum.ClientStatus review_status) {
        this.review_status = review_status;
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

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getReviewStatusStr() {
        return reviewStatusStr;
    }

    public void setReviewStatusStr(String reviewStatusStr) {
        this.reviewStatusStr = reviewStatusStr;
    }

    public String getConfirmStatusStr() {
        return confirmStatusStr;
    }

    public void setConfirmStatusStr(String confirmStatusStr) {
        this.confirmStatusStr = confirmStatusStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public PublicEnum.ClientGroupType getGroupType() {
        return group_type;
    }

    public void setGroupType(PublicEnum.ClientGroupType group_type) {
        this.group_type = group_type;
    }

    public String getImportTagStr() {
        return importTagStr;
    }

    public void setImportTagStr(String importTagStr) {
        this.importTagStr = importTagStr;
    }

    public String getGroupTypeStr() {
        return groupTypeStr;
    }

    public void setGroupTypeStr(String groupTypeStr) {
        this.groupTypeStr = groupTypeStr;
    }
}
