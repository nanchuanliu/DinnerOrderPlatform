package com.lzw.weixin.dao.impl;

import com.lzw.weixin.dao.CategoryDao;
import com.lzw.weixin.pojo.Category;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class CategoryDaoImpl implements CategoryDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> findAllCategory() {
        String hql="from Category category order by category.categoryId";
        return (List<Category>)sessionFactory.openSession().createQuery(hql).list();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return (Category)sessionFactory.openSession().get(Category.class,id);
    }
}
