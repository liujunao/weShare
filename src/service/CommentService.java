package service;

import dao.CommentDAO;
import model.Comment;

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
}
