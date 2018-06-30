package model;

/**
 *
 * @author lenovo
 * @date 2018/6/29
 */
public class Weibo_interest {

    private int id;
    private int weibo_id;
    private int interest_id;

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

    public int getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(int interest_id) {
        this.interest_id = interest_id;
    }

    public Weibo_interest(int id, int weibo_id, int interest_id) {
        this.id = id;
        this.weibo_id = weibo_id;
        this.interest_id = interest_id;
    }

    public Weibo_interest(int weibo_id, int interest_id) {
        this.weibo_id = weibo_id;
        this.interest_id = interest_id;
    }

    public Weibo_interest() {
    }

    @Override
    public String toString() {
        return "Weibo_interest{" +
                "id=" + id +
                ", weibo_id=" + weibo_id +
                ", interest_id=" + interest_id +
                '}';
    }
}
