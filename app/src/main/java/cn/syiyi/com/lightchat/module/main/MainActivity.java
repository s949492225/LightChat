package cn.syiyi.com.lightchat.module.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.module.recent.view.RecentFragment;
import cn.syiyi.com.lightchat.module.contacts.view.ContactsFragment;
import cn.syiyi.com.lightchat.module.friendsNews.FriendNewsFragment;
import cn.syiyi.com.lightchat.module.main.adapter.MainAdapter;
import cn.syiyi.com.lightchat.module.mine.MineFragment;
import cn.syiyi.com.swifttablayout.SwiftTabLayout;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.content)
    ViewPager mContent;
    @Bind(R.id.tabhost)
    SwiftTabLayout mTabhost;
    private List<Fragment> mFragments;
    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mFragments = new ArrayList<>();
        Fragment chat = RecentFragment.newInstance("0", "0");
        Fragment contacts = ContactsFragment.newInstance("0", "0");
        Fragment friendNews = FriendNewsFragment.newInstance("0", "0");
        Fragment mine = MineFragment.newInstance("0", "0");
        mFragments.add(chat);
        mFragments.add(contacts);
        mFragments.add(friendNews);
        mFragments.add(mine);
        mMainAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        mContent.setAdapter(mMainAdapter);
        mTabhost.setupWithViewPager(mContent);
    }

}
