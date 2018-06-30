package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.DataToJson;
import common.JsonCommon;
import common.TimeCommon;
import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.*;

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
@RequestMapping("/weibo")
public class WeiboController {

    JsonCommon jsonCommon = new JsonCommon();

    /**
     * 发布微博
     *
     * @param body
     * @param response
     */
    @RequestMapping("/issue")
    public void issueWeibo(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", 200);
        Map<String, Object> msgMap = new HashMap<>();
        Weibo weibo = new Weibo();
        weibo.setContext(jsonObject.get("context").toString());
        weibo.setUser_id(Integer.parseInt(jsonObject.get("user_id").toString()));
        weibo.setRead_count(0);
        weibo.setThumb_count(0);
        weibo.setCreate_time(TimeCommon.sqlTime());
        UserService userService = new UserService();
        weibo.setUser_name(userService.get_name_by_id(Integer.parseInt(jsonObject.get("user_id").toString())));
        WeiboService weiboService = new WeiboService();
        int addWeibo = weiboService.addWeibo(weibo);
        if (addWeibo > 0) {
            System.out.println("微博添加成功");
            JsonArray jsonArray = jsonObject.get("interest_ids").getAsJsonArray();
            JsonCommon jsonCommon = new JsonCommon();
            List<Integer> interestList = jsonCommon.jsonArrayToObject(jsonArray, Integer.class);
            int last_weibo_id = weiboService.get_last_weibo();
            Weibo_interest weibo_interest = new Weibo_interest();
            weibo_interest.setWeibo_id(last_weibo_id);
            Weibo_interestService weibo_interestService = new Weibo_interestService();
            for (int i = 0; i < interestList.size(); i++) {
                weibo_interest.setInterest_id(interestList.get(i));
                System.out.println(weibo_interest);
                weibo_interestService.add_weibo_interest(weibo_interest);
            }
            msgMap.put("success", 1);
            map.put("msg", msgMap);
            DataToJson.submitByJson(map, response);
        }
    }

