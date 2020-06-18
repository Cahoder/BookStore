package core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 	用于对查询出来的结果集进行封装
 * 
 * @see Query#executeQuery(String, Object[], ResultSetProcessor)
 * @author CAIHONGDE 根据不同的封装操作决定返回
 * @param <E>
 */
public interface ResultSetProcessor<E> {

	/**
	 * 	根据不同的封装操作返回
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	E process(ResultSet rs) throws SQLException;

}
