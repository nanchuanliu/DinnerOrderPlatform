package com.lzw.weixin.main;

import com.lzw.weixin.Services.UserService;
import com.lzw.weixin.Test.TestClass;
import com.lzw.weixin.Utils.CommonUtil;
import com.lzw.weixin.Utils.DBUtility;
import com.lzw.weixin.Utils.MenuUtil;
import com.lzw.weixin.Utils.TokenUtil;
import com.lzw.weixin.pojo.Token;
import com.lzw.weixin.pojo.UserInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/27.
 */
public class MenuManagerTest {

    private static Logger log= LoggerFactory.getLogger(TestClass.class);

    @Test
    public void main() throws Exception {
    }

    @org.junit.Test
    public void testGetToken()
    {
        Token token= TokenUtil.getToken();
        System.out.println("access_token:"+token.getAccessToken());
        System.out.println("expires_in:"+token.getExpiresIn());
    }

    @Test
    public void testgetConnection() throws SQLException {
        DBUtility db=new DBUtility();
        System.out.println(DBUtility.getConnection());
    }

    @Test
    public void testSqlServerGetToken()
    {
        Token token= TokenUtil.getToken();
        System.out.println(token.getAccessToken());
        System.out.println(token.getExpiresIn());
    }

    @Test
    public void testSaveToken()
    {
        Token token=TokenUtil.getToken();
        TokenUtil.saveToken(token);
    }

    @Test
    public void testCreateMenu()
    {
        String token= TokenUtil.getToken().getAccessToken();
        int result= MenuUtil.createMenu(MenuUtil.getMenu(),token);

        if(0==result)
        {
            log.info("菜单创建成功！");
        }else
        {
            log.info("菜单创建失败，错误码："+result);
        }
    }

    @Test
    public void getUserInfo()
    {
        String openId="ogp2a01cmCOv3AWNKISiKO3_V8lI";
        UserInfo info= UserService.getUserInfo(openId);
        System.out.println("OpenID："+info.getOpenId());
        System.out.println("关注状态："+info.getSubscribe());
        System.out.println("关注时间："+info.getSubscribeTime());
        System.out.println("昵称："+info.getNickname());
        System.out.println("性别："+info.getSex());
        System.out.println("国家："+info.getCountry());
        System.out.println("省份："+info.getProvince());
        System.out.println("城市："+info.getCity());
        System.out.println("语言："+info.getLanguage());
        System.out.println("头像："+info.getHeadImgUrl());
    }

    @Test
    public void testEncodeUrl()
    {
        String source="http://35.185.149.208/oauthServlet/";
        System.out.println(CommonUtil.urlEncodeUTF8(source));
    }

}