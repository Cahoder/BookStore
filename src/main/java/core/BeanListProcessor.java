package core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.ReflectUtils;

/**
 * 将结果集封装到给定的List<JavaBean>容器
 * @param <E>
 * @author CAIHONGDE
 */
public class BeanListProcessor<E> implements ResultSetProcessor<List<E>>{
	
	private Class<E> clazz;
	
	public BeanListProcessor(Class<E> clazz) {
		this.clazz = clazz;
	}

	@Override
	public List<E> process(ResultSet rs) throws SQLException {
		List<E> list = new ArrayList<E>();
		String[] columns = getColumns(rs);
		
		while(rs.next()) {
			try {
				E instance = clazz.newInstance();
				for (String column : columns) 
					ReflectUtils.invokeSetter(instance, column, rs.getObject(column));
				
				list.add(instance);
			} catch (InstantiationException | IllegalAccessException e) {}
		}
		return list;
	}
	
	/**
	 * 获取应该插入数据的成员属性列表
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private String[] getColumns(ResultSet rs) throws SQLException {
		ArrayList<String> lists = new ArrayList<String>();
		
		ResultSetMetaData metaData = rs.getMetaData();
		int index = metaData.getColumnCount();
		
		for (int i = 0; i < index; i++) {
			String columnName = metaData.getColumnName(i+1);
			
			if(ReflectUtils.fieldExist(clazz, columnName)) 
				lists.add(columnName);
		}
		
		return lists.toArray(new String[lists.size()]);
	}

}
