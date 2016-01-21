package cn.syiyi.com.lightchat.bean;

import cn.bmob.v3.BmobObject;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.bean...........
 * Created by lintao.song on 2016/1/20.
 */
public class UserInfo extends BmobObject {
    private String userName;
    private String passWord;

    public UserInfo(String userName, String passWord) {
        this.passWord = passWord;
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
