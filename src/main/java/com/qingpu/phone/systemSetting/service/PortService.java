package com.qingpu.phone.systemSetting.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemSetting.condition.PortCondition;
import com.qingpu.phone.systemSetting.entity.Port;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Call;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("portService")
@Transactional
public class PortService {
	private static Logger logger = Logger.getLogger(PortService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private CallRecordService callRecordService;

	/**
	 * 创建
	 * @param port
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Port create(Port port) throws Exception{
		if(port != null && StringUtils.isBlank(port.getId())){
			port.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(port);
		return (Port) baseDao.save(port);
	}


	/**
	 * 更新
	 * @param port
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Port port)  throws Exception {
		CommonUtils.checkEntity(port);
		baseDao.update(port);
	}


	/**
	 * 删除
	 * @param port
	 */
	public void delete(Port port) {
		port.setIsDel(true);
		port.setDelTime(new Date());
		baseDao.update(port);
		String portId = port.getId();
		if( CallPhoneListener._idlePortIdList.contains(portId)){
			CallPhoneListener._idlePortIdList.remove(portId);
			CallPhoneListener._idlePortMap.remove(portId);
		}
		if( CallPhoneListener._incallingPort.containsKey(portId)){
			Port port1 = CallPhoneListener._incallingPort.get(portId);
			port1.setIsDel(true);
			port1.setDelTime(new Date());
			CallPhoneListener._incallingPort.put(portId, port1);
		}
		logger.info("禁用端口： " + portId);
	}
	/**
	 * 启用
	 * @param port
	 */
	public void enable(Port port) {
		port.setIsDel(false);
		port.setDelTime(new Date());
		baseDao.update(port);
		String portId = port.getId();
		if(StringUtils.isNotBlank(portId) && ! CallPhoneListener._idlePortIdList.contains(portId)){
			CallPhoneListener._idlePortMap.put(portId, port);
			CallPhoneListener._idlePortIdList.add(portId);
		}else{
			logger.info("启用了空端口");
		}
		if( CallPhoneListener._incallingPort.containsKey(portId)){
			Port port1 = CallPhoneListener._incallingPort.get(portId);
			port1.setIsDel(false);
			port1.setDelTime(new Date());
			CallPhoneListener._incallingPort.put(portId, port1);
		}
		logger.info("启用端口： " + portId);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Port get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (Port)baseDao.get(Port.class, id);
	}


	/**
	 * 按条件查找
	 * @param portCondition
	 * @return
	 */
	public List<Port> list(PortCondition portCondition){
		return  portCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param portCondition
	 * @param sorter
	 * @return
	 */
	public List<Port> list(PortCondition portCondition, Sorter sorter){
		return  portCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param portCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Port> list(PortCondition portCondition, Range range, Sorter sorter){
		PaginationSupport<Port> testTablePaginationSupport = (PaginationSupport<Port>) portCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param portCondition
	 * @return
	 */
	public Long countByCondition(PortCondition portCondition){
		return   portCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param portCondition
	 * @return
	 */
	public List<Object[]> listCustom(PortCondition portCondition){
		return  portCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param portCondition
	 * @param num
	 * @return
	 */
	public List<Port> listCustom(PortCondition portCondition, int num){
		return  portCondition.list(sessionFactory, num);
	}

	/**
	 * 根据参数创建
	 * @param port
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(Port port) throws Exception{
		port = this.create(port);
		Map<String, Object> map = new HashMap<>();
		map.put("id", port.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsPort
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(Port paramsPort) throws Exception{
		Port port = this.get(paramsPort.getId());
		if(port == null){
			throw new Exception("未找到对象");
		}
		this.update(port);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		Port port = this.get(id);
		if(port == null){
			throw new Exception("禁用失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			port.setOperatorId(user.getId());
		}
		this.delete(port);
	}

	/**
	 * 初始化端口
	 */
	public void init(){
		PortCondition portCondition = new PortCondition();
		List<Port> portList = this.list(portCondition, new Sorter().asc("id"));
		for(Port port : portList){
			String id = port.getId();
			port.setStatus(PublicEnum.PortStatus.Idle);
			if( ! port.getIsDel()){
				CallPhoneListener._idlePortMap.put(id, port);
				CallPhoneListener._idlePortIdList.add(id);
			}
			try {
				this.update(port);
			}catch (Exception e){
				logger.error("设置端口空闲失败：" + e.getMessage());
			}
		}
	}

	/**
	 * 获取表下拉选择框
	 * @return
	 */
	public List<Map<String, String>> getSelect(){
		PortCondition portCondition = new PortCondition();
		List<Port> portList = this.list(portCondition, new Sorter().asc("id"));
		List<Map<String, String>> mapList = new ArrayList<>();
		String devStr = "";
		for(Port port : portList){
			Map<String, String> map = new HashMap<>();
			String id = port.getId();
			String[] idArr = id.split("-");
			if(idArr.length > 0){
				String name = idArr[0];
				if( ! devStr.contains(name)){
					devStr += name + ",";
					map.put(name, name);
					mapList.add(map);
				}
			}
		}
		return mapList;
	}


	/**
	 * 获取空闲端口id
	 * @return
	 */
	public synchronized String getIdlePortId(Boolean isSip, String phone){
		String returnPortId = null;
		if(CallPhoneListener._idlePortIdList.contains(null)){
			CallPhoneListener._idlePortIdList.remove(null);
		}
		List<String> idlePortIdList = CallPhoneListener._idlePortIdList;
		if(idlePortIdList.size() > 0 ){
			if(isSip){
				// 优先获取sip开头的端口
				for(String portId : idlePortIdList){
					if(portId.startsWith("sipprovider")){
						returnPortId = portId;
						break;
					}
				}
			}
			Boolean isOutPhone = false;
			if(phone.startsWith("0")){
				isOutPhone = true;
			}
			if(returnPortId == null){
				Date now = new Date();
				// 按照端口属性选择端口
				String firstPortId = null;
//				returnPortId = idlePortIdList.get(0);
				int i = 0;
				int max = 5;	// 判断端口优先拨打的最大判断数量
				if(max > idlePortIdList.size()){
					max = idlePortIdList.size();
				}
				for(; i < max; i++){
					String portId = idlePortIdList.get(i);
					Port port = CallPhoneListener._idlePortMap.get(portId);
					if(port == null){
						returnPortId = portId;
						break;
					}
					if(CallPhoneListener._portIdEndTimeMap.containsKey(portId)){
						Date endTime = CallPhoneListener._portIdEndTimeMap.get(portId);
						long sub = DateUtil.millisecondsBetween(endTime, now);
						if(sub <= 2000){
							// 公司固话端口延时时间
						    continue;
						}
					}
                    if(firstPortId == null){
                        firstPortId = portId;
                    }
                    if(port.getIsFirst()){
                        if(isOutPhone){
                            if(port.getIsCallOut()){
                                returnPortId = portId;
                                break;
                            }
                        }else{
                            returnPortId = portId;
                            break;
                        }
                    }
				}
				if(returnPortId == null){
					returnPortId = firstPortId;
				}
				if(returnPortId == null){
					for(i = 0; i < idlePortIdList.size(); i++){
						String portId = idlePortIdList.get(i);
						Port port = CallPhoneListener._idlePortMap.get(portId);
						if(port == null){
							returnPortId = portId;
							break;
						}
						if(port.getIsCallOut()){
							returnPortId = portId;
							break;
						}
					}
					if(returnPortId == null){
						return null;
					}
				}
				Port port = CallPhoneListener._idlePortMap.get(returnPortId);
				if(port == null){
					port = this.get(returnPortId);
					Boolean isFail = false;
					if(port == null){
						isFail = true;
					}else if(port.getIsDel()){
						isFail = true;
					}
					if(isFail){
						CallPhoneListener._idlePortIdList.remove(returnPortId);
						logger.info("移除空端口： " + returnPortId);
						return getIdlePortId(isSip, phone);
					}else{
						CallPhoneListener._idlePortMap.put(returnPortId, port);
					}
				}
			}
			CallPhoneListener._idlePortIdList.remove(returnPortId);
		}
		return returnPortId;
	}

	/**
	 * 端口挂断
	 * @param portId
	 */
	public synchronized void setFinish(String portId, String phone){
		if(CallPhoneListener._incallingPort.containsKey(portId)){
			Port port = CallPhoneListener._incallingPort.get(portId);
			for(String key : CallPhoneListener._groupCallPortIdMap.keySet()){
				List<String> idList = CallPhoneListener._groupCallPortIdMap.get(key);
				if(idList.contains(portId)){
					CallPhoneListener._groupCallPortIdMap.get(key).remove(portId);
				}
			}
			CallPhoneListener._incallingPort.remove(portId);
			if(CallPhoneListener._currentPhoneList.contains(phone)){
				CallPhoneListener._currentPhoneList.remove(phone);
			}
			if( ! port.getIsDel() && ! CallPhoneListener._idlePortIdList.contains(portId)){
				if(StringUtils.isBlank(portId)){
					logger.error("加入了空的端口");
				}
				CallPhoneListener._idlePortMap.put(portId, port);
				if( ! port.getIsThird()){
					CallPhoneListener._portIdEndTimeMap.put(portId, new Date());
				}
				CallPhoneListener._idlePortIdList.add(portId);
			}else{
				logger.info(phone + "  " + portId + " 从呼叫端口加入空闲端口失败：" + port.getIsDel());
			}
		}else{
			logger.info(phone + " 在呼叫端口未找到端口： " + portId);
		}
	}

	/**
	 * 启动相应数量
	 * @param dev
	 * @param count
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void savePortNum(String dev, Integer count, Boolean isFirst, Boolean isThird, Boolean isCallOut) throws Exception{
		if(StringUtils.isBlank(dev)){
			throw new Exception("请传入设备名称");
		}
		if(count == null || count < 0 ){
			throw new Exception("传入数量非法");
		}
		// 更新基础属性
		PortCondition portCondition = new PortCondition();
		portCondition.setLikeId(dev + "_");
		List<Port> portList = this.list(portCondition, new Sorter().desc("id"));
		for(Port port : portList){
			port.setIsFirst(isFirst);
			port.setIsThird(isThird);
			port.setIsCallOut(isCallOut);
			this.update(port);
		}

		portCondition = new PortCondition();
		portCondition.setLikeId(dev + "-");
		portCondition.setbDel(false);
		portList = this.list(portCondition, new Sorter().desc("id"));
		Integer size = portList.size();
		if(size > count){
			// 删除端口
			for(int i = 0; i < size - count; i++){
				Port port = portList.get(i);
				this.delete(port);
			}
		}else if(size < count){
			// 增加端口
			portCondition = new PortCondition();
			portCondition.setLikeId(dev + "-");
			portCondition.setbDel(true);
			portList = this.listCustom(portCondition, count - size);
			for(Port port : portList){
				this.enable(port);
			}
		}
	}
}
