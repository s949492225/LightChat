package cn.syiyi.com.lightchat.util;

import android.content.Context;
import android.widget.Toast;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.util...........
 * Created by lintao.song on 2016/1/20.
 */
public class ToastUtils {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
