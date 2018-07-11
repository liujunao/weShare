package service;

import dao.FollowDAO;
import model.Follow;
import model.User;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class FollowService {

    FollowDAO followDAO = new FollowDAO();

    /**
     * 添加关注者与被关注者关系
     *
     * @param follow
     * @return
     */
    public int add_follow(Follow follow) {
        int result = followDAO.add_follow(follow);


        return result;
    }

    /**
     * 取消关注
     *
     * @param follow
     * @return
     */
    public int del_follow(Follow follow) {
        int result = followDAO.del_follow(follow);

        return result;
    }

    /**
     * 查询是否已关注
     *
     * @param follow
     * @return
     */
    public int is_follow(Follow follow) {
        int result = followDAO.is_follow(follow);

        return result;
    }

    /**
     * 查询关注用户列表
     *
     * @param following_id
     * @return
     */
    public List<User> get_follows(int following_id) {
        List<User> users = followDAO.get_follows(following_id);

        return users;
    }

    /**
     * 获取已关注者数量
     *
     * @param following_id
     * @return
     */
    public int get_follow_count(int following_id) {
        int result = followDAO.get_follow_count(following_id);

        return result;
    }

    /**
     * 获取粉丝数
     *
     * @param followed_id
     * @return
     */
    public int get_fans_count(int followed_id) {
        int result = followDAO.get_fans_count(followed_id);

        return result;
    }
}
