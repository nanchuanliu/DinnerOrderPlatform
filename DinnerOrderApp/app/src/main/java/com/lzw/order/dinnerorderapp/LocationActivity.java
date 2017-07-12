package com.lzw.order.dinnerorderapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.AddressInfo;
import com.lzw.order.dinnerorderapp.Bean.GeoInfo;
import com.lzw.order.dinnerorderapp.Bean.LatLng;
import com.lzw.order.dinnerorderapp.Bean.LocationInfo;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.CoordinateUtil;
import com.lzw.order.dinnerorderapp.utils.LocationUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.lzw.order.dinnerorderapp.Bean.AddressInfo.Regeocode.POI;

public class LocationActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Location curLocation;
    private LocationManager locMgr;
    private TextView tvCurCity, tvCurAddress;
    private LinearLayout lineRefreshLocation;
    private ImageView imgLocation;
    private Animation animRotate;
    private List<POI> locationInfos;
    private ListView lvNearAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        curLocation=(Location)bundle.get("curLocation");

        tvCurCity = (TextView) findViewById(R.id.tvCurCity);
        tvCurAddress = (TextView) findViewById(R.id.tvCurAddress);

        tvCurAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text=((TextView)view).getText().toString();
                returnMainActivity(text,curLocation.getLatitude(),curLocation.getLongitude());
            }
        });

        EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(searchWatcher);

        locMgr=(LocationManager)getSystemService(LOCATION_SERVICE);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotaterepeat);
        lineRefreshLocation = (LinearLayout) findViewById(R.id.lineRefreshLocation);
        lineRefreshLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgLocation.startAnimation(animRotate);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });

        //curLocation = LocationUtil.getCurrentLocation(this);
        refreshLocationInfo(curLocation);

        lvNearAddress = (ListView) findViewById(R.id.lvNearAddress);
        lvNearAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                POI info = locationInfos.get(i);
                returnMainActivity(info.getName(),Double.parseDouble(info.getLatitude()), Double.parseDouble(info.getLongitude()));
            }
        });

        ImageButton btn = (ImageButton) findViewById(R.id.btnReturn);
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
    }

    private void refreshLocationInfo(Location loc) {
        getCurrentGeoInfo(loc);
        getNearAddressByLocation(loc);
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


    private void getNearAddressByWord(String keyWord) {
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
                        //locationInfos=infos;
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_1, list.toArray(new String[list.size()]));
                        lvNearAddress.setAdapter(adapter);
                    }
                });
    }

    private void getNearAddressByLocation(Location loc) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtil.ADDRESS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        String cod = loc.getLongitude() + "," + loc.getLatitude();
        service.getAddressInfos(cod, "all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int i = 0;
                    }

                    @Override
                    public void onNext(AddressInfo address) {
                        List<String> list = new ArrayList<String>();
                        List<POI> pois = address.getRegeos().getPois();
                        for (POI poi :
                                pois) {
                            list.add(poi.getName());
                        }
                        locationInfos = pois;
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_1, list.toArray(new String[list.size()]));
                        lvNearAddress.setAdapter(adapter);
                    }
                });
    }


    private TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            getNearAddressByWord(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            LatLng lat = CoordinateUtil.transform2Mars(location.getLatitude(), location.getLongitude());
            location.setLatitude(lat.getLatitude());
            location.setLongitude(lat.getLongitude());

            locMgr.removeUpdates(this);
            curLocation = location;
            refreshLocationInfo(location);

           // new Handler().postDelayed(new Runnable() {
             //   @Override
              //  public void run() {
                    imgLocation.clearAnimation();
            //    }
           // }, 1000);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    private void returnMainActivity(String addr,double lat,double lng)
    {
        Intent intent = new Intent();
        intent.putExtra("name", addr);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lng);
        setResult(RESULT_OK, intent);
        finish();
    }
}
