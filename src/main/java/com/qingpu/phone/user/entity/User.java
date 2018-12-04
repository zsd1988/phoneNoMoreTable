package com.qingpu.phone.user.entity;

import javax.persistence.*;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.UUIDGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * @Desc 用户信息
 * */
@Entity
@Table(name="user")
public class User implements Serializable{
	private static final long serialVersionUID = -5474847689657196828L;

	@Id
	@GeneratedValue
	private int id;

	private String  work_number;		//员工工号

	@Column
	private String name;	// 用户名

	private Integer operator_id;     // 操作者id：如新建或删除

	@Column(length = 14)
	private String phone;	// 手机号码

	private Date create_time = new Date();

	private Integer group_id;		//用户组id

	//网页后台邮箱注册用户密码
	private String password;

	private String ext_ip_id;	//分机号与ip,id

	@Column
	private Boolean is_del = false;

	@Column
	private Boolean is_online = false;

	private Boolean is_login = false;

	@Column(length = 32)
	private String role_id;		//角色Id

	private Integer ext_num;		//分机号

	@Enumerated(value = EnumType.STRING)
	PublicEnum.WorkType work_type = PublicEnum.WorkType.FullTime;      // 默认全职

	private Integer waiting_index = 0;	// 空闲序号，转接优先选最小值

	@Column(length = 15)
	private String ip;

	Boolean is_team_leader = false;

	@Column
	private Date del_time;

	private Integer del_user_id;

	private String del_reason;

	Date new_create_time = new Date();	// 更改名字的时间

    @Transient
    Boolean is_no_name = false;     // 在user表里面因为离职找不到了

    public Boolean getIsNoName() {
        return is_no_name;
    }

    public void setIsNoName(Boolean is_no_name) {
        this.is_no_name = is_no_name;
    }

    public Date getNewCreateTime() {
		return new_create_time;
	}

	public void setNewCreateTime(Date new_create_time) {
		this.new_create_time = new_create_time;
	}

	public Boolean getIsTeamLeader() {
		return is_team_leader;
	}

	public void setIsTeamLeader(Boolean is_team_leader) {
		this.is_team_leader = is_team_leader;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorkNumber() {
		return work_number;
	}

	public Boolean getIsLogin() {
		return is_login;
	}

	public void setIsLogin(Boolean is_login) {
		this.is_login = is_login;
	}

	public void setWorkNumber(String work_number) {
		this.work_number = work_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOperatorId() {
		return operator_id;
	}

	public void setOperatorId(Integer operator_id) {
		this.operator_id = operator_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return create_time;
	}

	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getGroupId() {
		return group_id;
	}

	public void setGroupId(Integer group_id) {
		this.group_id = group_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExtIpId() {
		return ext_ip_id;
	}

	public void setExtIpId(String ext_ip_id) {
		this.ext_ip_id = ext_ip_id;
	}

	public Boolean getIsDel() {
		return is_del;
	}

	public void setIsDel(Boolean is_del) {
		this.is_del = is_del;
	}

	public Boolean getIsOnline() {
		return is_online;
	}

	public void setIsOnline(Boolean is_online) {
		this.is_online = is_online;
	}

	public String getRoleId() {
		return role_id;
	}

	public void setRoleId(String role_id) {
		this.role_id = role_id;
	}

	public Integer getExtNum() {
		return ext_num;
	}

	public void setExtNum(Integer ext_num) {
		this.ext_num = ext_num;
	}

	public PublicEnum.WorkType getWorkType() {
		return work_type;
	}

	public void setWorkType(PublicEnum.WorkType work_type) {
		this.work_type = work_type;
	}

	public Integer getWaitingIndex() {
		return waiting_index;
	}

	public void setWaitingIndex(Integer waiting_index) {
		this.waiting_index = waiting_index;
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

	public String getDelReason() {
		return del_reason;
	}

	public void setDelReason(String del_reason) {
		this.del_reason = del_reason;
	}
}
