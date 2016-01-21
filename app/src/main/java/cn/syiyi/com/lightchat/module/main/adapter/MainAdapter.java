package cn.syiyi.com.lightchat.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.config.Config;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.view.main.adapter...........
 * Created by lintao.song on 2016/1/20.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;

    public MainAdapter(FragmentManager fm, List<Fragment> mData) {
        super(fm);
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Config.PAGE_NAME[position];
    }

    public int getPageDrawable(int postion) {
        return R.drawable.tab_selector;
    }
}
