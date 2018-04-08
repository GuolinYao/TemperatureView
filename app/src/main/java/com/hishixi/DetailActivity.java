package com.hishixi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by seamus on 2018/3/21 15:11
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_cus)
    TextView mTvCus;
    @BindView(R.id.tv_finish_time)
    TextView mTvFinishTime;
    @BindView(R.id.tv_length)
    TextView mTvLength;
    @BindView(R.id.tv_width)
    TextView mTvWidth;
    @BindView(R.id.tv_weightScale)
    TextView mTvWeightScale;
    @BindView(R.id.tv_temperature)
    TextView mTvTemperature;
    @BindView(R.id.tv_weight)
    TextView mTvWeight;
    @BindView(R.id.tv_roll)
    TextView mTvRoll;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_dye_group)
    TextView mTvDyeGroup;
    @BindView(R.id.tv_quality)
    TextView mTvQuality;
    @BindView(R.id.tv_shade)
    TextView mTvShade;
    @BindView(R.id.tv_mach_group)
    TextView mTvMachGroup;
    @BindView(R.id.tv_dye_no)
    TextView mTvDyeNo;
    @BindView(R.id.tv_mach_no)
    TextView mTvMachNo;
    @BindView(R.id.tv_total_time)
    TextView mTvTotalTime;
    @BindView(R.id.tv_addquality_time)
    TextView mTvAddqualityTime;
    @BindView(R.id.tv_handler_no)
    TextView mTvHandlerNo;
    @BindView(R.id.tv_owner)
    TextView mTvOwner;
    @BindView(R.id.tv_remarks)
    TextView mTvRemarks;
    private DyeEntity mDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        Intent intent = getIntent();
        mDetail = (DyeEntity) intent.getSerializableExtra("detail");
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("批号：" + mDetail.getLotid());
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initData() {
        mTvFinishTime.setText(mDetail.getLotdeliver());

        mTvCus.setText(mDetail.getLotcus());
        mTvLength.setText(mDetail.getLotlength());
        mTvWidth.setText(mDetail.getLotwidth());
        mTvWeightScale.setText(mDetail.getLotweightscale());

        mTvWeight.setText(mDetail.getLotweight());
        mTvRoll.setText(mDetail.getLotroll());
        mTvType.setText(mDetail.getLottype());
        mTvDyeGroup.setText(mDetail.getDyegroup());
        mTvQuality.setText(mDetail.getQuality());

        mTvShade.setText(mDetail.getShade());
        mTvMachGroup.setText(mDetail.getLotmachgroup());
        mTvDyeNo.setText(mDetail.getDyeno());
        mTvMachNo.setText(mDetail.getLotmach());

        mTvTotalTime.setText(mDetail.getSecondes());
        mTvAddqualityTime.setText(mDetail.getAddquality());
        mTvHandlerNo.setText(mDetail.getLothandler());
        mTvOwner.setText(mDetail.getLotown());

        mTvRemarks.setText("备注：" + mDetail.getRemarks());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.tv_temperature})
    void click(View v) {
        Intent intent = new Intent(this, TempeActivity.class);
        intent.putExtra("num", mDetail.getLotid());
        intent.putExtra("date", mDetail.getLotdeliver());
        intent.putExtra("time", mDetail.getStarttime());
        startActivity(intent);
    }
}
