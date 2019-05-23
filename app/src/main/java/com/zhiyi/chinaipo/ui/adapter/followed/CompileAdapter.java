package com.zhiyi.chinaipo.ui.adapter.followed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;

import java.util.List;

/**
 * 自选股里面的 编辑后的界面
 * Created by admin on 2017/7/31.
 */
public class CompileAdapter extends RecyclerView.Adapter<CompileAdapter.GuapaiViewHolder>{
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private Context mContext;
    private List<StockCodeEntity> mList;

    public CompileAdapter(Context context, List<StockCodeEntity> mList1, SwipeMenuRecyclerView menuRecyclerView) {
        this.mMenuRecyclerView = menuRecyclerView;
        mContext = context;
        mList = mList1;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GuapaiViewHolder itemView =new GuapaiViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_zixuan_shezhi, parent, false)) ;
        itemView.mMenuRecyclerView=mMenuRecyclerView;
        return (itemView);
    }


    @Override
    public void onBindViewHolder(final GuapaiViewHolder holder, int position) {
        holder.setShujuTop(mList.get(position));
        holder.refreshView();
        holder.imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(onSlide!=null) {
                  onSlide.onTop(holder.getAdapterPosition());
              }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
   //和activity通信
    public interface onSlide{
        void onTop(int i);
    }
    private onSlide onSlide;


    public void setOnSlide(onSlide onSlide) {
        this.onSlide = onSlide;
    }


    class GuapaiViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{

        TextView gupiao,gupiaohao;
        StockCodeEntity shujuTop;
        ImageView imgUp;
        SwipeMenuRecyclerView mMenuRecyclerView;
        public GuapaiViewHolder(View v) {
            super(v);
           gupiao= (TextView) v.findViewById(R.id.tv_gupiao);
            gupiaohao= (TextView) v.findViewById(R.id.gupaihao);
            imgUp= (ImageView) v.findViewById(R.id.img_up);
            v.findViewById(R.id.img_tuodong).setOnTouchListener(this);

        }

        public void setShujuTop(StockCodeEntity shujuTop) {
            this.shujuTop = shujuTop;
        }


        void refreshView() {
           gupiao.setText(shujuTop.getName());
            gupiaohao.setText(shujuTop.getCode());
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
