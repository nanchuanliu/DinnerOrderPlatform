package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzw.order.dinnerorderapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZW on 2017/07/04.
 */
public class ScrollViewIndicator  implements ViewPager.OnPageChangeListener {

    private Context context;
    private ViewPager viewPager;
    private LinearLayout layout;
    private int size;
    private List<ImageView> dotList=new ArrayList<>();

/*    private final Paint mPaintStroke=new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintFill=new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int defaultColor=Color.WHITE;

    private int radius=4;
    private int interval=4;

    public ScrollViewIndicator(Context context)
    {
        super(context);
        initColor(defaultColor,defaultColor);
    }

    public ScrollViewIndicator(Context context, AttributeSet attrs)
    {
        super(context,attrs);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ScrollViewIndicator);
        int fillColor=array.getColor(R.styleable.ScrollViewIndicator_fillColor,0xFFFFFFFF);
        int strokeColor=array.getColor(R.styleable.ScrollViewIndicator_strokeColor,0xFFFFFFFF);
        radius=array.getColor(R.styleable.ScrollViewIndicator_fillColor,radius);
        interval=array.getColor(R.styleable.ScrollViewIndicator_interval,radius);

        initColor(fillColor,strokeColor);
    }*/

    public ScrollViewIndicator(Context mContext, ViewPager mViewPager, LinearLayout mLayout,int mSize)
    {
        this.context=mContext;
        this.viewPager=mViewPager;
        this.layout=mLayout;
        this.size=mSize;

        for (int i = 0; i < size; i++) {
            ImageView imageView=new ImageView(context);
            imageView.setBackgroundResource(R.drawable.indicator);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            params.rightMargin=15;

            layout.addView(imageView,params);
            dotList.add(imageView);

        }

    }


/*    private void initColor(int fillColor,int strokeColor)
    {
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setColor(strokeColor);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(fillColor);
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < size; i++) {
            if((position%size)==i)
            {
                dotList.get(i).setEnabled(true);
            }
            else
            {
                dotList.get(i).setEnabled(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
