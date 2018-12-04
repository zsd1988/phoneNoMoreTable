package com.qingpu.phone.common.condition;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.Source;
import com.qingpu.phone.common.utils.criteria.QPCondition;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class SourceCondition extends QPCondition {

	private String   likeSourceName;
	private List<String> permissionIdList;			//文件ID集合
	private String   permissionId;			//文件ID
	private String   sourceName;				//资源名称
	private Boolean  isDel;
	private String   md5;
	PublicEnum.SourceType sourceType;

	public void initCriteria(SessionFactory sessionFactory){
		this.criteria = sessionFactory.getCurrentSession().createCriteria(Source.class);
		if(StringUtils.isNotBlank(permissionId)){
			this.criteria.add( Restrictions.ne("permission_id", permissionId) );
		}
		if(StringUtils.isNotBlank(sourceName)){
			this.criteria.add( Restrictions.eq("source_name", sourceName) );
		}
		if(isDel!=null) {
			this.criteria.add(Restrictions.eq("is_del", isDel));
		}
		if(sourceType!=null) {
			this.criteria.add(Restrictions.eq("source_type", sourceType));
		}
		if(permissionIdList!=null&&permissionIdList.size()>0){
			if(permissionIdList.size() == 1){
				this.criteria.add(Restrictions.eq("permission_id", permissionIdList.get(0)));
			}else{
				this.criteria.add(Restrictions.in("permission_id", permissionIdList));
			}
		}
		if(StringUtils.isNotBlank(likeSourceName)){
			this.criteria.add( Restrictions.like("source_name", likeSourceName, MatchMode.ANYWHERE) );
		}
		if(StringUtils.isNotBlank(md5)){
			this.criteria.add( Restrictions.eq("md5", md5) );
		}

		super.initCriteria(sessionFactory);
	}

	public void setSourceType(PublicEnum.SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Boolean getDel() {
		return isDel;
	}

	public void setDel(Boolean del) {
		isDel = del;
	}

	public List<String> getPermissionIdList() {
		return permissionIdList;
	}

	public void setPermissionIdList(List<String> permissionIdList) {
		this.permissionIdList = permissionIdList;
	}

	public String getLikeSourceName() {
		return likeSourceName;
	}

	public void setLikeSourceName(String likeSourceName) {
		this.likeSourceName = likeSourceName;
	}
}
