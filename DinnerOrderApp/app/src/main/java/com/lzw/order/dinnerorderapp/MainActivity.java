package com.lzw.order.dinnerorderapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.Bean.HotSearchWord;
import com.lzw.order.dinnerorderapp.Bean.WeatherInfo;
import com.lzw.order.dinnerorderapp.fragment.HomeFragment;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;

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

        tvLocation=(TextView)findViewById(R.id.tvLocation);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        checkFirstStart();

        HomeFragment homeFrag = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, homeFrag, "HomeFragment");
        transaction.commit();

        loadWeatherByLocation();
        refreshSearchWords();

        LinearLayout linePosition=(LinearLayout)findViewById(R.id.linePosition);
        linePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),LocationActivity.class);
                startActivityForResult(intent,1);
            }
        });

        LinearLayout layoutSearch=(LinearLayout)findViewById(R.id.layoutSearch);
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_LONG).show();
            }
        });

        //下拉刷新
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshHotWords);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshSearchWords();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Bundle bundle=data.getExtras();
            curAddr=bundle.getString("name");
            latitude=bundle.getFloat("latitude");
            longitude=bundle.getFloat("longitude");

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

    private void loadWeatherByLocation()
    {
        final TextView tvTemp = (TextView) findViewById(R.id.tvTemp);
        final TextView tvWeather = (TextView) findViewById(R.id.tvWeather);
        final ImageView imgView=(ImageView)findViewById(R.id.imgWeather);

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
                        tvTemp.setText(info.getTemperature()+"°");
                        tvWeather.setText(info.getDescription());
                        String path=info.getImage_hash();
                        String url=UrlUtil.getImageUrlFromPath(path);
                        Picasso.with(getApplicationContext()).load(url).into(imgView);
                    }
                });
    }
    
    
    public void refreshSearchWords()
    {
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
                        LinearLayout line=(LinearLayout)findViewById(R.id.lineWords);
                        line.removeAllViews();
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,10,10);
                        for (HotSearchWord item:
                             info) {
                            TextView tv=new TextView(getApplicationContext());
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
