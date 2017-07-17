package com.lzw.order.dinnerorderapp.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.BaseAttribute;
import com.lzw.order.dinnerorderapp.R;

/**
 * Created by Administrator on 2017/7/15.
 */

public class CustomSwitchButton extends LinearLayout {

    private BaseAttribute attr = new BaseAttribute();
    private int selectedColor = getResources().getColor(R.color.colorPrimary);
    private int contentColor = getResources().getColor(R.color.colorContent);
    private TextView tvIcon;
    private TextView tvName;
    private LinearLayout lineItem;
    private boolean isSelected;

    public CustomSwitchButton(Context context) {
        super(context);
    }

    public CustomSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        //initView(context);

/*        tvIcon.setText(attrs.getIcon_name());
        tvIcon.setTextColor(Color.parseColor(attrs.getIcon_color()));
        tvName.setText(attrs.getName());*/
    }

    public CustomSwitchButton(Context context, BaseAttribute attr, int selectedColor) {
        super(context);
        this.attr = attr;
        this.selectedColor = selectedColor;

        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.control_switch, this);
        tvIcon = (TextView) findViewById(R.id.tvIcon);
        tvName = (TextView) findViewById(R.id.tvName);
        lineItem = (LinearLayout) findViewById(R.id.lineItem);

        tvIcon.setText(attr.getIcon_name());
        tvIcon.setBackgroundColor(Color.parseColor("#" + attr.getIcon_color()));
        tvName.setText(attr.getName());

        lineItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelected = !isSelected;
                refreshSelectedState(isSelected);
            }
        });
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        refreshSelectedState(selected);
    }

    private void refreshSelectedState(boolean state) {
        if (state) {
            tvName.setTextColor(selectedColor);
            tvIcon.setTextColor(selectedColor);
            tvIcon.setBackgroundColor(Color.TRANSPARENT);
            tvIcon.setText("√");
        } else {
            tvName.setTextColor(contentColor);
            tvIcon.setBackgroundColor(Color.parseColor("#" + attr.getIcon_color()));
            tvIcon.setTextColor(Color.WHITE);
            tvIcon.setText(attr.getIcon_name());
        }
    }

    public boolean IsSelected() {
        return isSelected;
    }

    public String getAttrId()
    {
        return attr.getId();
    }
}
