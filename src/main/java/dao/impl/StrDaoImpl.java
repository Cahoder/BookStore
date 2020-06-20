package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import core.BeanListProcessor;
import core.BeanProcessor;
import core.Query;
import dao.IStrDao;
import model.Str;
import utils.ReflectUtils;

public class StrDaoImpl implements IStrDao{

	@Override
	public List<Str> getStrs() {
		String sql = "SELECT * FROM chd_book_str WHERE is_del = 0 ORDER BY create_time ASC";
		List<Str> list = null;
		Query query = Query.getInstance();
		try {
			list = query.executeQuery(sql,new BeanListProcessor<Str>(Str.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Str> getStrs(Integer pageNo, Integer pageSize) {
		String sql = "SELECT * FROM chd_book_str WHERE is_del = 0 LIMIT ?,? ORDER BY create_time ASC";
		List<Str> list = null;
		Query query = Query.getInstance();
		try {
			list = query.executeQuery(sql,new BeanListProcessor<Str>(Str.class)
					,(pageNo-1)*pageSize,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer getStrsCount() {
		String sql = "SELECT COUNT(*) FROM chd_book_str WHERE is_del = 0";
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeQueryCount(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Str getStrById(Integer id) {
		String sql = "SELECT * FROM chd_book_str WHERE id = ? AND is_del = 0 limit 1";
		Query query = Query.getInstance();
		try {
			return query.executeQuery(sql,new BeanProcessor<Str>(Str.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			query.close();
		}
	}

	@Override
	public List<Str> getStrsByCid(Integer cid) {
		String sql = "SELECT * FROM chd_book_str WHERE is_del = 0 AND str_cid = ? ORDER BY str_order ASC";
		List<Str> list = null;
		Query query = Query.getInstance();
		try {
			list = query.executeQuery(sql,new BeanListProcessor<Str>(Str.class),cid);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public List<Str> getStrsByCid(Integer cid, Integer pageNo, Integer pageSize) {
		String sql = "SELECT * FROM chd_book_str WHERE is_del = 0 AND str_cid = ? ORDER BY str_order ASC LIMIT ?,?";
		List<Str> list = null;
		Query query = Query.getInstance();
		try {
			list = query.executeQuery(sql,new BeanListProcessor<Str>(Str.class)
					,cid,(pageNo-1)*pageSize,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer getStrsCountByCid(Integer cid) {
		String sql = "SELECT COUNT(*) FROM chd_book_str WHERE is_del = 0 AND str_cid = ?";
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeQueryCount(sql, new Object[]{cid});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public List<Str> getStrsByRange(Integer range) {
		String sql = "SELECT * FROM chd_book_str WHERE is_del = 0 AND str_range = ? ORDER BY create_time ASC";
		List<Str> list = null;
		Query query = Query.getInstance();
		try {
			list = query.executeQuery(sql,new BeanListProcessor<Str>(Str.class),range);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return list;
	}

	@Override
	public Integer deleteByCid(Integer cid) {
		String sql = "UPDATE chd_book_str SET is_del = 1 WHERE str_cid = ?";
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, new Object[] {cid});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer deleteByIds(Integer... ids) {
		String sql = "UPDATE chd_book_str SET is_del = 1 WHERE id IN (";
		for (int i = 0; i < ids.length; i++) sql += "?,";
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, ids);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

	@Override
	public Integer insertStr(Str str) {
		String[] fields = { "str_cid", "str_name", "str_value", "str_order", "str_tips", "str_range", "is_show" };
		String sql = "INSERT INTO chd_book_str ( "
				+ Arrays.toString(fields).substring(1, Arrays.toString(fields).length() - 1) + " ) VALUES (";
		Object[] params = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(str, fields[i]);
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
	public Integer insertStrs(List<Str> strs) {
		Integer res = 0;
		if(strs==null||strs.size()<1) return res;
		
		Query query = Query.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = query.getConnection();
			query.startTranscation();	//开启事务
			
			String[] fields = { "str_cid", "str_name", "str_value", "str_order", "str_tips", "str_range", "is_show" };
			StringBuilder sql = new StringBuilder("INSERT INTO chd_book_str ( ");
			sql.append(Arrays.toString(fields)).deleteCharAt(sql.indexOf("[")).deleteCharAt(sql.length()-1);
			sql.append(" ) VALUES (");
			for (int i = 0; i < fields.length; i++) 
				sql.append("?,");
			sql.deleteCharAt(sql.length()-1).append(")");
			
			pstmt = conn.prepareStatement(sql.toString(),
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			for (Str str : strs) {
				pstmt.setObject(1, str.getStr_cid());
				pstmt.setObject(2, str.getStr_name());
				pstmt.setObject(3, str.getStr_value());
				pstmt.setObject(4, str.getStr_order());
				pstmt.setObject(5, str.getStr_tips());
				pstmt.setObject(6, str.getStr_range());
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
	public Integer updateStr(Str str) {
		String[] fields = { "str_cid", "str_name", "str_value", "str_order", "str_tips", "is_show" };
		String sql = "UPDATE chd_book_str SET ";
		
		Object[] params = new Object[fields.length + 1];
		for (int i = 0; i < fields.length; i++) {
			params[i] = ReflectUtils.invokeGetter(str, fields[i]);
			sql+= fields[i] + " = ?,";
		}
		sql = sql.substring(0,sql.length()-1) + " WHERE id = ? AND is_del = 0";
		params[fields.length] = ReflectUtils.invokeGetter(str, "id");
		
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
	public Integer updateStrs(List<Str> strs) {
		String[] fields = { "str_cid", "str_name", "str_value", "str_order", "str_tips", "is_show" };
		Integer res = 0;
		if(strs==null|strs.size()<1) return res;
		
		StringBuilder sql = new StringBuilder("UPDATE chd_book_str SET ");
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
				
				for (Str str : strs) {
					pstmt.setObject(1, str.getStr_cid());
					pstmt.setObject(2, str.getStr_name());
					pstmt.setObject(3, str.getStr_value());
					pstmt.setObject(4, str.getStr_order());
					pstmt.setObject(5, str.getStr_tips());
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
				
				for (Str str : strs) {
					pstmt.setObject(1, str.getStr_cid());
					pstmt.setObject(2, str.getStr_name());
					pstmt.setObject(3, str.getStr_value());
					pstmt.setObject(4, str.getStr_order());
					pstmt.setObject(5, str.getStr_tips());
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

	@Override
	public Integer showChangeStrByIds(Integer... ids) {
		String sql = "UPDATE chd_book_str SET is_show = ABS(is_show - 1) WHERE id IN (";
		for (int i = 0; i < ids.length; i++) sql += "?,";
		sql = sql.substring(0, sql.length() - 1) + ")";
		
		Integer res = 0;
		Query query = Query.getInstance();
		try {
			res = query.executeUpdate(sql, ids);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			query.close();
		}
		return res;
	}

}
