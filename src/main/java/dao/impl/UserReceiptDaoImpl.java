package dao.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import core.BeanListProcessor;
import core.BeanProcessor;
import core.Query;
import dao.IUserReceiptDao;
import model.User;
import model.UserReceipt;
import utils.ReflectUtils;

public class UserReceiptDaoImpl implements IUserReceiptDao{

	@Override
	public List<UserReceipt> getUserReceipts(User user) {
		List<UserReceipt> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT bur.* FROM chd_book_user_receipt bur INNER JOIN chd_book_user bu ON "
					+ "bur.user_id = bu.id WHERE bur.is_del = 0 AND bur.user_id = ?";
			list = query.executeQuery(sql,new Object[] {user.getId()}, 
					new BeanListProcessor<UserReceipt>(UserReceipt.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer delUserReceiptById(Integer id) {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "UPDATE chd_book_user_receipt SET is_del = 1 WHERE id = ?";
			res = query.executeUpdate(sql,new Object[] {id});
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer addUserReceipt(UserReceipt receipt) {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			StringBuilder sql = new StringBuilder("INSERT INTO chd_book_user_receipt (");
			String[] fields = {"user_id","name","address","phone"};
			sql.append(Arrays.toString(fields));
			sql.deleteCharAt(sql.indexOf("[")).deleteCharAt(sql.indexOf("]"));
			sql.append(") VALUES (");
			
			Object[] params = new Object[fields.length];
			params[0] = receipt.getUser().getId();
			
			for (int i = 1; i < fields.length; i++) {
				sql.append("?,");
				params[i] = ReflectUtils.invokeGetter(receipt, fields[i]);
			}
			sql.append("?)");
			
			res = query.executeUpdate(sql.toString(),params);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public UserReceipt getUserReceiptById(Integer id) {
		UserReceipt userReceipt = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT bur.* FROM chd_book_user_receipt bur "
					+ "INNER JOIN chd_book_user bu ON bur.user_id = bu.id "
					+ "WHERE bur.is_del = 0 AND bur.id = ?";
			userReceipt = query.executeQuery(sql,new Object[] {id},
					new BeanProcessor<UserReceipt>(UserReceipt.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return userReceipt;
	}

	@Override
	public Integer updateUserReceipt(UserReceipt receipt) {
		String[] fields = { "name", "phone", "address" };
		String sql = "UPDATE chd_book_user_receipt SET ";
		
		Object[] params = new Object[fields.length + 1];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(receipt, fields[i]);
			sql+= fields[i] + " = ?,";
		}
		sql = sql.substring(0,sql.length()-1) + " WHERE id = ? AND is_del = 0";
		params[fields.length] = ReflectUtils.invokeGetter(receipt, "id");
		
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
	
}
