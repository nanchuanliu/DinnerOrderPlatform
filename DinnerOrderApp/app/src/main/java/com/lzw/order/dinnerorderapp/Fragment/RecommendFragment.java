package com.lzw.order.dinnerorderapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzw.order.dinnerorderapp.R;
import com.lzw.order.dinnerorderapp.component.ScrollViewAdapter;
import com.lzw.order.dinnerorderapp.component.ScrollViewIndicator;
import com.lzw.order.dinnerorderapp.component.ScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {

    private int[] imgIds={R.drawable.djyt,R.drawable.hgr,R.drawable.mpdf};

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recommend, container, false);

        ScrollViewPager vpBanner=(ScrollViewPager)view.findViewById(R.id.vpBanner);
        vpBanner.setAdapter(new ScrollViewAdapter(imgIds));

        LinearLayout layout=(LinearLayout)view.findViewById(R.id.indicatorLayout);
        vpBanner.setOnPageChangeListener(new ScrollViewIndicator(this.getActivity(),vpBanner,layout,imgIds.length));

        return view;
    }

}
