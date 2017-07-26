package com.lzw.weixin.pojo;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class Token {

    private int TokenId;

    private String accessToken;

    private int expiresIn;

    public int getTokenId() {
        return TokenId;
    }

    public void setTokenId(int tokenId) {
        TokenId = tokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
