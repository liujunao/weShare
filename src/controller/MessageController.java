package controller;

import com.google.gson.JsonObject;
import common.DataToJson;
import common.DesUtils;
import common.JsonCommon;
import model.Follow;
import model.Interest;
import model.User;
import model.User_interest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.FollowService;
import service.UserService;
import service.User_interestService;
import service.WeiboService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 * @date 2018/6/29
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    JsonCommon jsonCommon = new JsonCommon();

    /**
     * 修改个人信息
     *
     * @param body
     * @param response
     */
    @RequestMapping("/modify")
    public void modify(@RequestBody String body, HttpServletResponse response) {
        System.out.println("message_modify");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int id = Integer.parseInt(jsonObject.get("id").toString());
        String name = jsonObject.get("name").toString();
        int gender = Integer.parseInt(jsonObject.get("gender").toString());
        String photo = jsonObject.get("photo").toString();
        String token = null;
        if (jsonObject.get("token") != null) {
            token = jsonObject.get("token").toString();
        }

        User user = new User(id, name, photo, gender);
        UserService userService = new UserService();
        int result = userService.modify_user(user);

        msgMap.put("success", result);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 修改密码
     *
     * @param body
     * @param response
     * @throws Exception
     */
    @RequestMapping("/modify_pass")
    public void modifyPassword(@RequestBody String body, HttpServletResponse response) throws Exception {
        System.out.println("message_modifyPassword");
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int id = Integer.parseInt(jsonObject.get("id").toString());
        String old_password = jsonObject.get("old_password").toString();
        String new_password = jsonObject.get("new_password").toString();
        System.out.println(new_password);
        DesUtils desUtils = new DesUtils();
        User user = new User(id, desUtils.encrypt(old_password), desUtils.encrypt(new_password));
        UserService userService = new UserService();
        int result = userService.update_password(user);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("success", result);
        map.put("statusCode", 200);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 关注用户
     *
     * @param body
     * @param response
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public void postFollow(@RequestBody String body, HttpServletResponse response) {
        System.out.println("message_postFollow");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int followed_id = Integer.parseInt(jsonObject.get("followed_id").toString());
        int following_id = Integer.parseInt(jsonObject.get("following_id").toString());
        int is_follow = Integer.parseInt(jsonObject.get("is_follow").toString());

        Follow follow = new Follow(-1, followed_id, following_id);
        FollowService followService = new FollowService();
        int result = -1;
        if (is_follow == 1) {
            result = followService.add_follow(follow);
        } else if (is_follow == 0) {
            result = followService.del_follow(follow);
        }
        msgMap.put("success", result);
        DataToJson.submitByJson(map, response);

    }

    /**
     * 查询是否关注某用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public void getFollow(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("message_getFollow");
        String token = request.getParameter("token");
        int followed_id = Integer.parseInt(request.getParameter("followedId"));
        int following_id = Integer.parseInt(request.getParameter("followerId"));

        Follow follow = new Follow(-1, followed_id, following_id);
        int result = new FollowService().is_follow(follow);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("is_follow", result);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 查询关注用户列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/followeds", method = RequestMethod.GET)
    public void searchFollows(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("message_searchFollows");
        String token = request.getParameter("token");
        int user_id = Integer.parseInt(request.getParameter("userId"));

        List<User> users = new FollowService().get_follows(user_id);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("users", users);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 查询单一用户的详细信息  (用于SideBar)
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public void getUsers(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("message_getUsers");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        int user_id = Integer.parseInt(request.getParameter("userId"));
        User user = new UserService().get_user_by_id(user_id);
        msgMap.put("user", user);

        int count_follow = new FollowService().get_follow_count(user_id);
        int count_fans = new FollowService().get_fans_count(user_id);
        int count_weibo = new WeiboService().get_weibo_count(user_id);
        Map<String, Integer> counter = new HashMap<>();
        counter.put("count_follow", count_follow);
        counter.put("count_fans", count_fans);
        counter.put("count_weibo", count_weibo);
        msgMap.put("counter", counter);

        List<User> follows = new FollowService().get_follows(user_id);
        msgMap.put("follow", follows);

        List<User_interest> user_interests = new User_interestService().look_list_by_user_id(user_id);
        List<Interest> interests = new ArrayList<>();
        Interest interest = new Interest();
        for (int i = 0; i < user_interests.size(); i++) {
            User_interest tmpInterest = user_interests.get(i);
            interest.setId(tmpInterest.getInterest_id());
            interest.setName(tmpInterest.getInterest_name());
            interests.add(interest);
        }
        msgMap.put("interests", interests);

        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);

    }
}
