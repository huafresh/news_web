package user;

import com.sun.istack.internal.NotNull;
import db.CRUDAndroid;
import db.DBConstant;
import db.handers.HandlerToStringList;
import user.entitys.CollectEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 收藏相关业务处理帮助
 *
 * @author hua
 * @version 2017/10/18 16:43
 */
public class CollectHelper {

    /**
     * 数据库操作工具
     */
    private CRUDAndroid mSqlDataBase;

    public CollectHelper() {
        mSqlDataBase = new CRUDAndroid();
    }

    /**
     * 添加一条收藏信息
     *
     * @param user_id 用户id
     * @param news_id 新闻id
     * @param result  result[0]存放状态码，result[1]存放状态信息
     * @return 是否收藏成功
     */
    public boolean addUserCollectInfo(String user_id, String news_id, @NotNull String[] result) {

        boolean isSuccess = false;
        String errorInfo = "";

        try {
            HashMap<String, Object> values = new HashMap<>();
            String curCollect = queryCollect(user_id);
            String newCollect = curCollect + news_id;
            values.put(DBConstant.User.COLUMN_COLLECT, newCollect);
            long ret = mSqlDataBase.update(DBConstant.User.TABLE_NAME, values, new String[]{DBConstant.User.COLUMN_USER_ID},
                    new String[]{user_id});
            if (ret <= 0) {
                errorInfo = "收藏失败";
            } else {
                errorInfo = "收藏成功";
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorInfo = "未知异常";
        }

        result[1] = errorInfo;
        return isSuccess;
    }

    /**
     * 取消收藏
     *
     * @param user_id 用户id
     * @param news_id 新闻id
     * @param results result[0]存放状态码，result[1]存放状态信息
     * @return 是否取消收藏成功
     */
    public boolean cancelOneCollectNews(String user_id, String news_id, String[] results) {
        boolean isSuccess = false;
        String errorInfo = "";

        try {
            HashMap<String, Object> values = new HashMap<>();
            String curCollect = queryCollect(user_id);
            String newCollect = "";
            if (curCollect.contains(news_id)) {
                newCollect = curCollect.replace(news_id, "");
            } else {
                results[1] = "未订阅该新闻";
                return false;
            }
            values.put(DBConstant.User.COLUMN_COLLECT, newCollect);
            long ret = mSqlDataBase.update(DBConstant.User.TABLE_NAME, values, new String[]{DBConstant.User.COLUMN_USER_ID},
                    new String[]{user_id});
            if (ret <= 0) {
                errorInfo = "取消收藏失败";
            } else {
                errorInfo = "取消收藏成功";
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorInfo = "未知异常";
        }

        results[1] = errorInfo;
        return isSuccess;
    }

    /**
     * 查询用户的收藏列表
     *
     * @param user_id 用户id
     * @return 收藏列表
     */
    public List<CollectEntity> queryUserCollectInfo(String user_id) {

        List<CollectEntity> resultList = null;
        try {
            String collect = queryCollect(user_id);
            String[] news_id_s = collect.split(";");
            resultList = new ArrayList<>();
            for (String news_id : news_id_s) {
                CollectEntity bean = new CollectEntity();
                bean.setNew_id(news_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @NotNull
    private String queryCollect(String user_id) {
        List<String> list = CRUDAndroid.DataBaseSupport.with()
                .table(DBConstant.User.TABLE_NAME)
                .select(new String[]{DBConstant.User.COLUMN_COLLECT})
                .where("&=?", DBConstant.User.COLUMN_USER_ID, user_id)
                .handler(new HandlerToStringList())
                .find();
        return list != null && list.size() > 0 ? list.get(0) : "";
    }

}
