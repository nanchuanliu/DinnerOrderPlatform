package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/15.
 */

public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater=null;
    private List<String> items=null;
    private int selectedPosition=-1;
    private int selectedColor;

    public ListViewAdapter(Context context,List<String> _items,int color)
    {
        inflater=LayoutInflater.from(context);
        this.items=_items;
        this.selectedColor=color;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listViewItem=null;
        if(view!=null)
        {
            listViewItem=view;
        }else
        {
            listViewItem=inflater.inflate(R.layout.list_view_item,null,false);
        }

        ViewHolder holder=(ViewHolder)listViewItem.getTag();
        if(holder==null)
        {
            holder=new ViewHolder();
            holder.textView=(TextView)listViewItem.findViewById(R.id.textView);
            holder.indiView=(TextView)listViewItem.findViewById(R.id.indiView);
            listViewItem.setTag(holder);
        }

        holder.textView.setText(items.get(i));
        if(i==selectedPosition)
        {
            holder.textView.setTextColor(selectedColor);
            holder.indiView.setTextColor(selectedColor);
            holder.indiView.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.textView.setTextColor(Color.BLACK);
            holder.indiView.setVisibility(View.GONE);
        }
        //holder.textView.setText(items.get(i));

        return listViewItem;
    }

    public void setSelectedItem(int pos)
    {
        selectedPosition=pos;
        notifyDataSetChanged();
    }

    public int getSelectedPosition()
    {
        return selectedPosition;
    }

    class ViewHolder
    {
        TextView textView;
        TextView indiView;
    }
}
