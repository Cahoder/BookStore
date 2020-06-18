package service;

import java.util.List;

import exception.ProductExistException;
import model.Product;

/**
 * 	商品业务逻辑层接口
 * @author CAIHONGDE
 */
public interface IProductService {
	
	/**
	 * 	不分页获取所有的商品列表
	 * @return
	 */
	public List<Product> getProducts();
	
	/**
	 *	获取商品总数量
	 * @return
	 */
	public Integer getProductsCount();
	
	/**
	 * 	分页获取所有的商品列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Product> getProductsByPage(Integer pageNo, Integer pageSize);
	
	/**
	 * 	新增商品
	 * @param product
	 * @return
	 */
	public Integer addProduct(Product product) throws ProductExistException;
	
	/**
	 * 	编辑商品
	 * @param product
	 * @return
	 */
	public Integer editProduct(Product product) throws ProductExistException;
	
	/**
	 * 	批量删除商品
	 * @param id
	 * @return
	 */
	public Integer delProducts(String[] ids) throws ProductExistException;
	
	/**
	 * 	搜索过滤商品
	 * @return
	 * @throws ProductExistException
	 */
	public List<Product> filterProducts(String min, String max, String id, String name, String category)
			throws ProductExistException;
	
	/**
	 * 	通过id获取指定商品
	 * @param id
	 * @return
	 */
	public Product getProduct(String id) throws ProductExistException;
	
}
