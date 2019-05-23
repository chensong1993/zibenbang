package com.zhiyi.chinaipo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhiyi.chinaipo.ui.activity.NewsDetailActivity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.util.tool.StringUtils;
import com.zhiyi.chinaipo.util.webview_photo.JavascriptInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author chen
 * @date 2017
 */
public class WebviewUtil {
    private Context context;
    //*代表所有文字，a代表a标签里面的文字 text-decoration: none 去掉原生下划线；border-bottom:2px solid orange，下划线高度和下划线颜色；color:#0d0d0d 字体颜色
    public final static String CSS_STYLE = "<style>* {font-size:18px;text-align:justify;}a{text-decoration: none;border-bottom:2px solid orange;color:#0d0d0d;}strong{font-weight:bold;}</style> ";
    private String[] imageUrls;

    public WebviewUtil(Context context) {
        this.context = context;
    }

    /**
     * webview显示内容
     *
     * @param content
     */
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    public void setWBcontents(WebView webView, String content) {

        WebSettings webSetting = webView.getSettings();
        // webview的背景颜色设置透明
        webView.setBackgroundColor(0);
        // 设置图片自适应
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 添加图片点击事件
        webSetting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        //页面图片适配

        Document doc_Dis = Jsoup.parse(content);
        Elements ele_Img = doc_Dis.getElementsByTag("img");

        for (Element element : ele_Img) {
            element.attr("width", "100%").attr("height", "auto");
        }
        String newHtmlContent = doc_Dis.toString();
        imageUrls = StringUtils.returnImageUrlsFromHtml(newHtmlContent);
        webView.addJavascriptInterface(new JavascriptInterface(context, imageUrls), "imagelistener");
        webView.loadData(CSS_STYLE + newHtmlContent, "text/html;charset=UTF-8", null);


    }


    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            int gotNewsID = RegUtil.getNewsId(url);
            String gotStockID = RegUtil.getStockId(url);
            LogUtil.i("gotStockID", gotNewsID + "");
            if (url == null) {
                return false;
            }
            if (gotNewsID != -1) {
                NewsDetailActivity.launch(new NewsDetailActivity.Builder()
                        .setOriginalId(gotNewsID)
                        .setContext(context)
                );
                LogUtil.i("11111", "1");
                return true;

            }
            if (gotStockID != null ) {
                StockDetailActivity.launch(new StockDetailActivity.Builder()
                        .setStockCode(gotStockID)
                        .setStockName("股票编号")
                        .setContext(context)
                );
                LogUtil.i("11111", "3");

              /*  WebActivity.launch(new WebActivity.Builder()
                        .setContext(context)
                        .setUrl(url)
                );
                LogUtil.i("11111", "2" + url);
                return true;*/
            }
            return true;
            // Otherwise allow the OS to handle things like tel, mailto, etc.
           /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
            return true;*/
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListener(view);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);


        }

        private void addImageClickListener(WebView webView) {
            webView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName(\"img\"); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "    objs[i].onclick=function()  " +
                    "    {  "
                    + "        window.imagelistener.openImage(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                    "    }  " +
                    "}" +
                    "})()");
        }


        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
