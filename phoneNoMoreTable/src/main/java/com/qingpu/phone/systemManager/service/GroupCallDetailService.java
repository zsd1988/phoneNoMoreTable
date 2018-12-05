package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.TableParams;
import com.qingpu.phone.common.utils.*;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.GroupCallDetailCondition;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.security.MD5Encoder;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Service("groupCallDetailService")
@Transactional
public class GroupCallDetailService {
	private static Logger logger = Logger.getLogger(GroupCallDetailService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private GroupCallService groupCallService;

	@Resource
	private ClientService clientService;

	/**
	 * 创建
	 * @param groupCallDetail
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public GroupCallDetail create(GroupCallDetail groupCallDetail) throws Exception{
		if(groupCallDetail != null && StringUtils.isBlank(groupCallDetail.getId())){
			groupCallDetail.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(groupCallDetail);
		return (GroupCallDetail) baseDao.save(groupCallDetail);
	}


	/**
	 * 更新
	 * @param groupCallDetail
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(GroupCallDetail groupCallDetail)  throws Exception {
		CommonUtils.checkEntity(groupCallDetail);
		baseDao.update(groupCallDetail);
	}


	/**
	 * 删除
	 * @param groupCallDetail
	 */
	public void delete(GroupCallDetail groupCallDetail) {
		baseDao.delete(groupCallDetail);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public GroupCallDetail get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (GroupCallDetail)baseDao.get(GroupCallDetail.class, id);
	}


	/**
	 * 按条件查找
	 * @param groupCallDetailCondition
	 * @return
	 */
	public List<GroupCallDetail> list(GroupCallDetailCondition groupCallDetailCondition){
		return  groupCallDetailCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param groupCallDetailCondition
	 * @param sorter
	 * @return
	 */
	public List<GroupCallDetail> list(GroupCallDetailCondition groupCallDetailCondition, Sorter sorter){
		return  groupCallDetailCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param groupCallDetailCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<GroupCallDetail> list(GroupCallDetailCondition groupCallDetailCondition, Range range, Sorter sorter){
		PaginationSupport<GroupCallDetail> testTablePaginationSupport = (PaginationSupport<GroupCallDetail>) groupCallDetailCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param groupCallDetailCondition
	 * @return
	 */
	public Long countByCondition(GroupCallDetailCondition groupCallDetailCondition){
		return   groupCallDetailCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param groupCallDetailCondition
	 * @return
	 */
	public List<Object[]> listCustom(GroupCallDetailCondition groupCallDetailCondition){
		return  groupCallDetailCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param groupCallDetailCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(GroupCallDetailCondition groupCallDetailCondition, int num){
		return  groupCallDetailCondition.list(sessionFactory, num);
	}

	public List<GroupCallDetail> listCustom(GroupCallDetailCondition groupCallDetailCondition,Sorter sorter,  int num){
		return  AutoEvaluationUtil.toClassList(groupCallDetailCondition.list(sessionFactory, sorter, num));
	}


    /**
     * 初始化群呼明细，将呼叫中的设置为呼叫失败
     * @throws Exception
     */
    public void initGroupCallDetail(){
        try {
            GroupCallDetailCondition groupCallDetailCondition = new GroupCallDetailCondition();
            groupCallDetailCondition.setInStatus(new PublicEnum.GroupCallDetailStatus[]{PublicEnum.GroupCallDetailStatus.Calling,
				PublicEnum.GroupCallDetailStatus.Success});
            List<GroupCallDetail> groupCallDetailList = this.list(groupCallDetailCondition);
            for(GroupCallDetail groupCallDetail : groupCallDetailList){
                groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
                this.update(groupCallDetail);
            }

            groupCallDetailCondition = new GroupCallDetailCondition();
            groupCallDetailCondition.setStatus(PublicEnum.GroupCallDetailStatus.ResetWaiting);
            groupCallDetailList = this.list(groupCallDetailCondition);
			for(GroupCallDetail groupCallDetail : groupCallDetailList){
				groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Waiting);
				this.update(groupCallDetail);
			}
        }catch (Exception e){
            logger.error("初始化群呼明细失败" + e.getMessage());
        }
    }

	/**
	 * 根据参数创建
	 * @param groupCallDetail
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(GroupCallDetail groupCallDetail) throws Exception{
		groupCallDetail = this.create(groupCallDetail);
		Map<String, Object> map = new HashMap<>();
		map.put("id", groupCallDetail.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsGroupCallDetail
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Integer> importGroupCallDetail(GroupCallDetail paramsGroupCallDetail) throws Exception{
		String projectId = paramsGroupCallDetail.getProjectId();
		if(StringUtils.isBlank(projectId)){
			throw new Exception("请传入项目");
		}
		String groupCallId = paramsGroupCallDetail.getGroupCallId();
		if(StringUtils.isBlank(groupCallId)){
			throw new Exception("请传入群呼");
		}
		PublicEnum.ImportTag importTag = paramsGroupCallDetail.getImportTag();
		if(importTag == null){
			throw new Exception("请选择导入类别");
		}
		Integer num = paramsGroupCallDetail.getCountNum();
		if(num == null){
			throw new Exception("请选择导入数量");
		}
		String key = EncryptUtils.encryptByMD5(projectId + importTag);
		if(CallPhoneListener._currentImportKey.contains(key)){
			throw new Exception("当前已有相同项目的导入类别");
		}else{
			CallPhoneListener._currentImportKey.add(key);
		}
		System.out.println(groupCallId + " 开始获取：" + new Date());
		String strSql = "select * from client " +
				"where project_id = \"" + projectId + "\" and import_tag = \"" + importTag + "\" and is_distribute = 0  limit " + num;

		@SuppressWarnings("unchecked")
		List<Client> clientList = sessionFactory.getCurrentSession().createSQLQuery(strSql).addEntity(Client.class).list();
        Map<String, Integer> map = new HashMap<>();
        map.put("count", clientList.size());

		System.out.println(groupCallId + " 开始读取：" + new Date());
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
		String nowStr = DateUtil.dateToString(new Date(), 2);
		// 获取当前工作薄的每一行，从第2行开始
		String lastId = "";
		Integer i = 0;
		for(Client client : clientList){
			String id = UUIDGenerator.getUUID();
			if( i == clientList.size() - 1){
				lastId = id;
			}
			String inputStr = id + "##&&" + client.getId() + "##&&" + nowStr  + "##&&\\N##&&\\N##&&\\N##&&" + groupCallId
					+ "##&&0##&&" + client.getPhone() + "##&&\\N##&&" + client.getProjectId() + "##&&\\N##&&" + PublicEnum.GroupCallDetailStatus.Waiting +
					"##&&\\N##&&\\N##&&\\N##&&\\N##&&\\N##&&\\N##&&"  + PublicEnum.VoiceType.CS + "##&&\\N##&&\\0##&&\\N##&&\\N##&&\\N&&&###";
			writer.write(inputStr);
			client.setIsDistribute(true);
			clientService.update(client);
			i++;
		}
		writer.flush();
		writer.close();
		System.out.println(groupCallId + " 创建文件结束：" + new Date());

		PreparedStatement statement = conn.prepareStatement(
				"LOAD DATA INFILE '" + filepath + "' IGNORE into table group_call_detail  fields terminated by '##&&' lines terminated by '&&&###'");
		statement.execute("SET NAMES UTF8");
		statement.execute();
		statement.close();
		conn.close();

		if(StringUtils.isNotBlank(lastId)){
			CheckImportFinishThread checkImportFinishThread = new CheckImportFinishThread(this, groupCallId, key, lastId);
			checkImportFinishThread.start();
		}else{
			CallPhoneListener._currentImportKey.remove(key);
		}
		System.out.println(groupCallId + " 导入结束：" + new Date());
		return map;
	}

	class CheckImportFinishThread extends Thread{
		GroupCallDetailService _groupCallDetailService;
		String _groupCallId;
		Boolean _isFinish = false;
		String _key;
		Integer _count = 0;
		String _groupCallDetailId;

		private CheckImportFinishThread(GroupCallDetailService groupCallDetailService, String id, String key, String lastGroupCallDetailId){
			_groupCallDetailService = groupCallDetailService;
			_groupCallId = id;
			_key = key;
			_groupCallDetailId = lastGroupCallDetailId;
		}

		public void run(){
			while ( !_isFinish){
				try{
					GroupCallDetail groupCallDetail = _groupCallDetailService.get(_groupCallDetailId);
					if(groupCallDetail != null || _count > 600){
						// 如果半小时没有导入完成自动释放
						_isFinish = true;
						CallPhoneListener._currentImportKey.remove(_key);
						System.out.println(_groupCallId + " 结束导入：" + new Date());
					}
					System.out.println("查询 " + _groupCallId);
					Thread.currentThread().sleep(3000);//毫秒
					this._count++;
				}catch (Exception e){
					System.out.println("检测是否有导入群呼任务失败： " + e.getMessage());
				}
			}
		}
	}


	/**
	 * 转人工失败转成未呼叫
	 * @param groupCallId
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void allToWaiting(String groupCallId) throws Exception{
		if(StringUtils.isNotBlank(groupCallId)){
			GroupCallDetailCondition groupCallDetailCondition = new GroupCallDetailCondition();
			groupCallDetailCondition.setGroupCallId(groupCallId);
			groupCallDetailCondition.setStatus(PublicEnum.GroupCallDetailStatus.TransFail);
			List<GroupCallDetail> groupCallDetailList = this.list(groupCallDetailCondition);
			for(GroupCallDetail groupCallDetail : groupCallDetailList){
				groupCallDetail.setStatus(PublicEnum.GroupCallDetailStatus.Waiting);
				this.update(groupCallDetail);
			}
		}
	}


	/**
	 * 获取将要拨打的群呼电话
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public GroupCallDetail getCallPhone(){
		try {
			Integer groupCallNum = CallPhoneListener._callPhoneListMap.size();	//同时启动的群呼任务数量
			String groupCallId = null;
			if(groupCallNum == 0){
				return null;
			}
			//判断并发数量
			String ignoreGroupCallId = "";
			Boolean isBeyond = false;
			for(String key : CallPhoneListener._groupCallConNumMap.keySet()){
				if(ignoreGroupCallId.contains(key)){
					continue;
				}
				Integer total = CallPhoneListener._groupCallConNumMap.get(key);
				List<String> portIdList = CallPhoneListener._groupCallPortIdMap.get(key);
				Integer portIdListSize = 0;
				if(portIdList != null){
					portIdListSize = portIdList.size();
				}
				if(portIdListSize + 1 > total){
					ignoreGroupCallId += key + ",";
					isBeyond = true;
				}
			}
			if(groupCallNum == 1){
				for(String key : CallPhoneListener._callPhoneListMap.keySet()){
					groupCallId = key;
				}
			}else{
				// 超出变为没超出并发时，清空_callPhoneNum数量
				if( ! isBeyond && CallPhoneListener._isHaveGroupCalleyond){
					CallPhoneListener._isHaveGroupCalleyond = false;
					for(String key : CallPhoneListener._callPhoneNum.keySet()){
						CallPhoneListener._callPhoneNum.put(key, 0);
					}
				}else{
					CallPhoneListener._isHaveGroupCalleyond = isBeyond;
				}

				// 按照群呼任务的客服管哪个项目的配置比率，从相应项目中选择电话
				Integer totalCalledPhoneNum = 0;
				String firstGroupCallId = null;
				for(String key : CallPhoneListener._callPhoneNum.keySet()){
					totalCalledPhoneNum += CallPhoneListener._callPhoneNum.get(key);
					if(firstGroupCallId == null &&  ! ignoreGroupCallId.contains(key)){
						firstGroupCallId = key;
					}
				}
				if(totalCalledPhoneNum != 0){
					Boolean isStartCompare = false;
					for(String key : CallPhoneListener._callPhoneNum.keySet()){
						if(CallPhoneListener._lastRateGroupCallId.equals("") || CallPhoneListener._lastRateGroupCallId.equals(key)){
							isStartCompare = true;
						}
						if( isStartCompare && ! ignoreGroupCallId.contains(key) && ! CallPhoneListener._lastRateGroupCallId.equals(key)){
							Integer num = CallPhoneListener._callPhoneNum.get(key);
							Double rate = CallPhoneListener._callPhoneRate.get(key);
							if( StringUtils.isNotBlank(ignoreGroupCallId) || num.doubleValue()*1.0/totalCalledPhoneNum <= rate ){
								// 当有群呼超出并发数时，不用判断是否超出倍率
								groupCallId = key;
								CallPhoneListener._lastRateGroupCallId = key;
								break;
							}
						}
					}
				}else{
					groupCallId = firstGroupCallId;
				}
				if(groupCallId == null){
					// 去除最后一次的限制
					CallPhoneListener._lastRateGroupCallId = "";
					CallPhoneListener._getDetailNullCount++;
					// 如果连续获取空的明细，则清空_callPhoneNum数量
					if(CallPhoneListener._getDetailNullCount > 500 && StringUtils.isBlank(ignoreGroupCallId)){
						for(String key : CallPhoneListener._callPhoneNum.keySet()){
						    if(CallPhoneListener._callPhoneNum.get(key) != 0){
                                logger.info(key + " 概率死循环，重置呼叫总数： " +  CallPhoneListener._callPhoneRate.get(key));
                                CallPhoneListener._callPhoneNum.put(key, 0);
                            }else{
						        break;
                            }
						}
					}
					return null;
				}
				CallPhoneListener._getDetailNullCount = 0;
			}
			// 超出并发量
			if(ignoreGroupCallId.contains(groupCallId)){
				if(CallPhoneListener._doCount == 1){
					System.out.println("群呼忽略：" + groupCallId);
				}
				return null;
			}
			List<GroupCallDetail> groupCallDetailList = CallPhoneListener._callPhoneListMap.get(groupCallId);
			if(groupCallDetailList == null){
				groupCallDetailList = new ArrayList<>();
			}
			Integer phoneListLength = groupCallDetailList.size();
			if(phoneListLength == 0){
				GroupCallDetailCondition groupCallDetailCondition = new GroupCallDetailCondition();
				groupCallDetailCondition.setStatus(PublicEnum.GroupCallDetailStatus.Waiting);
				groupCallDetailCondition.setGroupCallId(groupCallId);
				groupCallDetailList = this.listCustom(groupCallDetailCondition, new Sorter().asc("create_time"), 1000);
				phoneListLength = groupCallDetailList.size();
				CallPhoneListener._callPhoneListMap.put(groupCallId, groupCallDetailList);
			}
			if(phoneListLength == 0){
				// 群呼详细表已拨打完
				logger.info("号码拨打完毕，自动结束群呼：" + groupCallId);
                groupCallService.setIsEnable(groupCallId, false);
				return null;
			}
			GroupCallDetail groupCallDetail = groupCallDetailList.get(0);
			// 查看同一手机号码是否被同时拨打
			String phone = groupCallDetail.getPhone();
			if(CallPhoneListener._currentPhoneList.contains(phone)){
			    groupCallDetail = null;
			    for(GroupCallDetail groupCallDetailTemp : groupCallDetailList){
			        if( ! CallPhoneListener._currentPhoneList.contains(groupCallDetailTemp.getPhone())){
			            groupCallDetail = groupCallDetailTemp;
			            break;
                    }
                }
            }
            if(groupCallDetail == null){
				// 碰到groupCallDetailList都是重复号码，清空缓存，重新获取号码
				groupCallDetailList.clear();
				CallPhoneListener._callPhoneListMap.put(groupCallId, groupCallDetailList);
			    return null;
            }
			groupCallDetailList.remove(groupCallDetail);
			return groupCallDetail;
		}catch (Exception e){
			logger.error("群呼获取手机号失败：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 统计端口接通率
	 * @return
	 * @throws Exception
	 */
	public List<GroupCallDetail> portDetailRate(TableParams tableParams){
		String startTime = tableParams.getStartTimeStr();
		String endTime = tableParams.getEndTimeStr();
		String condition ="";
		if(StringUtils.isNotBlank(startTime)){condition += " and start_time >= str_to_date('" + startTime + "', '%Y-%m-%d %T') " ;}
		if(StringUtils.isNotBlank(endTime)){condition += " and start_time <= str_to_date('" + endTime + "', '%Y-%m-%d %T') " ;}
		String devName = tableParams.getName();
		if(StringUtils.isNotBlank(devName)){
			condition += " and port_id like \"" + devName + "-%\" ";
		}
		String strSql=
				" select '总计' as port_id," +
						" sum(case status when 'Waiting' then 0 else 1 end) as sumTotal," +
						" sum(case status when 'Success' then 1 when 'TransSuccess' then 1 when 'Refuse' then 1 when 'TransFail' then 1 else 0 end) sumSuccess," +
						" sum(case status when 'Waiting' then 0 else 1 end)-sum(case status when 'Success' then 1 when 'TransSuccess' then 1 when 'Refuse' then 1 when 'TransFail' then 1 else 0 end) sumFail," +
						" CONCAT(" +
						"  format(" +
						"  sum(case status when 'Success' then 1 when 'TransSuccess' then 1 when 'Refuse' then 1 when 'TransFail' then 1 else 0 end)*100/" +
						"  sum(case status when 'Waiting' then 0 else 1 end),2),'%') portRate" +
						" from group_call_detail" +
						" where 1=1 " + condition +
				" UNION "+
						"select port_id," +
						" sum(case status when 'Waiting' then 0 else 1 end) as sumTotal," +
						" sum(case status when 'Success' then 1 when 'TransSuccess' then 1 when 'Refuse' then 1 when 'TransFail' then 1 else 0 end) sumSuccess," +
						" sum(case status when 'Waiting' then 0 else 1 end)-sum(case status when 'Success' then 1 when 'TransSuccess' then 1 when 'Refuse' then 1 when 'TransFail' then 1 else 0 end) sumFail," +
						" CONCAT(" +
						"  format(" +
						"  sum(case status when 'Success' then 1 when 'TransSuccess' then 1 when 'Refuse' then 1 when 'TransFail' then 1 else 0 end)*100/" +
						"  sum(case status when 'Waiting' then 0 else 1 end),2),'%') portRate" +
						" from group_call_detail" +
						" where 1=1 " + condition +
						" group by port_id " ;
		@SuppressWarnings("unchecked")
		//List<Object[]> list1 =sessionFactory.getCurrentSession().createSQLQuery(strSql).list(); //java entity申明类型与sql返回类型必须匹配才可用
		List<GroupCallDetail> list = sessionFactory.getCurrentSession().createSQLQuery(strSql).setResultTransformer(Transformers.aliasToBean(GroupCallDetail.class)).list();
		return list;
	}

}