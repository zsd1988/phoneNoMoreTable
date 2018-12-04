package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.func.webSocket.WebSocketData;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.GroupCallCondition;
import com.qingpu.phone.systemManager.condition.GroupCallDetailCondition;
import com.qingpu.phone.systemManager.entity.GroupCall;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service("groupCallService")
@Transactional
public class GroupCallService {
	private static Logger logger = Logger.getLogger(GroupCallService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	PrologueService prologueService;

	@Resource
	GroupCallDetailService groupCallDetailService;

	/**
	 * 创建
	 * @param groupCall
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public GroupCall create(GroupCall groupCall) throws Exception{
		if(groupCall != null && StringUtils.isBlank(groupCall.getId())){
			groupCall.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(groupCall);
		return (GroupCall) baseDao.save(groupCall);
	}


	/**
	 * 更新
	 * @param groupCall
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(GroupCall groupCall)  throws Exception {
		CommonUtils.checkEntity(groupCall);
		baseDao.update(groupCall);
	}


	/**
	 * 删除
	 * @param groupCall
	 */
	public void delete(GroupCall groupCall) {
		groupCall.setIsEnable(true);
		groupCall.setDelTime(new Date());
		baseDao.update(groupCall);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public GroupCall get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (GroupCall)baseDao.get(GroupCall.class, id);
	}


	/**
	 * 按条件查找
	 * @param groupCallCondition
	 * @return
	 */
	public List<GroupCall> list(GroupCallCondition groupCallCondition){
		return  groupCallCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param groupCallCondition
	 * @param sorter
	 * @return
	 */
	public List<GroupCall> list(GroupCallCondition groupCallCondition, Sorter sorter){
		return  groupCallCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param groupCallCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<GroupCall> list(GroupCallCondition groupCallCondition, Range range, Sorter sorter){
		PaginationSupport<GroupCall> groupCallPaginationSupport = (PaginationSupport<GroupCall>)groupCallCondition.pageList(sessionFactory, sorter, range);
		return groupCallPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param groupCallCondition
	 * @return
	 */
	public Long countByCondition(GroupCallCondition groupCallCondition){
		return   groupCallCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param groupCallCondition
	 * @return
	 */
	public List<Object[]> listCustom(GroupCallCondition groupCallCondition){
		return  groupCallCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param groupCallCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(GroupCallCondition groupCallCondition, int num){
		return  groupCallCondition.list(sessionFactory, num);
	}

	/**
	 * 根据参数创建
	 * @param groupCall
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(GroupCall groupCall) throws Exception{
		this.updateCommon(groupCall);
		groupCall = this.create(groupCall);
		Map<String, Object> map = new HashMap<>();
		map.put("id", groupCall.getId());
		return map;
	}

	/**
	 * 更新创建共同
	 * @param groupCall
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateCommon(GroupCall groupCall) throws Exception{
		if(StringUtils.isBlank(groupCall.getName())){
			throw new Exception("请输入任务名称");
		}
		if(StringUtils.isBlank(groupCall.getProjectId())){
			throw new Exception("请选择项目");
		}
		Double rate = groupCall.getRate();
		if(rate == null){
			throw new Exception("请输入并发倍率");
		}
		if(rate <= 0){
			throw new Exception("输入并发倍率非法");
		}
		if(groupCall.getVoiceType() == null){
			throw new Exception("请选择工作模式");
		}
		if(StringUtils.isBlank(groupCall.getExtNumList())){
			throw new Exception("请至少选择一个工号");
		}
	}

	/**
	 * 根据参数更新
	 * @param paramsGroupCall
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(GroupCall paramsGroupCall) throws Exception{
		this.updateCommon(paramsGroupCall);
		GroupCall groupCall = this.get(paramsGroupCall.getId());
		if(groupCall == null){
			throw new Exception("未找到对象");
		}
		groupCall.setName(paramsGroupCall.getName());
		String id = groupCall.getId();
		Boolean isEnable = groupCall.getIsEnable();

		// 并发倍率
		Double paramsRate = paramsGroupCall.getRate();
		Double rate = groupCall.getRate();
		if(paramsRate != rate){
			groupCall.setRate(ArithUtil.getRoundTwo(paramsRate));
		}

		// 改变群呼项目
		String paramsProjectId = paramsGroupCall.getProjectId();
		String projectId = groupCall.getProjectId();
		if( ! projectId.equals(paramsProjectId)){
			groupCall.setProjectId(paramsProjectId);
			CallPhoneListener._groupCallAndProjectMap.put(id, paramsProjectId);
		}
		// 更新工作模式
		PublicEnum.VoiceType paramsVoiceType = paramsGroupCall.getVoiceType();
		PublicEnum.VoiceType voiceType = groupCall.getVoiceType();
		if(paramsVoiceType != voiceType){
			groupCall.setVoiceType(paramsVoiceType);
			if(isEnable){
				CallPhoneListener._callVoiceType.put(id, paramsVoiceType);
			}
		}
		// 更新结束时间
		String paramsTime1 = paramsGroupCall.getEndTime1();
		String time1 = groupCall.getEndTime1();
		if( ! paramsTime1.equals(time1)){
		    groupCall.setEndTime1(paramsTime1);
        }
        String paramsTime2 = paramsGroupCall.getEndTime2();
        String time2 = groupCall.getEndTime2();
        if( ! paramsTime2.equals(time2)){
            groupCall.setEndTime2(paramsTime2);
        }
        String paramsTime3 = paramsGroupCall.getEndTime3();
        String time3 = groupCall.getEndTime3();
        if( ! paramsTime3.equals(time3)){
            groupCall.setEndTime3(paramsTime3);
        }
        String paramsTime4 = paramsGroupCall.getEndTime4();
        String time4 = groupCall.getEndTime4();
        if( ! paramsTime4.equals(time4)){
            groupCall.setEndTime4(paramsTime4);
        }
		String paramsExtNumList = paramsGroupCall.getExtNumList();
		String extNumList = groupCall.getExtNumList();
		if( ! extNumList.equals(paramsExtNumList)){
			groupCall.setExtNumList(paramsExtNumList);
			if(isEnable){
				this.updateExtNumList();
			}
		}
		this.update(groupCall);
		if(isEnable){
			CallPhoneListener._groupCallWorkNumMap.put(id, groupCall.getExtNumList());
			this.setStopGroupCallRunnable(groupCall);
			this.updateRateNum();
		}
	}

	/**
	 * 创建停止群呼计划任务
	 * @param groupCall
	 */
	private void setStopGroupCallRunnable(GroupCall groupCall){
		String groupCallId = groupCall.getId();
		if(groupCall.getIsEnable()){
			Date now = new Date();
			Date endTime = null;
			long minSub = 1000000000;
			for(int i = 1; i < 5; i++){
				String endTimeStr = null;
				switch (i){
					case 1:
						endTimeStr = groupCall.getEndTime1();
						break;
					case 2:
						endTimeStr = groupCall.getEndTime2();
						break;
					case 3:
						endTimeStr = groupCall.getEndTime3();
						break;
					case 4:
						endTimeStr = groupCall.getEndTime4();
						break;
				}
				if(endTimeStr == null){
					continue;
				}
				Date time = DateUtil.strDateToDate(endTimeStr, 5);
				long sub = DateUtil.millisecondsBetween(now, time);
				if(sub > 0 && sub < minSub){
					minSub = sub;
					endTime = time;
				}
			}
			if(endTime != null){
				if(CallPhoneListener._groupCallStopMap.containsKey(groupCallId)){
					StopRunnable stopRunnable = CallPhoneListener._groupCallStopMap.get(groupCallId);
					if(DateUtil.millisecondsBetween(endTime, stopRunnable.stopTime) == 0){
						return;
					}
					stopRunnable.stop();
					CallPhoneListener._groupCallStopMap.remove(groupCallId);
				}
				StopRunnable stopRunnable = new StopRunnable();
				stopRunnable.start(endTime, groupCall.getId(), this);
				CallPhoneListener._groupCallStopMap.put(groupCallId, stopRunnable);
			}
		}else{
			if(CallPhoneListener._groupCallStopMap.containsKey(groupCallId)){
				StopRunnable stopRunnable = CallPhoneListener._groupCallStopMap.get(groupCallId);
				stopRunnable.stop();
				CallPhoneListener._groupCallStopMap.remove(groupCallId);
			}
		}
	}

	/**
	 * java服务器停止时，关闭群呼的任务
	 */
	public void stopTaskScheduler(){
		logger.info("java服务器停止时，关闭群呼的任务");
		for(String key : CallPhoneListener._groupCallStopMap.keySet()){
			GroupCallService.StopRunnable stopRunnable = CallPhoneListener._groupCallStopMap.get(key);
			stopRunnable.stop();
			CallPhoneListener._groupCallStopMap.remove(key);
		}
	}

	/**
	 * 停止群呼任务
	 */
	public class StopRunnable implements Runnable {
		private ThreadPoolTaskScheduler concurrentTaskScheduler = new ThreadPoolTaskScheduler();
		private String groupCallId = "";
		private GroupCallService groupCallService;
		public Date stopTime;

		private void start(Date endTime, String groupCallId, GroupCallService groupCallService) {
			concurrentTaskScheduler.initialize();
			concurrentTaskScheduler.schedule(this, endTime);
			this.stopTime = endTime;
			this.groupCallId = groupCallId;
			this.groupCallService = groupCallService;
		}

		public void stop(){
			concurrentTaskScheduler.shutdown();
		}

		public void run() {
			try {
				logger.info("自动停止群呼");
				groupCallService.setIsEnable(groupCallId, false);
				if(CallPhoneListener._groupCallStopMap.containsKey(groupCallId)){
					CallPhoneListener._groupCallStopMap.remove(groupCallId);
				}
			}catch(Exception e1){
				logger.error("自动停止群呼失败id为：" + groupCallId + "  错误信息为：" + e1.getMessage());
			}
		}
	}

	/**
	 * 关闭所有开启的群呼
	 * @throws Exception
	 */
	public void setAllDisable(){
		try {
			GroupCallCondition	groupCallCondition = new GroupCallCondition();
			groupCallCondition.setEnable(true);
			List<GroupCall> groupCallList = this.list(groupCallCondition);
			for(GroupCall groupCall : groupCallList){
				groupCall.setIsEnable(false);
				this.update(groupCall);
			}
		}catch (Exception e){
			logger.error("关闭所有开启的群呼失败" + e.getMessage());
		}
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param isEnable
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setIsEnable(String id, Boolean isEnable) throws Exception{
		GroupCall groupCall = this.get(id);
		if(groupCall == null){
			throw new Exception("操作失败：未找到数据");
		}
		if(groupCall.getIsEnable() == isEnable){
			return;
		}
		String projectId = groupCall.getProjectId();
		List<String> extNumList = StringUtil.splitByComma(groupCall.getExtNumList());
		groupCall.setIsEnable(isEnable);
		/*
		判断是否有客服负责两个不同的项目
		 */
		if(isEnable){
			GroupCallCondition groupCallCondition = new GroupCallCondition();
			groupCallCondition.setEnable(true);
			List<GroupCall> groupCallList = this.list(groupCallCondition);
			for(GroupCall groupCallItem : groupCallList){
				if( ! projectId.equals(groupCallItem.getProjectId())){
					List<String> extNumListItem = StringUtil.splitByComma(groupCallItem.getExtNumList());
					for(String item1 : extNumList){
						for(String item2 : extNumListItem){
							if(item1.equals( item2 ) ){
								throw new Exception("工号：" + item1 + "负责了两个项目");
							}
						}
					}
				}
			}
		}

		/*
		重置全局变量：概率，拨号总数
		 */
		final String groupCallId = groupCall.getId();
		if(isEnable){
			logger.info("启动群呼。。。" + groupCall.getName());
			CallPhoneListener._callPhoneListMap.put(groupCallId, new ArrayList<GroupCallDetail>());
			CallPhoneListener._groupCallAndProjectMap.put(groupCallId, projectId);
			CallPhoneListener._callVoiceType.put(groupCallId, groupCall.getVoiceType());
			CallPhoneListener._groupCallWorkNumMap.put(groupCallId, groupCall.getExtNumList());
			CallPhoneListener._groupCallConNumMap.put(groupCallId, 0);
			prologueService.setPrologueMap(projectId, groupCallId);

			if(  CallPhoneListener.callPhoneThread == null){
                CallPhoneListener.start();
			}
		}else{
			logger.info("关闭群呼。。。" + groupCall.getName());
			if(CallPhoneListener._callPhoneListMap.containsKey(groupCallId)){
                CallPhoneListener._callPhoneListMap.put(groupCallId, new ArrayList<GroupCallDetail>());
				CallPhoneListener._callPhoneListMap.remove(groupCallId);
				CallPhoneListener._groupCallAndProjectMap.remove(groupCallId);
				CallPhoneListener._callPhoneNum.remove(groupCallId);
                CallPhoneListener._callVoiceType.remove(groupCallId);
				CallPhoneListener._groupCallConNumMap.remove(groupCallId);
				// 重新计算概率
				CallPhoneListener._callPhoneRate.remove(groupCallId);
				for (String key : CallPhoneListener._callPhoneRate.keySet()) {
					CallPhoneListener._callPhoneNum.put(key, 0);
				}
			}
		}
		this.updateExtNumList();
		this.update(groupCall);
		this.updateRateNum();
		this.setStopGroupCallRunnable(groupCall);
		final GroupCallService groupCallService = this;
		final GroupCallDetailService finalGroupCallDetailService = groupCallDetailService;
//		停止时后处理
		if( ! isEnable ){
			Thread thread = new Thread(){
				public void run(){
					try {
						GroupCallDetailCondition groupCallDetailCondition = new GroupCallDetailCondition();
						groupCallDetailCondition.setStatus(PublicEnum.GroupCallDetailStatus.ResetWaiting);
						groupCallDetailCondition.setGroupCallId(groupCallId);
						List<GroupCallDetail> groupCallDetailList = finalGroupCallDetailService.list(groupCallDetailCondition);
						for(GroupCallDetail groupCallDetail : groupCallDetailList){
							groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Waiting);
							finalGroupCallDetailService.update(groupCallDetail);
						}
					}catch (Exception e){
						System.out.println(e.getMessage());
					}
				}
			 };
			thread.start();
		}
	}

	/**
	 * 重新计算并发数
	 */
	@Transactional(rollbackFor = Exception.class)
	public void  updateRateNum() throws Exception{
		for(String key : CallPhoneListener._groupCallConNumMap.keySet()){
			GroupCall groupCall = this.get(key);
			if(groupCall != null && groupCall.getIsEnable()){
				Integer loginUserCount = 0;
				List<String> extNumList = StringUtil.splitByComma(groupCall.getExtNumList());
				for(String extNum : extNumList){
					if(CallPhoneListener._onlineUserWorkNumList.contains(extNum)){
						loginUserCount++;
					}
				}
				List<String> phoneList = CallPhoneListener._groupCallCallingPhoneMap.get(key);	// 通话中
				Integer conCount = 0;
				Integer phoneCount = 0;
				if(phoneList != null){
					conCount = phoneList.size();
					phoneCount = conCount;
				}
				switch (loginUserCount){
					case 0:
						conCount = 0;
						break;
					case 1:
						conCount += 1;
						break;
					default:
						Double count = ArithUtil.round(groupCall.getRate() * loginUserCount, 0);
						conCount += count.intValue();
						break;
				}
				CallPhoneListener._groupCallConNumMap.put(key, conCount);
				Integer callCount = 0;
				List<String> portIdList = CallPhoneListener._groupCallPortIdMap.get(key);
				if(portIdList != null){
					callCount = portIdList.size() - phoneCount;
				}
				String alreadyStr = "";
				if(CallPhoneListener._callPhoneNum.containsKey(key)){
					Integer alreadyCount = CallPhoneListener._callPhoneNum.get(key);
					if(alreadyCount != null){
						alreadyStr = " 已呼数： " + alreadyCount;
					}
				}
				logger.info(groupCall.getName() + " 倍率为： " + groupCall.getRate() + " 空闲客服数： " + loginUserCount
						+ " 通话中： " + phoneCount + "  群呼并发数： " + conCount  + alreadyStr + " 呼叫中： " + callCount);
			}else{
//				CallPhoneListener._groupCallConNumMap.remove(key);
			}
		}
	}

	/**
	 * 重置拨打数据以及计算概率
	 */
	public void updateExtNumList(){
		Double totalCount = 0.0;
		Map<String, Double> groupCallExtNumMap = new HashMap<>();
        for(String key : CallPhoneListener._groupCallWorkNumMap.keySet()){
			GroupCall groupCall1Item = this.get(key);
			if(groupCall1Item.getIsEnable()){
				CallPhoneListener._callPhoneNum.put(key, 0);
				List<String> extNumTempList = StringUtil.splitByComma(groupCall1Item.getExtNumList());
				Double size = extNumTempList.size() * groupCall1Item.getRate()*1.0;
				groupCallExtNumMap.put(key, size);
				totalCount += size;
			}
		}
        for(String key : groupCallExtNumMap.keySet()){
			CallPhoneListener._callPhoneRate.put(key, groupCallExtNumMap.get(key)*1.0/totalCount);
		}
	}

	/**
	 * 获取表下拉选择框
	 * @return
	 */
	public List<Map.Entry<String, String>> getSelect(){
		GroupCallCondition groupCallCondition = new GroupCallCondition();
		List<GroupCall> groupCallList = this.list(groupCallCondition, new Sorter().desc("create_time"));
		Map<String, String> map = new HashMap<>();
		for(GroupCall groupCall : groupCallList){
			map.put(groupCall.getId(), groupCall.getName());
		}
		List<Map.Entry<String, String>> infoIds =
				new ArrayList<>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return infoIds;
	}

	/**
	 * 根据Id获取名称
	 * @param id
	 * @return
	 */
	public String getName(String id){
		GroupCall groupCall = this.get(id);
		String name = null;
		if(groupCall != null){
			name = groupCall.getName();
		}
		return name;
	}


	/**
	 * 获取群呼记录列表
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Integer> getTableMap() throws Exception{
		String strSql = "select count(1),group_call_id FROM group_call_detail a where `status` = 'Waiting'   GROUP BY  group_call_id ORDER BY group_call_id";
		@SuppressWarnings("unchecked")
		List<Object> list = sessionFactory.getCurrentSession().createSQLQuery(strSql).list();
		Map<String, Integer> groupIdMap = new HashMap<>();
		for(int i=0;i<list.size();i++){
			Object[] obj= (Object[]) list.get(i);
			Object obj0 = obj[0];
			if(obj0 == null){
				continue;
			}
			Object obj1 = obj[1];
			if(obj1 == null){
				continue;
			}
			String groupCallId = obj1.toString();
			groupIdMap.put(groupCallId, ((BigInteger)obj0).intValue());
		}
		return groupIdMap;
	}
}
