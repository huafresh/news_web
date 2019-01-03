package db.handers;

import java.sql.ResultSet;

/**
 * Created by hua on 2017/7/20.
 * 数据库查询结果集处理接口
 */
public interface ResultSetHandler {

    <T> T handle(ResultSet rs);
}
