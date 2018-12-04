package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.condition.UserInfoCondition;
import com.qingpu.phone.user.entity.JiangHuRen;
import com.qingpu.phone.user.entity.UserInfo;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userInfoService")
@Transactional
public class UserInfoService {
	private static Logger logger = Logger.getLogger(UserInfoService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	UserService userService;

	@Resource
	JiangHuRenService jiangHuRenService;

	/**
	 * 创建
	 * @param userInfo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserInfo create(UserInfo userInfo) throws Exception{
		CommonUtils.checkEntity(userInfo);
		return (UserInfo) baseDao.save(userInfo);
	}


	/**
	 * 更新
	 * @param userInfo
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(UserInfo userInfo)  throws Exception {
		CommonUtils.checkEntity(userInfo);
		baseDao.update(userInfo);
	}


	/**
	 * 删除
	 * @param userInfo
	 */
	public void delete(UserInfo userInfo) {
		userInfo.setIsDel(true);
		userInfo.setDelTime(new Date());
		baseDao.update(userInfo);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public UserInfo get(Long id) {
		if(id == null ){
			return null;
		}
		return (UserInfo)baseDao.get(UserInfo.class, id);
	}


	/**
	 * 按条件查找
	 * @param userInfoCondition
	 * @return
	 */
	public List<UserInfo> list(UserInfoCondition userInfoCondition){
		return  userInfoCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param userInfoCondition
	 * @param sorter
	 * @return
	 */
	public List<UserInfo> list(UserInfoCondition userInfoCondition, Sorter sorter){
		return  userInfoCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param userInfoCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<UserInfo> list(UserInfoCondition userInfoCondition, Range range, Sorter sorter){
		PaginationSupport<UserInfo> testTablePaginationSupport = (PaginationSupport<UserInfo>) userInfoCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param userInfoCondition
	 * @return
	 */
	public Long countByCondition(UserInfoCondition userInfoCondition){
		return   userInfoCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param userInfoCondition
	 * @return
	 */
	public List<Object[]> listCustom(UserInfoCondition userInfoCondition){
		return  userInfoCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param userInfoCondition
	 * @param num
	 * @return
	 */
	public List<UserInfo> listCustom(UserInfoCondition userInfoCondition, int num){
		return  AutoEvaluationUtil.toClassList(userInfoCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param userInfoCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<UserInfo> listCustom(UserInfoCondition userInfoCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(userInfoCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(UserInfo userInfo) throws Exception{
		userInfo = this.create(userInfo);
		Map<String, Object> map = new HashMap<>();
		map.put("id", userInfo.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsUserInfo
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(UserInfo paramsUserInfo) throws Exception{
		UserInfo userInfo = this.get(paramsUserInfo.getId());
		if(userInfo == null){
			throw new Exception("未找到对象");
		}
		String birthDayStr = paramsUserInfo.getBirthDayStr();
		if(StringUtils.isBlank(birthDayStr)){
			throw new Exception("请选择生日");
		}
		userInfo.setBirthDay(DateUtil.strDateToDate(birthDayStr, 0));
		String employeeDayStr = paramsUserInfo.getEmployeeDayStr();
		if(StringUtils.isBlank(employeeDayStr)){
			throw new Exception("请选择入职日期");
		}
		userInfo.setEmployeeDay(DateUtil.strDateToDate(employeeDayStr, 0));
		userInfo.setHeadImage(paramsUserInfo.getHeadImage());
		JiangHuRen jiangHuRen = jiangHuRenService.getByNameAndRoleType(userInfo.getName(), PublicEnum.RoleType.CS);
		if(jiangHuRen == null){
			jiangHuRen = jiangHuRenService.getByNameAndRoleType(userInfo.getName(), PublicEnum.RoleType.Interview);
		}
		if(jiangHuRen != null){
			jiangHuRen.setUserHeadImage(userInfo.getHeadImage());
			jiangHuRenService.update(jiangHuRen);
		}
		this.update(userInfo);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(Long id, HttpServletRequest request) throws Exception{
		UserInfo userInfo = this.get(id);
		if(userInfo == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			userInfo.setOperatorId(user.getId());
		}
		this.delete(userInfo);
	}


	/**
	 * 根据名称获取
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserInfo getByName(String name) throws Exception{
		UserInfoCondition userInfoCondition = new UserInfoCondition();
		userInfoCondition.setName(name);
		List<UserInfo> userInfoList = this.list(userInfoCondition);
		if(userInfoList.isEmpty()){
			UserCondition userCondition = new UserCondition();
			userCondition.setName(name);
			List<User> userList = userService.list(userCondition);
			if(userList.isEmpty()){
				return null;
			}else{
				if(StringUtil.checkIsNumber(name)){
					return null;
				}
				User user = userList.get(0);
				UserInfo userInfo = new UserInfo();
				userInfo.setName(name);
				userInfo.setUserId(user.getId());
				return this.create(userInfo);
			}
		}
		return userInfoList.get(0);
	}
}
