package com.qingpu.phone.common.entity;


/**
 * 
 * @author zw
 *
 */
public class PageInfo {
	
	private Integer pageIndex=1;
	
	private Integer pageMax= 10;
	
	private Integer pageTotal=0;	
	
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public PageInfo(){}
	
	public PageInfo(Integer pageIndex, Integer pageMax) {
		this.pageIndex = pageIndex;
		this.pageMax = pageMax;		
	}	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageMax() {
		return pageMax;
	}
	public void setPageMax(Integer pageMax) {
		this.pageMax = pageMax;
	}
	public Integer getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}
	
	public Integer getFirstRecordIndex() {
		Integer fri=(pageIndex-1)*pageMax;
		return fri;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getPageNum(){
		if(pageMax==0)
			return 0;
		Integer pageDiv=pageTotal/pageMax;
		Integer pageMod=pageTotal%pageMax;
		if(pageMod==0)
			return pageDiv;
		if(pageMod>0)
			return pageDiv+1;
		return 0;		
	}
}
