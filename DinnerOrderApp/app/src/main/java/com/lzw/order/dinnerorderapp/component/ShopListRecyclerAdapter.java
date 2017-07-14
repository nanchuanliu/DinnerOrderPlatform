package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.transition.Visibility;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/7/8.
 */

public class ShopListRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ShopInfo> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    Drawable drawable;
    Resources resource;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public ShopListRecyclerAdapter(Context context) {

        this.mContext = context;

        resource = mContext.getResources();
        drawable = resource.getDrawable(R.drawable.txt_shape);

        inflater = LayoutInflater.from(context);
    }

    public void addDatas(List<ShopInfo> datas, int pos) {
        this.mDatas.addAll(datas);
        this.notifyItemRangeInserted(pos, datas.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.control_shopitem, null, false);
            ShopListViewHolder holder = new ShopListViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.control_footerview, null);
            view.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //动态修改RatingBar的星星颜色，解决progressTint设置无效
/*            ProgressBar bar=(ProgressBar)view.findViewById(R.id.pbLoadMore);
            LayerDrawable ldBar = (LayerDrawable) bar.getProgressDrawable();
            ldBar.getDrawable(2).setColorFilter(resource.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);*/
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, int position) {
        if (_holder instanceof ShopListViewHolder) {
            final ShopListViewHolder holder = (ShopListViewHolder) _holder;
            ShopInfo info = mDatas.get(position);
            String imagePath = info.getImage_path();
            String shopIconUrl = UrlUtil.getImageUrlFromPath(UrlUtil.SHOP_URL, imagePath, true);
            Picasso.with(mContext).load(shopIconUrl).into(holder.imgShopIcon);

            if (info.is_premium())
                holder.tvIsPremium.setVisibility(VISIBLE);

            holder.tvShopName.setText(info.getName());
            holder.lineSupports.removeAllViews();
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
            LayerDrawable ld_stars = (LayerDrawable) holder.rbRating.getProgressDrawable();
            //ld_stars.getDrawable(0).clearColorFilter();
            ld_stars.getDrawable(1).setColorFilter(Color.parseColor("#dddddd"), PorterDuff.Mode.SRC_ATOP);
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

/*            try {
                ShopActivityFragment activityFrag = new ShopActivityFragment(mContext, info.getActivities());
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(holder.fragmentActivities.getId(), activityFrag, "ActivitiesFragment");
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            List<ShopInfo.ShopActivity> activities = info.getActivities();
            holder.lineActivities.removeAllViews();
            if (activities == null) {
                holder.lineActivities.setVisibility(GONE);
                holder.lineShowHide.setVisibility(GONE);
            } else {
                holder.lineActivities.setVisibility(VISIBLE);
                holder.lineShowHide.setVisibility(VISIBLE);
                for (int i = 0; i < activities.size(); i++) {
                    ShopInfo.ShopActivity act = activities.get(i);
                    View control = inflater.inflate(R.layout.control_shopactivity, null, false);
                    TextView tvIcon = (TextView) control.findViewById(R.id.tvActivityIcon);
                    GradientDrawable drawable = (GradientDrawable) tvIcon.getBackground();
                    tvIcon.setText(act.getIcon_name());
                    drawable.setColor(Color.parseColor("#" + act.getIcon_color()));

                    TextView tvTip = (TextView) control.findViewById(R.id.tvActivityTip);
                    tvTip.setText(act.getTips());

                    if (i >= 2)
                        control.setVisibility(View.GONE);
                    else
                        control.setVisibility(View.VISIBLE);
                    holder.lineActivities.addView(control);
                }

                holder.tvActivityNum.setText(activities.size() + "个活动");

                if (activities.size() <= 2) {
                    holder.btnShowHide.setVisibility(View.GONE);
                } else {
                    holder.btnShowHide.setVisibility(View.VISIBLE);
                    holder.lineShowHide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int count = holder.lineActivities.getChildCount();
                            int visibility = VISIBLE;
                            for (int i = 2; i < count; i++) {
                                View sub = holder.lineActivities.getChildAt(i);
                                if (i == 2)
                                    visibility = sub.getVisibility();
                                if (visibility == GONE) {
                                    sub.setVisibility(View.VISIBLE);
                                } else {
                                    sub.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
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
        LinearLayout lineActivities;
        TextView tvActivityNum;
        ImageButton btnShowHide;
        LinearLayout lineShowHide;

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
/*            fragmentActivities = (FrameLayout) view.findViewById(R.id.fragment_activities);
            fragmentActivities.setId(++randomId);*/
            lineActivities = (LinearLayout) view.findViewById(R.id.lineActivities);
            tvActivityNum = (TextView) view.findViewById(R.id.tvActivityNum);
            btnShowHide = (ImageButton) view.findViewById(R.id.btnShowHide);
            lineShowHide = (LinearLayout) view.findViewById(R.id.lineShowHide);
        }
    }

    class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }
}
