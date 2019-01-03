package db.handers;

import db.JdbcUtil;
import utils.TextUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/4.
 * 将查询结果处理为List<String>列表。
 */
public class HandlerToStringList implements ResultSetHandler {

    @Override
    public <T> T handle(ResultSet rs) {
        T ret = null;
        try {
            List<String> list = null;
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }

                String value = (String) rs.getObject(1);
                list.add(value);
            }
            if (list != null) {
                ret = (T) list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JdbcUtil.release(rs);
        }
        return ret;
    }
}
