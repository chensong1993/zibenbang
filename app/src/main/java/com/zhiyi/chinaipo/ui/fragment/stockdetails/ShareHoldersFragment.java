package com.zhiyi.chinaipo.ui.fragment.stockdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.ShareHoldersConnector;
import com.zhiyi.chinaipo.models.entity.details.ShareHoldersEntity;
import com.zhiyi.chinaipo.models.entity.details.ShareListEntity;
import com.zhiyi.chinaipo.presenters.stocks.ShareHoldersPresenter;
import com.zhiyi.chinaipo.ui.adapter.market.TenShareholderAdapter;
import com.zhiyi.chinaipo.ui.adapter.slidetable.StringListAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * table页面点进来下面的股东tab
 */
public class ShareHoldersFragment extends BaseFragment<ShareHoldersPresenter> implements ShareHoldersConnector.View {

    @BindView(R.id.rv_gubengudong)
    RecyclerView mGuben;
    @BindView(R.id.rv_shidagudong)
    RecyclerView mShida;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    @BindView(R.id.ll_gudong)
    LinearLayout mLlGuDong;
    private String mStockCode;
    private List<String> mShareList;
    private List<ShareHoldersEntity> mShareHolders;

    //股本股东
    private StringListAdapter mShareListAdapter;
    //十大股东
    private TenShareholderAdapter mShidaGubenAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gudong;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        mPresenter.getShareList(mStockCode);
        mPresenter.getTopShareHolders(mStockCode);
        //股本股东
        mShareList = new ArrayList<>();
        mShareListAdapter = new StringListAdapter(getActivity(), mShareList);
        mGuben.setAdapter(mShareListAdapter);
        mGuben.setLayoutManager(new LinearLayoutManager(getActivity()));
        //惯性滑动
        mGuben.setNestedScrollingEnabled(false);
        //十大股东
        mShareHolders = new ArrayList<>();
        mShidaGubenAdapter = new TenShareholderAdapter(getActivity(), mShareHolders);
        mShida.setAdapter(mShidaGubenAdapter);
        mShida.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShida.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(getActivity(), R.color.hui)));
        //惯性滑动
        mShida.setNestedScrollingEnabled(false);

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showShareHolders(List<ShareHoldersEntity> mList) {
        mLlGuDong.setVisibility(View.VISIBLE);
        mTvReload.setVisibility(View.GONE);
        mShareHolders.clear();
        mShareHolders.addAll(mList);
        mShidaGubenAdapter.notifyDataSetChanged();
    }

    @Override
    public void showShareList(List<ShareListEntity> sList) {
        mLlGuDong.setVisibility(View.VISIBLE);
        mTvReload.setVisibility(View.GONE);
        if (sList.size() < 1) {
            return;
        }
        ShareListEntity gotShareList = sList.get(0);
        mShareList.clear();
        mShareList.add(NumericUtil.getStockValue(gotShareList.getTotal()));
        mShareList.add(NumericUtil.getStockValue(gotShareList.getOut_shr()));
        int totalHolders = gotShareList.getTotal_holders().intValue();
        mShareList.add("" + totalHolders);
        mShareListAdapter.notifyDataSetChanged();
    }

    @Override
    public void err() {
        mLlGuDong.setVisibility(View.GONE);
        mTvReload.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_reload)
    void reload() {
        init();
    }
}
