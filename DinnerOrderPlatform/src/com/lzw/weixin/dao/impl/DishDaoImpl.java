package com.lzw.weixin.dao.impl;

import com.lzw.weixin.dao.DishDao;
import com.lzw.weixin.pojo.Dish;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class DishDaoImpl implements DishDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Dish> findDishListByCategory(Integer categoryId) {
        String hql="from Dish dish where CategoryId="+categoryId;
        return (List<Dish>)sessionFactory.openSession().createQuery(hql).list();
    }
}
