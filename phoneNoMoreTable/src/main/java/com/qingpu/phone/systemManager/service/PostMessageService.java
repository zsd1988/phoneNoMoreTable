package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.func.webSocket.WebSocketData;
import com.qingpu.phone.common.func.webSocket.WebSocketService;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.PostMessageCondition;
import com.qingpu.phone.systemManager.entity.PostMessage;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.*;

@Service("postMessageService")
@Transactional
public class PostMessageService {
	private static Logger logger = Logger.getLogger(PostMessageService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	UserService userService;

	@Resource
	WebSocketService webSocketService;

	/**
	 * 创建
	 * @param postMessage
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public PostMessage create(PostMessage postMessage) throws Exception{
		CommonUtils.checkEntity(postMessage);
		return (PostMessage) baseDao.save(postMessage);
	}


	/**
	 * 更新
	 * @param postMessage
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(PostMessage postMessage)  throws Exception {
		CommonUtils.checkEntity(postMessage);
		baseDao.update(postMessage);
	}


	/**
	 * 删除
	 * @param postMessage
	 */
	public void delete(PostMessage postMessage) {
		postMessage.setIsDel(true);
		postMessage.setDelTime(new Date());
		baseDao.update(postMessage);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public PostMessage get(Long id) {
		if(id == null ){
			return null;
		}
		return (PostMessage)baseDao.get(PostMessage.class, id);
	}


	/**
	 * 按条件查找
	 * @param postMessageCondition
	 * @return
	 */
	public List<PostMessage> list(PostMessageCondition postMessageCondition){
		return  postMessageCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param postMessageCondition
	 * @param sorter
	 * @return
	 */
	public List<PostMessage> list(PostMessageCondition postMessageCondition, Sorter sorter){
		return  postMessageCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param postMessageCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<PostMessage> list(PostMessageCondition postMessageCondition, Range range, Sorter sorter){
		PaginationSupport<PostMessage> testTablePaginationSupport = (PaginationSupport<PostMessage>) postMessageCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param postMessageCondition
	 * @return
	 */
	public Long countByCondition(PostMessageCondition postMessageCondition){
		return   postMessageCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param postMessageCondition
	 * @return
	 */
	public List<Object[]> listCustom(PostMessageCondition postMessageCondition){
		return  postMessageCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param postMessageCondition
	 * @param num
	 * @return
	 */
	public List<PostMessage> listCustom(PostMessageCondition postMessageCondition, int num){
		return  AutoEvaluationUtil.toClassList(postMessageCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param postMessageCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<PostMessage> listCustom(PostMessageCondition postMessageCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(postMessageCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param postMessage
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(PostMessage postMessage,HttpServletRequest request) throws Exception{
		this.checkCommon(postMessage, request);
		postMessage = this.create(postMessage);
        this.startPost(postMessage);
		Map<String, Object> map = new HashMap<>();
		map.put("id", postMessage.getId());
		return map;
	}


	/**
	 * 检测
	 * @param postMessage
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void checkCommon(PostMessage postMessage,HttpServletRequest request) throws Exception{
		String pushTimeStr = postMessage.getPushTimeStr();
		if(StringUtils.isNotBlank(pushTimeStr)){
			Date pushTime = DateUtil.strDateToDate(pushTimeStr, 2);
			if(DateUtil.millisecondsBetween(new Date(), pushTime) < 0){
				throw new Exception("推送时间已过");
			}
			postMessage.setPushTime(pushTime);
		}
		String roleTypes = postMessage.getReceiveRoleTypes();
		if(roleTypes.contains(PublicEnum.RoleType.All.getType())){
			roleTypes = PublicEnum.RoleType.All.getType();
		}
		postMessage.setReceiveRoleTypes(roleTypes);
		User user = (User)request.getSession().getAttribute("_user");
		user = userService.get(user.getId());
		if(user != null){
			String roleId = user.getRoleId();
			if( ! roleId.equals(QingpuConstants.Roles_Manager_id) && ! roleId.equals(QingpuConstants.Roles_Super_id)){
				throw new Exception("非管理员不能推送公告");
			}
			postMessage.setUserId(user.getId());
			postMessage.setUserName(user.getName());
			postMessage.setType(PublicEnum.PostMessageType.Admin);
		}
	}

	/**
	 * 根据参数更新
	 * @param paramsPostMessage
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(PostMessage paramsPostMessage,HttpServletRequest request) throws Exception{
		PostMessage postMessage = this.get(paramsPostMessage.getId());
		if(postMessage == null){
			throw new Exception("未找到对象");
		}
		this.checkCommon(paramsPostMessage, request);
		postMessage.setPushTime(paramsPostMessage.getPushTime());
		postMessage.setTitle(paramsPostMessage.getTitle());
		postMessage.setContent(paramsPostMessage.getContent());
		postMessage.setReceiveRoleTypes(paramsPostMessage.getReceiveRoleTypes());
		this.update(postMessage);
		this.startPost(postMessage);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(Long id, HttpServletRequest request) throws Exception{
		PostMessage postMessage = this.get(id);
		if(postMessage == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			postMessage.setOperatorId(user.getId());
		}
		this.delete(postMessage);
	}


	/**
	 * 创建通知消息，默认是删除，等用户点击按钮后触发非删除
	 * @param title
	 * @param content
	 * @param userId
	 * @param userName
	 * @param type
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void create(String title, String content, Integer userId, String userName, PublicEnum.PostMessageType type, Date pushTime) throws Exception{
		PostMessage postMessage = new PostMessage();
		postMessage.setTitle(title);
		postMessage.setContent(content);
		postMessage.setUserId(userId);
		postMessage.setPushTime(pushTime);
		postMessage.setUserName(userName);
		postMessage.setType(type);
		postMessage.setIsDel(true);
		postMessage.setReceiveRoleTypes(PublicEnum.RoleType.All.getType());
//		this.create(postMessage);
		this.startPost(postMessage);
	}


    /**
     * 开始推送消息
     * @param postMessage
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void timeToPost(PostMessage postMessage) throws Exception{
        postMessage = this.get(postMessage.getId());
        if(postMessage != null){
            String roleTypes = postMessage.getReceiveRoleTypes();
            List<String> roleTypeList = StringUtil.splitByComma(roleTypes);
            List<String> roleIdList = new ArrayList<>();
            for(String roleTypeStr : roleTypeList){
                if(roleTypeStr.equals(PublicEnum.RoleType.CS.getType())){
                    roleIdList.add(QingpuConstants.Roles_Employee_id);
                }else if(roleTypeStr.equals(PublicEnum.RoleType.Interview.getType())){
                    roleIdList.add(QingpuConstants.Roles_Interview_id);
                }else if(roleTypeStr.equals(PublicEnum.RoleType.Quality.getType())){
                    roleIdList.add(QingpuConstants.Roles_Quality_id);
                }else if(roleTypeStr.equals(PublicEnum.RoleType.Manager.getType())){
                    roleIdList.add(QingpuConstants.Roles_Manager_id);
                    roleIdList.add(QingpuConstants.Roles_Super_id);
                }
            }
            UserCondition userCondition = new UserCondition();
            userCondition.setRoleIdList(roleIdList);
            List<User> userList = userService.list(userCondition);
            WebSocketData.WebSocketWant want = WebSocketData.WebSocketWant.ShowPostMessage;
            Map<String, Object> map = AutoEvaluationUtil.domainToMap(postMessage);
            for(User item : userList){
                String userId = item.getId() + "";
                if(WebSocketService.onLineClient.containsKey(userId)){
                    Session session = WebSocketService.onLineClient.get(userId);
                    try {
                        webSocketService.sendMessage(session, new WebSocketData(want, map));
                    }catch (Exception e){
                        logger.error("公告推送失败： " + e.getMessage());
                    }
                }
            }
            postMessage.setIsPush(true);
            this.update(postMessage);
        }
    }


	/**
	 * 创建推送公告
	 * @param postMessage
	 */
	@Transactional(rollbackFor = Exception.class)
	public void startPost(PostMessage postMessage) throws Exception{
		if( ! postMessage.getIsPush() && ! postMessage.getIsDel()){
			Date pushTime = postMessage.getPushTime();
			if(pushTime == null){
				postMessage.setPushTime(new Date());
			    this.timeToPost(postMessage);
			}else{
                new Timer(true).schedule(new PostMessageThread(this, postMessage), postMessage.getPushTime());
            }
		}
	}

	public void init(){
	    PostMessageCondition postMessageCondition = new PostMessageCondition();
	    postMessageCondition.setbDel(false);
	    postMessageCondition.setPush(false);
	    List<PostMessage> postMessageList = this.list(postMessageCondition);
	    for(PostMessage postMessage : postMessageList){
	        try {
                if(postMessage.getPushTime() != null){
                    new Timer(true).schedule(new PostMessageThread(this, postMessage), postMessage.getPushTime());
                }else{
                    postMessage.setIsPush(true);
                    this.update(postMessage);
                }
            }catch (Exception e){
	            logger.info("初始化公告推送失败： " + e.getMessage());
            }
        }
    }


    public class PostMessageThread extends TimerTask {
        PostMessageService postMessageService;
        PostMessage postMessage;

        PostMessageThread(PostMessageService postMessageService, PostMessage postMessage) {
            this.postMessageService = postMessageService;
            this.postMessage = postMessage;
        }

        public void run(){
            try {
                logger.info("定时推送公告");
                postMessageService.timeToPost(postMessage);
            }catch(Exception e1){
                logger.error("定时推送公告失败： " + e1.getMessage());
            }
        }
    }
}
