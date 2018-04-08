package com.hishixi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hishixi.wheel.DateWheelDialog;
import com.hishixi.wheel.DateWheelFrameLayout;
import com.hishixi.wheel.SingleWheelDialog;
import com.hishixi.wheel.SingleWheelFrameLayout;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * Created by seamus on 2018/3/19 14:00
 */

public class IndexActivity extends AppCompatActivity implements IndexAdapter.OnItemClickLitener {
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private List<DyeEntity> list;//批次结果
    private String mLotId = "", mStart = "2000-01-01", mEnd = "2200-12-31";
    private String mCurrentDate;
    private DateWheelDialog mDateWheelDialog;
    private DateWheelFrameLayout mDateWheelFrameLayout;
    private String[] mNumList;
    private SingleWheelDialog mNumWheelDialog;
    private SingleWheelFrameLayout mNumWheelFrameLayout;
    private DBManager mDbManager;
    private String[] mColumns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("批号查询");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));

    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mCurrentDate = year + "-" + (month + 1) + "-" + day;
        mDateWheelDialog = new DateWheelDialog(this, year, month, day);
        mDateWheelFrameLayout = mDateWheelDialog
                .getmDateWheelFrameLayout();
        mDateWheelFrameLayout.setEndYear(year + 5);

        mDbManager = new DBManager(this);
        mNumList = mDbManager.getNumList(mDbManager.initDB(getPackageName()));

        mColumns = new String[]{"lotid", "lotcus", "lotweight", "lotlength",
                "lotweightscale", "lotwidth", "lotroll", "lottype", "dyegroup", "quality",
                "shade", "lotmachgroup", "dyeno", "lotmach", "secondes", "addquality",
                "lothandler", "lotdeliver", "lotown", "remarks","starttime"};
        SQLiteDatabase sqLiteDatabase = mDbManager.initDB(getPackageName());
        list = mDbManager.query(sqLiteDatabase, mColumns, null, null);

        IndexAdapter indexAdapter = new IndexAdapter(this, list);
        indexAdapter.setOnItemClickLitener(this);
        mRecyclerView.setAdapter(indexAdapter);
        File file = new File("data/data/com.hishixi/Dyeing.db");
        if (file.exists()) {
            Log.e("db", "exist");
        }
    }

    private void search() {
        String[] selectionArgs = new String[3];
        String selection = "lotid  LIKE ? and lotdeliver>=? and lotdeliver<=?";//

        SQLiteDatabase sqLiteDatabase = mDbManager.initDB(getPackageName());
        if (mLotId != null || mStart != null || mEnd != null) {
            selectionArgs[0] = "%" + mLotId + "%";
            selectionArgs[1] = mStart;
            selectionArgs[2] = mEnd;
            list = mDbManager.query(sqLiteDatabase, mColumns, selection, selectionArgs);
        } else {
            list = mDbManager.query(sqLiteDatabase, mColumns, null, null);
        }

        IndexAdapter indexAdapter = new IndexAdapter(this, list);
        indexAdapter.setOnItemClickLitener(this);
        mRecyclerView.setAdapter(indexAdapter);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.tv_num, R.id.tv_start, R.id.tv_end, R.id.btn_search, R.id.btn_reset})
    void click(View v) {

        switch (v.getId()) {
            case R.id.tv_num:
                mNumWheelDialog = new SingleWheelDialog(this, mNumList, 0);
                mNumWheelFrameLayout = mNumWheelDialog.getmSingleWheelFrameLayout();
                mNumWheelDialog.show();
                setDateCallBack2(mNumWheelFrameLayout);
                break;
            case R.id.tv_start:
                mDateWheelDialog.show();
                setDateCallBack(mDateWheelFrameLayout, 1);
                break;
            case R.id.tv_end:
                mDateWheelDialog.show();
                setDateCallBack(mDateWheelFrameLayout, 2);
                break;
            case R.id.btn_search:
                mProgressBar.setVisibility(View.VISIBLE);
                search();
                break;
            case R.id.btn_reset:
                mTvNum.setText("不限");
                mTvStart.setText("年月日");
                mTvEnd.setText("年月日");
                mStart = "0";
                mEnd = "2200年12月31日";
                mLotId = "";
                search();
                break;
        }
    }

    private void setDateCallBack2(SingleWheelFrameLayout numWheelFrameLayout) {
        numWheelFrameLayout.setOnDataChangedListener(new SingleWheelFrameLayout
                .OnSelectChangedListener() {

            @Override
            public void onSelectChanged(String currentItem, int currentIndex) {
                mTvNum.setText(currentItem);
                if (currentIndex > 0)
                    mLotId = currentItem;
                else mLotId = "";
                mNumWheelDialog.cancel();
            }

            @Override
            public void onCancel() {
                mNumWheelDialog.cancel();
            }
        });
    }

    /**
     * 设置选择日期后的回调
     *
     * @param mDateWheelFrameLayout layout
     */
    public void setDateCallBack(DateWheelFrameLayout mDateWheelFrameLayout, int type) {
        mDateWheelFrameLayout.setOnDateChangedListener(new DateWheelFrameLayout
                .OnDateChangedListener() {

            @Override
            public void onDateChanged(String year, String month,
                                      String day) {
                if (month.length() == 1)
                    month = "0" + month;
                if (day.length() == 1)
                    day = "0" + day;
                if (type == 1) {
                    mStart = year + "-" + month + "-" + day;
                    mTvStart.setText(year + "." + month + "." + day);
                } else {
                    mEnd = year + "-" + month + "-" + day;
                    mTvEnd.setText(year + "." + month + "." + day);
                }
                mDateWheelDialog.cancel();
            }

            @Override
            public void onCancel() {
                mDateWheelDialog.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.index_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                CacheUtils.saveIfLogin(this, "0");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Snackbar.make(mRootView, "" + position, LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("detail", list.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
