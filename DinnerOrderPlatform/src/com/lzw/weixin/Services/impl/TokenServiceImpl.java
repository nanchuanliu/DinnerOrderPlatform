package com.lzw.weixin.Services.impl;

import com.lzw.weixin.Services.TokenService;
import com.lzw.weixin.dao.TokenDao;
import com.lzw.weixin.pojo.Token;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public class TokenServiceImpl implements TokenService {

    private TokenDao tokenDao;

    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public Token getToken() {
        return tokenDao.getToken();
    }

    @Override
    public void saveToken(Token token) {
        tokenDao.saveToken(token);
    }
}
