package com.zhiyi.chinaipo.ui.fragment.stockdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.SummaryConnector;
import com.zhiyi.chinaipo.models.entity.details.ManagementEntity;
import com.zhiyi.chinaipo.models.entity.details.ProfileEntity;
import com.zhiyi.chinaipo.presenters.stocks.SummaryPresenter;
import com.zhiyi.chinaipo.ui.adapter.slidetable.DatumGongsiGaiKuoAdapter;
import com.zhiyi.chinaipo.ui.adapter.slidetable.ManagementsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * table详情页面的资料table
 */
public class SummaryFragment extends BaseFragment<SummaryPresenter> implements SummaryConnector.View {

    @BindView(R.id.rv_zhongdashijian)
    RecyclerView mRvzongdashijian;
    @BindView(R.id.rv_gongsigaikuo)
    RecyclerView mRvgongsi;
    @BindView(R.id.rv_guanliceng)
    RecyclerView mRvguanliceng;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    @BindView(R.id.ll_ziliao)
    LinearLayout mLlZiliao;
    private String mStockCode;
    private int pageOffset;
    private List<String> mProfileValues;
    private List<ManagementEntity> mManagements;
    private DatumGongsiGaiKuoAdapter mGaiKuoAdapter;
   // private StringListAdapter mTopEvents, mProfileAdapter;
    private ManagementsAdapter mF10Adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ziliao;
    }

    @Override
    protected void initEventAndData() {
        initview();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initview() {
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        mPresenter.getProfile(mStockCode);
        // mPresenter.getManagement(mStockCode, pageOffset);
        mPresenter.getManagement(mStockCode, ++pageOffset);
//        mProfileAdapter = new StringListAdapter(getActivity(), getGudong());
        //重大事件
      //  mRvzongdashijian.setAdapter(mProfileAdapter);
      //  mRvzongdashijian.setLayoutManager(new LinearLayoutManager(getContext()));
        //惯性滑动
        mRvzongdashijian.setNestedScrollingEnabled(false);
//        //公司概括
        mProfileValues = new ArrayList<>();
        mGaiKuoAdapter=new DatumGongsiGaiKuoAdapter(getActivity(),mProfileValues);
       // mProfileAdapter = new StringListAdapter(getActivity(), mProfileValues);
        mRvgongsi.setAdapter(mGaiKuoAdapter);
        mRvgongsi.setLayoutManager(new LinearLayoutManager(getContext()));
        //惯性滑动
        mRvgongsi.setNestedScrollingEnabled(false);
       //管理层
        mManagements = new ArrayList<>();
        mF10Adapter = new ManagementsAdapter(getActivity(), mManagements);
        mRvguanliceng.setAdapter(mF10Adapter);
        mRvguanliceng.setLayoutManager(new LinearLayoutManager(getContext()));
        //惯性滑动
        mRvguanliceng.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showProfile(ProfileEntity profile) {
        mLlZiliao.setVisibility(View.VISIBLE);
        mTvReload.setVisibility(View.GONE);
        mProfileValues.clear();
        mProfileValues.add(profile.getCname());
        mProfileValues.add(profile.getBuild_date());
        mProfileValues.add(profile.getDeclaredate());
        mProfileValues.add(profile.getRegi_cap());
        mProfileValues.add(profile.getIndustry());
        mProfileValues.add(profile.getScope_buss());
        mProfileValues.add(profile.getDealer());
        mProfileValues.add(profile.getExchange_type());
        mProfileValues.add(profile.getDealer());
        mProfileValues.add(profile.getDealing_date());
        mProfileValues.add(profile.getLawyer());
        mProfileValues.add(profile.getAccountant());
        mProfileValues.add(profile.getLeg_person());
        mProfileValues.add(profile.getChairman());
        mProfileValues.add(profile.getBoard_sectry());
        mProfileValues.add(profile.getOffice_addr());
        mProfileValues.add(profile.getTel());
        mProfileValues.add(profile.getFax());
        mProfileValues.add(profile.getEmail());
        mProfileValues.add(profile.getWeb_site());
        mGaiKuoAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_reload)
    void reload(){
        initview();
    }
    @Override
    public void showManagement(List<ManagementEntity> managers) {
        if (pageOffset == 1) {
            mManagements.clear();
        }
        mManagements.addAll(managers);
        mF10Adapter.notifyDataSetChanged();
    }

    @Override
    public void errM() {
        /*mLlZiliao.setVisibility(View.GONE);
        mTvReload.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void errP() {
        mLlZiliao.setVisibility(View.GONE);
        mTvReload.setVisibility(View.VISIBLE);
    }

}
