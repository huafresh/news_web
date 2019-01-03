package db.handers;

import db.JdbcUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/7/20.
 * 把查询的结果转换为list集合
 */
public class HandlerToBeanList implements ResultSetHandler {

    private Class mClass;

    public HandlerToBeanList(Class cls) {
        mClass = cls;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T handle(ResultSet rs) {
        T ret = null;
        try {
            List<Object> list = null;
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }

                ResultSetMetaData metaData = rs.getMetaData();
                Object bean = mClass.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {

                    String columnName = metaData.getColumnName(i + 1);
                    Object value = rs.getObject(i + 1);
                    try {
                        Field f = mClass.getDeclaredField(columnName);
                        f.setAccessible(true);
                        f.set(bean, value);
                    } catch (NoSuchFieldException e) {
                        //e.printStackTrace();
                    }
                }
                list.add(bean);
            }
            if (list != null) {
                ret = (T) list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(rs);
        }
        return ret;
    }
}
