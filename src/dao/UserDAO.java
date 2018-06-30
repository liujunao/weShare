package dao;

import db.MyBatisUtils;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/27
 */
public class UserDAO {

    /**
     * 根据用户名或邮箱进行用户查询，实现登陆
     *
     * @return
     */
    public User login(User user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.login";
        User resUser = sqlSession.selectOne(statement, user);

        sqlSession.close();
        return resUser;
    }

    /**
     * 实现用户注册
     *
     * @param user
     * @return
     */
    public int register(User user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.register";
        int insert = sqlSession.insert(statement, user);
        sqlSession.commit();
        sqlSession.close();
        return insert;
    }

    /**
     * 检查用户名或邮箱是否重复
     *
     * @param name
     * @return
     */
    public int checkName(String name) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.checkName";
        User user = sqlSession.selectOne(statement, name);
        sqlSession.close();
        int result = 0;
        if (user != null) {
            result = 1;
        }
        return result;
    }

    public int checkEmail(String email) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.checkEmail";
        User user = sqlSession.selectOne(statement, email);
        sqlSession.close();
        int result = 0;
        if (user != null) {
            result = 1;
        }
        return result;
    }

    /**
     * 登陆时间更新
     *
     * @param user
     * @return
     */
    public int update_login_time(User user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.update_login_time";
        int result = sqlSession.update(statement, user);
        sqlSession.commit();
        sqlSession.close();

        return result;
    }

    /**
     * 根据 ID 获取用户名，用于博文的 user_name
     *
     * @param id
     * @return
     */
    public String get_name_by_id(int id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.get_name_by_id";
        String name = sqlSession.selectOne(statement, id);
        sqlSession.close();

        return name;
    }

    /**
     * 根据keyword进行模糊查询用户
     *
     * @param keyword
     * @return
     */
    public List<User> search_user(String keyword) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.search_user";
        List<User> list = sqlSession.selectList(statement, keyword);
        sqlSession.close();

        return list;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public int modify_user(User user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.modify_user";
        int update = sqlSession.update(statement, user);
        sqlSession.commit();
        sqlSession.close();
        int result = -1;
        if (update > 0) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * 根据 user_id 获取 user
     *
     * @param user_id
     * @return
     */
    public User get_user_by_id(int user_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.get_user_by_id";
        User user = sqlSession.selectOne(statement, user_id);
        sqlSession.close();

        return user;
    }

    /**
     * 密码修改
     *
     * @param user
     * @return
     */
    public int update_password(User user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.update_password";

        int update = sqlSession.update(statement, user);
        sqlSession.commit();
        sqlSession.close();
        int result = -1;
        if (update > 0) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * 获取用户总数量
     *
     * @return
     */
    public int get_user_count() {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.get_user_count";
        int result = sqlSession.selectOne(statement);
        sqlSession.close();

        return result;
    }

    /**
     * 按 ID 倒叙每次获取一定数量用户
     *
     * @param limit
     * @return
     */
    public List<User> get_user_limit(int limit) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.get_user_limit";
        List<User> list = sqlSession.selectList(statement, limit);
        sqlSession.close();

        return list;
    }

    /**
     * 设置禁言状态
     *
     * @param user
     * @return
     */
    public int set_banned(User user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.set_banned";
        int update = sqlSession.update(statement, user);
        sqlSession.commit();
        sqlSession.close();

        int result = -1;
        if (update > 0) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }
}