    /**
     * 点赞或取消点赞博文
     *
     * @param body
     * @param response
     */
    @RequestMapping("/thumb")
    public void thumbWeibo(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", 200);
        Map<String, Object> msgMap = new HashMap<>();
        String is_thumb = jsonObject.get("thumb").toString();
        int weibo_id = Integer.parseInt(jsonObject.get("weibo_id").toString());
        int user_id = Integer.parseInt(jsonObject.get("user_id").toString());
        WeiboService weiboService = new WeiboService();
        Thumb_weibo_user thumb_weibo_user = new Thumb_weibo_user(-1, weibo_id, user_id);
        Thumb_weibo_userService thumb_weibo_userService = new Thumb_weibo_userService();
        int weibo_add_thumb_count = -1;
        int thumbWeiboUser = -1;
        if (is_thumb.equalsIgnoreCase("1")) {//点赞
            weibo_add_thumb_count = weiboService.add_thumb_count(weibo_id);//点赞记录+1
            thumbWeiboUser = thumb_weibo_userService.thumb_weibo_user(thumb_weibo_user);//记录博文与点赞者关系
        } else {//取消点赞
            weibo_add_thumb_count = weiboService.cancel_thumb_count(weibo_id);//点赞记录-1
            thumbWeiboUser = thumb_weibo_userService.thumb_weibo_user(thumb_weibo_user);//记录博文与点赞者关系
        }
        if (thumbWeiboUser > 0 && weibo_add_thumb_count > 0) {
            System.out.println("点赞记录完成");
            msgMap.put("success", 1);
        } else {
            msgMap.put("success", 0);
        }
        int weibo_user = thumb_weibo_userService.get_weibo_user(thumb_weibo_user);
        if (weibo_user > 0) {
            msgMap.put("is_thumb", weibo_user);
        } else {
            msgMap.put("is_thumb", 0);
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 根据 “用户名/关键字/趣点名” 查询博文
     *
     * @param body
     * @param response
     */
    @RequestMapping("/search")
    public void search(@RequestBody String body, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String keyword = jsonObject.get("keyword").toString();
        WeiboService weiboService = new WeiboService();
        Weibo_interestService weibo_interestService = new Weibo_interestService();
        keyword = "%" + keyword  + "%";
        int sort = Integer.parseInt(jsonObject.get("sort").toString());
        if (sort == 0) {
            List<Weibo> lists = weiboService.search_weibo(keyword);
            List<Integer> weibo_ids = new ArrayList<>();//记录查询到的博文 ID，避免重复查询
            for (int i = 0; i < lists.size(); i++) {
                weibo_ids.add(lists.get(i).getId());
            }
            List<Integer> integerList = weibo_interestService.search_by_interest(keyword);
            for (int i = 0; i < integerList.size(); i++) {
                Weibo weiboById = weiboService.search_by_weibo_id(integerList.get(i));
                boolean contains = weibo_ids.contains(weiboById.getId());
                if (!contains) {
                    lists.add(weiboById);
                    weibo_ids.add(weiboById.getId());
                }
            }
            msgMap.put("weibos", lists);
        } else if (sort == 1) {
            UserService userService = new UserService();
            List<User> lists = userService.search_user(keyword);
            msgMap.put("users", lists);
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 删除博文（包括博文，点赞，评论）
     *
     * @param body
     * @param response
     */
    @RequestMapping("/delete")
    public void delete(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int weibo_id = Integer.parseInt(jsonObject.get("id").toString());
        System.out.println(weibo_id);
        WeiboService weiboService = new WeiboService();
        int result = weiboService.delete_by_id(weibo_id);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
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
     * 添加评论
     *
     * @param body
     * @param response
     */
    @RequestMapping("/comment")
    public void comment(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        Comment comment = new Comment(-1, Integer.parseInt(jsonObject.get("weibo_id").toString()), jsonObject.get("content").toString(),
                TimeCommon.sqlTime(), Integer.parseInt(jsonObject.get("parent_id").toString()), Integer.parseInt(jsonObject.get("user_id").toString()));
        CommentService commentService = new CommentService();
        int add_comment = commentService.add_comment(comment);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        msgMap.put("success", add_comment);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 删除评论
     *
     * @param body
     * @param response
     */
    @RequestMapping("/del_comment")
    public void del_comment(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        Comment comment = new Comment(Integer.parseInt(jsonObject.get("id").toString()), Integer.parseInt(jsonObject.get("user_id").toString()));
        CommentService commentService = new CommentService();
        int del_comment = commentService.del_comment(comment);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        msgMap.put("success", del_comment);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 查看热门微博 按照时间排序返回最新的微博
     *
     * @param body
     * @param response
     */
    @RequestMapping("/look_hot")
    public void look_hot(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        if (jsonObject.get("weibo") != null) {
            WeiboService weiboService = new WeiboService();
            List<Weibo> weiboList = weiboService.look_hot();
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> msgMap = new HashMap<>();
            map.put("statusCode", 200);
            msgMap.put("success", 1);
            msgMap.put("weibos", weiboList);
            map.put("msg", msgMap);
            DataToJson.submitByJson(map, response);
        }
    }

    /**
     * 按照趣点查看微博
     *
     * @param body
     * @param response
     */
    @RequestMapping("/look_insterest")
    public void look_insterest(@RequestBody String body, HttpServletResponse response) {
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int interest_id = Integer.parseInt(jsonObject.get("interest_id").toString());
        Weibo_interestService weibo_interestService = new Weibo_interestService();
        List<Integer> weibo_by_interest = weibo_interestService.get_weibo_by_interest(interest_id);
        WeiboService weiboService = new WeiboService();
        List<Weibo> weibos = new ArrayList<>();
        for (int i = 0; i < weibo_by_interest.size(); i++) {
            Weibo weibo = weiboService.search_by_weibo_id(weibo_by_interest.get(i));
            weibos.add(weibo);
        }
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        msgMap.put("success", 1);
        msgMap.put("weibos", weibos);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

}
