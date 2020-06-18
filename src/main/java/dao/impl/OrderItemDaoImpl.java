package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Query;
import dao.IOrderItemDao;
import model.Order;
import model.OrderItem;
import model.Product;

public class OrderItemDaoImpl implements IOrderItemDao {

	@Override
	public List<OrderItem> getOrderItemByOrder(Order order) {
		ArrayList<OrderItem> list = new ArrayList<OrderItem>();
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_orderitem "
					+ "INNER JOIN chd_book_products ON chd_book_orderitem.product_id = chd_book_products.id "
					+ "WHERE chd_book_orderitem.order_id = ?";
			ResultSet rs = query.executeQuery(sql, new String[] {order.getId()});
			while (rs.next()) {
				list.add(new OrderItem(
					new Order(
						order.getId(), 
						order.getMoney(), 
						order.getReceiverAddress(), 
						order.getReceiverName(), 
						order.getReceiverPhone(), 
						order.getPayState(), 
						order.getOrderTime()
					),
					new Product(
						rs.getString("id"),
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getString("category"),
						rs.getInt("pnum"),
						rs.getString("imgurl"),
						rs.getString("description"),
						rs.getInt("is_del")
					),
					rs.getInt("buynum")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer delOrderItemByOrderId(String order_id) {
		Integer res = 0;
		String sql = "DELETE FROM chd_book_orderitem WHERE order_id = ?";
		
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, new Object[] {order_id});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer addOrderItemByOrder(Order order, List<OrderItem> orderItems) {
		Integer res = 0;
		if(order==null||orderItems==null||orderItems.size()<1) return res;
		
		Query query = Query.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = query.getConnection();
			query.startTranscation();	//开启事务
			
			String[] fields = { "order_id", "product_id", "buynum" };
			StringBuilder sql = new StringBuilder("INSERT INTO chd_book_orderitem ( ");
			sql.append(Arrays.toString(fields)).deleteCharAt(sql.indexOf("[")).deleteCharAt(sql.length()-1);
			sql.append(" ) VALUES (");
			for (int i = 0; i < fields.length; i++) 
				sql.append("?,");
			sql.deleteCharAt(sql.length()-1).append(")");
			
			pstmt = conn.prepareStatement(sql.toString(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			for (OrderItem orderitem : orderItems) {
				pstmt.setObject(1, orderitem.getOrder().getId());
				pstmt.setObject(2, orderitem.getProduct().getId());
				pstmt.setObject(3, orderitem.getBuynum());
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

}
