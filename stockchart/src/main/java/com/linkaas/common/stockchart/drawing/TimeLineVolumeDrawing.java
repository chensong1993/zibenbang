package com.linkaas.common.stockchart.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.linkaas.common.stockchart.compat.ViewUtils;
import com.linkaas.common.stockchart.entry.ChartConfigs;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.EntrySet;
import com.linkaas.common.stockchart.render.AbstractRender;

/**
 * <p>KLineVolumeDrawing K线成交量的绘制</p>
 * <p>Date: 2017/6/28</p>
 *
 * @author zhangzhi
 */

public class TimeLineVolumeDrawing implements IDrawing {
    private static final String TAG = "KLineVolumeDrawing";

    private Paint axisPaint; // X 轴和 Y 轴的画笔
    private Paint candlePaint; // 成交量画笔

    private final RectF candleRect = new RectF(); // 绘图区域
    private AbstractRender render;

    // 计算 MA(5, 10) 线条坐标用的
    private float[] xPointBuffer = new float[4];

    private float candleSpace = 0.1f; // entry 与 entry 之间的间隙，默认 0.1f (10%)
    private float[] xRectBuffer = new float[4];
    private float[] candleBuffer = new float[4];

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = render;
        final ChartConfigs chartConfigs = render.getChartConfigs();

        if (axisPaint == null) {
            axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            axisPaint.setStyle(Paint.Style.STROKE);
        }
        axisPaint.setStrokeWidth(chartConfigs.getAxisSize());
        axisPaint.setColor(chartConfigs.getAxisColor());

        if (candlePaint == null) {
            candlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            candlePaint.setStyle(Paint.Style.FILL);
            candlePaint.setStrokeWidth(chartConfigs.getCandleBorderSize());
        }

        candleRect.set(contentRect);
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {
        final int count = (maxIndex - minIndex) * 4;
        if (xPointBuffer.length < count) {
            xPointBuffer = new float[count];
        }

//        final EntrySet entrySet = render.getEntrySet();
//        final Entry entry = entrySet.getEntryList().get(currentIndex);
        final int i = currentIndex - minIndex;

        if (currentIndex < maxIndex - 1) {
            xPointBuffer[i * 4 + 0] = currentIndex ;
            xPointBuffer[i * 4 + 1] = 0;
            xPointBuffer[i * 4 + 2] = currentIndex + 1 ;
            xPointBuffer[i * 4 + 3] = 0;
        }
    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        final EntrySet entrySet = render.getEntrySet();
        final ChartConfigs chartConfigs = render.getChartConfigs();

        canvas.save();
        canvas.clipRect(candleRect);

        canvas.drawRect(candleRect, axisPaint);

        for (int i = minIndex; i < maxIndex; i++) {
            // 设置画笔颜色
            Entry entry = ViewUtils.setUpCandlePaint(candlePaint, entrySet, i, chartConfigs);

            // 计算 成交量的矩形卓坐标
            xRectBuffer[0] = i + candleSpace;
            xRectBuffer[1] = 0;
            xRectBuffer[2] = i + 1 - candleSpace;
            xRectBuffer[3] = 0;
            render.mapPoints(xRectBuffer);

            candleBuffer[0] = 0;
            candleBuffer[1] = entry.getVolume();
            candleBuffer[2] = 0;
            candleBuffer[3] = minY;

            render.mapPoints(null, candleBuffer);

            if (Math.abs(candleBuffer[1] - candleBuffer[3]) < 1.f) { // 成交量非常小画一条直线
//                canvas.drawRect(xRectBuffer[0], candleBuffer[1] - 2, xRectBuffer[2], candleBuffer[1], candlePaint);
            } else {
                canvas.drawRect(xRectBuffer[0], candleBuffer[1], xRectBuffer[2], candleBuffer[3] - axisPaint.getStrokeWidth(), candlePaint);
            }
        }

        // 映射坐标，绘制成交量
        render.mapPoints(xPointBuffer);

        final int count = (maxIndex - minIndex) * 4;

        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }
}
