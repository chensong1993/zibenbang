/*
 * Copyright (C) 2017 Apache Open Source Project
 *
 *      https://wordplat.com/InteractiveTimeLineView/
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

package com.linkaas.common.stockchart;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.linkaas.common.R;
import com.linkaas.common.stockchart.compat.ViewUtils;
import com.linkaas.common.stockchart.drawing.TimeLineVolumeDrawing;
import com.linkaas.common.stockchart.drawing.TimeLineVolumeHighlightDrawing;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.TimeLineVolumeIndex;
import com.linkaas.common.stockchart.marker.XAxisTextMarkerView;
import com.linkaas.common.stockchart.marker.YAxisTextMarkerView;
import com.linkaas.common.stockchart.render.TimeLineRender;

public class InteractiveTimeLineLayout extends FrameLayout {
    private static final String TAG = "InteractiveTimeLineLayout";

    private Context context;

    private InteractiveTimeLineView timeLineView;
    private TimeLineHandler timeLineHandler;
    private TimeLineRender timeLineRender;

    private int stockMarkerViewHeight;
    private int stockIndexViewHeight;
    private RectF currentRect;

    public InteractiveTimeLineLayout(Context context) {
        this(context, null);
    }

    public InteractiveTimeLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InteractiveTimeLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        stockMarkerViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.stock_marker_view_height);
        stockIndexViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.stock_index_view_height);

        initUI(context, attrs, defStyleAttr);
    }

    private void initUI(Context context, AttributeSet attrs, int defStyleAttr) {
        timeLineView = new InteractiveTimeLineView(context);
        timeLineRender = (TimeLineRender) timeLineView.getRender();

        timeLineRender.setChartConfigs(ViewUtils.getSizeColor(context, attrs, defStyleAttr));

        timeLineView.setTimeLineHandler(new TimeLineHandler() {

            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {
                if (timeLineHandler != null) {
                    timeLineHandler.onSingleTap(e, x, y);
                }
            }

            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {
                if (timeLineHandler != null) {
                    timeLineHandler.onDoubleTap(e, x, y);
                }
            }

            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {
                if (timeLineHandler != null) {
                    timeLineHandler.onHighlight(entry, entryIndex, x, y);
                }
            }

            @Override
            public void onCancelHighlight() {
                if (timeLineHandler != null) {
                    timeLineHandler.onCancelHighlight();
                }
            }
        });

        // 成交量
        TimeLineVolumeIndex timeLineVolumeIndex = new TimeLineVolumeIndex(stockIndexViewHeight);
        timeLineVolumeIndex.addDrawing(new TimeLineVolumeDrawing());
        timeLineVolumeIndex.addDrawing(new TimeLineVolumeHighlightDrawing());
        timeLineRender.addStockIndex(timeLineVolumeIndex);

//        // MACD
//        HighlightDrawing macdHighlightDrawing = new HighlightDrawing();
//        macdHighlightDrawing.addMarkerView(new YAxisTextMarkerView(stockMarkerViewHeight));
//
//        macdIndex = new StockMACDIndex(stockIndexViewHeight);
//        macdIndex.addDrawing(new MACDDrawing());
//        macdIndex.addDrawing(new StockIndexYLabelDrawing());
//        macdIndex.addDrawing(macdHighlightDrawing);
//        macdIndex.setPaddingTop(stockIndexTabHeight);
//        timeLineRender.addStockIndex(macdIndex);

        timeLineRender.addMarkerView(new YAxisTextMarkerView(stockMarkerViewHeight));
        timeLineRender.addMarkerView(new XAxisTextMarkerView(stockMarkerViewHeight));

        addView(timeLineView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public InteractiveTimeLineView getTimeLineView() {
        return timeLineView;
    }

    public void setTimeLineHandler(TimeLineHandler timeLineHandler) {
        this.timeLineHandler = timeLineHandler;
    }
    
}
