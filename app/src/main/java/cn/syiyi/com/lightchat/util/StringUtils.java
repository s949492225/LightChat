package cn.syiyi.com.lightchat.util;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.util...........
 * Created by lintao.song on 2016/1/20.
 */
public class StringUtils {
    public static boolean isNull(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
