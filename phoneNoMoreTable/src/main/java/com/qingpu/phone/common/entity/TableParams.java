package com.qingpu.phone.common.entity;

import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.user.entity.Permissions;

import java.util.Date;

public class TableParams {
    Boolean isInclude;
    PaginationParams paginationParams;
    String groupId;
    String projectId;
    String status;
    String[] statusArr;
    Date startTime;         // 开始时间
    String startTimeStr;    // 开始时间字符串
    Date endTime;           // 结束时间
    String endTimeStr;      // 结束时间字符串
    Integer limit;  // 获取数量
    Permissions.Tag permissionsTag; // 菜单权限
    String clientId;      //所属工号id--只在project_client表中表示所属工号
    String userId;      //所属工号id
    String userIds;      //用户ids
    Boolean isCommon = false;  //是否共有
    Boolean isGetPublicRepository = false;      //是否是管理者公共资源库发来的请求
    String approvalId;      //审批人id
    String name;
    Boolean isGetEmployee;
    String phone;
    String id;
    String groupType;           //项目类型
    Boolean isDistribute;           ////是否导入/分配
    PublicEnum.ClientStatus clientStatus; // //状态
    PublicEnum.ImportTag importTag; // 资源类别
    PublicEnum.ClientGroupType clientGroupType; // 客户分组类别
    String workNumber;      //工号
    String roleType;         //角色
    String transYn;          //是否转移
    Integer isAppeal;   // 是否申诉

    public Integer getIsAppeal() {
        return isAppeal;
    }

    public void setIsAppeal(Integer isAppeal) {
        this.isAppeal = isAppeal;
    }

    public String getTransYn() { return transYn; }

    public void setTransYn(String transYn) { this.transYn = transYn; }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getWorkNumber() { return workNumber; }

    public void setWorkNumber(String workNumber) { this.workNumber = workNumber; }

    public Boolean getInclude() {
        return isInclude;
    }

    public void setInclude(Boolean include) {
        isInclude = include;
    }

    public PaginationParams getPaginationParams() {
        return paginationParams;
    }

    public void setPaginationParams(PaginationParams paginationParams) {
        this.paginationParams = paginationParams;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public String[] getStatusArr() {
        return statusArr;
    }

    public void setStatusArr(String[] statusArr) {
        this.statusArr = statusArr;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Permissions.Tag getPermissionsTag() {
        return permissionsTag;
    }

    public void setPermissionsTag(Permissions.Tag permissionsTag) {
        this.permissionsTag = permissionsTag;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getCommon() {
        return isCommon;
    }

    public void setCommon(Boolean common) {
        isCommon = common;
    }

    public Boolean getGetPublicRepository() {
        return isGetPublicRepository;
    }

    public void setGetPublicRepository(Boolean getPublicRepository) {
        isGetPublicRepository = getPublicRepository;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGetEmployee() {
        return isGetEmployee;
    }

    public void setGetEmployee(Boolean getEmployee) {
        isGetEmployee = getEmployee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public PublicEnum.ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(PublicEnum.ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Boolean getDistribute() {
        return isDistribute;
    }

    public void setDistribute(Boolean distribute) {
        isDistribute = distribute;
    }

    public PublicEnum.ImportTag getImportTag() {
        return importTag;
    }

    public void setImportTag(PublicEnum.ImportTag importTag) {
        this.importTag = importTag;
    }

    public PublicEnum.ClientGroupType getClientGroupType() {
        return clientGroupType;
    }

    public void setClientGroupType(PublicEnum.ClientGroupType clientGroupType) {
        this.clientGroupType = clientGroupType;
    }
}
