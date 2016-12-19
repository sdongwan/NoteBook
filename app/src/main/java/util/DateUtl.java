package util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/12/8.
 */

public class DateUtl {

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTime(new Date(System.currentTimeMillis()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String monthStr = "" + month;
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        String dayStr = "" + day;
        if (day < 10) {
            dayStr = "0" + dayStr;
        }
        String hourStr = "" + hour;
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        String minuteStr = "" + minute;
        if (minute < 10) {
            minuteStr = "0" + minuteStr;
        }

        String currentTime = "" + year + "年" + monthStr + "月" + dayStr + "日" + "  " + hourStr + ":" + minuteStr;

        return currentTime;
    }

}
