package com.gjjg.camera_app_java;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

public class GridViewItem extends LinearLayout {

    public GridViewItem(Context context) {
        super(context);
    }

    public GridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}