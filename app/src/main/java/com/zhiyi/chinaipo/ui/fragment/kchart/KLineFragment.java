package com.zhiyi.chinaipo.ui.fragment.kchart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.linkaas.common.stockchart.InteractiveKLineFrameLayout;
import com.linkaas.common.stockchart.KLineHandler;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.EntrySet;
import com.linkaas.common.stockchart.render.KLineRender;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.KlineConnector;
import com.zhiyi.chinaipo.models.entity.details.KLineEntity;
import com.zhiyi.chinaipo.presenters.stocks.KlinePresenter;
import com.zhiyi.chinaipo.ui.activity.stocks.LandscapeKLineActivity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 日线，周线
 */
public class KLineFragment extends BaseFragment<KlinePresenter> implements KlineConnector.View {
    @BindView(R.id.kLineLayout)
    InteractiveKLineFrameLayout kLineLayout;
    private int loadStartPos = 500;
    private int loadEndPos = 600;
    private int loadCount = 100;

    private int pageOffset = 1;
    private final EntrySet entrySet = new EntrySet();
    private String mType = "day";
    private String mStockCode = "833027";
    private String mLandscape;
    private int mIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kline;
    }

    @Override
    protected void initEventAndData() {
        loadKLineData();
        initUI();
    }

    private void loadKLineData() {
        mType = getArguments().getString(Constants.FRAGMENT_KLINE_TYPE, "day");
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        mLandscape = getArguments().getString(Constants.PARAMETER_LANDSCAPE,"noLandscape");
        mPresenter.getKlineData(mStockCode, mType, pageOffset);
        mPresenter.getKlineData(mStockCode, mType, ++pageOffset);
        mPresenter.getKlineData(mStockCode, mType, ++pageOffset);
        mPresenter.getKlineData(mStockCode, mType, ++pageOffset);
        switch (mType) {
            case "day":
                mIndex = 1;
                break;
            case "week":
                mIndex = 2;
                break;
            case "month":
                mIndex = 3;
                break;
            default:
                mIndex=0;
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void showKline(List<KLineEntity> mKLineDatas) {
        if (pageOffset == 1) {
            entrySet.clearAll();
        }
        ListSort(mKLineDatas);
        for (KLineEntity kData : mKLineDatas) {
            float open = NumericUtil.stringToFloat(kData.getTopen());
            float high = NumericUtil.stringToFloat(kData.getThigh());
            float low = NumericUtil.stringToFloat(kData.getTlow());
            float close = NumericUtil.stringToFloat(kData.getTclose());
            int volume = NumericUtil.getVolumeValue(kData.getTvolume());
            String updated = kData.getTime().substring(2, 10);
            LogUtil.i("ListSort", updated);
            entrySet.insertFirst(new Entry(open, high, low, close, volume, updated));

        }
        entrySet.computeStockIndex();
        kLineLayout.getKLineView().setEntrySet(entrySet);
        kLineLayout.getKLineView().notifyDataSetChanged();
    }

    @Override
    public void err() {

    }

    //时间排序
    private void ListSort(List<KLineEntity> mKLineDatas) {
        Collections.sort(mKLineDatas, new Comparator<KLineEntity>() {
            @Override
            public int compare(KLineEntity o1, KLineEntity o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 0;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    private void initUI() {
        kLineLayout.setKLineHandler(new KLineHandler() {
            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {
                if (!mLandscape.equals("landscape")) {
                    Intent intent = new Intent(getActivity(), LandscapeKLineActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PARAMETER_STOCK_CODE, mStockCode);
                    bundle.putInt(Constants.PARAMETER_STOCK_INDEX, mIndex);
                    intent.putExtra(Constants.PARAMETER_LANDSCAPE_STOCK, bundle);
                    startActivity(intent);
               /* final KLineRender kLineRender = (KLineRender) kLineLayout.getKLineView().getRender();

                if (kLineRender.getKLineRect().contains(x, y)) {

                }*/

                }
            }
            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {
            }

            @Override
            public void onCancelHighlight() {

            }


            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {
                final KLineRender kLineRender = (KLineRender) kLineLayout.getKLineView().getRender();

                if (kLineRender.getKLineRect().contains(x, y)) {
                    kLineRender.zoomIn(x, y);
                }
            }

            @Override
            public void onLeftRefresh() {
                mPresenter.getKlineData(mStockCode, mType, ++pageOffset);
            }

            @Override
            public void onRightRefresh() {

            }
        });
    }

}
