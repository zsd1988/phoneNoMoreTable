package com.qingpu.phone.common.controller;

import com.qingpu.phone.clientManager.condition.ClientCondition;
import com.qingpu.phone.clientManager.entity.Client;
import com.qingpu.phone.clientManager.service.ClientGroupService;
import com.qingpu.phone.clientManager.service.ClientService;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.systemManager.service.ProjectService;
import com.qingpu.phone.systemSetting.service.PortService;
import com.qingpu.phone.user.service.UserGroupService;
import com.qingpu.phone.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//错误处理controller
@Controller
@RequestMapping("/service/platform/noLogin/common/publicEnum")
public class PublicEnumController extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(PublicEnumController.class);

	@Resource
	ProjectService projectService;

	@Resource
	PortService portService;

	@Resource
	ClientGroupService clientGroupService;

    @Resource
    UserGroupService userGroupService;

	@Resource
	ClientService clientService;

	/**
	 * 获取列表
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getMap")
	public void getMap(String params, HttpServletResponse response) throws IOException {
		try{
			List<String> nameList = StringUtil.splitByComma(params);
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> resultMap = new HashMap<>();
			for(String name : nameList){
				switch (name){
					case "Project":
						map.put(name, projectService.getSelect());
						break;
					case "ClientGroupNameYf":	// 用于转移客户，分组名称+对应约访员工姓名
						map.put(name, clientGroupService.getSelectAddWorkerName());
						break;
                    case "CSGroup":	// 客服组
                        resultMap = userGroupService.getCSSelect();
                        map.put(name, resultMap.get("select"));
                        map.put(name + "User", resultMap.get("userList"));
                        break;
					case "UserGroup":	// 客服组
						resultMap = userGroupService.getCSAndInterviewSelect();
						map.put(name, resultMap.get("select"));
						map.put(name + "User", resultMap.get("userList"));
						break;
					case "PortDev":
						map.put(name, portService.getSelect());
						break;
					default:
						map.put(name, PublicEnum.getEnumKeyValueString(name));
						break;
				}
			}
			JsonRetInfo.returnSuccess(response, "操作成功", map);
		}catch (Exception e){
			JsonRetInfo.returnFail(response, "操作失败：" + e.getMessage());
		}
	}
}
