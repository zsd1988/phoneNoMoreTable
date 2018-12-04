package com.qingpu.phone.common.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class FileUploadUtil {

    /**
     * 单个上传文件
     * @param request
     * @param filePathUrl               //文件路径
     * @param fileName                  //上传的文件名
     * @return                           //保存的文件名
     * @throws Exception
     */
    public static String uploadFile(HttpServletRequest request, String filePathUrl, String fileName) {

        String uploadImgName="";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile=multipartRequest.getFile(fileName);
        try{
            if(multipartFile!=null){
                String type  = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                //图片文件名
                //判断文件夹存不存在
                String filepath=filePathUrl;
                File fileFolder = new File(filepath);
                if(!fileFolder.exists()){
                    fileFolder.mkdirs();
                }
                uploadImgName =System.currentTimeMillis()+type;
                //文件不存在，创建新文件
                filepath = filepath+"//" +DateUtil.getFolderDate() + "_"+uploadImgName;
                File file = new File(filepath);
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileCopyUtils.copy(multipartFile.getBytes(), file);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "//" + DateUtil.getFolderDate()+"_"+uploadImgName;
    }
}
