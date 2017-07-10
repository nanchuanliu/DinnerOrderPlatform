package com.lzw.order.dinnerorderapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.lzw.order.dinnerorderapp.Bean.LatLng;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by LZW on 2017/07/07.
 */

public class LocationUtil {

    public static boolean isLocationEnabled(Context context)
    {
        int locationMode= Settings.Secure.LOCATION_MODE_OFF;
        String locationProviders;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            try{
                locationMode=Settings.Secure.getInt(context.getContentResolver(),Settings.Secure.LOCATION_MODE);
            }catch (Settings.SettingNotFoundException e)
            {
                e.printStackTrace();
            }
            return locationMode!=Settings.Secure.LOCATION_MODE_OFF;
        }else
        {
            locationProviders=Settings.Secure.getString(context.getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static Location getCurrentLocation(Activity activity) {
        Context context=activity.getApplicationContext();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        LocationManager locMgr = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        Location location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        //GPS坐标转火星坐标
        LatLng lat= CoordinateUtil.transform2Mars(location.getLatitude(),location.getLongitude());
        location.setLatitude(lat.getLatitude());
        location.setLongitude(lat.getLongitude());

        return location;
    }

}
