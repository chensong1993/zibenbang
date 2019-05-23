package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.search.SearchSave;

import java.util.List;

/**
 * 搜索结果
 */

public class SearchSaveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchSave> list;
    private ItemClickListener ItemClickListener=null;
    public SearchSaveAdapter(Context mContext, List<SearchSave> list) {
        this.list = list;
    }


    public interface ItemClickListener {
        void onItemClick(View view,int position);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.ItemClickListener=itemClickListener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_save, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mViewHolder = (ViewHolder) holder;
        mViewHolder.setItem(list.get(position));
        mViewHolder.refreshView();
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemClickListener.onItemClick(view,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        SearchSave items;
        private TextView mtitle;


        public ViewHolder(final View item) {
            super(item);

            mtitle =  item.findViewById(R.id.tv_searchName);
        }

        public void setItem(SearchSave item) {
            this.items = item;
        }

        void refreshView() {
            mtitle.setText(items.getSearchName());

        }
    }

    public void chengeData(List<SearchSave> searchSaveList) {
        list = searchSaveList;
        notifyDataSetChanged();
    }


}