package controller;

import common.DataToJson;
import common.DesUtils;
import common.JsonCommon;
import common.TimeCommon;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 注册
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/register")
    public void register(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("register")) {
            System.out.println(body);
            JsonCommon jsonCommon = new JsonCommon();
            User user = jsonCommon.getObject(body, User.class);
            UserService userService = new UserService();
            int checkName = userService.checkName(user.getName());
            Map<String, Object> map = new HashMap<>();
            map.put("statusCode", 200);
            Map<String, Object> msgMap = new HashMap<>();
            //用户名重复
            if (checkName == 1) {
                msgMap.put("success", -1);
                map.put("msg", msgMap);
                System.out.println("用户名重复");
                DataToJson.submitByJson(map, response);
                return;
            }
            int checkEmail = userService.checkEmail(user.getEmail());
            if (checkEmail == 1) {
                msgMap.put("success", -2);
                map.put("msg", msgMap);
                System.out.println("邮箱重复");
                DataToJson.submitByJson(map, response);
                return;
            }
            DesUtils desUtils = new DesUtils();
            user.setPassword(desUtils.encrypt(user.getPassword()));
            user.setPhoto("web/statics/images/user/th.jpg");
            user.setLogin_time(TimeCommon.sqlTime());
            user.setRegister_time(TimeCommon.sqlTime());
            user.setIs_banned(0);
            System.out.println(user);
            int result = userService.register(user);
            if (result > 0) {
                System.out.println("注册成功！");
                msgMap.put("success", 1);
                map.put("msg", msgMap);
                DataToJson.submitByJson(map, response);
                return;
            } else {
                System.out.println("注册失败！");
                msgMap.put("success", 0);
                map.put("msg", msgMap);
                DataToJson.submitByJson(map, response);
                return;
            }
        }
    }

    /**
     * 登陆
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public void login(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonCommon jsonCommon = new JsonCommon();
        User user = jsonCommon.getObject(body, User.class);
        user.setEmail(user.getName());
        DesUtils desUtils = new DesUtils();
        user.setPassword(desUtils.encrypt(user.getPassword()));
        UserService userService = new UserService();
        User login = userService.login(user);
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", 200);
        Map<String, Object> msgMap = new HashMap<>();
        if (login != null) {
            User user_login_time = new User(login.getId(), TimeCommon.sqlTime());
            int update_login_time = userService.update_login_time(user_login_time);
            if (update_login_time == 1) {
                System.out.println("登陆时间更新成功");
            }
            msgMap.put("succes", 1);
            msgMap.put("user", login);
            System.out.println("登陆成功");
            DataToJson.submitByJson(map, response);
            return;
        } else {
            msgMap.put("succes", 0);
            msgMap.put("user", login);
            map.put("msg", msgMap);
            DataToJson.submitByJson(map, response);
            System.out.println("登陆失败");
            return;
        }
    }

    @RequestMapping("/change_photo")
    public void change_photo(@RequestParam("photo") CommonsMultipartFile photo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = request.getServletContext();
        System.out.println(servletContext);
        String path = request.getServletContext().getRealPath("/statics/images/user");//获取文件上传目的位置的真实路径
        System.out.println("path: " + path);
        String filename = photo.getOriginalFilename();
        File file = new File(path, filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        photo.transferTo(new File(path + File.separator + filename));

        Map<String, Object> map = new HashMap<>();
        map.put("statsuCode", 200);
        DataToJson.submitByJson(map, response);

    }
}
