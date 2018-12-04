package com.qingpu.phone.systemSetting.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemSetting.condition.AppCondition;
import com.qingpu.phone.systemSetting.entity.App;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appService")
@Transactional
public class AppService {
	private static Logger logger = Logger.getLogger(AppService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param app
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public App create(App app) throws Exception{
		if(app != null && StringUtils.isBlank(app.getId())){
			app.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(app);
		return (App) baseDao.save(app);
	}


	/**
	 * 更新
	 * @param app
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(App app)  throws Exception {
		CommonUtils.checkEntity(app);
		baseDao.update(app);
	}


	/**
	 * 删除
	 * @param app
	 */
	public void delete(App app) {
		app.setIsDel(true);
		app.setDelTime(new Date());
		baseDao.update(app);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public App get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (App)baseDao.get(App.class, id);
	}


	/**
	 * 按条件查找
	 * @param appCondition
	 * @return
	 */
	public List<App> list(AppCondition appCondition){
		return  appCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param appCondition
	 * @param sorter
	 * @return
	 */
	public List<App> list(AppCondition appCondition, Sorter sorter){
		return  appCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param appCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<App> list(AppCondition appCondition, Range range, Sorter sorter){
		PaginationSupport<App> testTablePaginationSupport = (PaginationSupport<App>) appCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param appCondition
	 * @return
	 */
	public Long countByCondition(AppCondition appCondition){
		return   appCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param appCondition
	 * @return
	 */
	public List<Object[]> listCustom(AppCondition appCondition){
		return  appCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param appCondition
	 * @param num
	 * @return
	 */
	public List<App> listCustom(AppCondition appCondition, int num){
		return  AutoEvaluationUtil.toClassList(appCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param appCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<App> listCustom(AppCondition appCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(appCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param app
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(App app) throws Exception{
		this.commonSetApp(app, true);
		app = this.create(app);
		Map<String, Object> map = new HashMap<>();
		map.put("id", app.getId());
		return map;
	}

	/**
	 * 共同校验
	 * @param app
	 * @param isCreate
	 * @throws Exception
	 */
	private void commonSetApp(App app, Boolean isCreate) throws Exception{
		if(app.getOsType() == null){
			throw new Exception("请选择类型");
		}
		AppCondition appCondition = new AppCondition();
		appCondition.setOsType(app.getOsType());
		List<App> appList = this.list(appCondition);
		if( ! appList.isEmpty()){
			if(isCreate){
				throw new Exception("已存在相同类型");
			}else{
				for(App item : appList){
					if( ! item.getId().equals(app.getId())){
						throw new Exception("已存在相同类型");
					}
				}
			}
		}
		if(app.getLeastVersion() == null){
			throw new Exception("请输入最低版本");
		}
	}

	/**
	 * 根据参数更新
	 * @param paramsApp
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(App paramsApp) throws Exception{
		App app = this.get(paramsApp.getId());
		app.setOsType(paramsApp.getOsType());
		app.setLeastVersion(paramsApp.getLeastVersion());
		app.setDownloadUrl(paramsApp.getDownloadUrl());
		this.commonSetApp(app, false);
		if(app == null){
			throw new Exception("未找到对象");
		}
		this.update(app);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		App app = this.get(id);
		if(app == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			app.setOperatorId(user.getId());
		}
		this.delete(app);
	}


	/**
	 * 根据类型获取
	 * @param osType
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public App getByOsType(PublicEnum.OsType osType) throws Exception{
		AppCondition appCondition = new AppCondition();
		appCondition.setOsType(osType);
		List<App> appList = this.list(appCondition);
		if(appList.isEmpty()){
			return  null;
		}else{
			return  appList.get(0);
		}
	}
}
