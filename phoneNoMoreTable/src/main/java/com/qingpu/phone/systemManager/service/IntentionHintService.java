package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.IntentionHintCondition;
import com.qingpu.phone.systemManager.entity.IntentionHint;
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

@Service("intentionHintService")
@Transactional
public class IntentionHintService {
	private static Logger logger = Logger.getLogger(IntentionHintService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param intentionHint
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public IntentionHint create(IntentionHint intentionHint) throws Exception{
		if(intentionHint != null && StringUtils.isBlank(intentionHint.getId())){
			intentionHint.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(intentionHint);
		return (IntentionHint) baseDao.save(intentionHint);
	}


	/**
	 * 更新
	 * @param intentionHint
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(IntentionHint intentionHint)  throws Exception {
		CommonUtils.checkEntity(intentionHint);
		baseDao.update(intentionHint);
	}


	/**
	 * 删除
	 * @param intentionHint
	 */
	public void delete(IntentionHint intentionHint) {
		intentionHint.setIsDel(true);
		intentionHint.setDelTime(new Date());
		baseDao.update(intentionHint);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public IntentionHint get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (IntentionHint)baseDao.get(IntentionHint.class, id);
	}


	/**
	 * 按条件查找
	 * @param intentionHintCondition
	 * @return
	 */
	public List<IntentionHint> list(IntentionHintCondition intentionHintCondition){
		return  intentionHintCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param intentionHintCondition
	 * @param sorter
	 * @return
	 */
	public List<IntentionHint> list(IntentionHintCondition intentionHintCondition, Sorter sorter){
		return  intentionHintCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param intentionHintCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<IntentionHint> list(IntentionHintCondition intentionHintCondition, Range range, Sorter sorter){
		PaginationSupport<IntentionHint> testTablePaginationSupport = (PaginationSupport<IntentionHint>) intentionHintCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param intentionHintCondition
	 * @return
	 */
	public Long countByCondition(IntentionHintCondition intentionHintCondition){
		return   intentionHintCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param intentionHintCondition
	 * @return
	 */
	public List<Object[]> listCustom(IntentionHintCondition intentionHintCondition){
		return  intentionHintCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param intentionHintCondition
	 * @param num
	 * @return
	 */
	public List<IntentionHint> listCustom(IntentionHintCondition intentionHintCondition, int num){
		return  AutoEvaluationUtil.toClassList(intentionHintCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param intentionHintCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<IntentionHint> listCustom(IntentionHintCondition intentionHintCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(intentionHintCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param intentionHint
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(IntentionHint intentionHint) throws Exception{
		intentionHint = this.create(intentionHint);
		Map<String, Object> map = new HashMap<>();
		map.put("id", intentionHint.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsIntentionHint
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(IntentionHint paramsIntentionHint) throws Exception{
		IntentionHint intentionHint = this.get(paramsIntentionHint.getId());
		if(intentionHint == null){
			throw new Exception("未找到对象");
		}
		String content = paramsIntentionHint.getContent();
		if(StringUtils.isBlank(content)){
			throw new Exception("请输入词条内容");
		}
		intentionHint.setContent(content);
		intentionHint.setCount(paramsIntentionHint.getCount());
		intentionHint.setDayCount(paramsIntentionHint.getDayCount());
		intentionHint.setIsReward(paramsIntentionHint.getIsReward());
		intentionHint.setRate(paramsIntentionHint.getRate());
		this.update(intentionHint);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		IntentionHint intentionHint = this.get(id);
		if(intentionHint == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			intentionHint.setOperatorId(user.getId());
		}
		this.delete(intentionHint);
	}
}
