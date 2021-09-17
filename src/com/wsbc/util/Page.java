package com.wsbc.util;

/**
 * 分页所需要的参数
 * @author yxm
 */
public class Page {

	/**
	 * 每页大小
	 */
	private Integer pageSize = 5;
	
	/**
	 * 总页数
	 */
	private Integer totalPage;
	
	/**
	 * 当前页
	 */
	private Integer currentPage = 1;
	
	/**
	 * 总记录数
	 */
	private Integer count;
	
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		if(count%pageSize == 0){
			this.totalPage = count/pageSize;
		}else{
			this.totalPage = count/pageSize + 1;
		}
		this.count = count;
	}
	
	/**
	 * 方法描述：获得本页显示的第一条记录在总记录里的位置
	 * @data 2016年4月12日  下午6:02:12
	 * @author yxm
	 */
	public int getFirstCount(){
		if(currentPage>totalPage){
			currentPage = totalPage;
		}
		if(currentPage<1){
			currentPage = 1;
		}
		int temp = pageSize * (currentPage - 1)+1;
		if( temp < 0){
			temp = 0;
		}
		return temp;
	}
	
	/**
	 * 方法描述：获得本页显示的最后一条记录在总记录里的位置
	 * @data 2016年4月12日  下午6:03:07
	 * @author yxm
	 */
	public int getLastCount(){
		if(currentPage>totalPage){
			currentPage = totalPage;
		}
		if(currentPage<1){
			currentPage = 1;
		}
		int temp = pageSize * currentPage;
		if(temp > count){
			temp = count;
		}
		return temp;
	}
	
	/**
	 * 
	 * 方法描述：获得上一页
	 * @data 2016年4月13日  上午9:20:15
	 * @author yxm
	 */
	public int getPrePage(){
		if(currentPage <=1){
			return 1;
		}
		return currentPage-1;
	}
	
	/**
	 * 方法描述：获得下一页
	 * @data 2016年4月13日  上午9:20:28
	 * @author yxm
	 */
	public int getNextPage(){
		if( currentPage >= totalPage){
			return currentPage;
		}
		return currentPage + 1;
	}
	
}
