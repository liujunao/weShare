package service;

import dao.User_interestDAO;
import model.User_interest;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/30
 */
public class User_interestService {

    User_interestDAO user_interestDAO = new User_interestDAO();

    /**
     * 订阅趣点
     *
     * @param user_interest
     * @return
     */
    public int add_interest(User_interest user_interest) {
        int result = user_interestDAO.add_interest(user_interest);

        return result;
    }

    /**
     * 取消订阅
     *
     * @param user_interest
     * @return
     */
    public int cancel_interest(User_interest user_interest) {
        int result = user_interestDAO.cancel_interest(user_interest);

        return result;
    }

    /**
     * 用户查看自己的趣点列表
     *
     * @param user_id
     * @return
     */
    public List<User_interest> look_list_by_user_id(int user_id) {
        List<User_interest> list = user_interestDAO.look_list_by_user_id(user_id);

        return list;
    }

}
