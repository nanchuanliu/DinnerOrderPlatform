package com.lzw.weixin.dao;

import com.lzw.weixin.pojo.Category;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public interface CategoryDao {
    public List<Category> findAllCategory();
    public Category findCategoryById(Integer id);
}
