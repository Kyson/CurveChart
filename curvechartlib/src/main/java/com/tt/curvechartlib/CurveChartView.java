package com.tt.curvechartlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <曲线图>
 * Author: Kyson
 * Date: 2016/9/28
 * Copyright: Ctrip
 * //TODO 保留Y轴的有效数字
 * //TODO 画横线
 * // TODO 文字大小
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

    private Paint mXyPaint;
    private Paint mLinePaint;
    private Paint mFillPaint;
    private Path mLinePath;
    private Path mFillPath;

    private void initWaveView(Context context, AttributeSet attrs) {
        mXyPaint = new Paint();
        mXyPaint.setStyle(Paint.Style.STROKE);
        mXyPaint.setAntiAlias(true);
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setAntiAlias(true);
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
                    .setYUnitName(a.getString(R.styleable.CurveChartView_ccv_YUnitName))
                    .setXYColor(a.getColor(R.styleable.CurveChartView_ccv_XYColor, Config.DEFAULT_XY_COLOR))
                    .setXYStrokeWidth(a.getFloat(R.styleable.CurveChartView_ccv_XYStrokeWidth, Config.DEFAULT_XY_STROKE_WIDTH))
                    .setLineColor(a.getColor(R.styleable.CurveChartView_ccv_LineColor, Config.DEFAULT_LINE_COLOR))
                    .setLineStrokeWidth(a.getFloat(R.styleable.CurveChartView_ccv_LineStrokeWidth, Config.DEFAULT_LINE_STROKE_WIDTH))
                    .setFillColor(a.getColor(R.styleable.CurveChartView_ccv_FillColor, Config.DEFAULT_FILL_COLOR));
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
        mXyPaint.setColor(mConfig.mXYColor);
        mXyPaint.setStrokeWidth(mConfig.mXYStrokeWidth);
        mLinePaint.setColor(mConfig.mLineColor);
        mLinePaint.setStrokeWidth(mConfig.mLineStrokeWidth);
        mFillPaint.setColor(mConfig.mFillColor);
    }

    public void addData(float data) {
        if (mDatas.size() > mConfig.mDataSize) {
            mDatas.remove(0);
        }
        mDatas.add(data);
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
        // 画Y轴
        canvas.drawLine(getXPoint(), getPaddingTop(), getXPoint(), getYPoint(), mXyPaint);
        // 画X轴
        canvas.drawLine(getXPoint(), getYPoint(), getWidth() - getPaddingRight(), getYPoint(), mXyPaint);
    }

    /**
     * 画刻度和文字
     */
    private void drawScaleLabel(Canvas canvas) {
        createYText();
        int yIntervalLen = getYLen() / (mConfig.mYPartCount - 1);
        for (int i = 0; i < mConfig.mYPartCount; i++) {
            int scaleY = getYPoint() - yIntervalLen * i;
            // 刻度
            canvas.drawLine(getXPoint(), scaleY, getXPoint() + 5, scaleY, mXyPaint);
            // 文字
            canvas.drawText(mYLabels[i], getPaddingLeft(), scaleY, mXyPaint);
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
        canvas.drawPath(mLinePath, mLinePaint);
        canvas.drawPath(mFillPath, mFillPaint);
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
        for (int i = 0; i < mConfig.mYPartCount; i++) {
            mYLabels[i] = String.format("%.1f%s", (((yMaxValue - yMinValue) * i) / (mConfig.mYPartCount - 1)) + yMinValue, mConfig.mYUnitName);
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
        return (float) getXLen() / mConfig.mDataSize;
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
