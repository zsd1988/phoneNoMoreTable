package com.qingpu.phone.systemManager.service;

import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.systemManager.condition.ProjectCondition;
import com.qingpu.phone.systemManager.entity.Project;
import com.qingpu.phone.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("projectService")
@Transactional
public class ProjectService {
	private static Logger logger = Logger.getLogger(ProjectService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	PrologueService prologueService;

	/**
	 * 创建
	 * @param project
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Project create(Project project) throws Exception{
		if(project != null && StringUtils.isBlank(project.getId())){
			project.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(project);
		return (Project) baseDao.save(project);
	}


	/**
	 * 更新
	 * @param project
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Project project)  throws Exception {
		CommonUtils.checkEntity(project);
		baseDao.update(project);
	}


	/**
	 * 删除
	 * @param project
	 */
	public void delete(Project project) {
		project.setIsDel(true);
		project.setDelTime(new Date());
		baseDao.update(project);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Project get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (Project)baseDao.get(Project.class, id);
	}


	/**
	 * 按条件查找
	 * @param projectCondition
	 * @return
	 */
	public List<Project> list(ProjectCondition projectCondition){
		return  projectCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param projectCondition
	 * @param sorter
	 * @return
	 */
	public List<Project> list(ProjectCondition projectCondition, Sorter sorter){
		return  projectCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param projectCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Project> list(ProjectCondition projectCondition, Range range, Sorter sorter){
		PaginationSupport<Project> testTablePaginationSupport = (PaginationSupport<Project>) projectCondition.pageList(sessionFactory, sorter, range);
		return testTablePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param projectCondition
	 * @return
	 */
	public Long countByCondition(ProjectCondition projectCondition){
		return   projectCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param projectCondition
	 * @return
	 */
	public List<Object[]> listCustom(ProjectCondition projectCondition){
		return  projectCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param projectCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(ProjectCondition projectCondition, int num){
		return  projectCondition.list(sessionFactory, num);
	}

	/**
	 * 根据参数创建
	 * @param project
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createByParams(Project project) throws Exception{
		project = this.create(project);
		Map<String, Object> map = new HashMap<>();
		map.put("id", project.getId());
		prologueService.updatePrologue(project.getPrologueJsonArray(), project.getId());
		return map;
	}

	/**
	 * 根据参数更新
	 * @param paramsProject
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(Project paramsProject) throws Exception{
		Project project = this.get(paramsProject.getId());
		if(project == null){
			throw new Exception("未找到对象");
		}
		project.setName((paramsProject.getName()));
		project.setIntroduceVoiceUrl(paramsProject.getIntroduceVoiceUrl());
		project.setRetrieveVoiceUrl(paramsProject.getRetrieveVoiceUrl());
		this.update(project);
		prologueService.updatePrologue(paramsProject.getPrologueJsonArray(), paramsProject.getId());
	}

	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delById(String id, HttpServletRequest request) throws Exception{
		Project project = this.get(id);
		if(project == null){
			throw new Exception("删除失败：未找到数据");
		}
		User user = (User)request.getSession().getAttribute("_user");
		if(user != null){
			project.setOperatorId(user.getId());
		}
		this.delete(project);
	}

	/**
	 * 获取表下拉选择框
	 * @return
	 */
	public List<Map<String, String>> getSelect(){
		ProjectCondition projectCondition = new ProjectCondition();
		List<Project> projectList = this.list(projectCondition, new Sorter().desc("create_time"));
		List<Map<String, String>> mapList = new ArrayList<>();
		for(Project project : projectList){
			Map<String, String> map = new HashMap<>();
			map.put(project.getId(), project.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 获取name并写入map
	 * @param projectId
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public String getNameByMap(String projectId, Map<String, String> map)throws Exception{
		String projectName = null;
		if(StringUtils.isNotBlank(projectId)){
			if(map.containsKey(projectId)){
				projectName = map.get(projectId);
			}else{
				Project project = this.get(projectId);
				if(project != null){
					projectName = project.getName();
				}
				map.put(projectId, projectName);
			}
		}
		return projectName;
	}

	/**
	 * 根据Id获取名称
	 * @param id
	 * @return
	 */
	public String getName(String id){
		Project project = this.get(id);
		String name = null;
		if(project != null){
			name = project.getName();
		}
		return name;
	}

	/**
	 * 项目及各类资源统计
	 * @return
	 * @throws Exception
	 */
	public List<Project> projectClientNum(){
		String strSql="SELECT lis.project_id id,\n" +
				"sum(case lis.is_distribute when 0 then (case lis.import_tag when 'A' then lis.count else 0 end) else 0 end) sumA  ,\n" +
				"sum(case lis.is_distribute when 0 then (case lis.import_tag when 'B' then lis.count else 0 end) else 0 end) sumB  ,\n" +
				"sum(case lis.is_distribute when 0 then (case lis.import_tag when 'C' then lis.count else 0 end) else 0 end) sumC  ,\n" +
				"sum(case lis.is_distribute when 0 then (case lis.import_tag when 'D' then lis.count else 0 end) else 0 end) sumD  ,\n" +
				"sum(case lis.is_distribute when 0 then (case lis.import_tag when 'E' then lis.count else 0 end) else 0 end) sumE  ,\n" +
				"sum(case lis.is_distribute when 1 then lis.count else 0 end) sumDistribute\n" +
				"from (SELECT project_id,import_tag,count(1) count,is_distribute FROM client  GROUP BY import_tag,project_id,is_distribute ORDER BY project_id) lis GROUP BY lis.project_id;";
		@SuppressWarnings("unchecked")
		//List<Object[]> list1 =sessionFactory.getCurrentSession().createSQLQuery(strSql).list(); //java entity申明类型与sql返回类型必须匹配才可用
		List<Project> list = sessionFactory.getCurrentSession().createSQLQuery(strSql).setResultTransformer(new AliasToBeanResultTransformer(Project.class)).list();
		return list;
	}
}
