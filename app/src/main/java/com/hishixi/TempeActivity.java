package com.hishixi;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 温度曲线
 * Created by seamus on 2018/3/21 16:03
 */

public class TempeActivity extends AppCompatActivity {

    @BindView(R.id.temp_view)
    TemperatureView mTempView;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    private DBManager mDbManager;
    private String mTime,mDate;
    private String tableName ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        Intent intent = getIntent();
        mTime = intent.getStringExtra("time");
        mDate = intent.getStringExtra("date");
        ActionBar actionBar = getSupportActionBar();
        tableName = intent.getStringExtra("num");
        if (actionBar != null) {
            actionBar.setTitle("批号：" + intent.getStringExtra("num"));
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initData() {
        mDbManager = new DBManager(this);
        ArrayList<Point> points = mDbManager.queryTemp(mDbManager.initDB(getPackageName()), new
                String[]{"time", "data"},tableName);
        mTempView.setTemps(points);
        mTvStartTime.setText(mDate+"\n"+mTime);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
