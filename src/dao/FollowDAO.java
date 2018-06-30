package dao;

import db.MyBatisUtils;
import model.Follow;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

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
}
