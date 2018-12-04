package com.qingpu.phone.clientManager.service;

import com.qingpu.phone.clientManager.condition.ClientCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.service.BdfsBinaryProviderService;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.CallRecordCondition;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemManager.service.IntentionHintRewardRecordService;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.entity.UserInfo;
import com.qingpu.phone.user.service.JiangHuRenService;
import com.qingpu.phone.user.service.UserInfoService;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

@Service("clientService")
@Transactional
public class ClientService {
    private static Logger logger = Logger.getLogger(ClientService.class);

    @Resource
    private BaseDao baseDao;

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private CallRecordService callRecordService;

    @Resource
    private WebSocketService webSocketService;

    @Resource
    IntentionHintRewardRecordService intentionHintRewardRecordService;

    @Resource
    private BdfsBinaryProviderService bdfsBinaryProviderService;

    @Resource
    private UserService userService;

    @Resource
    JiangHuRenService jiangHuRenService;

    @Resource
    UserInfoService userInfoService;

    /**
     * 创建
     *
     * @param client
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Client create(Client client) throws Exception {
        if (client != null && StringUtils.isBlank(client.getId())) {
            client.setId(UUIDGenerator.getUUID());
        }
        CommonUtils.checkEntity(client);
        return (Client) baseDao.save(client);
    }


    /**
     * 更新
     *
     * @param client
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Client client) throws Exception {
        CommonUtils.checkEntity(client);
        baseDao.update(client);
    }


    /**
     * 删除
     *
     * @param client
     */
    public void delete(Client client) {
        client.setIsDel(true);
        client.setDelTime(new Date());
        baseDao.update(client);
    }

    /**
     * 删除
     * @param client
     */
    public void deleteReally(Client client) {
        baseDao.delete(client);
    }

    /**
     * 获取
     *
     * @param id
     * @return
     */
    public Client get(String id) {
        if (id == null || id.equals("")) {
            return null;
        }
        return (Client) baseDao.get(Client.class, id);
    }

    /**
     * 按条件查找
     *
     * @param clientCondition
     * @return
     */
    public List<Client> list(ClientCondition clientCondition) {
        return clientCondition.list(sessionFactory);
    }

    /**
     * 按条件排序
     *
     * @param clientCondition
     * @param sorter
     * @return
     */
    public List<Client> list(ClientCondition clientCondition, Sorter sorter) {
        return clientCondition.list(sessionFactory, sorter);
    }

    /**
     * 按条件分页查询
     *
     * @param clientCondition
     * @param range
     * @param sorter
     * @return
     */
    public PaginationSupport<Client> list(ClientCondition clientCondition, Range range, Sorter sorter) {
        PaginationSupport<Client> testTablePaginationSupport = (PaginationSupport<Client>) clientCondition.pageList(sessionFactory, sorter, range);
        return testTablePaginationSupport;
    }

    /**
     * 获取数量
     *
     * @param clientCondition
     * @return
     */
    public Long countByCondition(ClientCondition clientCondition) {
        return clientCondition.countByCondition(sessionFactory);
    }

    /**
     * 自定义复杂condition查询
     *
     * @param clientCondition
     * @return
     */
    public List<Client> listCustom(ClientCondition clientCondition) {
        return AutoEvaluationUtil.toClassList(clientCondition.list(sessionFactory));
    }

    /**
     * 获取相应数量
     *
     * @param clientCondition
     * @param num
     * @return
     */
    public List<Client> listCustom(ClientCondition clientCondition, int num) {
        return AutoEvaluationUtil.toClassList(clientCondition.list(sessionFactory, num));
    }

    public List<Client> listCustom(ClientCondition clientCondition, Sorter sorter, int num) {
        return AutoEvaluationUtil.toClassList(clientCondition.list(sessionFactory, sorter, num));
    }

