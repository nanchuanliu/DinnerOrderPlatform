package com.lzw.order.dinnerorderapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.ActivityAttribute;
import com.lzw.order.dinnerorderapp.Bean.BaseAttribute;
import com.lzw.order.dinnerorderapp.Bean.RestaurantInfo;
import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.component.ListViewAdapter;
import com.lzw.order.dinnerorderapp.component.ShopListRecyclerAdapter;
import com.lzw.order.dinnerorderapp.controls.CustomSwitchButton;
import com.lzw.order.dinnerorderapp.fragment.HomeFragment;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.CommonUtil;
import com.lzw.order.dinnerorderapp.utils.DisplayUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    private List<String> arrQuerySorts = Arrays.asList(new String[]{"综合排序", "好评优先", "起送价最低", "配送最快"});
    private String[] arrQueryValue = {"0", "3", "1", "2"};
    private PopupWindow popupWindow;
    private PopupWindow popupFilter;
    private View filterView;
    private TextView tvIntegratedQuery;
    private TextView tvHighestSales;
    private TextView tvNearestDistance;
    private TextView tvFilter;
    private ImageButton btnTriangle;
    private List<TextView> listSearchSort = new ArrayList<>();
    private TextView lastSearchView;
    private int selectedColor;
    private int contentColor;
    private ListView listView;
    private ListViewAdapter adapter;
    private String curOrderBy;
    private GridLayout gridDeliveryMode;
    private GridLayout gridActivities;
    private TextView btnClear;
    private TextView btnSearch;
    private String curSelectedIds;
    private String curSelectedModes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.bringToFront();

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
                    refreshRestaurantWithFoods(curKeyWord, curLocation.getLatitude(), curLocation.getLongitude(), lastListItemIndex, limit, curSelectedIds, curSelectedModes);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        refreshRestaurantWithFoods(curKeyWord, curLocation.getLatitude(), curLocation.getLongitude(), 0, limit, null, null);

        listView = new ListView(RestaurantActivity.this);
        Resources resource = getResources();
        selectedColor = resource.getColor(R.color.colorPrimary);
        contentColor = resource.getColor(R.color.colorContent);
        adapter = new ListViewAdapter(this, arrQuerySorts, selectedColor);
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setCacheColorHint(0);
        listView.setDrawingCacheBackgroundColor(Color.BLACK);

        popupWindow = new PopupWindow(listView, MATCH_PARENT, WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (adapter.getSelectedPosition() == -1) {
                    if (lastSearchView != null) {
                        lastSearchView.setTextColor(selectedColor);
                    }
                    tvIntegratedQuery.setTextColor(contentColor);
                    //动态改变SVG颜色
                    VectorDrawableCompat compat = VectorDrawableCompat.create(getResources(), R.drawable.ic_si_glyph_triangle_down_b, getTheme());
                    btnTriangle.setBackground(compat);
                    btnTriangle.setRotation(0);
                }
                backgroundAlpha(1);
            }
        });

        filterView = LayoutInflater.from(this).inflate(R.layout.control_filter, null);
        gridDeliveryMode = (GridLayout) filterView.findViewById(R.id.gridDeliveryMode);
        gridActivities = (GridLayout) filterView.findViewById(R.id.gridActivities);
        btnClear = (TextView) filterView.findViewById(R.id.btnClear);
        btnSearch = (TextView) filterView.findViewById(R.id.btnSearch);
        popupFilter = new PopupWindow(filterView, MATCH_PARENT, WRAP_CONTENT, true);
        popupFilter.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupFilter.setOutsideTouchable(true);
        popupFilter.setTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup);
        popupFilter.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        btnClear.setOnClickListener(filterClearListener);
        btnSearch.setOnClickListener(filterSearchListener);

        //查询分类
        tvIntegratedQuery = (TextView) findViewById(R.id.tvIntegratedQuery);
        btnTriangle = (ImageButton) findViewById(R.id.btnTriangle);
        tvHighestSales = (TextView) findViewById(R.id.tvHighestSales);
        tvNearestDistance = (TextView) findViewById(R.id.tvNearestDistance);
        tvFilter = (TextView) findViewById(R.id.tvFilter);
        listSearchSort.add(tvIntegratedQuery);
        listSearchSort.add(tvHighestSales);
        listSearchSort.add(tvNearestDistance);
        listSearchSort.add(tvFilter);
        tvIntegratedQuery.setOnClickListener(sortListener);
        tvHighestSales.setOnClickListener(sortListener);
        tvNearestDistance.setOnClickListener(sortListener);
        tvFilter.setOnClickListener(sortListener);

        //popupWindow.showAtLocation(view, Gravity.CENTER, 100,100);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvIntegratedQuery.setText(arrQuerySorts.get(i));
                tvIntegratedQuery.setTextColor(selectedColor);
                adapter.setSelectedItem(i);
                curOrderBy = arrQueryValue[i];
                resetRecycleView(curSelectedIds, curSelectedModes);
                if (popupWindow != null)
                    popupWindow.dismiss();
            }
        });

        getActivityAttributesByLocation(curLocation.getLatitude(), curLocation.getLongitude());
        getDeliveryModesByLocation(curLocation.getLatitude(), curLocation.getLongitude());

        ImageButton btn = (ImageButton) findViewById(R.id.btnReturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView tvSearch=(TextView)findViewById(R.id.tvSearch);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curKeyWord=etKewWord.getText().toString();
                resetRecycleView(curSelectedIds,curSelectedModes);
            }
        });
    }


    private void refreshRestaurantWithFoods(String keyword, double latitude, double longitude, int offset, final int limit, String ids, String modes) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        service.getRestaurantFoodsByKeyword(offset, limit, keyword, latitude, longitude, "2", "activities", curOrderBy, ids, modes)
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

    private View.OnClickListener sortListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (TextView sort :
                    listSearchSort) {
                int id = sort.getId();
                if (lastSearchView != null) {
                    int lastColor = sort.getCurrentTextColor();
                    if (lastColor == selectedColor && lastSearchView.getId() != id) {
                        lastSearchView = sort;
                    }
                }

                if (view.getId() == id)
                    sort.setTextColor(selectedColor);
                else
                    sort.setTextColor(contentColor);
            }

            if (view.getId() == R.id.tvIntegratedQuery) {
                popupWindow.showAsDropDown(view, 0, 10);
                backgroundAlpha(0.4f);

                //动态改变SVG颜色
                VectorDrawableCompat compat = VectorDrawableCompat.create(getResources(), R.drawable.ic_si_glyph_triangle_down_b, getTheme());
                compat.setTint(getResources().getColor(R.color.colorPrimary));
                btnTriangle.setBackground(compat);
                btnTriangle.setRotation(180);
            } else {
                //动态改变SVG颜色
                VectorDrawableCompat compat = VectorDrawableCompat.create(getResources(), R.drawable.ic_si_glyph_triangle_down_b, getTheme());
                btnTriangle.setBackground(compat);
                btnTriangle.setRotation(0);

                if (view.getId() == R.id.tvHighestSales) {
                    curOrderBy = "6";
                    resetRecycleView(curSelectedIds, curSelectedModes);
                } else if (view.getId() == R.id.tvNearestDistance) {
                    curOrderBy = "5";
                    resetRecycleView(curSelectedIds, curSelectedModes);
                } else {
                    popupFilter.showAsDropDown(view, 0, 10);
                    backgroundAlpha(0.4f);
                }
            }

            if (view.getId() == R.id.tvHighestSales || view.getId() == R.id.tvNearestDistance) {
                adapter.setSelectedItem(-1);
                listView.setSelection(-1);
                tvIntegratedQuery.setText("综合排序");
            }
        }
    };

    private void resetRecycleView(String ids, String modes) {
        restaurantListAdapter.resetAdapter();
        lastListItemIndex = 0;
        rvRestaurantList.removeAllViews();
        refreshRestaurantWithFoods(curKeyWord, curLocation.getLatitude(), curLocation.getLongitude(), 0, limit, ids, modes);
    }

    private void getDeliveryModesByLocation(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getDeliveryModesByLocation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ActivityAttribute>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ActivityAttribute> attrs) {
                        resetGridByAttributes(gridDeliveryMode, attrs);
                    }
                });
    }

    private void getActivityAttributesByLocation(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DataService service = retrofit.create(DataService.class);
        service.getActivityAttributesByLocation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BaseAttribute>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BaseAttribute> attrs) {
                        resetGridByAttributes(gridActivities, attrs);
                    }
                });
    }

    private void resetGridByAttributes(GridLayout layout, List<? extends BaseAttribute> attrs) {
        layout.removeAllViews();
        for (BaseAttribute attr :
                attrs) {
            CustomSwitchButton btn = new CustomSwitchButton(this, attr, selectedColor);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
            params.setMargins(0, 0, 1, 0);
            btn.setLayoutParams(params);
            layout.addView(btn);
        }
    }

    private View.OnClickListener filterSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<String> ids = getSelectedIds(gridActivities);
            List<String> modes = getSelectedIds(gridDeliveryMode);
            curSelectedIds = CommonUtil.Join(ids);
            curSelectedModes = CommonUtil.Join(modes);
            resetRecycleView(curSelectedIds, curSelectedModes);

            if (popupFilter != null)
                popupFilter.dismiss();
        }
    };

    private ArrayList<String> getSelectedIds(GridLayout grid) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < grid.getChildCount(); i++) {
            View item = grid.getChildAt(i);
            if (item instanceof CustomSwitchButton) {
                CustomSwitchButton custom = (CustomSwitchButton) item;
                if (custom.isSelected()) {
                    ids.add(custom.getAttrId());
                }
            }
        }
        return ids;
    }

    private View.OnClickListener filterClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clearGridLayoutSelectedState(gridDeliveryMode);
            clearGridLayoutSelectedState(gridActivities);
        }
    };

    private void clearGridLayoutSelectedState(GridLayout grid) {
        for (int i = 0; i < grid.getChildCount(); i++) {
            View item = grid.getChildAt(i);
            if (item instanceof CustomSwitchButton) {
                ((CustomSwitchButton) item).setSelected(false);
            }
        }
    }


    public void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = alpha;
        getWindow().setAttributes(params);
    }

    private int lastListItemIndex = 0;
    private int limit = 20;

}
