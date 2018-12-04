package com.qingpu.phone.common.utils.criteria;

import org.apache.commons.lang3.StringUtils;

public class PaginationParams {
    Integer pageIndex;
    Integer pageSize;
    String sortName;
    String sortOrder;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Range getRange() {
        Range range = null;
        if(pageIndex != null && pageSize != null){
            range = new Range((pageIndex - 1) * pageSize, pageSize);
        }
        return range;
    }

    public Sorter getSorter(){
        Sorter sorter = null;
        if(StringUtils.isNotBlank(sortName)){
            if(StringUtils.isNotBlank(sortOrder)){
                sorter = new Sorter();
                if(sortOrder.equals("asc")){
                    sorter.asc(sortName);
                }else{
                    sorter.desc(sortName);
                }
            }
        }
        return sorter;
    }
}
