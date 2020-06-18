package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * 	抽象的数据库使用工具
 * 	@author CAIHONGDE
 */
public abstract class AbstractDBUtils {
	
	//数据库配置源
	private static DataSource dataSource = null;
	//为每一个连接创建一个线程安全的本地副本
	private static ThreadLocal<Connection> cpc = new ThreadLocal<Connection>();
	
	/**
	 * 	JDBC 提供了 javax.sql.DataSource 接口负责与数据库建立连接
	 * 	只需要配置数据源接口信息即可
	 */
	public abstract DBDataSource configDataSource() throws SQLException;
	
	/**
	 * @return 获取数据库的一个连接
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if(dataSource == null)
			dataSource = configDataSource().getDataSource();
		
		Connection conn = cpc.get();
		
		if (conn==null) {
			conn = dataSource.getConnection();
			cpc.set(conn);
		}
		return conn;
	}
	
	/**
     * 	开启事务
     * @throws SQLException 
     */
    public void startTranscation() throws SQLException {
    	Connection conn = getConnection();
    	if(conn!=null) 
    		conn.setAutoCommit(false);
    }
    
    /**
     * 	事务回滚
     * @throws SQLException 
     */
    public void rollback() throws SQLException {
    	Connection conn = getConnection();
    	if(conn!=null) 
    		conn.rollback();
    }
    
    /**
     * 	事务提交
     * @throws SQLException
     */
    public void commit() throws SQLException {
    	Connection conn = getConnection();
    	if(conn!=null) 
    		conn.commit();
    }
	
	/**
	 *	使用完要释放各种资源（后开先关）
	 * @param rs
	 * @param pstmt
	 * @param stmt
	 * @param conn
	 */
	public void closeConnection
	(ResultSet rs, PreparedStatement pstmt, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
            	cpc.remove();
            	conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
