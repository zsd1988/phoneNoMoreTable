package com.qingpu.phone.user.condition;

import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.user.entity.UserGroup;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserGroupCondition extends QPCondition {
	Boolean bDel = false;
	private Integer  fid;
	private String   groupName;
	private int noId;
	private Integer   noFid;
	List<Integer> fidList;


	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(UserGroup.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}

		if(fid != null&&fid>0){
			this.criteria.add( Restrictions.eq("fid", fid) );
		}
		in("fid", fidList);

		if(noFid != null&&noFid>0){
			this.criteria.add( Restrictions.ne("fid", noFid) );
		}

		if(StringUtils.isNotBlank(groupName)){
			this.criteria.add( Restrictions.eq("group_name", groupName) );
		}

		if(noId>0){
			this.criteria.add( Restrictions.ne("id", noId) );
		}

		super.initCriteria(sessionFactory);
	}

	public void setFidList(List<Integer> fidList) {
		this.fidList = fidList;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setNoId(int noId) {
		this.noId = noId;
	}

	public void setNoFid(Integer noFid) {
		this.noFid = noFid;
	}
}
