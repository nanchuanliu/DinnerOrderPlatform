package com.lzw.order.dinnerorderapp.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public class ShopInfo {
    private String image_path;
    private List<ShopActivity> activities;
    private boolean is_premium;
    private String name;
    private List<Support> supports;
    private float rating;
    private int recent_order_num;
    private int float_minimum_order_amount;
    private String float_delivery_fee;
    private String average_cost;
    private DeliveryMode delivery_mode;
    private String distance;
    private String order_lead_time;
    private String id;

    public class ShopActivity {
        private String tips;
        private String icon_name;
        private String icon_color;

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getIcon_name() {
            return icon_name;
        }

        public void setIcon_name(String icon_name) {
            this.icon_name = icon_name;
        }

        public String getIcon_color() {
            return icon_color;
        }

        public void setIcon_color(String icon_color) {
            this.icon_color = icon_color;
        }
    }

    public class Support {
        private String icon_name;
        private String icon_color;
        private String name;

        public String getIcon_name() {
            return icon_name;
        }

        public void setIcon_name(String icon_name) {
            this.icon_name = icon_name;
        }

        public String getIcon_color() {
            return icon_color;
        }

        public void setIcon_color(String icon_color) {
            this.icon_color = icon_color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class  DeliveryMode
    {
        private boolean is_solid;
        private String text;
        private String color;
        private Gradient gradient;

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

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Gradient getGradient() {
            return gradient;
        }

        public void setGradient(Gradient gradient) {
            this.gradient = gradient;
        }

        public class Gradient
        {
            private String rgb_from;
            private String rgb_to;

            public String getRgb_from() {
                return rgb_from;
            }

            public void setRgb_from(String rgb_from) {
                this.rgb_from = rgb_from;
            }

            public String getRgb_to() {
                return rgb_to;
            }

            public void setRgb_to(String rgb_to) {
                this.rgb_to = rgb_to;
            }
        }
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public List<ShopActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<ShopActivity> activities) {
        this.activities = activities;
    }

    public boolean is_premium() {
        return is_premium;
    }

    public void setIs_premium(boolean is_premium) {
        this.is_premium = is_premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Support> getSupports() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRecent_order_num() {
        return recent_order_num;
    }

    public void setRecent_order_num(int recent_order_num) {
        this.recent_order_num = recent_order_num;
    }

    public int getFloat_minimum_order_amount() {
        return float_minimum_order_amount;
    }

    public void setFloat_minimum_order_amount(int float_minimum_order_amount) {
        this.float_minimum_order_amount = float_minimum_order_amount;
    }

    public String getFloat_delivery_fee() {
        return float_delivery_fee;
    }

    public void setFloat_delivery_fee(String float_delivery_fee) {
        this.float_delivery_fee = float_delivery_fee;
    }

    public String getAverage_cost() {
        return average_cost;
    }

    public void setAverage_cost(String average_cost) {
        this.average_cost = average_cost;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOrder_lead_time() {
        return order_lead_time;
    }

    public void setOrder_lead_time(String order_lead_time) {
        this.order_lead_time = order_lead_time;
    }

    public DeliveryMode getDelivery_mode() {
        return delivery_mode;
    }

    public void setDelivery_mode(DeliveryMode delivery_mode) {
        this.delivery_mode = delivery_mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
