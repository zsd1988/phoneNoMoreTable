package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.EncryptUtils;
import com.qingpu.phone.common.utils.RemoteCallException;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.condition.PermissionsCondition;
import com.qingpu.phone.user.condition.RolePermissionCondition;
import com.qingpu.phone.user.entity.Permissions;
import com.qingpu.phone.user.entity.RolePermission;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("permissionsService")
@Transactional
public class PermissionsService {
	private static Logger logger = Logger.getLogger(PermissionsService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	UserRoleService userRoleService;

	Integer showIndex = 0;

	@Resource
	private RolePermissionService rolePermissionService;

	List<String> superIdList = new ArrayList<>();	// 超级管理员默认菜单列表

	/**
	 * 创建
	 * @param permissions
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Permissions create(Permissions permissions) throws Exception{
		if(permissions != null && StringUtils.isBlank(permissions.getId())){
			permissions.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(permissions);
		return (Permissions) baseDao.save(permissions);
	}


	/**
	 * 更新
	 * @param permissions
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Permissions permissions)  throws Exception {
		CommonUtils.checkEntity(permissions);
		baseDao.update(permissions);
	}


	/**
	 * 删除
	 * @param permissions
	 */
	public void delete(Permissions permissions) {
		permissions.setIsDel(true);
		baseDao.update(permissions);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Permissions get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (Permissions)baseDao.get(Permissions.class, id);
	}


	/**
	 * 按条件查找
	 * @param permissionsCondition
	 * @return
	 */
	public List<Permissions> list(PermissionsCondition permissionsCondition){
		return  permissionsCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param permissionsCondition
	 * @param sorter
	 * @return
	 */
	public List<Permissions> list(PermissionsCondition permissionsCondition, Sorter sorter){
		return  permissionsCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param permissionsCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Permissions> list(PermissionsCondition permissionsCondition, Range range, Sorter sorter){
		PaginationSupport<Permissions> permissionsPaginationSupport = (PaginationSupport<Permissions>)permissionsCondition.pageList(sessionFactory, sorter, range);
		return permissionsPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param permissionsCondition
	 * @return
	 */
	public Long countByCondition(PermissionsCondition permissionsCondition){
		return   permissionsCondition.countByCondition(sessionFactory);
	}

	/**
	 * 初始化
	 */
	public void init(String id, String fid, String permissionName, String url, String className, Permissions.Tag tag){
		showIndex ++;
		Permissions permissions = this.get(id);
		if(permissions == null){
			permissions = new Permissions();
			permissions.setId(id);
			permissions.setFid(fid);
			permissions.setPermissionName(permissionName);
			permissions.setPermissionUrl(url);
			permissions.setClassName(className);
			permissions.setShowIndex(showIndex);
			permissions.setCanDelete(false);
			permissions.setTag(tag);
			permissions.setFolderType(Permissions.FolderType.No);
			try {
				this.create(permissions);
			}catch (Exception e){
				logger.error("初始化菜单失败：" + e.getMessage());
			}
		}
	}

	private void initAdmin(String id, String fid, String permissionName, String url, String className){
		this.init(id, fid, permissionName, url, className, Permissions.Tag.Admin);
	}

	private void initManager(String id, String fid, String permissionName, String url, String className){
		this.init(id, fid, permissionName, url, className, Permissions.Tag.Manager);
	}

	private void initEmployee(String id, String fid, String permissionName, String url, String className){
		this.init(id, fid, permissionName, url, className, Permissions.Tag.Employee);
	}

	/**
	 * 超级管理员
	 */
	private void initAdmin(){
		String rootId = QingpuConstants.Permissions_Root_id;
		String firstParentId = "5bda2a6c15b049b7bca7932d73a6ba6b";
		String secondParentId = firstParentId;
		String thirdParentId = firstParentId;
		String id = firstParentId;

		/*
		群呼管理
		 */
		id = "8d42384b697e439fac8f56a4c643cd51";
		firstParentId = id;
		superIdList.add(id);
		initManager(id, rootId,"系统管理", "", null);

		id = "4337790b12ca4b7dbda3a4e06c006572";
		superIdList.add(id);
		initAdmin(id, firstParentId,"角色管理", "common/roles", null);

		id = "e952a821437046af9f6dadff330e7503";
		superIdList.add(id);
		initAdmin(id, firstParentId,"用户管理", "common/user", null);

		id = "92651e7b619e42c3b797181a7da6f1c9";
		superIdList.add(id);
		initAdmin(id, firstParentId,"用户信息", "common/userInfo", null);

		id = "d6e501f6af9a4e14bee07e68500913e1";
		superIdList.add(id);
		initEmployee(id, firstParentId,"项目管理", "system/project", null);

		id = "d66d5f61a1134f09bb930fc9cad3925d";
		superIdList.add(id);
		initManager(id, firstParentId,"群呼管理", "system/callManagement", null);

		id = "dce2c8b0b98b48fdaddf1df684a98f22";
		superIdList.add(id);
		initManager(id, firstParentId,"抽奖记录", "system/luckDraw", null);

		id = "fd2311b6e92146a1b1a0dcd14942d595";
		superIdList.add(id);
		initManager(id, firstParentId,"中奖词条", "system/intentionHint", null);

		id = "f3f952cab7d945458a33a65ee5ab9c24";
		superIdList.add(id);
		initManager(id, firstParentId,"每日词条", "system/dayHint", null);


		id = "28e7735d98aa42fcbb029d2f485a3c4b";
		superIdList.add(id);
		initManager(id, firstParentId,"公告推送", "system/postMessage", null);


		/*
		客户管理
		 */
		id = "8b1fcc0e3dd6418abaa662860b9cf157";
		firstParentId = id;
		superIdList.add(id);
		initManager(id, rootId,"客户管理", "", "ti-paint-bucket");

		id = "557a43a75fd04f02a961f037b81c3f66";
		superIdList.add(id);
		initManager(id, firstParentId,"客户分组", "customerManagement/customerGrouping", null);


		id = "a4a561dd22b3468f9a1326e84e263d9d";
		superIdList.add(id);
		initManager(id, firstParentId,"待分配资源", "customerManagement/customerAllocated", null);

		/*
		系统设置
		 */
		id = "67e7625151014e3a99f1050732b7773b";
		firstParentId = id;
		superIdList.add(id);
		initEmployee(id, rootId,"系统设置", "", null);

		id = "1be92cbcb0474cad844232c6a4742114";
		superIdList.add(id);
		initEmployee(id, firstParentId,"端口管理", "systemSetting/portManagement", null);

		id = "42046d352b4d441f9cf8051ed3b2ceb7";
		superIdList.add(id);
		initEmployee(id, firstParentId,"分机IP绑定", "systemSetting/extAndIP", null);

		id = "233a3eac02034f0f88e55454f4964f7d";
		superIdList.add(id);
		initEmployee(id, firstParentId,"版本管理", "systemSetting/app", null);

		id = "f0c0e865426c4b11824a0eafbdd3f1f2";
		superIdList.add(id);
		initAdmin(id, firstParentId,"参数设置", "systemSetting/parameter", null);


		/*
		统计分析
		 */
		id = QingpuConstants.Permissions_EmployeeFloor_id;
		firstParentId = id;
		superIdList.add(id);
		initEmployee(id, rootId,"统计分析", "", null);

		id = "e54f8b58c68b4becacc6b1e50f7aef2a";
		superIdList.add(id);
		initEmployee(id, firstParentId,"话务统计", "statistics/trafficStatistics", null);

		id = "12fb8d5fd50448fd8facc3bfa909c4a9";
		superIdList.add(id);
		initManager(id, firstParentId,"通话记录", "statistics/callRecords", null);

		id = "755b4526b87544769936507e43fnc185";
		superIdList.add(id);
		initEmployee(id, firstParentId,"端口接通率", "statistics/portCompletionRate", null);

		id = "ba79d385c9af48259bb77ea81e698c85";
		superIdList.add(id);
		initEmployee(id, firstParentId,"语音通话", "statistics/groupCallDetail", null);

		id = "d6f7317dae57496a81e5a430562bdf28";
		superIdList.add(id);
		initEmployee(id, firstParentId,"意向转化率", "statistics/conversionRate", null);

		id = "b807eafbe2674b10b191ce12b89605a8";
		superIdList.add(id);
		initEmployee(id, firstParentId,"周报统计", "statistics/week", null);


		/**
		 * 分配默认菜单
		 */
		rolePermissionService.init(superIdList, QingpuConstants.Roles_Super_id);
	}


	/**
	 * 初始化角色表
	 */
	public void init(){
		this.initAdmin();
	}

	/**
	 * 根据角色id获取菜单列表
	 * @param roleId
	 * @return
	 */
	public List<Permissions> getByRoleId(String roleId){
		List<Permissions> permissionsList = new ArrayList<>();
		RolePermissionCondition rolePermissionCondition = new RolePermissionCondition();
		rolePermissionCondition.setRoleId(roleId);
		List<RolePermission> rolePermissionList = rolePermissionCondition.list(sessionFactory);
		for(RolePermission rolePermission : rolePermissionList){
			String permissionId = rolePermission.getPermissionId();
			Permissions permissions = this.get(permissionId);
			if(permissions != null){
				permissionsList.add(permissions);
			}
		}
		return permissionsList;
	}

	/**
	 * 根据父级菜单id获取子菜单
	 * @param permissionsList
	 * @param parentId
	 * @return
	 */
	public List<Permissions> getListByParentId(List<Permissions> permissionsList, String parentId){
		List<Permissions> resultList = new ArrayList<>();
		if(StringUtils.isNotBlank(parentId)){
			for(Permissions permissions : permissionsList){
				if(parentId.equals(permissions.getFid())){
					resultList.add(permissions);
				}
			}
		}
		Collections.sort(resultList,new Comparator<Permissions>(){
			public int compare(Permissions arg0, Permissions arg1) {
				if(arg0.getShowIndex().compareTo(arg1.getShowIndex()) != 0){
					// 按照显示序号排列，序号越小越前
					return arg0.getShowIndex().compareTo(arg1.getShowIndex());
				}else{
					// 按照创建时间排列，创建时间越大越前
					return arg1.getCreateTime().compareTo(arg0.getCreateTime());
				}
			}
		});
		return resultList;
	}

	/**
	 * 根据用户id获取菜单列表
	 * @param user
	 * @return
	 */
	public List<Permissions> getListByUserId(User user){
		List<Permissions> permissionsList = new ArrayList<>();
		try {
			String roleId = user.getRoleId();
			if(  StringUtils.isNotBlank(roleId)) {
				//获取用户权限
				List<Permissions> pList = this.getByRoleId(roleId);
				//获取fid列表，然后根据fid组建每个子菜单的结构
				String[] fidArray = new String[pList.size()];
				//查询id列表
				//去除重复元素
				List<String> fidList = new ArrayList<>();
				for (int i = 0; i < fidArray.length; i++) {
					if (!fidList.contains(fidArray[i])) {
						fidList.add(fidArray[i]);
					}
				}
				Collections.sort(pList);
				Permissions permissionsInfo=new Permissions();
				permissionsInfo.setId("root");
				permissionsList(permissionsInfo,pList,permissionsList);
			}else{
				throw new RemoteCallException("未分配角色，请通知管理员");
			}
		}catch (Exception e){
			throw new RemoteCallException(e.getMessage());
		}
		return permissionsList;
	}



	/**
	 * 递归查询资源
	 */
	public static void permissionsList(Permissions permissionsInfo,List<Permissions> permissionsList,List<Permissions> newPermissionsList){
		List<Permissions>  permissionsListInfo=new ArrayList<Permissions>();
		for(Permissions permissions:permissionsList){
			if(permissions.getFid().equals(permissionsInfo.getId())){
				permissionsListInfo.add(permissions);
			}
		}
		if(permissionsListInfo!=null&&permissionsListInfo.size()>0){
			permissionsInfo.setPermissionsList(permissionsListInfo);
			for(Permissions permissions1: permissionsListInfo ){
				if(permissions1.getFid().equals("root")){
					newPermissionsList.add(permissions1);
				}
				permissionsList(permissions1,permissionsList,newPermissionsList);
			}
		}
	}
}
