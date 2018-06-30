package service;

import dao.FollowDAO;
import model.Follow;

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
}
