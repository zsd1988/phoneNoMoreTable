package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.EncryptUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemSetting.service.ParameterService;
import com.qingpu.phone.user.condition.JiangHuRenCondition;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.condition.UserGroupCondition;
import com.qingpu.phone.user.entity.JiangHuPai;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserGroup;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("userGroupService")
@Transactional
public class UserGroupService {
	private static Logger logger = Logger.getLogger(UserGroupService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private UserService userService;

	@Resource
	JiangHuPaiService jiangHuPaiService;

	@Resource
	ParameterService parameterService;

	/**
	 * 创建
	 * @param userGroup
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserGroup create(UserGroup userGroup) throws Exception{
		/*if(userGroup != null && StringUtils.isBlank(userGroup.getId())){
			userGroup.setId(UUIDGenerator.getUUID());
		}*/
		CommonUtils.checkEntity(userGroup);
		return (UserGroup) baseDao.save(userGroup);
	}


	/**
	 * 更新
	 * @param userGroup
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(UserGroup userGroup)  throws Exception {
		CommonUtils.checkEntity(userGroup);
		baseDao.update(userGroup);
	}


	/**
	 * 删除
	 * @param userGroup
	 */
	public void delete(UserGroup userGroup) {
		userGroup.setIsDel(true);
		baseDao.update(userGroup);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public UserGroup get(Integer id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (UserGroup)baseDao.get(UserGroup.class, id);
	}


	/**
	 * 按条件查找
	 * @param userGroupCondition
	 * @return
	 */
	public List<UserGroup> list(UserGroupCondition userGroupCondition){
		return  userGroupCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param userGroupCondition
	 * @param sorter
	 * @return
	 */
	public List<UserGroup> list(UserGroupCondition userGroupCondition, Sorter sorter){
		return  userGroupCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param userGroupCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<UserGroup> list(UserGroupCondition userGroupCondition, Range range, Sorter sorter){
		PaginationSupport<UserGroup> userGroupPaginationSupport = (PaginationSupport<UserGroup>)userGroupCondition.pageList(sessionFactory, sorter, range);
		return userGroupPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param userGroupCondition
	 * @return
	 */
	public Long countByCondition(UserGroupCondition userGroupCondition){
		return   userGroupCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param userGroupCondition
	 * @return
	 */
	public List<Object[]> listCustom(UserGroupCondition userGroupCondition){
		return  userGroupCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param userGroupCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(UserGroupCondition userGroupCondition, int num){
		return  userGroupCondition.list(sessionFactory, num);
	}

	/**
	 * 根据参数创建
	 * @param userGroup
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(UserGroup userGroup) throws Exception{
		if(userGroup.getId()>0){
			//检测在同一个目录是否名字冲突
			UserGroupCondition userGroupCondition=new UserGroupCondition();
			userGroupCondition.setbDel(false);
			userGroupCondition.setFid(userGroup.getFid());
			userGroupCondition.setGroupName(userGroup.getGroupName());
			userGroupCondition.setNoId(userGroup.getId());
			long count=this.countByCondition(userGroupCondition);
			if(count>0){
				throw new Exception("组名称已存在");
			}
			this.update(userGroup);
		}else {
			//检测在同一个目录是否名字冲突
			UserGroupCondition userGroupCondition = new UserGroupCondition();
			userGroupCondition.setbDel(false);
			userGroupCondition.setFid(userGroup.getFid());
			userGroupCondition.setGroupName(userGroup.getGroupName());
			long count = this.countByCondition(userGroupCondition);
			if (count > 0) {
				throw new Exception("组名称已存在");
			}
			userGroup = this.create(userGroup);
		}
		String nickName = userGroup.getNickName();
		Integer fid = userGroup.getFid();
		if(StringUtils.isNotBlank(nickName) && (fid.intValue() == QingpuConstants.User_Group_Customer_Id
				|| fid.intValue() == QingpuConstants.User_Group_Interview_Id )){
			// 设置江湖组
			Integer id = userGroup.getId();
			Integer count = userGroup.getCount();
			if(count == null){
				throw new Exception("请设置每日目标");
			}
			String gongfu = userGroup.getGongfu();
			if(StringUtils.isBlank(gongfu)){
				throw new Exception("请输入功夫招式");
			}
			JiangHuPai jiangHuPai = jiangHuPaiService.get(id);
			Boolean isCreate = false;
			if(jiangHuPai == null){
				isCreate = true;
				jiangHuPai = new JiangHuPai();
				jiangHuPai.setId(id);
			}
			jiangHuPai.setNickName(nickName);
			jiangHuPai.setCount(count);
			jiangHuPai.setGongfu(gongfu);
			if(isCreate){
				PublicEnum.RoleType roleType = PublicEnum.RoleType.Interview;
				if(fid.intValue() == QingpuConstants.User_Group_Customer_Id){
					roleType = PublicEnum.RoleType.CS;
				}
				jiangHuPai.setRoleType(roleType);
				jiangHuPai = jiangHuPaiService.create(jiangHuPai);
				jiangHuPaiService.initGoldPaiIds(jiangHuPai);
			}else{
				UserCondition userCondition = new UserCondition();
				userCondition.setGroupId(userGroup.getId());
				userCondition.setDel(false);
				List<User> userList = userService.list(userCondition);
				Integer addGold = parameterService.getIntByCode(QingpuConstants.Parameter_Week_Gold);
				jiangHuPai.setInitGold(userList.size() * addGold);
				jiangHuPai.setRenCount(userList.size() );
				jiangHuPaiService.update(jiangHuPai);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", userGroup.getId());
		map.put("groupName", userGroup.getGroupName());
		map.put("fid", userGroup.getFid());
		return map;
	}


	/**
	 * 获取列表下拉选择框
	 * @return
	 */
	public Map<String, Object> getSelect(List<Integer> fidList){
		Map<String, Object> resultMap = new HashMap<>();
		UserGroupCondition userGroupCondition = new UserGroupCondition();
		userGroupCondition.setFidList(fidList);
		userGroupCondition.setbDel(false);
		List<UserGroup> userGroupList = this.list(userGroupCondition, new Sorter().asc("group_name"));
		List<Map<String, String>> mapList = new ArrayList<>();
		Map<String, List<String>> groupUserMap = new HashMap<>();
		for(UserGroup userGroup : userGroupList){
			String userGroupId = userGroup.getId() + "";
			Map<String, String> map = new HashMap<>();
			map.put(userGroupId, userGroup.getGroupName());
			// 用户数据
			UserCondition userCondition = new UserCondition();
			userCondition.setGroupId(userGroup.getId());
			userCondition.setDel(false);
			List<String> userIdList = new ArrayList<>();
			List<User> userList = userService.list(userCondition);
			// 客服组如果没有员工则不返回
			if( ! userList.isEmpty()){
				for(User user : userList){
					userIdList.add(user.getId() + "");
				}
				groupUserMap.put(userGroupId, userIdList);
				mapList.add(map);
			}
		}
		resultMap.put("select", mapList);
		resultMap.put("userList", groupUserMap);
		return resultMap;
	}

	/**
	 * 获取客服组
	 * @return
	 */
	public Map<String, Object> getCSSelect(){
		List<Integer> fidList = new ArrayList<>();
		fidList.add(QingpuConstants.User_Group_Customer_Id);
		return this.getSelect(fidList);
	}

	/**
	 * 获取客服和约访组
	 * @return
	 */
	public Map<String, Object> getCSAndInterviewSelect(){
		List<Integer> fidList = new ArrayList<>();
		fidList.add(QingpuConstants.User_Group_Customer_Id);
		fidList.add(QingpuConstants.User_Group_Interview_Id);
		return this.getSelect(fidList);
	}

	/**
	 * 初始化表数据
	 */
	public void init() {
		/*
		超级管理员
		 */
		// 建立表数据
		init(QingpuConstants.User_Group_Non_Id, QingpuConstants.User_Group_System_Id, QingpuConstants.User_Group_Non_Name);
		init(QingpuConstants.User_Group_Quality_Id, QingpuConstants.User_Group_System_Id, QingpuConstants.User_Group_Quality_Name);
		init(QingpuConstants.User_Group_Customer_Id, QingpuConstants.User_Group_System_Id, QingpuConstants.User_Group_Customer_Name);
		init(QingpuConstants.User_Group_Interview_Id, QingpuConstants.User_Group_System_Id, QingpuConstants.User_Group_Interview_Name);
		init(QingpuConstants.User_Group_System_Id, 0, QingpuConstants.User_Group_System_Name);
		init(QingpuConstants.User_Group_PartTime_Id, QingpuConstants.User_Group_Non_Id, QingpuConstants.User_Group_PartTime_Name);
		init(QingpuConstants.User_Group_Manager_Id, QingpuConstants.User_Group_Non_Id, QingpuConstants.User_Group_Manager_Name);

		// 分配超级管理员角色
		//userRoleService.init(QingpuConstants.Roles_Super_id, QingpuConstants.User_Admin_Id);
	}

	/**
	 * 初始化表数据
	 * @param id
	 */
	private void init(Integer id,Integer fid, String groupName) {
		UserGroup userGroup = this.get(id);
		if(userGroup == null){
			userGroup = new UserGroup();
			userGroup.setId(id);
			userGroup.setFid(fid);
			userGroup.setGroupName(groupName);
			userGroup.setIsDel(false);
			try{
				this.create(userGroup);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void getCSGroupMap() throws Exception{
		UserCondition userCondition = new UserCondition();
		userCondition.setRoleId(QingpuConstants.Roles_Employee_id);
	}

}
