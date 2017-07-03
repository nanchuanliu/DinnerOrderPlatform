package com.lzw.weixin.servlet;

import com.lzw.weixin.Services.TokenThread;
import com.lzw.weixin.Services.UserService;
import com.lzw.weixin.Utils.CommonUtil;
import com.lzw.weixin.Utils.TokenUtil;
import com.lzw.weixin.pojo.Oauth2Token;
import com.lzw.weixin.pojo.SNSUserInfo;
import com.lzw.weixin.pojo.Token;
import com.sun.glass.ui.TouchInputSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
@WebServlet(name = "OAuthServlet")
public class OAuthServlet extends HttpServlet {

    private static final long serialVersionUID=-1847238807216447030L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri=request.getParameter("url");
        request.setCharacterEncoding("GB2312");
        response.setCharacterEncoding("UTF-8");

        String code=request.getParameter("code");
        String state=request.getParameter("state");

        if(code!=null && !"authdeny".equals(code))
        {
            Oauth2Token token= UserService.getOauth2AccessToken(TokenThread.appID,TokenThread.appsecret,code);
            String accessToken=token.getAccessToken();
            String openId=token.getOpenId();
            SNSUserInfo info=UserService.getSNSUserInfo(accessToken,openId);

            request.setAttribute("snsUserInfo",info);
            request.setAttribute("state",state);

        }

        request.getRequestDispatcher(uri).forward(request,response);

    }

}
