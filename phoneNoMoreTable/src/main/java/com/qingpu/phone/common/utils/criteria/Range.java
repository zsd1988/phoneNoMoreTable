package com.qingpu.phone.common.utils.criteria;

import java.io.Serializable;

/**
 * Created by qp on 2017-08-17.
 */
public class Range implements Serializable {
    private static final long serialVersionUID = -3472933788362913672L;
    private int start;
    private int limit;

    public Range(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getEnd() {
        return this.start + this.limit;
    }
}
