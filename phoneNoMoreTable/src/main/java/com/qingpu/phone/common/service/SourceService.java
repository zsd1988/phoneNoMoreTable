package com.qingpu.phone.common.service;

import com.qingpu.phone.common.condition.SourceCondition;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.entity.Source;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("sourceService")
@Transactional
public class SourceService {

	private static Logger logger = Logger.getLogger(SourceService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param source
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Source create(Source source) throws Exception{
		if(source != null && StringUtils.isBlank(source.getId())){
			source.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(source);
		return (Source) baseDao.save(source);
	}


	/**
	 * 更新
	 * @param source
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(Source source)  throws Exception {
		CommonUtils.checkEntity(source);
		source.setUpdateTime(new Date());
		baseDao.update(source);
	}


	/**
	 * 删除
	 * @param source
	 */
	public void delete(Source source) {
		source.setIsDel(true);
		baseDao.update(source);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Source get(String id) {
		if(id == null || id.equals("")){
			return null;
		}
		return (Source)baseDao.get(Source.class, id);
	}


	/**
	 * 按条件查找
	 * @param sourceCondition
	 * @return
	 */
	public List<Source> list(SourceCondition sourceCondition){
		return  sourceCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param sourceCondition
	 * @param sorter
	 * @return
	 */
	public List<Source> list(SourceCondition sourceCondition, Sorter sorter){
		return  sourceCondition.list(sessionFactory, sorter);
	}

	/**
	 * 按条件分页查询
	 * @param sourceCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Source> list(SourceCondition sourceCondition, Range range, Sorter sorter){
		PaginationSupport<Source> sourcePaginationSupport = (PaginationSupport<Source>)sourceCondition.pageList(sessionFactory, sorter, range);
		return sourcePaginationSupport;
	}

	/**
	 * 获取数量
	 * @param sourceCondition
	 * @return
	 */
	public Long countByCondition(SourceCondition sourceCondition){
		return   sourceCondition.countByCondition(sessionFactory);
	}

	/**
	 * 自定义复杂condition查询
	 * @param sourceCondition
	 * @return
	 */
	public List<Object[]> listCustom(SourceCondition sourceCondition){
		return  sourceCondition.list(sessionFactory);
	}

	/**
	 * 获取相应数量
	 * @param sourceCondition
	 * @param num
	 * @return
	 */
	public List<Object[]> listCustom(SourceCondition sourceCondition, int num){
		return  sourceCondition.list(sessionFactory, num);
	}

	/**
	 * 获取资源图片
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public String getSourceImg(String id) throws Exception{
		if(StringUtils.isBlank(id)){
			throw new Exception("请传入资源id");
		}
		String sourceImg = null;
		Source source = this.get(id);
		if(source != null){
			sourceImg = source.getSourceImg();
		}
		return sourceImg;
	}


	@Transactional(rollbackFor = Exception.class)
	public String getTwoDimensionCodeBySourceId(String id, String content) throws Exception{
		if(StringUtils.isBlank(id)){
			throw new Exception("请传入资源id");
		}

		return "1";
	}
}
