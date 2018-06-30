package controller;

import com.google.gson.JsonObject;
import common.DataToJson;
import common.JsonCommon;
import model.Interest;
import model.User;
import model.Weibo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.InterestService;
import service.UserService;
import service.WeiboService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 * @date 2018/6/30
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    JsonCommon jsonCommon = new JsonCommon();
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> msgMap = new HashMap<>();

    //趣点管理

    /**
     * 趣点默认展示
     *
     * @param body
     * @param response
     */
    @RequestMapping("/interest/default")
    public void interest_default(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int page = Integer.parseInt(jsonObject.get("page").toString());
        InterestService interestService = new InterestService();

        int count = interestService.get_interest_count();
        List<Interest> interests = interestService.get_interest_limit(10 * page);
        map.put("statusCode", 200);
        if (interests != null) {
            msgMap.put("success", 1);
            msgMap.put("count", count);
            msgMap.put("interests", interests);
        } else {
            msgMap.put("success", 0);
            msgMap.put("count", count);
            msgMap.put("interests", "[]");
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 趣点查询展示
     *
     * @param body
     * @param response
     */
    @RequestMapping("/interest/search")
    public void interest_search(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String keyword = jsonObject.get("keyword").toString();
        InterestService interestService = new InterestService();

        List<Interest> interests = interestService.get_interest_by_keyword(keyword);
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        if (interests != null) {
            msgMap.put("interests", interests);
        } else {
            msgMap.put("interests", "[]");
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 创建趣点
     *
     * @param body
     * @param response
     */
    @RequestMapping("/interest/add")
    public void interest_add(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String name = jsonObject.get("name").toString();
        InterestService interestService = new InterestService();

        int result = interestService.insert_interest(name);
        map.put("statusCode", 200);
        if (result > 0) {
            msgMap.put("success", 1);
        } else {
            msgMap.put("success", 0);
        }
        map.put("msgMap", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 删除趣点
     *
     * @param body
     * @param response
     */
    @RequestMapping("/interest/delete")
    public void interest_delete(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int interest_id = Integer.parseInt(jsonObject.get("id").toString());
        InterestService interestService = new InterestService();

        int result = interestService.delete_interest(interest_id);
        map.put("statusCode", 200);
        msgMap.put("success", result);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }


    //微博管理

    /**
     * 微博查询展示
     *
     * @param body
     * @param response
     */
    @RequestMapping("/weibo/search")
    public void weibo_search(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String keyword = jsonObject.get("keyword").toString();
        WeiboService weiboService = new WeiboService();

        List<Weibo> weibos = weiboService.search_weibo(keyword);
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("weibos", weibos);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 微博删除
     *
     * @param body
     * @param response
     */
    @RequestMapping("/weibo/delete")
    public void weibo_delete(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int weibo_id = Integer.parseInt(jsonObject.get("id").toString());
        WeiboService weiboService = new WeiboService();

        int result = weiboService.delete_by_id(weibo_id);
        map.put("statusCode", 200);
        msgMap.put("success", result);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }


    //用户管理

    /**
     * 用户默认展示
     *
     * @param body
     * @param response
     */
    @RequestMapping("/user/default")
    public void user_default(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int page = Integer.parseInt(jsonObject.get("page").toString());
        UserService userService = new UserService();

        int count = userService.get_user_count();
        List<User> users = userService.get_user_limit(10 * page);
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("users", users);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 用户查询展示
     *
     * @param body
     * @param response
     */
    @RequestMapping("/user/search")
    public void user_search(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String keyword = jsonObject.get("keyword").toString();
        UserService userService = new UserService();

        List<User> users = userService.search_user(keyword);
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("users", users);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 设置禁言状态
     *
     * @param body
     * @param response
     */
    @RequestMapping("/user/banned")
    public void user_banned(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int user_id = Integer.parseInt(jsonObject.get("user_id").toString());
        int is_banned = Integer.parseInt(jsonObject.get("is_banned").toString());
        int is_banning = -1;
        if (is_banned == 1) {
            is_banning = 0;
        } else if (is_banned == 0) {
            is_banning = 1;
        }
        User user = new User(user_id, is_banning);
        UserService userService = new UserService();
        int result = userService.set_banned(user);
        map.put("statusCode", 200);
        msgMap.put("success", result);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

}
