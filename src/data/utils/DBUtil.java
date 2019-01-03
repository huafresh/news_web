package data.utils;

import data.entitys.Image;
import data.entitys.NormalNewsEntity;
import data.entitys.VideoNewsEntity;
import db.CRUDAndroid;
import db.DBConstant;
import db.handers.HandlerToBeanList;
import user.entitys.LoginUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hua on 2017/8/17.
 */
public class DBUtil {

    /**
     * 将一条新闻所有相关的信息插入相应的表中。
     * 任何插入环节失败，都将删除之前插入的记录，保证新闻数据的完整性
     *
     * @param results 新闻信息列表
     */
    public static void insertNormalNewsIntoDB(List<NormalNewsEntity> results) {
        CRUDAndroid db = new CRUDAndroid();
        for (NormalNewsEntity news : results) {

            if (isExist(DBConstant.Normal.TABLE_NAME, DBConstant.Normal.COLUMN_NEWS_ID, news.getNews_id())) {
                continue;
            }

            //插入一般新闻总表(news_normal)
            HashMap<String, Object> values = new HashMap<>();
            values.put(DBConstant.Normal.COLUMN_TITLE, news.getTitle());
            values.put(DBConstant.Normal.COLUMN_NEWS_ID, news.getNews_id());
            values.put(DBConstant.Normal.COLUMN_CHANNEL, news.getChannel());
            values.put(DBConstant.Normal.COLUMN_SHOW_TYPE, news.getShow_type());
            values.put(DBConstant.Normal.COLUMN_IS_PHOTO_SKIP, news.getIs_photo_skip());
            values.put(DBConstant.Normal.COLUMN_DATE, news.getDate());
            values.put(DBConstant.Normal.COLUMN_SOURCE, news.getSource());

            long id = db.insert(DBConstant.Normal.TABLE_NAME, null, values);
            if (id <= 0) {
                deleteNews(db, news.getNews_id());
                continue;
            }

            //插入图片表
            HashMap<String, Object> imageValues = new HashMap<>();
            List<Image> thumbs = news.getThumbs();
            int i = 0;
            for (i = 0; i < thumbs.size(); i++) {
                Image thumb = thumbs.get(i);
                imageValues.put(DBConstant.Image.COLUMN_NEWS_ID, thumb.getNews_id());
                imageValues.put(DBConstant.Image.COLUMN_TYPE, thumb.getType());
                imageValues.put(DBConstant.Image.COLUMN_URL, thumb.getUrl());
                long l = db.insert(DBConstant.Image.TABLE_NAME, null, imageValues);
                if (l <= 0) {
                    break;
                }
            }
            if (i < thumbs.size()) {
                deleteNews(db, news.getNews_id());
                continue;
            }

            List<Image> bodys = news.getBodys();
            for (i = 0; i < bodys.size(); i++) {
                Image body = bodys.get(i);
                imageValues.put(DBConstant.Image.COLUMN_NEWS_ID, body.getNews_id());
                imageValues.put(DBConstant.Image.COLUMN_TYPE, body.getType());
                imageValues.put(DBConstant.Image.COLUMN_URL, body.getUrl());
                imageValues.put(DBConstant.Image.COLUMN_REF, body.getRef());
                imageValues.put(DBConstant.Image.COLUMN_PIXEL, body.getPixel());
                long l = db.insert(DBConstant.Image.TABLE_NAME, null, imageValues);
                if (l <= 0) {
                    break;
                }
            }
            if (i < thumbs.size()) {
                deleteNews(db, news.getNews_id());
                continue;
            }

            //插入body表
            NormalNewsEntity.Body body = news.getBody();
            HashMap<String, Object> bodyValues = new HashMap<>();
            bodyValues.put(DBConstant.Body.COLUMN_NEWS_ID, body.getNews_id());
            bodyValues.put(DBConstant.Body.COLUMN_TITLE, body.getTitle());
            bodyValues.put(DBConstant.Body.COLUMN_SOURCE, body.getSource());
            bodyValues.put(DBConstant.Body.COLUMN_CONTNET, body.getContent());
            bodyValues.put(DBConstant.Body.COLUMN_TIME, body.getTime());
            bodyValues.put(DBConstant.Body.COLUMN_SHARE_LINK, body.getShare_link());
            bodyValues.put(DBConstant.Body.COLUMN_SKIP_TYPE, body.getSkip_type());
            long l = db.insert(DBConstant.Body.TABLE_NAME, null, bodyValues);
            if (l <= 0) {
                deleteNews(db, news.getNews_id());
            }
        }
    }

