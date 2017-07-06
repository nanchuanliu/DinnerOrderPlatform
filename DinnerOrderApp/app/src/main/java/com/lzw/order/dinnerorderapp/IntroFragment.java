package com.lzw.order.dinnerorderapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {

    public int imageId;

    public IntroFragment()
    {

    }

    public IntroFragment(int id)
    {
        this.imageId=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_intro, container, false);
        ImageView image=(ImageView) view.findViewById(R.id.imgIntro);
        Picasso.with(getActivity()).load(imageId).into(image);

        return view;
    }

}
