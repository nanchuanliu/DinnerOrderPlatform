package com.lzw.order.dinnerorderapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.Bean.Category;
import com.lzw.order.dinnerorderapp.Bean.Food;
import com.lzw.order.dinnerorderapp.Bean.ShopInfo;
import com.lzw.order.dinnerorderapp.component.CategoryAdapter;
import com.lzw.order.dinnerorderapp.component.DishAdapter;
import com.lzw.order.dinnerorderapp.component.DividerItemDecoration;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.DisplayUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class DinnerOrderActivity extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {

    private String restaurantId;
    private Location curLocation;
    private RecyclerView rvCategory;
    private RecyclerView rvDish;
    private LinearLayoutManager dishLayoutManager;
    private DishAdapter dishAdapter;
    private LinearLayoutManager categoryLayoutManager;
    private CategoryAdapter categoryAdapter;
    private List<Category> listCategory;
    private ImageView imgCart;
    private TextView tvCartCount;
    private RelativeLayout rlLayoutBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rlLayoutBar = (RelativeLayout) findViewById(R.id.rlLayoutBar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        restaurantId = bundle.getString("Id");
        curLocation = (Location) bundle.get("Location");

        categoryLayoutManager = new LinearLayoutManager(this);
        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(categoryLayoutManager);
        rvCategory.addItemDecoration(new DividerItemDecoration(this, categoryLayoutManager.getOrientation()));

        rvDish = (RecyclerView) findViewById(R.id.rvDish);
        dishLayoutManager = new LinearLayoutManager(this);
        dishLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        dishLayoutManager.setAutoMeasureEnabled(true);
        rvDish.setLayoutManager(dishLayoutManager);
        rvDish.addItemDecoration(new DividerItemDecoration(this, dishLayoutManager.getOrientation()));


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

        refreshRestaurantInfo(restaurantId, curLocation);
        refreshMenusByRestaurantId(restaurantId);

        imgCart = (ImageView) findViewById(R.id.imgCart);
        tvCartCount = (TextView) findViewById(R.id.tvCartCount);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = 1 + (float) verticalOffset / 100;
                if (alpha < 0)
                    alpha = 0;

                rlLayoutBar.setAlpha(alpha);
            }
        });
    }

    private void refreshRestaurantInfo(String id, Location location) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        service.getRestaurantInfo(id, location.getLatitude(), location.getLongitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShopInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ShopInfo info) {
                        ShopInfo.DeliveryMode mode = info.getDelivery_mode();

                        ImageView imgIcon = (ImageView) findViewById(R.id.imgIcon);
                        ImageView imgBanner = (ImageView) findViewById(R.id.imgBanner);
                        TextView tvDeliveryMode = (TextView) findViewById(R.id.tvDeliveryMode);
                        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
                        CollapsingToolbarLayout bar = (CollapsingToolbarLayout) findViewById(R.id.ctlToolbar);

                        String imagePath = info.getImage_path();
                        String shopIconUrl = UrlUtil.getImageUrlFromPath(UrlUtil.SHOP_URL, imagePath, true);
                        Picasso.with(DinnerOrderActivity.this).load(shopIconUrl).into(imgIcon);

                        String shopBackUrl = UrlUtil.getImageUrlFromPath(UrlUtil.SHOP_BACK_URL, imagePath, true);
                        Picasso.with(DinnerOrderActivity.this).load(shopBackUrl).into(imgBanner);

                        TextView tvIsPremium = (TextView) findViewById(R.id.tvIsPremium);
                        if (info.is_premium()) {
                            tvIsPremium.setVisibility(VISIBLE);
                        } else {
                            tvIsPremium.setVisibility(View.GONE);
                        }

                        String strDelivery = "";
                        if (mode != null) {

                            //动态设置渐变色
                            GradientDrawable background = (GradientDrawable) tvDeliveryMode.getBackground();
                            int startColor = Color.parseColor("#" + mode.getGradient().getRgb_from());
                            int color = Color.parseColor("#" + mode.getColor());
                            int endColor = Color.parseColor("#" + mode.getGradient().getRgb_to());
                            int[] colors = {startColor, color, endColor};
                            background.setColors(colors);
                            strDelivery += mode.getText();
                        } else {
                            strDelivery += "商家配送";
                        }

                        String leadTime = info.getOrder_lead_time();
                        if (leadTime != null && !"".equals(leadTime)) {
                            strDelivery += "·约" + leadTime + "分钟";
                        }

                        tvDeliveryMode.setText(strDelivery);

                        String prompt = info.getPromotion_info();
                        if (prompt == null || "".equals(prompt)) {
                            prompt = "欢迎光临，用餐高峰期请提前下单，谢谢。";
                        }
                        tvWelcome.setText("公告：" + prompt);
                        bar.setTitle(info.getName());
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
                        categoryAdapter.setOnItemClickListener(DinnerOrderActivity.this);

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
            int top = rvDish.getChildAt(pos - firstItem).getTop();
            rvDish.scrollBy(0, top - DisplayUtil.dip2px(this, 28));
        } else {
            rvDish.scrollToPosition(pos);
            movePosition = pos;
            needMove = true;
        }
    }

    public void refreshCategorySelectedCount(String categoryId, int num) {
        int sum = 0;
        for (Category category :
                listCategory) {

            if (categoryId.equals(category.getId())) {
                category.setSelectedCount(category.getSelectedCount() + num);
            }
            sum += category.getSelectedCount();
        }

        if (sum > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(sum));
        } else {
            tvCartCount.setVisibility(View.GONE);
        }

        categoryAdapter.notifyDataSetChanged();
    }

    //动画层
    private ViewGroup anim_mask_layout;

    /*
     * 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(params);
        animLayout.setId(Integer.MAX_VALUE - 1);
        /*animLayout.setBackgroundResource(android.R.color.holo_purple);
        animLayout.setAlpha(0.3f);*/
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(ViewGroup parent, View view, int[] location) {
        int x = location[0];
        int y = location[1];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        view.setLayoutParams(params);
        return view;
    }

    public void setAnim(final View _view, int[] startLocation) {
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(_view);
        //startLocation=new int[]{0,0};
        View view = addViewToAnimLayout(anim_mask_layout, _view, startLocation);
        int[] endLocation = new int[2];  //存储动画结束为止的X、Y坐标
        imgCart.getLocationInWindow(endLocation);
        //imgCart.getLocationOnScreen(endLocation);

        //计算位移
        int endX = endLocation[0] - startLocation[0];
        int endY = endLocation[1] - startLocation[1];

        TranslateAnimation translateAnimX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimX.setInterpolator(new LinearInterpolator());
        translateAnimX.setRepeatCount(0);
        translateAnimX.setFillAfter(true);

        TranslateAnimation translateAnimY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimY.setInterpolator(new AccelerateInterpolator());
        translateAnimY.setRepeatCount(0);
        translateAnimY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimX);
        set.addAnimation(translateAnimY);
        set.setDuration(500);

        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                _view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                _view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
