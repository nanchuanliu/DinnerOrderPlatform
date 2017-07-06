package com.lzw.order.dinnerorderapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.squareup.picasso.Picasso;


/**
 * Created by Administrator on 2017/7/4.
 */

public class IntroActivity extends AppIntro2 {

    private  int[] imgIds = {R.drawable.djyt, R.drawable.hgr, R.drawable.mpdf};

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        for (final int id :
                imgIds) {
            Fragment fragment = new IntroFragment(id);
            addSlide(fragment);
        }
/*        addSlide(AppIntroFragment.newInstance("ces","描述",R.drawable.djyt,android.R.color.darker_gray));*/


        // OPTIONAL METHODS
        // Override bar/separator color.
/*        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);*/

        //setFadeAnimation();
        setZoomAnimation();
        //setFlowAnimation();
        //setSlideOverAnimation();
        //setDepthAnimation();

    }


    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
