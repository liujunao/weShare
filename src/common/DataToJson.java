package common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author lenovo
 * @date 2018/6/28
 */
public class DataToJson {

    public static void submitByJson(Map<String,Object>list, HttpServletResponse response){
        JsonCommon jsonCommon = new JsonCommon();
        String data = jsonCommon.getJsonString(list);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            System.out.println("向前端传送json出错");
            e.printStackTrace();
        }
        writer.flush();
        writer.write(data);
        writer.flush();
        writer.close();
    }
}
