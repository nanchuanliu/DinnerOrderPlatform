package com.lzw.order.dinnerorderapp.utils;

/**
 * Created by Administrator on 2017/7/5.
 */

public class UrlUtil {
    public static final String BASE_URL="https://mainsite-restapi.ele.me";
    public static final String LOCATION_URL="bgs/poi/search_poi_nearby?keyword=%s&offset=0&limit=20";
    public static final String WEATHER_URL="bgs/weather/current?latitude=31.218254&longitude=121.359278";

    public static final String IMAGE_URL="http://fuss10.elemecdn.com/%s/%s/%s.png?imageMogr/format/webp/thumbnail/!69x69r/gravity/Center/crop/69x69/";

    public static String getImageUrlFromPath(String path)
    {
        String str1=path.substring(0,1);
        String str2=path.substring(1,3);
        String str3=path.substring(3);

        return String.format(IMAGE_URL,str1,str2,str3);
    }
}
