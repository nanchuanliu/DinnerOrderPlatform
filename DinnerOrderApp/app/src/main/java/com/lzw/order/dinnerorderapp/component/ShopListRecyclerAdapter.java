package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.fragment.HomeFragment;
import com.lzw.order.dinnerorderapp.fragment.ShopActivityFragment;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/7/8.
 */

public class ShopListRecyclerAdapter extends RecyclerView.Adapter<ShopListRecyclerAdapter.ShopListViewHolder> {
    private List<ShopInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private FragmentManager manager;
    private static int randomId=100000;

    public ShopListRecyclerAdapter(FragmentManager fm,Context context, List<ShopInfo> datas) {
        this.manager=fm;
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ShopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.control_shopitem, parent, false);
        ShopListViewHolder holder = new ShopListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShopListViewHolder holder, int position) {
        ShopInfo info = mDatas.get(position);
        String imagePath = info.getImage_path();
        String shopIconUrl = UrlUtil.getImageUrlFromPath(UrlUtil.SHOP_URL, imagePath, true);
        Picasso.with(mContext).load(shopIconUrl).into(holder.imgShopIcon);

        if (info.is_premium())
            holder.tvIsPremium.setVisibility(VISIBLE);

        holder.tvShopName.setText(info.getName());

        Resources resource = mContext.getResources();
        Drawable drawable = resource.getDrawable(R.drawable.txt_shape);

        for (ShopInfo.Support support :
                info.getSupports()) {
            if ("准".equals(support.getIcon_name())) {
                holder.tvDeliveryTime.setVisibility(VISIBLE);
                holder.tvDeliveryTime.setText(support.getName());
                holder.tvDeliveryTime.setTextColor(Color.parseColor("#" + support.getIcon_color()));
            }

            TextView tvSupport = new TextView(mContext);
            tvSupport.setTextSize(10);
            tvSupport.setText(support.getIcon_name());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(3, 3, 3, 3);
            tvSupport.setPadding(8, 0, 8, 2);
            tvSupport.setBackgroundDrawable(drawable);
            tvSupport.setLayoutParams(params);
            holder.lineSupports.addView(tvSupport);
        }
        holder.rbRating.setRating(info.getRating());
        //动态修改RatingBar的星星颜色，解决progressTint设置无效
        LayerDrawable ld_stars=(LayerDrawable)holder.rbRating.getProgressDrawable();
        ld_stars.getDrawable(2).setColorFilter(Color.parseColor("#ffaa0c"), PorterDuff.Mode.SRC_ATOP);

        holder.tvRating.setText(String.valueOf(info.getRating()));
        holder.tvMonthSales.setText("月售" + String.valueOf(info.getRecent_order_num()) + "单");
        holder.tvMinOrderAmount.setText("¥" + String.valueOf(info.getFloat_minimum_order_amount()) + "起送");
        holder.tvDeliveryFee.setText("配送费¥" + String.valueOf(info.getFloat_delivery_fee()));
        if (info.getAverage_cost() == null) {
            holder.tvCostDivide.setVisibility(View.GONE);
            holder.tvAverageCost.setVisibility(View.GONE);
        } else {
            holder.tvAverageCost.setText(String.valueOf(info.getAverage_cost()));
        }

        ShopInfo.DeliveryMode mode = info.getDelivery_mode();
        if (mode != null) {

            //动态设置渐变色
            GradientDrawable background = (GradientDrawable) holder.tvDeliveryMode.getBackground();
            int startColor = Color.parseColor("#" + mode.getGradient().getRgb_from());
            int color = Color.parseColor("#" + mode.getColor());
            int endColor = Color.parseColor("#" + mode.getGradient().getRgb_to());
            int[] colors = {startColor, color, endColor};
            background.setColors(colors);

            holder.tvDeliveryMode.setText(mode.getText());
            holder.tvDeliveryMode.setVisibility(VISIBLE);
        }

        DecimalFormat format = new DecimalFormat("0.00");
        float distance = Float.parseFloat(info.getDistance());
        String formatDist = "";
        if (distance >= 1000) {
            formatDist = format.format(distance / 1000) + "km";
        } else {
            formatDist = info.getDistance() + "m";
        }
        holder.tvDistance.setText(formatDist);

        holder.tvOrderLeadTime.setText(info.getOrder_lead_time() + "分钟");
        //holder.tvActivityNum.setText(String.valueOf(info.getActivities().size()));

/*        BaseAdapter adapter=new ShopActivityAdapter(mContext,info.getActivities());
        holder.lvActivities.setAdapter(adapter);*/

        ShopActivityFragment activityFrag = new ShopActivityFragment(mContext,info.getActivities());
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(holder.fragmentActivities.getId(), activityFrag, "ActivitiesFragment");
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ShopListViewHolder extends ViewHolder {
        ImageView imgShopIcon;
        TextView tvIsPremium;
        TextView tvShopName;
        LinearLayout lineSupports;
        RatingBar rbRating;
        TextView tvRating;
        TextView tvMonthSales;
        TextView tvDeliveryTime;
        TextView tvDeliveryMode;
        TextView tvMinOrderAmount;
        TextView tvDeliveryFee;
        TextView tvCostDivide;
        TextView tvAverageCost;
        TextView tvDistance;
        TextView tvOrderLeadTime;
        FrameLayout fragmentActivities;
        TextView tvActivityNum;

        public ShopListViewHolder(View view) {
            super(view);
            imgShopIcon = (ImageView) view.findViewById(R.id.imgShopIcon);
            tvIsPremium = (TextView) view.findViewById(R.id.tvIsPremium);
            tvShopName = (TextView) view.findViewById(R.id.tvShopName);
            lineSupports = (LinearLayout) view.findViewById(R.id.lineSupports);
            rbRating = (RatingBar) view.findViewById(R.id.rbRating);
            tvRating = (TextView) view.findViewById(R.id.tvRating);
            tvMonthSales = (TextView) view.findViewById(R.id.tvMonthSales);
            tvMinOrderAmount = (TextView) view.findViewById(R.id.tvMinOrderAmount);
            tvDeliveryFee = (TextView) view.findViewById(R.id.tvDeliveryFee);
            tvCostDivide = (TextView) view.findViewById(R.id.tvCostDivide);
            tvAverageCost = (TextView) view.findViewById(R.id.tvAverageCost);
            tvDeliveryTime = (TextView) view.findViewById(R.id.tvDeliveryTime);
            tvDeliveryMode = (TextView) view.findViewById(R.id.tvDeliveryMode);
            tvDistance = (TextView) view.findViewById(R.id.tvDistance);
            tvOrderLeadTime = (TextView) view.findViewById(R.id.tvOrderLeadTime);
            fragmentActivities = (FrameLayout) view.findViewById(R.id.fragment_activities);
            fragmentActivities.setId(++randomId);
            tvActivityNum = (TextView) view.findViewById(R.id.tvActivityNum);

        }
    }
}
