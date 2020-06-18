package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Query;
import dao.IFavorDao;
import model.Favor;
import model.Product;
import model.User;

public class FavorDaoImpl implements IFavorDao {

	@Override
	public List<Favor> getFavorsByUserId(Integer user_id) {
		List<Favor> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT bf.*,bu.username,bp.name,bp.price,bp.category,bp.pnum,bp.imgurl "
					+ "FROM (chd_book_favor bf INNER JOIN chd_book_user bu ON bf.user_id=bu.id) "
					+ "INNER JOIN chd_book_products bp ON bf.product_id=bp.id WHERE bf.user_id = ? AND bf.is_del = 0";
			ResultSet rs = query.executeQuery(sql,new Object[] {user_id});
			list = new ArrayList<Favor>();
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
				list.add(new Favor(rs.getInt("id"),product,user,rs.getInt("is_del"),rs.getTimestamp("create_time")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Favor getFavorById(Integer favor_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addFavor(Favor favor) {
		Integer res = 0;
		if(favor == null) return res;
		String[] fields = { "user_id", "product_id" };
		String sql = "INSERT INTO chd_book_favor ( "
				+ Arrays.toString(fields).substring(1, Arrays.toString(fields).length() - 1) + " ) VALUES (";
		for (int i = 0; i < fields.length; i++) 
			sql += "?,";
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, new Object[] {favor.getUser().getId(),favor.getProduct().getId()});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer updateFavor(Favor favor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delFavor(Integer favor_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delFavors(Integer ...favor_ids) {
		Integer res = 0;
		if(favor_ids == null || favor_ids.length < 1) return res;
		String sql = "UPDATE chd_book_favor SET is_del = 1 WHERE id IN (";
		for (int i = 0; i < favor_ids.length; i++) 
			sql += "?,";
		
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, favor_ids);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

}
