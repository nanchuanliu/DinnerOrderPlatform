package com.lzw.order.dinnerorderapp.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopActivityFragment extends Fragment {

    List<ShopInfo.ShopActivity> activities;
    Context context;
    private boolean isHide = true;

    public ShopActivityFragment(Context context, List<ShopInfo.ShopActivity> activities) {
        this.context = context;
        this.activities = activities;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_activity, container, false);
        final LinearLayout lineActivities = (LinearLayout) view.findViewById(R.id.lineActivities);

        for (int i = 0; i < activities.size(); i++) {
            ShopInfo.ShopActivity act = activities.get(i);
            View control = inflater.inflate(R.layout.control_shopactivity, container, false);
            TextView tvIcon = (TextView) control.findViewById(R.id.tvActivityIcon);
            GradientDrawable drawable = (GradientDrawable) tvIcon.getBackground();
            tvIcon.setText(act.getIcon_name());
            drawable.setColor(Color.parseColor("#" + act.getIcon_color()));

            TextView tvTip = (TextView) control.findViewById(R.id.tvActivityTip);
            tvTip.setText(act.getTips());

            if (i >= 2)
                control.setVisibility(View.GONE);

            lineActivities.addView(control);
        }

        TextView tvActivityNum = (TextView) view.findViewById(R.id.tvActivityNum);
        tvActivityNum.setText(activities.size() + "个活动");

        if (activities.size() <= 2) {
            ImageButton btnShowHide = (ImageButton) view.findViewById(R.id.btnShowHide);
            btnShowHide.setVisibility(View.GONE);
        } else {
            LinearLayout lineShowHide = (LinearLayout) view.findViewById(R.id.lineShowHide);
            lineShowHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = lineActivities.getChildCount();
                    for (int i = 2; i < count; i++) {
                        View sub = lineActivities.getChildAt(i);
                        if (isHide) {
                            sub.setVisibility(View.VISIBLE);
                        } else {
                            sub.setVisibility(View.GONE);
                        }
                    }

                    isHide = !isHide;

                }
            });
        }

        return view;
    }

}
