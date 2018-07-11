package service;

import dao.CommentDAO;
import model.Comment;

import java.util.List;

/**
 * Created by lenovo on 2018/6/29.
 */
public class CommentService {

    CommentDAO commentDAO = new CommentDAO();

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    public int add_comment(Comment comment) {
        int result = commentDAO.add_comment(comment);

        return result;
    }

    /**
     * 删除评论
     *
     * @param comment
     * @return
     */
    public int del_comment(Comment comment) {
        int result = commentDAO.del_comment(comment);

        return result;
    }

    /**
     * 根据 weibo_id 获取所有的评论信息
     *
     * @param weibo_id
     * @return
     */
    public List<Comment> get_by_weibo(int weibo_id) {
        List<Comment> commentList = commentDAO.get_by_weibo(weibo_id);

        return commentList;
    }

    /**
     * 根据关键字查询评论
     *
     * @param keyword
     * @return
     */
    public List<Comment> search_comment(String keyword) {
        List<Comment> commentList = commentDAO.search_comment(keyword);

        return commentList;
    }

    /**
     * 根据 ID 删除评论
     *
     * @param id
     * @return
     */
    public int del_comment_by_id(int id) {
        int result = commentDAO.del_comment_by_id(id);

        return result;
    }
}
