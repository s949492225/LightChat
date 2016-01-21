package cn.syiyi.com.lightchat.module.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.bean.UserInfo;
import cn.syiyi.com.lightchat.event.LoginLoadDataEvent;
import cn.syiyi.com.lightchat.util.SPutils;
import cn.syiyi.com.lightchat.util.StringUtils;
import cn.syiyi.com.lightchat.util.ToastUtils;
import cn.syiyi.com.lightchat.util.ViewUtil;
import de.greenrobot.event.EventBus;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtil.actionBarConfig(this, null, true);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register)
    public void register(View v) {
        final String userName = mEtName.getText().toString().toLowerCase().trim();
        final String pwd = mEtPwd.getText().toString().toLowerCase().trim();
        if (StringUtils.isNull(userName)) {
            ToastUtils.show(RegisterActivity.this, "请输入用户名！");
            return;
        }
        if (StringUtils.isNull(pwd)) {
            ToastUtils.show(RegisterActivity.this, "请输入密码！");
            return;
        }

        //查询是否存在username;
        BmobQuery<UserInfo> userInfoBmobQuery = new BmobQuery<UserInfo>();
        userInfoBmobQuery.addWhereEqualTo("userName", userName);
        userInfoBmobQuery.findObjects(this, new FindListener<UserInfo>() {
                    @Override
                    public void onSuccess(List<UserInfo> list) {
                        if (list.size() > 0) {
                            ToastUtils.show(RegisterActivity.this, "用户名已存在！");
                        } else {
                            UserInfo userInfo = new UserInfo(userName, pwd);
                            userInfo.save(RegisterActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    ToastUtils.show(RegisterActivity.this, "注册成功");
                                    SPutils.saveUserName(RegisterActivity.this, userName);
                                    SPutils.saveUserPwd(RegisterActivity.this, pwd);
                                    finish();
                                    EventBus.getDefault().post(new LoginLoadDataEvent());
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    if (i == 401) {
                                        ToastUtils.show(RegisterActivity.this, "用户名已存在！");
                                    } else {
                                        ToastUtils.show(RegisterActivity.this, s);
                                    }

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        ToastUtils.show(RegisterActivity.this, s);
                    }
                }
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

}
