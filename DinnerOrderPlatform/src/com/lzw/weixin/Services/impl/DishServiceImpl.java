package com.lzw.weixin.Services.impl;

import com.lzw.weixin.Services.DishService;
import com.lzw.weixin.dao.CategoryDao;
import com.lzw.weixin.dao.DishDao;
import com.lzw.weixin.pojo.Dish;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class DishServiceImpl implements DishService {

    private DishDao dao;

    public void setDao(DishDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Dish> findDishListByCategory(Integer categoryId) {
        return dao.findDishListByCategory(categoryId);
    }
}
