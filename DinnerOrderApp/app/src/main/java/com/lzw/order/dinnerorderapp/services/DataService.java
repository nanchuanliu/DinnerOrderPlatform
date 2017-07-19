package com.lzw.order.dinnerorderapp.services;

import com.lzw.order.dinnerorderapp.Bean.ActivityAttribute;
import com.lzw.order.dinnerorderapp.Bean.AddressInfo;
import com.lzw.order.dinnerorderapp.Bean.BaseAttribute;
import com.lzw.order.dinnerorderapp.Bean.Category;
import com.lzw.order.dinnerorderapp.Bean.Food;
import com.lzw.order.dinnerorderapp.Bean.GeoInfo;
import com.lzw.order.dinnerorderapp.Bean.HotSearchWord;
import com.lzw.order.dinnerorderapp.Bean.LocationInfo;
import com.lzw.order.dinnerorderapp.Bean.RestaurantInfo;
import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.Bean.WeatherInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LZW on 2017/07/03.
 */
public interface DataService {

    @GET("bgs/poi/search_poi_nearby")
    Observable<List<LocationInfo>> getLocationInfos(@Query("keyword") String key, @Query("offset") int offset, @Query("limit") int limit);

    @GET("geocode/regeo?key=3f3868abdb36336114bde5ab6eecdb68")
    Observable<AddressInfo> getAddressInfos(@Query("location") String location, @Query("extensions") String all);

    @GET("bgs/weather/current")
    Observable<WeatherInfo> getWeather(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("shopping/v3/hot_search_words")
    Observable<List<HotSearchWord>> getHotSearchWords(@Query("geohash") String geohash, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("bgs/poi/reverse_geo_coding")
    Observable<GeoInfo> getGeoInfo(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("shopping/restaurants")
    Observable<List<ShopInfo>> getShopInfosByLocation(@Query("latitude") double latitude, @Query("longitude") double longitude,
                                                      @Query("offset") int offset, @Query("limit") int limit, @Query("extras[]") String extras);

    @GET("shopping/v1/restaurants/search")
    Observable<RestaurantInfo> getRestaurantFoodsByKeyword(@Query("offset") int offset, @Query("limit") int limit, @Query("keyword") String keyword,
                                                           @Query("latitude") double latitude, @Query("longitude") double longitude,@Query("search_item_type") String search_item_type,
                                                           @Query("extra[]") String extra,@Query("order_by") String orderBy,@Query("support_ids[]") String ids,@Query("delivery_mode[]") String modes);

    @GET("shopping/v1/restaurants/delivery_modes")
    Observable<List<ActivityAttribute>> getDeliveryModesByLocation(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("shopping/v1/restaurants/activity_attributes")
    Observable<List<BaseAttribute>> getActivityAttributesByLocation(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("shopping/v2/menu")
    Observable<List<Category>> getMenusByRestaurantId(@Query("restaurant_id") String restaurantId);
}
