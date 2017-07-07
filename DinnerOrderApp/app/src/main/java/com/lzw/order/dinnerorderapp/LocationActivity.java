package com.lzw.order.dinnerorderapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.AndroidCharacter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.GeoInfo;
import com.lzw.order.dinnerorderapp.Bean.LocationInfo;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.LocationUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocationActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Location location;
    private LocationManager locMgr;
    private TextView tvCurCity, tvCurAddress;
    private LinearLayout lineRefreshLocation;
    private ImageView imgLocation;
    private Animation animRotate;
    private List<LocationInfo> locationInfos;
    private ListView lvNearAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvCurCity = (TextView) findViewById(R.id.tvCurCity);
        tvCurAddress = (TextView) findViewById(R.id.tvCurAddress);

        EditText etSearch=(EditText)findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(searchWatcher);

        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotaterepeat);
        lineRefreshLocation = (LinearLayout) findViewById(R.id.lineRefreshLocation);
        lineRefreshLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgLocation.startAnimation(animRotate);
                refreshLocationInfo();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgLocation.clearAnimation();
                    }
                }, 1000);
            }
        });

        refreshLocationInfo();

        lvNearAddress = (ListView) findViewById(R.id.lvNearAddress);
        lvNearAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LocationInfo info=locationInfos.get(i);
                Intent intent=new Intent();
                intent.putExtra("name",info.getName());
                intent.putExtra("latitude",info.getLatitude());
                intent.putExtra("longitude",info.getLongitude());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        ImageButton btn=(ImageButton)findViewById(R.id.btnReturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

/*        if (!LocationUtil.isLocationEnabled(this)) {
            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent,0);
        }*/
        //locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
    }

    private void refreshLocationInfo() {
        Location loc = getCurrentLocation();
        getCurrentGeoInfo(loc);
        getNearAddress("淞虹路");
    }


    private Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        return location;
    }

    private void getCurrentGeoInfo(Location loc) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getGeoInfo(loc.getLatitude(), loc.getLongitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GeoInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GeoInfo geoInfo) {
                        tvCurCity.setText(geoInfo.getCity());
                        tvCurAddress.setText(geoInfo.getAddress());
                    }
                });
    }


    private void getNearAddress(String keyWord) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getLocationInfos(keyWord, 0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LocationInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<LocationInfo> infos) {
                        List<String> list = new ArrayList<String>();
                        for (LocationInfo info :
                                infos) {
                            list.add(info.getName());
                        }
                        locationInfos=infos;
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_1, list.toArray(new String[list.size()]));
                        lvNearAddress.setAdapter(adapter);
                    }
                });
    }


    private TextWatcher searchWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            getNearAddress(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
