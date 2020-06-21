package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Query;
import dao.IOrderDao;
import model.Order;
import model.User;
import utils.ReflectUtils;

public class OrderDaoImpl implements IOrderDao {

	@Override
	public Integer addOrder(Order order) {
		String[] fields = { "user_id", "id", "money", "receiverAddress", "receiverName", "receiverPhone", "orderTime" };
		String sql = "INSERT INTO chd_book_orders ( "
				+ Arrays.toString(fields).substring(1, Arrays.toString(fields).length() - 1) + " ) VALUES (";
		Object[] params = new Object[fields.length];
		params[0] = order.getUser().getId();
		sql += "?,";
		
		for (int i = 1; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(order, fields[i]);
			sql += "?,";
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
	public List<Order> getOrdersData() {
		ArrayList<Order> list = new ArrayList<Order>();
		Query query = Query.getInstance();
		try {
			String sql = "SELECT chd_book_orders.*,chd_book_user.username FROM chd_book_orders "
					+ "INNER JOIN chd_book_user ON chd_book_orders.user_id = chd_book_user.id WHERE chd_book_orders.is_del = 0";
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				list.add(new Order(rs.getString("id"), rs.getDouble("money"), rs.getString("receiverAddress"),
						rs.getString("receiverName"), rs.getString("receiverPhone"), rs.getInt("payState"),
						rs.getTimestamp("orderTime"), user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer delOrderByIds(String... order_ids) {
		int res = 0;
		Query query = Query.getInstance();
		try {
			StringBuilder sql = new StringBuilder("UPDATE chd_book_orders SET is_del = 1 WHERE id IN(");
			for (int i = 0; i < order_ids.length; i++) sql.append("?,");
			sql.deleteCharAt(sql.length()-1).append(")");
			
			res = query.executeUpdate(sql.toString(), order_ids);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Order getOrderById(String order_id) {
		Order order = null;
		User user = null;
		Query query = Query.getInstance();

		try {
			String sql = "SELECT chd_book_orders.*,chd_book_user.username FROM chd_book_orders "
					+ "INNER JOIN chd_book_user ON chd_book_orders.user_id = chd_book_user.id "
					+ "WHERE chd_book_orders.id = ? AND chd_book_orders.is_del = 0 LIMIT 1";
			ResultSet rs = query.executeQuery(sql, new String[] { order_id });
			while (rs.next()) {
				order = new Order();
				order.setId(rs.getString("id"));
				order.setMoney(rs.getDouble("money"));
				order.setOrderTime(rs.getTimestamp("orderTime"));
				order.setPayState(rs.getInt("payState"));
				order.setReceiverAddress(rs.getString("receiverAddress"));
				order.setReceiverName(rs.getString("receiverName"));
				order.setReceiverPhone(rs.getString("receiverPhone"));
				user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				order.setUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}

		return order;
	}

	@Override
	public List<Order> getOrdersByUserId(Integer user_id) {
		List<Order> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT chd_book_orders.*,chd_book_user.username FROM chd_book_orders "
					+ "INNER JOIN chd_book_user ON chd_book_orders.user_id = chd_book_user.id "
					+ "WHERE chd_book_orders.is_del = 0 AND chd_book_orders.user_id = ?";
			ResultSet rs = query.executeQuery(sql, new Object[] {user_id});
			list = new ArrayList<Order>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				list.add(new Order(rs.getString("id"), rs.getDouble("money"), rs.getString("receiverAddress"),
						rs.getString("receiverName"), rs.getString("receiverPhone"), rs.getInt("payState"),
						rs.getTimestamp("orderTime"), user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Order> getOrdersByUserId(Integer user_id, Integer pageNo, Integer pageSize) {
		List<Order> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT chd_book_orders.*,chd_book_user.username FROM chd_book_orders "
					+ "INNER JOIN chd_book_user ON chd_book_orders.user_id = chd_book_user.id "
					+ "WHERE chd_book_orders.is_del = 0 AND chd_book_orders.user_id = ? LIMIT ?,?";
			ResultSet rs = query.executeQuery(sql, new Object[] {user_id,(pageNo-1)*pageSize,pageSize});
			list = new ArrayList<Order>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				list.add(new Order(rs.getString("id"), rs.getDouble("money"), rs.getString("receiverAddress"),
						rs.getString("receiverName"), rs.getString("receiverPhone"), rs.getInt("payState"),
						rs.getTimestamp("orderTime"), user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Order> getOrderByReceiverName(String receiverName) {
		ArrayList<Order> list = new ArrayList<Order>();
		Query query = Query.getInstance();
		try {
			String sql = "SELECT chd_book_orders.*,chd_book_user.username FROM chd_book_orders "
					+ "INNER JOIN chd_book_user ON chd_book_orders.user_id = chd_book_user.id "
					+ "WHERE chd_book_orders.is_del = 0 AND chd_book_orders.receiverName = ?";
			ResultSet rs = query.executeQuery(sql, new String[] {receiverName});
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				list.add(new Order(rs.getString("id"), rs.getDouble("money"), rs.getString("receiverAddress"),
						rs.getString("receiverName"), rs.getString("receiverPhone"), rs.getInt("payState"),
						rs.getTimestamp("orderTime"), user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Order> getOrdersData(Integer pageNo, Integer pageSize) {
		ArrayList<Order> list = new ArrayList<Order>();
		Query query = Query.getInstance();
		try {
			String sql = "SELECT chd_book_orders.*,chd_book_user.username FROM chd_book_orders "
					+ "INNER JOIN chd_book_user ON chd_book_orders.user_id = chd_book_user.id WHERE chd_book_orders.is_del = 0 LIMIT ?,?";
			ResultSet rs = query.executeQuery(sql,new Object[] {(pageNo-1)*pageSize,pageSize});
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				list.add(new Order(rs.getString("id"), rs.getDouble("money"), rs.getString("receiverAddress"),
						rs.getString("receiverName"), rs.getString("receiverPhone"), rs.getInt("payState"),
						rs.getTimestamp("orderTime"), user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer getOrdersCount() {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT COUNT(*) FROM chd_book_orders WHERE is_del = 0";
			res = query.executeQueryCount(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer getOrdersCountByUserId(Integer user_id) {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT COUNT(*) FROM chd_book_orders WHERE user_id = ? AND is_del = 0";
			res = query.executeQueryCount(sql,new Object[]{user_id});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer updateOrder(String order_id, Integer payState) {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "UPDATE chd_book_orders SET payState = ? WHERE id = ?";
			res = query.executeUpdate(sql,new Object[] {payState,order_id});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}
	
}
