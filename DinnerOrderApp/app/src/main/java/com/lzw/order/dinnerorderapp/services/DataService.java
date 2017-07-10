package com.lzw.order.dinnerorderapp.services;

import com.lzw.order.dinnerorderapp.Bean.AddressInfo;
import com.lzw.order.dinnerorderapp.Bean.GeoInfo;
import com.lzw.order.dinnerorderapp.Bean.HotSearchWord;
import com.lzw.order.dinnerorderapp.Bean.LocationInfo;
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
    Observable<AddressInfo> getAddressInfos(@Query("location") String location,@Query("extensions") String all);

    @GET("bgs/weather/current")
    Observable<WeatherInfo> getWeather(@Query("latitude") String latitude,@Query("longitude") String longitude);

    @GET("shopping/v3/hot_search_words")
    Observable<List<HotSearchWord>> getHotSearchWords(@Query("latitude") String latitude, @Query("longitude") String longitude);

    @GET("bgs/poi/reverse_geo_coding")
    Observable<GeoInfo> getGeoInfo(@Query("latitude") double latitude,@Query("longitude") double longitude);

    @GET("shopping/restaurants")
    Observable<List<ShopInfo>> getShopInfosByLocation(@Query("latitude") double latitude,@Query("longitude") double longitude,@Query("offset") int offset, @Query("limit") int limit,@Query("extras[]") String extras);
}
