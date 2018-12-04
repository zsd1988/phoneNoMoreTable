package com.qingpu.phone.common.utils.criteria;

import com.qingpu.phone.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by qp on 2017-08-16.
 */
public class QPCondition {

    public Criteria criteria;

    private Sorter sorter;

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    /**
     * 初始化一些必要的条件
     * @param sessionFactory
     */
    public void initCriteria(SessionFactory sessionFactory){
        if(this.sorter != null){
            List<Sorter.Sort> sortList = sorter.getSorts();
            for(Sorter.Sort sort : sortList){
                if(sort.type == Sorter.Type.ASCENDING){
                    criteria.addOrder(Order.asc(sort.property));
                }else{
                    criteria.addOrder(Order.desc(sort.property));
                }
            }
        }
    }



    public void eq(String propertyName, Object value) {
        if (StringUtil.isNullOrEmpty(value))
            return;
        this.criteria.add( Restrictions.eq(propertyName, value));
    }

    public void ne(String propertyName, Object value) {
        if (StringUtil.isNullOrEmpty(value))
            return;
        this.criteria.add( Restrictions.ne(propertyName, value));
    }

    public void in(String propertyName, Collection value) {
        if ((value == null) || (value.size() == 0)) {
            return;
        }
        if(value.size() == 1){
            this.criteria.add( Restrictions.eq(propertyName, value.iterator().next()));
        }else{
            this.criteria.add( Restrictions.in(propertyName, value) );
        }
    }

    public void notIn(String propertyName, Collection value) {
        if ((value == null) || (value.size() == 0)) {
            return;
        }
        if(value.size() == 1){
            this.criteria.add( Restrictions.ne(propertyName, value.iterator().next()));
        }else{
            this.criteria.add(Restrictions.not( Restrictions.in(propertyName, value)) );
        }
    }

    public void ge(String propertyName, Number value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.ge(propertyName, value) );
    }

    public void gt(String propertyName, Number value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.gt(propertyName, value) );
    }

    public void gt(String propertyName, Date value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.gt(propertyName, value) );
    }

    public void ge(String propertyName, Date value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.ge(propertyName, value) );
    }

    public void le(String propertyName, Number value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.le(propertyName, value) );
    }

    public void le(String propertyName, Date value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.le(propertyName, value) );
    }

    public void lt(String propertyName, Number value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.lt(propertyName, value) );
    }

    public void lt(String propertyName, Date value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return;
        }
        this.criteria.add( Restrictions.lt(propertyName, value) );
    }

    public void between(String propertyName, Number lo, Number go) {
        if (!(StringUtil.isNullOrEmpty(lo)))
            ge(propertyName, lo);

        if (!(StringUtil.isNullOrEmpty(go)))
            le(propertyName, go);
    }

    public void between(String propertyName, Date lo, Date go) {
        if (!StringUtil.isNullOrEmpty(lo) && !StringUtil.isNullOrEmpty(go)) {
            this.criteria.add( Restrictions.between(propertyName, lo, go) );
        }
    }

    public void isNotNull(String propertyName, Boolean isNotNull) {
        if (StringUtil.isNullOrEmpty(isNotNull))
            return;
        this.criteria.add( Restrictions.isNotNull(propertyName) );
    }

    public void isNull(String propertyName, Boolean isNull) {
        if (StringUtil.isNullOrEmpty(isNull))
            return;
        this.criteria.add( Restrictions.isNull(propertyName) );
    }

    public void like(String propertyName, String value, MatchMode matchMode ) {
        if (StringUtils.isBlank(value))
            return;
        this.criteria.add( Restrictions.like(propertyName, value, matchMode) );
    }

    /**
     * 按条件查找
     * @param sessionFactory
     * @return
     */
    public List list(SessionFactory sessionFactory){
        this.initCriteria(sessionFactory);
        return criteria.list();
    }


    /**
     * 指定获取前面多少条
     * @param sessionFactory
     * @param num
     * @return
     */
    public List list(SessionFactory sessionFactory, int num){
        this.initCriteria(sessionFactory);
        criteria.setMaxResults(num);
        return criteria.list();
    }

    /**
     * 按条件查找并排序
     * @param sessionFactory
     * @param sorter
     * @return
     */
    public List list(SessionFactory sessionFactory, Sorter sorter){
        this.sorter = sorter;
        this.initCriteria(sessionFactory);
        return criteria.list();
    }


    /**
     * 获取按条件查询的数量
     * @param sessionFactory
     * @return
     */
    public List<Double> sumListByCondition(SessionFactory sessionFactory){
        this.initCriteria(sessionFactory);
        List<Double> doubleList =  (List<Double>)criteria.list();
        return doubleList;
    }


    /**
     * 指定获取前面多少条
     * @param sessionFactory
     * @param sorter
     * @param num
     * @return
     */
    public List list(SessionFactory sessionFactory, Sorter sorter, int num){
        this.sorter = sorter;
        this.initCriteria(sessionFactory);
        criteria.setMaxResults(num);
        return criteria.list();
    }

    /**
     * 分页查找
     * @param sessionFactory
     * @param sorter
     * @param range
     * @return
     */
    public PaginationSupport<?>  pageList(SessionFactory sessionFactory, Sorter sorter, Range range){
        this.sorter = sorter;
        this.initCriteria(sessionFactory);
        PaginationSupport<?> paginationSupport = new PaginationSupport<>();
        if(range != null){
            Long totalCount =  (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
            criteria.setProjection(null);
            criteria.setFirstResult(range.getStart());
            criteria.setMaxResults(range.getLimit());
            List list = criteria.list();
            paginationSupport.setItems(list);
            paginationSupport.setTotalCount(Integer.parseInt(totalCount + ""));
            paginationSupport.setOffset(range.getStart()+1);
            paginationSupport.setPageSize(range.getLimit());
            paginationSupport.setTotalPage(paginationSupport.getTotalCount(),0,range.getLimit());
            if((paginationSupport.getPageSize()+paginationSupport.getOffset())>paginationSupport.getTotalCount()){
                paginationSupport.setSelectLimit(paginationSupport.getTotalCount());
            }else{
                paginationSupport.setSelectLimit(paginationSupport.getPageSize()+paginationSupport.getOffset());
            }

        }
        return paginationSupport;
    }

    /**
     * 获取按条件查询的数量
     * @param sessionFactory
     * @return
     */
    public Long countByCondition(SessionFactory sessionFactory){
        this.initCriteria(sessionFactory);
        Long totalCount =  (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
        return totalCount;
    }


    /**
     * 获取按照条件的数据总和
     * @param sessionFactory
     * @return
     */
    public Double sumByCondition(SessionFactory sessionFactory){
        this.initCriteria(sessionFactory);
        Double totalCount =  (Double)criteria.uniqueResult();
        if(totalCount==null){
            totalCount=0.0;
        }
        return totalCount;
    }


    /**
     * 获取按条件查询的数量
     * @param sessionFactory
     * @return
     */
    public Object sumCondition(SessionFactory sessionFactory){
        this.initCriteria(sessionFactory);
        Object totalCount =  criteria.uniqueResult();
        return totalCount;
    }


}
