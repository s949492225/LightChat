package cn.syiyi.com.lightchat.module.chat.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.util.SPutils;
import cn.syiyi.com.lightchat.util.ViewUtil;

public class ChatActivity extends AppCompatActivity {
    public static final int TYPE_KEYBOARD_SYSTEM = 0;
    public static final int TYPE_KEYBOARD_CUSTUM = 1;
    @Bind(R.id.edit_msg)
    EditText mEditMsg;
    @Bind(R.id.btn_img)
    ImageButton mBtnImg;
    @Bind(R.id.keyBoard)
    FrameLayout mKeyBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        ViewUtil.actionBarConfig(this, null, true);
        setCustomKeyBoardHeight(mKeyBoard);
        mEditMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLayout();
            }
        });
        mBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow()) {
                    hideLayout();
                    showKeyboard(ChatActivity.this);
                } else {
                    showLayout();
                }
            }
        });
    }

    private void setCustomKeyBoardHeight(FrameLayout view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height = SPutils.getKeyBoardHeight(getApplicationContext());
        view.invalidate();
    }

    public void showLayout() {
        hideKeyboard(this);
        // 延迟一会，让键盘先隐藏再显示表情键盘，否则会有一瞬间表情键盘和软键盘同时显示
        mKeyBoard.postDelayed(new Runnable() {
            @Override
            public void run() {
                mKeyBoard.setVisibility(View.VISIBLE);
            }
        }, 50);
    }


    public boolean isShow() {
        return mKeyBoard.getVisibility() == View.VISIBLE;
    }

    public void hideLayout() {
        mKeyBoard.setVisibility(View.GONE);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(activity.getCurrentFocus()
                    .getWindowToken(), 0);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
