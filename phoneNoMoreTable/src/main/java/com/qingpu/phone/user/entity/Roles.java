package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="roles")
//用户角色类
public class Roles {
	@Id
	@Column(length = 32)
	private String id = UUIDGenerator.getUUID();

	private String role_type;									//角色名称

	private Boolean is_del = false;

	private Date create_time = new Date();

	private Date del_time;

	private Integer del_user_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleType() {
		return role_type;
	}

	public void setRoleType(String role_type) {
		this.role_type = role_type;
	}

	public Boolean getIsDel() {
		return is_del;
	}

	public void setIsDel(Boolean is_del) {
		this.is_del = is_del;
	}

	public Date getCreateTime() {
		return create_time;
	}

	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
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
}
