package com.lzw.weixin.servlet;

import com.lzw.weixin.Services.CoreService;
import com.lzw.weixin.Utils.SignUtil;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/6/23.
 */
public class CoreServlet extends javax.servlet.http.HttpServlet {

    /**
     * 确认请求来自微信服务器
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        System.out.println("signature:"+signature);
        System.out.println("timestamp"+timestamp);
        System.out.println("nonce"+nonce);
        System.out.println("echostr"+echostr);

        PrintWriter out = response.getWriter();
        if(SignUtil.checkSignature(signature,timestamp,nonce))
        {
            out.print(echostr);
        }

        out.close();
        out=null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        request.setCharacterEncoding("GB2312");
        response.setCharacterEncoding("UTF-8");

        String respXml= CoreService.processRequest(request);

        PrintWriter out=response.getWriter();
        out.println(respXml);
        out.close();
        System.out.println("自动回复:");
        System.out.println(respXml);
    }

}
