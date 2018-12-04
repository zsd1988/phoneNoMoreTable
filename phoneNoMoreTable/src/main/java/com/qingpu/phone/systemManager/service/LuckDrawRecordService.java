package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.LuckDrawCondition;
import com.qingpu.phone.systemManager.condition.LuckDrawRecordCondition;
import com.qingpu.phone.systemManager.entity.LuckDraw;
import com.qingpu.phone.systemManager.entity.LuckDrawRecord;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserInfo;
import com.qingpu.phone.user.service.UserInfoService;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("luckDrawRecordService")
@Transactional
public class LuckDrawRecordService {
	private static Logger logger = Logger.getLogger(LuckDrawRecordService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	UserService userService;

	@Resource
	LuckDrawService luckDrawService;

	@Resource
	WebSocketService webSocketService;

	@Resource
	UserInfoService userInfoService;

	/**
	 * 创建
	 * @param luckDrawRecord
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public LuckDrawRecord create(LuckDrawRecord luckDrawRecord) throws Exception{
		if(luckDrawRecord != null && StringUtils.isBlank(luckDrawRecord.getId())){
			luckDrawRecord.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(luckDrawRecord);
		return (LuckDrawRecord) baseDao.save(luckDrawRecord);
	}


	/**
	 * 更新
	 * @param luckDrawRecord
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(LuckDrawRecord luckDrawRecord)  throws Exception {
		CommonUtils.checkEntity(luckDrawRecord);
		baseDao.update(luckDrawRecord);
	}


	/**
	 * 删除
	 * @param luckDrawRecord
	 */
	public void delete(LuckDrawRecord luckDrawRecord) {
		luckDrawRecord.setIsDel(true);
		luckDrawRecord.setDelTime(new Date());
		baseDao.update(luckDrawRecord);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public LuckDrawRecord get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (LuckDrawRecord)baseDao.get(LuckDrawRecord.class, id);
	}


	/**
	 * 按条件查找
	 * @param luckDrawRecordCondition
	 * @return
	 */
	public List<LuckDrawRecord> list(LuckDrawRecordCondition luckDrawRecordCondition){
		return  luckDrawRecordCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param luckDrawRecordCondition
	 * @param sorter
	 * @return
	 */
	public List<LuckDrawRecord> list(LuckDrawRecordCondition luckDrawRecordCondition, Sorter sorter){
		return  luckDrawRecordCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param luckDrawRecordCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<LuckDrawRecord> list(LuckDrawRecordCondition luckDrawRecordCondition, Range range, Sorter sorter){
		PaginationSupport<LuckDrawRecord> testTablePaginationSupport = (PaginationSupport<LuckDrawRecord>) luckDrawRecordCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param luckDrawRecordCondition
	 * @return
	 */
	public Long countByCondition(LuckDrawRecordCondition luckDrawRecordCondition){
		return   luckDrawRecordCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param luckDrawRecordCondition
	 * @return
	 */
	public List<Object[]> listCustom(LuckDrawRecordCondition luckDrawRecordCondition){
		return  luckDrawRecordCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param luckDrawRecordCondition
	 * @param num
	 * @return
	 */
	public List<LuckDrawRecord> listCustom(LuckDrawRecordCondition luckDrawRecordCondition, int num){
		return  AutoEvaluationUtil.toClassList(luckDrawRecordCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param luckDrawRecordCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<LuckDrawRecord> listCustom(LuckDrawRecordCondition luckDrawRecordCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(luckDrawRecordCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param luckDrawRecord
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(LuckDrawRecord luckDrawRecord) throws Exception{
		luckDrawRecord = this.create(luckDrawRecord);
		Map<String, Object> map = new HashMap<>();
		map.put("id", luckDrawRecord.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsLuckDrawRecord
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(LuckDrawRecord paramsLuckDrawRecord) throws Exception{
		LuckDrawRecord luckDrawRecord = this.get(paramsLuckDrawRecord.getId());
		if(luckDrawRecord == null){
			throw new Exception("未找到对象");
		}
		this.update(luckDrawRecord);
	}


	/**
	 * 领取奖品
	 * @param id
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setIsGet(String id) throws Exception{
		LuckDrawRecord luckDrawRecord = this.get(id);
		if(luckDrawRecord == null){
			throw new Exception("未找到对象");
		}
		Boolean isGet = luckDrawRecord.getIsGet();
		if(isGet != null && isGet){
			throw new Exception("奖品已领取，不能再次领取");
		}
		luckDrawRecord.setIsGet(true);
		luckDrawRecord.setGetDate(new Date());
		this.update(luckDrawRecord);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		LuckDrawRecord luckDrawRecord = this.get(id);
		if(luckDrawRecord == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			luckDrawRecord.setOperatorId(user.getId());
		}
		this.delete(luckDrawRecord);
	}


	/**
	 * 抽奖
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public synchronized LuckDraw create(Integer userId) throws Exception{
		LuckDraw resultLuckDraw = null;
		User user = userService.get(userId);
		if(user == null){
			throw new Exception("未找到用户");
		}
		UserInfo userInfo = userInfoService.getByName(user.getName());
		if(userInfo == null){
			throw new Exception("未找到用户信息");
		}
		Integer dayDrawCount = userInfo.getDayDrawCount();
		if(dayDrawCount <= 0){
			throw new Exception("抱歉，您还没有抽奖次数");
		}
		LuckDrawCondition luckDrawCondition = new LuckDrawCondition();
		luckDrawCondition.setbDel(false);
		luckDrawCondition.setGtCount(0);
		List<LuckDraw> luckDrawList = luckDrawService.list(luckDrawCondition);
		if(luckDrawList.isEmpty()){
			throw new Exception("抱歉，当前没有任何奖品");
		}
		Integer totalRate = 0;
		for(LuckDraw luckDraw : luckDrawList){
			totalRate += luckDraw.getRate();
			System.out.println(luckDraw.getName() + " 总数区间： " + totalRate);
		}
		Integer num = new Random().nextInt(totalRate) + 1;
		System.out.println("随机数：" + num);
		totalRate = 0;
		for(LuckDraw luckDraw : luckDrawList){
			totalRate += luckDraw.getRate();
			if(num <= totalRate){
				LuckDrawRecord luckDrawRecord = new LuckDrawRecord();
				luckDrawRecord.setName(luckDraw.getName());
				luckDrawRecord.setUserId(userId + "");
				luckDrawRecord.setUserName(user.getName());
				this.create(luckDrawRecord);

				luckDraw.setCount(luckDraw.getCount() - 1);
				luckDrawService.update(luckDraw);

				Integer leftDrawCount = dayDrawCount - 1;
				userInfo.setDayDrawCount(leftDrawCount);
				userInfoService.update(userInfo);
				luckDraw.setUserDrawCount(leftDrawCount);
				resultLuckDraw = luckDraw;

				webSocketService.showLuckDrawHint(luckDrawRecord);
				break;
			}
		}
		return resultLuckDraw;
	}


	/**
	 * 批量领取
	 * @param ids
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void getAll(String ids) throws Exception{
		List<String> idList = StringUtil.splitByComma(ids);
		Date now = new Date();
		for(String id : idList){
			LuckDrawRecord luckDrawRecord = this.get(id);
			if(luckDrawRecord != null && ! luckDrawRecord.getIsGet()){
				luckDrawRecord.setIsGet(true);
				luckDrawRecord.setGetDate(now);
				this.update(luckDrawRecord);
			}
		}
	}
}
