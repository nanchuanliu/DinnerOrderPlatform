package com.lzw.order.dinnerorderapp.component;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzw.order.dinnerorderapp.fragment.RecommendFragment;

/**
 * Created by LZW on 2017/07/05.
 */
public class IndicatorAdapter extends FragmentPagerAdapter {

    public IndicatorAdapter(FragmentManager fm) {
        super(fm);
    }

    private static final String[] CONTENT = new String[]{"推荐", "娱乐", "体育", "财经", "科技", "汽车", "NBA", "头条", "娱乐", "体育", "财经", "科技", "汽车", "NBA"};

    @Override
    public Fragment getItem(int position) {
        return new RecommendFragment();
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length].toUpperCase();
    }
}
