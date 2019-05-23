package com.zhiyi.chinaipo.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.activity.login.LoginActivity;

import java.util.Iterator;
import java.util.List;

/**
 * @author chensong
 * @date 2018/8/6 17:54
 */
public class StarUtil {
    public static void Star(View view, String mToken, Context context, List<StockPriceEntity> mStockList,int position){
        TextView textView = view.findViewById(R.id.tv_search_focus);
        if (mToken == null || mToken.equals("")) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            if (textView.getText().equals("+关注")) {
                LogUtil.i("1234567898", "onClick: " + view.getTag());
                StockCodeEntity mStockCodeEntity = new StockCodeEntity(null, mToken, mStockList.get(position).getStock_name(), mStockList.get(position).getStock_code(), mStockList.get(position).getLatest_price(), mStockList.get(position).getChng_pct(), mStockList.get(position).getLatest_turnover());
                App.getInstance().getStockCodeDao().insert(mStockCodeEntity);
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.yiguanzhu));
                textView.setText("已关注");
            } else {
                for (Iterator iter = App.getInstance().getStockCodeDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
                    StockCodeEntity element = (StockCodeEntity) iter.next();
                    if (mStockList.get(position).getStock_code().equals(element.getCode())) {
                        App.getInstance().getStockCodeDao().deleteByKey(element.get_id());
                        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.weiguanzhu));
                        textView.setText("+关注");

                    }
                }
            }
        }
    }
}
