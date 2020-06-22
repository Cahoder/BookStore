package dao;

import java.sql.Timestamp;
import java.util.List;

import model.Sale;

//通用的销售dao层接口
public interface ISaleDao {
	
	/**
	 * @return 建立连接数据库获取上架的商品销售记录列表的数据
	 */
	public List<Sale> getOnSalesData();
	
	/**
	 * @return 建立连接数据库获取所有商品销售记录列表的数据
	 */
	public List<Sale> getSalesData();

	/**
	 * 	建立连接数据库分页获取销售记录列表的数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Sale> getSalesData(Integer pageNo, Integer pageSize);
	
	/**
	 * 	建立连接数据库分页获取上架中商品销售记录列表的数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Sale> getOnSalesData(Integer pageNo, Integer pageSize);
	
	/**
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 建立连接数据库获取指定时间段内销售记录列表的数据
	 */
	public List<Sale> getSalesBetweenData(Timestamp start, Timestamp end);
	
	/**
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 建立连接数据库获取指定时间段内上架中商品销售记录列表的数据
	 */
	public List<Sale> getOnSalesBetweenData(Timestamp start, Timestamp end);
	
	/**
	 * 	建立连接数据库获取销售记录列表的总数量
	 * @return
	 */
	public Integer getSalesCount();
	
	/**
	 * 	建立连接数据库获取上架的商品销售记录列表的总数量
	 * @return
	 */
	public Integer getOnSalesCount();

}
