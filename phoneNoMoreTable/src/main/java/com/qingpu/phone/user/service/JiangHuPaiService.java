package com.qingpu.phone.user.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.StringUtil;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.condition.JiangHuPaiCondition;
import com.qingpu.phone.user.entity.JiangHuPai;
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

@Service("testTableService")
@Transactional
public class JiangHuPaiService {
	private static Logger logger = Logger.getLogger(JiangHuPaiService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param jiangHuPai
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JiangHuPai create(JiangHuPai jiangHuPai) throws Exception{
		CommonUtils.checkEntity(jiangHuPai);
		return (JiangHuPai) baseDao.save(jiangHuPai);
	}


	/**
	 * 更新
	 * @param jiangHuPai
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(JiangHuPai jiangHuPai)  throws Exception {
		CommonUtils.checkEntity(jiangHuPai);
		baseDao.update(jiangHuPai);
	}


	/**
	 * 删除
	 * @param jiangHuPai
	 */
	public void delete(JiangHuPai jiangHuPai) {
		jiangHuPai.setIsDel(true);
		jiangHuPai.setDelTime(new Date());
		baseDao.update(jiangHuPai);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public JiangHuPai get(Integer id) {
		if(id == null){
			return null;
		}
		return (JiangHuPai)baseDao.get(JiangHuPai.class, id);
	}


	/**
	 * 按条件查找
	 * @param jiangHuPaiCondition
	 * @return
	 */
	public List<JiangHuPai> list(JiangHuPaiCondition jiangHuPaiCondition){
		return  jiangHuPaiCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param jiangHuPaiCondition
	 * @param sorter
	 * @return
	 */
	public List<JiangHuPai> list(JiangHuPaiCondition jiangHuPaiCondition, Sorter sorter){
		return  jiangHuPaiCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param jiangHuPaiCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<JiangHuPai> list(JiangHuPaiCondition jiangHuPaiCondition, Range range, Sorter sorter){
		PaginationSupport<JiangHuPai> testTablePaginationSupport = (PaginationSupport<JiangHuPai>) jiangHuPaiCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param jiangHuPaiCondition
	 * @return
	 */
	public Long countByCondition(JiangHuPaiCondition jiangHuPaiCondition){
		return   jiangHuPaiCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param jiangHuPaiCondition
	 * @return
	 */
	public List<Object[]> listCustom(JiangHuPaiCondition jiangHuPaiCondition){
		return  jiangHuPaiCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param jiangHuPaiCondition
	 * @param num
	 * @return
	 */
	public List<JiangHuPai> listCustom(JiangHuPaiCondition jiangHuPaiCondition, int num){
		return  AutoEvaluationUtil.toClassList(jiangHuPaiCondition.list(sessionFactory, num));
	}

	/**
	 * 按照排序获取相应数量
	 * @param jiangHuPaiCondition
	 * @param sorter
	 * @param num
	 * @return
	 */
	public List<JiangHuPai> listCustom(JiangHuPaiCondition jiangHuPaiCondition, Sorter sorter, int num){
		return  AutoEvaluationUtil.toClassList(jiangHuPaiCondition.list(sessionFactory, sorter, num));
	}

	/**
	 * 根据参数创建
	 * @param jiangHuPai
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(JiangHuPai jiangHuPai) throws Exception{
		jiangHuPai = this.create(jiangHuPai);
		Map<String, Object> map = new HashMap<>();
		map.put("id", jiangHuPai.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsJiangHuPai
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(JiangHuPai paramsJiangHuPai) throws Exception{
		JiangHuPai jiangHuPai = this.get(paramsJiangHuPai.getId());
		if(jiangHuPai == null){
			throw new Exception("未找到对象");
		}
		this.update(jiangHuPai);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(Integer id, HttpServletRequest request) throws Exception{
		JiangHuPai jiangHuPai = this.get(id);
		if(jiangHuPai == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			jiangHuPai.setOperatorId(user.getId());
		}
		this.delete(jiangHuPai);
	}

	/**
	 * 获取昵称
	 * @param paiId
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String getNameByMap(Integer paiId, Map<Integer, String> map){
		String projectName = null;
		if( paiId != null){
			if(map.containsKey(paiId)){
				projectName = map.get(paiId);
			}else{
				JiangHuPai project = this.get(paiId);
				if(project != null){
					projectName = project.getNickName();
				}
				map.put(paiId, projectName);
			}
		}
		return projectName;
	}

	/**
	 * 初始化派ids
	 * @param jiangHuPai
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void initGoldPaiIds(JiangHuPai jiangHuPai) throws Exception{
		Integer newId = jiangHuPai.getId();
		String newIdStr = newId + "";
		JiangHuPaiCondition jiangHuPaiCondition = new JiangHuPaiCondition();
		jiangHuPaiCondition.setRoleType(jiangHuPai.getRoleType());
		List<JiangHuPai> jiangHuPaiList = this.list(jiangHuPaiCondition, new Sorter().asc("id"));
		Integer maxId = 0;
		String newPaiIds = "";
		Integer minId = 10000;
		for(JiangHuPai jiangHuPai1 : jiangHuPaiList){
			Integer id = jiangHuPai1.getId();
			if(id > maxId){
				maxId = id;
			}
			if(id < minId){
				minId = id;
			}
			if(newId < id){
				newPaiIds += id + ",";
			}
		}
		Boolean isMax = false;
		if(maxId == newId.intValue()){
			isMax = true;
		}
		Boolean isMin = false;
		if(minId == newId.intValue()){
			isMin = true;
		}
		for(JiangHuPai jiangHuPai1 : jiangHuPaiList){
			Integer idMine = jiangHuPai1.getId();
			if(newId > idMine){
				newPaiIds += idMine + ",";
			}
			String paiIds = jiangHuPai1.getGoldPaiIds();
			if(StringUtils.isNotBlank(paiIds)){
				List<String> paiIdList = StringUtil.splitByComma(paiIds);
				if(isMax){
					int addIndex = 0;
					if(paiIdList.size() == 1){
						Integer id = Integer.parseInt(paiIdList.get(0));
						if(id < idMine){
							addIndex = 0;
						}else{
							addIndex = 1;
						}
					}else{
						int maxIndex = 0;
						maxId = 0;
						for(int i = 0; i < paiIdList.size(); i++){
							Integer id = Integer.parseInt(paiIdList.get(i));
							if(maxId < id){
								maxId = id;
								maxIndex = i;
							}
						}
						if(maxId < idMine){
							maxIndex = -1;
						}
						addIndex = maxIndex + 1;
					}
					paiIdList.add(addIndex, newIdStr);
				}else if(isMin){
					int addIndex = 0;
					if(paiIdList.size() == 1){
						Integer id = Integer.parseInt(paiIdList.get(0));
						if(id > idMine){
							addIndex = 1;
						}else{
							addIndex = 0;
						}
					}else{
						int minIndex = 0;
						minId = 10000;
						for(int i = 0; i < paiIdList.size(); i++){
							Integer id = Integer.parseInt(paiIdList.get(i));
							if(minId > id){
								minId = id;
								minIndex = i;
							}
						}
						if(minId > idMine){
							minIndex = paiIdList.size();
						}
						addIndex = minIndex;
					}
					paiIdList.add(addIndex, newIdStr);
				} else{
					int addIndex = 0;
					Boolean isCompareLittle = true;
					for(int i = 0; i < paiIdList.size(); i++){
						Integer id = Integer.parseInt(paiIdList.get(i));
						if(id > newId && isCompareLittle){
							int index = i - 1;
							if(index >= 0){
								addIndex = i;
								break;
							}else{
								isCompareLittle = false;
							}
						}
						if( ! isCompareLittle){
							if(id < newId){
								isCompareLittle = true;
							}
						}
					}
					if(addIndex == 0){
						if(idMine > newId){
							addIndex = paiIdList.size();
						}
					}
					paiIdList.add(addIndex, newIdStr);
				}
				paiIds = "";
				Boolean isNewIdFirst = null;
				for(String id : paiIdList){
					if(isNewIdFirst == null){
						if(id.equals(newIdStr)){
							isNewIdFirst = true;
						}else{
							isNewIdFirst = false;
						}
					}
					paiIds += id + ",";
				}
				if(isNewIdFirst != null && isNewIdFirst){
					// 防止出现新组的人数过少，subCount大于组数导致负数的出现
					jiangHuPai.setSubCount(0);
				}
				jiangHuPai1.setGoldPaiIds(StringUtil.subLastChar(paiIds));
				this.update(jiangHuPai1);
			}else{
				if(jiangHuPaiList.size() == 2 && idMine.intValue() != newId){
					jiangHuPai1.setGoldPaiIds(newId + "");
					this.update(jiangHuPai1);
				}
			}
		}
		jiangHuPai.setGoldPaiIds(StringUtil.subLastChar(newPaiIds));
		this.update(jiangHuPai);
	}
}
