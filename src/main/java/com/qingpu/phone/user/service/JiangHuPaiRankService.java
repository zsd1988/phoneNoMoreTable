package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.JiangHuPaiRankCondition;
import com.qingpu.phone.user.entity.JiangHuPaiRank;
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

@Service("jiangHuPaiRankService")
@Transactional
public class JiangHuPaiRankService {
	private static Logger logger = Logger.getLogger(JiangHuPaiRankService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param jiangHuPaiRank
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JiangHuPaiRank create(JiangHuPaiRank jiangHuPaiRank) throws Exception{
		if(jiangHuPaiRank != null && StringUtils.isBlank(jiangHuPaiRank.getId())){
			jiangHuPaiRank.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(jiangHuPaiRank);
		return (JiangHuPaiRank) baseDao.save(jiangHuPaiRank);
	}


	/**
	 * 更新
	 * @param jiangHuPaiRank
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(JiangHuPaiRank jiangHuPaiRank)  throws Exception {
		CommonUtils.checkEntity(jiangHuPaiRank);
		baseDao.update(jiangHuPaiRank);
	}


	/**
	 * 删除
	 * @param jiangHuPaiRank
	 */
	public void delete(JiangHuPaiRank jiangHuPaiRank) {
		jiangHuPaiRank.setIsDel(true);
		jiangHuPaiRank.setDelTime(new Date());
		baseDao.update(jiangHuPaiRank);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public JiangHuPaiRank get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (JiangHuPaiRank)baseDao.get(JiangHuPaiRank.class, id);
	}


	/**
	 * 按条件查找
	 * @param jiangHuPaiRankCondition
	 * @return
	 */
	public List<JiangHuPaiRank> list(JiangHuPaiRankCondition jiangHuPaiRankCondition){
		return  jiangHuPaiRankCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param jiangHuPaiRankCondition
	 * @param sorter
	 * @return
	 */
	public List<JiangHuPaiRank> list(JiangHuPaiRankCondition jiangHuPaiRankCondition, Sorter sorter){
		return  jiangHuPaiRankCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param jiangHuPaiRankCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<JiangHuPaiRank> list(JiangHuPaiRankCondition jiangHuPaiRankCondition, Range range, Sorter sorter){
		PaginationSupport<JiangHuPaiRank> testTablePaginationSupport = (PaginationSupport<JiangHuPaiRank>) jiangHuPaiRankCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param jiangHuPaiRankCondition
	 * @return
	 */
	public Long countByCondition(JiangHuPaiRankCondition jiangHuPaiRankCondition){
		return   jiangHuPaiRankCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param jiangHuPaiRankCondition
	 * @return
	 */
	public List<Object[]> listCustom(JiangHuPaiRankCondition jiangHuPaiRankCondition){
		return  jiangHuPaiRankCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param jiangHuPaiRankCondition
	 * @param num
	 * @return
	 */
	public List<JiangHuPaiRank> listCustom(JiangHuPaiRankCondition jiangHuPaiRankCondition, int num){
		return  AutoEvaluationUtil.toClassList(jiangHuPaiRankCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param jiangHuPaiRankCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<JiangHuPaiRank> listCustom(JiangHuPaiRankCondition jiangHuPaiRankCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(jiangHuPaiRankCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param jiangHuPaiRank
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(JiangHuPaiRank jiangHuPaiRank) throws Exception{
		jiangHuPaiRank = this.create(jiangHuPaiRank);
		Map<String, Object> map = new HashMap<>();
		map.put("id", jiangHuPaiRank.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsJiangHuPaiRank
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(JiangHuPaiRank paramsJiangHuPaiRank) throws Exception{
		JiangHuPaiRank jiangHuPaiRank = this.get(paramsJiangHuPaiRank.getId());
		if(jiangHuPaiRank == null){
			throw new Exception("未找到对象");
		}
		this.update(jiangHuPaiRank);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		JiangHuPaiRank jiangHuPaiRank = this.get(id);
		if(jiangHuPaiRank == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			jiangHuPaiRank.setOperatorId(user.getId());
		}
		this.delete(jiangHuPaiRank);
	}
}
