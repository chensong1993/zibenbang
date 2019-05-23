package com.linkaas.common.stockchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;

import com.linkaas.common.stockchart.compat.GestureMoveActionCompat;
import com.linkaas.common.stockchart.compat.ViewUtils;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.EntrySet;
import com.linkaas.common.stockchart.entry.StockDataTest;
import com.linkaas.common.stockchart.render.AbstractRender;
import com.linkaas.common.stockchart.render.TimeLineRender;

public class InteractiveTimeLineView extends View {
    private static final String TAG = "InteractiveTimeLineView";
    private static final boolean DEBUG = false;

    // 与视图大小相关的属性
    private final RectF viewRect;
    private final float viewPadding;

    // 与数据加载、渲染相关的属性
    private AbstractRender render;
    private EntrySet entrySet;
    private TimeLineHandler timeLineHandler;

    private long startTime = 0;
    private final int interval = 300; // 0.1 Second
    Handler timerHandler = new Handler();

    // 与滚动控制、滑动加载数据相关的属性
    private static final int OVERSCROLL_DURATION = 500; // dragging 松手之后回中的时间，单位：毫秒
    private static final int OVERSCROLL_THRESHOLD = 220; // dragging 的偏移量大于此值时即是一个有效的滑动加载
    private static final int TIMELINE_STATUS_IDLE = 0; // 空闲
    private static final int TIMELINE_STATUS_RELEASE_BACK = 2; // 放手，回弹到 loading 位置
    private static final int TIMELINE_STATUS_LOADING = 3; // 加载中
    private static final int TIMELINE_STATUS_SPRING_BACK = 4; // 加载结束，回弹到初始位置
    private int timeLineStatus = TIMELINE_STATUS_IDLE;
    private int lastFlingX = 0;
    private int lastScrollDx = 0;
    private int lastEntrySize = 0; // 上一次的 entry 列表大小，用于判断是否成功加载了数据
    private int lastHighlightIndex = -1; // 上一次高亮的 entry 索引，用于减少回调
    private final ScrollerCompat scroller;

    // 与手势控制相关的属性
    private boolean onTouch = false;
    private boolean onLongPress = false;
    private boolean onDoubleFingerPress = false;
    private boolean onVerticalMove = false;

    public InteractiveTimeLineView(Context context) {
        this(context, null);
    }

    public InteractiveTimeLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InteractiveTimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewRect = new RectF();
        viewPadding = ViewUtils.dpTopx(context, 2);

        render = new TimeLineRender();

        gestureDetector.setIsLongpressEnabled(true);

        int touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        gestureCompat.setTouchSlop(touchSlop);

