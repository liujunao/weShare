package common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lenovo
 * @date 2018/6/28
 */
public class ObjAnalisis {

    /**
     * 将对象转换成map类型
     * @param object
     * @return
     */
    public static Map convertObjToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        if (object == null) {
            return null;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = object.getClass().getDeclaredField(fields[i].getName());
                field.setAccessible(true);
                Object obj = field.get(object);
                map.put(fields[i].getName(), obj);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
