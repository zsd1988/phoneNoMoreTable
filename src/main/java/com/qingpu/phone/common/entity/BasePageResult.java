package com.qingpu.phone.common.entity;


import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class BasePageResult extends Object{
    List<T> result;
    Integer totalCount;

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
