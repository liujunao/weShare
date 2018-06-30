package model;

/**
 * Created by lenovo on 2018/6/29.
 */
public class Thumb_weibo_user {

    private int id;
    private int weibo_id;
    private int user_id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Thumb_weibo_user(int id, int weibo_id, int user_id) {
        this.id = id;
        this.weibo_id = weibo_id;
        this.user_id = user_id;
    }

    public Thumb_weibo_user() {
    }

    @Override
    public String toString() {
        return "Thumb_weibo_userDAO{" +
                "id=" + id +
                ", weibo_id=" + weibo_id +
                ", user_id=" + user_id +
                '}';
    }
}
