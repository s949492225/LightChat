package cn.syiyi.com.lightchat.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.util...........
 * Created by lintao.song on 2016/1/20.
 */
public class SPutils {
    private static final String sp_name = "lightChat";

    public static String getUserName(Context context) {
        return getSP(context).getString("username", null);
    }

    public static String getUserPwd(Context context) {
        return getSP(context).getString("password", null);
    }

    public static void saveUserName(Context context, String username) {
        getSP(context).edit().putString("username", username).commit();
    }

    public static void saveUserPwd(Context context, String password) {
        getSP(context).edit().putString("password", password).commit();
    }

    public static int getKeyBoardHeight(Context context) {
        return getSP(context).getInt("keyboardheight", 0);
    }

    public static void saveKeyBoardHeight(Context context, int keyBoardHeight) {
        getSP(context).edit().putInt("keyboardheight", keyBoardHeight).commit();
    }

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
    }

}
