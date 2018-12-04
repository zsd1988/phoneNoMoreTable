package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.DateUtil;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import com.qingpu.phone.systemManager.condition.DayHintCondition;
import com.qingpu.phone.systemManager.entity.DayHint;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("dayHintService")
@Transactional
public class DayHintService {
	private static Logger logger = Logger.getLogger(DayHintService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	PostMessageService postMessageService;

	/**
	 * 创建
	 * @param dayHint
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public DayHint create(DayHint dayHint) throws Exception{
		CommonUtils.checkEntity(dayHint);
		return (DayHint) baseDao.save(dayHint);
	}


	/**
	 * 更新
	 * @param dayHint
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(DayHint dayHint)  throws Exception {
		CommonUtils.checkEntity(dayHint);
		baseDao.update(dayHint);
	}


	/**
	 * 删除
	 * @param dayHint
	 */
	public void delete(DayHint dayHint) {
		dayHint.setIsDel(true);
		dayHint.setDelTime(new Date());
		baseDao.update(dayHint);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public DayHint get(Long id) {
		if(id == null ){
			return null;
		}
		return (DayHint)baseDao.get(DayHint.class, id);
	}


	/**
	 * 按条件查找
	 * @param dayHintCondition
	 * @return
	 */
	public List<DayHint> list(DayHintCondition dayHintCondition){
		return  dayHintCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param dayHintCondition
	 * @param sorter
	 * @return
	 */
	public List<DayHint> list(DayHintCondition dayHintCondition, Sorter sorter){
		return  dayHintCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param dayHintCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<DayHint> list(DayHintCondition dayHintCondition, Range range, Sorter sorter){
		PaginationSupport<DayHint> testTablePaginationSupport = (PaginationSupport<DayHint>) dayHintCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param dayHintCondition
	 * @return
	 */
	public Long countByCondition(DayHintCondition dayHintCondition){
		return   dayHintCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param dayHintCondition
	 * @return
	 */
	public List<Object[]> listCustom(DayHintCondition dayHintCondition){
		return  dayHintCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param dayHintCondition
	 * @param num
	 * @return
	 */
	public List<DayHint> listCustom(DayHintCondition dayHintCondition, int num){
		return  AutoEvaluationUtil.toClassList(dayHintCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param dayHintCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<DayHint> listCustom(DayHintCondition dayHintCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(dayHintCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param dayHint
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(DayHint dayHint) throws Exception{
		dayHint = this.create(dayHint);
		Map<String, Object> map = new HashMap<>();
		map.put("id", dayHint.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsDayHint
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(DayHint paramsDayHint) throws Exception{
		DayHint dayHint = this.get(paramsDayHint.getId());
		if(dayHint == null){
			throw new Exception("未找到对象");
		}
		String content = paramsDayHint.getContent();
		if(StringUtils.isBlank(content)){
			throw new Exception("请输入内容");
		}
		PublicEnum.DayHintType type = paramsDayHint.getType();
		if(type == null){
			throw new Exception("请选择类型");
		}
		dayHint.setType(type);
		dayHint.setContent(content);
		dayHint.setRate(paramsDayHint.getRate());
		this.update(dayHint);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(Long id, HttpServletRequest request) throws Exception{
		DayHint dayHint = this.get(id);
		if(dayHint == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			dayHint.setOperatorId(user.getId());
		}
		this.delete(dayHint);
	}

	/**
	 * 获取该类型的提示
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public DayHint getRateDayHintByType(PublicEnum.DayHintType type) throws Exception{
		DayHintCondition dayHintCondition = new DayHintCondition();
		dayHintCondition.setType(type);
		List<DayHint> dayHintList = this.list(dayHintCondition);
		Integer totalRate = 0;
		for(DayHint dayHint : dayHintList){
			totalRate += dayHint.getRate();
		}
		if(totalRate != 0){
            Integer num = new Random().nextInt(totalRate) + 1;
            totalRate = 0;
            for(DayHint dayHint : dayHintList){
                totalRate += dayHint.getRate();
                if(num <= totalRate){
                    return dayHint;
                }
            }
        }
		return null;
	}

	/**
	 * 获取昵称
	 * @return
	 */
	public String getNickName(){
		String nickName = "";
		if(QingpuConstants._nickName.length > 0){
			Integer num = new Random().nextInt(QingpuConstants._nickName.length);
			nickName = QingpuConstants._nickName[num];
		}
		return nickName;
	}
}
