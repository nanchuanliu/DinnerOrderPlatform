package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.Category;
import com.lzw.order.dinnerorderapp.Bean.Food;
import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZW on 2017/07/19.
 */

public class DishAdapter extends RecyclerView.Adapter<ViewHolder> implements StickyRecyclerHeadersAdapter<ViewHolder>{

    private Context context;
    private List<Category> list;
    private List<Food> foods=new ArrayList<>();
    private LayoutInflater inflater;


    public DishAdapter(Context _context, List<Category> _list) {
        this.context = _context;
        this.list = _list;
        inflater = LayoutInflater.from(_context);

        for (Category cat:
             _list) {
            foods.addAll(cat.getFoods());
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_dish, null, false);
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        DishViewHolder holder = new DishViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, int position) {
        DishViewHolder holder = (DishViewHolder) _holder;
        Food food = foods.get(position);
        String imagePath = food.getImage_path();
        String imageUrl = UrlUtil.getImageUrlFromPath(UrlUtil.DISH_URL, imagePath, true);
        Picasso.with(context).load(imageUrl).into(holder.imgDishIcon);

        holder.tvDishName.setText(food.getName());
        String description = food.getDescription();
        if (description == null || description.isEmpty()) {
            holder.tvDescription.setVisibility(View.GONE);
        } else {
            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.tvDescription.setText(food.getDescription());
        }

        holder.tvMonthSales.setText("月售"+food.getMonth_sales()+"份");
        holder.tvSatisfyRate.setText("好评率"+food.getSatisfy_rate()+"%");
        holder.tvPrice.setText("¥"+food.getSpecfoods().get(0).getPrice());

    }

    @Override
    public long getHeaderId(int position) {
        return getSortType(position);
    }

    public int getSortType(int position)
    {
        int sort=-1;
        int sum=0;
        for (int i = 0; i < list.size(); i++) {
            if(position>=sum)
                sort++;
            else
                return sort;

            sum+=list.get(i).getFoods().size();
        }
        return sort;
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dish_header,parent,false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {
        View view=holder.itemView;
        TextView tvHeaderCat=(TextView)view.findViewById(R.id.tvHeaderCat);
        TextView tvHeaderDesp=(TextView)view.findViewById(R.id.tvHeaderDesp);

        Category cate=list.get(getSortType(position));
        tvHeaderCat.setText(cate.getName());
        tvHeaderDesp.setText(cate.getDescription());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class DishViewHolder extends ViewHolder {
        ImageView imgDishIcon;
        TextView tvDishName;
        TextView tvDescription;
        TextView tvMonthSales;
        TextView tvSatisfyRate;
        TextView tvPrice;
        ImageView imgOrder;

        public DishViewHolder(View view) {
            super(view);

            imgDishIcon = (ImageView) view.findViewById(R.id.imgDishIcon);
            tvDishName = (TextView) view.findViewById(R.id.tvDishName);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            tvMonthSales = (TextView) view.findViewById(R.id.tvMonthSales);
            tvSatisfyRate = (TextView) view.findViewById(R.id.tvSatisfyRate);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            imgOrder = (ImageView) view.findViewById(R.id.imgOrder);
        }
    }
}



