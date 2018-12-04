package com.qingpu.phone.clientManager.service;

import com.qingpu.phone.clientManager.condition.ClientGroupCondition;
import com.qingpu.phone.clientManager.entity.ClientGroup;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.entity.User;
import com.qingpu.phone.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("clientGroupService")
@Transactional
public class ClientGroupService {
	private static Logger logger = Logger.getLogger(ClientGroupService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private UserService userService;

	/**
	 * 创建
	 * @param testTable
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ClientGroup create(ClientGroup testTable) throws Exception{
		if(testTable != null && StringUtils.isBlank(testTable.getId())){
			testTable.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(testTable);
		return (ClientGroup) baseDao.save(testTable);
	}


	/**
	 * 更新
	 * @param testTable
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(ClientGroup testTable)  throws Exception {
		CommonUtils.checkEntity(testTable);
		baseDao.update(testTable);
	}


	/**
	 * 删除
	 * @param testTable
	 */
	public void delete(ClientGroup testTable) {
		testTable.setIsDel(true);
		baseDao.update(testTable);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public ClientGroup get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (ClientGroup)baseDao.get(ClientGroup.class, id);
	}


	/**
	 * 按条件查找
	 * @param testTableCondition
	 * @return
	 */
	public List<ClientGroup> list(ClientGroupCondition testTableCondition){
		return  testTableCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param testTableCondition
	 * @param sorter
	 * @return
	 */
	public List<ClientGroup> list(ClientGroupCondition testTableCondition, Sorter sorter){
		return  testTableCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param testTableCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<ClientGroup> list(ClientGroupCondition testTableCondition, Range range, Sorter sorter){
		PaginationSupport<ClientGroup> testTablePaginationSupport = (PaginationSupport<ClientGroup>)testTableCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param testTableCondition
	 * @return
	 */
	public Long countByCondition(ClientGroupCondition testTableCondition){
		return   testTableCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param testTableCondition
	 * @return
	 */
	public List<ClientGroup> listCustom(ClientGroupCondition testTableCondition){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory));
	}

	/**
	 * 获取相应数量
	 * @param testTableCondition
	 * @param num
	 * @return
	 */
	public List<ClientGroup> listCustom(ClientGroupCondition testTableCondition, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param testTableCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<ClientGroup> listCustom(ClientGroupCondition testTableCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param testTable
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(ClientGroup testTable) throws Exception{
		testTable = this.create(testTable);
		Map<String, Object> map = new HashMap<>();
		map.put("id", testTable.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param clientGroup
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(ClientGroup clientGroup) throws Exception{
		ClientGroup testTable = this.get(clientGroup.getId());
		if(testTable == null){
			throw new Exception("未找到对象");
		}
		testTable.setClientGroupName(clientGroup.getClientGroupName());
		this.update(testTable);
	}

	/**
	 * 获取列表下拉选择框
	 * @return
	 */
	public List<Map<String, String>> getSelect(){
		ClientGroupCondition clientGroupCondition = new ClientGroupCondition();
		List<ClientGroup> userList = this.list(clientGroupCondition, new Sorter().asc("client_group_name"));
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for(ClientGroup clientGroup : userList){
			Map<String, String> map = new HashMap<>();
			map.put(clientGroup.getId() + "", clientGroup.getClientGroupName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 获取列表下拉选择框，客户所在组别名称->约访员工工号->约访员工姓名，用于转移客户时显示工号+姓名
	 * @return
	 */
	public List<Map<String, String>> getSelectAddWorkerName(){
		ClientGroupCondition clientGroupCondition = new ClientGroupCondition();
		List<ClientGroup> userList = this.list(clientGroupCondition, new Sorter().asc("client_group_name"));
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for(ClientGroup clientGroup : userList){
			Map<String, String> map = new HashMap<>();
			//当只存在组别而不存在对应工号时，做空处理
			String tempWorkerName=userService.getByWorkNumber(clientGroup.getClientGroupName())!=null?userService.getByWorkNumber(clientGroup.getClientGroupName()).getName():"";
			map.put(clientGroup.getId() + "", clientGroup.getClientGroupName() + " - " + tempWorkerName);
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		ClientGroup testTable = this.get(id);
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
