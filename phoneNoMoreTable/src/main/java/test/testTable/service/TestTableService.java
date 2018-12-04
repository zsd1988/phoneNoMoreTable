package test.testTable.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.JsonRetInfo;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.RemoteCallException;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.GroupCallDetailCondition;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.testTable.condition.TestTableCondition;
import test.testTable.entity.TestTable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("testTableService")
@Transactional
public class TestTableService {
	private static Logger logger = Logger.getLogger(TestTableService.class);

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
	public TestTable create(TestTable testTable) throws Exception{
		CommonUtils.checkEntity(testTable);
		return (TestTable) baseDao.save(testTable);
	}


	/**
	 * 更新
	 * @param testTable
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(TestTable testTable)  throws Exception {
		CommonUtils.checkEntity(testTable);
		baseDao.update(testTable);
	}


	/**
	 * 删除
	 * @param testTable
	 */
	public void delete(TestTable testTable) {
		testTable.setIsDel(true);
		testTable.setDelTime(new Date());
		baseDao.update(testTable);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public TestTable get(Long id) {
		if(id == null ){
			return null;
		}
		return (TestTable)baseDao.get(TestTable.class, id);
	}


	/**
	 * 按条件查找
	 * @param testTableCondition
	 * @return
	 */
	public List<TestTable> list(TestTableCondition testTableCondition){
		return  testTableCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param testTableCondition
	 * @param sorter
	 * @return
	 */
	public List<TestTable> list(TestTableCondition testTableCondition, Sorter sorter){
		return  testTableCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param testTableCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<TestTable> list(TestTableCondition testTableCondition, Range range, Sorter sorter){
		PaginationSupport<TestTable> testTablePaginationSupport = (PaginationSupport<TestTable>)testTableCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param testTableCondition
	 * @return
	 */
	public Long countByCondition(TestTableCondition testTableCondition){
		return   testTableCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param testTableCondition
	 * @return
	 */
	public List<Object[]> listCustom(TestTableCondition testTableCondition){
		return  testTableCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param testTableCondition
	 * @param num
	 * @return
	 */
	public List<TestTable> listCustom(TestTableCondition testTableCondition, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param testTableCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<TestTable> listCustom(TestTableCondition testTableCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param testTable
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(TestTable testTable) throws Exception{
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
	public void updateByParams(TestTable paramsTestTable) throws Exception{
		TestTable testTable = this.get(paramsTestTable.getId());
		if(testTable == null){
			throw new Exception("未找到对象");
		}
		this.update(testTable);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(Long id, HttpServletRequest request) throws Exception{
		TestTable testTable = this.get(id);
		if(testTable == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			testTable.setOperatorId(user.getId());
		}
		this.delete(testTable);
	}
}
