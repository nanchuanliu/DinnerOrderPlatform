package com.lzw.weixin.Test;

import com.lzw.weixin.Services.TokenThread;
import com.lzw.weixin.Services.UserService;
import com.lzw.weixin.Utils.*;
import com.lzw.weixin.pojo.Token;
import com.lzw.weixin.pojo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Map;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class TestClass {


/*    public final static String appID = "wx2d080b58edae0b82";
    public final static String appsecret = "044c3518a9877adfba62382dc738667f";*/

    public static void main(String[] args) throws Exception {

        String appID=TokenThread.appID;
        String appsecret=TokenThread.appsecret;

        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret=" + appsecret;

        URL url = new URL(tokenUrl);
        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new SecureRandom());
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        httpUrlConn.setSSLSocketFactory(ssf);
        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);

        httpUrlConn.setRequestMethod("GET");

        InputStream stream = httpUrlConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuffer buffer = new StringBuffer();
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }

        bufferedReader.close();
        inputStreamReader.close();
        stream.close();
        httpUrlConn.disconnect();

        System.out.print(buffer);
    }


}
