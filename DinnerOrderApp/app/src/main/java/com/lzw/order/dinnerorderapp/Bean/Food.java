package com.lzw.order.dinnerorderapp.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class Food {
    private String category_id;
    private String description;
    private String image_path;
    private String item_id;
    private int month_sales;
    private String name;
    private String restaurant_id;
    private double rating;
    private int satisfy_count;
    private int satisfy_rate;
    private String tips;
    private List<SpecFood> specfoods;
    private String category_type;

    public class SpecFood
    {
        private String food_id;
        private String item_id;
        private String name;
        private String price;

        public String getFood_id() {
            return food_id;
        }

        public void setFood_id(String food_id) {
            this.food_id = food_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getMonth_sales() {
        return month_sales;
    }

    public void setMonth_sales(int month_sales) {
        this.month_sales = month_sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getSatisfy_count() {
        return satisfy_count;
    }

    public void setSatisfy_count(int satisfy_count) {
        this.satisfy_count = satisfy_count;
    }

    public int getSatisfy_rate() {
        return satisfy_rate;
    }

    public void setSatisfy_rate(int satisfy_rate) {
        this.satisfy_rate = satisfy_rate;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public List<SpecFood> getSpecfoods() {
        return specfoods;
    }

    public void setSpecfoods(List<SpecFood> specfoods) {
        this.specfoods = specfoods;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }
}
