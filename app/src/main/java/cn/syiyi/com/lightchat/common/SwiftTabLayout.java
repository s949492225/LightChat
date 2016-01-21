package cn.syiyi.com.lightchat.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * LightChat...........
 * cn.syiyi.com.lightchat.common...........
 * Created by lintao.song on 2016/1/20.
 */
public class SwiftTabLayout extends ViewGroup {
    private List<ImageView> mIcons;
    private List<TextView> mTitles;

    public SwiftTabLayout(Context context) {
        super(context);
    }

    public SwiftTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwiftTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            View v = getChildAt(0);
            v.layout(l,t,r,b);
        }

    }

    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        setTabsFromPagerAdapter(adapter);
    }

    public void setTabsFromPagerAdapter(@NonNull PagerAdapter adapter) {
        removeAllTabs();
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            addTab(adapter.getPageTitle(i), getPageDrawable(adapter, i));
        }
        invalidate();
    }

    private void addTab(CharSequence pageTitle, int pageDrawable) {
        TextView title = new TextView(getContext());
        ImageView icon = new ImageView(getContext());
        title.setText(pageTitle);
        icon.setImageDrawable(getResources().getDrawable(pageDrawable));
        mIcons.add(icon);
        mTitles.add(title);
        addView(title, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(icon, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

    }

    private int getPageDrawable(@NonNull PagerAdapter adapter, int position) {
        try {
            Method method = adapter.getClass().getMethod("getPageDrawable", int.class);
            return (int) method.invoke(adapter, position);
        } catch (Exception e) {
            throw new RuntimeException("public int getPageDrawable(int postiion) Method not impl!");
        }
    }

    /**
     * Remove all tabs from the action bar and deselect the current tab.
     */
    public void removeAllTabs() {
        // Remove all the views
        if (mIcons != null) {
            mIcons.clear();
        }
        if (mTitles != null) {
            mTitles.clear();
        }
        mIcons = new ArrayList<>();
        mTitles = new ArrayList<>();
        removeAllViews();

    }

}
