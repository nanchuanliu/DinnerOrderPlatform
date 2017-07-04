package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LZW on 2017/07/04.
 */
public class ScrollViewPager extends ViewPager  {

    private int delay=3000;
    private PagerAdapter adapter;
    private Timer timer=new Timer();
    private ScrollViewHandler handler=new ScrollViewHandler(this);

    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {

        super.setAdapter(adapter);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },delay,delay);
    }


    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

}
