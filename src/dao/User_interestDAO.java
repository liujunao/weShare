package dao;

import db.MyBatisUtils;
import model.User_interest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/30
 */
public class User_interestDAO {

    /**
     * 订阅趣点
     *
     * @param user_interest
     * @return
     */
    public int add_interest(User_interest user_interest) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.add_interest";
        int insert = sqlSession.insert(statement, user_interest);
        sqlSession.commit();
        sqlSession.close();
        int result = -1;
        if (insert > 0) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * 取消订阅
     *
     * @param user_interest
     * @return
     */
    public int cancel_interest(User_interest user_interest) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.cancel_interest";
        int insert = sqlSession.delete(statement, user_interest);
        sqlSession.commit();
        sqlSession.close();
        int result = -1;
        if (insert > 0) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * 用户查看自己的趣点列表
     *
     * @param user_id
     * @return
     */
    public List<User_interest> look_list_by_user_id(int user_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.look_list_by_user_id";
        List<User_interest> list = sqlSession.selectList(statement, user_id);
        sqlSession.close();

        return list;
    }
}
