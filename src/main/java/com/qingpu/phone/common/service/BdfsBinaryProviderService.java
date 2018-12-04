package com.qingpu.phone.common.service;

import com.qingpu.phone.constants.QingpuConstants;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.webserver.ServletWebServer;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service("bdfsBinaryProviderService")
@Transactional
public class BdfsBinaryProviderService {

	private static Logger logger = Logger.getLogger(BdfsBinaryProviderService.class);

	private TrackerClient tracker;
	private TrackerServer trackerServer;
	private StorageServer storageServer;
	StorageClient1 client;

	public void init(){
		try{
			ClientGlobal.init(BdfsBinaryProviderService.class.getClassLoader().getResource("bdfs.conf").getPath());
			String fastHost = ClientGlobal.getG_tracker_group().tracker_servers[0].getHostName();
			QingpuConstants.uploadUrl = "http://" + fastHost + ":8899/";
		}catch (Exception e){
			logger.error("文件上传系统初始化失败" + e.getMessage());
		}
	}

	public String upload(byte[] data, String type) {
		String sealPictureFileId = "";
		try {
			tracker = new TrackerClient();
			trackerServer = tracker.getConnection();
			client = new StorageClient1(trackerServer, storageServer);
			sealPictureFileId = client.upload_appender_file1(data, type, null);
		} catch (Exception e) {
			logger.error("文件上传失败：" + e.getMessage());
		}
		return sealPictureFileId;
	}

}
