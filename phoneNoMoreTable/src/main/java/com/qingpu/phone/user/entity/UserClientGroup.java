package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="user_client_group")
public class UserClientGroup implements Serializable {

    @Id
    @GeneratedValue
    private     int     id;

    private     int     user_id;                    //用户iD

    private     String  client_group_id;            //客户分组ID

    private     String  project_id;                  //项目Id

    private Date create_time = new Date();

    private Boolean is_del = false;

    private Date del_time;

    private Integer del_user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getClientGroupId() {
        return client_group_id;
    }

    public void setClientGroupId(String client_group_id) {
        this.client_group_id = client_group_id;
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

    public Date getDelTime() {
        return del_time;
    }

    public void setDelTime(Date del_time) {
        this.del_time = del_time;
    }

    public Integer getDelUserId() {
        return del_user_id;
    }

    public void setDelUserId(Integer del_user_id) {
        this.del_user_id = del_user_id;
    }

    public String getProjectId() {
        return project_id;
    }

    public void setProjectId(String project_id) {
        this.project_id = project_id;
    }
}
