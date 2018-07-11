import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.CacheUtils;
import common.JWTUtils;
import common.JsonCommon;
import common.TimeCommon;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;

/**
 * Created by lenovo on 2018/6/28.
 */
public class Test {

    CacheUtils cacheUtils = new CacheUtils();

    @org.junit.Test
    public void stringToJson() {
        String string = "{\"name\":\"eee\",\"password\":\"123\",\"email\":\"123@qq.com\"}";
        String jsonStr = "{\n" +
                "\t\"name\":\"eee\",\n" +
                "\t\"password\":\"123\",\n" +
                "\t\"email\":\"12134@qq.com\"\n" +
                "}";
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        System.out.println(jsonObject.getString("password"));
    }

    @org.junit.Test
    public void gsonToJson() {
        String string = "{\n" +
                "\t\"context\":\"aaa\",\n" +
                "\t\"user_id\":1,\n" +
                "\t\"interest_ids\":[\n" +
                "\t\t1,2\n" +
                "\t]\n" +
                "}";
        JsonObject jsonObject = new JsonParser().parse(string).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("interest_ids").getAsJsonArray();
        JsonCommon jsonCommon = new JsonCommon();
        System.out.println(jsonCommon.jsonArrayToObject(jsonArray, Integer.class).get(0));

    }

    @org.junit.Test
    public void testJWT() {
        String sub = "{\"user\":\"liu\",\"password\":123}";
        String jwtData = JWTUtils.createJWT("jwt", sub);
        System.out.println(jwtData);
        String data = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MzA1NDI4NzksInN1YiI6IntcInVzZXJcIjpcImxpdVwiLFwicGFzc3dvcmRcIjoxMjN9IiwiZXhwIjoxNTMxMTQ3Njc5fQ._0Ue8JMX7JNjkmLWsZkta8lxELoRKMPWwWXK2nfL30c";
        Claims claims = JWTUtils.verifyJWT(data);
        System.out.println(claims.getId());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getSubject());
    }

    @org.junit.Test
    public void testDate() {
        System.out.println(System.currentTimeMillis());
    }

    @org.junit.Test
    public void testCache() {
        cacheUtils.addOrUpdate("1", "aaa");
    }

    @org.junit.Test
    public void testGetCache() {
        System.out.println(TimeCommon.sqlTime());
    }
}
