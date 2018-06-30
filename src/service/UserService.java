package service;

import dao.UserDAO;
import model.User;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/27
 */
public class UserService {

    UserDAO userDAO = new UserDAO();

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public int register(User user) {
        int result = userDAO.register(user);

        return result;
    }

    /**
     * 用户登陆
     *
     * @return
     */
    public User login(User user) {
        User resUser = userDAO.login(user);

        return resUser;
    }

    /**
     * 检查用户名是否重复
     *
     * @param name
     * @return
     */
    public int checkName(String name) {
        int result = userDAO.checkName(name);

        return result;
    }

    /**
     * 检查邮箱是否重复
     *
     * @param email
     * @return
     */
    public int checkEmail(String email) {
        int result = userDAO.checkEmail(email);

        return result;
    }

    /**
     * 登陆时间更新
     *
     * @param user
     * @return
     */
    public int update_login_time(User user) {
        int result = userDAO.update_login_time(user);

        return result;
    }

    /**
     * 根据 ID 获取用户名，用于博文的 user_name
     *
     * @param id
     * @return
     */
    public String get_name_by_id(int id) {
        String user_name = userDAO.get_name_by_id(id);

        return user_name;
    }

    /**
     * 根据keyword进行模糊查询用户
     *
     * @param keyword
     * @return
     */
    public List<User> search_user(String keyword) {
        List<User> list = userDAO.search_user(keyword);

        return list;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public int modify_user(User user) {
        int result = userDAO.modify_user(user);

        return result;
    }

    /**
     * 根据 user_id 获取 user
     *
     * @param user_id
     * @return
     */
    public User get_user_by_id(int user_id) {
        User user = userDAO.get_user_by_id(user_id);

        return user;
    }

    /**
     * 密码修改
     *
     * @param user
     * @return
     */
    public int update_password(User user) {
        int result = userDAO.update_password(user);

        return result;
    }

    /**
     * 获取用户总数量
     *
     * @return
     */
    public int get_user_count() {
        int result = userDAO.get_user_count();

        return result;
    }

    /**
     * 按 ID 倒叙每次获取一定数量用户
     *
     * @param limit
     * @return
     */
    public List<User> get_user_limit(int limit) {
        List<User> list = userDAO.get_user_limit(limit);

        return list;
    }

    /**
     * 设置禁言状态
     *
     * @param user
     * @return
     */
    public int set_banned(User user) {
        int result = userDAO.set_banned(user);

        return result;
    }
}
