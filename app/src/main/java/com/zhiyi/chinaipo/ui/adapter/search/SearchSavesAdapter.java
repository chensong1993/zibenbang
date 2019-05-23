package com.zhiyi.chinaipo.ui.adapter.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.search.SearchSave;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

/**
 * 搜索记录
 */
public class SearchSavesAdapter extends RecyclerView.Adapter<SearchSavesAdapter.ViewHolder> {
    private Context mContext;
    private ItemClickListener ItemClickListener = null;
    private List<SearchSave> mList;


    public SearchSavesAdapter(Context context, List<SearchSave> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        ItemClickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_search_save, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setmSearchSave(mList.get(position));
        holder.refreshView();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    ItemClickListener.onItemClick(view, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        TextView mTvSearchSave;
        SearchSave mSearchSave;
        SwipeMenuRecyclerView mMenuRecyclerView;

        public ViewHolder(View v) {
            super(v);
            mTvSearchSave = v.findViewById(R.id.tv_searchName);


        }

        public void setmSearchSave(SearchSave mSearchSave) {
            this.mSearchSave = mSearchSave;
        }

        void refreshView() {
            mTvSearchSave.setText(mSearchSave.getSearchName());

        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mMenuRecyclerView.startDrag(this);
                    break;
                }
            }
            return false;
        }
    }


}
