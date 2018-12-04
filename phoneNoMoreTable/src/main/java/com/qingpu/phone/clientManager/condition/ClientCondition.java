package com.qingpu.phone.clientManager.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.clientManager.entity.Client;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientCondition extends QPCondition {
	Boolean bDel;
	String projectId;
	PublicEnum.ImportTag importTag;
	Boolean bDistribute; //�Ƿ���
	String groupId;
	PublicEnum.ClientGroupType clientGroupType;
	String  phoneLike;
	String  phone;
	PublicEnum.ClientStatus clientStatus;
	Boolean  isEmptyGroupId;
	Date ltLastUpdateTime;
	Boolean notNullGroupId;
	Boolean notNullGroupType;
	List<String> neGroupIdList;
	PublicEnum.ClientGroupType neClientGroupType;

	Boolean nullGroupId;
	PublicEnum.ClientStatus[] reviewStatusArr;
	Boolean onceStatus;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Client.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(nullGroupId != null){
			this.criteria.add( Restrictions.isNull("group_id") );
		}
		if(reviewStatusArr != null){
			this.criteria.add( Restrictions.in("review_status", reviewStatusArr) );
		}
		if(onceStatus != null){
			this.criteria.add( Restrictions.eq("once_status", onceStatus) );
		}
		if(notNullGroupId != null){
			this.criteria.add( Restrictions.isNotNull("group_id") );
		}
		if(notNullGroupType != null){
			this.criteria.add( Restrictions.isNotNull("group_type") );
		}
		if(neGroupIdList != null){
			Integer size = neGroupIdList.size();
			if(size == 1){
				this.criteria.add( Restrictions.ne("group_id", neGroupIdList.get(0)) );
			}else{
				this.criteria.add( Restrictions.not(Restrictions.in("group_id", neGroupIdList)) );
			}
		}
		if(neClientGroupType != null){
			this.criteria.add( Restrictions.ne("group_type", neClientGroupType) );
		}
		if(ltLastUpdateTime != null){
			this.criteria.add( Restrictions.lt("last_update_time", ltLastUpdateTime) );
		}
		if(bDistribute != null){
			this.criteria.add( Restrictions.eq("is_distribute", bDistribute) );
		}
		if(importTag != null){
			this.criteria.add( Restrictions.eq("import_tag", importTag) );
		}

		if(clientGroupType != null){
			this.criteria.add( Restrictions.eq("group_type", clientGroupType) );
		}

		if(StringUtil.isNotNull(groupId)){
			this.criteria.add( Restrictions.eq("group_id", groupId) );
		}

		if(isEmptyGroupId!=null&&isEmptyGroupId){
			this.criteria.add( Restrictions.isNull("group_id"));
		}
		if(StringUtil.isNotNull(phone)){
			this.criteria.add( Restrictions.eq("phone", phone) );
		}

		if(StringUtil.isNotNull(clientStatus)){
			this.criteria.add( Restrictions.eq("status", clientStatus) );
		}

		if(StringUtil.isNotNull(phoneLike)){
			if(phoneLike.length() == 11){
				List<String> phoneList = new ArrayList<>();
				phoneList.add(phoneLike);
				phoneList.add("0" + phoneLike);
				in("phone", phoneList);
			}else{
				this.criteria.add( Restrictions.like("phone", "%"+phoneLike) );
			}
		}

		if(StringUtils.isNotBlank(projectId)){
			this.criteria.add( Restrictions.eq("project_id", projectId) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setNullGroupId(Boolean nullGroupId) {
		this.nullGroupId = nullGroupId;
	}

	public void setReviewStatusArr(PublicEnum.ClientStatus[] reviewStatusArr) {
		this.reviewStatusArr = reviewStatusArr;
	}

	public void setOnceStatus(Boolean onceStatus) {
		this.onceStatus = onceStatus;
	}

	public void setNotNullGroupId(Boolean notNullGroupId) {
		this.notNullGroupId = notNullGroupId;
	}

	public void setNotNullGroupType(Boolean notNullGroupType) {
		this.notNullGroupType = notNullGroupType;
	}

	public void setNeGroupIdList(List<String> neGroupIdList) {
		this.neGroupIdList = neGroupIdList;
	}

	public void setNeClientGroupType(PublicEnum.ClientGroupType neClientGroupType) {
		this.neClientGroupType = neClientGroupType;
	}

	public void setLtLastUpdateTime(Date ltLastUpdateTime) {
		this.ltLastUpdateTime = ltLastUpdateTime;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setImportTag(PublicEnum.ImportTag importTag) {
		this.importTag = importTag;
	}

	public void setbDistribute(Boolean bDistribute) {
		this.bDistribute = bDistribute;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setClientGroupType(PublicEnum.ClientGroupType clientGroupType) {
		this.clientGroupType = clientGroupType;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhoneLike(String phoneLike) {
		this.phoneLike = phoneLike;
	}

	public void setClientStatus(PublicEnum.ClientStatus clientStatus) {
		this.clientStatus = clientStatus;
	}

	public void setEmptyGroupId(Boolean emptyGroupId) {
		isEmptyGroupId = emptyGroupId;
	}
}
