package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.JiangHuRankCondition;
import com.qingpu.phone.user.entity.JiangHuRank;
import com.qingpu.phone.user.entity.JiangHuRen;
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

@Service("jiangHuRankService")
@Transactional
public class JiangHuRankService {
	private static Logger logger = Logger.getLogger(JiangHuRankService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param jiangHuRank
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JiangHuRank create(JiangHuRank jiangHuRank) throws Exception{
		if(jiangHuRank != null && StringUtils.isBlank(jiangHuRank.getId())){
			jiangHuRank.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(jiangHuRank);
		return (JiangHuRank) baseDao.save(jiangHuRank);
	}


	/**
	 * 更新
	 * @param jiangHuRank
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(JiangHuRank jiangHuRank)  throws Exception {
		CommonUtils.checkEntity(jiangHuRank);
		baseDao.update(jiangHuRank);
	}


	/**
	 * 删除
	 * @param jiangHuRank
	 */
	public void delete(JiangHuRank jiangHuRank) {
		jiangHuRank.setIsDel(true);
		jiangHuRank.setDelTime(new Date());
		baseDao.update(jiangHuRank);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public JiangHuRank get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (JiangHuRank)baseDao.get(JiangHuRank.class, id);
	}


	/**
	 * 按条件查找
	 * @param jiangHuRankCondition
	 * @return
	 */
	public List<JiangHuRank> list(JiangHuRankCondition jiangHuRankCondition){
		return  jiangHuRankCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param jiangHuRankCondition
	 * @param sorter
	 * @return
	 */
	public List<JiangHuRank> list(JiangHuRankCondition jiangHuRankCondition, Sorter sorter){
		return  jiangHuRankCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param jiangHuRankCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<JiangHuRank> list(JiangHuRankCondition jiangHuRankCondition, Range range, Sorter sorter){
		PaginationSupport<JiangHuRank> testTablePaginationSupport = (PaginationSupport<JiangHuRank>) jiangHuRankCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param jiangHuRankCondition
	 * @return
	 */
	public Long countByCondition(JiangHuRankCondition jiangHuRankCondition){
		return   jiangHuRankCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param jiangHuRankCondition
	 * @return
	 */
	public List<Object[]> listCustom(JiangHuRankCondition jiangHuRankCondition){
		return  jiangHuRankCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param jiangHuRankCondition
	 * @param num
	 * @return
	 */
	public List<JiangHuRank> listCustom(JiangHuRankCondition jiangHuRankCondition, int num){
		return  AutoEvaluationUtil.toClassList(jiangHuRankCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param jiangHuRankCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<JiangHuRank> listCustom(JiangHuRankCondition jiangHuRankCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(jiangHuRankCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param jiangHuRank
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(JiangHuRank jiangHuRank) throws Exception{
		jiangHuRank = this.create(jiangHuRank);
		Map<String, Object> map = new HashMap<>();
		map.put("id", jiangHuRank.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsJiangHuRank
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(JiangHuRank paramsJiangHuRank) throws Exception{
		JiangHuRank jiangHuRank = this.get(paramsJiangHuRank.getId());
		if(jiangHuRank == null){
			throw new Exception("未找到对象");
		}
		this.update(jiangHuRank);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		JiangHuRank jiangHuRank = this.get(id);
		if(jiangHuRank == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			jiangHuRank.setOperatorId(user.getId());
		}
		this.delete(jiangHuRank);
	}


	/**
	 * 创建排行榜
	 * @param jiangHuRenList
	 * @param createTime
	 */
	public void createByRenList(List<JiangHuRen> jiangHuRenList, Date createTime){
		for(JiangHuRen jiangHuRen : jiangHuRenList){
			try{
				JiangHuRank jiangHuRank = new JiangHuRank();
				jiangHuRank.setCreateTime(createTime);
				jiangHuRank.setFinish(jiangHuRen.getWeekFinish());
				jiangHuRank.setGold(jiangHuRen.getWeekGold());
				jiangHuRank.setIntention(jiangHuRen.getWeekIntention());
				jiangHuRank.setRoleType(jiangHuRen.getRoleType());
				jiangHuRank.setPaiId(jiangHuRen.getPaiId());
				jiangHuRank.setUserId(jiangHuRen.getUserId());
				jiangHuRank.setUserName(jiangHuRen.getUserName());
				this.create(jiangHuRank);
			}catch (Exception e){
				logger.info("创建排行榜失败： " + e.getMessage());
			}
		}
	}
}
