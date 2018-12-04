package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.utils.UUIDGenerator;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="permissions")
//用户权限类
public class Permissions implements  Comparable<Permissions>{

	@Id
	@Column(length = 32)
	private String id = UUIDGenerator.getUUID();
	
	private String permissionName;//权限名字
	
	//此权限对应的请求url
	private String permissionUrl;
	
	//父菜单id
	@Column(length = 32)
	private String fid;
	
	//二级菜单的图标class名称
	private String className;

	private Boolean is_del = false;

	private Date create_time = new Date();

	private Integer show_index;

	private Boolean canDelete=false;	//能否删除

	@Enumerated(value = EnumType.STRING)
	private FolderType folder_type =FolderType.Image;    //0：视频    1:音频    2：图片  3:其它文件

	@Enumerated(value = EnumType.STRING)
	private Tag tag =Tag.Admin;

	public enum Tag {
		Admin("超级管理员"),
		Manager("管理员"),
		Interview("约访"),
		Quality("质检"),
		Employee("客服");
		private String name;
		Tag(String name){
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String getType(){
			return this.toString();
		}
	}

	public enum FolderType {
		Image("图片"),
		Video("视频"),
		No("没有格式");
		private String name;
		FolderType(String name){
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String getType(){
			return this.toString();
		}
	}

	@Transient
	private String  buttonState;

	@Transient
	private String  checkBoxState;

	@Transient
	private int  fidExistCount;
	
	//存储子菜单的list
	@Transient
	private List<Permissions> permissionsList;

	public Boolean getIsDel() {
		return is_del;
	}

	public void setIsDel(Boolean is_del) {
		this.is_del = is_del;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Integer getShowIndex() {
		return show_index;
	}

	public void setShowIndex(Integer show_index) {
		this.show_index = show_index;
	}

	public Date getCreateTime() {
		return create_time;
	}

	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Permissions> getPermissionsList() {
		return permissionsList;
	}

	public void setPermissionsList(List<Permissions> permissionsList) {
		this.permissionsList = permissionsList;
	}

	public String getButtonState() {
		return buttonState;
	}

	public void setButtonState(String buttonState) {
		this.buttonState = buttonState;
	}

	public String getCheckBoxState() {
		return checkBoxState;
	}

	public void setCheckBoxState(String checkBoxState) {
		this.checkBoxState = checkBoxState;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	@Override
	public int compareTo(Permissions arg0) {
		return this.getShowIndex().compareTo(arg0.getShowIndex());
	}

	public int getFidExistCount() {
		return fidExistCount;
	}

	public void setFidExistCount(int fidExistCount) {
		this.fidExistCount = fidExistCount;
	}

	public FolderType getFolderType() {
		return folder_type;
	}

	public void setFolderType(FolderType folder_type) {
		this.folder_type = folder_type;
	}
}
