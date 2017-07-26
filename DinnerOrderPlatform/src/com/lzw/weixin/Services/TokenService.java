package com.lzw.weixin.Services;

import com.lzw.weixin.pojo.Token;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/24.
 */
public interface TokenService {
    public Token getToken();
    public void saveToken(Token token);
}
