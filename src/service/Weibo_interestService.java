package service;

import dao.Weibo_interestDAO;
import model.Weibo_interest;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/29
 */
public class Weibo_interestService {

    Weibo_interestDAO weibo_interestDAO = new Weibo_interestDAO();

    /**
     * 添加博文与趣点的联系
     *
     * @param weibo_interest
     * @return
     */
    public int add_weibo_interest(Weibo_interest weibo_interest) {
        int result = weibo_interestDAO.add_weibo_interest(weibo_interest);

        return result;
    }

    /**
     * 根据趣点名获取 weibo_id
     *
     * @param keyword
     * @return
     */
    public List<Integer> search_by_interest(String keyword) {
        List<Integer> list = weibo_interestDAO.search_by_interest(keyword);

        return list;
    }

    /**
     * 根据趣点ID 获取 weibo_id
     *
     * @param interest_id
     * @return
     */
    public List<Integer> get_weibo_by_interest(int interest_id) {
        List<Integer> list = weibo_interestDAO.get_weibo_by_interest(interest_id);

        return list;
    }
}
