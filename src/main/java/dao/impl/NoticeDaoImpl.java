package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.BeanListProcessor;
import core.BeanProcessor;
import core.Query;
import dao.INoticeDao;
import model.Notice;
import utils.ReflectUtils;

public class NoticeDaoImpl implements INoticeDao{

	@Override
	public List<Notice> getNotices() {
		String sql = "SELECT * FROM chd_book_notice WHERE is_del = 0";
		List<Notice> data = new ArrayList<Notice>();
		Query query = Query.getInstance();
		try {
			data = query.executeQuery(sql,new BeanListProcessor<Notice>(Notice.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return data;
	}

	@Override
	public Notice getNoticeById(Integer id) {
		String sql = "SELECT * FROM chd_book_notice WHERE id = ? AND is_del = 0 limit 1";
		Notice bean = null;
		Query query = Query.getInstance();
		try {
			bean = query.executeQuery(sql,new Integer[] {id},
					new BeanProcessor<Notice>(Notice.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return bean;
	}

	@Override
	public Notice getLastestNotice() {
		String sql = "SELECT * FROM chd_book_notice WHERE is_del = 0 ORDER BY create_time DESC limit 1";
		Notice bean = null;
		Query query = Query.getInstance();
		try {
			bean = query.
					executeQuery(sql,new BeanProcessor<Notice>(Notice.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return bean;
	}

	@Override
	public Integer delNotice(Notice notice) {
		String sql = "UPDATE chd_book_notice SET is_del = 1 WHERE id = ?";
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, new Integer[] {notice.getId()});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer addNotice(Notice notice) {
		String[] fields = { "title", "details" };
		String sql = "INSERT INTO chd_book_notice ( "
				+ Arrays.toString(fields).substring(1, Arrays.toString(fields).length() - 1) + " ) VALUES (";
		Object[] params = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(notice, fields[i]);
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
	public Integer updateNotice(Notice notice) {
		String[] fields = { "title", "details" };
		String sql = "UPDATE chd_book_notice SET ";
		
		Object[] params = new Object[fields.length + 1];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(notice, fields[i]);
			sql+= fields[i] + " = ?,";
		}
		sql = sql.substring(0,sql.length()-1) + " WHERE id = ? AND is_del = 0";
		params[fields.length] = ReflectUtils.invokeGetter(notice, "id");
		
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
	public List<Notice> getNotices(Integer[] ids) {
		String sql = "SELECT * FROM chd_book_notice WHERE is_del = 0 AND id IN (";
		for (int i = 0; i < ids.length; i++) 
			sql += "?,";
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		List<Notice> data = new ArrayList<Notice>();
		Query query = Query.getInstance();
		try {
			data = query.executeQuery(sql,ids,new BeanListProcessor<Notice>(Notice.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return data;
	}

	@Override
	public Integer delNotice(List<Notice> notices) {
		String sql = "UPDATE chd_book_notice SET is_del = 1 WHERE id IN (";
		Object[] params = new Object[notices.size()];
		for (int i = 0; i < notices.size(); i++) {
			sql += "?,";
			params[i] = ReflectUtils.invokeGetter(notices.get(i), "id");
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
	public Integer getNoticesCount() {
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT COUNT(*) FROM chd_book_notice WHERE is_del = 0";
			res = query.executeQueryCount(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public List<Notice> getNotices(Integer pageNo, Integer pageSize) {
		List<Notice> list = null;
		Query query = Query.getInstance();
		try {
			String sql = "SELECT * FROM chd_book_notice WHERE is_del = 0 LIMIT ?,?";
			list = query.executeQuery(sql, new Object[]{(pageNo-1)*pageSize,pageSize}, 
					new BeanListProcessor<Notice>(Notice.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

}
