package com.qingpu.phone.user.dao;

import com.qingpu.phone.common.dao.BaseDaoImpl;
import com.qingpu.phone.user.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserById(String openid){
		// TODO Auto-generated method stub
		return (List<User>) findByHqlParams("from User where openid=?", new Object[]{openid});
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserRole getUserRoleByUserId(String id) {
		UserRole userRole = null;
		List<UserRole> ret = (List<UserRole>) findByHqlParams("from UserRole where user_id=?", new Object[]{id});
		if(ret.size() > 0){
			userRole = ret.get(0);
		}

		return userRole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Roles getRolesByRoleId(String roleid) {
		return (Roles) get(Roles.class, roleid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissions> getPermissionsByRoleId(String roleid) {
		List<RolePermission> list = (List<RolePermission>) findByHqlParams("from RolePermission where role_id = ?", new Object[]{roleid});
		String idStr = "";
		for(int i = 0; i < list.size(); i++){
			idStr += list.get(i).getPermissionId()+",";
		}
		idStr = idStr.substring(0, idStr.length()-1);

		return (List<Permissions>) findByHqlParams("from Permissions where id in(" + idStr + ")", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissions> getPermissionsByFidAndId(String fid, String idArr) {
		String hql = "from Permissions where fid = "+fid+" and id in ("+idArr+")";
		List<Permissions> list = (List<Permissions>) findByHql(hql);

		return list;
	}

	@Override
	public void saveOrUpdateUserRole(UserRole userRole) {
		this.saveOrUpdate(userRole);
	}

	@Override
	public void createRolePermissionList(int roleid, List<RolePermission> list) {
		//先删除roleid的权限列表
		execQueryHql("delete RolePermission r where r.role_id = ?", new Object[]{roleid});
		//再增加新的权限列表
		this.saveOrUpdateAll(list);
	}

	@Override
	public List<Permissions> getAllPermissionsList() {
		return (List<Permissions>) this.getAll(Permissions.class);
	}
}
