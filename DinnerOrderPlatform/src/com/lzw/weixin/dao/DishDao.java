package com.lzw.weixin.dao;

import com.lzw.weixin.pojo.Dish;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public interface DishDao {
    public List<Dish> findDishListByCategory(Integer categoryId);
}
