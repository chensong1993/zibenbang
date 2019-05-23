/*
 * Copyright (C) 2017 Apache Open Source Project
 *
 *      https://wordplat.com/InteractiveKLineView/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linkaas.common.stockchart.compat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.linkaas.common.R;
import com.linkaas.common.stockchart.align.XMarkerAlign;
import com.linkaas.common.stockchart.align.YLabelAlign;
import com.linkaas.common.stockchart.align.YMarkerAlign;
import com.linkaas.common.stockchart.entry.ChartConfigs;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.EntrySet;

/**
 * <p>ViewUtils</p>
 * <p>Date: 2017/3/29</p>
 *
 * @author zhangzhi
 */

public class ViewUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxTodp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置 蜡烛图画笔的颜色和是否空心
     */
    public static Entry setUpCandlePaint(Paint candlePaint, EntrySet entrySet, int currentEntryIndex, ChartConfigs chartConfigs) {
        Entry entry = entrySet.getEntryList().get(currentEntryIndex);

        // 设置 涨、跌的颜色
        if (entry.getClose() > entry.getOpen()) { // 今日收盘价大于今日开盘价为涨
            candlePaint.setColor(chartConfigs.getIncreasingColor());
        } else if (entry.getClose() == entry.getOpen()) { // 今日收盘价等于今日开盘价有涨停、跌停、不涨不跌三种情况
            if (currentEntryIndex > 0) {
                if (entry.getOpen() > entrySet.getEntryList().get(currentEntryIndex - 1).getClose()) { // 今日开盘价大于昨日收盘价为涨停
                    candlePaint.setColor(chartConfigs.getIncreasingColor());
                } else if (entry.getOpen() == entrySet.getEntryList().get(currentEntryIndex - 1).getClose()) { // 不涨不跌
                    candlePaint.setColor(chartConfigs.getNeutralColor());
                } else { // 否则为跌停
                    candlePaint.setColor(chartConfigs.getDecreasingColor());
                }
            } else {
                if (entry.getOpen() > entrySet.getPreClose()) {
                    candlePaint.setColor(chartConfigs.getIncreasingColor());
                } else if (entry.getOpen() == entrySet.getPreClose()) {
                    candlePaint.setColor(chartConfigs.getNeutralColor());
                } else {
                    candlePaint.setColor(chartConfigs.getDecreasingColor());
                }
            }
        } else { // 今日收盘价小于今日开盘价为跌
            candlePaint.setColor(chartConfigs.getDecreasingColor());
        }

        if (candlePaint.getColor() == chartConfigs.getIncreasingColor()) {
            if (chartConfigs.getIncreasingStyle() == Paint.Style.STROKE) {
                candlePaint.setStyle(Paint.Style.STROKE);
            } else {
                candlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            }
        } else {
            if (chartConfigs.getDecreasingStyle() == Paint.Style.STROKE) {
                candlePaint.setStyle(Paint.Style.STROKE);
            } else {
                candlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            }
        }

        return entry;
    }

    /**
     * 初始化 ChartConfigs
     */
    public static ChartConfigs getSizeColor(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.InteractiveKLineView, defStyleAttr, defStyleAttr);

        final ChartConfigs chartConfigs = new ChartConfigs();

        try {
            // 与轴、网格有关的属性
            chartConfigs.setXLabelSize(a.getDimension(R.styleable.InteractiveKLineView_xLabelSize,
                    chartConfigs.getXLabelSize()));

            chartConfigs.setXLabelColor(a.getColor(R.styleable.InteractiveKLineView_xLabelColor,
                    chartConfigs.getXLabelColor()));

            chartConfigs.setXLabelViewHeight(a.getDimension(R.styleable.InteractiveKLineView_xLabelViewHeight,
                    chartConfigs.getXLabelViewHeight()));

            chartConfigs.setYLabelSize(a.getDimension(R.styleable.InteractiveKLineView_yLabelSize,
                    chartConfigs.getYLabelSize()));

            chartConfigs.setYLabelColor(a.getColor(R.styleable.InteractiveKLineView_yLabelColor,
                    chartConfigs.getYLabelColor()));

            int align = a.getInteger(R.styleable.InteractiveKLineView_yLabelAlign, YLabelAlign.LEFT.ordinal());
            chartConfigs.setYLabelAlign(YLabelAlign.values()[align]);

            chartConfigs.setAxisSize(a.getDimension(R.styleable.InteractiveKLineView_axisSize,
                    chartConfigs.getAxisSize()));

            chartConfigs.setAxisColor(a.getColor(R.styleable.InteractiveKLineView_axisColor,
                    chartConfigs.getAxisColor()));

            chartConfigs.setGridSize(a.getDimension(R.styleable.InteractiveKLineView_gridSize,
                    chartConfigs.getGridSize()));

            chartConfigs.setGridColor(a.getColor(R.styleable.InteractiveKLineView_gridColor,
                    chartConfigs.getGridColor()));

            // 与高亮、MarkerView 有关的属性
            chartConfigs.setHighlightSize(a.getDimension(R.styleable.InteractiveKLineView_highlightSize,
                    chartConfigs.getHighlightSize()));

            chartConfigs.setHighlightColor(a.getColor(R.styleable.InteractiveKLineView_highlightColor,
                    chartConfigs.getHighlightColor()));

            chartConfigs.setMarkerBorderSize(a.getDimension(R.styleable.InteractiveKLineView_markerBorderSize,
                    chartConfigs.getMarkerBorderSize()));

            chartConfigs.setMarkerBorderColor(a.getColor(R.styleable.InteractiveKLineView_markerBorderColor,
                    chartConfigs.getMarkerBorderColor()));

            chartConfigs.setMarkerTextSize(a.getDimension(R.styleable.InteractiveKLineView_markerTextSize,
                    chartConfigs.getMarkerTextSize()));

            chartConfigs.setMarkerTextColor(a.getColor(R.styleable.InteractiveKLineView_markerTextColor,
                    chartConfigs.getMarkerTextColor()));

            align = a.getInteger(R.styleable.InteractiveKLineView_xMarkerAlign, XMarkerAlign.AUTO.ordinal());
            chartConfigs.setXMarkerAlign(XMarkerAlign.values()[align]);

            align = a.getInteger(R.styleable.InteractiveKLineView_yMarkerAlign, YMarkerAlign.AUTO.ordinal());
            chartConfigs.setYMarkerAlign(YMarkerAlign.values()[align]);

            // 与分时图有关的属性
            chartConfigs.setTimeLineSize(a.getDimension(R.styleable.InteractiveKLineView_timeLineSize,
                    chartConfigs.getTimeLineSize()));

            chartConfigs.setTimeLineColor(a.getColor(R.styleable.InteractiveKLineView_timeLineColor,
                    chartConfigs.getTimeLineColor()));

            chartConfigs.setTimeLineMaxCount(a.getInteger(R.styleable.InteractiveKLineView_timeLineMaxCount,
                    chartConfigs.getTimeLineMaxCount()));

            // 与蜡烛图有关的属性
            chartConfigs.setCandleBorderSize(a.getDimension(R.styleable.InteractiveKLineView_candleBorderSize,
                    chartConfigs.getCandleBorderSize()));

            chartConfigs.setCandleExtremumLabelSize(a.getDimension(R.styleable.InteractiveKLineView_candleExtremumLabelSize,
                    chartConfigs.getCandleExtremumLabelSize()));

            chartConfigs.setCandleExtremumLableColor(a.getColor(R.styleable.InteractiveKLineView_candleExtremumLableColor,
                    chartConfigs.getCandleExtremumLableColor()));

            chartConfigs.setShadowSize(a.getDimension(R.styleable.InteractiveKLineView_shadowSize,
                    chartConfigs.getShadowSize()));

            chartConfigs.setIncreasingColor(a.getColor(R.styleable.InteractiveKLineView_increasingColor,
                    chartConfigs.getIncreasingColor()));

            chartConfigs.setDecreasingColor(a.getColor(R.styleable.InteractiveKLineView_decreasingColor,
                    chartConfigs.getDecreasingColor()));

            chartConfigs.setNeutralColor(a.getColor(R.styleable.InteractiveKLineView_neutralColor,
                    chartConfigs.getNeutralColor()));

            chartConfigs.setPortraitDefaultVisibleCount(a.getInteger(R.styleable.InteractiveKLineView_portraitDefaultVisibleCount,
                    chartConfigs.getPortraitDefaultVisibleCount()));

            chartConfigs.setZoomInTimes(a.getInteger(R.styleable.InteractiveKLineView_zoomInTimes,
                    chartConfigs.getZoomInTimes()));

            chartConfigs.setZoomOutTimes(a.getInteger(R.styleable.InteractiveKLineView_zoomOutTimes,
                    chartConfigs.getZoomOutTimes()));

            int style = a.getInteger(R.styleable.InteractiveKLineView_increasingStyle, Paint.Style.FILL.ordinal());
            chartConfigs.setIncreasingStyle(Paint.Style.values()[style]);

            style = a.getInteger(R.styleable.InteractiveKLineView_decreasingStyle, Paint.Style.FILL.ordinal());
            chartConfigs.setDecreasingStyle(Paint.Style.values()[style]);

            // 与股票指标有关的属性
            chartConfigs.setMaLineSize(a.getDimension(R.styleable.InteractiveKLineView_maLineSize,
                    chartConfigs.getMaLineSize()));

            chartConfigs.setMa5Color(a.getColor(R.styleable.InteractiveKLineView_ma5Color,
                    chartConfigs.getMa5Color()));

            chartConfigs.setMa10Color(a.getColor(R.styleable.InteractiveKLineView_ma10Color,
                    chartConfigs.getMa10Color()));

            chartConfigs.setMa20Color(a.getColor(R.styleable.InteractiveKLineView_ma20Color,
                    chartConfigs.getMa20Color()));

            chartConfigs.setBollLineSize(a.getDimension(R.styleable.InteractiveKLineView_bollLineSize,
                    chartConfigs.getBollLineSize()));

            chartConfigs.setBollMidLineColor(a.getColor(R.styleable.InteractiveKLineView_bollMidLineColor,
                    chartConfigs.getBollMidLineColor()));

            chartConfigs.setBollUpperLineColor(a.getColor(R.styleable.InteractiveKLineView_bollUpperLineColor,
                    chartConfigs.getBollUpperLineColor()));

            chartConfigs.setBollLowerLineColor(a.getColor(R.styleable.InteractiveKLineView_bollLowerLineColor,
                    chartConfigs.getBollLowerLineColor()));

            chartConfigs.setKdjLineSize(a.getDimension(R.styleable.InteractiveKLineView_kdjLineSize,
                    chartConfigs.getKdjLineSize()));

            chartConfigs.setKdjKLineColor(a.getColor(R.styleable.InteractiveKLineView_kdjKLineColor,
                    chartConfigs.getKdjKLineColor()));

            chartConfigs.setKdjDLineColor(a.getColor(R.styleable.InteractiveKLineView_kdjDLineColor,
                    chartConfigs.getKdjDLineColor()));

            chartConfigs.setKdjJLineColor(a.getColor(R.styleable.InteractiveKLineView_kdjJLineColor,
                    chartConfigs.getKdjJLineColor()));

            chartConfigs.setMacdLineSize(a.getDimension(R.styleable.InteractiveKLineView_macdLineSize,
                    chartConfigs.getMacdLineSize()));

            chartConfigs.setMacdHighlightTextColor(a.getColor(R.styleable.InteractiveKLineView_macdHighlightTextColor,
                    chartConfigs.getMacdHighlightTextColor()));

            chartConfigs.setDeaLineColor(a.getColor(R.styleable.InteractiveKLineView_deaLineColor,
                    chartConfigs.getDeaLineColor()));

            chartConfigs.setDiffLineColor(a.getColor(R.styleable.InteractiveKLineView_diffLineColor,
                    chartConfigs.getDiffLineColor()));

            chartConfigs.setRsiLineSize(a.getDimension(R.styleable.InteractiveKLineView_rsiLineSize,
                    chartConfigs.getRsiLineSize()));

            chartConfigs.setRsi1LineColor(a.getColor(R.styleable.InteractiveKLineView_rsi1LineColor,
                    chartConfigs.getRsi1LineColor()));

            chartConfigs.setRsi2LineColor(a.getColor(R.styleable.InteractiveKLineView_rsi2LineColor,
                    chartConfigs.getRsi2LineColor()));

            chartConfigs.setRsi3LineColor(a.getColor(R.styleable.InteractiveKLineView_rsi3LineColor,
                    chartConfigs.getRsi3LineColor()));

            chartConfigs.setMaTextSize(a.getDimension(R.styleable.InteractiveKLineView_maTextSize,
                    chartConfigs.getMaTextSize()));

            chartConfigs.setMaTextColor(a.getColor(R.styleable.InteractiveKLineView_maTextColor,
                    chartConfigs.getMaTextColor()));

            chartConfigs.setBollTextSize(a.getDimension(R.styleable.InteractiveKLineView_bollTextSize,
                    chartConfigs.getBollTextSize()));

            chartConfigs.setBollTextColor(a.getColor(R.styleable.InteractiveKLineView_bollTextColor,
                    chartConfigs.getBollTextColor()));

            chartConfigs.setKdjTextSize(a.getDimension(R.styleable.InteractiveKLineView_kdjTextSize,
                    chartConfigs.getKdjTextSize()));

            chartConfigs.setKdjTextColor(a.getColor(R.styleable.InteractiveKLineView_kdjTextColor,
                    chartConfigs.getKdjTextColor()));

            chartConfigs.setMacdTextSize(a.getDimension(R.styleable.InteractiveKLineView_macdTextSize,
                    chartConfigs.getMacdTextSize()));

            chartConfigs.setMacdTextColor(a.getColor(R.styleable.InteractiveKLineView_macdTextColor,
                    chartConfigs.getMacdTextColor()));

            chartConfigs.setRsiTextSize(a.getDimension(R.styleable.InteractiveKLineView_rsiTextSize,
                    chartConfigs.getRsiTextSize()));

            chartConfigs.setRsiTextColor(a.getColor(R.styleable.InteractiveKLineView_rsiTextColor,
                    chartConfigs.getRsiTextColor()));

            // 其它
            chartConfigs.setLoadingTextSize(a.getDimension(R.styleable.InteractiveKLineView_loadingTextSize,
                    chartConfigs.getLoadingTextSize()));

            chartConfigs.setLoadingTextColor(a.getColor(R.styleable.InteractiveKLineView_loadingTextColor,
                    chartConfigs.getLoadingTextColor()));

            String loadingText = a.getString(R.styleable.InteractiveKLineView_loadingText);
            if (!TextUtils.isEmpty(loadingText)) {
                chartConfigs.setLoadingText(loadingText);
            }

            chartConfigs.setErrorTextSize(a.getDimension(R.styleable.InteractiveKLineView_errorTextSize,
                    chartConfigs.getErrorTextSize()));

            chartConfigs.setErrorTextColor(a.getColor(R.styleable.InteractiveKLineView_errorTextColor,
                    chartConfigs.getErrorTextColor()));

            String errorText = a.getString(R.styleable.InteractiveKLineView_errorText);
            if (!TextUtils.isEmpty(errorText)) {
                chartConfigs.setErrorText(errorText);
            }

        } finally {
            a.recycle();
        }

        return chartConfigs;
    }
}
