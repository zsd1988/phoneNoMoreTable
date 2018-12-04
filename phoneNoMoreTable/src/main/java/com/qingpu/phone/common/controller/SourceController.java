package com.qingpu.phone.common.controller;

import com.qingpu.phone.common.condition.SourceCondition;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.entity.Source;
import com.qingpu.phone.common.service.SourceService;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.PermissionsService;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


@Controller
@RequestMapping("/service/platform/func/source")
public class SourceController extends HandlerInterceptorAdapter {

	@Resource
	PermissionsService permissionService;
	@Resource
	UserService userService;
	@Resource
	SourceService sourceService;

	@Autowired
	HttpServletRequest request; //获取到当前请求的request


	/**
	 * 检测文件md5值是否存在
	 * @param md5
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/checkMD5")
	public void checkMD5(String md5, String sourceName, String folderId, String fileType, HttpServletResponse response) throws IOException  {
		try{
			User user = (User)request.getSession().getAttribute("_user");
			Integer userId = 0;
			if(user != null){
				userId = user.getId();
			}
			if(StringUtils.isBlank(md5)){
				throw new Exception("请传入md5值");
			}
			if(StringUtils.isBlank(folderId)){
				folderId = QingpuConstants.Source_Common_Folder_Id;
			}
			SourceCondition sourceCondition = new SourceCondition();
			sourceCondition.setMd5(md5);
			List<Source> sourceList = sourceService.list(sourceCondition);
			if(sourceList.isEmpty()){
				JsonRetInfo.returnSuccess(response, "操作成功");
				return;
			}else{
				sourceCondition.setLikeSourceName(sourceName);
				List<Source> likeSourceList = sourceService.list(sourceCondition);
				Source existSource = null;

				// 判断是否存在该目录，如果不存在，则复制一个资源表数据至该目录
				Boolean isContain = false;
				if( ! likeSourceList.isEmpty()){
					for(Source source : likeSourceList){
						if(source.getPermissionId().equals(folderId)){
							isContain = true;
							existSource = source;
							break;
						}
					}
				}
				if( ! isContain){
					Source source = sourceList.get(0);
					Source newSource = new Source();
					BeanUtils.copyProperties(source, newSource);
					newSource.setSourceName(sourceName);
					newSource.setId(UUIDGenerator.getUUID());
					Date now = new Date();
					newSource.setUpdateTime(now);
					newSource.setCreateTime(now);
					newSource.setPermissionId(folderId);
					newSource.setUserId(userId);
					sourceService.create(newSource);
					existSource = source;
				}
				Map<String, Object> map = new HashMap<>();
				map.put("id", existSource.getId());
				map.put("sourceImg", existSource.getSourceImg());
				map.put("name", existSource.getSourceName());
				JsonRetInfo.returnSuccess(response, "操作成功", map);
				return;
			}
		}catch (Exception e){
			String error = e.getMessage();
			JsonRetInfo.returnFail(response, error);
		}
	}
}
