package com.qingpu.phone.common.servlet;

import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.entity.Source;
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
@WebServlet(name="uploadFile", urlPatterns="/uploadFile")
public class UploadFileServlet extends HttpServlet {
	SourceService sourceService;

	private static Logger logger = Logger.getLogger(UploadFileServlet.class);
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		sourceService = (SourceService)wac.getBean("sourceService");
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
				//遍历，得到每一个上传项
				for (FileItem fileItem : list){
					//判断：是普通表单项，还是文件上传表单项
					if (fileItem.isFormField()){
						//普通表单x
						String fieldName = fileItem.getFieldName();//获取元素名称
						String value = fileItem.getString("utf-8"); //获取元素值
						map.put(fieldName, value);
					}else {
						String id = this.getUUID();
						String name = fileItem.getName(); //上传的文件名称
						if( StringUtils.isBlank(name)){
							throw new Exception("文件名为空");
						}
						String type  = name.substring(name.lastIndexOf("."));
						//图片文件名
						//判断文件夹存不存在
						String filepath= QingpuConstants.FILE_UPLOAD_PATH ;
						String sourcePath = DateUtil.getFolderDate();
						filepath += sourcePath;
						File fileFolder = new File(filepath);
						if(!fileFolder.exists()){
							fileFolder.mkdirs();
						}
						String uploadImgName = id + type;
						//文件不存在，创建新文件
						filepath = filepath+"/"+uploadImgName;
						sourcePath += "/" + uploadImgName;
						File file = new File(filepath);
						if(!file.exists()) {
							file.createNewFile();
						}
						fileMap.put(id, file);
						fileItemMap.put(id, fileItem);
						filePathMap.put(id, sourcePath);
					}
				}
				/*
				存入数据库
				 */
				// 判断是否存入动态素材库
				String fileType = map.get("fileType");
				PublicEnum.SourceType sourceType = PublicEnum.SourceType.Image;
				switch (fileType){
					case "video":
						sourceType = PublicEnum.SourceType.Video;
						break;
					case "zip":
						sourceType = PublicEnum.SourceType.Zip;
						break;
					case "office":
						sourceType = PublicEnum.SourceType.Office;
						break;
				}
				String permissionId;
				String type = map.get("sourceType");
				if("1".equals(type)){
					permissionId = QingpuConstants.Source_Common_Folder_Id;
				}else{
					permissionId = user.getId() + "";
				}
				if(StringUtils.isBlank(permissionId)){
					throw new Exception("请选择指定文件夹");
				}
				Integer userId = user.getId();
				List<Map<String, String>> resultMap = new ArrayList<>();
				for(Map.Entry<String, File> entry : fileMap.entrySet()){
					String id = entry.getKey();
					FileItem fileItem = fileItemMap.get(id);
					File file = entry.getValue();
					FileCopyUtils.copy(fileItem.get(), file);
					Source source = new Source();
					String sourcePath = filePathMap.get(id);
					source.setSourcePathUrl(sourcePath);
					String sourceImage = sourcePath;

					switch (sourceType){
						case Video:
							// 获取视频基本信息
							Encoder encoder = new Encoder();
							MultimediaInfo multimediaInfo = encoder.getInfo(file);
							source.setSourceTime(multimediaInfo.getDuration()/1000.00);
							VideoInfo videoInfo = multimediaInfo.getVideo();
							VideoSize videoSize = videoInfo.getSize();
							Integer width = videoSize.getWidth();
							Integer height = videoSize.getHeight();
							source.setImgWidth(width);
							source.setImgHeight(height);

							// 创建视频截图
							String sourceName = filePathMap.get(id);
							String imageName = sourceName.substring(0, sourceName.lastIndexOf("."));
							imageName += ".png";
							File imageFile = new File(QingpuConstants.FILE_UPLOAD_PATH + imageName);
							EncodingAttributes attrs = new EncodingAttributes();
							attrs.setFormat("image2");//转图片
							attrs.setOffset(3f);//设置偏移位置，即开始转码位置（3秒）
							attrs.setDuration(0.01f);//设置转码持续时间（1秒）
							VideoAttributes video = new VideoAttributes();
							video.setCodec("png");//转图片
							video.setSize(new VideoSize(width, height));
							attrs.setVideoAttributes(video);
							try {
								encoder.encode(file, imageFile, attrs);
							}catch (Exception e){

							}
							source.setSourceImg(imageName);
							source.setMd5(map.get("md5"));
							sourceImage = imageName;
							break;
						case Image:
							BufferedImage bufferedImage = ImageIO.read(file);
							source.setImgHeight(bufferedImage.getHeight());
							source.setImgWidth(bufferedImage.getWidth());
							source.setSourceImg(sourcePath);
							break;
						default:
							source.setMd5(map.get("md5"));
							source.setSourceImg(sourcePath);
							break;
					}

					source.setId(id);
					source.setSourceType(sourceType);
					String name = fileItem.getName();
					source.setSourceName(name);
					source.setPermissionId(permissionId);
					source.setSourceSize((double)fileItem.getSize());
					source.setUserId(userId);
					sourceService.create(source);
					Map<String, String> itemMap = new HashMap<>();
					itemMap.put("sourceImg", sourceImage);
					itemMap.put("sourcePathUrl", sourcePath);
					itemMap.put("id", id);
					itemMap.put("name", name);
					if(map.containsKey("addParams")){
						itemMap.put("addParams", map.get("addParams"));
					}
					resultMap.add(itemMap);
				}
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
