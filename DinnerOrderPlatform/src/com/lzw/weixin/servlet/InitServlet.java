package com.lzw.weixin.servlet;

import com.lzw.weixin.Services.TokenThread;
import com.lzw.weixin.Utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
@WebServlet(name = "InitServlet")
public class InitServlet extends HttpServlet {

    private static final long serialVersionUID=1L;
    private static Logger log= LoggerFactory.getLogger(InitServlet.class);
    public static String baiduApiKey="";

    public void init()
    {
        TokenThread.appID=getInitParameter("appID");
        TokenThread.appsecret=getInitParameter("appsecret");

        baiduApiKey=getInitParameter("BaiduApiKey");

        if("".equals(TokenThread.appID) || "".equals(TokenThread.appsecret))
        {
            log.error("appID and appsecret configuration error,please check carefully.");
        }else
        {
            new Thread(new TokenThread()).start();
        }
    }


}
