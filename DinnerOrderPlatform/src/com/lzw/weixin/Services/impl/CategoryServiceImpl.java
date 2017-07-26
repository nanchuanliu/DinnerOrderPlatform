package com.lzw.weixin.Services.impl;

import com.lzw.weixin.Services.CategoryService;
import com.lzw.weixin.dao.CategoryDao;
import com.lzw.weixin.pojo.Category;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao;

    public CategoryDao getDao() {
        return dao;
    }

    public void setDao(CategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> findAllCategory() {
        return dao.findAllCategory();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return dao.findCategoryById(id);
    }
}
