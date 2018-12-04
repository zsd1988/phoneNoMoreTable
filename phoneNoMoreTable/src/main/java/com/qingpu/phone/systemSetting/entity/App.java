package com.qingpu.phone.systemSetting.entity;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="app")
public class App implements Serializable {

    @Id
    @Column(length = 32)
    private String id = UUIDGenerator.getUUID(); // 主键Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer operator_id;     // 操作者id：如新建或删除

    @Column(length = 10)
    private String versions_text;    //外部版本

    private Integer least_version;	//最低兼容版本，低于该版本的必须更新到最新版本

    @Column(length = 200)
    private String download_url;		//下载地址

    @Enumerated(value = EnumType.STRING)
    private PublicEnum.OsType os_type = PublicEnum.OsType.PC;		// 类型

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

    public String getVersionsText() {
        return versions_text;
    }

    public void setVersionsText(String versions_text) {
        this.versions_text = versions_text;
    }

    public Integer getLeastVersion() {
        return least_version;
    }

    public void setLeastVersion(Integer least_version) {
        this.least_version = least_version;
    }

    public String getDownloadUrl() {
        return download_url;
    }

    public void setDownloadUrl(String download_url) {
        this.download_url = download_url;
    }

    public PublicEnum.OsType getOsType() {
        return os_type;
    }

    public void setOsType(PublicEnum.OsType os_type) {
        this.os_type = os_type;
    }
}
