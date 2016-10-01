package com.tt.curvechartlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <曲线图>
 * Author: Kyson
 * Date: 2016/9/28
 * Copyright: Ctrip
 */
public class CurveChartView extends View {
    private Config mConfig;
    //y轴最大值，绘制之前设置
    private float yMaxValue = Integer.MIN_VALUE;
    //y轴最小值（原点值），绘制之前设置
    private float yMinValue = Integer.MAX_VALUE;
    //纵轴的文字（个数是Y轴的刻度数），绘制之前设置
    private String[] mYLabels;
    //数据
    private List<Float> mDatas = new ArrayList<Float>();

    public CurveChartView(Context context) {
        this(context, null);
    }

    public CurveChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWaveView(context, attrs);
    }

    private Path mLinePath;
    private Path mFillPath;
    private Paint mPaint;

    private void initWaveView(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mLinePath = new Path();
        mFillPath = new Path();
        Config config;
        if (attrs == null) {
            config = new Config();
        } else {
            //从attr中解析出配置
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.CurveChartView);
            Config.Builder builder = new Config.Builder()
                    .setXTextPadding(a.getInteger(R.styleable.CurveChartView_ccv_XTextPadding, Config.DEFAULT_X_TEXT_PADDING))
                    .setYTextPadding(a.getInteger(R.styleable.CurveChartView_ccv_YTextPadding, Config.DEFAULT_Y_TEXT_PADDING))
                    .setMaxValueMulti(a.getFloat(R.styleable.CurveChartView_ccv_MaxValueMulti, Config.DEFAULT_Y_MAX_MULTIPLE))
                    .setMinValueMulti(a.getFloat(R.styleable.CurveChartView_ccv_MinValueMulti, Config.DEFAULT_Y_MIN_MULTIPLE))
                    .setYPartCount(a.getInteger(R.styleable.CurveChartView_ccv_YPartCount, Config.DEFAULT_Y_PART_COUNT))
                    .setDataSize(a.getInteger(R.styleable.CurveChartView_ccv_DataSize, Config.DEFAULT_DATA_SIZE))
                    .setYFormat(a.getString(R.styleable.CurveChartView_ccv_YFormat))
                    .setXYColor(a.getColor(R.styleable.CurveChartView_ccv_XYColor, Config.DEFAULT_XY_COLOR))
                    .setXYStrokeWidth(a.getFloat(R.styleable.CurveChartView_ccv_XYStrokeWidth, Config.DEFAULT_XY_STROKE_WIDTH))
                    .setLineColor(a.getColor(R.styleable.CurveChartView_ccv_LineColor, Config.DEFAULT_LINE_COLOR))
                    .setLineStrokeWidth(a.getFloat(R.styleable.CurveChartView_ccv_LineStrokeWidth, Config.DEFAULT_LINE_STROKE_WIDTH))
                    .setFillColor(a.getColor(R.styleable.CurveChartView_ccv_FillColor, Config.DEFAULT_FILL_COLOR))
                    .setYLabelColor(a.getColor(R.styleable.CurveChartView_ccv_YLabelColor, Config.DEFAULT_Y_LABEL_COLOR))
                    .setYLabelSize(a.getFloat(R.styleable.CurveChartView_ccv_YLabelSize, Config.DEFAULT_Y_LABEL_SIZE))
                    .setGraduatedLineColor(a.getColor(R.styleable.CurveChartView_ccv_GraduatedLineColor, Config.DEFAULT_GRADUATEDLINE_COLOR))
                    .setGraduatedStrokeWidth(a.getFloat(R.styleable.CurveChartView_ccv_GraduatedLineStrokeWidth, Config.DEFAULT_GRADUATEDLINE_STROKE_WIDTH));
            a.recycle();
            config = builder.create();
        }
        setUp(config);
    }

    public void setUp(Config config) {
        if (config == null) {
            return;
        }
        this.mConfig = config;
        mYLabels = new String[mConfig.mYPartCount];
        mDatas.clear();
    }

    public void addData(float data) {
        mDatas.add(data);
        if (mDatas.size() > mConfig.mDataSize) {
            mDatas.remove(0);
        }
        prepareData();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY(canvas);
        int size = mDatas.size();
        if (size == 0) {
            return;
        }
        drawLine(canvas, size);
        drawScaleLabel(canvas);
    }

    /**
     * 画xy轴
     *
     * @param canvas
     */
    private void drawXY(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mConfig.mXYColor);
        mPaint.setStrokeWidth(mConfig.mXYStrokeWidth);
        // 画Y轴
        canvas.drawLine(getXPoint(), getPaddingTop(), getXPoint(), getYPoint(), mPaint);
        // 画X轴
        canvas.drawLine(getXPoint(), getYPoint(), getWidth() - getPaddingRight(), getYPoint(), mPaint);
    }

    /**
     * 画刻度和文字
     */
    private void drawScaleLabel(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        createYText();
        int yIntervalLen = getYLen() / (mConfig.mYPartCount - 1);
        for (int i = 0; i < mConfig.mYPartCount; i++) {
            int scaleY = getYPoint() - yIntervalLen * i;
            mPaint.setColor(mConfig.mGraduatedLineColor);
            mPaint.setStrokeWidth(mConfig.mGraduatedLineStrokeWidth);
            // 刻度
            canvas.drawLine(getXPoint(), scaleY, getWidth() - getPaddingRight(), scaleY, mPaint);

            if (TextUtils.isEmpty(mConfig.mYFormat)) {
                continue;
            }
            mPaint.setColor(mConfig.mYLabelColor);
            mPaint.setStrokeWidth(0);
            mPaint.setTextSize(mConfig.mYLabelSize);
            // 文字
            canvas.drawText(mYLabels[i], getPaddingLeft(), scaleY, mPaint);
        }
    }

    private void drawLine(Canvas canvas, int size) {
        mLinePath.reset();
        mFillPath.reset();
        mLinePath.moveTo(getXPoint(), getYPoint() - (mDatas.get(0) - yMinValue) * getYLenPerValue());
        mFillPath.moveTo(getXPoint(), getYPoint());
        mFillPath.lineTo(getXPoint(), getYPoint() - (mDatas.get(0) - yMinValue) * getYLenPerValue());
        for (int i = 1; i < size; i++) {
            float value = mDatas.get(i);
            mLinePath.lineTo(getXPoint() + i * getXLenPerCount(), getYPoint() - (value - yMinValue) * getYLenPerValue());
            mFillPath.lineTo(getXPoint() + i * getXLenPerCount(), getYPoint() - (value - yMinValue) * getYLenPerValue());
        }
        mFillPath.lineTo(getXPoint() + (size - 1) * getXLenPerCount(), getYPoint());
        mFillPath.close();

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mConfig.mLineColor);
        mPaint.setStrokeWidth(mConfig.mLineStrokeWidth);
        canvas.drawPath(mLinePath, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mConfig.mFillColor);
        canvas.drawPath(mFillPath, mPaint);
    }

    /**
     * 准备数据
     */
    private void prepareData() {
        float maxValue = Integer.MIN_VALUE;
        float minValue = Integer.MAX_VALUE;
        final int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            float v = mDatas.get(i);
            if (v > maxValue) {
                maxValue = v;
            }
            if (v < minValue) {
                minValue = v;
            }
        }
        yMaxValue = mConfig.mMaxValueMulti * maxValue;
        yMinValue = mConfig.mMinValueMulti * minValue;
    }

    private void createYText() {
        if (TextUtils.isEmpty(mConfig.mYFormat)) {
            return;
        }
        for (int i = 0; i < mConfig.mYPartCount; i++) {
            mYLabels[i] = String.format(mConfig.mYFormat, ((yMaxValue - yMinValue) * i) / (mConfig.mYPartCount - 1) + yMinValue);
        }
    }

    /**
     * 获取Y每个value值多少像素
     *
     * @return
     */
    private float getYLenPerValue() {
        return (float) getYLen() / (yMaxValue - yMinValue);
    }

    /**
     * 每个数据的长度
     *
     * @return
     */
    private float getXLenPerCount() {
        return (float) getXLen() / (mConfig.mDataSize - 1);
    }

    /**
     * 原点X
     *
     * @return
     */
    private int getXPoint() {
        return getPaddingLeft() + mConfig.mXTextPadding;
    }

    /**
     * 原点Y
     *
     * @return
     */
    private int getYPoint() {
        return getHeight() - getPaddingBottom() - mConfig.mYTextPadding;
    }

    /**
     * 横轴长度
     *
     * @return
     */
    private int getXLen() {
        return getWidth() - getPaddingLeft() - getPaddingRight() - mConfig.mXTextPadding;
    }

    /**
     * 纵轴长度
     *
     * @return
     */
    private int getYLen() {
        return getHeight() - getPaddingBottom() - getPaddingTop() - mConfig.mYTextPadding;
    }
}
