package com.zhiyi.chinaipo.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.SimpleActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @note 搜索界面
 * @anthor Song Chen
 */
public class SearchActivity extends SimpleActivity {

    @BindView(R.id.rl_seek)
    RelativeLayout mRlSeek;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    //    @BindView(R.id.et_seek)
//    EditText mEtSeek;
    @BindView(R.id.img_seek)
    ImageView mImgSeek;
    //    @BindView(R.id.imgcache)
//    ImageView mImgCache;
    @BindView(R.id.tv_zixun)
    TextView mTvZixun;
    @BindView(R.id.tv_hangqing)
    TextView mTvHangqing;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    private Intent intent;

   /* @OnClick(R.id.imgcache)
    void clearCache () {

    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //5.0以上新动画特性
       // Cinematics.requestFeature(this);
    }

    @OnClick(R.id.rl_back)
    void quit() {
        finish();
    }

    @OnClick(R.id.img_seek)
    void search() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            intent = new Intent(this, SearchDetailsActivity.class);
            intent.putExtra(Constants.SEARCHING_KEY_USING, "");
            intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_ALL);
            startActivity(intent);
        }
    }

    @OnClick(R.id.tv_zixun)
    void searchNews() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            intent = new Intent(this, SearchDetailsActivity.class);
            intent.putExtra(Constants.SEARCHING_KEY_USING, "");
            intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_NEWS);
            intent.putExtra(Constants.SEARCHING_TYPE, R.string.x_zixun);
            startActivity(intent);
        }
    }

    @OnClick(R.id.tv_hangqing)
    void searchStocks() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            intent = new Intent(this, SearchDetailsActivity.class);
            intent.putExtra(Constants.SEARCHING_KEY_USING, "");
            intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_STOCK);
            intent.putExtra(Constants.SEARCHING_TYPE, R.string.x_hangqing);
            // LogUtil.i("Got input as " , mEtSeek.getText().toString() + Constants.SEARCHING_TYPE_STOCK);
            startActivity(intent);
        }
    }

    @OnClick(R.id.tv_author)
    void searchAuthor() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            intent = new Intent(this, SearchDetailsActivity.class);
            intent.putExtra(Constants.SEARCHING_KEY_USING, "");
            intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_AUTHOR);
            intent.putExtra(Constants.SEARCHING_TYPE, R.string.x_author);
            startActivity(intent);
        }
    }

    @OnClick(R.id.rl_seek)
    void searchAll() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            intent = new Intent(this, SearchDetailsActivity.class);
            intent.putExtra(Constants.SEARCHING_KEY_USING, "");
            intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_ALL);
            startActivity(intent);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_seek;
    }

    @Override
    protected void initEventAndData() {
       /* mEtSeek.setImeActionLabel(getString(R.string.x_search), KeyEvent.KEYCODE_ENTER);
        mEtSeek.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    intent = new Intent(getBaseContext(), SearchDetailsActivity.class);
                    intent.putExtra(Constants.SEARCHING_KEY_USING, mEtSeek.getText().toString());
                    intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_ALL);
                    startActivity(intent);
                    SnackbarUtil.showShort(mContext.getWindow().getDecorView(), getString(R.string.start_serach) + mEtSeek.getText());
                    return true;
                }
                return false;
            }
        });*/
      /*  mEtSeek.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    intent = new Intent(getBaseContext(), SearchDetailsActivity.class);
                    intent.putExtra(Constants.SEARCHING_KEY_USING, mEtSeek.getText().toString());
                    intent.putExtra(Constants.SEARCHING_TYPE_KEY, Constants.SEARCHING_TYPE_ALL);
                    startActivity(intent);
                    SnackbarUtil.showShort(mContext.getWindow().getDecorView(), "开始搜索" + mEtSeek.getText());
                    return true;
                }
                return false;
            }
        });*/

    }

}
