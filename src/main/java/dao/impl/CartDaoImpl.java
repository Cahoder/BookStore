package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Query;
import dao.ICartDao;
import model.Cart;
import model.Product;
import model.User;

public class CartDaoImpl implements ICartDao {

	@Override
	public List<Cart> getCartsByUserId(Integer user_id) {
		List<Cart> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT bc.*,bu.username,bp.name,bp.price,bp.category,bp.pnum,bp.imgurl "
					+ "FROM (chd_book_cart bc INNER JOIN chd_book_user bu ON bc.user_id=bu.id) "
					+ "INNER JOIN chd_book_products bp ON bc.product_id=bp.id WHERE bc.user_id = ? AND bc.is_del = 0";
			ResultSet rs = query.executeQuery(sql,new Object[] {user_id});
			list = new ArrayList<Cart>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				Product product = new Product();
				product.setId(rs.getString("product_id"));
				product.setCategory(rs.getString("category"));
				product.setName(rs.getString("name"));
				product.setPnum(rs.getInt("pnum"));
				product.setPrice(rs.getDouble("price"));
				product.setImgurl(rs.getString("imgurl"));
				Cart cart = new Cart(rs.getInt("id"),product,rs.getInt("addnum"),user,rs.getInt("is_del"));
				cart.setCreate_time(rs.getTimestamp("create_time"));
				cart.setUpdate_time(rs.getTimestamp("update_time"));
				list.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Cart getCartByCartId(Integer cart_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addCart(Cart cart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addCarts(List<Cart> carts) {
		Integer res = 0;
		if(carts==null||carts.size()<1) return res;
		
		Query query = Query.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = query.getConnection();
			query.startTranscation();	//开启事务
			
			String[] fields = { "product_id", "addnum", "user_id" };
			StringBuilder sql = new StringBuilder("INSERT INTO chd_book_cart ( ");
			sql.append(Arrays.toString(fields)).deleteCharAt(sql.indexOf("[")).deleteCharAt(sql.length()-1);
			sql.append(" ) VALUES (");
			for (int i = 0; i < fields.length; i++) 
				sql.append("?,");
			sql.deleteCharAt(sql.length()-1).append(")");
			
			pstmt = conn.prepareStatement(sql.toString(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			for (Cart cart : carts) {
				pstmt.setObject(1, cart.getProduct().getId());
				pstmt.setObject(2, cart.getAddnum());
				pstmt.setObject(3, cart.getUser().getId());
		        pstmt.addBatch();   
			}
			
			int[] counts = pstmt.executeBatch();
			query.commit();	//事务提交
			pstmt.clearBatch();
			for (int count : counts) 
				res+=count;
			
		} catch (SQLException e) {
			try {
				query.rollback();
			} catch (SQLException e1) {}
		} finally {
			query.closeConnection(null, pstmt, null, conn);
		}
		return res;
	}

	@Override
	public Integer delCart(Integer cart_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delCarts(Integer... cart_ids) {
		Integer res = 0;
		if(cart_ids == null || cart_ids.length < 1) return res;
		String sql = "UPDATE chd_book_cart SET is_del = 1 WHERE id IN (";
		for (int i = 0; i < cart_ids.length; i++) 
			sql += "?,";
		
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, cart_ids);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer updateCart(Cart cart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateCarts(List<Cart> carts) {
		String[] fields = { "product_id", "addnum", "is_del", "user_id" };
		Integer res = 0;
		if(carts==null|carts.size()<1) return res;
		
		StringBuilder sql = new StringBuilder("UPDATE chd_book_cart SET ");
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
				
				for (Cart cart : carts) {
					pstmt.setObject(1, cart.getProduct().getId());
					pstmt.setObject(2, cart.getAddnum());
					pstmt.setObject(3, cart.getIs_del());
					pstmt.setObject(4, cart.getUser().getId());
					pstmt.setObject(5, cart.getId());
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
				for (Cart cart : carts) {
					pstmt.setObject(1, cart.getProduct().getId());
					pstmt.setObject(2, cart.getAddnum());
					pstmt.setObject(3, cart.getIs_del());
					pstmt.setObject(4, cart.getUser().getId());
					pstmt.setObject(5, cart.getId());
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
