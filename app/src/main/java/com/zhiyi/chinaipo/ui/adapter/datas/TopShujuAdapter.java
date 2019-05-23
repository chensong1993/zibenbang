package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.ui.activity.datas.IndustryListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.NewStockActivity;
import com.zhiyi.chinaipo.ui.activity.datas.OrganizationListActivity;
import com.zhiyi.chinaipo.ui.activity.tables.TableMultiActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;


/**
 * 数据模块，最上面的那几个图片和文字top
 */

public class TopShujuAdapter extends RecyclerView.Adapter<TopShujuAdapter.StringViewHolder> {

    private Context mContext;
    private String[] mNameList;
    private static int[] mImageList = {R.mipmap.ic_chuang, R.mipmap.ic_zhuanban, R.mipmap.ic_xingu, R.mipmap.ic_tongji, R.mipmap.ic_zhuban,
            R.mipmap.ic_lvshi, R.mipmap.ic_zuoshi, R.mipmap.ic_kuaiji,
    };

//    private List<String> mVideoList;

    private Intent intent;

    public TopShujuAdapter(Context context) {
        this.mContext = context;
        this.mNameList = mContext.getResources().getStringArray(R.array.news);
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_shujuone, parent, false);
        return new StringViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.setItemTop(position);
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mNameList.length;
    }

    class StringViewHolder extends RecyclerView.ViewHolder {

        TextView mBiaoti;
        ImageView imgicon;
        String itemName;
        int imageId;

        public StringViewHolder(View v) {
            super(v);
            mBiaoti = (TextView) v.findViewById(R.id.biaoti);
            imgicon = (ImageView) v.findViewById(R.id.img_icon);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        switch (getAdapterPosition()) {
                            case 0:
                                //创新层跳转到table
                                TableMultiActivity.launch(new TableMultiActivity.Builder()
                                        .setContext(mContext)
                                        .setBaseIndex(Constants.STOCK_BASE_INDEX_ARRAY[1])
                                        .setSortBy(Constants.STOCK_SORT_BY_CHNG_PCT)
                                        .setTitle("创新层")
                                        .setTabTitle(1)
                                );
                                break;
                            case 1:
                                //转板公司
                                TableMultiActivity.launch(new TableMultiActivity.Builder()
                                        .setContext(mContext)
                                        .setBaseIndex(Constants.STOCK_BASE_INDEX_ARRAY[5])
                                        .setSortBy(Constants.STOCK_SORT_BY_CHNG_PCT)
                                        .setTitle("转板公司")
                                        .setTabTitle(1)
                                );
                                break;
                            case 2:
                                //新股挂牌
                                mContext.startActivity(new Intent(mContext, NewStockActivity.class));
                                break;
                            case 3:
                                //行业统计
                                intent = new Intent(mContext, IndustryListActivity.class);
                                mContext.startActivity(intent);
                                break;
                            case 4:
                                //主办券商
                                Intent getInvestor = new Intent(mContext, OrganizationListActivity.class);
                                getInvestor.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "investor");
                                mContext.startActivity(getInvestor);
                                break;
                            case 5:
                                //做市商
                                Intent getDealer = new Intent(mContext, OrganizationListActivity.class);
                                getDealer.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "dealer");
                                mContext.startActivity(getDealer);
                                break;
                            case 6:
                                //律师事务所
                                Intent getLawyer = new Intent(mContext, OrganizationListActivity.class);
                                getLawyer.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "lawyer");
                                mContext.startActivity(getLawyer);
                                break;
                            case 7:
                                //会计事务所
                                Intent getAccountant = new Intent(mContext, OrganizationListActivity.class);
                                getAccountant.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "accountant");
                                mContext.startActivity(getAccountant);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }

        public void setItemTop(int itemCount) {
            this.itemName = mNameList[itemCount];
            this.imageId = mImageList[itemCount];
        }

        void refreshView() {
            mBiaoti.setText(itemName);
            Glide.with(mContext).load(imageId).into(imgicon);
        }
    }

}
