package com.lzw.weixin.pojo;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class Dish {
    private Integer DishId;
    private String DishName;
    private String DishPrice;
    private String DishImg;
    private Category category;

    public Integer getDishId() {
        return DishId;
    }

    public void setDishId(Integer dishId) {
        DishId = dishId;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(String dishPrice) {
        DishPrice = dishPrice;
    }

    public String getDishImg() {
        return DishImg;
    }

    public void setDishImg(String dishImg) {
        DishImg = dishImg;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
