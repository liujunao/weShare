package controller;

import com.google.gson.JsonObject;
import common.DataToJson;
import common.DesUtils;
import common.JsonCommon;
import model.Follow;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.FollowService;
import service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int id = Integer.parseInt(jsonObject.get("id").toString());
        String name = jsonObject.get("name").toString();
        int gender = Integer.parseInt(jsonObject.get("gender").toString());
        String photo = jsonObject.get("photo").toString();

        User user = new User(id, name, photo, gender);
        UserService userService = new UserService();
        int result = userService.modify_user(user);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("success", result);
        if (result > 0) {
            map.put("statusCode", 200);
            msgMap.put("user", userService.get_user_by_id(id));
        } else {
            map.put("statusCode", 500);
            msgMap.put("user", "{}");
        }
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
    public void modify_pass(@RequestBody String body, HttpServletResponse response) throws Exception {
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
     * 取消关注
     *
     * @param body
     * @param response
     */
    @RequestMapping("/cancel_connect")
    public void cancel_connect(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int followed_id = Integer.parseInt(jsonObject.get("followed_id").toString());
        int following_id = Integer.parseInt(jsonObject.get("following_id").toString());
        Follow follow = new Follow(-1, followed_id, following_id);
        FollowService followService = new FollowService();
        int result = followService.add_follow(follow);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("success", result);
        map.put("statusCode", 200);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }


}
