package news;

import constants.Constant;
import data.entitys.Image;
import data.entitys.VideoNewsEntity;
import db.CRUDAndroid;
import db.DBConstant;
import db.handers.HandlerToBeanList;
import db.handers.HandlerToListMap;
import db.handers.HandlerToStringList;
import user.entitys.LoginUserInfo;
import utils.TextUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hua on 2017/8/15.
 * 处理新闻请求
 */
public class NewsManager {

    private final String tableName;
    private CRUDAndroid mSqlDataBase;

    public NewsManager() {
        tableName = DBConstant.Normal.TABLE_NAME;
        mSqlDataBase = new CRUDAndroid();
    }

    public static NewsManager getInstance() {
        return NewsManager.HOLDER.sInstance;
    }

    private static final class HOLDER {
        private static final NewsManager sInstance = new NewsManager();
    }


    /**
     * 根据新闻id获取新闻详情
     *
     * @param news_id 新闻id
     * @return 新闻详情bean
     */
    public NormalNewsDetailBean getNormalNewsDetail(String news_id) {

        try {
            List<NormalNewsDetailBean> detailList = null;
            detailList = CRUDAndroid.DataBaseSupport.with()
                    .table(DBConstant.Body.TABLE_NAME)
                    .where("&=?", DBConstant.Body.COLUMN_NEWS_ID, news_id)
                    .limit(1)
                    .handler(new HandlerToBeanList(NormalNewsDetailBean.class))
                    .find();

            List<String> imageList = null;
            imageList = CRUDAndroid.DataBaseSupport.with()
                    .table(DBConstant.Image.TABLE_NAME)
                    .select(new String[]{DBConstant.Image.COLUMN_URL})
                    .where("&=? and &=?", new String[]{DBConstant.Image.COLUMN_NEWS_ID, DBConstant.Image.COLUMN_TYPE},
                            new Object[]{news_id, 10})
                    .handler(new HandlerToStringList())
                    .find();
            NormalNewsDetailBean newsDetailBean = detailList.get(0);
            newsDetailBean.setImages(imageList);

            return newsDetailBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取视频新闻列表
     *
     * @param count    请求数量
     * @param isPullUp 是否是上拉。
     *                 对于下拉刷新：返回lastTime之后的count数量的新闻
     *                 对于上拉刷新：返回lastTime之前的count数量的新闻
     * @param time     最后一次请求的时间。为空则返回count数量的最新的视频列表数据
     * @return 视频新闻列表
     */
    public List<VideoNewsEntity> getVideoNewsList(int count, boolean isPullUp, String time) {
        List<VideoNewsEntity> resultList;

        String where = null;
        if (!TextUtils.isEmpty(time)) {
            if (isPullUp) {
                where = "&<?";
            } else {
                where = "&>?";
            }
        } else {
            where = "&>?";
            time = "0";
        }
        resultList = CRUDAndroid.DataBaseSupport.with()
                .table(DBConstant.Video.TABLE_NAME)
                .where(where, new String[]{DBConstant.Video.COLUMN_DATE},
                        new String[]{time})
                .order(DBConstant.Normal.COLUMN_DATE, "desc")
                .handler(new HandlerToBeanList(VideoNewsEntity.class))
                .limit(count)
                .find();

        //传了time，但是查询结果为null，那么可能是time是最大值，此时尝试返回最新的数据
        if (resultList == null && !TextUtils.isEmpty(time)) {
            resultList = CRUDAndroid.DataBaseSupport.with()
                    .table(DBConstant.Video.TABLE_NAME)
                    .where("&>?", new String[]{DBConstant.Video.COLUMN_DATE},
                            new String[]{"0"})
                    .order(DBConstant.Normal.COLUMN_DATE, "desc")
                    .handler(new HandlerToBeanList(VideoNewsEntity.class))
                    .limit(count)
                    .find();
        }

        if (resultList != null) {
            for (VideoNewsEntity video : resultList) {


                List<LoginUserInfo> temp;
                temp = CRUDAndroid.DataBaseSupport.with()
                        .table(DBConstant.User.TABLE_NAME)
                        .where("&=?", new String[]{DBConstant.User.COLUMN_USER_ID},
                                new String[]{video.getUser_id()})
                        .handler(new HandlerToBeanList(LoginUserInfo.class))
                        .find();
                if (temp != null && temp.size() > 0) {
                    video.setAuthor(temp.get(0));
                }

                List<Image> images;
                images = CRUDAndroid.DataBaseSupport.with()
                        .table(DBConstant.Image.TABLE_NAME)
                        .where("&=? and &=?", new String[]{DBConstant.Image.COLUMN_NEWS_ID, DBConstant.Image.COLUMN_TYPE},
                                new String[]{video.getVideo_id(), "3"})
                        .handler(new HandlerToBeanList(Image.class))
                        .find();
                if (images != null && images.size() > 0) {
                    video.setCover_path(images.get(0).getUrl());
                }
            }
        }

        return resultList;
    }

    /**
     * 请求某一频道的新闻列表信息
     *
     * @param channel  频道名称
     * @param count    请求的新闻数量
     * @param isPullUp 是否是上拉。
     *                 对于下拉刷新：返回time之后count数量的新闻
     *                 对于上拉刷新：返回time之前count数量的新闻
     * @param time     时间戳
     */
    public List<NormalNewsPreviewBean> getNormalNewsPreviewList(String channel, int count, boolean isPullUp, String time) {

        String channelId = getChannelId(channel);

        if (TextUtils.isEmpty(channelId)) {
            return null;
        }

        List<NormalNewsPreviewBean> newsList = null;
        if (isPullUp) {
            newsList = queryChannel(channelId, count, time);
        } else {
            newsList = queryChannel(channelId, count);
        }
        return newsList;
    }

    private String getChannelId(String channel) {
        String channelId = "";
        switch (channel) {
            case "fun":
                channelId = Constant.CHANNEL_ID_FUN;
                break;
            case "sport":
                channelId = Constant.CHANNEL_ID_SPORT;
                break;
            case "finance":
                channelId = Constant.CHANNEL_ID_FINANCE;
                break;
            case "society":
                channelId = Constant.CHANNEL_ID_SOCIETY;
                break;
            case "NBA":
                channelId = Constant.CHANNEL_ID_NBA;
                break;
            case "history":
                channelId = Constant.CHANNEL_ID_HISTORY;
                break;
            case "main":
                channelId = Constant.CHANNEL_ID_MAIN;
                break;
        }
        return channelId;
    }

    /**
     * 查询指定频道最新的新闻预览数据
     *
     * @param channel 频道
     * @param count   请求数量
     * @return 新闻列表
     */
    private List<NormalNewsPreviewBean> queryChannel(String channel, int count) {
        List<NormalNewsPreviewBean> previewList = null;
        try {
            previewList = CRUDAndroid.DataBaseSupport.with()
                    .table(DBConstant.Normal.TABLE_NAME)
                    .where("&=?", DBConstant.Normal.COLUMN_CHANNEL, channel)
                    .order(DBConstant.Normal.COLUMN_DATE, "desc")
                    .limit(count)
                    .handler(new HandlerToBeanList(NormalNewsPreviewBean.class))
                    .find();

            for (NormalNewsPreviewBean bean : previewList) {
                List<ImageBean> imageList = CRUDAndroid.DataBaseSupport.with()
                        .table(DBConstant.Image.TABLE_NAME)
                        .where("&=? and &=?", new String[]{DBConstant.Image.COLUMN_NEWS_ID,
                                DBConstant.Image.COLUMN_TYPE}, new String[]{bean.getNews_id(), "1"})
                        .handler(new HandlerToBeanList(ImageBean.class))
                        .find();

                try {
                    ImageBean imgBean1 = imageList.get(0);
                    bean.setImg_url(imgBean1.getUrl());
                    ImageBean imgBean2 = imageList.get(1);
                    bean.setImgextra_url1(imgBean2.getUrl());
                    ImageBean imgBean3 = imageList.get(2);
                    bean.setImgextra_url2(imgBean3.getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return previewList;
    }


    /**
     * 查询指定频道某一时间点之前的新闻预览数据
     *
     * @param channel 频道
     * @param count   请求数量
     * @return 新闻列表
     */
    private List<NormalNewsPreviewBean> queryChannel(String channel, int count, String time) {
        return CRUDAndroid.DataBaseSupport.with()
                .table(DBConstant.Normal.TABLE_NAME)
                .where("&=? and &<?", new String[]{DBConstant.Normal.COLUMN_CHANNEL, DBConstant.Normal.COLUMN_DATE},
                        new String[]{channel, time})
                .order(DBConstant.Normal.COLUMN_DATE, "desc")
                .handler(new HandlerToBeanList(NormalNewsPreviewBean.class))
                .limit(count)
                .find();
    }


}
