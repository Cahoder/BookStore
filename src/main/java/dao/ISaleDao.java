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
	 * @param timestamps 年-月
	 * @return 建立连接数据库获取指定时间段内销售记录列表的数据
	 */
	public List<Sale> getSalesData(Timestamp... timestamps);
	
	/**
	 * 	建立连接数据库获取销售记录列表的总数量
	 * @return
	 */
	public Integer getSalesCount();
}
