package dao.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import core.BeanListProcessor;
import core.BeanProcessor;
import core.Query;
import dao.IUserDao;
import model.User;
import utils.JDBCUtils;
import utils.ReflectUtils;

public class UserDaoImpl implements IUserDao {

	@Override
	public List<User> getUsersData(String role) {
		List<User> data = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_user WHERE role = ?";
			data = query.executeQuery(sql, new Object[] {role},new BeanListProcessor<User>(User.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return data;
	}
	
	@Override
	public User verifyUser(String username, String password) {
		User user = null;
		Query query = Query.getInstance();

		try {
			String sql = "SELECT * FROM chd_book_user WHERE username = ? AND password = ? limit 1";
			user = query.executeQuery(sql, new Object[] { username,password }, new BeanProcessor<User>(User.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return user;
	}

	@Override
	public User verifyUser(String role, String username, String password) {
		User user = null;
		Query query = Query.getInstance();

		try {
			String sql = "SELECT * FROM chd_book_user WHERE username = ? AND password = ? AND role = ? limit 1";
			user = query.executeQuery(sql, new Object[] { username,password,role }, new BeanProcessor<User>(User.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return user;
	}

	@Override
	public Integer delUser(User user) {
		String sql = "UPDATE chd_book_user SET is_del = 1 WHERE id = ?";
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, new Integer[] {user.getId()});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer updateUser(User user) {
		String[] fields = { "username", "password", "gender", "email", "telephone", "introduce", "role", "avatar" };
		String sql = "UPDATE chd_book_user SET ";
		
		Object[] params = new Object[fields.length + 1];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(user, fields[i]);
			sql+= fields[i] + " = ?,";
		}
		sql = sql.substring(0,sql.length()-1) + " WHERE id = ? AND is_del = 0";
		params[fields.length] = ReflectUtils.invokeGetter(user, "id");
		
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
	public Integer addUser(User user) {
		if(user.getRegister_time() == null) user.setRegister_time(JDBCUtils.getCurrentTimestamp());
		
		String[] fields = { "username", "password", "gender", "email", "telephone", "introduce", "role", "register_time" };
		String sql = "INSERT INTO chd_book_user ( "
				+ Arrays.toString(fields).substring(1, Arrays.toString(fields).length() - 1) + " ) VALUES (";
		Object[] params = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(user, fields[i]);
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
	public User getUserById(Integer id) {
		User user = null;
		Query query = Query.getInstance();

		try {
			String sql = "SELECT * FROM chd_book_user WHERE id = ? AND is_del = 0 limit 1";
			user = query.executeQuery(sql, new Object[] { id }, new BeanProcessor<User>(User.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return user;
	}

	@Override
	public List<User> getUsersData(String role, Integer pageNo, Integer pageSize) {
		List<User> data = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_user WHERE role = ? LIMIT ?,?";
			data = query.executeQuery(sql, new Object[] {role,(pageNo-1)*pageSize,pageSize},
					new BeanListProcessor<User>(User.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return data;
	}

	@Override
	public Integer getUsersCount(String role) {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT COUNT(*) FROM chd_book_user WHERE role = ?";
			res = query.executeQueryCount(sql,new Object[] {role});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public List<User> nameFilterUsersData(String role, String username) {
		List<User> data = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_user WHERE role = ? AND username LIKE ?";
			data = query.executeQuery(sql, new Object[] {role,"%"+username+"%"},
					new BeanListProcessor<User>(User.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return data;
	}

}
