package common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2018/6/29.
 */
public class TimeCommon {

    public static String sqlTime(){
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date());

        return time;
    }
}