    /**
     * 根据参数创建
     *
     * @param client
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createByParams(Client client) throws Exception {
        client = this.create(client);
        Map<String, Object> map = new HashMap<>();
        map.put("id", client.getId());
        return map;
    }

    /**
     * 根据参数更新
     *
     * @param paramsClient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByParams(Client paramsClient, Boolean calling, User user) throws Exception {
        Integer userId = user.getId();
        user = userService.get(userId);
        if (user == null) {
            throw new Exception("未找到用户");
        }
        String roleId = user.getRoleId();
        Client client = this.get(paramsClient.getId());
        if (client == null) {
            throw new Exception("未找到对象");
        }
        client.setName(paramsClient.getName());
        client.setSex(paramsClient.getSex());
        client.setYearTag(paramsClient.getYearTag());
        client.setArea(paramsClient.getArea());
        Boolean isClearMap = false;
        // 判断客服意向变动
        Boolean isShowIntentionHint = false;    // 显示词条
        PublicEnum.ClientStatus clientStatus = paramsClient.getStatus();
        String des = paramsClient.getDes();
        if(StringUtils.isBlank(des) && clientStatus != null){
            if(clientStatus == PublicEnum.ClientStatus.A || clientStatus == PublicEnum.ClientStatus.B || clientStatus == PublicEnum.ClientStatus.C ||
                    clientStatus == PublicEnum.ClientStatus.Blur || clientStatus == PublicEnum.ClientStatus.Merchants || clientStatus == PublicEnum.ClientStatus.Referrals ||
                    clientStatus == PublicEnum.ClientStatus.Shop || clientStatus == PublicEnum.ClientStatus.House || clientStatus == PublicEnum.ClientStatus.Office ||
                    clientStatus == PublicEnum.ClientStatus.Flats){
                throw new Exception("备注不能为空");
            }
        }
        PublicEnum.ClientStatus orStatus = client.getStatus();
        if (clientStatus != null && (clientStatus == PublicEnum.ClientStatus.A || clientStatus == PublicEnum.ClientStatus.B
                || clientStatus == PublicEnum.ClientStatus.C || clientStatus == PublicEnum.ClientStatus.Waiting)) {
            if (orStatus == null) {
                isClearMap = true;
                if (clientStatus != PublicEnum.ClientStatus.Waiting && QingpuConstants.Roles_Employee_id.equals(roleId)) {
                    isShowIntentionHint = true;
                }
            } else if (orStatus != clientStatus) {
                isClearMap = true;
            }
        }

        // 判断质检意向是否与客服意向相同
        PublicEnum.ClientStatus reviewStatus = paramsClient.getReviewStatus();
        if (clientStatus != null && reviewStatus != null && reviewStatus != clientStatus) {
            isClearMap = true;
        }
        if (isClearMap) {
            CallPhoneListener._statisticsDataMap.clear();
            CallPhoneListener._conversionRateDataMap.clear();
        }
        PublicEnum.ClientStatus orReviewStatus = client.getReviewStatus();
        client.setStatus(clientStatus);
        client.setReviewStatus(reviewStatus);
        client.setOnceStatus(paramsClient.getOnceStatus());
        client.setDes(des);
        client.setLastUpdateTime(new Date());
        String paramsProjectId = paramsClient.getProjectId();
        String projectId = client.getProjectId();
        if (StringUtils.isNotBlank(paramsProjectId) && !projectId.equals(paramsProjectId)) {
            client.setProjectId(paramsClient.getProjectId());
            CallRecordCondition callRecordCondition = new CallRecordCondition();
            callRecordCondition.setPhone(client.getPhone());
            callRecordCondition.setClientId(client.getId());
            List<CallRecord> callRecordList = callRecordService.list(callRecordCondition);
            for(CallRecord callRecord : callRecordList){
                callRecord.setProjectId(paramsProjectId);
                callRecordService.update(callRecord);
            }
        }
        PublicEnum.ClientStatus showHintStatus = clientStatus;
        PublicEnum.ClientStatus interview = paramsClient.getConfirmStatus();
        PublicEnum.ClientStatus orInterview = client.getConfirmStatus();
        if (orInterview == null && (interview == PublicEnum.ClientStatus.A || interview == PublicEnum.ClientStatus.B
                || interview == PublicEnum.ClientStatus.C) && QingpuConstants.Roles_Interview_id.equals(roleId) && (calling != null && calling)) {
            isShowIntentionHint = true;
            showHintStatus = interview;
        }
        if (isShowIntentionHint) {
            // 显示词条
            intentionHintRewardRecordService.createForIntentionType(user, showHintStatus);
        }
        PublicEnum.ClientStatus confirmStatus = paramsClient.getConfirmStatus();
        client.setConfirmStatus(confirmStatus);
        if( confirmStatus != null  ){
            // 设置第一次约访状态
            PublicEnum.ClientStatus firstConfirmStatus = client.getFirstConfirmStatus();
            Boolean isSetValue = false;
            if(firstConfirmStatus == null){
                isSetValue = true;
            }else{
                if(firstConfirmStatus != PublicEnum.ClientStatus.No && firstConfirmStatus != PublicEnum.ClientStatus.NoReject &&
                        firstConfirmStatus != PublicEnum.ClientStatus.Blur && firstConfirmStatus != PublicEnum.ClientStatus.C &&
                        firstConfirmStatus != PublicEnum.ClientStatus.B && firstConfirmStatus != PublicEnum.ClientStatus.A &&
                        firstConfirmStatus != PublicEnum.ClientStatus.Referrals && firstConfirmStatus != PublicEnum.ClientStatus.Merchants ){
                    isSetValue = true;
                }
            }
            if(isSetValue){
                client.setFirstConfirmStatus(confirmStatus);
                client.setInterviewId(userId);
            }
        }
        // 写入质检id
        switch (roleId) {
            case QingpuConstants.Roles_Employee_id:
            case QingpuConstants.Roles_Interview_id:
                if (calling != null && calling) {
                    if (CallPhoneListener._inCallingUser.contains(userId)) {
                        throw new Exception("请在通话结束后，再点击保存");
                    } else {
                        // 发送用户在线的指令给前端
                        webSocketService.setIsOnline(true, userId);
                        Session session = WebSocketService.onLineClient.get(userId + "");
                        webSocketService.sendOnline(true, session);
                        logger.info(user.getId() + " " + user.getExtNum() + " 工号设置空闲：  " + user.getWorkNumber());
                    }
                }
                break;
            case QingpuConstants.Roles_Quality_id:
                String callRecordId = paramsClient.getCallRecordId();
                Boolean isSetNewIntention = client.getIsSetNewIntention();
                if (StringUtils.isNotBlank(callRecordId)) {
                    CallRecord callRecord = callRecordService.get(callRecordId);
                    if (callRecord != null) {
                        String recordReviewId = callRecord.getReviewId();
                        long subDay = DateUtil.daysBetween(callRecord.getCreateTime(), DateUtil.getDayEndTime());
                        if (StringUtils.isBlank(recordReviewId)) {
                            // 今天的通话记录
                            if (subDay == 0) {
                                if(isSetNewIntention){
                                    this.checkReviewStatusChange(reviewStatus, callRecord, client, orReviewStatus, user, subDay );
                                }else{
                                    if (reviewStatus != null && (reviewStatus == PublicEnum.ClientStatus.A || reviewStatus == PublicEnum.ClientStatus.B
                                            || reviewStatus == PublicEnum.ClientStatus.C || reviewStatus == PublicEnum.ClientStatus.Waiting)) {
                                        // 产生新意向
                                        callRecord = this.createNewIntention(callRecord, client, subDay);
                                        logger.info(callRecordId + " 直接保存为意向： " + user.getName() + " " + callRecord.getUserName());
                                    }else{
                                        logger.info(callRecordId + " 质检第一次保存直接取消意向： " + user.getName() + " " + callRecord.getUserName());
                                    }
                                }
                            }else{
                                if(orReviewStatus == null){
                                    // 实际orReviewStatus不会为空
                                    if(reviewStatus != null){
                                        if(reviewStatus == PublicEnum.ClientStatus.A || reviewStatus == PublicEnum.ClientStatus.B
                                                || reviewStatus == PublicEnum.ClientStatus.C || reviewStatus == PublicEnum.ClientStatus.Waiting){
                                            // 产生新意向
                                            logger.info(callRecordId + " 质检一天以后第一次保存为意向： " + user.getName() + " " + callRecord.getUserName());
                                            callRecord = this.createNewIntention(callRecord, client, subDay);
                                        }else{
                                            logger.info(callRecordId + " 质检一天以后第一次保存直接取消意向： " + user.getName() + " " + callRecord.getUserName());
                                        }
                                    }
                                }else{
                                    this.oneDayLaterCheckReviewStatusChange(reviewStatus, callRecord, client, orReviewStatus, user, subDay);
                                }
                            }
                            callRecord.setReviewWorkNumber(user.getWorkNumber());
                            callRecord.setReviewId(userId + "");
                            callRecord.setReviewName(user.getName());
                            callRecordService.update(callRecord);
                        }else{
                            if ( subDay == 0) {
                                if(isSetNewIntention){
                                    this.checkReviewStatusChange(reviewStatus, callRecord, client, orReviewStatus, user, subDay );
                                }else{
                                    if( (orReviewStatus != PublicEnum.ClientStatus.A && orReviewStatus != PublicEnum.ClientStatus.B
                                            && orReviewStatus != PublicEnum.ClientStatus.C && orReviewStatus != PublicEnum.ClientStatus.Waiting)
                                            && (reviewStatus == PublicEnum.ClientStatus.A || reviewStatus == PublicEnum.ClientStatus.B
                                            || reviewStatus == PublicEnum.ClientStatus.C || reviewStatus == PublicEnum.ClientStatus.Waiting) ){
                                        logger.info(callRecordId + " 质检取消意向后，再次回退到意向： " + user.getName() + " " + callRecord.getUserName());
                                        callRecord = this.createNewIntention(callRecord, client, subDay);
                                    }
                                }
                            }else{
                                this.oneDayLaterCheckReviewStatusChange(reviewStatus, callRecord, client, orReviewStatus, user, subDay);
                            }
                            callRecordService.update(callRecord);
                        }
                    }
                }
                break;
        }
        this.update(client);
    }

    /**
     * 产生新的意向
     * @param callRecord
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    protected CallRecord createNewIntention(CallRecord callRecord, Client client, long subDay) throws Exception{
        User kefu = this.getUserByCallRecordUserId(callRecord);
        if (kefu != null) {
            PublicEnum.ClientStatus reviewStatus = client.getReviewStatus();
            UserInfo userInfo = userInfoService.getByName(kefu.getName());
            if(userInfo != null){
                if(subDay == 0 && ! kefu.getIsNoName()){
                    Integer dayIntentionCount = userInfo.getDayIntentionCount();
                    dayIntentionCount++;
                    int count0 = QingpuConstants._luckDrawIntentionCountArr[0];
                    int count1 = QingpuConstants._luckDrawIntentionCountArr[1];
                    int count2 = QingpuConstants._luckDrawIntentionCountArr[2];
                    if (dayIntentionCount == count0 || dayIntentionCount == count1 || dayIntentionCount == count2) {
                        String nowDayFinishDraw = userInfo.getDayFinishDraw();
                        List<String> drawList = StringUtil.splitByComma(nowDayFinishDraw);
                        Boolean addDrawCount = false;
                        // 设置是否已到过5，8,11次意向
                        Integer integer = 1;
                        if (dayIntentionCount == count0) {
                            integer = Integer.parseInt(drawList.get(0));
                            drawList.set(0, "1");
                        } else if (dayIntentionCount == count1) {
                            integer = Integer.parseInt(drawList.get(1));
                            drawList.set(1, "1");
                        } else {
                            integer = Integer.parseInt(drawList.get(2));
                            drawList.set(2, "1");
                        }
                        if (integer == 0) {
                            addDrawCount = true;
                        }
                        if (addDrawCount) {
                            // 没有达到则增加抽奖次数
                            userInfo.setDayDrawCount(userInfo.getDayDrawCount() + 1);
                            webSocketService.showDraw(kefu.getId());
                        }
                        userInfo.setDayFinishDraw(drawList.get(0) + "," + drawList.get(1) + "," + drawList.get(2));
                    }
                    userInfo.setDayIntentionCount(dayIntentionCount);
                    userInfoService.update(userInfo);
                }
                jiangHuRenService.addCSIntention(userInfo, reviewStatus, true, null, subDay);
            }
            client.setIsSetNewIntention(true);
            client.setIntentionStatus(reviewStatus);
        }
        return callRecord;
    }


    /**
     * 一天后质检保存检测变化
     * @param reviewStatus
     * @param callRecord
     * @param client
     * @param orReviewStatus
     * @param user
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    protected void oneDayLaterCheckReviewStatusChange(PublicEnum.ClientStatus reviewStatus, CallRecord callRecord, Client client, PublicEnum.ClientStatus orReviewStatus, User user,
                                                      long subDay)
            throws Exception{
        String callRecordId = callRecord.getId();
        Boolean isSetNewIntention = client.getIsSetNewIntention();
        if( (orReviewStatus != PublicEnum.ClientStatus.A && orReviewStatus != PublicEnum.ClientStatus.B
                && orReviewStatus != PublicEnum.ClientStatus.C && orReviewStatus != PublicEnum.ClientStatus.Waiting)
                && (reviewStatus == PublicEnum.ClientStatus.A || reviewStatus == PublicEnum.ClientStatus.B
                || reviewStatus == PublicEnum.ClientStatus.C || reviewStatus == PublicEnum.ClientStatus.Waiting) ){
            logger.info(callRecordId + " 质检一天以后回退到意向： " + user.getName() + " " + callRecord.getUserName());
            if( ! isSetNewIntention){
                // 产生新意向
                callRecord = this.createNewIntention(callRecord, client, subDay);
            }
        }else{
            if(isSetNewIntention){
                this.checkReviewStatusChange(reviewStatus, callRecord, client, orReviewStatus, user, subDay);
            }
        }
    }


    /**
     * 根据通话记录的Id查找客服
     * @param callRecord
     * @return
     */
    private User getUserByCallRecordUserId(CallRecord callRecord){
        User kefu = null;
        String kefuUserId = callRecord.getUserId();
        if (StringUtils.isNotBlank(kefuUserId)) {
            kefu = userService.get(Integer.parseInt(kefuUserId));
            if (kefu != null) {
                String userName = kefu.getName();
                String callRecordUserName = callRecord.getUserName();
                if (!userName.equals(callRecord.getUserName())) {
                    logger.info("通话记录客服名字不相同： " + callRecordUserName + "-" + userName);
                    UserCondition userCondition = new UserCondition();
                    userCondition.setName(callRecordUserName);
                    userCondition.setRoleId(QingpuConstants.Roles_Employee_id);
                    List<User> userList = userService.list(userCondition);
                    if (!userList.isEmpty()) {
                        kefu = userList.get(0);
                    }else{
                        kefu = new User();
                        kefu.setIsNoName(true);
                        kefu.setName(callRecord.getUserName());
                    }
                }
            }
        }
        return kefu;
    }

