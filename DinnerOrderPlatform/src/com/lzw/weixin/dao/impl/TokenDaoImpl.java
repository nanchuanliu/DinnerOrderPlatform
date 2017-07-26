package com.lzw.weixin.dao.impl;

import com.lzw.weixin.dao.TokenDao;
import com.lzw.weixin.pojo.Token;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class TokenDaoImpl implements TokenDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Token getToken() {
        String hql="from Token token order by createTime desc ";
        Query query=sessionFactory.openSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(1);

        return (Token) query.list().get(0);
    }

    @Override
    public void saveToken(Token token) {
        sessionFactory.openSession().save(token);
    }
}
