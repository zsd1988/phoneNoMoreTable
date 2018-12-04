package com.qingpu.phone.user.service;

import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.func.webSocket.WebSocketData;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemSetting.service.ParameterService;
import com.qingpu.phone.user.condition.*;
import com.qingpu.phone.user.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

@Service("jiangHuRenService")
@Transactional
public class JiangHuRenService {
    private static Logger logger = Logger.getLogger(JiangHuRenService.class);

    public static Map<String, Object> statisticsMap = new HashMap<>();

    @Resource
    private BaseDao baseDao;

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    UserGroupService userGroupService;

    @Resource
    ParameterService parameterService;

    @Resource
    JiangHuPaiService jiangHuPaiService;

    @Resource
    WebSocketService webSocketService;

    @Resource
    UserService userService;

    @Resource
    CallRecordService callRecordService;

    @Resource
    JiangHuRankService jiangHuRankService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    ClientService clientService;

    /**
     * 创建
     *
     * @param jiangHuRen
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JiangHuRen create(JiangHuRen jiangHuRen) throws Exception {
        if (jiangHuRen != null && StringUtils.isBlank(jiangHuRen.getId())) {
            jiangHuRen.setId(UUIDGenerator.getUUID());
        }
        CommonUtils.checkEntity(jiangHuRen);
        return (JiangHuRen) baseDao.save(jiangHuRen);
    }


    /**
     * 更新
     *
     * @param jiangHuRen
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(JiangHuRen jiangHuRen) throws Exception {
        CommonUtils.checkEntity(jiangHuRen);
        baseDao.update(jiangHuRen);
    }


    /**
     * 删除
     *
     * @param jiangHuRen
     */
    public void delete(JiangHuRen jiangHuRen) {
        jiangHuRen.setIsDel(true);
        jiangHuRen.setDelTime(new Date());
        baseDao.update(jiangHuRen);
    }

    /**
     * 获取
     *
     * @param id
     * @return
     */
    public JiangHuRen get(String id) {
        if (id == null || id.equals("")) {
            return null;
        }
        return (JiangHuRen) baseDao.get(JiangHuRen.class, id);
    }


    /**
     * 按条件查找
     *
     * @param jiangHuRenCondition
     * @return
     */
    public List<JiangHuRen> list(JiangHuRenCondition jiangHuRenCondition) {
        return jiangHuRenCondition.list(sessionFactory);
    }

    /**
     * 按条件排序
     *
     * @param jiangHuRenCondition
     * @param sorter
     * @return
     */
    public List<JiangHuRen> list(JiangHuRenCondition jiangHuRenCondition, Sorter sorter) {
        return jiangHuRenCondition.list(sessionFactory, sorter);
    }

    /**
     * 按条件分页查询
     *
     * @param jiangHuRenCondition
     * @param range
     * @param sorter
     * @return
     */
    public PaginationSupport<JiangHuRen> list(JiangHuRenCondition jiangHuRenCondition, Range range, Sorter sorter) {
        PaginationSupport<JiangHuRen> testTablePaginationSupport = (PaginationSupport<JiangHuRen>) jiangHuRenCondition.pageList(sessionFactory, sorter, range);
        return testTablePaginationSupport;
    }

    /**
     * 获取数量
     *
     * @param jiangHuRenCondition
     * @return
     */
    public Long countByCondition(JiangHuRenCondition jiangHuRenCondition) {
        return jiangHuRenCondition.countByCondition(sessionFactory);
    }

    /**
     * 自定义复杂condition查询
     *
     * @param jiangHuRenCondition
     * @return
     */
    public List<Object[]> listCustom(JiangHuRenCondition jiangHuRenCondition) {
        return jiangHuRenCondition.list(sessionFactory);
    }

    /**
     * 获取相应数量
     *
     * @param jiangHuRenCondition
     * @param num
     * @return
     */
    public List<JiangHuRen> listCustom(JiangHuRenCondition jiangHuRenCondition, int num) {
        return AutoEvaluationUtil.toClassList(jiangHuRenCondition.list(sessionFactory, num));
    }

    /**
     * 按照排序获取相应数量
     *
     * @param jiangHuRenCondition
     * @param sorter
     * @param num
     * @return
     */
    public List<JiangHuRen> listCustom(JiangHuRenCondition jiangHuRenCondition, Sorter sorter, int num) {
        return AutoEvaluationUtil.toClassList(jiangHuRenCondition.list(sessionFactory, sorter, num));
    }

    /**
     * 根据参数创建
     *
     * @param jiangHuRen
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createByParams(JiangHuRen jiangHuRen) throws Exception {
        jiangHuRen = this.create(jiangHuRen);
        Map<String, Object> map = new HashMap<>();
        map.put("id", jiangHuRen.getId());
        return map;
    }

    /**
     * 根据参数更新
     *
     * @param paramsJiangHuRen
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByParams(JiangHuRen paramsJiangHuRen) throws Exception {
        JiangHuRen jiangHuRen = this.get(paramsJiangHuRen.getId());
        if (jiangHuRen == null) {
            throw new Exception("未找到对象");
        }
        this.update(jiangHuRen);
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
        JiangHuRen jiangHuRen = this.get(id);
        if (jiangHuRen == null) {
            throw new Exception("删除失败：未找到数据");
        }
        User user = (User) request.getSession().getAttribute("_user");
        if (user != null) {
            jiangHuRen.setOperatorId(user.getId());
        }
        this.delete(jiangHuRen);
    }


    public JiangHuRen getByNameAndRoleType(String name, PublicEnum.RoleType roleType) {
        JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setUserName(name);
        jiangHuRenCondition.setRoleType(roleType);
        List<JiangHuRen> jiangHuRenList = this.list(jiangHuRenCondition);
        if (jiangHuRenList.isEmpty()) {
            return null;
        }
        return jiangHuRenList.get(0);
    }

    /**
     * 根据名字和用户id获取
     *
     * @param name
     * @param userId 去掉id
     * @return
     */
    public JiangHuRen getByNameAndUserId(String name, Integer userId) {
        JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setUserName(name);
        jiangHuRenCondition.setUserId(userId);
        List<JiangHuRen> jiangHuRenList = this.list(jiangHuRenCondition);
        if (jiangHuRenList.isEmpty()) {
            return null;
        }
        return jiangHuRenList.get(0);
    }


