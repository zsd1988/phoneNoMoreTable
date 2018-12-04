package com.qingpu.phone.systemSetting.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.systemSetting.condition.ExtAndIPCondition;
import com.qingpu.phone.systemSetting.entity.ExtAndIP;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("extAndIPService")
@Transactional
public class ExtAndIPService {
	private static Logger logger = Logger.getLogger(ExtAndIPService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private UserService userService;

	@Resource
	private GroupCallService groupCallService;

	/**
	 * 创建
	 * @param extAndIP
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ExtAndIP create(ExtAndIP extAndIP) throws Exception{
		if(extAndIP != null && StringUtils.isBlank(extAndIP.getId())){
			extAndIP.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(extAndIP);
		return (ExtAndIP) baseDao.save(extAndIP);
	}


	/**
	 * 更新
	 * @param extAndIP
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(ExtAndIP extAndIP)  throws Exception {
		CommonUtils.checkEntity(extAndIP);
		baseDao.update(extAndIP);
	}


	/**
	 * 删除
	 * @param extAndIP
	 */
	public void delete(ExtAndIP extAndIP) {
		extAndIP.setIsDel(true);
		extAndIP.setDelTime(new Date());
		baseDao.update(extAndIP);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public ExtAndIP get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (ExtAndIP)baseDao.get(ExtAndIP.class, id);
	}


	/**
	 * 按条件查找
	 * @param extAndIPCondition
	 * @return
	 */
	public List<ExtAndIP> list(ExtAndIPCondition extAndIPCondition){
		return  extAndIPCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param extAndIPCondition
	 * @param sorter
	 * @return
	 */
	public List<ExtAndIP> list(ExtAndIPCondition extAndIPCondition, Sorter sorter){
		return  extAndIPCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param extAndIPCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<ExtAndIP> list(ExtAndIPCondition extAndIPCondition, Range range, Sorter sorter){
		PaginationSupport<ExtAndIP> testTablePaginationSupport = (PaginationSupport<ExtAndIP>) extAndIPCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param extAndIPCondition
	 * @return
	 */
	public Long countByCondition(ExtAndIPCondition extAndIPCondition){
		return   extAndIPCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param extAndIPCondition
	 * @return
	 */
	public List<Object[]> listCustom(ExtAndIPCondition extAndIPCondition){
		return  extAndIPCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param extAndIPCondition
	 * @param num
	 * @return
	 */
	public List<ExtAndIP> listCustom(ExtAndIPCondition extAndIPCondition, int num){
		return  AutoEvaluationUtil.toClassList(extAndIPCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param extAndIPCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<ExtAndIP> listCustom(ExtAndIPCondition extAndIPCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(extAndIPCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param extAndIP
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(ExtAndIP extAndIP) throws Exception{
		if( ! StringUtil.checkIsIP(extAndIP.getIp())){
			throw new Exception("请输入正确的IP地址");
		}
		extAndIP = this.create(extAndIP);
		Map<String, Object> map = new HashMap<>();
		map.put("id", extAndIP.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsExtAndIP
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(ExtAndIP paramsExtAndIP) throws Exception{
		ExtAndIP extAndIP = this.get(paramsExtAndIP.getId());
		if(extAndIP == null){
			throw new Exception("未找到对象");
		}
		if( ! StringUtil.checkIsIP(paramsExtAndIP.getIp())){
			throw new Exception("请输入正确的IP地址");
		}
		extAndIP.setIp(paramsExtAndIP.getIp());
		extAndIP.setExtNum(paramsExtAndIP.getExtNum());
		this.update(extAndIP);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, Boolean isDel, HttpServletRequest request) throws Exception{
		ExtAndIP extAndIP = this.get(id);
		if(extAndIP == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			extAndIP.setOperatorId(user.getId());
		}
		extAndIP.setIsDel(isDel);
		this.update(extAndIP);
	}


	/**
	 * 根据ip登录获取分机号
	 * @param jsonObject
	 * @param user
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void loginByIP(JSONObject jsonObject, User user) throws Exception{
		String roleId = user.getRoleId();
		String ipStr = "";
		Integer userId = user.getId();
		Boolean isSuccess = false;
		Object ipObj = jsonObject.get("ip");
		if(ipObj != null){
			ipStr = ipObj.toString();
			if(StringUtil.checkIsIP(ipStr)){
				isSuccess = true;
			}
		}
		if(QingpuConstants.Roles_Employee_id.equals(roleId) || QingpuConstants.Roles_Interview_id.equals(roleId)){
			if( ! isSuccess){
				throw new Exception("获取IP失败，请查看电脑IP设置");
			}
		}
		if(isSuccess){
			user.setIp(ipStr);
			ExtAndIPCondition extAndIPCondition = new ExtAndIPCondition();
			extAndIPCondition.setIp(ipStr);
			extAndIPCondition.setbDel(false);
			List<ExtAndIP> extAndIPList = this.list(extAndIPCondition);
			if( ! extAndIPList.isEmpty()){
				ExtAndIP extAndIP = extAndIPList.get(0);
				Integer extNum = extAndIP.getExtNum();
				user.setExtNum(extNum);
				if(CallPhoneListener._inCallingUser.contains(userId)){
					CallPhoneListener._inCallingUser.remove(userId);
				}
				logger.info("工号登录：" + user.getWorkNumber() + "  分机：" + extNum + " IP：" + ipStr);
			}else {
				if(QingpuConstants.Roles_Employee_id.equals(roleId) || QingpuConstants.Roles_Interview_id.equals(roleId)){
					throw new Exception("电脑未配置分机号，请联系管理员");
				}
			}
		}
	}
}
