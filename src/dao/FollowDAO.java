package dao;

import db.MyBatisUtils;
import model.Follow;
import model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/29.
 */
public class FollowDAO {

    /**
     * 添加关注者与被关注者关系
     *
     * @param follow
     * @return
     */
    public int add_follow(Follow follow) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.add_follow";
        int insert = sqlSession.insert(statement, follow);
        int result = -1;
        if (insert > 0) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * 取消关注
     *
     * @param follow
     * @return
     */
    public int del_follow(Follow follow) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.del_follow";
        int delete = sqlSession.delete(statement, follow);
        int result = -1;
        if (delete > 0) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * 查询是否已关注
     *
     * @param follow
     * @return
     */
    public int is_follow(Follow follow) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.is_follow";
        Follow selectOne = sqlSession.selectOne(statement, follow);
        int result = -1;
        if (selectOne != null) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

    /**
     * 查询关注用户列表
     *
     * @param following_id
     * @return
     */
    public List<User> get_follows(int following_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_follows";
        List<Follow> list = sqlSession.selectList(statement, following_id);
        List<User> listUser = new ArrayList<>();
        UserService userService = new UserService();
        for (int i = 0; i < list.size(); i++) {
            User userById = userService.get_user_by_id(list.get(i).getFollowed_id());
            listUser.add(userById);
        }

        return listUser;
    }

    /**
     * 获取已关注者数量
     *
     * @param following_id
     * @return
     */
    public int get_follow_count(int following_id) {
        List<User> follows = get_follows(following_id);
        int sum = follows.size();

        return sum;
    }

    /**
     * 获取粉丝数
     *
     * @param followed_id
     * @return
     */
    public int get_fans_count(int followed_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_fans_count";
        int sum = sqlSession.selectOne(statement, followed_id);
        sqlSession.close();
        return sum;
    }
}
