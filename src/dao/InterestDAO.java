package dao;

import db.MyBatisUtils;
import model.Interest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/30
 */
public class InterestDAO {

    /**
     * 根据 interest_id 获取指定趣点
     *
     * @param interest_id
     * @return
     */
    public Interest get_interest_by_id(int interest_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_interest_by_id";
        Interest interest = sqlSession.selectOne(statement, interest_id);
        sqlSession.close();

        return interest;
    }

    /**
     * 获取趣点总数量
     *
     * @return
     */
    public int get_interest_count() {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_interest_count";
        int result = sqlSession.selectOne(statement);
        sqlSession.close();

        return result;
    }

    /**
     * 按 ID 倒叙每次获取一定数量趣点
     *
     * @param limit
     * @return
     */
    public List<Interest> get_interest_limit(int limit) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_interest_limit";
        List<Interest> list = sqlSession.selectList(statement, limit);
        sqlSession.close();

        return list;
    }

    /**
     * 根据关键字查询趣点
     *
     * @param keyword
     * @return
     */
    public List<Interest> get_interest_by_keyword(String keyword) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_interest_by_keyword";
        List<Interest> list = sqlSession.selectList(statement, keyword);
        sqlSession.close();

        return list;
    }

    /**
     * 创建趣点
     *
     * @param name
     * @return
     */
    public int insert_interest(String name) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.insert_interest";
        int insert = sqlSession.insert(statement, name);
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
     * 删除趣点
     *
     * @param interest_id
     * @return
     */
    public int delete_interest(int interest_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.delete_interest";
        int delete = sqlSession.delete(statement, interest_id);
        sqlSession.commit();
        sqlSession.close();

        int result = -1;
        if (delete > 0) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * 获取所有趣点列表
     *
     * @return
     */
    public List<Interest> get_all_interests() {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_all_interests";
        List<Interest> list = sqlSession.selectList(statement);

        return list;
    }
}
