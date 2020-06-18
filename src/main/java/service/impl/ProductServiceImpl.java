package service.impl;

import java.util.List;

import exception.ProductExistException;
import factory.DaoFactory;
import model.Product;
import service.IProductService;

public class ProductServiceImpl implements IProductService{

	@Override
	public List<Product> getProducts() {
		return DaoFactory.getProductDao().getProductsData();
	}

	@Override
	public List<Product> getProductsByPage(Integer pageNo, Integer pageSize) {
		return DaoFactory.getProductDao().getProductsData(pageNo, pageSize);
	}

	@Override
	public Integer addProduct(Product product) throws ProductExistException {
		if(product == null)
			throw new ProductExistException("添加失败,商品信息有误!");
		return DaoFactory.getProductDao().addProductData(product);
	}

	@Override
	public Integer editProduct(Product product) throws ProductExistException {
		Product bean = DaoFactory.getProductDao().getProductByIdData(product.getId());
		if(bean == null)
			throw new ProductExistException("更新失败,该商品不存在!");
		return DaoFactory.getProductDao().updateProduct(product);
	}

	@Override
	public Integer delProducts(String[] ids) throws ProductExistException {
		if(ids == null || ids.length < 1)
			throw new ProductExistException("删除失败,商品id有误!");
		
		List<Product> products = DaoFactory.getProductDao().getProductByIdsData(ids);
		Integer res = DaoFactory.getProductDao().delProduct(products);
		
		if(res < 1)
			throw new ProductExistException("删除失败!");
		return res;
	}

	@Override
	public Integer getProductsCount() {
		return DaoFactory.getProductDao().getProductsCount();
	}

	@Override
	public Product getProduct(String id) throws ProductExistException {
		if("".equals(id))
			throw new ProductExistException("查询失败,商品id有误!");
		Product product = DaoFactory.getProductDao().getProductByIdData(id);
		if(product==null)
			throw new ProductExistException("查询失败,商品不存在!");
		return product;
	}

	@Override
	public List<Product> filterProducts(String min, String max, String id, String name, String category)
			throws ProductExistException {
		
		if(min!=null&&!"".equals(min)&&max!=null&&!"".equals(max))
			return DaoFactory.getProductDao().priceFilterProductsData(Integer.parseInt(min), Integer.parseInt(max));
		if(category!=null&&!"".equals(category))
			return DaoFactory.getProductDao().categoryFilterProductsData(category);
		if(name!=null&&!"".equals(name))
			return DaoFactory.getProductDao().nameFilterProductsData(name);
		if(id!=null&&!"".equals(id))
			return DaoFactory.getProductDao().getProductByIdsData(new String[] {id});
		
		throw new ProductExistException("查询失败,过滤参数有误!");
	}

}
