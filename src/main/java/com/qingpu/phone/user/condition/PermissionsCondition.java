package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.Permissions;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PermissionsCondition extends QPCondition {
	private String role_id;

	private String permission_id;
	private String noId;
	private String permissionName;
	private String fid;
	private Permissions.Tag tag;
	private Boolean  isDel;
	private List<String> permissionList;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Permissions.class);
		if(StringUtils.isNotBlank(role_id)){
			this.criteria.add( Restrictions.eq("role_id", role_id) );
		}
		if(StringUtils.isNotBlank(permission_id)){
			this.criteria.add( Restrictions.eq("permission_id", permission_id) );
		}
		if(StringUtils.isNotBlank(noId)){
			this.criteria.add( Restrictions.ne("id", noId) );
		}
		if(StringUtils.isNotBlank(permissionName)){
			this.criteria.add( Restrictions.eq("permissionName", permissionName) );
		}
		if(tag!=null){
			this.criteria.add( Restrictions.eq("tag", tag) );
		}
		if(isDel!=null){
			this.criteria.add( Restrictions.eq("is_del", isDel) );
		}
		if(StringUtils.isNotBlank(fid)){
			this.criteria.add( Restrictions.eq("fid", fid) );
		}

		if(permissionList!=null&&permissionList.size()>0){
			this.criteria.add( Restrictions.in("id", permissionList) );
		}

		super.initCriteria(sessionFactory);
	}

	public void setTag(Permissions.Tag tag) {
		this.tag = tag;
	}

	public Boolean getDel() {
		return isDel;
	}

	public void setDel(Boolean del) {
		isDel = del;
	}

	public String getNoId() {
		return noId;
	}

	public void setNoId(String noId) {
		this.noId = noId;
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
	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}

	public void setPermissionId(String permission_id) {
		this.permission_id = permission_id;
	}

	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
}
