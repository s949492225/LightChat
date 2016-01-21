package cn.syiyi.com.lightchat.app;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.syiyi.com.lightchat.config.Config;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.app...........
 * Created by lintao.song on 2016/1/20.
 */
public class LightChatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, Config.BMOB_APPLICATION_ID);
    }
}
