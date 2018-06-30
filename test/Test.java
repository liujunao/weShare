import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.JsonCommon;
import net.sf.json.JSONObject;

/**
 * Created by lenovo on 2018/6/28.
 */
public class Test {

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
}
