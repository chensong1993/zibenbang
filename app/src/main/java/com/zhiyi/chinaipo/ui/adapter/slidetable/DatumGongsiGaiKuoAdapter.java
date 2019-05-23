package com.zhiyi.chinaipo.ui.adapter.slidetable;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;

import java.util.List;

/**
 * 公司概括
 */

public class DatumGongsiGaiKuoAdapter extends RecyclerView.Adapter<DatumGongsiGaiKuoAdapter.ViewHolder> {

    View mHeaderView;
    private Context context;
    String name[] = {"公司名称", "成立日期", "挂牌日期", "注册资本", "所属行业", "主营业务", "主办券商", "转让方式", "做市商", "做市起始日", "律师事务所", "会计事务所", "法人代表", "董事长", "董秘", "地址", "电话", "传真", "邮件", "网站"};
    List<String> mStringList;

    public DatumGongsiGaiKuoAdapter(Context context, List<String> mStringList) {
        this.context = context;
        this.mStringList = mStringList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_placement_replication, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setName(name[position],mStringList.get(position));
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName, mTvGongsiGaiKuo;
        String name,conetnt;

        public ViewHolder(final View item) {
            super(item);
            if (item == mHeaderView) {
                return;
            }
            mTvGongsiGaiKuo = item.findViewById(R.id.tv_placement_replication);
            mTvName = item.findViewById(R.id.tv_name);
        }

        public void setName(String name,String conetnt) {
            this.name = name;
            this.conetnt=conetnt;
        }

        void refreshView() {
            mTvName.setText(name);
            mTvGongsiGaiKuo.setText(conetnt);
        }
    }

}