package dao;

import db.MyBatisUtils;
import model.Comment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

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

    /**
     * 根据 weibo_id 获取所有的评论信息
     *
     * @param weibo_id
     * @return
     */
    public List<Comment> get_by_weibo(int weibo_id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.get_by_weibo";

        List<Comment> listComment = sqlSession.selectList(statement, weibo_id);
        sqlSession.close();

        return listComment;
    }

    /**
     * 根据关键字查询评论
     *
     * @param keyword
     * @return
     */
    public List<Comment> search_comment(String keyword) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.search_comment";
        List<Comment> list = sqlSession.selectList(statement, keyword);

        return list;
    }

    /**
     * 根据 ID 删除评论
     *
     * @param id
     * @return
     */
    public int del_comment_by_id(int id) {
        SqlSessionFactory factory = MyBatisUtils.getFactory();
        SqlSession sqlSession = factory.openSession();
        String statement = "db.mapper.del_comment_by_id";
        int delete = sqlSession.delete(statement, id);
        int result = -1;
        if (delete > 0) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }
}
