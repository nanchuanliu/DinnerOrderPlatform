package com.lzw.order.dinnerorderapp.Bean;

/**
 * Created by Administrator on 2017/7/15.
 */

public class ActivityAttribute extends BaseAttribute{
    private String color;
    private ShopInfo.DeliveryMode.Gradient gradient;
    private boolean is_solid;
    private String text;

    @Override
    public String getIcon_color() {
        return color;
    }

    @Override
    public String getName() {
        return this.text;
    }

    @Override
    public String getIcon_name() {
        return text.substring(0,1);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ShopInfo.DeliveryMode.Gradient getGradient() {
        return gradient;
    }

    public void setGradient(ShopInfo.DeliveryMode.Gradient gradient) {
        this.gradient = gradient;
    }

    public boolean is_solid() {
        return is_solid;
    }

    public void setIs_solid(boolean is_solid) {
        this.is_solid = is_solid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
