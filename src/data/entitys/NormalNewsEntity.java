package data.entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/15.
 * 一般类型的新闻bean
 */
public class NormalNewsEntity {

    private String news_id;
    private String title;
    private String channel;
    private String show_type;
    private int is_photo_skip;
    private String source;
    private String date;
    private String reply_count;
    private String digest;

    //对应image表
    private List<Image> thumbs = new ArrayList<>();
    private List<Image> bodys = new ArrayList<>();

    //对应body表
    private Body body;




    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getIs_photo_skip() {
        return is_photo_skip;
    }

    public void setIs_photo_skip(int is_photo_skip) {
        this.is_photo_skip = is_photo_skip;
    }

    public List<Image> getThumbs() {
        return thumbs;
    }


    public List<Image> getBodys() {
        return bodys;
    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }


    public static class Body {
        private String news_id;
        private String title;
        private String source;
        private String time;
        private String content;
        private String share_link;
        private String skip_type;
        private String relative_sys;


        public String getSkip_type() {
            return skip_type;
        }

        public void setSkip_type(String skip_type) {
            this.skip_type = skip_type;
        }

        public String getRelative_sys() {
            return relative_sys;
        }

        public void setRelative_sys(String relative_sys) {
            this.relative_sys = relative_sys;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getShare_link() {
            return share_link;
        }

        public void setShare_link(String share_link) {
            this.share_link = share_link;
        }

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}


