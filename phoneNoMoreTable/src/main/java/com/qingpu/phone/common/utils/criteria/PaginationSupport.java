package com.qingpu.phone.common.utils.criteria;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by qp on 2017-08-17.
 */
public class PaginationSupport<T> {
    public static final int DEFAULT_CURRENT_PAGE = 1;
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFALUT_INDEX = "center";
    private int offset;
    private int pageSize;
    private int currentPage;
    private int totalCount;
    private int totalPage;
    private List<T> items;
    private String index;
    private int[] indexes;
    private int  selectLimit;               //查询的条数

    public PaginationSupport() {
        this.offset = 0;
        this.pageSize = 10;
        this.currentPage = 1;
        this.totalCount = 0;
        this.items = new LinkedList();
        this.index = "center";
        this.indexes = new int[0];
    }

    public PaginationSupport(List<T> list) {
        this(list, list.size());
    }

    public PaginationSupport(Collection<T> list) {
        this((List)(new LinkedList(list)));
    }

    public PaginationSupport(T[] a) {
        this(Arrays.asList(a));
    }

    public PaginationSupport(List<T> items, int totalCount) {
        this.offset = 0;
        this.pageSize = 10;
        this.currentPage = 1;
        this.totalCount = 0;
        this.items = new LinkedList();
        this.index = "center";
        this.indexes = new int[0];
        this.setPageSize(10);
        this.setTotalCount(totalCount);
        this.setItems(items);
        this.setOffset(0);
    }

    public PaginationSupport(List<T> items, int totalCount, int offset) {
        this.offset = 0;
        this.pageSize = 10;
        this.currentPage = 1;
        this.totalCount = 0;
        this.items = new LinkedList();
        this.index = "center";
        this.indexes = new int[0];
        this.setPageSize(10);
        this.setTotalCount(totalCount);
        this.setItems(items);
        this.setOffset(offset);
    }

    public PaginationSupport(List<T> items, int totalCount, int offset, int pageSize) {
        this.offset = 0;
        this.pageSize = 10;
        this.currentPage = 1;
        this.totalCount = 0;
        this.items = new LinkedList();
        this.index = "center";
        this.indexes = new int[0];
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        this.setItems(items);
        this.setOffset(offset);
        this.setCurrentPageNo(totalCount, offset, pageSize);
        this.setTotalPage(totalCount, offset, pageSize);
    }

    public PaginationSupport(List<T> items, int totalCount, int offset, int pageSize, String index) {
        this.offset = 0;
        this.pageSize = 10;
        this.currentPage = 1;
        this.totalCount = 0;
        this.items = new LinkedList();
        this.index = "center";
        this.indexes = new int[0];
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        this.setItems(items);
        this.setOffset(offset);
        this.setIndex(index);
        this.setTotalPage(totalCount, offset, pageSize);
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = (List)(items == null?new LinkedList():items);
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize == -1) {
            pageSize = 2147483647;
        }

        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        if(totalCount > 0) {
            this.totalCount = totalCount;
            int count = totalCount / this.pageSize;
            if(totalCount % this.pageSize > 0) {
                ++count;
            }

            this.indexes = new int[count];

            for(int i = 0; i < count; ++i) {
                this.indexes[i] = this.pageSize * i;
            }
        } else {
            this.totalCount = 0;
        }

    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int startIndex) {
        if(this.totalCount <= 0) {
            this.offset = 0;
        } else if(startIndex >= this.totalCount) {
            this.offset = this.indexes[this.indexes.length - 1];
        } else if(startIndex < 0) {
            this.offset = 0;
        } else {
            this.offset = this.indexes[startIndex / this.pageSize];
        }

    }

    public int getNextPage() {
        int p = this.getCurrentPageNo() + 1;
        if(p > this.totalPage) {
            p = this.totalPage;
        }

        return p;
    }

    public int getPreviousPage() {
        int p = this.getCurrentPageNo() - 1;
        if(p < 1) {
            p = 1;
        }

        return p;
    }

    public int getNextIndex() {
        int nextIndex = this.getOffset() + this.pageSize;
        return nextIndex >= this.totalCount?this.getOffset():nextIndex;
    }

    public int getPreviousIndex() {
        int previousIndex = this.getOffset() - this.pageSize;
        return previousIndex < 0?0:previousIndex;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public T[] getArrayItems(T[] objs) {
        return this.items != null?this.items.toArray(objs):null;
    }

    public int getCurrentPageNo() {
        return this.currentPage;
    }

    public void setCurrentPageNo(int totalCount, int offest, int maxPageItems) {
        if(totalCount != 0 && offest != 0) {
            this.currentPage = offest / maxPageItems + 1;
        } else {
            this.currentPage = 1;
        }

    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalCount, int offest, int maxPageItems) {
        if(totalCount == 0) {
            this.totalPage = 0;
        } else if(totalCount % maxPageItems > 0) {
            this.totalPage = totalCount / maxPageItems + 1;
        } else {
            this.totalPage = totalCount / maxPageItems;
        }

    }

    public int size() {
        return this.getItems().size();
    }

    public boolean isEmpty() {
        return this.getItems().size() == 0;
    }

    public T getItem(int index) {
        return this.getItems().get(index);
    }

    public int getSelectLimit() {
        return selectLimit;
    }

    public void setSelectLimit(int selectLimit) {
        this.selectLimit = selectLimit;
    }
}