    /**
     * 将视频列表信息插入到相应的表中
     *
     * @param list 视频列表信息
     */
    public static void insertVideoListIntoDb(List<VideoNewsEntity> list) {
        if (list == null) {
            return;
        }
        CRUDAndroid db = new CRUDAndroid();
        for (VideoNewsEntity video : list) {

            //插入到用户表
            if (!isExist(DBConstant.User.TABLE_NAME, DBConstant.User.COLUMN_USER_ID, video.getAuthor().getUser_id())) {
                HashMap<String, Object> values = new HashMap<>();
                values.put(DBConstant.User.COLUMN_USER_ID, video.getAuthor().getUser_id());
                values.put(DBConstant.User.COLUMN_MAIL, video.getAuthor().getMail());
                values.put(DBConstant.User.COLUMN_NICK_NAME, video.getAuthor().getNick_name());
                values.put(DBConstant.User.COLUMN_AVATAR, video.getAuthor().getAvatar_path());
                CRUDAndroid mSQLiteBase = new CRUDAndroid();
                mSQLiteBase.insert(DBConstant.User.TABLE_NAME, null, values);
            }

            //插入到视频信息表
            if (!isExist(DBConstant.Video.TABLE_NAME, DBConstant.Video.COLUMN_VIDEO_ID, video.getVideo_id())) {
                HashMap<String, Object> videoValues = new HashMap<>();
                videoValues.put(DBConstant.Video.COLUMN_VIDEO_ID, video.getVideo_id());
                videoValues.put(DBConstant.Video.COLUMN_TITLE, video.getTitle());
                videoValues.put(DBConstant.Video.COLUMN_USER_ID, video.getAuthor().getUser_id());
                videoValues.put(DBConstant.Video.COLUMN_360P_URL, video.get_360p_url());
                videoValues.put(DBConstant.Video.COLUMN_480P_URL, video.get_480p_url());
                videoValues.put(DBConstant.Video.COLUMN_720P_URL, video.get_720p_url());
                videoValues.put(DBConstant.Video.COLUMN_DURATION, video.getDuration());
                videoValues.put(DBConstant.Video.COLUMN_WIDTH, video.getWidth());
                videoValues.put(DBConstant.Video.COLUMN_HEIGHT, video.getHeight());
                videoValues.put(DBConstant.Video.COLUMN_CATEGORY_NAME, video.getCategory_name());
                videoValues.put(DBConstant.Video.COLUMN_LIKE_COUNT, video.getLike_count());
                videoValues.put(DBConstant.Video.COLUMN_UNLIKE_COUNT, video.getUnlike_count());
                videoValues.put(DBConstant.Video.COLUMN_SHARE_COUNT, video.getShare_count());
                videoValues.put(DBConstant.Video.COLUMN_PLAY_COUNT, video.getPlay_count());
                videoValues.put(DBConstant.Video.COLUMN_COMMENT_COUNT, video.getComment_count());
                videoValues.put(DBConstant.Video.COLUMN_COMMENT_ID, video.getComment_id());
                videoValues.put(DBConstant.Video.COLUMN_DATE, String.valueOf(System.currentTimeMillis()));
                db.insert(DBConstant.Video.TABLE_NAME, null, videoValues);
            }
        }
    }

    private static void deleteNews(CRUDAndroid db, String newsId) {
        List<String> tableList = new ArrayList<>();
        tableList.add(DBConstant.Normal.TABLE_NAME);
        //        tableList.add("image"); //外键约束下，从表会自动删除
        //        tableList.add("body");
        for (String table : tableList) {
            db.delete(table, new String[]{DBConstant.Normal.COLUMN_NEWS_ID}, new String[]{newsId});
        }

    }

    private static boolean isExist(String tableName, String column, String value) {
        CRUDAndroid db = new CRUDAndroid();
        Object result = db.query(tableName, null, new String[]{column},
                new Object[]{value}, new HandlerToBeanList(LoginUserInfo.class));
        return result != null;
    }

}
