package com.lzw.order.dinnerorderapp.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzw.order.dinnerorderapp.Bean.GeoInfo;
import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.component.FoodPagerAdapter;
import com.lzw.order.dinnerorderapp.component.ShopListRecyclerAdapter;
import com.lzw.order.dinnerorderapp.component.ScrollViewIndicator;
import com.lzw.order.dinnerorderapp.component.ScrollViewPager;
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

/**
 * Created by LZW on 2017/07/05.
 */
public class HomeFragment extends Fragment {

    private static final String tag = "HomeFragment";
    private RecyclerView rvShopList;
    private Location curLocation;
    private FragmentManager fragmentManager;
    private SwipeRefreshLayout refreshLayout;
    private Context context;
    private ShopListRecyclerAdapter shopListAdapter;
    private boolean isLoading = false;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);

    public HomeFragment() {
        super();
    }

    public HomeFragment(Context context, FragmentManager fm, SwipeRefreshLayout layout, Location loc) {
        this();
        this.context = context;
        fragmentManager = fm;
        refreshLayout = layout;
        curLocation = loc;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

      /*  FragmentPagerAdapter adapter = new IndicatorAdapter(getActivity().getSupportFragmentManager());
        ViewPager pager = (ViewPager) view.findViewById(R.id.vp_pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) view.findViewById(R.id.vpi_indicator);
        indicator.setViewPager(pager);*/

        //SVG颜色设置
/*        VectorDrawableCompat compat=VectorDrawableCompat.create(getResources(),R.drawable.ic_si_glyph_pin_location,getActivity().getTheme());
        compat.setTint(getResources().getColor(android.R.color.white));
        ImageView imageView=(ImageView)view.findViewById(R.id.imgLocation);
        imageView.setImageDrawable(compat);*/


        ScrollViewPager vpFoodEntry = (ScrollViewPager) view.findViewById(R.id.vpFoodEntry);
        FoodPagerAdapter foodEntryAdapter = new FoodPagerAdapter(this.getActivity(), UrlUtil.listFoodName);
        vpFoodEntry.setAdapter(foodEntryAdapter);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.indicatorLayout);
        vpFoodEntry.setOnPageChangeListener(new ScrollViewIndicator(this.getActivity(), vpFoodEntry, layout, 2));

        initRecycleList(view);

        return view;
    }

    private void initRecycleList(View view) {
        rvShopList = (RecyclerView) view.findViewById(R.id.rvShopList);

        layoutManager.setAutoMeasureEnabled(true);
        rvShopList.setLayoutManager(layoutManager);
        rvShopList.setHasFixedSize(true);
        rvShopList.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator)rvShopList.getItemAnimator()).setSupportsChangeAnimations(false);
        shopListAdapter = new ShopListRecyclerAdapter(fragmentManager, getContext());
        rvShopList.setAdapter(shopListAdapter);

        refreshShopList(curLocation.getLatitude(), curLocation.getLongitude(), 0, limit);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvShopList.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        rvShopList.setItemAnimator(new DefaultItemAnimator());
    }

    public void loadMoreShopList() {
        if (isLoading)
            return;
        isLoading = true;
        refreshShopList(curLocation.getLatitude(), curLocation.getLongitude(), lastListItemIndex, limit);
    }

    private void refreshShopList(double latitude, double longitude, int offset, final int limit) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getShopInfosByLocation(latitude, longitude, offset, limit, "activities")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ShopInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ShopInfo> shopInfo) {
                        shopListAdapter.addDatas(shopInfo, lastListItemIndex);
                        rvShopList.smoothScrollToPosition(lastListItemIndex);
                        lastListItemIndex += limit;
                        isLoading = false;
/*                        rvShopList.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if(newState==RecyclerView.SCROLL_STATE_IDLE && lastListItemIndex+1==adapter.getItemCount())
                                {
                                    refreshLayout.setRefreshing(true);
                                }
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                //lastListItemIndex=layoutManager.findLastVisibleItemPosition();
                            }
                        });*/
                    }
                });
    }

    private int lastListItemIndex = 0;
    private int limit = 20;

}
