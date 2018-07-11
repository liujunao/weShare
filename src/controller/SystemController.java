package controller;

import com.google.gson.JsonObject;
import common.DataToJson;
import common.JsonCommon;
import model.Comment;
import model.Interest;
import model.User;
import model.Weibo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author lenovo
 * @date 2018/6/30
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    JsonCommon jsonCommon = new JsonCommon();
    //趣点管理

    /**
     * 趣点查询展示
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/interest/search", method = RequestMethod.GET)
    public void interest_search(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("system_interest_search");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

        String keyword = request.getParameter("keyword");
        String token = request.getParameter("token");
        int page = Integer.parseInt(request.getParameter("page"));

        InterestService interestService = new InterestService();
        List<Interest> interests = interestService.get_interest_by_keyword(keyword);
        List<Map> interestList = new ArrayList<>();
        for (int i = 0; i < interests.size(); i++) {
            Map<String, Object> tmpMap = new HashMap<>();
            Interest tmpInterest = interests.get(i);
            tmpMap.put("id", tmpInterest.getId());
            tmpMap.put("name", tmpInterest.getName());
            tmpMap.put("weibo_count", new Weibo_interestService().get_weibo_by_interest(tmpInterest.getId()).size());
            tmpMap.put("sub_count", new User_interestService().get_sub_by_interest(tmpInterest.getId()).size());
            interestList.add(tmpMap);
        }
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("interests", interestList);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 创建趣点
     *
     * @param body
     * @param response
     */
    @RequestMapping(value = "/interest/add", method = RequestMethod.POST)
    public void interest_add(@RequestBody String body, HttpServletResponse response) {
        System.out.println("system_interest_add");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

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
        map.put("msg", msgMap);
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
        System.out.println("system_interest_delete");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

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
     * @param request
     * @param response
     */
    @RequestMapping(value = "/weibo/search", method = RequestMethod.GET)
    public void weibo_search(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("system_weibo_search");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

        String keyword = request.getParameter("keyword");
        WeiboService weiboService = new WeiboService();

        List<Weibo> weibos = weiboService.search_weibo(keyword);
        List<Map> weiboList = new ArrayList<>();
        for (int i = 0; i < weibos.size(); i++) {
            Weibo weiboTmp = weibos.get(i);
            Map<String, Object> mapTmp = new HashMap<>();
            mapTmp.put("id", weiboTmp.getId());
            mapTmp.put("userName", weiboTmp.getUser_name());
            mapTmp.put("read_count", weiboTmp.getRead_count());
            mapTmp.put("comment_count", new CommentService().get_by_weibo(weiboTmp.getId()));
            mapTmp.put("transmit_count", new Random().nextInt(5));
            mapTmp.put("context", weiboTmp.getContext());
            weiboList.add(mapTmp);
        }
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("weibos", weiboList);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 微博删除
     *
     * @param body
     * @param response
     */
    @RequestMapping(value = "/weibo/delete", method = RequestMethod.POST)
    public void weibo_delete(@RequestBody String body, HttpServletResponse response) {
        System.out.println("system_weibo_delete");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

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
     * 用户查询展示
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    public void user_search(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("system_user_search");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();

        String keyword = request.getParameter("keyword");
        UserService userService = new UserService();
        List<User> users = userService.search_user(keyword);
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("users", users);
        msgMap.put("user_count", users.size());
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 设置禁言状态
     *
     * @param body
     * @param response
     */
    @RequestMapping(value = "/user/banned", method = RequestMethod.POST)
    public void user_banned(@RequestBody String body, HttpServletResponse response) {
        System.out.println("system_user_banned");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

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
        msgMap.put("success", result);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }


    //评论管理

    /**
     * 评论查询展示
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/comment/search", method = RequestMethod.GET)
    public void searchComment(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("system_searchComment");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        String keyword = request.getParameter("keyword");
        keyword = "%" + keyword + "%";

        List<Comment> commentList = new CommentService().search_comment(keyword);
        msgMap.put("success", 1);
        msgMap.put("comments", commentList);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);

    }

    @RequestMapping(value = "/comment/delete", method = RequestMethod.POST)
    public void deleteComment(@RequestBody String body, HttpServletResponse response) {
        System.out.println("system_deleteComment");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int id = Integer.parseInt(jsonObject.get("id").toString());
        int del = new CommentService().del_comment_by_id(id);
        msgMap.put("success", del);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }


}
