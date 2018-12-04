package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.JiangHuRank;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class JiangHuRankCondition extends QPCondition {
	Boolean bDel;
	List<Integer> userIdList;
	String likeUserName;
	Integer paiId;
	Date gtCreateTime;
	Date ltCreateTime;
	Date geCreateTime;
	Date createTime;
	PublicEnum.RoleType roleType;
	List<String> inUserName;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(JiangHuRank.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		in("user_name", inUserName);
		eq("roleType", roleType);
		eq("pai_id", paiId);
		in("user_id", userIdList);
		like("user_name", likeUserName, MatchMode.ANYWHERE);
		lt("create_time", ltCreateTime);
		gt("create_time", gtCreateTime);
		ge("create_time", geCreateTime);
		eq("create_time", createTime);
		super.initCriteria(sessionFactory);
	}

	public void setInUserName(List<String> inUserName) {
		this.inUserName = inUserName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setRoleType(PublicEnum.RoleType roleType) {
		this.roleType = roleType;
	}

	public void setGeCreateTime(Date geCreateTime) {
		this.geCreateTime = geCreateTime;
	}

	public void setGtCreateTime(Date gtCreateTime) {
		this.gtCreateTime = gtCreateTime;
	}

	public void setLtCreateTime(Date ltCreateTime) {
		this.ltCreateTime = ltCreateTime;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public void setLikeUserName(String likeUserName) {
		this.likeUserName = likeUserName;
	}

	public void setPaiId(Integer paiId) {
		this.paiId = paiId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
