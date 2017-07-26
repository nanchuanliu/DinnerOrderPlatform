package com.lzw.weixin.Services;

import com.lzw.weixin.pojo.Dish;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public interface DishService {
    public List<Dish> findDishListByCategory(Integer categoryId);
}
