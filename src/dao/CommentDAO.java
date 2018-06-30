package dao;

import db.MyBatisUtils;
import model.Comment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by lenovo on 2018/6/29.
 */
public class CommentDAO {

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    public int add_comment(Comment comment) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.add_comment";
        int insert = sqlSession.insert(statement, comment);
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
     * 删除评论
     *
     * @param comment
     * @return
     */
    public int del_comment(Comment comment) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.del_comment";
        int insert = sqlSession.delete(statement, comment);
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
}
