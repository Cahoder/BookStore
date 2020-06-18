package core;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *	C3P0开源数据库连接池
 */
public class C3P0Pool implements DBDataSource {

	@Override
	public DataSource getDataSource() throws SQLException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		Properties configs = new Properties();
		try {
        	configs.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("../db.config"));
            dataSource.setDriverClass(configs.getProperty("driverClassName"));
            dataSource.setJdbcUrl("jdbc:"
        			+configs.getProperty("usingDB")
                    +"://"+configs.getProperty("host")
                    +":"+configs.getProperty("port")+"/"
                    +configs.getProperty("dbName")+"?serverTimezone=GMT%2B8&useSSL=false");
            dataSource.setUser(configs.getProperty("username"));
            dataSource.setPassword(configs.getProperty("password"));
            dataSource.setInitialPoolSize(Integer.valueOf(configs.getProperty("initPoolSize")));
            dataSource.setMaxPoolSize(Integer.valueOf(configs.getProperty("maxTotal")));
            dataSource.setMinPoolSize(Integer.valueOf(configs.getProperty("minTotal")));
            dataSource.setCheckoutTimeout(Integer.valueOf(configs.getProperty("maxWaitMillis")));
            dataSource.setMaxIdleTime(Integer.valueOf(configs.getProperty("maxIdle")));
            //池中不够时一次增长多少个
            dataSource.setAcquireIncrement(Integer.valueOf(configs.getProperty("acquireIncrement")));
        } catch (Exception e) {
            throw new SQLException(e);
        }
		return dataSource;
	}
}
