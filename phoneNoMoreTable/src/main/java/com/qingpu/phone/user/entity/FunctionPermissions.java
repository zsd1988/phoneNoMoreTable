package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="function_permissions")
//功能权限
public class FunctionPermissions  {

	@Id
	@Column(length = 32)
	private String id;
	private String permissionName;//权限名字
	//父菜单id
	@Column(length = 32)
	private String fid;

	private Boolean is_del = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Boolean getIsDel() {
		return is_del;
	}

	public void setIsDel(Boolean is_del) {
		this.is_del = is_del;
	}
}
