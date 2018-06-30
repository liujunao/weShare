package model;

/**
 *
 * @author lenovo
 * @date 2018/6/28
 */
public class Comment {

    private int id;
    private int weibo_id;
    private String context;
    private String comment_time;
    private int parent_id;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(int weibo_id) {
        this.weibo_id = weibo_id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public Comment(int id, int weibo_id, String context, String comment_time, int parent_id, int user_id) {
        this.id = id;
        this.weibo_id = weibo_id;
        this.context = context;
        this.comment_time = comment_time;
        this.parent_id = parent_id;
        this.user_id = user_id;
    }

    public Comment(int id, int user_id) {
        this.id = id;
        this.user_id = user_id;
    }

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", weibo_id=" + weibo_id +
                ", context='" + context + '\'' +
                ", comment_time='" + comment_time + '\'' +
                ", parent_id=" + parent_id +
                '}';
    }
}
