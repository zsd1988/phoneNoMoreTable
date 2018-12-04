package com.qingpu.phone.constants;

import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.systemManager.entity.GroupCallDetail;

import java.util.*;

/**
 * 定义数据常量
 * */
public class QingpuConstants {
	
	public static final String URL = "www.phone.com";//www.ldhxtj.com

	public static String File_Server_Ip = "192.168.0.188";      // 资源服务器Ip地址或域名
	public static String File_Server_Port = "8080";             // 资源服务器端口

    public static Boolean _isSupportIP = true;      // 是否支持通过ip设置分机号

	public static String FILE_UPLOAD_URL ="http://" + QingpuConstants.File_Server_Ip + ":" + QingpuConstants.File_Server_Port + "/upload/";    //文件网络路径
	public static String FILE_UPLOAD_PATH ="\\\\" + QingpuConstants.File_Server_Ip + "\\upload\\";      //文件上传路径

	public static String uploadUrl = "http://192.168.1.195:8899/";

	public static String FILE_UPLOAD_PATH_IMG="/mnt/java/upload";      //图片文件上传路径
	public static String FILE_UPLOAD_PATH_VIDEO= "\\\\" + QingpuConstants.File_Server_Ip + "\\upload\\video\\";    //视频文件上传路径
	public static String FILE_UPLOAD_PATH_VOICE="\\\\" + QingpuConstants.File_Server_Ip + "\\upload\\voice\\";    //音频文件上传路径

	public static String FILE_UPLOAD_URL_IMG="http://" + QingpuConstants.File_Server_Ip + ":" + QingpuConstants.File_Server_Port + "/upload/img/";      //图片文件网络路径
	public static String FILE_UPLOAD_URL_VIDEO="http://" + QingpuConstants.File_Server_Ip + ":" + QingpuConstants.File_Server_Port + "/upload/video/";    //图片文件网络路径
	public static String FILE_UPLOAD_URL_VOICE="http://" + QingpuConstants.File_Server_Ip + ":" + QingpuConstants.File_Server_Port + "/upload/voice/";    //图片文件网络路径

	/**********************************表id*************************************/	/*
	资源表
	 */
	public static final String Source_Common_Folder_Id = "3de914bc662b4382969f922911657e9b";		// 超级管理员

	public static final int[] _luckDrawIntentionCountArr = new int[]{5,8,11};

	public static String[] _postName;	// 职位列表

	public static int[] _csIntention;		// 客服升级需要的金币

	public static int[] _interviewIntention;	// 约访升级需要的金币

	public static int[] _csGold;		// 客服金币等级

	public static int[] _interviewGold;	// 约访金币等级

	public static String[] _nickName;	// 昵称列表


	/*
	角色表
	 */
	public static final String Roles_Super_id = "2b9c6fa88e7b495e81deef4146f130b1";		// 超级管理员
	public static final String Roles_Manager_id = "f8029f72a77a5713b03cc820d0a0a8a3";		// 管理员
	public static final String Roles_Employee_id = "0fc02e24b67949dbb14843fe1916cb01";		// 客服
	public static final String Roles_Interview_id = "cd90132af26d44c3adace2f9270ee247";		// 约访
    public static final String Roles_Quality_id = "745e28237e8091d5ae8355b2e91077f1";		// 质检


	/*
	菜单表
	 */
	public static final String Permissions_Root_id = "root";		// 总菜单
	public static final String Permissions_AdminSystem_id = "a2c328e40c30426cb85e57acb6f4ed46";	// 超级管理员系统管理菜单不能设置为隐藏
	public static final String Permissions_ManagerProject_id = "0a150827b14442ada986ef83dcde1ad6";	// 超级管理员系统管理菜单不能设置为隐藏
	public static final String Permissions_EmployeeProject_id = "4961595af20d4451a7c25999dce171d2";	// 超级管理员系统管理菜单不能设置为隐藏
	public static final String Permissions_EmployeeFloor_id = "0d26e3ebc8fe4dca90a4853b11e2ef4d";	// 超级管理员系统管理菜单不能设置为隐藏
	public static final String Permissions_ManagerFloor_id = "745e28237e8041d5ae8355b2e91077f1";	// 超级管理员系统管理菜单不能设置为隐藏

