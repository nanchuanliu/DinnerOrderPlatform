package com.lzw.order.dinnerorderapp.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.lzw.order.dinnerorderapp.R;

/**
 * Created by Administrator on 2017/7/22.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int orientation= LinearLayout.VERTICAL;
    private int size=1;
    private Paint paint;

    public DividerItemDecoration(Context context,int orientation)
    {
        this.orientation=orientation;
        size=(int) TypedValue.applyDimension(size,TypedValue.COMPLEX_UNIT_DIP,context.getResources().getDisplayMetrics());
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources=context.getResources();
        paint.setColor(resources.getColor(R.color.colorIndicatorD));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(orientation== LinearLayoutManager.VERTICAL)
        {
            drawVertical(c,parent);
        }else
        {
            drawHorizontal(c,parent);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int top=parent.getPaddingTop();
        int bottom=parent.getMeasuredHeight()-parent.getPaddingBottom();
        int childSize=parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params=(RecyclerView.LayoutParams)child.getLayoutParams();
            int left=child.getRight()+params.rightMargin;
            int right=left+size;
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int left=parent.getPaddingLeft();
        int right=parent.getMeasuredWidth()-parent.getPaddingRight();
        int childSize=parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params=(RecyclerView.LayoutParams)child.getLayoutParams();
            int top=child.getBottom()+params.bottomMargin;
            int bottom=top+size;
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }

    /*
     *设置item分割线的size
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(orientation==LinearLayoutManager.VERTICAL)
        {
            outRect.set(0,0,0,size);
        }else
        {
            outRect.set(0,0,size,0);
        }
    }
}
