package com.lzw.weixin.Utils;

import com.lzw.weixin.menu.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author: lzw
 * Date:  2017/6/25.
 */
public class MenuUtil {
    private static Logger log= LoggerFactory.getLogger(MenuUtil.class);

    public final static String menu_create_url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    public static int createMenu(Menu menu,String accessToken)
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


    public static Menu getMenu()
    {
        ViewButton btn11=new ViewButton();
        btn11.setName("点菜");
        btn11.setType("view");
        btn11.setUrl("http://35.185.149.208/ordermenu.jsp");

        CommonButton btn12=new CommonButton();
        btn12.setName("公交查询");
        btn12.setType("click");
        btn12.setKey("12");

        CommonButton btn13=new CommonButton();
        btn13.setName("周边搜索");
        btn13.setType("click");
        btn13.setKey("13");

        CommonButton btn14=new CommonButton();
        btn14.setName("历史上的今天");
        btn14.setType("click");
        btn14.setKey("14");

        CommonButton btn21=new CommonButton();
        btn21.setName("歌曲点播");
        btn21.setType("click");
        btn21.setKey("21");

        CommonButton btn22=new CommonButton();
        btn22.setName("经典游戏");
        btn22.setType("click");
        btn22.setKey("22");

        CommonButton btn23=new CommonButton();
        btn23.setName("广播电台");
        btn23.setType("click");
        btn23.setKey("23");

        CommonButton btn24=new CommonButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn25=new CommonButton();
        btn25.setName("聊天唠嗑");
        btn25.setType("click");
        btn25.setKey("25");

        CommonButton btn31=new CommonButton();
        btn31.setName("Q友圈");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32=new CommonButton();
        btn32.setName("电影排行榜");
        btn32.setType("click");
        btn32.setKey("32");

        CommonButton btn33=new CommonButton();
        btn33.setName("幽默笑话");
        btn33.setType("click");
        btn33.setKey("33");

        ComplexButton mainBtn1=new ComplexButton();
        mainBtn1.setName("生活助手");
        mainBtn1.setSub_button(new Button[]{btn11,btn12,btn13,btn14});

        ComplexButton mainBtn2=new ComplexButton();
        mainBtn2.setName("休闲驿站");
        mainBtn2.setSub_button(new Button[]{btn21,btn22,btn23,btn24,btn25});

        ComplexButton mainBtn3=new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new Button[]{btn31,btn32,btn33});

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
