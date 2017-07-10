package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.ShopInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public class ShopActivityAdapter extends BaseAdapter {
    private Context context;
    private List<ShopInfo.ShopActivity> activities;
    
    public ShopActivityAdapter(Context context, List<ShopInfo.ShopActivity> activities)
    {
        this.context=context;
        this.activities=activities;
    }
    
    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int i) {
        return activities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params.setMargins(0,5,0,5);
        ShopInfo.ShopActivity shopActivity=activities.get(i);
        
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setPadding(0,5,0,5);
        //linearLayout.setLayoutParams(params);
        TextView tvIcon=new TextView(context);
        tvIcon.setText(shopActivity.getIcon_name());
        tvIcon.setBackgroundColor(Color.parseColor("#"+shopActivity.getIcon_color()));
        linearLayout.addView(tvIcon);

        TextView tvTip=new TextView(context);
        tvTip.setText(shopActivity.getTips());
        linearLayout.addView(tvTip);

        return linearLayout;
    }
}