        final Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float t) {
                t -= 1.0f;
                return t * t * t * t * t + 1.0f;
            }
        };

        scroller = ScrollerCompat.create(context, interpolator);

        render.setChartConfigs(ViewUtils.getSizeColor(context, attrs, defStyleAttr));

    }

    public void setEntrySet(EntrySet set) {
        entrySet = set;
    }

    public void notifyDataSetChanged() {
        notifyDataSetChanged(true);
    }

    public void notifyDataSetChanged(final boolean invalidate) {
        render.setViewRect(viewRect);
        render.onViewRect(viewRect);
        render.setEntrySet(entrySet);

        if (invalidate) {
            invalidate();
        }
//        Runnable timerRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                long millis = System.currentTimeMillis() - startTime;
//                int seconds = (int) (millis / 1000);
//                int minutes = seconds / 60;
//                seconds = seconds % 60;
//                invalidate();
//                timerHandler.postDelayed(this, interval);
//            }
//        };
//
//        startTime = System.currentTimeMillis();
//        timerHandler.postDelayed(timerRunnable, 300);
    }

    public void setRender(AbstractRender render) {
        render.setChartConfigs(this.render.getChartConfigs());
        this.render = render;
    }

    public AbstractRender getRender() {
        return render;
    }

    public void setTimeLineHandler(TimeLineHandler timeLineHandler) {
        this.timeLineHandler = timeLineHandler;
    }

    public RectF getViewRect() {
        return viewRect;
    }

    private final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
            if (onTouch) {
                onLongPress = true;
                highlight(e.getX(), e.getY());
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (timeLineHandler != null) {
                timeLineHandler.onDoubleTap(e, e.getX(), e.getY());
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (timeLineHandler != null) {
                timeLineHandler.onSingleTap(e, e.getX(), e.getY());
            }
            return true;
        }

    });

    private final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            float f = detector.getScaleFactor();

            if (f < 1.0f) {
                render.zoomOut(detector.getFocusX(), detector.getFocusY());
            } else if (f > 1.0f) {
                render.zoomIn(detector.getFocusX(), detector.getFocusY());
            }
        }
    });

    private GestureMoveActionCompat gestureCompat = new GestureMoveActionCompat(null);

    private void highlight(float x, float y) {
        render.onHighlight(x, y);
        invalidate();

        int highlightIndex = render.getEntrySet().getHighlightIndex();
        Entry entry = render.getEntrySet().getHighlightEntry();

        if (entry != null && lastHighlightIndex != highlightIndex) {
            if (timeLineHandler != null) {
                timeLineHandler.onHighlight(entry, highlightIndex, x, y);
            }else {
                refreshComplete();
            }
            lastHighlightIndex = highlightIndex;
        }
    }

    private void cancelHighlight() {
        render.onCancelHighlight();
        invalidate();

        if (timeLineHandler != null) {
            timeLineHandler.onCancelHighlight();
        }
        lastHighlightIndex = -1;
    }

    /**
     * 加载完成
     */
    public void refreshComplete() {
        refreshComplete(false);
    }

    /**
     * 加载完成
     *
     * @param reverse 是否反转滚动的方向
     */
    public void refreshComplete(boolean reverse) {
        final int overScrollOffset = (int) render.getOverScrollOffset();

        if (DEBUG) {
            Log.i(TAG, "##d refreshComplete: refreshComplete... overScrollOffset = " + overScrollOffset);
        }

        if (overScrollOffset != 0) {
            timeLineStatus = TIMELINE_STATUS_SPRING_BACK;
            lastFlingX = 0;
           // scroller.startScroll(0, 0, reverse ? -overScrollOffset : overScrollOffset, 0, OVERSCROLL_DURATION);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean isRefreshing() {
        return timeLineStatus == TIMELINE_STATUS_LOADING;
    }

    public boolean isHighlighting() {
        return render.isHighlight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean onHorizontalMove = gestureCompat.onTouchEvent(event, event.getX(), event.getY());
        final int action = MotionEventCompat.getActionMasked(event);

        onVerticalMove = false;

        if (action == MotionEvent.ACTION_MOVE) {
            if (!onHorizontalMove && !onLongPress && !onDoubleFingerPress && gestureCompat.isDragging()) {
                onTouch = false;
                onVerticalMove = true;
            }
        }

        getParent().requestDisallowInterceptTouchEvent(!onVerticalMove);

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewRect.set(viewPadding, viewPadding, w - viewPadding, h - viewPadding);

        if (DEBUG) {
            Log.i(TAG, "##d onSizeChanged: " + viewRect);
        }

        // 在 Android Studio 预览模式下，添加一些测试数据，可以把 K 线图预览出来
        if (isInEditMode()) {
            EntrySet entrySet = StockDataTest.parseKLineData(StockDataTest.TIMELINE);
            if (entrySet != null) {
                entrySet.computeStockIndex();
            }

            setEntrySet(entrySet);
        }
        if (entrySet == null) {
            entrySet = new EntrySet();
        }

        notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
     //   final int action = MotionEventCompat.getActionMasked(e);

        gestureDetector.onTouchEvent(e);
        scaleDetector.onTouchEvent(e);

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                onTouch = true;
                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                onDoubleFingerPress = true;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (onLongPress) {
                    highlight(e.getX(), e.getY());
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                onLongPress = false;
                onDoubleFingerPress = false;
                onTouch = false;

                cancelHighlight();

                break;

            }
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        render.render(canvas);
    }
}
