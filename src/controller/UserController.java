package controller;

import common.*;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lenovo
 * @date 2018/6/27
 */
@Controller
@RequestMapping("/user")
public class UserController {

    CacheUtils cacheUtils = new CacheUtils();

    /**
     * 注册
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody String body, HttpServletResponse response) throws Exception {
        System.out.println("registerController");
        JsonCommon jsonCommon = new JsonCommon();
        User user = jsonCommon.getObject(body, User.class);
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", 200);
        Map<String, Object> msgMap = new HashMap<>();

        UserService userService = new UserService();
        //用户名重复
        int checkName = userService.checkName(user.getName());
        if (checkName == 1) {
            msgMap.put("success", -1);
            System.out.println("用户名重复");
        }
        //邮箱重复
        int checkEmail = userService.checkEmail(user.getEmail());
        if (checkEmail == 1) {
            msgMap.put("success", -2);
            System.out.println("邮箱重复");
        }
        //注册数据表
        DesUtils desUtils = new DesUtils();
        user.setPassword(desUtils.encrypt(user.getPassword()));
        user.setPhoto("/user/th.jpg");
        user.setLogin_time(TimeCommon.sqlTime());
        user.setRegister_time(TimeCommon.sqlTime());
        user.setIs_banned(0);
        //设定管理员
        if ("1713507920@qq.com".equalsIgnoreCase(user.getEmail())) {
            user.setIs_admini(1);
        } else {
            user.setIs_admini(0);
        }
        int result = userService.register(user);
        if (result > 0) {
            msgMap.put("success", 1);
        } else {
            msgMap.put("success", 0);
        }
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

    /**
     * 登陆
     *
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody String body, HttpServletResponse response) throws Exception {
        System.out.println("loginController");
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", 200);
        Map<String, Object> msgMap = new HashMap<>();

        JsonCommon jsonCommon = new JsonCommon();
        User user = jsonCommon.getObject(body, User.class);
        user.setEmail(user.getName());
        DesUtils desUtils = new DesUtils();
        user.setPassword(desUtils.encrypt(user.getPassword()));

        UserService userService = new UserService();
        User login = userService.login(user);
        String token = "";
        if (login != null) {
            User user_login_time = new User(login.getId(), TimeCommon.sqlTime());
            int update_login_time = userService.update_login_time(user_login_time);
            if (login.getIs_admini() == 1) {//管理员登陆成功
                msgMap.put("success", 2);
            } else {
                msgMap.put("success", 1);//用户登陆成功
            }
            String sub = jsonCommon.getJsonString(user);
            token = JWTUtils.createJWT(String.valueOf(user.getId()), sub);
            //将 token 存入缓存
            cacheUtils.addOrUpdate(String.valueOf(user.getId()), token);
        } else {
            msgMap.put("success", 0);
        }
        msgMap.put("user", login);
        msgMap.put("token", token);
        map.put("msg", msgMap);
        DataToJson.submitByJson(map, response);
    }

}
