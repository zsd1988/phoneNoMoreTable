package com.qingpu.phone.listener;

import com.qingpu.phone.common.service.BdfsBinaryProviderService;
import com.qingpu.phone.systemManager.service.GroupCallDetailService;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.systemSetting.service.ParameterService;
import com.qingpu.phone.systemSetting.service.PortService;
import com.qingpu.phone.user.service.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("objectJDBCService")
public class ObjectJDBCService {

    private static Logger logger = Logger.getLogger(ObjectJDBCService.class);

    @Resource
    private UserService userService;

    @Resource
    private UserGroupService userGroupService;

    @Resource
    private RolesService rolesService;

    @Resource
    private PermissionsService permissionsService;

    @Resource
    private PortService portService;

    @Resource
    private GroupCallService groupCallService;

    @Resource
    private GroupCallDetailService groupCallDetailService;


    @Resource
    private FunctionPermissionsService functionPermissionsService;


    @Resource
    private BdfsBinaryProviderService bdfsBinaryProviderService;

    @Resource
    ParameterService parameterService;

    /**
     * 保存所有初始化数据
     */
    public void initSaveOrUpdateAllObject(){
        /*
        初始化用户表
         */
        userService.init();

        /*
         初始化角色表
         */
        rolesService.init();

        /*
        初始化权限表
         */
        permissionsService.init();


        /*
        用户分组
         */
        userGroupService.init();

        /*
        启动服务器设置所有群呼为停止
         */
        groupCallService.setAllDisable();

        /*
         * 初始化群呼明细，将呼叫中的设置为呼叫失败
         */
        groupCallDetailService.initGroupCallDetail();

        /*
         * 功能权限初始化
         */
        functionPermissionsService.init();

        /*
        初始化端口
         */
        portService.init();

        bdfsBinaryProviderService.init();

        /*
         参数表
         */
        parameterService.init();
    }
}
