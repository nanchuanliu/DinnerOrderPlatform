package com.lzw.order.dinnerorderapp.component;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by LZW on 2017/07/04.
 */
public class ScrollViewHandler extends Handler {

    private WeakReference<ScrollViewPager> pagerRef;

    public ScrollViewHandler(ScrollViewPager svPager)
    {
        pagerRef=new WeakReference<ScrollViewPager>(svPager);
    }

    @Override
    public void handleMessage(Message msg) {
        ScrollViewPager pager=pagerRef.get();
        int cur=pager.getCurrentItem()+1;
        if(cur>=pager.getAdapter().getCount())
            cur=0;

        pager.setCurrentItem(cur);
    }
}
