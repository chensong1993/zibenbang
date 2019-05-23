package com.zhiyi.chinaipo.ui.fragment.kchart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.linkaas.common.stockchart.InteractiveTimeLineLayout;
import com.linkaas.common.stockchart.TimeLineHandler;
import com.linkaas.common.stockchart.entry.Entry;
import com.linkaas.common.stockchart.entry.EntrySet;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.TimelineConnector;
import com.zhiyi.chinaipo.models.entity.details.TimeLineEntity;
import com.zhiyi.chinaipo.models.entity.details.Top5Entity;
import com.zhiyi.chinaipo.presenters.stocks.TimelinePresenter;
import com.zhiyi.chinaipo.ui.activity.stocks.LandscapeKLineActivity;
import com.zhiyi.chinaipo.ui.adapter.WudangsAdapter;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 分时，日线，周线
 */
public class TimelineFragment extends BaseFragment<TimelinePresenter> implements TimelineConnector.View {
    private WudangsAdapter mPaihangAdapter;

    @BindView(R.id.timeLineLayout)
    InteractiveTimeLineLayout mTimeLineLayout;
    @BindView((R.id.rl_wudang))
    RecyclerView mRvMai1;
    //    @BindView(R.id.rl_wudang1)
//    RecyclerView mRvMai2;
    private EntrySet entrySet;
    private String mStockCode;
    private String mPreClose;
    private String mLandscape;
    private List<Pair<String, String>> mTopsList;

