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

package com.linkaas.common.stockchart.render;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.linkaas.common.stockchart.drawing.EmptyDataDrawing;
import com.linkaas.common.stockchart.drawing.HighlightDrawing;
import com.linkaas.common.stockchart.drawing.IDrawing;
import com.linkaas.common.stockchart.drawing.TimeLineDrawing;
import com.linkaas.common.stockchart.drawing.TimeLineGridAxisDrawing;
import com.linkaas.common.stockchart.entry.EntrySet;
import com.linkaas.common.stockchart.entry.StockIndex;
import com.linkaas.common.stockchart.marker.IMarkerView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TimeLineRender 分时图</p>
 * <p>Date: 2017/3/9</p>
 *
 * @author zhangzhi
 */

public class TimeLineRender extends AbstractRender {

    private final RectF chartRect = new RectF(); // 分时图显示区域

    private final float[] extremumY = new float[2];
    private final float[] contentPts = new float[2];

    /**
     * 当前缩放下显示的 entry 数量
     */
    private int currentVisibleCount = -1;

    /**
     * 竖屏时各级别缩放下显示的 entry 数量
     */
    private int[] portraitVisibleCountBuffer = new int[7];

    /**
     * 横屏时各级别缩放下显示的 entry 数量
     */
    private int[] landscapeVisibleCountBuffer = new int[7];
    private int minVisibleIndex;
    private int maxVisibleIndex;

    private final List<IDrawing> drawingList = new ArrayList<>();
//    private final MADrawing maDrawing = new MADrawing();
    private final EmptyDataDrawing emptyDataDrawing = new EmptyDataDrawing();
    private final HighlightDrawing highlightDrawing = new HighlightDrawing();
    private final TimeLineDrawing timeLineDrawing = new TimeLineDrawing();
    private final List<StockIndex> stockIndexList = new ArrayList<>(); // 股票指标列表

    public TimeLineRender() {
        //更改网张图和文字
        drawingList.add(new TimeLineGridAxisDrawing());
        drawingList.add(timeLineDrawing);
        drawingList.add(emptyDataDrawing);
        drawingList.add(highlightDrawing);
    }

    public void addDrawing(IDrawing drawing) {
        drawingList.add(drawing);
    }

    public void clearDrawing() {
        drawingList.clear();
    }

    public RectF getChartRect() {
        return chartRect;
    }

    @Override
    public void setEntrySet(EntrySet entrySet) {
        super.setEntrySet(entrySet);

        computeVisibleCount();

        postMatrixTouch(chartRect.width(), chartConfigs.getTimeLineMaxCount());

        entrySet.computeTimeLineMinMax(0, entrySet.getEntryList().size(), stockIndexList);
        computeExtremumValue(extremumY, entrySet.getMinY(), entrySet.getDeltaY());
        postMatrixValue(chartRect.width(), chartRect.height(), extremumY[0], extremumY[1]);
        postMatrixOffset(chartRect.left, chartRect.top);
        scroll(0);
//
//        if (timerRunnable != null) {
//            timerHandler.removeCallbacks(timerRunnable);
//        }

    }

    @Override
    public boolean canScroll(float dx) {
        return false;
    }

    @Override
    public boolean canDragging(float dx) {
        return false;
    }

