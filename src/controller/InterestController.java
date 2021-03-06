package controller;

import com.google.gson.JsonObject;
import common.DataToJson;
import common.JsonCommon;
import model.Interest;
import model.User_interest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.InterestService;
import service.User_interestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/6/30.
 */
@Controller
@RequestMapping("/interest")
public class InterestController {

    JsonCommon jsonCommon = new JsonCommon();

    /**
     * （取消）订阅趣点
     *
     * @param body
     * @param response
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add_interest(@RequestBody String body, HttpServletResponse response) {
        System.out.println("interest_add_interest");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int user_id = Integer.parseInt(jsonObject.get("user_id").toString());
        int interest_id = Integer.parseInt(jsonObject.get("interest_id").toString());
        int is_sub = Integer.parseInt(jsonObject.get("is_sub").toString());
        InterestService interestService = new InterestService();
        Interest interest_by_id = interestService.get_interest_by_id(interest_id);
        User_interest user_interest = new User_interest(user_id, interest_id, interest_by_id.getName());
        User_interestService user_interestService = new User_interestService();
        int result = -1;
        if (is_sub > 0) {//订阅
            result = user_interestService.add_interest(user_interest);
        } else {//取消订阅
            result = user_interestService.cancel_interest(user_interest);
        }
        map.put("statusCode", 200);
        msgMap.put("success", result);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 查看趣点列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/look_list", method = RequestMethod.GET)
    public void look_list_interest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("interest_look_list_interest");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        User_interestService user_interestService = new User_interestService();
        List<User_interest> user_interests = user_interestService.look_list_by_user_id(user_id);
        if (user_interests != null) {
            msgMap.put("success", 1);
            msgMap.put("interests", user_interests);
        } else {
            msgMap.put("success", 0);
            msgMap.put("interests", "[]");
        }
        map.put("statusCode", 200);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 热门趣点列表
     *
     * @param response
     */
    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public void hot(HttpServletResponse response) {
        System.out.println("interest_hot");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

        List<Interest> interests = new InterestService().get_interest_limit(10);
        msgMap.put("success", 1);
        msgMap.put("interests", interests);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);

    }

}
