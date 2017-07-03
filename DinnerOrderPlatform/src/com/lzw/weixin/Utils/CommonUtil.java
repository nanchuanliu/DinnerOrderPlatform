package com.lzw.weixin.Utils;

import com.lzw.weixin.Services.TokenThread;
import com.lzw.weixin.pojo.Token;
import com.sun.deploy.net.HttpRequest;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class CommonUtil {

    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public final static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;

        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);

            //当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream stream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(stream,"UTF-8");
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

            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException e) {
            log.error("连接超时：{}", e);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }

        log.debug(jsonObject.toString());
        System.out.println(jsonObject.toString());

        return jsonObject;
    }


    public static Token getToken(String appID, String appsecret) {
        Token token = null;
        String requestUrl = String.format(tokenUrl, appID, appsecret);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (Exception e) {
                token = null;
                log.error("获取token失败 errcode：{} errmsg：{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }

    public static Map<String,Object> getWxConfig(HttpServletRequest request)
    {
        Map<String,Object> ret=new HashMap<String,Object>();
        String appId= TokenThread.appID;
        String secret=TokenThread.appsecret;

        String requestUrl=request.getRequestURL().toString();
        String access_token=TokenUtil.getToken().getAccessToken();
        String jsapi_ticket="";
        String timestamp=Long.toString(System.currentTimeMillis()/1000);
        String nonceStr= UUID.randomUUID().toString();

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";
        JSONObject json=CommonUtil.httpsRequest(url,"GET",null);
        if(json!=null)
            jsapi_ticket=json.getString("ticket");

        String signature="";
        String sign="jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+requestUrl;

        System.out.println("sign:"+sign);
        log.debug("sign:"+sign);

        try {
            MessageDigest digest=MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(sign.getBytes("UTF-8"));
            signature=SignUtil.byteToHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("appId",appId);
        ret.put("timestamp",timestamp);
        ret.put("nonceStr",nonceStr);
        ret.put("signature",signature);

        return ret;
    }


    public static String urlEncodeUTF8(String source)
    {
        String result=source;
        try {
            result= URLEncoder.encode(source,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static String getRequestDomain(HttpServletRequest request)
    {
        StringBuffer url =request.getRequestURL() ;
        String uri=request.getRequestURI();
        String domain = url.delete(url.length() - uri.length(), url.length()).append("/").toString();
        return domain;
    }
}