    @Override
    public void onViewRect(RectF viewRect) {
        final float candleBottom = viewRect.bottom - chartConfigs.getXLabelViewHeight();
        final int remainHeight = (int) (candleBottom - viewRect.top);

        int calculateHeight = 0;
        for (StockIndex stockIndex : stockIndexList) {
                stockIndex.setEnable(stockIndex.getHeight() > 0
                        && calculateHeight + stockIndex.getHeight() < remainHeight);

                calculateHeight += stockIndex.getHeight();
        }

        chartRect.set(viewRect.left, viewRect.top, viewRect.right, candleBottom - calculateHeight);

        initDrawingList(chartRect, drawingList);

        calculateHeight = 0;
        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                calculateHeight += stockIndex.getHeight();

                float top = chartRect.bottom + chartConfigs.getXLabelViewHeight() + calculateHeight - stockIndex.getHeight();
                float bottom = chartRect.bottom + chartConfigs.getXLabelViewHeight() + calculateHeight;

                stockIndex.setRect(
                        viewRect.left + stockIndex.getPaddingLeft(),
                        top + stockIndex.getPaddingTop(),
                        viewRect.right - stockIndex.getPaddingRight(),
                        bottom - stockIndex.getPaddingBottom());

                initDrawingList(stockIndex.getRect(), stockIndex.getDrawingList());
            }
        }


    }

    @Override
    public void zoomIn(float x, float y) {

    }

    @Override
    public void zoomOut(float x, float y) {

    }

    private void initDrawingList(RectF rect, List<IDrawing> drawingList) {
        for (IDrawing drawing : drawingList) {
            drawing.onInit(rect, this);
        }
    }

    public void addMarkerView(IMarkerView markerView) {
        highlightDrawing.addMarkerView(markerView);
    }

    @Override
    public void render(final Canvas canvas) {
        final int count = entrySet.getEntryList().size();

        computeVisibleIndex();

        final float minY = count > 0 ? entrySet.getEntryList().get(entrySet.getMinYIndex()).getLow() : Float.NaN;
        final float maxY = count > 0 ? entrySet.getEntryList().get(entrySet.getMaxYIndex()).getHigh() : Float.NaN;

        renderDrawingList(canvas, drawingList, minY, maxY);

        for (StockIndex stockIndex : stockIndexList) {
            if (stockIndex.isEnable()) {
                float deltaY = stockIndex.getDeltaY();

                if (deltaY > 0) {
                    computeExtremumValue(extremumY,
                            stockIndex.getMinY(),
                            deltaY,
                            stockIndex.getExtremumYScale(),
                            stockIndex.getExtremumYDelta());
                    postMatrixValue(stockIndex.getMatrix(), stockIndex.getRect(), extremumY[0], extremumY[1]);
                    renderDrawingList(canvas, stockIndex.getDrawingList(), stockIndex.getMinY(), stockIndex.getMaxY());

                } else {
                    postMatrixValue(stockIndex.getMatrix(), stockIndex.getRect(), Float.NaN, Float.NaN);
                    renderDrawingList(canvas, stockIndex.getDrawingList(), Float.NaN, Float.NaN);
                }
            }
        }

    }

    private void renderDrawingList(Canvas canvas, List<IDrawing> drawingList, float minY, float maxY) {
        for (int i = minVisibleIndex ; i < maxVisibleIndex ; i++) {
            for (IDrawing drawing : drawingList) {
                drawing.computePoint(minVisibleIndex, maxVisibleIndex, i);
            }
        }

        for (IDrawing drawing : drawingList) {
            drawing.onComputeOver(canvas, minVisibleIndex, maxVisibleIndex, minY, maxY);
        }

        for (IDrawing drawing : drawingList) {
            drawing.onDrawOver(canvas);
        }
    }

    public void updateBreazeAnimation(Canvas canvas) {
        timeLineDrawing.onDrawOver(canvas);
    }

    private void computeVisibleCount() {
        if (currentVisibleCount == -1) {
            currentVisibleCount = 240;
        }
    }

    private void computeVisibleIndex() {
        contentPts[0] = chartRect.left;
        contentPts[1] = 0;
        invertMapPoints(contentPts);

        minVisibleIndex = contentPts[0] <= 0 ? 0 : (int) contentPts[0];
        maxVisibleIndex = minVisibleIndex + currentVisibleCount + 1;
        if (maxVisibleIndex > entrySet.getEntryList().size()) {
            maxVisibleIndex = entrySet.getEntryList().size();
        }

        // 计算当前显示区域内 entry 在 Y 轴上的最小值和最大值
        entrySet.computeTimeLineMinMax(minVisibleIndex, maxVisibleIndex, stockIndexList);

        computeExtremumValue(extremumY, entrySet.getMinY(), entrySet.getDeltaY());
        postMatrixValue(chartRect.width(), chartRect.height(), extremumY[0], extremumY[1]);
    }
    
    public void addStockIndex(StockIndex stockIndex) {
        stockIndexList.add(stockIndex);
    }

}
