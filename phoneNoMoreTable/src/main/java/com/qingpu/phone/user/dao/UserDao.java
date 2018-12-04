package com.qingpu.phone.user.dao;

import java.util.List;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.user.entity.*;

/**
 * 用户表和用户权限操作Dao
 */
public interface UserDao extends BaseDao{

	/**
	 * 使用用户id查找用户
	 * @param openid
	 * @return
	 */
	List<User> getUserById(String openid);

	/**
	 * 使用用户id查找用户角色对照
	 * @param id
	 * @return
	 */
	UserRole getUserRoleByUserId(String id);

	/**
	 * 使用角色id查找角色对象
	 * @param roleid
	 * @return
	 */
	Roles getRolesByRoleId(String roleid);

	/**
	 * 使用角色id查找该角色的权限列表
	 * @param roleid
	 * @return
	 */
	List<Permissions> getPermissionsByRoleId(String roleid);

	/**
	 * 查找父菜单的子菜单列表
	 * @param fid 父id
	 * @param idArr 子id的字符串列表
	 * @return
	 */
	List<Permissions> getPermissionsByFidAndId(String fid, String idArr);

	/**
	 * 新增或者更新用户的角色id
	 * @param userRole
	 */
	void saveOrUpdateUserRole(UserRole userRole);

	/**
	 * 创建新的角色权限列表，先将旧的角色权限删除重新创建，在后台管理的时候不同角色默认加载所有的权限列表，然后超级管理员进行勾选选择
	 * @param list
	 */
	void createRolePermissionList(int roleid, List<RolePermission> list);

	/**
	 * 查询所有权限列表
	 * @return
	 */
	List<Permissions> getAllPermissionsList();
}
