package com.softdesign.devintensive.ui.view.behaviors;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.softdesign.devintensive.utils.ConstantManager;

/**
 * Created by vladimirpapin on 29.06.16.
 */
public class HeaderBehavior  extends CoordinatorLayout.Behavior<LinearLayout> {
    private static final String TAG = ConstantManager.TAG_PREFIX + "HeaderBehavior";

    private static final String MIN_SCROLL = "minScroll";
    private static final String CURRENT_PERCENT = "currentPercent";

    //Переменные для управления отступами LineatLayout
    private float mMinScroll, mMaxScroll;
    private float mMaxPaddingSize;
    private float mCurrentPaddingSize;

    //Переменные для вычисления % передвижения ScrollView
    private float mProcScroll;
    private float mScrollSize;

    //Переменные для управления отступом NestedScrollView
    CoordinatorLayout.LayoutParams mNestedScrollParam;
    private int mTopMarginMax;


    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        mMinScroll = 0; //Начальный минимальный уровень.
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {

        //Начальная инициализация переменных
        if (mMinScroll == 0) {
            mMinScroll = parent.getHeight() - dependency.getHeight();
            mMaxScroll = child.getY();
            mScrollSize = mMaxScroll - mMinScroll;

            mMaxPaddingSize = child.getPaddingTop();
            mNestedScrollParam = (CoordinatorLayout.LayoutParams)dependency.getLayoutParams();
            mTopMarginMax = mNestedScrollParam.topMargin;
        }

        //Вычисление в % перелистывание ScrollView
        // и пропорциональное изменение отступов.
        float check = mMaxScroll - child.getY();
        if (check < mScrollSize && check > 0) {
            mProcScroll = (100 * (mMaxScroll - child.getY())) / mScrollSize;
            mCurrentPaddingSize = mMaxPaddingSize - (int)((mMaxPaddingSize * mProcScroll) / 100);
            int tempTopMargin = mTopMarginMax - (int)((mTopMarginMax * mProcScroll) / 210);
//            Log.d(TAG, "Padding " + String.valueOf(mCurrentPaddingSize) + "View " + dependency.getPaddingTop());
            mNestedScrollParam.topMargin = tempTopMargin;
            dependency.setPadding(dependency.getPaddingLeft(), tempTopMargin, dependency.getPaddingRight(), dependency.getPaddingBottom());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                child.setPaddingRelative(0, (int)mCurrentPaddingSize, 0, (int)mCurrentPaddingSize);
            }
        }
        return true;
    }
}