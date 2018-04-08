package com.hishixi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置透明状态栏,这样才能让 ContentView 向上
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        if (CacheUtils.getIfCopy(this).equals("0")) {//如果未复制数据库文件
            Intent intent = new Intent(this.getApplicationContext(), CopyDBIntentService.class);
            startService(intent);
        }
        Handler mHandler = new Handler();
        mHandler.postDelayed(() -> {
            delayedJump();
            finish();
        }, 3000);


    }

    public void delayedJump() {
        String ifLogin = CacheUtils.getIfLogin(this);
        if (ifLogin.equals("0")) {//未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, IndexActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void finish() {
        super.finish();
        finishTransition();
    }

    public void finishTransition() {
        overridePendingTransition(R.anim.push_fade_in, R.anim.push_fade_out);
    }
}
