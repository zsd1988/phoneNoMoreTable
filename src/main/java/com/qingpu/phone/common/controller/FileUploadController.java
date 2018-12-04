package com.qingpu.phone.common.controller;

import com.qingpu.phone.common.service.BdfsBinaryProviderService;
import com.qingpu.phone.common.utils.HttpUtils;
import com.qingpu.phone.common.utils.ZipUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/upLoadFile")
public class FileUploadController {
	
	@Resource
	private BdfsBinaryProviderService bdfsProviderService;

	public FileUploadController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 上传一个文件
	 * */
	@RequestMapping("/uploadOneFile")
	public synchronized void uploadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam("kartik-input-709[]") MultipartFile file){
		String fileUrl = "";
		
		if (!file.isEmpty()) {			
			try {
				//获取上传文件参数
				String openid = request.getParameter("openid");
				
				byte[] b = file.getBytes();
				String type = file.getContentType();
				fileUrl = bdfsProviderService.upload(b, type.split("/")[1]);//image/png 类型	
				fileUrl = "http://www.phone.com:8080/" + fileUrl;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{

			}	        
        } else {
            System.out.println("--Error, File is empty");
        }
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileUrl", fileUrl);
		HttpUtils.sendJsonStr(response, jsonObject.toString());
	}

	/**
	 * 上传多个文件
	 * */
	@RequestMapping("/uploadMultipleFile")
    public void uploadMultipleFileHandler(@RequestParam("kartik-input-710[]") MultipartFile[] files) throws IOException {        
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            if (!file.isEmpty()) {
                InputStream in = null;
                OutputStream out = null;

                try {
                    String rootPath = System.getProperty("catalina.home");
                    File dir = new File(rootPath + File.separator + "tmpFiles");
                    if (!dir.exists())
                        dir.mkdirs();
                    File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                    in = file.getInputStream();
                    out = new FileOutputStream(serverFile);
                    byte[] b = new byte[1024];
                    int len = 0;
                    while ((len = in.read(b)) > 0) {
                        out.write(b, 0, len);
                    }
                    out.close();
                    in.close();
                } catch (Exception e) {
                    arr.add(i);
                } finally {
                    if (out != null) {
                        out.close();
                        out = null;
                    }

                    if (in != null) {
                        in.close();
                        in = null;
                    }
                }
            } else {
                arr.add(i);
            }
        }

        if(arr.size() > 0) {
            System.out.println("Files upload fail");            
        } else {
            System.out.println("Files upload success");
        }
    }




    /**
     *	上传ZIP文件并解压
     *
     */
    @RequestMapping("/uploadFileZip")
    public void uploadFileZip(HttpServletRequest request) throws Exception{
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists())
            dir.mkdirs();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
     //   MultipartFile multipartFile=multipartRequest.getFile("fileZip");
        List<MultipartFile> multipartFileList=multipartRequest.getFiles("fileName");
        String result=null;
        for(MultipartFile multipartFile:multipartFileList){
            if(multipartFile!=null){
                String type  = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                if(type.equals(".zip")){
                    String filePath=dir.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename();
                    File saveFile = new File(filePath);
                    if(!saveFile.exists()) {
                        saveFile.createNewFile();
                    }
                    FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
                    ZipUtil.unZip(filePath);
                }else{
                    String filePath=dir.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename();
                    File saveFile = new File(filePath);
                    if(!saveFile.exists()) {
                        saveFile.createNewFile();
                    }
                    FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
                }
            }
        }
    }

    //下载网络路径文件
    @RequestMapping("/downloadFileByUrl")
    public void downloadFileByUrl(String sourcePathUrl,HttpServletResponse response){
        try{
            String[] sourceNameList=sourcePathUrl.split("/");
            String sourceName=sourceNameList[sourceNameList.length-1];
            URL url = new URL(sourcePathUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            response.setContentType( "application/x-msdownload");
            response.setHeader("Content-Type","application/octet-stream");
            String file_name = new String(sourceName.getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition","attachment;filename="+file_name);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(context);
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
