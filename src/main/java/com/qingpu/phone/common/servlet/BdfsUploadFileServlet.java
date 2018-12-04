package com.qingpu.phone.common.servlet;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.Source;
import com.qingpu.phone.common.service.BdfsBinaryProviderService;
import com.qingpu.phone.common.service.SourceService;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.entity.User;
import it.sauronsoftware.jave.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件上传组件完整使用
 */
@WebServlet(name="bdfsUploadFile", urlPatterns="/bdfsUploadFile")
public class BdfsUploadFileServlet extends HttpServlet {
	SourceService sourceService;
	BdfsBinaryProviderService bdfsBinaryProviderService;

	private static Logger logger = Logger.getLogger(BdfsUploadFileServlet.class);
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		sourceService = (SourceService)wac.getBean("sourceService");
		bdfsBinaryProviderService = (BdfsBinaryProviderService)wac.getBean("bdfsBinaryProviderService");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,response);
	}

	/**
	 * 获取uuid，保证uuid唯一性
	 * @return
	 */
	private synchronized String getUUID(){
		return UUIDGenerator.getUUID();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.创建文件上传工厂类
		DiskFileItemFactory fac = new DiskFileItemFactory();
		//2.创建文件上传核心类对象
		ServletFileUpload upload = new ServletFileUpload(fac);
		//解决http报头乱码，即中文文件名乱码
		upload.setHeaderEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		//判断，当前表单是否为文件上传表单
		if (upload.isMultipartContent(request)){
			try {
				User user = (User)request.getSession().getAttribute("_user");
				if(user == null){
					throw new Exception("登录失效，请重新登录");
				}
				Map<String, File> fileMap = new HashMap<>();
				Map<String, FileItem> fileItemMap = new HashMap<>();
				//3.把请求数据转换为FileItem对象的集合
				List<FileItem> list = upload.parseRequest(request);
				Map<String, String> map = new HashMap<>();
				Map<String, String> filePathMap = new HashMap<>();
				String typeName = null;
				String path = "";
				String name = "";
				List<Map<String, String>> resultMap = new ArrayList<>();
				//遍历，得到每一个上传项
				for (FileItem fileItem : list){
					//判断：是普通表单项，还是文件上传表单项
					if (fileItem.isFormField()){
						//普通表单x
						String fieldName = fileItem.getFieldName();//获取元素名称
						String value = fileItem.getString("utf-8"); //获取元素值
						map.put(fieldName, value);
					}else {
						name = fileItem.getName(); //上传的文件名称
						if( StringUtils.isBlank(name)){
							throw new Exception("文件名为空");
						}
						typeName  = name.substring(name.lastIndexOf("."));
						typeName = typeName.substring(1, typeName.length());
						path = bdfsBinaryProviderService.upload(fileItem.get(),typeName );
						if(StringUtils.isBlank(path)){
							response.getWriter().write( new JsonRetInfo(JsonRetInfo.ERROR, "上传失败，请重试").toString());
						}
					}
				}

				Map<String, String> itemMap = new HashMap<>();
				if(map.containsKey("addParams")){
					itemMap.put("addParams", map.get("addParams"));
				}
				itemMap.put("sourcePathUrl", path);
				itemMap.put("name", name);
				itemMap.put("id", this.getUUID());
				itemMap.put("sourceImg", path);
				resultMap.add(itemMap);
				response.getWriter().write( new JsonRetInfo(JsonRetInfo.SUCCESS, "上传成功", resultMap).toString());
			}  catch (Exception exception) {
				String errorHint = exception.getMessage();
				response.getWriter().write( new JsonRetInfo(JsonRetInfo.ERROR, errorHint).toString());
			}
		}else {
			System.out.println("不处理！");
		}

	}
}
