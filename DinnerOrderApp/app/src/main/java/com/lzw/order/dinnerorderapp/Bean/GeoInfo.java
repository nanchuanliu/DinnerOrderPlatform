package com.lzw.order.dinnerorderapp.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LZW on 2017/07/07.
 */

public class GeoInfo {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @SerializedName("address")

    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

}
