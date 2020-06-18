package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import core.Query;

import dao.ISaleDao;
import model.Sale;

public class SaleDaoImpl implements ISaleDao {

	@Override
	public List<Sale> getSalesData() {
		ArrayList<Sale> list = new ArrayList<Sale>();
		Query query = Query.getInstance();
		try {
			String sql = "select bp.name,sum(bo.buynum) as buynum,bo.product_id,bp.category "
					+ "from chd_book_orderitem bo left join chd_book_products bp on bp.id = bo.product_id "
					+ "group by bo.product_id order by buynum desc";
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				list.add(new Sale(
					rs.getInt("buynum"),
					rs.getString("product_id"),
					rs.getString("name"),
					rs.getString("category")
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
	public List<Sale> getOnSalesData() {
		ArrayList<Sale> list = new ArrayList<Sale>();
		Query query = Query.getInstance();
		try {
			String sql = "select bp.name,sum(bo.buynum) as buynum,bo.product_id,bp.category "
					+ "from chd_book_orderitem bo inner join chd_book_products bp on bp.id = bo.product_id "
					+ "and bp.is_del = 0 group by bo.product_id order by buynum desc";
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				list.add(new Sale(
					rs.getInt("buynum"),
					rs.getString("product_id"),
					rs.getString("name"),
					rs.getString("category")
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
	public List<Sale> getSalesData(Integer pageNo, Integer pageSize) {
		ArrayList<Sale> list = new ArrayList<Sale>();
		Query query = Query.getInstance();
		try {
			String sql = "select bp.name,sum(bo.buynum) as buynum,bo.product_id,bp.category "
					+ "from chd_book_orderitem bo left join chd_book_products bp on bp.id = bo.product_id "
					+ "group by bo.product_id order by buynum desc limit ?,?";
			ResultSet rs = query.executeQuery(sql,new Object[]{(pageNo-1)*pageSize,pageSize});
			while (rs.next()) {
				list.add(new Sale(
					rs.getInt("buynum"),
					rs.getString("product_id"),
					rs.getString("name"),
					rs.getString("category")
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
	public List<Sale> getSalesData(Timestamp... timestamps) {
		ArrayList<Sale> list = new ArrayList<Sale>();
		Query query = Query.getInstance();
		try {
			String sql = "select bp.name,sum(bo.buynum) as buynum,bo.product_id,bp.category "
					+ "from chd_book_orderitem bo left join chd_book_products bp on bp.id = bo.product_id "
					+ "inner join chd_book_orders ord on ord.id = bo.order_id where ord.ordertime between ? and ? "
					+ "group by bo.product_id order by buynum desc";
			ResultSet rs = query.executeQuery(sql, timestamps);
			while (rs.next()) {
				list.add(new Sale(
					rs.getInt("buynum"),
					rs.getString("product_id"),
					rs.getString("name"),
					rs.getString("category")
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
	public Integer getSalesCount() {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "select count(distinct bo.product_id) from chd_book_orderitem bo "
					+ "left join chd_book_products bp on bp.id = bo.product_id";
			res = query.executeQueryCount(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

}
