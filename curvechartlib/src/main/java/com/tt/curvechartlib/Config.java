package com.tt.curvechartlib;

import android.graphics.Color;

/**
 * Created by Kyson on 2016/10/1.
 */

public class Config {
    public static final int DEFAULT_X_TEXT_PADDING = 0;
    public static final int DEFAULT_Y_TEXT_PADDING = 0;
    public static final float DEFAULT_Y_MAX_MULTIPLE = 1.2f;
    public static final float DEFAULT_Y_MIN_MULTIPLE = 0.8f;
    public static final int DEFAULT_Y_PART_COUNT = 5;
    public static final int DEFAULT_DATA_SIZE = 30;
    public static final String DEFAULT_Y_FORMAT = "%.1f";
    public static final int DEFAULT_XY_COLOR = Color.GRAY;
    public static final float DEFAULT_XY_STROKE_WIDTH = 2f;
    public static final int DEFAULT_LINE_COLOR = Color.GRAY;
    public static final float DEFAULT_LINE_STROKE_WIDTH = 2f;
    public static final int DEFAULT_FILL_COLOR = Color.parseColor("#aa0000FF");
    public static final int DEFAULT_Y_LABEL_COLOR = Color.GRAY;
    public static final int DEFAULT_Y_LABEL_SIZE = 12;
    public static final int DEFAULT_GRADUATEDLINE_COLOR = Color.parseColor("#bbbbbb");
    public static final float DEFAULT_GRADUATEDLINE_STROKE_WIDTH = 1f;
    //    public static final String DEFAULT_Y_UNIT = "";

    //y横线颜色，可配置
    public int mGraduatedLineColor = DEFAULT_GRADUATEDLINE_COLOR;
    //y横线宽度，可配置
    public float mGraduatedLineStrokeWidth = DEFAULT_GRADUATEDLINE_STROKE_WIDTH;
    //Y轴文字颜色，可配置
    public int mYLabelColor = DEFAULT_Y_LABEL_COLOR;
    //Y轴文字大小，可配置
    public float mYLabelSize = DEFAULT_Y_LABEL_SIZE;
    //X上的文字的间距（Y轴上），可配置
    public int mXTextPadding = DEFAULT_X_TEXT_PADDING;
    //Y上的文字的间距（X轴上），可配置
    public int mYTextPadding = DEFAULT_Y_TEXT_PADDING;
    //Y轴最大值相当于当前数据最大值的倍数(>1)，可配置
    public float mMaxValueMulti = DEFAULT_Y_MAX_MULTIPLE;
    //Y轴最小值相当于当前数据最小值的倍数（<1），可配置
    public float mMinValueMulti = DEFAULT_Y_MIN_MULTIPLE;
    //Y 刻度线数量，可配置
    public int mYPartCount = DEFAULT_Y_PART_COUNT;
    // 横轴上最多的数据个数，可配置
    public int mDataSize = DEFAULT_DATA_SIZE;
    // 纵轴单位，可配置
//    public String mYUnitName = DEFAULT_Y_UNIT;
    //纵轴的文字格式
    public String mYFormat = DEFAULT_Y_FORMAT;
    //横纵轴颜色，可配置
    public int mXYColor = DEFAULT_XY_COLOR;
    //横纵轴线宽，可配置
    public float mXYStrokeWidth = DEFAULT_XY_STROKE_WIDTH;
    //曲线颜色，可配置
    public int mLineColor = DEFAULT_LINE_COLOR;
    //曲线线宽，可配置
    public float mLineStrokeWidth = DEFAULT_LINE_STROKE_WIDTH;
    //填充颜色，可配置
    public int mFillColor = DEFAULT_FILL_COLOR;

    public static class Builder {
        private Config mConfig;

        public Builder() {
            mConfig = new Config();
        }

        public Builder setXTextPadding(int XTextPadding) {
            mConfig.mXTextPadding = XTextPadding;
            return this;
        }

        public Builder setYTextPadding(int YTextPadding) {
            mConfig.mYTextPadding = YTextPadding;
            return this;
        }

        public Builder setMaxValueMulti(float maxValueMulti) {
            mConfig.mMaxValueMulti = maxValueMulti;
            return this;
        }

        public Builder setMinValueMulti(float minValueMulti) {
            mConfig.mMinValueMulti = minValueMulti;
            return this;
        }

        public Builder setYPartCount(int YPartCount) {
            mConfig.mYPartCount = YPartCount;
            return this;
        }

        public Builder setDataSize(int dataSize) {
            mConfig.mDataSize = dataSize;
            return this;
        }

//        public Builder setYUnitName(String YUnitName) {
//            mConfig.mYUnitName = YUnitName;
//            return this;
//        }

        public Builder setYFormat(String format) {
            mConfig.mYFormat = format;
            return this;
        }

        public Builder setXYColor(int XYColor) {
            mConfig.mXYColor = XYColor;
            return this;
        }

        public Builder setXYStrokeWidth(float XYStrokeWidth) {
            mConfig.mXYStrokeWidth = XYStrokeWidth;
            return this;
        }

        public Builder setLineColor(int lineColor) {
            mConfig.mLineColor = lineColor;
            return this;
        }

        public Builder setLineStrokeWidth(float lineStrokeWidth) {
            mConfig.mLineStrokeWidth = lineStrokeWidth;
            return this;
        }

        public Builder setFillColor(int fillColor) {
            mConfig.mFillColor = fillColor;
            return this;
        }

        public Builder setYLabelColor(int yLabelColor) {
            mConfig.mYLabelColor = yLabelColor;
            return this;
        }

        public Builder setYLabelSize(float yLabelSize) {
            mConfig.mYLabelSize = yLabelSize;
            return this;
        }

        public Builder setGraduatedLineColor(int graduatedLineColor) {
            mConfig.mGraduatedLineColor = graduatedLineColor;
            return this;
        }

        public Builder setGraduatedStrokeWidth(float graduatedLineStrokeWidth) {
            mConfig.mGraduatedLineStrokeWidth = graduatedLineStrokeWidth;
            return this;
        }

        public Config create() {
            return mConfig;
        }
    }

}
