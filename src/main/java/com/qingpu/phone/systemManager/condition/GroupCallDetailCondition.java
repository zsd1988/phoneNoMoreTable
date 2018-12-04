package com.qingpu.phone.systemManager.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.PublicEnum.GroupCallDetailStatus;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

public class GroupCallDetailCondition extends QPCondition {
	Boolean bDel = false;
	GroupCallDetailStatus status = null;
	GroupCallDetailStatus[] inStatus = null;
	String projectId;
	String groupCallId;
	Boolean notNullRefuseRecordPath;
	Date dayStartStartTime;
	Date dayEndStartTime;
	Boolean isNotNullFirstText;
	PublicEnum.VoiceType voiceType;
	Date ltCreateTime;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(GroupCallDetail.class);
		if(bDel != null){
			this.criteria.add( Restrictions.eq("is_del", bDel) );
		}
		if(status != null){
			this.criteria.add( Restrictions.eq("status", status) );
		}
		if(ltCreateTime != null){
			this.criteria.add( Restrictions.lt("create_time", ltCreateTime) );
		}
		if(voiceType != null){
			this.criteria.add( Restrictions.eq("voice_type", voiceType) );
		}
		if(dayStartStartTime != null){
			this.criteria.add( Restrictions.ge("start_time", DateUtil.dayStart(dayStartStartTime)) );
		}
		if(dayEndStartTime != null){
			this.criteria.add( Restrictions.lt("start_time", DateUtil.dayEnd(dayEndStartTime)) );
		}
		if(inStatus != null){
			this.criteria.add( Restrictions.in("status", inStatus) );
		}
		if(notNullRefuseRecordPath != null){
			this.criteria.add( Restrictions.isNotNull("refuse_record_path") );
		}
		if(isNotNullFirstText != null){
			if(isNotNullFirstText){
				this.criteria.add( Restrictions.isNotNull("first_text") );
			}else{
				this.criteria.add( Restrictions.isNull("first_text") );
			}
		}
		if(StringUtils.isNotBlank(projectId)){
			this.criteria.add( Restrictions.eq("project_id", projectId) );
		}
		if(StringUtils.isNotBlank(groupCallId)){
			this.criteria.add( Restrictions.eq("group_call_id", groupCallId) );
		}
		super.initCriteria(sessionFactory);
	}

	public void setLtCreateTime(Date ltCreateTime) {
		this.ltCreateTime = ltCreateTime;
	}

	public void setVoiceType(PublicEnum.VoiceType voiceType) {
		this.voiceType = voiceType;
	}

	public void setNotNullFirstText(Boolean notNullFirstText) {
		isNotNullFirstText = notNullFirstText;
	}

	public void setDayStartStartTime(Date dayStartStartTime) {
		this.dayStartStartTime = dayStartStartTime;
	}

	public void setDayEndStartTime(Date dayEndStartTime) {
		this.dayEndStartTime = dayEndStartTime;
	}

	public void setNotNullRefuseRecordPath(Boolean notNullRefuseRecordPath) {
		this.notNullRefuseRecordPath = notNullRefuseRecordPath;
	}

	public void setStatus(GroupCallDetailStatus status) {
		this.status = status;
	}

	public void setInStatus(GroupCallDetailStatus[] inStatus) {
		this.inStatus = inStatus;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setGroupCallId(String groupCallId) {
		this.groupCallId = groupCallId;
	}

	public void setbDel(Boolean bDel) {
		this.bDel = bDel;
	}
}
