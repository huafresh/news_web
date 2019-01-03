package user;

import db.CRUDAndroid;
import db.DBConstant;
import db.handers.HandlerToStringList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import user.entitys.ColumnEntity;
import utils.TextUtils;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 栏目相关业务处理帮助类
 *
 * @author hua
 * @version 2017/10/18 16:45
 */
public class ColumnHelper {

    /**
     * 数据库操作工具
     */
    private CRUDAndroid mSqlDataBase;
    private ServletContext context;

    public ColumnHelper(ServletContext context) {
        this.context = context;
        mSqlDataBase = new CRUDAndroid();
    }

    /**
     * 查询栏目列表信息
     *
     * @param user_id 用户id
     * @param results result[0]存放状态码，result[1]存放状态信息
     * @return 栏目列表信息(json格式字符串)
     */
    public String queryColumnList(String user_id, String[] results) {
        try {
            if (TextUtils.isEmpty(user_id)) {
                //对于未登录的游客，始终返回默认的栏目信息
                List<ColumnEntity> columnList = getColumnListFromXml();
                return JSONArray.fromObject(columnList).toString();
            } else {
                List<String> list = CRUDAndroid.DataBaseSupport.with()
                        .table(DBConstant.User.TABLE_NAME)
                        .select(new String[]{DBConstant.User.COLUMN_COLUMN})
                        .where("&=?", DBConstant.User.COLUMN_USER_ID, user_id)
                        .handler(new HandlerToStringList())
                        .find();
                String resultString = "";
                if (list != null && list.size() > 0) {
                    resultString = list.get(0);
                }
                if (!TextUtils.isEmpty(resultString)) {
                    return resultString;
                } else {
                    //如果该用户还没有栏目信息，则为此用户插入默认的栏目信息
                    List<ColumnEntity> columnList = getColumnListFromXml();
                    String columnInfo = JSONArray.fromObject(columnList).toString();
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put(DBConstant.User.COLUMN_COLUMN, columnInfo);
                    mSqlDataBase.insert(DBConstant.User.TABLE_NAME, null, maps);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 改变一个栏目的显示状态。
     *
     * @param user_id   用户id
     * @param column_id 栏目id
     * @param isAdd     是否显示
     * @param results   result[0]存放状态码，result[1]存放状态信息
     * @return 新的栏目列表信息(json格式字符串)
     */
    public String changeColumnState(String user_id, String column_id, boolean isAdd, String[] results) {
        final String temp = isAdd ? "添加" : "删除";
        String error_info = "";
        if (TextUtils.isEmpty(user_id)) {
            error_info = temp + "栏目失败，用户id不能为空";
        } else if (TextUtils.isEmpty(column_id)) {
            error_info = temp + "栏目失败，栏目id不能为空";
        } else {
            String curColumnString = queryColumnList(user_id, null);
            if (TextUtils.isEmpty(curColumnString)) {
                error_info = "服务器异常";
            } else {
                JSONArray jsonArray = JSONArray.fromObject(curColumnString);
                Object[] objects = jsonArray.toArray();
                int i = 0;
                for (i = 0; i < objects.length; i++) {
                    ColumnEntity entity = (ColumnEntity) objects[i];
                    if (entity.getColumn_id().equals(column_id)) {
                        entity.setAdd(isAdd);
                    }
                }
                if (i == objects.length) {
                    error_info = temp + "栏目失败，栏目id不存在";
                } else {
                    error_info = temp + "栏目" + column_id + "成功";
                }
            }
        }

        results[1] = error_info;
        String result = queryColumnList(user_id, results);
        if (TextUtils.isEmpty(result)) {
            results[1] = "服务器异常";
        }
        return result;
    }

    private List<ColumnEntity> getColumnListFromXml() {
        ColumnXmlParseHelper helper = new ColumnXmlParseHelper();
        return helper.getColumnEntityList(ColumnHelper.class.getClassLoader().getResourceAsStream("column.xml"));
    }


}
