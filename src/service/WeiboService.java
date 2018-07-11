package service;

import dao.WeiboDAO;
import model.Weibo;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class WeiboService {

    WeiboDAO weiboDAO = new WeiboDAO();

    /**
     * 添加博文
     *
     * @param weibo
     * @return
     */
    public int addWeibo(Weibo weibo) {
        int result = weiboDAO.addWeibo(weibo);

        return result;
    }

    /**
     * 阅读量 +1
     *
     * @param weibo_id
     * @return
     */
    public int add_read_count(int weibo_id) {
        int result = weiboDAO.add_read_count(weibo_id);

        return result;
    }

    /**
     * 点赞
     *
     * @param weibo_id
     * @return
     */
    public int add_thumb_count(int weibo_id) {
        int result = weiboDAO.add_thumb_count(weibo_id);

        return result;
    }

    /**
     * 取消点赞
     *
     * @param weibo_id
     * @return
     */
    public int cancel_thumb_count(int weibo_id) {
        int result = weiboDAO.cancel_thumb_count(weibo_id);

        return result;
    }

    /**
     * 获取最后一条微博ID，用于添加博文与趣点的联系
     *
     * @return
     */
    public int get_last_weibo() {
        int result = weiboDAO.get_last_weibo();

        return result;
    }

    /**
     * 根据用户名或博文内容获取微博
     *
     * @param keyword
     * @return
     */
    public List<Weibo> search_weibo(String keyword) {
        List<Weibo> list = weiboDAO.search_weibo(keyword);

        return list;
    }

    /**
     * 根据从weibo_interest 获取的 weibo_id 来查询
     *
     * @param weibo_id
     * @return
     */
    public Weibo search_by_weibo_id(int weibo_id) {
        Weibo list = weiboDAO.search_by_weibo_id(weibo_id);

        return list;
    }

    /**
     * 根据 weibo_id 删除指定博文
     *
     * @param weibo_id
     * @return
     */
    public int delete_by_id(int weibo_id) {
        int result = weiboDAO.delete_by_id(weibo_id);

        return result;
    }

    /**
     * 查看热门微博 按照时间排序返回最新的微博
     *
     * @return
     */
    public List<Weibo> look_hot() {
        List<Weibo> list = weiboDAO.look_hot();

        return list;
    }

    /**
     * 查询指定用户的博文
     *
     * @param user_id
     * @return
     */
    public List<Weibo> search_by_userid(int user_id) {
        List<Weibo> weiboList = weiboDAO.search_by_userid(user_id);

        return weiboList;
    }

    /**
     * 查询指定用户的博文数
     *
     * @param user_id
     * @return
     */
    public int get_weibo_count(int user_id) {
        int result = weiboDAO.get_weibo_count(user_id);

        return result;
    }
}
