package data.resolvers;

import data.entitys.NormalNewsEntity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/15.
 * 获取腾讯新闻网新闻
 */
@Deprecated
public class TxObtainData implements IObtainData {

    public List<NormalNewsEntity> resolve(Document document) {
        List<NormalNewsEntity> results = new ArrayList<>();

        //取出所有新闻
        Elements news = document.select("div.Q-tpList");
        for (Element newsElement : news) {
            //解析类型一新闻（特点时一张图片加文字）
            Element element1 = newsElement.select("div.Q-tpWrap").first();
            if (element1 != null) {
                NormalNewsEntity info = new NormalNewsEntity();

//                info.setDis_type("1");
//                info.setOrigin("腾讯网");
//                info.setFollow_sum(String.valueOf(new Random().nextInt(3000)));

                //解析标题
                Elements title = element1.select("a.linkto");
                if (!title.isEmpty()) {
                    info.setTitle(title.text());
                }

//                //解析新闻链接
//                Elements link = element1.select("a.linkto");
//                if (!link.isEmpty()) {
//                    info.setLink(link.attr("href"));
//                    info.setNews_id(info.getLink()); //网站爬取得新闻，链接就认为是唯一的
//                }
//
//                //解析单张预览图片链接
//                Elements image = element1.select("img.picto");
//                if (!image.isEmpty()) {
//                    String url = image.attr("src");
//                    if (TextUtils.isEmpty(url)) {
//                        url = image.attr("_src");
//                    }
//                    info.setThumbnail_url(url);
//                }

                results.add(info);
            }

            //解析类型二新闻（特点时多张图片，一般是三张 + 文字）
            Elements element2 = document.select("div.content");
            if (element2 != null) {
                NormalNewsEntity info = new NormalNewsEntity();

//                info.setDis_type("2");
//                info.setOrigin("腾讯网");
//                info.setFollow_sum(String.valueOf(new Random().nextInt(3000)));
//
//                //解析标题
//                Elements title = element2.select("a.linkto");
//                if (!title.isEmpty()) {
//                    info.setTitle(title.text());
//                }
//
//                //解析新闻链接
//                Elements link = element2.select("a.linkto");
//                if (!link.isEmpty()) {
//                    info.setLink(link.attr("href"));
//                    info.setNews_id(info.getLink()); //网站爬取得新闻，链接就认为是唯一的
//                }

                //解析多张预览图片链接
                Elements picLinks = element2.select("img.picto");
                if (!picLinks.isEmpty()) {
                    StringBuilder urls = new StringBuilder();
                    for (Element picLink : picLinks) {
                        //多个url先以分号分隔
                        urls.append(picLink.attr("src"));
                    }
                    try {
                        String[] thumbs = urls.toString().split(";");
//                        info.setThumbnail_url_01(thumbs[0]);
//                        info.setThumbnail_url_02(thumbs[1]);
//                        info.setThumbnail_url_02(thumbs[2]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return results;
    }

    @Override
    public Object obtain(String josn) {
        return null;
    }
}
