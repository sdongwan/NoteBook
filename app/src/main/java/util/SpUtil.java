package util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/11.
 */

public class SpUtil {
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("flag", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("flag", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

}
