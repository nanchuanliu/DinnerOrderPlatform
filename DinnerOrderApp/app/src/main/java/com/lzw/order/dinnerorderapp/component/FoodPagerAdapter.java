package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/8.
 */

public class FoodPagerAdapter extends PagerAdapter {

    private List<View> viewList=new ArrayList<>();
    private List<String> list;
    private Context context;

    public FoodPagerAdapter(Context context,List<String> list)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return (list.size()-1)/8+1;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=findViewByPosition(container,position);
        container.addView(view);
        return view;
    }

    private View findViewByPosition(ViewGroup container,int position)
    {
        for (View view :
                viewList) {
            if ((int) view.getTag() == position && view.getParent() == null)
                return view;
        }

        Context context=container.getContext();
        View view=View.inflate(context, R.layout.control_foodentry,null);
        GridView gv=(GridView) view.findViewById(R.id.gvFoodEntry);

        FoodEntryAdapter adapter=new FoodEntryAdapter(context,position);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(),i+","+l,Toast.LENGTH_SHORT).show();
            }
        });
        view.setTag(position);
        viewList.add(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}
