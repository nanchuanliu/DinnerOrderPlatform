package com.lzw.weixin.Services;

import com.lzw.weixin.Utils.CommonUtil;
import com.lzw.weixin.Utils.TokenUtil;
import com.lzw.weixin.pojo.Oauth2Token;
import com.lzw.weixin.pojo.SNSUserInfo;
import com.lzw.weixin.pojo.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class UserService {

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    public static UserInfo getUserInfo(String openId) {
        UserInfo info = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";

        String token = TokenUtil.getToken().getAccessToken();
        requestUrl = String.format(requestUrl, token, openId);

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                info = new UserInfo();
                info.setOpenId(jsonObject.getString("openid"));
                info.setSubscribe(jsonObject.getInt("subscribe"));
                info.setSubscribeTime(jsonObject.getString("subscribe_time"));
                info.setNickname(jsonObject.getString("nickname"));
                info.setSex(jsonObject.getInt("sex"));
                info.setCountry(jsonObject.getString("country"));
                info.setProvince(jsonObject.getString("province"));
                info.setCity(jsonObject.getString("city"));
                info.setLanguage(jsonObject.getString("language"));
                info.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == info.getSubscribe()) {
                    log.error("用户{}已取消关注", info.getOpenId());
                } else {
                    int errCode = jsonObject.getInt("errcode");
                    String errMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode：{}  errmsg：{}", errCode, errMsg);
                }

            }
        }

        return info;
    }


    public static Oauth2Token getOauth2AccessToken(String appID, String appsecret, String code) {
        Oauth2Token token = null;
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        requestUrl = String.format(requestUrl, appID, appsecret, code);

        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        try {
            token = new Oauth2Token();
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
            token.setRefreshToken(jsonObject.getString("refresh_token"));
            token.setOpenId(jsonObject.getString("openid"));
            token.setScope(jsonObject.getString("scope"));
        } catch (Exception e) {
            token = null;
            int errCode = jsonObject.getInt("errcode");
            String errMsg = jsonObject.getString("errmsg");
            log.error("获取网页授权凭证失败 errcode：{} errmsg：{}", errCode, errMsg);
        }

        return token;
    }


    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo info = null;

        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

        requestUrl = String.format(requestUrl, accessToken, openId);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                info = new SNSUserInfo();
                info.setOpenId(jsonObject.getString("openid"));
                info.setNickname(jsonObject.getString("nickname"));
                info.setSex(jsonObject.getInt("sex"));
                info.setCountry(jsonObject.getString("country"));
                info.setProvince(jsonObject.getString("province"));
                info.setCity(jsonObject.getString("city"));
                info.setHeadImgUrl(jsonObject.getString("headimgurl"));
                info.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                int errCode = jsonObject.getInt("errcode");
                String errMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode：{}  errmsg：{}", errCode, errMsg);
            }
        }

        return info;
    }
}
