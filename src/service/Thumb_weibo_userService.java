package service;

import dao.Thumb_weibo_userDAO;
import model.Thumb_weibo_user;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class Thumb_weibo_userService {

    Thumb_weibo_userDAO thumb_weibo_userDAO = new Thumb_weibo_userDAO();

    /**
     * 点赞微博时，建立博文与点赞者的联系，方便取消点赞
     *
     * @param thumb_weibo_user
     * @return
     */
    public int thumb_weibo_user(Thumb_weibo_user thumb_weibo_user) {
        int result = thumb_weibo_userDAO.thumb_weibo_user(thumb_weibo_user);

        return result;
    }

    /**
     * 获取点赞者与博文的关系，方便取消点赞
     *
     * @param thumb_weibo_user
     * @return
     */
    public int get_weibo_user(Thumb_weibo_user thumb_weibo_user) {
        int result = thumb_weibo_userDAO.get_weibo_user(thumb_weibo_user);

        return result;
    }
}
