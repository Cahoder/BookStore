package core;

import java.sql.SQLException;

/**
 * 提供一个执行具体Query操作的接口,通过回调完成具体操作
 * @author cahoder
 *
 */
public interface QueryCallback {

    /**
     * @throws SQLException
     */
    Object doExecute() throws SQLException;
    
}
