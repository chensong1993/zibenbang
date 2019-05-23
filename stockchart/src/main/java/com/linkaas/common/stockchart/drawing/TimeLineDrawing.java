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

package com.linkaas.common.stockchart.drawing;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.linkaas.common.stockchart.entry.ChartConfigs;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.EntrySet;
import com.linkaas.common.stockchart.render.AbstractRender;

/**
 * <p>TimeLineDrawing</p>
 * <p>Date: 2017/3/9</p>
 *
 * @author zhangzhi
 */

public class TimeLineDrawing implements IDrawing {

    private Paint linePaint, areaPaint, avgLinePaint, breathePaint;

    private final RectF chartRect = new RectF(); // 分时图显示区域

    private AbstractRender render;

    private float[] lineBuffer = new float[4];
    private float[] avgLineBuffer = new float[4];
    private float[] areaBuffer = new float[4];
    private float[] pointBuffer = new float[2];
    private final Matrix matrixValue = new Matrix();
    private long startTime;
    private float lineHeight, lineWidth;
   // private int breatheCount;
    private float lineTop = chartRect.top + lineHeight;

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = render;
        final ChartConfigs chartConfigs = render.getChartConfigs();

        if (linePaint == null) {
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            linePaint.setStyle(Paint.Style.STROKE);
        }

        linePaint.setStrokeWidth(chartConfigs.getTimeLineSize());
        linePaint.setColor(chartConfigs.getTimeLineColor());
        linePaint.setShadowLayer(4, 2, 2, 0x80000000);

        if (areaPaint == null) {
            areaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            areaPaint.setStyle(Paint.Style.FILL);
        }

        areaPaint.setStrokeWidth(1);
        areaPaint.setColor(chartConfigs.getFillColor());
//        areaPaint.setShadowLayer(4, 2, 2, 0x80000000);

        if (avgLinePaint == null) {
            avgLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            avgLinePaint.setStyle(Paint.Style.FILL);
        }
        avgLinePaint.setStrokeWidth(chartConfigs.getTimeLineSize() + 1);
        avgLinePaint.setColor(chartConfigs.getAvgLineColor());
        avgLinePaint.setShadowLayer(4, 2, 2, 0x80000000);

        if (breathePaint == null) {
            breathePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            breathePaint.setStyle(Paint.Style.FILL);
        }
        breathePaint.setStrokeWidth(1f);
        breathePaint.setColor(chartConfigs.getBreatheColor());

        chartRect.set(contentRect);

       // this.breatheCount = 0;

