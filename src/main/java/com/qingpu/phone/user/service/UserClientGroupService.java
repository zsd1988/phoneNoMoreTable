package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.UserClientGroupCondition;
import com.qingpu.phone.user.entity.UserClientGroup;
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

@Service("userClientGroupService")
@Transactional
public class UserClientGroupService {
	private static Logger logger = Logger.getLogger(UserClientGroupService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param testTable
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserClientGroup create(UserClientGroup testTable) throws Exception{
		CommonUtils.checkEntity(testTable);
		return (UserClientGroup) baseDao.save(testTable);
	}


	/**
	 * 更新
	 * @param testTable
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(UserClientGroup testTable)  throws Exception {
		CommonUtils.checkEntity(testTable);
		baseDao.update(testTable);
	}


	/**
	 * 删除
	 * @param testTable
	 */
	public void delete(UserClientGroup testTable) {
		testTable.setIsDel(true);
		testTable.setDelTime(new Date());
		baseDao.update(testTable);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public UserClientGroup get(Integer id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (UserClientGroup)baseDao.get(UserClientGroup.class, id);
	}


	/**
	 * 按条件查找
	 * @param testTableCondition
	 * @return
	 */
	public List<UserClientGroup> list(UserClientGroupCondition testTableCondition){
		return  testTableCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param testTableCondition
	 * @param sorter
	 * @return
	 */
	public List<UserClientGroup> list(UserClientGroupCondition testTableCondition, Sorter sorter){
		return  testTableCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param testTableCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<UserClientGroup> list(UserClientGroupCondition testTableCondition, Range range, Sorter sorter){
		PaginationSupport<UserClientGroup> testTablePaginationSupport = (PaginationSupport<UserClientGroup>)testTableCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param testTableCondition
	 * @return
	 */
	public Long countByCondition(UserClientGroupCondition testTableCondition){
		return   testTableCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param testTableCondition
	 * @return
	 */
	public List<UserClientGroup> listCustom(UserClientGroupCondition testTableCondition){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory));
	}

	/**
	 * 获取相应数量
	 * @param testTableCondition
	 * @param num
	 * @return
	 */
	public List<UserClientGroup> listCustom(UserClientGroupCondition testTableCondition, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param testTableCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<UserClientGroup> listCustom(UserClientGroupCondition testTableCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param testTable
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(UserClientGroup testTable) throws Exception{
		testTable = this.create(testTable);
		Map<String, Object> map = new HashMap<>();
		map.put("id", testTable.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsTestTable
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(UserClientGroup paramsTestTable) throws Exception{
		UserClientGroup testTable = this.get(paramsTestTable.getId());
		if(testTable == null){
			throw new Exception("未找到对象");
		}
		this.update(testTable);
	}


}
