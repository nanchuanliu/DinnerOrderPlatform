package com.lzw.weixin.Services;

import com.lzw.weixin.Utils.CommonUtil;
import com.lzw.weixin.Utils.TokenUtil;
import com.lzw.weixin.pojo.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class TokenThread implements Runnable {

    private static Logger log= LoggerFactory.getLogger(TokenThread.class);

    public static String appID="";
    public static String appsecret="";
    public static Token accessToken=null;

    @Override
    public void run() {
            while (true)
            {
                try {
                    accessToken= CommonUtil.getToken(appID,appsecret);
                    if(null!=accessToken)
                    {
                        TokenUtil.saveToken(accessToken);
                        log.info("获取access_token成功，有效时长{}秒 token:{}",accessToken.getExpiresIn(),accessToken.getAccessToken());
                        Thread.sleep((accessToken.getExpiresIn()-200)*1000);
                    }
                    else
                    {
                        Thread.sleep(60*1000);
                    }
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(60*1000);
                    } catch (InterruptedException e1) {
                        log.error("{}",e1);
                    }
                    log.error("{}",e);
                }
            }
    }
}
