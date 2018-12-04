package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.LuckDrawCondition;
import com.qingpu.phone.systemManager.entity.LuckDraw;
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

@Service("luckDrawService")
@Transactional
public class LuckDrawService {
	private static Logger logger = Logger.getLogger(LuckDrawService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param luckDraw
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public LuckDraw create(LuckDraw luckDraw) throws Exception{
		if(luckDraw != null && StringUtils.isBlank(luckDraw.getId())){
			luckDraw.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(luckDraw);
		return (LuckDraw) baseDao.save(luckDraw);
	}


	/**
	 * 更新
	 * @param luckDraw
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(LuckDraw luckDraw)  throws Exception {
		CommonUtils.checkEntity(luckDraw);
		baseDao.update(luckDraw);
	}


	/**
	 * 删除
	 * @param luckDraw
	 */
	public void delete(LuckDraw luckDraw) {
		luckDraw.setIsDel(true);
		luckDraw.setDelTime(new Date());
		baseDao.update(luckDraw);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public LuckDraw get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (LuckDraw)baseDao.get(LuckDraw.class, id);
	}


	/**
	 * 按条件查找
	 * @param luckDrawCondition
	 * @return
	 */
	public List<LuckDraw> list(LuckDrawCondition luckDrawCondition){
		return  luckDrawCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param luckDrawCondition
	 * @param sorter
	 * @return
	 */
	public List<LuckDraw> list(LuckDrawCondition luckDrawCondition, Sorter sorter){
		return  luckDrawCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param luckDrawCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<LuckDraw> list(LuckDrawCondition luckDrawCondition, Range range, Sorter sorter){
		PaginationSupport<LuckDraw> testTablePaginationSupport = (PaginationSupport<LuckDraw>) luckDrawCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param luckDrawCondition
	 * @return
	 */
	public Long countByCondition(LuckDrawCondition luckDrawCondition){
		return   luckDrawCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param luckDrawCondition
	 * @return
	 */
	public List<Object[]> listCustom(LuckDrawCondition luckDrawCondition){
		return  luckDrawCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param luckDrawCondition
	 * @param num
	 * @return
	 */
	public List<LuckDraw> listCustom(LuckDrawCondition luckDrawCondition, int num){
		return  AutoEvaluationUtil.toClassList(luckDrawCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param luckDrawCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<LuckDraw> listCustom(LuckDrawCondition luckDrawCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(luckDrawCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param luckDraw
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(LuckDraw luckDraw) throws Exception{
		luckDraw = this.create(luckDraw);
		Map<String, Object> map = new HashMap<>();
		map.put("id", luckDraw.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsLuckDraw
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(LuckDraw paramsLuckDraw) throws Exception{
		LuckDraw luckDraw = this.get(paramsLuckDraw.getId());
		if(luckDraw == null){
			throw new Exception("未找到对象");
		}
		luckDraw.setCount( paramsLuckDraw.getCount());
		luckDraw.setRate(paramsLuckDraw.getRate());
		luckDraw.setName(paramsLuckDraw.getName());
		luckDraw.setDayCount(paramsLuckDraw.getDayCount());
		this.update(luckDraw);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		LuckDraw luckDraw = this.get(id);
		if(luckDraw == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			luckDraw.setOperatorId(user.getId());
		}
		this.delete(luckDraw);
	}


	/**
	 * 检测是否有奖品
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean isHaveLuckDraw() throws Exception{
		LuckDrawCondition luckDrawCondition = new LuckDrawCondition();
		luckDrawCondition.setbDel(false);
		luckDrawCondition.setGtCount(0);
		List<LuckDraw> luckDrawList = this.list(luckDrawCondition);
		if(luckDrawList.isEmpty()){
			return false;
		}
		return true;
	}
}
