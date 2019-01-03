package db;

/**
 * Created by hua on 2017/7/20.
 * 数据库表常量
 */
public class DBConstant {

    /**
     * 注册用户信息表
     */
    public interface User {

        //表名
        String TABLE_NAME = "user";

        //列名
        String _ID = "_id";
        String COLUMN_USER_ID = "user_id";
        String COLUMN_PASSWORD = "password";
        String COLUMN_NICK_NAME = "nick_name";
        String COLUMN_BIND_PHONE = "bind_phone";
        String COLUMN_MAIL = "mail";
        String COLUMN_THIRD_PLAT = "third_plat";
        String COLUMN_THIRD_UNIQUE_ID = "third_unique_id";
        String COLUMN_AVATAR = "avatar_path";
        String COLUMN_COLLECT = "collect";
        String COLUMN_COLUMN = "column";
    }

    /**
     * 一般类型新闻表
     */
    public interface Normal {
        //表名
        String TABLE_NAME = "news_normal";

        //列名
        String _ID = "_id";
        String COLUMN_TITLE = "title";
        String COLUMN_NEWS_ID = "news_id";
        String COLUMN_DATE = "date";
        String COLUMN_CHANNEL = "channel";
        String COLUMN_IS_PHOTO_SKIP = "is_photo_skip";
        String COLUMN_SHOW_TYPE = "show_type";
        String COLUMN_SOURCE = "source";
        String COLUMN_REPLY_COUNT = "reply_count";
        String COLUMN_DIGEST = "digest";
    }

    /**
     * 图片表
     */
    public interface Image {
        //表名
        String TABLE_NAME = "image";

        //列名
        String _ID = "_id";
        String COLUMN_NEWS_ID = "news_id";
        String COLUMN_TYPE = "type";
        String COLUMN_URL = "url";
        String COLUMN_REF = "ref";
        String COLUMN_PIXEL = "pixel";

    }

    /**
     * 评论表
     */
    public interface Comment {
        //表名
        String TABLE_NAME = "comment";

        //列名
        String _ID = "_id";
        String COLUMN_COMMENT_ID = "comment_id";
        String COLUMN_COTENT= "cotent";
        String COLUMN_CREATE_TIME= "create_time";
        String COLUMN_NEWS_ID= "news_id";
        String COLUMN_USER_ID= "user_id";
        String COLUMN_VOTE_COUNT= "vote_count";
        String COLUMN_REPLY_COUNT= "reply_count";
        String COLUMN_REPLY_COMMENT= "reply_comment";

    }

    /**
     * 新闻详情表
     */
    public interface Body {
        //表名
        String TABLE_NAME = "body";

        //列名
        String _ID = "_id";
        String COLUMN_NEWS_ID = "news_id";
        String COLUMN_TITLE = "title";
        String COLUMN_SOURCE = "source";
        String COLUMN_TIME = "time";
        String COLUMN_CONTNET = "contnet";
        String COLUMN_SHARE_LINK = "share_link";
        String COLUMN_SKIP_TYPE = "skip_type";
        String COLUMN_RELATIVE_SYS = "relative_sys ";
    }

    /**
     * 视频详情表
     */
    public interface Video {
        //表名
        String TABLE_NAME = "news_video";

        //列名
        String COLUMN__ID = "_id";
        String COLUMN_VIDEO_ID = "video_id";
        String COLUMN_TITLE = "title";
        String COLUMN_USER_ID = "user_id";
        String COLUMN_360P_URL = "_360p_url";
        String COLUMN_480P_URL = "_480p_url";
        String COLUMN_720P_URL = "_720p_url";
        String COLUMN_DURATION = "duration";
        String COLUMN_WIDTH = "width";
        String COLUMN_HEIGHT = "height";
        String COLUMN_CATEGORY_NAME = "category_name";
        String COLUMN_LIKE_COUNT = "like_count";
        String COLUMN_UNLIKE_COUNT = "unlike_count";
        String COLUMN_SHARE_COUNT = "share_count";
        String COLUMN_PLAY_COUNT = "play_count";
        String COLUMN_COMMENT_COUNT = "comment_count";
        String COLUMN_COMMENT_ID = "comment_id";
        String COLUMN_DATE = "date";

    }
}
