package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.clientManager.condition.ClientGroupCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.entity.ClientGroup;
import com.qingpu.phone.clientManager.service.ClientGroupService;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationParams;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.CallRecordCondition;
import com.qingpu.phone.systemManager.condition.GroupCallCondition;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.entity.GroupCall;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.phoneSocket.PhoneSocket;
import com.qingpu.phone.systemManager.phoneSocket.UserPhoneSocket;
import com.qingpu.phone.systemSetting.condition.PortCondition;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.systemSetting.service.PortService;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.condition.UserGroupCondition;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserGroup;
import com.qingpu.phone.user.service.UserGroupService;
import com.qingpu.phone.user.service.UserService;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service("callRecordService")
@Transactional
public class CallRecordService {
	private static Logger logger = Logger.getLogger(CallRecordService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private ProjectService projectService;

	@Resource
	private ClientService clientService;

	@Resource
	private UserService userService;

	@Resource
	private PortService portService;

    @Resource
    private ClientGroupService clientGroupService;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	GroupCallService groupCallService;

	/**
	 * 创建
	 * @param callRecord
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public CallRecord create(CallRecord callRecord) throws Exception{
		if(callRecord != null && StringUtils.isBlank(callRecord.getId())){
			callRecord.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(callRecord);
		return (CallRecord) baseDao.save(callRecord);
	}


	/**
	 * 更新
	 * @param callRecord
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(CallRecord callRecord)  throws Exception {
		CommonUtils.checkEntity(callRecord);
		baseDao.update(callRecord);
	}


	/**
	 * 删除
	 * @param callRecord
	 */
	public void delete(CallRecord callRecord) {
		callRecord.setIsDel(true);
		callRecord.setDelTime(new Date());
		baseDao.update(callRecord);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public CallRecord get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (CallRecord)baseDao.get(CallRecord.class, id);
	}


	/**
	 * 按条件查找
	 * @param callRecordCondition
	 * @return
	 */
	public List<CallRecord> list(CallRecordCondition callRecordCondition){
		return  callRecordCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param callRecordCondition
	 * @param sorter
	 * @return
	 */
	public List<CallRecord> list(CallRecordCondition callRecordCondition, Sorter sorter){
		return  callRecordCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param callRecordCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<CallRecord> list(CallRecordCondition callRecordCondition, Range range, Sorter sorter){
		PaginationSupport<CallRecord> testTablePaginationSupport = (PaginationSupport<CallRecord>) callRecordCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param callRecordCondition
	 * @return
	 */
	public Long countByCondition(CallRecordCondition callRecordCondition){
		return   callRecordCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param callRecordCondition
	 * @return
	 */
	public List<Object[]> listCustom(CallRecordCondition callRecordCondition){
		return  callRecordCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param callRecordCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(CallRecordCondition callRecordCondition, int num){
		return  callRecordCondition.list(sessionFactory, num);
	}

    public List<CallRecord> listCustom(CallRecordCondition testTableCondition, Sorter sorter, int num) {
        return AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, sorter, num));
    }

    /**
     * 根据参数创建
     *
     * @param callRecord
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createByParams(CallRecord callRecord) throws Exception {
        User user = userService.get(Integer.parseInt(callRecord.getUserId()));
        if (user != null) {
            if (CallPhoneListener._inCallingUser.contains(user.getId())) {
                throw new Exception("通话过程中或未点保存的情况，不能主动拨打客户电话");
            }
            if (user.getIsOnline()) {
                throw new Exception("请设置为休息状态");
            }
            callRecord.setUserName(user.getName());
            callRecord.setWorkType(user.getWorkType());
            callRecord.setWorkNumber(user.getWorkNumber());
            callRecord.setExtNum(user.getExtNum());
        }
        Client client = clientService.get(callRecord.getClientId());
        if (client != null) {
            callRecord.setProjectId(client.getProjectId());
            callRecord.setPhone(client.getPhone());
        } else {
            throw new Exception("未找到客户手机号");
        }
        client.setLastUpdateTime(new Date());
        clientService.update(client);
        Map<String, Object> map = new HashMap<>();
        map.put("id", callRecord.getId());
        if (CallPhoneListener._callPhoneListMap.size() == 0) {
            // 没有启动群呼则在空闲端口找
            String portId = portService.getIdlePortId(true, callRecord.getPhone());
            this.startCall(callRecord, portId);
        } else {
            // 启动了群呼则跟群呼任务一起找端口
            Boolean isExit = false;
            String phone = callRecord.getPhone();
            for (CallRecord callRecord1 : CallPhoneListener._waitingCaller) {
                if (callRecord1.getPhone().equals(phone)) {
                    isExit = true;
                    break;
                }
            }
            if (!isExit) {
                CallPhoneListener._waitingCaller.add(callRecord);
            }
            map.put("message", "正在寻找端口，请稍后");
        }
		return map;
	}


    /**
     * 输入号码拨打电话
     *
     * @param phone
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> toCallPhone(String phone, HttpServletRequest request) throws Exception {
        String tempPhone = phone;
        if (tempPhone.startsWith("0")) {
            tempPhone = tempPhone.substring(1, tempPhone.length());
        }
        if (!StringUtil.checkIsPhone(tempPhone)) {
            throw new Exception("拨打的手机号格式不正确");
        }
        User user = (User) request.getSession().getAttribute("_user");
        user = userService.get(user.getId());
        CallRecord callRecord = new CallRecord();
        if (user != null) {
            if (CallPhoneListener._inCallingUser.contains(user.getId())) {
                throw new Exception("通话过程中或未点保存的情况，不能主动拨打客户电话");
            }
            if (user.getIsOnline() && user.getRoleId().equals(QingpuConstants.Roles_Interview_id)) {
                throw new Exception("请设置为休息状态");
            }
            if (user.getExtNum() == null) {
                throw new Exception("分机号不能为空");
            }
            callRecord.setExtNum(user.getExtNum());
            callRecord.setPhone(phone);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", callRecord.getId());
        if (CallPhoneListener._callPhoneListMap.size() == 0) {
            // 没有启动群呼则在空闲端口找
            String portId = portService.getIdlePortId(true, phone);
            this.startCall(callRecord, portId);
        } else {
            // 启动了群呼则跟群呼任务一起找端口
            Boolean isExit = false;
            for (CallRecord callRecord1 : CallPhoneListener._waitingCaller) {
                if (callRecord1.getPhone().equals(phone)) {
                    isExit = true;
                    break;
                }
            }
            if (!isExit) {
                CallPhoneListener._waitingCaller.add(callRecord);
            }
            map.put("message", "正在寻找端口，请稍后");
        }
        return map;
    }

	/**
	 * 呼叫
	 * @param callRecord
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void startCall(CallRecord callRecord, String portId) throws Exception{
		try{
			Port port = CallPhoneListener._idlePortMap.get(portId);
			UserPhoneSocket phoneSocket = new UserPhoneSocket(CallPhoneListener._socketHost,CallPhoneListener._socketPort, callRecord, this, port);
			phoneSocket.startCall();
			if(CallPhoneListener._waitingCaller.contains(callRecord)){
				CallPhoneListener._waitingCaller.remove(callRecord);
			}
		}catch (Exception e){
			CallPhoneListener._idlePortIdList.add(portId);
			throw new Exception("回拨电话失败：" + e.getMessage());
		}
	}


	/**
	 * 根据参数更新
	 * @param paramsCallRecord
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(CallRecord paramsCallRecord) throws Exception{
		CallRecord callRecord = this.get(paramsCallRecord.getId());
		if(callRecord == null){
			throw new Exception("未找到对象");
		}
		this.update(callRecord);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		CallRecord callRecord = this.get(id);
		if(callRecord == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			callRecord.setOperatorId(user.getId());
		}
		this.delete(callRecord);
	}

	/**
	 * 锁定记录
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setLock(String id, HttpServletRequest request) throws Exception{
		CallRecord callRecord = this.get(id);
		if(callRecord == null){
			throw new Exception("锁定失败：未找到数据");
		}
		if( ! callRecord.getIsLock()){
			callRecord.setIsLock(true);
			this.update(callRecord);
		}
	}

	/**
	 * 获取通话记录列表
	 * @param tableParams
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> getList(TableParams tableParams, HttpServletResponse response, HttpServletRequest request) throws Exception{
		Boolean isExport = tableParams.getCommon();		// 是否导出
		String listSql = "select a.id, a.client_id clientId,a.time, a.work_number workNumber, a.review_work_number reviewWorkNumber, b.name clientName, " +
				"a.phone, b.review_status clientReviewStatus,b.group_id groupId, b.group_type groupType," +
				"a.create_time, b.des clientDes, a.project_id, a.user_id,a.user_name, a.record_path recordPath, a.review_id , a.is_lock isLock, " +
				"b.is_appeal isAppeal, b.status clientStatus, ";
		Boolean isShowTrueConfirmStatus = tableParams.getInclude();
		if(isShowTrueConfirmStatus != null && isShowTrueConfirmStatus){
			listSql += " b.confirm_status clientConfirmStatus ";
		}else{
			listSql += " b.first_confirm_status clientConfirmStatus ";
		}
		String countSql = "select count(*) ";
		String baseSql = " from call_record a left join client b on b.id = a.client_id  " +
				"right JOIN ( SELECT max(a.create_time) createTime,a.client_id clientId from call_record a left join client b on b.id = a.client_id  ";
		String condition = " a.work_number != 'robot' ";
		// 手机
		String phone = tableParams.getPhone();
		if(StringUtils.isNotBlank(phone)){
			condition += " and a.phone like \"%" + phone + "\" ";
		}
		// 时间
		String startTimeStr = tableParams.getStartTimeStr();
		if(StringUtils.isBlank(startTimeStr)){
			throw new Exception("请选择开始时间");
		}
		condition += " and a.create_time >= \"" + startTimeStr + " 00\\:00\\:00\" ";
		String endTimeStr = tableParams.getEndTimeStr();
		if(StringUtils.isBlank(endTimeStr)){
			throw new Exception("请选择结束时间");
		}
		condition += " and a.create_time <= \"" + endTimeStr + " 23\\:59\\:59\" ";
		// 工号
		String workNumber = tableParams.getWorkNumber();
		if(StringUtils.isNotBlank(workNumber)){
			condition += " and a.work_number = \"" + workNumber + "\" ";
		}else{
			// 用户ids
			String userIds = tableParams.getUserIds();
			if(StringUtils.isNotBlank(userIds)){
				condition += " and a.user_id in (" + StringUtil.commaStrToSqlStr(userIds) + ") ";
			}
		}
		// userName操作人
		String reviewId = tableParams.getId();
		if(StringUtils.isNotBlank(reviewId)){
			condition += " and a.user_name = \"" + reviewId + "\" ";
		}
		// 审核状态
		String[] reviewStatusArr = tableParams.getStatusArr();
		if(reviewStatusArr != null && reviewStatusArr.length != 0){
			condition += " and b.review_status in (" + StringUtil.stringArrToSqlStr(reviewStatusArr) + ") ";
		}
		// 项目
		String paramsProjectId = tableParams.getProjectId();
		if(StringUtils.isNotBlank(paramsProjectId)){
			condition += " and a.project_id = \"" + paramsProjectId + "\" ";
		}
        // 是否转移
        String transYn = tableParams.getTransYn();
        if(transYn.equals("yes")){
            condition += " and b.group_type is not null ";
        }
        if(transYn.equals("no")){
            condition += " and b.group_type is null ";
        }

        Integer isAppeal = tableParams.getIsAppeal();
        if( isAppeal != null){
        	switch (isAppeal){
				case 0:
					condition += " and b.is_appeal is null ";
					break;
				case 1:
					condition += " and b.is_appeal = 1 ";
					break;
				case 2:
					condition += " and b.is_appeal = 0 ";
					break;
			}
		}

		baseSql += " where a.is_del = 0 and " + condition;
		baseSql += " group by a.client_id) y on y.clientId = a.client_id and y.createTime = a.create_time  ";

		Map<String, Object> result = new HashMap<>();
		// 获取总数
//		@SuppressWarnings("unchecked")
//		List<Object> countList = sessionFactory.getCurrentSession().createSQLQuery(countSql + baseSql).list();
//		Integer count = Integer.parseInt(countList.get(0).toString());
//		result.put("totalCount", count);

		// 获取数据
		baseSql += " ORDER BY a.create_time desc  ";;
		PaginationParams paginationParams = tableParams.getPaginationParams();
		Range range = paginationParams.getRange();
		if( ! isExport){
			baseSql += " limit " + range.getStart() + "," + range.getLimit();
		}
		Map<String, String> groupIdNameMap = new HashMap<>();
		@SuppressWarnings("unchecked")
		List<CallRecord> callRecordList = sessionFactory.getCurrentSession().createSQLQuery(listSql + baseSql).setResultTransformer(Transformers.aliasToBean(CallRecord.class)).list();

		Integer totalCount = callRecordList.size();
		if (callRecordList.size() == range.getLimit()) {
			totalCount = range.getStart() + range.getLimit() + 100;
		}
		result.put("totalCount", totalCount);
		Map<String, String> projectNameMap = new HashMap<>();
		List<Map<String, Object>> mapList = new ArrayList<>();
		for(CallRecord callRecord : callRecordList){
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(callRecord);
			map.put("createTimeStr", DateUtil.dateToString(callRecord.getCreateTime(), 8));
			String clientReviewStatusStr = callRecord.getClientReviewStatus();
			if(StringUtils.isNotBlank(clientReviewStatusStr)){
				map.put("clientReviewStatusStr", PublicEnum.ClientStatus.valueOf(clientReviewStatusStr).getName());
			}
			String clientConfirmStatus = callRecord.getClientConfirmStatus();
			if(StringUtils.isNotBlank(clientConfirmStatus)){
				map.put("clientConfirmStatusStr", PublicEnum.ClientStatus.valueOf(clientConfirmStatus).getName());
			}
			String clientStatus = callRecord.getClientStatus();
			if(StringUtils.isNotBlank(clientStatus)){
				map.put("clientStatusStr", PublicEnum.ClientStatus.valueOf(clientStatus).getName());
			}
			String projectId = callRecord.getProjectId();
			String projectName = "";
			if( ! projectNameMap.containsKey(projectId)){
				projectName = projectService.getName(projectId);
			}else{
				projectName = projectNameMap.get(projectId);
			}
			map.put("projectName", projectName);
			String groupId = callRecord.getGroupId();
			if(StringUtils.isNotBlank(groupId)){
			    String groupUserName = "";
			    if(groupIdNameMap.containsKey(groupId)){
			        groupUserName = groupIdNameMap.get(groupId);
                }else{
                    ClientGroup clientGroup = clientGroupService.get(groupId);
                    if(clientGroup != null){
                        User user = userService.getByWorkNumber(clientGroup.getClientGroupName());
                        if(user != null){
                            groupUserName = user.getName();
                        }
                    }
                    groupIdNameMap.put(groupId, groupUserName);
                }
                map.put("groupName", groupUserName);
            }
			mapList.add(map);
		}
		result.put("result", mapList);
		if(isExport){
			ExportDataUtil.exportData(mapList, "callRecord", "通话记录_" + DateUtil.dateToString(new Date(), 0), request, response);
		}
		return  result;
	}


	/**
	 * 话务统计
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> listStatistics(TableParams tableParams, Boolean isSystem) throws Exception{
		String startTime = tableParams.getStartTimeStr() ;
		String endTime = tableParams.getEndTimeStr();
		String condition =" cr.is_del=false ";
		if(StringUtils.isBlank(startTime)) {
			throw new Exception("请选择开始时间");
		}
		condition += " and cr.start_time >= str_to_date('" + startTime + " 00:00:00" + "', '%Y-%m-%d %T') " ;
		if(StringUtils.isBlank(endTime)) {
			throw new Exception("请选择结束时间");
		}
		condition += " and cr.start_time <= str_to_date('" + endTime +" 23:59:59" + "', '%Y-%m-%d %T') " ;
		if(StringUtils.isNotBlank(tableParams.getProjectId())){condition += " and cr.project_id = '" + tableParams.getProjectId() + "' ";}
		//else{
		// 角色用户ids
		String userIds = tableParams.getUserIds();
		if(StringUtils.isNotBlank(userIds)){
			condition += " and cr.user_id in (" + StringUtil.commaStrToSqlStr(userIds) + ") ";
		}
		//}
		String voiceType = tableParams.getGroupType();
		if(StringUtils.isNotBlank(voiceType)){
			condition += " and cr.voice_type = '" + voiceType  + "' ";
		}
		String roleType = tableParams.getRoleType();
		String workNumber = tableParams.getWorkNumber();
		if(StringUtils.isNotBlank(workNumber)) {
			condition += " and cr.work_number = '" + workNumber + "' ";
			UserCondition userCondition = new UserCondition();
			userCondition.setWorkNumber(workNumber);
			List<User> userList = userService.list(userCondition);
			if(userList.isEmpty()){
				throw new Exception("未找到该工号用户");
			}
			User user = userList.get(0);
			if(user.getRoleId().equals(QingpuConstants.Roles_Employee_id)){
				roleType = "cs";
			}else{
				roleType = "interview";
			}
		}
		String key = EncryptUtils.encryptByMD5(condition + roleType);
		Map<String, Object> callRecordList = CallPhoneListener._statisticsDataMap.get(key);
		if(callRecordList != null){
			System.out.println("从缓存获取话务统计");
			return callRecordList;
		}
		GroupCallCondition groupCallCondition = new GroupCallCondition();
		groupCallCondition.setEnable(true);
		List<GroupCall> groupCallList = groupCallService.list(groupCallCondition);
		if( ! groupCallList.isEmpty()){
			// 有正在群呼的任务，则限制开始时间
			Date startDate = DateUtil.strDateToDate(startTime, 0 );
			long sub = DateUtil.daysBetween(startDate, DateUtil.getDayStartTime());
			if(sub > 1 && ! isSystem){
				throw new Exception("群呼任务启动中，不能大批量统计");
			}
		}
        Boolean isMoreDay = false;
        if(DateUtil.daysBetween(DateUtil.strDateToDate(startTime, 0), DateUtil.strDateToDate(endTime, 0)) > 20 ){
            if(CallPhoneListener._isHaveMoreDayStatistics){
                throw new Exception("当前已有大批量统计，请等之前的统计完成");
            }
            isMoreDay = true;
            CallPhoneListener._isHaveMoreDayStatistics = true;
        }
		if(CallPhoneListener._onSelectStatistics.contains(key)){
			throw new Exception("正在统计请稍后");
		}else{
			CallPhoneListener._onSelectStatistics.add(key);
		}
		logger.info("查询话务统计：  " + condition);
		synchronized (key){
			String selectSql =
					" ( " +
							"SELECT cr.client_id client_id, cr.work_number work_number, cr.user_name user_name,cr.create_time from call_record cr " +
							"RIGHT JOIN " +
							"( SELECT max(cr.create_time) createTime,cr.id,cr.client_id,cr.user_name,cr.work_number from  call_record cr where "
							+ condition
							+  "group by cr.work_number,cr.user_name,cr.client_id ) " +
							"y on y.client_id = cr.client_id and y.createTime = cr.create_time " +
							") " +
							"crd on ct.id=crd.client_id WHERE ct.is_del=false  ";
			String strSql="";
			if("cs".equals(roleType)) {		//客服话务统计
				strSql = "select crd.work_number,crd.user_name,count(1) as sumTotal," +
						"                sum(case ct.review_status when 'A' then 1 when 'B' then 1 when 'C' then 1 when 'Waiting' then 1 else 0 end) as sumYx," +
						"                sum(case when ct.review_status in ('A','B','C','Waiting') then (case ct.once_status when true then 1 else 0 end) else 0 end) as sumSs," +
						"                sum(case ct.review_status when 'A' then 1 else 0 end) as sumA," +
						"                sum(case ct.review_status when 'B' then 1 else 0 end) as sumB," +
						"                sum(case ct.review_status when 'C' then 1 else 0 end) as sumC," +
						"                sum(case ct.review_status when 'Waiting' then 1 else 0 end) as sumWaiting," +
						"                sum(case ct.review_status when 'Blur' then 1 else 0 end) as sumBlur," +
						"                sum(case ct.review_status when 'Merchants' then 1 else 0 end) as sumMerchants," +
						"                sum(case ct.review_status when 'Referrals' then 1 else 0 end) as sumReferrals," +
						"                sum(case ct.review_status when 'Shop' then 1 else 0 end) as sumShop," +
						"                sum(case ct.review_status when 'House' then 1 else 0 end) as sumHouse," +
						"                sum(case ct.review_status when 'Office' then 1 else 0 end) as sumOffice," +
						"                sum(case ct.review_status when 'Flats' then 1 else 0 end) as sumFlats," +
						"                sum(case ct.review_status when 'No' then 1 when 'NoReject' then 1 else 0 end) as sumNo," +
						"                case sum(case ct.review_status when 'A' then 1 when 'B' then 1 when 'C' then 1 when 'Waiting' then 1 else 0 end) when 0 then '0' else round(count(1)/sum(case ct.review_status when 'A' then 1 when 'B' then 1 when 'C' then 1 when 'Waiting' then 1 else 0 end),0) end as efficiency," +
						"				 sum(case ct.review_status when 'A' then 1 else 0 end)*17 + sum(case ct.review_status when 'B' then 1 else 0 end)*14 + sum(case ct.review_status when 'C' then 1 else 0 end)*10 + sum(case when ct.review_status in('Blur','Merchants','Referrals','Shop','House','Office','Flats') then 1 else 0 end) as salary" +
						"                 from client ct right join " + selectSql + " group by crd.work_number,crd.user_name ";
			}
			else if("interview".equals(roleType)){	//约访话务统计
				strSql= "select crd.work_number,crd.user_name,count(1) as sumTotal," +
						"                sum(case ct.confirm_status when 'A' then 1 when 'B' then 1 when 'C' then 1 else 0 end) as sumYx," +
						"                sum(case ct.confirm_status when 'A' then 1 else 0 end) as sumA," +
						"                sum(case ct.confirm_status when 'B' then 1 else 0 end) as sumB," +
						"                sum(case ct.confirm_status when 'C' then 1 else 0 end) as sumC," +
						"                sum(case when ct.review_status in ('A','B','C','Waiting') then (case when ct.confirm_status in ('A','B','C') then 1 else 0 end) else 0 end) as Yx2Yx," +
						"				 sum(case when ct.review_status='Blur' then (case when ct.confirm_status in ('A','B','C') then 1 else 0 end) else 0 end) as Mh2Yx," +
						"				 sum(case when ct.review_status is null then (case when ct.confirm_status in ('A','B','C') then 1 else 0 end) else 0 end) as Mb2Yx," +
						"                sum(case ct.confirm_status when 'Blur' then 1 else 0 end) as sumBlur," +
						"                sum(case ct.confirm_status when 'Merchants' then 1 else 0 end) as sumMerchants," +
						"                sum(case ct.confirm_status when 'Referrals' then 1 else 0 end) as sumReferrals," +
						"                sum(case ct.confirm_status when 'Shop' then 1 else 0 end) as sumShop," +
						"                sum(case ct.confirm_status when 'House' then 1 else 0 end) as sumHouse," +
						"                sum(case ct.confirm_status when 'Office' then 1 else 0 end) as sumOffice," +
						"                sum(case ct.confirm_status when 'Flats' then 1 else 0 end) as sumFlats," +
						"                sum(case ct.confirm_status when 'No' then 1 when 'NoReject' then 1 else 0 end) as sumNo," +
						"                case sum(case ct.confirm_status when 'A' then 1 when 'B' then 1 when 'C' then 1 else 0 end) when 0 then '0' else round(count(1)/sum(case ct.confirm_status when 'A' then 1 when 'B' then 1 when 'C' then 1 when 'Waiting' then 1 else 0 end),0) end as efficiency," +
						"				 sum(case when ct.review_status in ('A','B','C','Waiting') then (case when ct.confirm_status in ('A','B','C') then 1 else 0 end) else 0 end)*3 + sum(case when ct.review_status = 'Blur' then (case when ct.confirm_status in ('A','B','C') then 1 else 0 end) else 0 end)*5 + sum(case when ct.review_status is null then (case when ct.confirm_status in ('A','B','C') then 1 else 0 end) else 0 end)*12 as salary" +
						"                 from client ct right join " + selectSql + " group by crd.work_number,crd.user_name ";
			}
			@SuppressWarnings("unchecked")
			//List<Object[]> list1 =sessionFactory.getCurrentSession().createSQLQuery(strSql).list(); //java entity申明类型与sql返回类型必须匹配才可用
			List<CallRecord> callRecordlist = sessionFactory.getCurrentSession().createSQLQuery(strSql).setResultTransformer(Transformers.aliasToBean(CallRecord.class)).list();

			List<Map<String, Object>> mapList = new ArrayList<>();
			Integer userCount = callRecordlist.size() - 1;
			Integer sumTotal = 0;
			Integer sumYx = 0;
			Integer sumSs = 0;
			Integer sumA = 0;
			Integer sumB = 0;
			Integer sumC = 0;
			Integer sumWaiting = 0;
			Integer sumBlur = 0;
			Integer sumMerchants = 0;
			Integer sumReferrals = 0;
			Integer sumShop = 0;
			Integer sumHouse = 0;
			Integer sumOffice = 0;
			Integer sumFlats = 0;
			Integer sumNo = 0;
			Integer efficiency = 0;
			Integer salary = 0;
			for(CallRecord callRecord : callRecordlist){
				sumTotal += callRecord.getSumTotal().intValue();
				sumYx += callRecord.getSumYx().intValue();
				BigDecimal sumSS = callRecord.getSumSs();
				if(sumSS == null){
					sumSS = new BigDecimal(0);
				}
				sumSs += sumSS.intValue();
				BigDecimal SumA = callRecord.getSumA();
				if(SumA == null){
					SumA = new BigDecimal(0);
				}
				sumA += SumA.intValue();
				BigDecimal SumB = callRecord.getSumB();
				if(SumB == null){
					SumB = new BigDecimal(0);
				}
				sumB += SumB.intValue();
				BigDecimal SumC = callRecord.getSumC();
				if(SumC == null){
					SumC = new BigDecimal(0);
				}
				sumC += SumC.intValue();
				BigDecimal SumWaiting = callRecord.getSumWaiting();
				if(SumWaiting == null){
					SumWaiting = new BigDecimal(0);
				}
				sumWaiting += SumWaiting.intValue();
				BigDecimal SumBlur = callRecord.getSumBlur();
				if(SumBlur == null){
					SumBlur = new BigDecimal(0);
				}
				sumBlur += SumBlur.intValue();
				BigDecimal SumMerchants = callRecord.getSumMerchants();
				if(SumMerchants == null){
					SumMerchants = new BigDecimal(0);
				}
				sumMerchants += SumMerchants.intValue();
				BigDecimal SumReferrals = callRecord.getSumReferrals();
				if(SumReferrals == null){
					SumReferrals = new BigDecimal(0);
				}
				sumReferrals += SumReferrals.intValue();
				BigDecimal SumShop = callRecord.getSumShop();
				if(SumShop == null){
					SumShop = new BigDecimal(0);
				}
				sumShop += SumShop.intValue();
				BigDecimal SumHouse = callRecord.getSumHouse();
				if(SumHouse == null){
					SumHouse = new BigDecimal(0);
				}
				sumHouse += SumHouse.intValue();
				BigDecimal SumOffice = callRecord.getSumOffice();
				if(SumOffice == null){
					SumOffice = new BigDecimal(0);
				}
				sumOffice += SumOffice.intValue();
				BigDecimal SumFlats = callRecord.getSumFlats();
				if(SumFlats == null){
					SumFlats = new BigDecimal(0);
				}
				sumFlats += SumFlats.intValue();
				BigDecimal SumNo = callRecord.getSumNo();
				if(SumNo == null){
					SumNo = new BigDecimal(0);
				}
				sumNo += SumNo.intValue();
				BigDecimal Salary = callRecord.getSalary();
				if(Salary == null){
					Salary = new BigDecimal(0);
				}
				salary += Salary.intValue();
			}
			Map<String, Object> evaMap = new HashMap<>();
			evaMap.put("workNumber", "总计");
			evaMap.put("sumTotal", sumTotal);
			evaMap.put("sumYx", sumYx);
			evaMap.put("sumNo", sumNo);
			evaMap.put("sumSs", sumSs);
			evaMap.put("sumA", sumA);
			evaMap.put("sumB", sumB);
			evaMap.put("sumC", sumC);
			evaMap.put("sumWaiting", sumWaiting);
			evaMap.put("sumBlur", sumBlur);
			evaMap.put("sumMerchants", sumMerchants);
			evaMap.put("sumReferrals", sumReferrals);
			evaMap.put("sumShop", sumShop);
			evaMap.put("sumHouse", sumHouse);
			evaMap.put("sumOffice", sumOffice);
			evaMap.put("sumFlats", sumFlats);
			if(sumYx != 0){
				evaMap.put("efficiency", ArithUtil.getRoundTwo(sumTotal / sumYx));
			}
			evaMap.put("salary", salary);
			mapList.add(evaMap);
			if(userCount > 0){
				evaMap = new HashMap<>();
				evaMap.put("workNumber", "平均");
				evaMap.put("sumTotal", ArithUtil.getRoundTwo(sumTotal / userCount));
				evaMap.put("sumYx", ArithUtil.round((double)sumYx / userCount, 1));
				evaMap.put("sumNo", ArithUtil.getRoundTwo(sumNo / userCount));
				evaMap.put("salary", ArithUtil.getRoundTwo(salary / userCount));
				mapList.add(evaMap);
			}
			for(CallRecord callRecord : callRecordlist){
				Map<String, Object> map = AutoEvaluationUtil.domainToMap(callRecord);
				mapList.add(map);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("result", mapList);
			CallPhoneListener._statisticsDataMap.put(key, result);
			CallPhoneListener._onSelectStatistics.remove(key);
			if(isMoreDay){
			    CallPhoneListener._isHaveMoreDayStatistics = false;
            }
			return result;
		}
	}
}
