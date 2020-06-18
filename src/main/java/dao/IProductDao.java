package dao;

import java.util.List;

import model.Product;

//通用的产品dao层接口
public interface IProductDao {
	
	/**
	 * 	建立连接数据库获取商品列表的非分页数据
	 * @return
	 */
	public List<Product> getProductsData();
	
	/**
	 * 	建立连接数据库获取商品列表的分页数据
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	public List<Product> getProductsData(Integer pageNo, Integer pageSize);
	
	/**
	 * 	建立连接数据库根据id获取指定商品
	 * @param id
	 * @return
	 */
	public Product getProductByIdData(String id);
	
	/**
	 * 	建立连接数据库根据ids批量获取商品
	 * @param ids
	 * @return
	 */
	public List<Product> getProductByIdsData(String[] ids);
	
	/**
	 * 	建立连接数据库获取商品列表总数
	 * @return
	 */
	public Integer getProductsCount();
	
	/**
	 * @param product 新商品JavaBean
	 * @return 建立连接数据库插入新商品的影响函数,0为插入失败
	 */
	public Integer addProductData(Product product);
	
	/**
	 * @param like
	 * @return 建立连接数据库模糊匹配商品列表的数据
	 */
	public List<Product> nameFilterProductsData(String like);
	
	/**
	 * @param min
	 * @param max
	 * @return 建立连接数据库匹配价格区间商品列表的数据
	 */
	public List<Product> priceFilterProductsData(Integer min, Integer max);
	
	/**
	 * @param category
	 * @return 建立连接数据库匹配分类商品列表的数据
	 */
	public List<Product> categoryFilterProductsData(String category);
	
	/**
	 * 	删除指定的商品
	 * @param product
	 * @return
	 */
	public Integer delProduct(Product product);
	
	/**
	 * 	批量删除指定的商品
	 * @param products
	 * @return
	 */
	public Integer delProduct(List<Product> products);
	
	/**
	 * 	更新指定的商品
	 * @param product
	 * @return
	 */
	public Integer updateProduct(Product product);
	
	/**
	 * 	批量更新指定的商品
	 * @param products
	 * @return
	 */
	public Integer updateProduct(List<Product> products);
	
}
