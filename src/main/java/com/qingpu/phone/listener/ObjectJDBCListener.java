package com.qingpu.phone.listener;

import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemManager.service.GroupCallService;
import com.qingpu.phone.systemManager.service.PostMessageService;
import com.qingpu.phone.user.service.JiangHuRenService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.Timer;

@WebListener
public class ObjectJDBCListener implements ServletContextListener {

    private ObjectJDBCThread objectJDBCThread;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        System.out.println("objectJDBCService====================================");
        ObjectJDBCService objectJDBCService=(ObjectJDBCService) WebApplicationContextUtils.getWebApplicationContext(context).getBean("objectJDBCService");
        System.out.println("objectJDBCService="+objectJDBCService);
        objectJDBCThread=new ObjectJDBCThread(objectJDBCService);
        //启动主线程
        objectJDBCThread.start();

        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        PostMessageService postMessageService = webApplicationContext.getBean(PostMessageService.class);
        postMessageService.init();

        JiangHuRenService jiangHuRenService = webApplicationContext.getBean(JiangHuRenService.class);
        jiangHuRenService.initScheduled(true);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        /**
         * 停止定时关闭群呼的任务
         */
        GroupCallService groupCallService=(GroupCallService) WebApplicationContextUtils.getWebApplicationContext(context).getBean("groupCallService");
        groupCallService.stopTaskScheduler();
    }
}