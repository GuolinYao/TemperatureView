package com.hishixi;

import android.app.Application;

/**
 * Created by seamus on 2018/3/21 13:44
 */

public class BaseApp extends Application {
    public static BaseApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
