package com.lzw.order.dinnerorderapp.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by LZW on 2017/07/13.
 */

public class DisplayUtil {
    //动态获取View宽度
    public static int getDynamicViewWidth(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        return view.getMeasuredWidth();
    }

    //动态获取View宽度
    public static int getDynamicViewHeight(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        return view.getMeasuredHeight();
    }


    //获得手机屏幕尺寸
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * dp转成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return(int) (dpValue * scale + 0.5f);
    }
}
