package core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import utils.ReflectUtils;

/**
 * 将结果集封装到给定的JavaBean泛型
 * E 应该是一个符合标准的JavaBean
 * @param <E>
 * @author CAIHONGDE
 */
public class BeanProcessor<E> implements ResultSetProcessor<E>{
	
	private Class<E> clazz;
	
	public BeanProcessor(Class<E> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public E process(ResultSet rs) throws SQLException {
		E instance = null;
		try {
			if(!rs.first()) return instance;	//无数据
			instance = clazz.newInstance();
			ResultSetMetaData metaData = rs.getMetaData();
			
			int index = metaData.getColumnCount();
			for (int i = 0; i < index; i++) {
				String columnName = metaData.getColumnName(i+1);
				
				if(ReflectUtils.fieldExist(clazz, columnName)) 
					ReflectUtils.invokeSetter(instance, columnName, rs.getObject(columnName));
			}
		} catch (InstantiationException | 
				IllegalAccessException | 
				SecurityException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
}
