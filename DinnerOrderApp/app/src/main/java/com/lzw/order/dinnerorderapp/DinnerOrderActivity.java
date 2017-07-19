package com.lzw.order.dinnerorderapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.Bean.Category;
import com.lzw.order.dinnerorderapp.Bean.Food;
import com.lzw.order.dinnerorderapp.component.CategoryAdapter;
import com.lzw.order.dinnerorderapp.component.DishAdapter;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.DisplayUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DinnerOrderActivity extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {

    private String restaurantId;
    private RecyclerView rvCategory;
    private RecyclerView rvDish;
    private LinearLayoutManager dishLayoutManager;
    private DishAdapter dishAdapter;
    private LinearLayoutManager categoryLayoutManager;
    private CategoryAdapter categoryAdapter;
    private List<Category> listCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        restaurantId = bundle.getString("Id");

        categoryLayoutManager = new LinearLayoutManager(this);
        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(categoryLayoutManager);
        rvDish = (RecyclerView) findViewById(R.id.rvDish);
        dishLayoutManager = new LinearLayoutManager(this);
        dishLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        dishLayoutManager.setAutoMeasureEnabled(true);
        rvDish.setLayoutManager(dishLayoutManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rvDish.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    refreshScrollListener();
                }
            });
        } else {
            rvDish.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    refreshScrollListener();
                }
            });
        }

        refreshMenusByRestaurantId(restaurantId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void refreshMenusByRestaurantId(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        service.getMenusByRestaurantId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Category> datas) {
                        List<Food> foods = new ArrayList<Food>();

                        listCategory = datas;

                        categoryAdapter = new CategoryAdapter(DinnerOrderActivity.this, datas);
                        rvCategory.setAdapter(categoryAdapter);

                        foods = datas.get(0).getFoods();
                        dishAdapter = new DishAdapter(DinnerOrderActivity.this, datas);
                        rvDish.setAdapter(dishAdapter);

                        StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(dishAdapter);
                        rvDish.addItemDecoration(decoration);

                    }
                });
    }

    private int oldSelectedPosition = 0;
    private boolean needMove = false;
    private int movePosition;

    private void changeSelected(int position) {
        listCategory.get(oldSelectedPosition).setSelected(false);
        listCategory.get(position).setSelected(true);
        //增加左侧联动
        rvCategory.scrollToPosition(position);
        oldSelectedPosition = position;
        categoryAdapter.notifyDataSetChanged();
    }

    private void refreshScrollListener() {
        //第一个完全显示的item和最后一个item
        int firstVisibleItem = dishLayoutManager.findFirstCompletelyVisibleItemPosition();
        int lastVisibleItem = dishLayoutManager.findLastVisibleItemPosition();

        //此判断，避免左侧点击最后一个item无响应
        if (lastVisibleItem != dishLayoutManager.getItemCount() - 1) {
            int sort = dishAdapter.getSortType(firstVisibleItem);
            changeSelected(sort);
        } else {
            changeSelected(categoryAdapter.getItemCount() - 1);
        }
        if (needMove) {
            needMove = false;
            //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
            int n = movePosition - dishLayoutManager.findFirstVisibleItemPosition();
            if (0 <= n && n < rvDish.getChildCount()) {
                //获取要置顶的项顶部离RecyclerView顶部的距离
                int top = rvDish.getChildAt(n).getTop() - DisplayUtil.dip2px(DinnerOrderActivity.this, 28);
                //最后的移动
                rvDish.scrollBy(0, top);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        changeSelected(position);
        scrollToSelectedCategoryItem(position);
    }

    private void scrollToSelectedCategoryItem(int position) {
        movePosition = 0;
        for (int i = 0; i < position; i++) {
            movePosition += listCategory.get(i).getFoods().size();
        }

        scrollToPosition(movePosition);
    }

    private void scrollToPosition(int pos) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = dishLayoutManager.findFirstVisibleItemPosition();
        int lastItem = dishLayoutManager.findLastVisibleItemPosition();

        if (pos <= firstItem) {
            rvDish.scrollToPosition(pos);
        } else if (pos <= lastItem) {
            int top = rvDish.getChildAt(pos = firstItem).getTop();
            rvDish.scrollBy(0, top - DisplayUtil.dip2px(this, 28));
        } else {
            rvDish.scrollToPosition(pos);
            movePosition = pos;
            needMove = true;
        }
    }
}