	/*
      功能权限字典表信息
    */
	public static final String Function_Customer_Id = "35997d379e4d490192a5238d59b69678";	// 客户信息
	public static final String Function_Hide_Telephone_Id = "1bd24c8491134d988639ec6c3e7496c6";	// 隐藏客户号码中间四位
	public static final String Function_Customer_Add_Id = "34ecdf7adb1344608bcb305466b7bc95";	// 添加客户
	public static final String Function_Customer_Del_Id = "5446fe8acca64f9abdd904263b3689a4";	// 删除客户
	public static final String Function_Customer_Move_Id = "93fb73ad52a441bb8aef960681ba985b";	// 移动组
	public static final String Function_Customer_Resources_Id = "84f782f3934549a49d44da9b4692dd6b";	// 客户资源分配
	public static final String Function_Traffic_Id = "b341ad4f222f4ee19fbb8759ad3e8b8e";	// 话务信息
	//public static final String Function_Traffic_Telephone_Id = "2c3b62cbb06b492389c1bb1ab72714c9";	// 只查看个人通话记录
	public static final String Function_Traffic_Export_Id = "fc0f2ba695484899a7ba010d5721192d";	// 导出通话记录
	public static final String Function_Traffic_Select_Id = "1364fdae701d44538f747a09de3d209f";	// 查看历史通话记录联系人
	public static final String Function_FindAllCallRecord = "d7905fea91774c83810b21d25746f531";	// 查看所有通话记录，没有权限的话只能查看规定7天内的





	/*
	用户表
	 */
	public static final Integer User_Admin_Id = 1;
	public static final String User_Admin_WorkNum = "admin";
	public static final String User_Admin_NickName = "超级管理员";


	/*
	用户分组
	 */
	public static final Integer User_Group_Non_Id = 1;
	public static final String User_Group_Non_Name = "未分组";
	public static final Integer User_Group_Quality_Id = 2;
	public static final String User_Group_Quality_Name = "质检组";
	public static final Integer User_Group_Customer_Id = 3;
	public static final String User_Group_Customer_Name = "客服组";
	public static final Integer User_Group_Interview_Id = 4;
	public static final String User_Group_Interview_Name = "约访组";
	public static final Integer User_Group_System_Id = 5;
	public static final String User_Group_System_Name = "用户管理";
	public static final Integer User_Group_PartTime_Id = 6;
	public static final String User_Group_PartTime_Name = "兼职组";
	public static final Integer User_Group_Manager_Id = 7;
	public static final String User_Group_Manager_Name = "管理员组";
	/*
	参数表
	 */
	public static final String Parameter_Mouth_Goal = "d7905fea91774c83810b21d25746f531";
	public static final String Parameter_Mouth_Interview_Goal = "fc0f2ba695484899a7ba010d5721192d";
	public static final String Parameter_Sip_IP = "1bd24c8491134d988639ec6c3e7496c6";
	public static final String Parameter_Week_Gold = "9df9769565cd4ed69da36b0073f59b86";
	public static final String Parameter_Interview_Intention = "c333cb59fb864b9ea65434f0ca4407f7";
	public static final String Parameter_Post = "0ee2884fa2cf4c36b7d6a77ebcb30ec0";
	public static final String Parameter_CS_Intention = "3afb526fe615463abf30baca6df134a9";
	public static final String Parameter_CS_Gold = "3925aea2ff9e4f0f834526debb1e7c07";
	public static final String Parameter_Interview_Gold = "2a7cd27bd638430aae2d82068f96b0c9";
	public static final String Parameter_GoldRate = "0ccdf685a1114f6e8454275ccb46faa0";
	public static final String Parameter_TransNoneRate = "5a57d5f3d27841c8829aba4435027f09";
	public static final String Parameter_CSWeekIntentionGoal = "3c0a2e0d57c045e49a80f8a179ded976";
	public static final String Parameter_NickName = "9f49ef173c2f453085014da692c5b5c1";

	/**********************************表id*************************************/
}
