package service;

import dao.InterestDAO;
import model.Interest;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/6/30
 */
public class InterestService {

    InterestDAO interestDAO = new InterestDAO();

    /**
     * 根据 interest_id 获取指定趣点
     *
     * @param interest_id
     * @return
     */
    public Interest get_interest_by_id(int interest_id) {
        Interest interest = interestDAO.get_interest_by_id(interest_id);

        return interest;
    }

    /**
     * 获取趣点总数量
     *
     * @return
     */
    public int get_interest_count() {
        int result = interestDAO.get_interest_count();

        return result;
    }

    /**
     * 按 ID 倒叙每次获取一定数量趣点
     *
     * @param limit
     * @return
     */
    public List<Interest> get_interest_limit(int limit) {
        List<Interest> list = interestDAO.get_interest_limit(limit);

        return list;
    }

    /**
     * 根据关键字查询趣点
     *
     * @param keyword
     * @return
     */
    public List<Interest> get_interest_by_keyword(String keyword) {
        List<Interest> interests = interestDAO.get_interest_by_keyword(keyword);

        return interests;
    }

    /**
     * 创建趣点
     *
     * @param name
     * @return
     */
    public int insert_interest(String name) {
        int result = interestDAO.insert_interest(name);

        return result;
    }

    /**
     * 删除趣点
     *
     * @param interest_id
     * @return
     */
    public int delete_interest(int interest_id) {
        int result = interestDAO.delete_interest(interest_id);

        return result;
    }
}
