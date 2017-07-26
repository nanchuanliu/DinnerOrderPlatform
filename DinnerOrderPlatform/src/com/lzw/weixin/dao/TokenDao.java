package com.lzw.weixin.dao;

import com.lzw.weixin.pojo.Token;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public interface TokenDao {
    public Token getToken();
    public void saveToken(Token token);
}