    /**
     * 检测意向的变更
     * @param reviewStatus
     * @param callRecord
     * @param client
     * @param orReviewStatus
     * @param user
     * @param subDay
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    protected void checkReviewStatusChange(PublicEnum.ClientStatus reviewStatus, CallRecord callRecord, Client client, PublicEnum.ClientStatus orReviewStatus, User user,
                                         long subDay) throws Exception{
        String callRecordId = callRecord.getId();
        String hint = "";
        if( subDay > 0){
            hint = "一天以后";
        }
        if (reviewStatus != null ) {
            User kefu = this.getUserByCallRecordUserId(callRecord);
            if(kefu != null){
                if(reviewStatus != PublicEnum.ClientStatus.A && reviewStatus != PublicEnum.ClientStatus.B
                        && reviewStatus != PublicEnum.ClientStatus.C && reviewStatus != PublicEnum.ClientStatus.Waiting){
                    // 如果设置了为意向，则回退意向数量
                    if(subDay == 0 &&  ! kefu.getIsNoName()){
                        UserInfo userInfo = userInfoService.getByName(kefu.getName());
                        if(userInfo != null){
                            userInfo.setDayIntentionCount(userInfo.getDayIntentionCount() - 1);
                            userInfoService.update(userInfo);
                        }
                    }
                    client.setIsSetNewIntention(false);
                    callRecordService.update(callRecord);
                    jiangHuRenService.addCSIntention(kefu, orReviewStatus, false, null, subDay);
                    logger.info(callRecordId + " 质检" + hint + "撤销意向： " + user.getName() + " " + callRecord.getUserName());
                }else{
                    PublicEnum.ClientStatus intentionStatus = client.getIntentionStatus();
                    if(intentionStatus != null && reviewStatus != intentionStatus){
                        // 质检意向降级
                        client.setIntentionStatus(reviewStatus);
                        jiangHuRenService.addCSIntention(kefu, reviewStatus, false, intentionStatus, subDay);
                        logger.info(callRecordId + " 质检" + hint + "更换意向： " + user.getName()  + " " + callRecord.getUserName() + " " + intentionStatus.getName() + "到" + reviewStatus.getName());
                    }
                }
            }
        }
    }

    /**
     * 转移客户
     *
     * @param paramsClient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void transClient(Client paramsClient) throws Exception {
        Client client = this.get(paramsClient.getId());
        if (client == null) {
            throw new Exception("未找到对象");
        }
        String groupId = paramsClient.getGroupId();
        if (StringUtils.isNotBlank(groupId)) {
            client.setGroupId(groupId);
        }
        PublicEnum.ClientGroupType clientGroupType = paramsClient.getGroupType();
        if (clientGroupType != null) {
            client.setGroupType(clientGroupType);
        }
        client.setUpdateTime(new Date());
        this.update(client);
    }


    /**
     * 取消转移
     * @param paramsClient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelTransClient(Client paramsClient) throws Exception {
        Client client = this.get(paramsClient.getId());
        if (client == null) {
            throw new Exception("未找到对象");
        }
        client.setGroupId(null);
        client.setGroupType(null);
        client.setUpdateTime(new Date());
        this.update(client);
    }

    /**
     * 批量转移
     *
     * @param paramsClient
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void transAllClient(Client paramsClient) throws Exception {
        String ids = paramsClient.getCallRecordId();
        if (StringUtils.isBlank(ids)) {
            throw new Exception("请选择需要转移的通话记录");
        }
        List<String> idList = StringUtil.splitByComma(ids);
        if (idList.isEmpty()) {
            throw new Exception("请选择需要转移的通话记录");
        }
        String groupId = paramsClient.getGroupId();
        if (StringUtils.isBlank(groupId)) {
            throw new Exception("请选择转移的工号");
        }
        PublicEnum.ClientGroupType clientGroupType = paramsClient.getGroupType();
        if (clientGroupType == null) {
            throw new Exception("请选择转移的类型");
        }
        for (String id : idList) {
            Client client = this.get(id);
            if (client == null) {
                continue;
            }
            client.setGroupId(groupId);
            client.setGroupType(clientGroupType);
            client.setUpdateTime(new Date());
            this.update(client);
        }
    }

    /**
     * 根据id删除
     *
     * @param id
     * @param request
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void delById(String id, HttpServletRequest request) throws Exception {
        Client client = this.get(id);
        if (client == null) {
            throw new Exception("删除失败：未找到数据");
        }
        User user = (User) request.getSession().getAttribute("_user");
        if (user != null) {
            client.setOperatorId(user.getId());
        }
        this.delete(client);
    }


    /**
     * 导入客户资源
     *
     * @param
     * @param
     * @param request
     * @throws Exception
     */
    public void saveExportProjectClient(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("开始上传：" + new Date());
        User currentUser = (User) request.getSession().getAttribute("_user");
        String projectId = request.getParameter("id");
        String importTag = request.getParameter("importTag");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("fileXls");
        if (!multipartFile.isEmpty()) {
            //读取文件内容
            CommonsMultipartFile cFile = (CommonsMultipartFile) multipartFile;
            DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
            InputStream inputStream = fileItem.getInputStream();
            Workbook wbs = WorkbookFactory.create(inputStream);
            // 获取每一个工作薄
            for (int numSheet = 0; numSheet < wbs.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = wbs.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                System.out.println("开始读取：" + new Date());
                // 获取当前工作薄的每一行，从第2行开始
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        String createSql = "INSERT INTO `client` ( ";
                        String valueSql = " VALUES ( ";
                        createSql += " `id`,  `create_time`, `import_tag`, `is_del`, `is_distribute`, `phone`, `project_id`, `update_time` ";
                        String nowStr = DateUtil.dateToString(new Date(), 2);
                        DataFormatter formatter = new DataFormatter();
                        String phone = formatter.formatCellValue(hssfRow.getCell(0));
                        valueSql += " '" + UUIDGenerator.getUUID() + "' , '" + nowStr + "' , '" + importTag + "' , '0', '0', '" + phone + "' , '" + projectId + "' , '"
                                + nowStr + "' ";

                        Cell nameHss = hssfRow.getCell(1);            //姓名
                        Cell desHss = hssfRow.getCell(2);                //备注

                        String selectSql = "select name,sex,des,year_tag,area from client where phone = " + phone + " order by create_time desc limit 1";
                        @SuppressWarnings("unchecked")
                        List<Object> clientObject = sessionFactory.getCurrentSession().createSQLQuery(selectSql).list();
                        if (!clientObject.isEmpty()) {
                            Object[] obj = (Object[]) clientObject.get(0);
                            Object obj0 = obj[0];
                            if (obj0 != null) {
                                createSql += " , `name` ";
                                valueSql += " ,'" + obj0.toString() + "' ";
                            }
                            Object obj1 = obj[1];
                            if (obj1 != null) {
                                createSql += " , `sex` ";
                                valueSql += " ,'" + obj1.toString() + "' ";
                            }
                            Object obj2 = obj[2];
                            if (obj2 != null) {
                                createSql += " , `des` ";
                                valueSql += " ,'" + obj2.toString() + "' ";
                            }
                            Object obj3 = obj[3];
                            if (obj3 != null) {
                                //当此客户已经标记老人或学生时，不再导入
                                if (obj3.toString().equals(PublicEnum.YearTag.Old.getType()) || obj3.toString().equals(PublicEnum.YearTag.Stu.getType())) {
                                    continue;
                                }
                                createSql += " , `year_tag` ";
                                valueSql += " ,'" + obj3.toString() + "' ";
                            }
                            Object obj4 = obj[4];
                            if (obj4 != null) {
                                createSql += " , `area` ";
                                valueSql += " ,'" + obj4.toString() + "' ";
                            }
                        }
                        createSql += " )";
                        valueSql += " )";

//						Client  client=new Client();
//						client.setProjectId(projectId);							//项目id
//						client.setImportTag(Enum.valueOf(PublicEnum.ImportTag.class,importTag));		//客户类别
//						client.setPhone(phone);									//号码
//						Client clientTemp=getByPhoneTop1(phone);				//获取之前的客户资料，并设置新导入的客户
//						if(clientTemp != null){
//							client.setName(clientTemp.getName());				//姓名，备注，性别，年龄段，区域
//							client.setDes(clientTemp.getDes());
//							client.setSex(clientTemp.getSex());
//							client.setYearTag(clientTemp.getYearTag());
//							client.setArea(clientTemp.getArea());
//						}
                        SQLQuery query1 = sessionFactory.getCurrentSession().createSQLQuery(createSql + valueSql);
                        query1.addEntity(Client.class);
                        query1.executeUpdate();
                    }
                }
                System.out.println("导入结束：" + new Date());
            }
            JsonRetInfo.returnSuccess(response, "保存成功");
        }
    }

    /**
     * 导入客户资源
     *
     * @param
     * @param
     * @param request
     * @throws Exception
     */
    public void saveExportProjectClient2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("开始上传：" + new Date());
        String projectId = request.getParameter("id");
        String importTag = request.getParameter("importTag");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("fileXls");
        if (!multipartFile.isEmpty()) {
            //读取文件内容
            CommonsMultipartFile cFile = (CommonsMultipartFile) multipartFile;
            DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
            InputStream inputStream = fileItem.getInputStream();
            Workbook wbs = WorkbookFactory.create(inputStream);
            // 获取每一个工作薄
            for (int numSheet = 0; numSheet < wbs.getNumberOfSheets(); numSheet++) {
                Sheet sheet = wbs.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }
                Integer count = sheet.getLastRowNum();
                if (count > 0) {
                    System.out.println("开始读取：" + new Date());
                    String filepath = QingpuConstants.FILE_UPLOAD_PATH_IMG + "/" + DateUtil.getFolderDate() + "_" + System.currentTimeMillis() + ".sql";
                    File file = new File(filepath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    Properties myProperty = new Properties();
                    String jdbcPath = StringUtil.getBashPath() + "WEB-INF" + File.separator + "classes" + File.separator + "config" + File.separator + "jdbc.properties";
                    myProperty.load(new FileInputStream(new File(jdbcPath)));
                    String driverName = myProperty.getProperty("driver");
                    String connUrl = "jdbc:mysql://" + myProperty.getProperty("mysqlbase.host") + ":" + myProperty.getProperty("mysqlbase.port") + "/"
                            + myProperty.getProperty("mysqlbase.sid") + "?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&useSSL=false&user=" +
                            myProperty.getProperty("mysqlbase.user") + "&password=QPjituan@123456";
                    Class.forName(driverName).newInstance();
                    Connection conn = DriverManager.getConnection(connUrl);

                    // 获取当前工作薄的每一行，从第2行开始
                    FileOutputStream writerStream = new FileOutputStream(filepath);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF8"));
                    for (int rowNum = 1; rowNum <= count; rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row != null) {
                            String nowStr = DateUtil.dateToString(new Date(), 2);
                            DataFormatter formatter = new DataFormatter();
                            String phone = formatter.formatCellValue(row.getCell(0));
                            String name = formatter.formatCellValue(row.getCell(1));
                            String des = formatter.formatCellValue(row.getCell(2));
                            String inputStr = UUIDGenerator.getUUID() + "##&&\\N##&&\\N##&&" + nowStr + "##&&\\N##&&" + des + "##&&\\N##&&\\N##&&"
                                    + importTag + "##&&0##&&0##&&" + name + "##&&\\N##&&\\N##&&" + phone + "##&&"
                                    + projectId + "##&&\\N##&&\\N##&&\\N##&&" + nowStr + "##&&\\N##&&" + nowStr + "##&&0##&&\\N##&&\\N##&&\\N##&&\\N##&&\\N&&&###";
                            writer.write(inputStr);
                        }
                    }
                    writer.flush();
                    writer.close();
                    System.out.println("创建文件结束：" + new Date());

                    PreparedStatement statement = conn.prepareStatement(
                            "LOAD DATA INFILE '" + filepath + "' IGNORE into table client  fields terminated by '##&&' lines terminated by '&&&###'");
                    statement.execute("SET NAMES UTF8");
                    statement.execute();
                    statement.close();
                    conn.close();
                    System.out.println("导入结束：" + new Date());
                }
            }
            JsonRetInfo.returnSuccess(response, "保存成功");
        }
    }

    /**
     * 获取:根据号码获取最新一条客户信息，用户导入数据时提取客户信息
     *
     * @param phone
     * @return
     */
    public Client getByPhoneTop1(String phone) {
        ClientCondition clientCondition = new ClientCondition();
        clientCondition.setPhone(phone);
        Client client = new Client();
        List<Client> list = this.listCustom(clientCondition, new Sorter().desc("create_time"), 1);
        if (list.size() > 0) {
            client = list.get(0);
            return client;
        } else {
            return null;
        }
    }

    /**
     * 客服意向转化率
     * @param tableParams
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getConversionRate(TableParams tableParams) throws Exception{
        String startTime = tableParams.getStartTimeStr() ;
        String endTime = tableParams.getEndTimeStr();
        String condition =" ";
        if(StringUtils.isBlank(startTime)) {
            throw new Exception("请选择开始时间");
        }
        condition += " create_time >= \"" + startTime + " 00\\:00\\:00\" " ;
        if(StringUtils.isBlank(endTime)) {
            throw new Exception("请选择结束时间");
        }
        condition += " and create_time <= \"" + endTime +" 23\\:59\\:59\" " ;
        String workNumber = tableParams.getWorkNumber();
        if(StringUtils.isNotBlank(workNumber)) {
            UserCondition userCondition = new UserCondition();
            userCondition.setWorkNumber(workNumber);
            List<User> userList = userService.list(userCondition);
            if( userList.isEmpty()){
                throw new Exception("未找到该工号客服");
            }
            condition += " and user_name = '" + userList.get(0).getName() + "' ";
        }else{
            // 角色用户ids
            String userIds = tableParams.getUserIds();
            if(StringUtils.isNotBlank(userIds)){
                condition += " and user_id in (" + StringUtil.commaStrToSqlStr(userIds) + ") ";
            }
        }
        String key = EncryptUtils.encryptByMD5(condition);
        Map<String, Object> callRecordList = CallPhoneListener._conversionRateDataMap.get(key);
        if(callRecordList != null){
            System.out.println("从缓存获取客服意向转化率");
            return callRecordList;
        }
        if(CallPhoneListener._onSelectStatistics.contains(key)){
            throw new Exception("正在统计请稍后");
        }else{
            CallPhoneListener._onSelectStatistics.add(key);
        }
        logger.info("客服意向转化率：  " + condition);
        String selectSql = "SELECT sum( CASE temp3.review_status WHEN 'A' THEN 1 WHEN 'B' THEN 1 WHEN 'C' THEN 1  WHEN 'Waiting' THEN 1 ELSE 0 END ) AS sumReviewStatus, sum( CASE temp3. STATUS WHEN 'A' THEN 1 WHEN 'B' THEN 1 WHEN 'C' THEN 1  WHEN 'Waiting' THEN 1 ELSE 0 END ) AS sumStatus, user_name " +
                "FROM ( SELECT * FROM ( SELECT c.`status`, c.review_status, t2.user_name FROM client c RIGHT JOIN ( SELECT user_name, client_id FROM call_record WHERE "
                + condition + "  GROUP BY client_id ) t2 ON c.id = t2.client_id AND ( c.`status` = 'A' OR c.`status` = 'C' OR c.`status` = 'B' OR c.`status` = 'Waiting' ) ) aa WHERE aa.`status` IS NOT NULL ) temp3 GROUP BY user_name; ";
        @SuppressWarnings("unchecked")
        List<Object[]> objList =sessionFactory.getCurrentSession().createSQLQuery(selectSql).list();

        List<Map<String, Object>> mapList = new ArrayList<>();
        Integer totalReviewStatus = 0;
        Integer totalStatus = 0;
        for(Object[] objects : objList){
            BigDecimal sumReviewStatus = (BigDecimal) objects[0];
            totalReviewStatus += sumReviewStatus.intValue();
            BigDecimal sumStatus = (BigDecimal) objects[1];
            totalStatus += sumStatus.intValue();
            String name = (String)objects[2];
            Double rate = ArithUtil.round((sumReviewStatus.doubleValue()/sumStatus.intValue())*100, 2);
            Map<String, Object> evaMap = new HashMap<>();
            evaMap.put("rateDouble", rate);
            evaMap.put("rate", rate  + "%");
            evaMap.put("rateNum", rate);
            evaMap.put("name", name);
            evaMap.put("reviewCount", sumReviewStatus.intValue());
            evaMap.put("count", sumStatus.intValue());
            mapList.add(evaMap);
        }
        Collections.sort(mapList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = (Double)o1.get("rateDouble") ;
                Double name2 = (Double)o2.get("rateDouble") ;
                return name2.compareTo(name1);
            }
        });

        Map<String, Object> evaMap = new HashMap<>();
        Double rate = 0.0;
        if(totalStatus != 0){
            rate = ArithUtil.round((totalReviewStatus.doubleValue()/totalStatus)*100, 2);
        }
        evaMap.put("rate",rate + "%" );
        evaMap.put("name", "总计");
        evaMap.put("reviewCount", totalReviewStatus);
        evaMap.put("count", totalStatus);
        mapList.add(0, evaMap);
        Map<String, Object> result = new HashMap<>();
        result.put("result", mapList);
        CallPhoneListener._conversionRateDataMap.put(key, result);
        CallPhoneListener._onSelectStatistics.remove(key);
        return result;
    }
}
