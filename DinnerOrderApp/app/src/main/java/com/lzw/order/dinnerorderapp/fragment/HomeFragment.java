package com.lzw.order.dinnerorderapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzw.order.dinnerorderapp.Bean.LocationInfo;
import com.lzw.order.dinnerorderapp.Bean.WeatherInfo;
import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.component.IndicatorAdapter;
import com.lzw.order.dinnerorderapp.library.TabPageIndicator;
import com.lzw.order.dinnerorderapp.services.DataService;
import com.lzw.order.dinnerorderapp.utils.UrlUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

import rx.schedulers.Schedulers;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LZW on 2017/07/05.
 */
public class HomeFragment extends Fragment  {

    private static final String tag="HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentPagerAdapter adapter = new IndicatorAdapter(getActivity().getSupportFragmentManager());
        ViewPager pager = (ViewPager) view.findViewById(R.id.vp_pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) view.findViewById(R.id.vpi_indicator);
        indicator.setViewPager(pager);

        //SVG颜色设置
/*        VectorDrawableCompat compat=VectorDrawableCompat.create(getResources(),R.drawable.ic_si_glyph_pin_location,getActivity().getTheme());
        compat.setTint(getResources().getColor(android.R.color.white));
        ImageView imageView=(ImageView)view.findViewById(R.id.imgLocation);
        imageView.setImageDrawable(compat);*/

        final TextView tvTemp = (TextView) view.findViewById(R.id.tvTemp);
        final TextView tvWeather = (TextView) view.findViewById(R.id.tvWeather);
        final ImageView imgView=(ImageView)view.findViewById(R.id.imgWeather);

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
                        tvTemp.setText(info.getTemperature()+"℃");
                        tvWeather.setText(info.getDescription());
                        String path=info.getImage_hash();
                        String url=UrlUtil.getImageUrlFromPath(path);
                        Picasso.with(getContext()).load(url).into(imgView);
                    }
                });
        return view;
    }

}
