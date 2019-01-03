package data.entitys;

/**
 * @author hua
 * @version 2017/11/22 15:13
 */
public class CommentEntity {

    private String comment_id;
    private String content;
    private String create_time;
    private String user_id;
    private String news_id;
    private String vote_count;
    private String reply_count;
    private String reply_comment;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }

    public String getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(String reply_comment) {
        this.reply_comment = reply_comment;
    }
}
