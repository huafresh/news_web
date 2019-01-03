package news;

/**
 * Created by hua on 2017/8/18.
 * 当客户端请求一般类型的新闻列表信息时，返回此bean
 */
public class NormalNewsPreviewBean {

    private String news_id;
    private String channel;
    private String title;
    private int show_type;
    private String img_url;
    private String imgextra_url1;
    private String imgextra_url2;
    //如果此字段为true，则在展示预览图片时，要在右下角显示图片张数
    private int  is_photo_skip;
    private String source;
    private int reply_count;
    private String digest;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImgextra_url1() {
        return imgextra_url1;
    }

    public void setImgextra_url1(String imgextra_url1) {
        this.imgextra_url1 = imgextra_url1;
    }

    public String getImgextra_url2() {
        return imgextra_url2;
    }

    public void setImgextra_url2(String imgextra_url2) {
        this.imgextra_url2 = imgextra_url2;
    }

    public int getIs_photo_skip() {
        return is_photo_skip;
    }

    public void setIs_photo_skip(int is_photo_skip) {
        this.is_photo_skip = is_photo_skip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getShow_type() {
        return show_type;
    }

    public void setShow_type(int show_type) {
        this.show_type = show_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

}
