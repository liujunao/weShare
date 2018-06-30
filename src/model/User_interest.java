package model;

/**
 *
 * @author lenovo
 * @date 2018/6/30
 */
public class User_interest {

    private int id;
    private int user_id;
    private int interest_id;
    private String interest_name;

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(int interest_id) {
        this.interest_id = interest_id;
    }

    public User_interest(int user_id, int interest_id, String interest_name) {
        this.user_id = user_id;
        this.interest_id = interest_id;
        this.interest_name = interest_name;
    }

    public User_interest() {
    }

    @Override
    public String toString() {
        return "User_interest{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", interest_id=" + interest_id +
                ", interest_name='" + interest_name + '\'' +
                '}';
    }
}
