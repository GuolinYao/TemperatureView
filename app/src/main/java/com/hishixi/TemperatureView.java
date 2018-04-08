package com.hishixi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 温度--时间折线图
 * Created by seamus on 2018/3/21 17:03
 */

public class TemperatureView extends View {

    private float mMaxTemp;
    private float mMinTemp;
    private int mLineColor;
    private Paint mLinePaint, mPointPaint, mAxisPaint, mFloatTextPaint, mDashLinePaint,
            mFloatBgPaint;
    private Paint mTextPaint;
    private int textAxisColor = 0xff7e7e7e, floatTextColor = 0xffffffff, floatBgColor2 =
            0xff2f6ad2, floatBgColor = 0xffda6156;
    private int viewWidth, viewHeight;
    private Path brokenPath;//折线path
    private Path animPath;//动画折线path
    private List<Point> tempPoints;//温度采样点
    private float mTempPerPx;//每度像素
    private float mTimePerPx;//每分像素
    private int currentIndex = 0;
    private PathMeasure mPathMeasure;
    private float mAverageTemp;
    private float mAverageTime;
    private boolean isAnimRunning = false;

    public TemperatureView(Context context) {
        super(context);
        initConfig(context, null);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initConfig(context, attrs);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
        init();
    }

    /**
     * 初始化布局配置
     *
     * @param context context
     * @param attrs   attrs
     */
    @SuppressLint("CustomViewStyleable")
    private void initConfig(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.temperature);
        mMaxTemp = ta.getFloat(R.styleable.temperature_max_temp, 100);
        mMinTemp = ta.getFloat(R.styleable.temperature_min_temp, 0);
        mLineColor = ta.getColor(R.styleable.temperature_line_color, getResources().getColor(R
                .color.colorPrimary));
        ta.recycle();
    }

    private void init() {
        brokenPath = new Path();
        animPath = new Path();
        //温度折线的画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(mLineColor);

        //采样点虚线画笔
        mDashLinePaint = new Paint();
        mDashLinePaint.setAntiAlias(true);
        mDashLinePaint.setStyle(Paint.Style.STROKE);
        mDashLinePaint.setStrokeWidth(3);
        mDashLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mDashLinePaint.setColor(0xffb8b0b8);
        mDashLinePaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 10));

        //温度采样点的画笔
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(2f);
        mPointPaint.setColor(mLineColor);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);

        //坐标轴画笔
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.FILL);
        mAxisPaint.setColor(textAxisColor);
        mAxisPaint.setStrokeWidth(2f);

        //坐标轴文字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(20);
        mTextPaint.setColor(textAxisColor);

        //悬浮温度文字
        mFloatTextPaint = new Paint();
        mFloatTextPaint.setAntiAlias(true);
        mFloatTextPaint.setStyle(Paint.Style.FILL);
        mFloatTextPaint.setTextAlign(Paint.Align.CENTER);
        mFloatTextPaint.setTextSize(dipToPx(16));
        mFloatTextPaint.setColor(floatTextColor);

        //悬浮温度文字
        mFloatBgPaint = new Paint();
        mFloatBgPaint.setAntiAlias(true);
        mFloatBgPaint.setStyle(Paint.Style.FILL);
        mFloatBgPaint.setColor(floatBgColor);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxis(canvas);
        drawBrokenLine(canvas);
        drawPoint(canvas);
        drawDashLines(canvas);

    }

    /**
     * 画辅助线
     *
     * @param canvas canvas
     */
    private void drawDashLines(Canvas canvas) {
        if (tempPoints != null && tempPoints.size() > 0) {
            Point point = tempPoints.get(currentIndex);
            drawDashLine(canvas, 40, viewHeight - 40 - point.y * mTempPerPx / 10, viewWidth,
                    viewHeight - 40 - point.y * mTempPerPx / 10);
            drawDashLine(canvas, point.x * mTimePerPx + 40, 0, point.x * mTimePerPx + 40, viewHeight
                    - 40);
            drawFloatTemp(canvas, point, (float) point.y / 10 + "℃", point.x + "'");
        }
    }

    /**
     * 画坐标轴
     *
     * @param canvas 画布
     */
    private void drawAxis(Canvas canvas) {
        canvas.drawLine(40, viewHeight - 40, viewWidth, viewHeight - 40, mAxisPaint);//横轴
        canvas.drawLine(40, viewHeight - 40, 40, 0, mAxisPaint);//纵轴
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("温度（℃）", dipToPx(75), dipToPx(10), mTextPaint);//y坐标
        canvas.drawText("时间（分钟）", viewWidth, viewHeight - 60, mTextPaint);//x坐标


        for (int i = 0; i < 15; i++) {//纵轴坐标
            canvas.drawLine(40, viewHeight - 40 - i * mAverageTemp, 36, viewHeight - 40 - i *
                    mAverageTemp, mAxisPaint);//刻度
            //刻度文字
            mTextPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(i * 10 + "", 30, viewHeight - 40 - i * mAverageTemp + 8, mTextPaint);
        }

        for (int i = 1; i < 25; i++) {//横轴坐标
            canvas.drawLine(mAverageTime * i + 40, viewHeight - 35, mAverageTime * i + 40,
                    viewHeight - 40, mAxisPaint);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(5 * i + "", mAverageTime * i + 40, viewHeight - 10, mTextPaint);
        }

    }

    /**
     * 画悬浮温度
     *
     * @param canvas canvas
     */
    private void drawFloatTemp(Canvas canvas, Point point, String temp, String min) {

        float floatX = point.x * mTimePerPx + 40 + 65;
        float floatY = viewHeight - 40 - point.y * mTempPerPx / 10 + 20 - 40;
        float floatX2 = point.x * mTimePerPx + 40 + 65;
        float floatY2 = viewHeight - 40 - point.y * mTempPerPx / 10 + 20 + 25;
        mFloatBgPaint.setColor(floatBgColor);
        canvas.drawRoundRect(floatX - dipToPx(30), floatY - dipToPx(18), floatX + dipToPx(30),
                floatY + dipToPx(5), 10, 10,
                mFloatBgPaint);
        canvas.drawText(temp, floatX, floatY, mFloatTextPaint);
        mFloatBgPaint.setColor(floatBgColor2);
        canvas.drawRoundRect(floatX2 - dipToPx(30), floatY2 - dipToPx(18), floatX2 + dipToPx(30),
                floatY2 + dipToPx(5), 10, 10,
                mFloatBgPaint);
        canvas.drawText(min, floatX2, floatY2, mFloatTextPaint);
    }

    /**
     * 画虚线
     *
     * @param canvas canvas
     */
    private void drawDashLine(Canvas canvas, float startX, float startY, float stopX, float stopY) {
        Path path = new Path();
        path.reset();
        path.moveTo(startX, startY);
        path.lineTo(stopX, stopY);
        canvas.drawPath(path, mDashLinePaint);
    }

    /**
     * 绘制采样点
     *
     * @param canvas canvas
     */
    private void drawPoint(Canvas canvas) {
        for (int i = 0; i < tempPoints.size(); i++) {
            Point point = tempPoints.get(i);
            mPointPaint.setStyle(Paint.Style.STROKE);
            mPointPaint.setColor(mLineColor);
            canvas.drawCircle(point.x * mTimePerPx + 40, viewHeight - 40 - point.y * mTempPerPx /
                            10,
                    4f, mPointPaint);
            mPointPaint.setStyle(Paint.Style.FILL);
            mPointPaint.setColor(0xffffffff);
            canvas.drawCircle(point.x * mTimePerPx + 40, viewHeight - 40 - point.y * mTempPerPx /
                            10,
                    2f, mPointPaint);
        }
    }

    /**
     * 初始化折线path
     */
    private void initBrokenLinePath() {
        brokenPath.reset();
        if (tempPoints.size() == 0) {
            return;
        }
        brokenPath.moveTo(tempPoints.get(0).x * mTimePerPx + 40, viewHeight - 40 - tempPoints.get
                (0).y * mTempPerPx / 10);
        for (int i = 1; i < tempPoints.size(); i++) {
            brokenPath.lineTo(tempPoints.get(i).x * mTimePerPx + 40, viewHeight - 40 - tempPoints
                    .get(i).y * mTempPerPx / 10);
        }

    }

    private void drawBrokenLine(Canvas canvas) {
//        initBrokenLinePath();
        canvas.drawPath(animPath, mLinePaint);
    }

    /**
     * 设置温度折现动画
     */
    private void setBrokeLIneAnim() {

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(brokenPath, false);
        animPath.reset();
        animPath.moveTo(0, 0);

        float length = mPathMeasure.getLength();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                mPathMeasure.getSegment(0, mPathMeasure.getLength() * value,
                        animPath, true);
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimRunning = false;
            }
        });
        valueAnimator.start();
        isAnimRunning = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        initData();
        if (!isAnimRunning)
            setBrokeLIneAnim();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_UP:
                onActionUoEvent(event);
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return true;
    }

    private void onActionUoEvent(MotionEvent event) {
        boolean isValid = validateTouch(event.getX(), event.getY());
        if (isValid)
            invalidate();
    }

    /**
     * 检验是否点到采样点上
     *
     * @param x
     * @param y
     */
    private boolean validateTouch(float x, float y) {
        //曲线触摸区域
        for (int i = 0; i < tempPoints.size(); i++) {
            if (x > (tempPoints.get(i).x * mTimePerPx + 40 - dipToPx(8) * 2) && x < (tempPoints
                    .get(i).x * mTimePerPx + 40 + dipToPx(8) * 2)) {
                if (y > (viewHeight - 40 - tempPoints.get(i).y * mTempPerPx / 10 - dipToPx(8) *
                        2) && y < (viewHeight - 40 - tempPoints.get(i).y * mTempPerPx / 10 +
                        dipToPx(8) * 2)) {
                    currentIndex = i;
                    return true;
                }
            }
        }
        return false;
    }

    private void initData() {
        //温度每度平均像素
        mAverageTemp = (float) viewHeight / 17;
        mTempPerPx = mAverageTemp / 10;
        //时间每分钟平均像素
        mAverageTime = (float) viewWidth / 29;
        mTimePerPx = mAverageTime / 5;
        initBrokenLinePath();
    }

    public void setTemps(ArrayList<Point> pointArrayList) {
        tempPoints = pointArrayList;
        invalidate();
    }

    /**
     * dip 转换成px
     *
     * @param dip dp
     * @return int
     */
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }


}
