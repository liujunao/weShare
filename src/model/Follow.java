package model;

/**
 * Created by lenovo on 2018/6/29.
 */
public class Follow {

    private int id;
    private int followed_id;
    private int following_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowed_id() {
        return followed_id;
    }

    public void setFollowed_id(int followed_id) {
        this.followed_id = followed_id;
    }

    public int getFollowing_id() {
        return following_id;
    }

    public void setFollowing_id(int following_id) {
        this.following_id = following_id;
    }

    public Follow(int id, int followed_id, int following_id) {
        this.id = id;
        this.followed_id = followed_id;
        this.following_id = following_id;
    }

    public Follow() {
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", followed_id=" + followed_id +
                ", following_id=" + following_id +
                '}';
    }
}
