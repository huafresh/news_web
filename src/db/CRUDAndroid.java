package db;

import db.handers.ResultSetHandler;
import utils.TextUtils;

import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by hua on 2017/8/7.
 * 仿Android下的增删改查方法。
 */
public class CRUDAndroid {

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param table          the table to insert the row into
     * @param nullColumnHack the <code>nullColumnHack</code> parameter
     *                       provides the name of nullable column name to explicitly insert a NULL into
     *                       in the case where your <code>values</code> is empty.
     * @param values         this map contains the initial column values for the
     *                       row. The keys should be the column names and the values the
     *                       column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public synchronized long insert(String table, String nullColumnHack, HashMap<String, Object> values) {

        //insert into table1(field1,field2) values(value1,value2)

        long ret = -1;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("insert into ");
            builder.append(table);

            if (!(values != null && values.size() > 0)) {
                if (!TextUtils.isEmpty(nullColumnHack)) {
                    if (values == null) {
                        values = new HashMap<>();
                    }
                    values.put(nullColumnHack, null);
                } else {
                    return -1;
                }
            }

            int len = values.size();
            String[] columnArray = new String[len];
            Object[] valueArray = new Object[len];

            //取出列名和相应的值
            int j = 0;
            for (String key : values.keySet()) {
                columnArray[j] = key;
                valueArray[j] = values.get(key);
                j++;
            }

            //拼接sql语句
            buildSpecString(columnArray, builder, true);
            builder.append(" values");
            String[] marks = new String[len];
            for (int i = 0; i < len; i++) {
                marks[i] = "?";
            }
            buildSpecString(marks, builder, true);

            //执行插入
            String sql = builder.toString();
            long sum = JdbcUtil.update(sql, valueArray, Statement.RETURN_GENERATED_KEYS);
            if (sum > 0) {
//                String sql2 = "select last_insert_id()";
//                ResultSet rs = JdbcUtil.query(sql2, null, null);
//                try {
//                    if (rs != null && rs.next()) {
//                        ret = (int) rs.getObject(1);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                ret = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    //构造形如：(filed1,filed2,filed3)的字符串
    private static void buildSpecString(String[] columns, StringBuilder builder) {
        buildSpecString(columns, builder, false);
    }

    //构造形如：(filed1,filed2,filed3)的字符串
    private static void buildSpecString(String[] columns, StringBuilder builder, boolean isContainBrackets) {
        if (columns != null && columns.length > 0) {
            int len = columns.length;
            if (isContainBrackets) {
                builder.append("(");
            }
            for (int i = 0; i < len; i++) {
                String col = columns[i];
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(col);
            }
            if (isContainBrackets) {
                builder.append(")");
            }
        }
    }

    //构造形如：filed1=?&filed2=?&filed3=?的字符串，其中&字符的内容由参数space决定
    private static void buildSpecString(String[] columns, StringBuilder builder, String space) {
        if (columns != null && columns.length > 0) {
            int len = columns.length;
            for (int i = 0; i < len; i++) {
                String col = columns[i];
                if (i > 0) {
                    builder.append(space);
                }
                builder.append(col);
                builder.append("=?");
            }
        }
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param table        the table to delete from
     * @param whereClauses provide column name.
     * @param whereArgs    You may include ?s in the where clause, which
     *                     will be replaced by the values from whereArgs. The values
     *                     will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, -1 if an error occurred
     */
    public long delete(String table, String[] whereClauses, Object[] whereArgs) {

        //delete from table where _id=1 and user_id=123465

        long ret = -1;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("delete from ");
            builder.append(table);
            builder.append(" where ");
            buildSpecString(whereClauses, builder, " and ");
            ret = JdbcUtil.update(builder.toString(), whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;

    }


    /**
     * Convenience method for updating rows in the database.
     *
     * @param table        the table to update in
     * @param values       a map from column names to new column values. null is a
     *                     valid value that will be translated to NULL.
     * @param whereClauses provide where column names.
     * @param whereArgs    You may include ?s in the where clause, which
     *                     will be replaced by the values from whereArgs. The values
     *                     will be bound as Strings.
     * @return the number of rows affected
     */
    public long update(String table, HashMap<String, Object> values, String[] whereClauses, Object[] whereArgs) {

        //update table set _id=1,user_id="123456" where _id=1

        long ret = -1;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("update ");
            builder.append(table);
            builder.append(" set ");

            int len = values.size();
            String[] columnArray = new String[len];
            Object[] valueArray = new Object[len];

            //取出列名和相应的值
            int j = 0;
            for (String key : values.keySet()) {
                columnArray[j] = key;
                valueArray[j] = values.get(key);
                j++;
            }

            //构造set部分
            buildSpecString(columnArray, builder, ",");
            builder.append(" where ");
            //构造where部分
            buildSpecString(whereClauses, builder, " and ");

            //合并问号替换值
            Object[] args = new Object[valueArray.length + whereArgs.length];
            System.arraycopy(valueArray, 0, args, 0, valueArray.length);
            System.arraycopy(whereArgs, 0, args, valueArray.length, whereArgs.length);

            ret = JdbcUtil.update(builder.toString(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Query the given table, handler will decide what will be returned.
     *
     * @param table         The table name to compile the query against.
     * @param columns       A list of which columns to return. Passing null will
     *                      return all columns, which is discouraged to prevent reading
     *                      data from storage that isn't going to be used.
     * @param selections    A filter declaring which rows to return, Passing null
     *                      will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @return handler will decide what will be returned.see{@link ResultSetHandler}
     */
    public <T> T query(String table, String[] columns, String[] selections,
                       Object[] selectionArgs, ResultSetHandler handler) {

        //select _id,user_id from table where _id=1 and user_id="123456"

        StringBuilder where = new StringBuilder();
        for (Object selectionArg : selectionArgs) {
            where.append("&=?");
        }

        return DataBaseSupport.with()
                .table(table)
                .select(columns)
                .where(where.toString(), selections, selectionArgs)
                .handler(handler)
                .find();

    }

    //使用以下内部类，可以实现链式查询，易扩展
    public static class DataBaseSupport {

        private String tableName;
        private String[] columns;
        private String where;
        private String[] whereColums;
        private Object[] whereArgs;
        private String order;
        private String limit;
        private ResultSetHandler handler;

        public static DataBaseSupport with() {
            return new DataBaseSupport();
        }

        //设置表名
        public DataBaseSupport table(String tableName) {
            this.tableName = tableName;
            return this;
        }

        //设置返回的列
        public DataBaseSupport select(String[] columns) {
            this.columns = columns;
            return this;
        }

        //设置结果集处理器
        public DataBaseSupport handler(ResultSetHandler handler) {
            this.handler = handler;
            return this;
        }

        //设置设置行数限制
        public DataBaseSupport limit(int limit) {
            this.limit = " limit " + limit;
            return this;
        }

        //设置查询条件
        public DataBaseSupport where(String where, String column, Object arg) {
            this.where = where;
            this.whereColums = new String[]{column};
            this.whereArgs = new Object[]{arg};
            return this;
        }

        //设置查询条件
        public DataBaseSupport where(String where, String[] columns, Object[] ars) {
            this.where = where;
            this.whereColums = columns;
            this.whereArgs = ars;
            return this;
        }

        //设置排序方式
        public DataBaseSupport order(String col, String order) {
            this.order = " order by " + col + " " + order;
            return this;
        }


        //开始查询
        public <T> T find() {
            return find(tableName);
        }

        //开始查询
        public <T> T find(String tableName) {
            return find(tableName, handler);
        }

        //开始查询
        public <T> T find(String tableName, ResultSetHandler handler) {
            try {
                if (TextUtils.isEmpty(tableName)) {
                    return null;
                }

                StringBuilder builder = new StringBuilder();
                builder.append("select ");

                if (columns != null && columns.length > 0) {
                    buildSpecString(columns, builder);
                } else {
                    builder.append("*");
                }

                builder.append(" from ");
                builder.append(tableName);
                builder.append(" where ");
                builder.append(where);

                if (!TextUtils.isEmpty(order)) {
                    builder.append(order);
                }

                if (!TextUtils.isEmpty(limit)) {
                    builder.append(limit);
                }

                String sql = builder.toString();
                for (String colum : whereColums) {
                    sql = sql.replaceFirst("&", colum);
                }

                return JdbcUtil.query(sql, whereArgs, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
