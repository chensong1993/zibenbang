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

package com.linkaas.common.stockchart;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.linkaas.common.R;
import com.linkaas.common.stockchart.compat.ViewUtils;
import com.linkaas.common.stockchart.drawing.KLineVolumeDrawing;
import com.linkaas.common.stockchart.drawing.KLineVolumeHighlightDrawing;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.StockKLineVolumeIndex;
import com.linkaas.common.stockchart.marker.XAxisTextMarkerView;
import com.linkaas.common.stockchart.marker.YAxisTextMarkerView;
import com.linkaas.common.stockchart.render.KLineRender;

public class InteractiveKLineFrameLayout extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "InteractiveKLineLayout";

    private Context context;

    private InteractiveKLineView kLineView;
    private KLineHandler kLineHandler;
    private KLineRender kLineRender;

//    private StockMACDIndex macdIndex;
//    private StockRSIIndex rsiIndex;
//    private StockKDJIndex kdjIndex;
//    private StockBOLLIndex bollIndex;

    private int stockMarkerViewHeight;
    private int stockIndexViewHeight;
    private int stockIndexTabHeight;
    private RectF currentRect;

    public InteractiveKLineFrameLayout(Context context) {
        this(context, null);
    }

    public InteractiveKLineFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InteractiveKLineFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        stockMarkerViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.stock_marker_view_height);
        stockIndexViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.stock_index_view_height);
        stockIndexTabHeight = context.getResources().getDimensionPixelOffset(R.dimen.stock_index_tab_height);

        initUI(context, attrs, defStyleAttr);
    }

    private void initUI(Context context, AttributeSet attrs, int defStyleAttr) {
        kLineView = new InteractiveKLineView(context);
        kLineRender = (KLineRender) kLineView.getRender();

        kLineRender.setChartConfigs(ViewUtils.getSizeColor(context, attrs, defStyleAttr));

        kLineView.setKLineHandler(new KLineHandler() {
            @Override
            public void onLeftRefresh() {
                if (kLineHandler != null) {
                    kLineHandler.onLeftRefresh();
                }
            }

            @Override
            public void onRightRefresh() {
                if (kLineHandler != null) {
                    kLineHandler.onRightRefresh();
                }
            }

            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {
                if (kLineHandler != null) {
                    kLineHandler.onSingleTap(e, x, y);
                }
            }

            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {
                if (kLineHandler != null) {
                    kLineHandler.onDoubleTap(e, x, y);
                }
            }

            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {
                if (kLineHandler != null) {
                    kLineHandler.onHighlight(entry, entryIndex, x, y);
                }
            }

            @Override
            public void onCancelHighlight() {
                if (kLineHandler != null) {
                    kLineHandler.onCancelHighlight();
                }
            }
        });

        // 成交量
        StockKLineVolumeIndex kLineVolumeIndex = new StockKLineVolumeIndex(stockIndexViewHeight);
        kLineVolumeIndex.addDrawing(new KLineVolumeDrawing());
        kLineVolumeIndex.addDrawing(new KLineVolumeHighlightDrawing());
        kLineRender.addStockIndex(kLineVolumeIndex);

        kLineRender.addMarkerView(new YAxisTextMarkerView(stockMarkerViewHeight));
        kLineRender.addMarkerView(new XAxisTextMarkerView(stockMarkerViewHeight));

        addView(kLineView);
    }

    public InteractiveKLineView getKLineView() {
        return kLineView;
    }

    public void setKLineHandler(KLineHandler kLineHandler) {
        this.kLineHandler = kLineHandler;
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (kLineHandler != null) {
            kLineHandler.onCancelHighlight();
        }

        kLineView.notifyDataSetChanged();
    }


}
