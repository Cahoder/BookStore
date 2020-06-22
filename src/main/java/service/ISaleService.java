package service;

import java.sql.Timestamp;
import java.util.List;

import exception.SaleExistException;
import model.Sale;

/**
 * 	销售榜单业务逻辑层接口
 * @author CAIHONGDE
 */
public interface ISaleService {
	
	/**
	 * 	获取销售榜单数据,此方法包含上架的和下架的
	 * @return
	 * @throws SaleExistException
	 */
	public List<Sale> getSales() throws SaleExistException;
	
	/**
	 * 	获取上架的商品销售榜单数据
	 * @return
	 * @throws SaleExistException
	 */
	public List<Sale> getOnSales() throws SaleExistException;
	
	/**
	 * 	分页获取销售榜单数据,此方法包含上架的和下架的
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SaleExistException
	 */
	public List<Sale> getSales(Integer pageNo, Integer pageSize) throws SaleExistException;
	
	/**
	 * 	分页获取上架的商品销售榜单数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SaleExistException
	 */
	public List<Sale> getOnSales(Integer pageNo, Integer pageSize) throws SaleExistException;
	
	/**
	 * 	获取一段时间内的销售榜单数据,此方法包含上架的和下架的
	 * @param start
	 * @param end
	 * @return
	 * @throws SaleExistException
	 */
	public List<Sale> getBetweenSales(Timestamp start, Timestamp end) throws SaleExistException;
	
	/**
	 * 	获取一段时间内上架的商品销售榜单数据
	 * @param start
	 * @param end
	 * @return
	 * @throws SaleExistException
	 */
	public List<Sale> getBetweenOnSales(Timestamp start, Timestamp end) throws SaleExistException;

	/**
	 * 	获取销售榜单数据总数量,此方法包含上架的和下架的
	 * @return
	 */
	public Integer getSalesCount();
	
	/**
	 * 	获取上架的商品销售榜单数据总数量
	 * @return
	 */
	public Integer getOnSalesCount();
}
