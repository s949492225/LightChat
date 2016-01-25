package cn.syiyi.com.lightchat.util;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.util...........
 * Created by lintao.song on 2016/1/20.
 */
public class ViewUtil {
    public static void actionBarConfig(AppCompatActivity context, Toolbar toolbar, Boolean WithHomeButton) {
        if (toolbar != null) {
            context.setSupportActionBar(toolbar);
        }
        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null && WithHomeButton) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
    public static int dpi2px(Context context,float dpi){
        float density=context.getResources().getDisplayMetrics().density;
        return (int) (density*dpi);
    }
}
