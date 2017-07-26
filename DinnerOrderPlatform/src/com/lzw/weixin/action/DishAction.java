package com.lzw.weixin.action;

import com.lzw.weixin.Services.DishService;
import com.lzw.weixin.pojo.Dish;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class DishAction extends ActionSupport {
    private DishService dishService;
    private Integer categoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    private List<Dish> dishes;
    private JSONArray datas;
    private String dish;

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getDishesByCategory()
    {
        dishes=dishService.findDishListByCategory(categoryId);
        datas= JSONArray.fromObject(dishes);
        dish= JSONObject.fromObject(dishes.get(0)).toString();
        return SUCCESS;
    }
}
