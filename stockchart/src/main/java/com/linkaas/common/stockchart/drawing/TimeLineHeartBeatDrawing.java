package com.linkaas.common.stockchart.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.linkaas.common.stockchart.entry.ChartConfigs;
import com.linkaas.common.stockchart.render.AbstractRender;

/**
 * Created by wuhe on 2018/1/14.
 */

public class TimeLineHeartBeatDrawing implements IDrawing {
    private static Paint paint;
    private float X, Y;
    private Path path;
    private float initialScreenW;
    private float initialX, plusX;
    private float TX;
    private boolean translate;
    private int flash;

    private final RectF chartRect = new RectF(); // 分时图显示区域
    private AbstractRender render;

    private float[] pointBuffer = new float[2];

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = render;
        final ChartConfigs chartConfigs = render.getChartConfigs();

        chartRect.set(contentRect);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setShadowLayer(7, 0, 0, Color.RED);
        }

        paint.setStrokeWidth(chartConfigs.getTimeLineSize());
        paint.setColor(chartConfigs.getTimeLineColor());

        path= new Path();
        TX=0;
        translate=false;

        flash=0;
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {
//        lineBuffer[i * 4 + 2] = currentIndex + 1;
//        lineBuffer[i * 4 + 3] = entrySet.getEntryList().get(currentIndex + 1).getClose();
        path.moveTo(X, Y);
    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        canvas.save();

        flash+=1;
        if(flash<10 || (flash>20 && flash<30))
        {
            paint.setStrokeWidth(16);
            paint.setColor(Color.RED);
            paint.setShadowLayer(12, 0, 0, Color.RED);
        }
        else
        {
            paint.setStrokeWidth(10);
            paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
            paint.setShadowLayer(7, 0, 0, Color.RED);
        }

        if(flash==100)
        {
            flash=0;
        }

        path.lineTo(X,Y);
        canvas.translate(-TX, 0);
        canvas.drawPath(path, paint);

        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }

}
