package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.PrologueCondition;
import com.qingpu.phone.systemManager.entity.Prologue;
import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.user.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("prologueService")
@Transactional
public class PrologueService {
	private static Logger logger = Logger.getLogger(PrologueService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param prologue
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Prologue create(Prologue prologue) throws Exception{
		if(prologue != null && StringUtils.isBlank(prologue.getId())){
			prologue.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(prologue);
		return (Prologue) baseDao.save(prologue);
	}


	/**
	 * 更新
	 * @param prologue
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Prologue prologue)  throws Exception {
		CommonUtils.checkEntity(prologue);
		baseDao.update(prologue);
	}


	/**
	 * 删除
	 * @param prologue
	 */
	public void delete(Prologue prologue) {
		prologue.setIsDel(true);
		prologue.setDelTime(new Date());
		baseDao.update(prologue);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Prologue get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (Prologue)baseDao.get(Prologue.class, id);
	}


	/**
	 * 按条件查找
	 * @param prologueCondition
	 * @return
	 */
	public List<Prologue> list(PrologueCondition prologueCondition){
		return  prologueCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param prologueCondition
	 * @param sorter
	 * @return
	 */
	public List<Prologue> list(PrologueCondition prologueCondition, Sorter sorter){
		return  prologueCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param prologueCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Prologue> list(PrologueCondition prologueCondition, Range range, Sorter sorter){
		PaginationSupport<Prologue> testTablePaginationSupport = (PaginationSupport<Prologue>) prologueCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param prologueCondition
	 * @return
	 */
	public Long countByCondition(PrologueCondition prologueCondition){
		return   prologueCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param prologueCondition
	 * @return
	 */
	public List<Object[]> listCustom(PrologueCondition prologueCondition){
		return  prologueCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param prologueCondition
	 * @param num
	 * @return
	 */
	public List<Prologue> listCustom(PrologueCondition prologueCondition, int num){
		return  AutoEvaluationUtil.toClassList(prologueCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param prologueCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<Prologue> listCustom(PrologueCondition prologueCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(prologueCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param prologue
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(Prologue prologue) throws Exception{
		prologue = this.create(prologue);
		Map<String, Object> map = new HashMap<>();
		map.put("id", prologue.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsPrologue
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(Prologue paramsPrologue) throws Exception{
		Prologue prologue = this.get(paramsPrologue.getId());
		if(prologue == null){
			throw new Exception("未找到对象");
		}
		this.update(prologue);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		Prologue prologue = this.get(id);
		if(prologue == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			prologue.setOperatorId(user.getId());
		}
		this.delete(prologue);
	}


	/**
	 * 设置开场白内存对象
	 * @param projectId
	 * @param groupCallId
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setPrologueMap(String projectId, String groupCallId)  throws Exception {
		PrologueCondition prologueCondition = new PrologueCondition();
		prologueCondition.setProjectId(projectId);
		List<Prologue> prologueList = this.list(prologueCondition);
		Double totalRate = 0.0;
		for(Prologue prologue : prologueList){
			totalRate += prologue.getRate();
		}
		Map<String, Double> map = new HashMap<>();
		for(Prologue prologue : prologueList){
			Double value = 1.0/prologueList.size();
			if(totalRate != 0){
				value = prologue.getRate()/totalRate;
			}
			map.put(prologue.getId(), value);

			List<Object> stringList = new ArrayList<>();
			stringList.add(prologue.getPath1());
			stringList.add(prologue.getPath2());
			stringList.add(prologue.getPath3());
			stringList.add(prologue.getName());
			stringList.add(0);
			CallPhoneListener._prologueMap.put(prologue.getId(), stringList);
		}
		CallPhoneListener._groupCallPrologueMap.put(groupCallId, map);
	}


	/**
	 * 更新信息
	 * @param prologueJsonArray
	 * @param projectId
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updatePrologue(String prologueJsonArray, String projectId)  throws Exception {
		if(StringUtils.isNotBlank(prologueJsonArray)){
			JSONArray jsonArray = JSONArray.fromObject(prologueJsonArray);
			if(jsonArray != null){
				List<Prologue> prologueList = JSONArray.toList(jsonArray, new Prologue(), new JsonConfig());
				for(Prologue prologue : prologueList){
					String id = prologue.getId();
					Prologue newPrologue = new Prologue();
					Boolean isCreate = true;
					if(StringUtils.isNotBlank(id)){
						newPrologue = this.get(id);
						if(newPrologue != null){
							isCreate = false;
						}else{
							newPrologue = new Prologue();
						}
					}
					newPrologue.setProjectId(projectId);
					newPrologue.setName(prologue.getName());
					newPrologue.setPath1(prologue.getPath1());
					newPrologue.setPath2(prologue.getPath2());
					newPrologue.setPath3(prologue.getPath3());
					newPrologue.setRate(prologue.getRate());
					if(isCreate){
						this.create(newPrologue);
					}else{
						this.update(newPrologue);
					}
				}
			}
		}
	}
}
