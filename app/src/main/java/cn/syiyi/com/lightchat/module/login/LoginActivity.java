package cn.syiyi.com.lightchat.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.bean.UserInfo;
import cn.syiyi.com.lightchat.event.LoginLoadDataEvent;
import cn.syiyi.com.lightchat.util.SPutils;
import cn.syiyi.com.lightchat.util.StringUtils;
import cn.syiyi.com.lightchat.util.ToastUtils;
import cn.syiyi.com.lightchat.module.main.MainActivity;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_username)
    EditText mEtUsername;
    @Bind(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initData(null);

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void initData(LoginLoadDataEvent event) {
        String userName = SPutils.getUserName(this);
        String pwd = SPutils.getUserPwd(this);
        if (mEtUsername != null) {
            mEtUsername.setText(userName);
        }
        if (mEtPassword != null) {
            mEtPassword.setText(pwd);
        }

    }

    @OnClick(R.id.btn_login)
    public void login(View v) {
        final String userName = mEtUsername.getText().toString().toLowerCase().trim();
        final String pwd = mEtPassword.getText().toString().toLowerCase().trim();
        if (StringUtils.isNull(userName)) {
            ToastUtils.show(this, "请输入用户名！");
            return;
        }
        if (StringUtils.isNull(pwd)) {
            ToastUtils.show(this, "请输入密码！");
            return;
        }

        //查询是否存在username;
        BmobQuery<UserInfo> userInfoBmobQuery = new BmobQuery<UserInfo>();
        userInfoBmobQuery.addWhereEqualTo("userName", userName);
        userInfoBmobQuery.findObjects(this, new FindListener<UserInfo>() {
                    @Override
                    public void onSuccess(List<UserInfo> list) {
                        if (list.size() > 0) {
                            if (list.get(0).getPassWord().equals(pwd)) {
                                SPutils.saveUserName(LoginActivity.this, userName);
                                SPutils.saveUserPwd(LoginActivity.this, pwd);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtils.show(LoginActivity.this, "密码输入错误！");
                            }
                        } else {
                            ToastUtils.show(LoginActivity.this, "用户名不存在！");
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        ToastUtils.show(LoginActivity.this, s);
                    }
                }
        );
    }

    @OnClick(R.id.tv_register)
    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
