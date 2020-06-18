package dao.impl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import core.BeanListProcessor;
import core.BeanProcessor;
import core.Query;
import dao.IProductDao;
import model.Product;
import utils.ReflectUtils;

public class ProductDaoImpl implements IProductDao {

	@Override
	public Integer addProductData(Product product) {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String[] fields = { "id", "name", "price", "category", "pnum", "imgurl", "description" };
			String sql = "INSERT INTO chd_book_products ( "
					+ Arrays.toString(fields).substring(1, Arrays.toString(fields).length() - 1) + " ) VALUES (";
			Object[] params = new Object[fields.length];
			for (int i = 0; i < fields.length; i++) {
				params[i] = ReflectUtils.invokeGetter(product, fields[i]);
				sql += "?,";
			}
			sql = sql.substring(0, sql.length() - 1) + ")";
			res = query.executeUpdate(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public List<Product> nameFilterProductsData(String like) {
		List<Product> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_products  WHERE name LIKE ? AND is_del = 0";
			list = query.executeQuery(sql, 
					new Object[] {"%"+like+"%"}, 
					new BeanListProcessor<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Product> getProductsData(Integer pageNo, Integer pageSize) {
		List<Product> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_products WHERE is_del = 0 LIMIT ?,?";
			list = query.executeQuery(sql, new Object[]{(pageNo-1)*pageSize,pageSize}, 
					new BeanListProcessor<Product>(Product.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer getProductsCount() {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT COUNT(*) FROM chd_book_products WHERE is_del = 0";
			res = query.executeQueryCount(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Product getProductByIdData(String id) {
		String sql = "SELECT * FROM chd_book_products WHERE id = ? AND is_del = 0 limit 1";
		Product bean = null;
		Query query = Query.getInstance();
		try {
			bean = query.
					executeQuery(sql,new Object[] {id},
					new BeanProcessor<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return bean;
	}

	@Override
	public List<Product> priceFilterProductsData(Integer min, Integer max) {
		List<Product> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_products WHERE is_del = 0 AND price BETWEEN ? AND ?";
			list = query.executeQuery(sql, new Object[]{min,max}, new BeanListProcessor<Product>(Product.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Product> categoryFilterProductsData(String category) {
		List<Product> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_products WHERE is_del = 0 AND category = ?";
			list = query.executeQuery(sql, new Object[]{category}, new BeanListProcessor<Product>(Product.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Product> getProductsData() {
		List<Product> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_products WHERE is_del = 0";
			list = query.executeQuery(sql, new BeanListProcessor<Product>(Product.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer delProduct(Product product) {
		String sql = "UPDATE chd_book_products SET is_del = 1 WHERE id = ?";
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, new Object[] {product.getId()});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer delProduct(List<Product> products) {
		String sql = "UPDATE chd_book_products SET is_del = 1 WHERE id IN (";
		Object[] params = new Object[products.size()];
		for (int i = 0; i < products.size(); i++) {
			sql += "?,";
			params[i] = ReflectUtils.invokeGetter(products.get(i), "id");
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public List<Product> getProductByIdsData(String[] ids) {
		String sql = "SELECT * FROM chd_book_products WHERE is_del = 0 AND id IN (";
		for (int i = 0; i < ids.length; i++) 
			sql += "?,";
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		List<Product> data = null;
		Query query = Query.getInstance();
		try {
			data = query.executeQuery(sql,ids,new BeanListProcessor<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return data;
	}

	@Override
	public Integer updateProduct(Product product) {
		String[] fields = { "name", "price", "category", "pnum", "imgurl", "description" };
		String sql = "UPDATE chd_book_products SET ";
		
		Object[] params = new Object[fields.length + 1];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(product, fields[i]);
			sql+= fields[i] + " = ?,";
		}
		sql = sql.substring(0,sql.length()-1) + " WHERE id = ? AND is_del = 0";
		params[fields.length] = ReflectUtils.invokeGetter(product, "id");
		
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer updateProduct(List<Product> products) {
		String[] fields = { "name", "price", "category", "pnum", "imgurl", "description" };
		Integer res = 0;
		if(products==null|products.size()<1) return res;
		
		StringBuilder sql = new StringBuilder("UPDATE chd_book_products SET ");
		for (int i = 0; i < fields.length; i++) sql.append(fields[i]).append(" = ?,");
		
		sql.deleteCharAt(sql.length()-1).append(" WHERE id = ?");
		
		Query query = Query.getInstance();
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = query.getConnection();
			//先查询数据库是否支持批量处理
			boolean sbu = conn.getMetaData().supportsBatchUpdates();
			
			query.startTranscation(); //开启事务
			if(sbu) {
				pstmt = conn.prepareStatement(sql.toString());
				
				for (Product product : products) {
					pstmt.setObject(1, product.getName());
					pstmt.setObject(2, product.getPrice());
					pstmt.setObject(3, product.getCategory());
					pstmt.setObject(4, product.getPnum());
					pstmt.setObject(5, product.getImgurl());
					pstmt.setObject(6, product.getDescription());
					pstmt.setObject(7, product.getId());
					pstmt.addBatch();
				}
				
				int[] counts = pstmt.executeBatch();
				query.commit();
				pstmt.clearBatch();
				for (int count : counts) 
					res+=count;
				
			} else {
				pstmt = conn.prepareStatement(sql.toString());
				int t = 0;	//必须等commit之后才有意义
				for (Product product : products) {
					pstmt.setObject(1, product.getName());
					pstmt.setObject(2, product.getPrice());
					pstmt.setObject(3, product.getCategory());
					pstmt.setObject(4, product.getPnum());
					pstmt.setObject(5, product.getImgurl());
					pstmt.setObject(6, product.getDescription());
					pstmt.setObject(7, product.getId());
					t+=pstmt.executeUpdate();
				}
				query.commit();
				res = t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				query.rollback(); //回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			query.closeConnection(null, pstmt, null, conn);
		}
		return res;
	}

}
