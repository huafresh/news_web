package data.tasks;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import data.entitys.VideoNewsEntity;
import data.utils.DBUtil;
import db.CRUDAndroid;
import db.DBConstant;
import net.sf.json.JSONObject;
import network.IResponseListener;
import network.NetworkRequest;
import user.entitys.LoginUserInfo;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取内涵段子推荐数据。
 * content_type是-101
 *
 * @author hua
 * @version 2017/11/20 16:18
 */
public class NeiHanDuanZi implements Runnable {

    private String lastTime = String.valueOf(System.currentTimeMillis() - 10000);
    private String url_recomm = "http://iu.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-101&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&count=50&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.6.4&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120";
    private String url_video = "http://iu.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&count=50&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.6.4&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120";
    private String url = url_video;

    @Override
    public void run() {
        String suffix = "&am_loc_time="+ String.valueOf(System.currentTimeMillis())+"&min_time="+lastTime;
        lastTime = String.valueOf(System.currentTimeMillis());
        NetworkRequest request = NetworkRequest.with();
        request.url(url);
        request.suffix(suffix);
        request.responseListener(new IResponseListener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    url = url_video;
                    String json = result.toString();
                    String s = json.replace("\"360p_video\":", "\"_360p_video\":")
                            .replace("\"720p_video\":", "\"_720p_video\":")
                            .replace("\"480p_video\":", "\"_480p_video\":");
                    Gson gson = new Gson();
                    NeiHanBean bean = gson.fromJson(s, NeiHanBean.class);

                    List<VideoNewsEntity> videoList = new ArrayList<>();

                    if (TextUtils.isEmpty(bean.getMessage())) {
                        return;
                    }

                    List<NeiHanBean.DataEntity.DataEntity2> dataList = bean.getData().getData();
                    if (dataList == null) {
                        return;
                    }

                    for (NeiHanBean.DataEntity.DataEntity2 dataEntity2 : dataList) {
                        NeiHanBean.DataEntity.DataEntity2.GroupEntity groupEntity = dataEntity2.getGroup();

                        //使用内涵段子的用户信息构造一个用户注册到自己的账户体系中
                        NeiHanBean.DataEntity.DataEntity2.GroupEntity.UserEntity user = groupEntity.getUser();
                        LoginUserInfo loginUserInfo = new LoginUserInfo();
                        String mail = user.getUser_id() + "@163.com";
                        loginUserInfo.setUser_id(String.valueOf(user.getUser_id()));
                        loginUserInfo.setMail(mail);
                        loginUserInfo.setNick_name(user.getName());
                        loginUserInfo.setAvatar_path(user.getAvatar_url());

                        //初始化视频信息
                        VideoNewsEntity videoNewsEntity = new VideoNewsEntity();
                        //id
                        videoNewsEntity.setVideo_id(String.valueOf(groupEntity.getId()));
                        //作者信息
                        videoNewsEntity.setAuthor(loginUserInfo);
                        //标题
                        videoNewsEntity.setTitle(groupEntity.getText());
                        //视频类型
                        videoNewsEntity.setCategory_name(groupEntity.getCategory_name());
                        //视频地址
                        try {
                            videoNewsEntity.set_360p_url(groupEntity.get_360p_video().getUrl_list().get(0).getUrl());
                            videoNewsEntity.set_480p_url(groupEntity.get_480p_video().getUrl_list().get(0).getUrl());
                            videoNewsEntity.set_720p_url(groupEntity.get_720p_video().getUrl_list().get(0).getUrl());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //视频时长
                        videoNewsEntity.setDuration((int) groupEntity.getDuration());
                        //视频的宽高
                        videoNewsEntity.setWidth(groupEntity.getVideo_width());
                        videoNewsEntity.setHeight(groupEntity.getVideo_height());
                        //播放、点赞、踩、评论、转发数量
                        videoNewsEntity.setPlay_count(groupEntity.getPlay_count());
                        videoNewsEntity.setLike_count(groupEntity.getDigg_count());
                        videoNewsEntity.setUnlike_count(groupEntity.getBury_count());
                        videoNewsEntity.setComment_count(groupEntity.getComment_count());
                        videoNewsEntity.setShare_count(groupEntity.getShare_count());
                        //更新封面图片到图片表
                        String coverUrl = groupEntity.getMedium_cover().getUrl_list().get(0).getUrl();
                        if (!TextUtils.isEmpty(coverUrl)) {
                            CRUDAndroid mSQLiteBase2 = new CRUDAndroid();
                            HashMap<String, Object> values2 = new HashMap<>();
                            values2.put(DBConstant.Image.COLUMN_NEWS_ID, groupEntity.getId());
                            values2.put(DBConstant.Image.COLUMN_TYPE, 3);
                            values2.put(DBConstant.Image.COLUMN_URL, coverUrl);
                            mSQLiteBase2.insert(DBConstant.Image.TABLE_NAME, null, values2);
                        }
                        videoList.add(videoNewsEntity);
                    }

                    //把所有的视频信息存入数据库
                    DBUtil.insertVideoListIntoDb(videoList);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        request.request();
    }
}
