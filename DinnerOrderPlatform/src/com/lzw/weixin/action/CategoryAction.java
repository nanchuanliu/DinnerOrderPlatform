package com.lzw.weixin.action;

import com.lzw.weixin.Services.CategoryService;
import com.lzw.weixin.pojo.Category;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class CategoryAction extends ActionSupport {
    private CategoryService categoryService;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    private List<Category> categories;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String execute() throws Exception {
         return super.execute();
    }

    public String getCategoryList()
    {
        categories =categoryService.findAllCategory();
        return SUCCESS;
    }

}
