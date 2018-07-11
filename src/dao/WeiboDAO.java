package dao;

import db.MyBatisUtils;
import model.Weibo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class WeiboDAO {

    /**
     * 添加博文
     *
     * @param weibo
     * @return
     */
    public int addWeibo(Weibo weibo) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.addWeibo";
        int insert = sqlSession.insert(statement, weibo);
        sqlSession.commit();
        sqlSession.close();

        return insert;
    }

    /**
     * 阅读量 +1
     *
     * @param weibo_id
     * @return
     */
    public int add_read_count(int weibo_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.add_read_count";
        int update = sqlSession.update(statement, weibo_id);
        sqlSession.commit();
        sqlSession.close();

        return update;
    }

    /**
     * 点赞
     *
     * @param weibo_id
     * @return
     */
    public int add_thumb_count(int weibo_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.add_thumb_count";
        int update = sqlSession.update(statement, weibo_id);
        sqlSession.commit();
        sqlSession.close();

        return update;
    }

    /**
     * 取消点赞
     *
     * @param weibo_id
     * @return
     */
    public int cancel_thumb_count(int weibo_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.cancel_thumb_count";
        int update = sqlSession.update(statement, weibo_id);
        sqlSession.commit();
        sqlSession.close();

        return update;
    }

    /**
     * 获取最后一条微博ID，用于添加博文与趣点的联系
     *
     * @return
     */
    public int get_last_weibo() {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.get_last_weibo";
        int selectOne = sqlSession.selectOne(statement);
        sqlSession.commit();
        sqlSession.close();

        return selectOne;
    }

    /**
     * 根据用户名或博文内容获取微博
     *
     * @param keyword
     * @return
     */
    public List<Weibo> search_weibo(String keyword) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.search_weibo";
        List<Weibo> list = sqlSession.selectList(statement, keyword);
        sqlSession.commit();
        sqlSession.close();

        return list;
    }

    /**
     * 根据从weibo_interest 获取的 weibo_id 来查询
     *
     * @param weibo_id
     * @return
     */
    public Weibo search_by_weibo_id(int weibo_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.search_by_weibo_id";
        Weibo list = sqlSession.selectOne(statement, weibo_id);
        sqlSession.commit();
        sqlSession.close();

        return list;
    }

    /**
     * 根据 weibo_id 删除指定博文
     *
     * @param weibo_id
     * @return
     */
    public int delete_by_id(int weibo_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.delete_by_id";
        int delete = sqlSession.delete(statement, weibo_id);
        sqlSession.commit();
        sqlSession.close();

        return delete;
    }

    /**
     * 查看热门微博 按照时间排序返回最新的微博
     *
     * @return
     */
    public List<Weibo> look_hot() {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.look_hot";
        List<Weibo> list = sqlSession.selectList(statement);
        sqlSession.commit();
        sqlSession.close();

        return list;
    }

    /**
     * 查询指定用户的博文
     *
     * @param user_id
     * @return
     */
    public List<Weibo> search_by_userid(int user_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();

        String statement = "db.mapper.search_by_userid";
        List<Weibo> list = sqlSession.selectList(statement, user_id);
        sqlSession.close();

        return list;
    }

    /**
     * 查询指定用户的博文数
     *
     * @param user_id
     * @return
     */
    public int get_weibo_count(int user_id) {
        List<Weibo> weiboList = search_by_userid(user_id);
        int result = weiboList.size();

        return result;
    }
}
