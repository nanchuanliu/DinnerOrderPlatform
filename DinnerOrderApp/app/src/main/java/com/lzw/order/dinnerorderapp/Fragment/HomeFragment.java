package com.lzw.order.dinnerorderapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.component.IndicatorAdapter;
import com.lzw.order.dinnerorderapp.library.TabPageIndicator;

/**
 * Created by LZW on 2017/07/05.
 */
public class HomeFragment extends Fragment {

    private static final String tag="HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home,container,false);

        FragmentPagerAdapter adapter=new IndicatorAdapter(getActivity().getSupportFragmentManager());
        ViewPager pager=(ViewPager)view.findViewById(R.id.vp_pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator=(TabPageIndicator)view.findViewById(R.id.vpi_indicator);
        indicator.setViewPager(pager);

        return view;
    }
}
