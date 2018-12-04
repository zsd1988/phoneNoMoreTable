package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="role_function")
/**
 * 用户角色与功能权限
 */
public class RoleFunction implements Serializable{

	private static final long serialVersionUID = 4063416193352315243L;

	@Id
	@Column(length = 32)
	private String id = UUIDGenerator.getUUID(); // 主键Id


	@Column(length = 32)
	private String role_id;

	@Column(length = 32)
	private String function_id;

	private Date create_time = new Date();

	private Boolean is_del = false;

	private Date del_time;

	private Integer del_user_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getRoleId() {
		return role_id;
	}

	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}

	public String getFunctionId() {
		return function_id;
	}

	public void setFunctionId(String function_id) {
		this.function_id = function_id;
	}
}
