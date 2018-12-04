package com.qingpu.phone.user.entity;

import com.qingpu.phone.common.entity.PublicEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Desc 用户分组
 * */
@Entity
@Table(name="user_group")
public class UserGroup implements Serializable{
	private static final long serialVersionUID = -5474847689657196828L;

	@Id
	@GeneratedValue
	private int id;

	@Column
	private String group_name;		// 组别名称

	private Integer operator_id;     // 操作者id：如新建或删除

	private int  fid;					//父ID

	private Boolean is_del = false;

	@Transient
	String gongfu;

	@Transient
	String nickName;

	@Transient
	Integer count;

	public String getGongfu() {
		return gongfu;
	}

	public void setGongfu(String gongfu) {
		this.gongfu = gongfu;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return group_name;
	}

	public void setGroupName(String group_name) {
		this.group_name = group_name;
	}

	public Integer getOperatorId() {
		return operator_id;
	}

	public void setOperatorId(Integer operator_id) {
		this.operator_id = operator_id;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public Boolean getIsDel() {
		return is_del;
	}

	public void setIsDel(Boolean is_del) {
		this.is_del = is_del;
	}
}
