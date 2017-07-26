package com.lzw.order.dinnerorderapp.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/5.
 */

public class UrlUtil {
    public static final String BASE_URL = "https://mainsite-restapi.ele.me";
    public static final String ADDRESS_URL = "https://restapi.amap.com/v3/";
    public static final String LOCATION_URL = "bgs/poi/search_poi_nearby?keyword=%s&offset=0&limit=20";
    public static final String WEATHER_URL = "bgs/weather/current?latitude=31.218254&longitude=121.359278";

    public static final String IMAGE_URL = "http://fuss10.elemecdn.com/%s/%s/%s.png?imageMogr/format/webp/thumbnail/!69x69r/gravity/Center/crop/69x69/";
    public static final String SHOP_URL = "http://fuss10.elemecdn.com/%s/%s/%s?imageMogr/format/webp/thumbnail/!120x120r/gravity/Center/crop/120x120/";
    public static final String MENU_URL="https://fuss10.elemecdn.com/%s/%s/%s?imageMogr/format/webp/thumbnail/18x/";
    public static final String DISH_URL="https://fuss10.elemecdn.com/%s/%s/%s?imageMogr/format/webp/thumbnail/!140x140r/gravity/Center/crop/140x140/";
    public static final String SHOP_BACK_URL="https://fuss10.elemecdn.com/%s/%s/%s?imageMogr/format/webp/thumbnail/!40p/blur/50x40/";

    public static String getImageUrlFromPath(String baseUrl, String path, boolean isSuffix) {
        String str1 = path.substring(0, 1);
        String str2 = path.substring(1, 3);
        String str3 = path.substring(3);

        if (isSuffix)
            str3 += path.endsWith("jpeg") ? ".jpeg" : path.endsWith("png") ? ".png" : path.endsWith("jpg") ? ".jpg" : "";
        return String.format(baseUrl, str1, str2, str3);
    }

    public static final List<String> listFoodName = new ArrayList<>();
    public static final List<String> listFoodUrl = new ArrayList<>();

    static {

        listFoodUrl.add("https://fuss10.elemecdn.com/b/7e/d1890cf73ae6f2adb97caa39de7fcjpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/2/35/696aa5cf9820adada9b11a3d14bf5jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/0/da/f42235e6929a5cb0e7013115ce78djpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/d/49/7757ff22e8ab28e7dfa5f7e2c2692jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/c/db/d20d49e5029281b9b73db1c5ec6f9jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/a/fa/d41b04d520d445dc5de42dae9a384jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/3/84/8e031bf7b3c036b4ec19edff16e46jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/e/89/185f7259ebda19e16123884a60ef2jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/1/c1/dfade1a31f312f161074c3dd6a89cjpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/b/7f/432619fb21a40b05cd25d11eca02djpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/2/17/244241b514affc0f12f4168cf6628jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/8/83/171fd98b85dee3b3f4243b7459b48jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/3/c7/a9ef469a12e7a596b559145b87f09jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/9/c0/c4a1e0692daa0ca7f8c4c8f2e1496jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/7/b6/235761e50d391445f021922b71789jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");
        listFoodUrl.add("https://fuss10.elemecdn.com/6/d2/de0683a49a0655c728b70fdb344d5jpeg.jpeg?imageMogr/format/webp/thumbnail/!90x90r/gravity/Center/crop/90x90/");


        listFoodName.add("美食");
        listFoodName.add("甜品饮品");
        listFoodName.add("商超便利");
        listFoodName.add("预定早餐");
        listFoodName.add("果蔬生鲜");
        listFoodName.add("新店特惠");
        listFoodName.add("准时达");
        listFoodName.add("晚餐");
        listFoodName.add("帮买帮送");
        listFoodName.add("汉堡薯条");
        listFoodName.add("包子粥店");
        listFoodName.add("鲜花蛋糕");
        listFoodName.add("麻辣烫");
        listFoodName.add("地方菜");
        listFoodName.add("比萨意面");
        listFoodName.add("异国料理");
    }

}
