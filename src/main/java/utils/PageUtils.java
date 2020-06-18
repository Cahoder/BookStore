package utils;

/**
 * 	分页管理工具
 * @author CAIHONGDE
 */
public class PageUtils {
	
	/**
	 * 	当前页号
	 */
	private Integer pageNo;
	
	/**
	 * 	最大页号
	 */
	private Integer totalNo;
	
	/**
	 * 	每页数目
	 */
	private Integer pageSize;
	
	/**
	 * 	总数目
	 */
	private Integer totalSize;

	/**
	 * 	计算合适的页数
	 */
	private void compute() {
		totalNo = (totalSize%pageSize==0)?totalSize/pageSize:totalSize/pageSize+1; 	//最大页数
		if(pageNo>totalNo)
			pageNo = totalNo;
	}

	/**
	 * @param pageNo 当前页号
	 * @param pageSize 每页数量
	 * @param totalSize 总数量
	 */
	public PageUtils(Integer pageNo, Integer pageSize, Integer totalSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		compute();
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getTotalNo() {
		return totalNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getTotalSize() {
		return totalSize;
	}
	
}
