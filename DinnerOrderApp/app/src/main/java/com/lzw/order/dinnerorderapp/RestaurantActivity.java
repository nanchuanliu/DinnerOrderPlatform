package com.lzw.order.dinnerorderapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzw.order.dinnerorderapp.Bean.RestaurantInfo;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RestaurantActivity extends AppCompatActivity {

    private String curKeyWord;
    private Location curLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        curKeyWord=bundle.getString("KeyWord");
        curLocation=(Location)bundle.get("Location");

        FragmentManager manager=getSupportFragmentManager();
        //todo

        refreshRestaurantWithFoods(curKeyWord);
    }


    private void refreshRestaurantWithFoods(String keyword)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        service.getRestaurantFoodsByKeyword(0,20,keyword, curLocation.getLatitude(), curLocation.getLongitude(),"2","activities")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RestaurantInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(RestaurantInfo info) {
                       int i=0;
                        i++;
                    }

                });
    }

}
