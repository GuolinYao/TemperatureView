package com.hishixi.wheel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hishixi.BaseApp;
import com.hishixi.DateUtils;
import com.hishixi.R;

/**
 * 年月日
 * @author guolin
 * @date:2015-10-28 下午5:08:30
 */
public class DateWheelDialog extends Dialog implements View.OnClickListener {
    private DateWheelFrameLayout mDateWheelFrameLayout;

    public DateWheelDialog(Context context, int year, int month, int day) {
        super(context, R.style.MMTheme);
        mDateWheelFrameLayout = new DateWheelFrameLayout(context, year, month, day,
                DateUtils.getCurrentYear());
    }
    public DateWheelDialog(Context context, int year, int month) {
        super(context, R.style.MMTheme);
        mDateWheelFrameLayout = new DateWheelFrameLayout(context, year, month,
                DateUtils.getCurrentYear());
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mDateWheelFrameLayout);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                AnimatorSet mAnimatorSet = new AnimatorSet();
                ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat
                        (mDateWheelFrameLayout, "translationY", 200, 0).setDuration
                        (300);
                ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat
                        (mDateWheelFrameLayout, "alpha", 0, 1).setDuration
                        (300);

                mAnimatorSet.playTogether(translationYAnimator, fadeAnimator);
                mAnimatorSet.start();
            }
        });
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();

        WindowManager manager = (WindowManager)  BaseApp.mApp
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        lp.width =  display.getWidth();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(lp);
    }

    public DateWheelFrameLayout getmDateWheelFrameLayout() {
        return mDateWheelFrameLayout;
    }

    public void setmDateWheelFrameLayout(DateWheelFrameLayout mDateWheelFrameLayout) {
        this.mDateWheelFrameLayout = mDateWheelFrameLayout;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