    /**
     * 客服意向变动
     *
     * @param user
     * @param clientStatus
     * @param isAdd
     * @throws Exception
     */
    public synchronized void addCSIntention(User user, PublicEnum.ClientStatus clientStatus, Boolean isAdd, PublicEnum.ClientStatus beforeClientStatus, long subDay) {
        try {
            UserInfo userInfo = userInfoService.getByName(user.getName());
            if (userInfo != null) {
                this.addCSIntention(userInfo, clientStatus, isAdd, beforeClientStatus, subDay);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 客服意向变动
     *
     * @param userInfo
     * @param clientStatus
     * @param isAdd
     * @throws Exception
     */
    public synchronized void addCSIntention(UserInfo userInfo, PublicEnum.ClientStatus clientStatus, Boolean isAdd, PublicEnum.ClientStatus beforeClientStatus, long subDay) {
        try {
            String userName = userInfo.getName();
            JiangHuRen jiangHuRen = this.getByNameAndRoleType(userName, PublicEnum.RoleType.CS);
            if (jiangHuRen != null) {
                int index = 0;
                Date now = new Date();
                switch (clientStatus) {
                    case A:
                        index = 2;
                        break;
                    case B:
                        index = 1;
                        break;
                    default:
                        index = 0;
                        break;
                }
                int gold = QingpuConstants._csGold[index];
                int intention = 1;
                if (!isAdd) {
                    if (beforeClientStatus != null) {
                        switch (beforeClientStatus) {
                            case A:
                                index = 2;
                                break;
                            case B:
                                index = 1;
                                break;
                            default:
                                index = 0;
                                break;
                        }
                        int beforeGold = QingpuConstants._csGold[index];
                        gold = beforeGold - gold;
                        intention = 0;
                    } else {
                        intention = -1;
                    }
                    gold = -gold;
                }
                Date createDay = DateUtil.add(now, DateUtil.ADDDAY, -(int) subDay);    // 意向创建时间
                Boolean isWeekend = DateUtil.isWeekend(createDay);
                Boolean isCanSetWeek = true;
                if (!DateUtil.isThisWeek(createDay)) {
                    isCanSetWeek = false;
                }
                if (isWeekend && isCanSetWeek) {
                    Integer weekGoal = parameterService.getIntByCode(QingpuConstants.Parameter_CSWeekIntentionGoal);
                    if (jiangHuRen.getWeekDayIntention() < weekGoal && isAdd) {
                        gold = 0;
                        logger.info(userName + " 未完成周任务，不获取学分");
                    }
                }
				/*
				操作自己的内容
				 */
                Integer oldGold = jiangHuRen.getWeekGold();
                Integer newGold = oldGold + gold;
                if (subDay == 0) {
                    jiangHuRen.setDayIntention(jiangHuRen.getDayIntention() + intention);
                }
                if (isCanSetWeek) {
                    jiangHuRen.setWeekIntention(jiangHuRen.getWeekIntention() + intention);
                    jiangHuRen.setWeekGold(newGold);
                    if (subDay == 0) {
                        jiangHuRen.setWeekDayIntention(jiangHuRen.getWeekDayIntention() + intention);
                    }
                }
                Boolean isCanSetMonth = true;
                if (!DateUtil.isThisMonth(createDay)) {
                    isCanSetMonth = false;
                }
                if (isCanSetMonth) {
                    jiangHuRen.setMonthIntention(jiangHuRen.getMonthIntention() + intention);
                }
                jiangHuRen.setUpdateTime(now);
                this.update(jiangHuRen);
                // 判断是否升级
                Integer oldIndex = null;
                Integer newIndex = null;
                int[] intentionArr = QingpuConstants._csIntention;
                for (int i = 0; i < intentionArr.length; i++) {
                    int val = intentionArr[i];
                    if (oldGold < val && oldIndex == null) {
                        oldIndex = i;
                    }
                    if (newGold < val && newIndex == null) {
                        newIndex = i;
                    }
                    if (oldIndex != null && newIndex != null) {
                        break;
                    }
                }
                if (oldIndex == null) {
                    oldIndex = intentionArr.length;
                }
                if (newIndex == null) {
                    newIndex = intentionArr.length;
                }

				/*
				操作组
				 */
                Integer winPaiId = jiangHuRen.getPaiId();
                JiangHuPai jiangHuPai = jiangHuPaiService.get(winPaiId);
                if (jiangHuPai != null) {
                    String win = jiangHuPai.getNickName();
                    String intentionStr = clientStatus.getName();
                    String gongfu = jiangHuPai.getGongfu();
                    if (isAdd && oldIndex != null && newIndex != null && oldIndex < newIndex && isCanSetMonth) {
                        // 发送升级消息
                        Map<String, Object> map = new HashMap<>();
                        map.put("pai", win);
                        map.put("name", userName);
                        map.put("intention", intentionStr);
                        if (newIndex < 2) {
                            newIndex = 2;
                        }
                        map.put("oldPost", QingpuConstants._postName[newIndex - 2]);
                        map.put("post", QingpuConstants._postName[newIndex - 1]);
                        map.put("title", QingpuConstants._postName[newIndex - 1]);
                        String headImage = "";
                        if (StringUtils.isNotBlank(userInfo.getHeadImage())) {
                            headImage = QingpuConstants.uploadUrl + userInfo.getHeadImage();
                        }
                        map.put("headImage", headImage);
                        map.put("roleType", jiangHuRen.getRoleType());
                        for (Session session : WebSocketService.cocosOnlineSession) {
                            webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.SendJianghuUpgrade, map));
                        }
                    }
                    if (subDay == 0) {
                        jiangHuPai.setFinishCount(jiangHuPai.getFinishCount() + intention);
                    }
                    Integer winOldGold = jiangHuPai.getGold();
                    if (isCanSetWeek) {
                        jiangHuPai.setGold(winOldGold + gold);
                    }
                    Integer subCount = jiangHuPai.getSubCount();
                    // 从别的组扣金币
                    String goldPaiIds = jiangHuPai.getGoldPaiIds();
                    if (StringUtils.isNotBlank(goldPaiIds)) {
                        List<String> paiIdList = StringUtil.splitByComma(goldPaiIds);
                        if (!paiIdList.isEmpty()) {
                            Integer size = paiIdList.size();
                            Integer currentPaiId = null;
                            JiangHuPai losePai = null;
                            Integer loseOldGold = null;
                            if (gold > 0) {
                                for (int i = 0; i < gold; ) {
                                    currentPaiId = Integer.parseInt(paiIdList.get(0));
                                    JiangHuPai otherPai = jiangHuPaiService.get(currentPaiId);
                                    if (otherPai != null) {
                                        Integer leftCount = otherPai.getRenCount() - subCount;
                                        if (leftCount < 0) {
                                            // 避免出现负数进入无线循环
                                            leftCount = 0;
                                        }
                                        Integer alreadySubCount = leftCount;
                                        for (int j = 0; j < leftCount && i < gold; j++) {
                                            if (losePai == null) {
                                                losePai = otherPai;
                                                loseOldGold = otherPai.getGold();
                                            }
                                            alreadySubCount--;
                                            i++;
                                            subCount++;
                                            otherPai.setGold(otherPai.getGold() - 1);
                                        }
                                        if (alreadySubCount == 0) {
                                            subCount = 0;
                                            paiIdList.remove(0);
                                            paiIdList.add(currentPaiId + "");
                                            jiangHuPai.setGoldPaiIds(StringUtil.join(paiIdList, ","));
                                        }
                                        jiangHuPaiService.update(otherPai);
                                        if (i >= gold) {
                                            jiangHuPai.setSubCount(subCount);
                                            break;
                                        }
                                    } else {
                                        logger.error("未找到帮派： " + currentPaiId);
                                        break;
                                    }
                                }
                            } else if (gold < 0) {
                                for (int i = gold; i < 0; ) {
                                    currentPaiId = Integer.parseInt(paiIdList.get(0));
                                    JiangHuPai otherPai = jiangHuPaiService.get(currentPaiId);
                                    if (otherPai != null) {
                                        if (subCount == null) {
                                            subCount = otherPai.getRenCount();
                                        }
                                        Integer leftCount = subCount;
                                        Integer alreadySubCount = leftCount;
                                        for (int j = 0; j < leftCount && i < 0; j++) {
                                            alreadySubCount--;
                                            i++;
                                            subCount--;
                                            otherPai.setGold(otherPai.getGold() + 1);
                                        }
                                        if (alreadySubCount == 0) {
                                            subCount = null;
                                            Integer otherPaiId = Integer.parseInt(paiIdList.get(size - 1));
                                            paiIdList.remove(size - 1);
                                            paiIdList.add(0, otherPaiId + "");
                                            jiangHuPai.setGoldPaiIds(StringUtil.join(paiIdList, ","));
                                        }
                                        jiangHuPaiService.update(otherPai);
                                        if (i >= 0) {
                                            if (subCount == null) {
                                                subCount = 0;
                                            }
                                            jiangHuPai.setSubCount(subCount);
                                            break;
                                        }
                                    } else {
                                        logger.error("未找到帮派： " + currentPaiId);
                                        break;
                                    }
                                }
                            }

                            if (isAdd && gold > 0 && isCanSetWeek) {
                                DecimalFormat decimalFormat = new DecimalFormat("0");
                                Map<String, Object> map = new HashMap<>();
                                map.put("winName", win);
                                map.put("name", userName);
                                map.put("intention", intentionStr);
                                map.put("gongfu", gongfu);
                                map.put("loseName", losePai.getNickName());
                                map.put("hurt", gold * parameterService.getIntByCode(QingpuConstants.Parameter_GoldRate));
                                map.put("gold", gold);
//								map.put("winOldGold", winOldGold + 100 - jiangHuPai.getInitGold());
//								map.put("loseOldGold", loseOldGold + 100 - losePai.getInitGold());
                                map.put("winOldGold", Integer.parseInt(decimalFormat.format(jiangHuPai.getDoubleGold())));
                                map.put("loseOldGold", Integer.parseInt(decimalFormat.format(losePai.getDoubleGold())));
                                map.put("roleType", jiangHuRen.getRoleType());
                                JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
                                jiangHuRenCondition.setGtUpdateTime(DateUtil.getLastWeekMonday(now));
                                List<Integer> inPaiIdList = new ArrayList<>();
                                inPaiIdList.add(winPaiId);
                                inPaiIdList.add(losePai.getId());
                                jiangHuRenCondition.setInPaiId(inPaiIdList);
                                List<String> winHeadImageList = new ArrayList<>();
                                List<String> loseHeadImageList = new ArrayList<>();
                                List<JiangHuRen> jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("week_gold"));
                                for (JiangHuRen item : jiangHuRenList) {
                                    String headImage = "";
                                    if (StringUtils.isNotBlank(item.getUserHeadImage())) {
                                        headImage = QingpuConstants.uploadUrl + item.getUserHeadImage();
                                    }
                                    if (item.getPaiId().intValue() == winPaiId) {
                                        winHeadImageList.add(headImage);
                                    } else {
                                        loseHeadImageList.add(headImage);
                                    }
                                }
                                map.put("win", winHeadImageList);
                                map.put("lose", loseHeadImageList);
                                if (!loseHeadImageList.isEmpty()) {
                                    for (Session session : WebSocketService.cocosOnlineSession) {
                                        webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.SendJianghuIntention, map));
                                    }
                                }
                            }
                        }

                    }
                    // 从别的组扣金币方式二
                    if (gold != 0 && isCanSetWeek) {
                        BigDecimal goldBig = new BigDecimal(gold + "");
                        BigDecimal paiBig = new BigDecimal(jiangHuPai.getDoubleGold() + "");
                        jiangHuPai.setDoubleGold(goldBig.add(paiBig).setScale(13, BigDecimal.ROUND_HALF_UP).doubleValue());
                        UserGroupCondition userGroupCondition = new UserGroupCondition();
                        userGroupCondition.setFid(QingpuConstants.User_Group_Customer_Id);
                        userGroupCondition.setNoId(jiangHuPai.getId());
                        List<UserGroup> userGroupList = userGroupService.list(userGroupCondition);
                        List<Integer> groupIdList = new ArrayList<>();
                        for (UserGroup userGroup : userGroupList) {
                            UserCondition userCondition = new UserCondition();
                            userCondition.setDel(false);
                            userCondition.setGroupId(userGroup.getId());
                            List<User> userList = userService.list(userCondition);
                            if (!userList.isEmpty()) {
                                groupIdList.add(userGroup.getId());
                            }
                        }
                        if (!groupIdList.isEmpty()) {
                            Double paiGold = (gold * 1.0) / groupIdList.size();
                            BigDecimal subBig = new BigDecimal(paiGold + "");
                            for (Integer groupId : groupIdList) {
                                JiangHuPai item = jiangHuPaiService.get(groupId);
                                if (item != null) {
                                    BigDecimal otherBig = new BigDecimal(item.getDoubleGold() + "");
                                    item.setDoubleGold(otherBig.subtract(subBig).setScale(13, BigDecimal.ROUND_HALF_UP).doubleValue());
                                    jiangHuPaiService.update(item);
                                }
                            }
                        }
                    }
                    jiangHuPaiService.update(jiangHuPai);
                }
            }
        } catch (Exception e) {
            logger.info("客服意向变动失败： " + e.getMessage());
        }
    }

    /**
     * 约访加入意向
     *
     * @param user
     * @param finishCount
     * @param intention
     */
    public synchronized void addInterviewIntention(UserInfo user, Integer intention, Integer finishCount) {
        try {
            String userName = user.getName();
            JiangHuRen jiangHuRen = this.getByNameAndRoleType(userName, PublicEnum.RoleType.Interview);
            if (intention != 0 || finishCount != 0) {
                if (jiangHuRen != null) {
                    int index = 0;
                    int gold = QingpuConstants._interviewGold[0] * intention + QingpuConstants._interviewGold[1] * finishCount;
				/*
				操作自己的内容
				 */
                    Integer oldGold = jiangHuRen.getWeekGold();
                    Integer newGold = oldGold + gold;
                    jiangHuRen.setWeekIntention(jiangHuRen.getWeekIntention() + intention);
                    jiangHuRen.setMonthIntention(jiangHuRen.getMonthIntention() + intention);
                    jiangHuRen.setWeekFinish(jiangHuRen.getWeekFinish() + finishCount);
                    jiangHuRen.setMonthFinish(jiangHuRen.getMonthFinish() + finishCount);
                    jiangHuRen.setWeekGold(newGold);
                    jiangHuRen.setUpdateTime(new Date());
                    this.update(jiangHuRen);
                    // 判断是否升级
                    Integer oldIndex = null;
                    Integer newIndex = null;
                    int[] intentionArr = QingpuConstants._interviewIntention;
                    for (int i = 0; i < intentionArr.length; i++) {
                        int val = intentionArr[i];
                        if (oldGold < val && oldIndex == null) {
                            oldIndex = i;
                        }
                        if (newGold < val && newIndex == null) {
                            newIndex = i;
                        }
                        if (oldIndex != null && newIndex != null) {
                            break;
                        }
                    }
                    if (oldIndex == null) {
                        oldIndex = intentionArr.length;
                    }
                    if (newIndex == null) {
                        newIndex = intentionArr.length;
                    }

				/*
				操作组
				 */
                    Integer winPaiId = jiangHuRen.getPaiId();
                    JiangHuPai jiangHuPai = jiangHuPaiService.get(winPaiId);
                    if (jiangHuPai != null) {
                        String win = jiangHuPai.getNickName();
                        String gongfu = jiangHuPai.getGongfu();
                        if (oldIndex < newIndex) {
                            // 发送升级消息
                            Map<String, Object> map = new HashMap<>();
                            map.put("finish", finishCount);
                            map.put("intention", intention);
                            map.put("pai", win);
                            map.put("name", userName);
                            map.put("oldPost", QingpuConstants._postName[newIndex - 2]);
                            map.put("post", QingpuConstants._postName[newIndex - 1]);
                            map.put("title", QingpuConstants._postName[newIndex - 1]);
                            map.put("headImage", user.getHeadImage());
                            map.put("roleType", jiangHuRen.getRoleType());
//							for(Session session : WebSocketService.cocosOnlineSession) {
//								webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.SendJianghuUpgrade, map));
//							}
                        }
                        jiangHuPai.setFinishCount(jiangHuPai.getFinishCount() + intention);
                        Integer winOldGold = jiangHuPai.getGold();
                        jiangHuPai.setGold(winOldGold + gold);
                        Integer subCount = jiangHuPai.getSubCount();
                        // 从别的组扣金币
                        String goldPaiIds = jiangHuPai.getGoldPaiIds();
                        if (StringUtils.isNotBlank(goldPaiIds)) {
                            List<String> paiIdList = StringUtil.splitByComma(goldPaiIds);
                            if (gold > 0 && !paiIdList.isEmpty()) {
                                Integer currentPaiId = null;
                                JiangHuPai losePai = null;
                                Integer loseOldGold = null;
                                for (int i = 0; i < gold; ) {
                                    currentPaiId = Integer.parseInt(paiIdList.get(0));
                                    JiangHuPai otherPai = jiangHuPaiService.get(currentPaiId);
                                    if (otherPai != null) {
                                        Integer leftCount = otherPai.getRenCount() - subCount;
                                        if (leftCount < 0) {
                                            // 避免出现负数进入无线循环
                                            leftCount = 0;
                                        }
                                        Integer alreadySubCount = leftCount;
                                        for (int j = 0; j < leftCount && i < gold; j++) {
                                            if (losePai == null) {
                                                losePai = otherPai;
                                                loseOldGold = otherPai.getGold();
                                            }
                                            alreadySubCount--;
                                            i++;
                                            subCount++;
                                            otherPai.setGold(otherPai.getGold() - 1);
                                        }
                                        if (alreadySubCount == 0) {
                                            subCount = 0;
                                            paiIdList.remove(0);
                                            paiIdList.add(currentPaiId + "");
                                            jiangHuPai.setGoldPaiIds(StringUtil.join(paiIdList, ","));
                                        }
                                        jiangHuPaiService.update(otherPai);
                                        if (i >= gold) {
                                            jiangHuPai.setSubCount(subCount);
                                            break;
                                        }
                                    } else {
                                        logger.error("未找到帮派： " + currentPaiId);
                                        break;
                                    }
                                }
                                Map<String, Object> map = new HashMap<>();
                                map.put("winName", win);
                                map.put("finish", finishCount);
                                map.put("intention", intention);
                                map.put("name", userName);
                                map.put("gongfu", gongfu);
                                map.put("loseName", losePai.getNickName());
                                map.put("hurt", gold * parameterService.getIntByCode(QingpuConstants.Parameter_GoldRate));
                                map.put("gold", gold);
                                map.put("winOldGold", winOldGold + 100 - jiangHuPai.getInitGold());
                                map.put("loseOldGold", loseOldGold + 100 - losePai.getInitGold());
                                map.put("roleType", jiangHuRen.getRoleType());
                                JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
                                jiangHuRenCondition.setGtUpdateTime(DateUtil.getLastWeekMonday(new Date()));
                                List<Integer> inPaiIdList = new ArrayList<>();
                                inPaiIdList.add(winPaiId);
                                inPaiIdList.add(losePai.getId());
                                jiangHuRenCondition.setInPaiId(inPaiIdList);
                                List<String> winHeadImageList = new ArrayList<>();
                                List<String> loseHeadImageList = new ArrayList<>();
                                List<JiangHuRen> jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("week_gold"));
                                for (JiangHuRen item : jiangHuRenList) {
                                    if (item.getPaiId().intValue() == winPaiId) {
                                        winHeadImageList.add(item.getUserHeadImage());
                                    } else {
                                        loseHeadImageList.add(item.getUserHeadImage());
                                    }
                                }
                                map.put("win", winHeadImageList);
                                map.put("lose", loseHeadImageList);
                                if (!loseHeadImageList.isEmpty()) {
//									for(Session session : WebSocketService.cocosOnlineSession){
//										webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.SendJianghuIntention, map));
//									}
                                }
                            }

                        }
                        jiangHuPaiService.update(jiangHuPai);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("约访意向变动失败： " + e.getMessage());
        }
    }


    /**
     * 获取基础信息
     *
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getBaseInfo() throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("mouthCSGoal", parameterService.getIntByCode(QingpuConstants.Parameter_Mouth_Goal));
        resultMap.put("mouthInterviewGoal", parameterService.getIntByCode(QingpuConstants.Parameter_Mouth_Interview_Goal));
        Integer postLength = QingpuConstants._postName.length;
        resultMap.put("firstPost", QingpuConstants._postName[postLength - 1]);
        resultMap.put("secondPost", QingpuConstants._postName[postLength - 2]);

        Map<Integer, String> paiNameMap = new HashMap<>();
        Map<String, Object> rankMap = this.getLastWeekRankMap();
        Map<String, Object> csMap = (Map<String, Object>) rankMap.get("CS");
        List<JiangHuRen> topRenList = (List<JiangHuRen>) csMap.get("first");
        List<JiangHuRen> lastList = (List<JiangHuRen>) csMap.get("last");
        csMap = new HashMap<>();
        csMap.put("first", this.listToMap(topRenList, paiNameMap));
        csMap.put("last", this.listToMap(lastList, paiNameMap));
        resultMap.put("CS", csMap);

        csMap = (Map<String, Object>) rankMap.get("interview");
        topRenList = (List<JiangHuRen>) csMap.get("first");
        lastList = (List<JiangHuRen>) csMap.get("last");
        csMap = new HashMap<>();
        csMap.put("first", this.listToMap(topRenList, paiNameMap));
        csMap.put("last", this.listToMap(lastList, paiNameMap));
        resultMap.put("interview", csMap);

        Map<Integer, JiangHuRen> secondMap = (Map<Integer, JiangHuRen>) rankMap.get("secondMap");
        // 加入第二名
        List<JiangHuRen> secondRenList = new ArrayList<>();
        for (Integer key : secondMap.keySet()) {
            secondRenList.add(secondMap.get(key));
        }
        resultMap.put("secondRen", this.listToMap(secondRenList, paiNameMap));
        return resultMap;
    }


    /**
     * 获取上周排名
     *
     * @return
     */
    public Map<String, Object> getLastWeekRankMap() {
        Map<String, Object> resultMap = new HashMap<>();
        Date lastFir = DateUtil.getLastWeekMonday(DateUtil.getDayStartTime());
        // 客服前几名与最后几名
        Map<String, Object> csMap = new HashMap<>();
        JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setGtUpdateTime(lastFir);
        jiangHuRenCondition.setRoleType(PublicEnum.RoleType.CS);
        List<JiangHuRen> jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("last_week_gold"));
        Integer beforeMaxCount = 0;
        Map<Integer, JiangHuRen> secondMap = new HashMap<>();
        List<JiangHuRen> topRenList = new ArrayList<>();
        List<Integer> ignorePaiId = new ArrayList<>();
        List<JiangHuRen> lastList = new ArrayList<>();
        Integer csRenSize = jiangHuRenList.size();
        Integer maxWeekCount = null;
        for (int i = 0; i < csRenSize; i++) {
            JiangHuRen jiangHuRen = jiangHuRenList.get(i);
            Integer count = jiangHuRen.getLastWeekGold();
            Integer paiId = jiangHuRen.getPaiId();
            if (maxWeekCount == null) {
                maxWeekCount = count;
            }
            if (maxWeekCount != 0) {
                if (count != 0) {
                    if (count >= beforeMaxCount) {
                        // 并列排名第一
                        beforeMaxCount = count;
                        topRenList.add(jiangHuRen);
                        ignorePaiId.add(paiId);
                    } else {
                        if (!secondMap.containsKey(paiId) && !ignorePaiId.contains(paiId)) {
                            secondMap.put(paiId, jiangHuRen);
                        }
                    }
                }
            }
        }
        csMap.put("first", topRenList);

        List<String> userNameList = new ArrayList<>();
        UserCondition userCondition = new UserCondition();
        userCondition.setDel(false);
        userCondition.setRoleId(QingpuConstants.Roles_Employee_id);
        userCondition.setWorkType(PublicEnum.WorkType.FullTime);
        userCondition.setLeNewCreateTime(DateUtil.getLastWeekMonday(new Date()));
        List<User> userList = userService.list(userCondition);
        for (User user : userList) {
            userNameList.add(user.getName());
        }
        JiangHuRankCondition jiangHuRankCondition = new JiangHuRankCondition();
        jiangHuRankCondition.setGtCreateTime(DateUtil.add(new Date(), DateUtil.ADDDAY, -14));
        jiangHuRankCondition.setRoleType(PublicEnum.RoleType.CS);
        jiangHuRankCondition.setInUserName(userNameList);
        List<JiangHuRank> jiangHuRankList = jiangHuRankService.listCustom(jiangHuRankCondition, new Sorter().asc("gold"), 5);
        int lastIndex = 1;
        for (JiangHuRank jiangHuRank : jiangHuRankList) {
            if (jiangHuRank.getLastIndex() == null) {
                try {
                    jiangHuRank.setLastIndex(lastIndex);
                    jiangHuRankService.update(jiangHuRank);
                } catch (Exception e) {
                    logger.info("设置倒数第几名失败： " + e.getMessage());
                }
            }
            lastIndex++;
            JiangHuRen jiangHuRen = new JiangHuRen();
            jiangHuRen.setLastWeekGold(jiangHuRank.getGold());
            jiangHuRen.setLastWeekIntention(jiangHuRank.getIntention());
            jiangHuRen.setUserName(jiangHuRank.getUserName());
            JiangHuRen exitRen = this.getByNameAndRoleType(jiangHuRank.getUserName(), PublicEnum.RoleType.CS);
            if (exitRen != null && StringUtils.isNotBlank(exitRen.getUserHeadImage())) {
                jiangHuRen.setUserHeadImage(QingpuConstants.uploadUrl + exitRen.getUserHeadImage());
            } else {
                jiangHuRen.setUserHeadImage("");
            }
            lastList.add(jiangHuRen);
        }
        csMap.put("last", lastList);
        resultMap.put("CS", csMap);


        // 约访前几名与最后几名
        jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setGtUpdateTime(lastFir);
        jiangHuRenCondition.setRoleType(PublicEnum.RoleType.Interview);
        jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("last_week_gold"));
        beforeMaxCount = 0;
        topRenList = new ArrayList<>();
        lastList = new ArrayList<>();
        csRenSize = jiangHuRenList.size();
        Integer maxInterviewWeekCount = null;
        Map<String, Object> interviewMap = new HashMap<>();
//		for(int i = 0; i < csRenSize; i++){
//			JiangHuRen jiangHuRen = jiangHuRenList.get(i);
//			Integer count = jiangHuRen.getLastWeekGold();
//			Integer paiId = jiangHuRen.getPaiId();
//			if(maxInterviewWeekCount == null){
//				maxInterviewWeekCount = count;
//			}
//			if(maxInterviewWeekCount != 0){
//				if(count != 0) {
//					if (count >= beforeMaxCount) {
//						// 并列排名第一
//						beforeMaxCount = count;
//						topRenList.add(jiangHuRen);
//						ignorePaiId.add(paiId);
//					} else {
//						if (!secondMap.containsKey(paiId) && !ignorePaiId.contains(paiId)) {
//							secondMap.put(paiId, jiangHuRen);
//						}
//					}
//				}
//				if(i > csRenSize - 2){
//					lastList.add(jiangHuRen);
//				}
//			}
//		}
        interviewMap.put("first", topRenList);
        interviewMap.put("last", lastList);
        resultMap.put("interview", interviewMap);
        resultMap.put("secondMap", secondMap);
        return resultMap;
    }

    /**
     * 获取动态信息
     *
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getChangeInfo() throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        // 统计本月
        Date gtTime = DateUtil.getStartMonthTime();
        JiangHuRenCondition jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setGtUpdateTime(gtTime);
        List<JiangHuRen> jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("month_intention"));
        Integer csMonthTotal = 0;
        Integer interviewMonthTotal = 0;
        for (JiangHuRen jiangHuRen : jiangHuRenList) {
            Integer count = jiangHuRen.getMonthIntention();
            if (jiangHuRen.getRoleType() == PublicEnum.RoleType.CS) {
                csMonthTotal += count;
            } else {
                interviewMonthTotal += count;
            }
        }
        resultMap.put("csMonthTotal", csMonthTotal);
        resultMap.put("interviewMonthTotal", interviewMonthTotal);
        resultMap.put("timestamp", new Date().getTime());

        // 统计今日
        Map<Integer, JiangHuPai> jiangHuPaiMap = new HashMap<>();
        Map<Integer, String> paiNameMap = new HashMap<>();
        gtTime = DateUtil.getLastWeekMonday(new Date());
        Integer csDayFinishCount = 0;
        // 客服
        jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setGtUpdateTime(gtTime);
        jiangHuRenCondition.setRoleType(PublicEnum.RoleType.CS);
        jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("pai_id").asc("update_time"));
        List<Map<String, Object>> paiList = new ArrayList<>();
        Map<String, Object> paiMap = new HashMap<>();
        List<JiangHuRen> paiRenList = new ArrayList<>();
        Integer csDayGoal = 0;
        DecimalFormat decimalFormat = new DecimalFormat("0");
        for (JiangHuRen jiangHuRen : jiangHuRenList) {
            Integer paiId = jiangHuRen.getPaiId();
//			if(csDayGoal == 0){
//				User user = new User();
//				user.setId(jiangHuRen.getUserId());
//				user.setName(jiangHuRen.getUserName());
//				user.setHeadImage(jiangHuRen.getUserHeadImage());
//				this.addCSIntention(user, PublicEnum.ClientStatus.A, true);
//			}
            if (!jiangHuPaiMap.containsKey(paiId)) {
                if (!paiMap.isEmpty()) {
                    paiMap.put("ren", this.listToMap(paiRenList, paiNameMap));
                    paiList.add(paiMap);
                }
                paiMap = new HashMap<>();
                paiRenList = new ArrayList<>();
                JiangHuPai jiangHuPai = jiangHuPaiService.get(paiId);
                jiangHuPaiMap.put(paiId, jiangHuPai);
                csDayGoal += jiangHuPai.getCount();
                paiMap.put("name", jiangHuPai.getNickName());
                paiMap.put("count", jiangHuPai.getCount());
                paiMap.put("finishCount", jiangHuPai.getFinishCount());
//				paiMap.put("gold", 100 - jiangHuPai.getInitGold() + jiangHuPai.getGold());
                paiMap.put("gold", Integer.parseInt(decimalFormat.format(jiangHuPai.getDoubleGold())));
            }
            csDayFinishCount += jiangHuRen.getDayIntention();
            paiRenList.add(jiangHuRen);
        }
        if (!paiMap.isEmpty()) {
            paiMap.put("ren", this.listToMap(paiRenList, paiNameMap));
            paiList.add(paiMap);
        }
        resultMap.put("cs", paiList);
        resultMap.put("csDayFinishCount", csDayFinishCount);
        resultMap.put("csDayGoal", csDayGoal);

        // 约访
        jiangHuRenCondition = new JiangHuRenCondition();
        jiangHuRenCondition.setGtUpdateTime(gtTime);
        jiangHuRenCondition.setRoleType(PublicEnum.RoleType.Interview);
        jiangHuRenList = this.list(jiangHuRenCondition, new Sorter().desc("pai_id").asc("update_time"));
        paiList = new ArrayList<>();
        paiMap = new HashMap<>();
        paiRenList = new ArrayList<>();
//		for(JiangHuRen jiangHuRen : jiangHuRenList){
//			Integer paiId = jiangHuRen.getPaiId();
//			if( ! jiangHuPaiMap.containsKey(paiId)){
//				if( ! paiMap.isEmpty()){
//					paiMap.put("ren", this.listToMap(paiRenList, paiNameMap));
//					paiList.add(paiMap);
//				}
//				paiMap = new HashMap<>();
//				paiRenList = new ArrayList<>();
//				JiangHuPai jiangHuPai = jiangHuPaiService.get(paiId);
//				jiangHuPaiMap.put(paiId, jiangHuPai);
//				paiMap.put("name", jiangHuPai.getNickName());
//				paiMap.put("count", jiangHuPai.getCount());
//				paiMap.put("finishCount", jiangHuPai.getFinishCount());
//				paiMap.put("gold", 100 - jiangHuPai.getInitGold() + jiangHuPai.getGold());
//			}
//			paiRenList.add(jiangHuRen);
//		}
//		if( ! paiMap.isEmpty()){
//			paiMap.put("ren", this.listToMap(paiRenList, paiNameMap));
//			paiList.add(paiMap);
//		}
        resultMap.put("interview", paiList);

        return resultMap;
    }


    /**
     * 列表转map
     *
     * @param jiangHuRenList
     * @param paiNameMap
     * @return
     */
    private List<Map<String, Object>> listToMap(List<JiangHuRen> jiangHuRenList, Map<Integer, String> paiNameMap) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (JiangHuRen jiangHuRen : jiangHuRenList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", jiangHuRen.getUserName());
            Integer lastWeekGold = jiangHuRen.getLastWeekGold();
            map.put("lastWeekGold", lastWeekGold);
            Integer weekGold = jiangHuRen.getWeekGold();
            map.put("weekGold", weekGold);
            map.put("lastWeekIntention", jiangHuRen.getLastWeekIntention());
            map.put("weekIntention", jiangHuRen.getWeekIntention());
            String headImage = "";
            if (StringUtils.isNotBlank(jiangHuRen.getUserHeadImage())) {
                headImage = QingpuConstants.uploadUrl + jiangHuRen.getUserHeadImage();
            }
            map.put("headImage", headImage);
            map.put("dayIntention", jiangHuRen.getDayIntention());
            map.put("paiName", jiangHuPaiService.getNameByMap(jiangHuRen.getPaiId(), paiNameMap));
            PublicEnum.RoleType roleType = jiangHuRen.getRoleType();
            int[] intentionArr = QingpuConstants._csIntention;
            if (roleType == PublicEnum.RoleType.Interview) {
                intentionArr = QingpuConstants._interviewIntention;
                map.put("weekFinish", jiangHuRen.getWeekFinish());
                map.put("lastWeekFinish", jiangHuRen.getLastWeekFinish());
            }
            map.put("roleType", roleType);
            Integer lastWeekPost = null;
            Integer weekPost = null;
            for (int i = 0; i < intentionArr.length; i++) {
                int val = intentionArr[i];
                if (lastWeekGold < val && lastWeekPost == null) {
                    lastWeekPost = i;
                }
                if (weekGold < val && weekPost == null) {
                    weekPost = i;
                }
                if (weekPost != null && lastWeekPost != null) {
                    break;
                }
            }
            if (lastWeekPost == null) {
                lastWeekPost = intentionArr.length;
            }
            if (weekPost == null) {
                weekPost = intentionArr.length;
            }
            if (weekPost == 0) {
                weekPost = 1;
            }
            if (lastWeekPost == 0) {
                lastWeekPost = 1;
            }
            map.put("weekPost", QingpuConstants._postName[weekPost - 1]);
            map.put("lastWeekPost", QingpuConstants._postName[lastWeekPost - 1]);
            resultList.add(map);
        }
        return resultList;
    }


    /**
     * 录入约访意向
     *
     * @param userIds
     * @param intentions
     * @param finishes
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void importIntention(String userIds, String intentions, String finishes) throws Exception {
        List<String> userIdList = StringUtil.splitByComma(userIds);
        List<String> intentionList = StringUtil.splitByComma(intentions);
        List<String> finishList = StringUtil.splitByComma(finishes);
        int i = 0;
        for (String userId : userIdList) {
            Integer userIdInt = Integer.parseInt(userId);
            User user = userService.get(userIdInt);
            if (user != null) {
                Integer intention = Integer.parseInt(intentionList.get(i));
                Integer finish = Integer.parseInt(finishList.get(i));
                if (intention != 0 || finish != 0) {
                    JiangHuRen jiangHuRen = this.getByNameAndRoleType(user.getName(), PublicEnum.RoleType.Interview);
                    if (jiangHuRen != null) {
                        Integer oldIntention = jiangHuRen.getWeekIntention();
                        Integer oldFinish = jiangHuRen.getWeekFinish();
                        UserInfo userInfo = userInfoService.getByName(user.getName());
                        if (userInfo != null) {
                            this.addInterviewIntention(userInfo, intention - oldIntention, finish - oldFinish);
                        }
                    }
                }
            }
            i++;
        }
    }

    /**
     * 获取刷新数据
     *
     * @param session
     */
    @Transactional(rollbackFor = Exception.class)
    public void getJianghuRefreshInfo(Session session) throws Exception {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            // 用户数据
            String userIds = "";
            UserCondition userCondition = new UserCondition();
            userCondition.setRoleId(QingpuConstants.Roles_Employee_id);
            List<User> userList = userService.list(userCondition);
            // 客服组如果没有员工则不返回
            for (User user : userList) {
                userIds += user.getId() + ",";
            }
            TableParams tableParams = new TableParams();
            tableParams.setRoleType("cs");
            String nowDayStr = DateUtil.dateToString(new Date(), 0);
            tableParams.setStartTimeStr(nowDayStr);
            tableParams.setEndTimeStr(nowDayStr);
            tableParams.setUserIds(StringUtil.subLastChar(userIds));
            Map<String, Object> map = callRecordService.listStatistics(tableParams, true);
            Integer dayFinishCount = 0;
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("result");
            for (Map<String, Object> item : mapList) {
                String workNumber = (String) item.get("workNumber");
                if (StringUtils.isNotBlank(workNumber) && workNumber.equals("总计")) {
                    dayFinishCount = (Integer) item.get("sumYx");
                    break;
                }
            }
            resultMap.put("dayFinishCount", dayFinishCount);
            webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.GetJianghuRefreshInfo, resultMap));
        } catch (Exception e) {
            logger.error("发送江湖基础信息：" + e.getMessage());
        }
    }

    /**
     * 获取统计信息
     *
     * @param session
     */
    public void getStatisticsInfo(Session session, Boolean isSystem) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            if (!isSystem) {
                resultMap = JiangHuRenService.statisticsMap;
            } else {
                // 用户数据
                String userIds = "";

                UserGroupCondition userGroupCondition = new UserGroupCondition();
                userGroupCondition.setFid(QingpuConstants.User_Group_Customer_Id);
                List<UserGroup> userGroupList = userGroupService.list(userGroupCondition);
                for (UserGroup userGroup : userGroupList) {
                    UserCondition userCondition = new UserCondition();
                    userCondition.setDel(false);
                    userCondition.setGroupId(userGroup.getId());
                    List<User> userList = userService.list(userCondition);
                    if (!userList.isEmpty()) {
                        for (User user : userList) {
                            userIds += user.getId() + ",";
                        }
                    }
                }
                Map<String, UserInfo> nameUserInfoMap = new HashMap<>();
                TableParams tableParams = new TableParams();
                tableParams.setRoleType("cs");
                tableParams.setStartTimeStr(DateUtil.dateToString(DateUtil.getBeginDayOfWeek(), 0));
                tableParams.setEndTimeStr(DateUtil.dateToString(new Date(), 0));
                tableParams.setUserIds(StringUtil.subLastChar(userIds));

                /*
                效率统计
                 */
                Map<String, Object> map = callRecordService.listStatistics(tableParams, true);
                List<Map<String, Object>> statisticsList = (List<Map<String, Object>>) map.get("result");
                Collections.sort(statisticsList, new Comparator<Map<String, Object>>() {
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        Object o1ValObj = o1.get("efficiency");
                        int map1value = 100000;
                        if (o1ValObj != null) {
                            if (o1ValObj instanceof Double) {
                                map1value = ((Double) o1ValObj).intValue();
                            } else if (o1ValObj instanceof String) {
                                map1value = Integer.parseInt((String) o1ValObj);
                            }
                        }
                        Object o2ValObj = o2.get("efficiency");
                        int map2value = 100000;
                        if (o2ValObj != null) {
                            if (o2ValObj instanceof Double) {
                                map2value = ((Double) o2ValObj).intValue();
                            } else if (o2ValObj instanceof String) {
                                map2value = Integer.parseInt((String) o2ValObj);
                            }
                        }
                        if (map1value == map2value) {
                            Object o3ValObj = o1.get("sumTotal");
                            if (o3ValObj != null) {
                                if (o3ValObj instanceof Double) {
                                    map1value = ((Double) o3ValObj).intValue();
                                } else if (o3ValObj instanceof String) {
                                    map1value = Integer.parseInt((String) o3ValObj);
                                } else if (o3ValObj instanceof BigDecimal) {
                                    map1value = ((BigDecimal) o3ValObj).intValue();
                                } else if (o3ValObj instanceof BigInteger) {
                                    map1value = ((BigInteger) o3ValObj).intValue();
                                }
                            }
                            Object o4ValObj = o2.get("sumTotal");
                            if (o4ValObj != null) {
                                if (o4ValObj instanceof Double) {
                                    map2value = ((Double) o4ValObj).intValue();
                                } else if (o4ValObj instanceof String) {
                                    map2value = Integer.parseInt((String) o4ValObj);
                                } else if (o4ValObj instanceof BigDecimal) {
                                    map2value = ((BigDecimal) o4ValObj).intValue();
                                } else if (o4ValObj instanceof BigInteger) {
                                    map2value = ((BigInteger) o4ValObj).intValue();
                                }
                            }
                        }
                        if (map1value == 0) {
                            map1value = 100000;
                        }
                        if (map2value == 0) {
                            map2value = 100000;
                        }
                        return map1value - map2value;
                    }
                });
                Integer count = 15;
                if (statisticsList.size() < count) {
                    count = statisticsList.size();
                }
                List<Map<String, Object>> efficiencyResultMap = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Map<String, Object> itemMap = statisticsList.get(i);
                    String userName = (String) itemMap.get("userName");
                    if (StringUtils.isNotBlank(userName)) {
                        Map<String, Object> statisticsMap = new HashMap<>();
                        statisticsMap.put("userName", userName);
                        UserInfo userInfo = nameUserInfoMap.get(userName);
                        if (userInfo == null) {
                            userInfo = userInfoService.getByName(userName);
                            nameUserInfoMap.put(userName, userInfo);
                        }
                        if (StringUtils.isNotBlank(userInfo.getHeadImage())) {
                            statisticsMap.put("headImage", QingpuConstants.uploadUrl + userInfo.getHeadImage());
                        }
                        statisticsMap.put("sumTotal", itemMap.get("sumTotal"));
                        statisticsMap.put("sumYx", itemMap.get("sumYx"));
                        statisticsMap.put("efficiency", itemMap.get("efficiency"));
                        efficiencyResultMap.add(statisticsMap);
                    }
                }
                resultMap.put("efficiency", efficiencyResultMap);

			/*
			意向转化率
			 */
                Map<String, Object> conversionRateTotalMap = clientService.getConversionRate(tableParams);
                List<Map<String, Object>> conversionRateList = (List<Map<String, Object>>) conversionRateTotalMap.get("result");
                Collections.sort(conversionRateList, new Comparator<Map<String, Object>>() {
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        Object o1ValObj = o1.get("rateNum");
                        int map1value = 0;
                        if (o1ValObj != null) {
                            if (o1ValObj instanceof Double) {
                                map1value = ((Double) o1ValObj).intValue();
                            } else if (o1ValObj instanceof String) {
                                map1value = Integer.parseInt((String) o1ValObj);
                            }
                        }
                        Object o2ValObj = o2.get("rateNum");
                        int map2value = 0;
                        if (o2ValObj != null) {
                            if (o2ValObj instanceof Double) {
                                map2value = ((Double) o2ValObj).intValue();
                            } else if (o2ValObj instanceof String) {
                                map2value = Integer.parseInt((String) o2ValObj);
                            }
                        }
                        if (map1value == map2value) {
                            Object o3ValObj = o1.get("reviewCount");
                            if (o3ValObj != null) {
                                if (o3ValObj instanceof Double) {
                                    map1value = ((Double) o3ValObj).intValue();
                                } else if (o3ValObj instanceof String) {
                                    map1value = Integer.parseInt((String) o3ValObj);
                                } else if (o3ValObj instanceof BigDecimal) {
                                    map1value = ((BigDecimal) o3ValObj).intValue();
                                } else if (o3ValObj instanceof BigInteger) {
                                    map1value = ((BigInteger) o3ValObj).intValue();
                                } else if (o3ValObj instanceof Integer) {
                                    map1value = (Integer) o3ValObj;
                                }
                            }
                            Object o4ValObj = o2.get("reviewCount");
                            if (o4ValObj != null) {
                                if (o4ValObj instanceof Double) {
                                    map2value = ((Double) o4ValObj).intValue();
                                } else if (o4ValObj instanceof String) {
                                    map2value = Integer.parseInt((String) o4ValObj);
                                } else if (o4ValObj instanceof BigDecimal) {
                                    map2value = ((BigDecimal) o4ValObj).intValue();
                                } else if (o4ValObj instanceof BigInteger) {
                                    map2value = ((BigInteger) o4ValObj).intValue();
                                } else if (o4ValObj instanceof Integer) {
                                    map2value = (Integer) o4ValObj;
                                }
                            }
                        }
                        return map2value - map1value;
                    }
                });
                count = 15;
                if (conversionRateList.size() < count) {
                    count = conversionRateList.size();
                }
                List<Map<String, Object>> conversionRateResultMap = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Map<String, Object> itemMap = conversionRateList.get(i);
                    String userName = (String) itemMap.get("name");
                    if (StringUtils.isNotBlank(userName) && !userName.equals("总计")) {
                        Map<String, Object> conversionRateMap = new HashMap<>();
                        conversionRateMap.put("userName", userName);
                        UserInfo userInfo = nameUserInfoMap.get(userName);
                        if (userInfo == null) {
                            userInfo = userInfoService.getByName(userName);
                            nameUserInfoMap.put(userName, userInfo);
                        }
                        if (StringUtils.isNotBlank(userInfo.getHeadImage())) {
                            conversionRateMap.put("headImage", QingpuConstants.uploadUrl + userInfo.getHeadImage());
                        }
                        conversionRateMap.put("rate", itemMap.get("rate"));
                        conversionRateMap.put("reviewCount", itemMap.get("reviewCount"));
                        conversionRateMap.put("count", itemMap.get("count"));
                        conversionRateResultMap.add(conversionRateMap);
                    }
                }
                resultMap.put("conversionRate", conversionRateResultMap);
                JiangHuRenService.statisticsMap = resultMap;
            }
            if (session != null) {
                webSocketService.sendMessage(session, new WebSocketData(WebSocketData.WebSocketWant.GetStatisticsInfo, resultMap));
            }
        } catch (Exception e) {
            logger.error("发送统计信息失败：" + e.getMessage());
        }
    }

    public void initScheduled(Boolean isRestart) {
        Date now = new Date();
        if(isRestart){
            new Timer(true).schedule(new StatisticsThread(this), 1000);
        }
        Date startDate = DateUtil.strDateToDate("08:00:00", 5);
        if(DateUtil.millisecondsBetween(now, startDate) > 0){
            new Timer(true).schedule(new StatisticsThread(this), startDate);
        }
        startDate = DateUtil.strDateToDate("12:00:00", 5);
        if(DateUtil.millisecondsBetween(now, startDate) > 0){
            new Timer(true).schedule(new StatisticsThread(this), startDate);
        }
        startDate = DateUtil.strDateToDate("18:00:00", 5);
        if(DateUtil.millisecondsBetween(now, startDate) > 0){
            new Timer(true).schedule(new StatisticsThread(this), startDate);
        }
    }

    public class StatisticsThread extends TimerTask {
        JiangHuRenService jiangHuRenService;

        StatisticsThread(JiangHuRenService jiangHuRenService) {
            this.jiangHuRenService = jiangHuRenService;
        }

        public void run() {
            try {
                logger.info("定时获取统计信息");
                jiangHuRenService.getStatisticsInfo(null, true);
            } catch (Exception e1) {
                logger.error("定时获取统计信息失败： " + e1.getMessage());
            }
        }
    }
}
