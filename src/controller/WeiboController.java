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
import org.springframework.web.bind.annotation.RequestMethod;
import service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author lenovo
 * @date 2018/6/29
 */
@Controller
@RequestMapping("/weibo")
public class WeiboController {

    JsonCommon jsonCommon = new JsonCommon();

    /**
     * 根据 “用户名/关键字/趣点名” 查询博文
     *
     * @param body
     * @param response
     */
    @RequestMapping("/search")
    public void search(@RequestBody String body, HttpServletResponse response) {
        System.out.println("weibo_searchController");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String keyword = jsonObject.get("keyword").toString();
        int sort = Integer.parseInt(jsonObject.get("sort").toString());
        String token = null;
        if (jsonObject.get("token") != null) {
            token = jsonObject.get("token").toString();
        }
        keyword = "%" + keyword + "%";
        if (sort == 0) {
            WeiboService weiboService = new WeiboService();
            List<Weibo> lists = weiboService.search_weibo(keyword);//根据关键字查询博文
            List<Map> listMap = getListMap(lists, token);//设置传给前端的值
            msgMap.put("weibos", listMap);
        } else if (sort == 1) {
            UserService userService = new UserService();
            List<User> lists = userService.search_user(keyword);
            msgMap.put("users", lists);
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    public List<Map> getListMap(List<Weibo> lists, String token) {
        List<Map> listMap = new ArrayList<>();

        CommentService commentService = new CommentService();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> weiboMap = new HashMap<>();
            Map<String, Object> contentMap = new HashMap<>();
            Weibo weiboTmp = lists.get(i);
            Map<String, Object> tmpMap = getMap(weiboTmp, commentService, token);
            contentMap.put("content", tmpMap);
            weiboMap.put("id", weiboTmp.getId());
            weiboMap.put("content", contentMap);
            listMap.add(weiboMap);
        }
        return listMap;
    }

    public Map getMap(Weibo weiboTmp, CommentService commentService, String token) {
        Map<String, Object> tmpMap = new HashMap<>();
        tmpMap.put("create_time", weiboTmp.getCreate_time());
        tmpMap.put("read_count", weiboTmp.getRead_count());
        tmpMap.put("comment_count", commentService.get_by_weibo(weiboTmp.getId()).size());
        tmpMap.put("tansmit_count", new Random().nextInt(5));
        tmpMap.put("thumb_count", weiboTmp.getThumb_count());
        if (token != null) {
            tmpMap.put("is_thumb", 1);
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", weiboTmp.getUser_id());
        userMap.put("photo", "web/user/th.jpg");
        userMap.put("name", weiboTmp.getUser_name());
        tmpMap.put("user", userMap);

        return tmpMap;
    }

    /**
     * 查询单一用户的微博/区别于搜索
     *
     * @param body
     * @param response
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public void searchUser(@RequestBody String body, HttpServletResponse response) {
        System.out.println("weibo_searchUser");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int targetId = Integer.parseInt(jsonObject.get("targetId").toString());
        String token = null;
        if (jsonObject.get("token") != null) {
            token = jsonObject.get("token").toString();
        }
        WeiboService weiboService = new WeiboService();
        List<Weibo> weiboList = weiboService.search_by_userid(targetId);
        if (weiboList != null) {
            msgMap.put("success", 1);
            List<Map> lists = getListMap(weiboList, token);
            msgMap.put("weibos", lists);
        } else {
            msgMap.put("success", 0);
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 获得发布微博时的趣点列表
     *
     * @param response
     */
    @RequestMapping(value = "/interests", method = RequestMethod.GET)
    public void getInterests(HttpServletResponse response) {
        System.out.println("weibo_getInterests");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);
        InterestService interestService = new InterestService();
        List<Interest> interests = interestService.get_all_interests();
        if (interests != null) {
            msgMap.put("success", 1);
            msgMap.put("interest", interests);
        } else {
            msgMap.put("success", 0);
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 发布微博
     *
     * @param body
     * @param response
     */
    @RequestMapping("/issue")
    public void issueWeibo(@RequestBody String body, HttpServletResponse response) {
        System.out.println("weibo_issueWeibo");
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

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
        System.out.println("weibo_thumbWeibo");
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
     * 删除博文（包括博文，点赞，评论）
     *
     * @param body
     * @param response
     */
    @RequestMapping("/delete")
    public void delete(@RequestBody String body, HttpServletResponse response) {
        System.out.println("weibo_delete");
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
     * 获取单个微博详情
     *
     * @param body
     * @param response
     */
    @RequestMapping("/detail")
    public void getDetail(@RequestBody String body, HttpServletResponse response) {
        System.out.println("weibo_getDetail");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        int weibo_id = Integer.parseInt(jsonObject.get("weibo_id").toString());
        String token = null;
        if (jsonObject.get("token") != null) {
            token = jsonObject.get("token").toString();
        }
        WeiboService weiboService = new WeiboService();
        CommentService commentService = new CommentService();
        Weibo weibo = weiboService.search_by_weibo_id(weibo_id);
        Map<String, Object> content = getMap(weibo, commentService, token);
        Map<String, Object> weiboMap = new HashMap<>();
        weiboMap.put("id", weibo_id);
        weiboMap.put("content", content);

        List<Comment> commentList = commentService.get_by_weibo(weibo_id);
        List<Map> comments = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            Map<String, Object> tmpMap = new HashMap<>();
            Comment tmpComment = commentList.get(i);
            tmpMap.put("id", tmpComment.getId());
            tmpMap.put("user", new UserService().get_user_by_id(tmpComment.getUser_id()));
            boolean parent = tmpComment.getParent_id() > 0 ? true : false;
            if (parent) {
                tmpMap.put("parent", new UserService().get_user_by_id(tmpComment.getParent_id()));
            }
            tmpMap.put("create_time", tmpComment.getComment_time());
            tmpMap.put("context", tmpComment.getContext());
            comments.add(tmpMap);
        }

        msgMap.put("success", 1);
        msgMap.put("weibo", weiboMap);
        msgMap.put("comments", comments);

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
        System.out.println("weibo_comment");
        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        Comment comment = new Comment(-1, Integer.parseInt(jsonObject.get("weibo_id").toString()), jsonObject.get("context").toString(),
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
        System.out.println("weibo_del_comment");
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
        System.out.println("weibo_look_hot");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        JsonObject jsonObject = jsonCommon.toJsonObject(body);
        String token = jsonObject.get("token").toString();
        if (jsonObject.get("weibo") != null) {
            WeiboService weiboService = new WeiboService();
            List<Weibo> weiboList = weiboService.look_hot();
            if (weiboList != null) {
                msgMap.put("success", 1);
            }
            List<Map> listMap = getListMap(weiboList, token);
            msgMap.put("weibos", listMap);
            map.put("msg", msgMap);
            DataToJson.submitByJson(map, response);
        }
    }

    /**
     * 按照趣点查看微博
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/look_insterest",method = RequestMethod.GET)
    public void look_insterest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("weibo_look_insterest");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        map.put("statusCode", 200);

        int interest_id = Integer.parseInt(request.getParameter("interest_id"));
        String token = null;
        if (request.getParameter("token") != null) {
            token = request.getParameter("token");
        }
        int user_id = 0;
        if (request.getParameter("user_id") != null) {
            user_id = Integer.parseInt(request.getParameter("user_id"));
        }
        Weibo_interestService weibo_interestService = new Weibo_interestService();
        List<Integer> weibo_by_interest = weibo_interestService.get_weibo_by_interest(interest_id);
        WeiboService weiboService = new WeiboService();
        List<Weibo> weibos = new ArrayList<>();
        for (int i = 0; i < weibo_by_interest.size(); i++) {
            Weibo weibo = weiboService.search_by_weibo_id(weibo_by_interest.get(i));
            weibos.add(weibo);
        }
        List<Map> listMap = getListMap(weibos, token);
        msgMap.put("success", 1);
        msgMap.put("weibos", listMap);

        User_interestService user_interestService = new User_interestService();
        int subs = user_interestService.is_subs(user_id, interest_id);
        msgMap.put("is_subs", subs);

        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

}
