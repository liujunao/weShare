package model;

/**
 * Created by lenovo on 2018/6/28.
 */
public class Weibo {

    private int id;
    private String context;
    private int read_count;
    private int thumb_count;
    private int user_id;
    private String create_time;
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public int getThumb_count() {
        return thumb_count;
    }

    public void setThumb_count(int thumb_count) {
        this.thumb_count = thumb_count;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Weibo(int id, String context, int read_count, int thumb_count, int user_id, String create_time, String user_name) {
        this.id = id;
        this.context = context;
        this.read_count = read_count;
        this.thumb_count = thumb_count;
        this.user_id = user_id;
        this.create_time = create_time;
        this.user_name = user_name;
    }

    public Weibo() {
    }

    @Override
    public String toString() {
        return "Weibo{" +
                "id=" + id +
                ", context='" + context + '\'' +
                ", read_count=" + read_count +
                ", thumb_count=" + thumb_count +
                ", user_id=" + user_id +
                ", create_time='" + create_time + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
