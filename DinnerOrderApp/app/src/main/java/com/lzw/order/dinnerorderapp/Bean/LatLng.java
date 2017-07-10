package com.lzw.order.dinnerorderapp.Bean;

/**
 * Created by Administrator on 2017/7/8.
 */

public class LatLng {
    private double latitude;
    private double longitude;

    public LatLng(double ltd, double lgd) {
        this.latitude = ltd;
        this.longitude = lgd;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude;
    }
}
