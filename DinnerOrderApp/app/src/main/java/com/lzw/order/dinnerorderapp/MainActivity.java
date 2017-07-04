package com.lzw.order.dinnerorderapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.lzw.order.dinnerorderapp.component.ScrollViewAdapter;
import com.lzw.order.dinnerorderapp.component.ScrollViewIndicator;
import com.lzw.order.dinnerorderapp.component.ScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private int[] imgIds={R.drawable.djyt,R.drawable.hgr,R.drawable.mpdf};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollViewPager vpBanner=(ScrollViewPager) findViewById(R.id.vpBanner);
        vpBanner.setAdapter(new ScrollViewAdapter(imgIds));

        LinearLayout layout=(LinearLayout)findViewById(R.id.indicatorLayout);
        vpBanner.setOnPageChangeListener(new ScrollViewIndicator(this,vpBanner,layout,3));
    }
}
