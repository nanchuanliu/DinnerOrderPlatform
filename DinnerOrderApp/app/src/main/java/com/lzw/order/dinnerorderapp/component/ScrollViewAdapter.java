package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZW on 2017/07/04.
 */
public class ScrollViewAdapter extends PagerAdapter {
    private int[] imageIds;
    private ArrayList<View> viewList = new ArrayList<View>();

    public ScrollViewAdapter(int[] ids) {
        imageIds = ids;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = findViewByPosition(container, position);
        container.addView(view);
        return view;
    }

    private View findViewByPosition(ViewGroup container, int position) {
        for (View view :
                viewList) {
            if ((int) view.getTag() == position && view.getParent() == null) {
                return view;
            }
        }

        Context context = container.getContext();
        ImageView imageView = new ImageView(context);
        imageView.setTag(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load(imageIds[position]).into(imageView);

        viewList.add(imageView);

        return imageView;
    }

}
