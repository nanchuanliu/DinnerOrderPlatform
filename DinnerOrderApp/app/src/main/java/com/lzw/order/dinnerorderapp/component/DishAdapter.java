package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.Category;
import com.lzw.order.dinnerorderapp.Bean.Food;
import com.lzw.order.dinnerorderapp.DinnerOrderActivity;
import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.utils.DisplayUtil;
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

public class DishAdapter extends RecyclerView.Adapter<ViewHolder> implements StickyRecyclerHeadersAdapter<ViewHolder> {

    private Context context;
    private List<Category> list;
    private List<Food> foods = new ArrayList<>();
    private LayoutInflater inflater;


    public DishAdapter(Context _context, List<Category> _list) {
        this.context = _context;
        this.list = _list;
        inflater = LayoutInflater.from(_context);

        for (Category cat :
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
        final DishViewHolder holder = (DishViewHolder) _holder;
        final Food food = foods.get(position);
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

        holder.tvMonthSales.setText("月售" + food.getMonth_sales() + "份");
        holder.tvSatisfyRate.setText("好评率" + food.getSatisfy_rate() + "%");
        holder.tvPrice.setText("¥" + food.getSpecfoods().get(0).getPrice());

        holder.imgDishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer num = Integer.parseInt(holder.tvCount.getText().toString());
                if (num++ == 0) {
                    holder.tvCount.setVisibility(View.VISIBLE);
                    holder.imgDishSub.setVisibility(View.VISIBLE);
                }

                holder.tvCount.setText(num.toString());
                int[] startLocation = new int[2];
                holder.imgDishAdd.getLocationInWindow(startLocation);
/*
                startLocation[0]= DisplayUtil.px2dip(context,startLocation[0]);
                startLocation[1]= DisplayUtil.px2dip(context,startLocation[1]);
*/

                //holder.imgDishAdd.getLocationOnScreen(startLocation);
                ImageView animView = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
                animView.setLayoutParams(params);
                animView.setImageResource(R.drawable.ic_si_glyph_billiard_ball);
                DinnerOrderActivity activity = (DinnerOrderActivity) context;
                activity.setAnim(animView, startLocation);

                activity.refreshCategorySelectedCount(food.getCategory_id(), 1);
            }
        });

        holder.imgDishSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer num = Integer.parseInt(holder.tvCount.getText().toString());
                if (--num == 0) {
                    holder.tvCount.setVisibility(View.GONE);
                    holder.imgDishSub.setVisibility(View.GONE);
                }

                holder.tvCount.setText(num.toString());
                ((DinnerOrderActivity) context).refreshCategorySelectedCount(food.getCategory_id(), -1);
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        return getSortType(position);
    }

    public int getSortType(int position) {
        int sort = -1;
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (position >= sum)
                sort++;
            else
                return sort;

            sum += list.get(i).getFoods().size();
        }
        return sort;
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dish_header, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {
        View view = holder.itemView;
        TextView tvHeaderCat = (TextView) view.findViewById(R.id.tvHeaderCat);
        TextView tvHeaderDesp = (TextView) view.findViewById(R.id.tvHeaderDesp);

        Category cate = list.get(getSortType(position));
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
        ImageView imgDishAdd;
        TextView tvCount;
        ImageView imgDishSub;

        public DishViewHolder(View view) {
            super(view);

            imgDishIcon = (ImageView) view.findViewById(R.id.imgDishIcon);
            tvDishName = (TextView) view.findViewById(R.id.tvDishName);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            tvMonthSales = (TextView) view.findViewById(R.id.tvMonthSales);
            tvSatisfyRate = (TextView) view.findViewById(R.id.tvSatisfyRate);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            imgDishAdd = (ImageView) view.findViewById(R.id.imgDishAdd);
            tvCount = (TextView) view.findViewById(R.id.tvCount);
            imgDishSub = (ImageView) view.findViewById(R.id.imgDishSub);
        }
    }
}



