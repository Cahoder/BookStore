package core;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface DBDataSource {
	
	/**
	 * 	获取数据库数据源
	 * @return
	 */
	public DataSource getDataSource() throws SQLException;
	
}
