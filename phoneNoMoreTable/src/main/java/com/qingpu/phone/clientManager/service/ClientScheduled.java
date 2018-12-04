package com.qingpu.phone.clientManager.service;

import com.qingpu.phone.clientManager.condition.ClientCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.JsonUtil;
import com.qingpu.phone.common.utils.LinuxUtil;
import com.qingpu.phone.systemManager.condition.CallRecordCondition;
import com.qingpu.phone.systemManager.condition.GroupCallDetailCondition;
import com.qingpu.phone.systemManager.entity.CallRecord;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;
import com.qingpu.phone.systemManager.service.CallRecordService;
import com.qingpu.phone.systemManager.service.GroupCallDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 客户每日检测移至公海
 */
@Component
public class ClientScheduled {

	private static final Logger logger = LoggerFactory.getLogger(ClientScheduled.class);
	
	@Autowired
	ClientService clientService;

	@Autowired
	GroupCallDetailService groupCallDetailService;

	@Autowired
	CallRecordService callRecordService;
	
	//每天凌晨一点执行将很久没更新的约访客户资源移至公海资源
	@Scheduled(cron = "0 0 1 * * *")//秒、分、时、日、月、年
	public void clientToPublicInterview() {
		Date now = new Date();
		ClientCondition clientCondition = new ClientCondition();
		clientCondition.setLtLastUpdateTime(DateUtil.add(now, DateUtil.ADDMONTH, -2));
		clientCondition.setNotNullGroupId(true);
		clientCondition.setNotNullGroupType(true);
		clientCondition.setNeClientGroupType(PublicEnum.ClientGroupType.Visiting);
		List<String> groupIdList = new ArrayList<>();
		groupIdList.add("0");
		groupIdList.add("1");
		clientCondition.setNeGroupIdList(groupIdList);
		List<Client> clientList = clientService.list(clientCondition);
		this.clientToPublicInterview(clientList);

		clientCondition = new ClientCondition();
		clientCondition.setNullGroupId(true);
		clientCondition.setOnceStatus(true);
        clientCondition.setLtLastUpdateTime(DateUtil.add(now, DateUtil.ADDMONTH, -2));
		clientCondition.setReviewStatusArr(new PublicEnum.ClientStatus[]{PublicEnum.ClientStatus.A, PublicEnum.ClientStatus.B, PublicEnum.ClientStatus.C});
		clientList = clientService.list(clientCondition);
		this.clientToPublicInterview(clientList);

		// 清除三个月前群呼明细CallNoAnswer的客户数据
		Date lastCreateTime = DateUtil.add(now, DateUtil.ADDMONTH, -3);
		GroupCallDetailCondition groupCallDetailCondition = new GroupCallDetailCondition();
		groupCallDetailCondition.setLtCreateTime(lastCreateTime);
		groupCallDetailCondition.setStatus(PublicEnum.GroupCallDetailStatus.CallNoAnswer);
		List<GroupCallDetail> groupCallDetailList = groupCallDetailService.list(groupCallDetailCondition);
		logger.info("-----------------------------------------每天凌晨一点清除三个月前群呼明细CallNoAnswer的客户数据： " + groupCallDetailList.size());
		for(GroupCallDetail groupCallDetail : groupCallDetailList){
			String clientId = groupCallDetail.getClientId();
			Client client = clientService.get(clientId);
			if(client != null){
				CallRecordCondition callRecordCondition = new CallRecordCondition();
				callRecordCondition.setClientId(clientId);
				List<CallRecord> callRecordList = callRecordService.list(callRecordCondition);
				if(callRecordList.isEmpty()){
					groupCallDetailService.delete(groupCallDetail);
					logger.info(groupCallDetail.getId() + " 删除群呼记录： " + JsonUtil.getJsonStrFromEntity(groupCallDetail));
					clientService.deleteReally(client);
					logger.info(clientId + " 删除客户记录： " + JsonUtil.getJsonStrFromEntity(client));
				}
			}
		}
	}

	private void clientToPublicInterview(List<Client> clientList){
		logger.info("-----------------------------------------每天凌晨一点执行将很久没更新的约访客户资源移至公海资源： " + clientList.size());
		for(Client client : clientList){
			client.setGroupId("1");
			client.setGroupType(null);
			client.setConfirmStatus(null);
			try{
				clientService.update(client);
			}catch (Exception e){
				logger.error("更新客户为公海资源失败：" + e.getMessage());
			}
		}
	}


	/**
	 * 重启tomcat
	 */
	@Scheduled(cron = "0 40 23 * * *")//秒、分、时、日、月、年
	public void restartTomcat() {
		String os = System.getProperty("os.name");
		if( ! os.toLowerCase().startsWith("win")){
			try {
				List<String> commandList = new ArrayList<>();

				logger.info("----------------------------------------轮转tomcat日志");
				String command="/usr/sbin/logrotate -vf /etc/logrotate.conf";
				commandList.add(command);

				logger.info("----------------------------------------清理");
				command="find /mnt/java/ -mtime +5 -name \"*log_20*\" -exec rm -rf {} \\;";
				commandList.add(command);

				command="find /mnt/apache/logs/ -mtime +4 -name \"*20*\" -exec rm -rf {} \\;";
                commandList.add(command);


				logger.info("----------------------------------------重启");
				command="/mnt/apache/bin/shutdown.sh";
                commandList.add(command);

				command="pkill -9 java";
				commandList.add(command);

				command="/mnt/apache/bin/startup.sh";
                commandList.add(command);
                LinuxUtil.executeNewFlow(commandList);
			}catch (Exception e){
				logger.error("重启tomcat失败：" + e.getMessage());
			}
		}
	}

}
