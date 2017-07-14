package com.lzw.order.dinnerorderapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.RestaurantInfo;
import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.component.ShopListRecyclerAdapter;
import com.lzw.order.dinnerorderapp.fragment.HomeFragment;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class RestaurantActivity extends AppCompatActivity {

    private String curKeyWord;
    private Location curLocation;
    private RecyclerView rvRestaurantList;
    private ShopListRecyclerAdapter restaurantListAdapter;
    private boolean isLoading = false;
    private boolean hasMore = true;
    private EditText etKewWord;
    private String[] arrQuerySorts={"综合排序","好评优先","起送价最低","配送最快"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        curKeyWord = bundle.getString("KeyWord");
        curLocation = (Location) bundle.get("Location");
        etKewWord = (EditText) findViewById(R.id.etKewWord);
        etKewWord.setText(curKeyWord);

        rvRestaurantList = (RecyclerView) findViewById(R.id.rvRestaurantList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        rvRestaurantList.setLayoutManager(layoutManager);
        rvRestaurantList.setHasFixedSize(true);
        rvRestaurantList.setNestedScrollingEnabled(false);
        rvRestaurantList.setItemAnimator(new DefaultItemAnimator());
        restaurantListAdapter = new ShopListRecyclerAdapter(this);
        rvRestaurantList.setAdapter(restaurantListAdapter);

        rvRestaurantList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    if (!hasMore)
                        return;

                    if (isLoading)
                        return;
                    isLoading = true;
                    refreshRestaurantWithFoods(curKeyWord, curLocation.getLatitude(), curLocation.getLongitude(), lastListItemIndex, limit);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        refreshRestaurantWithFoods(curKeyWord, curLocation.getLatitude(), curLocation.getLongitude(), 0, limit);

        TextView tvIntegratedQuery=(TextView)findViewById(R.id.tvIntegratedQuery);
        tvIntegratedQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //View popupView= LayoutInflater.from(RestaurantActivity.this).inflate(R.layout.control_footerview,null,false);
                ListView listView=new ListView(RestaurantActivity.this);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(RestaurantActivity.this,android.R.layout.simple_list_item_1,arrQuerySorts);
                listView.setAdapter(adapter);

                PopupWindow popupWindow=new PopupWindow(listView,MATCH_PARENT,WRAP_CONTENT,true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAsDropDown(view,0,0);
                //popupWindow.showAtLocation(view, Gravity.CENTER, 100,100);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }
        });

    }


    private void refreshRestaurantWithFoods(String keyword, double latitude, double longitude, int offset, final int limit) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        service.getRestaurantFoodsByKeyword(offset, limit, keyword, latitude, longitude, "2", "activities")
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
                        isLoading = false;

                        List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();

                        if (info == null) {
                            hasMore = false;
                            return;
                        }

                        for (RestaurantInfo.RestaurantWithFoods item :
                                info.getRoot().getRestaurantWithFoods()) {
                            shopInfoList.add(item.getRestaurant());
                        }

                        if (shopInfoList.size() <= 0) {
                            hasMore = false;
                            return;
                        }

                        restaurantListAdapter.addDatas(shopInfoList, lastListItemIndex);
                        rvRestaurantList.smoothScrollToPosition(lastListItemIndex);
                        lastListItemIndex += limit;

                    }
                });
    }

    private int lastListItemIndex = 0;
    private int limit = 20;
}