        lineHeight = chartRect.height() / 2;
        lineWidth = chartRect.width() / 2;
        lineTop = chartRect.top + lineHeight;

    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {
        final int count = (maxIndex - minIndex ) * 4;

        if (lineBuffer.length < count) {
            lineBuffer = new float[count];
            areaBuffer = new float[count];
            avgLineBuffer = new float[count];
        }

        final EntrySet entrySet = render.getEntrySet();
        final Entry entry = entrySet.getEntryList().get(currentIndex);
        final int i = currentIndex - minIndex;

        if (currentIndex < maxIndex - 1) {
            lineBuffer[i * 4 + 0] = currentIndex;
            lineBuffer[i * 4 + 1] = entry.getClose();
            lineBuffer[i * 4 + 2] = currentIndex + 1;
            lineBuffer[i * 4 + 3] = entrySet.getEntryList().get(currentIndex + 1).getClose();

            areaBuffer[i * 4 + 0] = currentIndex;
            areaBuffer[i * 4 + 1] = entry.getClose();
            areaBuffer[i * 4 + 2] = currentIndex + 1;
            areaBuffer[i * 4 + 3] = entrySet.getEntryList().get(currentIndex + 1).getClose();
        }

        if (currentIndex < maxIndex - 1) {
            avgLineBuffer[i * 4 + 0] = currentIndex ;
            avgLineBuffer[i * 4 + 1] = entry.getAverage();
            avgLineBuffer[i * 4 + 2] = currentIndex + 1 ;
            avgLineBuffer[i * 4 + 3] = entrySet.getEntryList().get(currentIndex + 1).getAverage();
        }
    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        canvas.save();
        canvas.clipRect(chartRect);
        render.mapPoints(avgLineBuffer);
        render.mapPoints(lineBuffer);
//        render.mapPoints(areaBuffer);

        final int count = (maxIndex - minIndex) * 4;

        if (count > 0) {
            Path mPath = new Path();
            mPath.moveTo(chartRect.left, lineTop);
            for (int i = 0 ; i < maxIndex - minIndex - 1; i++ ) {
                mPath.quadTo(lineBuffer[i * 4], lineBuffer[i * 4 + 1], lineBuffer[i * 4 + 2], lineBuffer[i * 4 + 3]);
            }
            mPath.lineTo(lineBuffer[lineBuffer.length - 6], chartRect.bottom);
            mPath.lineTo(chartRect.left, chartRect.bottom);
            mPath.lineTo(chartRect.left, lineTop);
            mPath.close();
            canvas.drawPath(mPath, areaPaint);
            canvas.drawLines(avgLineBuffer, 0, count, avgLinePaint);
            canvas.drawLines(lineBuffer, 0, count, linePaint);
        }

        // 计算高亮坐标
        if (render.isHighlight()) {
            final EntrySet entrySet = render.getEntrySet();
            final int lastEntryIndex = entrySet.getEntryList().size() - 2;
            final float[] highlightPoint = render.getHighlightPoint();
            pointBuffer[0] = highlightPoint[0];
            render.invertMapPoints(pointBuffer);
            final int highlightIndex = pointBuffer[0] < 0 ? 0 : (int) pointBuffer[0];
            final int i = highlightIndex - minIndex;
            Log.i("index",highlightIndex+"  "+lastEntryIndex+"  "+maxIndex);
            highlightPoint[0] = highlightIndex < lastEntryIndex ?
                    lineBuffer[i * 4 ] : lineBuffer[lastEntryIndex * 4 ];
            highlightPoint[1] = highlightIndex < lastEntryIndex ?
                    lineBuffer[i * 4 ] : lineBuffer[lastEntryIndex * 4 ];

        }
        canvas.restore();
    }

    @Override
    public void onDrawOver(final Canvas canvas) {
//        canvas.save();
//        canvas.clipRect(chartRect);

        //breatheCount ++;

//
//        matrix.postScale(30, 30);        // rotate 30° every second
//        matrix.postTranslate(100 * elapsedTime/1000, 0); // move 100 pixels to the right
//        canvas.concat(matrix);        // call this before drawing on the canvas!!
        /*if (lineBuffer.length > 5 && lineBuffer[lineBuffer.length - 6] != Float.NaN && lineBuffer[lineBuffer.length - 5] != Float.NaN) {
            breathePaint.setShadowLayer(4 , 0, 0, Color.BLUE);
            canvas.drawCircle(lineBuffer[lineBuffer.length - 6], lineBuffer[lineBuffer.length - 5], breatheCount, breathePaint);*/
//            final RectF pulseRect = new RectF(lineBuffer[lineBuffer.length - 6], lineBuffer[lineBuffer.length - 5], 15, 15 );
//            ValueAnimator slider = ValueAnimator.ofInt(0, 20);
//            slider.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator anim) {
//                    final int val = (Integer) anim.getAnimatedValue();
//                    int dx = val - animatedValue;
//                    animatedValue = val;
//
//                    linePaint.setShadowLayer(4 + elapsedTime / 100, 0, 0, Color.BLUE);
//                    canvas.drawCircle(lineBuffer[lineBuffer.length - 6], lineBuffer[lineBuffer.length - 5], 15, linePaint);
//                }
//            });
//            slider.setRepeatCount(ValueAnimator.INFINITE);
//            slider.setRepeatMode(ValueAnimator.REVERSE);
//
//            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(pulseRect,
//                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f),
//                    PropertyValuesHolder.ofFloat(View.SCALE_Y,0.5f));
//            scaleDown.setDuration(1000);
//            scaleDown.start();

        }

    /*    if (breatheCount > 13)
            breatheCount = 3;
//        canvas.restore();*/

 //   }

}
