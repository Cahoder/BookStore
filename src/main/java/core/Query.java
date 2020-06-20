package core;

import java.sql.*;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import utils.*;

/**
 * 	对外提供服务的核心类
 * @author cahoder
 */
public class Query extends AbstractDBUtils implements Cloneable {
	
	private static Query query = new Query();
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	@Override
	public DBDataSource configDataSource() throws SQLException {
		return new C3P0Pool();	//C3P0的连接池配置源
	}
	
	/**
	 * 	无参构造器屏蔽掉，仅仅对外提供单例模式
	 */
	private Query() {}
	
	/**
	 * @return 单例克隆复制
	 */
	public static Query getInstance() {
		try {
			return (Query) query.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			query = new Query();
		}
		return query;
	}

	/**
	 * 	定义一个模板方法,对所有的连接和关闭数据库操作进行封装，在回调进行具体操作
	 * @param callback 回调具体操作
	 * @return
	 */
    private Object generalTemplate(QueryCallback callback) throws SQLException {
        try {
        	conn = getConnection();		  //建立数据库连接
            return callback.doExecute();  //让回调做具体任务
        }
        catch (SQLException e) {
        	throw e;
        }
    }
    
    /**
     * 	关闭数据库连接
     */
    public void close() {
    	closeConnection(rs, pstmt, stmt, conn);
    }
    
    /**
     * 	增|删|改 数据
     * @param SQL 预处理SQL语句
     * @param params 预处理语句参数
     * @return 数据库影响行数,大于1则插入成功
     * @throws SQLException 
     */
    public int executeUpdate(String SQL, Object[] params) throws SQLException {
    	return (int)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
	    		pstmt = conn.prepareStatement(SQL);
				JDBCUtils.installParams(pstmt, params); //预处理语句填充数据
	    		return pstmt.executeUpdate();	//影响数据量
			}
		});
    }
    
    /**
     * 	增|删|改 数据
     * @param SQL	非预处理SQL语句
     * @return	数据库影响行数,大于1则插入成功
     * @throws SQLException
     */
    public int executeUpdate(String SQL) throws SQLException {
    	return (int)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
				stmt = conn.createStatement();
	    		return stmt.executeUpdate(SQL);	//影响数据量
			}
		});
    }
    
    /**
     * 	查询SQL符合记录量
     * @param SQL 预处理SQL语句
     * @param params 预处理语句参数
     * @return 查询到的符合记录个数
     * @throws SQLException 
     */
    public int executeQueryCount(String SQL, Object[] params) throws SQLException {
    	return (int)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
	    		int count = 0;
	    		
				rs = executeQuery(SQL,params);
				// SQL 语句中使用了  count(*) 函数只会返回一行一列数据
				if(SQL.toLowerCase().contains("count")) 
					if(rs.next())
						count=rs.getInt(1);
				// SQL 语句没有使用了  count(*) 函数也想查数据量
				else {
					rs.last();
					count = rs.getRow();
				}
	    		return count;
			}
		});
	}
    
    /**
     * 	查询SQL符合记录量
     * @param SQL 非预处理语句
     * @return	查询到的符合记录个数
     * @throws SQLException
     */
    public int executeQueryCount(String SQL) throws SQLException {
    	return (int)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
	    		int count = 0;
				rs = executeQuery(SQL);
				
				// SQL 语句中使用了  count(*) 函数只会返回一行一列数据
				if(SQL.toLowerCase().contains("count")) 
					if(rs.next())
						count=rs.getInt(1);
				// SQL 语句没有使用了  count(*) 函数也想查数据量
				else {
					rs.last();
					count = rs.getRow();
				}
	    		return count;
			}
		});
	}
    
    /**
     * 	查询SQL符合记录
     * 	jdk 要求大于1.7
     * @param SQL
     * @param params
     * @return 查询到的符合记录
     * @throws SQLException 
     */
    public ResultSet executeQuery(String SQL,Object[] params) throws SQLException {
    	return (ResultSet)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
				pstmt = conn.prepareStatement(SQL);
	    		JDBCUtils.installParams(pstmt, params); //预处理语句填充数据
	    		
				rs = pstmt.executeQuery();
				
				RowSetFactory factory = RowSetProvider.newFactory();
				CachedRowSet cachedRs = factory.createCachedRowSet();
				
				//ResultSet装填到RowSet缓存到内存，ResultSet就可以关闭掉了
				cachedRs.populate(rs);
	    		return cachedRs;
			}
		});
    }
    
    /**
     * 	查询SQL符合记录
     * 	jdk 要求大于1.7
     * @param SQL 非预处理语句
     * @return	查询到的符合记录
     * @throws SQLException
     */
    public ResultSet executeQuery(String SQL) throws SQLException {
    	return (ResultSet)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				
				RowSetFactory factory = RowSetProvider.newFactory();
				CachedRowSet cachedRs = factory.createCachedRowSet();
				
				//ResultSet装填到RowSet缓存到内存，ResultSet就可以关闭掉了
				cachedRs.populate(rs);
	    		return cachedRs;
			}
		});
    }
    
    /**
     * 	根据用户所需封装结果集
     * @param <E>
     * @param SQL	预处理SQL语句
     * @param params
     * @param process
     * @return
     * @throws SQLException
     */
	public <E> E executeQuery(String SQL,Object[] params,ResultSetProcessor<E> processor) 
    		throws SQLException {
		ResultSet rs = executeQuery(SQL, params);
		return processor.process(rs);
    }
	
	/**
     * 	根据用户所需封装结果集
     * @param <E>
     * @param SQL	预处理SQL语句
     * @param params
     * @param process
     * @return
     * @throws SQLException
     */
	public <E> E executeQuery(String SQL,ResultSetProcessor<E> processor,Object...params) 
    		throws SQLException {
		ResultSet rs = executeQuery(SQL, params);
		return processor.process(rs);
    }
	
	/**
	 * 	根据用户所需封装结果集
	 * @param <E>
	 * @param SQL	非预处理SQL语句
	 * @param processor
	 * @return
	 * @throws SQLException
	 */
	public <E> E executeQuery(String SQL,ResultSetProcessor<E> processor) 
    		throws SQLException {
		ResultSet rs = executeQuery(SQL);
		return processor.process(rs);
    }
	
}
