package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class UserCondition extends QPCondition {
	private String openId;
	private Integer neId;
	String nickName;
	private String login_name;
	String likeLoginName;
	String roleId;
	List<String> roleIdList;
	Boolean isOnline;
	Boolean isDel;
	private Integer ext_num;
	private String work_number;
	private Integer  notNullGroupId;
	Integer groupId;
	Boolean isLogin;
	String name;
	PublicEnum.WorkType workType;
	Integer neGroupId;
	Date leCreateTime;
	Date leNewCreateTime;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		if(StringUtils.isNotBlank(openId)){
			this.criteria.add( Restrictions.eq("openid", openId) );
		}
		eq("name", name);
		if(null != neId){
			this.criteria.add( Restrictions.ne("id", neId) );
		}
		le("create_time", leCreateTime);
		le("new_create_time", leNewCreateTime);
		ne("group_id", neGroupId);
		eq("work_type", workType);
		if(null != isDel){
			this.criteria.add( Restrictions.eq("is_del", isDel) );
		}
		if(null != isLogin){
			this.criteria.add( Restrictions.eq("is_login", isLogin) );
		}
		if(null != isOnline){
			this.criteria.add( Restrictions.eq("is_online", isOnline) );
		}
		if(StringUtils.isNotBlank(login_name)){
			this.criteria.add( Restrictions.eq("login_name", login_name) );
		}
		if(StringUtils.isNotBlank(likeLoginName)){
			this.criteria.add( Restrictions.like("login_name", likeLoginName, MatchMode.ANYWHERE) );
		}
		if(StringUtils.isNotBlank(nickName)){
			this.criteria.add( Restrictions.like("nickname", nickName, MatchMode.ANYWHERE) );
		}
		if(StringUtils.isNotBlank(roleId)){
			this.criteria.add( Restrictions.eq("role_id", roleId) );
		}
		if(roleIdList != null){
			if (roleIdList.size() > 0) {
				if (roleIdList.size() == 1) criteria.add(Restrictions.eq("role_id", roleIdList.get(0)));
				if (roleIdList.size() > 1) criteria.add(Restrictions.in("role_id", roleIdList));
			}
		}
		if(ext_num!=null&&ext_num>0){
			this.criteria.add( Restrictions.eq("ext_num", ext_num) );
		}

		if(StringUtils.isNotBlank(work_number)){
			this.criteria.add( Restrictions.eq("work_number", work_number) );
		}

		if(groupId != null){
			this.criteria.add( Restrictions.eq("group_id", groupId) );
		}

		if(notNullGroupId!=null){
			this.criteria.add( Restrictions.isNotNull("group_id") );
		}
		super.initCriteria(sessionFactory);
	}

	public void setLeNewCreateTime(Date leNewCreateTime) {
		this.leNewCreateTime = leNewCreateTime;
	}

	public void setLeCreateTime(Date leCreateTime) {
		this.leCreateTime = leCreateTime;
	}

	public void setNeGroupId(Integer neGroupId) {
		this.neGroupId = neGroupId;
	}

	public void setWorkType(PublicEnum.WorkType workType) {
		this.workType = workType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLogin(Boolean login) {
		isLogin = login;
	}

	public void setDel(Boolean del) {
		isDel = del;
	}

	public void setOnline(Boolean online) {
		isOnline = online;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public void setLikeLoginName(String likeLoginName) {
		this.likeLoginName = likeLoginName;
	}

	public void setNeId(Integer neId) {
		this.neId = neId;
	}

	public void setLoginName(String login_name) {
		this.login_name = login_name;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public void setExtNum(Integer ext_num) {
		this.ext_num = ext_num;
	}

	public void setWorkNumber(String work_number) {
		this.work_number = work_number;
	}

	public void setNotNullGroupId(Integer notNullGroupId) {
		this.notNullGroupId = notNullGroupId;
	}
}
