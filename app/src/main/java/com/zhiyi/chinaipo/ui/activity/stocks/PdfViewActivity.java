package com.zhiyi.chinaipo.ui.activity.stocks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.shizhefei.guide.GuideHelper;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.SimpleActivity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.ToastUtil;

import butterknife.BindView;

/**
 * @note 公告详情
 * @anthor Song Chen
 */
public class PdfViewActivity extends SimpleActivity implements OnLoadCompleteListener, OnDrawListener, OnPageChangeListener {

    @BindView(R.id.pdf)
    PDFView pdfView;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_title_number)
    TextView mTvNumber;
    @BindView(R.id.tv_page)
    TextView mTvpage;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    private String mDocTitle;
    private String mDocURL;
    private GuideHelper guideHelper;

    @Override
    protected void initEventAndData() {
        initView();
        //  initGuide();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pdf_view;
    }

    private void initGuide() {
        String only = SPHelper.get("guideOnlyOne", "");
        if (only.equals("")) {
            guideHelper = new GuideHelper(this);
            View view = guideHelper.inflate(R.layout.item_guide);
            TextView textView = view.findViewById(R.id.tv_content);
            textView.setText("双指同时向外分开-放大  向内-缩小");
            view.findViewById(R.id.rl_guide).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guideHelper.dismiss();
                }
            });
            GuideHelper.TipData tipData = new GuideHelper.TipData(view, Gravity.CENTER);
            guideHelper.addPage(tipData);
            guideHelper.show(false);
            SPHelper.save("guideOnlyOne", "123456");
        }
    }

    private void initView() {
          mRlProgress.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mDocTitle = intent.getExtras().getString(Constants.PARAMETER_ANNOUNCEMENT_TITLE, "");
            mDocURL = intent.getExtras().getString(Constants.PARAMETER_ANNOUNCEMENT_URL, "");
            LogUtil.i("url", mDocURL);
        } else {
            mDocTitle = "";
            mDocURL = "";
        }

        //pdf页数
        mTvTitle.setText(mDocTitle);
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pdfView.fileFromLocalStorage(this, this, this, mDocURL, URLUtil.guessFileName(mDocURL, null, null));

    }


    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    /**
     * 加载完成回调
     * nbPages 总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
          mRlProgress.setVisibility(View.GONE);
    }

    /**
     * 翻页回调
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
        if (page >= 0 && pageCount >= 0) {
            mTvpage.setText(page + "/" + pageCount);
            LogUtil.i("onPageChanged", "onPageChanged: " + page + "   " + pageCount);
        } else {
            ToastUtil.showToast(this, "加载失败");
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("post", "Dpost");
        //  pdfView = null;
    }


    public static class Builder {

        private String docTitle;
        private String documentUrl;
        private Context mContext;

        public PdfViewActivity.Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public PdfViewActivity.Builder setDocumentURL(String docURL) {
            this.documentUrl = docURL;
            return this;
        }

        public PdfViewActivity.Builder setDocTitle(String docTitle) {
            this.docTitle = docTitle;
            return this;
        }

    }

    public static void launch(PdfViewActivity.Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, PdfViewActivity.class);
        intent.putExtra(Constants.PARAMETER_ANNOUNCEMENT_URL, builder.documentUrl);
        intent.putExtra(Constants.PARAMETER_ANNOUNCEMENT_TITLE, builder.docTitle);
        builder.mContext.startActivity(intent);
    }

}
