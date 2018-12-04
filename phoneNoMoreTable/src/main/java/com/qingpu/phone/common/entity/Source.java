package com.qingpu.phone.common.entity;


import com.qingpu.phone.common.entity.PublicEnum.SourceType;
import com.qingpu.phone.common.utils.UUIDGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="source")
public class Source {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID();                            // 主键Id

    private Date create_time = new Date();      // 创建时间

    private Boolean is_del = false;             // 是否删除
    @Column(length = 100)
    private String source_name;                 // 素材名称

    @Enumerated(value = EnumType.STRING)
    private SourceType  source_type = SourceType.Image;              //0：视频    1:音频    2：图片  3:其它文件

    private Double source_time=0.00;            //视频、音频时长

    private Integer   type;                      //0:基本素材  1：动态素材

    private Date  update_time=new Date();      //修改时间

    @Column(length = 500)
    private String  source_path_url;           //文件路径（按照每天的时间做成文件夹方式如20170808）

    private String  source_img;                 //文件图片,zip放的文件路径

    @Column(length = 32)
    private String  permission_id;              //文件夹Id

    private Integer checkState=0;                //0：审核中   1：审核待定   2：审核通过  3：审核不通过

    private String  checkReason;                //审核原因

    private Integer img_width;                  //图片分辨率（宽）

    private Integer img_height;                 //图片分辨率（高）

    private Double source_size;                //素材文件大小

    private Integer user_id;                     //素材上传操作者

    private String remark;                      //素材备注信息

    @Column(length = 32)
    private String md5;                         // hash md5值，主要针对视频文件等大文件


    @Transient
    private String  operation;                  //操作者
    @Transient
    private String  folderName;                 //文件夹名称

    @Transient
    private String  pixel;                      //素材分辨率
    @Transient
    private String  sourceTimeStr;          //视频素材文件时长

    @Transient
    private String  createTimeStr;          //时间格式化

    @Transient
    private String  updateTimeStr;          //时间格式化

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
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

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
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

    public String getSourceName() {
        return source_name;
    }

    public void setSourceName(String source_name) {
        this.source_name = source_name;
    }

    public SourceType getSourceType() {
        return source_type;
    }

    public void setSourceType(SourceType source_type) {
        this.source_type = source_type;
    }

    public Double getSourceTime() {
        return source_time;
    }

    public void setSourceTime(Double source_time) {
        this.source_time = source_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return update_time;
    }

    public void setUpdateTime(Date update_time) {
        this.update_time = update_time;
    }

    public String getSourcePathUrl() {
        return source_path_url;
    }

    public void setSourcePathUrl(String source_path_url) {
        this.source_path_url = source_path_url;
    }

    public String getPermissionId() {
        return permission_id;
    }

    public void setPermissionId(String permission_id) {
        this.permission_id = permission_id;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    public Integer getImgWidth() {
        return img_width;
    }

    public void setImgWidth(Integer img_width) {
        this.img_width = img_width;
    }

    public Integer getImgHeight() {
        return img_height;
    }

    public void setImgHeight(Integer img_height) {
        this.img_height = img_height;
    }

    public Double getSourceSize() {
        return source_size;
    }

    public void setSourceSize(Double source_size) {
        this.source_size = source_size;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceTimeStr() {
        return sourceTimeStr;
    }

    public void setSourceTimeStr(String sourceTimeStr) {
        this.sourceTimeStr = sourceTimeStr;
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

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSourceImg() {
        return source_img;
    }

    public void setSourceImg(String source_img) {
        this.source_img = source_img;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
