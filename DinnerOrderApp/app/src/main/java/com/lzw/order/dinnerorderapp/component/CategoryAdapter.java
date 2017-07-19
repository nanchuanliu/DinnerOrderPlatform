package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.Category;
import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LZW on 2017/07/19.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> list;
    private LayoutInflater inflater;

    public CategoryAdapter(Context _context, List<Category> _list) {
        this.inflater = LayoutInflater.from(_context);
        this.context = _context;
        this.list = _list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_category,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Category category = list.get(position);

        String url = category.getIcon_url();
        if (url != null && !url.isEmpty()) {
            url = UrlUtil.getImageUrlFromPath(UrlUtil.MENU_URL, category.getIcon_url(), true);
            Picasso.with(context).load(url).into(holder.imgIcon);
            holder.imgIcon.setVisibility(View.VISIBLE);
        } else {
            holder.imgIcon.setVisibility(View.GONE);
        }

        holder.lineCategory.setSelected(category.isSelected());
        holder.tvCategory.setText(category.getName());
        holder.lineCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener!=null)
                    itemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   /* @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listViewItem = null;
        if (view != null) {
            listViewItem = view;
        } else {
            listViewItem = inflater.inflate(R.layout.list_category, null, false);
        }

        ViewHolder holder = (ViewHolder) listViewItem.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.imgIcon = (ImageView) listViewItem.findViewById(R.id.imgIcon);
            holder.tvCategory = (TextView) listViewItem.findViewById(R.id.tvCategory);
            listViewItem.setTag(holder);
        }

        Category category = (Category) getItem(i);
        String url = category.getIcon_url();
        if (url != null && !url.isEmpty()) {
            url = UrlUtil.getImageUrlFromPath(UrlUtil.MENU_URL, category.getIcon_url(), true);
            Picasso.with(context).load(url).into(holder.imgIcon);
            holder.imgIcon.setVisibility(View.VISIBLE);
        } else {
            holder.imgIcon.setVisibility(View.GONE);
        }

        holder.tvCategory.setText(category.getName());

        return listViewItem;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout lineCategory;
        ImageView imgIcon;
        TextView tvCategory;

        public ViewHolder(View view)
        {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            tvCategory = (TextView) view.findViewById(R.id.tvCategory);
            lineCategory=(LinearLayout)view.findViewById(R.id.lineCategory);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.itemClickListener=onItemClickListener;
    }
}
