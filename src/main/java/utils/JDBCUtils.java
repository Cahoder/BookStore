package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * 	封装JAVA常用的JDBC操作
 */
public class JDBCUtils {

    /**
     * 	给预处理语句填参数
     * @param ps 预处理语句对象
     * @param params 可变参数列表
     */
    public static void installParams(PreparedStatement ps,Object...params){
        if (ps==null || params==null) return;

        for (int i = 0; i < params.length; i++) {
            try {
                ps.setObject(i+1,params[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 	获取系统当前SQL时间戳对象
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
    	Calendar instance = Calendar.getInstance();
    	return new Timestamp(instance.getTimeInMillis());
    }
    
}
