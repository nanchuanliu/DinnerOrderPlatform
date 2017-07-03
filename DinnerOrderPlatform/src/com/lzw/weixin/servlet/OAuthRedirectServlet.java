package com.lzw.weixin.servlet;

import com.lzw.weixin.Services.TokenThread;
import com.lzw.weixin.Utils.CommonUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 * Author: lzw
 * Date:  2017/7/1.
 */
@WebServlet(name = "OAuthRedirectServlet")
public class OAuthRedirectServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String domain = CommonUtil.getRequestDomain(request);

        String url=request.getParameter("url");
        String dirUrl=domain+"oauth?url="+url;
        String oauthUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        dirUrl= CommonUtil.urlEncodeUTF8(dirUrl);
        oauthUrl=String.format(oauthUrl, TokenThread.appID,dirUrl);

        response.sendRedirect(oauthUrl);
    }

}
