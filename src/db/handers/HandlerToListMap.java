package db.handers;

import db.JdbcUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hua on 2017/7/20.
 * 把查询的结果转换为List<HashMap<String, Object>>对象，其中hashMap存储的是某一行的列名与值
 */
public class HandlerToListMap implements ResultSetHandler {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T handle(ResultSet rs) {

        try {
            List<HashMap<String, Object>> resultList = null;
            while (rs.next()) {
                if (resultList == null) {
                    resultList = new ArrayList<>();
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    hashMap.put(columnName, rs.getObject(i + 1));
                }
                resultList.add(hashMap);
            }
            return (T) resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(rs);
        }
        return null;

    }
}
