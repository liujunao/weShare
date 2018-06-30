package dao;

import db.MyBatisUtils;
import model.Thumb_weibo_user;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class Thumb_weibo_userDAO {

    /**
     * 点赞微博时，建立博文与点赞者的联系，方便取消点赞
     *
     * @param thumb_weibo_user
     * @return
     */
    public int thumb_weibo_user(Thumb_weibo_user thumb_weibo_user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.thumb_weibo_user";
        int insert = sqlSession.insert(statement, thumb_weibo_user);
        sqlSession.commit();
        sqlSession.close();

        return insert;
    }

    /**
     * 获取点赞者与博文的关系，方便取消点赞
     *
     * @param thumb_weibo_user
     * @return
     */
    public int get_weibo_user(Thumb_weibo_user thumb_weibo_user) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_weibo_user";
        Thumb_weibo_user result = sqlSession.selectOne(statement, thumb_weibo_user);
        sqlSession.close();
        int weibo_id = -1;
        if (thumb_weibo_user != null) {
            weibo_id = thumb_weibo_user.getWeibo_id();
        }

        return weibo_id;
    }
}
