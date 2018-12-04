package com.qingpu.phone.user.service;

import com.qingpu.phone.clientManager.condition.ClientCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.ArithUtil;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.LinuxUtil;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.IntentionHintCondition;
import com.qingpu.phone.systemManager.condition.LuckDrawCondition;
import com.qingpu.phone.systemManager.entity.IntentionHint;
import com.qingpu.phone.systemManager.entity.LuckDraw;
import com.qingpu.phone.systemManager.entity.PostMessage;
import com.qingpu.phone.systemManager.service.IntentionHintService;
import com.qingpu.phone.systemManager.service.LuckDrawService;
import com.qingpu.phone.systemSetting.service.ParameterService;
import com.qingpu.phone.user.condition.JiangHuPaiCondition;
import com.qingpu.phone.user.condition.JiangHuRenCondition;
import com.qingpu.phone.user.condition.UserCondition;
import com.qingpu.phone.user.condition.UserInfoCondition;
import com.qingpu.phone.user.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 用户日常
 */
@Component
public class UserScheduled {

	private static final Logger logger = LoggerFactory.getLogger(UserScheduled.class);

	@Autowired
	UserService userService;

	/**
	 * 清空抽奖次数
	 */
	@Scheduled(cron = "0 0 5 * * *")//秒、分、时、日、月、年
	public void resetUser() {
	    userService.dayInit();
	}
}
