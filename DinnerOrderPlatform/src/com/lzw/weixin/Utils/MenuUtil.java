package com.lzw.weixin.Utils;

import com.lzw.weixin.Services.TokenThread;
import com.lzw.weixin.menu.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class MenuUtil {
    private static Logger log= LoggerFactory.getLogger(MenuUtil.class);

    public final static String menu_create_url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    public static int createMenu(Menu menu)
    {
        int result=0;
        String url=String.format(menu_create_url,TokenUtil.getToken().getAccessToken());
        String jsonMenu= JSONObject.fromObject(menu).toString();
        JSONObject jsonObject=CommonUtil.httpsRequest(url,"POST",jsonMenu);
        if(null!=jsonObject)
        {
            if(0!=jsonObject.getInt("errcode"))
            {
                result=jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
            }
        }

        return result;
    }


    public static Menu getMenu(String appId)
    {
        ViewButton btn11=new ViewButton();
        btn11.setName("订位");
        btn11.setType("view");
        btn11.setUrl("http://mylife.51vip.biz/restaurantlist.jsp");

        ViewButton btn12=new ViewButton();
        btn12.setName("点菜");
        btn12.setType("view");
        btn12.setUrl("http://mylife.51vip.biz/ordermenu.jsp");

        ViewButton btn13=new ViewButton();
        btn13.setName("店内服务");
        btn13.setType("view");
        btn13.setUrl("http://mylife.51vip.biz/service.jsp");

        CommonButton btn14=new CommonButton();
        btn14.setName("叫号查询");
        btn14.setType("click");
        btn14.setKey("14");

        CommonButton btn21=new CommonButton();
        btn21.setName("订位单");
        btn21.setType("click");
        btn21.setKey("21");

        CommonButton btn22=new CommonButton();
        btn22.setName("我的菜单");
        btn22.setType("click");
        btn22.setKey("22");

        CommonButton btn23=new CommonButton();
        btn23.setName("我的账单");
        btn23.setType("click");
        btn23.setKey("23");

        CommonButton btn24=new CommonButton();
        btn24.setName("券包");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn25=new CommonButton();
        btn25.setName("会员卡");
        btn25.setType("click");
        btn25.setKey("25");

        ViewButton btn31=new ViewButton();
        btn31.setName("特色推荐");
        btn31.setType("view");
        btn31.setUrl("http://mylife.51vip.biz/recommend.jsp");

        ViewButton btn32=new ViewButton();
        btn32.setName("周边服务");
        btn32.setType("view");
        btn32.setUrl("http://mylife.51vip.biz/periphery.jsp");

        CommonButton btn33=new CommonButton();
        btn33.setName("好友分享");
        btn33.setType("click");
        btn33.setKey("33");

        CommonButton btn34=new CommonButton();
        btn34.setName("美文赏析");
        btn34.setType("click");
        btn34.setKey("33");

        ComplexButton mainBtn1=new ComplexButton();
        mainBtn1.setName("服务");
        mainBtn1.setSub_button(new Button[]{btn11,btn12,btn13,btn14});

        ComplexButton mainBtn2=new ComplexButton();
        mainBtn2.setName("我的");
        mainBtn2.setSub_button(new Button[]{btn21,btn22,btn23,btn24,btn25});

        ComplexButton mainBtn3=new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new Button[]{btn31,btn32,btn33,btn34});

        Menu menu=new Menu();
        menu.setButton(new Button[]{mainBtn1,mainBtn2,mainBtn3});
        return menu;
    }

    public static int deleteMenu()
    {
        String token=TokenUtil.getToken().getAccessToken();
        String url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+token;

        int result=0;
        JSONObject jsonObject=CommonUtil.httpsRequest(url,"POST",null);
        if(null!=jsonObject)
        {
            if(0!=jsonObject.getInt("errcode"))
            {
                result=jsonObject.getInt("errcode");
                log.error("删除菜单失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
            }
        }

        return result;
    }

    public static int searchMenu()
    {
        String token=TokenUtil.getToken().getAccessToken();
        String url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+token;

        int result=0;
        JSONObject jsonObject=CommonUtil.httpsRequest(url,"POST",null);
        if(null!=jsonObject)
        {
            if(0!=jsonObject.getInt("errcode"))
            {
                result=jsonObject.getInt("errcode");
                log.error("删除菜单失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
}
