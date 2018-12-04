package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.func.webSocket.WebSocketData;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.IntentionHintCondition;
import com.qingpu.phone.systemManager.condition.IntentionHintRewardRecordCondition;
import com.qingpu.phone.systemManager.entity.DayHint;
import com.qingpu.phone.systemManager.entity.IntentionHint;
import com.qingpu.phone.systemManager.entity.IntentionHintRewardRecord;
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
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

@Service("intentionHintRewardRecordService")
@Transactional
public class IntentionHintRewardRecordService {
	private static Logger logger = Logger.getLogger(IntentionHintRewardRecordService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	IntentionHintService intentionHintService;

	@Resource
	WebSocketService webSocketService;

	@Resource
	UserService userService;

	@Resource
	UserInfoService userInfoService;

	@Resource
	DayHintService dayHintService;

	@Resource
	PostMessageService postMessageService;

	/**
	 * 创建
	 * @param intentionHintRewardRecord
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public IntentionHintRewardRecord create(IntentionHintRewardRecord intentionHintRewardRecord) throws Exception{
		if(intentionHintRewardRecord != null && StringUtils.isBlank(intentionHintRewardRecord.getId())){
			intentionHintRewardRecord.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(intentionHintRewardRecord);
		return (IntentionHintRewardRecord) baseDao.save(intentionHintRewardRecord);
	}


	/**
	 * 更新
	 * @param intentionHintRewardRecord
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(IntentionHintRewardRecord intentionHintRewardRecord)  throws Exception {
		CommonUtils.checkEntity(intentionHintRewardRecord);
		baseDao.update(intentionHintRewardRecord);
	}


	/**
	 * 删除
	 * @param intentionHintRewardRecord
	 */
	public void delete(IntentionHintRewardRecord intentionHintRewardRecord) {
		intentionHintRewardRecord.setIsDel(true);
		intentionHintRewardRecord.setDelTime(new Date());
		baseDao.update(intentionHintRewardRecord);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public IntentionHintRewardRecord get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (IntentionHintRewardRecord)baseDao.get(IntentionHintRewardRecord.class, id);
	}


	/**
	 * 按条件查找
	 * @param intentionHintRewardRecordCondition
	 * @return
	 */
	public List<IntentionHintRewardRecord> list(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition){
		return  intentionHintRewardRecordCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param intentionHintRewardRecordCondition
	 * @param sorter
	 * @return
	 */
	public List<IntentionHintRewardRecord> list(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition, Sorter sorter){
		return  intentionHintRewardRecordCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param intentionHintRewardRecordCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<IntentionHintRewardRecord> list(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition, Range range, Sorter sorter){
		PaginationSupport<IntentionHintRewardRecord> testTablePaginationSupport = (PaginationSupport<IntentionHintRewardRecord>) intentionHintRewardRecordCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param intentionHintRewardRecordCondition
	 * @return
	 */
	public Long countByCondition(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition){
		return   intentionHintRewardRecordCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param intentionHintRewardRecordCondition
	 * @return
	 */
	public List<Object[]> listCustom(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition){
		return  intentionHintRewardRecordCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param intentionHintRewardRecordCondition
	 * @param num
	 * @return
	 */
	public List<IntentionHintRewardRecord> listCustom(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition, int num){
		return  AutoEvaluationUtil.toClassList(intentionHintRewardRecordCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param intentionHintRewardRecordCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<IntentionHintRewardRecord> listCustom(IntentionHintRewardRecordCondition intentionHintRewardRecordCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(intentionHintRewardRecordCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param intentionHintRewardRecord
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(IntentionHintRewardRecord intentionHintRewardRecord) throws Exception{
		intentionHintRewardRecord = this.create(intentionHintRewardRecord);
		Map<String, Object> map = new HashMap<>();
		map.put("id", intentionHintRewardRecord.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsIntentionHintRewardRecord
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(IntentionHintRewardRecord paramsIntentionHintRewardRecord) throws Exception{
		IntentionHintRewardRecord intentionHintRewardRecord = this.get(paramsIntentionHintRewardRecord.getId());
		if(intentionHintRewardRecord == null){
			throw new Exception("未找到对象");
		}
		this.update(intentionHintRewardRecord);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		IntentionHintRewardRecord intentionHintRewardRecord = this.get(id);
		if(intentionHintRewardRecord == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			intentionHintRewardRecord.setOperatorId(user.getId());
		}
		this.delete(intentionHintRewardRecord);
	}

	/**
	 * 领取奖品
	 * @param id
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setIsGet(String id) throws Exception{
		IntentionHintRewardRecord intentionHintRewardRecord = this.get(id);
		if(intentionHintRewardRecord == null){
			throw new Exception("未找到对象");
		}
		Boolean isGet = intentionHintRewardRecord.getIsGet();
		if(isGet != null && isGet){
			throw new Exception("奖品已领取，不能再次领取");
		}
		intentionHintRewardRecord.setIsGet(true);
		intentionHintRewardRecord.setGetDate(new Date());
		this.update(intentionHintRewardRecord);
	}

	/**
	 * 随机意向词条
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public synchronized void createForIntentionType(User user, PublicEnum.ClientStatus clientStatus) throws Exception{
		if(user == null){
			throw new Exception("未找到用户");
		}
		IntentionHintCondition intentionHintCondition = new IntentionHintCondition();
		intentionHintCondition.setbDel(false);
		intentionHintCondition.setType(PublicEnum.IntentionHintType.Intention);
		intentionHintCondition.setGtCount(0);
		List<IntentionHint> intentionHintList = intentionHintService.list(intentionHintCondition);
		if(intentionHintList.isEmpty()){
			return;
		}
		Integer totalRate = 0;
		for(IntentionHint intentionHint : intentionHintList){
			totalRate += intentionHint.getRate();
			System.out.println(intentionHint.getId() + " 总数区间： " + totalRate);
		}
		Integer num = new Random().nextInt(totalRate) + 1;
		System.out.println("随机数：" + num);
		totalRate = 0;
		for(IntentionHint intentionHint : intentionHintList){
			totalRate += intentionHint.getRate();
			if(num <= totalRate){
				String content = intentionHint.getContent();
				content = content.replaceAll("\\[name\\]", user.getName());
				content = content.replaceAll("\\[intention\\]", clientStatus.getName());
				if(intentionHint.getIsReward()){
					IntentionHintRewardRecord record = new IntentionHintRewardRecord();
					record.setHintId(intentionHint.getId());
					record.setUserId(user.getId() + "");
					record.setContent(content);
					record.setUserName(user.getName());
					record.setType(PublicEnum.IntentionHintType.Intention);
					this.create(record);
				}
				intentionHint.setCount(intentionHint.getCount() - 1);
				intentionHint.setTodayCount(intentionHint.getTodayCount() + 1);
				intentionHintService.update(intentionHint);
				webSocketService.showHint(content, WebSocketData.WebSocketWant.ShowIntentionHint);
				break;
			}
		}
	}


	/**
	 * 砸金蛋
	 * @param request
	 * @throws IOException
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object>  createForGoldenType( HttpServletRequest request) throws Exception{
		User user = (User)request.getSession().getAttribute("_user");
		user = userService.get(user.getId());
		if(user == null){
			throw new Exception("未找到用户");
		}
		String roleId = user.getRoleId();
		if(roleId.equals(QingpuConstants.Roles_Super_id) || roleId.equals(QingpuConstants.Roles_Manager_id)){
			throw new Exception("您的角色不能砸金蛋");
		}
		Date now = new Date();
		Integer userId = user.getId();
		String name = user.getName();
		UserInfo userInfo = userInfoService.getByName(name);
		if(userInfo.getIsGoldenEgg()){
			throw new Exception("今天已经砸过金蛋了");
		}
		Map<String, Object> resultMap = new HashMap<>();
		IntentionHintCondition intentionHintCondition = new IntentionHintCondition();
		intentionHintCondition.setbDel(false);
		intentionHintCondition.setType(PublicEnum.IntentionHintType.GoldenEgg);
		intentionHintCondition.setGtCount(0);
		List<IntentionHint> intentionHintList = intentionHintService.list(intentionHintCondition);
		if( ! intentionHintList.isEmpty()){
			Integer totalRate = 0;
			for(IntentionHint intentionHint : intentionHintList){
				totalRate += intentionHint.getRate();
				System.out.println(intentionHint.getId() + " 总数区间： " + totalRate);
			}
			Integer num = new Random().nextInt(totalRate) + 1;
			System.out.println("随机数：" + num);
			totalRate = 0;
			for(IntentionHint intentionHint : intentionHintList){
				totalRate += intentionHint.getRate();
				if(num <= totalRate){
					String content = intentionHint.getContent();
					Boolean isWin = false;
					if(intentionHint.getIsReward()){
						IntentionHintRewardRecord record = new IntentionHintRewardRecord();
						record.setHintId(intentionHint.getId());
						record.setUserId(user.getId() + "");
						content = content.replaceAll("\\[name\\]", user.getName());
						record.setContent(content);
						record.setUserName(user.getName());
						record.setType(PublicEnum.IntentionHintType.GoldenEgg);
						this.create(record);
						isWin = true;
						webSocketService.showHint(content, WebSocketData.WebSocketWant.ShowGoldenHint);
					}
					intentionHint.setCount(intentionHint.getCount() - 1);
					intentionHint.setTodayCount(intentionHint.getTodayCount() + 1);
					intentionHintService.update(intentionHint);
					content = intentionHint.getContent();
					content = content.replaceAll("\\[name\\]", "你");
					resultMap.put("result", content);
					resultMap.put("isWin", isWin);
					userInfo.setIsGoldenEgg(true);
					break;
				}
			}
		}

		/*
		每日提醒
		 */
		String nickName = dayHintService.getNickName();
		Date employeeDay = userInfo.getEmployeeDay();
		long subDay = DateUtil.daysBetween(employeeDay, now);
		DayHint dayHint = dayHintService.getRateDayHintByType(PublicEnum.DayHintType.Day);
		if(dayHint != null){
		    resultMap.put("nickName", nickName);
		    resultMap.put("name", name);
		    resultMap.put("dayCount", subDay + 1);
			resultMap.put("dayHint", dayHint.getContent());
			resultMap.put("headImage", userInfo.getHeadImage());
		}

		/*
		阶段提醒
		 */
		String stageHint = "";
        PublicEnum.PostMessageType postMessageType = null;
		if(  ! userInfo.getIsFirstDayHint() && subDay == 0 ){
            postMessageType = PublicEnum.PostMessageType.FirstDay;
			stageHint += "今天是你来到擎谱的第一天";
			userInfo.setIsFirstDayHint(true);
		}else if ( ! userInfo.getIsWeekDayHint() && subDay == 6 ){
            postMessageType = PublicEnum.PostMessageType.WeekDay;
			stageHint += "你来到擎谱已经一周啦";
			userInfo.setIsWeekDayHint(true);
		}else if (  ! userInfo.getIsMonthHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDMONTH, 1), now) == 0){
            postMessageType = PublicEnum.PostMessageType.MonthDay;
			stageHint += "你已经在擎谱工作一个月了";
			userInfo.setIsMonthHint(true);
		}else if (  ! userInfo.getIsFirstYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 1), now) == 0){
            postMessageType = PublicEnum.PostMessageType.YearDay;
			stageHint += "这是你在擎谱的第365天";
			userInfo.setIsFirstYearHint(true);
		}else if (  ! userInfo.getIsSecondYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 2), now) == 0){
            postMessageType = PublicEnum.PostMessageType.SecondYear;
			stageHint += "你来到擎谱已经两年啦";
			userInfo.setIsSecondYearHint(true);
		}else if (  ! userInfo.getIsThirdYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 3), now) == 0){
            postMessageType = PublicEnum.PostMessageType.ThirdYear;
			stageHint += "你来到擎谱已经三年啦";
			userInfo.setIsThirdYearHint(true);
		}else if (  ! userInfo.getIsFourthYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 4), now) == 0){
            postMessageType = PublicEnum.PostMessageType.FourthYear;
			stageHint += "你来到擎谱已经四年啦";
			userInfo.setIsFourthYearHint(true);
		}else if (  ! userInfo.getIsFifthYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 5), now) == 0){
            postMessageType = PublicEnum.PostMessageType.FifthYear;
			stageHint += "你来到擎谱已经五年啦";
			userInfo.setIsFifthYearHint(true);
		}else if (  ! userInfo.getIsSixthYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 6), now) == 0){
            postMessageType = PublicEnum.PostMessageType.SixthYear;
			stageHint += "你来到擎谱已经六年啦";
			userInfo.setIsSixthYearHint(true);
		}else if (  ! userInfo.getIsSeventhYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 7), now) == 0){
            postMessageType = PublicEnum.PostMessageType.SeventhYear;
			stageHint += "你来到擎谱已经七年啦";
			userInfo.setIsSeventhYearHint(true);
		}else if (  ! userInfo.getIsEighthYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 8), now) == 0){
            postMessageType = PublicEnum.PostMessageType.EighthYear;
			stageHint += "你来到擎谱已经八年啦";
			userInfo.setIsEighthYearHint(true);
		}else if (  ! userInfo.getIsNinthYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 9), now) == 0){
            postMessageType = PublicEnum.PostMessageType.NinthYear;
			stageHint += "你来到擎谱已经九年啦";
			userInfo.setIsNinthYearHint(true);
		}else if (  ! userInfo.getIsTenthYearHint() && DateUtil.daysBetween(DateUtil.add(employeeDay, DateUtil.ADDYEAR, 10), now) == 0){
            postMessageType = PublicEnum.PostMessageType.TenthYear;
			stageHint += "你来到擎谱已经十年啦";
			userInfo.setIsTenthYearHint(true);
		}
		if(StringUtils.isNotBlank(stageHint)){
			DayHint dayHintItem = dayHintService.getRateDayHintByType(PublicEnum.DayHintType.MyStage);
			if(dayHintItem != null){
			    resultMap.put("stageDayHint", dayHintItem.getContent());
				resultMap.put("stageHint", stageHint);
			}
            DayHint dayHintStage = dayHintService.getRateDayHintByType(PublicEnum.DayHintType.Stage);
            if(dayHintStage != null){
                String content = "今天是" + name + "加入擎谱大家庭" + postMessageType.getName() + "，" + dayHintStage.getContent();
                postMessageService.create("", content, userId, name, postMessageType, null);
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("headImage", QingpuConstants.uploadUrl + userInfo.getHeadImage());
                map.put("stage", postMessageType.getName());
                map.put("content", dayHintStage.getContent());
                for(Session session : WebSocketService.cocosOnlineSession){
                    webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.SendStage, map));
                }
            }
		}

		/*
		生日提醒
		 */
		Date birthDay = userInfo.getBirthDay();
		if(  ! userInfo.getIsBirthdayHint() && birthDay != null && DateUtil.dateToString(now, 6).equals(DateUtil.dateToString(birthDay, 6))){
			DayHint dayHintItem = dayHintService.getRateDayHintByType(PublicEnum.DayHintType.BirthDay);
			if(dayHintItem != null){
				String content = dayHintItem.getContent().replaceAll("\\[name\\]", name);
				postMessageService.create("", content, userId, name, PublicEnum.PostMessageType.Birthday, null);
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("nickName",nickName );
                map.put("headImage", QingpuConstants.uploadUrl + userInfo.getHeadImage());
                map.put("content", content);
                for(Session session : WebSocketService.cocosOnlineSession){
                    webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.SendBirthday, map));
                }
			}
			DayHint dayHintBirthday = dayHintService.getRateDayHintByType(PublicEnum.DayHintType.MyBirthDay);
			if(dayHintBirthday != null){
				stageHint =  dayHintBirthday.getContent();
				resultMap.put("birthdayHint", stageHint);
			}
			userInfo.setIsBirthdayHint(true);
		}
		userInfoService.update(userInfo);

		return resultMap;
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
			IntentionHintRewardRecord intentionHintRewardRecord = this.get(id);
			if(intentionHintRewardRecord != null && ! intentionHintRewardRecord.getIsGet()){
				intentionHintRewardRecord.setIsGet(true);
				intentionHintRewardRecord.setGetDate(now);
				this.update(intentionHintRewardRecord);
			}
		}
	}
}
