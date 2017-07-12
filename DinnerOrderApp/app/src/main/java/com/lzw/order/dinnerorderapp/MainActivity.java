package com.lzw.order.dinnerorderapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.Bean.GeoInfo;
import com.lzw.order.dinnerorderapp.Bean.HotSearchWord;
import com.lzw.order.dinnerorderapp.Bean.WeatherInfo;
import com.lzw.order.dinnerorderapp.component.FoodEntryAdapter;
import com.lzw.order.dinnerorderapp.fragment.HomeFragment;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.LocationUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvLocation;
    private String curAddr;
    private float latitude;
    private float longitude;
    private Location curLocation;
    private boolean isLoading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }*/
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        LocationManager locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent,0);
        }

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        curLocation = LocationUtil.getCurrentLocation(this);

        //默认东方明珠
        if(curLocation==null)
        {
            curLocation=new Location(LocationManager.GPS_PROVIDER);
            curLocation.setLatitude(31.2395176730);
            curLocation.setLongitude(121.4997661114);
        }

        getCurrentGeoInfo(curLocation);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        checkFirstStart();

        FragmentManager manager = getSupportFragmentManager();
        final HomeFragment homeFrag = new HomeFragment(getApplicationContext(),manager,swipeRefreshLayout,curLocation);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, homeFrag, "HomeFragment");
        transaction.commit();

        NestedScrollView nestedScrollView=(NestedScrollView)findViewById(R.id.nestHome);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>(v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())-500)
                {
                    homeFrag.loadMoreShopList();
                }
            }
        });

        loadWeatherByLocation();
        refreshSearchWords();

        LinearLayout linePosition = (LinearLayout) findViewById(R.id.linePosition);
        linePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LocationActivity.class);
                intent.putExtra("curLocation",curLocation);
                startActivityForResult(intent, 1);
            }
        });

        LinearLayout layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch);
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
            }
        });

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshHotWords);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
       // swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshSearchWords();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        //swipeRefreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        swipeRefreshLayout.setDistanceToTriggerSync(200);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            curAddr = bundle.getString("name");
            latitude = bundle.getFloat("latitude");
            longitude = bundle.getFloat("longitude");

            tvLocation.setText(curAddr);
        }
    }

    /**
     * 首次启动欢迎界面
     */
    private void checkFirstStart() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isFirstStart = pref.getBoolean("firstStart", true);

        if (isFirstStart) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(intent);
                }
            });

            thread.start();
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
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
                        tvLocation.setText(geoInfo.getAddress());
                    }
                });
    }


    private void loadWeatherByLocation() {
        final TextView tvTemp = (TextView) findViewById(R.id.tvTemp);
        final TextView tvWeather = (TextView) findViewById(R.id.tvWeather);
        final ImageView imgView = (ImageView) findViewById(R.id.imgWeather);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getWeather("31.218254", "121.359278")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WeatherInfo info) {
                        tvTemp.setText(info.getTemperature() + "°");
                        tvWeather.setText(info.getDescription());
                        String path = info.getImage_hash();
                        String url = UrlUtil.getImageUrlFromPath(UrlUtil.IMAGE_URL,path,false);
                        Picasso.with(getApplicationContext()).load(url).into(imgView);
                    }
                });
    }


    public void refreshSearchWords() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getHotSearchWords("31.218254", "121.359278")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HotSearchWord>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<HotSearchWord> info) {
                        LinearLayout line = (LinearLayout) findViewById(R.id.lineWords);
                        line.removeAllViews();
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10, 10, 10, 10);
                        for (HotSearchWord item :
                                info) {
                            TextView tv = new TextView(getApplicationContext());
                            tv.setText(item.getWord());
                            tv.setSingleLine();
                            tv.setTextColor(Color.WHITE);
                            tv.setLayoutParams(params);
                            //tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            line.addView(tv);
                        }
                    }
                });
    }
}
