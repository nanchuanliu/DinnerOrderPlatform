package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/8.
 */

public class FoodEntryAdapter extends BaseAdapter {
    private Context context;
    private List<String> listNames;
    private List<String> listUrls;
    private LayoutInflater layoutInflater;
    private int curPage;

    public FoodEntryAdapter(Context context,int page) {
        this.context = context;
        this.listNames= UrlUtil.listFoodName;
        this.listUrls=UrlUtil.listFoodUrl;
        this.curPage=page;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return listNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        position=curPage*8+position;

        View view=layoutInflater.inflate(R.layout.control_fooditem,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.imgFoodItem);
        TextView textView=(TextView)view.findViewById(R.id.tvFoodName);
        textView.setText(listNames.get(position));
        Picasso.with(context).load(listUrls.get(position)).into(imageView);
        return view;
    }


}
