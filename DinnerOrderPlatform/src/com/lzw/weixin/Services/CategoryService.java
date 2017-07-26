package com.lzw.weixin.Services;

import com.lzw.weixin.pojo.Category;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public interface CategoryService {
    public List<Category> findAllCategory();
    public Category findCategoryById(Integer id);
}
