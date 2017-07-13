package com.lzw.order.dinnerorderapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.order.dinnerorderapp.Bean.HotSearchWord;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.CommonUtil;
import com.lzw.order.dinnerorderapp.utils.DisplayUtil;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends Activity {

    private Location curLocation;
    private List<String> listHistory = new ArrayList<>();
    private TextView tvHistory;
    private RelativeLayout lineHistory;
    private SharedPreferences pref;
    private ImageView imgHistorySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        curLocation = (Location) bundle.get("curLocation");

        tvHistory = (TextView) findViewById(R.id.tvHistory);
        lineHistory = (RelativeLayout) findViewById(R.id.lineHistory);
        imgHistorySearch = (ImageView) findViewById(R.id.btnHistorySearch);
        pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String history = pref.getString("searchHistory", "");
        if (!"".equals(history)) {
            listHistory = new ArrayList<>(Arrays.asList(history.split(",")));
        }

        refreshSearchHistory(listHistory);
        refreshHotSearch();

        TextView tvSearch = (TextView) findViewById(R.id.tvSearch);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etSearch = (EditText) findViewById(R.id.etSearch);
                String value = etSearch.getText().toString();
                if ("".equals(value) || listHistory.contains(value)) {
                    return;
                }

                listHistory.add(0, value);

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("searchHistory", CommonUtil.Join(listHistory));
                editor.apply();
                refreshSearchHistory(listHistory);
            }
        });

        imgHistorySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listHistory.clear();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("searchHistory", CommonUtil.Join(listHistory));
                editor.apply();
                refreshSearchHistory(listHistory);
            }
        });
    }

    private void refreshSearchHistory(List<String> list) {

        if (list.size() == 0) {
            tvHistory.setVisibility(View.GONE);
            lineHistory.setVisibility(View.GONE);
            imgHistorySearch.setVisibility(View.GONE);
        } else {
            tvHistory.setVisibility(View.VISIBLE);
            lineHistory.setVisibility(View.VISIBLE);
            imgHistorySearch.setVisibility(View.VISIBLE);

            fillRelativeLayoutByView(lineHistory, listHistory);
        }
    }

    private void refreshHotSearch() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DataService service = retrofit.create(DataService.class);
        service.getHotSearchWords("wtw399ses7m2", curLocation.getLatitude(), curLocation.getLongitude())
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
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.gridHotSearch);

                        List<String> words = new ArrayList<String>();
                        for (HotSearchWord item :
                                info) {
                            words.add(item.getWord());
                            if (!"0".equals(item.getIs_highlight())) {
                                Toast.makeText(getApplicationContext(), item.getIs_highlight(), Toast.LENGTH_LONG).show();
                            }
                        }

                        fillRelativeLayoutByView(layout, words);

                    }

                });
    }

    private void fillRelativeLayoutByView(RelativeLayout layout, List<String> values) {
        layout.removeAllViews();

        int btnColor = getResources().getColor(R.color.colorIndicatorD);
        int totalWidth = DisplayUtil.getDisplayMetrics(SearchActivity.this).widthPixels - 10;
        int gridWidth = totalWidth;
        int marginL = 10, marginT = 0;
        int paddingLR = 20, paddingTB = 20;

        for (String value : values) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            TextView tv = new TextView(getApplicationContext());
            tv.setText(value);
            tv.setPadding(paddingLR, paddingTB, paddingLR, paddingTB);
            tv.setSingleLine();
            tv.setTextSize(16);
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(btnColor);
            params.setMargins(marginL, marginT, 10, 10);
            tv.setLayoutParams(params);
            //tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);

            int tvWidth = DisplayUtil.getDynamicViewWidth(tv);
            if (tvWidth + paddingLR > gridWidth - 50) {
                int tvHeight = DisplayUtil.getDynamicViewHeight(tv);
                marginT += tvHeight + paddingLR;
                marginL = 10;
                gridWidth = totalWidth;
                params.setMargins(marginL, marginT, 10, 10);
                tv.setLayoutParams(params);
            }

            marginL += tvWidth + paddingLR;
            gridWidth -= tvWidth + paddingLR;
            tv.setOnClickListener(searchListener);
            layout.addView(tv);
        }

    }

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String str = ((TextView) view).getText().toString();
            Intent intent = new Intent();
            intent.setClass(SearchActivity.this, RestaurantActivity.class);
            intent.putExtra("KeyWord", str);
            intent.putExtra("Location",curLocation);
            startActivity(intent);
            finish();
        }
    };
}
