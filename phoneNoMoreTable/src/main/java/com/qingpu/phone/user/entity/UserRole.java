package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="user_role")
//用户与角色对照类
public class UserRole implements Serializable{

	private static final long serialVersionUID = -8970588514901596495L;

	@Id
	@Column(length = 32)
	private String id = UUIDGenerator.getUUID();

	private Integer user_id;//使用openid作为主键，string类型

	@Column(length = 32)
	private String role_id;

	@Column
	private Date create_time = new Date();

	@Column
	private Boolean is_del = false;

	@Column
	private Date del_time;

	private Integer del_user_id;

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

	public Boolean getIsDel() {
		return is_del;
	}

	public void setIsDel(Boolean is_del) {
		this.is_del = is_del;
	}

	public Integer getUserId() {
		return user_id;
	}

	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}

	public String getRoleId() {
		return role_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}
}
