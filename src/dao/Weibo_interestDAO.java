package dao;

import db.MyBatisUtils;
import model.Weibo_interest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class Weibo_interestDAO {

    /**
     * 添加博文与趣点的联系
     *
     * @param weibo_interest
     * @return
     */
    public int add_weibo_interest(Weibo_interest weibo_interest) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.add_weibo_interest";
        int insert = sqlSession.insert(statement, weibo_interest);
        sqlSession.commit();
        sqlSession.close();

        return insert;
    }

    /**
     * 根据趣点名获取 weibo_id
     *
     * @param keyword
     * @return
     */
    public List<Integer> search_by_interest(String keyword) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.search_by_interest";
        List<Integer> list = sqlSession.selectList(statement, keyword);
        sqlSession.commit();
        sqlSession.close();

        return list;
    }

    /**
     * 根据趣点ID 获取 weibo_id
     *
     * @param interest_id
     * @return
     */
    public List<Integer> get_weibo_by_interest(int interest_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_weibo_by_interest";
        List<Integer> list = sqlSession.selectList(statement, interest_id);
        sqlSession.commit();
        sqlSession.close();

        return list;
    }
}