    /* public static TimelineFragment newInstance() {
         Bundle args = new Bundle();
         TimelineFragment fragment = new TimelineFragment();
         fragment.setArguments(args);
         return fragment;
     }
 */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_timeline;
    }

    @Override
    protected void initEventAndData() {
        initView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initView() {
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "830925");
        mPreClose = getArguments().getString(Constants.TIMELINE_PARAMETER_PRE_CLOSE_PRICE, "0.01");
        mLandscape = getArguments().getString(Constants.PARAMETER_LANDSCAPE, "noLandscape");
        // initUI();
//        initData();
        LogUtil.i("mStockCode", mStockCode + mPreClose);
        if (mStockCode != null) {
            mPresenter.setStockCode(mStockCode);
            for (int i = 0; i < 22; i++) {
                mPresenter.getTimeline(mStockCode);
            }
            mPresenter.getTopDeals(mStockCode);
        }
        mTopsList = new ArrayList<>();
        mPaihangAdapter = new WudangsAdapter(getActivity(), mTopsList);
        mRvMai1.setAdapter(mPaihangAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvMai1.setLayoutManager(layoutManager);

//        mPaihangAdapter = new WudangsAdapter(getActivity(), getZhuban());
//        mRvMai2.setAdapter(mPaihangAdapter);
//        mRvMai2.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTimeLineLayout.getTimeLineView().setEntrySet(entrySet);
    }


    private void initData() {
        entrySet = new EntrySet();
        entrySet.clearAll();
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
            String nowString = parser.format(new Date());
            Date nowDate = parser.parse(nowString);
            Date startAM = parser.parse("9:30");
            Date stopAM = parser.parse("11:30");
            Date startPM = parser.parse("13:00");
            Date stopPM = parser.parse("15:00");

            Entry entry0 = new Entry(NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), 0, NumericUtil.stringToFloat(mPreClose), "9:30");
            entrySet.addEntry(entry0);
            Entry entry1 = new Entry(NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), 0, NumericUtil.stringToFloat(mPreClose), "11:30");
            entrySet.addEntry(entry1);
            Entry entry2 = new Entry(NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), 0, NumericUtil.stringToFloat(mPreClose), "14:00");
            entrySet.addEntry(entry2);
            Entry entry3 = new Entry(NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), 0, NumericUtil.stringToFloat(mPreClose), "15:00");
            entrySet.addEntry(entry3);
            for (int i = 3; i < 243; i++) {

                if (i < 123 && nowDate.after(startAM) && nowDate.before(stopAM) && nowDate.getTime() < startAM.getTime() + Constants.ONE_MINUTE_IN_MILLIS * (i - 3)) {
//                    Log.d("Prebuild timeline", "Before setup at index " + i);
                    break;
                }

                if (i > 123 && nowDate.after(startPM) && nowDate.before(stopPM) && nowDate.getTime() < startPM.getTime() + Constants.ONE_MINUTE_IN_MILLIS * (i - 123)) {
                    Log.d("Prebuild timeline", "Before setup at index " + i + ", and time " + nowDate.getTime() + ", and incremental " + Constants.ONE_MINUTE_IN_MILLIS * (i - 3));
                    break;
                }

                Date incrementalTime = new Date(startAM.getTime() + Constants.ONE_MINUTE_IN_MILLIS * (i - 3));

                if (i > 123) {
                    if (nowDate.after(stopAM) && nowDate.before(startPM)) {
                        break;
                    }
                    incrementalTime = new Date(startPM.getTime() + Constants.ONE_MINUTE_IN_MILLIS * (i - 123));
                }
                String currentTimeString = parser.format(incrementalTime);

                Entry entry = new Entry(NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), NumericUtil.stringToFloat(mPreClose), 0, NumericUtil.stringToFloat(mPreClose), currentTimeString);
                entrySet.addEntry(entry);

            }

            entrySet.getEntryList().get(0).setXLabel("09:30");
            entrySet.getEntryList().get(1).setXLabel("10:30");
            entrySet.getEntryList().get(2).setXLabel("11:30");
            entrySet.getEntryList().get(3).setXLabel("14:00");
            entrySet.getEntryList().get(4).setXLabel("15:00");

            entrySet.setPreClose(NumericUtil.stringToFloat(mPreClose));
            entrySet.computeStockIndex();
            mTimeLineLayout.getTimeLineView().setEntrySet(entrySet);
            mTimeLineLayout.getTimeLineView().notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initUI() {

        mTimeLineLayout.setTimeLineHandler(new TimeLineHandler() {
            @Override
            public void onSingleTap(MotionEvent e, float x, float y) {
                if (!mLandscape.equals("landscape")) {
                    Intent intent = new Intent(getActivity(), LandscapeKLineActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PARAMETER_STOCK_CODE, mStockCode);
                    bundle.putInt(Constants.PARAMETER_STOCK_INDEX, 0);
                    bundle.putString(Constants.TIMELINE_PARAMETER_PRE_CLOSE_PRICE, "0.01");
                    intent.putExtra(Constants.PARAMETER_LANDSCAPE_STOCK, bundle);
                    startActivity(intent);
                }

            }

            @Override
            public void onDoubleTap(MotionEvent e, float x, float y) {

            }

            @Override
            public void onHighlight(Entry entry, int entryIndex, float x, float y) {

            }

            @Override
            public void onCancelHighlight() {

            }
        });
    }

    private void updateData(List<TimeLineEntity> timeDatas) {

        if (mPreClose.equals("0.01")) {
            mPreClose = timeDatas.get(0).getLclose();
            initData();
        }
        //initData();
        for (TimeLineEntity it : timeDatas) {
            float open = NumericUtil.stringToFloat(it.getTopen());
            float high = NumericUtil.stringToFloat(it.getHighest_price());
            float low = NumericUtil.stringToFloat(it.getLowest_price());
            float close = NumericUtil.stringToFloat(it.getLatest_price());
            int volume = NumericUtil.getVolumeValue(it.getLatest_volume());
            float average = NumericUtil.stringToFloat(it.getAvg_price());
            String updated = it.getUpdated_at().substring(11, 16);
            //entrySet.setPreClose(NumericUtil.stringToFloat(it.getLclose()));
            Entry entry = new Entry(open, high, low, close, volume, average, updated);
            entrySet.insertAt(entry);

        }

        entrySet.computeStockIndex();

        mTimeLineLayout.getTimeLineView().notifyDataSetChanged();
    }

    @Override
    public void setupPrePrice(String preClosePrice) {
        try {
            if (preClosePrice != null) {
                mPresenter.setStockCode(mStockCode);
                for (int i = 0; i < 22; i++) {
                    mPresenter.getTimeline(mStockCode);
                }
                mPresenter.getTopDeals(mStockCode);
                if (mPreClose.equals("0.01")) {
                    mPreClose = preClosePrice;
                    initData();
                }
            }
        } catch (Exception e) {

        }

    }


    @Override
    public void showTimeline(List<TimeLineEntity> mList) {
        updateData(mList);
        LogUtil.i("updateData", "" + mList.size());
    }

    @Override
    public void showTops(Top5Entity mTops) {
        mTopsList.clear();
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getSale5_price()), NumericUtil.getStockValue(mTops.getSale5_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getSale4_price()), NumericUtil.getStockValue(mTops.getSale4_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getSale3_price()), NumericUtil.getStockValue(mTops.getSale3_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getSale2_price()), NumericUtil.getStockValue(mTops.getSale2_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getSale1_price()), NumericUtil.getStockValue(mTops.getSale1_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getBuy1_price()), NumericUtil.getStockValue(mTops.getBuy1_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getBuy2_price()), NumericUtil.getStockValue(mTops.getBuy2_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getBuy3_price()), NumericUtil.getStockValue(mTops.getBuy3_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getBuy4_price()), NumericUtil.getStockValue(mTops.getBuy4_amount())));
        mTopsList.add(Pair.create(NumericUtil.getStockValue(mTops.getBuy5_price()), NumericUtil.getStockValue(mTops.getBuy5_amount())));
        LogUtil.i("getBuy2_amount", mTops.getBuy5_amount() + "'");
        mPaihangAdapter.notifyDataSetChanged();
    }

    @Override
    public void error() {
        mTopsList.clear();
        for (int i = 0; i < 10; i++) {
            mTopsList.add(Pair.create("--", "--"));
        }
        mPaihangAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
