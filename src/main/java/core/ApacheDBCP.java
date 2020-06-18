package core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 	使用Apache基金会提供的JDBC连接池
 * 	不依赖服务器的连接池（解耦）
 * @author cahoder
 */
public class ApacheDBCP implements DBDataSource {

	@Override
	public DataSource getDataSource() throws SQLException {
		Properties configs = new Properties();
		try {
        	//Thread.currentThread().getContextClassLoader()的路径是  WEB-INF/classes/
        	configs.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("../db.config"));
        	configs.setProperty("url", "jdbc:"
        			+configs.getProperty("usingDB")
                    +"://"+configs.getProperty("host")
                    +":"+configs.getProperty("port")+"/"
                    +configs.getProperty("dbName")+"?serverTimezone=GMT%2B8&useSSL=false");
        	return BasicDataSourceFactory.createDataSource(configs);
        } catch (IOException e) {
        	throw new SQLException("数据库连接失败：请在WEB-INF目录下创建数据库连接配置文件db.config后重试！");
        } catch (Exception e) {
        	throw new SQLException(e.getMessage());
		}
	}
	
}
