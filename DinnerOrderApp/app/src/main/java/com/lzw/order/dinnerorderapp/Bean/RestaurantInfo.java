package com.lzw.order.dinnerorderapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LZW on 2017/07/13.
 */

public class RestaurantInfo {
    @SerializedName("0")
    private RestaurantFilter Root;

    public class RestaurantFilter
    {
        @SerializedName("restaurant_with_foods")
        private List<RestaurantWithFoods> restaurantWithFoods;

        public List<RestaurantWithFoods> getRestaurantWithFoods() {
            return restaurantWithFoods;
        }

        public void setRestaurantWithFoods(List<RestaurantWithFoods> restaurantWithFoods) {
            this.restaurantWithFoods = restaurantWithFoods;
        }
    }

    public class RestaurantWithFoods
    {
        private List<Food> foods;
        private ShopInfo restaurant;

        public List<Food> getFoods() {
            return foods;
        }

        public void setFoods(List<Food> foods) {
            this.foods = foods;
        }

        public ShopInfo getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(ShopInfo restaurant) {
            this.restaurant = restaurant;
        }
    }

    public class Food
    {

    }

    public RestaurantFilter getRoot() {
        return Root;
    }

    public void setRoot(RestaurantFilter root) {
        Root = root;
    }
}
