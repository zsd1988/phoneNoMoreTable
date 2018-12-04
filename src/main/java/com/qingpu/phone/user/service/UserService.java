package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.DayHintCondition;
import com.qingpu.phone.systemManager.condition.IntentionHintCondition;
import com.qingpu.phone.systemManager.condition.LuckDrawCondition;
import com.qingpu.phone.systemManager.entity.DayHint;
import com.qingpu.phone.systemManager.entity.IntentionHint;
import com.qingpu.phone.systemManager.entity.LuckDraw;
import com.qingpu.phone.systemManager.entity.PostMessage;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.*;
import com.qingpu.phone.systemSetting.service.ParameterService;
import com.qingpu.phone.user.condition.JiangHuPaiCondition;
import com.qingpu.phone.user.condition.JiangHuRenCondition;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.condition.UserInfoCondition;
import com.qingpu.phone.user.dao.UserDao;
import com.qingpu.phone.user.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

@Service("userService")
@Transactional
public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);

	@Resource
	private UserDao userDao;

	@Resource
	private BaseDao baseDao;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	UserInfoService userInfoService;


	@Resource
	GroupCallService groupCallService;

	@Resource
	JiangHuRenService jiangHuRenService;

	@Resource
	ParameterService parameterService;

	@Resource
	JiangHuPaiService jiangHuPaiService;

	@Resource
	LuckDrawService luckDrawService;

	@Resource
	JiangHuRankService jiangHuRankService;

	@Resource
	JiangHuPaiRankService jiangHuPaiRankService;

	@Resource
	IntentionHintService intentionHintService;

	@Resource
	PostMessageService postMessageService;

	@Resource
	DayHintService dayHintService;


	/**
	 * 创建
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public User create(User user) throws Exception{
		UserCondition userCondition = new UserCondition();
		userCondition.setRoleId(user.getRoleId());
		userCondition.setName(user.getName());
		List<User> userList = this.list(userCondition);
		if( ! userList.isEmpty()){
			throw new Exception("已存在相同角色的同名用户");
		}
		CommonUtils.checkEntity(user);
		return (User) baseDao.save(user);
	}


	/**
	 * 更新
	 * @param user
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(User user)  throws Exception {
		CommonUtils.checkEntity(user);
		baseDao.update(user);
	}


	/**
	 * 删除
	 * @param user
	 */
	public void delete(User user) {
		user.setIsDel(true);
		user.setDelTime(new Date());
		baseDao.update(user);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public User get(Integer id) {
		if(id == null){
			return null;
		}
		return (User)baseDao.get(User.class, id);
	}


	/**
	 * 按条件查找
	 * @param userCondition
	 * @return
	 */
	public List<User> list(UserCondition userCondition){
		return  userCondition.list(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param userCondition
	 * @return
	 */
	public List<Object[]> listCustom(UserCondition userCondition){
		return  userCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param userCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(UserCondition userCondition, int num){
		return  userCondition.list(sessionFactory, num);
	}

	/**
	 * 按条件排序
	 * @param userCondition
	 * @param sorter
	 * @return
	 */
	public List<User> list(UserCondition userCondition, Sorter sorter){
		return  userCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param userCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<User> list(UserCondition userCondition, Range range, Sorter sorter){
		PaginationSupport<User> userRolePaginationSupport = (PaginationSupport<User>) userCondition.pageList(sessionFactory, sorter, range);
		return userRolePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param userCondition
	 * @return
	 */
	public Long countByCondition(UserCondition userCondition){
		return   userCondition.countByCondition(sessionFactory);
	}
	
	public User getUserByOpenid(String openid) {
		// TODO Auto-generated method stub
		List<User> list = userDao.getUserById(openid);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return (User) userDao.save(user);
	}

	
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	public UserRole getUserRoleByOpenid(String openid) {
		// TODO Auto-generated method stub
		UserRole userRole = userDao.getUserRoleByUserId(openid);

		return userRole;
	}

	public Roles getUserRoleNameByRoleId(String roleid) {
		return userDao.getRolesByRoleId(roleid);
	}

	public List<Permissions> getUserPermissionByRoleId(String role_id) {
		// TODO Auto-generated method stub
		List<Permissions> list = userDao.getPermissionsByRoleId(role_id);

		return list;
	}

	public List<Permissions> getUserPermissionByFidAndId(String fid, String idArr) {
		// TODO Auto-generated method stub
		List<Permissions> list = userDao.getPermissionsByFidAndId(fid, idArr);

		return list;
	}

	public void createRolePermissionList(int roleid, List<RolePermission> list) {
		userDao.createRolePermissionList(roleid, list);
	}

	public List<Permissions> getAllPermissionsList() {
		return userDao.getAllPermissionsList();
	}

	/**
	 * 初始化表数据
	 * @param id
	 * @param name
	 */
	private void init(Integer id, String workNum, String name, String roleId){
		User user = this.get(id);
		if(user == null){
			user = new User();
			user.setId(id);
			user.setWorkNumber(workNum);
			user.setPassword(EncryptUtils.encryptByMD5("123456"));
			user.setName(name);
			user.setRoleId(roleId);
			this.saveUser(user);
		}
	}


	@Transactional(rollbackFor = Exception.class)
	public void changePassword(Integer id,String passwordBefore,String password) throws Exception{
		if(StringUtils.isBlank(password)){
			throw new Exception("新密码不能为空");
		}
		User user = this.get(id);
		if(user != null){
			if(user.getPassword().equals(passwordBefore)){
				user.setPassword(password);
				this.update(user);
			}else{
				throw new Exception("输入的原密码不正确！");
			}
		}
	}

	/**
	 * 初始化表数据
	 */
	public void init() {
		/*
		超级管理员
		 */
		// 建立表数据
		init(QingpuConstants.User_Admin_Id,  QingpuConstants.User_Admin_WorkNum,  QingpuConstants.User_Admin_WorkNum, QingpuConstants.Roles_Super_id);


		/*
		重置用户为未登录状态
		 */
		List<User> userList = this.list(new UserCondition());
		for(User user : userList){
			user.setIsLogin(false);
			this.updateUser(user);
		}
	}

	/**
	 * 根据用户名获取用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(String loginName){
		UserCondition userCondition = new UserCondition();
		userCondition.setLoginName(loginName);
		List<User> userList = userCondition.list(sessionFactory, new Sorter().asc("create_time"));
		if(userList.isEmpty()){
			return null;
		}else{
			return userList.get(0);
		}
	}

	/**
	 * 创建用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParamsUser(User user) throws Exception{
		Boolean isCreateUser = false;
		User existUser = null;
		if(StringUtils.isBlank( user.getWorkNumber())){
			throw new Exception("工号不能为空");
		}
		if(user.getId()>0){
			existUser=this.get(user.getId());
			if(existUser==null){
				throw new Exception("没找到此用户");
			}

			//检测在同一个目录是否有冲突
			UserCondition userCondition=new UserCondition();
			userCondition.setDel(false);
			userCondition.setNeId(user.getId());

			long count=0;
			if( ! QingpuConstants._isSupportIP && user.getExtNum()!=null&&user.getExtNum()>0){
				userCondition.setExtNum(user.getExtNum());
				count=this.countByCondition(userCondition);
				if(count>0){
					throw new Exception("分机号已分配");
				}
			}

			userCondition.setExtNum(0);
			userCondition.setWorkNumber(user.getWorkNumber());
			count=this.countByCondition(userCondition);
			if(count>0){
				throw new Exception("用户工号已存在");
			}
			if(user.getPassword().equals("000000")){
				user.setPassword(existUser.getPassword());
			}else{
				user.setPassword(EncryptUtils.encryptByMD5(user.getPassword()));
			}
		}else {
			isCreateUser = true;
			//检测在同一个目录是否有冲突
			UserCondition userCondition=new UserCondition();
			userCondition.setDel(false);
			userCondition.setNeId(user.getId());
			long count=0;
			if( ! QingpuConstants._isSupportIP && user.getExtNum()!=null&&user.getExtNum()>0){
				userCondition.setExtNum(user.getExtNum());
				count=this.countByCondition(userCondition);
				if(count>0){
					throw new Exception("分机号已分配");
				}
			}
			userCondition.setExtNum(0);
			userCondition.setWorkNumber(user.getWorkNumber());
			count=this.countByCondition(userCondition);
			if(count>0){
				throw new Exception("用户工号已存在");
			}
			user.setPassword(EncryptUtils.encryptByMD5(user.getPassword()));
			existUser = new User();
		}
		if(StringUtils.isBlank(user.getName())){
			throw new Exception("用户姓名不能为空");
		}
		String oldName = existUser.getName();
		String newName = user.getName();
		if(oldName != null && ! oldName.equals(newName)){
			existUser.setNewCreateTime(new Date());
		}
		existUser.setName(newName);

		Boolean orIsDel = existUser.getIsDel();
		existUser.setIsDel(user.getIsDel());
		existUser.setGroupId(user.getGroupId());
		existUser.setRoleId(user.getRoleId());
		existUser.setWorkType(user.getWorkType());
		existUser.setExtNum(user.getExtNum());
		existUser.setPassword(user.getPassword());
		existUser.setIsTeamLeader(user.getIsTeamLeader());
		existUser.setWorkNumber(user.getWorkNumber());
		if(isCreateUser){
			UserCondition userCondition = new UserCondition();
			userCondition.setName(newName);
			List<User> userList = this.list(userCondition);
			if( ! userList.isEmpty()){
				throw new Exception("已存在相同姓名的用户： " + userList.get(0).getWorkNumber());
			}
			this.create(existUser);
		}else{
			UserCondition userCondition = new UserCondition();
			userCondition.setName(newName);
			List<User> userList = this.list(userCondition);
			if( userList.size() > 1){
				String workNumber = "";
				for(User user1 : userList){
					workNumber += user1.getWorkNumber() + ",";
				}
				throw new Exception("存在多个相同姓名的用户： " + StringUtil.subLastChar(workNumber));
			}
			this.update(existUser);
		}
//		jiangHuRenService.getChangeInfo();
		String roleId = existUser.getRoleId();
		Integer grouId = existUser.getGroupId();
		UserInfo userInfo = userInfoService.getByName(user.getName());
		if((roleId.equals(QingpuConstants.Roles_Employee_id ) || roleId.equals(QingpuConstants.Roles_Interview_id)) && existUser.getWorkType() == PublicEnum.WorkType.FullTime){
			JiangHuPai jiangHuPai = jiangHuPaiService.get(grouId);
			if(jiangHuPai != null){
				// 创建江湖人
				Boolean isCreate = false;
				String userName = existUser.getName();
				Integer userId = existUser.getId();
				Date delUpdateTime  = DateUtil.add(new Date(), DateUtil.ADDDAY, -14);
				PublicEnum.RoleType roleType = PublicEnum.RoleType.Interview;
				if(roleId.equals(QingpuConstants.Roles_Employee_id)){
					roleType = PublicEnum.RoleType.CS;
				}
				// 更新江湖人userId相同，名字不同的 移除风云榜
				JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
				jiangHuRenCondition.setUserId(userId);
				List<JiangHuRen> jiangHuRenList = jiangHuRenService.list(jiangHuRenCondition);
				for(JiangHuRen jiangHuRen1 : jiangHuRenList){
					jiangHuRen1.setUpdateTime(delUpdateTime);
					jiangHuRenService.update(jiangHuRen1);
				}

				JiangHuRen jiangHuRen = jiangHuRenService.getByNameAndRoleType(userName, roleType);
				if(jiangHuRen == null){
					isCreate = true;
					jiangHuRen = new JiangHuRen();
					jiangHuRen.setUserName(userName);
				}
				jiangHuRen.setUserId(userId);
				jiangHuRen.setRoleType(roleType);
				if(userInfo != null){
					jiangHuRen.setUserHeadImage(userInfo.getHeadImage());
				}
				Integer addGold = parameterService.getIntByCode(QingpuConstants.Parameter_Week_Gold);
				Boolean isAdd = null;
				if(isCreate){
					if( ! existUser.getIsDel()){
						isAdd = true;
					}
					jiangHuRenService.create(jiangHuRen);
				}else{
					if(orIsDel && ! existUser.getIsDel()){
						isAdd = true;
					}else if( ! orIsDel && existUser.getIsDel()){
						isAdd = false;
					}
					jiangHuRenService.update(jiangHuRen);
				}
				Integer beforePaiId = jiangHuRen.getPaiId();
				if(beforePaiId != null && beforePaiId.intValue() != grouId){
					JiangHuPai beforePai = jiangHuPaiService.get(beforePaiId);
					if(beforePai != null){
						beforePai.setGold(beforePai.getGold() - addGold);
						beforePai.setInitGold(beforePai.getInitGold() - addGold);
						beforePai.setRenCount(beforePai.getRenCount() - 1);
						jiangHuPaiService.update(beforePai);
					}
					isAdd = true;
				}
				jiangHuRen.setPaiId(grouId);
				UserCondition userCondition = new UserCondition();
				userCondition.setGroupId(grouId);
				userCondition.setDel(false);
				List<User> userList = this.list(userCondition);
				Integer oldInitGold = jiangHuPai.getInitGold();
				Integer toSetGold = userList.size() * addGold;
				if(oldInitGold.intValue() == toSetGold){
				    // 直接更换名字，创建新用户
				    isAdd = null;
                }
                jiangHuPai.setInitGold(toSetGold);
				if(isAdd != null ){
					if(isAdd){
						jiangHuPai.setGold(jiangHuPai.getGold() + addGold);
					}else{
						jiangHuPai.setGold(jiangHuPai.getGold() - addGold);
					}
				}
				if(existUser.getIsDel()){
					jiangHuRen.setUpdateTime(delUpdateTime);		// 离职从风云榜移除
				}else{
					jiangHuRen.setUpdateTime(new Date());
				}
				jiangHuPai.setRenCount(userList.size());
				jiangHuPaiService.update(jiangHuPai);
			}else{
				if(user.getGroupId().intValue() != QingpuConstants.User_Group_PartTime_Id){
					// 兼职组可以加入不用排行榜的全职类型
					throw new Exception("没有排行榜组信息，请先录入组的排行榜信息");
				}
			}
			logger.info("修改用户数据： " + user.getId());
		}

		// 检测userInfo是否存在
		if( ! StringUtil.checkIsNumber(newName)){
			UserInfoCondition userInfoCondition = new UserInfoCondition();
			userInfoCondition.setName(newName);
			List<UserInfo> userInfoList = userInfoService.list(userInfoCondition);
			if(userInfoList.isEmpty()){
				UserInfo userInfo1 = new UserInfo();
				userInfo1.setName(newName);
				userInfo1.setUserId(user.getId());
				userInfoService.create(userInfo1);
			}
		}

		Map<String, Object> map = new HashMap<>();
		//查询分组列表的id
		map.put("groupId", grouId);
		map.put("id", user.getId());
		return map;
	}


	/**
	 * 根据工号获取用户
	 * @param workNumber
	 * @return
	 * @throws Exception
	 */
	public User getByWorkNumber(String workNumber){
		User user = null;
		if(StringUtils.isNotBlank(workNumber)){
			UserCondition userCondition = new UserCondition();
			userCondition.setWorkNumber(workNumber);
			List<User> userList = this.list(userCondition, new Sorter().desc("create_time"));
			if( ! userList.isEmpty()){
				return userList.get(0);
			}
		}
		return user;
	}

	/**
	 * 更新用户数据
	 * @param paramsUser
	 * @param roleIds
	 * @param request
	 * @throws Exception
	 */
	public void updateByParamsUser(User paramsUser,String roleIds, HttpServletRequest request) throws Exception{
		Integer id = paramsUser.getId();
		User user = this.get(id);
		if(user == null){
			throw new Exception("未找到数据");
		}
		if(StringUtil.isNotNull(paramsUser.getPassword())){
			user.setPassword(paramsUser.getPassword());
		}
		user.setName(paramsUser.getName());
		this.update(user);

		Integer operatorId = null;
		User operatorUser = (User)request.getSession().getAttribute("_user");
		if(operatorUser != null){
			operatorId = operatorUser.getId();
		}

		/*
		更新用户角色信息
		 */
		userRoleService.set(roleIds, id, operatorId);
	}

	/**
	 * 根据角色id获取用户
	 * @param tableParams
	 * @param roleId
	 * @param range
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public PaginationSupport<User> getByRoleId(TableParams tableParams, String roleId, Range range) throws Exception{
		if(StringUtils.isBlank(roleId)){
			throw new Exception("请传入角色id");
		}
		UserCondition userCondition = new UserCondition();
		userCondition.setNickName(tableParams.getName());
		userCondition.setLikeLoginName(tableParams.getPhone());
		userCondition.setRoleId(roleId);
		PaginationSupport<User> userPaginationSupport = this.list(userCondition,range, new Sorter().desc("create_time") );
		return userPaginationSupport;
	}


	/**
	 * 获取角色用户的ids
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public String getIdsByRoleId(String roleId) throws Exception{
		UserCondition userCondition = new UserCondition();
		userCondition.setRoleId(roleId);
		List<User> userList = this.list(userCondition);
		String userIds = "";
		for(User user : userList){
			userIds += user.getId() + ",";
		}
		userIds = StringUtil.subLastChar(userIds);
		return userIds;
	}

	/**
	 * 设置用户在线状态
	 * @param isOnline
	 * @param userId
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public synchronized void setIsOnline(Boolean isOnline, Integer userId) throws Exception{
		User user = this.get(userId);
		if(user.getIsOnline() != isOnline){
			user.setIsOnline(isOnline);
			this.update(user);
		}
		switch (user.getRoleId()){
			case QingpuConstants.Roles_Employee_id:
			case QingpuConstants.Roles_Interview_id:
				if(isOnline){
					if( ! CallPhoneListener._onlineUserList.contains(userId)){
						CallPhoneListener._onlineUserList.add(userId);
						CallPhoneListener._onlineUserWorkNumList.add(user.getWorkNumber());
						groupCallService.updateRateNum();
					}
					CallPhoneListener._onlineUserMap.put(userId, user);
				}else{
					if(CallPhoneListener._onlineUserList.contains(userId)){
						CallPhoneListener._onlineUserWorkNumList.remove(user.getWorkNumber());
						CallPhoneListener._onlineUserList.remove(userId);
						CallPhoneListener._onlineUserMap.remove(userId);
						groupCallService.updateRateNum();
					}
				}
				break;
		}
	}

	/**
	 * 获取客服分机列表
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<Map.Entry<Integer, Boolean>> getCSExtNum() throws Exception{
		List<User> userList = this.getCSList(new Sorter().asc("ext_num"));
		Map<Integer, Boolean> map = new HashMap<>();
		for(User user : userList){
			Integer extNum = user.getExtNum();
			if(extNum != null && ! map.containsKey(extNum)){
				Boolean isOnline = false;
				if(CallPhoneListener._onlineExtNum.contains(extNum)){
					isOnline = true;
				}
				map.put(extNum, isOnline);
			}
		}
		List<Map.Entry<Integer, Boolean>> infoIds =
				new ArrayList<>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Boolean>>() {
			public int compare(Map.Entry<Integer, Boolean> o1, Map.Entry<Integer, Boolean> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		return infoIds;
	}

	/**
	 * 获取员工工号
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<Map.Entry<String, Boolean>> getWorkNum() throws Exception{
		List<User> userList = this.getCSList(new Sorter().asc("work_number"));
		Map<String, Boolean> map = new HashMap<>();
		for(User user : userList){
			Integer extNum = user.getExtNum();
			String workNum = user.getWorkNumber();
			if(extNum != null && ! map.containsKey(workNum)){
				Boolean isOnline = false;
				if(user.getIsLogin()){
					isOnline = true;
				}
				map.put(workNum, isOnline);
			}
		}
		List<Map.Entry<String, Boolean>> infoIds =
				new ArrayList<>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Boolean>>() {
			public int compare(Map.Entry<String, Boolean> o1, Map.Entry<String, Boolean> o2) {
				int len1 = o1.getKey().length();
				int len2 = o2.getKey().length();
				if(len1 != len2){
					return len1 - len2;
				}
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		return infoIds;
	}

	/**
	 * 获取客服列表
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<User> getCSList(Sorter sorter) throws Exception{
		UserCondition userCondition = new UserCondition();
		List<String> roleIdList = new ArrayList<>();
		roleIdList.add(QingpuConstants.Roles_Employee_id);
		roleIdList.add(QingpuConstants.Roles_Interview_id);
		userCondition.setRoleIdList(roleIdList);
		userCondition.setDel(false);
		return this.list(userCondition, sorter);
	}

	/**
	 * 获取登录客服列表
	 * @param sorter
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<User> getLoginCSList(Sorter sorter) throws Exception{
		UserCondition userCondition = new UserCondition();
		List<String> roleIdList = new ArrayList<>();
		roleIdList.add(QingpuConstants.Roles_Employee_id);
		roleIdList.add(QingpuConstants.Roles_Interview_id);
		userCondition.setRoleIdList(roleIdList);
		userCondition.setDel(false);
		userCondition.setLogin(true);
		return this.list(userCondition, sorter);
	}

	/**
	 * 获取群呼设置空闲客服
	 * @param groupCallId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public synchronized  User getIdleUserId(String groupCallId) throws Exception{
		if(CallPhoneListener._onlineUserList.isEmpty()){
			return null;
		}
		String workNums = CallPhoneListener._groupCallWorkNumMap.get(groupCallId);
		if(workNums != null){
			for(Integer id : CallPhoneListener._onlineUserList ){
				User user = CallPhoneListener._onlineUserMap.get(id);
				if(user != null){
					Integer userIdInt = user.getId();
					if( ! CallPhoneListener._inCallingUser.contains(userIdInt)){
						Integer extNum = user.getExtNum();
						String workNum = user.getWorkNumber();
						if(extNum != null){
							if(workNums.contains(workNum) && CallPhoneListener._onlineExtNum.contains(extNum)){
								CallPhoneListener._inCallingUser.add(userIdInt);
								String userName = user.getName();
								if(StringUtils.isBlank(userName)){
									user = this.get(user.getId());
								}
								return  user;
							}
						}else{
                            logger.error("转人工选择用户，未找到用户分机号：" + id);
                        }
					}
				}else {
				    logger.error("转人工选择用户，未找到在线用户：" + id);
                }
			}
		}
		return null;
	}

	/**
	 * 获取相应角色的用户选择列表
	 * @param roleId
	 * @return
	 */
	public List<Map.Entry<Integer, String>> getUserSelectByRoleId(String roleId){
		List<User> userList = this.list( new UserCondition());
		Map<Integer, String> map = new HashMap<>();
		for(User user : userList){
			if(QingpuConstants.User_Admin_Id.equals(user.getId())){
				continue;
			}
			map.put(user.getId(), user.getName());
		}
		List<Map.Entry<Integer, String>> infoIds =
				new ArrayList<>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer, String>>() {
			public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return infoIds;
	}

	/**
	 * 每天初始化
	 */
	public void dayInit(){
		Date now = new Date();
		System.out.println("----------------------------------------清空抽奖次数");
		LuckDrawCondition luckDrawCondition = new LuckDrawCondition();
		List<LuckDraw> luckDrawList = luckDrawService.list(luckDrawCondition);
		for(LuckDraw luckDraw : luckDrawList){
			try{
				luckDraw.setCount(luckDraw.getDayCount());
				luckDrawService.update(luckDraw);
			}catch (Exception e){
				logger.error("抽奖加入库存失败：" + e.getMessage());
			}
		}
		System.out.println("----------------------------------------清空意向词条次数");
		IntentionHintCondition intentionHintCondition = new IntentionHintCondition();
		List<IntentionHint> intentionHintList = intentionHintService.list(intentionHintCondition);
		for(IntentionHint intentionHint : intentionHintList){
			try{
				intentionHint.setCount(intentionHint.getDayCount());
				intentionHint.setTodayCount(0);
				intentionHintService.update(intentionHint);
			}catch (Exception e){
				logger.error("意向词条加入库存失败：" + e.getMessage());
			}
		}
		System.out.println("----------------------------------------重置用户信息");
		UserInfoCondition userInfoCondition = new UserInfoCondition();
		List<UserInfo> userInfoList = userInfoService.list(userInfoCondition);
		for(UserInfo userInfo : userInfoList){
			try{
				userInfo.setIsGoldenEgg(false);
				userInfo.setDayDrawCount(0);
				userInfo.setDayFinishDraw("0,0,0");
				userInfo.setDayIntentionCount(0);
				userInfo.setIsBirthdayHint(false);
				userInfoService.update(userInfo);
			}catch (Exception e){
				logger.error("重置用户信息失败：" + e.getMessage());
			}
		}

		System.out.println("----------------------------------------重置金币及意向");
		// 判断是否为月初
		String dayIndex = DateUtil.getWeekOfDate(now);
		Boolean isMonday = false;	// 是否周一
		if(dayIndex.equals("周一")){
			isMonday = true;
		}
		Date monthFirstDate = DateUtil.getStartMonthTime();
		if(DateUtil.daysBetween(monthFirstDate, now) < 1){
			JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
			List<JiangHuRen> jiangHuRenList = jiangHuRenService.list(jiangHuRenCondition);
			for(JiangHuRen jiangHuRen : jiangHuRenList){
				try{
					jiangHuRen.setMonthIntention(0);
					jiangHuRen.setMonthFinish(0);
					jiangHuRenService.update(jiangHuRen);
				}catch (Exception e){
					logger.info("重置月任务为零失败： " + e.getMessage());
				}
			}
		}


		// 周一写入排行榜

		Date lastFir = DateUtil.getLastWeekMonday(DateUtil.getDayStartTime());
		if(isMonday){
			JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
			jiangHuRenCondition.setGtUpdateTime(lastFir);
			List<JiangHuRen> jiangHuRenList = jiangHuRenService.list(jiangHuRenCondition);
			jiangHuRankService.createByRenList(jiangHuRenList, lastFir);
		}

		// 更新帮派
		JiangHuPaiCondition jiangHuPaiCondition = new JiangHuPaiCondition();
		List<JiangHuPai> jiangHuPaiList = jiangHuPaiService.list(jiangHuPaiCondition, new Sorter().asc("id"));
		Integer weekRenGold = parameterService.getIntByCode(QingpuConstants.Parameter_Week_Gold);
		List<String> csIdList = new ArrayList<>();
		List<String> interviewIdList = new ArrayList<>();
		for(JiangHuPai jiangHuPai : jiangHuPaiList){
			String id = jiangHuPai.getId() + "";
			if(jiangHuPai.getRoleType() == PublicEnum.RoleType.CS){
				csIdList.add( id);
			}else {
				interviewIdList.add( id);
			}
		}
		for(JiangHuPai jiangHuPai : jiangHuPaiList){
			try{
				PublicEnum.RoleType roleType = jiangHuPai.getRoleType();
				if(isMonday){
					// 创建排行榜
					JiangHuPaiRank jiangHuPaiRank = new JiangHuPaiRank();
					jiangHuPaiRank.setCreateTime(lastFir);
					jiangHuPaiRank.setRoleType(jiangHuPai.getRoleType());
					DecimalFormat decimalFormat = new DecimalFormat("######0");
					jiangHuPaiRank.setGold(Integer.parseInt(decimalFormat.format(jiangHuPai.getDoubleGold())));
					jiangHuPaiRank.setPaiId(jiangHuPai.getId());
					jiangHuPaiRankService.create(jiangHuPaiRank);


					jiangHuPai.setInitGold(weekRenGold * jiangHuPai.getRenCount());
					jiangHuPai.setGold(jiangHuPai.getInitGold());
					jiangHuPai.setDoubleGold(100.0);
					Boolean isCS = true;
					if(roleType == PublicEnum.RoleType.Interview){
						jiangHuPai.setFinishCount(0);
						isCS = false;
					}
					jiangHuPai.setSubCount(0);
					List<String> idList;
					if(isCS){
						idList = csIdList;
					}else {
						idList = interviewIdList;
					}
					String idStr = jiangHuPai.getId() + "";
					Integer index = idList.indexOf(idStr);
					List<String> paidIdList = new ArrayList<>();
					for(int i = 0; i < idList.size(); i++){
						String item = idList.get(i);
						if(index > i){
							paidIdList.add(item);
						}else if ( index < i){
							paidIdList.add(i - index - 1, item);
						}
					}
					jiangHuPai.setGoldPaiIds(StringUtil.join(paidIdList, ","));
				}
				if(roleType == PublicEnum.RoleType.CS){
					jiangHuPai.setFinishCount(0);
				}
				jiangHuPaiService.update(jiangHuPai);
			}catch (Exception e){
				logger.info("重置派任务失败： " + e.getMessage());
			}
		}

		// 每天判断
		JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
		List<JiangHuRen> jiangHuRenList = jiangHuRenService.list(jiangHuRenCondition);
		for(JiangHuRen jiangHuRen : jiangHuRenList){
			try{
				if(isMonday){
					jiangHuRen.setLastWeekFinish(jiangHuRen.getWeekFinish());
					jiangHuRen.setWeekFinish(0);
					jiangHuRen.setLastWeekGold(jiangHuRen.getWeekGold());
					jiangHuRen.setWeekGold(0);
					jiangHuRen.setLastWeekIntention(jiangHuRen.getWeekIntention());
					jiangHuRen.setWeekIntention(0);
					jiangHuRen.setWeekDayIntention(0);
				}
				jiangHuRen.setWeekDayIntention(jiangHuRen.getWeekDayIntention() + jiangHuRen.getDayIntention());
				jiangHuRen.setDayIntention(0);
				jiangHuRenService.update(jiangHuRen);
			}catch (Exception e){
				logger.info("重置人任务失败： " + e.getMessage());
			}
		}

		//初始化江湖人统计信息的定时任务
		jiangHuRenService.initScheduled(false);
	}
}
