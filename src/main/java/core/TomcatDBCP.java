package core;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 	使用	Tomcat Web 服务器提供的实现连接池
 * @author cahoder
 *
 */
public class TomcatDBCP implements DBDataSource {

	@Override
	public DataSource getDataSource() throws SQLException {
		try {
			/**
			 * 	Tomcat服务器实现了这个接口，可以直接通过context.xml定义的参数与数据库建立连接
			 *	然后要通过Java的JNDI命名和目录接口来获取DataSource实例引用,不可以自行创建实例
			 */
			//Context 是 javax.naming 包提供的一个接口，用于查找数据库连接的配置文件
			Context envContext = new InitialContext();
			// java:comp/env 是 environment naming context（ENC）的标准JNDI CONTEXT
			envContext = (Context) envContext.lookup("java:comp/env");
			//DataSource 实例必须通过Tomcat提供的不可以自己实例化，lookup("xxx")要与context.xml中配置的name相同
			DataSource ds = (DataSource)envContext.lookup("jdbc/mysql");
			
            return ds;
		} catch (NamingException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
}
