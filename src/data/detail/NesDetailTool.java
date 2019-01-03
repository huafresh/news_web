package data.detail;

import com.google.gson.Gson;
import data.entitys.Image;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import network.IResponseListener;
import network.NetworkRequest;
import data.entitys.NormalNewsEntity;

import java.util.List;

/**
 * Created by hua on 2017/8/17.
 * 新闻详情解析
 */
public class NesDetailTool {

    public static final String baseUrl = "http://c.m.163.com/nc/article/";
    public static final String suffix = "/full.html";

    public static NesDetailTool getInstance() {
        return HOLDER.sInstance;
    }

    private static final class HOLDER {
        private static final NesDetailTool sInstance = new NesDetailTool();
    }

    public void getDetailData(NormalNewsEntity info, String newsId) {
        String url = baseUrl + newsId + suffix;
        NetworkRequest.with()
                .url(url)
                .responseListener(new IResponseListener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            JSONObject json = JSONObject.fromObject(result.toString().replace(newsId, "result"));

                            Gson gson = new Gson();
                            DetailBean bean = gson.fromJson(json.toString(), DetailBean.class);

                            //body
                            NormalNewsEntity.Body body = new NormalNewsEntity.Body();
                            body.setTitle(bean.getResult().getTitle());
                            body.setSource(bean.getResult().getSource());
                            body.setNews_id(newsId);
                            body.setTime(bean.getResult().getPtime());
                            info.setDate(bean.getResult().getPtime());
                            body.setContent(bean.getResult().getBody());
                            body.setShare_link(bean.getResult().getShareLink());
                            body.setRelative_sys(JSONArray.fromObject(bean.getResult().getRelative_sys()).toString());
                            if (info.getIs_photo_skip()==1) {
                                body.setSkip_type("2");
                            } else {
                                body.setSkip_type("1");
                            }

                            info.setBody(body);

                            //图片链接
                            List<DetailBean.Result.ImgEntity> imgs = bean.getResult().getImg();
                            for (DetailBean.Result.ImgEntity img : imgs) {
                                Image bodyImg = new Image();
                                bodyImg.setType(10);
                                bodyImg.setUrl(img.getSrc());
                                bodyImg.setNews_id(newsId);
                                bodyImg.setPixel(img.getPixel());
                                bodyImg.setRef(img.getRef());
                                info.getBodys().add(bodyImg);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                })
                .request();

    }

}
