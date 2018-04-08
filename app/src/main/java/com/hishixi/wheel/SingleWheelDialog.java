package com.hishixi.wheel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hishixi.BaseApp;
import com.hishixi.R;


/**
 * @author guolin
 */
public class SingleWheelDialog extends Dialog implements View.OnClickListener {
    private SingleWheelFrameLayout mSingleWheelFrameLayout;

    public SingleWheelDialog(Context context, String[] arraylist, int selectIndex) {
        super(context, R.style.MMTheme);
        mSingleWheelFrameLayout = new SingleWheelFrameLayout(context, arraylist,
                selectIndex);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mSingleWheelFrameLayout);
        this.setOnShowListener(dialog -> {
            AnimatorSet mAnimatorSet = new AnimatorSet();
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat
                    (mSingleWheelFrameLayout, "translationY", 200, 0).setDuration
                    (300);
            ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat
                    (mSingleWheelFrameLayout, "alpha", 0, 1).setDuration
                    (300);

            mAnimatorSet.playTogether(translationYAnimator, fadeAnimator);
            mAnimatorSet.start();
        });
        this.setOnDismissListener(dialog -> {
            AnimatorSet mAnimatorSet = new AnimatorSet();
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat
                    (mSingleWheelFrameLayout, "translationY", 0, 500).setDuration
                    (300);
            ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat
                    (mSingleWheelFrameLayout, "alpha", 1, 0).setDuration
                    (300);

            mAnimatorSet.playTogether(translationYAnimator, fadeAnimator);
            mAnimatorSet.start();
        });
        this.setOnCancelListener(dialog -> {
            AnimatorSet mAnimatorSet = new AnimatorSet();
            ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat
                    (mSingleWheelFrameLayout, "translationY", 0, 500).setDuration
                    (300);
            ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat
                    (mSingleWheelFrameLayout, "alpha", 1, 0).setDuration
                    (300);

            mAnimatorSet.playTogether(translationYAnimator, fadeAnimator);
            mAnimatorSet.start();
        });
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        WindowManager manager = (WindowManager)  BaseApp.mApp
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        lp.width = display.getWidth();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(lp);
    }


    public SingleWheelFrameLayout getmSingleWheelFrameLayout() {
        return mSingleWheelFrameLayout;
    }

    public void setmSingleWheelFrameLayout(SingleWheelFrameLayout
                                                   mSingleWheelFrameLayout) {
        this.mSingleWheelFrameLayout = mSingleWheelFrameLayout;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
