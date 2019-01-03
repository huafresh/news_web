package data.tasks;

import com.google.gson.Gson;
import data.detail.NesDetailTool;
import data.entitys.Image;
import data.utils.DBUtil;
import net.sf.json.JSONObject;
import network.IResponseListener;
import network.NetworkRequest;
import data.entitys.NormalNewsEntity;
import utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hua on 2017/8/17.
 * 频道新闻解析
 */
public class ChannelTask implements Runnable {

//    private String baseUrl = "http://c.m.163.com/nc/article/list/";
//    private String suffix = "/40-20.html";

    private int i = 0;

    private String baseUrl = "https://c.m.163.com/dlist/article/dynamic";
    private String suffix = "&LastStdTime=0&passport=6ras%2B7eG1rU49VTGVUoaOSjDTS7evK17e33OxkeE9uM%3D&devId=JRx6OIZqM%2F23C3BSim%2FTFA%3D%3D&lat=&lon=&version=25.1&net=wifi&ts=1502959208&sign=8euSbbJsyY4NOEe7Qk3S9Uujhqd0ZLndmm3FLi2HJwl48ErR02zJ6%2FKXOnxX046I&encryption=1&canal=netease_gw&mac=1TjHUFyrqgi7m8wLFqOZ6X9UZbg%2FOBGwvHnogEfb82I%3D&open=&openpath=";

    private final String channel;

    public ChannelTask(String channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        HashMap<String, String> params = new HashMap<>();
        params.put("offset", 10 * i + "");
        params.put("size", 10 + "");
        params.put("fn",i+"");
        params.put("from",channel);
        i++;
        NetworkRequest request = NetworkRequest.with();
        request.url(baseUrl);
        request.params(params);
        request.suffix(suffix);
        request.responseListener(new IResponseListener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                //网易返回的json，是一个bean里放了一个List，List里面对应每条新闻，并且bean字段的名称不是固定的。
                //这里统一替换为resultList
                try {
                    JSONObject jsonObject = JSONObject.fromObject(result.toString().replace(channel, "resultList"));

                    Gson gson = new Gson();
                    ChannelBean bean = gson.fromJson(jsonObject.toString(), ChannelBean.class);

                    //存放解析出来的一般类型新闻
                    List<NormalNewsEntity> infoList = new ArrayList<>();

                    List<ChannelBean.Result> list = bean.getResultList();
                    for (ChannelBean.Result newsBean : list) {

                        NormalNewsEntity info = new NormalNewsEntity();

                        //新闻id
                        info.setNews_id(newsBean.getDocid());

                        //获取标题
                        info.setTitle(newsBean.getTitle());

                        //频道
                        info.setChannel(channel);

                        //是否提示图片张数
                        if (!TextUtils.isEmpty(newsBean.getSkipType())) {
                            info.setIs_photo_skip(1);
                        } else {
                            info.setIs_photo_skip(0);
                        }

                        //缩略图片链接
                        List<ChannelBean.Result.ImgnewextraBean> imgnewextra = newsBean.getImgnewextra();
                        if (imgnewextra == null) {
                            String thumbUrl = newsBean.getImgsrc();
                            Image image = new Image();
                            image.setNews_id(info.getNews_id());
                            image.setType(1);
                            image.setUrl(thumbUrl);
                            info.getThumbs().add(image);
                            // TODO: 2017/8/18  这里也有可能是大图+文字新闻，目前没找到规律
                            info.setShow_type("1");
                        } else {
                            String[] urls = new String[3];
                            urls[0] = newsBean.getImgsrc();
                            urls[1] = imgnewextra.get(0).getImgsrc();
                            urls[2] = imgnewextra.get(1).getImgsrc();
                            for (int i = 0; i < 3; i++) {
                                Image image = new Image();
                                image.setNews_id(info.getNews_id());
                                image.setUrl(urls[i]);
                                image.setType(1);
                                info.getThumbs().add(image);
                            }
                            info.setShow_type("2");
                        }

                        //来源
                        info.setSource(newsBean.getSource());

                        //回帖数
                        //info.setReply_count(String.valueOf(newsBean.getReplyCount()));

                        //摘要
                        info.setDigest(newsBean.getDigest());

                        //根据获取到的新闻id，调用新闻详情接口，进一步解析新闻的内容
                        NesDetailTool.getInstance().getDetailData(info, info.getNews_id());

                        infoList.add(info);
                    }

                    //将所有的新闻插入数据
                    DBUtil.insertNormalNewsIntoDB(infoList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                System.out.println("网络访问出错");
            }
        });
        request.request();


    }
}
