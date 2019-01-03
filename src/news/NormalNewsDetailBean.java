package news;

import java.util.List;

/**
 * Created by hua on 2017/9/18.
 */
public class NormalNewsDetailBean {

    private String news_id;
    private String title;
    private String source;
    private String time;
    private String content;
    private String share_link;
    private int skip_type;
    private String relative_sys;
    private List<String> images;


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }

    public int getSkip_type() {
        return skip_type;
    }

    public void setSkip_type(int skip_type) {
        this.skip_type = skip_type;
    }

    public String getRelative_sys() {
        return relative_sys;
    }

    public void setRelative_sys(String relative_sys) {
        this.relative_sys = relative_sys;
    }
}
